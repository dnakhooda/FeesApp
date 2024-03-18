package com.example.feesapp.ui.editItem;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.feesapp.Fee;
import com.example.feesapp.MainActivity;
import com.example.feesapp.R;
import com.example.feesapp.databinding.FragmentEditItemBinding;

public class EditItemFragment extends Fragment {

    private FragmentEditItemBinding binding;
    private MainActivity mainActivity;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Get Binding
        binding = FragmentEditItemBinding.inflate(inflater, container, false);

        // Set Main Activity Reference & Remove Navigation Bar
        mainActivity = MainActivity.instance;
        mainActivity.removeNavBar();

        NavController navController = Navigation.findNavController(mainActivity, R.id.nav_host_fragment_activity_main);

        // Set Add Button & Delete Button & Cancel Button Click Event Listener
        binding.editChangeButton.setOnClickListener(view -> {
            // If changeFee Returns True, An Error Has Occurred
            if (changeFee())
                return;

            navController.popBackStack();
        });

        binding.editDeleteButton.setOnClickListener(view -> {
            mainActivity.getFees().removeFeeByTitle(mainActivity.getFeeToEdit().getTitle());
            navController.popBackStack();
        });

        binding.editCancelButton.setOnClickListener(view -> navController.popBackStack());

        // Set Text Fields & Radio Groups With Fee
        setInputs();

        return binding.getRoot();
    }

    private void setInputs() {
        // Get Fee Being Edited
        Fee fee = mainActivity.getFeeToEdit();

        // Set Title
        binding.editTitleField.setText(fee.getTitle());

        // Set Charge Rate Cost
        binding.editAmountField.setText(Fee.numberToStringWithTwoDecimals(fee.getAmount()));

        // Set Charge Rate
        if (fee.getChargeRate().equals(Fee.ChargeRate.daily))
            binding.editButtonDaily.setChecked(true);
        else if (fee.getChargeRate().equals(Fee.ChargeRate.weekly))
            binding.editButtonWeekly.setChecked(true);
        else if ((fee.getChargeRate().equals(Fee.ChargeRate.monthly)))
            binding.editButtonMonthly.setChecked(true);
        else
            binding.editButtonYearly.setChecked(true);

        // Set Category
        if (fee.getCategory().equals(Fee.Category.ServiceFee))
            binding.editServiceFeeButton.setChecked(true);
        else if (fee.getCategory().equals(Fee.Category.InsuranceFee))
            binding.editInsuranceFeeButton.setChecked(true);
        else if (fee.getCategory().equals(Fee.Category.MembershipFee))
            binding.editMembershipFeeButton.setChecked(true);
        else
            binding.editRentFeeButton.setChecked(true);
    }

    private boolean changeFee() {
        // Get Title & Check If Title Is Valid
        String title = binding.editTitleField.getText().toString();

        if (mainActivity.isFeeTitleValid(title))
            return true;

        if (mainActivity.getFees().getFeeByTitleExcluding(title, mainActivity.getFeeToEdit()) != null) {
            Toast.makeText(mainActivity, "Title Unique! No Two Fees Should Have The Same Title!", Toast.LENGTH_LONG).show();
            return true;
        }

        // Get Charge Rate Cost & Check If Charge Rate Cost Is Valid
        double chargeRateCost;
        try {
            chargeRateCost = Double.parseDouble(binding.editAmountField.getText().toString());
        }
        catch (NumberFormatException exception) {
            Toast.makeText(mainActivity, "Illegal Input For Charge Rate Cost!", Toast.LENGTH_LONG).show();
            return true;
        }

        if (mainActivity.isAmountValid(chargeRateCost, "Charge Rate Cost"))
            return true;

        // Get Charge Rate & Make Sure Charge Rate Is Selected
        Fee.ChargeRate chargeRate;

        if (binding.editRateGroup.getCheckedRadioButtonId() == -1) {
            Toast.makeText(mainActivity, "A Charge Rate Must Be Selected!", Toast.LENGTH_LONG).show();
            return true;
        }

        int rateCheckedId = mainActivity.findViewById(binding.editRateGroup.getCheckedRadioButtonId()).getId();
        if (rateCheckedId == binding.editButtonDaily.getId())
            chargeRate = Fee.ChargeRate.daily;
        else if (rateCheckedId == binding.editButtonWeekly.getId())
            chargeRate = Fee.ChargeRate.weekly;
        else if (rateCheckedId == binding.editButtonMonthly.getId())
            chargeRate = Fee.ChargeRate.monthly;
        else
            chargeRate = Fee.ChargeRate.yearly;

        // Get Category & Make Sure Category Is Selected
        Fee.Category category;

        if (binding.editCategoryGroup.getCheckedRadioButtonId() == -1) {
            Toast.makeText(mainActivity, "A Category Must Be Selected!", Toast.LENGTH_LONG).show();
            return true;
        }

        int categoryCheckedId = mainActivity.findViewById(binding.editCategoryGroup.getCheckedRadioButtonId()).getId();
        if (categoryCheckedId == binding.editInsuranceFeeButton.getId())
            category = Fee.Category.InsuranceFee;
        else if (categoryCheckedId == binding.editRentFeeButton.getId())
            category = Fee.Category.RentOrPropertyTaxFee;
        else if (categoryCheckedId == binding.editServiceFeeButton.getId())
            category = Fee.Category.ServiceFee;
        else if (categoryCheckedId == binding.editOtherFeeButton.getId())
            category = Fee.Category.OtherFee;
        else
            category = Fee.Category.MembershipFee;

        // Change Fee & Save Fee
        mainActivity.getFeeToEdit().setTitle(title);
        mainActivity.getFeeToEdit().setAmount(chargeRateCost);
        mainActivity.getFeeToEdit().setChargeRate(chargeRate);
        mainActivity.getFeeToEdit().setCategory(category);

        return false;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}