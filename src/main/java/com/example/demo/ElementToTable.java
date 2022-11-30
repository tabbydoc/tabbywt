package com.example.demo;

import TableCells.Cell;
import TableCells.OneWayCell;
import TableThings.Table;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public abstract class ElementToTable {

    public static Table transfer(Element element, String urlAddres) {
        // считаем количество строк (нужно дописать для варианта с колспан ровспан)
        Elements rows = element.getElementsByTag("tr");
        int countRows = rows.size(); //переменная с количесвтом строк
        int[] buf = new int[countRows]; //буферная переменная для подсчета количества столбцов в каждой строке

        Stream<String> list = element.toString().lines();

        //считаем количество столбцов
        for (int i = 0; i < buf.length; i++) {

            buf[i] = rows.get(i).getElementsByTag("td").size()
                    + rows.get(i).getElementsByTag("th").size();
        }
        Arrays.sort(buf); // сортируем массив buf, получаем
        int countColumns = buf[buf.length - 1]; // переменная с количесвом столбцов

        //Присваиваем id
        int id = 0;

        // создаем список клеток
        List<Cell> oneWayCells = new ArrayList<>();

        // Добавляем элементы класса Cell в лист, предварительно нужно будет вытрясти
        //текст из ячеек и тип таблицы или чёт вроде того
        for (int i = 0; i < countRows; i++) {
            for (int j = 0; j < countColumns; j++) {
                id+=1;
                oneWayCells.add(new OneWayCell(id,countColumns,countRows));
            }
        }

        //Добавляем данные Provenance
        String htmlCode = element.toString();
        Provenance provenance = new Provenance(urlAddres, htmlCode);

        List<String> notes = null; //Список сносок, нуженбудет в дальнейшем

        //Создаем объект типа Metadata
        Metadata metadata = new Metadata(countRows, countColumns, id, "test", "test", notes);

        return new Table(metadata, provenance, oneWayCells);

    }
}

