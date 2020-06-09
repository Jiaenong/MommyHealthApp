package com.example.mommyhealthapp.Nurse.ui.NurseProfile;

import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.mommyhealthapp.Class.MedicalPersonnel;
import com.example.mommyhealthapp.Class.Mommy;
import com.example.mommyhealthapp.MainActivity;
import com.example.mommyhealthapp.R;
import com.example.mommyhealthapp.SaveSharedPreference;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.mikhaellopez.circularimageview.CircularImageView;

public class NurseProfileFragment extends Fragment {

    private TextView textViewMinId, textViewMinName, textViewMinEmail, textViewMinAddress, textViewMinIC;
    private Button btnLogout, buttonUpdatePW, buttonConfirm, buttonCancel;
    private TextInputLayout mpCurrentPassword, mpNewPassword,mpConfirmPassword;
    private TextInputEditText mpsCurrentPassword, mpsNewPassword,mpsConfirmPassword;
    private ProgressBar progressBarLogOut;
    private LinearLayoutCompat layoutProfileDetail;
    private RelativeLayout layoutProfile;
    private CircularImageView imageViewUserPic;
    private String tag;

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
        mpsCurrentPassword = (TextInputEditText) root.findViewById(R.id.mpsCurrentPassword);
        mpsNewPassword = (TextInputEditText)root.findViewById(R.id.mpsNewPassword);
        mpsConfirmPassword = (TextInputEditText)root.findViewById(R.id.mpsConfirmPassword);
        mpCurrentPassword = (TextInputLayout)root.findViewById(R.id.mpCurrentPassword);
        mpNewPassword = (TextInputLayout)root.findViewById(R.id.mpNewPassword);
        mpConfirmPassword = (TextInputLayout)root.findViewById(R.id.mpConfirmPassword);
        buttonUpdatePW = (Button)root.findViewById(R.id.buttonUpdatePW);
        buttonConfirm = (Button)root.findViewById(R.id.buttonConfirm);
        buttonCancel = (Button)root.findViewById(R.id.buttonCancel);
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
                mpCurrentPassword.setVisibility(View.GONE);
                mpNewPassword.setVisibility(View.GONE);
                mpConfirmPassword.setVisibility(View.GONE);
                buttonConfirm.setVisibility(View.GONE);
                buttonCancel.setVisibility(View.GONE);
                try{
                    tag = getArguments().getString("tag");
                    if(tag != null && tag.equals("TAG"))
                    {
                        mpCurrentPassword.setVisibility(View.VISIBLE);
                        mpNewPassword.setVisibility(View.VISIBLE);
                        mpConfirmPassword.setVisibility(View.VISIBLE);
                        buttonUpdatePW.setVisibility(View.VISIBLE);
                        buttonConfirm.setVisibility(View.VISIBLE);
                        buttonCancel.setVisibility(View.VISIBLE);
                    }
                }catch (Exception ex){

                }
            }
        });

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SaveSharedPreference.clearUser(getActivity());
                Intent intent = new Intent(getActivity(), MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                getActivity().finish();
            }
        });

        buttonUpdatePW.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mpCurrentPassword.setVisibility(View.VISIBLE);
                mpNewPassword.setVisibility(View.VISIBLE);
                mpConfirmPassword.setVisibility(View.VISIBLE);
                buttonUpdatePW.setVisibility(View.GONE);
                buttonConfirm.setVisibility(View.VISIBLE);
                buttonCancel.setVisibility(View.VISIBLE);
            }
        });

        buttonConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDocumentReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        MedicalPersonnel mps = documentSnapshot.toObject(MedicalPersonnel.class);
                        String password = mps.getPassword();
                        checkRequiredTextChange();
                        if(mpsCurrentPassword.getText().toString().equals(password)){
                            if(mpsNewPassword.getText().toString().equals(mpsConfirmPassword.getText().toString())){
                                mDocumentReference.update("password", mpsNewPassword.getText().toString());
                                Toast.makeText(getActivity(),"Successfully Updated!", Toast.LENGTH_LONG).show();
                                mpCurrentPassword.setVisibility(View.GONE);
                                mpNewPassword.setVisibility(View.GONE);
                                mpConfirmPassword.setVisibility(View.GONE);
                                buttonConfirm.setVisibility(View.GONE);
                                buttonCancel.setVisibility(View.GONE);
                                buttonUpdatePW.setVisibility(View.VISIBLE);
                                mpsCurrentPassword.setText("");
                                mpsNewPassword.setText("");
                                mpsConfirmPassword.setText("");
                            }
                            else{
                                Toast.makeText(getActivity(),"Confirmation password are not same with the new password", Toast.LENGTH_LONG).show();
                                mpsNewPassword.setText("");
                                mpsConfirmPassword.setText("");
                            }

                        }
                        else{
                            Toast.makeText(getActivity(),"Incorrect with current password!", Toast.LENGTH_LONG).show();
                            mpsCurrentPassword.setText("");
                            mpsNewPassword.setText("");
                            mpsConfirmPassword.setText("");
                        }
                    }
                });
            }
        });

        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mpCurrentPassword.setVisibility(View.GONE);
                mpNewPassword.setVisibility(View.GONE);
                mpConfirmPassword.setVisibility(View.GONE);
                buttonConfirm.setVisibility(View.GONE);
                buttonCancel.setVisibility(View.GONE);
                buttonUpdatePW.setVisibility(View.VISIBLE);
            }
        });

        return root;
    }

    private void checkRequiredTextChange()
    {
        mpsCurrentPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(mpsCurrentPassword.getText().toString().equals(""))
                {
                    mpCurrentPassword.setErrorEnabled(true);
                    mpCurrentPassword.setError("This field is required!");
                }else{
                    mpCurrentPassword.setErrorEnabled(false);
                    mpCurrentPassword.setError(null);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mpsNewPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(mpsNewPassword.getText().toString().equals(""))
                {
                    mpNewPassword.setErrorEnabled(true);
                    mpNewPassword.setError("This field is required!");
                }else{
                    mpNewPassword.setErrorEnabled(false);
                    mpNewPassword.setError(null);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mpsConfirmPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(mpsConfirmPassword.getText().toString().equals(""))
                {
                    mpConfirmPassword.setErrorEnabled(true);
                    mpConfirmPassword.setError("This field is required!");
                }else{
                    mpConfirmPassword.setErrorEnabled(false);
                    mpConfirmPassword.setError(null);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                if(mpsConfirmPassword.getText().toString().equals(mpsNewPassword.getText().toString()))
                {
                    mpConfirmPassword.setError(null);
                }else{
                    mpConfirmPassword.setError("Confirmation password are not same with the new password");
                }
            }
        });
    }

}
