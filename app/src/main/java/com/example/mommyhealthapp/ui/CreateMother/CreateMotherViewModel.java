package com.example.mommyhealthapp.ui.CreateMother;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class CreateMotherViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public CreateMotherViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is gallery fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}