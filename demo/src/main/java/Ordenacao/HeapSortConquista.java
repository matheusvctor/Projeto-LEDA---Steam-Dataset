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

public class HeapSortConquista {
    // Caminho relativo para leitura
    private static final String INPUT_CSV = "./archive/games.csv";
    private static final String OUTPUT_DIR = "./Arquivos Gerados Conquista/";

    public static void main(String[] args) throws IOException, CsvException {
        List<String[]> rows = readCsv(INPUT_CSV);
        List<Game> games = convertToGames(rows);

        heapSort(games);
        writeCsv(games, "./games_achievements_heapSort_melhorCaso.csv");

        // Médio caso: embaralhado
        Collections.shuffle(games);
        heapSort(games);
        writeCsv(games, "./games_achievements_heapSort_medioCaso.csv");

        Collections.sort(games, (a, b) -> Integer.compare(b.achievements, a.achievements)); 
        Collections.reverse(games); 
        heapSort(games);
        writeCsv(games, "./games_achievements_heapSort_piorCaso.csv");
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
                System.err.println("Erro ao converter conquistas para o jogo: " + row[1]);
            }
        }
        return games;
    }

    private static void heapSort(List<Game> games) {
        int n = games.size();

        for (int i = n / 2 - 1; i >= 0; i--)
            heapify(games, n, i);

        for (int i = n - 1; i > 0; i--) {
  
            Collections.swap(games, 0, i);

            heapify(games, i, 0);
        }
    }

    private static void heapify(List<Game> games, int n, int i) {
        int largest = i; 
        int l = 2 * i + 1; 
        int r = 2 * i + 2; 
  
        if (l < n && games.get(l).achievements > games.get(largest).achievements)
            largest = l;


                if (r < n && games.get(r).achievements > games.get(largest).achievements)
                largest = r;
    

            if (largest != i) {
                Collections.swap(games, i, largest);
    
                heapify(games, n, largest);
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
    


