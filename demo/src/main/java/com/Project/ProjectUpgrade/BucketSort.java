package com.Project.ProjectUpgrade;

import java.io.*;
import java.text.ParseException;
import java.util.*;

public class BucketSort {
    public static void main(String[] args) throws IOException, ParseException {
        String inputFile = "demo/src/main/resources/archive/Formatacoes/games_formatted_release_date.csv";
        String outputDir = "demo/src/main/resources/archive/Arquivos Project Upgrade/";

        Game[] games = readCSV(inputFile);
    
        bucketSort(games, Comparator.comparing(Game::getReleaseDate));
        writeCSV(games, outputDir + "sorted_bucket_date.csv");

        bucketSort(games, Comparator.comparing(Game::getPrice));
        writeCSV(games, outputDir + "sorted_bucket_price.csv");

        bucketSort(games, Comparator.comparing(Game::getAchievements));
        writeCSV(games, outputDir + "sorted_bucket_achievements.csv");
    }

    public static void bucketSort(Game[] games, Comparator<Game> comparator) {
        if (games.length == 0) return;

        int bucketCount = (int) Math.sqrt(games.length);
        List<List<Game>> buckets = new ArrayList<>(bucketCount);
        for (int i = 0; i < bucketCount; i++) {
            buckets.add(new ArrayList<>());
        }

        double minValue = Double.POSITIVE_INFINITY;
        double maxValue = Double.NEGATIVE_INFINITY;
        for (Game game : games) {
            double value = getComparableValue(game, comparator);
            if (value < minValue) minValue = value;
            if (value > maxValue) maxValue = value;
        }

        for (Game game : games) {
            double value = getComparableValue(game, comparator);
            int bucketIndex = (int) ((value - minValue) / (maxValue - minValue + 1) * bucketCount);
            buckets.get(bucketIndex).add(game);
        }

        int index = 0;
        for (List<Game> bucket : buckets) {
            if (!bucket.isEmpty()) {
                bucket.sort(comparator);
                for (Game game : bucket) {
                    games[index++] = game;
                }
            }
        }
    }

    private static double getComparableValue(Game game, Comparator<Game> comparator) {
        if (comparator == Comparator.comparing(Game::getPrice)) {
            return game.getPrice();
        } else if (comparator == Comparator.comparing(Game::getAchievements)) {
            return game.getAchievements();
        } else if (comparator == Comparator.comparing(Game::getReleaseDate)) {
            return game.getReleaseDate().getTime();
        }
        return 0;
    }

    public static Game[] readCSV(String filePath) throws IOException, ParseException {
        BufferedReader br = new BufferedReader(new FileReader(filePath));
        String header = br.readLine(); // Skip header
        Game[] games = new Game[0];
        String line;
        while ((line = br.readLine()) != null) {
            String[] values = parseLine(line);
            Game game = new Game(values);
            games = append(games, game);
        }
        br.close();
        return games;
    }

    public static void writeCSV(Game[] games, String filePath) throws IOException {
        BufferedWriter bw = new BufferedWriter(new FileWriter(filePath));
        bw.write("AppID,Name,Release date,Estimated owners,Peak CCU,Required age,Price,DLC count,About the game,Supported languages,Full audio languages,Reviews,Header image,Website,Support url,Support email,Windows,Mac,Linux,Metacritic score,Metacritic url,User score,Positive,Negative,Score rank,Achievements,Recommendations,Notes,Average playtime forever,Average playtime two weeks,Median playtime forever,Median playtime two weeks,Developers,Publishers,Categories,Genres,Tags,Screenshots,Movies\n");
        for (Game game : games) {
            bw.write(game.toCSV() + "\n");
        }
        bw.close();
    }

    private static String[] parseLine(String line) {
        String[] values = new String[39]; 
        StringBuilder sb = new StringBuilder();
        boolean inQuotes = false;
        int index = 0;

        for (char ch : line.toCharArray()) {
            if (ch == '"') {
                inQuotes = !inQuotes;
            } else if (ch == ',' && !inQuotes) {
                values[index++] = sb.toString().trim();
                sb.setLength(0);
            } else {
                sb.append(ch);
            }
        }
        values[index] = sb.toString().trim();

        return values;
    }

    private static Game[] append(Game[] array, Game element) {
        Game[] newArray = new Game[array.length + 1];
        System.arraycopy(array, 0, newArray, 0, array.length);
        newArray[array.length] = element;
        return newArray;
    }
}
