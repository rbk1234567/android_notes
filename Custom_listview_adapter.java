package com.example.rbk.notatnik.git;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.rbk.notatnik.R;

import java.text.DateFormatSymbols;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by rbk on 31.12.17.
 */

public class Custom_listview_adapter extends ArrayAdapter {


    public Custom_listview_adapter(@NonNull Context context, int resource, @NonNull List objects) {
        super(context, resource, objects);

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
            // generate view for list items entries (date and part of note)
    {
        View view = convertView;
        if (view == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(this.getContext());
            view = vi.inflate(R.layout.listview_row, null);
        }

        List_entry entry = (List_entry) getItem(position);

        if (entry != null) {
            TextView TvDate = (TextView) view.findViewById(R.id.list_row_date);
            TextView TvNote = (TextView) view.findViewById(R.id.list_row_note);

            if (TvDate != null) {
                TvDate.setText(MainActivity.getDisplayDate(MainActivity.getDate_pattern()+MainActivity.getDayofweek_pattern(),MainActivity.getCalendarFromFile(entry.getDate())));

                view = getColoredView(TvDate.getText().toString(),view);
            }

            if (TvNote != null) {
                String notetodisplay = entry.getNote();
                try {
                    notetodisplay = notetodisplay.replace("\r\n"," ").replace("\n"," ");
                    notetodisplay = notetodisplay.substring(0, 30);
                    notetodisplay = notetodisplay+"...";
                }
                catch (StringIndexOutOfBoundsException e)
                {
                    e.printStackTrace();
                }
                TvNote.setText(notetodisplay);

            }

        }

        return view;
    }

    private void nothing()
    {

    }

    private Boolean isSunday(String entrydate)
    {
        Boolean result = Boolean.FALSE;

        DateFormatSymbols symbols = new DateFormatSymbols(Locale.getDefault());
        String sundayname = symbols.getShortWeekdays()[1];
        String fullsundayname = symbols.getWeekdays()[1];

        if(entrydate.contains(sundayname)||entrydate.contains(fullsundayname))
        {
            result = Boolean.TRUE;
        }
        return result;
    }

    private Boolean isToday(String entrydate)
    {
        Boolean result = Boolean.FALSE;
        if (entrydate.contains(MainActivity.getDisplayDate(MainActivity.getDate_pattern(),MainActivity.getCurrent_date())))
        {
            result = Boolean.TRUE;
        }
        return result;
    }

    private View getColoredView(String entrydate,View view)
    {
        int everydaycolor = MainActivity.getEverydaycolor();
        int sundaycolor = MainActivity.getSundaycolor();
        int todaycolor = MainActivity.getTodayColor();


        view.setBackgroundColor(everydaycolor);

        if (isSunday(entrydate)==Boolean.TRUE)
        {
            view.setBackgroundColor(sundaycolor);
        }

        if(isToday(entrydate)==Boolean.TRUE)
        {
            view.setBackgroundColor(todaycolor);
        }

        return view;

    }


}
