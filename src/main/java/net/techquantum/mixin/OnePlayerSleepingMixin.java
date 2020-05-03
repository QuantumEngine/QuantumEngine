package net.techquantum.mixin;

import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;
import java.util.stream.Stream;
import java.util.function.Predicate;

@Mixin(ServerWorld.class)
public class OnePlayerSleepingMixin {
    @Shadow @Final private List<ServerPlayerEntity> players;
    @Shadow private boolean allPlayersSleeping;

    @Inject(method = "updateSleepingPlayers", cancellable = true, at = @At("HEAD"))
    private void updateOnePlayerSleeping(CallbackInfo ci)
    {
        allPlayersSleeping = false;
        for (ServerPlayerEntity p : players)
            if (!p.isSpectator() && p.isSleeping())
            {
                allPlayersSleeping = true;
                ci.cancel();
                return;
            }
        ci.cancel();
    }

    @Redirect(method = "tick", at = @At(
            value = "INVOKE",
            target = "Ljava/util/stream/Stream;noneMatch(Ljava/util/function/Predicate;)Z"
    ))
    private boolean noneMatchSleep(Stream<ServerPlayerEntity> stream, Predicate<ServerPlayerEntity> predicate)
    {
        return stream.anyMatch((p) -> !p.isSpectator() && p.isSleepingLongEnough());
    }
}
