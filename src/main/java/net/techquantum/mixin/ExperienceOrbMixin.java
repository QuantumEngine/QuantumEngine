package net.techquantum.mixin;

import net.minecraft.entity.ExperienceOrbEntity;
import net.techquantum.fakes.ExperienceOrbInterface;
import net.techquantum.helper.XPCombine;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ExperienceOrbEntity.class)
public class ExperienceOrbMixin implements ExperienceOrbInterface {
    @Shadow private int amount;

    public void setAmount(int amount) { this.amount = amount; }

    public int combineDelay = 50;

    public int getCombineDelay() { return combineDelay; }
    public void setCombineDelay(int what) { combineDelay = what; }

    @Inject(method = "tick",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/entity/ExperienceOrbEntity;move(Lnet/minecraft/entity/MovementType;Lnet/minecraft/util/math/Vec3d;)V",
                    shift = At.Shift.AFTER
            ))
    void checkCombineAtTick(CallbackInfo ci) {
        if (getCombineDelay() > 0) {
            setCombineDelay(getCombineDelay()-1);
        }

        if (getCombineDelay() == 0) {
            XPCombine.searchForOtherXPNearby((ExperienceOrbEntity) (Object) this);
        }
    }
}
