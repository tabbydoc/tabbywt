package TableCells;

import lombok.*;

@Getter
@Setter

public abstract class Cell {

    protected int row;
    protected int col;
    protected int rowspan; // optional объедененные колонки или строки
    protected int colspan; // optional
    protected String text;

    protected enum Type {
        Label,
        Entry,
        Attribute,
        Value
    }

    protected Type type;
    protected String value; // optional
    protected String group; // optional
    protected Integer parent; // optional

    public Cell(int row, int col) {
        this.row = row;
        this.col = col;
    }

    public Cell(int row, int col, String text, Type type) {
        this.row = row;
        this.col = col;
        this.text = text;
        this.type = type;
    }
}
