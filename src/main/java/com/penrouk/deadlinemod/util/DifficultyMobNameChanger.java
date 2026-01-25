package com.penrouk.deadlinemod.util;

import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Mob;
import net.minecraft.network.chat.Style;

public class DifficultyMobNameChanger {
    public static void stylizeMobName(Mob mob, DifficultyTier difficultyTier) {
        // Add the tier to the name as well as color it
        String existingName = mob.getName().getString();

        int colorRgb = 0xFF0000;
        if (difficultyTier.getDifficulty() == Difficulty.TIER_0) {
            colorRgb = 0xFFFFFF;
        }  else if (difficultyTier.getDifficulty() == Difficulty.TIER_1) {
            colorRgb = 0xc0ffbd;
        } else if (difficultyTier.getDifficulty() == Difficulty.TIER_2) {
            colorRgb = 0x059700;
        } else if (difficultyTier.getDifficulty() == Difficulty.TIER_3) {
            colorRgb = 0xfcef00;
        } else if (difficultyTier.getDifficulty() == Difficulty.TIER_4) {
            colorRgb = 0xfcac00;
        }

        mob.setCustomName(
                Component.literal(difficultyTier + " " + existingName).withStyle(Style.EMPTY.withColor(colorRgb))
        );
        mob.setCustomNameVisible(true);
    }

    private static String getMobTierColor(DifficultyTier difficultyTier) {
        return "#ffffff";
    }
}
