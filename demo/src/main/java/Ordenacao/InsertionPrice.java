package Ordenacao;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvException;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class InsertionPrice {
    private static final String INPUT_CSV = "./archive/games.csv";
    private static final String OUTPUT_DIR = "./Arquivos Gerados Preço/";
    
    public static void main(String[] args) throws IOException, CsvException {
        List<String[]> rows = readCsv(INPUT_CSV);
        List<Game> games = convertToGames(rows);

        insertionSort(games);
        writeCsv(games, "./games_price_insertionSort_melhorCaso.csv");

        Collections.shuffle(games);
        insertionSort(games);
        writeCsv(games, "./games_price_insertionSort_medioCaso.csv");

        Collections.reverse(games);
        insertionSort(games);
        writeCsv(games, "./games_price_insertionSort_piorCaso.csv");
    }

    private static List<String[]> readCsv(String filePath) throws IOException, CsvException {
        try (CSVReader reader = new CSVReader(new FileReader(filePath))) {
            List<String[]> allRows = reader.readAll();
            return allRows.subList(1, allRows.size());  
        }
    }

private static void writeCsv(List<Game> games, String fileName) throws IOException {
    String outputFilePath = OUTPUT_DIR + fileName;

    File directory = new File(OUTPUT_DIR);
    if (!directory.exists()) {
        directory.mkdirs();
    }
    try (CSVWriter writer = new CSVWriter(new FileWriter(outputFilePath))) {
        writer.writeNext(new String[]{"AppID", "Name", "Price"});
        for (Game game : games) {
            writer.writeNext(new String[]{game.appId, game.name, String.format("%.2f", game.price)});
        }
    }
    System.out.println("Arquivo escrito com sucesso: " + outputFilePath);
}

    private static List<Game> convertToGames(List<String[]> rows) {
        List<Game> games = new ArrayList<>();
        for (String[] row : rows) {
            try {
                double price = Double.parseDouble(row[6]); 
                games.add(new Game(row[0], row[1], price));
            } catch (NumberFormatException e) {
                games.add(new Game(row[0], row[1], 0.0));
            }
        }
        return games;
    }

    private static void insertionSort(List<Game> games) {
        for (int i = 1; i < games.size(); i++) {
            Game key = games.get(i);
            int j = i - 1;
            while (j >= 0 && games.get(j).price > key.price) {
                games.set(j + 1, games.get(j));
                j--;
            }
            games.set(j + 1, key);
        }
    }

    static class Game {
        String appId;
        String name;
        double price;

        public Game(String appId, String name, double price) {
            this.appId = appId;
            this.name = name;
            this.price = price;
        }
    }
}


