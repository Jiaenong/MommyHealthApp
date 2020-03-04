package com.example.mommyhealthapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Random;

public class ForgetPasswordActivity extends AppCompatActivity {
    private EditText editTextRecoverEmail;
    private TextInputLayout inputlayoutEmailAddress;
    private Button btnRecoverOkay;

    private FirebaseFirestore mFirebaseFirestore;
    private CollectionReference mCollectionReference, nCollectionreference;
    private DocumentReference mDocumentReference;

    private String id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);

        editTextRecoverEmail = (EditText)findViewById(R.id.editTextRecoverEmail);
        inputlayoutEmailAddress = (TextInputLayout) findViewById(R.id.inputlayoutEmailAddress);
        btnRecoverOkay = (Button)findViewById(R.id.btnRecoverOkay);

        mFirebaseFirestore = FirebaseFirestore.getInstance();
        mCollectionReference = mFirebaseFirestore.collection("MedicalPersonnel");
        nCollectionreference = mFirebaseFirestore.collection("Mommy");

        btnRecoverOkay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(editTextRecoverEmail.getText().toString().equals(""))
                {
                    inputlayoutEmailAddress.setErrorEnabled(true);
                    inputlayoutEmailAddress.setError("This field is required");
                }else{
                    String email = editTextRecoverEmail.getText().toString();
                    SendEmail(email);
                }
            }
        });

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void SendEmail(final String email)
    {
        final Random random = new Random();
        final String randomNum = String.format("%04d",random.nextInt(10000));
        final String subject = "Forget Password";
        final String message = "Below is the temporary password for you to login into your account, " +
                "please change the password immediately after login to the system successfully"+"\n"+"\n"+"4 digit :"+randomNum;

        mCollectionReference.whereEqualTo("email", email).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                if(!queryDocumentSnapshots.isEmpty())
                {
                    for(QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots)
                    {
                        id = documentSnapshot.getId();
                    }
                    mDocumentReference = mFirebaseFirestore.collection("MedicalPersonnel").document(id);
                    mDocumentReference.update("password", randomNum);
                    SendMail sm = new SendMail(ForgetPasswordActivity.this, email, subject, message);
                    sm.execute();
                    Intent intent = new Intent(ForgetPasswordActivity.this, MainActivity.class);
                    intent.putExtra("tag","TAG");
                    startActivity(intent);
                    finish();
                }else{
                    nCollectionreference.whereEqualTo("email", email).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                        @Override
                        public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                            if(!queryDocumentSnapshots.isEmpty())
                            {
                                for(QueryDocumentSnapshot documentSnapshots : queryDocumentSnapshots)
                                {
                                    id = documentSnapshots.getId();
                                }
                                mDocumentReference = mFirebaseFirestore.collection("Mommy").document(id);
                                mDocumentReference.update("password", randomNum);
                                SendMail sm = new SendMail(ForgetPasswordActivity.this, email, subject, message);
                                sm.execute();
                                Intent intent = new Intent(ForgetPasswordActivity.this, MainActivity.class);
                                intent.putExtra("tag","TAG");
                                startActivity(intent);
                                finish();
                            }else{
                                AlertDialog.Builder builder = new AlertDialog.Builder(ForgetPasswordActivity.this);
                                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        return;
                                    }
                                });
                                builder.setTitle("Error");
                                builder.setMessage("Email address does not exist from database!");
                                AlertDialog alert = builder.create();
                                alert.show();
                            }
                        }
                    });
                }
            }
        });
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
