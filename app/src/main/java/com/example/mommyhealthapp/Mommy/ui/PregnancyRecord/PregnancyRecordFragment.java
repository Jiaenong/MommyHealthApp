package com.example.mommyhealthapp.Mommy.ui.PregnancyRecord;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.mommyhealthapp.Class.Mommy;
import com.example.mommyhealthapp.Nurse.BabyKickRecordActivity;
import com.example.mommyhealthapp.Nurse.EarlyTestActivity;
import com.example.mommyhealthapp.Nurse.ImageRecordActivity;
import com.example.mommyhealthapp.Nurse.MGTTActivity;
import com.example.mommyhealthapp.Nurse.MommyProfileActivity;
import com.example.mommyhealthapp.Nurse.PregnancyControlActivity;
import com.example.mommyhealthapp.Nurse.PregnancyExamRecordActivity;
import com.example.mommyhealthapp.Nurse.PregnancyExaminationActivity;
import com.example.mommyhealthapp.Nurse.SectionNActivity;
import com.example.mommyhealthapp.Nurse.SummaryReportActivity;
import com.example.mommyhealthapp.R;
import com.example.mommyhealthapp.SaveSharedPreference;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class PregnancyRecordFragment extends Fragment {

    private PregnancyRecordViewModel dashboardViewModel;
    private CardView earlyTest, mgtt, pregnantExamination, healthTutorial, babyKicks, graphReport, pregnancyControl, documentRecord;
    private ProgressBar progressBarPregnancyRecord;
    private LinearLayoutCompat layoutPregnancyRecord;

    private FirebaseFirestore mFirebaseFirestore;
    private DocumentReference mDocumentReference;
    private String status, healthInfoId;

    public View onCreateView(@NonNull final LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        //dashboardViewModel =
        //        ViewModelProviders.of(this).get(PregnancyRecordViewModel.class);
        View root = inflater.inflate(R.layout.fragment_pregnancy_record, container, false);
        //dashboardViewModel.getText().observe(this, new Observer<String>() {
          //  @Override
         //   public void onChanged(@Nullable String s) {
         //       textView.setText(s);
         //   }
       // });
        earlyTest = (CardView)root.findViewById(R.id.earlyTest);
        mgtt = (CardView)root.findViewById(R.id.mgtt);
        pregnantExamination = (CardView)root.findViewById(R.id.pregnantExamination);
        healthTutorial = (CardView)root.findViewById(R.id.healthTutorial);
        babyKicks = (CardView)root.findViewById(R.id.babyKicks);
        graphReport = (CardView)root.findViewById(R.id.graphReport);
        pregnancyControl = (CardView)root.findViewById(R.id.pregnancyControl);
        documentRecord = (CardView)root.findViewById(R.id.documentRecord);
        progressBarPregnancyRecord = (ProgressBar)root.findViewById(R.id.progressBarPregnancyRecord);
        layoutPregnancyRecord = (LinearLayoutCompat)root.findViewById(R.id.layoutPregnancyRecord);

        progressBarPregnancyRecord.setVisibility(View.VISIBLE);
        layoutPregnancyRecord.setVisibility(View.GONE);

        mFirebaseFirestore = FirebaseFirestore.getInstance();
        mDocumentReference = mFirebaseFirestore.collection("Mommy").document(SaveSharedPreference.getID(getActivity()));
        mDocumentReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                Mommy mommy = documentSnapshot.toObject(Mommy.class);
                status = mommy.getStatus();
                healthInfoId = mommy.getHealthInfoId();
                progressBarPregnancyRecord.setVisibility(View.GONE);
                layoutPregnancyRecord.setVisibility(View.VISIBLE);
            }
        });

        earlyTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), EarlyTestActivity.class);
                intent.putExtra("Status", status);
                intent.putExtra("healthInfoId", healthInfoId);
                startActivity(intent);
            }
        });

        mgtt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MGTTActivity.class);
                startActivity(intent);
            }
        });

        pregnantExamination.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), PregnancyExamRecordActivity.class);
                startActivity(intent);
            }
        });

        healthTutorial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SectionNActivity.class);
                startActivity(intent);
            }
        });

        babyKicks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), BabyKickRecordActivity.class);
                startActivity(intent);
            }
        });

        graphReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SummaryReportActivity.class);
                startActivity(intent);
            }
        });

        pregnancyControl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), PregnancyControlActivity.class);
                startActivity(intent);
            }
        });

        documentRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ImageRecordActivity.class);
                startActivity(intent);
            }
        });

        return root;
    }
}