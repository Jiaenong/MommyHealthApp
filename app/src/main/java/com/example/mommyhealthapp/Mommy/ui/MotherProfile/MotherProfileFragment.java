package com.example.mommyhealthapp.Mommy.ui.MotherProfile;

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

public class MotherProfileFragment extends Fragment {

    private MotherProfileModel motherProfileModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        motherProfileModel =
                ViewModelProviders.of(this).get(MotherProfileModel.class);
        View root = inflater.inflate(R.layout.fragment_mother_profile, container, false);
        final TextView textView = root.findViewById(R.id.text_home);
        motherProfileModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }
}