package com.example.feesapp;

public class Fee {

    enum FeesCategory {
        ApplicationFee,
        GovernmentFee,
        ServiceFee,
        MembershipFee,
    }

    enum ChargeRate {
        daily,
        weekly,
        monthly,
        yearly,
    }

    private String title;
    private double amount;
    private ChargeRate chargeRate;
    private FeesCategory category;

    public Fee(String title, double amount, ChargeRate chargeRate, FeesCategory category) {
        this.title = title;
        setAmount(amount);
        this.chargeRate = chargeRate;
        this.category = category;
    }

    public static String changeFeeCategoryToString(FeesCategory feesCategory) {
        switch (feesCategory) {
            case ServiceFee:
                return "Service Fee";
            case GovernmentFee:
                return "Government Fee";
            case MembershipFee:
                return "Membership Fee";
            case ApplicationFee:
                return "Application Fee";
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public double getAmount() {
        return amount;
    }

    public String getAmountAsString() {
        if ((amount * 10) % 10 == 0 || (amount * 100) % 10 == 0)
            return amount + "0";
        return String.valueOf(amount);
    }

    public void setAmount(double amount) {
        int tempInt = (int) (amount * 100);
        this.amount = tempInt/100.0;
    }

    public ChargeRate getChargeRate() {
        return chargeRate;
    }

    public void setChargeRate(ChargeRate chargeRate) {
        this.chargeRate = chargeRate;
    }

    public FeesCategory getCategory() {
        return category;
    }

    public void setCategory(FeesCategory category) {
        this.category = category;
    }
}
