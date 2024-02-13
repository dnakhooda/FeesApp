package com.example.feesapp.ui.calcIncome;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.feesapp.databinding.FragmentCalcincomeBinding;

public class CalcIncomeFragment extends Fragment {

    private FragmentCalcincomeBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        CalcIncomeViewModel notificationsViewModel =
                new ViewModelProvider(this).get(CalcIncomeViewModel.class);

        binding = FragmentCalcincomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textNotifications;
        notificationsViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}