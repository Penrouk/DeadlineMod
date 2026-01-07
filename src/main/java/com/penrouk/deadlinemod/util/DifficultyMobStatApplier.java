package com.penrouk.deadlinemod.util;

import com.penrouk.deadlinemod.DeadlineMod;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.attributes.*;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.player.Player;

import java.util.UUID;

public class DifficultyMobStatApplier {
    private static final UUID BONUS_HEALTH_UUID = UUID.fromString("dcccd4be-c2af-4aa4-9667-6cb6cef467b7");

    public static void tweakMobStats(Mob mob, DifficultyTier difficultyTier) {
        // Don't touch TIER_0
        if (difficultyTier.getDifficulty() == Difficulty.TIER_0) {
            return;
        }

        AttributeModifier healthModifier = getHealthModifier(difficultyTier);
        applyAttributeModifier(mob, healthModifier, Attributes.MAX_HEALTH);
        mob.setHealth(mob.getMaxHealth());
    }

    private static void applyAttributeModifier(Mob mob, AttributeModifier modifier, Holder<Attribute> attribute) {
        AttributeInstance attributeInstance = mob.getAttribute(attribute);
        if (attributeInstance != null) {
            attributeInstance.addPermanentModifier(modifier);
        }
    }

    private static AttributeModifier getHealthModifier(DifficultyTier difficultyTier) {
        ResourceLocation id = ResourceLocation.fromNamespaceAndPath(DeadlineMod.MOD_ID, "deadline_mod_health_scaling");

        if (difficultyTier.getDifficulty() == Difficulty.TIER_1) {
            return new AttributeModifier(
                    id,
                    0.3,
                    AttributeModifier.Operation.ADD_MULTIPLIED_BASE
            );
        } else if (difficultyTier.getDifficulty() == Difficulty.TIER_2) {
            return new AttributeModifier(
                    id,
                    0.6,
                    AttributeModifier.Operation.ADD_MULTIPLIED_BASE
            );
        } else if (difficultyTier.getDifficulty() == Difficulty.TIER_3) {
            return new AttributeModifier(
                    id,
                    0.9,
                    AttributeModifier.Operation.ADD_MULTIPLIED_BASE
            );
        } else if (difficultyTier.getDifficulty() == Difficulty.TIER_4) {
            return new AttributeModifier(
                    id,
                    1.2,
                    AttributeModifier.Operation.ADD_MULTIPLIED_BASE
            );
        } else {
            double modifier = difficultyTier.calculateTier5Multiplier(100) * 0.1;
            return new AttributeModifier(
                    id,
                    1.5 + modifier,
                    AttributeModifier.Operation.ADD_MULTIPLIED_BASE
            );
        }
    }
}
