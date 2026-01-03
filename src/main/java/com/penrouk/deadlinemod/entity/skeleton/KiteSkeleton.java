package com.penrouk.deadlinemod.entity.skeleton;

import com.penrouk.deadlinemod.KitePlayerGoal;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.monster.Skeleton;
import net.minecraft.world.level.Level;

public class KiteSkeleton extends Skeleton {
    public KiteSkeleton(EntityType<? extends Skeleton> entityType, Level level) {
        super(entityType, level);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Skeleton.createAttributes();
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new KitePlayerGoal(this));
    }
}
