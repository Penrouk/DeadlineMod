package com.penrouk.deadlinemod.util;

import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Mob;
import net.minecraft.network.chat.Style;

public class DifficultyMobNameChanger {
    public static void stylizeMobName(Mob mob, DifficultyTier difficultyTier) {
        // Add the tier to the name as well as color it
        String existingName = mob.getName().getString();
        mob.setCustomName(
                Component.literal(difficultyTier + " " + existingName).withStyle(Style.EMPTY.withColor(0xFF0000))
        );
        mob.setCustomNameVisible(true);
    }

    private static String getMobTierColor(DifficultyTier difficultyTier) {
        return "#ffffff";
    }
}
