package com.example.demo;

import TableCells.OneWayCellClissifier;
import TableThings.ClassifierErebius;
import TableThings.Discriminator;
import TableThings.Table;
import TableThings.TableClassifier;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import webreduce.data.TableType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@SpringBootApplication
@RestController
public class DemoApplication {
    static String url = new String();

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }


    @GetMapping("/extract")
    public Map<Element, TableType> extract(@RequestParam(value = "url", defaultValue = "") String url) throws Exception {
        this.url = url;

        //TODO фильтр (есть)

        Document doc = Jsoup.connect(url).get();
        String htmlResource = GetHTMLCode.getHtmlResourceByURL(url, "UTF-8");
        Document document = Jsoup.parse(htmlResource);
        Elements tables = document.getElementsByTag("table");
        Elements tablesForUse = FIlter.FilterForTable(tables);


        //TODO дискриминация(есть)
        Map<Element, TableType> discrimenatedTables = new HashMap<>();
        Discriminator discriminator = new DiscriminatorErebius();
        for (Element tableForuse : tablesForUse) {
            discrimenatedTables.put(tableForuse, discriminator.classify(tableForuse));
        }
        //TODO классификация таблиц(есть)
        Map<Element, TableType> bufer = new HashMap<>();
        Map<Element, TableType> classifyedOneWayTables = new HashMap<>();
        TableClassifier classificator = new ClassifierErebius();
        discrimenatedTables.forEach((k, v) -> bufer.put(k, classificator.classify(k)));

        for (Map.Entry<Element, TableType> entry : bufer.entrySet()) {
            if (entry.getValue() == TableType.RELATION || entry.getValue() == TableType.ENTITY) {
                classifyedOneWayTables.put(entry.getKey(), entry.getValue());
            }
        }
        // пока получается, что в classifyedOneWayTables лежат ток односторонние таблицы
//        Можно завести отдельную мапу для многосторонних таблиц

//        TODO перевести в Table
        List<ArrayList<Table>> Tables = convertToTable(classifyedOneWayTables);

//        TODO классифицировать ячейки в Table

        OneWayCellClissifier.classify(Tables);


        // TODO упоковать в map(есть)
        return classifyedOneWayTables;

    }

// Метод конвертации классифицированых таблиц в Table
    public static List<ArrayList<Table>> convertToTable(Map<Element, TableType> map) {
        Elements RealationalElements = new Elements();
        Elements EntityElements = new Elements();

        //Заполняем списки для relational и для entity - это получается списки с таблицами
        for (Map.Entry<Element, TableType> entry : map.entrySet()) {
            if (entry.getValue() == TableType.RELATION) {
                RealationalElements.add(entry.getKey());
            } else if (entry.getValue() == TableType.ENTITY) {
                EntityElements.add(entry.getKey());
            }
        }

        // создаем список для таблиц типа Relational Table
        ArrayList<Table> RelationalTables = new ArrayList<>();
        //заполняем RelationalTables
        for (Element element : RealationalElements) {
            RelationalTables.add(ElementToTable.transfer(element));
        }

//создаем список для таблиц типа Entity Table
        ArrayList<Table> EntityTables = new ArrayList<>();
        //заполняем RelationalTables
        for (Element element : EntityElements) {
            EntityTables.add(ElementToTable.transfer(element));
        }
        return List.of(RelationalTables, EntityTables);
    }

}