package com.igweze.ebi.journalapp.ui.model;

public class Writeup {
    public static final String ITEM_ID = "ITEM_ID";
    public static final String ITEM_TEXT = "ITEM_TEXT";
    public static final String ITEM_TIME = "ITEM_TIME";

    private int id;
    private String time;
    private String text;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
