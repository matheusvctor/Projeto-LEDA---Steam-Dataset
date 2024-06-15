package com.Project;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.csv.CSVParser;

import java.io.Reader;
import java.io.Writer;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class FiltrarParaLinux {
    public static void main(String[] args) {
        String inputFile = "C:/Users/mathe/Downloads/Java Maeven Teste/demo/src/main/resources/archive/games.csv";
        String outputFile = "C:/Users/mathe/Downloads/Java Maeven Teste/demo/src/main/resources/archive/Formatacoes/games_linux.csv";

        int linuxColumnIndex = 18; 

        try (
            Reader reader = new FileReader(inputFile);
            Writer writer = new FileWriter(outputFile);

            CSVParser parser = new CSVParser(reader, CSVFormat.DEFAULT.withFirstRecordAsHeader());

            CSVPrinter printer = new CSVPrinter(writer, CSVFormat.DEFAULT.withHeader(parser.getHeaderNames().toArray(new String[0])))
        ) {
            for (CSVRecord record : parser) {
                String linuxSupport = record.get(linuxColumnIndex);
                if ("True".equalsIgnoreCase(linuxSupport.trim())) {
                    printer.printRecord(record);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
