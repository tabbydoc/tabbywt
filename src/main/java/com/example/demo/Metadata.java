package com.example.demo;

import java.util.ArrayList;
import java.util.List;
import lombok.*;


@Getter
@Setter
public class Metadata {
    private int countRows;
    private int countCols;
    private int countCells;
    private String title;
    private String unit;
    private List<String> notes;
    private String type; // Добавлено, если необходимо

    // Конструктор
    public Metadata(int countRows, int countCols, int countCells, String title, String unit, List<String> notes, String type) {
        this.countRows = countRows;
        this.countCols = countCols;
        this.countCells = countCells;
        this.title = title;
        this.unit = unit;
        this.notes = notes;
        this.type = type;
    }


    @Override
    public String toString() {
        return "Metadata{" +
                "countRows=" + countRows +
                ", countCols=" + countCols +
                ", countCells=" + countCells +
                ", title='" + title + '\'' +
                ", unit='" + unit + '\'' +
                ", notes=" + notes +
                ", type='" + type + '\'' +
                '}';
    }
}
