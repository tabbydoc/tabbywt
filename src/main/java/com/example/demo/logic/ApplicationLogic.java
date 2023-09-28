package com.example.demo.logic;

import TableCells.OneWayCellClassifier;
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
import org.springframework.web.bind.annotation.*;
import webreduce.data.TableType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
public class ApplicationLogic {

    public static String descriptor = new String();

    @GetMapping("/extraction")
    public void extract(@RequestParam String url) throws Exception {
        this.descriptor = GetHTMLCode.getHtmlResourceByURL(url, "UTF-8");

        // Преобразование дескриптора в объект Document
        Document document = Jsoup.parse(descriptor);
        Elements tables = document.getElementsByTag("table");
        Elements tablesForUse = FIlter.FilterForTable(tables);

        Map<Element, TableType> classifiedOneWayTables = new HashMap<>();
        Discriminator discriminator = new DiscriminatorErebius();
        TableClassifier classifier = new ClassifierErebius();

        for (Element tableForUse : tablesForUse) {
            TableType type = discriminator.classify(tableForUse);
            if (type == TableType.RELATION || type == TableType.ENTITY) {
                classifiedOneWayTables.put(tableForUse, classifier.classify(tableForUse));
            }
        }

        List<Table> tableList = convertToTable(classifiedOneWayTables);
        OneWayCellClassifier.classifyEntityCells(tableList);
        OneWayCellClassifier.classifyRelationalCells(tableList);

        // Выводим информацию в консоль
        System.out.println("Number of one-way tables: " + tableList.size());
        for (Table table : tableList) {
            System.out.println("Table HTML: " + table.getProvenance().getHtml());
        }
    }

    // Метод конвертации классифицированных таблиц в Table
    public static List<Table> convertToTable(Map<Element, TableType> map) {
        Elements TablesElements = new Elements();
        List<Table> tables = new ArrayList<>();

        for (Map.Entry<Element, TableType> entry : map.entrySet()) {
            TablesElements.add(entry.getKey());
        }

        for (Element tab : TablesElements) {
            tables.add(ElementToTable.transfer(tab));
        }
        return tables;
    }
}
