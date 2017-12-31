package com.example.rbk.notatnik.git;

import android.os.Environment;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.type.TypeFactory;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by rbk on 31.12.17.
 */

public class DataLoader {
    private final String filename = "/note_database.json";
    private ArrayList<List_entry> database = null;

    public ArrayList<List_entry> getDatabase()
    {
        File file = new File(filename);
        ObjectMapper mapper = new ObjectMapper();
        try {
            database = mapper.readValue(filename, TypeFactory.defaultInstance().constructCollectionType(ArrayList.class, List_entry.class));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return database;
    }

    public void saveDatabase(ArrayList<List_entry> data)
    {
        String root = Environment.getExternalStorageDirectory().toString();
        System.out.println(root);
        File file = new File(root+filename);
        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        try {
            mapper.writerWithDefaultPrettyPrinter().writeValue(file,data);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
