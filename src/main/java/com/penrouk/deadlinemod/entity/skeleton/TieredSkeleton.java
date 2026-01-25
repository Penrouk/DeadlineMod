package com.penrouk.deadlinemod.entity.skeleton;

import com.penrouk.deadlinemod.entity.ModEntities;
import com.penrouk.deadlinemod.entity.TieredMob;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.client.renderer.entity.SkeletonRenderer;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;

import java.util.function.Supplier;

public class TieredSkeleton extends TieredMob {
    protected static Supplier<EntityType<?>> TIER_3;
    protected static Supplier<EntityType<?>> TIER_5;

    public static void register() {
        TIER_3 = ModEntities.ENTITY_TYPES.register("skeleton.tier_3", () -> EntityType.Builder.of(KiteSkeleton::new, MobCategory.MONSTER).sized(0.6f, 1.95f).build("skeleton.tier_3"));
        TIER_5 = ModEntities.ENTITY_TYPES.register("skeleton.tier_5", () -> EntityType.Builder.of(BullshitKiteSkeleton::new, MobCategory.MONSTER).sized(0.6f, 1.95f).build("skeleton.tier_1"));
    }

    public static void registerClient() {
        EntityRenderers.register(((EntityType<KiteSkeleton>)TIER_3.get()), SkeletonRenderer::new);
        EntityRenderers.register(((EntityType<BullshitKiteSkeleton>)TIER_5.get()), SkeletonRenderer::new);
    }

    public EntityType<?> getTier0() {
        return EntityType.SKELETON;
    }

    public EntityType<?> getTier1() {
        return EntityType.SKELETON;
    }

    public EntityType<?> getTier2() {
        return EntityType.SKELETON;
    }

    public EntityType<?> getTier3() {
        return TIER_3.get();
    }

    public EntityType<?> getTier4() { return getTier3(); }

    public EntityType<?> getTier5() { return getTier4(); }

    public static void registerMobAttributes(EntityAttributeCreationEvent event) {
        event.put((EntityType<KiteSkeleton>)TIER_3.get(), KiteSkeleton.createAttributes().build());
        event.put((EntityType<BullshitKiteSkeleton>)TIER_5.get(), BullshitKiteSkeleton.createAttributes().build());
    }
}
