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

import java.util.*;


@SpringBootApplication
@RestController
public class DemoApplication {
    static String url = new String();

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

}

