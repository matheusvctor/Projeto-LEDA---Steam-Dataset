package com.Project;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.text.DateFormat;
import java.text.ParseException;

public class FormatacaoData {
    private static final String INPUT_CSV_FILE = "demo/src/main/resources/archive/games.csv";
    private static final String OUTPUT_CSV_FILE = "demo/src/main/resources/archive/Formatacoes/games_formatted_release_date.csv";

    public static void main(String[] args) {
        List<String[]> gamesList = new ArrayList<>();
        String line;
        String[] header = null;

        // Leitura do arquivo CSV
        try (BufferedReader br = new BufferedReader(new FileReader(INPUT_CSV_FILE))) {
            boolean isFirstLine = true;
            while ((line = br.readLine()) != null) {
                String[] game = splitCSVLine(line);
                if (isFirstLine) {
                    header = game;
                    isFirstLine = false;
                } else {
                    gamesList.add(game);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Escrita no novo arquivo CSV com datas formatadas
        try (PrintWriter pw = new PrintWriter(new FileWriter(OUTPUT_CSV_FILE))) {
            // Escreve o cabeçalho
            if (header != null) {
                pw.println(joinCSVLine(header));
            }
            // Escreve os dados formatados
            for (String[] game : gamesList) {
                if (game.length > 2 && !game[2].isEmpty()) {
                    game[2] = convertDate(game[2]);
                }
                pw.println(joinCSVLine(game));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String convertDate(String dateStr) {
        List<String> formats = Arrays.asList("MMM dd, yyyy", "MMMM d, yyyy", "MMM d, yyyy");
        for (String format : formats) {
            try {
                DateFormat originalFormat = new SimpleDateFormat(format, Locale.ENGLISH);
                DateFormat targetFormat = new SimpleDateFormat("dd/MM/yyyy");
                Date date = originalFormat.parse(dateStr);
                return targetFormat.format(date);
            } catch (ParseException e) {
                // Continue to the next format
            }
        }
        return dateStr;
    }

    private static String[] splitCSVLine(String line) {
        List<String> tokens = new ArrayList<>();
        StringBuilder sb = new StringBuilder();
        boolean inQuotes = false;

        for (char c : line.toCharArray()) {
            switch (c) {
                case '"':
                    inQuotes = !inQuotes;
                    break;
                case ',':
                    if (inQuotes) {
                        sb.append(c);
                    } else {
                        tokens.add(sb.toString().trim());
                        sb.setLength(0);
                    }
                    break;
                default:
                    sb.append(c);
            }
        }
        tokens.add(sb.toString().trim());
        return tokens.toArray(new String[0]);
    }

    private static String joinCSVLine(String[] tokens) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < tokens.length; i++) {
            if (i > 0) {
                sb.append(',');
            }
            String token = tokens[i];
            if (token.contains(",") || token.contains("\"")) {
                sb.append('"').append(token.replace("\"", "\"\"")).append('"');
            } else {
                sb.append(token);
            }
        }
        return sb.toString();
    }
}
