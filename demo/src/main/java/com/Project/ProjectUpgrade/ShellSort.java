package com.Project.ProjectUpgrade;

import java.io.*;
import java.text.ParseException;
import java.util.Comparator;

public class ShellSort {
    public static void main(String[] args) throws IOException, ParseException {
        String inputFile = "demo/src/main/resources/archive/Formatacoes/games_formatted_release_date.csv";
        String outputDir = "demo/src/main/resources/archive/Arquivos Project Upgrade/";

        Game[] games = readCSV(inputFile);


        shellSort(games, Comparator.comparing(Game::getReleaseDate));
        writeCSV(games, outputDir + "sorted_shell_date.csv");


        shellSort(games, Comparator.comparing(Game::getPrice));
        writeCSV(games, outputDir + "sorted_shell_price.csv");


        shellSort(games, Comparator.comparing(Game::getAchievements));
        writeCSV(games, outputDir + "sorted_shell_achievements.csv");
    }

    public static void shellSort(Game[] games, Comparator<Game> comparator) {
        int n = games.length;
        for (int gap = n / 2; gap > 0; gap /= 2) {
            for (int i = gap; i < n; i++) {
                Game temp = games[i];
                int j;
                for (j = i; j >= gap && comparator.compare(games[j - gap], temp) > 0; j -= gap) {
                    games[j] = games[j - gap];
                }
                games[j] = temp;
            }
        }
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
        String[] values = new String[39]; // Assuming the CSV has 39 columns
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


