package com.mdjoon.enitemsearch.mixin;

import com.mdjoon.enitemsearch.EnglishLanguageCache;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.resource.language.LanguageDefinition;
import net.minecraft.client.resource.language.LanguageManager;
import net.minecraft.client.resource.language.TranslationStorage;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.registry.Registries;
import net.minecraft.resource.ResourceReloader;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.minecraft.util.Language;
import org.jspecify.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

@Mixin(ItemStack.class)
public abstract class ItemSearchMixin {

    @Inject(
            method = "getTooltip",
            at = @At("TAIL")
    )
    private void addEnSearchName(
            Item.TooltipContext context, @Nullable PlayerEntity player, TooltipType type, CallbackInfoReturnable<List<Text>> cir
    ) {
        TranslationStorage ts = EnglishLanguageCache.get();if(ts == null) return;

        Item item = ((ItemStack)(Object)this).getItem();
        String key = item.getTranslationKey();

        if(!ts.hasTranslation(key)) return;

        String en_name = ts.get(key);


        if (en_name != null) {
            cir.getReturnValue().add(
                    Text.literal(en_name).formatted(Formatting.DARK_GRAY)
            );
        }
    }
}

