package com.example.feesapp.ui.simulateExpenses;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SimulateExpensesViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public SimulateExpensesViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is Simulate Expenses fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}
