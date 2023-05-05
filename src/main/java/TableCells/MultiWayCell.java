package TableCells;

import java.util.ArrayList;
import java.util.List;

public class MultiWayCell extends Cell {

    private List<MultiWayCell> attributes;
    private List<MultiWayCell> records;
    private boolean isHeader;

    public MultiWayCell(int row, int col, String text, Type type) {
        super(row, col, text, type);
        attributes = new ArrayList<>();
        records = new ArrayList<>();
    }

    public void addAttribute(MultiWayCell attribute) {
        attributes.add(attribute);
    }

    public void addRecord(MultiWayCell record) {
        records.add(record);
    }

    public List<MultiWayCell> getAttributes() {
        return attributes;
    }

    public List<MultiWayCell> getRecords() {
        return records;
    }

    public boolean isHeader() {
        return isHeader;
    }

    public void setHeader(boolean header) {
        isHeader = header;
    }
}
