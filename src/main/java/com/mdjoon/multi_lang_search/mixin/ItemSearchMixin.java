package com.mdjoon.multi_lang_search.mixin;

import com.mdjoon.multi_lang_search.MultiLanguageCache;
import com.mdjoon.multi_lang_search.config.ConfigManager;
import org.jspecify.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;
import net.minecraft.ChatFormatting;
import net.minecraft.client.resources.language.ClientLanguage;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.contents.TranslatableContents;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.ItemEnchantments;

@Mixin(ItemStack.class)
public abstract class ItemSearchMixin {

    @Inject(
            method = "getTooltipLines",
            at = @At("TAIL")
    )
    private void addEnSearchName(
            Item.TooltipContext context, @Nullable Player player, TooltipFlag type, CallbackInfoReturnable<List<Component>> cir
    ) {
        if(!ConfigManager.get().isOn) return;
        ClientLanguage ts = MultiLanguageCache.get();if(ts == null) return;

        ItemStack itemStack = ((ItemStack)(Object)this);
        Item item = itemStack.getItem();

        String key = item.getDescriptionId();

        if(!ts.has(key)) return;

        String tooltip_name = ts.getOrDefault(key);

        if (tooltip_name != null) {
            if(itemStack.is(Items.ENCHANTED_BOOK)) {
                if(type.isCreative()) {
                    ItemEnchantments component = EnchantmentHelper.getEnchantmentsForCrafting(itemStack);
                    List<Holder<Enchantment>> enchantList = component.keySet().stream().toList();
                    Holder<Enchantment> entry = enchantList.getFirst();

                    if(entry.value().description().getContents() instanceof TranslatableContents content) {
                        if(ts.has(content.getKey())) {
                            tooltip_name = ts.getOrDefault(content.getKey()) + " " + tooltip_name;
                        }
                    }
                }
            }
            if(itemStack.is(Items.POTION) || itemStack.is(Items.LINGERING_POTION) || itemStack.is(Items.SPLASH_POTION)) {
                PotionContents component = itemStack.get(DataComponents.POTION_CONTENTS);
                if(component != null && component.potion().isPresent()) {
                    Holder<Potion> entry = component.potion().get();
                    List<MobEffectInstance> effects = entry.value().getEffects();

                    if(!effects.isEmpty()) {
                        String potion_name_key = effects.getFirst().getDescriptionId();

                        if(ts.has(potion_name_key)) {
                            tooltip_name = ts.getOrDefault(potion_name_key) + " " + tooltip_name;
                        }
                    }

                }
            }

            cir.getReturnValue().add(
                    Component.literal(tooltip_name).withStyle(ChatFormatting.DARK_GRAY)
            );
        }
    }
}

