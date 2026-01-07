package com.penrouk.deadlinemod.util;
import net.minecraft.world.phys.Vec2;

public class DifficultyTier {
    private final Difficulty difficulty;
    private final double distance;

    public DifficultyTier(int x, int z) {
        this.distance = Math.pow(x * x + z * z, 0.5);
        this.difficulty = this.calculateDifficulty();
    }

    public Difficulty getDifficulty() { return this.difficulty; }

    public double calculateTier5Multiplier(int blocksPer) {
        double adjustedDistance = this.distance - 5000;
        if (adjustedDistance < 0) {
            return 0;
        }
        return adjustedDistance / blocksPer;
    }

    @Override
    public String toString() {
        if (this.difficulty == Difficulty.TIER_0) {
            return "Tier 0";
        } else if (this.difficulty == Difficulty.TIER_1) {
            return "Tier 1";
        } else if (this.difficulty == Difficulty.TIER_2) {
            return "Tier 2";
        } else if (this.difficulty == Difficulty.TIER_3) {
            return "Tier 3";
        } else if (this.difficulty == Difficulty.TIER_4) {
            return "Tier 4";
        } else if (this.difficulty == Difficulty.TIER_5) {
            return "Tier 5";
        }
        return "Tier ??";
    }

    private Difficulty calculateDifficulty() {
        if (this.distance < 1000) {
            return Difficulty.TIER_0;
        } else if (this.distance < 2000) {
            return Difficulty.TIER_1;
        } else if (this.distance < 3000) {
            return Difficulty.TIER_2;
        } else if (this.distance < 4000) {
            return Difficulty.TIER_3;
        } else if(this.distance < 5000) {
            return Difficulty.TIER_4;
        } else {
            return Difficulty.TIER_5;
        }
    }
}
