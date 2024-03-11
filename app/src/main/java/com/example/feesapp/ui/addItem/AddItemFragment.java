package com.example.feesapp.ui.addItem;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.feesapp.Fee;
import com.example.feesapp.MainActivity;
import com.example.feesapp.R;
import com.example.feesapp.databinding.FragmentAddItemBinding;

public class AddItemFragment extends Fragment {

    private FragmentAddItemBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Get Add Item View Model
        AddItemViewModel viewModel = new ViewModelProvider(this).get(AddItemViewModel.class);

        binding = FragmentAddItemBinding.inflate(inflater, container, false);
        MainActivity.instance.removeNavBar();
        View root = binding.getRoot();

        binding.addAddButton.setOnClickListener(view -> {
            if (addFee())
                return;

            NavController navController = Navigation.findNavController(MainActivity.instance, R.id.nav_host_fragment_activity_main);
            navController.popBackStack();
        });

        binding.addCancelButton.setOnClickListener(view -> {
            NavController navController = Navigation.findNavController(MainActivity.instance, R.id.nav_host_fragment_activity_main);
            navController.popBackStack();
        });

        return root;
    }

    private boolean addFee() {
        String title = binding.addTitleField.getText().toString();
        if (title.length() < 1) {
            Toast.makeText(MainActivity.instance, "Title Must Be At Least One Character!", Toast.LENGTH_LONG).show();
            return true;
        }

        if (title.length() > 9) {
            Toast.makeText(MainActivity.instance, "Title Must Be Less Than Ten Characters!", Toast.LENGTH_LONG).show();
            return true;
        }

        if (MainActivity.instance.getFeeByTitle(title) != null) {
            Toast.makeText(MainActivity.instance, "Title Unique! No Two Fees Should Have The Same Title!", Toast.LENGTH_LONG).show();
            return true;
        }

        double amount = 0;
        try {
            amount = Double.parseDouble(binding.addAmountField.getText().toString());
        }
        catch (NumberFormatException exception) {
            Toast.makeText(MainActivity.instance, "Illegal Input For Charge Rate Cost!", Toast.LENGTH_LONG).show();
            return true;
        }

        if ((amount * 100) % 1 > 0) {
            Toast.makeText(MainActivity.instance, "Charge Rate Cost Cannot Have More Than Two Decimal Places!", Toast.LENGTH_LONG).show();
            return true;
        }

        if (amount > 10000000) {
            Toast.makeText(MainActivity.instance, "Charge Rate Cost Is Too Large Of A Value!", Toast.LENGTH_LONG).show();
            return true;
        }

        Fee.ChargeRate chargeRate;

        if (binding.addRateGroup.getCheckedRadioButtonId() == -1) {
            Toast.makeText(MainActivity.instance, "A Charge Rate Must Be Selected!", Toast.LENGTH_LONG).show();
            return true;
        }

        int rateCheckedId = MainActivity.instance.findViewById(binding.addRateGroup.getCheckedRadioButtonId()).getId();
        if (rateCheckedId == binding.addButtonDaily.getId())
            chargeRate = Fee.ChargeRate.daily;
        else if (rateCheckedId == binding.addButtonWeekly.getId())
            chargeRate = Fee.ChargeRate.weekly;
        else if (rateCheckedId == binding.addButtonMonthly.getId())
            chargeRate = Fee.ChargeRate.monthly;
        else
            chargeRate = Fee.ChargeRate.yearly;

        if (binding.addCategoryGroup.getCheckedRadioButtonId() == -1) {
            Toast.makeText(MainActivity.instance, "A Category Must Be Selected!", Toast.LENGTH_LONG).show();
            return true;
        }

        Fee.FeesCategory category;

        int categoryCheckedId = MainActivity.instance.findViewById(binding.addCategoryGroup.getCheckedRadioButtonId()).getId();
        if (categoryCheckedId == binding.addRentFeeButton.getId())
            category = Fee.FeesCategory.RentOrPropertyTaxFee;
        else if (categoryCheckedId == binding.addInsuranceFeeButton.getId())
            category = Fee.FeesCategory.InsuranceFee;
        else if (categoryCheckedId == binding.addServiceFeeButton.getId())
            category = Fee.FeesCategory.ServiceFee;
        else if (categoryCheckedId == binding.addOtherFeeButton.getId())
            category = Fee.FeesCategory.OtherFee;
        else
            category = Fee.FeesCategory.MembershipFee;

        Fee fee = new Fee(title, amount, chargeRate, category);
        MainActivity.instance.getFees().add(fee);

        MainActivity.instance.saveFees();

        return false;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}