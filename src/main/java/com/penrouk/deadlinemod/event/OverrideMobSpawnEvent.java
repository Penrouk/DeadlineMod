package com.penrouk.deadlinemod.event;

import com.penrouk.deadlinemod.DeadlineMod;
import com.penrouk.deadlinemod.entity.TieredMob;
import com.penrouk.deadlinemod.entity.creeper.TieredCreeper;
import com.penrouk.deadlinemod.entity.skeleton.TieredSkeleton;
import com.penrouk.deadlinemod.util.*;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.entity.monster.Skeleton;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.FinalizeSpawnEvent;

import java.util.LinkedHashMap;
import java.util.Map;

@EventBusSubscriber(modid = DeadlineMod.MOD_ID)
public class OverrideMobSpawnEvent {
    private static final Map<Class<?>, TieredMob> MOB_MAP = new LinkedHashMap<>();

    static {
        MOB_MAP.put(Skeleton.class, new TieredSkeleton());
        MOB_MAP.put(Creeper.class, new TieredCreeper());
    }

    @SubscribeEvent
    public static void onFinalizeMobSpawn(FinalizeSpawnEvent event) {
        System.out.println("FINALIZE MOB SPAWN");

        DifficultyTier difficultyTier = new DifficultyTier((int)event.getX(), (int)event.getZ());

        Mob newMob = checkMobSpawnOverride(event, difficultyTier);

        // Stylize mob name
        DifficultyMobNameChanger.stylizeMobName(newMob, difficultyTier);

        // Modify their stats
        DifficultyMobStatApplier.tweakMobStats(newMob, difficultyTier);
    }

    public static Mob checkMobSpawnOverride(FinalizeSpawnEvent event, DifficultyTier difficultyTier) {
        Mob mob = event.getEntity();

        for (Map.Entry<Class<?>, TieredMob> entry : MOB_MAP.entrySet()) {
            if (entry.getKey().isInstance(mob)) {
                return doSpawn(event, entry.getValue(), difficultyTier);
            }
        }

        return mob;
    }

    private static Mob doSpawn(FinalizeSpawnEvent event, TieredMob tieredMob, DifficultyTier difficultyTier) {
        Mob originalMob = event.getEntity();
        EntityType<?> entityType = getEntityType(difficultyTier, tieredMob);

        Mob replacement = (Mob) entityType.create(originalMob.level());
        replacement.moveTo(
                originalMob.getX(),
                originalMob.getY(),
                originalMob.getZ(),
                originalMob.getYRot(),
                originalMob.getXRot()
        );

        originalMob.discard();
        event.getLevel().addFreshEntity(replacement);
        replacement.finalizeSpawn(event.getLevel(), event.getDifficulty(), MobSpawnType.CONVERSION, null);
        return replacement;
    }

    private static EntityType<?> getEntityType(DifficultyTier difficultyTier, TieredMob tieredMob) {
        EntityType<?> supplier;

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
