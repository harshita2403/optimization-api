package com.optimization.model;

import java.util.Objects;

public class Location {
    private double x;
    private double y;
    private String name;

    public Location() {}

    public Location(double x, double y, String name) {
        this.x = x;
        this.y = y;
        this.name = name;
    }

    // Getters and Setters
    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Location location = (Location) o;
        return Double.compare(location.x, x) == 0 &&
               Double.compare(location.y, y) == 0 &&
               Objects.equals(name, location.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y, name);
    }

    @Override
    public String toString() {
        return "Location{" +
               "x=" + x +
               ", y=" + y +
               ", name='" + name + '\'' +
               '}';
    }
} 