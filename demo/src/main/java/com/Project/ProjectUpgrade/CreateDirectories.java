package com.Project.ProjectUpgrade;

import java.io.File;

public class CreateDirectories {
    public static void main(String[] args) {
        String baseDir = "demo/src/main/resources";
        String[] subDirs = {
                "archive",
                "archive/Arquivos Gerados Conquista",
                "archive/Arquivos Project Upgrade",
                "archive/Formatacoes",
                "archive/Arquivos Gerados Preço"
        };

        for (String dir : subDirs) {
            File directory = new File(baseDir + "/" + dir);
            if (!directory.exists()) {
                if (directory.mkdirs()) {
                    System.out.println("Directory " + directory.getPath() + " created successfully.");
                } else {
                    System.out.println("Failed to create directory " + directory.getPath());
                }
            } else {
                System.out.println("Directory " + directory.getPath() + " already exists.");
            }
        }
    }
}
