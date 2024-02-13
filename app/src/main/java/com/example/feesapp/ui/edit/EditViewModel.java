package com.example.feesapp.ui.edit;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class EditViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public EditViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is Edit fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}