package com.example.mommyhealthapp.Nurse.ui.CreateMother;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;

import com.example.mommyhealthapp.R;
import com.google.android.material.textfield.TextInputLayout;

public class CreateMotherFragment extends Fragment {
    private Button btnNextStep;
    private RadioGroup radioGroupRace;
    private RadioButton radioBtnMalay, radioBtnChinese, radioBtnIndian, radioBtnOtherRaces;
    private TextInputLayout txtIinputLayoutOtherRace;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_create_mummy, container, false);
        btnNextStep = (Button)root.findViewById(R.id.btnNextStep);
        radioGroupRace = (RadioGroup)root.findViewById(R.id.radioGroupRace);
        radioBtnMalay = (RadioButton)root.findViewById(R.id.radioBtnMalay);
        radioBtnChinese = (RadioButton)root.findViewById(R.id.radioBtnChinese);
        radioBtnOtherRaces = (RadioButton)root.findViewById(R.id.radioBtnOtherRace);
        txtIinputLayoutOtherRace = (TextInputLayout)root.findViewById(R.id.txtIinputLayoutOtherRace);

        btnNextStep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.createMotherDetailFragment);
            }
        });

        radioGroupRace.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(radioBtnOtherRaces.isChecked())
                {
                    txtIinputLayoutOtherRace.setVisibility(getView().VISIBLE);
                }else{
                    txtIinputLayoutOtherRace.setVisibility(getView().GONE);
                }
            }
        });

        return root;
    }
}