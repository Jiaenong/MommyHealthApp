package com.example.mommyhealthapp.Nurse;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.cardview.widget.CardView;
import androidx.navigation.Navigation;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.SectionIndexer;
import android.widget.Toast;

import com.example.mommyhealthapp.Class.BloodTest;
import com.example.mommyhealthapp.Class.MommyHealthInfo;
import com.example.mommyhealthapp.R;
import com.example.mommyhealthapp.Nurse.SectionAActivity;
import com.example.mommyhealthapp.SaveSharedPreference;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class EarlyTestActivity extends AppCompatActivity {

    private CardView cardViewSectionA, cardViewSectionB, cardViewSectionC, cardViewSectionD, cardViewBreastCheck, cardViewMentalExamination;
    private RadioGroup radioGroupBloodGroup, radioGroupRhd, radioGroupRPR;
    private RadioButton radioBtnBTA, radioBtnBTAB, radioBtnBTB, radioBtnBTO, radioBtnBTPositive, radioBtnBTNegative, radioBtnNonReactive, radioBtnReactive;
    private Button btnBTUpdate, btnBTCancel;
    private ProgressBar progressBarEarlyTest;
    private LinearLayoutCompat layoutEarlyTest;
    private RelativeLayout relativeLayoutBT;

    private FirebaseFirestore mFirebaseFirestore;
    private CollectionReference mCollectionReference, nCollectionReference;
    private DocumentReference mDocumentReference;

    private MommyHealthInfo mi;
    private String healthInfoId, bloodTestId, status;

    private int check = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_early_test);

        cardViewSectionA = (CardView)findViewById(R.id.cardViewSectionA);
        cardViewSectionB = (CardView)findViewById(R.id.cardViewSetionB);
        cardViewSectionC = (CardView)findViewById(R.id.cardViewSectionC);
        cardViewSectionD = (CardView)findViewById(R.id.cardViewSectionD);
        cardViewBreastCheck = (CardView)findViewById(R.id.cardViewBreastCheck);
        cardViewMentalExamination = (CardView)findViewById(R.id.cardViewMentalExamination);
        radioGroupBloodGroup = (RadioGroup)findViewById(R.id.radioGroupBloodGroup);
        radioGroupRhd = (RadioGroup)findViewById(R.id.radioGroupRhd);
        radioGroupRPR = (RadioGroup)findViewById(R.id.radioGroupRPR);
        radioBtnBTA = (RadioButton)findViewById(R.id.radioBtnBTA);
        radioBtnBTAB = (RadioButton)findViewById(R.id.radioBtnBTAB);
        radioBtnBTB = (RadioButton)findViewById(R.id.radioBtnBTB);
        radioBtnBTO = (RadioButton)findViewById(R.id.radioBtnBTO);
        radioBtnBTPositive = (RadioButton)findViewById(R.id.radioBtnBTPositive);
        radioBtnBTNegative = (RadioButton)findViewById(R.id.radioBtnBTNegative);
        radioBtnNonReactive = (RadioButton)findViewById(R.id.radioBtnNonReactive);
        radioBtnReactive = (RadioButton)findViewById(R.id.radioBtnReactive);
        btnBTUpdate = (Button)findViewById(R.id.btnBTUpdate);
        btnBTCancel = (Button)findViewById(R.id.btnBTCancel);
        progressBarEarlyTest = (ProgressBar)findViewById(R.id.progressBarEarlyTest);
        layoutEarlyTest = (LinearLayoutCompat)findViewById(R.id.layoutEarlyTest);
        relativeLayoutBT = (RelativeLayout)findViewById(R.id.relativeLayoutBT);

        btnBTCancel.setVisibility(View.GONE);

        Intent intent = getIntent();
        status = intent.getStringExtra("Status");
        mFirebaseFirestore = FirebaseFirestore.getInstance();
        mCollectionReference = mFirebaseFirestore.collection("MommyHealthInfo");

        if(SaveSharedPreference.getEarlyTest(EarlyTestActivity.this).equals("Old") || SaveSharedPreference.getUser(EarlyTestActivity.this).equals("Mommy"))
        {
            layoutEarlyTest.setVisibility(View.GONE);
            progressBarEarlyTest.setVisibility(View.VISIBLE);
            DisableRadioButton();
            if(SaveSharedPreference.getUser(EarlyTestActivity.this).equals("Mommy"))
            {
                MommyLogIn();
            }else{
                mCollectionReference.whereEqualTo("healthInfoId", SaveSharedPreference.getHealthInfoId(EarlyTestActivity.this)).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        for(final QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots)
                        {
                            healthInfoId = documentSnapshot.getId();
                        }
                        nCollectionReference = mFirebaseFirestore.collection("MommyHealthInfo").document(healthInfoId).collection("BloodTest");
                        nCollectionReference.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                            @Override
                            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                                for(QueryDocumentSnapshot documentSnapshots : queryDocumentSnapshots)
                                {
                                    bloodTestId = documentSnapshots.getId();
                                    BloodTest bt = documentSnapshots.toObject(BloodTest.class);
                                    for(int i=0; i<radioGroupBloodGroup.getChildCount(); i++)
                                    {
                                        if(((RadioButton)radioGroupBloodGroup.getChildAt(i)).getText().toString().equals(bt.getBloodGroup()))
                                        {
                                            ((RadioButton)radioGroupBloodGroup.getChildAt(i)).setChecked(true);
                                        }
                                    }
                                    for(int i=0; i<radioGroupRhd.getChildCount(); i++)
                                    {
                                        if(((RadioButton)radioGroupRhd.getChildAt(i)).getText().toString().equals(bt.getRhd()))
                                        {
                                            ((RadioButton)radioGroupRhd.getChildAt(i)).setChecked(true);
                                        }
                                    }
                                    for(int i=0; i<radioGroupRPR.getChildCount(); i++)
                                    {
                                        if(((RadioButton)radioGroupRPR.getChildAt(i)).getText().toString().equals(bt.getRpr()))
                                        {
                                            ((RadioButton)radioGroupRPR.getChildAt(i)).setChecked(true);
                                        }
                                    }
                                    layoutEarlyTest.setVisibility(View.VISIBLE);
                                    progressBarEarlyTest.setVisibility(View.GONE);
                                }
                            }
                        });
                    }
                });
            }
        }

        btnBTUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(SaveSharedPreference.getEarlyTest(EarlyTestActivity.this).equals("New"))
                {
                    layoutEarlyTest.setVisibility(View.GONE);
                    progressBarEarlyTest.setVisibility(View.VISIBLE);
                    if(checkRequired() == true)
                    {
                        layoutEarlyTest.setVisibility(View.VISIBLE);
                        progressBarEarlyTest.setVisibility(View.GONE);
                        Toast.makeText(EarlyTestActivity.this, "Field not selected!", Toast.LENGTH_LONG).show();
                    }else{
                        int radioBloodGroupId = radioGroupBloodGroup.getCheckedRadioButtonId();
                        RadioButton radioBloodGroup = (RadioButton)findViewById(radioBloodGroupId);
                        String bloodGroup = radioBloodGroup.getText().toString();

                        int radioRhdId = radioGroupRhd.getCheckedRadioButtonId();
                        RadioButton radioRhd = (RadioButton)findViewById(radioRhdId);
                        String rhd = radioRhd.getText().toString();

                        int radioRPRId = radioGroupRPR.getCheckedRadioButtonId();
                        RadioButton radioRPR = (RadioButton)findViewById(radioRPRId);
                        String rpr = radioRPR.getText().toString();

                        Date today = new Date();
                        String medicalPersonnelId = SaveSharedPreference.getID(EarlyTestActivity.this);

                        Calendar cal = Calendar.getInstance();
                        cal.setTime(today);
                        int month = cal.get(Calendar.MONTH);
                        int year = cal.get(Calendar.YEAR);

                        byte[] array = new byte[7]; // length is bounded by 7
                        new Random().nextBytes(array);
                        final String generatedString = new String(array, Charset.forName("UTF-8"));

                        MommyHealthInfo mi = new MommyHealthInfo(SaveSharedPreference.getMummyId(EarlyTestActivity.this),
                                GetMonth(month+1), year+"", status, generatedString);
                        final BloodTest bt = new BloodTest(bloodGroup, rhd, rpr, medicalPersonnelId, today);
                        CollectionReference pCollectionReference = mFirebaseFirestore.collection("Mommy");
                        pCollectionReference.whereEqualTo("mommyId", SaveSharedPreference.getMummyId(EarlyTestActivity.this)).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                            @Override
                            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                                String id = "";
                                for(QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots)
                                {
                                    id = documentSnapshot.getId();
                                }
                                DocumentReference pDocumentReference = mFirebaseFirestore.collection("Mommy").document(id);
                                pDocumentReference.update("healthInfoId", generatedString);
                            }
                        });
                        mCollectionReference.add(mi).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {
                                UpdateHealthInfoStatus(generatedString);
                                healthInfoId = documentReference.getId();
                                nCollectionReference = mFirebaseFirestore.collection("MommyHealthInfo").document(healthInfoId).collection("BloodTest");
                                nCollectionReference.add(bt).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                    @Override
                                    public void onSuccess(DocumentReference documentReference) {
                                        bloodTestId = documentReference.getId();
                                        AlertDialog.Builder builder = new AlertDialog.Builder(EarlyTestActivity.this);
                                        builder.setTitle("Register Successfully");
                                        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) {
                                                SaveSharedPreference.setEarlyTest(EarlyTestActivity.this, "Old");
                                                SaveSharedPreference.setHealthInfoId(EarlyTestActivity.this, generatedString);
                                                DisableRadioButton();
                                                return;
                                            }
                                        });
                                        builder.setMessage("Save Successful!");
                                        AlertDialog alert = builder.create();
                                        alert.setCanceledOnTouchOutside(false);
                                        alert.show();
                                        layoutEarlyTest.setVisibility(View.VISIBLE);
                                        progressBarEarlyTest.setVisibility(View.GONE);
                                    }
                                });
                            }
                        });
                    }
                }else if(SaveSharedPreference.getEarlyTest(EarlyTestActivity.this).equals("Old"))
                {
                    check++;
                    if(check == 1)
                    {
                        EnableRadioButton();
                        btnBTCancel.setVisibility(View.VISIBLE);
                    }else if(check == 2)
                    {
                        mDocumentReference = mFirebaseFirestore.document("MommyHealthInfo/"+healthInfoId+"/BloodTest/"+bloodTestId);
                        int radioBloodGroupId = radioGroupBloodGroup.getCheckedRadioButtonId();
                        RadioButton radioBloodGroup = (RadioButton)findViewById(radioBloodGroupId);
                        String bloodGroup = radioBloodGroup.getText().toString();

                        int radioRhdId = radioGroupRhd.getCheckedRadioButtonId();
                        RadioButton radioRhd = (RadioButton)findViewById(radioRhdId);
                        String rhd = radioRhd.getText().toString();

                        int radioRPRId = radioGroupRPR.getCheckedRadioButtonId();
                        RadioButton radioRPR = (RadioButton)findViewById(radioRPRId);
                        String rpr = radioRPR.getText().toString();

                        Log.i("CheckInfoId",healthInfoId);
                        Log.i("CheckBtId", bloodTestId);
                        Date today = new Date();
                        mDocumentReference.update("bloodGroup", bloodGroup);
                        mDocumentReference.update("rhd", rhd);
                        mDocumentReference.update("rpr", rpr);
                        mDocumentReference.update("createdDate", today);
                        Snackbar snackbar = Snackbar.make(relativeLayoutBT, "Updated Successfully!", Snackbar.LENGTH_LONG);
                        snackbar.show();
                        check = 0;
                        DisableRadioButton();
                        btnBTCancel.setVisibility(View.GONE);
                    }
                }
            }
        });

        btnBTCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DisableRadioButton();
                btnBTCancel.setVisibility(View.GONE);
                check = 0;
            }
        });

        cardViewSectionA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(SaveSharedPreference.getEarlyTest(EarlyTestActivity.this).equals("Old") || SaveSharedPreference.getUser(EarlyTestActivity.this).equals("Mommy"))
                {
                    Intent intent = new Intent(EarlyTestActivity.this, PregnantExperienceRecordActivity.class);
                    intent.putExtra("healthInfoId", healthInfoId);
                    intent.putExtra("bloodTestId", bloodTestId);
                    startActivity(intent);
                }else{
                    AlertDialog.Builder builder = new AlertDialog.Builder(EarlyTestActivity.this);
                    builder.setTitle("Action cannot be done");
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            return;
                        }
                    });
                    builder.setMessage("Blood Test must be carry out 1st");
                    AlertDialog alert = builder.create();
                    alert.show();
                }
            }
        });

        cardViewSectionB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String radioGroup = "";
                for(int i=0; i<radioGroupBloodGroup.getChildCount(); i++)
                {
                    if(((RadioButton)radioGroupBloodGroup.getChildAt(i)).isChecked())
                    {
                        radioGroup = ((RadioButton)radioGroupBloodGroup.getChildAt(i)).getText().toString();
                    }
                }
                if(SaveSharedPreference.getEarlyTest(EarlyTestActivity.this).equals("Old") || SaveSharedPreference.getUser(EarlyTestActivity.this).equals("Mommy"))
                {
                    Intent intent = new Intent(EarlyTestActivity.this, SectionBActivity.class);
                    intent.putExtra("healthInfoId", healthInfoId);
                    intent.putExtra("bloodTestId", bloodTestId);
                    intent.putExtra("radioGroup", radioGroup);
                    startActivity(intent);
                }else{
                    AlertDialog.Builder builder = new AlertDialog.Builder(EarlyTestActivity.this);
                    builder.setTitle("Action cannot be done");
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            return;
                        }
                    });
                    builder.setMessage("Blood Test must be carry out 1st");
                    AlertDialog alert = builder.create();
                    alert.show();
                }
            }
        });

        cardViewSectionC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(SaveSharedPreference.getEarlyTest(EarlyTestActivity.this).equals("Old") || SaveSharedPreference.getUser(EarlyTestActivity.this).equals("Mommy"))
                {
                    Intent intent = new Intent(EarlyTestActivity.this, SectionCActivity.class);
                    intent.putExtra("healthInfoId", healthInfoId);
                    intent.putExtra("bloodTestId", bloodTestId);
                    startActivity(intent);
                }else{
                    AlertDialog.Builder builder = new AlertDialog.Builder(EarlyTestActivity.this);
                    builder.setTitle("Action cannot be done");
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            return;
                        }
                    });
                    builder.setMessage("Blood Test must be carry out 1st");
                    AlertDialog alert = builder.create();
                    alert.show();
                }
            }
        });

        cardViewSectionD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(SaveSharedPreference.getEarlyTest(EarlyTestActivity.this).equals("Old") || SaveSharedPreference.getUser(EarlyTestActivity.this).equals("Mommy"))
                {
                    Intent intent = new Intent(EarlyTestActivity.this, SectionDActivity.class);
                    intent.putExtra("healthInfoId", healthInfoId);
                    intent.putExtra("bloodTestId", bloodTestId);
                    startActivity(intent);
                }else{
                    AlertDialog.Builder builder = new AlertDialog.Builder(EarlyTestActivity.this);
                    builder.setTitle("Action cannot be done");
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            return;
                        }
                    });
                    builder.setMessage("Blood Test must be carry out 1st");
                    AlertDialog alert = builder.create();
                    alert.show();
                }
            }
        });

        cardViewBreastCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(SaveSharedPreference.getEarlyTest(EarlyTestActivity.this).equals("Old") || SaveSharedPreference.getUser(EarlyTestActivity.this).equals("Mommy"))
                {
                    Intent intent = new Intent(EarlyTestActivity.this, BreastExamActivity.class);
                    intent.putExtra("healthInfoId", healthInfoId);
                    intent.putExtra("bloodTestId", bloodTestId);
                    startActivity(intent);
                }else{
                    AlertDialog.Builder builder = new AlertDialog.Builder(EarlyTestActivity.this);
                    builder.setTitle("Action cannot be done");
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            return;
                        }
                    });
                    builder.setMessage("Blood Test must be carry out 1st");
                    AlertDialog alert = builder.create();
                    alert.show();
                }
            }
        });

        cardViewMentalExamination.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(SaveSharedPreference.getEarlyTest(EarlyTestActivity.this).equals("Old") || SaveSharedPreference.getUser(EarlyTestActivity.this).equals("Mommy"))
                {
                    Intent intent = new Intent(EarlyTestActivity.this, HealthyMindFilterActivity.class);
                    intent.putExtra("healthInfoId", healthInfoId);
                    intent.putExtra("bloodTestId", bloodTestId);
                    startActivity(intent);
                }else{
                    AlertDialog.Builder builder = new AlertDialog.Builder(EarlyTestActivity.this);
                    builder.setTitle("Action cannot be done");
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            return;
                        }
                    });
                    builder.setMessage("Blood Test must be carry out 1st");
                    AlertDialog alert = builder.create();
                    alert.show();
                }
            }
        });

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void MommyLogIn()
    {
        String mommyhealthInfoId = "";
        btnBTUpdate.setVisibility(View.GONE);
        btnBTCancel.setVisibility(View.GONE);
        Intent intentss = getIntent();
        mommyhealthInfoId = intentss.getStringExtra("healthInfoId");
        mCollectionReference.whereEqualTo("healthInfoId", mommyhealthInfoId).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for(final QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots)
                {
                    healthInfoId = documentSnapshot.getId();
                }
                nCollectionReference = mFirebaseFirestore.collection("MommyHealthInfo").document(healthInfoId).collection("BloodTest");
                nCollectionReference.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        for(QueryDocumentSnapshot documentSnapshots : queryDocumentSnapshots)
                        {
                            bloodTestId = documentSnapshots.getId();
                            BloodTest bt = documentSnapshots.toObject(BloodTest.class);
                            for(int i=0; i<radioGroupBloodGroup.getChildCount(); i++)
                            {
                                if(((RadioButton)radioGroupBloodGroup.getChildAt(i)).getText().toString().equals(bt.getBloodGroup()))
                                {
                                    ((RadioButton)radioGroupBloodGroup.getChildAt(i)).setChecked(true);
                                }
                            }
                            for(int i=0; i<radioGroupRhd.getChildCount(); i++)
                            {
                                if(((RadioButton)radioGroupRhd.getChildAt(i)).getText().toString().equals(bt.getRhd()))
                                {
                                    ((RadioButton)radioGroupRhd.getChildAt(i)).setChecked(true);
                                }
                            }
                            for(int i=0; i<radioGroupRPR.getChildCount(); i++)
                            {
                                if(((RadioButton)radioGroupRPR.getChildAt(i)).getText().toString().equals(bt.getRpr()))
                                {
                                    ((RadioButton)radioGroupRPR.getChildAt(i)).setChecked(true);
                                }
                            }
                            layoutEarlyTest.setVisibility(View.VISIBLE);
                            progressBarEarlyTest.setVisibility(View.GONE);
                        }
                    }
                });
            }
        });

    }

    private void UpdateHealthInfoStatus(final String healthInfoId)
    {
        mCollectionReference.whereEqualTo("mommyId", SaveSharedPreference.getMummyId(EarlyTestActivity.this)).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for(QueryDocumentSnapshot documentSnapshotss: queryDocumentSnapshots)
                {
                    MommyHealthInfo mh = documentSnapshotss.toObject(MommyHealthInfo.class);
                    if(!mh.getHealthInfoId().equals(healthInfoId))
                    {
                        DocumentReference mDocumentReference = mFirebaseFirestore.collection("MommyHealthInfo").document(documentSnapshotss.getId());
                        mDocumentReference.update("status", "Inactive");
                    }
                }
            }
        });
    }

    private Boolean checkRequired()
    {
        Boolean unselected = true;
        if(radioGroupBloodGroup.getCheckedRadioButtonId() == -1 || radioGroupRhd.getCheckedRadioButtonId() == -1 || radioGroupRPR.getCheckedRadioButtonId() == -1)
        {
            if(radioGroupBloodGroup.getCheckedRadioButtonId() == -1)
            {
                radioBtnBTA.setError("Required!");
                radioBtnBTAB.setError("Required!");
                radioBtnBTB.setError("Required!");
                radioBtnBTO.setError("Required!");
            }
            if(radioGroupRhd.getCheckedRadioButtonId() == -1)
            {
                radioBtnBTPositive.setError("Required!");
                radioBtnBTNegative.setError("Required!");
            }
            if(radioGroupRPR.getCheckedRadioButtonId() == -1)
            {
                radioBtnNonReactive.setError("Required!");
                radioBtnReactive.setError("Required!");
            }
            unselected = true;
        }else{
            radioBtnBTA.setError(null);
            radioBtnBTAB.setError(null);
            radioBtnBTB.setError(null);
            radioBtnBTO.setError(null);
            radioBtnBTPositive.setError(null);
            radioBtnBTNegative.setError(null);
            radioBtnNonReactive.setError(null);
            radioBtnReactive.setError(null);
            unselected = false;
        }
        return unselected;
    }

    private void DisableRadioButton()
    {
        for(int i=0; i<radioGroupBloodGroup.getChildCount(); i++)
        {
            ((RadioButton)radioGroupBloodGroup.getChildAt(i)).setEnabled(false);
        }
        for(int i=0; i<radioGroupRhd.getChildCount(); i++)
        {
            ((RadioButton)radioGroupRhd.getChildAt(i)).setEnabled(false);
        }
        for(int i=0; i<radioGroupRPR.getChildCount(); i++)
        {
            ((RadioButton)radioGroupRPR.getChildAt(i)).setEnabled(false);
        }
    }

    private void EnableRadioButton()
    {
        for(int i=0; i<radioGroupBloodGroup.getChildCount(); i++)
        {
            ((RadioButton)radioGroupBloodGroup.getChildAt(i)).setEnabled(true);
        }
        for(int i=0; i<radioGroupRhd.getChildCount(); i++)
        {
            ((RadioButton)radioGroupRhd.getChildAt(i)).setEnabled(true);
        }
        for(int i=0; i<radioGroupRPR.getChildCount(); i++)
        {
            ((RadioButton)radioGroupRPR.getChildAt(i)).setEnabled(true);
        }
    }

    private String GetMonth(int month)
    {
        if(month == 1)
        {
            return "Jan";
        }else if(month == 2)
        {
            return "Feb";
        }else if(month == 3)
        {
            return "Mar";
        }else if(month == 4)
        {
            return "Apr";
        }else if(month == 5)
        {
            return "May";
        }else if(month == 6)
        {
            return "Jun";
        }else if(month == 7)
        {
            return "Jul";
        }else if(month == 8)
        {
            return "Aug";
        }else if(month == 9)
        {
            return "Sep";
        }else if(month == 10)
        {
            return "Oct";
        }else if(month == 11)
        {
            return "Nov";
        }else{
            return "Dec";
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
