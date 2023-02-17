package fr.sushi.abstraction.entities;

import fr.sushi.abstraction.capabilities.PlayerControlCapProvider;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

public class ControlBullet extends Projectile {

    public static final int MAX_TICK_BEFORE_KILL = 30;

    public ControlBullet(EntityType<? extends Projectile> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    public ControlBullet(Level level, Player player, InteractionHand hand) {
        this(ModEntities.CONTROL_BULLET.get(), level);
        this.setOwner(player);

        float angleRad = this.getOwner().getYRot() * Mth.PI / 180;

        double x = this.getOwner().getX() + Mth.cos(angleRad) * (hand == InteractionHand.MAIN_HAND ? -0.5 : 0.5);
        double y = this.getOwner().getY() + this.getOwner().getEyeHeight();
        double z = this.getOwner().getZ() + Mth.sin(angleRad) * (hand == InteractionHand.MAIN_HAND ? -0.5 : 0.5);

        this.setPos(x, y, z);

        this.setNoGravity(true);
    }

    @Override
    protected void defineSynchedData() { }

    @Override
    protected boolean canHitEntity(Entity pTarget) {
        return super.canHitEntity(pTarget) && pTarget instanceof IControllable;
    }

    @Override
    public boolean shouldRenderAtSqrDistance(double pDistance) {
        return pDistance < 16384.0D;
    }

    @Override
    public void tick() {
        super.tick();

        // TODO
        if (this.tickCount >= MAX_TICK_BEFORE_KILL)
            this.reset();

        /* Event triggerer de forge */
        HitResult hitresult = ProjectileUtil.getHitResult(this, this::canHitEntity);
        if (hitresult.getType() != HitResult.Type.MISS && !net.minecraftforge.event.ForgeEventFactory.onProjectileImpact(this, hitresult)) {
            this.onHit(hitresult);
        }

        double sine = Mth.sin(0.2f * (float)this.tickCount);

        Vec3 dMotion = this.getDeltaMovement().multiply(sine, sine, sine);
        System.out.println("SINE = " + sine + " | dMotion = " + dMotion);

        this.setPos(this.getX() + dMotion.x, this.getY() + dMotion.y, this.getZ() + dMotion.z);
    }

    @Override
    public boolean isOnFire() { return false; }

    /* Utilisé lorsque la bullet n'a d'effet sur aucun mob */
    private void reset() {
        if (getOwner() != null)
        {
            this.getOwner().getCapability(PlayerControlCapProvider.CONTROL_CAP).ifPresent(cap -> {
                if (!cap.isControllingEntity())
                    cap.setCanShoot(true);
            });
        }
        this.discard();
    }

    @Override
    protected void onHitBlock(BlockHitResult pResult) {
        super.onHitBlock(pResult);
        this.reset();
    }

    @Override
    protected void onHitEntity(EntityHitResult pResult) {
        if (!(pResult.getEntity() instanceof IControllable))
            return;

        Entity entity = pResult.getEntity();
        Player player = (Player)this.getOwner(); // Owner est de type Player (voir <Debugger$onUse()>)

        /* Un seul joueur peut controler une même entité */
        if (!((IControllable)entity).isFree())
            return;

        player.getCapability(PlayerControlCapProvider.CONTROL_CAP).ifPresent(cap -> {
            cap.setControlled(entity.getUUID());
            cap.setCanShoot(false);
            ((IControllable)entity).setController(player);
        });

        this.discard();
    }
}
