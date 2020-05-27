package com.example.mommyhealthapp.Nurse;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.mommyhealthapp.MainActivity;
import com.example.mommyhealthapp.R;
import com.example.mommyhealthapp.SaveSharedPreference;

public class SectionNActivity extends AppCompatActivity {

    private CardView cardViewAntenatal, cardViewPostnatal, cardViewCircum, cardViewBreastFeeding, cardViewSectionNOthers;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_section_n);

        cardViewAntenatal = (CardView)findViewById(R.id.cardViewAntenatal);
        cardViewPostnatal = (CardView)findViewById(R.id.cardViewPostnatal);
        cardViewCircum = (CardView)findViewById(R.id.cardViewCircum);
        cardViewBreastFeeding = (CardView)findViewById(R.id.cardViewBreastFeeding);
        cardViewSectionNOthers = (CardView)findViewById(R.id.cardViewSectionNOthers);

        cardViewAntenatal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SectionNActivity.this, AntenatalActivity.class);
                startActivity(intent);
            }
        });

        cardViewPostnatal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SectionNActivity.this, PostnatalActivity.class);
                startActivity(intent);
            }
        });

        cardViewCircum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SectionNActivity.this, UnusualCircumtancesActivity.class);
                startActivity(intent);
            }
        });
        cardViewBreastFeeding.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SectionNActivity.this, BreastFeedingActivity.class);
                startActivity(intent);
            }
        });

        cardViewSectionNOthers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SectionNActivity.this, SectionNOthersActivity.class);
                startActivity(intent);
            }
        });

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.action_logout:
                AlertDialog.Builder builder = new AlertDialog.Builder(SectionNActivity.this);
                builder.setTitle("Logout");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        SaveSharedPreference.clearUser(SectionNActivity.this);
                        Intent intent = new Intent(SectionNActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                builder.setMessage("Are you sure you want to log out?");
                AlertDialog alert = builder.create();
                alert.setCanceledOnTouchOutside(false);
                alert.show();
                return true;
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
