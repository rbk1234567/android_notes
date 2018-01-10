package com.example.rbk.notatnik.git;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.rbk.notatnik.R;

/**
 * Created by rbk on 02.01.18.
 *
 */

public class SpinnersListener implements AdapterView.OnItemSelectedListener {




    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long l) {



        if (SettActivity.userIsInteracting==true) {
            Spinner spinner;
            switch (adapterView.getId()) {


                case R.id.date_spinner:
                    String dateformat = view.getContext().getResources().getString(R.string.date_format_caption);
                    Toast.makeText(SettActivity.getContext(), dateformat +": "+ adapterView.getItemAtPosition(pos).toString(), Toast.LENGTH_SHORT).show();
                    break;

                case R.id.day_name_spinner:
                    String dayformat = view.getContext().getResources().getString(R.string.day_name_format_caption);
                    Toast.makeText(SettActivity.getContext(), dayformat +": "+ adapterView.getItemAtPosition(pos).toString(), Toast.LENGTH_SHORT).show();
                    break;

                case R.id.year_sel_spinner:
                    String selectedyear = view.getContext().getResources().getString(R.string.selected_year_text);

                    Toast.makeText(SettActivity.getContext(), selectedyear +": "+ adapterView.getItemAtPosition(pos).toString(), Toast.LENGTH_SHORT).show();

                    break;

            }
        }




    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

}
