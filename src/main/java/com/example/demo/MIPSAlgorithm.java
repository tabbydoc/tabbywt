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

    private boolean hasDuplicateRows(Element table[][], int x1, int y1, int x2, int y2){
        if(x1 > x2){
            int t = x1;
            x1 = x2;
            x2 = t;
        }
        if(y1 > y2){
            int t = y1;
            y1 = y2;
            y2 = t;
        }

        for(int i = x1; i <= x2; ++i){
            for(int j = i + 1; j <= x2; ++j) {
                if (table[i] == table[j])
                    return true;
            }
        }
        return false;
    }

    private boolean areColumnsEqual(Element table[][], int c1, int c2, int x1, int x2){
        for(int i = x1; i <= x2; ++i){
            if(table[i][c1] != table[i][c2])
                return false;
        }
        return true;
    }
    private boolean hasDuplicateColumns(Element table[][], int x1, int y1, int x2, int y2){
        if(x1 > x2){
            int t = x1;
            x1 = x2;
            x2 = t;
        }
        if(y1 > y2){
            int t = y1;
            y1 = y2;
            y2 = t;
        }

        for(int i = y1; i <= y2; ++i){
            for(int j = i + 1; j <= y2; ++j) {
                if (areColumnsEqual(table, i, j, x1, x2))
                    return true;
            }
        }
        return false;
    }

    // MIPS алгоритм, переписанный из статьи
    private TableCoordinates search2arr(Element table[][]){
        int rmax = table.length,        // CC4 -- (Rmax, Cmax)
            cmax = table[rmax].length;
        int r1 = 1,     //CC1 -- (r1, c1)
            c1 = 1;
        int r2 = (rmax - 1),  //CC2
            c2 = 1;
        boolean rightflag = false,
                upflag    = false;
        int maxarea = 0;

        while(c2 < cmax && r2 >= r1){
            if(!hasDuplicateRows(table, r2+1, c1, rmax, c2) &&
               !hasDuplicateColumns(table, r1, c2+1, r2-1, cmax))
            {
                --r2;
                upflag = true;
                rightflag = false;
            } else {
                ++c2;
                rightflag = true;
                if(upflag && rightflag){
                    int dataarea = (rmax - r2 + 1) * (cmax - c2 + 1);
                    if(dataarea > maxarea){
                        maxarea = dataarea;
                        // cc2 = (r2, c2)
                    }
                    upflag = false;
                }
            }
        }

        // надеюсь, что оно работает
        return new TableCoordinates(r2, c2);
    }
}
