package com.example.mommyhealthapp.Mommy.ui.MotherProfile;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MotherProfileModel extends ViewModel {

    private MutableLiveData<String> mText;

    public MotherProfileModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is home fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}