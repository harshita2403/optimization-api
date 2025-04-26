package com.container.routing.optimization_api.model;

import java.util.List;

public class ContainerRequest {
    private List<String> containerIds;
    private List<List<Integer>> costMatrix;

    public List<String> getContainerIds() {
        return containerIds;
    }

    public void setContainerIds(List<String> containerIds) {
        this.containerIds = containerIds;
    }

    public List<List<Integer>> getCostMatrix() {
        return costMatrix;
    }

    public void setCostMatrix(List<List<Integer>> costMatrix) {
        this.costMatrix = costMatrix;
    }
}
