package com.penrouk.deadlinemod.entity.creeper;

import com.penrouk.deadlinemod.entity.ModEntities;
import com.penrouk.deadlinemod.entity.TieredMob;
import net.minecraft.client.renderer.entity.CreeperRenderer;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;

import java.util.function.Supplier;

public class TieredCreeper extends TieredMob {
    protected static Supplier<EntityType<?>> TIER_1;
    protected static Supplier<EntityType<?>> TIER_2;
    protected static Supplier<EntityType<?>> TIER_3;
    protected static Supplier<EntityType<?>> TIER_4;
    protected static Supplier<EntityType<?>> TIER_5;

    public static void register() {
        TIER_1 = ModEntities.ENTITY_TYPES.register("creeper.tier_1", () -> EntityType.Builder.of(Tier1Creeper::new, MobCategory.MONSTER).sized(0.6f, 1.95f).build("creeper.tier_1"));
        TIER_2 = ModEntities.ENTITY_TYPES.register("creeper.tier_2", () -> EntityType.Builder.of(Tier2Creeper::new, MobCategory.MONSTER).sized(0.6f, 1.95f).build("creeper.tier_2"));
        TIER_3 = ModEntities.ENTITY_TYPES.register("creeper.tier_3", () -> EntityType.Builder.of(Tier3Creeper::new, MobCategory.MONSTER).sized(0.6f, 1.95f).build("creeper.tier_3"));
        TIER_4 = ModEntities.ENTITY_TYPES.register("creeper.tier_4", () -> EntityType.Builder.of(Tier4Creeper::new, MobCategory.MONSTER).sized(0.6f, 1.95f).build("creeper.tier_4"));
        TIER_5 = ModEntities.ENTITY_TYPES.register("creeper.tier_5", () -> EntityType.Builder.of(Tier5Creeper::new, MobCategory.MONSTER).sized(0.6f, 1.95f).build("creeper.tier_5"));
    }

    public static void registerClient() {
        EntityRenderers.register(((EntityType<Tier1Creeper>)TIER_1.get()), CreeperRenderer::new);
        EntityRenderers.register(((EntityType<Tier2Creeper>)TIER_2.get()), CreeperRenderer::new);
        EntityRenderers.register(((EntityType<Tier3Creeper>)TIER_3.get()), CreeperRenderer::new);
        EntityRenderers.register(((EntityType<Tier4Creeper>)TIER_4.get()), CreeperRenderer::new);
        EntityRenderers.register(((EntityType<Tier5Creeper>)TIER_5.get()), CreeperRenderer::new);
    }

    public EntityType<?> getTier0() {
        return EntityType.CREEPER;
    }
    public EntityType<?> getTier1() {
        return TIER_1.get();
    }
    public EntityType<?> getTier2() {
        return TIER_2.get();
    }
    public EntityType<?> getTier3() {
        return TIER_3.get();
    }
    public EntityType<?> getTier4() {
        return TIER_4.get();
    }
    public EntityType<?> getTier5() {
        return TIER_5.get();
    }

    public static void registerMobAttributes(EntityAttributeCreationEvent event) {
        event.put((EntityType<Tier1Creeper>)TIER_1.get(), Tier1Creeper.createAttributes().build());
        event.put((EntityType<Tier2Creeper>)TIER_2.get(), Tier2Creeper.createAttributes().build());
        event.put((EntityType<Tier3Creeper>)TIER_3.get(), Tier3Creeper.createAttributes().build());
        event.put((EntityType<Tier4Creeper>)TIER_4.get(), Tier4Creeper.createAttributes().build());
        event.put((EntityType<Tier5Creeper>)TIER_5.get(), Tier5Creeper.createAttributes().build());
    }
}
