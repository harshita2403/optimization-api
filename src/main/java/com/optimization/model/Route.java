package com.optimization.model;

import java.util.Objects;

public class Route {
    private Location start;
    private Location end;
    private double distance;
    private double cost;
    private double time;

    // Getters and Setters
    public Location getStart() {
        return start;
    }

    public void setStart(Location start) {
        this.start = start;
    }

    public Location getEnd() {
        return end;
    }

    public void setEnd(Location end) {
        this.end = end;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public double getTime() {
        return time;
    }

    public void setTime(double time) {
        this.time = time;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Route route = (Route) o;
        return Double.compare(route.distance, distance) == 0 &&
               Double.compare(route.cost, cost) == 0 &&
               Double.compare(route.time, time) == 0 &&
               Objects.equals(start, route.start) &&
               Objects.equals(end, route.end);
    }

    @Override
    public int hashCode() {
        return Objects.hash(start, end, distance, cost, time);
    }

    @Override
    public String toString() {
        return "Route{" +
               "start=" + start +
               ", end=" + end +
               ", distance=" + distance +
               ", cost=" + cost +
               ", time=" + time +
               '}';
    }
} 