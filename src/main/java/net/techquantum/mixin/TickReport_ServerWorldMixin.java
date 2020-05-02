package net.techquantum.mixin;

import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.dimension.DimensionType;
import net.techquantum.commands.TickReportCommand;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerWorld.class)
public class TickReport_ServerWorldMixin {
    @Inject(method = "tick", at = @At(value = "INVOKE_STRING", args = "ldc=tickPending", target = "Lnet/minecraft/util/profiler/Profiler;swap(Ljava/lang/String;)V"))
    private void logTileTickPhase(CallbackInfo ci) {
        if (((ServerWorld)(Object)this).dimension.getType() == DimensionType.OVERWORLD)
            TickReportCommand.setPhase("TileTicks");
    }

    @Inject(method = "tick", at = @At(value = "INVOKE_STRING", args = "ldc=blockEvents", target = "Lnet/minecraft/util/profiler/Profiler;swap(Ljava/lang/String;)V"))
    private void logBlockEventPhase(CallbackInfo ci) {
        if (((ServerWorld)(Object)this).dimension.getType() == DimensionType.OVERWORLD)
            TickReportCommand.setPhase("BlockEvents");
    }

    @Inject(method = "tick", at = @At(value = "INVOKE_STRING", args = "ldc=entities", target = "Lnet/minecraft/util/profiler/Profiler;swap(Ljava/lang/String;)V"))
    private void logEntityPhase(CallbackInfo ci) {
        if (((ServerWorld)(Object)this).dimension.getType() == DimensionType.OVERWORLD)
            TickReportCommand.setPhase("Entities");
    }

    @Inject(method = "tick", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/world/ServerWorld;tickBlockEntities()V"))
    private void logBlockEntityPhase(CallbackInfo ci) {
        if (((ServerWorld)(Object)this).dimension.getType() == DimensionType.OVERWORLD)
            TickReportCommand.setPhase("BlockEntities");
    }
}
