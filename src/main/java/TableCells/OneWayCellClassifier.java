package TableCells;

import TableThings.Table;
import webreduce.data.TableType;

import java.util.ArrayList;
import java.util.List;

import static webreduce.data.TableType.ENTITY;
import static webreduce.data.TableType.RELATION;

public abstract class OneWayCellClassifier {
    public static boolean classify = false;
    // Классифицируем ячейки Relational таблиц
    public static void classifyRelationalCells(List<Table> tables) {
        for (Table table : tables) {
            if (table.getType() == TableType.RELATION) {
                List<OneWayCell> cells = (List<OneWayCell>) table.getCells();
                List<OneWayCell> firstRowCells = new ArrayList<>();

                // Получение ячеек первой строки и установка их как атрибуты.
                for (OneWayCell cell : cells) {
                    if (cell.getRow() == 1) {
                        cell.setAttribute(cell);
                        firstRowCells.add(cell);
                    }
                }

                // Присвоение атрибутов всем ячейкам
                for (OneWayCell headerCell : firstRowCells) {
                    for (OneWayCell cell : cells) {
                        if (cell.getCol() == headerCell.getCol()) {
                            cell.setAttribute(headerCell);
                        }
                    }
                }

                // Присвоение record всем ячейкам
                for (OneWayCell cell : cells) {
                    cell.setRecord(cell.getRow());
                }
            }
        }
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
