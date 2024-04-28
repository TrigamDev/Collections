package dev.trigam.collections.worldgen;

import dev.trigam.collections.Collections;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.feature.PlacedFeature;

public class ModOres {

    public static final RegistryKey<PlacedFeature> SULPHUR_PLACED_KEY = register("ore_sulphur");
    public static final RegistryKey<PlacedFeature> NETHER_SULPHUR_PLACED_KEY = register("ore_sulphur_nether");

    private static RegistryKey<PlacedFeature> register(String id) {
        return RegistryKey.of(RegistryKeys.PLACED_FEATURE, new Identifier(Collections.ModId, id));
    }

    public static void register() {
        Collections.LOGGER.info("Registering ores for {}...", Collections.ModId);

        BiomeModifications.addFeature(BiomeSelectors.foundInOverworld(), GenerationStep.Feature.UNDERGROUND_ORES, SULPHUR_PLACED_KEY);
        BiomeModifications.addFeature(BiomeSelectors.foundInTheNether(), GenerationStep.Feature.UNDERGROUND_ORES, NETHER_SULPHUR_PLACED_KEY);
    }

}
