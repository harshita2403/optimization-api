package com.container.routing.optimization_api.model;


import java.util.List;

public class ContainerResponse {
    private List<String> movePlan;
    private int totalCost;

    public ContainerResponse(List<String> movePlan, int totalCost) {
        this.movePlan = movePlan;
        this.totalCost = totalCost;
    }

    // Getters
    public List<String> getMovePlan() { return movePlan; }
    public int getTotalCost() { return totalCost; }
}
