package TableCells;

import TableThings.Table;

import java.util.ArrayList;
import java.util.List;

import static webreduce.data.TableType.ENTITY;
import static webreduce.data.TableType.RELATION;

public abstract class OneWayCellClissifier {
    public static boolean classify = false;
    // Классифицируем ячейки Relational таблиц
    public static void classifyRelationalCells(List<Table> tables) {

        for (Table table : tables)
            if (table.getType() == RELATION) {
                classify = true;
                List<OneWayCell> cells = (List<OneWayCell>) table.getCells();
                //создадим список ячеек, в которых будут хранится ячейки 1-й строки
                List<OneWayCell> firstCells = new ArrayList<>();
                //добавим атрибуты в 1-ю строку

                for (int i = 0; i < cells.size(); i++) {

                    if (cells.get(i).getRow() == 1) {
                        cells.get(i).setAttribute(cells.get(i));
                        firstCells.add(cells.get(i)); //1-я строка (заголовки) с атрибутами
                    }
                }

                //присваиваем атрибуты клеткам
                for (int i = 0; i < firstCells.size(); i++) {
                    for (int j = 0; j < cells.size() ; j++) {
                        if (cells.get(j).getCol() == firstCells.get(i).getCol()){
                            cells.get(j).setAttribute(firstCells.get(i));
                        }
                    }
                }
// присваиваем record клеткам
                int temp;
                for (int i = 0; i < cells.size(); i++) {
                    temp = cells.get(i).getRow();
                    cells.get(i).setRecord(temp);
                }

            }
        else
                classify = false;
    }

    // Классифицируем ячейки  Entity таблиц
    public static void classifyEntityCells(List<Table> tables) {
        for (Table table : tables)
            if (table.getType() == ENTITY) {
                 classify = true;

                List<OneWayCell> cells = (List<OneWayCell>) table.getCells();
                //создадим список ячеек, в которых будут хранится ячейки 1-го столбца
                List<OneWayCell> firstColCells = new ArrayList<>();
                //добавим атрибуты в 1-ю строку

                for (int i = 0; i < cells.size(); i++) {

                    if (cells.get(i).col == 1) {
                        cells.get(i).setAttribute(cells.get(i));
                        firstColCells.add(cells.get(i)); //1-я строка (заголовки) с атрибутами
                    }
                }

                for (int i = 0; i < firstColCells.size(); i++) {
                    for (int j = 0; j < cells.size() ; j++) {
                        if (cells.get(j).getRow() == firstColCells.get(i).getRow()){
                            cells.get(j).setAttribute(firstColCells.get(i));
                        }
                    }
                }

            }
        else
            classify = false;
    }
}
