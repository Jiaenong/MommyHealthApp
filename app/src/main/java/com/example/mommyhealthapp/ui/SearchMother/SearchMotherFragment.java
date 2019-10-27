package com.example.mommyhealthapp.ui.SearchMother;

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

import com.example.mommyhealthapp.MommyProfileActivity;
import com.example.mommyhealthapp.R;

public class SearchMotherFragment extends Fragment {

    private ImageView imageViewQRCode;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_search_mummy, container, false);
        imageViewQRCode = (ImageView)root.findViewById(R.id.imageViewQRCode);

        imageViewQRCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MommyProfileActivity.class);
                startActivity(intent);
            }
        });

        return root;
    }
}