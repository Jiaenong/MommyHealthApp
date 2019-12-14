package com.example.mommyhealthapp.Nurse;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.SectionIndexer;

import com.example.mommyhealthapp.R;
import com.example.mommyhealthapp.Nurse.SectionAActivity;

public class EarlyTestActivity extends AppCompatActivity {

    private CardView cardViewSectionA, cardViewSectionB, cardViewSectionC, cardViewSectionD, cardViewBreastCheck;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_early_test);

        cardViewSectionA = (CardView)findViewById(R.id.cardViewSectionA);
        cardViewSectionB = (CardView)findViewById(R.id.cardViewSetionB);
        cardViewSectionC = (CardView)findViewById(R.id.cardViewSectionC);
        cardViewSectionD = (CardView)findViewById(R.id.cardViewSectionD);
        cardViewBreastCheck = (CardView)findViewById(R.id.cardViewBreastCheck);
        cardViewSectionA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EarlyTestActivity.this, SectionAActivity.class);
                startActivity(intent);
            }
        });

        cardViewSectionB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EarlyTestActivity.this, SectionBActivity.class);
                startActivity(intent);
            }
        });

        cardViewSectionC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EarlyTestActivity.this, SectionCActivity.class);
                startActivity(intent);
            }
        });

        cardViewSectionD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EarlyTestActivity.this, SectionDActivity.class);
                startActivity(intent);
            }
        });

        cardViewBreastCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EarlyTestActivity.this, BreastExamActivity.class);
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
