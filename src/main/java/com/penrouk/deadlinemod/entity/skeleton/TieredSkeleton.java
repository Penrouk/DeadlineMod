package com.penrouk.deadlinemod.entity.skeleton;

import com.penrouk.deadlinemod.entity.ModEntities;
import com.penrouk.deadlinemod.entity.TieredMob;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.client.renderer.entity.SkeletonRenderer;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;

public class TieredSkeleton extends TieredMob {
    public static void register() {
        TIER_0 = ModEntities.ENTITY_TYPES.register("skeleton.tier_0", () -> EntityType.Builder.of(Tier0Skeleton::new, MobCategory.MONSTER).sized(0.6f, 1.95f).build("skeleton.tier_0"));
        TIER_1 = ModEntities.ENTITY_TYPES.register("skeleton.tier_1", () -> EntityType.Builder.of(Tier1Skeleton::new, MobCategory.MONSTER).sized(0.6f, 1.95f).build("skeleton.tier_1"));
    }

    public static void registerClient() {
        EntityRenderers.register(((EntityType<Tier0Skeleton>)TIER_0.get()), SkeletonRenderer::new);
        EntityRenderers.register(((EntityType<Tier1Skeleton>)TIER_1.get()), SkeletonRenderer::new);
    }

    public static void registerMobAttributes(EntityAttributeCreationEvent event) {
        event.put((EntityType<Tier0Skeleton>)TIER_0.get(), Tier0Skeleton.createAttributes().build());
        event.put((EntityType<Tier1Skeleton>)TIER_1.get(), Tier0Skeleton.createAttributes().build());
    }
}
