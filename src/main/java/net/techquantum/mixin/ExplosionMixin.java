package net.techquantum.mixin;

//Author: gnembon

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.explosion.Explosion;
import net.techquantum.helpers.OptimizedExplosion;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(value = Explosion.class)
public class ExplosionMixin {
    @Shadow @Final private List<BlockPos> affectedBlocks;
    @Inject(method = "collectBlocksAndDamageEntities", at = @At("HEAD"),
            cancellable = true)
    private void onExplosionA(CallbackInfo ci)
    {
        OptimizedExplosion.doExplosionA((Explosion) (Object) this);
        ci.cancel();
    }

    @Inject(method = "affectWorld", at = @At("HEAD"),
            cancellable = true)
    private void onExplosionB(boolean spawnParticles, CallbackInfo ci)
    {
        OptimizedExplosion.doExplosionB((Explosion) (Object) this, spawnParticles);
        ci.cancel();
    }
}
