package com.example.rbk.notatnik.git;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.example.rbk.notatnik.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    public static Context context;
    private static Calendar current_date;
    private static Button bt_today;
    private static ImageButton bt_settings;
    private static ListView lista;
    private static Calendar selected_year;
    private static String date_pattern = "dd/MM/yyyy";
    private static String dayofweek_pattern = " EEEE";
    private static int sundaycolor = Color.parseColor("#f04800");
    private static int todaycolor = Color.parseColor("#691f00");
    private static int everydaycolor = Color.parseColor("#360700");
    private static int date_text_color = Color.parseColor("#ffeeaa");
    private static int note_text_color = Color.parseColor("#eeff99");
    private static int year_set = 2018;
    private static ArrayList<List_entry> database = new ArrayList<List_entry>();
    private static ArrayList<List_entry> list_values = new ArrayList<List_entry>();
    private static Custom_listview_adapter adapter;
    static final int EDIT_REQUEST = 7;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this.getApplicationContext();
        current_date = Calendar.getInstance();
        selected_year = Calendar.getInstance();
        selected_year.set(year_set,0,1);

        bt_today = (Button) findViewById(R.id.bt_today);
        bt_settings = (ImageButton) findViewById(R.id.bt_settings);
        lista = (ListView) findViewById(R.id.lista);
    }

    @Override
    protected void onStart(){
    super.onStart();
    //current_date.set(2018,2,3);
    loadDatabase();
    DataLoader loader = new DataLoader();
    LoadSettings(loader.getSettings());
    initialize_buttons();

    }

    private static void loadDatabase() {
        DataLoader loader = new DataLoader();
        database = loader.getDatabase();
    }

    private void initialize_buttons() {

        bt_today.setText(" >>> "+getResources().getString(R.string.button_today_caption)+" ("+this.getDisplayDate(date_pattern,current_date)+") <<< ");
        bt_today.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //look for today date on the hexlist then scroll to it and position it at 1/4 of screen height
                for (int i =0;i<lista.getAdapter().getCount();i++)
                {
                    List_entry entry = (List_entry) lista.getAdapter().getItem(i);

                    if(entry.getDate().contains(MainActivity.getFileFormatEntryDate(current_date)))
                    {
                        DisplayMetrics metrics = new DisplayMetrics();
                        getWindowManager().getDefaultDisplay().getMetrics(metrics);

                        lista.smoothScrollToPositionFromTop(i,Math.round(metrics.heightPixels/4),200);
                    }
                }


            }
        });

        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                int position = i;

                Intent editIntent = new Intent(MainActivity.this, EditActivity.class);
                editIntent.putExtra("position",i);
                editIntent.putExtra("date", MainActivity.list_values.get(i).getDate());
                editIntent.putExtra("note",MainActivity.list_values.get(i).getNote());

                MainActivity.this.startActivityForResult(editIntent,EDIT_REQUEST);




            }


        });


        bt_settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DataLoader loader = new DataLoader();
                SettingsSet set = new SettingsSet(sundaycolor,todaycolor,everydaycolor,date_text_color,note_text_color,date_pattern,dayofweek_pattern,year_set);
                System.out.println(set.toString());
                //loader.saveSettings(set);

                set = loader.getSettings();
                MainActivity.LoadSettings(set);

                Intent myIntent = new Intent(MainActivity.this, SettActivity.class);
                myIntent.putExtra("key", "nothing"); //Optional parameters
                MainActivity.this.startActivity(myIntent);


            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent response) {
        // read result of edition window request
        // get edited item position on list
        // if result was ok (save) check wheather response date is the same as list item date for safety
        // save changes to database
        if (requestCode == EDIT_REQUEST) {
            // Make sure the request was successful
            if(resultCode == RESULT_OK)
            {

                int position = Integer.parseInt(response.getExtras().get("position").toString());
                String responsenote = response.getExtras().get("note").toString();
                String responsedate = response.getExtras().get("date").toString();


                if(MainActivity.list_values.get(position).getDate().equalsIgnoreCase(responsedate)) {
                    MainActivity.addEntryToDatabase(new List_entry(MainActivity.list_values.get(position).getDate(), responsenote));
                    DataLoader loader = new DataLoader();
                    loader.saveDatabase(database);

                    adapter.notifyDataSetChanged();
                }
            }

        }
    }

    public static void addEntryToDatabase(List_entry entry)
    {
        Boolean alreadyexist = Boolean.FALSE;
       if(database.isEmpty()==Boolean.FALSE)
        {
            for (List_entry e : database) {
                if (e.getDate().matches(entry.getDate())) {
                    e.setNote(entry.getNote());
                    alreadyexist = Boolean.TRUE;
                    break;
                }
            }
            if(alreadyexist == Boolean.FALSE)
            {
                database.add(entry);
            }

        }
        else
       {
           database.add(entry);
       }

    }


    public static String getDisplayDate(String pattern, Calendar date)
    {
        SimpleDateFormat date_formatter = new SimpleDateFormat(pattern);
        return date_formatter.format(date.getTime());
    }

    public static void initialize_list(Calendar selected_year)
    {

        list_values.clear();

        for (int m = 0;m<12;m++) {
            for (int d = 0;d<selected_year.getActualMaximum(Calendar.DAY_OF_MONTH);d++)
            {
                list_values.add(new List_entry(MainActivity.getFileFormatEntryDate(selected_year),""));
                selected_year.roll(Calendar.DAY_OF_MONTH,1);
            }

            selected_year.roll(Calendar.MONTH,1);
        }

        if(database.isEmpty()==Boolean.FALSE) {
            for (int i = 0; i < list_values.size(); i++) {
                for (List_entry e : database) {
                    String ival = list_values.get(i).getDate();
                    String eval = e.getDate();
                    if (ival.matches(eval)) {
                        list_values.get(i).setNote(e.getNote());
                    }
                }

            }
        }

        adapter = new Custom_listview_adapter(context,android.R.layout.simple_list_item_1,list_values);

        lista.setAdapter(adapter);


    }


    public static String getFileFormatEntryDate(Calendar date)
    {
        SimpleDateFormat date_formatter = new SimpleDateFormat("dd.MM.yyyy");
        return date_formatter.format(date.getTime());
    }

    public static Calendar getCalendarFromFile(String formatted_date_string)
    {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat date_formatter = new SimpleDateFormat("dd.MM.yyyy");

        try {
            calendar.setTime(date_formatter.parse(formatted_date_string));
        } catch (ParseException e) {
            e.printStackTrace();
            System.out.println("Parse Error");
        }

        return calendar;
    }

    public static int getSundaycolor() {
        return sundaycolor;
    }

    public static Calendar getCurrent_date() {
        return current_date;
    }

    public static String getDate_pattern() {
        return date_pattern;
    }

    public static String getDayofweek_pattern() {
        return dayofweek_pattern;
    }

    public static int getTodayColor() {
        return todaycolor;
    }

    public static int getEverydaycolor() {
        return everydaycolor;
    }

    public static int getDate_text_color() {
        return date_text_color;
    }

    public static int getNote_text_color() {
        return note_text_color;
    }

    public static void LoadSettings(SettingsSet set)
    {
        sundaycolor = set.getSundaycolor();
        todaycolor = set.getTodaycolor();
        everydaycolor = set.getEverydaycolor();
        date_pattern = set.getDate_pattern();
        date_text_color = set.getDate_text_color();
        note_text_color = set.getNote_text_color();
        dayofweek_pattern = set.getDayofweek_pattern();
        year_set = set.getSelected_year();
        selected_year.set(year_set,0,1);
        initialize_list(selected_year);
    }

    public static void SaveSettings(SettingsSet set) {
        DataLoader loader = new DataLoader();
        loader.saveSettings(set);
    }


}
