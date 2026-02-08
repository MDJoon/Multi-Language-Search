package com.mdjoon.enitemsearch.mixin;

import com.mdjoon.enitemsearch.EnglishLanguageCache;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.resource.language.LanguageDefinition;
import net.minecraft.client.resource.language.LanguageManager;
import net.minecraft.client.resource.language.TranslationStorage;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.ItemEnchantmentsComponent;
import net.minecraft.component.type.PotionContentsComponent;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.potion.Potion;
import net.minecraft.registry.Registries;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.resource.ResourceReloader;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableTextContent;
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

        ItemStack itemStack = ((ItemStack)(Object)this);
        Item item = itemStack.getItem();

        String key = item.getTranslationKey();

        if(!ts.hasTranslation(key)) return;

        String en_name = ts.get(key);

        if (en_name != null) {
            if(itemStack.isOf(Items.ENCHANTED_BOOK)) {
                if(type.isCreative()) {
                    ItemEnchantmentsComponent component = EnchantmentHelper.getEnchantments(itemStack);
                    List<RegistryEntry<Enchantment>> enchantList = component.getEnchantments().stream().toList();
                    RegistryEntry<Enchantment> entry = enchantList.getFirst();

                    if(entry.value().description().getContent() instanceof TranslatableTextContent content) {
                        if(ts.hasTranslation(content.getKey())) {
                            en_name = ts.get(content.getKey()) + " " + en_name;
                        }
                    }
                }
            }
            if(itemStack.isOf(Items.POTION) || itemStack.isOf(Items.LINGERING_POTION) || itemStack.isOf(Items.SPLASH_POTION)) {
                PotionContentsComponent component = itemStack.get(DataComponentTypes.POTION_CONTENTS);
                if(component != null && component.potion().isPresent()) {
                    RegistryEntry<Potion> entry = component.potion().get();
                    List<StatusEffectInstance> effects = entry.value().getEffects();

                    if(!effects.isEmpty()) {
                        String potion_name_key = effects.getFirst().getTranslationKey();

                        if(ts.hasTranslation(potion_name_key)) {
                            en_name = ts.get(potion_name_key) + " " + en_name;
                        }
                    }

                }
            }

            cir.getReturnValue().add(
                    Text.literal(en_name).formatted(Formatting.DARK_GRAY)
            );
        }
    }
}

