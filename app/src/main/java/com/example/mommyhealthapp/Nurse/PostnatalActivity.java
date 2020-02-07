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
import com.example.mommyhealthapp.Class.PostnatalTutorial;
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
import java.util.Calendar;
import java.util.Date;

public class PostnatalActivity extends AppCompatActivity {
    private CheckBox checkBoxPostnatalMeal, checkBoxPostnatalExercise, checkBoxPHygiene, checkBoxPEarlyTreatment;
    private TextView txtViewPostnatalMealDate, txtPostnatalMealPerson, txtViewPExerciseDate, txtViewPExercisePerson, txtViewPHygieneDate, txtViewPHygienePerson,
            txtViewPEarlyTreatmentDate, txtViewPEarlyTreatmentPerson;
    private LinearLayoutCompat layoutPostnatal;
    private RelativeLayout relativeLayoutPostnatalTutorial;
    private ProgressBar progressBarPostnatalTutorial;
    DatePickerDialog datePickerDialog;
    private Button btnPostnatalSave, btnPostnatalCancel;

    private String medicalPersonName, healthInfoId, key;
    private Boolean isEmpty;
    private int check = 0;
    private FirebaseFirestore mFirebaseFirestroe;
    private CollectionReference mCollectionReference, nCollectionReference;
    private DocumentReference mDocumentReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_postnatal);

        checkBoxPostnatalMeal = (CheckBox)findViewById(R.id.checkBoxPostnatalMeal);
        checkBoxPostnatalExercise = (CheckBox)findViewById(R.id.checkBoxPostnatalExercise);
        checkBoxPHygiene = (CheckBox)findViewById(R.id.checkBoxPHygiene);
        checkBoxPEarlyTreatment = (CheckBox)findViewById(R.id.checkBoxPEarlyTreatment);

        txtViewPostnatalMealDate = (TextView)findViewById(R.id.txtViewPostnatalMealDate);
        txtPostnatalMealPerson = (TextView)findViewById(R.id.txtPostnatalMealPerson);
        txtViewPExerciseDate = (TextView)findViewById(R.id.txtViewPExerciseDate);
        txtViewPExercisePerson = (TextView)findViewById(R.id.txtViewPExercisePerson);
        txtViewPHygieneDate = (TextView)findViewById(R.id.txtViewPHygieneDate);
        txtViewPHygienePerson = (TextView)findViewById(R.id.txtViewPHygienePerson);
        txtViewPEarlyTreatmentDate = (TextView)findViewById(R.id.txtViewPEarlyTreatmentDate);
        txtViewPEarlyTreatmentPerson = (TextView)findViewById(R.id.txtViewPEarlyTreatmentPerson);

        layoutPostnatal = (LinearLayoutCompat)findViewById(R.id.layoutPostnatal);
        relativeLayoutPostnatalTutorial = (RelativeLayout)findViewById(R.id.relativeLayoutPostnatalTutorial);
        progressBarPostnatalTutorial = (ProgressBar)findViewById(R.id.progressBarPostnatalTutorial);

        btnPostnatalSave = (Button)findViewById(R.id.btnPostnatalSave);
        btnPostnatalCancel = (Button)findViewById(R.id.btnPostnatalCancel);

        btnPostnatalCancel.setVisibility(View.GONE);
        progressBarPostnatalTutorial.setVisibility(View.VISIBLE);
        layoutPostnatal.setVisibility(View.GONE);

        mFirebaseFirestroe = FirebaseFirestore.getInstance();
        mDocumentReference = mFirebaseFirestroe.collection("MedicalPersonnel").document(SaveSharedPreference.getID(PostnatalActivity.this));
        mDocumentReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                MedicalPersonnel md = documentSnapshot.toObject(MedicalPersonnel.class);
                medicalPersonName = md.getName();
            }
        });
        mCollectionReference = mFirebaseFirestroe.collection("MommyHealthInfo");
        mCollectionReference.whereEqualTo("mommyId", SaveSharedPreference.getMummyId(PostnatalActivity.this)).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for(QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots)
                {
                    healthInfoId = documentSnapshot.getId();
                }
                nCollectionReference = mFirebaseFirestroe.collection("MommyHealthInfo").document(healthInfoId).collection("PostnatalTutorial");
                nCollectionReference.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        if(queryDocumentSnapshots.isEmpty())
                        {
                            isEmpty = true;
                            progressBarPostnatalTutorial.setVisibility(View.GONE);
                            layoutPostnatal.setVisibility(View.VISIBLE);
                        }else{
                            isEmpty = false;
                            DisableField();
                            for(QueryDocumentSnapshot documentSnapshots : queryDocumentSnapshots)
                            {
                                key = documentSnapshots.getId();
                                PostnatalTutorial pt = documentSnapshots.toObject(PostnatalTutorial.class);
                                SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                                if(pt.getMealNutritionStatus().equals("Yes"))
                                {
                                    checkBoxPostnatalMeal.setChecked(true);
                                    txtPostnatalMealPerson.setText(pt.getMealNutritionPerson());
                                    txtViewPostnatalMealDate.setText(dateFormat.format(pt.getMealNutritionDate()));
                                }
                                if(pt.getPostnatalExerciseStatus().equals("Yes"))
                                {
                                    checkBoxPostnatalExercise.setChecked(true);
                                    txtViewPExercisePerson.setText(pt.getPostnatalExercisePerson());
                                    txtViewPExerciseDate.setText(dateFormat.format(pt.getPostnatalExerciseDate()));
                                }
                                if(pt.getHygieneStatus().equals("Yes"))
                                {
                                    checkBoxPHygiene.setChecked(true);
                                    txtViewPHygienePerson.setText(pt.getHygienePerson());
                                    txtViewPHygieneDate.setText(dateFormat.format(pt.getHygieneDate()));
                                }
                                if(pt.getEarlyTreatmentStatus().equals("Yes"))
                                {
                                    checkBoxPEarlyTreatment.setChecked(true);
                                    txtViewPEarlyTreatmentPerson.setText(pt.getEarlyTreatmentPerson());
                                    txtViewPEarlyTreatmentDate.setText(dateFormat.format(pt.getEarlyTreatmentDate()));
                                }
                            }
                            progressBarPostnatalTutorial.setVisibility(View.GONE);
                            layoutPostnatal.setVisibility(View.VISIBLE);
                        }
                    }
                });
            }
        });

        ViewGoneDateText();
        DateTextView();
        CheckBoxIsCheck();

        btnPostnatalSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isEmpty == true)
                {
                    boolean checkDateSelectMeal = true, checkDateSelectExercise = true, checkDateSelectHygiene = true, checkDateSelectEarlyTreatment = true;
                    nCollectionReference = mFirebaseFirestroe.collection("MommyHealthInfo").document(healthInfoId).collection("PostnatalTutorial");
                    String mealNutritionStatus = "", postnatalExerciseStatus = "", hygieneStatus = "", earlyTreatmentStatus = "";
                    String mealNutritionPerson = "", postnatalExercisePerson = "", hygienePerson = "", earlyTreatmentPerson = "";
                    Date mealNutritionDate = null, postnatalExerciseDate = null, hygieneDate = null, earlyTreatmentDate = null;

                    if(checkBoxPostnatalMeal.isChecked())
                    {
                        mealNutritionStatus = "Yes";
                        mealNutritionPerson = txtPostnatalMealPerson.getText().toString();
                        if(txtViewPostnatalMealDate.getText().toString().equals(getResources().getString(R.string.secN_Date)))
                        {
                            checkDateSelectMeal = false;
                            txtViewPostnatalMealDate.requestFocus();
                            txtViewPostnatalMealDate.setError("This field is required");
                        }else{
                            checkDateSelectMeal = true;
                            try {
                                mealNutritionDate = new SimpleDateFormat("dd/MM/yyyy").parse(txtViewPostnatalMealDate.getText().toString());
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                        }
                    }else{
                        mealNutritionStatus = "No";
                        mealNutritionPerson = txtPostnatalMealPerson.getText().toString();
                        mealNutritionDate = null;
                    }

                    if(checkBoxPostnatalExercise.isChecked())
                    {
                        postnatalExerciseStatus = "Yes";
                        postnatalExercisePerson = txtViewPExercisePerson.getText().toString();
                        if(txtViewPExerciseDate.getText().toString().equals(getResources().getString(R.string.secN_Date)))
                        {
                            checkDateSelectExercise = false;
                            txtViewPExerciseDate.requestFocus();
                            txtViewPExerciseDate.setError("This field is required");
                        }else{
                            checkDateSelectExercise = true;
                            try {
                                postnatalExerciseDate = new SimpleDateFormat("dd/MM/yyyy").parse(txtViewPExerciseDate.getText().toString());
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                        }
                    }else{
                        postnatalExerciseStatus = "No";
                        postnatalExercisePerson = txtViewPExercisePerson.getText().toString();
                        postnatalExerciseDate = null;
                    }

                    if(checkBoxPHygiene.isChecked())
                    {
                        hygieneStatus = "Yes";
                        hygienePerson = txtViewPHygienePerson.getText().toString();
                        if(txtViewPHygieneDate.getText().toString().equals(getResources().getString(R.string.secN_Date)))
                        {
                            checkDateSelectHygiene = false;
                            txtViewPHygieneDate.requestFocus();
                            txtViewPHygieneDate.setError("This field is required");
                        }else {
                            checkDateSelectHygiene = true;
                            try {
                                hygieneDate = new SimpleDateFormat("dd/MM/yyyy").parse(txtViewPHygieneDate.getText().toString());
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                        }
                    }else{
                        hygieneStatus = "No";
                        hygienePerson = txtViewPHygienePerson.getText().toString();
                        hygieneDate = null;
                    }

                    if(checkBoxPEarlyTreatment.isChecked())
                    {
                        earlyTreatmentStatus = "Yes";
                        earlyTreatmentPerson = txtViewPEarlyTreatmentPerson.getText().toString();
                        if(txtViewPEarlyTreatmentDate.getText().toString().equals(getResources().getString(R.string.secN_Date)))
                        {
                            checkDateSelectEarlyTreatment = false;
                            txtViewPEarlyTreatmentDate.requestFocus();
                            txtViewPEarlyTreatmentDate.setError("This field is required");
                        }else{
                            checkDateSelectEarlyTreatment = true;
                            try {
                                earlyTreatmentDate = new SimpleDateFormat("dd/MM/yyyy").parse(txtViewPEarlyTreatmentDate.getText().toString());
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                        }
                    }else{
                        earlyTreatmentStatus = "No";
                        earlyTreatmentPerson = txtViewPEarlyTreatmentPerson.getText().toString();
                        earlyTreatmentDate = null;
                    }
                    Log.i("Testing", checkDateSelectMeal+"");
                    if(checkDateSelectMeal==true && checkDateSelectExercise==true && checkDateSelectHygiene==true && checkDateSelectEarlyTreatment==true)
                    {
                        PostnatalTutorial pt = new PostnatalTutorial(mealNutritionStatus, mealNutritionDate, mealNutritionPerson, postnatalExerciseStatus, postnatalExerciseDate,
                                postnatalExercisePerson, hygieneStatus, hygieneDate, hygienePerson, earlyTreatmentStatus, earlyTreatmentDate, earlyTreatmentPerson);

                        nCollectionReference.add(pt).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {
                                AlertDialog.Builder builder = new AlertDialog.Builder(PostnatalActivity.this);
                                builder.setTitle("Save Successfully");
                                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        Intent intent = new Intent(PostnatalActivity.this, SectionNActivity.class);
                                        startActivity(intent);
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
                        btnPostnatalCancel.setVisibility(View.VISIBLE);
                    }else if(check == 2)
                    {
                        boolean checkDateSelectMeal = true, checkDateSelectExercise = true, checkDateSelectHygiene = true, checkDateSelectEarlyTreatment = true;
                        DocumentReference nDocumentReference = mFirebaseFirestroe.collection("MommyHealthInfo").document(healthInfoId).collection("PostnatalTutorial").document(key);
                        String mealNutritionStatus = "", postnatalExerciseStatus = "", hygieneStatus = "", earlyTreatmentStatus = "";
                        String mealNutritionPerson = "", postnatalExercisePerson = "", hygienePerson = "", earlyTreatmentPerson = "";
                        Date mealNutritionDate = null, postnatalExerciseDate = null, hygieneDate = null, earlyTreatmentDate = null;

                        if(checkBoxPostnatalMeal.isChecked())
                        {
                            mealNutritionStatus = "Yes";
                            mealNutritionPerson = txtPostnatalMealPerson.getText().toString();
                            if(txtViewPostnatalMealDate.getText().toString().equals(getResources().getString(R.string.secN_Date)))
                            {
                                checkDateSelectMeal = false;
                                txtViewPostnatalMealDate.requestFocus();
                                txtViewPostnatalMealDate.setError("This field is required");
                            }else{
                                checkDateSelectMeal = true;
                                try {
                                    mealNutritionDate = new SimpleDateFormat("dd/MM/yyyy").parse(txtViewPostnatalMealDate.getText().toString());
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }
                            }
                        }else{
                            mealNutritionStatus = "No";
                            mealNutritionPerson = txtPostnatalMealPerson.getText().toString();
                            mealNutritionDate = null;
                        }

                        if(checkBoxPostnatalExercise.isChecked())
                        {
                            postnatalExerciseStatus = "Yes";
                            postnatalExercisePerson = txtViewPExercisePerson.getText().toString();
                            if(txtViewPExerciseDate.getText().toString().equals(getResources().getString(R.string.secN_Date)))
                            {
                                checkDateSelectExercise = false;
                                txtViewPExerciseDate.requestFocus();
                                txtViewPExerciseDate.setError("This field is required");
                            }else{
                                checkDateSelectExercise = true;
                                try {
                                    postnatalExerciseDate = new SimpleDateFormat("dd/MM/yyyy").parse(txtViewPExerciseDate.getText().toString());
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }
                            }
                        }else{
                            postnatalExerciseStatus = "No";
                            postnatalExercisePerson = txtViewPExercisePerson.getText().toString();
                            postnatalExerciseDate = null;
                        }

                        if(checkBoxPHygiene.isChecked())
                        {
                            hygieneStatus = "Yes";
                            hygienePerson = txtViewPHygienePerson.getText().toString();
                            if(txtViewPHygieneDate.getText().toString().equals(getResources().getString(R.string.secN_Date)))
                            {
                                checkDateSelectHygiene = false;
                                txtViewPHygieneDate.requestFocus();
                                txtViewPHygieneDate.setError("This field is required");
                            }else {
                                checkDateSelectHygiene = true;
                                try {
                                    hygieneDate = new SimpleDateFormat("dd/MM/yyyy").parse(txtViewPHygieneDate.getText().toString());
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }
                            }
                        }else{
                            hygieneStatus = "No";
                            hygienePerson = txtViewPHygienePerson.getText().toString();
                            hygieneDate = null;
                        }

                        if(checkBoxPEarlyTreatment.isChecked())
                        {
                            earlyTreatmentStatus = "Yes";
                            earlyTreatmentPerson = txtViewPEarlyTreatmentPerson.getText().toString();
                            if(txtViewPEarlyTreatmentDate.getText().toString().equals(getResources().getString(R.string.secN_Date)))
                            {
                                checkDateSelectEarlyTreatment = false;
                                txtViewPEarlyTreatmentDate.requestFocus();
                                txtViewPEarlyTreatmentDate.setError("This field is required");
                            }else{
                                checkDateSelectEarlyTreatment = true;
                                try {
                                    earlyTreatmentDate = new SimpleDateFormat("dd/MM/yyyy").parse(txtViewPEarlyTreatmentDate.getText().toString());
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }
                            }
                        }else{
                            earlyTreatmentStatus = "No";
                            earlyTreatmentPerson = txtViewPEarlyTreatmentPerson.getText().toString();
                            earlyTreatmentDate = null;
                        }

                        if(checkDateSelectMeal==true && checkDateSelectExercise==true && checkDateSelectHygiene==true && checkDateSelectEarlyTreatment==true)
                        {
                            nDocumentReference.update("mealNutritionStatus", mealNutritionStatus);
                            nDocumentReference.update("mealNutritionDate", mealNutritionDate);
                            nDocumentReference.update("mealNutritionPerson", mealNutritionPerson);
                            nDocumentReference.update("postnatalExerciseStatus", postnatalExerciseStatus);
                            nDocumentReference.update("postnatalExerciseDate", postnatalExerciseDate);
                            nDocumentReference.update("postnatalExercisePerson", postnatalExercisePerson);
                            nDocumentReference.update("hygieneStatus", hygieneStatus);
                            nDocumentReference.update("hygieneDate", hygieneDate);
                            nDocumentReference.update("hygienePerson", hygienePerson);
                            nDocumentReference.update("earlyTreatmentStatus", earlyTreatmentStatus);
                            nDocumentReference.update("earlyTreatmentDate", earlyTreatmentDate);
                            nDocumentReference.update("earlyTreatmentPerson", earlyTreatmentPerson);
                            Snackbar snackbar = Snackbar.make(relativeLayoutPostnatalTutorial, "Updated Successfully!", Snackbar.LENGTH_LONG);
                            snackbar.show();
                            DisableField();
                            btnPostnatalCancel.setVisibility(View.GONE);
                            check = 0;
                        }else{
                            check = 1;
                        }
                    }
                }
            }
        });

        btnPostnatalCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DisableField();
                btnPostnatalCancel.setVisibility(View.GONE);
                check = 0;
            }
        });

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void DisableField()
    {
        for(int i = 0; i<layoutPostnatal.getChildCount(); i++)
        {
            View view = layoutPostnatal.getChildAt(i);
            if(view instanceof RelativeLayout){
                for(int n = 0; n<((RelativeLayout) view).getChildCount(); n++)
                {
                    View v = ((RelativeLayout) view).getChildAt(n);
                    if(v instanceof CheckBox)
                    {
                        ((CheckBox)v).setEnabled(false);
                    }
                }
            }
        }
    }

    private void EnableField()
    {
        for(int i = 0; i<layoutPostnatal.getChildCount(); i++)
        {
            View view = layoutPostnatal.getChildAt(i);
            if(view instanceof RelativeLayout){
                for(int n = 0; n<((RelativeLayout) view).getChildCount(); n++)
                {
                    View v = ((RelativeLayout) view).getChildAt(n);
                    if(v instanceof CheckBox)
                    {
                        ((CheckBox)v).setEnabled(true);
                    }
                }
            }
        }
    }

    private void ViewGoneDateText()
    {
        txtViewPostnatalMealDate.setVisibility(View.GONE);
        txtPostnatalMealPerson.setVisibility(View.GONE);
        txtViewPExerciseDate.setVisibility(View.GONE);
        txtViewPExercisePerson.setVisibility(View.GONE);
        txtViewPHygieneDate.setVisibility(View.GONE);
        txtViewPHygienePerson.setVisibility(View.GONE);
        txtViewPEarlyTreatmentDate.setVisibility(View.GONE);
        txtViewPEarlyTreatmentPerson.setVisibility(View.GONE);
    }

    private void DateTextView()
    {
        txtViewPostnatalMealDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR); // current year
                int mMonth = c.get(Calendar.MONTH); // current month
                int mDay = c.get(Calendar.DAY_OF_MONTH); // current day
                datePickerDialog = new DatePickerDialog(PostnatalActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                txtViewPostnatalMealDate.setText(dayOfMonth + "/" +(month + 1) + "/" + year);
                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });
        txtViewPExerciseDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR); // current year
                int mMonth = c.get(Calendar.MONTH); // current month
                int mDay = c.get(Calendar.DAY_OF_MONTH); // current day
                datePickerDialog = new DatePickerDialog(PostnatalActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                txtViewPExerciseDate.setText(dayOfMonth + "/" +(month + 1) + "/" + year);
                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });
        txtViewPHygieneDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR); // current year
                int mMonth = c.get(Calendar.MONTH); // current month
                int mDay = c.get(Calendar.DAY_OF_MONTH); // current day
                datePickerDialog = new DatePickerDialog(PostnatalActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                txtViewPHygieneDate.setText(dayOfMonth + "/" +(month + 1) + "/" + year);
                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });
        txtViewPEarlyTreatmentDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR); // current year
                int mMonth = c.get(Calendar.MONTH); // current month
                int mDay = c.get(Calendar.DAY_OF_MONTH); // current day
                datePickerDialog = new DatePickerDialog(PostnatalActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                txtViewPEarlyTreatmentDate.setText(dayOfMonth + "/" +(month + 1) + "/" + year);
                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });
    }

    private void CheckBoxIsCheck()
    {
        checkBoxPostnatalMeal.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                {
                    txtViewPostnatalMealDate.setVisibility(View.VISIBLE);
                    txtPostnatalMealPerson.setVisibility(View.VISIBLE);
                    txtPostnatalMealPerson.setText(medicalPersonName);
                }else{
                    txtViewPostnatalMealDate.setVisibility(View.GONE);
                    txtPostnatalMealPerson.setVisibility(View.GONE);
                    txtPostnatalMealPerson.setText(R.string.secN_medicalPersonnale_name);
                }
            }
        });

        checkBoxPostnatalExercise.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                {
                    txtViewPExerciseDate.setVisibility(View.VISIBLE);
                    txtViewPExercisePerson.setVisibility(View.VISIBLE);
                    txtViewPExercisePerson.setText(medicalPersonName);
                }else{
                    txtViewPExerciseDate.setVisibility(View.GONE);
                    txtViewPExercisePerson.setVisibility(View.GONE);
                    txtViewPExercisePerson.setText(R.string.secN_medicalPersonnale_name);
                }
            }
        });

        checkBoxPHygiene.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                {
                    txtViewPHygieneDate.setVisibility(View.VISIBLE);
                    txtViewPHygienePerson.setVisibility(View.VISIBLE);
                    txtViewPHygienePerson.setText(medicalPersonName);
                }else{
                    txtViewPHygieneDate.setVisibility(View.GONE);
                    txtViewPHygienePerson.setVisibility(View.GONE);
                    txtViewPHygienePerson.setText(R.string.secN_medicalPersonnale_name);
                }
            }
        });

        checkBoxPEarlyTreatment.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                {
                    txtViewPEarlyTreatmentDate.setVisibility(View.VISIBLE);
                    txtViewPEarlyTreatmentPerson.setVisibility(View.VISIBLE);
                    txtViewPEarlyTreatmentPerson.setText(medicalPersonName);
                }else{
                    txtViewPEarlyTreatmentDate.setVisibility(View.GONE);
                    txtViewPEarlyTreatmentPerson.setVisibility(View.GONE);
                    txtViewPEarlyTreatmentPerson.setText(R.string.secN_medicalPersonnale_name);
                }
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
