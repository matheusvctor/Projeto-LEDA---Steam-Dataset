package Ordenacao;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvException;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class QuickSortConquista {
    private static final Path INPUT_CSV = Paths.get("demo/src/main/resources/archive/Formatacoes/games_formatted_release_date.csv");
    private static final Path OUTPUT_DIR = Paths.get("demo/src/main/resources/archive/Arquivos Gerados Conquista/");

    public static void main(String[] args) throws IOException, CsvException {
        List<String[]> rows = readCsv(INPUT_CSV.toString());
        List<Game> games = convertToGames(rows);

        quickSort(games, 0, games.size() - 1);
        writeCsv(games, "games_achievements_quickSort_melhorCaso.csv");

        Collections.shuffle(games);
        quickSort(games, 0, games.size() - 1);
        writeCsv(games, "games_achievements_quickSort_medioCaso.csv");

        quickSort(games, 0, games.size() - 1); 
        Collections.reverse(games); 
        quickSort(games, 0, games.size() - 1); 
        writeCsv(games, "games_achievements_quickSort_piorCaso.csv");
    }

    private static List<String[]> readCsv(String filePath) throws IOException, CsvException {
        try (CSVReader reader = new CSVReader(new FileReader(filePath))) {
            List<String[]> allRows = reader.readAll();
            allRows.remove(0); 
            return allRows;
        }
    }

    private static void writeCsv(List<Game> games, String fileName) throws IOException {
        Path outputFilePath = OUTPUT_DIR.resolve(fileName);
        try (CSVWriter writer = new CSVWriter(new FileWriter(outputFilePath.toString()))) {
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

    private static void quickSort(List<Game> games, int low, int high) {
        if (low < high) {
            int pi = partition(games, low, high);

            quickSort(games, low, pi - 1);
            quickSort(games, pi + 1, high);
        }
    }

    private static int partition(List<Game> games, int low, int high) {
        Game pivot = games.get(high);
        int i = (low - 1);
        for (int j = low; j < high; j++) {
            if (games.get(j).achievements > pivot.achievements) { 
                i++;
                Game temp = games.get(i);
                games.set(i, games.get(j));
                games.set(j, temp);
            }
        }
        Game temp = games.get(i + 1);
        games.set(i + 1, games.get(high));
        games.set(high, temp);
        return i + 1;
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
