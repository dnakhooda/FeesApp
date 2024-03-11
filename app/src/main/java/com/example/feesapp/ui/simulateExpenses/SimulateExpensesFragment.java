package com.example.feesapp.ui.simulateExpenses;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SwitchCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.feesapp.Fee;
import com.example.feesapp.MainActivity;
import com.example.feesapp.R;
import com.example.feesapp.databinding.FragmentSimulateExpensesBinding;
import com.google.android.material.chip.Chip;

import java.util.ArrayList;

public class SimulateExpensesFragment extends Fragment {

    private MainActivity mainActivity;
    private FragmentSimulateExpensesBinding binding;
    private ArrayList<String> notOnTitle = new ArrayList<>();

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Get Simulate Expenses View Model
        SimulateExpensesViewModel viewModel = new ViewModelProvider(this).get(SimulateExpensesViewModel.class);

        binding = FragmentSimulateExpensesBinding.inflate(inflater, container, false);
        MainActivity.instance.bringBackViewBar();
        View root = binding.getRoot();

        mainActivity = MainActivity.instance;

        mainActivity.getFees().forEach(this::makeEditLayout);

        binding.inputMonthlyEarningTitleTextField.setOnFocusChangeListener((View view, boolean hasFocus) -> {
            if (!hasFocus)
                saveInformation();
        });

        setInputs();

        calculateSimulatedCost();

        return root;
    }

    private void calculateSimulatedCost() {
        if (binding.inputMonthlyEarningTitleTextField.getText().toString().equals(""))
            return;

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

        double expenses = 0;
        ArrayList<Fee> fees = MainActivity.instance.getFees();
        for (int i = 0; i < fees.size(); i++)
            if (!notOnTitle.contains(fees.get(i).getTitle()))
                expenses += fees.get(i).getMonthlyAmount();

        double netProfit = income - expenses;

        String netProfitTitleText = "Net Profit Per Month: " + Fee.roundToTwoDecimalPlaces(netProfit);
        binding.netProfitTitle.setText(netProfitTitleText);
        binding.netProfitTitle.setTextColor(MainActivity.instance.redToGreenColorLeve(netProfit));

        saveInformation();
    }

    private void saveInformation() {
        double income = 0;
        try {
            income = Double.parseDouble(binding.inputMonthlyEarningTitleTextField.getText().toString());
        }
        catch (NumberFormatException exception) {
            return;
        }

        MainActivity.instance.getSettings().setMonthlyEarnings(income);
    }

    private void setInputs() {
        if (MainActivity.instance.getSettings().getMonthlyEarnings() != 0)
            binding.inputMonthlyEarningTitleTextField.setText(String.valueOf(MainActivity.instance.getSettings().getMonthlyEarnings()));
    }

    private void makeEditLayout(Fee fee) {
        LinearLayout simulateLinearLayout = binding.simulateLinearLayout;
        LayoutInflater inflater = mainActivity.getLayoutInflater();
        View inflatedLayout = inflater.inflate(R.layout.simulated_expense, simulateLinearLayout, false);

        TextView title = inflatedLayout.findViewById(R.id.simulate_fee_title);
        title.setText(fee.getTitle());

        TextView category = inflatedLayout.findViewById(R.id.simulate_fee_category);
        category.setText(Fee.changeFeeCategoryToString(fee.getCategory()));

        TextView cost = inflatedLayout.findViewById(R.id.simulate_fee_cost);
        String feeString = mainActivity.currencyToSymbol(mainActivity.getSettings().getCurrencyType()) + Fee.numberToStringWithTwoDecimals(fee.getAmount()) + " " + Fee.changeChargeRateToString(fee.getChargeRate());
        cost.setText(feeString);

        SwitchCompat switchButton = inflatedLayout.findViewById(R.id.simulate_fee_switch);
        switchButton.setChecked(true);
        switchButton.setOnCheckedChangeListener((CompoundButton view, boolean isChecked) -> {
            if (isChecked)
                notOnTitle.remove(fee.getTitle());
            else
                notOnTitle.add(fee.getTitle());
            calculateSimulatedCost();
        });

        simulateLinearLayout.addView(inflatedLayout);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}