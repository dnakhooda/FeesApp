package com.example.feesapp;

public class Fee {
    // Time Duration Constants
    private static final double DAYS_IN_WEEK = 7;
    private static final double DAYS_IN_MONTH = 30.437;
    private static final double DAYS_IN_YEAR = 365.25;

    // Static Methods For Converting & Displaying Charge Rate Amounts
    public static double roundToTwoDecimalPlaces(double value) {
        int tempInt = (int) (value * 100);
        return tempInt/100.0;
    }

    public static String numberToStringWithTwoDecimals(double value) {
        if ((value * 10) % 10 == 0 || (value * 100) % 10 == 0)
            return value + "0";
        return String.valueOf(value);
    }

    // Category & Charge Rate Enumerations
    public enum Category {
        InsuranceFee,
        RentOrPropertyTaxFee,
        ServiceFee,
        MembershipFee,
        OtherFee,
    }
    public enum ChargeRate {
        daily,
        weekly,
        monthly,
        yearly,
    }

    private String title;
    private double amount;
    private ChargeRate chargeRate;
    private Category category;

    public Fee(String title, double amount, ChargeRate chargeRate, Category category) {
        this.title = title;
        setAmount(amount);
        this.chargeRate = chargeRate;
        this.category = category;
    }

    // Convert Enumerations Into Strings
    public static String changeFeeCategoryToString(Category feesCategory) {
        switch (feesCategory) {
            case ServiceFee:
                return "Service Fee";
            case RentOrPropertyTaxFee:
                return "Rent/Property Tax Fee";
            case MembershipFee:
                return "Membership Fee";
            case InsuranceFee:
                return "Insurance Fee";
            case OtherFee:
                return "Other Fee";
        }
        return null;
    }

    public static String changeChargeRateToString(ChargeRate chargeRate) {
        switch (chargeRate) {
            case daily:
                return "Daily";
            case weekly:
                return "Weekly";
            case monthly:
                return "Monthly";
            case yearly:
                return "Yearly";
        }
        return null;
    }

    // Calculate Daily Cost Amount
    public double getDailyAmount() {
        switch (chargeRate) {
            case daily:
                return amount;
            case weekly:
                return amount/DAYS_IN_WEEK;
            case monthly:
                return amount/DAYS_IN_MONTH;
            case yearly:
                return amount/DAYS_IN_YEAR;
        }
        return 0.0;
    }

    // Getters
    public String getTitle() {
        return title;
    }
    public Category getCategory() {
        return category;
    }
    public double getAmount() {
        return amount;
    }
    public double getMonthlyAmount() {
        return getDailyAmount()*DAYS_IN_MONTH;
    }
    public ChargeRate getChargeRate() {
        return chargeRate;
    }

    // Setters
    public void setTitle(String title) {
        this.title = title;
    }
    public void setAmount(double amount) {this.amount = roundToTwoDecimalPlaces(amount);}
    public void setChargeRate(ChargeRate chargeRate) {
        this.chargeRate = chargeRate;
    }
    public void setCategory(Category category) {
        this.category = category;
    }

}
