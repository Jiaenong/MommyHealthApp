package com.example.mommyhealthapp.Nurse;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.cardview.widget.CardView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.example.mommyhealthapp.Class.MGTT;
import com.example.mommyhealthapp.MainActivity;
import com.example.mommyhealthapp.Mommy.PregnancyWeightGainActivity;
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

public class MGTTActivity extends AppCompatActivity {

    private CheckBox checkBoxAge35, checkBoxBMI, checkBoxGestational, checkBoxInstrauterine, checkBoxStillBirth, checkBoxNeotanalDeath, checkBoxMiscarriages, checkBoxCongenital,
            checkBoxMascrosomic, checkBoxFirstDegreeDiabetes, checkBoxGlycosuria, checkBoxEssentialHypertension, checkBoxInducedHypertension, checkBoxPolyhydramanios, checkBoxUterus,
            checkBoxMultiplePregnancy, checkBoxUTI;
    private RelativeLayout relativeLayoutMGTT;
    private LinearLayoutCompat layoutMGTT;
    private ProgressBar progressBarMGTT;
    private Button buttonSaveMGTT, buttonCancelMGTT;
    private CardView cardViewMGTTResult;

    private String healthInfoId, key;
    private int check = 0;
    private Boolean isEmpty;
    private FirebaseFirestore mFirebaseFirestore;
    private CollectionReference mCollectionReference, nCollectionReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mgtt);
        checkBoxAge35 = (CheckBox)findViewById(R.id.checkBoxAge35);
        checkBoxBMI = (CheckBox)findViewById(R.id.checkBoxBMI);
        checkBoxGestational = (CheckBox)findViewById(R.id.checkBoxGestational);
        checkBoxInstrauterine = (CheckBox)findViewById(R.id.checkBoxInstrauterine);
        checkBoxStillBirth = (CheckBox)findViewById(R.id.checkBoxStillBirth);
        checkBoxNeotanalDeath = (CheckBox)findViewById(R.id.checkBoxNeotanalDeath);
        checkBoxMiscarriages = (CheckBox)findViewById(R.id.checkBoxMiscarriages);
        checkBoxCongenital = (CheckBox)findViewById(R.id.checkBoxCongenital);
        checkBoxMascrosomic = (CheckBox)findViewById(R.id.checkBoxMascrosomic);
        checkBoxFirstDegreeDiabetes = (CheckBox)findViewById(R.id.checkBoxFirstDegreeDiabetes);
        checkBoxGlycosuria = (CheckBox)findViewById(R.id.checkBoxGlycosuria);
        checkBoxEssentialHypertension = (CheckBox)findViewById(R.id.checkBoxEssentialHypertension);
        checkBoxInducedHypertension = (CheckBox)findViewById(R.id.checkBoxInducedHypertension);
        checkBoxPolyhydramanios = (CheckBox)findViewById(R.id.checkBoxPolyhydramanios);
        checkBoxUterus = (CheckBox)findViewById(R.id.checkBoxUterus);
        checkBoxMultiplePregnancy = (CheckBox)findViewById(R.id.checkBoxMultiplePregnancy);
        checkBoxUTI = (CheckBox)findViewById(R.id.checkBoxUTI);

        relativeLayoutMGTT = (RelativeLayout)findViewById(R.id.relativeLayoutMGTT);
        layoutMGTT = (LinearLayoutCompat)findViewById(R.id.layoutMGTT);
        progressBarMGTT = (ProgressBar)findViewById(R.id.progressBarMGTT);
        cardViewMGTTResult = (CardView)findViewById(R.id.cardViewMGTTResult);

        buttonCancelMGTT = (Button)findViewById(R.id.buttonCancelMGTT);
        buttonSaveMGTT = (Button)findViewById(R.id.buttonSaveMGTT);

        progressBarMGTT.setVisibility(View.VISIBLE);
        layoutMGTT.setVisibility(View.GONE);
        buttonCancelMGTT.setVisibility(View.GONE);

        mFirebaseFirestore = FirebaseFirestore.getInstance();
        mCollectionReference = mFirebaseFirestore.collection("MommyHealthInfo");
        mCollectionReference.whereEqualTo("healthInfoId", SaveSharedPreference.getHealthInfoId(MGTTActivity.this)).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for(QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots)
                {
                    healthInfoId = documentSnapshot.getId();
                }
                nCollectionReference = mFirebaseFirestore.collection("MommyHealthInfo").document(healthInfoId).collection("MGTT");
                if(SaveSharedPreference.getUser(MGTTActivity.this).equals("Mommy")){
                    MommyLogIn();
                }
                else{
                    nCollectionReference.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                        @Override
                        public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                            if(queryDocumentSnapshots.isEmpty())
                            {
                                isEmpty = true;
                                progressBarMGTT.setVisibility(View.GONE);
                                layoutMGTT.setVisibility(View.VISIBLE);
                            }else{
                                isEmpty = false;
                                DisableField();
                                for(QueryDocumentSnapshot documentSnapshots: queryDocumentSnapshots)
                                {
                                    key = documentSnapshots.getId();
                                    MGTT mgtt = documentSnapshots.toObject(MGTT.class);
                                    if(mgtt.isAgeAbove35() == true)
                                    {
                                        checkBoxAge35.setChecked(true);
                                    }else{
                                        checkBoxAge35.setChecked(false);
                                    }

                                    if(mgtt.isBMI() == true)
                                    {
                                        checkBoxBMI.setChecked(true);
                                    }else{
                                        checkBoxBMI.setChecked(false);
                                    }

                                    if(mgtt.isGestationalDiabetes() == true)
                                    {
                                        checkBoxGestational.setChecked(true);
                                    }else{
                                        checkBoxGestational.setChecked(false);
                                    }

                                    if(mgtt.isInstrauterineDeath() == true)
                                    {
                                        checkBoxInstrauterine.setChecked(true);
                                    }else{
                                        checkBoxInstrauterine.setChecked(false);
                                    }

                                    if(mgtt.isStillBirth() == true)
                                    {
                                        checkBoxStillBirth.setChecked(true);
                                    }else{
                                        checkBoxStillBirth.setChecked(false);
                                    }

                                    if(mgtt.isNeotanalDeath() == true)
                                    {
                                        checkBoxNeotanalDeath.setChecked(true);
                                    }else{
                                        checkBoxNeotanalDeath.setChecked(false);
                                    }

                                    if(mgtt.isRecurrentMiscarriage() == true)
                                    {
                                        checkBoxMiscarriages.setChecked(true);
                                    }else{
                                        checkBoxMiscarriages.setChecked(false);
                                    }

                                    if(mgtt.isCongenitalAbnormality() == true)
                                    {
                                        checkBoxCongenital.setChecked(true);
                                    }else{
                                        checkBoxCongenital.setChecked(false);
                                    }

                                    if(mgtt.isMascrosomicBaby() == true)
                                    {
                                        checkBoxMascrosomic.setChecked(true);
                                    }else{
                                        checkBoxMascrosomic.setChecked(false);
                                    }

                                    if(mgtt.isFirstDegreeRelativeDiabetes() == true)
                                    {
                                        checkBoxFirstDegreeDiabetes.setChecked(true);
                                    }else{
                                        checkBoxFirstDegreeDiabetes.setChecked(false);
                                    }

                                    if(mgtt.isGlycosuria() == true)
                                    {
                                        checkBoxGlycosuria.setChecked(true);
                                    }else{
                                        checkBoxGlycosuria.setChecked(false);
                                    }

                                    if(mgtt.isEssentialHypertension() == true)
                                    {
                                        checkBoxEssentialHypertension.setChecked(true);
                                    }else{
                                        checkBoxEssentialHypertension.setChecked(false);
                                    }

                                    if(mgtt.isPregnancyInducedHypertension() == true)
                                    {
                                        checkBoxInducedHypertension.setChecked(true);
                                    }else{
                                        checkBoxInducedHypertension.setChecked(false);
                                    }

                                    if(mgtt.isPolyhydramanios() == true)
                                    {
                                        checkBoxPolyhydramanios.setChecked(true);
                                    }else{
                                        checkBoxPolyhydramanios.setChecked(false);
                                    }

                                    if(mgtt.isUterusLargerThanDate() == true)
                                    {
                                        checkBoxUterus.setChecked(true);
                                    }else{
                                        checkBoxUterus.setChecked(false);
                                    }

                                    if(mgtt.isMultiplePregnancy() == true)
                                    {
                                        checkBoxMultiplePregnancy.setChecked(true);
                                    }else{
                                        checkBoxMultiplePregnancy.setChecked(false);
                                    }

                                    if(mgtt.isRecurrentUTI() == true)
                                    {
                                        checkBoxUTI.setChecked(true);
                                    }else{
                                        checkBoxUTI.setChecked(false);
                                    }
                                }
                                progressBarMGTT.setVisibility(View.GONE);
                                layoutMGTT.setVisibility(View.VISIBLE);
                                buttonSaveMGTT.setText("Update");
                            }
                        }
                    });
                }
            }
        });

        cardViewMGTTResult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MGTTActivity.this, MGTTResultActivity.class);
                intent.putExtra("healthInfoId", healthInfoId);
                intent.putExtra("MGTTKey", key);
                startActivity(intent);
            }
        });

        buttonSaveMGTT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isEmpty == true)
                {
                    int checkEmpty = 0;
                    final boolean ageAbove35, BMI, gestationalDiabetes, instrauterineDeath, stillBirth, neotanalDeath, recurrentMiscarriage, congenitalAbnormality, mascrosomicBaby,
                            firstDegreeRelativeDiabetes, glycosuria, essentialHypertension, pregnancyInducedHypertension, polyhydramanios, uterusLargerThanDate, multiplePregnancy,
                            recurrentUTI;
                    String medicalPersonnelId = SaveSharedPreference.getID(MGTTActivity.this);
                    Date createdDate = new Date();

                    if(checkBoxAge35.isChecked())
                    {
                        ageAbove35 = true;
                    }else{
                        ageAbove35 = false;
                        checkEmpty++;
                    }

                    if(checkBoxBMI.isChecked())
                    {
                        BMI = true;
                    }else{
                        BMI = false;
                        checkEmpty++;
                    }

                    if(checkBoxGestational.isChecked())
                    {
                        gestationalDiabetes = true;
                    }else{
                        gestationalDiabetes = false;
                        checkEmpty++;
                    }

                    if(checkBoxInstrauterine.isChecked())
                    {
                        instrauterineDeath = true;
                    }else{
                        instrauterineDeath = false;
                        checkEmpty++;
                    }

                    if(checkBoxStillBirth.isChecked())
                    {
                        stillBirth = true;
                    }else{
                        stillBirth = false;
                        checkEmpty++;
                    }

                    if(checkBoxNeotanalDeath.isChecked())
                    {
                        neotanalDeath = true;
                    }else{
                        neotanalDeath = false;
                        checkEmpty++;
                    }

                    if(checkBoxMiscarriages.isChecked())
                    {
                        recurrentMiscarriage = true;
                    }else{
                        recurrentMiscarriage = false;
                        checkEmpty++;
                    }

                    if(checkBoxCongenital.isChecked())
                    {
                        congenitalAbnormality = true;
                    }else{
                        congenitalAbnormality = false;
                        checkEmpty++;
                    }

                    if(checkBoxMascrosomic.isChecked())
                    {
                        mascrosomicBaby = true;
                    }else{
                        mascrosomicBaby = false;
                        checkEmpty++;
                    }

                    if(checkBoxFirstDegreeDiabetes.isChecked())
                    {
                        firstDegreeRelativeDiabetes = true;
                    }else{
                        firstDegreeRelativeDiabetes = false;
                        checkEmpty++;
                    }

                    if(checkBoxGlycosuria.isChecked())
                    {
                        glycosuria = true;
                    }else{
                        glycosuria = false;
                        checkEmpty++;
                    }

                    if(checkBoxEssentialHypertension.isChecked())
                    {
                        essentialHypertension = true;
                    }else{
                        essentialHypertension = false;
                        checkEmpty++;
                    }

                    if(checkBoxInducedHypertension.isChecked())
                    {
                        pregnancyInducedHypertension = true;
                    }else{
                        pregnancyInducedHypertension = false;
                        checkEmpty++;
                    }

                    if(checkBoxPolyhydramanios.isChecked())
                    {
                        polyhydramanios = true;
                    }else{
                        polyhydramanios = false;
                        checkEmpty++;
                    }

                    if(checkBoxUterus.isChecked())
                    {
                        uterusLargerThanDate = true;
                    }else{
                        uterusLargerThanDate = false;
                        checkEmpty++;
                    }

                    if(checkBoxMultiplePregnancy.isChecked())
                    {
                        multiplePregnancy = true;
                    }else{
                        multiplePregnancy = false;
                        checkEmpty++;
                    }

                    if(checkBoxUTI.isChecked())
                    {
                        recurrentUTI = true;
                    }else{
                        recurrentUTI = false;
                        checkEmpty++;
                    }
                    if(checkEmpty == 17)
                    {
                        AlertDialog.Builder builder = new AlertDialog.Builder(MGTTActivity.this);
                        builder.setTitle("Error");
                        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                return;
                            }
                        });
                        builder.setMessage("At least check box is selected");
                        AlertDialog alert = builder.create();
                        alert.show();
                    }else{
                        MGTT mgtt = new MGTT(ageAbove35, BMI, gestationalDiabetes, instrauterineDeath, stillBirth, neotanalDeath, recurrentMiscarriage, congenitalAbnormality,
                                mascrosomicBaby, firstDegreeRelativeDiabetes, glycosuria, essentialHypertension, pregnancyInducedHypertension, polyhydramanios,
                                uterusLargerThanDate, multiplePregnancy, recurrentUTI, medicalPersonnelId, createdDate);

                        nCollectionReference.add(mgtt).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(final DocumentReference documentReference) {
                                AlertDialog.Builder builder = new AlertDialog.Builder(MGTTActivity.this);
                                builder.setTitle("Save Successfully");
                                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        Intent intent = new Intent(MGTTActivity.this, MGTTResultActivity.class);
                                        intent.putExtra("healthInfoId", healthInfoId);
                                        intent.putExtra("MGTTKey", documentReference.getId());
                                        startActivity(intent);
                                        finish();
                                    }
                                });
                                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        finish();
                                    }
                                });
                                builder.setMessage("Save Successful! Continue to MGTT Result?");
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
                        buttonCancelMGTT.setVisibility(View.VISIBLE);
                    }else if(check == 2)
                    {
                        int checkEmpty = 0;
                        DocumentReference mDocumentReference = mFirebaseFirestore.collection("MommyHealthInfo").document(healthInfoId).collection("MGTT").document(key);
                        boolean ageAbove35, BMI, gestationalDiabetes, instrauterineDeath, stillBirth, neotanalDeath, recurrentMiscarriage, congenitalAbnormality, mascrosomicBaby,
                                firstDegreeRelativeDiabetes, glycosuria, essentialHypertension, pregnancyInducedHypertension, polyhydramanios, uterusLargerThanDate, multiplePregnancy,
                                recurrentUTI;
                        String medicalPersonnelId = SaveSharedPreference.getID(MGTTActivity.this);
                        Date createdDate = new Date();

                        if(checkBoxAge35.isChecked())
                        {
                            ageAbove35 = true;
                        }else{
                            ageAbove35 = false;
                            checkEmpty++;
                        }

                        if(checkBoxBMI.isChecked())
                        {
                            BMI = true;
                        }else{
                            BMI = false;
                            checkEmpty++;
                        }

                        if(checkBoxGestational.isChecked())
                        {
                            gestationalDiabetes = true;
                        }else{
                            gestationalDiabetes = false;
                            checkEmpty++;
                        }

                        if(checkBoxInstrauterine.isChecked())
                        {
                            instrauterineDeath = true;
                        }else{
                            instrauterineDeath = false;
                            checkEmpty++;
                        }

                        if(checkBoxStillBirth.isChecked())
                        {
                            stillBirth = true;
                        }else{
                            stillBirth = false;
                            checkEmpty++;
                        }

                        if(checkBoxNeotanalDeath.isChecked())
                        {
                            neotanalDeath = true;
                        }else{
                            neotanalDeath = false;
                            checkEmpty++;
                        }

                        if(checkBoxMiscarriages.isChecked())
                        {
                            recurrentMiscarriage = true;
                        }else{
                            recurrentMiscarriage = false;
                            checkEmpty++;
                        }

                        if(checkBoxCongenital.isChecked())
                        {
                            congenitalAbnormality = true;
                        }else{
                            congenitalAbnormality = false;
                            checkEmpty++;
                        }

                        if(checkBoxMascrosomic.isChecked())
                        {
                            mascrosomicBaby = true;
                        }else{
                            mascrosomicBaby = false;
                            checkEmpty++;
                        }

                        if(checkBoxFirstDegreeDiabetes.isChecked())
                        {
                            firstDegreeRelativeDiabetes = true;
                        }else{
                            firstDegreeRelativeDiabetes = false;
                            checkEmpty++;
                        }

                        if(checkBoxGlycosuria.isChecked())
                        {
                            glycosuria = true;
                        }else{
                            glycosuria = false;
                            checkEmpty++;
                        }

                        if(checkBoxEssentialHypertension.isChecked())
                        {
                            essentialHypertension = true;
                        }else{
                            essentialHypertension = false;
                            checkEmpty++;
                        }

                        if(checkBoxInducedHypertension.isChecked())
                        {
                            pregnancyInducedHypertension = true;
                        }else{
                            pregnancyInducedHypertension = false;
                            checkEmpty++;
                        }

                        if(checkBoxPolyhydramanios.isChecked())
                        {
                            polyhydramanios = true;
                        }else{
                            polyhydramanios = false;
                            checkEmpty++;
                        }

                        if(checkBoxUterus.isChecked())
                        {
                            uterusLargerThanDate = true;
                        }else{
                            uterusLargerThanDate = false;
                            checkEmpty++;
                        }

                        if(checkBoxMultiplePregnancy.isChecked())
                        {
                            multiplePregnancy = true;
                        }else{
                            multiplePregnancy = false;
                            checkEmpty++;
                        }

                        if(checkBoxUTI.isChecked())
                        {
                            recurrentUTI = true;
                        }else{
                            recurrentUTI = false;
                            checkEmpty++;
                        }
                        if(checkEmpty == 17)
                        {
                            check = 1;
                            AlertDialog.Builder builder = new AlertDialog.Builder(MGTTActivity.this);
                            builder.setTitle("Error");
                            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    return;
                                }
                            });
                            builder.setMessage("At least check box is selected");
                            AlertDialog alert = builder.create();
                            alert.show();
                        }else{
                            mDocumentReference.update("ageAbove35", ageAbove35);
                            mDocumentReference.update("BMI", BMI);
                            mDocumentReference.update("gestationalDiabetes", gestationalDiabetes);
                            mDocumentReference.update("InstrauterineDeath", instrauterineDeath);
                            mDocumentReference.update("stillBirth", stillBirth);
                            mDocumentReference.update("neotanalDeath", neotanalDeath);
                            mDocumentReference.update("recurrentMiscarriage", recurrentMiscarriage);
                            mDocumentReference.update("congenitalAbnormality", congenitalAbnormality);
                            mDocumentReference.update("mascrosomicBaby", mascrosomicBaby);
                            mDocumentReference.update("firstDegreeRelativeDiabetes", firstDegreeRelativeDiabetes);
                            mDocumentReference.update("glycosuria", glycosuria);
                            mDocumentReference.update("essentialHypertension", essentialHypertension);
                            mDocumentReference.update("pregnancyInducedHypertension", pregnancyInducedHypertension);
                            mDocumentReference.update("polyhydramanios", polyhydramanios);
                            mDocumentReference.update("uterusLargerThanDate", uterusLargerThanDate);
                            mDocumentReference.update("multiplePregnancy", multiplePregnancy);
                            mDocumentReference.update("recurrentUTI", recurrentUTI);
                            mDocumentReference.update("medicalPersonnelId", medicalPersonnelId);
                            mDocumentReference.update("createdDate", createdDate);
                            Snackbar snackbar = Snackbar.make(relativeLayoutMGTT, "Updated Successfully!", Snackbar.LENGTH_LONG);
                            snackbar.show();
                            check = 0;
                            DisableField();
                            buttonCancelMGTT.setVisibility(View.GONE);
                        }
                    }
                }
            }
        });

        buttonCancelMGTT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonCancelMGTT.setVisibility(View.GONE);
                DisableField();
                check = 0;
            }
        });

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void MommyLogIn()
    {
        nCollectionReference.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                if(queryDocumentSnapshots.isEmpty())
                {
                    isEmpty = true;
                    progressBarMGTT.setVisibility(View.GONE);
                    layoutMGTT.setVisibility(View.VISIBLE);
                    buttonSaveMGTT.setVisibility(View.GONE);
                    buttonCancelMGTT.setVisibility(View.GONE);
                    DisableField();
                }else{
                    isEmpty = false;
                    DisableField();
                    for(QueryDocumentSnapshot documentSnapshots: queryDocumentSnapshots)
                    {
                        key = documentSnapshots.getId();
                        MGTT mgtt = documentSnapshots.toObject(MGTT.class);
                        if(mgtt.isAgeAbove35() == true)
                        {
                            checkBoxAge35.setChecked(true);
                        }else{
                            checkBoxAge35.setChecked(false);
                        }

                        if(mgtt.isBMI() == true)
                        {
                            checkBoxBMI.setChecked(true);
                        }else{
                            checkBoxBMI.setChecked(false);
                        }

                        if(mgtt.isGestationalDiabetes() == true)
                        {
                            checkBoxGestational.setChecked(true);
                        }else{
                            checkBoxGestational.setChecked(false);
                        }

                        if(mgtt.isInstrauterineDeath() == true)
                        {
                            checkBoxInstrauterine.setChecked(true);
                        }else{
                            checkBoxInstrauterine.setChecked(false);
                        }

                        if(mgtt.isStillBirth() == true)
                        {
                            checkBoxStillBirth.setChecked(true);
                        }else{
                            checkBoxStillBirth.setChecked(false);
                        }

                        if(mgtt.isNeotanalDeath() == true)
                        {
                            checkBoxNeotanalDeath.setChecked(true);
                        }else{
                            checkBoxNeotanalDeath.setChecked(false);
                        }

                        if(mgtt.isRecurrentMiscarriage() == true)
                        {
                            checkBoxMiscarriages.setChecked(true);
                        }else{
                            checkBoxMiscarriages.setChecked(false);
                        }

                        if(mgtt.isCongenitalAbnormality() == true)
                        {
                            checkBoxCongenital.setChecked(true);
                        }else{
                            checkBoxCongenital.setChecked(false);
                        }

                        if(mgtt.isMascrosomicBaby() == true)
                        {
                            checkBoxMascrosomic.setChecked(true);
                        }else{
                            checkBoxMascrosomic.setChecked(false);
                        }

                        if(mgtt.isFirstDegreeRelativeDiabetes() == true)
                        {
                            checkBoxFirstDegreeDiabetes.setChecked(true);
                        }else{
                            checkBoxFirstDegreeDiabetes.setChecked(false);
                        }

                        if(mgtt.isGlycosuria() == true)
                        {
                            checkBoxGlycosuria.setChecked(true);
                        }else{
                            checkBoxGlycosuria.setChecked(false);
                        }

                        if(mgtt.isEssentialHypertension() == true)
                        {
                            checkBoxEssentialHypertension.setChecked(true);
                        }else{
                            checkBoxEssentialHypertension.setChecked(false);
                        }

                        if(mgtt.isPregnancyInducedHypertension() == true)
                        {
                            checkBoxInducedHypertension.setChecked(true);
                        }else{
                            checkBoxInducedHypertension.setChecked(false);
                        }

                        if(mgtt.isPolyhydramanios() == true)
                        {
                            checkBoxPolyhydramanios.setChecked(true);
                        }else{
                            checkBoxPolyhydramanios.setChecked(false);
                        }

                        if(mgtt.isUterusLargerThanDate() == true)
                        {
                            checkBoxUterus.setChecked(true);
                        }else{
                            checkBoxUterus.setChecked(false);
                        }

                        if(mgtt.isMultiplePregnancy() == true)
                        {
                            checkBoxMultiplePregnancy.setChecked(true);
                        }else{
                            checkBoxMultiplePregnancy.setChecked(false);
                        }

                        if(mgtt.isRecurrentUTI() == true)
                        {
                            checkBoxUTI.setChecked(true);
                        }else{
                            checkBoxUTI.setChecked(true);
                        }
                    }
                    progressBarMGTT.setVisibility(View.GONE);
                    layoutMGTT.setVisibility(View.VISIBLE);
                    buttonSaveMGTT.setVisibility(View.GONE);
                    buttonCancelMGTT.setVisibility(View.GONE);
                }
            }
        });
    }

    private void DisableField()
    {
        checkBoxAge35.setEnabled(false);
        checkBoxBMI.setEnabled(false);
        checkBoxGestational.setEnabled(false);
        checkBoxInstrauterine.setEnabled(false);
        checkBoxStillBirth.setEnabled(false);
        checkBoxNeotanalDeath.setEnabled(false);
        checkBoxMiscarriages.setEnabled(false);
        checkBoxCongenital.setEnabled(false);
        checkBoxMascrosomic.setEnabled(false);
        checkBoxFirstDegreeDiabetes.setEnabled(false);
        checkBoxGlycosuria.setEnabled(false);
        checkBoxEssentialHypertension.setEnabled(false);
        checkBoxInducedHypertension.setEnabled(false);
        checkBoxPolyhydramanios.setEnabled(false);
        checkBoxUterus.setEnabled(false);
        checkBoxMultiplePregnancy.setEnabled(false);
        checkBoxUTI.setEnabled(false);
    }

    private void EnableField()
    {
        checkBoxAge35.setEnabled(true);
        checkBoxBMI.setEnabled(true);
        checkBoxGestational.setEnabled(true);
        checkBoxInstrauterine.setEnabled(true);
        checkBoxStillBirth.setEnabled(true);
        checkBoxNeotanalDeath.setEnabled(true);
        checkBoxMiscarriages.setEnabled(true);
        checkBoxCongenital.setEnabled(true);
        checkBoxMascrosomic.setEnabled(true);
        checkBoxFirstDegreeDiabetes.setEnabled(true);
        checkBoxGlycosuria.setEnabled(true);
        checkBoxEssentialHypertension.setEnabled(true);
        checkBoxInducedHypertension.setEnabled(true);
        checkBoxPolyhydramanios.setEnabled(true);
        checkBoxUterus.setEnabled(true);
        checkBoxMultiplePregnancy.setEnabled(true);
        checkBoxUTI.setEnabled(true);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.action_logout:
                AlertDialog.Builder builder = new AlertDialog.Builder(MGTTActivity.this);
                builder.setTitle("Logout");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        SaveSharedPreference.clearUser(MGTTActivity.this);
                        Intent intent = new Intent(MGTTActivity.this, MainActivity.class);
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
