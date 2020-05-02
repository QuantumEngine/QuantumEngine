package net.techquantum.mixin;

import net.minecraft.server.MinecraftServer;
import net.techquantum.commands.TickReportCommand;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MinecraftServer.class)
public abstract class TickReport_ServerTickMixin {

    @Shadow public abstract int getTicks();

    @Inject(method = "tick", at = @At("HEAD"))
    private void startTick(CallbackInfo ci) {
        TickReportCommand.startTick(((MinecraftServer)(Object)this).getTicks());
    }

    @Inject(method = "tick", at = @At("RETURN"))
    private void endTick(CallbackInfo ci) {
        TickReportCommand.endTick();
    }

    @Inject(method = "tickWorlds", at = @At(value = "INVOKE_STRING", args = "ldc=players", target = "Lnet/minecraft/util/profiler/Profiler;swap(Ljava/lang/String;)V"))
    private void logPlayerPhase(CallbackInfo ci) {
        TickReportCommand.setPhase("PlayerActions");
    }
}