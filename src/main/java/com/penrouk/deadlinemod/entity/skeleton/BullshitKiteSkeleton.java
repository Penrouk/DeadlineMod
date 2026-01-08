package com.penrouk.deadlinemod.entity.skeleton;

import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.goal.AvoidEntityGoal;
import net.minecraft.world.entity.monster.Skeleton;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

class BullshitKitePlayerGoal extends AvoidEntityGoal {
    private Skeleton skeleton;

    public BullshitKitePlayerGoal(Skeleton skeleton) {
        super(skeleton, Player.class, 8, 1.6, 1.6, EntitySelector.NO_SPECTATORS);
        this.skeleton = skeleton;
    }

    @Override
    public void start() {
        this.skeleton.lookAt(this.toAvoid, 30, 30);
        this.skeleton.performRangedAttack(this.toAvoid, 1);
        super.start();
    }

    @Override
    public void stop() {
        this.skeleton.lookAt(this.toAvoid, 30, 30);
        this.skeleton.performRangedAttack(this.toAvoid, 1);
        super.stop();
    }
}


public class BullshitKiteSkeleton extends Skeleton {
    public BullshitKiteSkeleton(EntityType<? extends Skeleton> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(0, new BullshitKitePlayerGoal(this));
    }

    @Override
    protected boolean isSunBurnTick() { return false; }
}
