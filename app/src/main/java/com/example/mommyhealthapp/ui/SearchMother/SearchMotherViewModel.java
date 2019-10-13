package com.example.mommyhealthapp.ui.SearchMother;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SearchMotherViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public SearchMotherViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is slideshow fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}