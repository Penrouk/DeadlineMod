package com.penrouk.deadlinemod.event;

import com.penrouk.deadlinemod.DeadlineMod;
import com.penrouk.deadlinemod.util.DifficultyTier;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingExperienceDropEvent;

@EventBusSubscriber(modid = DeadlineMod.MOD_ID)
public class XpIncreaseEvent {
    @SubscribeEvent
    public static void onXpDrop(LivingExperienceDropEvent event) {
        if (event.getEntity() instanceof Player) {
            return;
        }

        DifficultyTier difficultyTier = new DifficultyTier(event.getEntity().getBlockX(), event.getEntity().getBlockZ());
        int difficulty = difficultyTier.toInt();
        if (difficulty == 5) {
            difficulty = 5 + (int)difficultyTier.calculateTier5Multiplier(2000);
        } else if (difficulty == 0) {
            return;
        }

        double xpIncrease = 0.1 * Math.pow(2, difficulty);
        int originalXp = event.getDroppedExperience();
        event.setDroppedExperience((int)(originalXp * (1+ xpIncrease)));
    }
}
