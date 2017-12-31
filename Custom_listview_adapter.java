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
                TvDate.setText(entry.getDate());

                view = getColoredView(entry.getDate(),view);
            }

            if (TvNote != null) {
                TvNote.setText(entry.getNote());

            }

        }

        return view;
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
        if (entrydate.contains(MainActivity.getFormattedDate(MainActivity.getDate_pattern(),MainActivity.getCurrent_date())))
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
