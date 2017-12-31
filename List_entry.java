package com.example.rbk.notatnik.git;

/**
 * Created by rbk on 31.12.17.
 */

public class List_entry {

    String date;
    String note;

    public List_entry(String date,String note)
    {
        this.date = date;
        this.note = note;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
