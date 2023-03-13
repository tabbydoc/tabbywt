package com.example.demo;

import org.jsoup.select.Elements;

import java.util.Arrays;


public  abstract class   FIlter {

    // В этом методе фильтруются таблицы по колличеству колонок, строк и по наличию тега
//    table нутри других таблиц
    public static Elements FilterForTable(Elements elementsTable) {
//        Elements elementsTable = element.getElementsByTag("table"); // Получаем все дочерние элементы table нашего аргумента element
        Elements tablesForUse = new Elements();

        // В этом цикле отбрасываем ненужные таблицы и добавляем нужные в список

        for (int j = 0; j < elementsTable.size(); j++) {

            boolean FilterForRows;
            Elements rows = elementsTable.get(j).getElementsByTag("tr");
            int countRows = rows.size();
            if (countRows > 1) {
                FilterForRows = true;
            } else {
                FilterForRows = false;
            }

            boolean FilterForColumns;
            int[] buf = new int[countRows]; //буферный массив для подсчета количества столбцов в каждой строке
            //считаем количество столбцов
            for (int m = 0; m < buf.length; m++) {
                buf[m] = rows.get(m).getElementsByTag("td").size()
                        + rows.get(m).getElementsByTag("th").size();
            }

            Arrays.sort(buf); // сортируем массив buf, получаем
            int countColumns = buf[buf.length - 1];// переменная с количесвом столбцов
            if (countColumns > 1) {
                FilterForColumns = true;
            } else {
                FilterForColumns = false;
            }

            //переменная с колличеством тэгов table внутри элемента
            int countElementsTable = elementsTable.get(j).getElementsByTag("table").size();

            if (countElementsTable == 1 && FilterForRows == true && FilterForColumns == true) {
                tablesForUse.add(elementsTable.get(j));
            }
        }
        return tablesForUse;
    }
}
