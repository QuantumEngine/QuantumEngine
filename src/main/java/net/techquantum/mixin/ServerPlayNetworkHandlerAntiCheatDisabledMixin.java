package net.techquantum.mixin;

//carpet

import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerPlayNetworkHandler.class)
public abstract class ServerPlayNetworkHandlerAntiCheatDisabledMixin {
    @Shadow private int floatingTicks;

    @Shadow private int vehicleFloatingTicks;

    @Inject(method = "tick", at = @At("HEAD"))
    private void restrictFloatingBits(CallbackInfo ci) {
            if (floatingTicks > 70) floatingTicks--;
            if (vehicleFloatingTicks > 70) vehicleFloatingTicks--;
    }

    @Redirect(method = "onVehicleMove", at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/server/network/ServerPlayNetworkHandler;isHost()Z"
    ))
    private boolean isServerTrusting(ServerPlayNetworkHandler serverPlayNetworkHandler) {
        return true;
    }

    @Redirect(method = "onPlayerMove", at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/server/network/ServerPlayerEntity;isInTeleportationState()Z"))
    private boolean relaxMoveRestrictions(ServerPlayerEntity serverPlayerEntity) {
        return true;
    }
}