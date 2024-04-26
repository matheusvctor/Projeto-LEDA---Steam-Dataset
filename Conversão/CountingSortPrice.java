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

public class CountingSortPrice {
    private static final String INPUT_CSV = "./archive/games.csv";
    private static final String OUTPUT_DIR = "./Arquivos Gerados Preço/";
    
    public static void main(String[] args) throws IOException, CsvException {
        List<String[]> rows = readCsv(INPUT_CSV);
        List<Game> games = convertToGames(rows);

        // Melhor caso: já ordenado
        countingSort(games);
        writeCsv(games, "./games_price_countingSort_melhorCaso.csv");

        // Médio caso: embaralhado
        Collections.shuffle(games);
        countingSort(games);
        writeCsv(games, "./games_price_countingSort_medioCaso.csv");

        // Pior caso: ordenado inversamente
        Collections.reverse(games);
        countingSort(games);
        writeCsv(games, "./games_price_countingSort_piorCaso.csv");
    }

    private static List<String[]> readCsv(String filePath) throws IOException, CsvException {
        try (CSVReader reader = new CSVReader(new FileReader(filePath))) {
            List<String[]> allRows = reader.readAll();
            allRows.remove(0); // Remove the header
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

    private static void countingSort(List<Game> games) {
        if (games.isEmpty()) return;

        double maxPrice = games.stream().mapToDouble(g -> g.price).max().getAsDouble();
        int maxIndex = (int) Math.ceil(maxPrice * 100);

        int[] count = new int[maxIndex + 1];
        for (Game game : games) {
            int index = (int) Math.ceil(game.price * 100);
            count[index]++;
        }

        List<Game> sortedGames = new ArrayList<>(Collections.nCopies(games.size(), null));
        int outputPos = 0;

        for (int i = 0; i < count.length; i++) {
            if (count[i] > 0) {
                for (Game game : games) {
                    if (Math.ceil(game.price * 100) == i) {
                        sortedGames.set(outputPos++, game);
                    }
                }
            }
        }

        for (int i = 0; i < games.size(); i++) {
            games.set(i, sortedGames.get(i));
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

