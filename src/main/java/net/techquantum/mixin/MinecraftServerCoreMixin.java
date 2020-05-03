package net.techquantum.mixin;

//carpet

import net.minecraft.server.MinecraftServer;
import net.techquantum.QuantumEngine;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MinecraftServer.class)
public abstract class MinecraftServerCoreMixin
{
    // Dedicated server only
    @Inject(method = "loadWorld", at = @At("HEAD"))
    private void serverLoaded(CallbackInfo ci)
    {
        QuantumEngine.onServerLoaded((MinecraftServer) (Object) this);
    }

    @Inject(method = "shutdown", at = @At("HEAD"))
    private void serverClosed(CallbackInfo ci)
    {
        QuantumEngine.onServerClosed((MinecraftServer) (Object) this);
    }

}