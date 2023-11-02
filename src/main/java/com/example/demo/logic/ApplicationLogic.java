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
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import webreduce.data.TableType;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController // Определяет класс как контроллер Spring, который может обрабатывать HTTP-запросы
public class ApplicationLogic {

    public static String descriptor = new String(); // Строка для хранения HTML-дескриптора веб-страницы

    @GetMapping("/extraction") // Определяет, что метод должен обрабатывать HTTP GET запросы на "/extraction"
    public ResponseEntity<String> extract(@RequestParam String url) throws Exception {
        this.descriptor = GetHTMLCode.getHtmlResourceByURL(url, "UTF-8");

        Document document = Jsoup.parse(descriptor);

        Elements tables = document.getElementsByTag("table");

        Elements tablesForUse = Filter.filterForTable(tables);
        Map<Element, TableType> CommonTables = new HashMap<>();

        DiscriminatorErebius discriminator = new DiscriminatorErebius();
        TableClassifier classifier = new ClassifierErebius();


        for (Element tableForUse : tablesForUse) {
            TableType type = discriminator.classify(tableForUse);
                CommonTables.put(tableForUse, classifier.classify(tableForUse));
        }

        List<Table> tableList = convertToTable(CommonTables);
        OneWayCellClassifier.classifyEntityCells(tableList);
        OneWayCellClassifier.classifyRelationalCells(tableList);

        // Создаем HTML ответ
        StringBuilder htmlResponse = new StringBuilder();
        htmlResponse.append("<html><body>");

        for (Map.Entry<Element, TableType> entry : CommonTables.entrySet()) {
            htmlResponse.append("<div>");
            htmlResponse.append("<p>Type: ").append(entry.getValue().toString()).append("</p>");
            htmlResponse.append(entry.getKey().outerHtml());

            // Добавляем выпадающий список для выбора типа таблицы
            htmlResponse.append("<select onchange='updateTableType(this)'>");

            String[] types = {"LAYOUT", "RELATION", "MATRIX", "ENTITY", "OTHER"};
            for (String type : types) {
                htmlResponse.append("<option value='").append(type).append("'");
                if (type.equals(entry.getValue().toString())) {
                    htmlResponse.append(" selected");
                }
                htmlResponse.append(">").append(type).append("</option>");
            }
            htmlResponse.append("</select>");

            htmlResponse.append("</div>");
        }


        htmlResponse.append("</body></html>");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("text", "html", StandardCharsets.UTF_8));



        return new ResponseEntity<>(htmlResponse.toString(), headers, HttpStatus.OK);
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