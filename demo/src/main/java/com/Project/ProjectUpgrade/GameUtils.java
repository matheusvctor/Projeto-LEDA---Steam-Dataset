package com.Project.ProjectUpgrade;

import java.io.*;
import java.text.ParseException;
import java.util.*;

public class GameUtils {

    public static Game[] readCSV(String filePath) throws IOException, ParseException {
        BufferedReader br = new BufferedReader(new FileReader(filePath));
        String header = br.readLine(); 
        List<Game> gamesList = new ArrayList<>();
        String line;
        while ((line = br.readLine()) != null) {
            String[] values = parseLine(line);
            Game game = new Game(values);
            gamesList.add(game);
        }
        br.close();
        return gamesList.toArray(new Game[0]);
    }

    public static void writeCSV(Game[] games, String filePath) throws IOException {
        BufferedWriter bw = new BufferedWriter(new FileWriter(filePath));
        bw.write("AppID,Name,Release date,Estimated owners,Peak CCU,Required age,Price,DLC count,About the game,Supported languages,Full audio languages,Reviews,Header image,Website,Support url,Support email,Windows,Mac,Linux,Metacritic score,Metacritic url,User score,Positive,Negative,Score rank,Achievements,Recommendations,Notes,Average playtime forever,Average playtime two weeks,Median playtime forever,Median playtime two weeks,Developers,Publishers,Categories,Genres,Tags,Screenshots,Movies\n");
        for (Game game : games) {
            bw.write(game.toCSV() + "\n");
        }
        bw.close();
    }

    private static String[] parseLine(String line) {
        String[] values = new String[39]; 
        StringBuilder sb = new StringBuilder();
        boolean inQuotes = false;
        int index = 0;

        for (char ch : line.toCharArray()) {
            if (ch == '"') {
                inQuotes = !inQuotes;
            } else if (ch == ',' && !inQuotes) {
                values[index++] = sb.toString().trim();
                sb.setLength(0);
            } else {
                sb.append(ch);
            }
        }
        values[index] = sb.toString().trim();

        return values;
    }
}
