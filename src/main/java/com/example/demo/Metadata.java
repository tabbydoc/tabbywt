package com.example.demo;

import java.util.ArrayList;
import java.util.List;

public class Metadata {

    int countRows;
    int countCols;
    int countCells;
    String title;
    String unit;
    List<String> notes = new  ArrayList<>();
    public Metadata(int countRows, int countCols, int countCells, String title, String unit, List<String> notes) {
        this.countRows = countRows;
        this.countCols = countCols;
        this.countCells = countCells;
        this.title = title;
        this.unit = unit;
        this.notes = notes;
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
                '}';
    }


    public Metadata() {
    }

    public void setCountRows(int countRows) {
        this.countRows = countRows;
    }

    public void setCountCols(int countCols) {
        this.countCols = countCols;
    }

    public void setCountCells(int countCells) {
        this.countCells = countCells;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public void setNotes(List<String> notes) {
        this.notes = notes;
    }

    public int getCountRows() {
        return countRows;
    }

    public int getCountCols() {
        return countCols;
    }

    public int getCountCells() {
        return countCells;
    }

    public String getTitle() {
        return title;
    }

    public String getUnit() {
        return unit;
    }

    public List<String> getNotes() {
        return notes;
    }
}
