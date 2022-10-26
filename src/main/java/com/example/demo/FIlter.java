package com.example.demo;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


public abstract class FIlter {


    public static void HasTableInTable(Element element) {
        Elements elements = element.getAllElements(); // Получаем все дочерние элементы нашего аргумента element
        Elements tablesForUse = null;
        // В этом цикле отбрасываем ненужные таблицы и добавляем нуужные в список
        for (int i = 0; i < elements.size() ; i++) {
            if (elements.get(i).getElementsByTag("table").size() == 0){
        tablesForUse.add(elements.get(i));
            }
        }

    }
}