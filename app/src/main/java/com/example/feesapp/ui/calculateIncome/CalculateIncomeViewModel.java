package com.example.feesapp.ui.calculateIncome;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class CalculateIncomeViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public CalculateIncomeViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is Income fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}