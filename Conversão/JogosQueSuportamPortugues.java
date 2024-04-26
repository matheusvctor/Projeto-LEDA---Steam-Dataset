package Conversão;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.IOException;

public class JogosQueSuportamPortugues {

    public static void main(String[] args) {
        String inputFile = "C:/Users/mathe/Downloads/Projeto Steam Dataset/archive/games.csv";
        String outputFile = "C:/Users/mathe/Downloads/Projeto Steam Dataset/archive/Formatacoes/games_portuguese.csv";

        try (BufferedReader reader = new BufferedReader(new FileReader(inputFile));
             PrintWriter writer = new PrintWriter(new FileWriter(outputFile))) {
            
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.contains("'Portuguese'") || line.contains("'Portuguese - Brazil'")) {
                    writer.println(line);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}




