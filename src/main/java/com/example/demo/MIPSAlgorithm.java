package com.example.demo;

import com.google.common.base.Optional;
import org.jsoup.nodes.Element;

import webreduce.extraction.mh.tools.TableConvert;

/* находит критические точки в таблице,
 * выделяет квадрат с загаловками (headers, CC2)
 * и с данными (data, CC3)
 */

public class MIPSAlgorithm {
    TableConvert tconv;

    public MIPSAlgorithm(){
        tconv = new TableConvert(2, 2);
    }

    public TableCoordinates search2(Element table){
        Optional<Element[][]> convTable = tconv.toTable(table);

        if(!convTable.isPresent()){
            return new TableCoordinates(0, 0);
        }

        TableCoordinates cc2 = search2arr(convTable.get());
        return cc2;
    }

    private TableCoordinates search2arr(Element convertedTable[][]){
        return new TableCoordinates(0, 0);
    }
}
