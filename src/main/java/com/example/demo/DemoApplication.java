package com.example.demo;

import TableThings.ClassifierErebius;
import TableThings.Discriminator;
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

import java.util.HashMap;
import java.util.Map;


@SpringBootApplication
@RestController
public class DemoApplication {
static  String url = new String();
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
        for(Element tableForuse : tablesForUse)
        {
            discrimenatedTables.put(tableForuse, discriminator.classify(tableForuse));
        }
        //TODO классификация(есть)
        Map<Element, TableType> bufer = new HashMap<>();
        Map<Element, TableType> classifyedOneWayTables = new HashMap<>();
        TableClassifier classificator = new ClassifierErebius();
        discrimenatedTables.forEach((k,v)-> bufer.put(k,classificator.classify(k)));


for(Map.Entry<Element, TableType> entry : bufer.entrySet()) {
    if (entry.getValue() == TableType.RELATION || entry.getValue() == TableType.ENTITY ){
        classifyedOneWayTables.put(entry.getKey(),entry.getValue());
    }
}
// пока получается, что в classifyedOneWayTables лежат ток односторонние таблицы
//        Можно завести отдельную мапу для многосторонних таблиц
        //TODO упоковать в map(есть)
return classifyedOneWayTables;
    }


    @GetMapping("/type")
    public String typeTest(@RequestParam(value = "url", defaultValue = "https://example.com") String url) throws Exception {


        Document doc = Jsoup.connect(url).get(); //document – объект документа представляет HTML DOM.
        //Jsoup – основной класс для подключения URL- адреса и получения строки HTML.
        //метод get () возвращает html запрошенного URL.


        String htmlResource = GetHTMLCode.getHtmlResourceByURL(url, "UTF-8"); //Получаем код со страницы

        Document document = Jsoup.parse(htmlResource);
//Элементс главнй класс jsoup

        //	System.out.println(htmlResource); //Вывод HTML кода в консоль

        Elements tables = document.getElementsByTag("table");
//Elements Наследует ArrayList так шо здесь у нас список тэйблов
        System.out.println("\nРАЗДЕЛЕНИЕ");


        StringBuilder sb = new StringBuilder();
        sb.append(url);
        sb.append("<br>");

        Discriminator discr = new DiscriminatorErebius();
        TableClassifier tclass = new ClassifierErebius();

        for (Element table : tables) {
            TableType type = discr.classify(table);
            if (type == TableType.LAYOUT) {
                sb.append("Layout table<br>");
            } else {
                type = tclass.classify(table);
                sb.append("<br>Type: ");
                sb.append(type);
                sb.append("<br>");
            }
            sb.append(table);
        }

        return sb.toString();

//		int count = doc.getElementsByTag("table").size(); //подсчет колличества тегов <table>
//		return String.format("Кол - во тегов " + count+ "\n" );

    }
}