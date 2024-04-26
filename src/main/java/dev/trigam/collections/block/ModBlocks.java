package dev.trigam.collections.block;

import dev.trigam.collections.Collections;
import net.minecraft.block.*;
import net.minecraft.block.enums.Instrument;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.intprovider.UniformIntProvider;

public class ModBlocks {

    public static final Block SULPHUR_BLOCK = register("sulphur_block",
        new Block(AbstractBlock.Settings.create().mapColor(MapColor.TERRACOTTA_YELLOW).requiresTool().strength(3.0F, 3.0F))
    );
    public static final Block SULPHUR_ORE = register("sulphur_ore",
        new ExperienceDroppingBlock(
            UniformIntProvider.create(1, 4),
            AbstractBlock.Settings.create().mapColor(MapColor.STONE_GRAY).instrument(Instrument.BASEDRUM).requiresTool().strength(3.0F, 3.0F)
        )
    );
    public static final Block DEEPSLATE_SULPHUR_ORE = register("deepslate_sulphur_ore",
        new ExperienceDroppingBlock(
            UniformIntProvider.create(1, 4),
            AbstractBlock.Settings.copy(SULPHUR_ORE).mapColor(MapColor.DEEPSLATE_GRAY).strength(4.5F, 3.0F).sounds(BlockSoundGroup.DEEPSLATE)
        )
    );
    public static final Block NETHER_SULPHUR_ORE = register("nether_sulphur_ore",
        new ExperienceDroppingBlock(
            UniformIntProvider.create(1, 4),
            AbstractBlock.Settings.copy(SULPHUR_ORE).mapColor(MapColor.DARK_RED).strength(3.0F, 3.0F).sounds(BlockSoundGroup.NETHER_ORE)
        )
    );
    public static final Block OAT_GRASS = register("oat_grass",
        new ShortPlantBlock(AbstractBlock.Settings.copy(Blocks.SHORT_GRASS).nonOpaque())
    );
    public static final Block COLORED_LAMP = register("colored_lamp",
        new ColoredLampBlock(AbstractBlock.Settings.copy(Blocks.REDSTONE_LAMP).mapColor(MapColor.TERRACOTTA_BLACK))
    );

    private static Block register(String name, Block block) {
        registerBlockItem(name, block);
        return Registry.register(Registries.BLOCK, new Identifier(Collections.ModId, name), block);
    }

    private static Item registerBlockItem(String name, Block block) {
        return Registry.register(Registries.ITEM, new Identifier(Collections.ModId, name), new BlockItem(block, new Item.Settings()));
    }

    public static void register() {
        Collections.LOGGER.info("Registering blocks for {}", Collections.ModId);
    }

}
