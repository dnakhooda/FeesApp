package com.example.feesapp;

import android.os.Bundle;
import android.view.View;

import com.example.feesapp.ui.settings.Settings;
import com.example.feesapp.ui.settings.SettingsFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import com.example.feesapp.databinding.ActivityMainBinding;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {
    public static MainActivity instance;
    private ArrayList<Fee> fees = new ArrayList<>();
    private ActivityMainBinding binding;
    private Fee feeToEdit = null;
    private Settings settings = new Settings();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        instance = this;

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Setup Navigation
        BottomNavigationView navView = binding.navView;
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        NavigationUI.setupWithNavController(navView, navController);

        getSavedFees();
        getSavedSettings();
    }

    public Fee getFeeByTitle(String title) {
        for (int i = 0; i < fees.size(); i++) {
            if (fees.get(i).getTitle().equals(title))
                return fees.get(i);
        }
        return null;
    }

    public boolean removeFeeByTitle(String title) {
        for (int i = 0; i < fees.size(); i++) {
            if (fees.get(i).getTitle().equals(title)) {
                fees.remove(i);
                return true;
            }
        }
        return false;
    }

    private void clearFiles() {
        File file = getApplication().getFilesDir();
        try {
            FileOutputStream fileOutputStreamFees = new FileOutputStream(new File(file, "Fees.txt"));
            FileOutputStream fileOutputStreamSettings = new FileOutputStream(new File(file, "FeeSettings.txt"));
            fileOutputStreamFees.write("".getBytes());
            fileOutputStreamSettings.write("".getBytes());
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void saveFees() {
        try {
            Gson gson = new Gson();
            byte[] byteInformation = gson.toJson(fees).getBytes();

            File file = getApplication().getFilesDir();
            FileOutputStream fileOutputStream = new FileOutputStream(new File(file, "Fees.txt"));
            fileOutputStream.write(byteInformation);
            fileOutputStream.close();
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void getSavedFees() {
        Gson gson = new Gson();
        File fileDirectory = getApplication().getFilesDir();
        File file = new File(fileDirectory, "Fees.txt");
        FileInputStream fileInputStream;
        byte[] bytes = new byte[(int) file.length()];

        try {
            file.createNewFile();
            fileInputStream = new FileInputStream(file);
            fileInputStream.read(bytes);
            String asString = Arrays.toString(bytes);
            if (asString.equals("[]"))
                return;

            System.out.println(asString);
            fees = gson.fromJson(asString, new TypeToken<ArrayList<Fee>>(){}.getType());
            fileInputStream.close();
        } catch (IOException e) {throw new RuntimeException(e);}
    }

    public void saveSettings() {
        try {
            Gson gson = new Gson();
            File filesDirectory = getApplication().getFilesDir();
            FileOutputStream fileOutputStream = new FileOutputStream(new File(filesDirectory, "FeeSettings.txt"));
            fileOutputStream.write(gson.toJson(settings).getBytes());
            fileOutputStream.close();
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void getSavedSettings() {
        Gson gson = new Gson();
        File fileDirectory = getApplication().getFilesDir();

        File file = new File(fileDirectory, "FeeSettings.txt");
        FileInputStream fileInputStream;

        byte[] bytes = new byte[(int) file.length()];

        try {
            file.createNewFile();
            fileInputStream = new FileInputStream(file);
            fileInputStream.read(bytes);
            String asString = new String(bytes);
            System.out.println(asString);
            if (asString.equals("[]") || asString.equals(""))
                return;

            settings = gson.fromJson(asString, Settings.class);
            fileInputStream.close();
        } catch (IOException e) {throw new RuntimeException(e);}
    }

    public void removeNavBar() {
        binding.navView.setVisibility(View.GONE);
    }

    public void bringBackViewBar() {
        if (binding == null)
            return;
        binding.navView.setVisibility(View.VISIBLE);
    }

    public String currencyToSymbol(SettingsFragment.Currency currency) {
        switch (currency) {
            case USDollar:
                return "$";
            case Euro:
                return "€";
            case Yen:
                return "JP¥";
            case Sterling:
                return "£";
            case Renminbi:
                return "CN¥";
            case AustralianDollar:
                return "A$";
            case CanadianDollar:
                return "C$";
            case SwissFranc:
                return "Fr";
            case HongKongDollar:
                return "HK$";
            case SingaporeDollar:
                return "S$";
        }
        return null;
    }

    public ArrayList<Fee> getFees() {
        return fees;
    }

    public Fee getFeeToEdit() {
        return feeToEdit;
    }

    public void setFeeToEdit(Fee feeToEdit) {
        this.feeToEdit = feeToEdit;
    }

    public Settings getSettings() {
        return settings;
    }
}