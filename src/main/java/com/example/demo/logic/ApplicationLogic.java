package com.example.demo.logic;

import TableCells.OneWayCellClassifier;
import TableThings.ClassifierErebius;
import TableThings.Table;
import TableThings.TableClassifier;
import com.example.demo.*;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.web.bind.annotation.*;
import webreduce.data.TableType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController // Определяет класс как контроллер Spring, который может обрабатывать HTTP-запросы
public class ApplicationLogic {

    public static String descriptor = new String(); // Строка для хранения HTML-дескриптора веб-страницы

    @GetMapping("/extraction") // Определяет, что метод должен обрабатывать HTTP GET запросы на "/extraction"
    public void extract(@RequestParam String url) throws Exception { // Метод для извлечения и обработки данных с веб-страницы
        this.descriptor = GetHTMLCode.getHtmlResourceByURL(url, "UTF-8"); // Получение HTML-кода веб-страницы по URL

        // Преобразование HTML-кода в объект Document для дальнейшей обработки
        Document document = Jsoup.parse(descriptor);

        // Извлечение всех таблиц из HTML-документа
        Elements tables = document.getElementsByTag("table");

        // Фильтрация таблиц, выбираются только подходящие для обработки
        Elements tablesForUse = Filter.filterForTable(tables);

        // Инициализация Map для хранения классифицированных таблиц
        Map<Element, TableType> classifiedOneWayTables = new HashMap<>();

        // Инициализация объектов дискриминатора и классификатора
        DiscriminatorErebius discriminator = new DiscriminatorErebius();
        TableClassifier classifier = new ClassifierErebius();

        // Цикл по всем отфильтрованным таблицам для классификации
        for (Element tableForUse : tablesForUse) {
            TableType type = discriminator.classify(tableForUse); // Дискриминация типа таблицы
            if (type == TableType.RELATION || type == TableType.ENTITY) {
                classifiedOneWayTables.put(tableForUse, classifier.classify(tableForUse)); // Классификация типа таблицы
            }
        }

        // Конвертация классифицированных таблиц в объекты типа Table
        List<Table> tableList = convertToTable(classifiedOneWayTables);

        // Классификация ячеек в таблицах
        OneWayCellClassifier.classifyEntityCells(tableList);
        OneWayCellClassifier.classifyRelationalCells(tableList);

        // Вывод информации о таблицах в консоль
        System.out.println("Number of one-way tables: " + tableList.size());
        for (Table table : tableList) {
            System.out.println("Table HTML: " + table.getProvenance().getHtml());
        }
    }

    // Метод для конвертации классифицированных элементов таблиц в объекты Table
    public static List<Table> convertToTable(Map<Element, TableType> map) {
        Elements TablesElements = new Elements();
        List<Table> tables = new ArrayList<>();

        // Добавление всех элементов map в список TablesElements
        for (Map.Entry<Element, TableType> entry : map.entrySet()) {
            TablesElements.add(entry.getKey());
        }

        // Конвертация каждого элемента таблицы в объект Table и добавление его в список tables
        for (Element tab : TablesElements) {
            tables.add(ElementToTable.transfer(tab));
        }
        return tables;
    }
}