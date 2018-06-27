package com.igweze.ebi.journalapp.ui.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

@Entity(tableName = "writeup")
public class Writeup {
    public static final String ITEM_ID = "ITEM_ID";
    public static final String ITEM_TEXT = "ITEM_TEXT";
    public static final String ITEM_TIME = "ITEM_TIME";
    public static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss", Locale.ENGLISH);


    @PrimaryKey(autoGenerate = true)
    private int id;
    private Date time;
    private String text;

    @Ignore
    public Writeup(Date time, String text) {
        this.time = time;
        this.text = text;
    }

    public Writeup(int id, Date time, String text) {
        this.id = id;
        this.time = time;
        this.text = text;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
