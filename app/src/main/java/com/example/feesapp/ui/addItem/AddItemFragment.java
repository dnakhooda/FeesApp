package com.example.feesapp.ui.addItem;

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
import com.example.feesapp.databinding.FragmentAddItemBinding;

public class AddItemFragment extends Fragment {

    private FragmentAddItemBinding binding;
    private MainActivity mainActivity;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Get Binding
        binding = FragmentAddItemBinding.inflate(inflater, container, false);

        // Set Main Activity Reference & Remove Navigation Bar
        mainActivity = MainActivity.instance;
        mainActivity.removeNavBar();

        NavController navController = Navigation.findNavController(mainActivity, R.id.nav_host_fragment_activity_main);

        // Set Add Button & Cancel Button Click Event Listener
        binding.addAddButton.setOnClickListener(view -> {
            // If addFee Returns True, An Error Has Occurred
            if (addFee())
                return;

            navController.popBackStack();
        });

        binding.addCancelButton.setOnClickListener(view -> navController.popBackStack());

        return binding.getRoot();
    }

    private boolean addFee() {
        // Get Title & Check If Title Is Valid
        String title = binding.addTitleField.getText().toString();

        if (mainActivity.isFeeTitleValid(title))
            return true;

        if (mainActivity.getFees().getFeeByTitle(title) != null) {
            Toast.makeText(mainActivity, "Title Unique! No Two Fees Should Have The Same Title!", Toast.LENGTH_LONG).show();
            return true;
        }

        // Get Charge Rate Cost & Check If Charge Rate Cost Is Valid
        double chargeRateCost;
        try {
            chargeRateCost = Double.parseDouble(binding.addAmountField.getText().toString());
        }
        catch (NumberFormatException exception) {
            Toast.makeText(mainActivity, "Illegal Input For Charge Rate Cost!", Toast.LENGTH_LONG).show();
            return true;
        }

        if (mainActivity.isAmountValid(chargeRateCost, "Charge Rate Cost"))
            return true;

        // Get Charge Rate & Make Sure Charge Rate Is Selected
        Fee.ChargeRate chargeRate;

        if (binding.addRateGroup.getCheckedRadioButtonId() == -1) {
            Toast.makeText(mainActivity, "A Charge Rate Must Be Selected!", Toast.LENGTH_LONG).show();
            return true;
        }

        int rateCheckedId = mainActivity.findViewById(binding.addRateGroup.getCheckedRadioButtonId()).getId();
        if (rateCheckedId == binding.addButtonDaily.getId())
            chargeRate = Fee.ChargeRate.daily;
        else if (rateCheckedId == binding.addButtonWeekly.getId())
            chargeRate = Fee.ChargeRate.weekly;
        else if (rateCheckedId == binding.addButtonMonthly.getId())
            chargeRate = Fee.ChargeRate.monthly;
        else
            chargeRate = Fee.ChargeRate.yearly;

        // Get Category & Make Sure Category Is Selected
        Fee.Category category;

        if (binding.addCategoryGroup.getCheckedRadioButtonId() == -1) {
            Toast.makeText(mainActivity, "A Category Must Be Selected!", Toast.LENGTH_LONG).show();
            return true;
        }

        int categoryCheckedId = mainActivity.findViewById(binding.addCategoryGroup.getCheckedRadioButtonId()).getId();
        if (categoryCheckedId == binding.addRentFeeButton.getId())
            category = Fee.Category.RentOrPropertyTaxFee;
        else if (categoryCheckedId == binding.addInsuranceFeeButton.getId())
            category = Fee.Category.InsuranceFee;
        else if (categoryCheckedId == binding.addServiceFeeButton.getId())
            category = Fee.Category.ServiceFee;
        else if (categoryCheckedId == binding.addOtherFeeButton.getId())
            category = Fee.Category.OtherFee;
        else
            category = Fee.Category.MembershipFee;

        // Make Fee & Add Fee & Save Fee
        Fee fee = new Fee(title, chargeRateCost, chargeRate, category);
        mainActivity.getFees().add(fee);

        return false;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}