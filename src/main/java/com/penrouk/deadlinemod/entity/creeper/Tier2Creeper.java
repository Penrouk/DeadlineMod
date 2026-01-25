package com.penrouk.deadlinemod.entity.creeper;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;

public class Tier2Creeper extends BullshitCreeper {

    public Tier2Creeper(EntityType<? extends BullshitCreeper> entityType, Level level) {
        super(entityType, level);
        this.explosionRadius = 5;
        this.maxSwell = 15;
    }
}
