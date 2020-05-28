package com.example.mommyhealthapp.Mommy;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.example.mommyhealthapp.Class.Guidebook;
import com.example.mommyhealthapp.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class MotherGuidebook extends AppCompatActivity {

    private TextView guidebookDetails;
    private TextInputLayout txtInputLayoutGuidebook;
    private FirebaseFirestore mFirebaseFirestore;
    private CollectionReference mCollectionReference;
    private DocumentReference mDocumentReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mother_guidebook);

        txtInputLayoutGuidebook = (TextInputLayout)findViewById(R.id.txtInputLayoutGuidebook);
        guidebookDetails = (TextView)findViewById(R.id.guidebookDetails);

        mFirebaseFirestore = FirebaseFirestore.getInstance();
        mDocumentReference = mFirebaseFirestore.collection("Guideline").document("KoFc6Vg20dgeDiJFRdRi");
        mDocumentReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                Guidebook guidebook = documentSnapshot.toObject(Guidebook.class);
                guidebookDetails.setText(guidebook.getContent());
            }
        });
    }
}
