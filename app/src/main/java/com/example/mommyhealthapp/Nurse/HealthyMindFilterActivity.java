package com.example.mommyhealthapp.Nurse;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.LinearLayoutCompat;

import android.app.AlarmManager;
import android.app.Dialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.mommyhealthapp.AlertService;
import com.example.mommyhealthapp.Class.MentalHealth;
import com.example.mommyhealthapp.MainActivity;
import com.example.mommyhealthapp.NotifyService;
import com.example.mommyhealthapp.R;
import com.example.mommyhealthapp.ReminderService;
import com.example.mommyhealthapp.SaveSharedPreference;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Date;

public class HealthyMindFilterActivity extends AppCompatActivity {

    private ImageView imageViewHint;
    private Button btnNoted, btnMentalSave, btnMentalCancel;
    private RatingBar ratingBarQ1, ratingBarQ2, ratingBarQ3, ratingBarQ4, ratingBarQ5, ratingBarQ6, ratingBarQ7, ratingBarQ8, ratingBarQ9, ratingBarQ10, ratingBarQ11, ratingBarQ12,
            ratingBarQ13, ratingBarQ14, ratingBarQ15, ratingBarQ16, ratingBarQ17, ratingBarQ18, ratingBarQ19, ratingBarQ20, ratingBarQ21;
    private TextInputLayout layoutStress, layoutAnxiety, layoutDepression;
    private EditText editTextMentalStress, editTextMentalAnxiety, editTextMentalDepression;
    private TextView textViewMentelHealthResult;
    private View viewMentalHealth;
    private RelativeLayout relativeLayoutMentalHealth;
    private LinearLayoutCompat layoutMentalHealth;
    private ProgressBar progressBarMentalHealth;
    Dialog questionDialog;

    private String healthInfoId, bloodTestId, key;
    private int check = 0;
    private Boolean isEmpty;
    private FirebaseFirestore mFirebaseFirestore;
    private CollectionReference mCollectionReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_healthy_mind_filter);
        imageViewHint = (ImageView)findViewById(R.id.imageViewHint);
        ratingBarQ1 = (RatingBar)findViewById(R.id.ratingBarQ1);
        ratingBarQ2 = (RatingBar)findViewById(R.id.ratingBarQ2);
        ratingBarQ3 = (RatingBar)findViewById(R.id.ratingBarQ3);
        ratingBarQ4 = (RatingBar)findViewById(R.id.ratingBarQ4);
        ratingBarQ5 = (RatingBar)findViewById(R.id.ratingBarQ5);
        ratingBarQ6 = (RatingBar)findViewById(R.id.ratingBarQ6);
        ratingBarQ7 = (RatingBar)findViewById(R.id.ratingBarQ7);
        ratingBarQ8 = (RatingBar)findViewById(R.id.ratingBarQ8);
        ratingBarQ9 = (RatingBar)findViewById(R.id.ratingBarQ9);
        ratingBarQ10 = (RatingBar)findViewById(R.id.ratingBarQ10);
        ratingBarQ11 = (RatingBar)findViewById(R.id.ratingBarQ11);
        ratingBarQ12 = (RatingBar)findViewById(R.id.ratingBarQ12);
        ratingBarQ13 = (RatingBar)findViewById(R.id.ratingBarQ13);
        ratingBarQ14 = (RatingBar)findViewById(R.id.ratingBarQ14);
        ratingBarQ15 = (RatingBar)findViewById(R.id.ratingBarQ15);
        ratingBarQ16 = (RatingBar)findViewById(R.id.ratingBarQ16);
        ratingBarQ17 = (RatingBar)findViewById(R.id.ratingBarQ17);
        ratingBarQ18 = (RatingBar)findViewById(R.id.ratingBarQ18);
        ratingBarQ19 = (RatingBar)findViewById(R.id.ratingBarQ19);
        ratingBarQ20 = (RatingBar)findViewById(R.id.ratingBarQ20);
        ratingBarQ21 = (RatingBar)findViewById(R.id.ratingBarQ21);
        layoutStress = (TextInputLayout)findViewById(R.id.layoutStress);
        layoutAnxiety = (TextInputLayout)findViewById(R.id.layoutAnxiety);
        layoutDepression = (TextInputLayout)findViewById(R.id.layoutDepression);
        editTextMentalStress = (EditText)findViewById(R.id.editTextMentalStress);
        editTextMentalAnxiety = (EditText)findViewById(R.id.editTextMentalAnxiety);
        editTextMentalDepression = (EditText)findViewById(R.id.editTextMentalDepression);
        textViewMentelHealthResult = (TextView) findViewById(R.id.textViewMentelHealthResult);
        viewMentalHealth = (View)findViewById(R.id.viewMentalHealth);
        relativeLayoutMentalHealth = (RelativeLayout)findViewById(R.id.relativeLayoutMentalHealth);
        layoutMentalHealth = (LinearLayoutCompat)findViewById(R.id.layoutMentalHealth);
        progressBarMentalHealth = (ProgressBar)findViewById(R.id.progressBarMentalHealth);
        btnMentalCancel = (Button)findViewById(R.id.btnMentalCancel);
        btnMentalSave = (Button)findViewById(R.id.btnMentalSave);

        layoutStress.setVisibility(View.GONE);
        layoutAnxiety.setVisibility(View.GONE);
        layoutDepression.setVisibility(View.GONE);
        viewMentalHealth.setVisibility(View.GONE);
        textViewMentelHealthResult.setVisibility(View.GONE);
        editTextMentalStress.setEnabled(false);
        editTextMentalStress.setTextColor(Color.parseColor("#000000"));
        editTextMentalAnxiety.setEnabled(false);
        editTextMentalAnxiety.setTextColor(Color.parseColor("#000000"));
        editTextMentalDepression.setEnabled(false);
        editTextMentalDepression.setTextColor(Color.parseColor("#000000"));
        layoutMentalHealth.setVisibility(View.GONE);
        progressBarMentalHealth.setVisibility(View.VISIBLE);
        btnMentalCancel.setVisibility(View.GONE);

        Intent intent = getIntent();
        healthInfoId = intent.getStringExtra("healthInfoId");
        bloodTestId = intent.getStringExtra("bloodTestId");

        mFirebaseFirestore = FirebaseFirestore.getInstance();
        mCollectionReference = mFirebaseFirestore.collection("MommyHealthInfo/"+healthInfoId+"/BloodTest/"+bloodTestId+"/MentalHealth");

        imageViewHint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                questionDialog = new Dialog(HealthyMindFilterActivity.this);
                questionDialog.setContentView(R.layout.custom_questionnaire_help);
                questionDialog.setTitle("Questionnaire Help");
                btnNoted = (Button)questionDialog.findViewById(R.id.btnNoted);

                btnNoted.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        questionDialog.cancel();
                    }
                });
                questionDialog.show();
                Window window =questionDialog.getWindow();
                window.setLayout(1000, 1200);
            }
        });

        if (SaveSharedPreference.getUser(HealthyMindFilterActivity.this).equals("Mommy")){
            MommyLogIn();
        }
        else{
            mCollectionReference.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                @Override
                public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                    if(queryDocumentSnapshots.isEmpty())
                    {
                        isEmpty = true;
                        layoutMentalHealth.setVisibility(View.VISIBLE);
                        progressBarMentalHealth.setVisibility(View.GONE);
                    }else{
                        isEmpty = false;
                        layoutStress.setVisibility(View.VISIBLE);
                        layoutAnxiety.setVisibility(View.VISIBLE);
                        layoutDepression.setVisibility(View.VISIBLE);
                        viewMentalHealth.setVisibility(View.VISIBLE);
                        textViewMentelHealthResult.setVisibility(View.VISIBLE);
                        DisableField();
                        for(QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots)
                        {
                            key = documentSnapshot.getId();
                            MentalHealth mh = documentSnapshot.toObject(MentalHealth.class);
                            ratingBarQ1.setRating(mh.getRatingHardToWindDown());
                            ratingBarQ2.setRating(mh.getRatingDrynessMouth());
                            ratingBarQ3.setRating(mh.getRatingNotExperiencePositive());
                            ratingBarQ4.setRating(mh.getRatingBreathDifficult());
                            ratingBarQ5.setRating(mh.getRatingNoInitiative());
                            ratingBarQ6.setRating(mh.getRatingOverReact());
                            ratingBarQ7.setRating(mh.getRatingExperienceTrembling());
                            ratingBarQ8.setRating(mh.getRatingNervousEnergy());
                            ratingBarQ9.setRating(mh.getRatingWorriedSituation());
                            ratingBarQ10.setRating(mh.getRatingNothingToLookForward());
                            ratingBarQ11.setRating(mh.getRatingAgigate());
                            ratingBarQ12.setRating(mh.getRatingDifficultRelax());
                            ratingBarQ13.setRating(mh.getRatingDownHearted());
                            ratingBarQ14.setRating(mh.getRatingIntolerant());
                            ratingBarQ15.setRating(mh.getRatingPanic());
                            ratingBarQ16.setRating(mh.getRatingLackEnthusiastic());
                            ratingBarQ17.setRating(mh.getRatingFeelNotWorth());
                            ratingBarQ18.setRating(mh.getRatingTouchy());
                            ratingBarQ19.setRating(mh.getRatingSenseHeartRateIncrease());
                            ratingBarQ20.setRating(mh.getRatingScared());
                            ratingBarQ21.setRating(mh.getRatingLifeMeaningLess());
                            editTextMentalStress.setText(mh.getStress());
                            editTextMentalAnxiety.setText(mh.getAnxiety());
                            editTextMentalDepression.setText(mh.getDepression());
                        }
                        layoutMentalHealth.setVisibility(View.VISIBLE);
                        progressBarMentalHealth.setVisibility(View.GONE);
                        btnMentalSave.setText("Update");
                    }
                }
            });
        }

        btnMentalSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isEmpty == true)
                {
                    float ratingHardToWindDown = ratingBarQ1.getRating();
                    float ratingDrynessMouth = ratingBarQ2.getRating();
                    float ratingNotExperiencePositive = ratingBarQ3.getRating();
                    float ratingBreathDifficult = ratingBarQ4.getRating();
                    float ratingNoInitiative = ratingBarQ5.getRating();
                    float ratingOverReact = ratingBarQ6.getRating();
                    float ratingExperienceTrembling = ratingBarQ7.getRating();
                    float ratingNervousEnergy = ratingBarQ8.getRating();
                    float ratingWorriedSituation = ratingBarQ9.getRating();
                    float ratingNothingToLookForward = ratingBarQ10.getRating();
                    float ratingAgigate = ratingBarQ11.getRating();
                    float ratingDifficultRelax = ratingBarQ12.getRating();
                    float ratingDownHearted = ratingBarQ13.getRating();
                    float ratingIntolerant = ratingBarQ14.getRating();
                    float ratingPanic = ratingBarQ15.getRating();
                    float ratingLackEnthusiastic = ratingBarQ16.getRating();
                    float ratingFeelNotWorth = ratingBarQ17.getRating();
                    float ratingTouchy = ratingBarQ18.getRating();
                    float ratingSenseHeartRateIncrease = ratingBarQ19.getRating();
                    float ratingScared = ratingBarQ20.getRating();
                    float ratingLifeMeaningLess = ratingBarQ21.getRating();
                    String medicalPersonnelId = SaveSharedPreference.getID(HealthyMindFilterActivity.this);
                    Date createdDate = new Date();

                    String stress = "", anxiety = "", depression = "";

                    float totalStress = ratingHardToWindDown + ratingOverReact + ratingNervousEnergy + ratingAgigate +  ratingDifficultRelax + ratingIntolerant + ratingTouchy;
                    float totalAnxiety = ratingDrynessMouth + ratingBreathDifficult + ratingExperienceTrembling + ratingWorriedSituation + ratingPanic + ratingSenseHeartRateIncrease + ratingScared;
                    float totalDepression = ratingNotExperiencePositive + ratingNoInitiative + ratingNothingToLookForward + ratingDownHearted + ratingLackEnthusiastic + ratingFeelNotWorth + ratingLifeMeaningLess;
                    layoutStress.setVisibility(View.VISIBLE);
                    layoutAnxiety.setVisibility(View.VISIBLE);
                    layoutDepression.setVisibility(View.VISIBLE);
                    viewMentalHealth.setVisibility(View.VISIBLE);
                    if(totalStress <= 7)
                    {
                        stress = "Normal";
                    }else if(totalStress > 7 && totalStress <= 9)
                    {
                        stress = "Light";
                    }else if(totalStress > 9 && totalStress <= 13)
                    {
                        stress = "Moderate";
                    }else if(totalStress > 13 && totalStress <= 17)
                    {
                        stress = "Bad";
                    }else if(totalStress > 17)
                    {
                        stress = "Very Bad";
                    }

                    if(totalAnxiety <= 4)
                    {
                        anxiety = "Normal";
                    }else if(totalAnxiety > 4 && totalAnxiety <= 6)
                    {
                        anxiety = "Light";
                    }else if(totalAnxiety > 6 && totalAnxiety <= 8)
                    {
                        anxiety = "Moderate";
                    }else if(totalAnxiety > 8 && totalAnxiety <= 10)
                    {
                        anxiety = "Bad";
                    }else if(totalAnxiety > 10)
                    {
                        anxiety = "Very Bad";
                    }

                    if(totalDepression <= 5)
                    {
                        depression = "Normal";
                    }else if(totalDepression > 5 && totalDepression <= 7)
                    {
                        depression = "Light";
                    }else if(totalDepression > 7 && totalDepression <= 10)
                    {
                        depression = "Moderate";
                    }else if(totalDepression > 10 && totalDepression <= 14)
                    {
                        depression = "Bad";
                    }else if(totalDepression > 14)
                    {
                        depression = "Very Bad";
                    }

                    editTextMentalStress.setText(stress);
                    editTextMentalAnxiety.setText(anxiety);
                    editTextMentalDepression.setText(depression);

                    MentalHealth mh = new MentalHealth(ratingHardToWindDown, ratingDrynessMouth, ratingNotExperiencePositive, ratingBreathDifficult, ratingNoInitiative, ratingOverReact,
                            ratingExperienceTrembling, ratingNervousEnergy, ratingWorriedSituation, ratingNothingToLookForward, ratingAgigate, ratingDifficultRelax, ratingDownHearted,
                            ratingIntolerant, ratingPanic, ratingLackEnthusiastic, ratingFeelNotWorth, ratingTouchy, ratingSenseHeartRateIncrease, ratingScared, ratingLifeMeaningLess,
                            stress, anxiety, depression, medicalPersonnelId, createdDate);

                    mCollectionReference.add(mh).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                            AlertDialog.Builder builder = new AlertDialog.Builder(HealthyMindFilterActivity.this);
                            builder.setTitle("Save Successfully");
                            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    finish();
                                }
                            });
                            builder.setMessage("Save Successful!\nStress: "+editTextMentalStress.getText().toString()+"\nAnxiety: "+editTextMentalAnxiety.getText().toString()+"\nDepression: "+editTextMentalDepression.getText().toString());
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
                        btnMentalCancel.setVisibility(View.VISIBLE);
                    }else if(check == 2)
                    {
                        float ratingHardToWindDown = ratingBarQ1.getRating();
                        float ratingDrynessMouth = ratingBarQ2.getRating();
                        float ratingNotExperiencePositive = ratingBarQ3.getRating();
                        float ratingBreathDifficult = ratingBarQ4.getRating();
                        float ratingNoInitiative = ratingBarQ5.getRating();
                        float ratingOverReact = ratingBarQ6.getRating();
                        float ratingExperienceTrembling = ratingBarQ7.getRating();
                        float ratingNervousEnergy = ratingBarQ8.getRating();
                        float ratingWorriedSituation = ratingBarQ9.getRating();
                        float ratingNothingToLookForward = ratingBarQ10.getRating();
                        float ratingAgigate = ratingBarQ11.getRating();
                        float ratingDifficultRelax = ratingBarQ12.getRating();
                        float ratingDownHearted = ratingBarQ13.getRating();
                        float ratingIntolerant = ratingBarQ14.getRating();
                        float ratingPanic = ratingBarQ15.getRating();
                        float ratingLackEnthusiastic = ratingBarQ16.getRating();
                        float ratingFeelNotWorth = ratingBarQ17.getRating();
                        float ratingTouchy = ratingBarQ18.getRating();
                        float ratingSenseHeartRateIncrease = ratingBarQ19.getRating();
                        float ratingScared = ratingBarQ20.getRating();
                        float ratingLifeMeaningLess = ratingBarQ21.getRating();
                        String medicalPersonnelId = SaveSharedPreference.getID(HealthyMindFilterActivity.this);
                        Date createdDate = new Date();
                        String stress = "", anxiety = "", depression = "";
                        float totalStress = ratingHardToWindDown + ratingOverReact + ratingNervousEnergy + ratingAgigate +  ratingDifficultRelax + ratingIntolerant + ratingTouchy;
                        float totalAnxiety = ratingDrynessMouth + ratingBreathDifficult + ratingExperienceTrembling + ratingWorriedSituation + ratingPanic + ratingSenseHeartRateIncrease + ratingScared;
                        float totalDepression = ratingNotExperiencePositive + ratingNoInitiative + ratingNothingToLookForward + ratingDownHearted + ratingLackEnthusiastic + ratingFeelNotWorth + ratingLifeMeaningLess;
                        if(totalStress <= 7)
                        {
                            stress = "Normal";
                        }else if(totalStress > 7 && totalStress <= 9)
                        {
                            stress = "Light";
                        }else if(totalStress > 9 && totalStress <= 13)
                        {
                            stress = "Moderate";
                        }else if(totalStress > 13 && totalStress <= 17)
                        {
                            stress = "Bad";
                        }else if(totalStress > 17)
                        {
                            stress = "Very Bad";
                        }

                        if(totalAnxiety <= 4)
                        {
                            anxiety = "Normal";
                        }else if(totalAnxiety > 4 && totalAnxiety <= 6)
                        {
                            anxiety = "Light";
                        }else if(totalAnxiety > 6 && totalAnxiety <= 8)
                        {
                            anxiety = "Moderate";
                        }else if(totalAnxiety > 8 && totalAnxiety <= 10)
                        {
                            anxiety = "Bad";
                        }else if(totalAnxiety > 10)
                        {
                            anxiety = "Very Bad";
                        }

                        if(totalDepression <= 5)
                        {
                            depression = "Normal";
                        }else if(totalDepression > 5 && totalDepression <= 7)
                        {
                            depression = "Light";
                        }else if(totalDepression > 7 && totalDepression <= 10)
                        {
                            depression = "Moderate";
                        }else if(totalDepression > 10 && totalDepression <= 14)
                        {
                            depression = "Bad";
                        }else if(totalDepression > 14)
                        {
                            depression = "Very Bad";
                        }

                        editTextMentalStress.setText(stress);
                        editTextMentalAnxiety.setText(anxiety);
                        editTextMentalDepression.setText(depression);

                        DocumentReference mDocumentReference = mFirebaseFirestore.document("MommyHealthInfo/"+healthInfoId+"/BloodTest/"+bloodTestId+"/MentalHealth/"+key);
                        mDocumentReference.update("ratingHardToWindDown", ratingHardToWindDown);
                        mDocumentReference.update("ratingDrynessMouth", ratingDrynessMouth);
                        mDocumentReference.update("ratingNotExperiencePositive", ratingNotExperiencePositive);
                        mDocumentReference.update("ratingBreathDifficult", ratingBreathDifficult);
                        mDocumentReference.update("ratingNoInitiative", ratingNoInitiative);
                        mDocumentReference.update("ratingOverReact", ratingOverReact);
                        mDocumentReference.update("ratingExperienceTrembling", ratingExperienceTrembling);
                        mDocumentReference.update("ratingNervousEnergy", ratingNervousEnergy);
                        mDocumentReference.update("ratingWorriedSituation", ratingWorriedSituation);
                        mDocumentReference.update("ratingNothingToLookForward", ratingNothingToLookForward);
                        mDocumentReference.update("ratingAgigate", ratingAgigate);
                        mDocumentReference.update("ratingDifficultRelax", ratingDifficultRelax);
                        mDocumentReference.update("ratingDownHearted", ratingDownHearted);
                        mDocumentReference.update("ratingIntolerant", ratingIntolerant);
                        mDocumentReference.update("ratingPanic", ratingPanic);
                        mDocumentReference.update("ratingLackEnthusiastic", ratingLackEnthusiastic);
                        mDocumentReference.update("ratingFeelNotWorth", ratingFeelNotWorth);
                        mDocumentReference.update("ratingTouchy", ratingTouchy);
                        mDocumentReference.update("ratingSenseHeartRateIncrease", ratingSenseHeartRateIncrease);
                        mDocumentReference.update("ratingScared", ratingScared);
                        mDocumentReference.update("ratingLifeMeaningLess", ratingLifeMeaningLess);
                        mDocumentReference.update("Stress", stress);
                        mDocumentReference.update("Anxiety", anxiety);
                        mDocumentReference.update("Depression", depression);
                        mDocumentReference.update("medicalPersonnelId", medicalPersonnelId);
                        mDocumentReference.update("createdDate", createdDate);
                        Snackbar snackbar = Snackbar.make(relativeLayoutMentalHealth,"Updated Successfully!", Snackbar.LENGTH_LONG);
                        snackbar.show();
                        check = 0;
                        DisableField();
                        btnMentalCancel.setVisibility(View.GONE);
                    }
                }
            }
        });

        btnMentalCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DisableField();
                btnMentalCancel.setVisibility(View.GONE);
                check = 0;
            }
        });

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void MommyLogIn(){
        mCollectionReference.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                if(queryDocumentSnapshots.isEmpty())
                {
                    isEmpty = true;
                    layoutMentalHealth.setVisibility(View.VISIBLE);
                    progressBarMentalHealth.setVisibility(View.GONE);
                    btnMentalCancel.setVisibility(View.GONE);
                    btnMentalSave.setVisibility(View.GONE);
                    DisableField();
                }else{
                    isEmpty = false;
                    layoutStress.setVisibility(View.VISIBLE);
                    layoutAnxiety.setVisibility(View.VISIBLE);
                    layoutDepression.setVisibility(View.VISIBLE);
                    viewMentalHealth.setVisibility(View.VISIBLE);
                    textViewMentelHealthResult.setVisibility(View.VISIBLE);
                    DisableField();
                    for(QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots)
                    {
                        key = documentSnapshot.getId();
                        MentalHealth mh = documentSnapshot.toObject(MentalHealth.class);
                        ratingBarQ1.setRating(mh.getRatingHardToWindDown());
                        ratingBarQ2.setRating(mh.getRatingDrynessMouth());
                        ratingBarQ3.setRating(mh.getRatingNotExperiencePositive());
                        ratingBarQ4.setRating(mh.getRatingBreathDifficult());
                        ratingBarQ5.setRating(mh.getRatingNoInitiative());
                        ratingBarQ6.setRating(mh.getRatingOverReact());
                        ratingBarQ7.setRating(mh.getRatingExperienceTrembling());
                        ratingBarQ8.setRating(mh.getRatingNervousEnergy());
                        ratingBarQ9.setRating(mh.getRatingWorriedSituation());
                        ratingBarQ10.setRating(mh.getRatingNothingToLookForward());
                        ratingBarQ11.setRating(mh.getRatingAgigate());
                        ratingBarQ12.setRating(mh.getRatingDifficultRelax());
                        ratingBarQ13.setRating(mh.getRatingDownHearted());
                        ratingBarQ14.setRating(mh.getRatingIntolerant());
                        ratingBarQ15.setRating(mh.getRatingPanic());
                        ratingBarQ16.setRating(mh.getRatingLackEnthusiastic());
                        ratingBarQ17.setRating(mh.getRatingFeelNotWorth());
                        ratingBarQ18.setRating(mh.getRatingTouchy());
                        ratingBarQ19.setRating(mh.getRatingSenseHeartRateIncrease());
                        ratingBarQ20.setRating(mh.getRatingScared());
                        ratingBarQ21.setRating(mh.getRatingLifeMeaningLess());
                        editTextMentalStress.setText(mh.getStress());
                        editTextMentalAnxiety.setText(mh.getAnxiety());
                        editTextMentalDepression.setText(mh.getDepression());
                    }
                    layoutMentalHealth.setVisibility(View.VISIBLE);
                    progressBarMentalHealth.setVisibility(View.GONE);
                    btnMentalSave.setVisibility(View.GONE);
                    btnMentalCancel.setVisibility(View.GONE);
                }
            }
        });

    }
    private void DisableField()
    {
        ratingBarQ1.setEnabled(false);
        ratingBarQ2.setEnabled(false);
        ratingBarQ3.setEnabled(false);
        ratingBarQ4.setEnabled(false);
        ratingBarQ5.setEnabled(false);
        ratingBarQ6.setEnabled(false);
        ratingBarQ7.setEnabled(false);
        ratingBarQ8.setEnabled(false);
        ratingBarQ9.setEnabled(false);
        ratingBarQ10.setEnabled(false);
        ratingBarQ11.setEnabled(false);
        ratingBarQ12.setEnabled(false);
        ratingBarQ13.setEnabled(false);
        ratingBarQ14.setEnabled(false);
        ratingBarQ15.setEnabled(false);
        ratingBarQ16.setEnabled(false);
        ratingBarQ17.setEnabled(false);
        ratingBarQ18.setEnabled(false);
        ratingBarQ19.setEnabled(false);
        ratingBarQ20.setEnabled(false);
        ratingBarQ21.setEnabled(false);
    }

    private void EnableField()
    {
        ratingBarQ1.setEnabled(true);
        ratingBarQ2.setEnabled(true);
        ratingBarQ3.setEnabled(true);
        ratingBarQ4.setEnabled(true);
        ratingBarQ5.setEnabled(true);
        ratingBarQ6.setEnabled(true);
        ratingBarQ7.setEnabled(true);
        ratingBarQ8.setEnabled(true);
        ratingBarQ9.setEnabled(true);
        ratingBarQ10.setEnabled(true);
        ratingBarQ11.setEnabled(true);
        ratingBarQ12.setEnabled(true);
        ratingBarQ13.setEnabled(true);
        ratingBarQ14.setEnabled(true);
        ratingBarQ15.setEnabled(true);
        ratingBarQ16.setEnabled(true);
        ratingBarQ17.setEnabled(true);
        ratingBarQ18.setEnabled(true);
        ratingBarQ19.setEnabled(true);
        ratingBarQ20.setEnabled(true);
        ratingBarQ21.setEnabled(true);
    }

    private void CancelAlarm()
    {
        AlarmManager alarmManager = (AlarmManager)HealthyMindFilterActivity.this.getSystemService(Context.ALARM_SERVICE );
        Intent intent = new Intent(HealthyMindFilterActivity.this, NotifyService.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(HealthyMindFilterActivity.this, 9000, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        alarmManager.cancel(pendingIntent);
        pendingIntent.cancel();

        Intent intentReminder = new Intent(HealthyMindFilterActivity.this, ReminderService.class);
        PendingIntent pendingIntentReminder = PendingIntent.getBroadcast(HealthyMindFilterActivity.this, 101, intentReminder, PendingIntent.FLAG_UPDATE_CURRENT);
        alarmManager.cancel(pendingIntentReminder);
        pendingIntentReminder.cancel();

        Intent intentAlert = new Intent(HealthyMindFilterActivity.this, AlertService.class);
        PendingIntent pendingIntentAlert = PendingIntent.getBroadcast(HealthyMindFilterActivity.this, 1000, intentAlert, 0);
        alarmManager.cancel(pendingIntentAlert);
        pendingIntentAlert.cancel();

        Log.i("TestingAlarmCancel", "Alarm Cancel");
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.action_logout:
                AlertDialog.Builder builder = new AlertDialog.Builder(HealthyMindFilterActivity.this);
                builder.setTitle("Logout");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if(SaveSharedPreference.getUser(HealthyMindFilterActivity.this).equals("Mommy"))
                        {
                            CancelAlarm();
                        }
                        SaveSharedPreference.clearUser(HealthyMindFilterActivity.this);
                        Intent intent = new Intent(HealthyMindFilterActivity.this, MainActivity.class);
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
