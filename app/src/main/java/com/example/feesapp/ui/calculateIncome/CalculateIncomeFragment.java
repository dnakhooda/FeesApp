package com.example.feesapp.ui.calculateIncome;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.feesapp.Fee;
import com.example.feesapp.MainActivity;
import com.example.feesapp.databinding.FragmentCalculateIncomeBinding;

import org.eazegraph.lib.charts.ValueLineChart;
import org.eazegraph.lib.models.ValueLinePoint;
import org.eazegraph.lib.models.ValueLineSeries;

import java.util.ArrayList;

public class CalculateIncomeFragment extends Fragment {

    private FragmentCalculateIncomeBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Get Calculate Income View Model
        CalculateIncomeViewModel viewModel = new ViewModelProvider(this).get(CalculateIncomeViewModel.class);

        binding = FragmentCalculateIncomeBinding.inflate(inflater, container, false);
        MainActivity.instance.bringBackViewBar();
        View root = binding.getRoot();

        binding.calculateIncomeButton.setOnClickListener(e -> calculate());

        binding.inputSavingsTextField.setOnFocusChangeListener((View view, boolean hasFocus) -> {
            if (!hasFocus)
                saveInformation();
        });

        binding.inputMonthlyEarningTitleTextField.setOnFocusChangeListener((View view, boolean hasFocus) -> {
            if (!hasFocus)
                saveInformation();
        });

        setInputs();

        return root;
    }

    private void calculate() {
        double income = 0;
        try {
            income = Double.parseDouble(binding.inputMonthlyEarningTitleTextField.getText().toString());
        }
        catch (NumberFormatException exception) {
            Toast.makeText(MainActivity.instance, "Illegal Input For Income!", Toast.LENGTH_LONG).show();
            return;
        }

        if ((income * 100) % 1 > 0) {
            Toast.makeText(MainActivity.instance, "Income Cannot Have More Than Two Decimal Places!", Toast.LENGTH_LONG).show();
            return;
        }

        if (income > 1000000000) {
            Toast.makeText(MainActivity.instance, "Income Cost Is Too Large Of A Value!", Toast.LENGTH_LONG).show();
            return;
        }

        double savings = 0;
        try {
            savings = Double.parseDouble(binding.inputSavingsTextField.getText().toString());
        }
        catch (NumberFormatException exception) {
            Toast.makeText(MainActivity.instance, "Illegal Input For Savings!", Toast.LENGTH_LONG).show();
            return;
        }

        if ((savings * 100) % 1 > 0) {
            Toast.makeText(MainActivity.instance, "Savings Cannot Have More Than Two Decimal Places!", Toast.LENGTH_LONG).show();
            return;
        }

        if (savings > 1000000000) {
            Toast.makeText(MainActivity.instance, "Savings Cost Is Too Large Of A Value!", Toast.LENGTH_LONG).show();
            return;
        }

        double expenses = 0;
        ArrayList<Fee> fees = MainActivity.instance.getFees();
        for (int i = 0; i < fees.size(); i++)
            expenses += fees.get(i).getMonthlyAmount();

        double netProfit = income - expenses;

        String netProfitTitleText = "Net Profit Per Month: " + Fee.roundToTwoDecimalPlaces(netProfit);
        binding.netProfitTitle.setText(netProfitTitleText);
        binding.netProfitTitle.setTextColor(MainActivity.instance.redToGreenColorLeve(netProfit));

        ValueLineChart chart = binding.incomeChart;
        chart.clearChart();
        ValueLineSeries series = new ValueLineSeries();
        series.setColor(0xFF56B7F1);

        for (int i = 1; i <= 5; i++)
            series.addPoint(new ValueLinePoint("Month " + i, (float) Fee.roundToTwoDecimalPlaces(savings + netProfit*i)));

        chart.addSeries(series);
        chart.startAnimation();

        saveInformation();
    }

    private void setInputs() {
        if (MainActivity.instance.getSettings().getMonthlyEarnings() != 0)
            binding.inputMonthlyEarningTitleTextField.setText(String.valueOf(MainActivity.instance.getSettings().getMonthlyEarnings()));
        if (MainActivity.instance.getSettings().getTotalSavings() != 0)
            binding.inputSavingsTextField.setText(String.valueOf(MainActivity.instance.getSettings().getTotalSavings()));
    }

    private void saveInformation() {
        double income = 0;
        try {
            income = Double.parseDouble(binding.inputMonthlyEarningTitleTextField.getText().toString());
        }
        catch (NumberFormatException exception) {
            return;
        }

        double savings = 0;
        try {
            savings = Double.parseDouble(binding.inputSavingsTextField.getText().toString());
        }
        catch (NumberFormatException exception) {
            return;
        }

        MainActivity.instance.getSettings().setMonthlyEarnings(income);
        MainActivity.instance.getSettings().setTotalSavings(savings);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}