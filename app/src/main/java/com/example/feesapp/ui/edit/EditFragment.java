package com.example.feesapp.ui.edit;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.feesapp.Fee;
import com.example.feesapp.MainActivity;
import com.example.feesapp.R;
import com.example.feesapp.databinding.FragmentEditBinding;

public class EditFragment extends Fragment {

    private FragmentEditBinding binding;
    private MainActivity mainActivity;
    private NavController navController;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Get Binding
        binding = FragmentEditBinding.inflate(inflater, container, false);

        // Set Main Activity Reference & Add Navigation Bar
        mainActivity = MainActivity.instance;
        mainActivity.bringBackNavBar();

        navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_activity_main);

        // Set Add Fee Button Click Event Listener
        binding.addFeeButtonEdit.setOnClickListener(view -> navController.navigate(R.id.action_navigation_edit_to_navigation_add_item));

        // Inflate Layouts For Each Fee
        mainActivity.getFees().forEach(this::makeEditLayout);

        return binding.getRoot();
    }

    private void makeEditLayout(Fee fee) {
        // Get Main Edit Page Linear Layout
        LinearLayout linearLayout = binding.linearLayoutEdit;

        // Inflate Edit Fee Item Layout
        LayoutInflater inflater = mainActivity.getLayoutInflater();
        View inflatedLayout = inflater.inflate(R.layout.item_edit_fee, linearLayout, false);

        // Set Title
        TextView title = inflatedLayout.findViewById(R.id.edit_fee_title);
        title.setText(fee.getTitle());

        // Set Category
        TextView category = inflatedLayout.findViewById(R.id.edit_fee_category);
        category.setText(Fee.changeFeeCategoryToString(fee.getCategory()));

        // Set Charge Rate
        TextView cost = inflatedLayout.findViewById(R.id.edit_fee_cost);
        String feeString = mainActivity.currencyToSymbol(mainActivity.getSettings().getCurrencyType()) + Fee.numberToStringWithTwoDecimals(fee.getAmount()) + " " + Fee.changeChargeRateToString(fee.getChargeRate());
        cost.setText(feeString);

        // Set Edit Button Click Event Listener
        inflatedLayout.findViewById(R.id.edit_fee_button).setOnClickListener(view -> {
            mainActivity.setFeeToEdit(fee);
            navController.navigate(R.id.action_navigation_edit_to_navigation_edit_item);
        });

        // Add Inflated Layout To Linear Layout
        linearLayout.addView(inflatedLayout);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}