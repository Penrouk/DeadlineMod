package com.penrouk.deadlinemod.event;

import com.penrouk.deadlinemod.DeadlineMod;
import com.penrouk.deadlinemod.util.Difficulty;
import com.penrouk.deadlinemod.util.DifficultyTier;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.event.level.BlockEvent;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@EventBusSubscriber(modid = DeadlineMod.MOD_ID)
public class OreFortuneEvent {
    @SubscribeEvent
    public static void checkApplyFortune(BlockEvent.BreakEvent blockBreakEvent) {
        Player player = blockBreakEvent.getPlayer();
        if (player == null || player.isCreative()) {
            return;
        }


        Level level = (Level) blockBreakEvent.getLevel();
        BlockPos pos = blockBreakEvent.getPos();
        BlockState state = level.getBlockState(pos);

        if (!state.getBlock().asItem().builtInRegistryHolder().is(Tags.Items.ORES)) return;

        ItemStack originalTool = player.getMainHandItem();
        if (originalTool.isEmpty()) {
            return;
        }

        Holder<Enchantment> fortune = level.registryAccess()
                .registryOrThrow(Registries.ENCHANTMENT)
                .getHolderOrThrow(Enchantments.FORTUNE);

        ItemStack modifiedTool = originalTool.copy();
        int fortuneLevel = EnchantmentHelper.getTagEnchantmentLevel(
                fortune, originalTool
        );
        DifficultyTier difficultyTier = new DifficultyTier(blockBreakEvent.getPos().getX(), blockBreakEvent.getPos().getZ());

        modifiedTool.enchant(fortune, fortuneLevel + getNumberOfRepeatDrops(difficultyTier));

        List<ItemStack> drops = Block.getDrops(
                state,
                (ServerLevel) level,
                pos,
                level.getBlockEntity(pos),
                player,
                modifiedTool
        );

        int fortuneCount = getNumberOfRepeatDrops(difficultyTier);

        for (ItemStack drop : drops) {
            Block.popResource(level, pos, drop);
        }
    }

    public static int getNumberOfRepeatDrops(DifficultyTier difficultyTier) {
        if (difficultyTier.getDifficulty() == Difficulty.TIER_0) {
            return 0;
        } else if (difficultyTier.getDifficulty() == Difficulty.TIER_1) {
            return 1;
        } else if (difficultyTier.getDifficulty() == Difficulty.TIER_2) {
            return 2;
        } else if (difficultyTier.getDifficulty() == Difficulty.TIER_3) {
            return 3;
        } else if (difficultyTier.getDifficulty() == Difficulty.TIER_4) {
            return 4;
        } else {
            return 5 + (int)difficultyTier.calculateTier5Multiplier(1000);
        }
    }

}
