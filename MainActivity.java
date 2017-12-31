package com.example.rbk.notatnik.git;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;

import com.example.rbk.notatnik.R;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this.getApplicationContext();
        current_date = Calendar.getInstance();
        bt_today = (Button) findViewById(R.id.bt_today);
        bt_settings = (ImageButton) findViewById(R.id.bt_settings);
        lista = (ListView) findViewById(R.id.lista);
        selected_year = Calendar.getInstance();
        selected_year.set(2017,0,1);
    }
    @Override
    protected void onStart(){
    super.onStart();
    current_date.set(2017,2,3);
    initialize_buttons();
    initialize_list(selected_year);
    }

    private void initialize_buttons() {
        bt_today.setText(">>>  "+this.getFormattedDate(date_pattern,current_date)+"  <<<");
        bt_today.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //look for today date on the list then scroll to it and position it at 1/4 of screen height
                for (int i =0;i<lista.getAdapter().getCount();i++)
                {
                    List_entry entry = (List_entry) lista.getAdapter().getItem(i);

                    if(entry.getDate().contains(MainActivity.getFormattedDate(date_pattern,current_date)))
                    {
                        DisplayMetrics metrics = new DisplayMetrics();
                        getWindowManager().getDefaultDisplay().getMetrics(metrics);

                        lista.smoothScrollToPositionFromTop(i,Math.round(metrics.heightPixels/4),200);
                    }
                }


            }
        });
    }


    public static String getFormattedDate(String pattern, Calendar date)
    {
        SimpleDateFormat date_formatter = new SimpleDateFormat(pattern);
        return date_formatter.format(date.getTime());
    }
    public void initialize_list(Calendar selected_year)
    {

        ArrayList<List_entry> list_values = new ArrayList<List_entry>();


        for (int m = 0;m<12;m++) {
            for (int d = 0;d<selected_year.getActualMaximum(Calendar.DAY_OF_MONTH);d++)
            {
                list_values.add(new List_entry(this.getFormattedDate(date_pattern+dayofweek_pattern,selected_year),"notatka"));
                selected_year.roll(Calendar.DAY_OF_MONTH,1);
            }

            selected_year.roll(Calendar.MONTH,1);
        }

        final Custom_listview_adapter adapter = new Custom_listview_adapter(context,android.R.layout.simple_list_item_1,list_values);

        lista.setAdapter(adapter);



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
