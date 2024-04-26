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

public class MergeSortConquista{
    private static final String INPUT_CSV = "./archive/games.csv";
    private static final String OUTPUT_DIR = "./Arquivos Gerados Conquista/";

    public static void main(String[] args) throws IOException, CsvException {
        List<String[]> rows = readCsv(INPUT_CSV);
        List<Game> games = convertToGames(rows);

        
        mergeSort(games, 0, games.size() - 1);
        writeCsv(games, "./games_achievements_mergeSort_melhorCaso.csv");

        Collections.shuffle(games);
        mergeSort(games, 0, games.size() - 1);
        writeCsv(games, "./games_achievements_mergeSort_medioCaso.csv");

        mergeSort(games, 0, games.size() - 1); 
        Collections.reverse(games); 
        mergeSort(games, 0, games.size() - 1); 
        writeCsv(games, "./games_achievements_mergeSort_piorCaso.csv");
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
            if (L.get(i).achievements >= R.get(j).achievements) {
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
        int achievements;

        public Game(String appId, String name, int achievements) {
            this.appId = appId;
            this.name = name;
            this.achievements = achievements;
        }
    }
}

