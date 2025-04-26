package com.container.routing.optimization_api.service;

import com.optimization.model.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OptimizationService {

    public OptimizationResponse optimize(OptimizationRequest request) {
        OptimizationResponse response = new OptimizationResponse();
        List<ContainerMove> moves = new ArrayList<>();

        // Sort containers based on optimization parameters
        List<Container> sortedContainers = sortContainers(request.getContainers(), request.getParameters());

        // Generate moves for each container
        int sequence = 1;
        for (Container container : sortedContainers) {
            ContainerMove move = createOptimalMove(container, request.getRoutes(), request.getParameters());
            if (move != null) {
                move.setSequence(sequence++);
                moves.add(move);
            }
        }

        // Calculate totals
        double totalCost = moves.stream().mapToDouble(ContainerMove::getCost).sum();
        double totalTime = moves.stream().mapToDouble(ContainerMove::getTime).sum();
        double totalDistance = moves.stream().mapToDouble(ContainerMove::getDistance).sum();

        // Set response
        response.setMoves(moves);
        response.setTotalCost(totalCost);
        response.setTotalTime(totalTime);
        response.setTotalDistance(totalDistance);
        response.setStatus("SUCCESS");
        response.setMessage("Optimization completed successfully");

        return response;
    }

    private List<Container> sortContainers(List<Container> containers, OptimizationParameters parameters) {
        // Implement sorting logic based on optimization parameters
        // For example, prioritize containers based on weight, distance, or other factors
        return containers.stream()
                .sorted((c1, c2) -> {
                    if (parameters.isPrioritizeCost()) {
                        return Double.compare(c1.getWeight(), c2.getWeight());
                    } else if (parameters.isPrioritizeTime()) {
                        return Double.compare(
                                calculateDistance(c1.getCurrentLocation(), c1.getTargetLocation()),
                                calculateDistance(c2.getCurrentLocation(), c2.getTargetLocation())
                        );
                    } else {
                        return 0;
                    }
                })
                .collect(Collectors.toList());
    }

    private ContainerMove createOptimalMove(Container container, List<Route> routes, OptimizationParameters parameters) {
        ContainerMove move = new ContainerMove();
        move.setContainerId(container.getId());
        move.setFrom(container.getCurrentLocation());
        move.setTo(container.getTargetLocation());

        // Find the optimal route
        Route optimalRoute = findOptimalRoute(container.getCurrentLocation(), container.getTargetLocation(), routes, parameters);

        if (optimalRoute != null) {
            move.setCost(optimalRoute.getCost());
            move.setTime(optimalRoute.getTime());
            move.setDistance(optimalRoute.getDistance());
            return move;
        }

        return null;
    }

    private Route findOptimalRoute(Location start, Location end, List<Route> routes, OptimizationParameters parameters) {
        return routes.stream()
                .filter(route -> route.getStart().equals(start) && route.getEnd().equals(end))
                .min((r1, r2) -> {
                    if (parameters.isPrioritizeCost()) {
                        return Double.compare(r1.getCost(), r2.getCost());
                    } else if (parameters.isPrioritizeTime()) {
                        return Double.compare(r1.getTime(), r2.getTime());
                    } else if (parameters.isPrioritizeDistance()) {
                        return Double.compare(r1.getDistance(), r2.getDistance());
                    } else {
                        return 0;
                    }
                })
                .orElse(null);
    }

    private double calculateDistance(Location loc1, Location loc2) {
        return Math.sqrt(
                Math.pow(loc2.getX() - loc1.getX(), 2) +
                        Math.pow(loc2.getY() - loc1.getY(), 2)
        );
    }
}