package com.example.mommyhealthapp.ui.home;

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

import com.example.mommyhealthapp.R;
import com.example.mommyhealthapp.ui.CreateMother.CreateMotherFragment;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private CardView cardViewCreate;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        cardViewCreate = (CardView)root.findViewById(R.id.cardViewCreate);

        cardViewCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                Fragment createFragement = new CreateMotherFragment();
                ft.replace(R.id.nav_host_fragment, createFragement);
                ft.commit();
            }
        });

        return root;
    }
}