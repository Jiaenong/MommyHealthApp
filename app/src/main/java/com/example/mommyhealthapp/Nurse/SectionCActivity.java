package com.example.mommyhealthapp.Nurse;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.LinearLayoutCompat;

import android.app.DatePickerDialog;
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
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.mommyhealthapp.Class.HealthHistory;
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

import org.w3c.dom.Text;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class SectionCActivity extends AppCompatActivity {

    private RadioGroup radioGroupPractice, radioGroupMotherSmoke, radioGroupFatherSmoke;
    private RadioButton radioButtonPracYes, radioButtonPracNo, radioBtnMotherSmokeYes, radioBtnMotherSmokeNo, radioBtnFatherSmokeYes, radioBtnFatherSmokeNo;
    private TextInputLayout txtInputLayoutPeriod, txtInputLayoutMethod, txtInputLayoutState, txtInputLayoutFamilyState, layoutMenstrDays, layoutMenstrRound;
    private EditText editTextDos1, editTextDos2, editTextDosAddOn, editTextMenstrDays, editTextMenstrRounds, editTextBCMethod, editTextBCPeriod,
            editTextMotherDiseaseOthers, editTextFamilyDiseaseOthers;
    private CheckBox chkMotherDiabetes, chkMotherAsthma, chkMotherAnemia, chkMotherThalassemia, chkMotherHighBloodPressure, chkMotherHeartDisease, chkMotherAllergy,
            chkMotherTuberculosis, chkMotherOthers;
    private  CheckBox chkFamilyDiabetes, chkFamilyAsthma, chkFamilyAnemia, chkFamilyThalassemia, chkFamilyHighBloodPressure, chkFamilyHeartDisease, chkFamilyAllergy,
            chkFamilyTuberculosis, checkBoxFamilyOther;
    private LinearLayoutCompat layoutMotherDisease1, layoutMotherDisease2, layoutMotherDisease3;
    private LinearLayoutCompat layoutFamilyDisease1, layoutFamilyDisease2, layoutFamilyDisease3, layoutHealthHistory;
    private RelativeLayout relativeLayoutHH;
    private Button btnHealthHistorySave, btnHealthHistoryCancel;
    private ProgressBar progressBarHealthHistory;
    DatePickerDialog datePickerDialog;

    private String healthInfoId, bloodTestId, key;
    private boolean isEmpty;
    private int check = 0;
    private FirebaseFirestore mFirebaseFirestore;
    private CollectionReference mCollectionReference;
    private DocumentReference mDocumentReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_section_c);

        progressBarHealthHistory = (ProgressBar)findViewById(R.id.progressBarHealthHistory);
        layoutHealthHistory = (LinearLayoutCompat)findViewById(R.id.layoutHealthHistory);

        radioGroupPractice = (RadioGroup)findViewById(R.id.radioGroupPractice);
        radioButtonPracYes = (RadioButton)findViewById(R.id.radioButtonPracYes);
        radioButtonPracNo = (RadioButton)findViewById(R.id.radioButtonPracNo);
        radioGroupMotherSmoke = (RadioGroup)findViewById(R.id.radioGroupMotherSmoke);
        radioGroupFatherSmoke = (RadioGroup)findViewById(R.id.radioGroupFatherSmoke);
        radioBtnMotherSmokeYes = (RadioButton)findViewById(R.id.radioBtnMotherSmokeYes);
        radioBtnMotherSmokeNo = (RadioButton)findViewById(R.id.radioBtnMotherSmokeNo);
        radioBtnFatherSmokeYes = (RadioButton)findViewById(R.id.radioBtnFatherSmokeYes);
        radioBtnFatherSmokeNo = (RadioButton)findViewById(R.id.radioBtnFatherSmokeNo);

        layoutMotherDisease1 = (LinearLayoutCompat)findViewById(R.id.layoutMotherDisease1);
        layoutMotherDisease2 = (LinearLayoutCompat)findViewById(R.id.layoutMotherDisease2);
        layoutMotherDisease3 = (LinearLayoutCompat)findViewById(R.id.layoutMotherDisease3);
        layoutFamilyDisease1 = (LinearLayoutCompat)findViewById(R.id.layoutFamilyDisease1);
        layoutFamilyDisease2 = (LinearLayoutCompat)findViewById(R.id.layoutFamilyDisease2);
        layoutFamilyDisease3 = (LinearLayoutCompat)findViewById(R.id.layoutFamilyDisease3);

        relativeLayoutHH = (RelativeLayout)findViewById(R.id.relativeLayoutHH);

        txtInputLayoutMethod = (TextInputLayout)findViewById(R.id.txtInputLayoutMethod);
        txtInputLayoutPeriod = (TextInputLayout)findViewById(R.id.txtInputLayotPeriod);
        txtInputLayoutState = (TextInputLayout)findViewById(R.id.txtInputLayoutState);
        txtInputLayoutFamilyState = (TextInputLayout)findViewById(R.id.txtInputLayoutFamilyState);
        layoutMenstrDays = (TextInputLayout)findViewById(R.id.layoutMenstrDays);
        layoutMenstrRound = (TextInputLayout)findViewById(R.id.layoutMenstrRound);

        chkMotherDiabetes = (CheckBox)findViewById(R.id.chkMotherDiabetes);
        chkMotherAsthma = (CheckBox)findViewById(R.id.chkMotherAsthma);
        chkMotherAnemia = (CheckBox)findViewById(R.id.chkMotherAnemia);
        chkMotherThalassemia = (CheckBox)findViewById(R.id.chkMotherThalassemia);
        chkMotherHighBloodPressure = (CheckBox)findViewById(R.id.chkMotherHighBloodPressure);
        chkMotherHeartDisease = (CheckBox)findViewById(R.id.chkMotherHeartDisease);
        chkMotherAllergy = (CheckBox)findViewById(R.id.chkMotherAllergy);
        chkMotherTuberculosis = (CheckBox)findViewById(R.id.chkMotherTuberculosis);
        chkMotherOthers = (CheckBox)findViewById(R.id.chkMotherOthers);

        chkFamilyDiabetes = (CheckBox)findViewById(R.id.chkFamilyDiabetes);
        chkFamilyAsthma = (CheckBox)findViewById(R.id.chkFamilyAsthma);
        chkFamilyAnemia = (CheckBox)findViewById(R.id.chkFamilyAnemia);
        chkFamilyThalassemia = (CheckBox)findViewById(R.id.chkFamilyThalassemia);
        chkFamilyHighBloodPressure = (CheckBox)findViewById(R.id.chkFamilyHighBloodPressure);
        chkFamilyHeartDisease = (CheckBox)findViewById(R.id.chkFamilyHeartDisease);
        chkFamilyAllergy = (CheckBox)findViewById(R.id.chkFamilyAllergy);
        chkFamilyTuberculosis = (CheckBox)findViewById(R.id.chkFamilyTuberculosis);
        checkBoxFamilyOther = (CheckBox)findViewById(R.id.checkBoxFamilyOther);

        editTextMenstrDays = (EditText)findViewById(R.id.editTextMenstrDays);
        editTextMenstrRounds = (EditText)findViewById(R.id.editTextMenstrRounds);
        editTextBCMethod = (EditText)findViewById(R.id.editTextBCMethod);
        editTextBCPeriod = (EditText)findViewById(R.id.editTextBCPeriod);
        editTextMotherDiseaseOthers = (EditText)findViewById(R.id.editTextMotherDiseaseOthers);
        editTextFamilyDiseaseOthers = (EditText)findViewById(R.id.editTextFamilyDiseaseOthers);
        editTextDos1 = (EditText)findViewById(R.id.editTextDos1);
        editTextDos2 = (EditText)findViewById(R.id.editTextDos2);
        editTextDosAddOn = (EditText)findViewById(R.id.editTextDosAddOn);

        btnHealthHistorySave = (Button)findViewById(R.id.btnHealthHistorySave);
        btnHealthHistoryCancel = (Button)findViewById(R.id.btnHealthHistoryCancel);

        Intent intent = getIntent();
        healthInfoId = intent.getStringExtra("healthInfoId");
        bloodTestId = intent.getStringExtra("bloodTestId");

        CheckRequiredField();

        mFirebaseFirestore = FirebaseFirestore.getInstance();
        mCollectionReference = mFirebaseFirestore.collection("MommyHealthInfo/"+healthInfoId+"/BloodTest/"+bloodTestId+"/HealthHistory");

        radioGroupPractice.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(radioButtonPracYes.isChecked())
                {
                    txtInputLayoutMethod.setVisibility(View.VISIBLE);
                    txtInputLayoutPeriod.setVisibility(View.VISIBLE);
                }else{
                    txtInputLayoutMethod.setVisibility(View.GONE);
                    txtInputLayoutPeriod.setVisibility(View.GONE);
                    editTextBCMethod.setText("");
                    editTextBCPeriod.setText("");
                }
            }
        });

        chkMotherOthers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(chkMotherOthers.isChecked())
                {
                    txtInputLayoutState.setVisibility(View.VISIBLE);
                }else{
                    txtInputLayoutState.setVisibility(View.GONE);
                    editTextMotherDiseaseOthers.setText("");
                }

            }
        });

        checkBoxFamilyOther.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkBoxFamilyOther.isChecked())
                {
                    txtInputLayoutFamilyState.setVisibility(View.VISIBLE);
                }else{
                    txtInputLayoutFamilyState.setVisibility(View.GONE);
                    editTextFamilyDiseaseOthers.setText("");
                }
            }
        });

        editTextDos1.setClickable(false);
        editTextDos1.setFocusable(false);
        editTextDos2.setClickable(false);
        editTextDos2.setFocusable(false);
        editTextDosAddOn.setClickable(false);
        editTextDosAddOn.setFocusable(false);

        editTextDos1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR); // current year
                int mMonth = c.get(Calendar.MONTH); // current month
                int mDay = c.get(Calendar.DAY_OF_MONTH); // current day
                datePickerDialog = new DatePickerDialog(SectionCActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                editTextDos1.setText(dayOfMonth + "/" +(month + 1) + "/" + year);
                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });

        editTextDos2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR); // current year
                int mMonth = c.get(Calendar.MONTH); // current month
                int mDay = c.get(Calendar.DAY_OF_MONTH); // current day
                datePickerDialog = new DatePickerDialog(SectionCActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                editTextDos2.setText(dayOfMonth + "/" +(month + 1) + "/" + year);
                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });

        editTextDosAddOn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR); // current year
                int mMonth = c.get(Calendar.MONTH); // current month
                int mDay = c.get(Calendar.DAY_OF_MONTH); // current day
                datePickerDialog = new DatePickerDialog(SectionCActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                editTextDosAddOn.setText(dayOfMonth + "/" +(month + 1) + "/" + year);
                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });

        btnHealthHistoryCancel.setVisibility(View.GONE);
        progressBarHealthHistory.setVisibility(View.VISIBLE);
        layoutHealthHistory.setVisibility(View.GONE);

        if(SaveSharedPreference.getUser(SectionCActivity.this).equals("Mommy")) {
            MommyLogIn();
        }
        else{
            mCollectionReference.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                @Override
                public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                    if(queryDocumentSnapshots.isEmpty()){
                        isEmpty = true;
                        progressBarHealthHistory.setVisibility(View.GONE);
                        layoutHealthHistory.setVisibility(View.VISIBLE);
                    }else{
                        isEmpty = false;
                        DisableField();
                        for(QueryDocumentSnapshot documentSnapshot: queryDocumentSnapshots)
                        {
                            key = documentSnapshot.getId();
                            HealthHistory hh = documentSnapshot.toObject(HealthHistory.class);
                            editTextMenstrDays.setText(hh.getMenstruationDays());
                            editTextMenstrRounds.setText(hh.getMenstruationRound());
                            if(hh.getBirthControl().equals("Yes"))
                            {
                                radioButtonPracYes.setChecked(true);
                                editTextBCMethod.setText(hh.getBirthControlMethod());
                                editTextBCPeriod.setText(hh.getBirthControlPeriod());
                            }else{
                                radioButtonPracNo.setChecked(true);
                            }
                            if(hh.getMotherSmoke().equals("Yes"))
                            {
                                radioBtnMotherSmokeYes.setChecked(true);
                            }else{
                                radioBtnMotherSmokeNo.setChecked(true);
                            }
                            if(hh.getFatherSmoke().equals("Yes"))
                            {
                                radioBtnFatherSmokeYes.setChecked(true);
                            }else{
                                radioBtnFatherSmokeNo.setChecked(true);
                            }
                            List<String> motherDisease = hh.getMotherDisease();
                            List<String> familyDisease = hh.getFamilyDisease();
                            getMotherDisease(motherDisease);
                            getFamilyDisease(familyDisease);

                            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                            if(hh.getDos1() == null)
                            {
                                editTextDos1.setText("");
                            }else{
                                editTextDos1.setText(dateFormat.format(hh.getDos1()));
                            }
                            if(hh.getDos2() == null)
                            {
                                editTextDos2.setText("");
                            }else{
                                editTextDos2.setText(dateFormat.format(hh.getDos2()));
                            }
                            if(hh.getDosAddOn() == null)
                            {
                                editTextDosAddOn.setText("");
                            }else{
                                editTextDosAddOn.setText(dateFormat.format(hh.getDosAddOn()));
                            }

                            progressBarHealthHistory.setVisibility(View.GONE);
                            layoutHealthHistory.setVisibility(View.VISIBLE);
                            btnHealthHistorySave.setText("Update");
                        }
                    }
                }
            });
        }

        btnHealthHistorySave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isEmpty == true)
                {
                    if(CheckRequiredOnClick() == false)
                    {
                        List<String> motherDisese = new ArrayList<>();
                        List<String> familyDisease = new ArrayList<>();
                        Date dos1 = null, dos2 = null, dosAddOn = null;
                        String menstrDays = editTextMenstrDays.getText().toString();
                        String menstrRound = editTextMenstrRounds.getText().toString();
                        String birthControl = "", birthControlMethod = "", birthControlPeriod = "";
                        String smokeMother = "", smokeFather = "";
                        if(radioButtonPracYes.isChecked()) {
                            birthControl = "Yes";
                            birthControlMethod = editTextBCMethod.getText().toString();
                            birthControlPeriod = editTextBCPeriod.getText().toString();
                        }else if(radioButtonPracNo.isChecked()){
                            birthControl = "No";
                        }
                        if(radioBtnMotherSmokeYes.isChecked()){
                            smokeMother = "Yes";
                        }else if(radioBtnMotherSmokeNo.isChecked()){
                            smokeMother = "No";
                        }
                        if(radioBtnFatherSmokeYes.isChecked()){
                            smokeFather = "Yes";
                        }else if(radioBtnFatherSmokeNo.isChecked()){
                            smokeFather = "No";
                        }
                        motherDisese = createMotherDiseaseList();
                        familyDisease = createFamilyDiseaseList();
                        try {
                            if(!editTextDos1.getText().toString().isEmpty()) {
                                dos1 = new SimpleDateFormat("dd/MM/yyyy").parse(editTextDos1.getText().toString());
                            }
                            if(!editTextDos2.getText().toString().isEmpty()) {
                                dos2 = new SimpleDateFormat("dd/MM/yyyy").parse(editTextDos2.getText().toString());
                            }
                            if(!editTextDosAddOn.getText().toString().isEmpty()){
                                dosAddOn = new SimpleDateFormat("dd/MM/yyyy").parse(editTextDosAddOn.getText().toString());
                            }
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        String medicalPersonnelId = SaveSharedPreference.getID(SectionCActivity.this);
                        Date today = new Date();

                        HealthHistory hh = new HealthHistory(menstrDays, menstrRound, birthControl, birthControlMethod, birthControlPeriod, smokeMother, smokeFather,
                                motherDisese, familyDisease, dos1, dos2, dosAddOn, medicalPersonnelId, today);

                        mCollectionReference.add(hh).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {
                                AlertDialog.Builder builder = new AlertDialog.Builder(SectionCActivity.this);
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
                }else{
                    check++;
                    if(check == 1)
                    {
                        EnableField();
                        btnHealthHistoryCancel.setVisibility(View.VISIBLE);
                    }else if(check == 2)
                    {
                        if(CheckRequiredOnClick() == false)
                        {
                            List<String> motherDisease = new ArrayList<>();
                            List<String> familyDisease = new ArrayList<>();
                            Date dos1 = null, dos2 = null, dosAddOn = null;
                            Date today = new Date();
                            mDocumentReference = mFirebaseFirestore.document("MommyHealthInfo/"+healthInfoId+"/BloodTest/"+bloodTestId+"/HealthHistory/"+key);
                            mDocumentReference.update("menstruationDays", editTextMenstrDays.getText().toString());
                            mDocumentReference.update("menstruationRound", editTextMenstrRounds.getText().toString());
                            if(radioButtonPracYes.isChecked())
                            {
                                mDocumentReference.update("birthControl", radioButtonPracYes.getText().toString());
                                mDocumentReference.update("birthControlMethod", editTextBCMethod.getText().toString());
                                mDocumentReference.update("birthControlPeriod", editTextBCPeriod.getText().toString());
                            }else{
                                mDocumentReference.update("birthControl", radioButtonPracNo.getText().toString());
                                mDocumentReference.update("birthControlMethod", "");
                                mDocumentReference.update("birthControlPeriod", "");
                            }
                            if(radioBtnMotherSmokeYes.isChecked())
                            {
                                mDocumentReference.update("MotherSmoke", radioBtnMotherSmokeYes.getText().toString());
                            }else{
                                mDocumentReference.update("MotherSmoke", radioBtnMotherSmokeNo.getText().toString());
                            }
                            if(radioBtnFatherSmokeYes.isChecked())
                            {
                                mDocumentReference.update("FatherSmoke", radioBtnFatherSmokeYes.getText().toString());
                            }else{
                                mDocumentReference.update("FatherSmoke", radioBtnFatherSmokeNo.getText().toString());
                            }
                            motherDisease = createMotherDiseaseList();
                            familyDisease = createFamilyDiseaseList();
                            mDocumentReference.update("motherDisease", motherDisease);
                            mDocumentReference.update("familyDisease", familyDisease);
                            try {
                                if(!editTextDos1.getText().toString().isEmpty()) {
                                    dos1 = new SimpleDateFormat("dd/MM/yyyy").parse(editTextDos1.getText().toString());
                                }
                                if(!editTextDos2.getText().toString().isEmpty()) {
                                    dos2 = new SimpleDateFormat("dd/MM/yyyy").parse(editTextDos2.getText().toString());
                                }
                                if(!editTextDosAddOn.getText().toString().isEmpty()){
                                    dosAddOn = new SimpleDateFormat("dd/MM/yyyy").parse(editTextDosAddOn.getText().toString());
                                }
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                            mDocumentReference.update("dos1", dos1);
                            mDocumentReference.update("dos2", dos2);
                            mDocumentReference.update("dosAddOn", dosAddOn);
                            mDocumentReference.update("medicalPersonnelId", SaveSharedPreference.getID(SectionCActivity.this));
                            mDocumentReference.update("createdDate", today);
                            check = 0;
                            btnHealthHistoryCancel.setVisibility(View.GONE);
                            Snackbar snackbar = Snackbar.make(relativeLayoutHH, "Updated Successfully!", Snackbar.LENGTH_LONG);
                            snackbar.show();
                            DisableField();
                        }else{
                            check = 1;
                        }
                    }
                }
            }
        });

        btnHealthHistoryCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DisableField();
                btnHealthHistoryCancel.setVisibility(View.GONE);
                check = 0;
            }
        });


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void MommyLogIn(){
        mCollectionReference.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                if(queryDocumentSnapshots.isEmpty()){
                    isEmpty = true;
                    progressBarHealthHistory.setVisibility(View.GONE);
                    layoutHealthHistory.setVisibility(View.VISIBLE);
                    btnHealthHistoryCancel.setVisibility(View.GONE);
                    btnHealthHistorySave.setVisibility(View.GONE);
                    DisableField();
                }else{
                    isEmpty = false;
                    DisableField();
                    for(QueryDocumentSnapshot documentSnapshot: queryDocumentSnapshots)
                    {
                        key = documentSnapshot.getId();
                        HealthHistory hh = documentSnapshot.toObject(HealthHistory.class);
                        editTextMenstrDays.setText(hh.getMenstruationDays());
                        editTextMenstrRounds.setText(hh.getMenstruationRound());
                        if(hh.getBirthControl().equals("Yes"))
                        {
                            radioButtonPracYes.setChecked(true);
                            editTextBCMethod.setText(hh.getBirthControlMethod());
                            editTextBCPeriod.setText(hh.getBirthControlPeriod());
                        }else{
                            radioButtonPracNo.setChecked(true);
                        }
                        if(hh.getMotherSmoke().equals("Yes"))
                        {
                            radioBtnMotherSmokeYes.setChecked(true);
                        }else{
                            radioBtnMotherSmokeNo.setChecked(true);
                        }
                        if(hh.getFatherSmoke().equals("Yes"))
                        {
                            radioBtnFatherSmokeYes.setChecked(true);
                        }else{
                            radioBtnFatherSmokeNo.setChecked(true);
                        }
                        List<String> motherDisease = hh.getMotherDisease();
                        List<String> familyDisease = hh.getFamilyDisease();
                        getMotherDisease(motherDisease);
                        getFamilyDisease(familyDisease);

                        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                        if(hh.getDos1() == null)
                        {
                            editTextDos1.setText("");
                        }else{
                            editTextDos1.setText(dateFormat.format(hh.getDos1()));
                        }
                        if(hh.getDos2() == null)
                        {
                            editTextDos2.setText("");
                        }else{
                            editTextDos2.setText(dateFormat.format(hh.getDos2()));
                        }
                        if(hh.getDosAddOn() == null)
                        {
                            editTextDosAddOn.setText("");
                        }else{
                            editTextDosAddOn.setText(dateFormat.format(hh.getDosAddOn()));
                        }

                        progressBarHealthHistory.setVisibility(View.GONE);
                        layoutHealthHistory.setVisibility(View.VISIBLE);
                        btnHealthHistorySave.setVisibility(View.GONE);
                        btnHealthHistoryCancel.setVisibility(View.GONE);
                    }
                }
            }
        });
    }

    private void getMotherDisease(List<String> motherDisease)
    {
        for(int i=0; i<motherDisease.size(); i++)
        {
            for(int n=0; n<layoutMotherDisease1.getChildCount(); n++)
            {
                View view = layoutMotherDisease1.getChildAt(n);
                if(view instanceof CheckBox){
                    if(((CheckBox)view).getText().toString().equals(motherDisease.get(i)))
                    {
                        ((CheckBox)view).setChecked(true);
                    }
                }
            }
            for(int m=0; m<layoutMotherDisease2.getChildCount(); m++)
            {
                View view = layoutMotherDisease2.getChildAt(m);
                if(view instanceof CheckBox){
                    if(((CheckBox)view).getText().toString().equals(motherDisease.get(i)))
                    {
                        ((CheckBox)view).setChecked(true);
                    }
                }
            }
            for(int p=0; p<layoutMotherDisease3.getChildCount(); p++)
            {
                View view = layoutMotherDisease3.getChildAt(p);
                if(view instanceof CheckBox){
                    if(((CheckBox)view).getText().toString().equals(motherDisease.get(i)))
                    {
                        ((CheckBox)view).setChecked(true);
                    }
                }
            }
            if(motherDisease.get(i).substring(0,6).equals("other_"))
            {
                chkMotherOthers.setChecked(true);
                txtInputLayoutState.setVisibility(View.VISIBLE);
                editTextMotherDiseaseOthers.setText(motherDisease.get(i).substring(6));
            }
        }
    }

    private void getFamilyDisease(List<String> familyDisease)
    {
        for(int i=0; i<familyDisease.size(); i++)
        {
            for(int n=0; n<layoutFamilyDisease1.getChildCount(); n++)
            {
                View view = layoutFamilyDisease1.getChildAt(n);
                if(view instanceof CheckBox){
                    if(((CheckBox)view).getText().toString().equals(familyDisease.get(i)))
                    {
                        ((CheckBox)view).setChecked(true);
                    }
                }
            }
            for(int m=0; m<layoutFamilyDisease2.getChildCount(); m++)
            {
                View view = layoutFamilyDisease2.getChildAt(m);
                if(view instanceof CheckBox){
                    if(((CheckBox)view).getText().toString().equals(familyDisease.get(i)))
                    {
                        ((CheckBox)view).setChecked(true);
                    }
                }
            }
            for(int p=0; p<layoutFamilyDisease3.getChildCount(); p++)
            {
                View view = layoutFamilyDisease3.getChildAt(p);
                if(view instanceof CheckBox){
                    if(((CheckBox)view).getText().toString().equals(familyDisease.get(i)))
                    {
                        ((CheckBox)view).setChecked(true);
                    }
                }
            }
            if(familyDisease.get(i).substring(0,6).equals("other_"))
            {
                checkBoxFamilyOther.setChecked(true);
                txtInputLayoutFamilyState.setVisibility(View.VISIBLE);
                editTextFamilyDiseaseOthers.setText(familyDisease.get(i).substring(6));
            }
        }
    }

    private List<String> createMotherDiseaseList()
    {
        List<String> motherDisese = new ArrayList<>();
        for(int i=0; i<layoutMotherDisease1.getChildCount(); i++)
        {
            View view = layoutMotherDisease1.getChildAt(i);
            if(view instanceof CheckBox){
                if(((CheckBox)view).isChecked()){
                    motherDisese.add(((CheckBox)view).getText().toString());
                }
            }
        }
        for(int i=0; i<layoutMotherDisease2.getChildCount(); i++)
        {
            View view = layoutMotherDisease2.getChildAt(i);
            if(view instanceof CheckBox){
                if(((CheckBox)view).isChecked()){
                    motherDisese.add(((CheckBox)view).getText().toString());
                }
            }
        }
        for(int i=0; i<layoutMotherDisease3.getChildCount(); i++)
        {
            View view = layoutMotherDisease3.getChildAt(i);
            if(view instanceof CheckBox){
                if(((CheckBox)view).isChecked()){
                    motherDisese.add(((CheckBox)view).getText().toString());
                }
            }
        }
        if(chkMotherOthers.isChecked()){
            motherDisese.add("other_"+editTextMotherDiseaseOthers.getText().toString());
        }
        return motherDisese;
    }

    private List<String> createFamilyDiseaseList()
    {
        List<String> familyDisese = new ArrayList<>();
        for(int i=0; i<layoutFamilyDisease1.getChildCount(); i++)
        {
            View view = layoutFamilyDisease1.getChildAt(i);
            if(view instanceof CheckBox){
                if(((CheckBox)view).isChecked()){
                    familyDisese.add(((CheckBox)view).getText().toString());
                }

            }
        }
        for(int i=0; i<layoutFamilyDisease2.getChildCount(); i++)
        {
            View view = layoutFamilyDisease2.getChildAt(i);
            if(view instanceof CheckBox){
                if(((CheckBox)view).isChecked()){
                    familyDisese.add(((CheckBox)view).getText().toString());
                }
            }
        }
        for(int i=0; i<layoutFamilyDisease3.getChildCount(); i++)
        {
            View view = layoutFamilyDisease3.getChildAt(i);
            if(view instanceof CheckBox){
                if(((CheckBox)view).isChecked()){
                    familyDisese.add(((CheckBox)view).getText().toString());
                }
            }
        }
        if(checkBoxFamilyOther.isChecked()){
            familyDisese.add("other_"+editTextFamilyDiseaseOthers.getText().toString());
        }
        return familyDisese;
    }

    private boolean CheckRequiredOnClick()
    {
        boolean empty;
        int check = 0;
        if(editTextMenstrDays.getText().toString().isEmpty() || editTextMenstrRounds.getText().toString().isEmpty()
                || radioGroupPractice.getCheckedRadioButtonId() == -1 || radioGroupMotherSmoke.getCheckedRadioButtonId() == -1
                || radioGroupFatherSmoke.getCheckedRadioButtonId() == -1)
        {
            if(editTextMenstrDays.getText().toString().isEmpty())
            {
                layoutMenstrDays.setErrorEnabled(true);
                layoutMenstrDays.setError("This field is required!");
            }else{
                layoutMenstrDays.setErrorEnabled(false);
                layoutMenstrDays.setError(null);
            }

            if(editTextMenstrRounds.getText().toString().isEmpty())
            {
                layoutMenstrRound.setErrorEnabled(true);
                layoutMenstrRound.setError("This field is required!");
            }else{
                layoutMenstrRound.setErrorEnabled(false);
                layoutMenstrRound.setError(null);
            }

            if(radioGroupPractice.getCheckedRadioButtonId() == -1)
            {
                radioButtonPracYes.setError("This field is required!");
                radioButtonPracNo.setError("This field is required!");
            }else{
                radioButtonPracYes.setError(null);
                radioButtonPracNo.setError(null);
            }

            if(radioGroupMotherSmoke.getCheckedRadioButtonId() == -1)
            {
                radioBtnMotherSmokeYes.setError("This field is required!");
                radioBtnMotherSmokeNo.setError("This field is required!");
            }else{
                radioBtnMotherSmokeYes.setError(null);
                radioBtnMotherSmokeNo.setError(null);
            }

            if(radioGroupFatherSmoke.getCheckedRadioButtonId() == -1)
            {
                radioBtnFatherSmokeYes.setError("This field is required!");
                radioBtnFatherSmokeNo.setError("This field is required!");
            }else{
                radioBtnFatherSmokeYes.setError(null);
                radioBtnFatherSmokeNo.setError(null);
            }
            check++;
        }

        if(radioButtonPracYes.isChecked())
        {
            if(editTextBCMethod.getText().toString().isEmpty() || editTextBCPeriod.getText().toString().isEmpty())
            {
                if(editTextBCMethod.getText().toString().isEmpty())
                {
                    txtInputLayoutMethod.setErrorEnabled(true);
                    txtInputLayoutMethod.setError("This field is required!");
                }else{
                    txtInputLayoutMethod.setErrorEnabled(false);
                    txtInputLayoutMethod.setError(null);
                }

                if(editTextBCPeriod.getText().toString().isEmpty())
                {
                    txtInputLayoutPeriod.setErrorEnabled(true);
                    txtInputLayoutPeriod.setError("This field is required!");
                }else{
                    txtInputLayoutPeriod.setErrorEnabled(false);
                    txtInputLayoutPeriod.setError("null");
                }
                check++;
            }
        }

        if(check > 0)
        {
            empty = true;
        }else{
            empty = false;
        }

        return empty;
    }

    private void CheckRequiredField()
    {
        editTextMenstrDays.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(editTextMenstrDays.getText().toString().isEmpty())
                {
                    layoutMenstrDays.setErrorEnabled(true);
                    layoutMenstrDays.setError("This field is required!");
                }else{
                    layoutMenstrDays.setErrorEnabled(false);
                    layoutMenstrDays.setError(null);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        editTextMenstrRounds.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(editTextMenstrRounds.getText().toString().isEmpty())
                {
                    layoutMenstrRound.setErrorEnabled(true);
                    layoutMenstrRound.setError("This field is required!");
                }else{
                    layoutMenstrRound.setErrorEnabled(false);
                    layoutMenstrRound.setError(null);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        editTextBCMethod.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(editTextBCMethod.getText().toString().isEmpty())
                {
                    txtInputLayoutMethod.setErrorEnabled(true);
                    txtInputLayoutMethod.setError("This field is required!");
                }else{
                    txtInputLayoutMethod.setErrorEnabled(false);
                    txtInputLayoutMethod.setError(null);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        editTextBCPeriod.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(editTextBCPeriod.getText().toString().isEmpty())
                {
                    txtInputLayoutPeriod.setErrorEnabled(true);
                    txtInputLayoutPeriod.setError("This field is required!");
                }else{
                    txtInputLayoutPeriod.setErrorEnabled(false);
                    txtInputLayoutPeriod.setError("null");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void DisableField()
    {
        editTextMenstrDays.setEnabled(false);
        editTextMenstrDays.setTextColor(Color.parseColor("#000000"));
        editTextMenstrRounds.setEnabled(false);
        editTextMenstrRounds.setTextColor(Color.parseColor("#000000"));
        editTextBCMethod.setEnabled(false);
        editTextBCMethod.setTextColor(Color.parseColor("#000000"));
        editTextBCPeriod.setEnabled(false);
        editTextBCPeriod.setTextColor(Color.parseColor("#000000"));
        editTextDos1.setEnabled(false);
        editTextDos1.setTextColor(Color.parseColor("#000000"));
        editTextDos2.setEnabled(false);
        editTextDos2.setTextColor(Color.parseColor("#000000"));
        editTextDosAddOn.setEnabled(false);
        editTextDosAddOn.setTextColor(Color.parseColor("#000000"));
        editTextMotherDiseaseOthers.setEnabled(false);
        editTextMotherDiseaseOthers.setTextColor(Color.parseColor("#000000"));
        editTextFamilyDiseaseOthers.setEnabled(false);
        editTextFamilyDiseaseOthers.setTextColor(Color.parseColor("#000000"));
        for(int i=0; i<radioGroupPractice.getChildCount(); i++)
        {
            ((RadioButton)radioGroupPractice.getChildAt(i)).setEnabled(false);
        }
        for(int i=0; i<radioGroupMotherSmoke.getChildCount(); i++)
        {
            ((RadioButton)radioGroupMotherSmoke.getChildAt(i)).setEnabled(false);
        }
        for(int i=0; i<radioGroupFatherSmoke.getChildCount(); i++)
        {
            ((RadioButton)radioGroupFatherSmoke.getChildAt(i)).setEnabled(false);
        }
        for(int i=0; i<layoutMotherDisease1.getChildCount(); i++)
        {
            View view = layoutMotherDisease1.getChildAt(i);
            if(view instanceof CheckBox){
                ((CheckBox)view).setEnabled(false);
            }
        }
        for(int i=0; i<layoutMotherDisease2.getChildCount(); i++)
        {
            View view = layoutMotherDisease2.getChildAt(i);
            if(view instanceof CheckBox){
                ((CheckBox)view).setEnabled(false);
            }
        }
        for(int i=0; i<layoutMotherDisease3.getChildCount(); i++)
        {
            View view = layoutMotherDisease3.getChildAt(i);
            if(view instanceof CheckBox){
                ((CheckBox)view).setEnabled(false);
            }
        }
        for(int i=0; i<layoutFamilyDisease1.getChildCount(); i++)
        {
            View view = layoutFamilyDisease1.getChildAt(i);
            if(view instanceof CheckBox){
                ((CheckBox)view).setEnabled(false);
            }
        }
        for(int i=0; i<layoutFamilyDisease2.getChildCount(); i++)
        {
            View view = layoutFamilyDisease2.getChildAt(i);
            if(view instanceof CheckBox){
                ((CheckBox)view).setEnabled(false);
            }
        }
        for(int i=0; i<layoutFamilyDisease3.getChildCount(); i++)
        {
            View view = layoutFamilyDisease3.getChildAt(i);
            if(view instanceof CheckBox){
                ((CheckBox)view).setEnabled(false);
            }
        }
    }

    private void EnableField()
    {
        editTextMenstrDays.setEnabled(true);
        editTextMenstrRounds.setEnabled(true);
        editTextBCMethod.setEnabled(true);
        editTextBCPeriod.setEnabled(true);
        editTextDos1.setEnabled(true);
        editTextDos2.setEnabled(true);
        editTextDosAddOn.setEnabled(true);
        for(int i=0; i<radioGroupPractice.getChildCount(); i++)
        {
            ((RadioButton)radioGroupPractice.getChildAt(i)).setEnabled(true);
        }
        for(int i=0; i<radioGroupMotherSmoke.getChildCount(); i++)
        {
            ((RadioButton)radioGroupMotherSmoke.getChildAt(i)).setEnabled(true);
        }
        for(int i=0; i<radioGroupFatherSmoke.getChildCount(); i++)
        {
            ((RadioButton)radioGroupFatherSmoke.getChildAt(i)).setEnabled(true);
        }
        for(int i=0; i<layoutMotherDisease1.getChildCount(); i++)
        {
            View view = layoutMotherDisease1.getChildAt(i);
            if(view instanceof CheckBox){
                ((CheckBox)view).setEnabled(true);
            }
        }
        for(int i=0; i<layoutMotherDisease2.getChildCount(); i++)
        {
            View view = layoutMotherDisease2.getChildAt(i);
            if(view instanceof CheckBox){
                ((CheckBox)view).setEnabled(true);
            }
        }
        for(int i=0; i<layoutMotherDisease3.getChildCount(); i++)
        {
            View view = layoutMotherDisease3.getChildAt(i);
            if(view instanceof CheckBox){
                ((CheckBox)view).setEnabled(true);
            }
        }
        for(int i=0; i<layoutFamilyDisease1.getChildCount(); i++)
        {
            View view = layoutFamilyDisease1.getChildAt(i);
            if(view instanceof CheckBox){
                ((CheckBox)view).setEnabled(true);
            }
        }
        for(int i=0; i<layoutFamilyDisease2.getChildCount(); i++)
        {
            View view = layoutFamilyDisease2.getChildAt(i);
            if(view instanceof CheckBox){
                ((CheckBox)view).setEnabled(true);
            }
        }
        for(int i=0; i<layoutFamilyDisease3.getChildCount(); i++)
        {
            View view = layoutFamilyDisease3.getChildAt(i);
            if(view instanceof CheckBox){
                ((CheckBox)view).setEnabled(true);
            }
        }
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
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
