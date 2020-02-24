package com.example.mommyhealthapp.Mommy.ui.PregnancyRecord;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.mommyhealthapp.Class.Mommy;
import com.example.mommyhealthapp.Nurse.EarlyTestActivity;
import com.example.mommyhealthapp.R;
import com.example.mommyhealthapp.SaveSharedPreference;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class PregnancyRecordFragment extends Fragment {

    private PregnancyRecordViewModel dashboardViewModel;
    private CardView earlyTest, mgtt, pregnantExamination, healthTutorial, babyKicks, graphReport;

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

        mFirebaseFirestore = FirebaseFirestore.getInstance();
        mDocumentReference = mFirebaseFirestore.collection("Mommy").document(SaveSharedPreference.getID(getActivity()));
        mDocumentReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                Mommy mommy = documentSnapshot.toObject(Mommy.class);
                status = mommy.getStatus();
                healthInfoId = mommy.getHealthInfoId();
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

        return root;
    }
}