package com.example.mommyhealthapp.Nurse.ui.CreateMother;

import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;

import com.example.mommyhealthapp.R;
import com.google.android.material.textfield.TextInputLayout;

import org.w3c.dom.Text;

public class CreateMotherFragment extends Fragment {
    private Button btnNextStep;
    private RadioGroup radioGroupRace;
    private Spinner spinnerNational;
    private RadioButton radioBtnMalay, radioBtnChinese, radioBtnIndian, radioBtnOtherRaces;
    private TextInputLayout txtIinputLayoutOtherRace, firstNameLayout, lastNameLayout, IClayout, phoneLayout, occupationLayout, passwordLayout, confirmpassLayout;
    private EditText fistNameEdiTtext, lastNameEditText, ICEditText, phoneEditTex, occupationEditText, passwordEditText, confirmPassEditText;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_create_mummy, container, false);
        btnNextStep = (Button)root.findViewById(R.id.btnNextStep);
        radioGroupRace = (RadioGroup)root.findViewById(R.id.radioGroupRace);
        radioBtnMalay = (RadioButton)root.findViewById(R.id.radioBtnMalay);
        radioBtnChinese = (RadioButton)root.findViewById(R.id.radioBtnChinese);
        radioBtnOtherRaces = (RadioButton)root.findViewById(R.id.radioBtnOtherRace);
        txtIinputLayoutOtherRace = (TextInputLayout)root.findViewById(R.id.txtIinputLayoutOtherRace);
        firstNameLayout = (TextInputLayout)root.findViewById(R.id.firstNameLayout);
        fistNameEdiTtext = (EditText)root.findViewById(R.id.firstNameEditText);
        lastNameLayout = (TextInputLayout)root.findViewById(R.id.lastNameLayout);
        lastNameEditText = (EditText)root.findViewById(R.id.lastNameEditText);
        IClayout = (TextInputLayout)root.findViewById(R.id.ICLayout);
        ICEditText = (EditText)root.findViewById(R.id.ICEditText);
        phoneLayout = (TextInputLayout)root.findViewById(R.id.phoneLayout);
        phoneEditTex = (EditText)root.findViewById(R.id.phoneEditText);
        occupationLayout = (TextInputLayout)root.findViewById(R.id.occupationLayout);
        occupationEditText = (EditText)root.findViewById(R.id.occupationEditText);
        passwordLayout = (TextInputLayout)root.findViewById(R.id.passwordLayout);
        passwordEditText = (EditText)root.findViewById(R.id.passwordEditText);
        confirmpassLayout = (TextInputLayout)root.findViewById(R.id.confirmpassLayout);
        confirmPassEditText = (EditText)root.findViewById(R.id.confirmPassEditText);
        spinnerNational = (Spinner)root.findViewById(R.id.spinnerNational);

        final ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this.getActivity() ,R.array.national, R.layout.support_simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spinnerNational.setAdapter(adapter);

        EnableConfirmPassword();
        checkRequiredTextChange();

        btnNextStep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(checkRequiredFieldNextBtn() == false)
                {
                    Navigation.findNavController(v).navigate(R.id.createMotherDetailFragment);
                }
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

    private void checkRequiredTextChange()
    {
        fistNameEdiTtext.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(fistNameEdiTtext.getText().toString().equals(""))
                {
                    firstNameLayout.setErrorEnabled(true);
                    firstNameLayout.setError("This field is required!");
                }else{
                    firstNameLayout.setErrorEnabled(false);
                    firstNameLayout.setError(null);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        lastNameEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(lastNameEditText.getText().toString().equals(""))
                {
                    lastNameLayout.setErrorEnabled(true);
                    lastNameLayout.setError("This field is required!");
                }else{
                    lastNameLayout.setErrorEnabled(false);
                    lastNameLayout.setError(null);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        ICEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(ICEditText.getText().toString().equals(""))
                {
                    IClayout.setErrorEnabled(true);
                    IClayout.setError("This field is required!");
                }else{
                    IClayout.setErrorEnabled(false);
                    IClayout.setError(null);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        phoneEditTex.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(phoneEditTex.getText().toString().equals(""))
                {
                    phoneLayout.setErrorEnabled(true);
                    phoneLayout.setError("This field is required!");
                }else{
                    phoneLayout.setErrorEnabled(false);
                    phoneLayout.setError(null);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        occupationEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(occupationEditText.getText().toString().equals(""))
                {
                    occupationLayout.setErrorEnabled(true);
                    occupationLayout.setError("This field is required!");
                }else{
                    occupationLayout.setErrorEnabled(false);
                    occupationLayout.setError(null);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        passwordEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(passwordEditText.getText().toString().equals(""))
                {
                    passwordLayout.setErrorEnabled(true);
                    passwordLayout.setError("This field is required!");
                    confirmPassEditText.setEnabled(false);
                    confirmPassEditText.setTextColor(Color.parseColor("#000000"));
                }else{
                    passwordLayout.setErrorEnabled(false);
                    passwordLayout.setError(null);
                    confirmPassEditText.setEnabled(true);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        confirmPassEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(confirmPassEditText.getText().toString().equals(""))
                {
                    confirmpassLayout.setErrorEnabled(true);
                    confirmpassLayout.setError("This field is required!");
                }else{
                    confirmpassLayout.setErrorEnabled(false);
                    confirmpassLayout.setError(null);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private boolean checkRequiredFieldNextBtn()
    {
        boolean empty = true;
        if(fistNameEdiTtext.getText().toString().equals(""))
        {
            firstNameLayout.setErrorEnabled(true);
            firstNameLayout.setError("This field is required!");
            empty = true;
        }else{
            firstNameLayout.setErrorEnabled(false);
            firstNameLayout.setError(null);
            empty = false;
        }

        if(lastNameEditText.getText().toString().equals(""))
        {
            lastNameLayout.setErrorEnabled(true);
            lastNameLayout.setError("This field is required!");
            empty = true;
        }else{
            lastNameLayout.setErrorEnabled(false);
            lastNameLayout.setError(null);
            empty = false;
        }

        if(ICEditText.getText().toString().equals(""))
        {
            IClayout.setErrorEnabled(true);
            IClayout.setError("This field is required!");
            empty = true;
        }else{
            IClayout.setErrorEnabled(false);
            IClayout.setError(null);
            empty = false;
        }

        if(phoneEditTex.getText().toString().equals(""))
        {
            phoneLayout.setErrorEnabled(true);
            phoneLayout.setError("This field is required!");
            empty = true;
        }else{
            phoneLayout.setErrorEnabled(false);
            phoneLayout.setError(null);
            empty = false;
        }

        if(occupationEditText.getText().toString().equals(""))
        {
            occupationLayout.setErrorEnabled(true);
            occupationLayout.setError("This field is required!");
            empty = true;
        }else{
            occupationLayout.setErrorEnabled(false);
            occupationLayout.setError(null);
            empty = false;
        }

        if(passwordEditText.getText().toString().equals(""))
        {
            passwordLayout.setErrorEnabled(true);
            passwordLayout.setError("This field is required!");
            empty = true;
        }else{
            passwordLayout.setErrorEnabled(false);
            passwordLayout.setError(null);
            empty = false;

            if(confirmPassEditText.getText().toString().equals(""))
            {
                confirmpassLayout.setErrorEnabled(true);
                confirmpassLayout.setError("This field is required!");
                empty = true;
            }else{
                confirmpassLayout.setErrorEnabled(false);
                confirmpassLayout.setError(null);
                empty = false;
            }
        }

        return empty;
    }

    private void EnableConfirmPassword()
    {
        if(passwordEditText.getText().toString().equals(""))
        {
            confirmPassEditText.setEnabled(false);
            confirmPassEditText.setTextColor(Color.parseColor("#000000"));
        }else{
            confirmPassEditText.setEnabled(true);
        }
    }

}