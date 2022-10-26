package com.example.demo;

public class Cell {

    private int id;
    private int row;
    private int col;
    private int rowspan;// optional
    private int colspan;// optional
    private String text;
    private String type;
    private String value;// optional
    private String group;// optional
    private String parent; // optional
    private int attribute;// optional
    private int record; //optional


        // создание объекта Cell




    public Cell(int id, int row, int col, int rowspan, int colspan, String text, String type, String value, String group, String parent, int attribute, int record) {
        this.id = id;
        this.row = row;
        this.col = col;
        this.rowspan = rowspan;
        this.colspan = colspan;
        this.text = text;
        this.type = type;
        this.value = value;
        this.group = group;
        this.parent = parent;
        this.attribute = attribute;
        this.record = record;
    }

    public Cell(int id, int row, int col, int rowspan, int colspan, String text, String type, String value, String group, String parent, int attribute) {
        this.id = id;
        this.row = row;
        this.col = col;
        this.rowspan = rowspan;
        this.colspan = colspan;
        this.text = text;
        this.type = type;
        this.value = value;
        this.group = group;
        this.parent = parent;
        this.attribute = attribute;
    }

    public Cell(int id, int row, int col, int rowspan, int colspan, String text, String type, String value, String group, String parent) {
        this.id = id;
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

    public Cell(int id, int row, int col, int rowspan, int colspan, String text, String type, String value, String group) {
        this.id = id;
        this.row = row;
        this.col = col;
        this.rowspan = rowspan;
        this.colspan = colspan;
        this.text = text;
        this.type = type;
        this.value = value;
        this.group = group;
    }

    public Cell(int id, int row, int col, int rowspan, int colspan, String text, String type, String value) {
        this.id = id;
        this.row = row;
        this.col = col;
        this.rowspan = rowspan;
        this.colspan = colspan;
        this.text = text;
        this.type = type;
        this.value = value;
    }

    public Cell(int id, int row, int col, int rowspan, int colspan, String text, String type) {
        this.id = id;
        this.row = row;
        this.col = col;
        this.rowspan = rowspan;
        this.colspan = colspan;
        this.text = text;
        this.type = type;
    }

    public Cell(int id, int row, int col, int rowspan, int colspan, String text) {
        this.id = id;
        this.row = row;
        this.col = col;
        this.rowspan = rowspan;
        this.colspan = colspan;
        this.text = text;
    }

    public Cell(int id, int row, int col, int rowspan, int colspan) {
        this.id = id;
        this.row = row;
        this.col = col;
        this.rowspan = rowspan;
        this.colspan = colspan;
    }

    public Cell(int id, int row, int col) {
        this.id = id;
        this.row = row;
        this.col = col;
    }

    public Cell() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

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

    public String getType() {
        return type;
    }

    public void setType(String type) {
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

    public String getParent() {
        return parent;
    }

    public void setParent(String parent) {
        this.parent = parent;
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
