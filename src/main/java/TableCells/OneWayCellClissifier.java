package TableCells;

import TableThings.Table;
import com.example.demo.ElementToTable;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import webreduce.data.TableType;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public abstract class OneWayCellClissifier {
    public static void classify(Map<Element, TableType> map) {
        Elements RealationalElements = new Elements();
        Elements EntityElements = new Elements();
        //Заполняем списки для relational и для entity - это получается списки с таблицами
        for (Map.Entry<Element, TableType> entry : map.entrySet()) {
            if (entry.getValue() == TableType.RELATION) {
                RealationalElements.add(entry.getKey());
            } else if (entry.getValue() == TableType.ENTITY) {
                EntityElements.add(entry.getKey());
            }
        }

        // создаем список для Table
        ArrayList<Table> RelationalTables = new ArrayList<>();

        for (Element element : RealationalElements) {
            RelationalTables.add(ElementToTable.transfer(element));
        }

        // отправим некоторые характеристики в ячейки
        for (Table table: RelationalTables){
List<OneWayCell> cells = table.getCells();
            for (OneWayCell oneWayCell: cells) {
                if (oneWayCell.getRow() ==1){

                }
            }
        }
    }
}
