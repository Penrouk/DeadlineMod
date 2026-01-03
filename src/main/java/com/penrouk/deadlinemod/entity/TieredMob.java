package com.penrouk.deadlinemod.entity;

import net.minecraft.world.entity.EntityType;
import net.neoforged.neoforge.registries.DeferredHolder;

import java.util.function.Supplier;

public abstract class TieredMob {
    protected static Supplier<EntityType<?>> TIER_0;
    protected static Supplier<EntityType<?>> TIER_1;
    protected static Supplier<EntityType<?>> TIER_2;
    protected static Supplier<EntityType<?>> TIER_3;
    protected static Supplier<EntityType<?>> TIER_4;
    protected static Supplier<EntityType<?>> TIER_5;

    public Supplier<EntityType<?>> getTier0() {
        return TIER_0;
    }

    public Supplier<EntityType<?>> getTier1() {
        return TIER_1;
    }

    public Supplier<EntityType<?>> getTier2() {
        return TIER_2;
    }

    public Supplier<EntityType<?>> getTier3() {
        return TIER_3;
    }

    public Supplier<EntityType<?>> getTier4() {
        return TIER_4;
    }

    public Supplier<EntityType<?>> getTier5() {
        return TIER_5;
    }
}
