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

public class HeapPrice{
    private static final String INPUT_CSV = "./archive/games.csv";
    private static final String OUTPUT_DIR = "./Arquivos Gerados Preço/";

    public static void main(String[] args) throws IOException, CsvException {
        List<String[]> rows = readCsv(INPUT_CSV);
        List<Game> games = convertToGames(rows);
       
        heapSort(games);
        writeCsv(games, "./games_price_heapSort_melhorCaso.csv");

        
        Collections.shuffle(games);
        heapSort(games);
        writeCsv(games, "./games_price_heapSort_medioCaso.csv");

      
        Collections.reverse(games);
        heapSort(games);
        writeCsv(games, "./games_price_heapSort_piorCaso.csv");
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
    // Agora, tente escrever no arquivo
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

    private static void heapSort(List<Game> games) {
        buildHeap(games);
        for (int i = games.size() - 1; i >= 0; i--) {
            swap(games, 0, i);
            heapify(games, i, 0);
        }
    }

    private static void buildHeap(List<Game> games) {
        int n = games.size();
        for (int i = n / 2 - 1; i >= 0; i--) {
            heapify(games, n, i);
        }
    }

    private static void heapify(List<Game> games, int n, int i) {
        int largest = i;
        int l = 2 * i + 1; 
        int r = 2 * i + 2; 

        if (l < n && games.get(l).price > games.get(largest).price) {
            largest = l;
        }

        if (r < n && games.get(r).price > games.get(largest).price) {
            largest = r;
        }

        if (largest != i) {
            swap(games, i, largest);
            heapify(games, n, largest);
        }
    }

    private static void swap(List<Game> games, int i, int j) {
        Game temp = games.get(i);
        games.set(i, games.get(j));
        games.set(j, temp);
    }

    static class Game {
        String appId;
        String name;
        double price;
        public int achievements;

        public Game(String appId, String name, double price) {
            this.appId = appId;
            this.name = name;
            this.price = price;
        }
    }
}

