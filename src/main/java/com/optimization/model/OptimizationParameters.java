package com.optimization.model;

public class OptimizationParameters {
    private double maxCost;
    private double maxTime;
    private double maxDistance;
    private boolean prioritizeCost;
    private boolean prioritizeTime;
    private boolean prioritizeDistance;

    // Getters and Setters
    public double getMaxCost() {
        return maxCost;
    }

    public void setMaxCost(double maxCost) {
        this.maxCost = maxCost;
    }

    public double getMaxTime() {
        return maxTime;
    }

    public void setMaxTime(double maxTime) {
        this.maxTime = maxTime;
    }

    public double getMaxDistance() {
        return maxDistance;
    }

    public void setMaxDistance(double maxDistance) {
        this.maxDistance = maxDistance;
    }

    public boolean isPrioritizeCost() {
        return prioritizeCost;
    }

    public void setPrioritizeCost(boolean prioritizeCost) {
        this.prioritizeCost = prioritizeCost;
    }

    public boolean isPrioritizeTime() {
        return prioritizeTime;
    }

    public void setPrioritizeTime(boolean prioritizeTime) {
        this.prioritizeTime = prioritizeTime;
    }

    public boolean isPrioritizeDistance() {
        return prioritizeDistance;
    }

    public void setPrioritizeDistance(boolean prioritizeDistance) {
        this.prioritizeDistance = prioritizeDistance;
    }
} 