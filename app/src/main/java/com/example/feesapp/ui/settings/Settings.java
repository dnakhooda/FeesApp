package com.example.feesapp.ui.settings;

import com.example.feesapp.MainActivity;
import com.example.feesapp.StorageHandler;

public class Settings {

    private SettingsFragment.Currency currencyType = SettingsFragment.Currency.USDollar;
    private final StorageHandler storageHandler;
    private double monthlyEarnings = 0;
    private double totalSavings = 0;

    public Settings() {
        storageHandler = MainActivity.instance.getStorageHandler();
    }

    // Getters
    public SettingsFragment.Currency getCurrencyType() {
        return currencyType;
    }

    public double getMonthlyEarnings() {
        return monthlyEarnings;
    }

    public double getTotalSavings() {
        return totalSavings;
    }

    // Setters
    public void setCurrencyType(SettingsFragment.Currency currencyType) {
        this.currencyType = currencyType;
        storageHandler.saveSettings();
    }

    public void setMonthlyEarnings(double monthlyEarnings) {
        this.monthlyEarnings = monthlyEarnings;
        storageHandler.saveSettings();
    }

    public void setTotalSavings(double totalSavings) {
        this.totalSavings = totalSavings;
        storageHandler.saveSettings();
    }

}
