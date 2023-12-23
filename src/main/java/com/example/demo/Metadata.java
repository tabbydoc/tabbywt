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

    // Геттеры и сеттеры для всех полей
    public int getCountRows() { return countRows; }
    public void setCountRows(int countRows) { this.countRows = countRows; }

    public int getCountCols() { return countCols; }
    public void setCountCols(int countCols) { this.countCols = countCols; }

    public int getCountCells() { return countCells; }
    public void setCountCells(int countCells) { this.countCells = countCells; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getUnit() { return unit; }
    public void setUnit(String unit) { this.unit = unit; }

    public List<String> getNotes() { return notes; }
    public void setNotes(List<String> notes) { this.notes = notes; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

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
