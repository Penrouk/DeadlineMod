package com.penrouk.deadlinemod.loot;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.penrouk.deadlinemod.util.DifficultyTier;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.util.Tuple;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.neoforged.neoforge.common.loot.IGlobalLootModifier;
import net.neoforged.neoforge.common.loot.LootModifier;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public class DifficultyTierLootModifier extends LootModifier {
    public record EnchantmentTuple(Holder<Enchantment> enchantment, int maxLevel) {}

    public static final MapCodec<DifficultyTierLootModifier> CODEC =
            RecordCodecBuilder.mapCodec(inst ->LootModifier.codecStart(inst)
                    .apply(inst, DifficultyTierLootModifier::new));

    protected DifficultyTierLootModifier(LootItemCondition[] conditions) {
        super(conditions);
    }

    @Override
    protected @NotNull ObjectArrayList<ItemStack> doApply(
            @NotNull ObjectArrayList<ItemStack> generatedLoot,
            LootContext context) {
        if (!(context.getParamOrNull(LootContextParams.THIS_ENTITY) instanceof Player player)) {
            return generatedLoot;
        }

        ResourceLocation lootTableId = context.getQueriedLootTableId();

        boolean valid = true;
        if (context.hasParam(LootContextParams.BLOCK_STATE)) {
            valid = false;
        }

        if (context.getQueriedLootTableId().getPath().equals("grant_book_on_first_join")) {
            valid = false;
        }

        if (!valid) {
            return generatedLoot;
        }

        DifficultyTier difficultyTier = new DifficultyTier(player.getBlockX(), player.getBlockZ());

        EnchantmentTuple[] enchantments = getMaxLevelEnchantments(player.level());

        RandomSource randomSource = context.getRandom();
        int numberEnchantments = enchantments.length;
        EnchantmentTuple enchantment = enchantments[randomSource.nextIntBetweenInclusive(0, numberEnchantments - 1)];

        int offset = difficultyTier.toInt();
        if (offset == 5) {
            offset = 5 + (int)difficultyTier.calculateTier5Multiplier(2000);
        }

        int enchantmentLevel = randomSource.nextIntBetweenInclusive(1 + offset, enchantment.maxLevel() + offset);

        ItemStack book = new ItemStack(Items.ENCHANTED_BOOK);
        book.enchant(enchantment.enchantment, enchantmentLevel);

        generatedLoot.add(book);
        return generatedLoot;
    }

    @Override
    public MapCodec<? extends IGlobalLootModifier> codec() {
        return CODEC;
    }

    public static EnchantmentTuple[] getMaxLevelEnchantments(Level level) {
        ArrayList<EnchantmentTuple> enchantmentsList = new ArrayList<>();
        // Tools
        enchantmentsList.add(new EnchantmentTuple(
                level.registryAccess()
                        .registryOrThrow(Registries.ENCHANTMENT)
                        .getHolderOrThrow(Enchantments.FORTUNE), 3));

        enchantmentsList.add(new EnchantmentTuple(
                level.registryAccess()
                        .registryOrThrow(Registries.ENCHANTMENT)
                        .getHolderOrThrow(Enchantments.SILK_TOUCH), 1));

        enchantmentsList.add(new EnchantmentTuple(
                level.registryAccess()
                        .registryOrThrow(Registries.ENCHANTMENT)
                        .getHolderOrThrow(Enchantments.EFFICIENCY), 5));

        enchantmentsList.add(new EnchantmentTuple(
                level.registryAccess()
                        .registryOrThrow(Registries.ENCHANTMENT)
                        .getHolderOrThrow(Enchantments.UNBREAKING), 3));

        // Weapons
        enchantmentsList.add(new EnchantmentTuple(
                level.registryAccess()
                        .registryOrThrow(Registries.ENCHANTMENT)
                        .getHolderOrThrow(Enchantments.SHARPNESS), 5));

        enchantmentsList.add(new EnchantmentTuple(
                level.registryAccess()
                        .registryOrThrow(Registries.ENCHANTMENT)
                        .getHolderOrThrow(Enchantments.SMITE), 5));

        enchantmentsList.add(new EnchantmentTuple(
                level.registryAccess()
                        .registryOrThrow(Registries.ENCHANTMENT)
                        .getHolderOrThrow(Enchantments.BANE_OF_ARTHROPODS), 5));

        enchantmentsList.add(new EnchantmentTuple(
                level.registryAccess()
                        .registryOrThrow(Registries.ENCHANTMENT)
                        .getHolderOrThrow(Enchantments.FIRE_ASPECT), 2));

        enchantmentsList.add(new EnchantmentTuple(
                level.registryAccess()
                        .registryOrThrow(Registries.ENCHANTMENT)
                        .getHolderOrThrow(Enchantments.LOOTING), 3));

        // Bow
        enchantmentsList.add(new EnchantmentTuple(
                level.registryAccess()
                        .registryOrThrow(Registries.ENCHANTMENT)
                        .getHolderOrThrow(Enchantments.POWER), 5));

        enchantmentsList.add(new EnchantmentTuple(
                level.registryAccess()
                        .registryOrThrow(Registries.ENCHANTMENT)
                        .getHolderOrThrow(Enchantments.PUNCH), 2));

        // Armor
        enchantmentsList.add(new EnchantmentTuple(
                level.registryAccess()
                        .registryOrThrow(Registries.ENCHANTMENT)
                        .getHolderOrThrow(Enchantments.PROTECTION), 4));

        enchantmentsList.add(new EnchantmentTuple(
                level.registryAccess()
                        .registryOrThrow(Registries.ENCHANTMENT)
                        .getHolderOrThrow(Enchantments.FIRE_PROTECTION), 4));

        enchantmentsList.add(new EnchantmentTuple(
                level.registryAccess()
                        .registryOrThrow(Registries.ENCHANTMENT)
                        .getHolderOrThrow(Enchantments.BLAST_PROTECTION), 4));

        enchantmentsList.add(new EnchantmentTuple(
                level.registryAccess()
                        .registryOrThrow(Registries.ENCHANTMENT)
                        .getHolderOrThrow(Enchantments.PROJECTILE_PROTECTION), 4));

        enchantmentsList.add(new EnchantmentTuple(
                level.registryAccess()
                        .registryOrThrow(Registries.ENCHANTMENT)
                        .getHolderOrThrow(Enchantments.FEATHER_FALLING), 4));

        enchantmentsList.add(new EnchantmentTuple(
                level.registryAccess()
                        .registryOrThrow(Registries.ENCHANTMENT)
                        .getHolderOrThrow(Enchantments.THORNS), 3));

        // Fishing
        enchantmentsList.add(new EnchantmentTuple(
                level.registryAccess()
                        .registryOrThrow(Registries.ENCHANTMENT)
                        .getHolderOrThrow(Enchantments.LUCK_OF_THE_SEA), 3));

        enchantmentsList.add(new EnchantmentTuple(
                level.registryAccess()
                        .registryOrThrow(Registries.ENCHANTMENT)
                        .getHolderOrThrow(Enchantments.LURE), 3));

        EnchantmentTuple[] list = new EnchantmentTuple[enchantmentsList.size()];
        return enchantmentsList.toArray(list);
    }

}