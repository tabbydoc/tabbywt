package com.example.demo;

import TableCells.Cell;
import TableCells.OneWayCell;
import TableThings.Table;
import com.example.demo.logic.ApplicationLogic;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public final class ElementToTable {

    public static Table transfer(Element element) {

        Elements rows = element.getElementsByTag("tr");
        int countRows = rows.size();

        // Использование Stream API для подсчета максимального числа столбцов
        int countColumns = rows.stream()
                .mapToInt(row -> row.getElementsByTag("td").size() + row.getElementsByTag("th").size())
                .max()
                .orElse(0);

        // Создание списка с предварительно определенным размером
        List<Cell> oneWayCells = new ArrayList<>(countRows * countColumns);

        // Заполнение списка
        for (int i = 0; i < countRows; i++) {
            for (int j = 0; j < countColumns; j++) {
                oneWayCells.add(new OneWayCell(i, j));
            }
        }

        String htmlCode = element.toString();
        Provenance provenance = new Provenance(ApplicationLogic.descriptor, htmlCode);
        List<String> notes = new ArrayList<>(); // Инициализация с пустым списком вместо null

        Metadata metadata = new Metadata(countRows, countColumns, oneWayCells.size(), "test", "test", notes);

        return new Table(metadata, provenance, oneWayCells);
    }
}


