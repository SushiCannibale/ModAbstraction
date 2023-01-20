package fr.sushi.abstraction.capabilities;

import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;
import org.checkerframework.checker.units.qual.C;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class PlayerControlCapProvider implements ICapabilityProvider {

    public static Capability<PlayerControlCapability> CONTROL_CAP = CapabilityManager.get(new CapabilityToken<PlayerControlCapability>() {});

    @Nullable
    private PlayerControlCapability capability = null;

    private final LazyOptional<PlayerControlCapability> optional = LazyOptional.of(this::getCapability);

    private PlayerControlCapability getCapability() {
        return this.capability == null ? new PlayerControlCapability() : this.capability;
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        return cap == CONTROL_CAP ? optional.cast() : LazyOptional.empty();
    }
}
