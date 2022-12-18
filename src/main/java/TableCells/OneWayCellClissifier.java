package TableCells;

import TableThings.Table;

import java.util.ArrayList;
import java.util.List;

import static webreduce.data.TableType.ENTITY;
import static webreduce.data.TableType.RELATION;

public abstract class OneWayCellClissifier {

    // Классифицируем ячейки Relational таблиц
    public static void classifyRelationalCells(List<Table> tables) {
        for (Table table : tables)
            if (table.getType() == RELATION) {

                List<OneWayCell> cells = (List<OneWayCell>) table.getCells();
                //создадим список ячеек, в которых будут хранится ячейки 1-й строки
                List<OneWayCell> firstCells = new ArrayList<>();
                //добавим атрибуты в 1-ю строку

                for (int i = 0; i < cells.size(); i++) {

                    if (cells.get(i).getRow() == 1) {
                        cells.get(i).setAttribute(cells.get(i).getId());
                        firstCells.add(cells.get(i)); //1-я строка (заголовки) с атрибутами
                    }
                }

                for (int i = 0; i < firstCells.size(); i++) {
                    for (int j = 0; j < cells.size() ; j++) {
                        if (cells.get(j).getCol() == firstCells.get(i).getCol()){
                            cells.get(j).setAttribute(firstCells.get(i).getId());
                        }
                    }
                }

            }
    }

    // Классифицируем ячейки  Entity таблиц
    public static void classifyEntityCells(List<Table> tables) {
        for (Table table : tables)
            if (table.getType() == ENTITY) {

                List<OneWayCell> cells = (List<OneWayCell>) table.getCells();
                //создадим список ячеек, в которых будут хранится ячейки 1-го столбца
                List<OneWayCell> firstColCells = new ArrayList<>();
                //добавим атрибуты в 1-ю строку

                for (int i = 0; i < cells.size(); i++) {

                    if (cells.get(i).col == 1) {
                        cells.get(i).setAttribute(cells.get(i).getId());
                        firstColCells.add(cells.get(i)); //1-я строка (заголовки) с атрибутами
                    }
                }

                for (int i = 0; i < firstColCells.size(); i++) {
                    for (int j = 0; j < cells.size() ; j++) {
                        if (cells.get(j).getRow() == firstColCells.get(i).getRow()){
                            cells.get(j).setAttribute(firstColCells.get(i).getId());
                        }
                    }
                }

            }
    }
}
