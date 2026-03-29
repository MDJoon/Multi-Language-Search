package com.mdjoon.multi_lang_search.mixin;

import net.minecraft.network.chat.Component;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(targets = "net.minecraft.client.gui.screens.options.LanguageSelectScreen$LanguageSelectionList$Entry")
public abstract class LanguageEntryMixin {

    @Shadow @Final
    String code;

    @ModifyArg(
            method = "extractContent",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/gui/GuiGraphicsExtractor;centeredText(Lnet/minecraft/client/gui/Font;Lnet/minecraft/network/chat/Component;III)V"
            ),
            index = 1
    )
    private Component adjustLanguageText(Component original) {
        return Component.literal(original.getString() + " (" + this.code + ")");
    }
}