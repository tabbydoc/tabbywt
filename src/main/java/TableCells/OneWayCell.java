package TableCells;

public final  class OneWayCell extends Cell {

    private int attribute;// optional
    private int record; //optional

    public OneWayCell(int id, int countColumns, int countRows) {
        super(id,countColumns,countRows );
    }


    public int getAttribute() {
        return attribute;
    }

    public void setAttribute(int attribute) {
        this.attribute = attribute;
    }

    public int getRecord() {
        return record;
    }

    public void setRecord(int record) {
        this.record = record;
    }
}
