package com.example.mommyhealthapp.Mommy.ui.MotherProfile;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.mommyhealthapp.Class.Mommy;
import com.example.mommyhealthapp.MainActivity;
import com.example.mommyhealthapp.R;
import com.example.mommyhealthapp.SaveSharedPreference;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class MotherProfileFragment extends Fragment {

    private MotherProfileModel motherProfileModel;
    private ImageView qrcodeDisplay;
    private TextView motherName, motherIC, motherNationality, motherRace, motherAddress, motherPhone, motherOccupation, motherEducation;
    private Button buttonLogOut;
    private ProgressBar progressBarLogOut;
    private LinearLayoutCompat myProfile;

    private FirebaseFirestore mFirebaseFirestore;
    private DocumentReference mDocumentReference;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
      //  motherProfileModel =
        //        ViewModelProviders.of(this).get(MotherProfileModel.class);
        View root = inflater.inflate(R.layout.fragment_mother_profile, container, false);
        //final TextView textView = root.findViewById(R.id.text_home);
        qrcodeDisplay = (ImageView)root.findViewById(R.id.qrcodeDisplay);
        motherName = (TextView)root.findViewById(R.id.motherName);
        motherIC = (TextView)root.findViewById(R.id.motherIC);
        motherNationality = (TextView)root.findViewById(R.id.motherNationality);
        motherRace = (TextView)root.findViewById(R.id.motherRace);
        motherAddress = (TextView)root.findViewById(R.id.motherAddress);
        motherPhone = (TextView)root.findViewById(R.id.motherPhone);
        motherOccupation = (TextView)root.findViewById(R.id.motherOccupation);
        motherEducation = (TextView)root.findViewById(R.id.motherEducation);
        myProfile = (LinearLayoutCompat)root.findViewById(R.id.myProfile);
        buttonLogOut = (Button)root.findViewById(R.id.buttonLogOut);
        progressBarLogOut = (ProgressBar)root.findViewById(R.id.progressBarLogOut);

        String mommyID = SaveSharedPreference.getID(getActivity());
        mFirebaseFirestore = FirebaseFirestore.getInstance();
        mDocumentReference = mFirebaseFirestore.collection("Mommy").document(mommyID);

        progressBarLogOut.setVisibility(View.VISIBLE);
        myProfile.setVisibility(View.GONE);

        mDocumentReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                Mommy mommy = documentSnapshot.toObject(Mommy.class);
                Log.i("Testing", mommy.getQrcodeImage());
                motherName.setText(mommy.getMommyName());
                motherIC.setText(mommy.getMommyIC());
                motherNationality.setText(mommy.getNationality());
                motherRace.setText(mommy.getRace());
                motherAddress.setText(mommy.getAddress());
                motherPhone.setText(mommy.getPhoneNo());
                motherOccupation.setText(mommy.getOccupation());
                motherEducation.setText(mommy.getEducation());
                Glide.with(getContext()).load(mommy.getQrcodeImage()).into(qrcodeDisplay);
                progressBarLogOut.setVisibility(View.GONE);
                myProfile.setVisibility(View.VISIBLE);
            }
        });

        buttonLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SaveSharedPreference.clearUser(getActivity());
                Intent intent = new Intent(getActivity(), MainActivity.class);
                startActivity(intent);
                getActivity().finish();
            }
        });

        //motherProfileModel.getText().observe(this, new Observer<String>() {
         //   @Override
         //   public void onChanged(@Nullable String s) {
         //       textView.setText(s);
         //   }
        //});
        return root;
    }
}