package com.example.demo;


import java.util.List;

public class Table {

    private Metadata metaData;
    private Provenance provenance;
    private List<Cell> cells;


    public Table() {
    }


    public Table(Metadata metaData, Provenance provenance, List<Cell> cells) {
        this.metaData = metaData;
        this.provenance = provenance;
        this.cells = cells;
    }

    public Metadata getMetaData() {
        return metaData;
    }

    public Provenance getProvenance() {
        return provenance;
    }

    public List<Cell> getCells() {
        return cells;
    }

    public void setMetaData(Metadata metaData) {
        this.metaData = metaData;
    }

    public void setProvenance(Provenance provenance) {
        this.provenance = provenance;
    }

    public void setCells(List<Cell> cells) {
        this.cells = cells;
    }

    @Override
    public String toString() {
        return "Table{" +
                "metaData=" + metaData +
                ", provenance=" + provenance +
                ", cells=" + cells +
                '}';
    }

}
