package Conversão;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvException;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class InsertionConquista {
    private static final String INPUT_CSV = "./archive/games.csv";
    private static final String OUTPUT_DIR = "./Arquivos Gerados Conquista/"; 

    public static void main(String[] args) throws IOException, CsvException {
        List<String[]> rows = readCsv(INPUT_CSV);
        List<Game> games = convertToGames(rows);
       
        insertionSort(games);
        writeCsv(games, "./games_achievements_insertionSort_melhorCaso.csv");

        Collections.shuffle(games);
        insertionSort(games);
        writeCsv(games, "./games_achievements_insertionSort_medioCaso.csv");

        insertionSort(games); 
        Collections.reverse(games); 
        insertionSort(games); 
        writeCsv(games, "./games_achievements_insertionSort_piorCaso.csv");
    }

    private static List<String[]> readCsv(String filePath) throws IOException, CsvException {
        try (CSVReader reader = new CSVReader(new FileReader(filePath))) {
            List<String[]> allRows = reader.readAll();
            allRows.remove(0); 
            return allRows;
        }
    }

    private static void writeCsv(List<Game> games, String fileName) throws IOException {
        String outputFilePath = OUTPUT_DIR + fileName;
        try (CSVWriter writer = new CSVWriter(new FileWriter(outputFilePath))) {
 
            writer.writeNext(new String[]{"AppID", "Name", "Achievements"});
            
            for (Game game : games) {
                writer.writeNext(new String[]{game.appId, game.name, String.valueOf(game.achievements)});
            }
        }
    }

    private static List<Game> convertToGames(List<String[]> rows) {
        List<Game> games = new ArrayList<>();
        for (String[] row : rows) {
            try {
                int achievements = Integer.parseInt(row[25]);
                games.add(new Game(row[0], row[1], achievements));
            } catch (NumberFormatException e) {
                System.out.println("Erro ao converter conquistas para o jogo: " + row[1]);
            }
        }
        return games;
    }

    private static void insertionSort(List<Game> games) {
        for (int i = 1; i < games.size(); i++) {
            Game key = games.get(i);
            int j = i - 1;
           
            while (j >= 0 && games.get(j).achievements < key.achievements) {
                games.set(j + 1, games.get(j));
                j = j - 1;
            }
            games.set(j + 1, key);
        }
    }

    static class Game {
        String appId;
        String name;
        int achievements;

        public Game(String appId, String name, int achievements) {
            this.appId = appId;
            this.name = name;
            this.achievements = achievements;
        }
    }
}

