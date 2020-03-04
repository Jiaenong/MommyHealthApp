package com.example.mommyhealthapp.Nurse;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.LinearLayoutCompat;

import android.app.DatePickerDialog;
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

import com.example.mommyhealthapp.Class.BreastFeedingTutorial;
import com.example.mommyhealthapp.Class.MedicalPersonnel;
import com.example.mommyhealthapp.R;
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

public class BreastFeedingActivity extends AppCompatActivity {
    private CheckBox checkBoxAdvantage, checkBoxBFExclusive, checkBoxEarlyBF, checkBoxRoomingIn, checkBoxBFMethod, checkBoxBFBabyNeeds, checkBoxBreastMilk, checkBoxBFProblem,
            checkBoxBFWorkMother;
    private TextView txtViewAdvantageDate, txtViewAdvantagePerson, txtViewBFExclusiveDate, txtViewBFExclusivePerson, txtViewEarlyBFDate, txtViewEarlyBFPerson, txtViewRoomingInDate,
            txtViewRoomingInPerson, txtViewBFMethodDate, txtViewBFMethodPerson, txtViewBFBabyNeedsDate, txtViewBFBabyNeedsPerson, txtViewBreastMilkDate, txtViewBreastMilkPerson,
            txtViewBFProblemDate, txtViewBFProblemPerson, txtViewBFWorkMotherDate, txtViewBFWorkMotherPerson;
    private Button btnBFSave, btnBFCancel;
    private RelativeLayout relativeLayoutBF;
    private LinearLayoutCompat layoutBF;
    private ProgressBar progressBarBF;
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
        setContentView(R.layout.activity_breast_feeding);

        checkBoxAdvantage = (CheckBox)findViewById(R.id.checkBoxAdvantage);
        checkBoxBFExclusive = (CheckBox)findViewById(R.id.checkBoxBFExclusive);
        checkBoxEarlyBF = (CheckBox)findViewById(R.id.checkBoxEarlyBF);
        checkBoxRoomingIn = (CheckBox)findViewById(R.id.checkBoxRoomingIn);
        checkBoxBFMethod = (CheckBox)findViewById(R.id.checkBoxBFMethod);
        checkBoxBFBabyNeeds = (CheckBox)findViewById(R.id.checkBoxBFBabyNeeds);
        checkBoxBreastMilk = (CheckBox)findViewById(R.id.checkBoxBreastMilk);
        checkBoxBFProblem = (CheckBox)findViewById(R.id.checkBoxBFProblem);
        checkBoxBFWorkMother = (CheckBox)findViewById(R.id.checkBoxBFWorkMother);

        txtViewAdvantageDate = (TextView)findViewById(R.id.txtViewAdvantageDate);
        txtViewAdvantagePerson = (TextView)findViewById(R.id.txtViewAdvantagePerson);
        txtViewBFExclusiveDate = (TextView)findViewById(R.id.txtViewBFExclusiveDate);
        txtViewBFExclusivePerson = (TextView)findViewById(R.id.txtViewBFExclusivePerson);
        txtViewEarlyBFDate = (TextView)findViewById(R.id.txtViewEarlyBFDate);
        txtViewEarlyBFPerson = (TextView)findViewById(R.id.txtViewEarlyBFPerson);
        txtViewRoomingInDate = (TextView)findViewById(R.id.txtViewRoomingInDate);
        txtViewRoomingInPerson = (TextView)findViewById(R.id.txtViewRoomingInPerson);
        txtViewBFMethodDate = (TextView)findViewById(R.id.txtViewBFMethodDate);
        txtViewBFMethodPerson = (TextView)findViewById(R.id.txtViewBFMethodPerson);
        txtViewBFBabyNeedsDate = (TextView)findViewById(R.id.txtViewBFBabyNeedsDate);
        txtViewBFBabyNeedsPerson = (TextView)findViewById(R.id.txtViewBFBabyNeedsPerson);
        txtViewBreastMilkDate = (TextView)findViewById(R.id.txtViewBreastMilkDate);
        txtViewBreastMilkPerson = (TextView)findViewById(R.id.txtViewBreastMilkPerson);
        txtViewBFProblemDate = (TextView)findViewById(R.id.txtViewBFProblemDate);
        txtViewBFProblemPerson = (TextView)findViewById(R.id.txtViewBFProblemPerson);
        txtViewBFWorkMotherDate = (TextView)findViewById(R.id.txtViewBFWorkMotherDate);
        txtViewBFWorkMotherPerson = (TextView)findViewById(R.id.txtViewBFWorkMotherPerson);

        btnBFSave = (Button)findViewById(R.id.btnBFSave);
        btnBFCancel = (Button)findViewById(R.id.btnBFCancel);

        relativeLayoutBF = (RelativeLayout)findViewById(R.id.relativeLayoutBF);
        layoutBF = (LinearLayoutCompat)findViewById(R.id.layoutBF);
        progressBarBF = (ProgressBar)findViewById(R.id.progressBarBF);

        btnBFCancel.setVisibility(View.GONE);
        progressBarBF.setVisibility(View.VISIBLE);
        layoutBF.setVisibility(View.GONE);

        mFirebaseFirestore = FirebaseFirestore.getInstance();
        if(!SaveSharedPreference.getUser(BreastFeedingActivity.this).equals("Mommy"))
        {
            mDocumentReference = mFirebaseFirestore.collection("MedicalPersonnel").document(SaveSharedPreference.getID(BreastFeedingActivity.this));
            mDocumentReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    MedicalPersonnel md = documentSnapshot.toObject(MedicalPersonnel.class);
                    medicalPersonName = md.getName();
                }
            });
        }
        mCollectionReference = mFirebaseFirestore.collection("MommyHealthInfo");
        mCollectionReference.whereEqualTo("mommyId", SaveSharedPreference.getMummyId(BreastFeedingActivity.this)).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for(QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots)
                {
                    healthInfoId = documentSnapshot.getId();
                }
                nCollectionReference = mFirebaseFirestore.collection("MommyHealthInfo").document(healthInfoId).collection("BreastFeedingTutorial");
                nCollectionReference.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        if(queryDocumentSnapshots.isEmpty())
                        {
                            isEmpty = true;
                            progressBarBF.setVisibility(View.GONE);
                            layoutBF.setVisibility(View.VISIBLE);
                        }else{
                            isEmpty = false;
                            DisableField();
                            if(SaveSharedPreference.getUser(BreastFeedingActivity.this).equals("Mommy"))
                            {
                                btnBFCancel.setVisibility(View.GONE);
                                btnBFSave.setVisibility(View.GONE);
                            }
                            for(QueryDocumentSnapshot documentSnapshots : queryDocumentSnapshots)
                            {
                                key = documentSnapshots.getId();
                                BreastFeedingTutorial bft = documentSnapshots.toObject(BreastFeedingTutorial.class);
                                SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                                if(bft.getAdvantageBreastMilkStatus().equals("Yes"))
                                {
                                    checkBoxAdvantage.setChecked(true);
                                    txtViewAdvantagePerson.setText(bft.getAdvantageBreastMilkPerson());
                                    txtViewAdvantageDate.setText(dateFormat.format(bft.getAdvantageBreastMilkDate()));
                                }
                                if(bft.getBfExclusivelyStatus().equals("Yes"))
                                {
                                    checkBoxBFExclusive.setChecked(true);
                                    txtViewBFExclusivePerson.setText(bft.getBfExclusivelyPerson());
                                    txtViewBFExclusiveDate.setText(dateFormat.format(bft.getBfExclusivelyDate()));
                                }
                                if(bft.getEarlyBFStatus().equals("Yes"))
                                {
                                    checkBoxEarlyBF.setChecked(true);
                                    txtViewEarlyBFPerson.setText(bft.getEarlyBFPerson());
                                    txtViewEarlyBFDate.setText(dateFormat.format(bft.getEarlyBFDate()));
                                }
                                if(bft.getRoomingInStatus().equals("Yes"))
                                {
                                    checkBoxRoomingIn.setChecked(true);
                                    txtViewRoomingInPerson.setText(bft.getRoomingInPerson());
                                    txtViewRoomingInDate.setText(dateFormat.format(bft.getRoomingInDate()));
                                }
                                if(bft.getBfMethodStatus().equals("Yes"))
                                {
                                    checkBoxBFMethod.setChecked(true);
                                    txtViewBFMethodPerson.setText(bft.getBfMethodPerson());
                                    txtViewBFMethodDate.setText(dateFormat.format(bft.getBfMethodDate()));
                                }
                                if(bft.getBfBabyNeedsStatus().equals("Yes"))
                                {
                                    checkBoxBFBabyNeeds.setChecked(true);
                                    txtViewBFBabyNeedsPerson.setText(bft.getBfBabyNeedsPerson());
                                    txtViewBFBabyNeedsDate.setText(dateFormat.format(bft.getBfBabyNeedsDate()));
                                }
                                if(bft.getBreastMilkProductionStatus().equals("Yes"))
                                {
                                    checkBoxBreastMilk.setChecked(true);
                                    txtViewBreastMilkPerson.setText(bft.getBreastMilkProductionPerson());
                                    txtViewBreastMilkDate.setText(dateFormat.format(bft.getBreastMilkProductionDate()));
                                }
                                if(bft.getBfProblemStatus().equals("Yes"))
                                {
                                    checkBoxBFProblem.setChecked(true);
                                    txtViewBFProblemPerson.setText(bft.getBfProblemPerson());
                                    txtViewBFProblemDate.setText(dateFormat.format(bft.getBfProblemDate()));
                                }
                                if(bft.getBfWorkMotherStatus().equals("Yes"))
                                {
                                    checkBoxBFWorkMother.setChecked(true);
                                    txtViewBFWorkMotherPerson.setText(bft.getBfWorkMotherPerson());
                                    txtViewBFWorkMotherDate.setText(dateFormat.format(bft.getBfWorkMotherDate()));
                                }
                            }
                            progressBarBF.setVisibility(View.GONE);
                            layoutBF.setVisibility(View.VISIBLE);
                            btnBFSave.setText("Update");
                        }
                    }
                });
            }
        });

        ViewGoneDateText();
        DateTextView();
        CheckBoxIsCheck();

        btnBFSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isEmpty == true)
                {
                    List<TextView> dateText = new ArrayList<>();
                    CollectionReference mCollectionReference = mFirebaseFirestore.collection("MommyHealthInfo").document(healthInfoId).collection("BreastFeedingTutorial");
                    String advantageBreastMilkStatus = "", bfExclusivelyStatus = "", earlyBFStatus = "", roomingInStatus = "", bfMethodStatus = "", bfBabyNeedsStatus = "",
                            breastMilkProductionStatus = "", bfProblemStatus = "", bfWorkMotherStatus = "";
                    String advantageBreastMilkPerson = "", bfExclusivelyPerson = "", earlyBFPerson = "", roomingInPerson = "", bfMethodPerson = "", bfBabyNeedsPerson = "",
                            breastMilkProductionPerson = "", bfProblemPerson = "", bfWorkMotherPerson = "";
                    Date advantageBreastMilkDate = null, bfExclusivelyDate = null, earlyBFDate = null, roomingInDate = null, bfMethodDate = null, bfBabyNeedsDate = null,
                            breastMilkProductionDate = null, bfProblemDate = null, bfWorkMotherDate = null;

                    if(checkBoxAdvantage.isChecked())
                    {
                        advantageBreastMilkStatus = "Yes";
                        advantageBreastMilkPerson = txtViewBreastMilkPerson.getText().toString();
                        try {
                            advantageBreastMilkDate = new SimpleDateFormat("dd/MM/yyyy").parse(txtViewAdvantageDate.getText().toString());
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        dateText.add(txtViewAdvantageDate);
                    }else{
                        advantageBreastMilkStatus = "No";
                        advantageBreastMilkPerson = "";
                        advantageBreastMilkDate = null;
                    }

                    if(checkBoxBFExclusive.isChecked())
                    {
                        bfExclusivelyStatus = "Yes";
                        bfExclusivelyPerson = txtViewBFExclusivePerson.getText().toString();
                        try {
                            bfExclusivelyDate = new SimpleDateFormat("dd/MM/yyyy").parse(txtViewBFExclusiveDate.getText().toString());
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        dateText.add(txtViewBFExclusiveDate);
                    }else{
                        bfExclusivelyStatus = "No";
                        bfExclusivelyPerson = "";
                        bfExclusivelyDate = null;
                    }

                    if(checkBoxEarlyBF.isChecked())
                    {
                        earlyBFStatus = "Yes";
                        earlyBFPerson = txtViewEarlyBFPerson.getText().toString();
                        try {
                            earlyBFDate = new SimpleDateFormat("dd/MM/yyyy").parse(txtViewEarlyBFDate.getText().toString());
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        dateText.add(txtViewEarlyBFDate);
                    }else{
                        earlyBFStatus = "No";
                        earlyBFPerson = "";
                        earlyBFDate = null;
                    }

                    if(checkBoxRoomingIn.isChecked())
                    {
                        roomingInStatus = "Yes";
                        roomingInPerson = txtViewRoomingInPerson.getText().toString();
                        try {
                            roomingInDate = new SimpleDateFormat("dd/MM/yyyy").parse(txtViewRoomingInDate.getText().toString());
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        dateText.add(txtViewRoomingInDate);
                    }else{
                        roomingInStatus = "No";
                        roomingInPerson = "";
                        roomingInDate = null;
                    }

                    if(checkBoxBFMethod.isChecked())
                    {
                        bfMethodStatus = "Yes";
                        bfMethodPerson = txtViewBFMethodPerson.getText().toString();
                        try {
                            bfMethodDate = new SimpleDateFormat("dd/MM/yyyy").parse(txtViewBFMethodDate.getText().toString());
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        dateText.add(txtViewBFMethodDate);
                    }else{
                        bfMethodStatus = "No";
                        bfMethodPerson = "";
                        bfMethodDate = null;
                    }

                    if(checkBoxBFBabyNeeds.isChecked())
                    {
                        bfBabyNeedsStatus = "Yes";
                        bfBabyNeedsPerson = txtViewBFBabyNeedsPerson.getText().toString();
                        try {
                            bfBabyNeedsDate = new SimpleDateFormat("dd/MM/yyyy").parse(txtViewBFBabyNeedsDate.getText().toString());
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        dateText.add(txtViewBFBabyNeedsDate);
                    }else{
                        bfBabyNeedsStatus = "No";
                        bfBabyNeedsPerson = "";
                        bfBabyNeedsDate = null;
                    }

                    if(checkBoxBreastMilk.isChecked())
                    {
                        breastMilkProductionStatus = "Yes";
                        breastMilkProductionPerson = txtViewBreastMilkPerson.getText().toString();
                        try {
                            breastMilkProductionDate = new SimpleDateFormat("dd/MM/yyyy").parse(txtViewBreastMilkDate.getText().toString());
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        dateText.add(txtViewBreastMilkDate);
                    }else{
                        breastMilkProductionStatus = "No";
                        breastMilkProductionPerson = "";
                        breastMilkProductionDate = null;
                    }

                    if(checkBoxBFProblem.isChecked())
                    {
                        bfProblemStatus = "Yes";
                        bfProblemPerson = txtViewBFProblemPerson.getText().toString();
                        try {
                            bfProblemDate = new SimpleDateFormat("dd/MM/yyyy").parse(txtViewBFProblemDate.getText().toString());
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        dateText.add(txtViewBFProblemDate);
                    }else{
                        bfProblemStatus = "No";
                        bfProblemPerson = "";
                        bfProblemDate = null;
                    }

                    if(checkBoxBFWorkMother.isChecked())
                    {
                        bfWorkMotherStatus = "Yes";
                        bfWorkMotherPerson = txtViewBFWorkMotherPerson.getText().toString();
                        try {
                            bfWorkMotherDate = new SimpleDateFormat("dd/MM/yyyy").parse(txtViewBFWorkMotherDate.getText().toString());
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        dateText.add(txtViewBFWorkMotherDate);
                    }else{
                        bfWorkMotherStatus = "No";
                        bfWorkMotherPerson = "";
                        bfWorkMotherDate = null;
                    }
                    BreastFeedingTutorial bft = new BreastFeedingTutorial(advantageBreastMilkStatus, advantageBreastMilkDate, advantageBreastMilkPerson, bfExclusivelyStatus,
                            bfExclusivelyDate, bfExclusivelyPerson, earlyBFStatus, earlyBFDate, earlyBFPerson, roomingInStatus,
                            roomingInDate, roomingInPerson, bfMethodStatus, bfMethodDate, bfMethodPerson, bfBabyNeedsStatus,
                            bfBabyNeedsDate, bfBabyNeedsPerson, breastMilkProductionStatus, breastMilkProductionDate,
                            breastMilkProductionPerson, bfProblemStatus, bfProblemDate, bfProblemPerson, bfWorkMotherStatus,
                            bfWorkMotherDate, bfWorkMotherPerson);
                    boolean checkDate = CheckDateSelected(dateText);
                    if(checkDate == true)
                    {
                        mCollectionReference.add(bft).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {
                                AlertDialog.Builder builder = new AlertDialog.Builder(BreastFeedingActivity.this);
                                builder.setTitle("Save Successfully");
                                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        Intent intent = new Intent(BreastFeedingActivity.this, SectionNActivity.class);
                                        startActivity(intent);
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
                        btnBFCancel.setVisibility(View.VISIBLE);
                    }else if(check == 2)
                    {
                        List<TextView> dateText = new ArrayList<>();
                        DocumentReference mDocumentReference = mFirebaseFirestore.collection("MommyHealthInfo").document(healthInfoId).collection("BreastFeedingTutorial").document(key);
                        String advantageBreastMilkStatus = "", bfExclusivelyStatus = "", earlyBFStatus = "", roomingInStatus = "", bfMethodStatus = "", bfBabyNeedsStatus = "",
                                breastMilkProductionStatus = "", bfProblemStatus = "", bfWorkMotherStatus = "";
                        String advantageBreastMilkPerson = "", bfExclusivelyPerson = "", earlyBFPerson = "", roomingInPerson = "", bfMethodPerson = "", bfBabyNeedsPerson = "",
                                breastMilkProductionPerson = "", bfProblemPerson = "", bfWorkMotherPerson = "";
                        Date advantageBreastMilkDate = null, bfExclusivelyDate = null, earlyBFDate = null, roomingInDate = null, bfMethodDate = null, bfBabyNeedsDate = null,
                                breastMilkProductionDate = null, bfProblemDate = null, bfWorkMotherDate = null;

                        if(checkBoxAdvantage.isChecked())
                        {
                            advantageBreastMilkStatus = "Yes";
                            advantageBreastMilkPerson = txtViewBreastMilkPerson.getText().toString();
                            try {
                                advantageBreastMilkDate = new SimpleDateFormat("dd/MM/yyyy").parse(txtViewAdvantageDate.getText().toString());
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                            dateText.add(txtViewAdvantageDate);
                        }else{
                            advantageBreastMilkStatus = "No";
                            advantageBreastMilkPerson = "";
                            advantageBreastMilkDate = null;
                        }

                        if(checkBoxBFExclusive.isChecked())
                        {
                            bfExclusivelyStatus = "Yes";
                            bfExclusivelyPerson = txtViewBFExclusivePerson.getText().toString();
                            try {
                                bfExclusivelyDate = new SimpleDateFormat("dd/MM/yyyy").parse(txtViewBFExclusiveDate.getText().toString());
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                            dateText.add(txtViewBFExclusiveDate);
                        }else{
                            bfExclusivelyStatus = "No";
                            bfExclusivelyPerson = "";
                            bfExclusivelyDate = null;
                        }

                        if(checkBoxEarlyBF.isChecked())
                        {
                            earlyBFStatus = "Yes";
                            earlyBFPerson = txtViewEarlyBFPerson.getText().toString();
                            try {
                                earlyBFDate = new SimpleDateFormat("dd/MM/yyyy").parse(txtViewEarlyBFDate.getText().toString());
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                            dateText.add(txtViewEarlyBFDate);
                        }else{
                            earlyBFStatus = "No";
                            earlyBFPerson = "";
                            earlyBFDate = null;
                        }

                        if(checkBoxRoomingIn.isChecked())
                        {
                            roomingInStatus = "Yes";
                            roomingInPerson = txtViewRoomingInPerson.getText().toString();
                            try {
                                roomingInDate = new SimpleDateFormat("dd/MM/yyyy").parse(txtViewRoomingInDate.getText().toString());
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                            dateText.add(txtViewRoomingInDate);
                        }else{
                            roomingInStatus = "No";
                            roomingInPerson = "";
                            roomingInDate = null;
                        }

                        if(checkBoxBFMethod.isChecked())
                        {
                            bfMethodStatus = "Yes";
                            bfMethodPerson = txtViewBFMethodPerson.getText().toString();
                            try {
                                bfMethodDate = new SimpleDateFormat("dd/MM/yyyy").parse(txtViewBFMethodDate.getText().toString());
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                            dateText.add(txtViewBFMethodDate);
                        }else{
                            bfMethodStatus = "No";
                            bfMethodPerson = "";
                            bfMethodDate = null;
                        }

                        if(checkBoxBFBabyNeeds.isChecked())
                        {
                            bfBabyNeedsStatus = "Yes";
                            bfBabyNeedsPerson = txtViewBFBabyNeedsPerson.getText().toString();
                            try {
                                bfBabyNeedsDate = new SimpleDateFormat("dd/MM/yyyy").parse(txtViewBFBabyNeedsDate.getText().toString());
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                            dateText.add(txtViewBFBabyNeedsDate);
                        }else{
                            bfBabyNeedsStatus = "No";
                            bfBabyNeedsPerson = "";
                            bfBabyNeedsDate = null;
                        }

                        if(checkBoxBreastMilk.isChecked())
                        {
                            breastMilkProductionStatus = "Yes";
                            breastMilkProductionPerson = txtViewBreastMilkPerson.getText().toString();
                            try {
                                breastMilkProductionDate = new SimpleDateFormat("dd/MM/yyyy").parse(txtViewBreastMilkDate.getText().toString());
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                            dateText.add(txtViewBreastMilkDate);
                        }else{
                            breastMilkProductionStatus = "No";
                            breastMilkProductionPerson = "";
                            breastMilkProductionDate = null;
                        }

                        if(checkBoxBFProblem.isChecked())
                        {
                            bfProblemStatus = "Yes";
                            bfProblemPerson = txtViewBFProblemPerson.getText().toString();
                            try {
                                bfProblemDate = new SimpleDateFormat("dd/MM/yyyy").parse(txtViewBFProblemDate.getText().toString());
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                            dateText.add(txtViewBFProblemDate);
                        }else{
                            bfProblemStatus = "No";
                            bfProblemPerson = "";
                            bfProblemDate = null;
                        }

                        if(checkBoxBFWorkMother.isChecked())
                        {
                            bfWorkMotherStatus = "Yes";
                            bfWorkMotherPerson = txtViewBFWorkMotherPerson.getText().toString();
                            try {
                                bfWorkMotherDate = new SimpleDateFormat("dd/MM/yyyy").parse(txtViewBFWorkMotherDate.getText().toString());
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                            dateText.add(txtViewBFWorkMotherDate);
                        }else{
                            bfWorkMotherStatus = "No";
                            bfWorkMotherPerson = "";
                            bfWorkMotherDate = null;
                        }
                        boolean checkDate = CheckDateSelected(dateText);
                        if(checkDate == true)
                        {
                            mDocumentReference.update("advantageBreastMilkStatus", advantageBreastMilkStatus);
                            mDocumentReference.update("advantageBreastMilkDate", advantageBreastMilkDate);
                            mDocumentReference.update("advantageBreastMilkPerson", advantageBreastMilkPerson);
                            mDocumentReference.update("bfExclusivelyStatus", bfExclusivelyStatus);
                            mDocumentReference.update("bfExclusivelyDate", bfExclusivelyDate);
                            mDocumentReference.update("bfExclusivelyPerson", bfExclusivelyPerson);
                            mDocumentReference.update("earlyBFStatus", earlyBFStatus);
                            mDocumentReference.update("earlyBFDate", earlyBFDate);
                            mDocumentReference.update("earlyBFPerson", earlyBFPerson);
                            mDocumentReference.update("roomingInStatus", roomingInStatus);
                            mDocumentReference.update("roomingInDate", roomingInDate);
                            mDocumentReference.update("roomingInPerson", roomingInPerson);
                            mDocumentReference.update("bfMethodStatus", bfMethodStatus);
                            mDocumentReference.update("bfMethodDate", bfMethodDate);
                            mDocumentReference.update("bfMethodPerson", bfMethodPerson);
                            mDocumentReference.update("bfBabyNeedsStatus", bfBabyNeedsStatus);
                            mDocumentReference.update("bfBabyNeedsDate", bfBabyNeedsDate);
                            mDocumentReference.update("bfBabyNeedsPerson", bfBabyNeedsPerson);
                            mDocumentReference.update("breastMilkProductionStatus", breastMilkProductionStatus);
                            mDocumentReference.update("breastMilkProductionDate", breastMilkProductionDate);
                            mDocumentReference.update("breastMilkProductionPerson", breastMilkProductionPerson);
                            mDocumentReference.update("bfProblemStatus", bfProblemStatus);
                            mDocumentReference.update("bfProblemDate", bfProblemDate);
                            mDocumentReference.update("bfProblemPerson", bfProblemPerson);
                            mDocumentReference.update("bfWorkMotherStatus", bfWorkMotherStatus);
                            mDocumentReference.update("bfWorkMotherDate", bfWorkMotherDate);
                            mDocumentReference.update("bfWorkMotherPerson", bfWorkMotherPerson);
                            Snackbar snackbar = Snackbar.make(relativeLayoutBF, "Updated Successfully!", Snackbar.LENGTH_LONG);
                            snackbar.show();
                            DisableField();
                            btnBFCancel.setVisibility(View.GONE);
                            check = 0;
                        }else{
                            check = 1;
                        }
                    }
                }
            }
        });

        btnBFCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DisableField();
                btnBFCancel.setVisibility(View.GONE);
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
        for(int i = 0; i<layoutBF.getChildCount(); i++)
        {
            View view = layoutBF.getChildAt(i);
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
        for(int i = 0; i<layoutBF.getChildCount(); i++)
        {
            View view = layoutBF.getChildAt(i);
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
        checkBoxAdvantage.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                {
                    txtViewAdvantageDate.setVisibility(View.VISIBLE);
                    txtViewAdvantagePerson.setVisibility(View.VISIBLE);
                    txtViewAdvantagePerson.setText(medicalPersonName);
                }else{
                    txtViewAdvantageDate.setVisibility(View.GONE);
                    txtViewAdvantagePerson.setVisibility(View.GONE);
                }
            }
        });
        checkBoxBFExclusive.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                {
                    txtViewBFExclusiveDate.setVisibility(View.VISIBLE);
                    txtViewBFExclusivePerson.setVisibility(View.VISIBLE);
                    txtViewBFExclusivePerson.setText(medicalPersonName);
                }else{
                    txtViewBFExclusiveDate.setVisibility(View.GONE);
                    txtViewBFExclusivePerson.setVisibility(View.GONE);
                }
            }
        });
        checkBoxEarlyBF.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                {
                    txtViewEarlyBFDate.setVisibility(View.VISIBLE);
                    txtViewEarlyBFPerson.setVisibility(View.VISIBLE);
                    txtViewEarlyBFPerson.setText(medicalPersonName);
                }else{
                    txtViewEarlyBFDate.setVisibility(View.GONE);
                    txtViewEarlyBFPerson.setVisibility(View.GONE);
                }
            }
        });
        checkBoxRoomingIn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                {
                    txtViewRoomingInDate.setVisibility(View.VISIBLE);
                    txtViewRoomingInPerson.setVisibility(View.VISIBLE);
                    txtViewRoomingInPerson.setText(medicalPersonName);
                }else{
                    txtViewRoomingInDate.setVisibility(View.GONE);
                    txtViewRoomingInPerson.setVisibility(View.GONE);
                }
            }
        });
        checkBoxBFMethod.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                {
                    txtViewBFMethodDate.setVisibility(View.VISIBLE);
                    txtViewBFMethodPerson.setVisibility(View.VISIBLE);
                    txtViewBFMethodPerson.setText(medicalPersonName);
                }else{
                    txtViewBFMethodDate.setVisibility(View.GONE);
                    txtViewBFMethodPerson.setVisibility(View.GONE);
                }
            }
        });
        checkBoxBFBabyNeeds.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                {
                    txtViewBFBabyNeedsDate.setVisibility(View.VISIBLE);
                    txtViewBFBabyNeedsPerson.setVisibility(View.VISIBLE);
                    txtViewBFBabyNeedsPerson.setText(medicalPersonName);
                }else{
                    txtViewBFBabyNeedsDate.setVisibility(View.GONE);
                    txtViewBFBabyNeedsPerson.setVisibility(View.GONE);
                }
            }
        });
        checkBoxBreastMilk.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                {
                    txtViewBreastMilkDate.setVisibility(View.VISIBLE);
                    txtViewBreastMilkPerson.setVisibility(View.VISIBLE);
                    txtViewBreastMilkPerson.setText(medicalPersonName);
                }else{
                    txtViewBreastMilkDate.setVisibility(View.GONE);
                    txtViewBreastMilkPerson.setVisibility(View.GONE);
                }
            }
        });
        checkBoxBFProblem.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                {
                    txtViewBFProblemDate.setVisibility(View.VISIBLE);
                    txtViewBFProblemPerson.setVisibility(View.VISIBLE);
                    txtViewBFProblemPerson.setText(medicalPersonName);
                }else{
                    txtViewBFProblemDate.setVisibility(View.GONE);
                    txtViewBFProblemPerson.setVisibility(View.GONE);
                }
            }
        });
        checkBoxBFWorkMother.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                {
                    txtViewBFWorkMotherDate.setVisibility(View.VISIBLE);
                    txtViewBFWorkMotherPerson.setVisibility(View.VISIBLE);
                    txtViewBFWorkMotherPerson.setText(medicalPersonName);
                }else{
                    txtViewBFWorkMotherDate.setVisibility(View.GONE);
                    txtViewBFWorkMotherPerson.setVisibility(View.GONE);
                }
            }
        });
    }

    private void ViewGoneDateText()
    {
        txtViewAdvantageDate.setVisibility(View.GONE);
        txtViewAdvantagePerson.setVisibility(View.GONE);
        txtViewBFExclusiveDate.setVisibility(View.GONE);
        txtViewBFExclusivePerson.setVisibility(View.GONE);
        txtViewEarlyBFDate.setVisibility(View.GONE);
        txtViewEarlyBFPerson.setVisibility(View.GONE);
        txtViewRoomingInDate.setVisibility(View.GONE);
        txtViewRoomingInPerson.setVisibility(View.GONE);
        txtViewBFMethodDate.setVisibility(View.GONE);
        txtViewBFMethodPerson.setVisibility(View.GONE);
        txtViewBFBabyNeedsDate.setVisibility(View.GONE);
        txtViewBFBabyNeedsPerson.setVisibility(View.GONE);
        txtViewBreastMilkDate.setVisibility(View.GONE);
        txtViewBreastMilkPerson.setVisibility(View.GONE);
        txtViewBFProblemDate.setVisibility(View.GONE);
        txtViewBFProblemPerson.setVisibility(View.GONE);
        txtViewBFWorkMotherDate.setVisibility(View.GONE);
        txtViewBFWorkMotherPerson.setVisibility(View.GONE);
    }

    private void DateTextView()
    {
        txtViewAdvantageDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR); // current year
                int mMonth = c.get(Calendar.MONTH); // current month
                int mDay = c.get(Calendar.DAY_OF_MONTH); // current day
                datePickerDialog = new DatePickerDialog(BreastFeedingActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                txtViewAdvantageDate.setText(dayOfMonth + "/" +(month + 1) + "/" + year);
                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });
        txtViewBFExclusiveDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR); // current year
                int mMonth = c.get(Calendar.MONTH); // current month
                int mDay = c.get(Calendar.DAY_OF_MONTH); // current day
                datePickerDialog = new DatePickerDialog(BreastFeedingActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                txtViewBFExclusiveDate.setText(dayOfMonth + "/" +(month + 1) + "/" + year);
                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });
        txtViewEarlyBFDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR); // current year
                int mMonth = c.get(Calendar.MONTH); // current month
                int mDay = c.get(Calendar.DAY_OF_MONTH); // current day
                datePickerDialog = new DatePickerDialog(BreastFeedingActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                txtViewEarlyBFDate.setText(dayOfMonth + "/" +(month + 1) + "/" + year);
                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });
        txtViewRoomingInDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR); // current year
                int mMonth = c.get(Calendar.MONTH); // current month
                int mDay = c.get(Calendar.DAY_OF_MONTH); // current day
                datePickerDialog = new DatePickerDialog(BreastFeedingActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                txtViewRoomingInDate.setText(dayOfMonth + "/" +(month + 1) + "/" + year);
                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });
        txtViewBFMethodDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR); // current year
                int mMonth = c.get(Calendar.MONTH); // current month
                int mDay = c.get(Calendar.DAY_OF_MONTH); // current day
                datePickerDialog = new DatePickerDialog(BreastFeedingActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                txtViewBFMethodDate.setText(dayOfMonth + "/" +(month + 1) + "/" + year);
                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });
        txtViewBFBabyNeedsDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR); // current year
                int mMonth = c.get(Calendar.MONTH); // current month
                int mDay = c.get(Calendar.DAY_OF_MONTH); // current day
                datePickerDialog = new DatePickerDialog(BreastFeedingActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                txtViewBFBabyNeedsDate.setText(dayOfMonth + "/" +(month + 1) + "/" + year);
                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });
        txtViewBreastMilkDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR); // current year
                int mMonth = c.get(Calendar.MONTH); // current month
                int mDay = c.get(Calendar.DAY_OF_MONTH); // current day
                datePickerDialog = new DatePickerDialog(BreastFeedingActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                txtViewBreastMilkDate.setText(dayOfMonth + "/" +(month + 1) + "/" + year);
                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });
        txtViewBFProblemDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR); // current year
                int mMonth = c.get(Calendar.MONTH); // current month
                int mDay = c.get(Calendar.DAY_OF_MONTH); // current day
                datePickerDialog = new DatePickerDialog(BreastFeedingActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                txtViewBFProblemDate.setText(dayOfMonth + "/" +(month + 1) + "/" + year);
                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });
        txtViewBFWorkMotherDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR); // current year
                int mMonth = c.get(Calendar.MONTH); // current month
                int mDay = c.get(Calendar.DAY_OF_MONTH); // current day
                datePickerDialog = new DatePickerDialog(BreastFeedingActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                txtViewBFWorkMotherDate.setText(dayOfMonth + "/" +(month + 1) + "/" + year);
                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });
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
