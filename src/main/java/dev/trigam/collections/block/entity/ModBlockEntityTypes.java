package dev.trigam.collections.block.entity;

import dev.trigam.collections.Collections;
import net.minecraft.block.Block;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModBlockEntityTypes {

    // public static final BlockEntityType<RelayBlockEntity> RELAY = register("relay", RelayBlockEntity::new, ModBlocks.RELAY);

    public static <T extends BlockEntity> BlockEntityType<T> register(String name, BlockEntityType.BlockEntityFactory<T> factory, Block... blocks) {
        BlockEntityType<T> type = BlockEntityType.Builder.create(factory, blocks).build();
        return Registry.register(Registries.BLOCK_ENTITY_TYPE, new Identifier(Collections.ModId, name), type);
    }

    public static void register() { Collections.LOGGER.debug("Registering block entities for {}...", Collections.ModId); }
}
