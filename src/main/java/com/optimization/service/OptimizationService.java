package com.optimization.service;

import com.optimization.model.*;
import org.springframework.stereotype.Service;
import org.springframework.stereotype.Component;
import java.util.*;

@Service
@Component
public class OptimizationService {

    public OptimizationService() {
        // Default constructor
    }

    public OptimizationResponse optimize(OptimizationRequest request) {
        OptimizationResponse response = new OptimizationResponse();
        List<ContainerMove> moves = new ArrayList<>();

        // Create cost matrix for Hungarian Algorithm
        double[][] costMatrix = createCostMatrix(request.getContainers(), request.getRoutes(), request.getParameters());
        
        // Apply Hungarian Algorithm
        HungarianAlgorithm algorithm = new HungarianAlgorithm(costMatrix);
        int[] assignments = algorithm.getMatchJobByWorker();

        // Generate moves based on optimal assignments
        List<Container> containers = request.getContainers();
        List<Route> routes = request.getRoutes();
        
        for (int i = 0; i < assignments.length; i++) {
            if (i < containers.size() && assignments[i] < routes.size()) {
                Container container = containers.get(i);
                Route route = routes.get(assignments[i]);
                
                ContainerMove move = createMove(container, route, i + 1);
                if (move != null) {
                    moves.add(move);
                }
            }
        }

        // Calculate totals
        double totalCost = moves.stream().mapToDouble(ContainerMove::getCost).sum();
        double totalTime = moves.stream().mapToDouble(ContainerMove::getTime).sum();
        double totalDistance = moves.stream().mapToDouble(ContainerMove::getDistance).sum();

        // Validate against constraints
        boolean isValid = validateSolution(totalCost, totalTime, totalDistance, request.getParameters());

        // Set response
        response.setMoves(moves);
        response.setTotalCost(totalCost);
        response.setTotalTime(totalTime);
        response.setTotalDistance(totalDistance);
        response.setStatus(isValid ? "SUCCESS" : "CONSTRAINTS_VIOLATED");
        response.setMessage(isValid ? "Optimization completed successfully" : "Solution violates constraints");

        return response;
    }

    private double[][] createCostMatrix(List<Container> containers, List<Route> routes, OptimizationParameters params) {
        double[][] costMatrix = new double[containers.size()][routes.size()];
        
        for (int i = 0; i < containers.size(); i++) {
            Container container = containers.get(i);
            for (int j = 0; j < routes.size(); j++) {
                Route route = routes.get(j);
                costMatrix[i][j] = calculateCost(container, route, params);
            }
        }
        
        return costMatrix;
    }

    private double calculateCost(Container container, Route route, OptimizationParameters params) {
        double cost = 0.0;
        
        // Check if route matches container's start and end locations
        if (!route.getStart().equals(container.getCurrentLocation()) || 
            !route.getEnd().equals(container.getTargetLocation())) {
            return Double.MAX_VALUE; // Invalid route
        }

        // Calculate weighted cost based on priorities
        if (params.isPrioritizeCost()) {
            cost += route.getCost() * 2.0;
        }
        if (params.isPrioritizeTime()) {
            cost += route.getTime() * 2.0;
        }
        if (params.isPrioritizeDistance()) {
            cost += route.getDistance() * 2.0;
        }

        // Add base costs if no priorities are set
        if (!params.isPrioritizeCost() && !params.isPrioritizeTime() && !params.isPrioritizeDistance()) {
            cost = route.getCost() + route.getTime() + route.getDistance();
        }

        // Factor in container weight
        cost *= (1.0 + container.getWeight() / 1000.0);

        return cost;
    }

    private ContainerMove createMove(Container container, Route route, int sequence) {
        ContainerMove move = new ContainerMove();
        move.setContainerId(container.getId());
        move.setFrom(container.getCurrentLocation());
        move.setTo(container.getTargetLocation());
        move.setCost(route.getCost());
        move.setTime(route.getTime());
        move.setDistance(route.getDistance());
        move.setSequence(sequence);
        return move;
    }

    private boolean validateSolution(double totalCost, double totalTime, double totalDistance, 
                                   OptimizationParameters params) {
        return (totalCost <= params.getMaxCost() || params.getMaxCost() == 0) &&
               (totalTime <= params.getMaxTime() || params.getMaxTime() == 0) &&
               (totalDistance <= params.getMaxDistance() || params.getMaxDistance() == 0);
    }
} 