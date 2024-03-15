package com.example.feesapp.ui.simulateExpenses;

public class ActionSimulateExpenses {

    private boolean turnOn;
    private String feeTitle;

    public ActionSimulateExpenses(boolean turnOn, String feeTitle) {
        this.turnOn = turnOn;
        this.feeTitle = feeTitle;
    }

    // Getters
    public String getFeeTitle() {
        return feeTitle;
    }

    public boolean isTurnOn() {
        return turnOn;
    }

    // Setters
    public void setFeeTitle(String feeTitle) {
        this.feeTitle = feeTitle;
    }

    public void setTurnOn(boolean turnOn) {
        this.turnOn = turnOn;
    }
}
