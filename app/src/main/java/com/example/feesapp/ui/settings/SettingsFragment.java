package com.example.feesapp.ui.settings;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.feesapp.MainActivity;
import com.example.feesapp.databinding.FragmentSettingsBinding;

import java.util.Currency;

public class SettingsFragment extends Fragment {
    public enum Currency {
        USDollar,
        Euro,
        Yen,
        Sterling,
        Renminbi,
        AustralianDollar,
        CanadianDollar,
        SwissFranc,
        HongKongDollar,
        SingaporeDollar,
    }
    private FragmentSettingsBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Get Settings View Model
        SettingsViewModel viewModel = new ViewModelProvider(this).get(SettingsViewModel.class);

        binding = FragmentSettingsBinding.inflate(inflater, container, false);
        MainActivity.instance.bringBackViewBar();
        View root = binding.getRoot();

        setCurrentCheck(binding);
        binding.settingsCurrencyGroup.setOnCheckedChangeListener(this::onCheck);

        return root;
    }

    private void setCurrentCheck(FragmentSettingsBinding binding) {
        switch (MainActivity.instance.getSettings().getCurrencyType()) {
            case USDollar:
                binding.currencyButtonUsDollar.setChecked(true);
                break;
            case Euro:
                binding.currencyButtonEuro.setChecked(true);
                break;
            case Yen:
                binding.currencyButtonYen.setChecked(true);
                break;
            case Sterling:
                binding.currencyButtonSterling.setChecked(true);
                break;
            case Renminbi:
                binding.currencyButtonRenminbi.setChecked(true);
                break;
            case AustralianDollar:
                binding.currencyButtonAusDollar.setChecked(true);
                break;
            case CanadianDollar:
                binding.currencyButtonCanadianDollar.setChecked(true);
                break;
            case SwissFranc:
                binding.currencyButtonSwissDollar.setChecked(true);
                break;
            case HongKongDollar:
                binding.currencyButtonHkDollar.setChecked(true);
                break;
            case SingaporeDollar:
                binding.currencyButtonSgDollar.setChecked(true);
                break;
        }
    }

    private void onCheck(RadioGroup group, int checkedId) {
        RadioButton checkedRadioButton = group.findViewById(checkedId);

        if (!checkedRadioButton.isChecked())
            return;

        if (checkedRadioButton.getId() == binding.currencyButtonUsDollar.getId()) {
            MainActivity.instance.getSettings().setCurrencyType(Currency.USDollar);
            return;
        }

        if (checkedRadioButton.getId() == binding.currencyButtonEuro.getId()) {
            MainActivity.instance.getSettings().setCurrencyType(Currency.Euro);
            return;
        }

        if (checkedRadioButton.getId() == binding.currencyButtonYen.getId()) {
            MainActivity.instance.getSettings().setCurrencyType(Currency.Yen);
            return;
        }

        if (checkedRadioButton.getId() == binding.currencyButtonSterling.getId()) {
            MainActivity.instance.getSettings().setCurrencyType(Currency.Sterling);
            return;
        }

        if (checkedRadioButton.getId() == binding.currencyButtonRenminbi.getId()) {
            MainActivity.instance.getSettings().setCurrencyType(Currency.Renminbi);
            return;
        }

        if (checkedRadioButton.getId() == binding.currencyButtonAusDollar.getId()) {
            MainActivity.instance.getSettings().setCurrencyType(Currency.AustralianDollar);
            return;
        }

        if (checkedRadioButton.getId() == binding.currencyButtonCanadianDollar.getId()) {
            MainActivity.instance.getSettings().setCurrencyType(Currency.CanadianDollar);
            return;
        }

        if (checkedRadioButton.getId() == binding.currencyButtonSwissDollar.getId()) {
            MainActivity.instance.getSettings().setCurrencyType(Currency.SwissFranc);
            return;
        }

        if (checkedRadioButton.getId() == binding.currencyButtonHkDollar.getId()) {
            MainActivity.instance.getSettings().setCurrencyType(Currency.HongKongDollar);
            return;
        }

        MainActivity.instance.getSettings().setCurrencyType(Currency.SingaporeDollar);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}