package com.example.mommyhealthapp.Nurse;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;

import com.example.mommyhealthapp.R;

public class HealthyMindFilterActivity extends AppCompatActivity {

    private ImageView imageViewHint;
    private Button btnNoted;
    Dialog questionDailog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_healthy_mind_filter);
        imageViewHint = (ImageView)findViewById(R.id.imageViewHint);

        imageViewHint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                questionDailog = new Dialog(HealthyMindFilterActivity.this);
                questionDailog.setContentView(R.layout.custom_questionnaire_help);
                questionDailog.setTitle("Questionnaire Help");
                btnNoted = (Button)questionDailog.findViewById(R.id.btnNoted);

                btnNoted.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        questionDailog.cancel();
                    }
                });
                questionDailog.show();
                Window window =questionDailog.getWindow();
                window.setLayout(1000, 1200);
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
