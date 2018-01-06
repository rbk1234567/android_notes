package com.example.rbk.notatnik.git;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.rbk.notatnik.R;

import java.util.ArrayList;

public class SettActivity extends AppCompatActivity {

    static Context settings_context;
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
    static Spinner date_spinner;
    static Spinner day_name_spinner;
    static ImageButton savesettings_button;
    static ImageButton goback_button;
    static ArrayList<String> hexlist;
    static ArrayList<String> day_name_formats;
    static ArrayList<String> date_formats;

    SpinnersListener spinners_listener;
    static Boolean userIsInteracting= false;

    static SettingsSet set = new SettingsSet();




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sett);
        settings_context = this.getApplicationContext();
        userIsInteracting= false;
        set = new DataLoader().getSettings();

        savesettings_button = (ImageButton)findViewById(R.id.bt_save_settings);
        savesettings_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //sets date and day name formats
                //save settings to file
                //reload settings for main window to make changes visible
                //close settings window (back to main window)
                setDateFormat();
                setDayNameFormat();

                MainActivity.SaveSettings(set);
                MainActivity.LoadSettings(set);
                finish();
            }
        });

        goback_button = (ImageButton)findViewById(R.id.goback_button);
        goback_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //close settings window (back to main window)
                finish();
            }
        });

        spinners_listener = new SpinnersListener();

        this.date_spinner = (Spinner) findViewById(R.id.date_spinner);
        this.date_spinner.setOnItemSelectedListener(spinners_listener);
        this.day_name_spinner = (Spinner) findViewById(R.id.day_name_spinner);
        this.day_name_spinner.setOnItemSelectedListener(spinners_listener);

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


        initialize_spinners_values();
        initialize_spinners();

    }

    private void initialize_spinners_values()
    {
        hexlist = new ArrayList<String>();
        hexlist.add("00");
        hexlist.add("11");
        hexlist.add("22");
        hexlist.add("33");
        hexlist.add("44");
        hexlist.add("55");
        hexlist.add("66");
        hexlist.add("77");
        hexlist.add("88");
        hexlist.add("99");
        hexlist.add("AA");
        hexlist.add("BB");
        hexlist.add("CC");
        hexlist.add("DD");
        hexlist.add("EE");
        hexlist.add("FF");

        date_formats = new ArrayList<String>();
        date_formats.add("dd/MM/yyyy");
        date_formats.add("dd.MM.yyyy");
        date_formats.add("dd-MM-yyyy");
        date_formats.add("dd MMM yyyy");
        date_formats.add("dd MMMM yyyy");

        day_name_formats = new ArrayList<String>();
        day_name_formats.add("short names");
        day_name_formats.add("long names");
    }

    private void initialize_spinners()
    //fill spinner selectors with data
    //call preview of colors
    {
        //initialize RGB values spinners
        ArrayAdapter<String> rgb_adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, hexlist);
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
        ArrayAdapter<String> day_adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item,day_name_formats);
        day_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        day_spinner.setAdapter(day_adapter);

        //initialize date format spinner
        Spinner date_spinner = (Spinner)findViewById(R.id.date_spinner);
        ArrayAdapter<String> date_adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item,date_formats);
        date_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        date_spinner.setAdapter(date_adapter);

        selectSpinnerInitialValues(set);

        setSundayColorPreview();
        setTodayColorPreview();
        setEverydayColorPreview();
    }
    @Override
    public void onUserInteraction() {
        //check if the user interact with app to avoid initial spinner values call for onItemSelected method
        super.onUserInteraction();
        userIsInteracting = true;
    }

    private void selectSpinnerInitialValues(SettingsSet set)
            //set initial values for spinner selectors based on loaded SettingsSet
    {
        String SundayHexColor = String.format("#%06X", (0xFFFFFF & set.getSundaycolor()));
        String TodayHexColor = String.format("#%06X", (0xFFFFFF & set.getTodaycolor()));
        String EverydayHexColor = String.format("#%06X", (0xFFFFFF & set.getEverydaycolor()));
        String date_format = set.getDate_pattern();
        String day_name_format = set.getDayofweek_pattern();


        SundayHexColor = SundayHexColor.replace("#","");
        String SundayRed = SundayHexColor.substring(0,2);
        sunday_red_spinner.setSelection(getPositionOfHexColor(SundayRed));
        String SundayGreen = SundayHexColor.substring(2,4);
        sunday_green_spinner.setSelection(getPositionOfHexColor(SundayGreen));
        String SundayBlue = SundayHexColor.substring(4,6);
        sunday_blue_spinner.setSelection(getPositionOfHexColor(SundayBlue));


        TodayHexColor = TodayHexColor.replace("#","");
        String TodayRed = TodayHexColor.substring(0,2);
        today_red_spinner.setSelection(getPositionOfHexColor(TodayRed));
        String TodayGreen = TodayHexColor.substring(2,4);
        today_green_spinner.setSelection(getPositionOfHexColor(TodayGreen));
        String TodayBlue = TodayHexColor.substring(4,6);
        today_blue_spinner.setSelection(getPositionOfHexColor(TodayBlue));

        EverydayHexColor = EverydayHexColor.replace("#","");
        String EverydayRed = EverydayHexColor.substring(0,2);
        everyday_red_spinner.setSelection(getPositionOfHexColor(EverydayRed));
        String EverydayGreen = EverydayHexColor.substring(2,4);
        everyday_green_spinner.setSelection(getPositionOfHexColor(EverydayGreen));
        String EverydayBlue = EverydayHexColor.substring(4,6);
        everyday_blue_spinner.setSelection(getPositionOfHexColor(EverydayBlue));

        date_spinner.setSelection(getPositionOfDateFormat(date_format));
        day_name_spinner.setSelection(getPositionOfDayName(day_name_format));


    }

    private int getPositionOfHexColor(String hexColor)
    //set the initial spinner value for color value
    {
        int position=0;
        for(int i = 0; i< hexlist.size(); i++)
        {
            if(hexlist.get(i).equalsIgnoreCase(hexColor))
            {
                position = i;
            }
        }
        return position;
    }

    private int getPositionOfDateFormat(String dateformat)
    //set the initial spinner value for date format
    {
        int position=0;
        for(int i = 0; i< date_formats.size(); i++)
        {
            if(date_formats.get(i).equalsIgnoreCase(dateformat.toString()))
            {
                position = i;
            }
        }
        return position;
    }

    private int getPositionOfDayName(String dayformat)
    //set the initial spinner value for day name format
    {
        int position=0;
        if (dayformat.equalsIgnoreCase(" EEE"))
        {
            for (int i = 0; i < day_name_formats.size(); i++) {
                if (day_name_formats.get(i).equalsIgnoreCase("short names")) {
                    position = i;
                }
            }
        }
        if (dayformat.equalsIgnoreCase(" EEEE"))
        {
            for (int i = 0; i < day_name_formats.size(); i++) {
                if (day_name_formats.get(i).equalsIgnoreCase("long names")) {
                    position = i;
                }
            }
        }

        return position;
    }

    public static void setSundayColorPreview() {
        //mixes selected RGB values into result color
        //saves this color to SettingSet variable (can be save to file with SAVE button)
        //shows preview of this color
        String colorhex = ("#"+sunday_red_spinner.getSelectedItem().toString()+sunday_green_spinner.getSelectedItem().toString()+sunday_blue_spinner.getSelectedItem().toString());
        System.out.println(colorhex);
        sunday_bar.setBackgroundColor(Color.parseColor(colorhex));
        set.setSundaycolor(Color.parseColor(colorhex));
        sunday_bar.getProgressDrawable().setColorFilter(Color.parseColor(colorhex), PorterDuff.Mode.SRC_IN);
        sunday_bar.invalidate();
    }

    public static void setTodayColorPreview() {
        //mixes selected RGB values into result color
        //saves this color to SettingSet variable (can be save to file with SAVE button)
        //shows preview of this color
        String colorhex = ("#"+today_red_spinner.getSelectedItem().toString()+today_green_spinner.getSelectedItem().toString()+today_blue_spinner.getSelectedItem().toString());
        System.out.println(colorhex);
        today_bar.setBackgroundColor(Color.parseColor(colorhex));
        set.setTodaycolor(Color.parseColor(colorhex));
        today_bar.getProgressDrawable().setColorFilter(Color.parseColor(colorhex), PorterDuff.Mode.SRC_IN);
        today_bar.invalidate();
    }
    public static void setEverydayColorPreview() {
        //mixes selected RGB values into result color
        //saves this color to SettingSet variable (can be save to file with SAVE button)
        //shows preview of this color
        String colorhex = ("#"+everyday_red_spinner.getSelectedItem().toString()+everyday_green_spinner.getSelectedItem().toString()+everyday_blue_spinner.getSelectedItem().toString());
        System.out.println(colorhex);
        everyday_bar.setBackgroundColor(Color.parseColor(colorhex));
        set.setEverydaycolor(Color.parseColor(colorhex));
        everyday_bar.getProgressDrawable().setColorFilter(Color.parseColor(colorhex), PorterDuff.Mode.SRC_IN);
        everyday_bar.invalidate();
    }

    public static void setDateFormat()
    {   //set settings file date format depending on selected value
        set.setDate_pattern(date_spinner.getSelectedItem().toString());
    }


    public static void setDayNameFormat()
    {
        //set settings file day name format depending on selected value
        String daynameformat = day_name_spinner.getSelectedItem().toString();
        if(daynameformat.matches("long names"))
        {
            set.setDayofweek_pattern(" EEEE");
        }
        if(daynameformat.matches("short names"))
        {
            set.setDayofweek_pattern(" EEE");
        }
    }

    public static Context getContext() {
        return settings_context;
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        // Checks the orientation of the screen
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            Toast.makeText(settings_context, "landscape", Toast.LENGTH_SHORT).show();
            SettActivity.setSundayColorPreview();
            SettActivity.setTodayColorPreview();
            SettActivity.setEverydayColorPreview();
            System.out.println("LANDSCAPE");
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT){
            Toast.makeText(settings_context, "portrait", Toast.LENGTH_SHORT).show();
            SettActivity.setSundayColorPreview();
            SettActivity.setTodayColorPreview();
            SettActivity.setEverydayColorPreview();
            System.out.println("PORTRAIT");
        }
    }




}
