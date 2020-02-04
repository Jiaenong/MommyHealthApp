package com.example.mommyhealthapp.Nurse;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.LinearLayoutCompat;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.example.mommyhealthapp.Class.CurrentHealthStatus;
import com.example.mommyhealthapp.R;
import com.example.mommyhealthapp.SaveSharedPreference;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Date;

public class SectionDActivity extends AppCompatActivity {
    private EditText editTextHeightCurrent, editTextCurrentBMI, editTextCurrentThyroid, editTextCurrentJaundice, editTextCurrentPallor, editTextCLeftBreast,
            editTextCRightBreast, editTextCVVLeft, editTextCVVRight, editTextCAbdomen, editTextCurrentOthers;
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

        mFirebaseFirestore = FirebaseFirestore.getInstance();
        mCollectionReference = mFirebaseFirestore.collection("MommyHealthInfo/"+healthInfoId+"/BloodTest/"+bloodTestId+"/CurrentHealthStatus");

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
                    }
                }
            }
        });


        btnCurrentSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isEmpty == true)
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
                                    Intent intent = new Intent(SectionDActivity.this, EarlyTestActivity.class);
                                    startActivity(intent);
                                }
                            });
                            builder.setMessage("Save Successful!");
                            AlertDialog alert = builder.create();
                            alert.setCanceledOnTouchOutside(false);
                            alert.show();
                        }
                    });
                }else{
                    check++;
                    if(check == 1)
                    {
                        EnableField();
                        btnCurrentCancel.setVisibility(View.VISIBLE);
                    }else if(check == 2)
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
