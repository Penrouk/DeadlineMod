package com.penrouk.deadlinemod.event;

import com.penrouk.deadlinemod.DeadlineMod;
import com.penrouk.deadlinemod.entity.ModEntities;
import com.penrouk.deadlinemod.util.DifficultyMobStatApplier;
import com.penrouk.deadlinemod.util.DifficultyTier;
import com.penrouk.deadlinemod.util.OverrideMobSpawn;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;
import net.neoforged.neoforge.event.entity.EntityAttributeModificationEvent;
import net.neoforged.neoforge.event.entity.living.FinalizeSpawnEvent;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;

@EventBusSubscriber(modid = DeadlineMod.MOD_ID)
public class ModEventBusEvents {
    @SubscribeEvent
    public static void registerAttributes(EntityAttributeCreationEvent event) {
        ModEntities.registerMobAttributes(event);
    }

    @SubscribeEvent
    public static void onModifyEntityAttributes(EntityAttributeModificationEvent event) {
        for (EntityType<?> entityType : BuiltInRegistries.ENTITY_TYPE) {
            if (entityType.getCategory() == MobCategory.CREATURE) {
                event.add((EntityType<? extends LivingEntity>) entityType, Attributes.ATTACK_DAMAGE);
            }
        }
    }

    @SubscribeEvent
    public static void onFinalizeMobSpawn(FinalizeSpawnEvent event) {
        DifficultyTier difficultyTier = new DifficultyTier((int)event.getEntity().getX(), (int)event.getEntity().getZ());

        Mob newMob = OverrideMobSpawn.checkMobSpawnOverride(event, difficultyTier);

        // Add the tier to the name as well as color it
        String existingName = newMob.getName().getString();
        newMob.setCustomName(
                Component.literal(difficultyTier + " " + existingName)
        );
        newMob.setCustomNameVisible(true);

        // Modify their stats
        DifficultyMobStatApplier.tweakMobStats(newMob, difficultyTier);
    }

    @SubscribeEvent
    public static void onEntityDamageEvent(LivingDamageEvent.Pre event) {
        if (event.getSource().isCreativePlayer()) {
            System.out.println("We pausing");
        }
    }


}