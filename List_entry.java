package com.example.rbk.notatnik.git;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;

/**
 * Created by rbk on 31.12.17.
 */

public class List_entry {

    @JsonProperty("date")String date;
    @JsonProperty("note")String note;

    public List_entry()
    {

    }

    @JsonCreator
    public List_entry(@JsonProperty("date")String date,@JsonProperty("note")String note)
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
