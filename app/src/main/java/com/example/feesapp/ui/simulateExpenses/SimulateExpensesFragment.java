package com.example.feesapp.ui.simulateExpenses;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.feesapp.databinding.FragmentSimulateExpensesBinding;

public class SimulateExpensesFragment extends Fragment {

    private FragmentSimulateExpensesBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        SimulateExpensesViewModel viewModel =
                new ViewModelProvider(this).get(SimulateExpensesViewModel.class);

        binding = FragmentSimulateExpensesBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textSimulateExpenses;
        viewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}