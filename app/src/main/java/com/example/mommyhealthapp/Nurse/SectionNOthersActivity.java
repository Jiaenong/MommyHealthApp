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
import com.example.mommyhealthapp.Class.MedicalPersonnel;
import com.example.mommyhealthapp.Class.OtherTutorial;
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

public class SectionNOthersActivity extends AppCompatActivity {
    private CheckBox checkBoxCaringNewBorn, checkBoxJaundice, checkBoxPapSmear, checkBoxBreastCancer;
    private TextView txtViewCaringNewBornDate, txtViewCaringNewBornPerson, txtViewJaundiceDate, txtViewJaundicePerson, txtViewPapSmearDate, txtViewPapSmearPerson,
            txtViewBreastCancerDate,  txtViewBreastCancerPerson;
    private Button btnOtherTutorialSave, btnOtherTutorialCancel;
    private RelativeLayout relativeLayoutOtherTutorial;
    private LinearLayoutCompat layoutOtherTutorial;
    private ProgressBar progressBarOtherTutorial;
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
        setContentView(R.layout.activity_section_nothers);

        checkBoxCaringNewBorn = (CheckBox)findViewById(R.id.checkBoxCaringNewBorn);
        checkBoxJaundice = (CheckBox)findViewById(R.id.checkBoxJaundice);
        checkBoxPapSmear = (CheckBox)findViewById(R.id.checkBoxPapSmear);
        checkBoxBreastCancer = (CheckBox)findViewById(R.id.checkBoxBreastCancer);

        txtViewCaringNewBornDate = (TextView)findViewById(R.id.txtViewCaringNewBornDate);
        txtViewCaringNewBornPerson = (TextView)findViewById(R.id.txtViewCaringNewBornPerson);
        txtViewJaundiceDate = (TextView)findViewById(R.id.txtViewJaundiceDate);
        txtViewJaundicePerson = (TextView)findViewById(R.id.txtViewJaundicePerson);
        txtViewPapSmearDate = (TextView)findViewById(R.id.txtViewPapSmearDate);
        txtViewPapSmearPerson = (TextView)findViewById(R.id.txtViewPapSmearPerson);
        txtViewBreastCancerDate = (TextView)findViewById(R.id.txtViewBreastCancerDate);
        txtViewBreastCancerPerson = (TextView)findViewById(R.id.txtViewBreastCancerPerson);

        btnOtherTutorialSave = (Button)findViewById(R.id.btnOtherTutorialSave);
        btnOtherTutorialCancel = (Button)findViewById(R.id.btnOtherTutorialCancel);

        relativeLayoutOtherTutorial = (RelativeLayout)findViewById(R.id.relativeLayoutOtherTutorial);
        layoutOtherTutorial = (LinearLayoutCompat)findViewById(R.id.layoutOtherTutorial);
        progressBarOtherTutorial = (ProgressBar)findViewById(R.id.progressBarOtherTutorial);

        btnOtherTutorialCancel.setVisibility(View.GONE);
        layoutOtherTutorial.setVisibility(View.GONE);
        progressBarOtherTutorial.setVisibility(View.VISIBLE);

        mFirebaseFirestore = FirebaseFirestore.getInstance();
        if(!SaveSharedPreference.getUser(SectionNOthersActivity.this).equals("Mommy"))
        {
            mDocumentReference = mFirebaseFirestore.collection("MedicalPersonnel").document(SaveSharedPreference.getID(SectionNOthersActivity.this));
            mDocumentReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    MedicalPersonnel md = documentSnapshot.toObject(MedicalPersonnel.class);
                    medicalPersonName = md.getName();
                }
            });
        }
        mCollectionReference = mFirebaseFirestore.collection("MommyHealthInfo");
        mCollectionReference.whereEqualTo("mommyId", SaveSharedPreference.getMummyId(SectionNOthersActivity.this)).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for(QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots)
                {
                    healthInfoId = documentSnapshot.getId();
                }
                nCollectionReference = mFirebaseFirestore.collection("MommyHealthInfo").document(healthInfoId).collection("OtherTutorial");
                nCollectionReference.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        if(queryDocumentSnapshots.isEmpty())
                        {
                            isEmpty = true;
                            layoutOtherTutorial.setVisibility(View.VISIBLE);
                            progressBarOtherTutorial.setVisibility(View.GONE);
                            if(SaveSharedPreference.getUser(SectionNOthersActivity.this).equals("Mommy"))
                            {
                                btnOtherTutorialCancel.setVisibility(View.GONE);
                                btnOtherTutorialSave.setVisibility(View.GONE);
                                DisableField();
                            }
                        }else{
                            isEmpty = false;
                            DisableField();
                            if(SaveSharedPreference.getUser(SectionNOthersActivity.this).equals("Mommy"))
                            {
                                btnOtherTutorialCancel.setVisibility(View.GONE);
                                btnOtherTutorialSave.setVisibility(View.GONE);
                            }
                            for(QueryDocumentSnapshot documentSnapshots : queryDocumentSnapshots)
                            {
                                key = documentSnapshots.getId();
                                OtherTutorial ot = documentSnapshots.toObject(OtherTutorial.class);
                                SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

                                if(ot.getCaringNewBornStatus().equals("Yes"))
                                {
                                    checkBoxCaringNewBorn.setChecked(true);
                                    txtViewCaringNewBornPerson.setText(ot.getCaringNewBornPerson());
                                    txtViewCaringNewBornDate.setText(dateFormat.format(ot.getCaringNewBornDate()));
                                }
                                if(ot.getNeonatalJaundiceStatus().equals("Yes"))
                                {
                                    checkBoxJaundice.setChecked(true);
                                    txtViewJaundicePerson.setText(ot.getNeonatalJaundicePerson());
                                    txtViewJaundiceDate.setText(dateFormat.format(ot.getNeonatalJaundiceDate()));
                                }
                                if(ot.getPapSmearStatus().equals("Yes"))
                                {
                                    checkBoxPapSmear.setChecked(true);
                                    txtViewPapSmearPerson.setText(ot.getPapSmearPerson());
                                    txtViewPapSmearDate.setText(dateFormat.format(ot.getPapSmearDate()));
                                }
                                if(ot.getBreastCancerScreeningStatus().equals("Yes"))
                                {
                                    checkBoxBreastCancer.setChecked(true);
                                    txtViewBreastCancerPerson.setText(ot.getBreastCancerScreeningPerson());
                                    txtViewBreastCancerDate.setText(dateFormat.format(ot.getBreastCancerScreeningDate()));
                                }
                            }
                            layoutOtherTutorial.setVisibility(View.VISIBLE);
                            progressBarOtherTutorial.setVisibility(View.GONE);
                            btnOtherTutorialSave.setText("Update");
                        }
                    }
                });
            }
        });

        ViewGoneDateText();
        DateTextView();
        CheckBoxIsCheck();

        btnOtherTutorialSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isEmpty == true)
                {
                    List<TextView> dateText = new ArrayList<>();
                    CollectionReference mCollectionReference = mFirebaseFirestore.collection("MommyHealthInfo").document(healthInfoId).collection("OtherTutorial");
                    String caringNewBornStatus = "", neonatalJaundiceStatus = "", papSmearStatus = "", breastCancerScreeningStatus = "";
                    String caringNewBornPerson = "", neonatalJaundicePerson = "", papSmearPerson = "", breastCancerScreeningPerson = "";
                    Date caringNewBornDate = null, neonatalJaundiceDate = null, papSmearDate = null, breastCancerScreeningDate = null;

                    if(checkBoxCaringNewBorn.isChecked())
                    {
                        caringNewBornStatus = "Yes";
                        caringNewBornPerson = txtViewCaringNewBornPerson.getText().toString();
                        try {
                            caringNewBornDate = new SimpleDateFormat("dd/MM/yyyy").parse(txtViewCaringNewBornDate.getText().toString());
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        dateText.add(txtViewCaringNewBornDate);
                    }else{
                        caringNewBornStatus = "No";
                        caringNewBornPerson = "";
                        caringNewBornDate = null;
                    }

                    if(checkBoxJaundice.isChecked())
                    {
                        neonatalJaundiceStatus = "Yes";
                        neonatalJaundicePerson = txtViewJaundicePerson.getText().toString();
                        try {
                            neonatalJaundiceDate = new SimpleDateFormat("dd/MM/yyyy").parse(txtViewJaundiceDate.getText().toString());
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        dateText.add(txtViewJaundiceDate);
                    }else{
                        neonatalJaundiceStatus = "No";
                        neonatalJaundicePerson = "";
                        neonatalJaundiceDate = null;
                    }

                    if(checkBoxPapSmear.isChecked())
                    {
                        papSmearStatus = "Yes";
                        papSmearPerson = txtViewPapSmearPerson.getText().toString();
                        try {
                            papSmearDate = new SimpleDateFormat("dd/MM/yyyy").parse(txtViewPapSmearDate.getText().toString());
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        dateText.add(txtViewPapSmearDate);
                    }else{
                        papSmearStatus = "No";
                        papSmearPerson = "";
                        papSmearDate = null;
                    }

                    if(checkBoxBreastCancer.isChecked())
                    {
                        breastCancerScreeningStatus = "Yes";
                        breastCancerScreeningPerson = txtViewBreastCancerPerson.getText().toString();
                        try {
                            breastCancerScreeningDate = new SimpleDateFormat("dd/MM/yyyy").parse(txtViewBreastCancerDate.getText().toString());
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        dateText.add(txtViewBreastCancerDate);
                    }else{
                        breastCancerScreeningStatus = "No";
                        breastCancerScreeningPerson = "";
                        breastCancerScreeningDate = null;
                    }
                    OtherTutorial ot = new OtherTutorial(caringNewBornStatus, caringNewBornDate, caringNewBornPerson, neonatalJaundiceStatus, neonatalJaundiceDate,
                            neonatalJaundicePerson, papSmearStatus, papSmearDate, papSmearPerson, breastCancerScreeningStatus,
                            breastCancerScreeningDate, breastCancerScreeningPerson);
                    if(dateText.isEmpty())
                    {
                        final AlertDialog.Builder builder = new AlertDialog.Builder(SectionNOthersActivity.this);
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
                        if(checkDate == true)
                        {
                            mCollectionReference.add(ot).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                @Override
                                public void onSuccess(DocumentReference documentReference) {
                                    AlertDialog.Builder builder = new AlertDialog.Builder(SectionNOthersActivity.this);
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
                        EnableField();
                        btnOtherTutorialCancel.setVisibility(View.VISIBLE);
                    }else if(check == 2)
                    {
                        List<TextView> dateText = new ArrayList<>();
                        DocumentReference mDocumentReference = mFirebaseFirestore.collection("MommyHealthInfo").document(healthInfoId).collection("OtherTutorial").document(key);
                        String caringNewBornStatus = "", neonatalJaundiceStatus = "", papSmearStatus = "", breastCancerScreeningStatus = "";
                        String caringNewBornPerson = "", neonatalJaundicePerson = "", papSmearPerson = "", breastCancerScreeningPerson = "";
                        Date caringNewBornDate = null, neonatalJaundiceDate = null, papSmearDate = null, breastCancerScreeningDate = null;

                        if(checkBoxCaringNewBorn.isChecked())
                        {
                            caringNewBornStatus = "Yes";
                            caringNewBornPerson = txtViewCaringNewBornPerson.getText().toString();
                            try {
                                caringNewBornDate = new SimpleDateFormat("dd/MM/yyyy").parse(txtViewCaringNewBornDate.getText().toString());
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                            dateText.add(txtViewCaringNewBornDate);
                        }else{
                            caringNewBornStatus = "Yes";
                            caringNewBornPerson = "";
                            caringNewBornDate = null;
                        }

                        if(checkBoxJaundice.isChecked())
                        {
                            neonatalJaundiceStatus = "Yes";
                            neonatalJaundicePerson = txtViewJaundicePerson.getText().toString();
                            try {
                                neonatalJaundiceDate = new SimpleDateFormat("dd/MM/yyyy").parse(txtViewJaundiceDate.getText().toString());
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                            dateText.add(txtViewJaundiceDate);
                        }else{
                            neonatalJaundiceStatus = "No";
                            neonatalJaundicePerson = "";
                            neonatalJaundiceDate = null;
                        }

                        if(checkBoxPapSmear.isChecked())
                        {
                            papSmearStatus = "Yes";
                            papSmearPerson = txtViewPapSmearPerson.getText().toString();
                            try {
                                papSmearDate = new SimpleDateFormat("dd/MM/yyyy").parse(txtViewPapSmearDate.getText().toString());
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                            dateText.add(txtViewPapSmearDate);
                        }else{
                            papSmearStatus = "No";
                            papSmearPerson = "";
                            papSmearDate = null;
                        }

                        if(checkBoxBreastCancer.isChecked())
                        {
                            breastCancerScreeningStatus = "Yes";
                            breastCancerScreeningPerson = txtViewBreastCancerPerson.getText().toString();
                            try {
                                breastCancerScreeningDate = new SimpleDateFormat("dd/MM/yyyy").parse(txtViewBreastCancerDate.getText().toString());
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                            dateText.add(txtViewBreastCancerDate);
                        }else{
                            breastCancerScreeningStatus = "No";
                            breastCancerScreeningPerson = "";
                            breastCancerScreeningDate = null;
                        }
                        if(dateText.isEmpty())
                        {
                            check = 1;
                            final AlertDialog.Builder builder = new AlertDialog.Builder(SectionNOthersActivity.this);
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
                            if(checkDate == true)
                            {
                                mDocumentReference.update("caringNewBornStatus", caringNewBornStatus);
                                mDocumentReference.update("caringNewBornDate", caringNewBornDate);
                                mDocumentReference.update("caringNewBornPerson", caringNewBornPerson);
                                mDocumentReference.update("neonatalJaundiceStatus", neonatalJaundiceStatus);
                                mDocumentReference.update("neonatalJaundiceDate", neonatalJaundiceDate);
                                mDocumentReference.update("neonatalJaundicePerson", neonatalJaundicePerson);
                                mDocumentReference.update("papSmearStatus", papSmearStatus);
                                mDocumentReference.update("papSmearDate", papSmearDate);
                                mDocumentReference.update("papSmearPerson", papSmearPerson);
                                mDocumentReference.update("breastCancerScreeningStatus", breastCancerScreeningStatus);
                                mDocumentReference.update("breastCancerScreeningDate", breastCancerScreeningDate);
                                mDocumentReference.update("breastCancerScreeningPerson", breastCancerScreeningPerson);
                                Snackbar snackbar = Snackbar.make(relativeLayoutOtherTutorial, "Updated Successfully!", Snackbar.LENGTH_LONG);
                                snackbar.show();
                                DisableField();
                                btnOtherTutorialCancel.setVisibility(View.GONE);
                                check = 0;
                            }else{
                                check = 1;
                            }
                        }
                    }
                }
            }
        });

        btnOtherTutorialCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DisableField();
                btnOtherTutorialCancel.setVisibility(View.GONE);
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
        for(int i = 0; i<layoutOtherTutorial.getChildCount(); i++)
        {
            View view = layoutOtherTutorial.getChildAt(i);
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
        for(int i = 0; i<layoutOtherTutorial.getChildCount(); i++)
        {
            View view = layoutOtherTutorial.getChildAt(i);
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

    private void DateTextView()
    {
        txtViewCaringNewBornDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR); // current year
                int mMonth = c.get(Calendar.MONTH); // current month
                int mDay = c.get(Calendar.DAY_OF_MONTH); // current day
                datePickerDialog = new DatePickerDialog(SectionNOthersActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                txtViewCaringNewBornDate.setText(dayOfMonth + "/" +(month + 1) + "/" + year);
                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });
        txtViewJaundiceDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR); // current year
                int mMonth = c.get(Calendar.MONTH); // current month
                int mDay = c.get(Calendar.DAY_OF_MONTH); // current day
                datePickerDialog = new DatePickerDialog(SectionNOthersActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                txtViewJaundiceDate.setText(dayOfMonth + "/" +(month + 1) + "/" + year);
                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });
        txtViewPapSmearDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR); // current year
                int mMonth = c.get(Calendar.MONTH); // current month
                int mDay = c.get(Calendar.DAY_OF_MONTH); // current day
                datePickerDialog = new DatePickerDialog(SectionNOthersActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                txtViewPapSmearDate.setText(dayOfMonth + "/" +(month + 1) + "/" + year);
                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });
        txtViewBreastCancerDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR); // current year
                int mMonth = c.get(Calendar.MONTH); // current month
                int mDay = c.get(Calendar.DAY_OF_MONTH); // current day
                datePickerDialog = new DatePickerDialog(SectionNOthersActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                txtViewBreastCancerDate.setText(dayOfMonth + "/" +(month + 1) + "/" + year);
                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });
    }

    private void ViewGoneDateText()
    {
        txtViewCaringNewBornDate.setVisibility(View.GONE);
        txtViewCaringNewBornPerson.setVisibility(View.GONE);
        txtViewJaundiceDate.setVisibility(View.GONE);
        txtViewJaundicePerson.setVisibility(View.GONE);
        txtViewPapSmearDate.setVisibility(View.GONE);
        txtViewPapSmearPerson.setVisibility(View.GONE);
        txtViewBreastCancerDate.setVisibility(View.GONE);
        txtViewBreastCancerPerson.setVisibility(View.GONE);
    }

    private void CheckBoxIsCheck()
    {
        checkBoxCaringNewBorn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                {
                    txtViewCaringNewBornDate.setVisibility(View.VISIBLE);
                    txtViewCaringNewBornPerson.setVisibility(View.VISIBLE);
                    txtViewCaringNewBornPerson.setText(medicalPersonName);
                }else{
                    txtViewCaringNewBornDate.setVisibility(View.GONE);
                    txtViewCaringNewBornPerson.setVisibility(View.GONE);
                }
            }
        });
        checkBoxJaundice.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                {
                    txtViewJaundiceDate.setVisibility(View.VISIBLE);
                    txtViewJaundicePerson.setVisibility(View.VISIBLE);
                    txtViewJaundicePerson.setText(medicalPersonName);
                }else{
                    txtViewJaundiceDate.setVisibility(View.GONE);
                    txtViewJaundicePerson.setVisibility(View.GONE);
                }
            }
        });
        checkBoxPapSmear.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                {
                    txtViewPapSmearDate.setVisibility(View.VISIBLE);
                    txtViewPapSmearPerson.setVisibility(View.VISIBLE);
                    txtViewPapSmearPerson.setText(medicalPersonName);
                }else{
                    txtViewPapSmearDate.setVisibility(View.GONE);
                    txtViewPapSmearPerson.setVisibility(View.GONE);
                }
            }
        });
        checkBoxBreastCancer.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                {
                    txtViewBreastCancerDate.setVisibility(View.VISIBLE);
                    txtViewBreastCancerPerson.setVisibility(View.VISIBLE);
                    txtViewBreastCancerPerson.setText(medicalPersonName);
                }else{
                    txtViewBreastCancerDate.setVisibility(View.GONE);
                    txtViewBreastCancerPerson.setVisibility(View.GONE);
                }
            }
        });
    }

    private void CancelAlarm()
    {
        AlarmManager alarmManager = (AlarmManager)SectionNOthersActivity.this.getSystemService(Context.ALARM_SERVICE );
        Intent intent = new Intent(SectionNOthersActivity.this, NotifyService.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(SectionNOthersActivity.this, 9000, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        alarmManager.cancel(pendingIntent);
        pendingIntent.cancel();

        Intent intentReminder = new Intent(SectionNOthersActivity.this, ReminderService.class);
        PendingIntent pendingIntentReminder = PendingIntent.getBroadcast(SectionNOthersActivity.this, 101, intentReminder, PendingIntent.FLAG_UPDATE_CURRENT);
        alarmManager.cancel(pendingIntentReminder);
        pendingIntentReminder.cancel();

        Intent intentAlert = new Intent(SectionNOthersActivity.this, AlertService.class);
        PendingIntent pendingIntentAlert = PendingIntent.getBroadcast(SectionNOthersActivity.this, 1000, intentAlert, 0);
        alarmManager.cancel(pendingIntentAlert);
        pendingIntentAlert.cancel();

        Log.i("TestingAlarmCancel", "Alarm Cancel");
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.action_logout:
                AlertDialog.Builder builder = new AlertDialog.Builder(SectionNOthersActivity.this);
                builder.setTitle("Logout");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if(SaveSharedPreference.getUser(SectionNOthersActivity.this).equals("Mommy"))
                        {
                            CancelAlarm();
                        }
                        SaveSharedPreference.clearUser(SectionNOthersActivity.this);
                        Intent intent = new Intent(SectionNOthersActivity.this, MainActivity.class);
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
