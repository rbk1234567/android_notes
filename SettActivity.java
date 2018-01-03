package com.example.rbk.notatnik.git;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;

import com.example.rbk.notatnik.R;

import java.util.ArrayList;

public class SettActivity extends AppCompatActivity {

    static Spinner sunday_red_spinner;
    static Spinner today_red_spinner;
    static Spinner everyday_red_spinner;
    static Spinner sunday_green_spinner;
    static Spinner today_green_spinner;
    static Spinner everyday_green_spinner;
    static Spinner sunday_blue_spinner;
    static Spinner today_blue_spinner;
    static Spinner everyday_blue_spinner;
    static ProgressBar sunday_bar;
    static ProgressBar today_bar;
    static ProgressBar everyday_bar;
    ArrayList<String> list;
    static Context settings_context;
    SpinnersListener spinners_listener;
    static Boolean userIsInteracting= false;
    int Sunday_color = Color.parseColor("#000000");
    int Today_color = Color.parseColor("#000000");
    int Everyday_color = Color.parseColor("#000000");



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sett);
        settings_context = this.getApplicationContext();
        userIsInteracting= false;



        spinners_listener = new SpinnersListener();
        this.sunday_red_spinner = (Spinner) findViewById(R.id.sunday_red_spinner);
        this.sunday_red_spinner.setOnItemSelectedListener(spinners_listener);

        this.today_red_spinner = (Spinner) findViewById(R.id.today_red_spinner);
        this.today_red_spinner.setOnItemSelectedListener(spinners_listener);

        this.everyday_red_spinner = (Spinner) findViewById(R.id.everyday_red_spinner);
        this.everyday_red_spinner.setOnItemSelectedListener(spinners_listener);

        this.sunday_green_spinner = (Spinner) findViewById(R.id.sunday_green_spinner);
        this.sunday_green_spinner.setOnItemSelectedListener(spinners_listener);

        this.today_green_spinner = (Spinner) findViewById(R.id.today_green_spinner);
        this.today_green_spinner.setOnItemSelectedListener(spinners_listener);

        this.everyday_green_spinner = (Spinner) findViewById(R.id.everyday_green_spinner);
        this.everyday_green_spinner.setOnItemSelectedListener(spinners_listener);

        this.sunday_blue_spinner = (Spinner) findViewById(R.id.sunday_blue_spinner);
        this.sunday_blue_spinner.setOnItemSelectedListener(spinners_listener);

        this.today_blue_spinner = (Spinner) findViewById(R.id.today_blue_spinner);
        this.today_blue_spinner.setOnItemSelectedListener(spinners_listener);

        this.everyday_blue_spinner = (Spinner) findViewById(R.id.everyday_blue_spinner);
        this.everyday_blue_spinner.setOnItemSelectedListener(spinners_listener);

        this.sunday_bar = (ProgressBar)findViewById(R.id.sunday_bar);
        this.today_bar = (ProgressBar)findViewById(R.id.today_bar);
        this.everyday_bar = (ProgressBar)findViewById(R.id.everyday_bar);

        this.list = new ArrayList<String>();
        this.list.add("00");
        this.list.add("11");
        this.list.add("22");
        this.list.add("33");
        this.list.add("44");
        this.list.add("55");
        this.list.add("66");
        this.list.add("77");
        this.list.add("88");
        this.list.add("99");
        this.list.add("AA");
        this.list.add("BB");
        this.list.add("CC");
        this.list.add("DD");
        this.list.add("EE");
        this.list.add("FF");

        initialize_spinners();

    }

    private void initialize_spinners()
    {
        //initialize RGB values spinners
        ArrayAdapter<String> rgb_adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item,list);
        rgb_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sunday_red_spinner.setAdapter(rgb_adapter);
        sunday_green_spinner.setAdapter(rgb_adapter);
        sunday_blue_spinner.setAdapter(rgb_adapter);
        today_red_spinner.setAdapter(rgb_adapter);
        today_green_spinner.setAdapter(rgb_adapter);
        today_blue_spinner.setAdapter(rgb_adapter);
        everyday_red_spinner.setAdapter(rgb_adapter);
        everyday_green_spinner.setAdapter(rgb_adapter);
        everyday_blue_spinner.setAdapter(rgb_adapter);

        //initialize day format spinner
        Spinner day_spinner = (Spinner)findViewById(R.id.day_name_spinner);
        ArrayList<String> day_name_formats = new ArrayList<String>();
        day_name_formats.add("short names");
        day_name_formats.add("long names");
        ArrayAdapter<String> day_adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item,day_name_formats);
        day_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        day_spinner.setAdapter(day_adapter);

        //initialize date format spinner
        Spinner date_spinner = (Spinner)findViewById(R.id.date_spinner);
        ArrayList<String> date_formats = new ArrayList<String>();
        date_formats.add("dd/MM/yyyy");
        date_formats.add("dd.MM.yyyy");
        date_formats.add("dd-MM-yyyy");
        date_formats.add("dd MMM yyyy");
        date_formats.add("dd MMMM yyyy");
        ArrayAdapter<String> date_adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item,date_formats);
        date_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        date_spinner.setAdapter(date_adapter);



        setSundayColorPreview();
        setTodayColorPreview();
        setEverydayColorPreview();
    }
    @Override
    public void onUserInteraction() {
        super.onUserInteraction();
        userIsInteracting = true;
    }

    public static void setSundayColorPreview() {
        String colorhex = ("#"+sunday_red_spinner.getSelectedItem().toString()+sunday_green_spinner.getSelectedItem().toString()+sunday_blue_spinner.getSelectedItem().toString());
        System.out.println(colorhex);
        sunday_bar.setBackgroundColor(Color.parseColor(colorhex));
        sunday_bar.getProgressDrawable().setColorFilter(Color.parseColor(colorhex), PorterDuff.Mode.SRC_IN);
        sunday_bar.invalidate();
    }

    public static void setTodayColorPreview() {
        String colorhex = ("#"+today_red_spinner.getSelectedItem().toString()+today_green_spinner.getSelectedItem().toString()+today_blue_spinner.getSelectedItem().toString());
        System.out.println(colorhex);
        today_bar.setBackgroundColor(Color.parseColor(colorhex));
        today_bar.getProgressDrawable().setColorFilter(Color.parseColor(colorhex), PorterDuff.Mode.SRC_IN);
        today_bar.invalidate();
    }
    public static void setEverydayColorPreview() {
        String colorhex = ("#"+everyday_red_spinner.getSelectedItem().toString()+everyday_green_spinner.getSelectedItem().toString()+everyday_blue_spinner.getSelectedItem().toString());
        System.out.println(colorhex);
        everyday_bar.setBackgroundColor(Color.parseColor(colorhex));
        everyday_bar.getProgressDrawable().setColorFilter(Color.parseColor(colorhex), PorterDuff.Mode.SRC_IN);
        everyday_bar.invalidate();
    }
    public static Context getContext() {
        return settings_context;
    }


}
