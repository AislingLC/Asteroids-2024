package com.example.asteroids2024;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class HighScoreReader {
    public static List<Integer> readHighScoresAsList(String filename) {
        List<Integer> highScores = new ArrayList<>();
        int linesRead = 0;
        try (BufferedReader br = new BufferedReader(new FileReader(filename))){
            String line;
            while((line = br.readLine()) != null && linesRead <5){
                try {
                    int number = Integer.parseInt(line.trim());
                    highScores.add(number);
                    linesRead++;
                } catch (NumberFormatException e) {
                    System.err.println("Skipping invalid number: " + line);
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return highScores;


    }

    public static String readHighScoresasString(String filename) {
        StringBuilder highScoreText = new StringBuilder();
        int linesRead = 0;
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null && linesRead < 5) {
                linesRead++;
                highScoreText.append(linesRead).append(". ").append(line).append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return highScoreText.toString();
    }




    public static void main(String[] args) {
        String filename = "highScores.txt"; // file path to high scores

        //  Get scores as a list for logic to add new high scores
        List<Integer> scoresList = readHighScoresAsList(filename);


        //  Get scores as a formatted string for high score screen
        String formattedScores = readHighScoresasString(filename);

    }

}
