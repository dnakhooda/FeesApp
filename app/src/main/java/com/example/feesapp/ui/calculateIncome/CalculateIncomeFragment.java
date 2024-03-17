package com.example.feesapp.ui.calculateIncome;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.feesapp.Fee;
import com.example.feesapp.MainActivity;
import com.example.feesapp.databinding.FragmentCalculateIncomeBinding;

import org.eazegraph.lib.charts.ValueLineChart;
import org.eazegraph.lib.models.ValueLinePoint;
import org.eazegraph.lib.models.ValueLineSeries;

import java.util.ArrayList;

public class CalculateIncomeFragment extends Fragment {

    private FragmentCalculateIncomeBinding binding;
    private MainActivity mainActivity;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Get Binding
        binding = FragmentCalculateIncomeBinding.inflate(inflater, container, false);

        // Set Main Activity Reference & Add Navigation Bar
        mainActivity = MainActivity.instance;
        mainActivity.bringBackNavBar();

        // Set Calculate Button Click Event Listener
        binding.calculateIncomeButton.setOnClickListener(e -> calculate());

        // Save Monthly Earnings & Savings When Text Fields Unselected
        binding.inputSavingsTextField.setOnFocusChangeListener((View view, boolean hasFocus) -> {
            if (!hasFocus)
                saveInformation();
        });

        binding.inputMonthlyEarningTitleTextField.setOnFocusChangeListener((View view, boolean hasFocus) -> {
            if (!hasFocus)
                saveInformation();
        });

        // Set Text Fields With Saved Inputs
        setTextFields();

        return binding.getRoot();
    }

    private void calculate() {
        // Get Monthly Income & Check If Monthly Income Is Valid
        double monthlyIncome;
        try {
            monthlyIncome = Double.parseDouble(binding.inputMonthlyEarningTitleTextField.getText().toString());
        }
        catch (NumberFormatException exception) {
            Toast.makeText(mainActivity, "Illegal Input For Monthly Income!", Toast.LENGTH_LONG).show();
            return;
        }

        if (mainActivity.isAmountValid(monthlyIncome, "Monthly Income"))
            return;

        // Get Total Savings & Check If Total Savings Is Valid
        double totalSavings;
        try {
            totalSavings = Double.parseDouble(binding.inputSavingsTextField.getText().toString());
        }
        catch (NumberFormatException exception) {
            Toast.makeText(mainActivity, "Illegal Input For Savings!", Toast.LENGTH_LONG).show();
            return;
        }

        if (mainActivity.isAmountValid(totalSavings, "Total Savings"))
            return;

        // Calculate Total Monthly Expenses From Fees
        double totalMonthlyExpenses = 0;
        ArrayList<Fee> fees = mainActivity.getFees();
        for (int i = 0; i < fees.size(); i++)
            totalMonthlyExpenses += fees.get(i).getMonthlyAmount();

        // Calculate Net Monthly Profit
        double netMonthlyProfit = monthlyIncome - totalMonthlyExpenses;

        // Display Net Profit
        String netProfitTitleText = "Net Profit Per Month: " + mainActivity.currencyToSymbol(mainActivity.getSettings().getCurrencyType()) + Fee.numberToStringWithTwoDecimals(Fee.roundToTwoDecimalPlaces(netMonthlyProfit));
        binding.netProfitTitle.setText(netProfitTitleText);
        binding.netProfitTitle.setTextColor(mainActivity.redToGreenColorLevel(netMonthlyProfit));

        // Set Line Chart For Earnings Over 5 Months
        ValueLineChart chart = binding.incomeChart;
        chart.clearChart();

        ValueLineSeries series = new ValueLineSeries();
        series.setColor(0xFF56B7F1);

        for (int i = 1; i <= 5; i++)
            series.addPoint(new ValueLinePoint("Month " + i, (float) Fee.roundToTwoDecimalPlaces(totalSavings + netMonthlyProfit*i)));

        chart.addSeries(series);
        chart.startAnimation();

        // Save Monthly Income & Total Savings
        saveInformation();
    }

    private void setTextFields() {
        // Set Text Fields With Saved Inputs
        if (mainActivity.getSettings().getMonthlyEarnings() != 0)
            binding.inputMonthlyEarningTitleTextField.setText(Fee.numberToStringWithTwoDecimals(mainActivity.getSettings().getMonthlyEarnings()));
        if (mainActivity.getSettings().getTotalSavings() != 0)
            binding.inputSavingsTextField.setText(Fee.numberToStringWithTwoDecimals(mainActivity.getSettings().getTotalSavings()));
    }

    private void saveInformation() {
        // Save Monthly Income In Settings Object
        double income;
        try {
            income = Double.parseDouble(binding.inputMonthlyEarningTitleTextField.getText().toString());
        }
        catch (NumberFormatException exception) {
            return;
        }

        mainActivity.getSettings().setMonthlyEarnings(income);

        // Save Total Savings In Settings Object
        double savings;
        try {
            savings = Double.parseDouble(binding.inputSavingsTextField.getText().toString());
        }
        catch (NumberFormatException exception) {
            return;
        }

        mainActivity.getSettings().setTotalSavings(savings);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}