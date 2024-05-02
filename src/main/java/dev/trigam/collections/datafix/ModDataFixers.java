package dev.trigam.collections.datafix;

import com.mojang.datafixers.DataFixerBuilder;
import com.mojang.datafixers.schemas.Schema;
import dev.trigam.collections.Collections;
import net.minecraft.datafixer.TypeReferences;
import net.minecraft.datafixer.fix.ChoiceTypesFix;

import java.util.function.BiFunction;

public class ModDataFixers {

    private static final BiFunction<Integer, Schema, Schema> EMPTY = Schema::new;
    public static void register() {
        Collections.LOGGER.info("Registering data fixers for {}...", Collections.ModId);

        DataFixerBuilder builder = new DataFixerBuilder(Collections.DataVersion);
        builder.addSchema(0, EMPTY);

        Schema schemaV1 = builder.addSchema(1, Schema::new);
        builder.addFixer(new ChoiceTypesFix(schemaV1, "Added dynamite", TypeReferences.ENTITY));
    }

}
