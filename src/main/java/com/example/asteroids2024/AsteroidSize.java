package com.example.asteroids2024;

public enum AsteroidSize {
    SMALL(10),
    MEDIUM(20),
    LARGE(30);

    private final int radius;

    AsteroidSize(int radius) {
        this.radius = radius;
    }

    public int getRadius() {
        return radius;
    }
}
