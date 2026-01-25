package com.penrouk.deadlinemod.entity;

import com.penrouk.deadlinemod.DeadlineMod;
import com.penrouk.deadlinemod.entity.creeper.TieredCreeper;
import com.penrouk.deadlinemod.entity.skeleton.TieredSkeleton;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.EntityType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModEntities {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES =
            DeferredRegister.create(BuiltInRegistries.ENTITY_TYPE, DeadlineMod.MOD_ID);

    static {
        TieredSkeleton.register();
        TieredCreeper.register();
    }


    public static void register(IEventBus eventBus) {
        ENTITY_TYPES.register(eventBus);
    }

    public static void registerClient(FMLClientSetupEvent event) {
        TieredSkeleton.registerClient();
        TieredCreeper.registerClient();
    }

    public static void registerMobAttributes(EntityAttributeCreationEvent event) {
        TieredSkeleton.registerMobAttributes(event);
        TieredCreeper.registerMobAttributes(event);
    }
}