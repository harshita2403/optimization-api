package com.optimization.service;

import java.util.Arrays;

public class HungarianAlgorithm {
    private final double[][] costMatrix;
    private final int rows, cols, dim;
    private final double[] labelByWorker, labelByJob;
    private final int[] minSlackWorkerByJob;
    private final double[] minSlackValueByJob;
    private final int[] matchJobByWorker, matchWorkerByJob;
    private final int[] parentWorkerByCommittedJob;
    private final boolean[] committedWorkers;

    public HungarianAlgorithm(double[][] costMatrix) {
        this.dim = Math.max(costMatrix.length, costMatrix[0].length);
        this.rows = costMatrix.length;
        this.cols = costMatrix[0].length;
        this.costMatrix = new double[this.dim][this.dim];
        
        for (int w = 0; w < this.dim; w++) {
            if (w < costMatrix.length) {
                if (costMatrix[w].length != cols) {
                    throw new IllegalArgumentException("Irregular cost matrix");
                }
                for (int j = 0; j < cols; j++) {
                    this.costMatrix[w][j] = costMatrix[w][j];
                }
                for (int j = cols; j < this.dim; j++) {
                    this.costMatrix[w][j] = 0;
                }
            } else {
                for (int j = 0; j < this.dim; j++) {
                    this.costMatrix[w][j] = 0;
                }
            }
        }

        labelByWorker = new double[this.dim];
        labelByJob = new double[this.dim];
        minSlackWorkerByJob = new int[this.dim];
        minSlackValueByJob = new double[this.dim];
        committedWorkers = new boolean[this.dim];
        parentWorkerByCommittedJob = new int[this.dim];
        matchJobByWorker = new int[this.dim];
        matchWorkerByJob = new int[this.dim];

        // Initialize arrays with -1
        Arrays.fill(matchJobByWorker, -1);
        Arrays.fill(matchWorkerByJob, -1);
        Arrays.fill(parentWorkerByCommittedJob, -1);

        computeInitialFeasibleSolution();
        executePhase();
    }

    protected void computeInitialFeasibleSolution() {
        for (int j = 0; j < dim; j++) {
            labelByJob[j] = Double.POSITIVE_INFINITY;
        }
        
        for (int w = 0; w < dim; w++) {
            for (int j = 0; j < dim; j++) {
                if (costMatrix[w][j] < labelByJob[j]) {
                    labelByJob[j] = costMatrix[w][j];
                }
            }
        }
    }

    protected void executePhase() {
        while (true) {
            int minSlackWorker = -1, minSlackJob = -1;
            double minSlackValue = Double.POSITIVE_INFINITY;

            for (int j = 0; j < dim; j++) {
                if (parentWorkerByCommittedJob[j] == -1) {
                    if (minSlackValueByJob[j] < minSlackValue) {
                        minSlackValue = minSlackValueByJob[j];
                        minSlackWorker = minSlackWorkerByJob[j];
                        minSlackJob = j;
                    }
                }
            }

            if (minSlackValue > 0) {
                updateLabeling(minSlackValue);
            }

            parentWorkerByCommittedJob[minSlackJob] = minSlackWorker;

            if (matchWorkerByJob[minSlackJob] == -1) {
                int committedJob = minSlackJob;
                int parentWorker = parentWorkerByCommittedJob[committedJob];
                while (true) {
                    int temp = matchJobByWorker[parentWorker];
                    match(parentWorker, committedJob);
                    if (temp == -1) {
                        break;
                    }
                    committedJob = temp;
                    parentWorker = parentWorkerByCommittedJob[committedJob];
                }
                return;
            }

            int worker = matchWorkerByJob[minSlackJob];
            committedWorkers[worker] = true;
            for (int j = 0; j < dim; j++) {
                if (parentWorkerByCommittedJob[j] == -1) {
                    double slack = costMatrix[worker][j] - labelByWorker[worker] - labelByJob[j];
                    if (minSlackValueByJob[j] > slack) {
                        minSlackValueByJob[j] = slack;
                        minSlackWorkerByJob[j] = worker;
                    }
                }
            }
        }
    }

    protected void updateLabeling(double slack) {
        for (int w = 0; w < dim; w++) {
            if (committedWorkers[w]) {
                labelByWorker[w] += slack;
            }
        }
        for (int j = 0; j < dim; j++) {
            if (parentWorkerByCommittedJob[j] != -1) {
                labelByJob[j] -= slack;
            } else {
                minSlackValueByJob[j] -= slack;
            }
        }
    }

    protected void match(int worker, int job) {
        matchJobByWorker[worker] = job;
        matchWorkerByJob[job] = worker;
    }

    public int[] getMatchJobByWorker() {
        return Arrays.copyOf(matchJobByWorker, rows);
    }

    public int[] getMatchWorkerByJob() {
        return Arrays.copyOf(matchWorkerByJob, cols);
    }
} 