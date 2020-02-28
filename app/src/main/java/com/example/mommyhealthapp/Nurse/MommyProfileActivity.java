package com.example.mommyhealthapp.Nurse;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.mommyhealthapp.Class.Mommy;
import com.example.mommyhealthapp.Class.MommyHealthInfo;
import com.example.mommyhealthapp.Nurse.MommyInfoActivity;
import com.example.mommyhealthapp.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.mikhaellopez.circularimageview.CircularImageView;

public class MommyProfileActivity extends AppCompatActivity {

    private RelativeLayout layoutMummyInfo;
    private LinearLayoutCompat layoutMommyProfilell;
    private ProgressBar progressBarProfile;
    private CardView cardViewEarlyTest, cardViewPE, cardViewMGTT, cardViewSectionN, cardViewBabyKick, cardViewSummaryReport;
    private TextView textViewMummyName, textViewMummyAge, textViewMummyID, textViewMummyStatus;
    private CircularImageView imageViewMummy;

    private FirebaseFirestore mFirebaseFirestore;
    private CollectionReference mCollectionReference;

    private String id;
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
        textViewMummyName = (TextView)findViewById(R.id.textViewMummyName);
        textViewMummyAge = (TextView)findViewById(R.id.textViewMummyAge);
        textViewMummyID = (TextView)findViewById(R.id.textViewMummyID);
        textViewMummyStatus = (TextView)findViewById(R.id.textViewMummyStatus);
        imageViewMummy = (CircularImageView)findViewById(R.id.imageViewMummy);
        layoutMommyProfilell = (LinearLayoutCompat)findViewById(R.id.layoutMommyProfilell);
        progressBarProfile = (ProgressBar)findViewById(R.id.progressBarProfile);

        mFirebaseFirestore = FirebaseFirestore.getInstance();
        mCollectionReference = mFirebaseFirestore.collection("Mommy");

        Intent intent = getIntent();
        id = intent.getStringExtra("MommyID");
        progressBarProfile.setVisibility(View.VISIBLE);
        layoutMommyProfilell.setVisibility(View.GONE);

        mCollectionReference.whereEqualTo("mommyId",id).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for(QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots)
                {
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

                    if(mommy.getMummyImage().equals(""))
                    {
                        imageViewMummy.setImageResource(R.drawable.user);
                    }else{
                        Glide.with(MommyProfileActivity.this).load(mommy.getMummyImage()).into(imageViewMummy);
                    }
                    progressBarProfile.setVisibility(View.GONE);
                    layoutMommyProfilell.setVisibility(View.VISIBLE);
                }
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
                Intent intent = new Intent(MommyProfileActivity.this, EarlyTestActivity.class);
                intent.putExtra("Status", textViewMummyStatus.getText().toString());
                startActivity(intent);
            }
        });

        cardViewPE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MommyProfileActivity.this, PregnancyExamRecordActivity.class);
                startActivity(intent);
            }
        });

        cardViewMGTT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MommyProfileActivity.this, MGTTActivity.class);
                startActivity(intent);
            }
        });

        cardViewSectionN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MommyProfileActivity.this, SectionNActivity.class);
                startActivity(intent);
            }
        });

        cardViewBabyKick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MommyProfileActivity.this, BabyKickRecordActivity.class);
                startActivity(intent);
            }
        });

        cardViewSummaryReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MommyProfileActivity.this, SummaryReportActivity.class);
                startActivity(intent);
            }
        });

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
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
