package com.example.mommyhealthapp.ui.CreateMother;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.mommyhealthapp.R;

public class CreateMotherFragment extends Fragment {

    private CreateMotherViewModel galleryViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        galleryViewModel =
                ViewModelProviders.of(this).get(CreateMotherViewModel.class);
        View root = inflater.inflate(R.layout.fragment_create_mummy, container, false);

        return root;
    }
}