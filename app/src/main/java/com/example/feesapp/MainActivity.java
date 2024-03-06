package com.example.feesapp;

import android.os.Bundle;
import android.view.View;

import com.example.feesapp.ui.settings.SettingsFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import com.example.feesapp.databinding.ActivityMainBinding;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    public static MainActivity instance;

    private final ArrayList<Fee> fees = new ArrayList<>();
    private ActivityMainBinding binding;
    private SettingsFragment.Currency currency = SettingsFragment.Currency.Euro;

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

    public SettingsFragment.Currency getCurrency() {
        return currency;
    }

    public void setCurrency(SettingsFragment.Currency currency) {
        this.currency = currency;
    }
}