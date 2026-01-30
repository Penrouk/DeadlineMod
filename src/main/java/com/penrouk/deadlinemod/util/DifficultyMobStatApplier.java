package com.penrouk.deadlinemod.util;

import com.penrouk.deadlinemod.DeadlineMod;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.*;

public class DifficultyMobStatApplier {
    public static void tweakMobStats(Mob mob, DifficultyTier difficultyTier) {
        // Don't touch TIER_0
        if (difficultyTier.getDifficulty() == Difficulty.TIER_0) {
            return;
        }

        AttributeModifier healthModifier = getHealthModifier(difficultyTier);
        applyAttributeModifier(mob, healthModifier, Attributes.MAX_HEALTH);
        mob.setHealth(mob.getMaxHealth());

        AttributeModifier attackSpeedModifier = getAttackSpeedModifier(difficultyTier);
        applyAttributeModifier(mob, attackSpeedModifier, Attributes.ATTACK_SPEED);

        AttributeModifier moveSpeedModifier = getMoveSpeedModifier(difficultyTier);
        applyAttributeModifier(mob, moveSpeedModifier, Attributes.MOVEMENT_SPEED);

        AttributeModifier attackDamageModifier = getDamageModifier(difficultyTier);
        applyAttributeModifier(mob, attackDamageModifier, Attributes.ATTACK_DAMAGE);

        AttributeModifier armorModifier = getArmorModifier(difficultyTier);
        applyAttributeModifier(mob, armorModifier, Attributes.ARMOR);
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
            double modifier = difficultyTier.calculateTier5Multiplier(100) * 0.01;
            return new AttributeModifier(
                    id,
                    1.5 + modifier,
                    AttributeModifier.Operation.ADD_MULTIPLIED_BASE
            );
        }
    }

    private static AttributeModifier getAttackSpeedModifier(DifficultyTier difficultyTier) {
        ResourceLocation id = ResourceLocation.fromNamespaceAndPath(DeadlineMod.MOD_ID, "deadline_mod_attack_speed_scaling");

        if (difficultyTier.getDifficulty() == Difficulty.TIER_1) {
            return new AttributeModifier(
                    id,
                    -0.1,
                    AttributeModifier.Operation.ADD_MULTIPLIED_BASE
            );
        } else if (difficultyTier.getDifficulty() == Difficulty.TIER_2) {
            return new AttributeModifier(
                    id,
                    -0.2,
                    AttributeModifier.Operation.ADD_MULTIPLIED_BASE
            );
        } else if (difficultyTier.getDifficulty() == Difficulty.TIER_3) {
            return new AttributeModifier(
                    id,
                    -0.3,
                    AttributeModifier.Operation.ADD_MULTIPLIED_BASE
            );
        } else if (difficultyTier.getDifficulty() == Difficulty.TIER_4) {
            return new AttributeModifier(
                    id,
                    -0.4,
                    AttributeModifier.Operation.ADD_MULTIPLIED_BASE
            );
        } else {
            double modifier = difficultyTier.calculateTier5Multiplier(100) * 0.01;
            return new AttributeModifier(
                    id,
                    -0.5 - modifier,
                    AttributeModifier.Operation.ADD_MULTIPLIED_BASE
            );
        }
    }

    private static AttributeModifier getMoveSpeedModifier(DifficultyTier difficultyTier) {
        ResourceLocation id = ResourceLocation.fromNamespaceAndPath(DeadlineMod.MOD_ID, "deadline_mod_move_speed_scaling");

        if (difficultyTier.getDifficulty() == Difficulty.TIER_1) {
            return new AttributeModifier(
                    id,
                    0,
                    AttributeModifier.Operation.ADD_MULTIPLIED_BASE
            );
        } else if (difficultyTier.getDifficulty() == Difficulty.TIER_2) {
            return new AttributeModifier(
                    id,
                    0.15,
                    AttributeModifier.Operation.ADD_MULTIPLIED_BASE
            );
        } else if (difficultyTier.getDifficulty() == Difficulty.TIER_3) {
            return new AttributeModifier(
                    id,
                    0.3,
                    AttributeModifier.Operation.ADD_MULTIPLIED_BASE
            );
        } else if (difficultyTier.getDifficulty() == Difficulty.TIER_4) {
            return new AttributeModifier(
                    id,
                    0.45,
                    AttributeModifier.Operation.ADD_MULTIPLIED_BASE
            );
        } else {
            double modifier = difficultyTier.calculateTier5Multiplier(100) * 0.01;
            modifier = Math.min(modifier, 3);
            return new AttributeModifier(
                    id,
                    0.6 + modifier,
                    AttributeModifier.Operation.ADD_MULTIPLIED_BASE
            );
        }
    }

    private static AttributeModifier getDamageModifier(DifficultyTier difficultyTier) {
        ResourceLocation id = ResourceLocation.fromNamespaceAndPath(DeadlineMod.MOD_ID, "deadline_mod_damage_scaling");

        if (difficultyTier.getDifficulty() == Difficulty.TIER_1) {
            return new AttributeModifier(
                    id,
                    0,
                    AttributeModifier.Operation.ADD_MULTIPLIED_BASE
            );
        } else if (difficultyTier.getDifficulty() == Difficulty.TIER_2) {
            return new AttributeModifier(
                    id,
                    0,
                    AttributeModifier.Operation.ADD_MULTIPLIED_BASE
            );
        } else if (difficultyTier.getDifficulty() == Difficulty.TIER_3) {
            return new AttributeModifier(
                    id,
                    0.2,
                    AttributeModifier.Operation.ADD_MULTIPLIED_BASE
            );
        } else if (difficultyTier.getDifficulty() == Difficulty.TIER_4) {
            return new AttributeModifier(
                    id,
                    0.4,
                    AttributeModifier.Operation.ADD_MULTIPLIED_BASE
            );
        } else {
            double modifier = difficultyTier.calculateTier5Multiplier(100) * 0.01;
            return new AttributeModifier(
                    id,
                    0.6 + modifier,
                    AttributeModifier.Operation.ADD_MULTIPLIED_BASE
            );
        }
    }

    private static AttributeModifier getArmorModifier(DifficultyTier difficultyTier) {
        ResourceLocation id = ResourceLocation.fromNamespaceAndPath(DeadlineMod.MOD_ID, "deadline_mod_armor_scaling");

        if (difficultyTier.getDifficulty() == Difficulty.TIER_1) {
            return new AttributeModifier(
                    id,
                    0,
                    AttributeModifier.Operation.ADD_VALUE
            );
        } else if (difficultyTier.getDifficulty() == Difficulty.TIER_2) {
            return new AttributeModifier(
                    id,
                    0,
                    AttributeModifier.Operation.ADD_VALUE
            );
        } else if (difficultyTier.getDifficulty() == Difficulty.TIER_3) {
            return new AttributeModifier(
                    id,
                    0,
                    AttributeModifier.Operation.ADD_VALUE
            );
        } else if (difficultyTier.getDifficulty() == Difficulty.TIER_4) {
            return new AttributeModifier(
                    id,
                    2,
                    AttributeModifier.Operation.ADD_VALUE
            );
        } else {
            double modifier = difficultyTier.calculateTier5Multiplier(100) * 0.2;
            return new AttributeModifier(
                    id,
                    4 + modifier,
                    AttributeModifier.Operation.ADD_VALUE
            );
        }
    }

}
