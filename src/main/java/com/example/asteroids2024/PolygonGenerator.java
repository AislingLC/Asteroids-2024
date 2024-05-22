package com.example.asteroids2024;
import java.util.Arrays;
import java.util.Random;

public class PolygonGenerator {

    private static final int NUM_POINTS = 12;

    public static double[] generatePoints(double radius) {
        double[] points = new double[NUM_POINTS * 2];
        double[] angles = new double[NUM_POINTS];
        Random random = new Random();

        // Generate random angles
        for (int i = 0; i < NUM_POINTS; i++) {
            angles[i] = random.nextDouble() * 360;
        }

        // Sort the angles
        Arrays.sort(angles);

        // Calculate the points based on sorted angles
        for (int i = 0; i < NUM_POINTS; i++) {
            double angle = angles[i];
            double x = radius * Math.cos(Math.toRadians(angle));
            double y = radius * Math.sin(Math.toRadians(angle));
            points[2 * i] = x;
            points[2 * i + 1] = y;
        }

        return points;
    }

    public static void main(String[] args) {
        double radius = 40.0; // Can input different radius here for different sized asteroids
        double[] points = generatePoints(radius);


    }
}

