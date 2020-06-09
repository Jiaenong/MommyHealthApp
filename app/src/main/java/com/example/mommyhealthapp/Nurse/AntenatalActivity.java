package com.example.mommyhealthapp.Nurse;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.LinearLayoutCompat;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.mommyhealthapp.AlertService;
import com.example.mommyhealthapp.Class.AntenatalTutorial;
import com.example.mommyhealthapp.Class.MedicalPersonnel;
import com.example.mommyhealthapp.MainActivity;
import com.example.mommyhealthapp.NotifyService;
import com.example.mommyhealthapp.R;
import com.example.mommyhealthapp.ReminderService;
import com.example.mommyhealthapp.SaveSharedPreference;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import io.grpc.internal.SharedResourceHolder;

public class AntenatalActivity extends AppCompatActivity {
    private CheckBox checkBoxAntenatalCare, checkBoxEarlyClinic, checkBoxSolveProblem, checkBoxNutrition, checkBoxMaternity, checkBoxExercise, checkBoxFamilyPlanner, checkBoxChildBirth,
            checkBoxSafeBirth, checkBoxEarlyBirth, checkBoxOralHealth;
    private TextView txtViewAntenatalCareDate, txtViewAntenatalCarePerson, txtViewEarlyClinicDate, txtViewEarlyClinicPerson, txtViewSolveProblemDate, txtViewSolveProblemPerson, txtViewNutritionDate, txtViewNutritionPerson,
            txtViewMaternityDate, txtViewMaternityPerson, txtViewExerciseDate, txtViewExercisePerson, txtViewFamilyPlannerDate, txtViewFamilyPlannerPerson, txtViewChildBirthDate, txtViewChildBirthPerson,
            txtViewSafeBirthDate, txtViewSafeBirthPerson, txtViewEarlyBirthDate, txtViewEarlyBirthPerson, txtViewOralHealthDate, txtViewOralHealthPerson;
    private Button btnAntenatalSave, btnAntenatalCancel;
    private LinearLayoutCompat layoutAntenatal;
    private RelativeLayout relativeLayoutAntenatalTutorial;
    private ProgressBar progressBarAntenatalTutorial;
    DatePickerDialog datePickerDialog;

    private String medicalPersonName, healthInfoId, key;
    private Boolean isEmpty;
    private int check = 0;

    private FirebaseFirestore mFirebaseFirestore;
    private DocumentReference mDocumentReference;
    private CollectionReference mCollectionReference, nCollectionReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_antenatal);

        checkBoxAntenatalCare = (CheckBox)findViewById(R.id.checkBoxAntenatalCare);
        checkBoxEarlyClinic = (CheckBox)findViewById(R.id.checkBoxEarlyClinic);
        checkBoxSolveProblem = (CheckBox)findViewById(R.id.checkBoxSolveProblem);
        checkBoxNutrition = (CheckBox)findViewById(R.id.checkBoxNutrition);
        checkBoxMaternity = (CheckBox)findViewById(R.id.checkBoxMaternity);
        checkBoxExercise = (CheckBox)findViewById(R.id.checkBoxExercise);
        checkBoxFamilyPlanner = (CheckBox)findViewById(R.id.checkBoxFamilyPlanner);
        checkBoxChildBirth = (CheckBox)findViewById(R.id.checkBoxChildBirth);
        checkBoxSafeBirth = (CheckBox)findViewById(R.id.checkBoxSafeBirth);
        checkBoxEarlyBirth = (CheckBox)findViewById(R.id.checkBoxEarlyBirth);
        checkBoxOralHealth = (CheckBox)findViewById(R.id.checkBoxOralHealth);

        txtViewAntenatalCareDate = (TextView)findViewById(R.id.txtViewAntenatalCareDate);
        txtViewAntenatalCarePerson = (TextView)findViewById(R.id.txtViewAntenatalCarePerson);
        txtViewEarlyClinicDate = (TextView)findViewById(R.id.txtViewEarlyClinicDate);
        txtViewEarlyClinicPerson = (TextView)findViewById(R.id.txtViewEarlyClinicPerson);
        txtViewSolveProblemDate = (TextView)findViewById(R.id.txtViewSolveProblemDate);
        txtViewSolveProblemPerson = (TextView)findViewById(R.id.txtViewSolveProblemPerson);
        txtViewNutritionDate = (TextView)findViewById(R.id.txtViewNutritionDate);
        txtViewNutritionPerson = (TextView)findViewById(R.id.txtViewNutritionPerson);
        txtViewMaternityDate = (TextView)findViewById(R.id.txtViewMaternityDate);
        txtViewMaternityPerson = (TextView)findViewById(R.id.txtViewMaternityPerson);
        txtViewExerciseDate = (TextView)findViewById(R.id.txtViewExerciseDate);
        txtViewExercisePerson = (TextView)findViewById(R.id.txtViewExercisePerson);
        txtViewFamilyPlannerDate = (TextView)findViewById(R.id.txtViewFamilyPlannerDate);
        txtViewFamilyPlannerPerson = (TextView)findViewById(R.id.txtViewFamilyPlannerPerson);
        txtViewChildBirthDate = (TextView)findViewById(R.id.txtViewChildBirthDate);
        txtViewChildBirthPerson = (TextView)findViewById(R.id.txtViewChildBirthPerson);
        txtViewSafeBirthDate = (TextView)findViewById(R.id.txtViewSafeBirthDate);
        txtViewSafeBirthPerson = (TextView)findViewById(R.id.txtViewSafeBirthPerson);
        txtViewEarlyBirthDate = (TextView)findViewById(R.id.txtViewEarlyBirthDate);
        txtViewEarlyBirthPerson = (TextView)findViewById(R.id.txtViewEarlyBirthPerson);
        txtViewOralHealthDate = (TextView)findViewById(R.id.txtViewOralHealthDate);
        txtViewOralHealthPerson = (TextView)findViewById(R.id.txtViewOralHealthPerson);

        layoutAntenatal = (LinearLayoutCompat)findViewById(R.id.layoutAntenatal);
        relativeLayoutAntenatalTutorial = (RelativeLayout)findViewById(R.id.relativeLayoutAntenatalTutorial);
        progressBarAntenatalTutorial = (ProgressBar)findViewById(R.id.progressBarAntenatalTutorial);

        btnAntenatalCancel = (Button)findViewById(R.id.btnAntenatalCancel);
        btnAntenatalSave = (Button)findViewById(R.id.btnAntenatalSave);

        btnAntenatalCancel.setVisibility(View.GONE);
        progressBarAntenatalTutorial.setVisibility(View.VISIBLE);
        layoutAntenatal.setVisibility(View.GONE);

        mFirebaseFirestore = FirebaseFirestore.getInstance();
        if(!SaveSharedPreference.getUser(AntenatalActivity.this).equals("Mommy"))
        {
            mDocumentReference = mFirebaseFirestore.collection("MedicalPersonnel").document(SaveSharedPreference.getID(AntenatalActivity.this));
            mDocumentReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    MedicalPersonnel md = documentSnapshot.toObject(MedicalPersonnel.class);
                    medicalPersonName = md.getName();
                }
            });
        }

        mCollectionReference = mFirebaseFirestore.collection("MommyHealthInfo");
        mCollectionReference.whereEqualTo("mommyId", SaveSharedPreference.getMummyId(AntenatalActivity.this)).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for(QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots)
                {
                    healthInfoId = documentSnapshot.getId();
                }
                nCollectionReference = mFirebaseFirestore.collection("MommyHealthInfo").document(healthInfoId).collection("AntenatalTutorial");
                nCollectionReference.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        if(queryDocumentSnapshots.isEmpty())
                        {
                            isEmpty = true;
                            progressBarAntenatalTutorial.setVisibility(View.GONE);
                            layoutAntenatal.setVisibility(View.VISIBLE);
                            if(SaveSharedPreference.getUser(AntenatalActivity.this).equals("Mommy"))
                            {
                                btnAntenatalCancel.setVisibility(View.GONE);
                                btnAntenatalSave.setVisibility(View.GONE);
                                DisableField();
                            }
                        }else{
                            isEmpty = false;
                            DisableField();
                            if(SaveSharedPreference.getUser(AntenatalActivity.this).equals("Mommy"))
                            {
                                btnAntenatalCancel.setVisibility(View.GONE);
                                btnAntenatalSave.setVisibility(View.GONE);
                            }
                            for(QueryDocumentSnapshot documentSnapshots : queryDocumentSnapshots)
                            {
                                key = documentSnapshots.getId();
                                AntenatalTutorial at = documentSnapshots.toObject(AntenatalTutorial.class);
                                SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                                if(at.getAntenatalCareStatus().equals("Yes"))
                                {
                                    checkBoxAntenatalCare.setChecked(true);
                                    txtViewAntenatalCarePerson.setText(at.getAntenatalCarePerson());
                                    txtViewAntenatalCareDate.setText(dateFormat.format(at.getAntenatalCareDate()));
                                }
                                if(at.getEarlyClinicStatus().equals("Yes"))
                                {
                                    checkBoxEarlyClinic.setChecked(true);
                                    txtViewEarlyClinicPerson.setText(at.getEarlyClinicPerson());
                                    txtViewEarlyClinicDate.setText(dateFormat.format(at.getEarlyClinicDate()));
                                }
                                if(at.getSolveProblemStatus().equals("Yes"))
                                {
                                    checkBoxSolveProblem.setChecked(true);
                                    txtViewSolveProblemPerson.setText(at.getSolveProblemPerson());
                                    txtViewSolveProblemDate.setText(dateFormat.format(at.getSolveProblemDate()));
                                }
                                if(at.getNutritionStatus().equals("Yes"))
                                {
                                    checkBoxNutrition.setChecked(true);
                                    txtViewNutritionPerson.setText(at.getNutritionPerson());
                                    txtViewNutritionDate.setText(dateFormat.format(at.getNutritionDate()));
                                }
                                if(at.getMaternityStatus().equals("Yes"))
                                {
                                    checkBoxMaternity.setChecked(true);
                                    txtViewMaternityPerson.setText(at.getMaternityPerson());
                                    txtViewMaternityDate.setText(dateFormat.format(at.getMaternityDate()));
                                }
                                if(at.getExerciseStatus().equals("Yes"))
                                {
                                    checkBoxExercise.setChecked(true);
                                    txtViewExercisePerson.setText(at.getExercisePerson());
                                    txtViewExerciseDate.setText(dateFormat.format(at.getExerciseDate()));
                                }
                                if(at.getFamilyPlannerStatus().equals("Yes"))
                                {
                                    checkBoxFamilyPlanner.setChecked(true);
                                    txtViewFamilyPlannerPerson.setText(at.getFamilyPlannerPerson());
                                    txtViewFamilyPlannerDate.setText(dateFormat.format(at.getFamilyPlannerDate()));
                                }
                                if(at.getChildBirthStatus().equals("Yes"))
                                {
                                    checkBoxChildBirth.setChecked(true);
                                    txtViewChildBirthPerson.setText(at.getChildBirthPerson());
                                    txtViewChildBirthDate.setText(dateFormat.format(at.getChildBirthDate()));
                                }
                                if(at.getEarlyBirthStatus().equals("Yes"))
                                {
                                    checkBoxEarlyBirth.setChecked(true);
                                    txtViewEarlyBirthPerson.setText(at.getEarlyBirthPerson());
                                    txtViewEarlyBirthDate.setText(dateFormat.format(at.getEarlyBirthPerson()));
                                }
                                if(at.getSaveBirthStatus().equals("Yes"))
                                {
                                    checkBoxSafeBirth.setChecked(true);
                                    txtViewSafeBirthPerson.setText(at.getSaveBirthPerson());
                                    txtViewSafeBirthDate.setText(dateFormat.format(at.getSaveBirthDate()));
                                }
                                if(at.getOralHealthStatus().equals("Yes"))
                                {
                                    checkBoxOralHealth.setChecked(true);
                                    txtViewOralHealthPerson.setText(at.getOralHealthPerson());
                                    txtViewOralHealthDate.setText(dateFormat.format(at.getOralHealthDate()));
                                }
                                progressBarAntenatalTutorial.setVisibility(View.GONE);
                                layoutAntenatal.setVisibility(View.VISIBLE);
                                btnAntenatalSave.setText("Update");
                            }
                        }
                    }
                });
            }
        });

        ViewGoneDateText();
        DateTextView();
        CheckBoxIsCheck();

        btnAntenatalSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isEmpty == true)
                {
                    List<TextView> dateText = new ArrayList<>();
                    nCollectionReference = mFirebaseFirestore.collection("MommyHealthInfo").document(healthInfoId).collection("AntenatalTutorial");
                    String antenatalCareStatus = "",earlyClinicStatus = "", solveProblemStatus = "", nutritionStatus = "", maternityStatus = "", exerciseStatus = "",
                            familyPlannerStatus = "", childBirthStatus = "", saveBirthStatus = "", earlyBirthStatus = "", oralHealthStatus = "";
                    String antenatalCarePerson = "", earlyClinicPerson = "", solveProblemPerson = "", nutritionPerson = "", maternityPerson = "", exercisePerson = "",
                            familyPlannerPerson = "", childBirthPerson = "", saveBirthPerson = "", earlyBirthPerson = "", oralHealthPerson = "";
                    Date antenatalCareDate = null, earlyClinicDate = null, solveProblemDate = null, nutritionDate = null, maternityDate = null, exerciseDate = null,
                            familyPlannerDate = null, childBirthDate = null, saveBirthDate = null, earlyBirthDate = null, oralHealthDate = null;
                    if(checkBoxAntenatalCare.isChecked())
                    {
                        antenatalCareStatus = "Yes";
                        antenatalCarePerson = txtViewAntenatalCarePerson.getText().toString();
                        try {
                            antenatalCareDate = new SimpleDateFormat("dd/MM/yyyy").parse(txtViewAntenatalCareDate.getText().toString());
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        dateText.add(txtViewAntenatalCareDate);
                    }else{
                        antenatalCareStatus = "No";
                        antenatalCarePerson = txtViewAntenatalCarePerson.getText().toString();
                        antenatalCareDate = null;
                    }
                    if(checkBoxEarlyClinic.isChecked())
                    {
                        earlyClinicStatus = "Yes";
                        earlyClinicPerson = txtViewEarlyClinicPerson.getText().toString();
                        try {
                            earlyClinicDate = new SimpleDateFormat("dd/MM/yyyy").parse(txtViewEarlyClinicDate.getText().toString());
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        dateText.add(txtViewEarlyClinicDate);
                    }else{
                        earlyClinicStatus = "No";
                        earlyClinicPerson = txtViewEarlyClinicPerson.getText().toString();
                        earlyClinicDate = null;
                    }
                    if(checkBoxSolveProblem.isChecked())
                    {
                        solveProblemStatus = "Yes";
                        solveProblemPerson = txtViewSolveProblemPerson.getText().toString();
                        try {
                            solveProblemDate = new SimpleDateFormat("dd/MM/yyyy").parse(txtViewSolveProblemDate.getText().toString());
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        dateText.add(txtViewSolveProblemDate);
                    }else{
                        solveProblemStatus = "No";
                        solveProblemPerson = txtViewSolveProblemPerson.getText().toString();
                        solveProblemDate = null;
                    }
                    if(checkBoxNutrition.isChecked())
                    {
                        nutritionStatus = "Yes";
                        nutritionPerson = txtViewNutritionPerson.getText().toString();
                        try {
                            nutritionDate = new SimpleDateFormat("dd/MM/yyyy").parse(txtViewNutritionDate.getText().toString());
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        dateText.add(txtViewNutritionDate);
                    }else{
                        nutritionStatus = "No";
                        nutritionPerson = txtViewNutritionPerson.getText().toString();
                        nutritionDate = null;
                    }
                    if(checkBoxMaternity.isChecked())
                    {
                        maternityStatus = "Yes";
                        maternityPerson = txtViewMaternityPerson.getText().toString();
                        try {
                            maternityDate = new SimpleDateFormat("dd/MM/yyyy").parse(txtViewMaternityDate.getText().toString());
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        dateText.add(txtViewMaternityDate);
                    }else{
                        maternityStatus = "No";
                        maternityPerson = txtViewMaternityPerson.getText().toString();
                        maternityDate = null;
                    }
                    if(checkBoxExercise.isChecked())
                    {
                        exerciseStatus = "Yes";
                        exercisePerson = txtViewExercisePerson.getText().toString();
                        try {
                            exerciseDate = new SimpleDateFormat("dd/MM/yyyy").parse(txtViewExerciseDate.getText().toString());
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        dateText.add(txtViewExerciseDate);
                    }else{
                        exerciseStatus = "No";
                        exercisePerson = txtViewExercisePerson.getText().toString();
                        exerciseDate = null;
                    }
                    if(checkBoxFamilyPlanner.isChecked())
                    {
                        familyPlannerStatus = "Yes";
                        familyPlannerPerson = txtViewFamilyPlannerPerson.getText().toString();
                        try {
                            familyPlannerDate = new SimpleDateFormat("dd/MM/yyyy").parse(txtViewFamilyPlannerDate.getText().toString());
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        dateText.add(txtViewFamilyPlannerDate);
                    }else{
                        familyPlannerStatus = "No";
                        familyPlannerPerson = txtViewFamilyPlannerPerson.getText().toString();
                        familyPlannerDate = null;
                    }
                    if(checkBoxChildBirth.isChecked())
                    {
                        childBirthStatus = "Yes";
                        childBirthPerson = txtViewChildBirthPerson.getText().toString();
                        try {
                            childBirthDate = new SimpleDateFormat("dd/MM/yyyy").parse(txtViewChildBirthDate.getText().toString());
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        dateText.add(txtViewChildBirthDate);
                    }else{
                        childBirthStatus = "No";
                        childBirthPerson = txtViewChildBirthPerson.getText().toString();
                        childBirthDate = null;
                    }
                    if(checkBoxSafeBirth.isChecked())
                    {
                        saveBirthStatus = "Yes";
                        saveBirthPerson = txtViewSafeBirthPerson.getText().toString();
                        try {
                            saveBirthDate = new SimpleDateFormat("dd/MM/yyyy").parse(txtViewSafeBirthDate.getText().toString());
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        dateText.add(txtViewSafeBirthDate);
                    }else{
                        saveBirthStatus = "No";
                        saveBirthPerson = txtViewSafeBirthPerson.getText().toString();
                        saveBirthDate = null;
                    }
                    if(checkBoxEarlyBirth.isChecked())
                    {
                        earlyBirthStatus = "Yes";
                        earlyBirthPerson = txtViewEarlyBirthPerson.getText().toString();
                        try {
                            earlyBirthDate = new SimpleDateFormat("dd/MM/yyyy").parse(txtViewEarlyBirthDate.getText().toString());
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        dateText.add(txtViewEarlyBirthDate);
                    }else{
                        earlyBirthStatus = "No";
                        earlyBirthPerson = txtViewEarlyBirthPerson.getText().toString();
                        earlyBirthDate = null;
                    }
                    if(checkBoxOralHealth.isChecked())
                    {
                        oralHealthStatus = "Yes";
                        oralHealthPerson = txtViewOralHealthPerson.getText().toString();
                        try {
                            oralHealthDate = new SimpleDateFormat("dd/MM/yyyy").parse(txtViewOralHealthDate.getText().toString());
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        dateText.add(txtViewOralHealthDate);
                    }else{
                        oralHealthStatus = "No";
                        oralHealthPerson = txtViewOralHealthPerson.getText().toString();
                        oralHealthDate = null;
                    }
                    if(dateText.isEmpty())
                    {
                        final AlertDialog.Builder builder = new AlertDialog.Builder(AntenatalActivity.this);
                        builder.setTitle("Error");
                        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                return;
                            }
                        });
                        builder.setMessage("At least one selection must be selected");
                        AlertDialog alert = builder.create();
                        alert.show();
                    }else{
                        boolean checkDate = CheckDateSelected(dateText);
                        AntenatalTutorial at = new AntenatalTutorial(antenatalCareStatus, antenatalCareDate, antenatalCarePerson, earlyClinicStatus, earlyClinicDate,
                                earlyClinicPerson, solveProblemStatus, solveProblemDate, solveProblemPerson, nutritionStatus,
                                nutritionDate, nutritionPerson, maternityStatus, maternityDate, maternityPerson, exerciseStatus,
                                exerciseDate, exercisePerson, familyPlannerStatus, familyPlannerDate, familyPlannerPerson,
                                childBirthStatus, childBirthDate, childBirthPerson, saveBirthStatus, saveBirthDate, saveBirthPerson,
                                earlyBirthStatus, earlyBirthDate, earlyBirthPerson, oralHealthStatus, oralHealthDate, oralHealthPerson);
                        if(checkDate == true)
                        {
                            nCollectionReference.add(at).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                @Override
                                public void onSuccess(DocumentReference documentReference) {
                                    AlertDialog.Builder builder = new AlertDialog.Builder(AntenatalActivity.this);
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
                    }
                }else{
                    check++;
                    if(check == 1)
                    {
                        btnAntenatalCancel.setVisibility(View.VISIBLE);
                        EnableField();
                    }else if(check == 2)
                    {
                        List<TextView> dateText = new ArrayList<>();
                        DocumentReference nDocumentReference = mFirebaseFirestore.collection("MommyHealthInfo").document(healthInfoId).collection("AntenatalTutorial").document(key);
                        String antenatalCareStatus = "",earlyClinicStatus = "", solveProblemStatus = "", nutritionStatus = "", maternityStatus = "", exerciseStatus = "",
                                familyPlannerStatus = "", childBirthStatus = "", saveBirthStatus = "", earlyBirthStatus = "", oralHealthStatus = "";
                        String antenatalCarePerson = "", earlyClinicPerson = "", solveProblemPerson = "", nutritionPerson = "", maternityPerson = "", exercisePerson = "",
                                familyPlannerPerson = "", childBirthPerson = "", saveBirthPerson = "", earlyBirthPerson = "", oralHealthPerson = "";
                        Date antenatalCareDate = null, earlyClinicDate = null, solveProblemDate = null, nutritionDate = null, maternityDate = null, exerciseDate = null,
                                familyPlannerDate = null, childBirthDate = null, saveBirthDate = null, earlyBirthDate = null, oralHealthDate = null;

                        if(checkBoxAntenatalCare.isChecked())
                        {
                            antenatalCareStatus = "Yes";
                            antenatalCarePerson = txtViewAntenatalCarePerson.getText().toString();
                            try {
                                antenatalCareDate = new SimpleDateFormat("dd/MM/yyyy").parse(txtViewAntenatalCareDate.getText().toString());
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                            dateText.add(txtViewAntenatalCareDate);
                        }else{
                            antenatalCareStatus = "No";
                            antenatalCarePerson = txtViewAntenatalCarePerson.getText().toString();
                            antenatalCareDate = null;
                        }
                        if(checkBoxEarlyClinic.isChecked())
                        {
                            earlyClinicStatus = "Yes";
                            earlyClinicPerson = txtViewEarlyClinicPerson.getText().toString();
                            try {
                                earlyClinicDate = new SimpleDateFormat("dd/MM/yyyy").parse(txtViewEarlyClinicDate.getText().toString());
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                            dateText.add(txtViewEarlyClinicDate);
                        }else{
                            earlyClinicStatus = "No";
                            earlyClinicPerson = txtViewEarlyClinicPerson.getText().toString();
                            earlyClinicDate = null;
                        }
                        if(checkBoxSolveProblem.isChecked())
                        {
                            solveProblemStatus = "Yes";
                            solveProblemPerson = txtViewSolveProblemPerson.getText().toString();
                            try {
                                solveProblemDate = new SimpleDateFormat("dd/MM/yyyy").parse(txtViewSolveProblemDate.getText().toString());
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                            dateText.add(txtViewSolveProblemDate);
                        }else{
                            solveProblemStatus = "No";
                            solveProblemPerson = txtViewSolveProblemPerson.getText().toString();
                            solveProblemDate = null;
                        }
                        if(checkBoxNutrition.isChecked())
                        {
                            nutritionStatus = "Yes";
                            nutritionPerson = txtViewNutritionPerson.getText().toString();
                            try {
                                nutritionDate = new SimpleDateFormat("dd/MM/yyyy").parse(txtViewNutritionDate.getText().toString());
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                            dateText.add(txtViewNutritionDate);
                        }else{
                            nutritionStatus = "No";
                            nutritionPerson = txtViewNutritionPerson.getText().toString();
                            nutritionDate = null;
                        }
                        if(checkBoxMaternity.isChecked())
                        {
                            maternityStatus = "Yes";
                            maternityPerson = txtViewMaternityPerson.getText().toString();
                            try {
                                maternityDate = new SimpleDateFormat("dd/MM/yyyy").parse(txtViewMaternityDate.getText().toString());
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                            dateText.add(txtViewMaternityDate);
                        }else{
                            maternityStatus = "No";
                            maternityPerson = txtViewMaternityPerson.getText().toString();
                            maternityDate = null;
                        }
                        if(checkBoxExercise.isChecked())
                        {
                            exerciseStatus = "Yes";
                            exercisePerson = txtViewExercisePerson.getText().toString();
                            try {
                                exerciseDate = new SimpleDateFormat("dd/MM/yyyy").parse(txtViewExerciseDate.getText().toString());
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                            dateText.add(txtViewExerciseDate);
                        }else{
                            exerciseStatus = "No";
                            exercisePerson = txtViewExercisePerson.getText().toString();
                            exerciseDate = null;
                        }
                        if(checkBoxFamilyPlanner.isChecked())
                        {
                            familyPlannerStatus = "Yes";
                            familyPlannerPerson = txtViewFamilyPlannerPerson.getText().toString();
                            try {
                                familyPlannerDate = new SimpleDateFormat("dd/MM/yyyy").parse(txtViewFamilyPlannerDate.getText().toString());
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                            dateText.add(txtViewFamilyPlannerDate);
                        }else{
                            familyPlannerStatus = "No";
                            familyPlannerPerson = txtViewFamilyPlannerPerson.getText().toString();
                            familyPlannerDate = null;
                        }
                        if(checkBoxChildBirth.isChecked())
                        {
                            childBirthStatus = "Yes";
                            childBirthPerson = txtViewChildBirthPerson.getText().toString();
                            try {
                                childBirthDate = new SimpleDateFormat("dd/MM/yyyy").parse(txtViewChildBirthDate.getText().toString());
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                            dateText.add(txtViewChildBirthDate);
                        }else{
                            childBirthStatus = "No";
                            childBirthPerson = txtViewChildBirthPerson.getText().toString();
                            childBirthDate = null;
                        }
                        if(checkBoxSafeBirth.isChecked())
                        {
                            saveBirthStatus = "Yes";
                            saveBirthPerson = txtViewSafeBirthPerson.getText().toString();
                            try {
                                saveBirthDate = new SimpleDateFormat("dd/MM/yyyy").parse(txtViewSafeBirthDate.getText().toString());
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                            dateText.add(txtViewSafeBirthDate);
                        }else{
                            saveBirthStatus = "No";
                            saveBirthPerson = txtViewSafeBirthPerson.getText().toString();
                            saveBirthDate = null;
                        }
                        if(checkBoxEarlyBirth.isChecked())
                        {
                            earlyBirthStatus = "Yes";
                            earlyBirthPerson = txtViewEarlyBirthPerson.getText().toString();
                            try {
                                earlyBirthDate = new SimpleDateFormat("dd/MM/yyyy").parse(txtViewEarlyBirthDate.getText().toString());
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                            dateText.add(txtViewEarlyBirthDate);
                        }else{
                            earlyBirthStatus = "No";
                            earlyBirthPerson = txtViewEarlyBirthPerson.getText().toString();
                            earlyBirthDate = null;
                        }
                        if(checkBoxOralHealth.isChecked())
                        {
                            oralHealthStatus = "Yes";
                            oralHealthPerson = txtViewOralHealthPerson.getText().toString();
                            try {
                                oralHealthDate = new SimpleDateFormat("dd/MM/yyyy").parse(txtViewOralHealthDate.getText().toString());
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                            dateText.add(txtViewOralHealthDate);
                        }else{
                            oralHealthStatus = "No";
                            oralHealthPerson = txtViewOralHealthPerson.getText().toString();
                            oralHealthDate = null;
                        }
                        if(dateText.isEmpty())
                        {
                            check = 1;
                            final AlertDialog.Builder builder = new AlertDialog.Builder(AntenatalActivity.this);
                            builder.setTitle("Error");
                            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    return;
                                }
                            });
                            builder.setMessage("At least one selection must be selected");
                            AlertDialog alert = builder.create();
                            alert.show();
                        }else{
                            boolean checkDate = CheckDateSelected(dateText);
                            Log.i("Testing2", checkDate+"");
                            if(checkDate == true)
                            {
                                nDocumentReference.update("antenatalCareStatus", antenatalCareStatus);
                                nDocumentReference.update("antenatalCareDate", antenatalCareDate);
                                nDocumentReference.update("antenatalCarePerson", antenatalCarePerson);
                                nDocumentReference.update("earlyClinicStatus", earlyClinicStatus);
                                nDocumentReference.update("earlyClinicDate", earlyClinicDate);
                                nDocumentReference.update("earlyClinicPerson", earlyClinicPerson);
                                nDocumentReference.update("solveProblemStatus", solveProblemStatus);
                                nDocumentReference.update("solveProblemDate", solveProblemDate);
                                nDocumentReference.update("solveProblemPerson", solveProblemPerson);
                                nDocumentReference.update("nutritionStatus", nutritionStatus);
                                nDocumentReference.update("nutritionDate", nutritionDate);
                                nDocumentReference.update("nutritionPerson", nutritionPerson);
                                nDocumentReference.update("maternityStatus", maternityStatus);
                                nDocumentReference.update("maternityDate", maternityDate);
                                nDocumentReference.update("maternityPerson", maternityPerson);
                                nDocumentReference.update("exerciseStatus", exerciseStatus);
                                nDocumentReference.update("exerciseDate", exerciseDate);
                                nDocumentReference.update("exercisePerson", exercisePerson);
                                nDocumentReference.update("familyPlannerStatus", familyPlannerStatus);
                                nDocumentReference.update("familyPlannerDate", familyPlannerDate);
                                nDocumentReference.update("familyPlannerPerson", familyPlannerPerson);
                                nDocumentReference.update("childBirthStatus", childBirthStatus);
                                nDocumentReference.update("childBirthDate", childBirthDate);
                                nDocumentReference.update("childBirthPerson", childBirthPerson);
                                nDocumentReference.update("saveBirthStatus", saveBirthStatus);
                                nDocumentReference.update("saveBirthDate", saveBirthDate);
                                nDocumentReference.update("saveBirthPerson", saveBirthPerson);
                                nDocumentReference.update("earlyBirthStatus", earlyBirthStatus);
                                nDocumentReference.update("earlyBirthDate", earlyBirthDate);
                                nDocumentReference.update("earlyBirthPerson", earlyBirthPerson);
                                nDocumentReference.update("oralHealthStatus", oralHealthStatus);
                                nDocumentReference.update("oralHealthDate", oralHealthDate);
                                nDocumentReference.update("oralHealthPerson", oralHealthPerson);
                                DisableField();
                                btnAntenatalCancel.setVisibility(View.GONE);
                                check = 0;
                                Snackbar snackbar = Snackbar.make(relativeLayoutAntenatalTutorial, "Updated Successfully!", Snackbar.LENGTH_LONG);
                                snackbar.show();
                            }else{
                                check = 1;
                            }
                        }
                    }
                }
            }
        });

        btnAntenatalCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DisableField();
                btnAntenatalCancel.setVisibility(View.GONE);
                check = 0;
            }
        });

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private boolean CheckDateSelected(List<TextView> dateText)
    {
        boolean dateSelected;
        List<TextView> dateNotSelected = new ArrayList<>();
        for(int i=0; i<dateText.size(); i++)
        {
            Log.i("Testing", dateText.get(i).getText().toString());
            if(dateText.get(i).getText().toString().equals(getResources().getString(R.string.secN_Date)))
            {
                dateNotSelected.add(dateText.get(i));
            }
        }
        Log.i("Testing1.5", dateNotSelected.size()+"");
        if(dateNotSelected.isEmpty())
        {
            dateSelected = true;
        }else{
            dateSelected = false;
            for(int n=0; n<dateNotSelected.size(); n++)
            {
                dateNotSelected.get(n).requestFocus();
                dateNotSelected.get(n).setError("This field is required");
            }
        }
        return dateSelected;
    }

    private void DisableField()
    {
        for(int i = 0; i<layoutAntenatal.getChildCount(); i++)
        {
            View view = layoutAntenatal.getChildAt(i);
            if(view instanceof RelativeLayout){
                for(int n = 0; n<((RelativeLayout) view).getChildCount(); n++)
                {
                    View v = ((RelativeLayout) view).getChildAt(n);
                    if(v instanceof CheckBox)
                    {
                        ((CheckBox)v).setEnabled(false);
                    }
                    if(v instanceof TextView)
                    {
                        ((TextView)v).setClickable(false);
                    }
                }
            }
        }
    }

    private void EnableField()
    {
        for(int i = 0; i<layoutAntenatal.getChildCount(); i++)
        {
            View view = layoutAntenatal.getChildAt(i);
            if(view instanceof RelativeLayout){
                for(int n = 0; n<((RelativeLayout) view).getChildCount(); n++)
                {
                    View v = ((RelativeLayout) view).getChildAt(n);
                    if(v instanceof CheckBox)
                    {
                        ((CheckBox)v).setEnabled(true);
                    }
                    if(v instanceof TextView)
                    {
                        ((TextView)v).setClickable(true);
                    }
                }
            }
        }
    }

    private void CheckBoxIsCheck()
    {
        checkBoxAntenatalCare.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                {
                    txtViewAntenatalCareDate.setVisibility(View.VISIBLE);
                    txtViewAntenatalCarePerson.setVisibility(View.VISIBLE);
                    txtViewAntenatalCarePerson.setText(medicalPersonName);
                    txtViewAntenatalCareDate.setText(getResources().getString(R.string.secN_Date));
                }else{
                    txtViewAntenatalCareDate.setVisibility(View.GONE);
                    txtViewAntenatalCarePerson.setVisibility(View.GONE);
                    txtViewAntenatalCarePerson.setText(R.string.secN_medicalPersonnale_name);
                    txtViewAntenatalCareDate.setText("");
                }
            }
        });
        checkBoxEarlyClinic.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                {
                    txtViewEarlyClinicDate.setVisibility(View.VISIBLE);
                    txtViewEarlyClinicPerson.setVisibility(View.VISIBLE);
                    txtViewEarlyClinicPerson.setText(medicalPersonName);
                    txtViewEarlyClinicDate.setText(getResources().getString(R.string.secN_Date));
                }else{
                    txtViewEarlyClinicDate.setVisibility(View.GONE);
                    txtViewEarlyClinicPerson.setVisibility(View.GONE);
                    txtViewEarlyClinicPerson.setText(R.string.secN_medicalPersonnale_name);
                    txtViewEarlyClinicDate.setText("");
                }
            }
        });
        checkBoxSolveProblem.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                {
                    txtViewSolveProblemDate.setVisibility(View.VISIBLE);
                    txtViewSolveProblemPerson.setVisibility(View.VISIBLE);
                    txtViewSolveProblemPerson.setText(medicalPersonName);
                    txtViewSolveProblemDate.setText(getResources().getString(R.string.secN_Date));
                }else{
                    txtViewSolveProblemDate.setVisibility(View.GONE);
                    txtViewSolveProblemPerson.setVisibility(View.GONE);
                    txtViewSolveProblemPerson.setText(R.string.secN_medicalPersonnale_name);
                    txtViewSolveProblemDate.setText("");
                }
            }
        });
        checkBoxNutrition.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                {
                    txtViewNutritionDate.setVisibility(View.VISIBLE);
                    txtViewNutritionPerson.setVisibility(View.VISIBLE);
                    txtViewNutritionPerson.setText(medicalPersonName);
                    txtViewNutritionDate.setText(getResources().getString(R.string.secN_Date));
                }else{
                    txtViewNutritionDate.setVisibility(View.GONE);
                    txtViewNutritionPerson.setVisibility(View.GONE);
                    txtViewNutritionPerson.setText(R.string.secN_medicalPersonnale_name);
                    txtViewNutritionDate.setText("");
                }
            }
        });
        checkBoxMaternity.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                {
                    txtViewMaternityDate.setVisibility(View.VISIBLE);
                    txtViewMaternityPerson.setVisibility(View.VISIBLE);
                    txtViewMaternityPerson.setText(medicalPersonName);
                    txtViewMaternityDate.setText(getResources().getString(R.string.secN_Date));
                }else{
                    txtViewMaternityDate.setVisibility(View.GONE);
                    txtViewMaternityPerson.setVisibility(View.GONE);
                    txtViewMaternityPerson.setText(R.string.secN_medicalPersonnale_name);
                    txtViewMaternityDate.setText("");
                }
            }
        });
        checkBoxExercise.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                {
                    txtViewExerciseDate.setVisibility(View.VISIBLE);
                    txtViewExercisePerson.setVisibility(View.VISIBLE);
                    txtViewExercisePerson.setText(medicalPersonName);
                    txtViewExerciseDate.setText(getResources().getString(R.string.secN_Date));
                }else{
                    txtViewExerciseDate.setVisibility(View.GONE);
                    txtViewExercisePerson.setVisibility(View.GONE);
                    txtViewExercisePerson.setText(R.string.secN_medicalPersonnale_name);
                    txtViewExerciseDate.setText("");
                }
            }
        });
        checkBoxFamilyPlanner.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                {
                    txtViewFamilyPlannerDate.setVisibility(View.VISIBLE);
                    txtViewFamilyPlannerPerson.setVisibility(View.VISIBLE);
                    txtViewFamilyPlannerPerson.setText(medicalPersonName);
                    txtViewFamilyPlannerDate.setText(getResources().getString(R.string.secN_Date));
                }else{
                    txtViewFamilyPlannerDate.setVisibility(View.GONE);
                    txtViewFamilyPlannerPerson.setVisibility(View.GONE);
                    txtViewFamilyPlannerPerson.setText(R.string.secN_medicalPersonnale_name);
                    txtViewFamilyPlannerDate.setText("");
                }
            }
        });
        checkBoxChildBirth.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                {
                    txtViewChildBirthDate.setVisibility(View.VISIBLE);
                    txtViewChildBirthPerson.setVisibility(View.VISIBLE);
                    txtViewChildBirthPerson.setText(medicalPersonName);
                    txtViewChildBirthDate.setText(getResources().getString(R.string.secN_Date));
                }else{
                    txtViewChildBirthDate.setVisibility(View.GONE);
                    txtViewChildBirthPerson.setVisibility(View.GONE);
                    txtViewChildBirthPerson.setText(R.string.secN_medicalPersonnale_name);
                    txtViewChildBirthDate.setText("");
                }
            }
        });
        checkBoxSafeBirth.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                {
                    txtViewSafeBirthDate.setVisibility(View.VISIBLE);
                    txtViewSafeBirthPerson.setVisibility(View.VISIBLE);
                    txtViewSafeBirthPerson.setText(medicalPersonName);
                    txtViewSafeBirthDate.setText(getResources().getString(R.string.secN_Date));
                }else{
                    txtViewSafeBirthDate.setVisibility(View.GONE);
                    txtViewSafeBirthPerson.setVisibility(View.GONE);
                    txtViewSafeBirthPerson.setText(R.string.secN_medicalPersonnale_name);
                    txtViewSafeBirthDate.setText("");
                }
            }
        });
        checkBoxEarlyBirth.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                {
                    txtViewEarlyBirthDate.setVisibility(View.VISIBLE);
                    txtViewEarlyBirthPerson.setVisibility(View.VISIBLE);
                    txtViewEarlyBirthPerson.setText(medicalPersonName);
                    txtViewEarlyBirthDate.setText(getResources().getString(R.string.secN_Date));
                }else{
                    txtViewEarlyBirthDate.setVisibility(View.GONE);
                    txtViewEarlyBirthPerson.setVisibility(View.GONE);
                    txtViewEarlyBirthPerson.setText(R.string.secN_medicalPersonnale_name);
                    txtViewEarlyBirthDate.setText("");
                }
            }
        });
        checkBoxOralHealth.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                {
                    txtViewOralHealthDate.setVisibility(View.VISIBLE);
                    txtViewOralHealthPerson.setVisibility(View.VISIBLE);
                    txtViewOralHealthPerson.setText(medicalPersonName);
                    txtViewOralHealthDate.setText(getResources().getString(R.string.secN_Date));
                }else{
                    txtViewOralHealthDate.setVisibility(View.GONE);
                    txtViewOralHealthPerson.setVisibility(View.GONE);
                    txtViewOralHealthPerson.setText(R.string.secN_medicalPersonnale_name);
                    txtViewOralHealthDate.setText("");
                }
            }
        });
    }

    private void ViewGoneDateText()
    {
        txtViewAntenatalCareDate.setVisibility(View.GONE);
        txtViewAntenatalCarePerson.setVisibility(View.GONE);
        txtViewEarlyClinicDate.setVisibility(View.GONE);
        txtViewEarlyClinicPerson.setVisibility(View.GONE);
        txtViewSolveProblemDate.setVisibility(View.GONE);
        txtViewSolveProblemPerson.setVisibility(View.GONE);
        txtViewNutritionDate.setVisibility(View.GONE);
        txtViewNutritionPerson.setVisibility(View.GONE);
        txtViewMaternityDate.setVisibility(View.GONE);
        txtViewMaternityPerson.setVisibility(View.GONE);
        txtViewExerciseDate.setVisibility(View.GONE);
        txtViewExercisePerson.setVisibility(View.GONE);
        txtViewFamilyPlannerDate.setVisibility(View.GONE);
        txtViewFamilyPlannerPerson.setVisibility(View.GONE);
        txtViewChildBirthDate.setVisibility(View.GONE);
        txtViewChildBirthPerson.setVisibility(View.GONE);
        txtViewSafeBirthDate.setVisibility(View.GONE);
        txtViewSafeBirthPerson.setVisibility(View.GONE);
        txtViewEarlyBirthDate.setVisibility(View.GONE);
        txtViewEarlyBirthPerson.setVisibility(View.GONE);
        txtViewOralHealthDate.setVisibility(View.GONE);
        txtViewOralHealthPerson.setVisibility(View.GONE);
    }

    private void DateTextView()
    {
        txtViewAntenatalCareDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR); // current year
                int mMonth = c.get(Calendar.MONTH); // current month
                int mDay = c.get(Calendar.DAY_OF_MONTH); // current day
                datePickerDialog = new DatePickerDialog(AntenatalActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                txtViewAntenatalCareDate.setText(dayOfMonth + "/" +(month + 1) + "/" + year);
                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });

        txtViewEarlyClinicDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR); // current year
                int mMonth = c.get(Calendar.MONTH); // current month
                int mDay = c.get(Calendar.DAY_OF_MONTH); // current day
                datePickerDialog = new DatePickerDialog(AntenatalActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                txtViewEarlyClinicDate.setText(dayOfMonth + "/" +(month + 1) + "/" + year);
                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });

        txtViewSolveProblemDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR); // current year
                int mMonth = c.get(Calendar.MONTH); // current month
                int mDay = c.get(Calendar.DAY_OF_MONTH); // current day
                datePickerDialog = new DatePickerDialog(AntenatalActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                txtViewSolveProblemDate.setText(dayOfMonth + "/" +(month + 1) + "/" + year);
                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });

        txtViewNutritionDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR); // current year
                int mMonth = c.get(Calendar.MONTH); // current month
                int mDay = c.get(Calendar.DAY_OF_MONTH); // current day
                datePickerDialog = new DatePickerDialog(AntenatalActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                txtViewNutritionDate.setText(dayOfMonth + "/" +(month + 1) + "/" + year);
                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });

        txtViewMaternityDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR); // current year
                int mMonth = c.get(Calendar.MONTH); // current month
                int mDay = c.get(Calendar.DAY_OF_MONTH); // current day
                datePickerDialog = new DatePickerDialog(AntenatalActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                txtViewMaternityDate.setText(dayOfMonth + "/" +(month + 1) + "/" + year);
                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });

        txtViewExerciseDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR); // current year
                int mMonth = c.get(Calendar.MONTH); // current month
                int mDay = c.get(Calendar.DAY_OF_MONTH); // current day
                datePickerDialog = new DatePickerDialog(AntenatalActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                txtViewExerciseDate.setText(dayOfMonth + "/" +(month + 1) + "/" + year);
                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });

        txtViewFamilyPlannerDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR); // current year
                int mMonth = c.get(Calendar.MONTH); // current month
                int mDay = c.get(Calendar.DAY_OF_MONTH); // current day
                datePickerDialog = new DatePickerDialog(AntenatalActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                txtViewFamilyPlannerDate.setText(dayOfMonth + "/" +(month + 1) + "/" + year);
                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });

        txtViewChildBirthDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR); // current year
                int mMonth = c.get(Calendar.MONTH); // current month
                int mDay = c.get(Calendar.DAY_OF_MONTH); // current day
                datePickerDialog = new DatePickerDialog(AntenatalActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                txtViewChildBirthDate.setText(dayOfMonth + "/" +(month + 1) + "/" + year);
                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });

        txtViewSafeBirthDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR); // current year
                int mMonth = c.get(Calendar.MONTH); // current month
                int mDay = c.get(Calendar.DAY_OF_MONTH); // current day
                datePickerDialog = new DatePickerDialog(AntenatalActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                txtViewSafeBirthDate.setText(dayOfMonth + "/" +(month + 1) + "/" + year);
                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });

        txtViewEarlyBirthDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR); // current year
                int mMonth = c.get(Calendar.MONTH); // current month
                int mDay = c.get(Calendar.DAY_OF_MONTH); // current day
                datePickerDialog = new DatePickerDialog(AntenatalActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                txtViewEarlyBirthDate.setText(dayOfMonth + "/" +(month + 1) + "/" + year);
                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });

        txtViewOralHealthDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR); // current year
                int mMonth = c.get(Calendar.MONTH); // current month
                int mDay = c.get(Calendar.DAY_OF_MONTH); // current day
                datePickerDialog = new DatePickerDialog(AntenatalActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                txtViewOralHealthDate.setText(dayOfMonth + "/" +(month + 1) + "/" + year);
                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });
    }

    private void CancelAlarm()
    {
        AlarmManager alarmManager = (AlarmManager)AntenatalActivity.this.getSystemService(Context.ALARM_SERVICE );
        Intent intent = new Intent(AntenatalActivity.this, NotifyService.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(AntenatalActivity.this, 9000, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        alarmManager.cancel(pendingIntent);
        pendingIntent.cancel();

        Intent intentReminder = new Intent(AntenatalActivity.this, ReminderService.class);
        PendingIntent pendingIntentReminder = PendingIntent.getBroadcast(AntenatalActivity.this, 101, intentReminder, PendingIntent.FLAG_UPDATE_CURRENT);
        alarmManager.cancel(pendingIntentReminder);
        pendingIntentReminder.cancel();

        Intent intentAlert = new Intent(AntenatalActivity.this, AlertService.class);
        PendingIntent pendingIntentAlert = PendingIntent.getBroadcast(AntenatalActivity.this, 1000, intentAlert, 0);
        alarmManager.cancel(pendingIntentAlert);
        pendingIntentAlert.cancel();

        Log.i("TestingAlarmCancel", "Alarm Cancel");
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.action_logout:
                AlertDialog.Builder builder = new AlertDialog.Builder(AntenatalActivity.this);
                builder.setTitle("Logout");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if(SaveSharedPreference.getUser(AntenatalActivity.this).equals("Mommy"))
                        {
                            CancelAlarm();
                        }
                        SaveSharedPreference.clearUser(AntenatalActivity.this);
                        Intent intent = new Intent(AntenatalActivity.this, MainActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
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
