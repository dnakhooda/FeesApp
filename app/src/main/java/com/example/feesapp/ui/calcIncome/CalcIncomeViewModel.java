package com.example.feesapp.ui.calcIncome;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class CalcIncomeViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public CalcIncomeViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is Income fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}