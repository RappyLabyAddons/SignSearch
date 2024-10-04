package com.rappytv.signsearch.v1_8_9.mixins;

import com.rappytv.signsearch.SignSearchAddon;
import com.rappytv.signsearch.utils.SignManager.SignData;
import net.labymod.api.client.blockentity.SignBlockEntity;
import net.minecraft.client.renderer.tileentity.TileEntitySignRenderer;
import net.minecraft.tileentity.TileEntitySign;
import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(TileEntitySignRenderer.class)
public abstract class SignMixin {

    @Inject(method = "renderTileEntityAt(Lnet/minecraft/tileentity/TileEntitySign;DDDFI)V", at = @At("HEAD"))
    private void injectSignManager(TileEntitySign signEntity, double lvt_2_1_, double lvt_4_1_, double lvt_6_1_, float lvt_8_1_, int lvt_9_1_, CallbackInfo ci) {
        SignSearchAddon.getSignManager().onRender((SignBlockEntity) signEntity, ((SignBlockEntity) signEntity).position());
        float red = 1.0f;
        float green = 1.0f;
        float blue = 1.0f;
        float alpha = 1.0f;
        SignData signData = SignSearchAddon.getSignManager().getSignData();
        if (signData != null) {
            SignData.SignColor signColor = signData.getSignColor();
            red = signColor.getRed();
            green = signColor.getGreen();
            blue = signColor.getBlue();
            alpha = signColor.getAlpha();
        }
        GL11.glColor4f(red, green, blue, alpha);
    }
}
