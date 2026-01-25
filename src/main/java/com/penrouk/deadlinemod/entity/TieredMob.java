package com.penrouk.deadlinemod.entity;

import net.minecraft.world.entity.EntityType;
import net.neoforged.neoforge.registries.DeferredHolder;

import java.util.function.Supplier;

public abstract class TieredMob {
    public abstract EntityType<?> getTier0();
    public abstract EntityType<?> getTier1();
    public abstract EntityType<?> getTier2();
    public abstract EntityType<?> getTier3();
    public abstract EntityType<?> getTier4();
    public abstract EntityType<?> getTier5();
}
