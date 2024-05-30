package com.example.feesapp.ui.settings;

import com.example.feesapp.MainActivity;

public class Settings {

    private SettingsFragment.Currency currencyType = SettingsFragment.Currency.USDollar;
    private double monthlyEarnings = 0;
    private double totalSavings = 0;

    public Settings() {
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
        MainActivity.instance.getStorageHandler().saveSettings();
    }

    public void setMonthlyEarnings(double monthlyEarnings) {
        this.monthlyEarnings = monthlyEarnings;
        MainActivity.instance.getStorageHandler().saveSettings();
    }

    public void setTotalSavings(double totalSavings) {
        this.totalSavings = totalSavings;
        MainActivity.instance.getStorageHandler().saveSettings();
    }

}
