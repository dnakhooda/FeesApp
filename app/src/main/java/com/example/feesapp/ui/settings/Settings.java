package com.example.feesapp.ui.settings;

import com.example.feesapp.MainActivity;

public class Settings {

    private SettingsFragment.Currency currencyType = SettingsFragment.Currency.USDollar;
    private double monthlyEarnings = 0;
    private double totalSavings = 0;

    public Settings() {

    }

    public SettingsFragment.Currency getCurrencyType() {
        return currencyType;
    }

    public void setCurrencyType(SettingsFragment.Currency currencyType) {
        this.currencyType = currencyType;
        MainActivity.instance.saveSettings();
    }

    public double getMonthlyEarnings() {
        return monthlyEarnings;
    }

    public void setMonthlyEarnings(double monthlyEarnings) {
        this.monthlyEarnings = monthlyEarnings;
        MainActivity.instance.saveSettings();
    }

    public double getTotalSavings() {
        return totalSavings;
    }

    public void setTotalSavings(double totalSavings) {
        this.totalSavings = totalSavings;
        MainActivity.instance.saveSettings();
    }
}
