package com.example.mommyhealthapp.Nurse;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.mommyhealthapp.R;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Calendar;

public class SectionCActivity extends AppCompatActivity {

    private RadioGroup radioGroupPractice;
    private RadioButton radioButtonPracYes, radioButtonPracNo;
    private TextInputLayout txtInputLayoutPeriod, txtInputLayoutState, txtInputLayoutFamilyState;
    private CheckBox checkBoxOther, checkBoxFamilyOther;
    private EditText editTextDos1, editTextDos2, editTextDosAddOn;
    DatePickerDialog datePickerDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_section_c);

        radioGroupPractice = (RadioGroup)findViewById(R.id.radioGroupPractice);
        radioButtonPracYes = (RadioButton)findViewById(R.id.radioButtonPracYes);
        radioButtonPracNo = (RadioButton)findViewById(R.id.radioButtonPracNo);
        txtInputLayoutPeriod = (TextInputLayout)findViewById(R.id.txtInputLayotPeriod);
        txtInputLayoutState = (TextInputLayout)findViewById(R.id.txtInputLayoutState);
        txtInputLayoutFamilyState = (TextInputLayout)findViewById(R.id.txtInputLayoutFamilyState);
        checkBoxOther = (CheckBox)findViewById(R.id.checkBoxOther);
        checkBoxFamilyOther = (CheckBox)findViewById(R.id.checkBoxFamilyOther);
        editTextDos1 = (EditText)findViewById(R.id.editTextDos1);
        editTextDos2 = (EditText)findViewById(R.id.editTextDos2);
        editTextDosAddOn = (EditText)findViewById(R.id.editTextDosAddOn);

        radioGroupPractice.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(radioButtonPracYes.isChecked())
                {
                    txtInputLayoutPeriod.setVisibility(View.VISIBLE);
                }else{
                    txtInputLayoutPeriod.setVisibility(View.GONE);
                }
            }
        });

        checkBoxOther.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkBoxOther.isChecked())
                {
                    txtInputLayoutState.setVisibility(View.VISIBLE);
                }else{
                    txtInputLayoutState.setVisibility(View.GONE);
                }

            }
        });

        checkBoxFamilyOther.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkBoxFamilyOther.isChecked())
                {
                    txtInputLayoutFamilyState.setVisibility(View.VISIBLE);
                }else{
                    txtInputLayoutFamilyState.setVisibility(View.GONE);
                }
            }
        });

        editTextDos1.setKeyListener(null);
        editTextDos2.setKeyListener(null);
        editTextDosAddOn.setKeyListener(null);

        editTextDos1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR); // current year
                int mMonth = c.get(Calendar.MONTH); // current month
                int mDay = c.get(Calendar.DAY_OF_MONTH); // current day
                datePickerDialog = new DatePickerDialog(SectionCActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                editTextDos1.setText(dayOfMonth + "/" +(month + 1) + "/" + year);
                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });

        editTextDos2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR); // current year
                int mMonth = c.get(Calendar.MONTH); // current month
                int mDay = c.get(Calendar.DAY_OF_MONTH); // current day
                datePickerDialog = new DatePickerDialog(SectionCActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                editTextDos2.setText(dayOfMonth + "/" +(month + 1) + "/" + year);
                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });

        editTextDosAddOn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR); // current year
                int mMonth = c.get(Calendar.MONTH); // current month
                int mDay = c.get(Calendar.DAY_OF_MONTH); // current day
                datePickerDialog = new DatePickerDialog(SectionCActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                editTextDosAddOn.setText(dayOfMonth + "/" +(month + 1) + "/" + year);
                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
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
