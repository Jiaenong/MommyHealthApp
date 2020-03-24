package com.example.mommyhealthapp.Nurse;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.cardview.widget.CardView;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.mommyhealthapp.Class.Mommy;
import com.example.mommyhealthapp.Class.MommyHealthInfo;
import com.example.mommyhealthapp.Nurse.MommyInfoActivity;
import com.example.mommyhealthapp.R;
import com.example.mommyhealthapp.SaveSharedPreference;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.mikhaellopez.circularimageview.CircularImageView;

public class MommyProfileActivity extends AppCompatActivity {

    private RelativeLayout layoutMummyInfo;
    private LinearLayoutCompat layoutMommyProfilell;
    private ProgressBar progressBarProfile;
    private CardView cardViewEarlyTest, cardViewPE, cardViewMGTT, cardViewSectionN, cardViewBabyKick, cardViewSummaryReport, cardViewColorCode, cardViewStorePicture;
    private TextView textViewMummyName, textViewMummyAge, textViewMummyID, textViewMummyStatus, textViewMummyColorCode;
    private CircularImageView imageViewMummy;
    private Boolean checkStatus, isEmpty;

    private FirebaseFirestore mFirebaseFirestore;
    private CollectionReference mCollectionReference, nCollectionReference;
    private DocumentReference mDocumentReference, nDocumentReference;

    private String id, healthInfoKey, mommyKey, healthInfoID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mommy_profile);

        layoutMummyInfo = (RelativeLayout)findViewById(R.id.layoutMummyInfo);
        cardViewEarlyTest = (CardView)findViewById(R.id.cardViewEarlyTest);
        cardViewPE = (CardView)findViewById(R.id.cardViewPE);
        cardViewMGTT = (CardView)findViewById(R.id.cardViewMGTT);
        cardViewSectionN = (CardView)findViewById(R.id.cardViewSectionN);
        cardViewBabyKick = (CardView)findViewById(R.id.cardViewBabyKick);
        cardViewSummaryReport = (CardView)findViewById(R.id.cardViewSummaryReport);
        cardViewColorCode = (CardView)findViewById(R.id.cardViewColorCode);
        cardViewStorePicture = (CardView)findViewById(R.id.cardViewStorePicture);
        textViewMummyName = (TextView)findViewById(R.id.textViewMummyName);
        textViewMummyAge = (TextView)findViewById(R.id.textViewMummyAge);
        textViewMummyID = (TextView)findViewById(R.id.textViewMummyID);
        textViewMummyStatus = (TextView)findViewById(R.id.textViewMummyStatus);
        textViewMummyColorCode = (TextView)findViewById(R.id.textViewMummyColorCode);
        imageViewMummy = (CircularImageView)findViewById(R.id.imageViewMummy);
        layoutMommyProfilell = (LinearLayoutCompat)findViewById(R.id.layoutMommyProfilell);
        progressBarProfile = (ProgressBar)findViewById(R.id.progressBarProfile);

        mFirebaseFirestore = FirebaseFirestore.getInstance();
        mCollectionReference = mFirebaseFirestore.collection("Mommy");
        nCollectionReference = mFirebaseFirestore.collection("MommyHealthInfo");

        Intent intent = getIntent();
        id = intent.getStringExtra("MommyID");
        progressBarProfile.setVisibility(View.VISIBLE);
        layoutMommyProfilell.setVisibility(View.GONE);

        mCollectionReference.whereEqualTo("mommyId",id).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                for(QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots)
                {
                    mommyKey = documentSnapshot.getId();
                    Mommy mommy = documentSnapshot.toObject(Mommy.class);
                    textViewMummyName.setText(mommy.getMommyName());
                    textViewMummyAge.setText(mommy.getAge()+"");
                    textViewMummyID.setText(mommy.getMommyId());
                    if(mommy.getStatus().equals("Active"))
                    {
                        textViewMummyStatus.setText(mommy.getStatus());
                        textViewMummyStatus.setTextColor(Color.parseColor("#008000"));
                    }else if(mommy.getStatus().equals("Inactive"))
                    {
                        textViewMummyStatus.setText(mommy.getStatus());
                        textViewMummyStatus.setTextColor(Color.parseColor("#FF0000"));
                    }

                    if(mommy.getColorCode().equals("red"))
                    {
                        textViewMummyColorCode.setText("Red Code");
                        textViewMummyColorCode.setTextColor(Color.RED);
                    }else if(mommy.getColorCode().equals("yellow"))
                    {
                        textViewMummyColorCode.setText("Yellow Code");
                        textViewMummyColorCode.setTextColor(Color.parseColor("#CCCC00"));
                    }else if(mommy.getColorCode().equals("green"))
                    {
                        textViewMummyColorCode.setText("Green Code");
                        textViewMummyColorCode.setTextColor(Color.parseColor("#006400"));
                    }else if(mommy.getColorCode().equals("white"))
                    {
                        textViewMummyColorCode.setText("White Code");
                        textViewMummyColorCode.setTextColor(Color.DKGRAY);
                    }else{
                        textViewMummyColorCode.setText("");
                    }

                    if(mommy.getMummyImage().equals(""))
                    {
                        imageViewMummy.setImageResource(R.drawable.user);
                    }else{
                        Glide.with(MommyProfileActivity.this).load(mommy.getMummyImage()).into(imageViewMummy);
                    }
                }
                checkStatus = CheckMummyStatus();
                progressBarProfile.setVisibility(View.GONE);
                layoutMommyProfilell.setVisibility(View.VISIBLE);
            }
        });

        layoutMummyInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MommyProfileActivity.this, MommyInfoActivity.class);
                intent.putExtra("MommyID", id);
                startActivity(intent);
            }
        });

        cardViewEarlyTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(SaveSharedPreference.getEarlyTest(MommyProfileActivity.this).equals("Old"))
                {
                    Intent intent = new Intent(MommyProfileActivity.this, EarlyTestActivity.class);
                    intent.putExtra("Status", textViewMummyStatus.getText().toString());
                    startActivity(intent);
                }else{
                    if(checkStatus == true)
                    {
                        Intent intent = new Intent(MommyProfileActivity.this, EarlyTestActivity.class);
                        intent.putExtra("Status", textViewMummyStatus.getText().toString());
                        startActivity(intent);
                    }else{
                        AlertDialog.Builder builder = new AlertDialog.Builder(MommyProfileActivity.this);
                        builder.setTitle("Mummy Not Available");
                        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                return;
                            }
                        });
                        builder.setMessage("Mummy Status is Inactive, please change to active inorder to add new Pregnant Record");
                        AlertDialog alert = builder.create();
                        alert.show();
                    }
                }
            }
        });

        cardViewPE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(SaveSharedPreference.getEarlyTest(MommyProfileActivity.this).equals("Old"))
                {
                    Intent intent = new Intent(MommyProfileActivity.this, PregnancyExamRecordActivity.class);
                    startActivity(intent);
                }else{
                    AlertDialog.Builder builder = new AlertDialog.Builder(MommyProfileActivity.this);
                    builder.setTitle("Mummy Not Available");
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            return;
                        }
                    });
                    builder.setMessage("Early Test must be carry out");
                    AlertDialog alert = builder.create();
                    alert.show();
                }
            }
        });

        cardViewMGTT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(SaveSharedPreference.getEarlyTest(MommyProfileActivity.this).equals("Old"))
                {
                    Intent intent = new Intent(MommyProfileActivity.this, MGTTActivity.class);
                    startActivity(intent);
                }else{
                    AlertDialog.Builder builder = new AlertDialog.Builder(MommyProfileActivity.this);
                    builder.setTitle("Mummy Not Available");
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            return;
                        }
                    });
                    builder.setMessage("Early Test must be carry out");
                    AlertDialog alert = builder.create();
                    alert.show();
                }
            }
        });

        cardViewSectionN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(SaveSharedPreference.getEarlyTest(MommyProfileActivity.this).equals("Old"))
                {
                    Intent intent = new Intent(MommyProfileActivity.this, SectionNActivity.class);
                    startActivity(intent);
                }else{
                    AlertDialog.Builder builder = new AlertDialog.Builder(MommyProfileActivity.this);
                    builder.setTitle("Mummy Not Available");
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            return;
                        }
                    });
                    builder.setMessage("Early Test must be carry out");
                    AlertDialog alert = builder.create();
                    alert.show();
                }
            }
        });

        cardViewBabyKick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(SaveSharedPreference.getEarlyTest(MommyProfileActivity.this).equals("Old"))
                {
                    Intent intent = new Intent(MommyProfileActivity.this, BabyKickRecordActivity.class);
                    startActivity(intent);
                }else{
                    AlertDialog.Builder builder = new AlertDialog.Builder(MommyProfileActivity.this);
                    builder.setTitle("Mummy Not Available");
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            return;
                        }
                    });
                    builder.setMessage("Early Test must be carry out");
                    AlertDialog alert = builder.create();
                    alert.show();
                }
            }
        });

        cardViewSummaryReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(SaveSharedPreference.getEarlyTest(MommyProfileActivity.this).equals("Old"))
                {
                    Intent intent = new Intent(MommyProfileActivity.this, SummaryReportActivity.class);
                    startActivity(intent);
                }else{
                    AlertDialog.Builder builder = new AlertDialog.Builder(MommyProfileActivity.this);
                    builder.setTitle("Mummy Not Available");
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            return;
                        }
                    });
                    builder.setMessage("Early Test must be carry out");
                    AlertDialog alert = builder.create();
                    alert.show();
                }
            }
        });

        cardViewColorCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(SaveSharedPreference.getEarlyTest(MommyProfileActivity.this).equals("Old"))
                {
                    Intent intent = new Intent(MommyProfileActivity.this, PregnancyControlActivity.class);
                    startActivity(intent);
                }else{
                    AlertDialog.Builder builder = new AlertDialog.Builder(MommyProfileActivity.this);
                    builder.setTitle("Mummy Not Available");
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            return;
                        }
                    });
                    builder.setMessage("Early Test must be carry out");
                    AlertDialog alert = builder.create();
                    alert.show();
                }
            }
        });

        cardViewStorePicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(SaveSharedPreference.getEarlyTest(MommyProfileActivity.this).equals("Old"))
                {
                    Intent intent = new Intent(MommyProfileActivity.this, ImageRecordActivity.class);
                    startActivity(intent);
                }else{
                    AlertDialog.Builder builder = new AlertDialog.Builder(MommyProfileActivity.this);
                    builder.setTitle("Mummy Not Available");
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            return;
                        }
                    });
                    builder.setMessage("Early Test must be carry out");
                    AlertDialog alert = builder.create();
                    alert.show();
                }
            }
        });

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private Boolean CheckMummyStatus()
    {
        if(textViewMummyStatus.getText().toString().equals("Active"))
        {
            return true;
        }else{
            return false;
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
