package com.example.feesapp.ui.calculateIncome;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.feesapp.MainActivity;
import com.example.feesapp.databinding.FragmentCalculateIncomeBinding;

public class CalculateIncomeFragment extends Fragment {

    private FragmentCalculateIncomeBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Get Calculate Income View Model
        CalculateIncomeViewModel viewModel = new ViewModelProvider(this).get(CalculateIncomeViewModel.class);

        binding = FragmentCalculateIncomeBinding.inflate(inflater, container, false);
        MainActivity.instance.bringBackViewBar();
        View root = binding.getRoot();

        final TextView textView = binding.textCalculateIncome;
        viewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}