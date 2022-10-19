package com.example.demo;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;
import java.util.regex.Pattern;

public class TableSearcher {
    // обнаружитель таблиц привязан к своему сайту
    // todo
    URL webpage;

    public TableSearcher(String url) throws MalformedURLException {
        webpage = new URL(url);
    }

    public Table findNextTable(){
        // поиск очередной таблицы на данном сайте?

        return null;
    }

    public int countTables() throws IOException {
        Scanner sc = new Scanner(webpage.openStream());

        int count = 0;

        Pattern p = Pattern.compile("<[tT]able>");
        while(sc.findWithinHorizon(p, 0) != null)
            ++count;

        return count;
    }
}
