package com.example.feesapp.ui.editItem;

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
import com.example.feesapp.databinding.FragmentEditItemBinding;

public class EditItemFragment extends Fragment {

    private FragmentEditItemBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Get Edit Item View Model
        EditItemViewModel viewModel = new ViewModelProvider(this).get(EditItemViewModel.class);

        binding = FragmentEditItemBinding.inflate(inflater, container, false);
        MainActivity.instance.removeNavBar();
        View root = binding.getRoot();
        NavController navController = Navigation.findNavController(MainActivity.instance, R.id.nav_host_fragment_activity_main);

        binding.editChangeButton.setOnClickListener(view -> {
            if (changeFee())
                return;

            navController.popBackStack();
        });

        binding.editCancelButton.setOnClickListener(view -> {
            navController.popBackStack();
        });

        binding.editDeleteButton.setOnClickListener(view -> {
            MainActivity.instance.removeFeeByTitle(MainActivity.instance.getFeeToEdit().getTitle());
            navController.popBackStack();
        });

        setInputs();

        return root;
    }

    private void setInputs() {
        Fee fee = MainActivity.instance.getFeeToEdit();

        binding.editTitleField.setText( fee.getTitle() );

        binding.editAmountField.setText( String.format( Double.toString( fee.getAmount() ) ) );

        if (fee.getChargeRate().equals(Fee.ChargeRate.daily))
            binding.editButtonDaily.setChecked(true);
        else if (fee.getChargeRate().equals(Fee.ChargeRate.weekly))
            binding.editButtonWeekly.setChecked(true);
        else if ((fee.getChargeRate().equals(Fee.ChargeRate.monthly)))
            binding.editButtonMonthly.setChecked(true);
        else
            binding.editButtonYearly.setChecked(true);

        if (fee.getCategory().equals(Fee.FeesCategory.ServiceFee))
            binding.editServiceFeeButton.setChecked(true);
        else if (fee.getCategory().equals(Fee.FeesCategory.ApplicationFee))
            binding.editApplicationFeeButton.setChecked(true);
        else if (fee.getCategory().equals(Fee.FeesCategory.MembershipFee))
            binding.editMembershipFeeButton.setChecked(true);
        else
            binding.editGovernmentFeeButton.setChecked(true);
    }

    private boolean changeFee() {
        String title = binding.editTitleField.getText().toString();
        if (title.length() < 1) {
            Toast.makeText(MainActivity.instance, "Title Must Be At Least One Character!", Toast.LENGTH_LONG).show();
            return true;
        }

        if (title.length() > 9) {
            Toast.makeText(MainActivity.instance, "Title Must Be Less Than Ten Characters!", Toast.LENGTH_LONG).show();
            return true;
        }

        if (MainActivity.instance.getFeeByTitle(title) != null && !MainActivity.instance.getFeeByTitle(title).equals(MainActivity.instance.getFeeToEdit())) {
            Toast.makeText(MainActivity.instance, "Title Unique! No Two Fees Should Have The Same Title!", Toast.LENGTH_LONG).show();
            return true;
        }

        double amount = 0;
        try {
            amount = Double.parseDouble(binding.editAmountField.getText().toString());
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

        if (binding.editRateGroup.getCheckedRadioButtonId() == -1) {
            Toast.makeText(MainActivity.instance, "A Charge Rate Must Be Selected!", Toast.LENGTH_LONG).show();
            return true;
        }

        int rateCheckedId = MainActivity.instance.findViewById(binding.editRateGroup.getCheckedRadioButtonId()).getId();
        if (rateCheckedId == binding.editButtonDaily.getId())
            chargeRate = Fee.ChargeRate.daily;
        else if (rateCheckedId == binding.editButtonWeekly.getId())
            chargeRate = Fee.ChargeRate.weekly;
        else if (rateCheckedId == binding.editButtonMonthly.getId())
            chargeRate = Fee.ChargeRate.monthly;
        else
            chargeRate = Fee.ChargeRate.yearly;

        if (binding.editCategoryGroup.getCheckedRadioButtonId() == -1) {
            Toast.makeText(MainActivity.instance, "A Category Must Be Selected!", Toast.LENGTH_LONG).show();
            return true;
        }

        Fee.FeesCategory category;

        int categoryCheckedId = MainActivity.instance.findViewById(binding.editCategoryGroup.getCheckedRadioButtonId()).getId();
        if (categoryCheckedId == binding.editApplicationFeeButton.getId())
            category = Fee.FeesCategory.ApplicationFee;
        else if (categoryCheckedId == binding.editGovernmentFeeButton.getId())
            category = Fee.FeesCategory.GovernmentFee;
        else if (categoryCheckedId == binding.editServiceFeeButton.getId())
            category = Fee.FeesCategory.ServiceFee;
        else
            category = Fee.FeesCategory.MembershipFee;

        MainActivity.instance.getFeeToEdit().setTitle(title);
        MainActivity.instance.getFeeToEdit().setAmount(amount);
        MainActivity.instance.getFeeToEdit().setChargeRate(chargeRate);
        MainActivity.instance.getFeeToEdit().setCategory(category);

        MainActivity.instance.saveFees();

        return false;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}