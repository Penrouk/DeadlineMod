package com.penrouk.deadlinemod.event;

import com.penrouk.deadlinemod.DeadlineMod;
import com.penrouk.deadlinemod.entity.ModEntities;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;
import net.neoforged.neoforge.event.entity.EntityAttributeModificationEvent;

@EventBusSubscriber(modid = DeadlineMod.MOD_ID)
public class RegisterMobEvents {
    @SubscribeEvent
    public static void onModifyEntityAttributes(EntityAttributeModificationEvent event) {
        System.out.println("MODIFY ENTITY ATTRIBUTES");

        for (EntityType<?> entityType : BuiltInRegistries.ENTITY_TYPE) {
            if (entityType.getCategory() == MobCategory.CREATURE) {
                event.add((EntityType<? extends LivingEntity>) entityType, Attributes.ATTACK_DAMAGE);
            }
        }
    }

    @SubscribeEvent
    public static void registerAttributes(EntityAttributeCreationEvent event) {
        System.out.println("REGISTER ATTRIBUTES");

        ModEntities.registerMobAttributes(event);
    }
}
