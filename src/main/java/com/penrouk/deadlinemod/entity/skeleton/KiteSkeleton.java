package com.penrouk.deadlinemod.entity.skeleton;

import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.goal.AvoidEntityGoal;
import net.minecraft.world.entity.monster.Skeleton;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

class KitePlayerGoal extends AvoidEntityGoal {
    private Skeleton skeleton;

    public KitePlayerGoal(Skeleton skeleton) {
        super(skeleton, Player.class, 8, 1.6, 1.6, EntitySelector.NO_SPECTATORS);
        this.skeleton = skeleton;
    }

    @Override
    public void start() {
        super.start();
    }

    @Override
    public void stop() {
        super.stop();
    }
}


public class KiteSkeleton extends Skeleton {
    public KiteSkeleton(EntityType<? extends Skeleton> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new KitePlayerGoal(this));
    }
}
