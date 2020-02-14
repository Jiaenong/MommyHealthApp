package com.example.mommyhealthapp.Nurse.ui.NurseProfile;

import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.mommyhealthapp.Class.MedicalPersonnel;
import com.example.mommyhealthapp.MainActivity;
import com.example.mommyhealthapp.R;
import com.example.mommyhealthapp.SaveSharedPreference;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.mikhaellopez.circularimageview.CircularImageView;

public class NurseProfileFragment extends Fragment {

    private TextView textViewMinId, textViewMinName, textViewMinEmail, textViewMinAddress, textViewMinIC;
    private Button btnLogout;
    private ProgressBar progressBarLogOut;
    private LinearLayoutCompat layoutProfileDetail;
    private RelativeLayout layoutProfile;
    private CircularImageView imageViewUserPic;

    private FirebaseFirestore mFirebaseFirestore;
    private DocumentReference mDocumentReference;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.nurse_profile_fragment, container, false);
        textViewMinId = (TextView)root.findViewById(R.id.textViewMinId);
        textViewMinName = (TextView)root.findViewById(R.id.textViewMinName);
        textViewMinEmail = (TextView)root.findViewById(R.id.textViewMinEmail);
        textViewMinAddress = (TextView)root.findViewById(R.id.textViewMinAddress);
        textViewMinIC = (TextView)root.findViewById(R.id.textViewMinIC);
        layoutProfile = (RelativeLayout)root.findViewById(R.id.layoutProfile);
        layoutProfileDetail = (LinearLayoutCompat)root.findViewById(R.id.layoutProfileDetail);
        progressBarLogOut = (ProgressBar)root.findViewById(R.id.progressBarLogOut);
        imageViewUserPic = (CircularImageView)root.findViewById(R.id.imageViewUserPic);
        btnLogout = (Button)root.findViewById(R.id.btnLogout);

        String mdID = SaveSharedPreference.getID(getActivity());
        mFirebaseFirestore = FirebaseFirestore.getInstance();
        mDocumentReference = mFirebaseFirestore.collection("MedicalPersonnel").document(mdID);

        progressBarLogOut.setVisibility(View.VISIBLE);
        layoutProfile.setVisibility(View.GONE);
        layoutProfileDetail.setVisibility(View.GONE);

        mDocumentReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                MedicalPersonnel md = documentSnapshot.toObject(MedicalPersonnel.class);
                if(md.getImageUrl().equals(""))
                {
                    imageViewUserPic.setImageResource(R.drawable.user);
                }else{
                    Glide.with(getActivity()).load(md.getImageUrl()).into(imageViewUserPic);
                }
                textViewMinId.setText(md.getMedicalPersonnelId());
                textViewMinName.setText(md.getName());
                textViewMinEmail.setText(md.getEmail());
                textViewMinAddress.setText(md.getAddress());
                textViewMinIC.setText(md.getIC());
                progressBarLogOut.setVisibility(View.GONE);
                layoutProfile.setVisibility(View.VISIBLE);
                layoutProfileDetail.setVisibility(View.VISIBLE);
            }
        });

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SaveSharedPreference.clearUser(getActivity());
                Intent intent = new Intent(getActivity(), MainActivity.class);
                startActivity(intent);
                getActivity().finish();
            }
        });

        return root;
    }


}
