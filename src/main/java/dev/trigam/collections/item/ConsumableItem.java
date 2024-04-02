package dev.trigam.collections.item;

import dev.trigam.collections.Collections;
import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsage;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.UseAction;
import net.minecraft.world.World;

public class ConsumableItem extends Item {
    public Item returnItem;
    public int usageTime = 40;
    public SoundEvent consumeSound = SoundEvents.ENTITY_GENERIC_EAT;

    public ConsumableItem(Item.Settings settings, Item returnItem, int usageTime, SoundEvent consumeSound) {
        super(settings);
        this.returnItem = returnItem;
        this.usageTime = usageTime;
        this.consumeSound = consumeSound;
    }

    public ConsumableItem(Item.Settings settings, Item returnItem, int usageTime) {
        super(settings);
        this.returnItem = returnItem;
        this.usageTime = usageTime;
    }

    public ConsumableItem(Item.Settings settings, Item returnItem) {
        super(settings);
        this.returnItem = returnItem;
    }

    public ConsumableItem(Item.Settings settings, Item returnItem, SoundEvent consumeSound) {
        super(settings);
        this.returnItem = returnItem;
        this.consumeSound = consumeSound;
    }

    @Override
    public ItemStack finishUsing(ItemStack stack, World world, LivingEntity user) {
        super.finishUsing(stack, world, user);
        Collections.LOGGER.info("mmm cofee");
        // Incremement usage stat
        if (user instanceof ServerPlayerEntity serverPlayerEntity) {
            Criteria.CONSUME_ITEM.trigger(serverPlayerEntity, stack);
            serverPlayerEntity.incrementStat(Stats.USED.getOrCreateStat(this));
        }

        if (stack.isEmpty()) {
            return new ItemStack(this.returnItem);
        } else {
            // Insert return item on consume
            if (user instanceof PlayerEntity playerEntity && !playerEntity.isInCreativeMode()) {
                ItemStack itemStack = new ItemStack(returnItem);
                if (!playerEntity.getInventory().insertStack(itemStack)) {
                    playerEntity.dropItem(itemStack, false);
                }
            }
            return stack;
        }
    }

    @Override
    public int getMaxUseTime(ItemStack stack) {
        return this.usageTime;
    }

    @Override
    public UseAction getUseAction(ItemStack stack) {
        return UseAction.EAT;
    }

    @Override
    public SoundEvent getDrinkSound() {
        return this.consumeSound;
    }

    @Override
    public SoundEvent getEatSound() {
        return this.consumeSound;
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        return ItemUsage.consumeHeldItem(world, user, hand);
    }
}
