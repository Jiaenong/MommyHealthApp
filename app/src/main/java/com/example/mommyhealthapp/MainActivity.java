package com.example.mommyhealthapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.mommyhealthapp.Mommy.MommyHomeActivity;
import com.example.mommyhealthapp.Nurse.NurseHomeActivity;


public class MainActivity extends AppCompatActivity {

    private Button buttonLogin;
    private EditText editTextLoginName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonLogin = (Button)findViewById(R.id.btnLogin);
        editTextLoginName = (EditText)findViewById(R.id.editTextLoginName);

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(editTextLoginName.getText().toString().equals("1"))
                {
                    Intent intent = new Intent(MainActivity.this, NurseHomeActivity.class);
                    startActivity(intent);
                }else{
                    Intent intent = new Intent(MainActivity.this, MommyHomeActivity.class);
                    startActivity(intent);
                }

            }
        });
    }
}
