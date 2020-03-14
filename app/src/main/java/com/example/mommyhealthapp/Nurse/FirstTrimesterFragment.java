package com.example.mommyhealthapp.Nurse;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mommyhealthapp.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class FirstTrimesterFragment extends Fragment {

    public FirstTrimesterFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_first_trimester, container, false);
    }
}
