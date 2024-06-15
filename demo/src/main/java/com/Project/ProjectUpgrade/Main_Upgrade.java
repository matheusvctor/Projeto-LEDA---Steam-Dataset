package com.Project.ProjectUpgrade;

import java.io.IOException;
import java.text.ParseException;
import java.util.Comparator;

public class Main_Upgrade {
    public static void main(String[] args) {
        try {
            String inputFile = "demo/src/main/resources/archive/Formatacoes/games_formatted_release_date.csv";
            String outputDir = "demo/src/main/resources/archive/Arquivos Project Upgrade/";

            System.out.println("Executando BucketSort...");
            Game[] games = GameUtils.readCSV(inputFile);

            BucketSort.bucketSort(games, Comparator.comparing(Game::getReleaseDate));
            GameUtils.writeCSV(games, outputDir + "sorted_bucket_date.csv");

            games = GameUtils.readCSV(inputFile);
            BucketSort.bucketSort(games, Comparator.comparing(Game::getPrice));
            GameUtils.writeCSV(games, outputDir + "sorted_bucket_price.csv");

            games = GameUtils.readCSV(inputFile);
            BucketSort.bucketSort(games, Comparator.comparing(Game::getAchievements));
            GameUtils.writeCSV(games, outputDir + "sorted_bucket_achievements.csv");

            System.out.println("Executando ShellSort...");
            games = GameUtils.readCSV(inputFile);

            ShellSort.shellSort(games, Comparator.comparing(Game::getReleaseDate));
            GameUtils.writeCSV(games, outputDir + "sorted_shell_date.csv");

            games = GameUtils.readCSV(inputFile);
            ShellSort.shellSort(games, Comparator.comparing(Game::getPrice));
            GameUtils.writeCSV(games, outputDir + "sorted_shell_price.csv");

            games = GameUtils.readCSV(inputFile);
            ShellSort.shellSort(games, Comparator.comparing(Game::getAchievements));
            GameUtils.writeCSV(games, outputDir + "sorted_shell_achievements.csv");


            System.out.println("Executando HeapSort...");
            games = GameUtils.readCSV(inputFile);


            HeapSort.heapSort(games, Comparator.comparing(Game::getReleaseDate));
            GameUtils.writeCSV(games, outputDir + "sorted_heap_date.csv");


            games = GameUtils.readCSV(inputFile);
            HeapSort.heapSort(games, Comparator.comparing(Game::getPrice));
            GameUtils.writeCSV(games, outputDir + "sorted_heap_price.csv");


            games = GameUtils.readCSV(inputFile);
            HeapSort.heapSort(games, Comparator.comparing(Game::getAchievements));
            GameUtils.writeCSV(games, outputDir + "sorted_heap_achievements.csv");

            System.out.println("Ordenação completa!");

        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }
}
