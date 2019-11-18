package com.example.mommyhealthapp.Nurse;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.LinearLayoutCompat;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.media.Image;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TimePicker;

import com.example.mommyhealthapp.R;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Calendar;
import java.util.Set;

public class MommyInfoActivity extends AppCompatActivity {

    private LinearLayoutCompat layoutInfo, layoutDetailInfo, layoutAppointment;
    private ImageView imageViewUp, imageViewUp1, imageViewDown, imageViewDown1, imageViewDownApp, imageViewAppUpApp;
    private EditText editTextFirstName, editTextLastName, editTextIC, editTextRace, editTextAddress, editTextPhone, editTextAge, editTextOccupation, editTextEducation;
    private EditText editTextEDD, editTextLNMP, editTextEDP, editTextDisease, editTextHeight, editTextWeight,
            editTextHusbandName, editTextHusbandIC, editTextHusbandWork, editTextHusbandWorkAddress, editTextHusbandPhone;
    private EditText editTextAppointment, editTextAppTime;
    DatePickerDialog datePickerDialog;
    TimePickerDialog timePickerDialog;
    private RadioGroup radioGroupYesNo, radioGroupMarriage, radioGroupRace;
    private RadioButton radioBtnYes, radioBtnNo, radioBtnMarried, radioBtnSingle, radioBtnMalay, radioBtnChinese, radioBtnIndian, radioBtnOtherRaces;;
    private TextInputLayout txtInputLayoutDisease, txtInputLayoutEDD, txtLayoutHusbandPhone, txtLayoutHusbandName, txtLayoutHusbandIC, txtLayoutHusbandWork, txtLayoutHusbandWorkPlacr, txtIinputLayoutOtherRace;
    private CheckBox chkBoxStatus;

    private Button buttonCancel, buttonCancelInfo, buttonUpdateInfo, buttonUpdate, btnUpdateApp, btnCancelApp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mommy_info);

        layoutInfo = (LinearLayoutCompat)findViewById(R.id.layoutInfo);
        layoutDetailInfo = (LinearLayoutCompat)findViewById(R.id.layoutDetailInfo);
        layoutAppointment = (LinearLayoutCompat)findViewById(R.id.layoutAppointment);
        imageViewUp = (ImageView)findViewById(R.id.imageViewUp);
        imageViewDown = (ImageView)findViewById(R.id.imageViewDown);
        imageViewDown1 = (ImageView)findViewById(R.id.imageViewDown1);
        imageViewUp1 = (ImageView)findViewById(R.id.imageViewUp1);
        imageViewAppUpApp = (ImageView)findViewById(R.id.imageViewUpAp);
        imageViewDownApp = (ImageView)findViewById(R.id.imageViewDownAp);

        editTextFirstName = (EditText)findViewById(R.id.editTextFirstName);
        editTextLastName = (EditText)findViewById(R.id.editTextLastName);
        editTextIC = (EditText)findViewById(R.id.editTextIC);
        editTextRace = (EditText)findViewById(R.id.editTextOtherRace);
        editTextAddress = (EditText)findViewById(R.id.editTextAddress);
        editTextPhone = (EditText)findViewById(R.id.editTextPhone);
        editTextAge = (EditText)findViewById(R.id.editTextAge);
        editTextOccupation = (EditText)findViewById(R.id.editTextOccupation);
        editTextEducation = (EditText)findViewById(R.id.editTextEducation);
        chkBoxStatus = (CheckBox)findViewById(R.id.chkBoxStatus);
        editTextAppointment = (EditText)findViewById(R.id.editTextAppointment);
        editTextAppTime = (EditText)findViewById(R.id.editTextAppTime);
        radioGroupRace = (RadioGroup)findViewById(R.id.radioGroupRace);
        radioBtnMalay = (RadioButton)findViewById(R.id.radioBtnMalay);
        radioBtnChinese = (RadioButton)findViewById(R.id.radioBtnChinese);
        radioBtnOtherRaces = (RadioButton)findViewById(R.id.radioBtnOtherRace);
        txtIinputLayoutOtherRace = (TextInputLayout)findViewById(R.id.txtIinputLayoutOtherRace);

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
        btnUpdateApp = (Button)findViewById(R.id.btnUpdateApp);
        btnCancelApp = (Button)findViewById(R.id.btnCancelApp);

        layoutDetailInfo.setVisibility(View.GONE);
        imageViewUp.setVisibility(View.GONE);
        imageViewDown1.setVisibility(View.GONE);
        buttonCancel.setVisibility(View.GONE);
        buttonCancelInfo.setVisibility(View.GONE);
        imageViewDownApp.setVisibility(View.GONE);
        btnCancelApp.setVisibility(View.GONE);

        editTextEDD.setKeyListener(null);
        editTextLNMP.setKeyListener(null);
        editTextEDP.setKeyListener(null);
        editTextAppointment.setKeyListener(null);
        editTextAppTime.setKeyListener(null);

        radioGroupRace.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(radioBtnOtherRaces.isChecked())
                {
                    txtIinputLayoutOtherRace.setVisibility(View.VISIBLE);
                }else{
                    txtIinputLayoutOtherRace.setVisibility(View.GONE);
                }
            }
        });

        editTextAppTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                timePickerDialog = new TimePickerDialog(MommyInfoActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        editTextAppTime.setText( selectedHour + ":" + selectedMinute);
                    }
                }, hour, minute, true);//Yes 24 hour time
                timePickerDialog.setTitle("Select Time");
                timePickerDialog.show();
            }
        });
        editTextAppointment.setOnClickListener(new View.OnClickListener() {
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
                                editTextAppointment.setText(dayOfMonth + "/" +(month + 1) + "/" + year);
                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });
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

        SetVisibility();
        DisableInfoEditText();
        DisableDetailInfoEditText();
        DisableAppointmentText();

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

        btnUpdateApp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EnableAppointmentText();
                btnCancelApp.setVisibility(View.VISIBLE);
            }
        });

        btnCancelApp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DisableAppointmentText();
                btnCancelApp.setVisibility(View.GONE);
            }
        });

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void SetVisibility()
    {
        imageViewAppUpApp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                layoutAppointment.setVisibility(View.GONE);
                imageViewAppUpApp.setVisibility(View.GONE);
                imageViewDownApp.setVisibility(View.VISIBLE);
            }
        });

        imageViewDownApp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                layoutAppointment.setVisibility(View.VISIBLE);
                imageViewAppUpApp.setVisibility(View.VISIBLE);
                imageViewDownApp.setVisibility(View.GONE);
            }
        });
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
    }

    private void DisableAppointmentText()
    {
        editTextAppointment.setEnabled(false);
        editTextAppTime.setEnabled(false);
    }

    private void EnableAppointmentText()
    {
        editTextAppointment.setEnabled(true);
        editTextAppTime.setEnabled(true);
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
        chkBoxStatus.setEnabled(false);
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
        chkBoxStatus.setEnabled(true);
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
