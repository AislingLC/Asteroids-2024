package com.example.asteroids2024;

import java.util.List;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class HighScoreWriter {

    public static void writeHighScores(List<Integer> highScores, String filename) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            for (int score : highScores) {
                writer.write(String.valueOf(score));
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        List<Integer> scores = List.of(4, 6, 8, 9, 23); // Example scores, replace with your actual logic
        String filename = "highScores.txt"; // Ensure this path is correct for your setup

        writeHighScores(scores, filename);

    }
}