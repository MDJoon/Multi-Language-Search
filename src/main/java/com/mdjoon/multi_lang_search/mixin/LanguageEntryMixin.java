package com.mdjoon.multi_lang_search.mixin;

import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(targets = "net.minecraft.client.gui.screen.option.LanguageOptionsScreen$LanguageSelectionListWidget$LanguageEntry")
public abstract class LanguageEntryMixin {

    @Shadow @Final
    String languageCode;

    @ModifyArg(
            method = "render",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/gui/DrawContext;drawCenteredTextWithShadow(Lnet/minecraft/client/font/TextRenderer;Lnet/minecraft/text/Text;III)V"
            ),
            index = 1
    )
    private Text adjustLanguageText(Text original) {
        return Text.literal(original.getString() + " (" + this.languageCode + ")");
    }
}