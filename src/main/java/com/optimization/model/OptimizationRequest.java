package com.optimization.model;

import java.util.List;

public class OptimizationRequest {
    private List<Container> containers;
    private List<Route> routes;
    private OptimizationParameters parameters;

    // Getters and Setters
    public List<Container> getContainers() {
        return containers;
    }

    public void setContainers(List<Container> containers) {
        this.containers = containers;
    }

    public List<Route> getRoutes() {
        return routes;
    }

    public void setRoutes(List<Route> routes) {
        this.routes = routes;
    }

    public OptimizationParameters getParameters() {
        return parameters;
    }

    public void setParameters(OptimizationParameters parameters) {
        this.parameters = parameters;
    }
} 