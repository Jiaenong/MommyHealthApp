package com.example.mommyhealthapp.Nurse;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;

import com.example.mommyhealthapp.Nurse.MommyInfoActivity;
import com.example.mommyhealthapp.R;

public class MommyProfileActivity extends AppCompatActivity {

    private RelativeLayout layoutMummyInfo;
    private CardView cardViewEarlyTest, cardViewPE, cardViewMGTT;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mommy_profile);

        layoutMummyInfo = (RelativeLayout)findViewById(R.id.layoutMummyInfo);
        cardViewEarlyTest = (CardView)findViewById(R.id.cardViewEarlyTest);
        cardViewPE = (CardView)findViewById(R.id.cardViewPE);
        cardViewMGTT = (CardView)findViewById(R.id.cardViewMGTT);

        layoutMummyInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MommyProfileActivity.this, MommyInfoActivity.class);
                startActivity(intent);
            }
        });

        cardViewEarlyTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MommyProfileActivity.this, EarlyTestActivity.class);
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
                Intent intent = new Intent(MommyProfileActivity.this, MGTTRecordActivity.class);
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
