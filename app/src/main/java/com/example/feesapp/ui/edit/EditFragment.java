package com.example.feesapp.ui.edit;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import com.example.feesapp.Fee;
import com.example.feesapp.MainActivity;
import com.example.feesapp.R;
import com.example.feesapp.databinding.FragmentEditBinding;
import com.example.feesapp.databinding.FragmentOverviewBinding;
import com.example.feesapp.ui.addItem.AddItemFragment;

import java.util.ArrayList;
import java.util.Objects;

public class EditFragment extends Fragment {
    private MainActivity mainActivity;
    private FragmentEditBinding binding;
    private NavController navController;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Get Edit View Model
        EditViewModel viewModel = new ViewModelProvider(this).get(EditViewModel.class);
        binding = FragmentEditBinding.inflate(inflater, container, false);
        mainActivity = MainActivity.instance;
        mainActivity.bringBackViewBar();
        navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_activity_main);

        binding.addFeeButtonEdit.setOnClickListener(view -> navController.navigate(R.id.action_navigation_edit_to_navigation_add_item));

        makeEditLayouts(mainActivity.getFees());

        return binding.getRoot();
    }

    private void makeEditLayouts(ArrayList<Fee> fees) {
        fees.forEach(this::makeEditLayout);
    }

    private void makeEditLayout(Fee fee) {
        LinearLayout editLinearLayout = binding.linearLayoutEdit;
        LayoutInflater inflater = mainActivity.getLayoutInflater();
        View inflatedLayout = inflater.inflate(R.layout.edit_fee, editLinearLayout, false);

        TextView title = inflatedLayout.findViewById(R.id.edit_fee_title);
        title.setText(fee.getTitle());

        TextView category = inflatedLayout.findViewById(R.id.edit_fee_category);
        category.setText(Fee.changeFeeCategoryToString(fee.getCategory()));

        TextView cost = inflatedLayout.findViewById(R.id.edit_fee_cost);
        String feeString = mainActivity.currencyToSymbol(mainActivity.getSettings().getCurrencyType()) + Fee.numberToStringWithTwoDecimals(fee.getAmount()) + " " + Fee.changeChargeRateToString(fee.getChargeRate());
        cost.setText(feeString);

        inflatedLayout.findViewById(R.id.edit_fee_button).setOnClickListener(view -> {
            MainActivity.instance.setFeeToEdit(fee);
            navController.navigate(R.id.action_navigation_edit_to_navigation_edit_item);
        });

        editLinearLayout.addView(inflatedLayout);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}