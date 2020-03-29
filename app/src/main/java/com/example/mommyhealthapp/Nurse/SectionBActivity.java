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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.mommyhealthapp.Class.ScreenTest;
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

public class SectionBActivity extends AppCompatActivity {
    private EditText editTextRBG, editTextTPHA, editTextRapidTest, editTextHepatitisB, editTextThalassaemia, editTextBFMP;
    private RadioGroup radioGroupTest1, radioGroupElizaTest;
    private RadioButton radioBtnNR, radioBtnR, radiobtnETPositive, radiobtnETNegative;
    private LinearLayoutCompat layoutScreenTest;
    private RelativeLayout relativeLayoutST;
    private ProgressBar progressBarST;
    private Button btnSTSave, btnSTCancel;

    private String healthInfoId, bloodTestId, radioGroup, key;
    private int check = 0;
    private Boolean isEmpty;

    private FirebaseFirestore mFirebaseFirestore;
    private CollectionReference mCollectionReference;
    private DocumentReference mDocumentReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_section_b);
        editTextRBG = (EditText)findViewById(R.id.editTextRBG);
        editTextTPHA = (EditText)findViewById(R.id.editTextTPHA);
        editTextHepatitisB = (EditText)findViewById(R.id.editTextHepatitisB);
        editTextThalassaemia = (EditText)findViewById(R.id.editTextThalassaemia);
        editTextBFMP = (EditText)findViewById(R.id.editTextBFMP);
        editTextRapidTest = (EditText)findViewById(R.id.editTextRapidTest);
        radioGroupTest1 = (RadioGroup)findViewById(R.id.radioGroupTest1);
        radioGroupElizaTest = (RadioGroup)findViewById(R.id.radioGroupElizaTest);
        radioBtnNR = (RadioButton)findViewById(R.id.radioBtnNR);
        radioBtnR = (RadioButton)findViewById(R.id.radioBtnR);
        radiobtnETPositive = (RadioButton)findViewById(R.id.radiobtnETPositive);
        radiobtnETNegative = (RadioButton)findViewById(R.id.radiobtnETNegative);
        progressBarST = (ProgressBar)findViewById(R.id.progressBarST);
        layoutScreenTest = (LinearLayoutCompat)findViewById(R.id.layoutScreenTest);
        relativeLayoutST = (RelativeLayout)findViewById(R.id.relativeLayoutST);
        btnSTSave = (Button)findViewById(R.id.btnSTSave);
        btnSTCancel = (Button)findViewById(R.id.btnSTCancel);

        layoutScreenTest.setVisibility(View.GONE);
        progressBarST.setVisibility(View.VISIBLE);

        Intent intent = getIntent();
        healthInfoId = intent.getStringExtra("healthInfoId");
        bloodTestId = intent.getStringExtra("bloodTestId");
        radioGroup = intent.getStringExtra("radioGroup");

        mFirebaseFirestore = FirebaseFirestore.getInstance();
        mCollectionReference = mFirebaseFirestore.collection("MommyHealthInfo/"+healthInfoId+"/BloodTest/"+bloodTestId+"/ScreenTest");
        if (SaveSharedPreference.getUser(SectionBActivity.this).equals("Mommy")){
            MommyLogIn();
        }
        else {
            mCollectionReference.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                if(queryDocumentSnapshots.isEmpty())
                {
                    isEmpty = true;
                    layoutScreenTest.setVisibility(View.VISIBLE);
                    progressBarST.setVisibility(View.GONE);
                }else{
                    isEmpty = false;
                    DisableField();
                    for(QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots)
                    {
                        key = documentSnapshot.getId();
                        ScreenTest st = documentSnapshot.toObject(ScreenTest.class);
                        editTextRBG.setText(st.getRhesusBloodGroup());
                        editTextTPHA.setText(st.getTpha());
                        for(int i=0; i<radioGroupTest1.getChildCount(); i++)
                        {
                            if(((RadioButton)radioGroupTest1.getChildAt(i)).getText().toString().equals(st.getRapidTest()))
                            {
                                ((RadioButton)radioGroupTest1.getChildAt(i)).setChecked(true);
                            }
                        }
                        for(int i=0; i<radioGroupElizaTest.getChildCount(); i++)
                        {
                            if(((RadioButton)radioGroupElizaTest.getChildAt(i)).getText().toString().equals(st.getRapidTest()))
                            {
                                ((RadioButton)radioGroupElizaTest.getChildAt(i)).setChecked(true);
                            }
                        }
                        editTextHepatitisB.setText(st.getHepatitisB());
                        editTextThalassaemia.setText(st.getThalassaemia());
                        editTextBFMP.setText(st.getBfmp());
                        layoutScreenTest.setVisibility(View.VISIBLE);
                        progressBarST.setVisibility(View.GONE);
                        btnSTSave.setText("Update");
                    }
                }
            }
            });
        }


        radioGroupTest1.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                radiobtnETPositive.setChecked(false);
                radiobtnETNegative.setChecked(false);
                if(radioBtnNR.isChecked())
                {
                    editTextRapidTest.setText(radioBtnNR.getText().toString());
                }else{
                    editTextRapidTest.setText(radioBtnR.getText().toString());
                }
            }
        });

        radioGroupElizaTest.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                radioBtnNR.setChecked(false);
                radioBtnR.setChecked(false);
                if(radiobtnETPositive.isChecked())
                {
                    editTextRapidTest.setText(radiobtnETPositive.getText().toString());
                }else{
                    editTextRapidTest.setText(radiobtnETNegative.getText().toString());
                }
            }
        });

        editTextRBG.setText(radioGroup);
        btnSTCancel.setVisibility(View.GONE);

        btnSTSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isEmpty == true)
                {
                    String rbg = editTextRBG.getText().toString();
                    String tpha = editTextTPHA.getText().toString();
                    String rapidTest = editTextRapidTest.getText().toString();
                    String hepatitisB = editTextHepatitisB.getText().toString();
                    String thalassaemia = editTextThalassaemia.getText().toString();
                    String bfmp = editTextBFMP.getText().toString();
                    Date today = new Date();
                    String medicalPersonnelId = SaveSharedPreference.getID(SectionBActivity.this);

                    ScreenTest st = new ScreenTest(rbg, tpha, rapidTest, hepatitisB, thalassaemia, bfmp, today, medicalPersonnelId);
                    mCollectionReference.add(st).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                            AlertDialog.Builder builder = new AlertDialog.Builder(SectionBActivity.this);
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
                }else if(isEmpty == false)
                {
                    check++;
                    if(check == 1)
                    {
                        btnSTCancel.setVisibility(View.VISIBLE);
                        EnableField();
                    }else if(check == 2)
                    {
                        Date today = new Date();
                        mDocumentReference = mFirebaseFirestore.document("MommyHealthInfo/"+healthInfoId+"/BloodTest/"+bloodTestId+"/ScreenTest/"+key);
                        mDocumentReference.update("rhesusBloodGroup", editTextRBG.getText().toString());
                        mDocumentReference.update("tpha", editTextTPHA.getText().toString());
                        mDocumentReference.update("rapidTest", editTextRapidTest.getText().toString());
                        mDocumentReference.update("hepatitisB", editTextHepatitisB.getText().toString());
                        mDocumentReference.update("thalassaemia", editTextThalassaemia.getText().toString());
                        mDocumentReference.update("bfmp", editTextBFMP.getText().toString());
                        mDocumentReference.update("createdDate", today);
                        mDocumentReference.update("medicalPersonnelId", SaveSharedPreference.getID(SectionBActivity.this));
                        Snackbar snackbar = Snackbar.make(relativeLayoutST, "Updated Successfully!", Snackbar.LENGTH_LONG);
                        snackbar.show();
                        btnSTCancel.setVisibility(View.GONE);
                        DisableField();
                        check = 0;
                    }
                }
            }
        });

        btnSTCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EnableField();
                btnSTCancel.setVisibility(View.GONE);
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
                    layoutScreenTest.setVisibility(View.VISIBLE);
                    progressBarST.setVisibility(View.GONE);
                    btnSTCancel.setVisibility(View.GONE);
                    btnSTSave.setVisibility(View.GONE);
                    DisableField();
                }else{
                    isEmpty = false;
                    DisableField();
                    for(QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots)
                    {
                        key = documentSnapshot.getId();
                        ScreenTest st = documentSnapshot.toObject(ScreenTest.class);
                        editTextRBG.setText(st.getRhesusBloodGroup());
                        editTextTPHA.setText(st.getTpha());
                        for(int i=0; i<radioGroupTest1.getChildCount(); i++)
                        {
                            if(((RadioButton)radioGroupTest1.getChildAt(i)).getText().toString().equals(st.getRapidTest()))
                            {
                                ((RadioButton)radioGroupTest1.getChildAt(i)).setChecked(true);
                            }((RadioButton)radioGroupTest1.getChildAt(i)).setEnabled(false);
                        }
                        for(int i=0; i<radioGroupElizaTest.getChildCount(); i++)
                        {
                            if(((RadioButton)radioGroupElizaTest.getChildAt(i)).getText().toString().equals(st.getRapidTest()))
                            {
                                ((RadioButton)radioGroupElizaTest.getChildAt(i)).setChecked(true);
                            }((RadioButton)radioGroupElizaTest.getChildAt(i)).setEnabled(false);
                        }
                        editTextHepatitisB.setText(st.getHepatitisB());
                        editTextThalassaemia.setText(st.getThalassaemia());
                        editTextBFMP.setText(st.getBfmp());
                        layoutScreenTest.setVisibility(View.VISIBLE);
                        progressBarST.setVisibility(View.GONE);
                        btnSTSave.setVisibility(View.GONE);
                        btnSTCancel.setVisibility(View.GONE);
                    }
                }
            }
        });
    }
    private void DisableField()
    {
        editTextRBG.setEnabled(false);
        editTextRBG.setTextColor(Color.parseColor("#000000"));
        editTextTPHA.setEnabled(false);
        editTextTPHA.setTextColor(Color.parseColor("#000000"));
        editTextRapidTest.setEnabled(false);
        editTextRapidTest.setTextColor(Color.parseColor("#000000"));
        editTextHepatitisB.setEnabled(false);
        editTextHepatitisB.setTextColor(Color.parseColor("#000000"));
        editTextThalassaemia.setEnabled(false);
        editTextThalassaemia.setTextColor(Color.parseColor("#000000"));
        editTextBFMP.setEnabled(false);
        editTextBFMP.setTextColor(Color.parseColor("#000000"));
        for(int i=0; i<radioGroupTest1.getChildCount(); i++)
        {
            ((RadioButton)radioGroupTest1.getChildAt(i)).setEnabled(false);
        }
        for(int i=0; i<radioGroupElizaTest.getChildCount(); i++)
        {
            ((RadioButton)radioGroupElizaTest.getChildAt(i)).setEnabled(false);
        }
    }

    private void EnableField()
    {
        editTextRBG.setEnabled(true);
        editTextRBG.setTextColor(Color.parseColor("#000000"));
        editTextTPHA.setEnabled(true);
        editTextTPHA.setTextColor(Color.parseColor("#000000"));
        editTextRapidTest.setEnabled(true);
        editTextRapidTest.setTextColor(Color.parseColor("#000000"));
        editTextHepatitisB.setEnabled(true);
        editTextHepatitisB.setTextColor(Color.parseColor("#000000"));
        editTextThalassaemia.setEnabled(true);
        editTextThalassaemia.setTextColor(Color.parseColor("#000000"));
        editTextBFMP.setEnabled(true);
        editTextBFMP.setTextColor(Color.parseColor("#000000"));
        for(int i=0; i<radioGroupTest1.getChildCount(); i++)
        {
            ((RadioButton)radioGroupTest1.getChildAt(i)).setEnabled(true);
        }
        for(int i=0; i<radioGroupElizaTest.getChildCount(); i++)
        {
            ((RadioButton)radioGroupElizaTest.getChildAt(i)).setEnabled(true);
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
