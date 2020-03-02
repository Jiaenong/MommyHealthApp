package com.example.mommyhealthapp.Mommy.ui.MotherProfile;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.mommyhealthapp.Class.Mommy;
import com.example.mommyhealthapp.MainActivity;
import com.example.mommyhealthapp.Nurse.MommyInfoActivity;
import com.example.mommyhealthapp.R;
import com.example.mommyhealthapp.SaveSharedPreference;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import org.w3c.dom.Text;

public class MotherProfileFragment extends Fragment {

    private MotherProfileModel motherProfileModel;
    private ImageView qrcodeDisplay;
    private TextView motherName, motherIC, motherNationality, motherRace, motherAddress, motherPhone, motherEmail, motherOccupation, motherEducation;
    private Button buttonUpdatePW, buttonConfirm, buttonCancel, buttonLogOut;
    private TextInputLayout mommyCurrentPassword, mommyNewPassword,mommyConfirmPassword;
    private TextInputEditText motherCurrentPassword, motherNewPassword,motherConfirmPassword;
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
        motherEmail = (TextView)root.findViewById(R.id.motherEmail);
        motherOccupation = (TextView)root.findViewById(R.id.motherOccupation);
        motherEducation = (TextView)root.findViewById(R.id.motherEducation);
        motherCurrentPassword = (TextInputEditText) root.findViewById(R.id.motherCurrentPassword);
        motherNewPassword = (TextInputEditText)root.findViewById(R.id.motherNewPassword);
        motherConfirmPassword = (TextInputEditText)root.findViewById(R.id.motherConfirmPassword);
        mommyCurrentPassword = (TextInputLayout)root.findViewById(R.id.mommyCurrentPassword);
        mommyNewPassword = (TextInputLayout)root.findViewById(R.id.mommyNewPassword);
        mommyConfirmPassword = (TextInputLayout)root.findViewById(R.id.mommyConfirmPassword);
        myProfile = (LinearLayoutCompat)root.findViewById(R.id.myProfile);
        buttonUpdatePW = (Button)root.findViewById(R.id.buttonUpdatePW);
        buttonConfirm = (Button)root.findViewById(R.id.buttonConfirm);
        buttonCancel = (Button)root.findViewById(R.id.buttonCancel);
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
                motherEmail.setText(mommy.getEmail());
                motherOccupation.setText(mommy.getOccupation());
                motherEducation.setText(mommy.getEducation());
                Glide.with(getContext()).load(mommy.getQrcodeImage()).into(qrcodeDisplay);
                progressBarLogOut.setVisibility(View.GONE);
                myProfile.setVisibility(View.VISIBLE);
                motherCurrentPassword.setVisibility(View.GONE);
                motherNewPassword.setVisibility(View.GONE);
                motherConfirmPassword.setVisibility(View.GONE);
                buttonConfirm.setVisibility(View.GONE);
                buttonCancel.setVisibility(View.GONE);

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

        buttonUpdatePW.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                motherCurrentPassword.setVisibility(View.VISIBLE);
                motherNewPassword.setVisibility(View.VISIBLE);
                motherConfirmPassword.setVisibility(View.VISIBLE);
                mommyCurrentPassword.setVisibility(View.VISIBLE);
                mommyNewPassword.setVisibility(View.VISIBLE);
                mommyConfirmPassword.setVisibility(View.VISIBLE);
                buttonUpdatePW.setVisibility(View.GONE);
                buttonConfirm.setVisibility(View.VISIBLE);
                buttonCancel.setVisibility(View.VISIBLE);

                buttonConfirm.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mDocumentReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                          @Override
                          public void onSuccess(DocumentSnapshot documentSnapshot) {
                              Mommy mommys = documentSnapshot.toObject(Mommy.class);
                              String password = mommys.getPassword();
                              checkRequiredTextChange();
                              if(motherCurrentPassword.getText().toString().equals(password)){
                                  if(motherNewPassword.getText().toString().equals(motherConfirmPassword.getText().toString())){
                                      mDocumentReference.update("password", motherNewPassword.getText().toString());
                                      Toast.makeText(getActivity(),"Successfully Updated!", Toast.LENGTH_LONG).show();
                                      mommyCurrentPassword.setVisibility(View.GONE);
                                      mommyNewPassword.setVisibility(View.GONE);
                                      mommyConfirmPassword.setVisibility(View.GONE);
                                      buttonConfirm.setVisibility(View.GONE);
                                      buttonCancel.setVisibility(View.GONE);
                                      buttonUpdatePW.setVisibility(View.VISIBLE);
                                      motherCurrentPassword.setText("");
                                      motherNewPassword.setText("");
                                      motherConfirmPassword.setText("");
                                  }
                                  else{
                                      Toast.makeText(getActivity(),"Confirmation password are not same with the new password", Toast.LENGTH_LONG).show();
                                      motherNewPassword.setText("");
                                      motherConfirmPassword.setText("");
                                  }

                              }
                              else{
                                  Toast.makeText(getActivity(),"Incorrect with current password!", Toast.LENGTH_LONG).show();
                                  motherCurrentPassword.setText("");
                                  motherNewPassword.setText("");
                                  motherConfirmPassword.setText("");
                              }
                          }
                      });
                    }
                });

                buttonCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mommyCurrentPassword.setVisibility(View.GONE);
                        mommyNewPassword.setVisibility(View.GONE);
                        mommyConfirmPassword.setVisibility(View.GONE);
                        buttonConfirm.setVisibility(View.GONE);
                        buttonCancel.setVisibility(View.GONE);
                        buttonUpdatePW.setVisibility(View.VISIBLE);
                    }
                });

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

    private void checkRequiredTextChange()
    {
        motherCurrentPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(motherCurrentPassword.getText().toString().equals(""))
                {
                    mommyCurrentPassword.setErrorEnabled(true);
                    mommyCurrentPassword.setError("This field is required!");
                }else{
                    mommyCurrentPassword.setErrorEnabled(false);
                    mommyCurrentPassword.setError(null);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        motherNewPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(motherNewPassword.getText().toString().equals(""))
                {
                    mommyNewPassword.setErrorEnabled(true);
                    mommyNewPassword.setError("This field is required!");
                }else{
                    mommyNewPassword.setErrorEnabled(false);
                    mommyNewPassword.setError(null);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        motherConfirmPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(motherConfirmPassword.getText().toString().equals(""))
                {
                    mommyConfirmPassword.setErrorEnabled(true);
                    mommyConfirmPassword.setError("This field is required!");
                }else{
                    mommyConfirmPassword.setErrorEnabled(false);
                    mommyConfirmPassword.setError(null);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                if(motherConfirmPassword.getText().toString().equals(motherNewPassword.getText().toString()))
                {
                    mommyConfirmPassword.setError(null);
                }else{
                    mommyConfirmPassword.setError("Confirmation password are not same with the new password");
                }
            }
        });
    }
}