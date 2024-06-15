package com.Project.ProjectUpgrade;

import java.io.*;
import java.text.ParseException;
import java.util.Comparator;

public class HeapSort {
    public static void main(String[] args) throws IOException, ParseException {
        String inputFile = "demo/src/main/resources/archive/Formatacoes/games_formatted_release_date.csv";
        String outputDir = "demo/src/main/resources/archive/Arquivos Project Upgrade/";

        Game[] games = readCSV(inputFile);

        heapSort(games, Comparator.comparing(Game::getReleaseDate));
        writeCSV(games, outputDir + "sorted_heap_date.csv");

        heapSort(games, Comparator.comparing(Game::getPrice));
        writeCSV(games, outputDir + "sorted_heap_price.csv");

        heapSort(games, Comparator.comparing(Game::getAchievements));
        writeCSV(games, outputDir + "sorted_heap_achievements.csv");
    }

    public static void heapSort(Game[] games, Comparator<Game> comparator) {
        int n = games.length;

        for (int i = n / 2 - 1; i >= 0; i--) {
            heapify(games, n, i, comparator);
        }

        for (int i = n - 1; i > 0; i--) {

            Game temp = games[0];
            games[0] = games[i];
            games[i] = temp;


            heapify(games, i, 0, comparator);
        }
    }

    public static void heapify(Game[] games, int n, int i, Comparator<Game> comparator) {
        int largest = i;
        int left = 2 * i + 1; 
        int right = 2 * i + 2; 

        if (left < n && comparator.compare(games[left], games[largest]) > 0) {
            largest = left;
        }

        if (right < n && comparator.compare(games[right], games[largest]) > 0) {
            largest = right;
        }

        if (largest != i) {
            Game swap = games[i];
            games[i] = games[largest];
            games[largest] = swap;

            heapify(games, n, largest, comparator);
        }
    }

    public static Game[] readCSV(String filePath) throws IOException, ParseException {
        BufferedReader br = new BufferedReader(new FileReader(filePath));
        String header = br.readLine(); 
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
