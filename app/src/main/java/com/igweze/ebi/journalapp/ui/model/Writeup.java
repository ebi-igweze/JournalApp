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
    public static final SimpleDateFormat dateFormat = new SimpleDateFormat("EEE MMMM, yyyy hh:mma", Locale.ENGLISH);
    public static final int SINGED_IN = 1;
    public static final int LOGGED_OUT = 0;
    public static final int ACTIVE = 0;
    public static final int IN_ACTIVE = -1;


    @PrimaryKey(autoGenerate = true)
    private int id;
    private Date time;
    private String text;
    private int loggedOut;
    private int active = ACTIVE;

    @Ignore
    public Writeup() {}

    @Ignore
    public Writeup(Date time, String text) {
        this.time = time;
        this.text = text;
    }

    public Writeup(int id, Date time, String text, int loggedOut, int active) {
        this.id = id;
        this.time = time;
        this.text = text;
        this.loggedOut = loggedOut;
        this.active = active;
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

    public int getLoggedOut() {
        return loggedOut;
    }

    public void setLoggedOut(int loggedOut) {
        this.loggedOut = loggedOut;
    }

    public int getActive() {
        return active;
    }

    public void setActive(int active) {
        this.active = active;
    }
}
