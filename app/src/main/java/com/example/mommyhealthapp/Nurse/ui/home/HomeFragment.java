package com.example.mommyhealthapp.Nurse.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;

import com.example.mommyhealthapp.R;
import com.example.mommyhealthapp.Nurse.ui.CreateMother.CreateMotherFragment;

public class HomeFragment extends Fragment {
    private CardView cardViewCreate, cardViewNurse, cardViewSearch;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        cardViewCreate = (CardView)root.findViewById(R.id.cardViewCreate);
        cardViewNurse = (CardView)root.findViewById(R.id.cardViewNurse);
        cardViewSearch = (CardView)root.findViewById(R.id.cardViewSearch);

        cardViewCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.nav_create);
            }
        });

        cardViewNurse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.nav_nurseprofile);
            }
        });

        cardViewSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.nav_search);
            }
        });

        return root;
    }
}