package com.container.routing.optimization_api.controller;

import com.optimization.model.OptimizationRequest;
import com.optimization.model.OptimizationResponse;
import com.optimization.service.OptimizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/optimization")
public class OptimizationController {

    private final OptimizationService optimizationService;

    @Autowired
    public OptimizationController(OptimizationService optimizationService) {
        this.optimizationService = optimizationService;
    }

    @PostMapping("/optimize")
    public ResponseEntity<OptimizationResponse> optimize(@RequestBody OptimizationRequest request) {
        long startTime = System.currentTimeMillis();
        OptimizationResponse response = optimizationService.optimize(request);
        long endTime = System.currentTimeMillis();

        // Log the processing time
        System.out.println("Optimization completed in " + (endTime - startTime) + " ms");

        return ResponseEntity.ok(response);
    }
}