package com.example.demo;

import TableCells.Cell;
import TableCells.OneWayCell;
import TableThings.ClassifierErebius;
import TableThings.Table;
import TableThings.TableClassifier;
import com.example.demo.logic.ApplicationLogic;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class ElementToTable {

    public static Table transfer(Element element) {

        String title = extractTitle(element);
        String unit = extractUnit(element);
        String type = extractType(element);

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

        // Использование обновленного конструктора Metadata
        Metadata metadata = new Metadata(countRows, countColumns, oneWayCells.size(), title, unit, notes, type);

        Table table = new Table(metadata, provenance, oneWayCells);
        table.setType(new ClassifierErebius().classify(element));

        return table;
    }

    // Логика для извлечения заголовка таблицы
    private static String extractTitle(Element element) {
        // Возвращаем пустую строку, если заголовок не найден
        return element.select("caption").isEmpty() ? "" : element.select("caption").first().text();
    }

    // Логика для извлечения единиц измерения
    private static String extractUnit(Element element) {
        // Ищем в заголовках столбцов
        for (Element th : element.select("th")) {
            String headerText = th.text();
            String unit = extractUnitFromText(headerText);
            if (!unit.isEmpty()) {
                return unit;
            }
        }

        // Ищем в первой строке данных
        Element firstRow = element.select("tr").first();
        if (firstRow != null) {
            for (Element td : firstRow.select("td")) {
                String cellText = td.text();
                String unit = extractUnitFromText(cellText);
                if (!unit.isEmpty()) {
                    return unit;
                }
            }
        }

        // Единицы измерения не найдены
        return "";
    }

    private static String extractUnitFromText(String text) {
        // Реализация может включать регулярные выражения или другие методы для извлечения единиц измерения
        // Например, ищем текст в скобках
        Pattern pattern = Pattern.compile("\\((.*?)\\)");
        Matcher matcher = pattern.matcher(text);
        if (matcher.find()) {
            return matcher.group(1); // Возвращает текст в скобках
        }
        return "";
    }

    private static String extractType(Element elementForExtractType) {
        TableClassifier transferClassifier = new ClassifierErebius();
       return transferClassifier.classify(elementForExtractType).toString();
    }

}


