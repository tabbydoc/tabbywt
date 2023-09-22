package TableCells;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public abstract class Cell {

    protected int row;
    protected int col;
    protected int rowspan; // optional
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
}