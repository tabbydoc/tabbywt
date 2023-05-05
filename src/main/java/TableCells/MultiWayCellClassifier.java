package TableCells;

import TableThings.Table;
import webreduce.data.TableType;

import java.util.ArrayList;
import java.util.List;

public class MultiWayCellClassifier {

    // Классифицируем ячейки многосторонних таблиц
    public static void classifyMatrixCells(List<Table> tables) {
        for (Table table : tables) {
            if (table.getType() == TableType.MATRIX) {
                List<MultiWayCell> cells = (List<MultiWayCell>) table.getCells();
                List<MultiWayCell> headerCells = new ArrayList<>();

                // Определение ячеек заголовка (первая строка и первый столбец)
                for (MultiWayCell cell : cells) {
                    if (cell.getRow() == 1 || cell.getCol() == 1) {
                        cell.setHeader(true);
                        headerCells.add(cell);
                    } else {
                        cell.setHeader(false);
                    }
                }

                // Присваиваем атрибуты и значения записей для ячеек данных
                for (MultiWayCell headerCell : headerCells) {
                    for (MultiWayCell cell : cells) {
                        if (!cell.isHeader()) {
                            if (headerCell.getRow() == 1 && cell.getCol() == headerCell.getCol()) {
                                cell.addAttribute(headerCell);
                            } else if (headerCell.getCol() == 1 && cell.getRow() == headerCell.getRow()) {
                                cell.addRecord(headerCell);
                            }
                        }
                    }
                }
            }
        }
    }
}

