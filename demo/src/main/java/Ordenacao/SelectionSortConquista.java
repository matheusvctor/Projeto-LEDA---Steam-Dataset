package Ordenacao;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvException;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SelectionSortConquista {
    private static final String INPUT_CSV = "./archive/games.csv";
    private static final String OUTPUT_DIR = "./Arquivos Gerados Conquista/";

    public static void main(String[] args) throws IOException, CsvException {
        List<String[]> rows = readCsv(INPUT_CSV);
        List<Game> games = convertToGames(rows);

        selectionSort(games);
        writeCsv(games, "./games_achievements_selectionSort_melhorCaso.csv");

        Collections.shuffle(games);
        selectionSort(games);
        writeCsv(games, "./games_achievements_selectionSort_medioCaso.csv");

        selectionSort(games); 
        Collections.reverse(games); 
        selectionSort(games); 
        writeCsv(games, "./games_achievements_selectionSort_piorCaso.csv");
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

    private static void selectionSort(List<Game> games) {
        for (int i = 0; i < games.size() - 1; i++) {
            int maxIdx = i;
            for (int j = i + 1; j < games.size(); j++) {
                if (games.get(j).achievements > games.get(maxIdx).achievements) {
                    maxIdx = j;
                }
            }
            Game temp = games.get(maxIdx);
            games.set(maxIdx, games.get(i));
            games.set(i, temp);
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

