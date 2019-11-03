package com.example.mommyhealthapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.LinearLayoutCompat;

import android.app.DatePickerDialog;
import android.media.Image;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.google.android.material.textfield.TextInputLayout;

import java.util.Calendar;

public class MommyInfoActivity extends AppCompatActivity {

    private LinearLayoutCompat layoutInfo, layoutDetailInfo;
    private ImageView imageViewUp, imageViewUp1, imageViewDown, imageViewDown1;
    private EditText editTextFirstName, editTextLastName, editTextIC, editTextRace, editTextAddress, editTextPhone, editTextAge, editTextOccupation, editTextEducation;
    private EditText editTextEDD, editTextLNMP, editTextEDP, editTextDisease, editTextHeight, editTextWeight,
            editTextHusbandName, editTextHusbandIC, editTextHusbandWork, editTextHusbandWorkAddress, editTextHusbandPhone;
    DatePickerDialog datePickerDialog;
    private RadioGroup radioGroupYesNo, radioGroupMarriage;
    private RadioButton radioBtnYes, radioBtnNo, radioBtnMarried, radioBtnSingle;
    private TextInputLayout txtInputLayoutDisease, txtInputLayoutEDD, txtLayoutHusbandPhone, txtLayoutHusbandName, txtLayoutHusbandIC, txtLayoutHusbandWork, txtLayoutHusbandWorkPlacr;

    private Button buttonCancel, buttonCancelInfo, buttonUpdateInfo, buttonUpdate;
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

        editTextFirstName = (EditText)findViewById(R.id.editTextFirstName);
        editTextLastName = (EditText)findViewById(R.id.editTextLastName);
        editTextIC = (EditText)findViewById(R.id.editTextIC);
        editTextRace = (EditText)findViewById(R.id.editTextRace);
        editTextAddress = (EditText)findViewById(R.id.editTextAddress);
        editTextPhone = (EditText)findViewById(R.id.editTextPhone);
        editTextAge = (EditText)findViewById(R.id.editTextAge);
        editTextOccupation = (EditText)findViewById(R.id.editTextOccupation);
        editTextEducation = (EditText)findViewById(R.id.editTextEducation);

        txtInputLayoutDisease = (TextInputLayout)findViewById(R.id.txtInputLayoutDisease);
        txtInputLayoutEDD = (TextInputLayout)findViewById(R.id.txtInputLayoutEDD);
        txtLayoutHusbandPhone = (TextInputLayout)findViewById(R.id.txtLayoutHusbandPhone);
        txtLayoutHusbandName = (TextInputLayout)findViewById(R.id.txtLayoutHusbandName);
        txtLayoutHusbandIC = (TextInputLayout)findViewById(R.id.txtLayoutHusbandIC);
        txtLayoutHusbandWork = (TextInputLayout)findViewById(R.id.txtLayoutHusbandWork);
        txtLayoutHusbandWorkPlacr = (TextInputLayout)findViewById(R.id.txtLayoutHusbandWorkAddress);
        editTextEDD = (EditText)findViewById(R.id.editTextEDD);
        editTextDisease = (EditText)findViewById(R.id.editTextDisease);
        editTextLNMP = (EditText)findViewById(R.id.editTextLNMP);
        editTextEDP = (EditText)findViewById(R.id.editTextEDP);
        editTextHeight = (EditText)findViewById(R.id.editTextHeight);
        editTextWeight = (EditText)findViewById(R.id.editTextWeight);
        editTextHusbandName = (EditText)findViewById(R.id.editTextHusbandName);
        editTextHusbandIC = (EditText)findViewById(R.id.editTextHusbandIC);
        editTextHusbandWork = (EditText)findViewById(R.id.editTextHusbandWork);
        editTextHusbandWorkAddress = (EditText)findViewById(R.id.editTextHusbandWorkAddress);
        editTextHusbandPhone = (EditText)findViewById(R.id.editTextHusbandPhone);
        radioGroupYesNo = (RadioGroup)findViewById(R.id.radioGroupYesNo);
        radioGroupMarriage = (RadioGroup)findViewById(R.id.radioGroupMarriage);
        radioBtnNo = (RadioButton)findViewById(R.id.radioBtnNo);
        radioBtnYes = (RadioButton)findViewById(R.id.radioBtnYes);
        radioBtnMarried = (RadioButton)findViewById(R.id.radioBtnMarried);
        radioBtnSingle = (RadioButton)findViewById(R.id.radioBtnSingle);

        buttonCancel = (Button)findViewById(R.id.buttonCancel);
        buttonCancelInfo = (Button)findViewById(R.id.buttonCancelInfo);
        buttonUpdateInfo = (Button)findViewById(R.id.buttonUpdateInfo);
        buttonUpdate = (Button)findViewById(R.id.buttonUpdate);

        layoutInfo.setVisibility(View.GONE);
        layoutDetailInfo.setVisibility(View.GONE);
        imageViewUp.setVisibility(View.GONE);
        imageViewUp1.setVisibility(View.GONE);
        buttonCancel.setVisibility(View.GONE);
        buttonCancelInfo.setVisibility(View.GONE);

        editTextEDD.setKeyListener(null);
        editTextLNMP.setKeyListener(null);
        editTextEDP.setKeyListener(null);
        editTextEDD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR); // current year
                int mMonth = c.get(Calendar.MONTH); // current month
                int mDay = c.get(Calendar.DAY_OF_MONTH); // current day
                datePickerDialog = new DatePickerDialog(MommyInfoActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                editTextEDD.setText(dayOfMonth + "/" +(month + 1) + "/" + year);
                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });

        editTextLNMP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR); // current year
                int mMonth = c.get(Calendar.MONTH); // current month
                int mDay = c.get(Calendar.DAY_OF_MONTH); // current day
                datePickerDialog = new DatePickerDialog(MommyInfoActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                editTextEDD.setText(dayOfMonth + "/" +(month + 1) + "/" + year);
                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });

        editTextEDP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR); // current year
                int mMonth = c.get(Calendar.MONTH); // current month
                int mDay = c.get(Calendar.DAY_OF_MONTH); // current day
                datePickerDialog = new DatePickerDialog(MommyInfoActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                editTextEDD.setText(dayOfMonth + "/" +(month + 1) + "/" + year);
                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });

        radioGroupYesNo.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(radioBtnYes.isChecked())
                {
                    txtInputLayoutDisease.setVisibility(View.VISIBLE);
                }else{
                    txtInputLayoutDisease.setVisibility(View.GONE);
                }
            }
        });

        radioGroupMarriage.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(radioBtnMarried.isChecked()){
                    txtLayoutHusbandName.setVisibility(View.VISIBLE);
                    txtLayoutHusbandIC.setVisibility(View.VISIBLE);
                    txtLayoutHusbandWork.setVisibility(View.VISIBLE);
                    txtLayoutHusbandWorkPlacr.setVisibility(View.VISIBLE);
                    txtLayoutHusbandPhone.setVisibility(View.VISIBLE);
                }else{
                    txtLayoutHusbandName.setVisibility(View.GONE);
                    txtLayoutHusbandIC.setVisibility(View.GONE);
                    txtLayoutHusbandWork.setVisibility(View.GONE);
                    txtLayoutHusbandWorkPlacr.setVisibility(View.GONE);
                    txtLayoutHusbandPhone.setVisibility(View.GONE);
                }
            }
        });

        DisableInfoEditText();
        DisableDetailInfoEditText();

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

        buttonUpdateInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EnableInfoEditText();
                buttonCancelInfo.setVisibility(View.VISIBLE);
            }
        });

        buttonCancelInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DisableInfoEditText();
                buttonCancelInfo.setVisibility(View.GONE);
            }
        });

        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EnableDetailInfoEditText();
                buttonCancel.setVisibility(View.VISIBLE);
            }
        });

        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DisableDetailInfoEditText();
                buttonCancel.setVisibility(View.GONE);
            }
        });

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void DisableInfoEditText()
    {
        editTextFirstName.setEnabled(false);
        editTextLastName.setEnabled(false);
        editTextIC.setEnabled(false);
        editTextRace.setEnabled(false);
        editTextAddress.setEnabled(false);
        editTextPhone.setEnabled(false);
        editTextAge.setEnabled(false);
        editTextOccupation.setEnabled(false);
        editTextEducation.setEnabled(false);
    }

    private void DisableDetailInfoEditText()
    {
        editTextEDD.setEnabled(false);
        editTextDisease.setEnabled(false);
        editTextLNMP.setEnabled(false);
        editTextEDP.setEnabled(false);
        editTextHeight.setEnabled(false);
        editTextWeight.setEnabled(false);
        editTextHusbandName.setEnabled(false);
        editTextHusbandIC.setEnabled(false);
        editTextHusbandWork.setEnabled(false);
        editTextHusbandWorkAddress.setEnabled(false);
        editTextHusbandPhone.setEnabled(false);
    }

    private void EnableDetailInfoEditText()
    {
        editTextEDD.setEnabled(true);
        editTextDisease.setEnabled(true);
        editTextLNMP.setEnabled(true);
        editTextEDP.setEnabled(true);
        editTextHeight.setEnabled(true);
        editTextWeight.setEnabled(true);
        editTextHusbandName.setEnabled(true);
        editTextHusbandIC.setEnabled(true);
        editTextHusbandWork.setEnabled(true);
        editTextHusbandWorkAddress.setEnabled(true);
        editTextHusbandPhone.setEnabled(true);
    }

    private void EnableInfoEditText()
    {
        editTextFirstName.setEnabled(true);
        editTextLastName.setEnabled(true);
        editTextIC.setEnabled(true);
        editTextRace.setEnabled(true);
        editTextAddress.setEnabled(true);
        editTextPhone.setEnabled(true);
        editTextAge.setEnabled(true);
        editTextOccupation.setEnabled(true);
        editTextEducation.setEnabled(true);
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
