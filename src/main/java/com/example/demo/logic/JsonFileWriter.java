package com.example.demo.logic;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.charset.StandardCharsets;
import java.io.IOException;

public class JsonFileWriter {

    public static void writeJsonToFile(String jsonData, String filePath) {
        try {
            // Попытка записи строки jsonData в файл по указанному пути с кодировкой UTF-8
            Files.write(Paths.get(filePath), jsonData.getBytes(StandardCharsets.UTF_8));
            System.out.println("JSON successfully saved to " + filePath);
        } catch (IOException e) {
            System.err.println("Error saving JSON to file: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
