package com.rappytv.signsearch.v1_16_5.mixins;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.rappytv.signsearch.SignSearchAddon;
import com.rappytv.signsearch.utils.SignManager.SignData;
import net.labymod.api.client.world.block.BlockPosition;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.SignRenderer;
import net.minecraft.world.level.block.entity.SignBlockEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(SignRenderer.class)
public abstract class SignMixin {

    @Inject(method = "render(Lnet/minecraft/world/level/block/entity/SignBlockEntity;FLcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;II)V", at = @At("HEAD"))
    private void injectSignManager(SignBlockEntity signEntity, float partialTicket, PoseStack matrixStack, MultiBufferSource buffer, int combinedLight, int combiedOverlay, CallbackInfo ci) {
        SignSearchAddon.getSignManager().onRender((net.labymod.api.client.blockentity.SignBlockEntity) signEntity, (BlockPosition) signEntity.getBlockPos());
    }

    @Redirect(method={"render(Lnet/minecraft/world/level/block/entity/SignBlockEntity;FLcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;II)V"}, at=@At(value="INVOKE", target="Lnet/minecraft/client/model/geom/ModelPart;render(Lcom/mojang/blaze3d/vertex/PoseStack;Lcom/mojang/blaze3d/vertex/VertexConsumer;II)V"))
    private void redirectSignColor(ModelPart modelRenderer, PoseStack matrixStackIn, VertexConsumer bufferIn, int packedLightIn, int packedOverlayIn) {
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
        modelRenderer.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
    }
}
