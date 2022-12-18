package TableCells;

import TableThings.Table;

import java.util.List;

import static webreduce.data.TableType.RELATION;

public abstract class OneWayCellClissifier {

    // Классифицируем ячейки Relational таблиц
    public static void classifyRelationalCells(List<Table> tables) {
        for (Table table : tables)
        if (table.getType() == RELATION){
            List<OneWayCell> cells = (List<OneWayCell>) table.getCells();
            for (int i = 0; i < cells.size() ; i++) {
                if (cells.get(i).getRow() == 1){

                }
            }
        }
    }

    // Классифицируем ячейки  Entity таблиц
    public static void classifyEntityCells(List<Table> tables){

    }
}
