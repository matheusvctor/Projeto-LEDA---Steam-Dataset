package Conversão;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.text.DateFormat;
import java.text.ParseException;

public class FormatacaoData {
    private static final String INPUT_CSV_FILE = "C:/Users/mathe/Downloads/Projeto Steam Dataset/archive/games.csv";
    private static final String OUTPUT_CSV_FILE = "C:/Users/mathe/Downloads/Projeto Steam Dataset/archive/Formatacoes/games_formatted_release_date.csv";

    public static void main(String[] args) {
        List<String[]> gamesList = new ArrayList<>();
        String line;

        try (BufferedReader br = new BufferedReader(new FileReader(INPUT_CSV_FILE))) {
            while ((line = br.readLine()) != null) {
                String[] game = line.split(",", -1); 
                gamesList.add(game);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        try (PrintWriter pw = new PrintWriter(new FileWriter(OUTPUT_CSV_FILE))) {
            for (String[] game : gamesList) {
                if (game.length > 2) { 
                    game[2] = convertDate(game[2]); 
                }
                pw.println(String.join(",", game)); 
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String convertDate(String dateStr) {
        try {
            DateFormat originalFormat = new SimpleDateFormat("MMM dd, yyyy", Locale.ENGLISH);
            DateFormat targetFormat = new SimpleDateFormat("dd/MM/yyyy");
            Date date = originalFormat.parse(dateStr);
            return targetFormat.format(date);
        } catch (ParseException e) {
            System.err.println("Erro ao converter a data: " + dateStr);
            return dateStr; 
        }
    }
}
