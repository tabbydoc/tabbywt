package com.example.demo;

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
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import webreduce.data.TableType;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
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
        List<Table> tableList = convertToTable(classifyedOneWayTables);

//        TODO классифицировать ячейки в Table


        // TODO упоковать в map(есть)
        return classifyedOneWayTables;

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

    @GetMapping("/LocalCheking")
    public void LocalChek(@RequestParam(value = "url", defaultValue = "") String url) throws Exception {
        readFile();
    }

    public static void readFile() throws IOException {

        // read a file
        Resource resource = new ClassPathResource("HTMLcheck.html");

        // get inputStream object
        InputStream inputStream = resource.getInputStream();

        // convert inputStream into a byte array
        byte[] dataAsBytes = FileCopyUtils.copyToByteArray(inputStream);

        // convert the byte array into a String
        String data = new String(dataAsBytes, StandardCharsets.UTF_8);


    }
}

