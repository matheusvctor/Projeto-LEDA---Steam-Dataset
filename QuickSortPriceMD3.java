package Conversão;

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

public class QuickSortPriceMD3 {
    private static final String INPUT_CSV = "./archive/games.csv";
    private static final String OUTPUT_DIR = "./Arquivos Gerados Preço/";

    public static void main(String[] args) throws IOException, CsvException {
        List<String[]> rows = readCsv(INPUT_CSV);
        List<Game> games = convertToGames(rows);

        quickSort(games, 0, games.size() - 1);
        writeCsv(games, "./games_price_quickSortMediana3_melhorCaso.csv");

        Collections.shuffle(games);
        quickSort(games, 0, games.size() - 1);
        writeCsv(games, "./games_price_quickSortMediana3_medioCaso.csv");

        Collections.reverse(games);
        quickSort(games, 0, games.size() - 1);
        writeCsv(games, "./games_price_quickSortMediana3_piorCaso.csv");
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
                System.out.println("Error parsing price for game: " + row[1]);
            }
        }
        return games;
    }

    private static void quickSort(List<Game> games, int low, int high) {
        if (low < high) {
            int pi = partition(games, low, high);
            quickSort(games, low, pi - 1);
            quickSort(games, pi + 1, high);
        }
    }

    private static int partition(List<Game> games, int low, int high) {

        int middle = low + (high - low) / 2;
        if (games.get(middle).price < games.get(low).price)
            Collections.swap(games, low, middle);
        if (games.get(high).price < games.get(low).price)
            Collections.swap(games, low, high);
        if (games.get(high).price < games.get(middle).price)
            Collections.swap(games, middle, high);

        Game pivot = games.get(middle);
        Collections.swap(games, middle, high);

        int i = (low - 1);
        for (int j = low; j < high; j++) {
            if (games.get(j).price <= pivot.price) {
                i++;
                Collections.swap(games, i, j);
            }
        }
        Collections.swap(games, i + 1, high);
        return i + 1;
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

