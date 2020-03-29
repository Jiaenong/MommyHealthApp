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

import com.example.mommyhealthapp.Class.MedicalPersonnel;
import com.example.mommyhealthapp.Class.UnusualCircumstancesTutorial;
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

public class UnusualCircumtancesActivity extends AppCompatActivity {
    private CheckBox checkBoxUCEclampsia, checkBoxDiabetes, checkBoxAnemia, checkBoxBleeding;
    private TextView txtViewUCEclampsiaDate, txtViewUCEclampsiaPerson, txtViewDiabetesDate, txtViewDiabetesPerson, txtViewAnemiaDate, txtViewAnemiaPerson,
            txtViewBleedingDate, txtViewBleedingPerson;
    private Button btnUCSave, btnUCCancel;
    private RelativeLayout relativeLayoutUC;
    private LinearLayoutCompat layoutUC;
    private ProgressBar progressBarUC;
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
        setContentView(R.layout.activity_unusual_circumtances);

        checkBoxUCEclampsia = (CheckBox)findViewById(R.id.checkBoxUCEclampsia);
        checkBoxDiabetes = (CheckBox)findViewById(R.id.checkBoxDiabetes);
        checkBoxAnemia = (CheckBox)findViewById(R.id.checkBoxAnemia);
        checkBoxBleeding = (CheckBox)findViewById(R.id.checkBoxBleeding);

        txtViewUCEclampsiaDate = (TextView)findViewById(R.id.txtViewUCEclampsiaDate);
        txtViewUCEclampsiaPerson = (TextView)findViewById(R.id.txtViewUCEclampsiaPerson);
        txtViewDiabetesDate = (TextView)findViewById(R.id.txtViewDiabetesDate);
        txtViewDiabetesPerson = (TextView)findViewById(R.id.txtViewDiabetesPerson);
        txtViewAnemiaDate = (TextView)findViewById(R.id.txtViewAnemiaDate);
        txtViewAnemiaPerson = (TextView)findViewById(R.id.txtViewAnemiaPerson);
        txtViewBleedingDate = (TextView)findViewById(R.id.txtViewBleedingDate);
        txtViewBleedingPerson = (TextView)findViewById(R.id.txtViewBleedingPerson);

        btnUCSave = (Button)findViewById(R.id.btnUCSave);
        btnUCCancel = (Button)findViewById(R.id.btnUCCancel);

        relativeLayoutUC = (RelativeLayout)findViewById(R.id.relativeLayoutUC);
        layoutUC = (LinearLayoutCompat)findViewById(R.id.layoutUC);
        progressBarUC = (ProgressBar)findViewById(R.id.progressBarUC);

        btnUCCancel.setVisibility(View.GONE);
        layoutUC.setVisibility(View.GONE);
        progressBarUC.setVisibility(View.VISIBLE);

        mFirebaseFirestore = FirebaseFirestore.getInstance();
        if(!SaveSharedPreference.getUser(UnusualCircumtancesActivity.this).equals("Mommy"))
        {
            mDocumentReference = mFirebaseFirestore.collection("MedicalPersonnel").document(SaveSharedPreference.getID(UnusualCircumtancesActivity.this));
            mDocumentReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    MedicalPersonnel md = documentSnapshot.toObject(MedicalPersonnel.class);
                    medicalPersonName = md.getName();
                }
            });
        }
        mCollectionReference = mFirebaseFirestore.collection("MommyHealthInfo");
        mCollectionReference.whereEqualTo("mommyId", SaveSharedPreference.getMummyId(UnusualCircumtancesActivity.this)).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for(QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots)
                {
                    healthInfoId = documentSnapshot.getId();
                }
                nCollectionReference = mFirebaseFirestore.collection("MommyHealthInfo").document(healthInfoId).collection("UnusualCircumstancesTutorial");
                nCollectionReference.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        if(queryDocumentSnapshots.isEmpty())
                        {
                            isEmpty = true;
                            layoutUC.setVisibility(View.VISIBLE);
                            progressBarUC.setVisibility(View.GONE);
                            if(SaveSharedPreference.getUser(UnusualCircumtancesActivity.this).equals("Mommy"))
                            {
                                btnUCCancel.setVisibility(View.GONE);
                                btnUCSave.setVisibility(View.GONE);
                                DisableField();
                            }
                        }else {
                            isEmpty = false;
                            DisableField();
                            if(SaveSharedPreference.getUser(UnusualCircumtancesActivity.this).equals("Mommy"))
                            {
                                btnUCCancel.setVisibility(View.GONE);
                                btnUCSave.setVisibility(View.GONE);
                            }
                            for(QueryDocumentSnapshot documentSnapshots : queryDocumentSnapshots)
                            {
                                key = documentSnapshots.getId();
                                UnusualCircumstancesTutorial uc = documentSnapshots.toObject(UnusualCircumstancesTutorial.class);
                                SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                                if(uc.getEclampsiaStatus().equals("Yes"))
                                {
                                    checkBoxUCEclampsia.setChecked(true);
                                    txtViewUCEclampsiaPerson.setText(uc.getEclampsiaPerson());
                                    txtViewUCEclampsiaDate.setText(dateFormat.format(uc.getEclmapsiaDate()));
                                }
                                if(uc.getDiabetesStatus().equals("Yes"))
                                {
                                    checkBoxDiabetes.setChecked(true);
                                    txtViewDiabetesPerson.setText(uc.getDiabetesPerson());
                                    txtViewDiabetesDate.setText(dateFormat.format(uc.getDiabetesDate()));
                                }
                                if(uc.getAnemiaStatus().equals("Yes"))
                                {
                                    checkBoxAnemia.setChecked(true);
                                    txtViewAnemiaPerson.setText(uc.getAnemiaPerson());
                                    txtViewAnemiaDate.setText(dateFormat.format(uc.getAnemiaDate()));
                                }
                                if(uc.getBleedingStatus().equals("Yes"))
                                {
                                    checkBoxBleeding.setChecked(true);
                                    txtViewBleedingPerson.setText(uc.getBleedingPerson());
                                    txtViewBleedingDate.setText(dateFormat.format(uc.getBleedingDate()));
                                }
                            }
                            layoutUC.setVisibility(View.VISIBLE);
                            progressBarUC.setVisibility(View.GONE);
                            btnUCSave.setText("Update");
                        }
                    }
                });
            }
        });

        ViewGoneDateText();
        DateTextView();
        CheckBoxIsCheck();

        btnUCSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isEmpty == true)
                {
                    List<TextView> dateText = new ArrayList<>();
                    CollectionReference mCollectionReference = mFirebaseFirestore.collection("MommyHealthInfo").document(healthInfoId).collection("UnusualCircumstancesTutorial");
                    String eclampsiaStatus = "", diabetesStatus = "", anemiaStatus = "", bleedingStatus = "";
                    String eclampsiaPerson = "", diabetesPerson = "", anemiaPerson = "", bleedingPerson = "";
                    Date eclmapsiaDate = null, diabetesDate = null, anemiaDate = null, bleedingDate = null;

                    if(checkBoxUCEclampsia.isChecked())
                    {
                        eclampsiaStatus = "Yes";
                        eclampsiaPerson = txtViewUCEclampsiaPerson.getText().toString();
                        try {
                            eclmapsiaDate = new SimpleDateFormat("dd/MM/yyyy").parse(txtViewUCEclampsiaDate.getText().toString());
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        dateText.add(txtViewUCEclampsiaDate);
                    }else{
                        eclampsiaStatus = "No";
                        eclampsiaPerson = "";
                        eclmapsiaDate = null;
                    }

                    if(checkBoxDiabetes.isChecked())
                    {
                        diabetesStatus = "Yes";
                        diabetesPerson = txtViewDiabetesPerson.getText().toString();
                        try {
                            diabetesDate = new SimpleDateFormat("dd/MM/yyyy").parse(txtViewDiabetesDate.getText().toString());
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        dateText.add(txtViewDiabetesDate);
                    }else{
                        diabetesStatus = "No";
                        diabetesPerson = "";
                        diabetesDate = null;
                    }

                    if(checkBoxAnemia.isChecked())
                    {
                        anemiaStatus = "Yes";
                        anemiaPerson = txtViewAnemiaPerson.getText().toString();
                        try {
                            anemiaDate = new SimpleDateFormat("dd/MM/yyyy").parse(txtViewAnemiaDate.getText().toString());
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        dateText.add(txtViewAnemiaDate);
                    }else{
                        anemiaStatus = "No";
                        anemiaPerson = "";
                        anemiaDate = null;
                    }

                    if(checkBoxBleeding.isChecked())
                    {
                        bleedingStatus = "Yes";
                        bleedingPerson = txtViewBleedingPerson.getText().toString();
                        try {
                            bleedingDate = new SimpleDateFormat("dd/MM/yyyy").parse(txtViewBleedingDate.getText().toString());
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        dateText.add(txtViewBleedingDate);
                    }else{
                        bleedingStatus = "No";
                        bleedingPerson = "";
                        bleedingDate = null;
                    }

                    UnusualCircumstancesTutorial uc = new UnusualCircumstancesTutorial(eclampsiaStatus, eclmapsiaDate, eclampsiaPerson, diabetesStatus, diabetesDate, diabetesPerson,
                            anemiaStatus, anemiaDate, anemiaPerson, bleedingStatus, bleedingDate, bleedingPerson);
                    boolean checkDate = CheckDateSelected(dateText);
                    if(checkDate == true)
                    {
                        mCollectionReference.add(uc).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {
                                AlertDialog.Builder builder = new AlertDialog.Builder(UnusualCircumtancesActivity.this);
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
                        btnUCCancel.setVisibility(View.VISIBLE);
                    }else if(check == 2)
                    {
                        List<TextView> dateText = new ArrayList<>();
                        DocumentReference mDocumentReference = mFirebaseFirestore.collection("MommyHealthInfo").document(healthInfoId).collection("UnusualCircumstancesTutorial").document(key);
                        String eclampsiaStatus = "", diabetesStatus = "", anemiaStatus = "", bleedingStatus = "";
                        String eclampsiaPerson = "", diabetesPerson = "", anemiaPerson = "", bleedingPerson = "";
                        Date eclmapsiaDate = null, diabetesDate = null, anemiaDate = null, bleedingDate = null;

                        if(checkBoxUCEclampsia.isChecked())
                        {
                            eclampsiaStatus = "Yes";
                            eclampsiaPerson = txtViewUCEclampsiaPerson.getText().toString();
                            try {
                                eclmapsiaDate = new SimpleDateFormat("dd/MM/yyyy").parse(txtViewUCEclampsiaDate.getText().toString());
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                            dateText.add(txtViewUCEclampsiaDate);
                        }else{
                            eclampsiaStatus = "No";
                            eclampsiaPerson = "";
                            eclmapsiaDate = null;
                        }

                        if(checkBoxDiabetes.isChecked())
                        {
                            diabetesStatus = "Yes";
                            diabetesPerson = txtViewDiabetesPerson.getText().toString();
                            try {
                                diabetesDate = new SimpleDateFormat("dd/MM/yyyy").parse(txtViewDiabetesDate.getText().toString());
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                            dateText.add(txtViewDiabetesDate);
                        }else{
                            diabetesStatus = "No";
                            diabetesPerson = "";
                            diabetesDate = null;
                        }

                        if(checkBoxAnemia.isChecked())
                        {
                            anemiaStatus = "Yes";
                            anemiaPerson = txtViewAnemiaPerson.getText().toString();
                            try {
                                anemiaDate = new SimpleDateFormat("dd/MM/yyyy").parse(txtViewAnemiaDate.getText().toString());
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                            dateText.add(txtViewAnemiaDate);
                        }else{
                            anemiaStatus = "No";
                            anemiaPerson = "";
                            anemiaDate = null;
                        }

                        if(checkBoxBleeding.isChecked())
                        {
                            bleedingStatus = "Yes";
                            bleedingPerson = txtViewBleedingPerson.getText().toString();
                            try {
                                bleedingDate = new SimpleDateFormat("dd/MM/yyyy").parse(txtViewBleedingDate.getText().toString());
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                            dateText.add(txtViewBleedingDate);
                        }else{
                            bleedingStatus = "No";
                            bleedingPerson = "";
                            bleedingDate = null;
                        }
                        boolean checkDate = CheckDateSelected(dateText);
                        if(checkDate == true)
                        {
                            mDocumentReference.update("eclampsiaStatus", eclampsiaStatus);
                            mDocumentReference.update("eclmapsiaDate", eclmapsiaDate);
                            mDocumentReference.update("eclampsiaPerson", eclampsiaPerson);
                            mDocumentReference.update("diabetesStatus", diabetesStatus);
                            mDocumentReference.update("diabetesDate", diabetesDate);
                            mDocumentReference.update("diabetesPerson", diabetesPerson);
                            mDocumentReference.update("anemiaStatus", anemiaStatus);
                            mDocumentReference.update("anemiaDate", anemiaDate);
                            mDocumentReference.update("anemiaPerson", anemiaPerson);
                            mDocumentReference.update("bleedingStatus", bleedingStatus);
                            mDocumentReference.update("bleedingDate", bleedingDate);
                            mDocumentReference.update("bleedingPerson", bleedingPerson);
                            Snackbar snackbar = Snackbar.make(relativeLayoutUC,"Updated Successfully!",Snackbar.LENGTH_LONG);
                            snackbar.show();
                            DisableField();
                            btnUCCancel.setVisibility(View.GONE);
                            check = 0;
                        }else{
                            check = 1;
                        }
                    }
                }
            }
        });

        btnUCCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DisableField();
                btnUCCancel.setVisibility(View.GONE);
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
        for(int i = 0; i<layoutUC.getChildCount(); i++)
        {
            View view = layoutUC.getChildAt(i);
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
        for(int i = 0; i<layoutUC.getChildCount(); i++)
        {
            View view = layoutUC.getChildAt(i);
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
        checkBoxUCEclampsia.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                {
                    txtViewUCEclampsiaDate.setVisibility(View.VISIBLE);
                    txtViewUCEclampsiaPerson.setVisibility(View.VISIBLE);
                    txtViewUCEclampsiaPerson.setText(medicalPersonName);
                }else{
                    txtViewUCEclampsiaDate.setVisibility(View.GONE);
                    txtViewUCEclampsiaPerson.setVisibility(View.GONE);
                }
            }
        });
        checkBoxDiabetes.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                {
                    txtViewDiabetesDate.setVisibility(View.VISIBLE);
                    txtViewDiabetesPerson.setVisibility(View.VISIBLE);
                    txtViewDiabetesPerson.setText(medicalPersonName);
                }else{
                    txtViewDiabetesDate.setVisibility(View.GONE);
                    txtViewDiabetesPerson.setVisibility(View.GONE);
                }
            }
        });
        checkBoxAnemia.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                {
                    txtViewAnemiaDate.setVisibility(View.VISIBLE);
                    txtViewAnemiaPerson.setVisibility(View.VISIBLE);
                    txtViewAnemiaPerson.setText(medicalPersonName);
                }else{
                    txtViewAnemiaDate.setVisibility(View.GONE);
                    txtViewAnemiaPerson.setVisibility(View.GONE);
                }
            }
        });
        checkBoxBleeding.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                {
                    txtViewBleedingDate.setVisibility(View.VISIBLE);
                    txtViewBleedingPerson.setVisibility(View.VISIBLE);
                    txtViewBleedingPerson.setText(medicalPersonName);
                }else{
                    txtViewBleedingDate.setVisibility(View.GONE);
                    txtViewBleedingPerson.setVisibility(View.GONE);
                }
            }
        });
    }

    private void ViewGoneDateText()
    {
        txtViewUCEclampsiaDate.setVisibility(View.GONE);
        txtViewUCEclampsiaPerson.setVisibility(View.GONE);
        txtViewDiabetesDate.setVisibility(View.GONE);
        txtViewDiabetesPerson.setVisibility(View.GONE);
        txtViewAnemiaDate.setVisibility(View.GONE);
        txtViewAnemiaPerson.setVisibility(View.GONE);
        txtViewBleedingDate.setVisibility(View.GONE);
        txtViewBleedingPerson.setVisibility(View.GONE);
    }

    private void DateTextView()
    {
        txtViewUCEclampsiaDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR); // current year
                int mMonth = c.get(Calendar.MONTH); // current month
                int mDay = c.get(Calendar.DAY_OF_MONTH); // current day
                datePickerDialog = new DatePickerDialog(UnusualCircumtancesActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                txtViewUCEclampsiaDate.setText(dayOfMonth + "/" +(month + 1) + "/" + year);
                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });
        txtViewDiabetesDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR); // current year
                int mMonth = c.get(Calendar.MONTH); // current month
                int mDay = c.get(Calendar.DAY_OF_MONTH); // current day
                datePickerDialog = new DatePickerDialog(UnusualCircumtancesActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                txtViewDiabetesDate.setText(dayOfMonth + "/" +(month + 1) + "/" + year);
                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });
        txtViewAnemiaDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR); // current year
                int mMonth = c.get(Calendar.MONTH); // current month
                int mDay = c.get(Calendar.DAY_OF_MONTH); // current day
                datePickerDialog = new DatePickerDialog(UnusualCircumtancesActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                txtViewAnemiaDate.setText(dayOfMonth + "/" +(month + 1) + "/" + year);
                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });
        txtViewBleedingDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR); // current year
                int mMonth = c.get(Calendar.MONTH); // current month
                int mDay = c.get(Calendar.DAY_OF_MONTH); // current day
                datePickerDialog = new DatePickerDialog(UnusualCircumtancesActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                txtViewBleedingDate.setText(dayOfMonth + "/" +(month + 1) + "/" + year);
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
