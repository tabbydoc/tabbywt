package com.example.demo.logic;

import TableCells.OneWayCellClissifier;
import TableThings.ClassifierErebius;
import TableThings.Discriminator;
import TableThings.Table;
import TableThings.TableClassifier;
import com.example.demo.*;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import webreduce.data.TableType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Controller
public class ApplicationLogic {

    public static String descriptor = new String();

    @GetMapping(value = "/extraction", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public List<Table> extract(@RequestBody JsonInput jsonInput) throws Exception {
        this.descriptor = jsonInput.getDescriptor();

        // Преобразование дескриптора в объект Document
        Document document = Jsoup.parse(descriptor);
        Elements tables = document.getElementsByTag("table");
        Elements tablesForUse = FIlter.FilterForTable(tables);

// отфильтровали ненужные таблицы


// дискриминация
        Map<Element, TableType> discrimenatedTables = new HashMap<>();
        Discriminator discriminator = new DiscriminatorErebius();
        for (Element tableForuse : tablesForUse) {
            discrimenatedTables.put(tableForuse, discriminator.classify(tableForuse));
        }
        // классификация таблиц(есть)
        Map<Element, TableType> bufer = new HashMap<>();
        Map<Element, TableType> classifyedOneWayTables = new HashMap<>();
        TableClassifier classificator = new ClassifierErebius();
        discrimenatedTables.forEach((k, v) -> bufer.put(k, classificator.classify(k)));

        for (Map.Entry<Element, TableType> entry : bufer.entrySet()) {
            if (entry.getValue() == TableType.RELATION || entry.getValue() == TableType.ENTITY) {
                classifyedOneWayTables.put(entry.getKey(), entry.getValue());
            }
        }

//         перевести в Table
        List<Table> tableList = convertToTable(classifyedOneWayTables);

//        классифицировать ячейки в Table

        OneWayCellClissifier.classifyEntityCells(tableList);
        OneWayCellClissifier.classifyRelationalCells(tableList);

        return tableList;

    }

    // Метод конвертации классифицированых таблиц в Table
    public static List<Table> convertToTable(Map<Element, TableType> map) {
        Elements TablesElements = new Elements();
        List<Table> tables = new ArrayList<>();

        //Заполняем списки для relational и для entity - это получается списки с таблицами
        for (Map.Entry<Element, TableType> entry : map.entrySet()) {
            TablesElements.add(entry.getKey());
        }
// Переводим элементную таблицу в класс Table
        for (Element tab : TablesElements) {
            tables.add(ElementToTable.transfer(tab));
        }
        return tables;
    }
}
