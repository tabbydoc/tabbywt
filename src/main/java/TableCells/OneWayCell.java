package TableCells;

public final  class OneWayCell extends Cell {

    private Cell attribute;// optional
    private int record; //optional

    public OneWayCell( int rows, int columns) {
        super(rows,columns );
    }


    public Cell getAttribute() {
        return attribute;
    }

    public void setAttribute(Cell attribute) {
        this.attribute = attribute;
    }

    public int getRecord() {
        return record;
    }

    public void setRecord(int record) {
        this.record = record;
    }
}
