package com.penrouk.deadlinemod.event;

import com.penrouk.deadlinemod.DeadlineMod;
import com.penrouk.deadlinemod.entity.ModEntities;
import com.penrouk.deadlinemod.entity.skeleton.KiteSkeleton;
import com.penrouk.deadlinemod.util.OverrideMobSpawn;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;
import net.neoforged.neoforge.event.entity.living.FinalizeSpawnEvent;

@EventBusSubscriber(modid = DeadlineMod.MOD_ID)
public class ModEventBusEvents {
    @SubscribeEvent
    public static void registerAttributes(EntityAttributeCreationEvent event) {
        ModEntities.registerMobAttributes(event);
    }

    @SubscribeEvent
    public static void onFinalizeMobSpawn(FinalizeSpawnEvent event) {
        OverrideMobSpawn.checkMobSpawnOverride(event);
    }
}