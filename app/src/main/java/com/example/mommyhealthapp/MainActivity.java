package com.example.mommyhealthapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mommyhealthapp.Class.MedicalPersonnel;
import com.example.mommyhealthapp.Class.Mommy;
import com.example.mommyhealthapp.Mommy.MommyHomeActivity;
import com.example.mommyhealthapp.Nurse.NurseHomeActivity;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

import org.w3c.dom.Text;


public class MainActivity extends AppCompatActivity {

    private Button buttonLogin;
    private EditText editTextLoginName, editTextLoginPass;
    private TextInputLayout loginNameLayout, loginPassLayout;
    private ProgressBar progressBarLogIn;
    private TextView textForgetPass;
    private int check;
    private String tag, deviceToken;

    private FirebaseFirestore mFirebaseFirestore;
    private CollectionReference mdCollectionReference, motherCollectionReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonLogin = (Button)findViewById(R.id.btnLogin);
        editTextLoginName = (EditText)findViewById(R.id.editTextLoginName);
        editTextLoginPass = (EditText)findViewById(R.id.editTextLoginPass);
        loginNameLayout = (TextInputLayout)findViewById(R.id.loginNameLayout);
        loginPassLayout = (TextInputLayout)findViewById(R.id.loginPassLayout);
        progressBarLogIn = (ProgressBar)findViewById(R.id.progressBarLogIn);
        textForgetPass = (TextView)findViewById(R.id.textForgetPass);

        mFirebaseFirestore = FirebaseFirestore.getInstance();
        mdCollectionReference = mFirebaseFirestore.collection("MedicalPersonnel");
        motherCollectionReference = mFirebaseFirestore.collection("Mommy");

        Intent intentPass = getIntent();
        tag = intentPass.getStringExtra("tag");

        if(SaveSharedPreference.getCheckLogin(MainActivity.this))
        {
            Log.i("TestingMain", SaveSharedPreference.getUser(MainActivity.this));
            if(SaveSharedPreference.getUser(MainActivity.this).equals("medicalPersonnel"))
            {
                Intent intent = new Intent(MainActivity.this, NurseHomeActivity.class);
                startActivity(intent);
                finish();
            }else if(SaveSharedPreference.getUser(MainActivity.this).equals("Mommy"))
            {
                Intent intent = new Intent(MainActivity.this, MommyHomeActivity.class);
                startActivity(intent);
                finish();
            }
        }

        FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener(new OnSuccessListener<InstanceIdResult>() {
            @Override
            public void onSuccess(InstanceIdResult instanceIdResult) {
                deviceToken = instanceIdResult.getToken();
            }
        });

        checkRequiredField();

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkRequiredFieldBtn() != true)
                {
                    Toast.makeText(MainActivity.this, "Field is empty", Toast.LENGTH_LONG).show();
                }else{
                    progressBarVisible();
                    mdCollectionReference.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                        @Override
                        public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                            for(QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots)
                            {
                                MedicalPersonnel md = documentSnapshot.toObject(MedicalPersonnel.class);
                                String name = editTextLoginName.getText().toString();
                                String pass = editTextLoginPass.getText().toString();
                                if(name.equals(md.getIC()) && pass.equals(md.getPassword()))
                                {
                                    check++;
                                    if(md.getType().equals("doctor"))
                                    {
                                        SaveSharedPreference.setID(MainActivity.this, documentSnapshot.getId());
                                        SaveSharedPreference.setCheckLogin(MainActivity.this, true);
                                        SaveSharedPreference.setUser(MainActivity.this, "doctor");
                                    }else{
                                        SaveSharedPreference.setID(MainActivity.this, documentSnapshot.getId());
                                        SaveSharedPreference.setCheckLogin(MainActivity.this, true);
                                        SaveSharedPreference.setUser(MainActivity.this, "medicalPersonnel");
                                    }
                                    Intent intent = new Intent(MainActivity.this, NurseHomeActivity.class);
                                    intent.putExtra("tag", tag);
                                    startActivity(intent);
                                    finish();
                                    break;
                                }
                            }
                            if(check == 0)
                            {
                                motherCollectionReference.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                                    @Override
                                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                                        for(QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots)
                                        {
                                            Mommy mommy = documentSnapshot.toObject(Mommy.class);
                                            String name = editTextLoginName.getText().toString();
                                            String pass = editTextLoginPass.getText().toString();
                                            if(name.equals(mommy.getMommyIC()) && pass.equals(mommy.getPassword()))
                                            {
                                                check++;
                                                SaveSharedPreference.setID(MainActivity.this, documentSnapshot.getId());
                                                SaveSharedPreference.setCheckLogin(MainActivity.this, true);
                                                SaveSharedPreference.setUser(MainActivity.this, "Mommy");
                                                SaveSharedPreference.setMummyId(MainActivity.this, mommy.getMommyId());
                                                SaveSharedPreference.setHealthInfoId(MainActivity.this, mommy.getHealthInfoId());
                                                DocumentReference mDocumentReference = mFirebaseFirestore.collection("Mommy").document(documentSnapshot.getId());
                                                mDocumentReference.update("deviceToken", deviceToken);
                                                Intent intent = new Intent(MainActivity.this, MommyHomeActivity.class);
                                                intent.putExtra("tag", tag);
                                                startActivity(intent);
                                                finish();
                                                break;
                                            }
                                        }

                                        if(check == 0)
                                        {
                                            progressBarGone();
                                            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                                            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialogInterface, int i) {
                                                    editTextLoginName.setText("");
                                                    editTextLoginPass.setText("");
                                                    return;
                                                }
                                            });
                                            builder.setTitle("Log In Failed");
                                            builder.setMessage("Incorrect username and password !");
                                            AlertDialog alert = builder.create();
                                            alert.setCanceledOnTouchOutside(false);
                                            alert.show();
                                        }
                                    }
                                });
                            }
                        }
                    });
                }
            }
        });

        textForgetPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ForgetPasswordActivity.class);
                startActivity(intent);
            }
        });
    }

    private boolean checkRequiredFieldBtn()
    {
        boolean empty = true;
        if(editTextLoginName.getText().toString().equals(""))
        {
            loginNameLayout.setErrorEnabled(true);
            loginNameLayout.setError("This field is required!");
            empty = false;
        }else{
            loginNameLayout.setErrorEnabled(false);
            loginNameLayout.setError(null);
            empty = true;
        }

        if(editTextLoginPass.getText().toString().equals(""))
        {
            loginPassLayout.setErrorEnabled(true);
            loginPassLayout.setError("This field is required!");
            empty = false;
        }else{
            loginPassLayout.setErrorEnabled(false);
            loginPassLayout.setError(null);
            empty = true;
        }
        return empty;
    }

    private void checkRequiredField()
    {
        editTextLoginName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(editTextLoginName.getText().toString().equals(""))
                {
                    loginNameLayout.setErrorEnabled(true);
                    loginNameLayout.setError("This field is required!");
                }else{
                    loginNameLayout.setErrorEnabled(false);
                    loginNameLayout.setError(null);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        editTextLoginPass.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(editTextLoginPass.getText().toString().equals(""))
                {
                    loginPassLayout.setErrorEnabled(true);
                    loginPassLayout.setError("This field is required!");
                }else{
                    loginPassLayout.setErrorEnabled(false);
                    loginPassLayout.setError(null);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void progressBarVisible()
    {
        loginNameLayout.setVisibility(View.GONE);
        loginPassLayout.setVisibility(View.GONE);
        textForgetPass.setVisibility(View.GONE);
        buttonLogin.setVisibility(View.GONE);
        progressBarLogIn.setVisibility(View.VISIBLE);
    }

    private void progressBarGone()
    {
        loginNameLayout.setVisibility(View.VISIBLE);
        loginPassLayout.setVisibility(View.VISIBLE);
        textForgetPass.setVisibility(View.VISIBLE);
        buttonLogin.setVisibility(View.VISIBLE);
        progressBarLogIn.setVisibility(View.GONE);
    }

}
