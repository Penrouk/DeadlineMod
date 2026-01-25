package com.penrouk.deadlinemod.entity.creeper;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;

public class Tier3Creeper extends BullshitCreeper {

    public Tier3Creeper(EntityType<? extends BullshitCreeper> entityType, Level level) {
        super(entityType, level);
        this.explosionRadius = 7;
        this.maxSwell = 15;
    }
}
