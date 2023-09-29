package TableCells;

import TableThings.Table;
import webreduce.data.TableType;

import java.util.ArrayList;
import java.util.List;

import static webreduce.data.TableType.ENTITY;
import static webreduce.data.TableType.RELATION;

public abstract class OneWayCellClassifier {
    // Флаг, указывающий была ли обработана хотя бы одна таблица типа ENTITY
    public static boolean classify = false;

    // Метод для классификации ячеек Relational таблиц
    public static void classifyRelationalCells(List<Table> tables) {
        // Вызов обобщенного метода classifyCells для классификации ячеек Relational таблиц
        classifyCells(tables, TableType.RELATION, true);
    }

    // Метод для классификации ячеек Entity таблиц
    public static void classifyEntityCells(List<Table> tables) {
        // Вызов обобщенного метода classifyCells для классификации ячеек Entity таблиц
        classifyCells(tables, TableType.ENTITY, false);
    }

    // Обобщенный метод для классификации ячеек таблиц
    private static void classifyCells(List<Table> tables, TableType type, boolean useRow) {
        // Проход по всем таблицам из списка
        for (Table table : tables) {
            // Если тип таблицы совпадает с заданным типом
            if (table.getType() == type) {
                // Получение списка ячеек таблицы
                List<OneWayCell> cells = (List<OneWayCell>) table.getCells();
                List<OneWayCell> firstCells = new ArrayList<>();

                // Определение ячеек, которые будут использоваться как атрибуты
                for (OneWayCell cell : cells) {
                    // Если мы работаем с Relational таблицами, то используем строки, иначе - колонки
                    int index = useRow ? cell.getRow() : cell.getCol();
                    if (index == 1) { // Если это первая строка или колонка, то устанавливаем ячейку как атрибут
                        cell.setAttribute(cell);
                        firstCells.add(cell); // Добавляем ячейку в список атрибутов
                    }
                }

                for (OneWayCell cell : cells) {
                    cell.setRecord(cell.getRow()); // Установка record для каждой ячейки на основе её строки.

                    for (OneWayCell headerCell : firstCells) {
                        int indexToCompare = useRow ? cell.getCol() : cell.getRow();
                        // Если индекс совпадает с индексом ячейки-атрибута, то устанавливаем атрибут для ячейки
                        if (indexToCompare == headerCell.getCol()) {
                            cell.setAttribute(headerCell);
                        }
                    }
                }


                // Если таблица типа ENTITY, то устанавливаем флаг classify в true
                if (type == TableType.ENTITY) {
                    classify = true;
                }
            }
        }
    }
}

