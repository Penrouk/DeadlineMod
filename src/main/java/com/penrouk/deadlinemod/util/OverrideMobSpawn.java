package com.penrouk.deadlinemod.util;

import com.penrouk.deadlinemod.entity.ModEntities;
import com.penrouk.deadlinemod.entity.TieredMob;
import com.penrouk.deadlinemod.entity.skeleton.KiteSkeleton;
import com.penrouk.deadlinemod.entity.skeleton.Tier0Skeleton;
import com.penrouk.deadlinemod.entity.skeleton.TieredSkeleton;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.monster.Skeleton;
import net.neoforged.neoforge.event.entity.living.FinalizeSpawnEvent;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Supplier;

public class OverrideMobSpawn {
    private static final Map<Class<?>, TieredMob> MOB_MAP = new LinkedHashMap<>();

    static {
        MOB_MAP.put(Skeleton.class, new TieredSkeleton());
    }

    public static void checkMobSpawnOverride(FinalizeSpawnEvent event) {
        Mob mob = event.getEntity();

        for (Map.Entry<Class<?>, TieredMob> entry : MOB_MAP.entrySet()) {
            if (entry.getKey().isInstance(mob)) {
                doSpawn(event, entry.getValue());
            }
        }
    }

    private static void doSpawn(FinalizeSpawnEvent event, TieredMob tieredMob) {
        Mob originalMob = event.getEntity();
        Supplier<EntityType<?>> supplier = getEntityTypeSupplier(event, tieredMob);

        Mob replacement = (Mob) supplier.get().create(originalMob.level());
        replacement.moveTo(
                originalMob.getX(),
                originalMob.getY(),
                originalMob.getZ(),
                originalMob.getYRot(),
                originalMob.getXRot()
        );

        originalMob.discard();
        event.getLevel().addFreshEntity(replacement);
    }

    private static Supplier<EntityType<?>> getEntityTypeSupplier(FinalizeSpawnEvent event, TieredMob tieredMob) {
        DifficultyTier difficultyTier = new DifficultyTier((int) event.getX(), (int) event.getZ());
        Supplier<EntityType<?>> supplier;

        if (difficultyTier.getDifficulty() == Difficulty.TIER_0) {
            supplier = tieredMob.getTier0();
        } else if (difficultyTier.getDifficulty() == Difficulty.TIER_1) {
            supplier = tieredMob.getTier1();
        } else if (difficultyTier.getDifficulty() == Difficulty.TIER_2) {
            supplier = tieredMob.getTier2();
        } else if (difficultyTier.getDifficulty() == Difficulty.TIER_3) {
            supplier = tieredMob.getTier3();
        } else if (difficultyTier.getDifficulty() == Difficulty.TIER_4) {
            supplier = tieredMob.getTier4();
        } else {
            supplier = tieredMob.getTier5();
        }

        return supplier;
    }
}
