package com.example.feesapp.ui.settings;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.feesapp.MainActivity;
import com.example.feesapp.databinding.FragmentSettingsBinding;

public class SettingsFragment extends Fragment {

    private FragmentSettingsBinding binding;
    private MainActivity mainActivity;
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

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Get Binding
        binding = FragmentSettingsBinding.inflate(inflater, container, false);

        // Set Main Activity Reference & Add Navigation Bar
        mainActivity = MainActivity.instance;
        mainActivity.bringBackNavBar();

        // Set What Currency Should Be Selected
        setCurrencyChecked();

        // Set On Currency Change Event Listener
        binding.settingsCurrencyGroup.setOnCheckedChangeListener(this::onCheck);

        return binding.getRoot();
    }

    private void setCurrencyChecked() {
        // Get Currency In Settings Object & Set That Currency In Radio Group To Checked
        Currency currency = mainActivity.getSettings().getCurrencyType();

        switch (currency) {
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
        // Get Button That Was Changed & See If It Is Now Checked & Get Settings Object
        RadioButton checkedRadioButton = group.findViewById(checkedId);

        if (!checkedRadioButton.isChecked())
            return;

        Settings settings = mainActivity.getSettings();

        if (checkedRadioButton.getId() == binding.currencyButtonUsDollar.getId()) {
            settings.setCurrencyType(Currency.USDollar);
            return;
        }

        if (checkedRadioButton.getId() == binding.currencyButtonEuro.getId()) {
            settings.setCurrencyType(Currency.Euro);
            return;
        }

        if (checkedRadioButton.getId() == binding.currencyButtonYen.getId()) {
            settings.setCurrencyType(Currency.Yen);
            return;
        }

        if (checkedRadioButton.getId() == binding.currencyButtonSterling.getId()) {
            settings.setCurrencyType(Currency.Sterling);
            return;
        }

        if (checkedRadioButton.getId() == binding.currencyButtonRenminbi.getId()) {
            settings.setCurrencyType(Currency.Renminbi);
            return;
        }

        if (checkedRadioButton.getId() == binding.currencyButtonAusDollar.getId()) {
            settings.setCurrencyType(Currency.AustralianDollar);
            return;
        }

        if (checkedRadioButton.getId() == binding.currencyButtonCanadianDollar.getId()) {
            settings.setCurrencyType(Currency.CanadianDollar);
            return;
        }

        if (checkedRadioButton.getId() == binding.currencyButtonSwissDollar.getId()) {
            settings.setCurrencyType(Currency.SwissFranc);
            return;
        }

        if (checkedRadioButton.getId() == binding.currencyButtonHkDollar.getId()) {
            settings.setCurrencyType(Currency.HongKongDollar);
            return;
        }

        settings.setCurrencyType(Currency.SingaporeDollar);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}