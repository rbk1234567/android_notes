package com.example.rbk.notatnik.git;

import android.graphics.Color;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by rbk on 01.01.18.
 */

public class SettingsSet {
    @JsonProperty("sun_col") private int sundaycolor = Color.RED;
    @JsonProperty("tod_col")private int todaycolor = Color.BLUE;
    @JsonProperty("eve_col")private int everydaycolor = Color.BLACK;
    @JsonProperty("date_patt")private String date_pattern = "dd/MM/yyyy";
    @JsonProperty("day_patt")private String dayofweek_pattern = " EEEE";


    public SettingsSet(int sundaycolor,int todaycolor, int everydaycolor, String date_pattern, String dayofweek_pattern)
    {
        this.sundaycolor = sundaycolor;
        this.todaycolor = todaycolor;
        this.everydaycolor = everydaycolor;
        this.date_pattern = date_pattern;
        this.dayofweek_pattern = dayofweek_pattern;
    }

    public int getSundaycolor() {
        return sundaycolor;
    }

    public void setSundaycolor(int sundaycolor) {
        this.sundaycolor = sundaycolor;
    }

    public int getTodaycolor() {
        return todaycolor;
    }

    public void setTodaycolor(int todaycolor) {
        this.todaycolor = todaycolor;
    }

    public int getEverydaycolor() {
        return everydaycolor;
    }

    public void setEverydaycolor(int everydaycolor) {
        this.everydaycolor = everydaycolor;
    }

    public String getDate_pattern() {
        return date_pattern;
    }

    public void setDate_pattern(String date_pattern) {
        this.date_pattern = date_pattern;
    }

    public String getDayofweek_pattern() {
        return dayofweek_pattern;
    }

    public void setDayofweek_pattern(String dayofweek_pattern) {
        this.dayofweek_pattern = dayofweek_pattern;
    }
}
