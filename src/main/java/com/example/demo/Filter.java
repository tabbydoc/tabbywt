package com.example.demo;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.Arrays;


public final class Filter {

    public static Elements filterForTable(Elements elementsTable) {
        Elements tablesForUse = new Elements();

        for (Element element : elementsTable) {
            Elements rows = element.getElementsByTag("tr");
            int countRows = rows.size();
            boolean filterForRows = countRows > 1;

            int[] columnCounts = new int[countRows];
            for (int m = 0; m < countRows; m++) {
                columnCounts[m] = rows.get(m).getElementsByTag("td").size()
                        + rows.get(m).getElementsByTag("th").size();
            }
            Arrays.sort(columnCounts);
            int countColumns = columnCounts[columnCounts.length - 1];
            boolean filterForColumns = countColumns > 1;

            int nestedTableCount = element.getElementsByTag("table").size();
            boolean isSingleTable = nestedTableCount == 1;

            if (isSingleTable && filterForRows && filterForColumns) {
                tablesForUse.add(element);
            }
        }
        return tablesForUse;
    }
}

