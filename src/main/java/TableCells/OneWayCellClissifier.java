package TableCells;

import TableThings.Table;

import java.util.ArrayList;
import java.util.List;

public abstract class OneWayCellClissifier {
    public static void classify(List<ArrayList<Table>> ListTables) {


        // создаем список для таблиц типа Relational Table


        int counter = 0;//счетчик для атрибута

        // отправим характеристики в ячейки
        for (Table table : ListTables.get(0)) {
            List<OneWayCell> cells = (List<OneWayCell>) table.getCells();
            for (OneWayCell oneWayCell : cells) {
                if (oneWayCell.getRow() == 1) {
                    oneWayCell.setAttribute(7000 + counter); // 7000 взял из головы, сомневаюсь, насоклько правильно реализовал присвоение атрибутов
                    counter++;
                }
                if (oneWayCell.getRow() != 1) {
                    oneWayCell.setRecord(oneWayCell.getRow()); // отправили record в строки
                }
            }
        }



        for (Table table : ListTables.get(1)) {
            List<OneWayCell> EntityCells = (List<OneWayCell>) table.getCells();
            for (OneWayCell oneWayCell : EntityCells) {
                if (oneWayCell.getCol() == 1) {
                    oneWayCell.setAttribute(7000 + counter);
                    counter++;
                }
                if (oneWayCell.getRow() != 1) {
                    oneWayCell.setRecord(oneWayCell.getCol()); // отправили record в колнки
                }
            }
        }
    }
}
