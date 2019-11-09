package com.example.mommyhealthapp.Nurse.ui.SearchMother;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.mommyhealthapp.Nurse.MommyRecordActivity;
import com.example.mommyhealthapp.R;

public class SearchMotherFragment extends Fragment {

    private ImageView imageViewQRCode, imageViewNoRecordFound;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_search_mummy, container, false);
        imageViewQRCode = (ImageView)root.findViewById(R.id.imageViewQRCode);
        imageViewNoRecordFound = (ImageView)root.findViewById(R.id.imageViewNoRecordFound);

        imageViewQRCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MommyRecordActivity.class);
                startActivity(intent);
            }
        });

        imageViewNoRecordFound.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MommyRecordActivity.class);
                startActivity(intent);
            }
        });

        return root;
    }
}