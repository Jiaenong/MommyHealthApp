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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.example.mommyhealthapp.Class.CurrentHealthStatus;
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

import org.w3c.dom.Text;

import java.util.Date;

public class SectionDActivity extends AppCompatActivity {
    private EditText editTextHeightCurrent, editTextCurrentBMI, editTextCurrentThyroid, editTextCurrentJaundice, editTextCurrentPallor, editTextCLeftBreast,
            editTextCRightBreast, editTextCVVLeft, editTextCVVRight, editTextCAbdomen, editTextCurrentOthers;
    private TextInputLayout layoutHeightCurrent, layoutCurrentBMI, layoutCurrentThyroid, layoutCurrentJaundice, layoutCLeftBreast, layoutCRightBreast,
            layoutCVVLeft, layoutCVVRight;
    private RelativeLayout relativeLayout;
    private LinearLayoutCompat layoutCHS;
    private ProgressBar progressBarCurrentHealthStatus;
    private Button btnCurrentSave, btnCurrentCancel;

    private String healthInfoId, bloodTestId, key;
    private Boolean isEmpty;
    private int check = 0;

    private FirebaseFirestore mFirebaseFirestore;
    private CollectionReference mCollectionReference;
    private DocumentReference mDocumentReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_section_d);

        editTextHeightCurrent = (EditText)findViewById(R.id.editTextHeightCurrent);
        editTextCurrentBMI = (EditText)findViewById(R.id.editTextCurrentBMI);
        editTextCurrentThyroid = (EditText)findViewById(R.id.editTextCurrentThyroid);
        editTextCurrentJaundice = (EditText)findViewById(R.id.editTextCurrentJaundice);
        editTextCurrentPallor = (EditText)findViewById(R.id.editTextCurrentPallor);
        editTextCLeftBreast = (EditText)findViewById(R.id.editTextCLeftBreast);
        editTextCRightBreast = (EditText)findViewById(R.id.editTextCRightBreast);
        editTextCVVLeft = (EditText)findViewById(R.id.editTextCVVLeft);
        editTextCVVRight = (EditText)findViewById(R.id.editTextCVVRight);
        editTextCAbdomen = (EditText)findViewById(R.id.editTextCAbdomen);
        editTextCurrentOthers = (EditText)findViewById(R.id.editTextCurrentOthers);
        layoutHeightCurrent = (TextInputLayout) findViewById(R.id.layoutHeightCurrent);
        layoutCurrentBMI = (TextInputLayout)findViewById(R.id.layoutCurrentBMI);
        layoutCurrentThyroid = (TextInputLayout)findViewById(R.id.layoutCurrentThyroid);
        layoutCurrentJaundice = (TextInputLayout)findViewById(R.id.layoutCurrentJaundice);
        layoutCLeftBreast = (TextInputLayout)findViewById(R.id.layoutCLeftBreast);
        layoutCRightBreast = (TextInputLayout)findViewById(R.id.layoutCRightBreast);
        layoutCVVLeft = (TextInputLayout)findViewById(R.id.layoutCVVLeft);
        layoutCVVRight = (TextInputLayout)findViewById(R.id.layoutCVVRight);
        relativeLayout = (RelativeLayout)findViewById(R.id.relativeLayout);
        layoutCHS = (LinearLayoutCompat)findViewById(R.id.layoutCHS);
        progressBarCurrentHealthStatus = (ProgressBar)findViewById(R.id.progressBarCurrentHealthStatus);
        btnCurrentSave = (Button)findViewById(R.id.btnCurrentSave);
        btnCurrentCancel = (Button)findViewById(R.id.btnCurrentCancel);

        btnCurrentCancel.setVisibility(View.GONE);
        layoutCHS.setVisibility(View.GONE);
        progressBarCurrentHealthStatus.setVisibility(View.VISIBLE);

        Intent intent = getIntent();
        healthInfoId = intent.getStringExtra("healthInfoId");
        bloodTestId = intent.getStringExtra("bloodTestId");

        CheckRequiredField();

        mFirebaseFirestore = FirebaseFirestore.getInstance();
        mCollectionReference = mFirebaseFirestore.collection("MommyHealthInfo/"+healthInfoId+"/BloodTest/"+bloodTestId+"/CurrentHealthStatus");
        if (SaveSharedPreference.getUser(SectionDActivity.this).equals("Mommy")){
            MommyLogIn();
        }
        else{
            mCollectionReference.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                @Override
                public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                    if(queryDocumentSnapshots.isEmpty())
                    {
                        isEmpty = true;
                        layoutCHS.setVisibility(View.VISIBLE);
                        progressBarCurrentHealthStatus.setVisibility(View.GONE);
                    }else{
                        isEmpty = false;
                        DisableField();
                        for(QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots)
                        {
                            key = documentSnapshot.getId();
                            CurrentHealthStatus chs = documentSnapshot.toObject(CurrentHealthStatus.class);
                            editTextHeightCurrent.setText(chs.getCurrentHeight()+"");
                            editTextCurrentBMI.setText(chs.getCurrentBMI()+"");
                            editTextCurrentThyroid.setText(chs.getCurrentThyroid());
                            editTextCurrentJaundice.setText(chs.getCurrentJaundice());
                            editTextCurrentPallor.setText(chs.getCurrentPallor());
                            editTextCLeftBreast.setText(chs.getCurrentLeftBreast());
                            editTextCRightBreast.setText(chs.getCurrentRightBreast());
                            editTextCVVLeft.setText(chs.getCurrentLeftVein());
                            editTextCVVRight.setText(chs.getCurrentRightVein());
                            editTextCAbdomen.setText(chs.getCurrentAbdomen());
                            editTextCurrentOthers.setText(chs.getCurrentOthers());
                            layoutCHS.setVisibility(View.VISIBLE);
                            progressBarCurrentHealthStatus.setVisibility(View.GONE);
                            btnCurrentSave.setText("Update");
                        }
                    }
                }
            });
        }

        btnCurrentSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isEmpty == true)
                {
                    if(CheckRequiredOnClick() == false)
                    {
                        Double currentHeight = Double.parseDouble(editTextHeightCurrent.getText().toString());
                        Double currentBMI = Double.parseDouble(editTextCurrentBMI.getText().toString());
                        String currentThyroid = editTextCurrentThyroid.getText().toString();
                        String currentJaundice = editTextCurrentJaundice.getText().toString();
                        String currentPallor = editTextCurrentPallor.getText().toString();
                        String currentLeftBreast = editTextCLeftBreast.getText().toString();
                        String currentRightBreast = editTextCRightBreast.getText().toString();
                        String currentLeftVein = editTextCVVLeft.getText().toString();
                        String currentRightVein = editTextCVVRight.getText().toString();
                        String currentAbdomen = editTextCAbdomen.getText().toString();
                        String currentOthers = editTextCurrentOthers.getText().toString();
                        String medicalPersonnelId = SaveSharedPreference.getID(SectionDActivity.this);
                        Date today = new Date();

                        CurrentHealthStatus chs = new CurrentHealthStatus(currentHeight, currentBMI, currentThyroid, currentJaundice, currentPallor, currentLeftBreast, currentRightBreast, currentLeftVein, currentRightVein,currentAbdomen, currentOthers, medicalPersonnelId, today);

                        mCollectionReference.add(chs).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {
                                AlertDialog.Builder builder = new AlertDialog.Builder(SectionDActivity.this);
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
                        btnCurrentCancel.setVisibility(View.VISIBLE);
                    }else if(check == 2)
                    {
                        if(CheckRequiredOnClick() == false)
                        {
                            Date today = new Date();
                            mDocumentReference = mFirebaseFirestore.document("MommyHealthInfo/"+healthInfoId+"/BloodTest/"+bloodTestId+"/CurrentHealthStatus/"+key);
                            mDocumentReference.update("currentHeight", Double.parseDouble(editTextHeightCurrent.getText().toString()));
                            mDocumentReference.update("currentBMI", Double.parseDouble(editTextCurrentBMI.getText().toString()));
                            mDocumentReference.update("currentThyroid", editTextCurrentThyroid.getText().toString());
                            mDocumentReference.update("currentJaundice", editTextCurrentJaundice.getText().toString());
                            mDocumentReference.update("currentPallor", editTextCurrentPallor.getText().toString());
                            mDocumentReference.update("currentLeftBreast", editTextCLeftBreast.getText().toString());
                            mDocumentReference.update("currentRightBreast", editTextCRightBreast.getText().toString());
                            mDocumentReference.update("currentLeftVein", editTextCVVLeft.getText().toString());
                            mDocumentReference.update("currentRightVein", editTextCVVRight.getText().toString());
                            mDocumentReference.update("currentAbdomen", editTextCAbdomen.getText().toString());
                            mDocumentReference.update("currentOthers", editTextCurrentOthers.getText().toString());
                            mDocumentReference.update("medicalPersonnelId", SaveSharedPreference.getID(SectionDActivity.this));
                            mDocumentReference.update("createdDate", today);
                            btnCurrentCancel.setVisibility(View.GONE);
                            Snackbar snackbar = Snackbar.make(relativeLayout, "Updated Successfully!", Snackbar.LENGTH_LONG);
                            snackbar.show();
                            check = 0;
                            DisableField();
                        }else{
                            check = 1;
                        }
                    }

                }
            }
        });

        btnCurrentCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DisableField();
                btnCurrentCancel.setVisibility(View.GONE);
                check = 0;
            }
        });

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void MommyLogIn()
    {
        mCollectionReference.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                if(queryDocumentSnapshots.isEmpty())
                {
                    isEmpty = true;
                    layoutCHS.setVisibility(View.VISIBLE);
                    progressBarCurrentHealthStatus.setVisibility(View.GONE);
                    btnCurrentCancel.setVisibility(View.GONE);
                    btnCurrentSave.setVisibility(View.GONE);
                    DisableField();
                }else{
                    isEmpty = false;
                    DisableField();
                    for(QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots)
                    {
                        key = documentSnapshot.getId();
                        CurrentHealthStatus chs = documentSnapshot.toObject(CurrentHealthStatus.class);
                        editTextHeightCurrent.setText(chs.getCurrentHeight()+"");
                        editTextCurrentBMI.setText(chs.getCurrentBMI()+"");
                        editTextCurrentThyroid.setText(chs.getCurrentThyroid());
                        editTextCurrentJaundice.setText(chs.getCurrentJaundice());
                        editTextCurrentPallor.setText(chs.getCurrentPallor());
                        editTextCLeftBreast.setText(chs.getCurrentLeftBreast());
                        editTextCRightBreast.setText(chs.getCurrentRightBreast());
                        editTextCVVLeft.setText(chs.getCurrentLeftVein());
                        editTextCVVRight.setText(chs.getCurrentRightVein());
                        editTextCAbdomen.setText(chs.getCurrentAbdomen());
                        editTextCurrentOthers.setText(chs.getCurrentOthers());
                        layoutCHS.setVisibility(View.VISIBLE);
                        progressBarCurrentHealthStatus.setVisibility(View.GONE);
                        btnCurrentSave.setVisibility(View.GONE);
                        btnCurrentCancel.setVisibility(View.GONE);
                    }
                }
            }
        });
    }

    private boolean CheckRequiredOnClick()
    {
        if(editTextHeightCurrent.getText().toString().isEmpty() || editTextCurrentBMI.getText().toString().isEmpty() || editTextCurrentThyroid.getText().toString().isEmpty()
                || editTextCurrentJaundice.getText().toString().isEmpty() || editTextCLeftBreast.getText().toString().isEmpty() || editTextCRightBreast.getText().toString().isEmpty()
                || editTextCVVLeft.getText().toString().isEmpty() || editTextCVVRight.getText().toString().isEmpty())
        {
            if(editTextHeightCurrent.getText().toString().isEmpty())
            {
                layoutHeightCurrent.setErrorEnabled(true);
                layoutHeightCurrent.setError("This field is required!");
            }else{
                layoutHeightCurrent.setErrorEnabled(false);
                layoutHeightCurrent.setError(null);
            }

            if(editTextCurrentBMI.getText().toString().isEmpty())
            {
                layoutCurrentBMI.setErrorEnabled(true);
                layoutCurrentBMI.setError("This field is required!");
            }else{
                layoutCurrentBMI.setErrorEnabled(false);
                layoutCurrentBMI.setError(null);
            }

            if(editTextCurrentThyroid.getText().toString().isEmpty())
            {
                layoutCurrentThyroid.setErrorEnabled(true);
                layoutCurrentThyroid.setError("This field is required!");
            }else{
                layoutCurrentThyroid.setErrorEnabled(false);
                layoutCurrentThyroid.setError(null);
            }

            if(editTextCurrentJaundice.getText().toString().isEmpty())
            {
                layoutCurrentJaundice.setErrorEnabled(true);
                layoutCurrentJaundice.setError("This field is required!");
            }else{
                layoutCurrentJaundice.setErrorEnabled(false);
                layoutCurrentJaundice.setError(null);
            }

            if(editTextCLeftBreast.getText().toString().isEmpty())
            {
                layoutCLeftBreast.setErrorEnabled(true);
                layoutCLeftBreast.setError("This field is required!");
            }else{
                layoutCLeftBreast.setErrorEnabled(false);
                layoutCLeftBreast.setError(null);
            }

            if(editTextCRightBreast.getText().toString().isEmpty())
            {
                layoutCRightBreast.setErrorEnabled(true);
                layoutCRightBreast.setError("This field is required!");
            }else{
                layoutCRightBreast.setErrorEnabled(false);
                layoutCRightBreast.setError(null);
            }

            if(editTextCVVLeft.getText().toString().isEmpty())
            {
                layoutCVVLeft.setErrorEnabled(true);
                editTextCVVLeft.setError("This field is required!");
            }else{
                layoutCVVLeft.setErrorEnabled(false);
                editTextCVVLeft.setError(null);
            }

            if(editTextCVVRight.getText().toString().isEmpty())
            {
                layoutCVVRight.setErrorEnabled(true);
                layoutCVVRight.setError("This field is required!");
            }else{
                layoutCVVRight.setErrorEnabled(false);
                layoutCVVRight.setError(null);
            }
            return true;
        }else{
            return false;
        }
    }

    private void CheckRequiredField()
    {
        editTextHeightCurrent.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(editTextHeightCurrent.getText().toString().isEmpty())
                {
                    layoutHeightCurrent.setErrorEnabled(true);
                    layoutHeightCurrent.setError("This field is required!");
                }else{
                    layoutHeightCurrent.setErrorEnabled(false);
                    layoutHeightCurrent.setError(null);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        editTextCurrentBMI.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(editTextCurrentBMI.getText().toString().isEmpty())
                {
                    layoutCurrentBMI.setErrorEnabled(true);
                    layoutCurrentBMI.setError("This field is required!");
                }else{
                    layoutCurrentBMI.setErrorEnabled(false);
                    layoutCurrentBMI.setError(null);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        editTextCurrentThyroid.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(editTextCurrentThyroid.getText().toString().isEmpty())
                {
                    layoutCurrentThyroid.setErrorEnabled(true);
                    layoutCurrentThyroid.setError("This field is required!");
                }else{
                    layoutCurrentThyroid.setErrorEnabled(false);
                    layoutCurrentThyroid.setError(null);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        editTextCurrentJaundice.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(editTextCurrentJaundice.getText().toString().isEmpty())
                {
                    layoutCurrentJaundice.setErrorEnabled(true);
                    layoutCurrentJaundice.setError("This field is required!");
                }else{
                    layoutCurrentJaundice.setErrorEnabled(false);
                    layoutCurrentJaundice.setError(null);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        editTextCLeftBreast.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(editTextCLeftBreast.getText().toString().isEmpty())
                {
                    layoutCLeftBreast.setErrorEnabled(true);
                    layoutCLeftBreast.setError("This field is required!");
                }else{
                    layoutCLeftBreast.setErrorEnabled(false);
                    layoutCLeftBreast.setError(null);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        editTextCRightBreast.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(editTextCRightBreast.getText().toString().isEmpty())
                {
                    layoutCRightBreast.setErrorEnabled(true);
                    layoutCRightBreast.setError("This field is required!");
                }else{
                    layoutCRightBreast.setErrorEnabled(false);
                    layoutCRightBreast.setError(null);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        editTextCVVLeft.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(editTextCVVLeft.getText().toString().isEmpty())
                {
                    layoutCVVLeft.setErrorEnabled(true);
                    editTextCVVLeft.setError("This field is required!");
                }else{
                    layoutCVVLeft.setErrorEnabled(false);
                    editTextCVVLeft.setError(null);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        editTextCVVRight.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(editTextCVVRight.getText().toString().isEmpty())
                {
                    layoutCVVRight.setErrorEnabled(true);
                    layoutCVVRight.setError("This field is required!");
                }else{
                    layoutCVVRight.setErrorEnabled(false);
                    layoutCVVRight.setError(null);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void DisableField()
    {
        editTextHeightCurrent.setEnabled(false);
        editTextHeightCurrent.setTextColor(Color.parseColor("#000000"));
        editTextCurrentBMI.setEnabled(false);
        editTextCurrentBMI.setTextColor(Color.parseColor("#000000"));
        editTextCurrentThyroid.setEnabled(false);
        editTextCurrentThyroid.setTextColor(Color.parseColor("#000000"));
        editTextCurrentJaundice.setEnabled(false);
        editTextCurrentJaundice.setTextColor(Color.parseColor("#000000"));
        editTextCurrentPallor.setEnabled(false);
        editTextCurrentPallor.setTextColor(Color.parseColor("#000000"));
        editTextCLeftBreast.setEnabled(false);
        editTextCLeftBreast.setTextColor(Color.parseColor("#000000"));
        editTextCRightBreast.setEnabled(false);
        editTextCRightBreast.setTextColor(Color.parseColor("#000000"));
        editTextCVVLeft.setEnabled(false);
        editTextCVVLeft.setTextColor(Color.parseColor("#000000"));
        editTextCVVRight.setEnabled(false);
        editTextCVVRight.setTextColor(Color.parseColor("#000000"));
        editTextCAbdomen.setEnabled(false);
        editTextCAbdomen.setTextColor(Color.parseColor("#000000"));
        editTextCurrentOthers.setEnabled(false);
        editTextCurrentOthers.setTextColor(Color.parseColor("#000000"));
    }

    private void EnableField()
    {
        editTextHeightCurrent.setEnabled(true);
        editTextCurrentBMI.setEnabled(true);
        editTextCurrentThyroid.setEnabled(true);
        editTextCurrentJaundice.setEnabled(true);
        editTextCurrentPallor.setEnabled(true);
        editTextCLeftBreast.setEnabled(true);
        editTextCRightBreast.setEnabled(true);
        editTextCVVLeft.setEnabled(true);
        editTextCVVRight.setEnabled(true);
        editTextCAbdomen.setEnabled(true);
        editTextCurrentOthers.setEnabled(true);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.action_logout:
                AlertDialog.Builder builder = new AlertDialog.Builder(SectionDActivity.this);
                builder.setTitle("Logout");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        SaveSharedPreference.clearUser(SectionDActivity.this);
                        Intent intent = new Intent(SectionDActivity.this, MainActivity.class);
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
