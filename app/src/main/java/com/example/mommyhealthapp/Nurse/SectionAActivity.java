package com.example.mommyhealthapp.Nurse;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.LinearLayoutCompat;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.mommyhealthapp.Class.PreviousPregnant;
import com.example.mommyhealthapp.MainActivity;
import com.example.mommyhealthapp.R;
import com.example.mommyhealthapp.SaveSharedPreference;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Date;
import java.util.UUID;

public class SectionAActivity extends AppCompatActivity {
    private EditText editTextPPYear, editTextPPResult, editTextPPBirthType, editTextPlaceGiveBirth, editTextPPWeight, editTextPPMother,
            editTextPPChild, editTextPPBFP, editTextSituation;
    private TextInputLayout layoutPPYear, layoutPPResult, layoutPPBirthType, layoutPlaceGiveBirth, layoutPPWeight, layoutPPMother, layoutPPChild, layoutPPBFP, layoutSituation;
    private RadioGroup radioGroupPPGender;
    private RadioButton radioButtonPPMale, radioButtonPPFemale;
    private Button btnPPSave, btnPPCancel;
    private ProgressBar progressBarPP;
    private LinearLayoutCompat layoutPP;
    private RelativeLayout relativeLayoutPP;

    private String healthInfoId, bloodTestId, ppId, key;
    private int check = 0;

    private FirebaseFirestore mFirebaseFirestore;
    private CollectionReference mCollectionReference, nCollectionReference;
    private DocumentReference mDocumentReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_section_a);
        editTextPPYear = (EditText)findViewById(R.id.editTextPPYear);
        editTextPPResult = (EditText)findViewById(R.id.editTextPPResult);
        editTextPPBirthType = (EditText)findViewById(R.id.editTextPPBirthType);
        editTextPlaceGiveBirth = (EditText)findViewById(R.id.editTextPlaceGiveBirth);
        editTextPPWeight = (EditText)findViewById(R.id.editTextPPWeight);
        editTextPPMother = (EditText)findViewById(R.id.editTextPPMother);
        editTextPPChild = (EditText)findViewById(R.id.editTextPPChild);
        editTextPPBFP = (EditText)findViewById(R.id.editTextPPBFP);
        editTextSituation = (EditText)findViewById(R.id.editTextSituation);
        layoutPPYear = (TextInputLayout)findViewById(R.id.layoutPPYear);
        layoutPPResult = (TextInputLayout)findViewById(R.id.layoutPPResult);
        layoutPPBirthType = (TextInputLayout)findViewById(R.id.layoutPPBirthType);
        layoutPlaceGiveBirth = (TextInputLayout)findViewById(R.id.layoutPlaceGiveBirth);
        layoutPPWeight = (TextInputLayout)findViewById(R.id.layoutPPWeight);
        layoutPPMother = (TextInputLayout)findViewById(R.id.layoutPPMother);
        layoutPPChild = (TextInputLayout)findViewById(R.id.layoutPPChild);
        layoutPPBFP = (TextInputLayout)findViewById(R.id.layoutPPBFP);
        layoutSituation = (TextInputLayout)findViewById(R.id.layoutSituation);
        radioGroupPPGender = (RadioGroup)findViewById(R.id.radioGroupPPGender);
        radioButtonPPMale = (RadioButton)findViewById(R.id.radioButtonPPMale);
        radioButtonPPFemale = (RadioButton)findViewById(R.id.radioButtonPPFemale);
        btnPPSave = (Button)findViewById(R.id.btnPPSave);
        btnPPCancel = (Button)findViewById(R.id.btnPPCancel);
        progressBarPP = (ProgressBar)findViewById(R.id.progressBarPP);
        layoutPP = (LinearLayoutCompat)findViewById(R.id.layoutPP);
        relativeLayoutPP = (RelativeLayout)findViewById(R.id.relativeLayoutPP);

        Intent intent = getIntent();
        healthInfoId = intent.getStringExtra("healthInfoId");
        bloodTestId = intent.getStringExtra("bloodTestId");
        ppId = intent.getStringExtra("ppId");

        CheckRequiredField();

        mFirebaseFirestore = FirebaseFirestore.getInstance();
        mCollectionReference = mFirebaseFirestore.collection("MommyHealthInfo/"+healthInfoId+"/BloodTest/"+bloodTestId+"/PreviousPregnant");

        btnPPCancel.setVisibility(View.GONE);

        if(SaveSharedPreference.getPreviousPregnant(SectionAActivity.this).equals("Exist")) {
            layoutPP.setVisibility(View.GONE);
            progressBarPP.setVisibility(View.VISIBLE);
            DisableField();

            if(SaveSharedPreference.getUser(SectionAActivity.this).equals("Mommy"))
            {
                MommyLogIn();
            }else{
                mCollectionReference.whereEqualTo("previousPregnantId", ppId).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        for(QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots)
                        {
                            key = documentSnapshot.getId();
                            PreviousPregnant pp = documentSnapshot.toObject(PreviousPregnant.class);
                            editTextPPYear.setText(pp.getYear());
                            editTextPPResult.setText(pp.getPregnantResult());
                            editTextPPBirthType.setText(pp.getBirthType());
                            editTextPlaceGiveBirth.setText(pp.getPlaceGiveBirth());
                            editTextPPWeight.setText(pp.getBirthWeight()+"");
                            editTextPPMother.setText(pp.getComplicationMother());
                            editTextPPChild.setText(pp.getComplicationChild());
                            editTextPPBFP.setText(pp.getBreastFeedingPeriod());
                            editTextSituation.setText(pp.getChildSituation());
                            if(radioButtonPPMale.getText().toString().equals(pp.getGender()))
                            {
                                radioButtonPPMale.setChecked(true);
                            }else if(radioButtonPPFemale.getText().toString().equals(pp.getGender()))
                            {
                                radioButtonPPFemale.setChecked(true);
                            }
                            layoutPP.setVisibility(View.VISIBLE);
                            progressBarPP.setVisibility(View.GONE);
                            btnPPSave.setText("Update");
                        }
                    }
                });
            }

        }

        btnPPSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(SaveSharedPreference.getPreviousPregnant(SectionAActivity.this).equals("New"))
                {
                    if(CheckOnclickRequired() == false)
                    {
                        String year = editTextPPYear.getText().toString();
                        String result = editTextPPResult.getText().toString();
                        String birthType = editTextPPBirthType.getText().toString();
                        String placeGiveBirth = editTextPlaceGiveBirth.getText().toString();
                        Double weight = Double.parseDouble(editTextPPWeight.getText().toString());
                        String mother = editTextPPMother.getText().toString();
                        String child = editTextPPChild.getText().toString();
                        String bfp = editTextPPBFP.getText().toString();
                        String situation = editTextSituation.getText().toString();
                        String gender = "";
                        if(radioButtonPPMale.isChecked())
                        {
                            gender = radioButtonPPMale.getText().toString();
                        }else if(radioButtonPPFemale.isChecked())
                        {
                            gender = radioButtonPPFemale.getText().toString();
                        }
                        Date today = new Date();
                        String medicalPersonnelId = SaveSharedPreference.getID(SectionAActivity.this);
                        String ppId = UUID.randomUUID().toString().replace("-", "");
                        PreviousPregnant pp = new PreviousPregnant(year, result, birthType, placeGiveBirth, gender, weight, mother, child, bfp, situation, today, medicalPersonnelId, ppId);

                        mCollectionReference.add(pp).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {
                                AlertDialog.Builder builder = new AlertDialog.Builder(SectionAActivity.this);
                                builder.setTitle("Save Successfully");
                                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        finish();
                                    }
                                });
                                builder.setMessage("Save Successful!");
                                AlertDialog alert = builder.create();
                                alert.setCanceledOnTouchOutside(false);
                                alert.show();
                            }
                        });
                    }
                }else if(SaveSharedPreference.getPreviousPregnant(SectionAActivity.this).equals("Exist"))
                {
                    check++;
                    if(check == 1)
                    {
                        EnableField();
                        btnPPCancel.setVisibility(View.VISIBLE);
                    }else if(check == 2)
                    {
                        if(CheckOnclickRequired() == false)
                        {
                            Date today = new Date();
                            mDocumentReference = mFirebaseFirestore.document("MommyHealthInfo/"+healthInfoId+"/BloodTest/"+bloodTestId+"/PreviousPregnant/"+key);
                            mDocumentReference.update("year", editTextPPYear.getText().toString());
                            mDocumentReference.update("pregnantResult", editTextPPResult.getText().toString());
                            mDocumentReference.update("birthType", editTextPPBirthType.getText().toString());
                            mDocumentReference.update("placeGiveBirth", editTextPlaceGiveBirth.getText().toString());
                            if(radioButtonPPMale.isChecked())
                            {
                                mDocumentReference.update("gender",radioButtonPPMale.getText().toString());
                            }else if(radioButtonPPFemale.isChecked())
                            {
                                mDocumentReference.update("gender", radioButtonPPFemale.getText().toString());
                            }
                            mDocumentReference.update("birthWeight", Double.parseDouble(editTextPPWeight.getText().toString()));
                            mDocumentReference.update("complicationMother", editTextPPMother.getText().toString());
                            mDocumentReference.update("complicationChild", editTextPPChild.getText().toString());
                            mDocumentReference.update("breastFeedingPeriod", editTextPPBFP.getText().toString());
                            mDocumentReference.update("childSituation", editTextSituation.getText().toString());
                            mDocumentReference.update("createdBy", SaveSharedPreference.getID(SectionAActivity.this));
                            mDocumentReference.update("today", today);
                            check = 0;
                            DisableField();
                            btnPPCancel.setVisibility(View.GONE);
                            Snackbar snackbar = Snackbar.make(relativeLayoutPP, "Updated Successfully!", Snackbar.LENGTH_LONG);
                            snackbar.show();
                        }else{
                            check = 1;
                        }
                    }
                }
            }
        });

        btnPPCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DisableField();
                btnPPCancel.setVisibility(View.GONE);
                check = 0;
            }
        });

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void MommyLogIn()
    {
        String healthInfoId = "";
        String bloodTestId = "";
        String ppId = "";
        btnPPSave.setVisibility(View.GONE);
        btnPPCancel.setVisibility(View.GONE);
        Intent intentss = getIntent();
        healthInfoId = intentss.getStringExtra("healthInfoId");
        bloodTestId = intentss.getStringExtra("bloodTestId");
        ppId = intentss.getStringExtra("ppId");

        mFirebaseFirestore = FirebaseFirestore.getInstance();
        mCollectionReference = mFirebaseFirestore.collection("MommyHealthInfo/"+healthInfoId+"/BloodTest/"+bloodTestId+"/PreviousPregnant");

        mCollectionReference.whereEqualTo("previousPregnantId", ppId).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for(QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots)
                {
                    key = documentSnapshot.getId();
                    PreviousPregnant pp = documentSnapshot.toObject(PreviousPregnant.class);
                    editTextPPYear.setText(pp.getYear());
                    editTextPPResult.setText(pp.getPregnantResult());
                    editTextPPBirthType.setText(pp.getBirthType());
                    editTextPlaceGiveBirth.setText(pp.getPlaceGiveBirth());
                    editTextPPWeight.setText(pp.getBirthWeight()+"");
                    editTextPPMother.setText(pp.getComplicationMother());
                    editTextPPChild.setText(pp.getComplicationChild());
                    editTextPPBFP.setText(pp.getBreastFeedingPeriod());
                    editTextSituation.setText(pp.getChildSituation());
                    if(radioButtonPPMale.getText().toString().equals(pp.getGender()))
                    {
                        radioButtonPPMale.setChecked(true);
                    }else if(radioButtonPPFemale.getText().toString().equals(pp.getGender()))
                    {
                        radioButtonPPFemale.setChecked(true);
                    }
                    layoutPP.setVisibility(View.VISIBLE);
                    progressBarPP.setVisibility(View.GONE);
                }
            }
        });
    }

    private boolean CheckOnclickRequired()
    {
        if(editTextPPYear.getText().toString().isEmpty() || editTextPPResult.getText().toString().isEmpty() || editTextPPBirthType.getText().toString().isEmpty()
        || editTextPlaceGiveBirth.getText().toString().isEmpty() || editTextPPWeight.getText().toString().isEmpty() || editTextPPMother.getText().toString().isEmpty()
        || editTextPPChild.getText().toString().isEmpty() || editTextPPBFP.getText().toString().isEmpty() || editTextSituation.getText().toString().isEmpty() || radioGroupPPGender.getCheckedRadioButtonId() == -1)
        {
            if(editTextPPYear.getText().toString().isEmpty())
            {
                layoutPPYear.setErrorEnabled(true);
                layoutPPYear.setError("This field is required!");
            }else{
                layoutPPYear.setErrorEnabled(false);
                layoutPPYear.setError(null);
            }

            if(editTextPPResult.getText().toString().isEmpty())
            {
                layoutPPResult.setErrorEnabled(true);
                layoutPPResult.setError("This field is required!");
            }else{
                layoutPPResult.setErrorEnabled(false);
                layoutPPResult.setError(null);
            }

            if(editTextPPBirthType.getText().toString().isEmpty())
            {
                layoutPPBirthType.setErrorEnabled(true);
                layoutPPBirthType.setError("This field is required!");
            }else{
                layoutPPBirthType.setErrorEnabled(false);
                layoutPPBirthType.setError(null);
            }

            if(editTextPlaceGiveBirth.getText().toString().isEmpty())
            {
                layoutPlaceGiveBirth.setErrorEnabled(true);
                layoutPlaceGiveBirth.setError("This field is required!");
            }else{
                layoutPlaceGiveBirth.setErrorEnabled(false);
                layoutPlaceGiveBirth.setError(null);
            }

            if(editTextPPWeight.getText().toString().isEmpty())
            {
                layoutPPWeight.setErrorEnabled(true);
                layoutPPWeight.setError("This field is required!");
            }else{
                layoutPPWeight.setErrorEnabled(false);
                layoutPPWeight.setError(null);
            }

            if(editTextPPMother.getText().toString().isEmpty())
            {
                layoutPPMother.setErrorEnabled(true);
                layoutPPMother.setError("This field is required!");
            }else{
                layoutPPMother.setErrorEnabled(false);
                layoutPPMother.setError(null);
            }

            if(editTextPPChild.getText().toString().isEmpty())
            {
                layoutPPChild.setErrorEnabled(true);
                layoutPPChild.setError("This field is required!");
            }else{
                layoutPPChild.setErrorEnabled(false);
                layoutPPChild.setError(null);
            }

            if(editTextPPBFP.getText().toString().isEmpty())
            {
                layoutPPBFP.setErrorEnabled(true);
                layoutPPBFP.setError("This field is required!");
            }else{
                layoutPPBFP.setErrorEnabled(false);
                layoutPPBFP.setError(null);
            }

            if(editTextSituation.getText().toString().isEmpty())
            {
                layoutSituation.setErrorEnabled(true);
                layoutSituation.setError("This field is required!");
            }else{
                layoutSituation.setErrorEnabled(false);
                layoutSituation.setError(null);
            }

            if(radioGroupPPGender.getCheckedRadioButtonId() == -1)
            {
                radioButtonPPMale.setError("This field is required!");
                radioButtonPPFemale.setError("This field is required!");
            }else{
                radioButtonPPMale.setError(null);
                radioButtonPPFemale.setError(null);
            }
            return true;
        }else{
            return false;
        }
    }

    private void CheckRequiredField()
    {
        editTextPPYear.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(editTextPPYear.getText().toString().isEmpty())
                {
                    layoutPPYear.setErrorEnabled(true);
                    layoutPPYear.setError("This field is required!");
                }else{
                    layoutPPYear.setErrorEnabled(false);
                    layoutPPYear.setError(null);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        editTextPPResult.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(editTextPPResult.getText().toString().isEmpty())
                {
                    layoutPPResult.setErrorEnabled(true);
                    layoutPPResult.setError("This field is required!");
                }else{
                    layoutPPResult.setErrorEnabled(false);
                    layoutPPResult.setError(null);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        editTextPPBirthType.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(editTextPPBirthType.getText().toString().isEmpty())
                {
                    layoutPPBirthType.setErrorEnabled(true);
                    layoutPPBirthType.setError("This field is required!");
                }else{
                    layoutPPBirthType.setErrorEnabled(false);
                    layoutPPBirthType.setError(null);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        editTextPlaceGiveBirth.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(editTextPlaceGiveBirth.getText().toString().isEmpty())
                {
                    layoutPlaceGiveBirth.setErrorEnabled(true);
                    layoutPlaceGiveBirth.setError("This field is required!");
                }else{
                    layoutPlaceGiveBirth.setErrorEnabled(false);
                    layoutPlaceGiveBirth.setError(null);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        editTextPPWeight.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(editTextPPWeight.getText().toString().isEmpty())
                {
                    layoutPPWeight.setErrorEnabled(true);
                    layoutPPWeight.setError("This field is required!");
                }else{
                    layoutPPWeight.setErrorEnabled(false);
                    layoutPPWeight.setError(null);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        editTextPPMother.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(editTextPPMother.getText().toString().isEmpty())
                {
                    layoutPPMother.setErrorEnabled(true);
                    layoutPPMother.setError("This field is required!");
                }else{
                    layoutPPMother.setErrorEnabled(false);
                    layoutPPMother.setError(null);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        editTextPPChild.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(editTextPPChild.getText().toString().isEmpty())
                {
                    layoutPPChild.setErrorEnabled(true);
                    layoutPPChild.setError("This field is required!");
                }else{
                    layoutPPChild.setErrorEnabled(false);
                    layoutPPChild.setError(null);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        editTextPPBFP.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(editTextPPBFP.getText().toString().isEmpty())
                {
                    layoutPPBFP.setErrorEnabled(true);
                    layoutPPBFP.setError("This field is required!");
                }else{
                    layoutPPBFP.setErrorEnabled(false);
                    layoutPPBFP.setError(null);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        editTextSituation.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(editTextSituation.getText().toString().isEmpty())
                {
                    layoutSituation.setErrorEnabled(true);
                    layoutSituation.setError("This field is required!");
                }else{
                    layoutSituation.setErrorEnabled(false);
                    layoutSituation.setError(null);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void DisableField()
    {
        editTextPPYear.setEnabled(false);
        editTextPPYear.setTextColor(Color.parseColor("#000000"));
        editTextPPResult.setEnabled(false);
        editTextPPResult.setTextColor(Color.parseColor("#000000"));
        editTextPPBirthType.setEnabled(false);
        editTextPPBirthType.setTextColor(Color.parseColor("#000000"));
        editTextPlaceGiveBirth.setEnabled(false);
        editTextPlaceGiveBirth.setTextColor(Color.parseColor("#000000"));
        editTextPPWeight.setEnabled(false);
        editTextPPWeight.setTextColor(Color.parseColor("#000000"));
        editTextPPMother.setEnabled(false);
        editTextPPMother.setTextColor(Color.parseColor("#000000"));
        editTextPPChild.setEnabled(false);
        editTextPPChild.setTextColor(Color.parseColor("#000000"));
        editTextPPBFP.setEnabled(false);
        editTextPPBFP.setTextColor(Color.parseColor("#000000"));
        editTextSituation.setEnabled(false);
        editTextSituation.setTextColor(Color.parseColor("#000000"));
        for(int i=0; i<radioGroupPPGender.getChildCount(); i++)
        {
            ((RadioButton)radioGroupPPGender.getChildAt(i)).setEnabled(false);
        }
    }

    private void EnableField()
    {
        editTextPPYear.setEnabled(true);
        editTextPPResult.setEnabled(true);
        editTextPPBirthType.setEnabled(true);
        editTextPlaceGiveBirth.setEnabled(true);
        editTextPPWeight.setEnabled(true);
        editTextPPMother.setEnabled(true);
        editTextPPChild.setEnabled(true);
        editTextPPBFP.setEnabled(true);
        editTextSituation.setEnabled(true);
        for(int i=0; i<radioGroupPPGender.getChildCount(); i++)
        {
            ((RadioButton)radioGroupPPGender.getChildAt(i)).setEnabled(true);
        }
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.action_logout:
                AlertDialog.Builder builder = new AlertDialog.Builder(SectionAActivity.this);
                builder.setTitle("Logout");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        SaveSharedPreference.clearUser(SectionAActivity.this);
                        Intent intent = new Intent(SectionAActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                builder.setMessage("Are you sure you want to log out?");
                AlertDialog alert = builder.create();
                alert.setCanceledOnTouchOutside(false);
                alert.show();
                return true;
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.nurse_home, menu);
        return true;
    }
}
