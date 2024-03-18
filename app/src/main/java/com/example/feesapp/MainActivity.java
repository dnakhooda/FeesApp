package com.example.feesapp;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.feesapp.ui.adts.FeesArrayList;
import com.example.feesapp.ui.settings.Settings;
import com.example.feesapp.ui.settings.SettingsFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import com.example.feesapp.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    public static MainActivity instance;
    private ActivityMainBinding binding;
    private FeesArrayList fees;
    private Settings settings;
    private StorageHandler storageHandler;
    private Fee feeToEdit = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Set Instance
        instance = this;

        // Get Binding
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Setup Navigation
        BottomNavigationView navView = binding.navView;
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        NavigationUI.setupWithNavController(navView, navController);

        fees = new FeesArrayList();
        storageHandler = new StorageHandler();
        settings = new Settings();

        // Get Saved Information
        storageHandler.getSavedFees();
        storageHandler.getSavedSettings();
    }

    // Convert Currency Enumeration Into Symbol
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

    // Error Checking For Title And Amounts
    public boolean isFeeTitleValid(String title) {
        if (title.length() < 1) {
            Toast.makeText(this, "Title Must Be At Least One Character!", Toast.LENGTH_LONG).show();
            return true;
        }

        if (title.length() > 9) {
            Toast.makeText(this, "Title Must Be Less Than Ten Characters!", Toast.LENGTH_LONG).show();
            return true;
        }

        return false;
    }

    public boolean isAmountValid(double amount, String amountName) {
        if ((amount * 100) % 1 > 0) {
            Toast.makeText(this, amountName + " Cannot Have More Than Two Decimal Places!", Toast.LENGTH_LONG).show();
            return true;
        }

        if (amount > 10000000) {
            Toast.makeText(this, amountName + " Is Too Large Of A Value!", Toast.LENGTH_LONG).show();
            return true;
        }

        return false;
    }

    // Determine Color (Red To Green) Given Net Profit (Negative: Red; Positive: Green;)
    public int redToGreenColorLevel(double netProfit) {
        netProfit += 500;
        if (netProfit > 1000)
            netProfit = 1000;
        else if (netProfit < 0)
            netProfit = 0;

        double r = (255 * ((1000-netProfit)/1000));
        double g = (255 * (netProfit/1000));
        return Color.rgb((int) r, (int) g, 0);
    }

    // Add/Remove Nav Bar
    public void bringBackNavBar() {
        if (binding == null)
            return;
        binding.navView.setVisibility(View.VISIBLE);
    }

    public void removeNavBar() {
        binding.navView.setVisibility(View.GONE);
    }

    // Getters
    public FeesArrayList getFees() {
        return fees;
    }

    public Settings getSettings() {
        return settings;
    }

    public StorageHandler getStorageHandler() {
        return storageHandler;
    }

    public Fee getFeeToEdit() {
        return feeToEdit;
    }

    // Setters
    public void setFees(FeesArrayList fees) {
        this.fees = fees;
    }

    public void setSettings(Settings settings) {
        this.settings = settings;
    }

    public void setStorageHandler(StorageHandler storageHandler) {
        this.storageHandler = storageHandler;
    }

    public void setFeeToEdit(Fee feeToEdit) {
        this.feeToEdit = feeToEdit;
    }

}