package TableThings;

import TableCells.Cell;
import com.example.demo.Metadata;
import com.example.demo.Provenance;
import webreduce.data.TableType;

import java.io.Serializable;
import java.util.List;

public final class Table implements Serializable {

    private TableType type;
    private Metadata metaData;
    private Provenance provenance;
    private List<? extends Cell> Cells;



    public Table(Metadata metaData, Provenance provenance, List<Cell> cells) {
        this.metaData = metaData;
        this.provenance = provenance;
        this.Cells = cells;
    }

    public Metadata getMetaData() {
        return metaData;
    }

    public Provenance getProvenance() {
        return provenance;
    }

    public List<? extends Cell> getCells() {
        return Cells;
    }

    public void setCells(List<? extends Cell> cells) {
        this.Cells = cells;
    }

    public void setMetaData(Metadata metaData) {
        this.metaData = metaData;
    }

    public void setProvenance(Provenance provenance) {
        this.provenance = provenance;
    }

    public TableType getType() {
        return type;
    }

    public void setType(TableType type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Table{" +
                "metaData=" + metaData +
                ", provenance=" + provenance +
                ", Cells=" + Cells +
                '}';
    }
}
