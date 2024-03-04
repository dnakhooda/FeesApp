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

import com.example.feesapp.Fee;
import com.example.feesapp.MainActivity;
import com.example.feesapp.R;
import com.example.feesapp.databinding.FragmentEditBinding;
import com.example.feesapp.databinding.FragmentOverviewBinding;

import java.util.ArrayList;

public class EditFragment extends Fragment {
    private MainActivity mainActivity;
    private FragmentEditBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Get Edit View Model
        EditViewModel viewModel = new ViewModelProvider(this).get(EditViewModel.class);
        binding = FragmentEditBinding.inflate(inflater, container, false);
        mainActivity = MainActivity.instance;

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
        String feeString = mainActivity.currencyToSymbol(mainActivity.getCurrency()) + fee.getAmountAsString() + " " + Fee.changeChargeRateToString(fee.getChargeRate());
        cost.setText(feeString);

        editLinearLayout.addView(inflatedLayout);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}