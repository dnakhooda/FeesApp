package com.example.feesapp.ui.addItem;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class AddItemViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public AddItemViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is Add Item fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}