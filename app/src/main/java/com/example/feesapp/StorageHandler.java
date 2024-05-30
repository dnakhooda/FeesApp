package com.example.feesapp;

import com.example.feesapp.ui.settings.Settings;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class StorageHandler {

    private final MainActivity mainActivity;

    public StorageHandler() {
        this.mainActivity = MainActivity.instance;
    }

    public void clearFiles() {
        File file = mainActivity.getApplication().getFilesDir();
        try {
            // Get File Out Put Streams For Both Text Files & Write Blank Text Into Them
            FileOutputStream fileOutputStreamFees = new FileOutputStream(new File(file, "Fees.txt"));
            FileOutputStream fileOutputStreamSettings = new FileOutputStream(new File(file, "FeeSettings.txt"));
            fileOutputStreamFees.write("".getBytes());
            fileOutputStreamSettings.write("".getBytes());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void saveFees() {
        try {
            // Convert Fees ArrayList Into Json
            Gson gson = new Gson();
            byte[] byteInformation = gson.toJson(mainActivity.getFees()).getBytes();

            // Write Json Bytes Into File
            File file = mainActivity.getApplication().getFilesDir();
            FileOutputStream fileOutputStream = new FileOutputStream(new File(file, "Fees.txt"));
            fileOutputStream.write(byteInformation);
            fileOutputStream.close();
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void getSavedFees() {
        Gson gson = new Gson();
        File fileDirectory = mainActivity.getApplication().getFilesDir();
        File file = new File(fileDirectory, "Fees.txt");
        FileInputStream fileInputStream;
        byte[] bytes = new byte[(int) file.length()];

        try {
            file.createNewFile();
            fileInputStream = new FileInputStream(file);

            // Read Bytes From Text File
            fileInputStream.read(bytes);
            String asString = new String(bytes);
            if (asString.equals(""))
                return;

            // Convert Bytes Into ArrayList & Add Those Fees In The ArrayList To Main Fees ArrayList
            ArrayList<Fee> arrayList = gson.fromJson(asString, new TypeToken<ArrayList<Fee>>(){}.getType());
            mainActivity.getFees().addAll(arrayList);
            fileInputStream.close();
        } catch (IOException e) {throw new RuntimeException(e);}
    }

    public void saveSettings() {
        try {
            Gson gson = new Gson();
            File filesDirectory = mainActivity.getApplication().getFilesDir();
            FileOutputStream fileOutputStream = new FileOutputStream(new File(filesDirectory, "FeeSettings.txt"));

            // Convert Settings Object Into Gson & Write Into File
            fileOutputStream.write(gson.toJson(mainActivity.getSettings()).getBytes());
            fileOutputStream.close();
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void getSavedSettings() {
        Gson gson = new Gson();
        File fileDirectory = mainActivity.getApplication().getFilesDir();

        File file = new File(fileDirectory, "FeeSettings.txt");
        FileInputStream fileInputStream;

        byte[] bytes = new byte[(int) file.length()];

        try {
            file.createNewFile();
            fileInputStream = new FileInputStream(file);
            fileInputStream.read(bytes);

            // Convert Read Bytes Into String
            String asString = new String(bytes);
            if (asString.equals(""))
                return;

            // Convert String From Json Into Settings Object
            mainActivity.setSettings(gson.fromJson(asString, Settings.class));
            fileInputStream.close();
        } catch (IOException e) {throw new RuntimeException(e);}
    }

}
