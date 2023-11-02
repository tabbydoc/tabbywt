package com.example.demo;

import java.util.ArrayList;
import java.util.List;
import lombok.*;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@RequiredArgsConstructor
public class Metadata {

    int countRows;
    int countCols;
    int countCells;
    String title;
    String unit;
    List<String> notes = new  ArrayList<>();



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

}
