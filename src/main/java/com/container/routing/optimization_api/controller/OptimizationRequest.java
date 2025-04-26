package com.container.routing.optimization_api.controller;

import java.util.List;
public class OptimizationRequest {
    private List<List<Integer>> costMatrix;

    public List<List<Integer>> getCostMatrix() {
        return costMatrix;
    }

    public void setCostMatrix(List<List<Integer>> costMatrix) {
        this.costMatrix = costMatrix;
    }
}
