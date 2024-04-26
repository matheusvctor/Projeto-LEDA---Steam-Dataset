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

public class MergePrice {
    private static final String INPUT_CSV = "./archive/games.csv";
    private static final String OUTPUT_DIR = "./Arquivos Gerados Preço/";
    
    public static void main(String[] args) throws IOException, CsvException {
        List<String[]> rows = readCsv(INPUT_CSV);
        List<Game> games = convertToGames(rows);

        mergeSort(games, 0, games.size() - 1);
        writeCsv(games, "./games_price_mergeSort_melhorCaso.csv");

        Collections.shuffle(games);
        mergeSort(games, 0, games.size() - 1);
        writeCsv(games, "./games_price_mergeSort_medioCaso.csv");

        Collections.reverse(games);
        mergeSort(games, 0, games.size() - 1);
        writeCsv(games, "./games_price_mergeSort_piorCaso.csv");
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

    private static void mergeSort(List<Game> games, int left, int right) {
        if (left < right) {
            int middle = (left + right) / 2;
            mergeSort(games, left, middle);
            mergeSort(games, middle + 1, right);
            merge(games, left, middle, right);
        }
    }

    private static void merge(List<Game> games, int left, int middle, int right) {
        int n1 = middle - left + 1;
        int n2 = right - middle;

        List<Game> L = new ArrayList<>(n1);
        List<Game> R = new ArrayList<>(n2);

        for (int i = 0; i < n1; ++i)
            L.add(i, games.get(left + i));
        for (int j = 0; j < n2; ++j)
            R.add(j, games.get(middle + 1 + j));

        int i = 0, j = 0;
        int k = left;
        while (i < n1 && j < n2) {
            if (L.get(i).price <= R.get(j).price) {
                games.set(k, L.get(i));
                i++;
            } else {
                games.set(k, R.get(j));
                j++;
            }
            k++;
        }

        while (i < n1) {
            games.set(k, L.get(i));
            i++;
            k++;
        }

        while (j < n2) {
            games.set(k, R.get(j));
            j++;
            k++;
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

