package com.optimization.model;

import java.util.List;

public class OptimizationResponse {
    private List<ContainerMove> moves;
    private double totalCost;
    private double totalTime;
    private double totalDistance;
    private String status;
    private String message;

    // Getters and Setters
    public List<ContainerMove> getMoves() {
        return moves;
    }

    public void setMoves(List<ContainerMove> moves) {
        this.moves = moves;
    }

    public double getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(double totalCost) {
        this.totalCost = totalCost;
    }

    public double getTotalTime() {
        return totalTime;
    }

    public void setTotalTime(double totalTime) {
        this.totalTime = totalTime;
    }

    public double getTotalDistance() {
        return totalDistance;
    }

    public void setTotalDistance(double totalDistance) {
        this.totalDistance = totalDistance;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
} 