package com.example.feesapp.ui.settings;

import com.example.feesapp.MainActivity;

public class Settings {

    private SettingsFragment.Currency currencyType = SettingsFragment.Currency.USDollar;

    public Settings() {

    }

    public SettingsFragment.Currency getCurrencyType() {
        return currencyType;
    }

    public void setCurrencyType(SettingsFragment.Currency currencyType) {
        this.currencyType = currencyType;
        MainActivity.instance.saveSettings();
    }
}
