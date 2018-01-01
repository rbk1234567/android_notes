package com.example.rbk.notatnik.git;

import android.content.Context;
import android.graphics.Color;
import android.icu.text.LocaleDisplayNames;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;

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
    private static int sundaycolor = Color.RED;
    private static int todaycolor = Color.BLUE;
    private static int everydaycolor = Color.BLACK;
    private static ArrayList<List_entry> database = new ArrayList<List_entry>();
    private static ArrayList<List_entry> list_values = new ArrayList<List_entry>();
    private static Custom_listview_adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this.getApplicationContext();
        current_date = Calendar.getInstance();
        selected_year = Calendar.getInstance();
        selected_year.set(2017,0,1);

        bt_today = (Button) findViewById(R.id.bt_today);
        bt_settings = (ImageButton) findViewById(R.id.bt_settings);
        lista = (ListView) findViewById(R.id.lista);
    }

    @Override
    protected void onStart(){
    super.onStart();
    current_date.set(2017,2,3);
    loadDatabase();
    initialize_list(selected_year);
    initialize_buttons();

    }

    private static void loadDatabase() {
        DataLoader loader = new DataLoader();
        database = loader.getDatabase();
    }

    private void initialize_buttons() {
        bt_today.setText(">>>  "+this.getDisplayDate(date_pattern,current_date)+"  <<<");
        bt_today.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //look for today date on the list then scroll to it and position it at 1/4 of screen height
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

                list_values.get(i).setNote("clicked");

                MainActivity.addEntryToDatabase(new List_entry(MainActivity.list_values.get(i).getDate(), MainActivity.list_values.get(i).getNote()));
                DataLoader loader = new DataLoader();
                loader.saveDatabase(database);

                adapter.notifyDataSetChanged();

            }


        });

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

    public void initialize_list(Calendar selected_year)
    {

        for (int m = 0;m<12;m++) {
            for (int d = 0;d<selected_year.getActualMaximum(Calendar.DAY_OF_MONTH);d++)
            {
                list_values.add(new List_entry(this.getFileFormatEntryDate(selected_year),"notatka"));
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

    public void addEntry(Calendar date, String note)
    {
        database.add(new List_entry(getFileFormatEntryDate(date),note));
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
}
