package com.example.feesapp.ui.simulateExpenses;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SwitchCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.feesapp.Fee;
import com.example.feesapp.MainActivity;
import com.example.feesapp.R;
import com.example.feesapp.databinding.FragmentSimulateExpensesBinding;
import com.example.feesapp.ui.adts.Stack;

import java.util.ArrayList;

public class SimulateExpensesFragment extends Fragment {

    private FragmentSimulateExpensesBinding binding;
    private MainActivity mainActivity;
    private final ArrayList<View> inflatedLayouts = new ArrayList<>();
    private final ArrayList<String> notOnTitle = new ArrayList<>();
    private Stack<ActionSimulateExpenses> undoStack = new Stack<>(ActionSimulateExpenses.class, 50);

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Get Binding
        binding = FragmentSimulateExpensesBinding.inflate(inflater, container, false);

        // Set Main Activity Reference & Add Navigation Bar
        mainActivity = MainActivity.instance;
        mainActivity.bringBackNavBar();

        // Set Undo Fee Click Event Listener & Save When Text Field Unfocused
        binding.inputMonthlyEarningTitleTextField.setOnFocusChangeListener((View view, boolean hasFocus) -> {
            if (!hasFocus)
                saveMonthlyIncomeInformation();
        });

        binding.undoButton.setOnClickListener((View view) -> {
            if (!undoStack.isEmpty()) {
                ActionSimulateExpenses action = undoStack.pop();
                undo(action);
            }
        });

        // Inflate Layouts For Each Fee
        mainActivity.getFees().forEach(this::makeEditLayout);

        // Set Text Fields With Saved Values
        setInputs();

        // Calculate Simulated Cost With Inputted Information
        calculateSimulatedCost();

        return binding.getRoot();
    }

    private void calculateSimulatedCost() {
        if (binding.inputMonthlyEarningTitleTextField.getText().toString().equals(""))
            return;

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

        // Calculate Monthly Expenses
        double monthlyExpenses = 0;
        ArrayList<Fee> fees = mainActivity.getFees();
        for (int i = 0; i < fees.size(); i++)
            if (!notOnTitle.contains(fees.get(i).getTitle()))
                monthlyExpenses += fees.get(i).getMonthlyAmount();

        // Calculate Net Monthly Profit
        double netMonthlyProfit = monthlyIncome - monthlyExpenses;

        // Display Net Profit & Change Text Color (Green Or Red Depending On Net Monthly Profit)
        String netProfitTitleText = "Net Profit Per Month: " + Fee.roundToTwoDecimalPlaces(netMonthlyProfit);
        binding.netProfitTitle.setText(netProfitTitleText);
        binding.netProfitTitle.setTextColor(mainActivity.redToGreenColorLevel(netMonthlyProfit));

        saveMonthlyIncomeInformation();
    }

    private void makeEditLayout(Fee fee) {
        // Get Main Page Linear Layout
        LinearLayout simulateLinearLayout = binding.simulateLinearLayout;

        // Inflate Simulate Fee Item Layout
        LayoutInflater inflater = mainActivity.getLayoutInflater();
        View inflatedLayout = inflater.inflate(R.layout.item_simulated_expense, simulateLinearLayout, false);

        // Set Title
        TextView title = inflatedLayout.findViewById(R.id.simulate_fee_title);
        title.setText(fee.getTitle());

        // Set Category
        TextView category = inflatedLayout.findViewById(R.id.simulate_fee_category);
        category.setText(Fee.changeFeeCategoryToString(fee.getCategory()));

        // Set Charge Rate
        TextView cost = inflatedLayout.findViewById(R.id.simulate_fee_cost);
        String feeString = mainActivity.currencyToSymbol(mainActivity.getSettings().getCurrencyType()) + Fee.numberToStringWithTwoDecimals(fee.getAmount()) + " " + Fee.changeChargeRateToString(fee.getChargeRate());
        cost.setText(feeString);

        // Set Switch Button Click Event Listener
        SwitchCompat switchButton = inflatedLayout.findViewById(R.id.simulate_fee_switch);
        // Tag Tells Button To Perform Event Listener Action
        switchButton.setTag(true);
        switchButton.setChecked(true);
        switchButton.setOnCheckedChangeListener((CompoundButton view, boolean isChecked) -> {
            if (view.getTag().equals(true)) {
                if (undoStack.isFull())
                    undoStack = new Stack<>(ActionSimulateExpenses.class, 50);
                undoStack.push(new ActionSimulateExpenses(isChecked, fee.getTitle()));

                if (isChecked)
                    notOnTitle.remove(fee.getTitle());
                else
                    notOnTitle.add(fee.getTitle());
                calculateSimulatedCost();
            }
        });

        // Add Inflated Layout To Linear Layout & Inflated Layouts Array List
        inflatedLayouts.add(inflatedLayout);
        simulateLinearLayout.addView(inflatedLayout);
    }

    private void undo(ActionSimulateExpenses action) {
        // Find Layout To Undo Action
        for (int i = 0; i < inflatedLayouts.size(); i++) {
            View inflatedLayout = inflatedLayouts.get(i);
            TextView title = inflatedLayout.findViewById(R.id.simulate_fee_title);
            if (title.getText().equals(action.getFeeTitle())) {
                SwitchCompat switchButton = inflatedLayout.findViewById(R.id.simulate_fee_switch);
                switchButton.setTag(false);
                switchButton.setChecked(!action.isTurnOn());
                switchButton.setTag(true);
            }
        }

        calculateSimulatedCost();
    }

    private void saveMonthlyIncomeInformation() {
        // Get Monthly Income And Set It To Settings Object
        double monthlyIncome;
        try {
            monthlyIncome = Double.parseDouble(binding.inputMonthlyEarningTitleTextField.getText().toString());
        }
        catch (NumberFormatException exception) {
            return;
        }

        mainActivity.getSettings().setMonthlyEarnings(monthlyIncome);
    }

    private void setInputs() {
        // Get Monthly Earnings From Settings & Set That Value To Monthly Income Text Field
        if (mainActivity.getSettings().getMonthlyEarnings() != 0)
            binding.inputMonthlyEarningTitleTextField.setText(String.valueOf(mainActivity.getSettings().getMonthlyEarnings()));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}