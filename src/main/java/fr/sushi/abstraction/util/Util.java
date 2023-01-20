package fr.sushi.abstraction.util;

import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.server.ServerLifecycleHooks;

import javax.annotation.Nullable;
import java.util.UUID;

public class Util {

    public static boolean isMovingDirection(Entity entity, Direction dir) {
        Vec3 dm = entity.getDeltaMovement();

        return switch (dir) {
            case DOWN -> dm.y < 0; // /!\ Le delta <y> n'est (presque) jamais 0, la gravité est toujours appliquée, même au sol /!\
            case UP -> dm.y > 0;
            case NORTH -> dm.z < 0;
            case SOUTH -> dm.z > 0;
            case WEST -> dm.x < 0;
            case EAST -> dm.x > 0;
        };
    }

    public static boolean isMovingAxis(Entity entity, Direction.Axis axis) {
        Vec3 dm = entity.getDeltaMovement();

        return switch (axis) {
            case X -> dm.x != 0;
            case Y -> dm.y != 0;
            case Z -> dm.z != 0;
        };
    }

}
