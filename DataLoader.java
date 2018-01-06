package com.example.rbk.notatnik.git;

import android.os.Environment;
import android.widget.Toast;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.type.TypeFactory;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by rbk on 31.12.17.
 */

public class DataLoader {
    private final String filename = "/note_database.json";
    private final String settingsfilename = "/note_settings.json";
    String root = Environment.getExternalStorageDirectory().toString();
    String savepath = root+filename;
    String settingssavepath = root+settingsfilename;

    public ArrayList<List_entry> getDatabase()
    {

        ArrayList<List_entry> loadeddata = new ArrayList<List_entry>();
        ObjectMapper mapper = new ObjectMapper();

        try {
            loadeddata = mapper.readValue(new FileReader(savepath), TypeFactory.defaultInstance().constructCollectionType(ArrayList.class, List_entry.class));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return loadeddata;
    }

    public void saveDatabase(ArrayList<List_entry> data)
    {


        File file = new File(savepath);
        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        try {
            mapper.writerWithDefaultPrettyPrinter().writeValue(file,data);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void saveSettings(SettingsSet set)
    {

        File file = new File(settingssavepath);
        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        try {
            mapper.writerWithDefaultPrettyPrinter().writeValue(file,set);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public SettingsSet getSettings()
    {

        SettingsSet loadeddata = new SettingsSet();
        ObjectMapper mapper = new ObjectMapper();
        try {
            loadeddata = mapper.readValue(new FileReader(settingssavepath), SettingsSet.class);
        } catch (IOException e) {
            e.printStackTrace();
            //Toast.makeText(SettActivity.getContext(), "Can't load settings from file. Default settings will be used.", Toast.LENGTH_LONG).show();
        }
        return loadeddata;
    }
}
