package com.example.rbk.notatnik.git;

import android.content.Context;
import android.content.Intent;
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

    static View sunday_bar;
    static View today_bar;
    static View everyday_bar;
    static View datetext_bar;
    static View notetext_bar;
    static Spinner date_spinner;
    static Spinner day_name_spinner;
    static Spinner year_sel_spinner;
    static ImageButton savesettings_button;
    static ImageButton goback_button;
    static Button bt_sunday_pick;
    static Button bt_today_pick;
    static Button bt_everyday_pick;
    static Button bt_date_text_color_pick;
    static Button bt_note_text_color_pick;

    static ArrayList<String> day_name_formats;
    static ArrayList<String> date_formats;
    static ArrayList<String> yearslist;

    SpinnersListener spinners_listener;
    static Boolean userIsInteracting= false;

    static SettingsSet set = new SettingsSet();
    private int COLOR_REQUEST = 3;



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
                setSelectedYear();

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

        bt_sunday_pick = (Button)findViewById(R.id.bt_sunday_pick);
        bt_sunday_pick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent editIntent = new Intent(SettActivity.this, ColorPickActivity.class);
                editIntent.putExtra("for","Sunday");
                editIntent.putExtra("color",set.getSundaycolor());
                SettActivity.this.startActivityForResult(editIntent,COLOR_REQUEST);

            }
        });

        bt_today_pick = (Button)findViewById(R.id.bt_today_pick);
        bt_today_pick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent editIntent = new Intent(SettActivity.this, ColorPickActivity.class);
                editIntent.putExtra("for","Today");
                editIntent.putExtra("color",set.getTodaycolor());
                SettActivity.this.startActivityForResult(editIntent,COLOR_REQUEST);
            }
        });

        bt_everyday_pick = (Button)findViewById(R.id.bt_everyday_pick);
        bt_everyday_pick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent editIntent = new Intent(SettActivity.this, ColorPickActivity.class);
                editIntent.putExtra("for","Everyday");
                editIntent.putExtra("color",set.getEverydaycolor());
                SettActivity.this.startActivityForResult(editIntent,COLOR_REQUEST);
            }
        });

        bt_date_text_color_pick = (Button)findViewById(R.id.bt_date_text_color_pick);
        bt_date_text_color_pick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent editIntent = new Intent(SettActivity.this, ColorPickActivity.class);
                editIntent.putExtra("for","datetext");
                editIntent.putExtra("color",set.getDate_text_color());
                SettActivity.this.startActivityForResult(editIntent,COLOR_REQUEST);
            }
        });

        bt_note_text_color_pick = (Button)findViewById(R.id.bt_note_text_color_pick);
        bt_note_text_color_pick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent editIntent = new Intent(SettActivity.this, ColorPickActivity.class);
                editIntent.putExtra("for","notetext");
                editIntent.putExtra("color",set.getNote_text_color());
                SettActivity.this.startActivityForResult(editIntent,COLOR_REQUEST);
            }
        });


        spinners_listener = new SpinnersListener();

        this.date_spinner = (Spinner) findViewById(R.id.date_spinner);
        this.date_spinner.setOnItemSelectedListener(spinners_listener);
        this.day_name_spinner = (Spinner) findViewById(R.id.day_name_spinner);
        this.day_name_spinner.setOnItemSelectedListener(spinners_listener);


        this.sunday_bar = (View)findViewById(R.id.sunday_bar);
        this.today_bar = (View)findViewById(R.id.today_bar);
        this.everyday_bar = (View)findViewById(R.id.everyday_bar);
        this.datetext_bar = (View)findViewById(R.id.date_text_bar);
        this.notetext_bar = (View)findViewById(R.id.note_text_bar);

        this.year_sel_spinner = (Spinner)findViewById(R.id.year_sel_spinner);
        year_sel_spinner.setOnItemSelectedListener(spinners_listener);

        initialize_spinners_values();
        initialize_spinners();


    }

    private void initialize_spinners_values()
    {

        date_formats = new ArrayList<String>();
        date_formats.add("dd/MM/yyyy");
        date_formats.add("dd.MM.yyyy");
        date_formats.add("dd-MM-yyyy");
        date_formats.add("dd MMM yyyy");
        date_formats.add("dd MMMM yyyy");

        day_name_formats = new ArrayList<String>();
        day_name_formats.add(getResources().getString(R.string.short_day_names));
        day_name_formats.add(getResources().getString(R.string.long_day_names));

        yearslist = new ArrayList<String>();
        for(int i=2018;i<2101;i++)
        {
            yearslist.add(String.valueOf(i));
        }
    }

    private void initialize_spinners()
    //fill spinner selectors with data
    //call preview of colors
    {


        //initialize day format spinner
        ArrayAdapter<String> day_adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item,day_name_formats);
        day_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        day_name_spinner.setAdapter(day_adapter);

        //initialize date format spinner
        ArrayAdapter<String> date_adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item,date_formats);
        date_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        date_spinner.setAdapter(date_adapter);

        //initialize year selection spinner
        ArrayAdapter<String> year_adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,yearslist);
        year_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        year_sel_spinner.setAdapter(year_adapter);

        selectSpinnerInitialValues(set);

        setSundayColorPreview(set.getSundaycolor());
        setTodayColorPreview(set.getTodaycolor());
        setEverydayColorPreview(set.getEverydaycolor());
        setDate_text_colorPreview(set.getDate_text_color());
        setNote_text_colorPreview(set.getNote_text_color());
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

        String date_format = set.getDate_pattern();
        String day_name_format = set.getDayofweek_pattern();
        int selected_year = set.getSelected_year();


        date_spinner.setSelection(getPositionOfDateFormat(date_format));
        day_name_spinner.setSelection(getPositionOfDayName(day_name_format));
        year_sel_spinner.setSelection(getPositionOfYear(selected_year));


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
                if (day_name_formats.get(i).equalsIgnoreCase(getResources().getString(R.string.short_day_names))) {
                    position = i;
                }
            }
        }
        if (dayformat.equalsIgnoreCase(" EEEE"))
        {
            for (int i = 0; i < day_name_formats.size(); i++) {
                if (day_name_formats.get(i).equalsIgnoreCase(getResources().getString(R.string.long_day_names))) {
                    position = i;
                }
            }
        }

        return position;
    }

    private int getPositionOfYear(int year)
    {
        int position=0;
        for(int i = 0; i< yearslist.size(); i++)
        {
            if(yearslist.get(i).equalsIgnoreCase(String.valueOf(set.selected_year)))
            {
                position = i;
            }
        }
        return position;
    }

    public static void setSundayColorPreview(int colorhex) {
        //saves this color to SettingSet variable (can be save to file with SAVE button)
        //shows preview of this color

        sunday_bar.setBackgroundColor(colorhex);
        set.setSundaycolor(colorhex);
        sunday_bar.invalidate();
    }

    public static void setTodayColorPreview(int colorhex) {

        //saves this color to SettingSet variable (can be save to file with SAVE button)
        //shows preview of this color

        today_bar.setBackgroundColor(colorhex);
        set.setTodaycolor(colorhex);
        today_bar.invalidate();
    }
    public static void setEverydayColorPreview(int colorhex) {

        //saves this color to SettingSet variable (can be save to file with SAVE button)
        //shows preview of this color

        everyday_bar.setBackgroundColor(colorhex);
        set.setEverydaycolor(colorhex);
        everyday_bar.invalidate();
    }

    public static void setDate_text_colorPreview(int colorhex)
    {
        datetext_bar.setBackgroundColor(colorhex);
        set.setDate_text_color(colorhex);
        datetext_bar.invalidate();
    }

    public static void setNote_text_colorPreview(int colorhex)
    {
        notetext_bar.setBackgroundColor(colorhex);
        set.setNote_text_color(colorhex);
        notetext_bar.invalidate();
    }

    public static void setDateFormat()
    {   //set settings file date format depending on selected value
        set.setDate_pattern(date_spinner.getSelectedItem().toString());
    }


    public static void setDayNameFormat()
    {
        //set settings file day name format depending on selected value
        String daynameformat = day_name_spinner.getSelectedItem().toString();
        String longnames = getContext().getResources().getString(R.string.long_day_names);
        String shortnames = getContext().getResources().getString(R.string.short_day_names);
        if(daynameformat.equalsIgnoreCase(longnames))
        {
            set.setDayofweek_pattern(" EEEE");
        }
        if(daynameformat.equalsIgnoreCase(shortnames))
        {
            set.setDayofweek_pattern(" EEE");
        }
    }

    public static void setSelectedYear()
    {
        set.setSelected_year(Integer.parseInt(year_sel_spinner.getSelectedItem().toString()));
    }

    public static Context getContext() {
        return settings_context;
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        // Checks the orientation of the screen
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            //Toast.makeText(settings_context, "landscape", Toast.LENGTH_SHORT).show();
            SettActivity.setSundayColorPreview(set.getSundaycolor());
            SettActivity.setTodayColorPreview(set.getTodaycolor());
            SettActivity.setEverydayColorPreview(set.getEverydaycolor());

        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT){
            //Toast.makeText(settings_context, "portrait", Toast.LENGTH_SHORT).show();
            SettActivity.setSundayColorPreview(set.getSundaycolor());
            SettActivity.setTodayColorPreview(set.getTodaycolor());
            SettActivity.setEverydayColorPreview(set.getEverydaycolor());

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent response) {
        // read result of edition window request
        // get edited item position on list
        // if result was ok (save) check wheather response date is the same as list item date for safety
        // save changes to database
        if (requestCode == COLOR_REQUEST)
        {
            // Make sure the request was successful
            if(resultCode == RESULT_OK)
            {

                String day = response.getExtras().get("for").toString();
                int color = Integer.parseInt(response.getExtras().get("color").toString());

                switch (day)
                {
                    case "Sunday":
                        setSundayColorPreview(color);
                        break;
                    case "Today":
                        setTodayColorPreview(color);
                        break;
                    case "Everyday":
                        setEverydayColorPreview(color);
                        break;
                    case "datetext":
                        setDate_text_colorPreview(color);
                        break;
                    case "notetext":
                        setNote_text_colorPreview(color);
                        break;

            }
            }

        }
    }


}
