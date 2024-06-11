package dev.trigam.collections.mixin.enchantmentOverhaul;

import net.minecraft.component.ComponentMap;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.registry.entry.RegistryEntryList;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Enchantment.class)
public class AddEnchantmentDefinitonFields {

    @Inject(method = "<init>", at = @At("RETURN"), cancellable = true)
    private void injectCustomDefinition(Text description, Enchantment.Definition definition, RegistryEntryList<Enchantment> exclusiveSet, ComponentMap effects, CallbackInfo ci) {
        
    }

}
