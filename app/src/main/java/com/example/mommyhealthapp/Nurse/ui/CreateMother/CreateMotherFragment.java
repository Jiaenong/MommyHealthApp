package com.example.mommyhealthapp.Nurse.ui.CreateMother;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
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
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;

import com.example.mommyhealthapp.Class.Mommy;
import com.example.mommyhealthapp.R;
import com.example.mommyhealthapp.SaveSharedPreference;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import org.w3c.dom.Text;

import java.io.ByteArrayOutputStream;

public class CreateMotherFragment extends Fragment {
    private Button btnNextStep;
    private RadioGroup radioGroupRace;
    private Spinner spinnerNational;
    private RadioButton radioBtnOtherRaces, radioBtnMalay, radioBtnChinese, radioBtnIndian;
    private TextInputLayout txtIinputLayoutOtherRace, firstNameLayout, lastNameLayout, IClayout, phoneLayout, occupationLayout, passwordLayout, confirmpassLayout, ageLayout;
    private EditText fistNameEdiTtext, lastNameEditText, ICEditText, phoneEditTex, occupationEditText, passwordEditText, confirmPassEditText, otherRaceEditText, addressEditText, ageEditText, educationEditText;
    private String mommyId, qrcode;
    private int mommyNumber;

    private FirebaseFirestore mFirebaseFirestore;
    private CollectionReference mCollectionReference;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        final View root = inflater.inflate(R.layout.fragment_create_mummy, container, false);
        btnNextStep = (Button)root.findViewById(R.id.btnNextStep);
        radioGroupRace = (RadioGroup)root.findViewById(R.id.radioGroupRace);
        radioBtnOtherRaces = (RadioButton)root.findViewById(R.id.radioBtnOtherRace);
        radioBtnMalay = (RadioButton)root.findViewById(R.id.radioBtnMalay);
        radioBtnChinese = (RadioButton)root.findViewById(R.id.radioBtnChinese);
        radioBtnIndian = (RadioButton)root.findViewById(R.id.radioBtnIndian);
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
        otherRaceEditText = (EditText)root.findViewById(R.id.otherRaceEditText);
        addressEditText = (EditText)root.findViewById(R.id.addressEditText);
        ageLayout = (TextInputLayout)root.findViewById(R.id.ageLayout);
        ageEditText = (EditText)root.findViewById(R.id.ageEditText);
        educationEditText = (EditText)root.findViewById(R.id.educationEditText);
        confirmpassLayout = (TextInputLayout)root.findViewById(R.id.confirmpassLayout);
        confirmPassEditText = (EditText)root.findViewById(R.id.confirmPassEditText);
        spinnerNational = (Spinner)root.findViewById(R.id.spinnerNational);

        final ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this.getActivity() ,R.array.national, R.layout.support_simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spinnerNational.setAdapter(adapter);

        mFirebaseFirestore = FirebaseFirestore.getInstance();
        mCollectionReference = mFirebaseFirestore.collection("Mommy");

        mCollectionReference.orderBy("mommyNumber", Query.Direction.DESCENDING).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                if(queryDocumentSnapshots.isEmpty())
                {
                    mommyNumber = 1;
                    mommyId = "M1";
                }else{
                    for(QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots)
                    {
                        Mommy mommy = documentSnapshot.toObject(Mommy.class);
                        int num = Integer.parseInt(mommy.getMommyId().substring(1));
                        ++num;
                        mommyId = "M"+num;
                        mommyNumber = mommy.getMommyNumber();
                        ++mommyNumber;
                        break;
                    }
                }
            }
        });

        EnableConfirmPassword();
        checkRequiredTextChange();

        btnNextStep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                if(checkRequiredFieldNextBtn() == false)
                {
                    String firstName = fistNameEdiTtext.getText().toString();
                    String lastName = lastNameEditText.getText().toString();
                    String mommyName = firstName + " " + lastName;
                    String ic = ICEditText.getText().toString();
                    String nationality = spinnerNational.getSelectedItem().toString();
                    int radioButtonID = radioGroupRace.getCheckedRadioButtonId();
                    RadioButton RadioButtonRace = (RadioButton) root.findViewById(radioButtonID);
                    String selectedRadioButton = RadioButtonRace.getText().toString();
                    if(selectedRadioButton.equals("Other"))
                    {
                        selectedRadioButton = otherRaceEditText.getText().toString();
                    }
                    String address = addressEditText.getText().toString();
                    String phoneNum = phoneEditTex.getText().toString();
                    String occupation = occupationEditText.getText().toString();
                    int age = Integer.parseInt(ageEditText.getText().toString());
                    String education = educationEditText.getText().toString();
                    String confirmPass = confirmPassEditText.getText().toString();
                    String status = "Active";
                    String imagePic = "";
                    qrcode = "";
                    SaveSharedPreference.setMommyId(getActivity(), mommyId);

                    Mommy mommy = new Mommy(mommyName,ic,nationality,selectedRadioButton,address,phoneNum,occupation,age,education,confirmPass, mommyId, mommyNumber,status,imagePic,qrcode);

                    Bundle bundle = new Bundle();
                    bundle.putParcelable("mommyinfo", mommy);
                    Navigation.findNavController(v).navigate(R.id.createMotherDetailFragment, bundle);
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
                if(confirmPassEditText.getText().toString().equals(passwordEditText.getText().toString()))
                {
                    confirmpassLayout.setError(null);
                }else{
                    confirmpassLayout.setError("Confirmation password are not same with the password");
                }
            }
        });

        ageEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if(ageEditText.getText().toString().equals(""))
                {
                    ageLayout.setErrorEnabled(true);
                    ageLayout.setError("This field is required!");
                }else {
                    ageLayout.setErrorEnabled(false);
                    ageLayout.setError(null);
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
                if(!(confirmPassEditText.getText().toString().equals(passwordEditText.getText().toString())))
                {
                    confirmpassLayout.setErrorEnabled(true);
                    confirmpassLayout.setError("Confirmation password are not same with the password");
                    empty = true;
                }else{
                    confirmpassLayout.setErrorEnabled(false);
                    confirmpassLayout.setError(null);
                    empty = false;
                }
            }
        }

        if(ageEditText.getText().toString().equals(""))
        {
            ageLayout.setErrorEnabled(true);
            ageLayout.setError("This field is required!");
            empty = true;
        }else {
            ageLayout.setErrorEnabled(false);
            ageLayout.setError(null);
            empty = false;
        }

        if(radioGroupRace.getCheckedRadioButtonId() == -1)
        {
            radioBtnMalay.setError("Required!");
            radioBtnChinese.setError("Required!");
            radioBtnIndian.setError("Required!");
            radioBtnOtherRaces.setError("Required!");
            empty = true;
        }else{
            radioBtnMalay.setError(null);
            radioBtnChinese.setError(null);
            radioBtnIndian.setError(null);
            radioBtnOtherRaces.setError(null);
            empty = false;
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