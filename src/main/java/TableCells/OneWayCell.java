package TableCells;

public final  class OneWayCell extends Cell {

    private int attribute;// optional
    private int record; //optional

    public OneWayCell(int id, int row, int col) {
        super(id,row,col );
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
