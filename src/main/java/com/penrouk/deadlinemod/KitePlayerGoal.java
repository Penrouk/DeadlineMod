package com.penrouk.deadlinemod;

import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.ai.goal.AvoidEntityGoal;
import net.minecraft.world.entity.monster.Skeleton;
import net.minecraft.world.entity.player.Player;


public class KitePlayerGoal extends AvoidEntityGoal {
    private Skeleton skeleton;

    public KitePlayerGoal(Skeleton skeleton) {
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
