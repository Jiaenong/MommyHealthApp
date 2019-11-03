package com.example.mommyhealthapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.LinearLayoutCompat;

import android.media.Image;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class MommyInfoActivity extends AppCompatActivity {

    private LinearLayoutCompat layoutInfo, layoutDetailInfo;
    private ImageView imageViewUp, imageViewUp1, imageViewDown, imageViewDown1;

    private Button buttonCancel, buttonCancelInfo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mommy_info);

        layoutInfo = (LinearLayoutCompat)findViewById(R.id.layoutInfo);
        layoutDetailInfo = (LinearLayoutCompat)findViewById(R.id.layoutDetailInfo);
        imageViewUp = (ImageView)findViewById(R.id.imageViewUp);
        imageViewDown = (ImageView)findViewById(R.id.imageViewDown);
        imageViewDown1 = (ImageView)findViewById(R.id.imageViewDown1);
        imageViewUp1 = (ImageView)findViewById(R.id.imageViewUp1);

        buttonCancel = (Button)findViewById(R.id.buttonCancel);
        buttonCancelInfo = (Button)findViewById(R.id.buttonCancelInfo);

        layoutInfo.setVisibility(View.GONE);
        layoutDetailInfo.setVisibility(View.GONE);
        imageViewUp.setVisibility(View.GONE);
        imageViewUp1.setVisibility(View.GONE);
        buttonCancel.setVisibility(View.GONE);
        buttonCancelInfo.setVisibility(View.GONE);

        imageViewDown1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                layoutInfo.setVisibility(View.VISIBLE);
                imageViewDown1.setVisibility(View.GONE);
                imageViewUp1.setVisibility(View.VISIBLE);
            }
        });

        imageViewDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                layoutDetailInfo.setVisibility(View.VISIBLE);
                imageViewDown.setVisibility(View.GONE);
                imageViewUp.setVisibility(View.VISIBLE);
            }
        });

        imageViewUp1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                layoutInfo.setVisibility(View.GONE);
                imageViewDown1.setVisibility(View.VISIBLE);
                imageViewUp1.setVisibility(View.GONE);
            }
        });

        imageViewUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                layoutDetailInfo.setVisibility(View.GONE);
                imageViewDown.setVisibility(View.VISIBLE);
                imageViewUp.setVisibility(View.GONE);
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
