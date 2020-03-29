package com.example.mommyhealthapp.Nurse;

import androidx.annotation.BoolRes;
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
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;

import com.example.mommyhealthapp.Class.BreastExamine;
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

public class BreastExamActivity extends AppCompatActivity {

    private Button btnBESave, btnBECancel;
    private EditText editTextNumOfChildren, editTextMenarcheAge, editTextMonopauseAge;
    private RadioGroup radioGroupHistoryBC, radioGroupFDBC, radioGroupBreastSurgery, radioGroupHormone, radioGroupHormoneC, radioGroupBreastFed, radioGroupMMG, radioGroupLump,
            radioGroupNippleDischarge, radioGroupNippleRetraction, radioGroupDiscomfort, radioGroupAxillary, radioGroupInterpretation;
    private RadioButton radioBtnHistoryBCYes, radioBtnHistoryBCNo, radioBtnFDBCYes, radioBtnFDBCNo, radioBtnBreastSurgeryYes, radioBtnBreastSurgeryNo, radioBtnHormoneYes,
            radioBtnHormoneNo, radioBtnHormoneCYes, radioBtnHormoneCNo, radioBtnBreastFedYes, radioBtnBreastFedNo, radioBtnMMGYes, radioBtnMMGNo, radioBtnLumpLeft, radioBtnLumpRight,
            radioBtnNippleDischargeLeft, radioBtnNippleDIschargeRight, radioBtnNippleRetractionLeft, radioBtnNippleRetractionRight, radioBtnDiscomfortLeft, radioBtnDiscomfortRight,
            radioBtnAxillaryLeft, radioBtnAxillaryRight, radioBtnInNormal, radioBtnInAbnormal;
    private RelativeLayout relativeLayoutBreastExamine;
    private LinearLayoutCompat layoutBreastExamine;
    private ProgressBar progressBarBreastExamine;

    private FirebaseFirestore mFirebaseFirestore;
    private CollectionReference mCollectionReference;

    private String healthInfoId, bloodTestId, key;
    private Boolean isEmpty;
    private int check = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_breast_exam);

        editTextNumOfChildren = (EditText)findViewById(R.id.editTextNumOfChildren);
        editTextMenarcheAge = (EditText)findViewById(R.id.editTextMenarcheAge);
        editTextMonopauseAge = (EditText)findViewById(R.id.editTextMonopauseAge);

        radioGroupHistoryBC = (RadioGroup)findViewById(R.id.radioGroupHistoryBC);
        radioGroupFDBC = (RadioGroup)findViewById(R.id.radioGroupFDBC);
        radioGroupBreastSurgery = (RadioGroup)findViewById(R.id.radioGroupBreastSurgery);
        radioGroupHormone = (RadioGroup)findViewById(R.id.radioGroupHormone);
        radioGroupHormoneC = (RadioGroup)findViewById(R.id.radioGroupHormoneC);
        radioGroupBreastFed = (RadioGroup)findViewById(R.id.radioGroupBreastFed);
        radioGroupMMG = (RadioGroup)findViewById(R.id.radioGroupMMG);
        radioGroupLump = (RadioGroup)findViewById(R.id.radioGroupLump);
        radioGroupNippleDischarge = (RadioGroup)findViewById(R.id.radioGroupNippleDischarge);
        radioGroupNippleRetraction = (RadioGroup)findViewById(R.id.radioGroupNippleRetraction);
        radioGroupDiscomfort = (RadioGroup)findViewById(R.id.radioGroupDiscomfort);
        radioGroupAxillary = (RadioGroup)findViewById(R.id.radioGroupAxillary);
        radioGroupInterpretation = (RadioGroup)findViewById(R.id.radioGroupInterpretation);

        radioBtnHistoryBCYes = (RadioButton)findViewById(R.id.radioBtnHistoryBCYes);
        radioBtnHistoryBCNo = (RadioButton)findViewById(R.id.radioBtnHistoryBCNo);
        radioBtnFDBCYes = (RadioButton)findViewById(R.id.radioBtnFDBCYes);
        radioBtnFDBCNo = (RadioButton)findViewById(R.id.radioBtnFDBCNo);
        radioBtnBreastSurgeryYes = (RadioButton)findViewById(R.id.radioBtnBreastSurgeryYes);
        radioBtnBreastSurgeryNo = (RadioButton)findViewById(R.id.radioBtnBreastSurgeryNo);
        radioBtnHormoneYes = (RadioButton)findViewById(R.id.radioBtnHormoneYes);
        radioBtnHormoneNo = (RadioButton)findViewById(R.id.radioBtnHormoneNo);
        radioBtnHormoneCYes = (RadioButton)findViewById(R.id.radioBtnHormoneCYes);
        radioBtnHormoneCNo = (RadioButton)findViewById(R.id.radioBtnHormoneCNo);
        radioBtnBreastFedYes = (RadioButton)findViewById(R.id.radioBtnBreastFedYes);
        radioBtnBreastFedNo = (RadioButton)findViewById(R.id.radioBtnBreastFedNo);
        radioBtnMMGYes = (RadioButton)findViewById(R.id.radioBtnMMGYes);
        radioBtnMMGNo = (RadioButton)findViewById(R.id.radioBtnMMGNo);
        radioBtnLumpLeft = (RadioButton)findViewById(R.id.radioBtnLumpLeft);
        radioBtnLumpRight = (RadioButton)findViewById(R.id.radioBtnLumpRight);
        radioBtnNippleDischargeLeft = (RadioButton)findViewById(R.id.radioBtnNippleDischargeLeft);
        radioBtnNippleDIschargeRight = (RadioButton)findViewById(R.id.radioBtnNippleDIschargeRight);
        radioBtnNippleRetractionLeft = (RadioButton)findViewById(R.id.radioBtnNippleRetractionLeft);
        radioBtnNippleRetractionRight = (RadioButton)findViewById(R.id.radioBtnNippleRetractionRight);
        radioBtnDiscomfortLeft = (RadioButton)findViewById(R.id.radioBtnDiscomfortLeft);
        radioBtnDiscomfortRight = (RadioButton)findViewById(R.id.radioBtnDiscomfortRight);
        radioBtnAxillaryLeft = (RadioButton)findViewById(R.id.radioBtnAxillaryLeft);
        radioBtnAxillaryRight = (RadioButton)findViewById(R.id.radioBtnAxillaryRight);
        radioBtnInNormal = (RadioButton)findViewById(R.id.radioBtnInNormal);
        radioBtnInAbnormal = (RadioButton)findViewById(R.id.radioBtnInAbnormal);

        relativeLayoutBreastExamine = (RelativeLayout)findViewById(R.id.relativeLayoutBreastExamine);
        layoutBreastExamine = (LinearLayoutCompat)findViewById(R.id.layoutBreastExamine);
        progressBarBreastExamine = (ProgressBar)findViewById(R.id.progressBarBreastExamine);

        btnBESave = (Button)findViewById(R.id.btnBESave);
        btnBECancel = (Button)findViewById(R.id.btnBECancel);

        Intent intent = getIntent();
        bloodTestId = intent.getStringExtra("bloodTestId");
        healthInfoId = intent.getStringExtra("healthInfoId");

        btnBECancel.setVisibility(View.GONE);
        progressBarBreastExamine.setVisibility(View.VISIBLE);
        layoutBreastExamine.setVisibility(View.GONE);

        mFirebaseFirestore = FirebaseFirestore.getInstance();
        mCollectionReference = mFirebaseFirestore.collection("MommyHealthInfo/"+healthInfoId+"/BloodTest/"+bloodTestId+"/BreastExamine");
        if(SaveSharedPreference.getUser(BreastExamActivity.this).equals("Mommy")){
            MommyLogIn();
        }
        else{
            mCollectionReference.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                @Override
                public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                    if(queryDocumentSnapshots.isEmpty())
                    {
                        isEmpty = true;
                        progressBarBreastExamine.setVisibility(View.GONE);
                        layoutBreastExamine.setVisibility(View.VISIBLE);
                    }else{
                        isEmpty = false;
                        DisableField();
                        for(QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots)
                        {
                            key = documentSnapshot.getId();
                            BreastExamine be = documentSnapshot.toObject(BreastExamine.class);
                            editTextNumOfChildren.setText(be.getNumOfChildren()+"");
                            editTextMenarcheAge.setText(be.getMenarcheAge()+"");
                            editTextMonopauseAge.setText(be.getMonopauseAge()+"");
                            for(int i=0; i<radioGroupHistoryBC.getChildCount(); i++)
                            {
                                if(((RadioButton)radioGroupHistoryBC.getChildAt(i)).getText().toString().equals(be.getBreastCancelHistory()))
                                {
                                    ((RadioButton)radioGroupHistoryBC.getChildAt(i)).setChecked(true);
                                }
                            }
                            for(int i=0; i<radioGroupFDBC.getChildCount(); i++)
                            {
                                if(((RadioButton)radioGroupFDBC.getChildAt(i)).getText().toString().equals(be.getFirstDegreeBreastCancel()))
                                {
                                    ((RadioButton)radioGroupFDBC.getChildAt(i)).setChecked(true);
                                }
                            }
                            for(int i=0; i<radioGroupBreastSurgery.getChildCount(); i++)
                            {
                                if(((RadioButton)radioGroupBreastSurgery.getChildAt(i)).getText().toString().equals(be.getPreviousBreastSurgery()))
                                {
                                    ((RadioButton)radioGroupBreastSurgery.getChildAt(i)).setChecked(true);
                                }
                            }
                            for(int i=0; i<radioGroupHormone.getChildCount(); i++)
                            {
                                if(((RadioButton)radioGroupHormone.getChildAt(i)).getText().toString().equals(be.getHormoneReplacementTherapy()))
                                {
                                    ((RadioButton)radioGroupHormone.getChildAt(i)).setChecked(true);
                                }
                            }
                            for(int i=0; i<radioGroupHormoneC.getChildCount(); i++)
                            {
                                if(((RadioButton)radioGroupHormoneC.getChildAt(i)).getText().toString().equals(be.getHormoneContraceptive()))
                                {
                                    ((RadioButton)radioGroupHormoneC.getChildAt(i)).setChecked(true);
                                }
                            }
                            for(int i=0; i<radioGroupBreastFed.getChildCount(); i++)
                            {
                                if(((RadioButton)radioGroupBreastFed.getChildAt(i)).getText().toString().equals(be.getBreastFed()))
                                {
                                    ((RadioButton)radioGroupBreastFed.getChildAt(i)).setChecked(true);
                                }
                            }
                            for(int i=0; i<radioGroupMMG.getChildCount(); i++)
                            {
                                if(((RadioButton)radioGroupMMG.getChildAt(i)).getText().toString().equals(be.getPreviousMMG()))
                                {
                                    ((RadioButton)radioGroupMMG.getChildAt(i)).setChecked(true);
                                }
                            }
                            for(int i=0; i<radioGroupLump.getChildCount(); i++)
                            {
                                if(((RadioButton)radioGroupLump.getChildAt(i)).getText().toString().equals(be.getLump()))
                                {
                                    ((RadioButton)radioGroupLump.getChildAt(i)).setChecked(true);
                                }
                            }
                            for(int i=0; i<radioGroupNippleDischarge.getChildCount(); i++)
                            {
                                if(((RadioButton)radioGroupNippleDischarge.getChildAt(i)).getText().toString().equals(be.getNippleDischarge()))
                                {
                                    ((RadioButton)radioGroupNippleDischarge.getChildAt(i)).setChecked(true);
                                }
                            }
                            for(int i=0; i<radioGroupNippleRetraction.getChildCount(); i++)
                            {
                                if(((RadioButton)radioGroupNippleRetraction.getChildAt(i)).getText().toString().equals(be.getNippleRetraction()))
                                {
                                    ((RadioButton)radioGroupNippleRetraction.getChildAt(i)).setChecked(true);
                                }
                            }
                            for(int i=0; i<radioGroupDiscomfort.getChildCount(); i++)
                            {
                                if(((RadioButton)radioGroupDiscomfort.getChildAt(i)).getText().toString().equals(be.getDiscomfort()))
                                {
                                    ((RadioButton)radioGroupDiscomfort.getChildAt(i)).setChecked(true);
                                }
                            }
                            for(int i=0; i<radioGroupAxillary.getChildCount(); i++)
                            {
                                if(((RadioButton)radioGroupAxillary.getChildAt(i)).getText().toString().equals(be.getAxillaryNodesSwelling()))
                                {
                                    ((RadioButton)radioGroupAxillary.getChildAt(i)).setChecked(true);
                                }
                            }
                            for(int i=0; i<radioGroupInterpretation.getChildCount(); i++)
                            {
                                if(((RadioButton)radioGroupInterpretation.getChildAt(i)).getText().toString().equals(be.getClinicalInterpretation()))
                                {
                                    ((RadioButton)radioGroupInterpretation.getChildAt(i)).setChecked(true);
                                }
                            }
                            progressBarBreastExamine.setVisibility(View.GONE);
                            layoutBreastExamine.setVisibility(View.VISIBLE);
                            btnBESave.setText("Update");
                        }
                    }
                }
            });
        }

        btnBESave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isEmpty == true)
                {
                    int numOfChildren = Integer.parseInt(editTextNumOfChildren.getText().toString());
                    int menarcheAge = Integer.parseInt(editTextMenarcheAge.getText().toString());
                    int monopauseAge = Integer.parseInt(editTextMonopauseAge.getText().toString());

                    int radioGroupBCHistoryId = radioGroupHistoryBC.getCheckedRadioButtonId();
                    RadioButton radioButtonBCHistory = (RadioButton)findViewById(radioGroupBCHistoryId);
                    String breastCancelHistory = radioButtonBCHistory.getText().toString();

                    int radioGroupFDBCId = radioGroupFDBC.getCheckedRadioButtonId();
                    RadioButton radioButtonFDBC = (RadioButton)findViewById(radioGroupFDBCId);
                    String firstDegreeBreastCancel = radioButtonFDBC.getText().toString();

                    int radioGroupBreastSurgeryId = radioGroupBreastSurgery.getCheckedRadioButtonId();
                    RadioButton radioButtonBreastSurgery = (RadioButton)findViewById(radioGroupBreastSurgeryId);
                    String previousBreastSurgery = radioButtonBreastSurgery.getText().toString();

                    int radioGroupHormoneId = radioGroupHormone.getCheckedRadioButtonId();
                    RadioButton radioButtonHormone = (RadioButton)findViewById(radioGroupHormoneId);
                    String hormoneReplacementTherapy = radioButtonHormone.getText().toString();

                    int radioGroupHormoneCId = radioGroupHormoneC.getCheckedRadioButtonId();
                    RadioButton radioButtonHormoneC = (RadioButton)findViewById(radioGroupHormoneCId);
                    String hormoneContraceptive = radioButtonHormoneC.getText().toString();

                    int radioGroupBreastFedId = radioGroupBreastFed.getCheckedRadioButtonId();
                    RadioButton radioButtonBreastFed = (RadioButton)findViewById(radioGroupBreastFedId);
                    String breastFed = radioButtonBreastFed.getText().toString();

                    int radioGroupMMGId = radioGroupMMG.getCheckedRadioButtonId();
                    RadioButton radioButtonMMG = (RadioButton)findViewById(radioGroupMMGId);
                    String previousMMG = radioButtonMMG.getText().toString();

                    int radioGroupLumpId = radioGroupLump.getCheckedRadioButtonId();
                    RadioButton radioButtonLump = (RadioButton)findViewById(radioGroupLumpId);
                    String lump = radioButtonLump.getText().toString();

                    int radioGroupNippleDischargeId = radioGroupNippleDischarge.getCheckedRadioButtonId();
                    RadioButton radioButtonNippleDischarge = (RadioButton)findViewById(radioGroupNippleDischargeId);
                    String nippleDischarge = radioButtonNippleDischarge.getText().toString();

                    int radioGroupNippleRetractionId = radioGroupNippleRetraction.getCheckedRadioButtonId();
                    RadioButton radioButtonNippleRetraction = (RadioButton)findViewById(radioGroupNippleRetractionId);
                    String nippleRetraction = radioButtonNippleRetraction.getText().toString();

                    int radioGroupDiscomfortId = radioGroupDiscomfort.getCheckedRadioButtonId();
                    RadioButton radioButtonDiscomfort = (RadioButton)findViewById(radioGroupDiscomfortId);
                    String discomfort = radioButtonDiscomfort.getText().toString();

                    int radioGroupAxillaryId = radioGroupAxillary.getCheckedRadioButtonId();
                    RadioButton radioButtonAxillary = (RadioButton)findViewById(radioGroupAxillaryId);
                    String axillaryNodesSwelling = radioButtonAxillary.getText().toString();

                    int radioGroupInterpretationId = radioGroupInterpretation.getCheckedRadioButtonId();
                    RadioButton radioButtonInterpretation = (RadioButton)findViewById(radioGroupInterpretationId);
                    String clinicalInterpretation = radioButtonInterpretation.getText().toString();

                    String medicalPersonnelId = SaveSharedPreference.getID(BreastExamActivity.this);
                    Date today = new Date();

                    BreastExamine be = new BreastExamine(numOfChildren, menarcheAge, monopauseAge, breastCancelHistory, firstDegreeBreastCancel, previousBreastSurgery, hormoneReplacementTherapy,
                            hormoneContraceptive, breastFed, previousMMG, lump, nippleDischarge, nippleRetraction, discomfort, axillaryNodesSwelling, clinicalInterpretation, medicalPersonnelId,
                            today);

                    mCollectionReference.add(be).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                            AlertDialog.Builder builder = new AlertDialog.Builder(BreastExamActivity.this);
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
                }else{
                    check++;
                    if(check == 1)
                    {
                        EnableField();
                        btnBECancel.setVisibility(View.VISIBLE);
                    }else if(check == 2)
                    {
                        DocumentReference mDocumentReference = mFirebaseFirestore.document("MommyHealthInfo/"+healthInfoId+"/BloodTest/"+bloodTestId+"/BreastExamine/"+key);
                        int radioGroupBCHistoryId = radioGroupHistoryBC.getCheckedRadioButtonId();
                        RadioButton radioButtonBCHistory = (RadioButton)findViewById(radioGroupBCHistoryId);
                        String breastCancelHistory = radioButtonBCHistory.getText().toString();

                        int radioGroupFDBCId = radioGroupFDBC.getCheckedRadioButtonId();
                        RadioButton radioButtonFDBC = (RadioButton)findViewById(radioGroupFDBCId);
                        String firstDegreeBreastCancel = radioButtonFDBC.getText().toString();

                        int radioGroupBreastSurgeryId = radioGroupBreastSurgery.getCheckedRadioButtonId();
                        RadioButton radioButtonBreastSurgery = (RadioButton)findViewById(radioGroupBreastSurgeryId);
                        String previousBreastSurgery = radioButtonBreastSurgery.getText().toString();

                        int radioGroupHormoneId = radioGroupHormone.getCheckedRadioButtonId();
                        RadioButton radioButtonHormone = (RadioButton)findViewById(radioGroupHormoneId);
                        String hormoneReplacementTherapy = radioButtonHormone.getText().toString();

                        int radioGroupHormoneCId = radioGroupHormoneC.getCheckedRadioButtonId();
                        RadioButton radioButtonHormoneC = (RadioButton)findViewById(radioGroupHormoneCId);
                        String hormoneContraceptive = radioButtonHormoneC.getText().toString();

                        int radioGroupBreastFedId = radioGroupBreastFed.getCheckedRadioButtonId();
                        RadioButton radioButtonBreastFed = (RadioButton)findViewById(radioGroupBreastFedId);
                        String breastFed = radioButtonBreastFed.getText().toString();

                        int radioGroupMMGId = radioGroupMMG.getCheckedRadioButtonId();
                        RadioButton radioButtonMMG = (RadioButton)findViewById(radioGroupMMGId);
                        String previousMMG = radioButtonMMG.getText().toString();

                        int radioGroupLumpId = radioGroupLump.getCheckedRadioButtonId();
                        RadioButton radioButtonLump = (RadioButton)findViewById(radioGroupLumpId);
                        String lump = radioButtonLump.getText().toString();

                        int radioGroupNippleDischargeId = radioGroupNippleDischarge.getCheckedRadioButtonId();
                        RadioButton radioButtonNippleDischarge = (RadioButton)findViewById(radioGroupNippleDischargeId);
                        String nippleDischarge = radioButtonNippleDischarge.getText().toString();

                        int radioGroupNippleRetractionId = radioGroupNippleRetraction.getCheckedRadioButtonId();
                        RadioButton radioButtonNippleRetraction = (RadioButton)findViewById(radioGroupNippleRetractionId);
                        String nippleRetraction = radioButtonNippleRetraction.getText().toString();

                        int radioGroupDiscomfortId = radioGroupDiscomfort.getCheckedRadioButtonId();
                        RadioButton radioButtonDiscomfort = (RadioButton)findViewById(radioGroupDiscomfortId);
                        String discomfort = radioButtonDiscomfort.getText().toString();

                        int radioGroupAxillaryId = radioGroupAxillary.getCheckedRadioButtonId();
                        RadioButton radioButtonAxillary = (RadioButton)findViewById(radioGroupAxillaryId);
                        String axillaryNodesSwelling = radioButtonAxillary.getText().toString();

                        int radioGroupInterpretationId = radioGroupInterpretation.getCheckedRadioButtonId();
                        RadioButton radioButtonInterpretation = (RadioButton)findViewById(radioGroupInterpretationId);
                        String clinicalInterpretation = radioButtonInterpretation.getText().toString();

                        Date createdDate = new Date();

                        mDocumentReference.update("numOfChildren", Integer.parseInt(editTextNumOfChildren.getText().toString()));
                        mDocumentReference.update("menarcheAge", Integer.parseInt(editTextMenarcheAge.getText().toString()));
                        mDocumentReference.update("monopauseAge", Integer.parseInt(editTextMonopauseAge.getText().toString()));
                        mDocumentReference.update("breastCancelHistory", breastCancelHistory);
                        mDocumentReference.update("firstDegreeBreastCancel", firstDegreeBreastCancel);
                        mDocumentReference.update("previousBreastSurgery",previousBreastSurgery);
                        mDocumentReference.update("hormoneReplacementTherapy", hormoneReplacementTherapy);
                        mDocumentReference.update("hormoneContraceptive", hormoneContraceptive);
                        mDocumentReference.update("breastFed", breastFed);
                        mDocumentReference.update("previousMMG", previousMMG);
                        mDocumentReference.update("lump", lump);
                        mDocumentReference.update("nippleDischarge", nippleDischarge);
                        mDocumentReference.update("nippleRetraction", nippleRetraction);
                        mDocumentReference.update("discomfort", discomfort);
                        mDocumentReference.update("axillaryNodesSwelling", axillaryNodesSwelling);
                        mDocumentReference.update("clinicalInterpretation",clinicalInterpretation);
                        mDocumentReference.update("medicalPersonnelId", SaveSharedPreference.getID(BreastExamActivity.this));
                        mDocumentReference.update("createdDate", createdDate);
                        Snackbar snackbar = Snackbar.make(relativeLayoutBreastExamine, "Updated Successfully!", Snackbar.LENGTH_LONG);
                        snackbar.show();
                        btnBECancel.setVisibility(View.GONE);
                        DisableField();
                        check = 0;

                    }
                }
            }
        });

        btnBECancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DisableField();
                btnBECancel.setVisibility(View.GONE);
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
                    progressBarBreastExamine.setVisibility(View.GONE);
                    layoutBreastExamine.setVisibility(View.VISIBLE);
                    btnBECancel.setVisibility(View.GONE);
                    btnBESave.setVisibility(View.GONE);
                    DisableField();
                }else{
                    isEmpty = false;
                    DisableField();
                    for(QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots)
                    {
                        key = documentSnapshot.getId();
                        BreastExamine be = documentSnapshot.toObject(BreastExamine.class);
                        editTextNumOfChildren.setText(be.getNumOfChildren()+"");
                        editTextMenarcheAge.setText(be.getMenarcheAge()+"");
                        editTextMonopauseAge.setText(be.getMonopauseAge()+"");
                        for(int i=0; i<radioGroupHistoryBC.getChildCount(); i++)
                        {
                            if(((RadioButton)radioGroupHistoryBC.getChildAt(i)).getText().toString().equals(be.getBreastCancelHistory()))
                            {
                                ((RadioButton)radioGroupHistoryBC.getChildAt(i)).setChecked(true);
                            }
                        }
                        for(int i=0; i<radioGroupFDBC.getChildCount(); i++)
                        {
                            if(((RadioButton)radioGroupFDBC.getChildAt(i)).getText().toString().equals(be.getFirstDegreeBreastCancel()))
                            {
                                ((RadioButton)radioGroupFDBC.getChildAt(i)).setChecked(true);
                            }
                        }
                        for(int i=0; i<radioGroupBreastSurgery.getChildCount(); i++)
                        {
                            if(((RadioButton)radioGroupBreastSurgery.getChildAt(i)).getText().toString().equals(be.getPreviousBreastSurgery()))
                            {
                                ((RadioButton)radioGroupBreastSurgery.getChildAt(i)).setChecked(true);
                            }
                        }
                        for(int i=0; i<radioGroupHormone.getChildCount(); i++)
                        {
                            if(((RadioButton)radioGroupHormone.getChildAt(i)).getText().toString().equals(be.getHormoneReplacementTherapy()))
                            {
                                ((RadioButton)radioGroupHormone.getChildAt(i)).setChecked(true);
                            }
                        }
                        for(int i=0; i<radioGroupHormoneC.getChildCount(); i++)
                        {
                            if(((RadioButton)radioGroupHormoneC.getChildAt(i)).getText().toString().equals(be.getHormoneContraceptive()))
                            {
                                ((RadioButton)radioGroupHormoneC.getChildAt(i)).setChecked(true);
                            }
                        }
                        for(int i=0; i<radioGroupBreastFed.getChildCount(); i++)
                        {
                            if(((RadioButton)radioGroupBreastFed.getChildAt(i)).getText().toString().equals(be.getBreastFed()))
                            {
                                ((RadioButton)radioGroupBreastFed.getChildAt(i)).setChecked(true);
                            }
                        }
                        for(int i=0; i<radioGroupMMG.getChildCount(); i++)
                        {
                            if(((RadioButton)radioGroupMMG.getChildAt(i)).getText().toString().equals(be.getPreviousMMG()))
                            {
                                ((RadioButton)radioGroupMMG.getChildAt(i)).setChecked(true);
                            }
                        }
                        for(int i=0; i<radioGroupLump.getChildCount(); i++)
                        {
                            if(((RadioButton)radioGroupLump.getChildAt(i)).getText().toString().equals(be.getLump()))
                            {
                                ((RadioButton)radioGroupLump.getChildAt(i)).setChecked(true);
                            }
                        }
                        for(int i=0; i<radioGroupNippleDischarge.getChildCount(); i++)
                        {
                            if(((RadioButton)radioGroupNippleDischarge.getChildAt(i)).getText().toString().equals(be.getNippleDischarge()))
                            {
                                ((RadioButton)radioGroupNippleDischarge.getChildAt(i)).setChecked(true);
                            }
                        }
                        for(int i=0; i<radioGroupNippleRetraction.getChildCount(); i++)
                        {
                            if(((RadioButton)radioGroupNippleRetraction.getChildAt(i)).getText().toString().equals(be.getNippleRetraction()))
                            {
                                ((RadioButton)radioGroupNippleRetraction.getChildAt(i)).setChecked(true);
                            }
                        }
                        for(int i=0; i<radioGroupDiscomfort.getChildCount(); i++)
                        {
                            if(((RadioButton)radioGroupDiscomfort.getChildAt(i)).getText().toString().equals(be.getDiscomfort()))
                            {
                                ((RadioButton)radioGroupDiscomfort.getChildAt(i)).setChecked(true);
                            }
                        }
                        for(int i=0; i<radioGroupAxillary.getChildCount(); i++)
                        {
                            if(((RadioButton)radioGroupAxillary.getChildAt(i)).getText().toString().equals(be.getAxillaryNodesSwelling()))
                            {
                                ((RadioButton)radioGroupAxillary.getChildAt(i)).setChecked(true);
                            }
                        }
                        for(int i=0; i<radioGroupInterpretation.getChildCount(); i++)
                        {
                            if(((RadioButton)radioGroupInterpretation.getChildAt(i)).getText().toString().equals(be.getClinicalInterpretation()))
                            {
                                ((RadioButton)radioGroupInterpretation.getChildAt(i)).setChecked(true);
                            }
                        }
                        progressBarBreastExamine.setVisibility(View.GONE);
                        layoutBreastExamine.setVisibility(View.VISIBLE);
                        btnBESave.setVisibility(View.GONE);
                        btnBECancel.setVisibility(View.GONE);
                    }
                }
            }
        });
    }
    private void DisableField()
    {
        editTextNumOfChildren.setEnabled(false);
        editTextNumOfChildren.setTextColor(Color.parseColor("#000000"));
        editTextMenarcheAge.setEnabled(false);
        editTextMenarcheAge.setTextColor(Color.parseColor("#000000"));
        editTextMonopauseAge.setEnabled(false);
        editTextMonopauseAge.setTextColor(Color.parseColor("#000000"));
        for(int i=0; i<radioGroupHistoryBC.getChildCount(); i++)
        {
            ((RadioButton)radioGroupHistoryBC.getChildAt(i)).setEnabled(false);
        }
        for(int i=0; i<radioGroupFDBC.getChildCount(); i++)
        {
            ((RadioButton)radioGroupFDBC.getChildAt(i)).setEnabled(false);
        }
        for(int i=0; i<radioGroupBreastSurgery.getChildCount(); i++)
        {
            ((RadioButton)radioGroupBreastSurgery.getChildAt(i)).setEnabled(false);
        }
        for(int i=0; i<radioGroupHormone.getChildCount(); i++)
        {
            ((RadioButton)radioGroupHormone.getChildAt(i)).setEnabled(false);
        }
        for(int i=0; i<radioGroupHormoneC.getChildCount(); i++)
        {
            ((RadioButton)radioGroupHormoneC.getChildAt(i)).setEnabled(false);
        }
        for(int i=0; i<radioGroupBreastFed.getChildCount(); i++)
        {
            ((RadioButton)radioGroupBreastFed.getChildAt(i)).setEnabled(false);
        }
        for(int i=0; i<radioGroupMMG.getChildCount(); i++)
        {
            ((RadioButton)radioGroupMMG.getChildAt(i)).setEnabled(false);
        }
        for(int i=0; i<radioGroupLump.getChildCount(); i++)
        {
            ((RadioButton)radioGroupLump.getChildAt(i)).setEnabled(false);
        }
        for(int i=0; i<radioGroupNippleDischarge.getChildCount(); i++)
        {
            ((RadioButton)radioGroupNippleDischarge.getChildAt(i)).setEnabled(false);
        }
        for(int i=0; i<radioGroupNippleRetraction.getChildCount(); i++)
        {
            ((RadioButton)radioGroupNippleRetraction.getChildAt(i)).setEnabled(false);
        }
        for(int i=0; i<radioGroupDiscomfort.getChildCount(); i++)
        {
            ((RadioButton)radioGroupDiscomfort.getChildAt(i)).setEnabled(false);
        }
        for(int i=0; i<radioGroupAxillary.getChildCount(); i++)
        {
            ((RadioButton)radioGroupAxillary.getChildAt(i)).setEnabled(false);
        }
        for(int i=0; i<radioGroupInterpretation.getChildCount(); i++)
        {
            ((RadioButton)radioGroupInterpretation.getChildAt(i)).setEnabled(false);
        }
    }

    private void EnableField()
    {
        editTextNumOfChildren.setEnabled(true);
        editTextMenarcheAge.setEnabled(true);
        editTextMonopauseAge.setEnabled(true);
        for(int i=0; i<radioGroupHistoryBC.getChildCount(); i++)
        {
            ((RadioButton)radioGroupHistoryBC.getChildAt(i)).setEnabled(true);
        }
        for(int i=0; i<radioGroupFDBC.getChildCount(); i++)
        {
            ((RadioButton)radioGroupFDBC.getChildAt(i)).setEnabled(true);
        }
        for(int i=0; i<radioGroupBreastSurgery.getChildCount(); i++)
        {
            ((RadioButton)radioGroupBreastSurgery.getChildAt(i)).setEnabled(true);
        }
        for(int i=0; i<radioGroupHormone.getChildCount(); i++)
        {
            ((RadioButton)radioGroupHormone.getChildAt(i)).setEnabled(true);
        }
        for(int i=0; i<radioGroupHormoneC.getChildCount(); i++)
        {
            ((RadioButton)radioGroupHormoneC.getChildAt(i)).setEnabled(true);
        }
        for(int i=0; i<radioGroupBreastFed.getChildCount(); i++)
        {
            ((RadioButton)radioGroupBreastFed.getChildAt(i)).setEnabled(true);
        }
        for(int i=0; i<radioGroupMMG.getChildCount(); i++)
        {
            ((RadioButton)radioGroupMMG.getChildAt(i)).setEnabled(true);
        }
        for(int i=0; i<radioGroupLump.getChildCount(); i++)
        {
            ((RadioButton)radioGroupLump.getChildAt(i)).setEnabled(true);
        }
        for(int i=0; i<radioGroupNippleDischarge.getChildCount(); i++)
        {
            ((RadioButton)radioGroupNippleDischarge.getChildAt(i)).setEnabled(true);
        }
        for(int i=0; i<radioGroupNippleRetraction.getChildCount(); i++)
        {
            ((RadioButton)radioGroupNippleRetraction.getChildAt(i)).setEnabled(true);
        }
        for(int i=0; i<radioGroupDiscomfort.getChildCount(); i++)
        {
            ((RadioButton)radioGroupDiscomfort.getChildAt(i)).setEnabled(true);
        }
        for(int i=0; i<radioGroupAxillary.getChildCount(); i++)
        {
            ((RadioButton)radioGroupAxillary.getChildAt(i)).setEnabled(true);
        }
        for(int i=0; i<radioGroupInterpretation.getChildCount(); i++)
        {
            ((RadioButton)radioGroupInterpretation.getChildAt(i)).setEnabled(true);
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
