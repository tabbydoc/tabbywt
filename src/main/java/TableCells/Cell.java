package TableCells;

public abstract class Cell {

    protected int row;
    protected int col;
    protected  int rowspan;// optional
    protected  int colspan;// optional
    protected  String text;



    protected enum Type{
        Label,
        Entry,
        Attrubute,
        Value
    }//

    Type type;
    protected  String value;// optional
    protected  String group;// optional
    protected  Integer parent; // optional



    // конструкторы объекта Cell


    public Cell( int row, int col, int rowspan, int colspan, String text, Type type, String value, String group, Integer parent) {
        this.row = row;
        this.col = col;
        this.rowspan = rowspan;
        this.colspan = colspan;
        this.text = text;
        this.type = type;
        this.value = value;
        this.group = group;
        this.parent = parent;
    }

    public Cell( int row, int col, int rowspan, int colspan, String text, Type type, String value, String group) {
        this.row = row;
        this.col = col;
        this.rowspan = rowspan;
        this.colspan = colspan;
        this.text = text;
        this.type =  type;
        this.value = value;
        this.group = group;
    }

    public Cell( int row, int col, int rowspan, int colspan, String text, Type type, String value) {
        this.row = row;
        this.col = col;
        this.rowspan = rowspan;
        this.colspan = colspan;
        this.text = text;
        this.type = type;
        this.value = value;
    }

    public Cell( int row, int col, int rowspan, int colspan, String text, Type type) {
        this.row = row;
        this.col = col;
        this.rowspan = rowspan;
        this.colspan = colspan;
        this.text = text;
        this.type = type;
    }

    public Cell( int row, int col, String text, Type type) {
        this.row = row;
        this.col = col;
        this.text = text;
        this.type = type;
    }

    public Cell( int row, int col) {
        this.row = row;
        this.col = col;
    }

    // Гетеры сетеры




    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getCol() {
        return col;
    }

    public void setCol(int col) {
        this.col = col;
    }

    public int getRowspan() {
        return rowspan;
    }

    public void setRowspan(int rowspan) {
        this.rowspan = rowspan;
    }

    public int getColspan() {
        return colspan;
    }

    public void setColspan(int colspan) {
        this.colspan = colspan;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public Integer getParent() {
        return parent;
    }

    public void setParent(Integer parent) {
        this.parent = parent;
    }

}
