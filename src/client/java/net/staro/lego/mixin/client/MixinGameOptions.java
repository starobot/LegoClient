package net.staro.lego.mixin.client;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.option.GameOptions;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Environment(EnvType.CLIENT)
@Mixin(GameOptions.class)
public abstract class MixinGameOptions {
    @ModifyConstant(method = "<init>", constant = @Constant(intValue = 110))
    private int maxFovValueHook(int constant) {
        return 180;
    }

}

