package com.example.demo;

import com.google.common.base.Optional;
import org.jsoup.nodes.Element;

import org.jsoup.parser.Tag;
import webreduce.extraction.mh.tools.TableConvert;

import java.util.Objects;

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

    public void spanThingy(Element t[][]){
        // дублируем ячейки с параметром col/rowspan
        int ii = t.length;
        int jj = t[0].length;

        //System.out.format("~!~!~ %d %d\n", ii, jj);

        for(int i = 0; i < ii; ++i){
            for(int j = 0; j < jj; ++j){
                if(t[i][j] == null) continue;
                if(t[i][j].hasAttr("rowspan")){
                    int kk = Integer.parseInt(t[i][j].attr("rowspan")) + i;
                    kk = Math.min(kk, ii);
                    for(int k = i+1; k < kk; ++k){
                        t[k][j] = t[i][j].clone();
                        t[k][j].removeAttr("rowspan");
                    }
                }
                if(t[i][j].hasAttr("colspan")){
                    int kk = Integer.parseInt(t[i][j].attr("colspan")) + j;
                    kk = Math.min(kk, jj);
                    for(int k = j+1; k < kk; ++k){
                        t[i][k] = t[i][j].clone();
                        t[i][k].removeAttr("colspan");
                    }
                }
            }
        }
    }

    private boolean areColumnsEqual(Element t[][], int c1, int c2, int y1, int y2){
        for(int i = y1; i <= y2; ++i){
            if(!t[i][c1].text().equals( t[i][c2].text() ))
                return false;
        }
        return true;
    }

    private boolean areRowsEqual(Element t[][], int r1, int r2, int x1, int x2){
        for(int j = x1; j <= x2; ++j){
            if(!t[r1][j].text().equals( t[r2][j].text() ))
                return false;
        }
        return true;
    }

    private boolean hasDuplicateRows(Element table[][], int y1, int x1, int y2, int x2){
        if(x1 > x2){
            int tt = x1;
            x1 = x2;
            x2 = tt;
        }
        if(y1 > y2){
            int tt = y1;
            y1 = y2;
            y2 = tt;
        }

        for(int i = y1; i <= y2; ++i){
            for(int j = i+1; j <= y2; ++j){
                if(areRowsEqual(table, i, j, x1, x2))
                    return true;
            }
        }
        return false;
    }


    private boolean hasDuplicateColumns(Element table[][], int y1, int x1, int y2, int x2){
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
                if (areColumnsEqual(table, i, j, y1, y2))
                    return true;
            }
        }
        return false;
    }

    // MIPS алгоритм, переписанный из статьи
    public TableCoordinates search2arr(Element table[][]){
        spanThingy(table);

        int rmax = table.length - 1,        // CC4 -- (Rmax, Cmax)
            cmax = table[rmax].length - 1;
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
                    int dataArea = (rmax - r2 + 1) * (cmax - c2 + 1);
                    if(dataArea > maxarea){
                        maxarea = dataArea;
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
