package com.example.rbk.notatnik.git;

import android.content.Context;
import android.graphics.Color;
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
                case R.id.sunday_red_spinner:
                    Toast.makeText(SettActivity.getContext(), "SUNDAY RED " + adapterView.getItemAtPosition(pos).toString(), Toast.LENGTH_SHORT).show();
                    SettActivity.setSundayColorPreview();
                    break;
                case R.id.sunday_green_spinner:
                    Toast.makeText(SettActivity.getContext(), "SUNDAY GREEN " + adapterView.getItemAtPosition(pos).toString(), Toast.LENGTH_SHORT).show();
                    SettActivity.setSundayColorPreview();
                    break;
                case R.id.sunday_blue_spinner:
                    Toast.makeText(SettActivity.getContext(), "SUNDAY BLUE " + adapterView.getItemAtPosition(pos).toString(), Toast.LENGTH_SHORT).show();
                    SettActivity.setSundayColorPreview();
                    break;

                case R.id.today_red_spinner:
                    Toast.makeText(SettActivity.getContext(), "TODAY RED " + adapterView.getItemAtPosition(pos).toString(), Toast.LENGTH_SHORT).show();
                    SettActivity.setTodayColorPreview();
                    break;
                case R.id.today_green_spinner:
                    Toast.makeText(SettActivity.getContext(), "TODAY GREEN " + adapterView.getItemAtPosition(pos).toString(), Toast.LENGTH_SHORT).show();
                    SettActivity.setTodayColorPreview();
                    break;
                case R.id.today_blue_spinner:
                    Toast.makeText(SettActivity.getContext(), "TODAY BLUE " + adapterView.getItemAtPosition(pos).toString(), Toast.LENGTH_SHORT).show();
                    SettActivity.setTodayColorPreview();
                    break;

                case R.id.everyday_red_spinner:
                    Toast.makeText(SettActivity.getContext(), "EVERY DAY RED " + adapterView.getItemAtPosition(pos).toString(), Toast.LENGTH_SHORT).show();
                    SettActivity.setEverydayColorPreview();
                    break;
                case R.id.everyday_green_spinner:
                    Toast.makeText(SettActivity.getContext(), "EVERY DAY GREEN " + adapterView.getItemAtPosition(pos).toString(), Toast.LENGTH_SHORT).show();
                    SettActivity.setEverydayColorPreview();
                    break;
                case R.id.everyday_blue_spinner:
                    Toast.makeText(SettActivity.getContext(), "EVERY DAY BLUE " + adapterView.getItemAtPosition(pos).toString(), Toast.LENGTH_SHORT).show();
                    SettActivity.setEverydayColorPreview();
                    break;

                case R.id.date_spinner:
                    Toast.makeText(SettActivity.getContext(), "DATE FORMAT " + adapterView.getItemAtPosition(pos).toString(), Toast.LENGTH_SHORT).show();
                    break;

                case R.id.day_name_spinner:
                    Toast.makeText(SettActivity.getContext(), "DAY NAME FORMAT " + adapterView.getItemAtPosition(pos).toString(), Toast.LENGTH_SHORT).show();
                    break;

            }
        }




    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

}
