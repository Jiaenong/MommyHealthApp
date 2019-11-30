package com.example.mommyhealthapp.Mommy.ui.PregnancyRecord;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class PregnancyRecordViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public PregnancyRecordViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is dashboard fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}