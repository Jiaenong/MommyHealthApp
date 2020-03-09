package com.example.mommyhealthapp.Nurse;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.media.Image;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.mommyhealthapp.Class.AppointmentDate;
import com.example.mommyhealthapp.Class.Mommy;
import com.example.mommyhealthapp.Class.MommyDetail;
import com.example.mommyhealthapp.Class.Notification;
import com.example.mommyhealthapp.R;
import com.example.mommyhealthapp.SaveSharedPreference;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.mikhaellopez.circularimageview.CircularImageView;

import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Calendar;
import java.util.Date;
import java.util.Set;

public class MommyInfoActivity extends AppCompatActivity {

    private LinearLayoutCompat layoutInfo, layoutDetailInfo, layoutAppointment, layoutAllView;
    private ImageView imageViewUp, imageViewUp1, imageViewDown, imageViewDown1, imageViewDownApp, imageViewAppUpApp;
    private EditText editTextMummyName, editTextIC, editTextRace, editTextAddress, editTextPhone, editTextEmail, editTextAge, editTextOccupation, editTextEducation;
    private EditText editTextEDD, editTextLNMP, editTextEDP, editTextDisease, editTextHeight, editTextWeight,
            editTextHusbandName, editTextHusbandIC, editTextHusbandWork, editTextHusbandWorkAddress, editTextHusbandPhone;
    private EditText editTextAppointment, editTextAppTime;
    private Spinner spinnerNational;
    DatePickerDialog datePickerDialog;
    TimePickerDialog timePickerDialog;
    private RadioGroup radioGroupYesNo, radioGroupMarriage, radioGroupRace;
    private RadioButton radioBtnYes, radioBtnNo, radioBtnMarried, radioBtnSingle, radioBtnMalay, radioBtnChinese, radioBtnIndian, radioBtnOtherRaces;;
    private TextInputLayout txtInputLayoutDisease, txtInputLayoutEDD, txtLayoutHusbandPhone, txtLayoutHusbandName, txtLayoutHusbandIC, txtLayoutHusbandWork, txtLayoutHusbandWorkPlacr, txtIinputLayoutOtherRace;
    private CheckBox chkBoxStatus;
    private TextView textViewMummyInfoName, textViewMummyInfoAge, textViewMummyInfoID;
    private CircularImageView imageViewMummyInfo;
    private ProgressBar progressBarMummyInfo;

    private String id, key, keyDetail, appointmentKey, healthInfoId;
    private Boolean isEmpty;
    private FirebaseFirestore mFirebaseFirestore;
    private CollectionReference mCollectionReference, nCollectionReference, pCollectionReference, oCollectionReference, qCollectionReference;
    private DocumentReference mDocumentReference, nDocumentReference;

    private int clickInfo = 0, clickInfoDetail = 0, clickApp = 0, check = 0;

    private Button buttonCancel, buttonCancelInfo, buttonUpdateInfo, buttonUpdate, btnUpdateApp, btnCancelApp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mommy_info);

        textViewMummyInfoName = (TextView)findViewById(R.id.textViewMummyInfoName);
        textViewMummyInfoAge = (TextView)findViewById(R.id.textViewMummyInfoAge);
        textViewMummyInfoID = (TextView)findViewById(R.id.textViewMummyInfoID);
        imageViewMummyInfo = (CircularImageView)findViewById(R.id.imageViewMummyInfo);

        progressBarMummyInfo = (ProgressBar)findViewById(R.id.progressBarMummyInfo);

        layoutAllView = (LinearLayoutCompat)findViewById(R.id.layoutAllView);
        layoutInfo = (LinearLayoutCompat)findViewById(R.id.layoutInfo);
        layoutDetailInfo = (LinearLayoutCompat)findViewById(R.id.layoutDetailInfo);
        layoutAppointment = (LinearLayoutCompat)findViewById(R.id.layoutAppointment);
        imageViewUp = (ImageView)findViewById(R.id.imageViewUp);
        imageViewDown = (ImageView)findViewById(R.id.imageViewDown);
        imageViewDown1 = (ImageView)findViewById(R.id.imageViewDown1);
        imageViewUp1 = (ImageView)findViewById(R.id.imageViewUp1);
        imageViewAppUpApp = (ImageView)findViewById(R.id.imageViewUpAp);
        imageViewDownApp = (ImageView)findViewById(R.id.imageViewDownAp);

        editTextMummyName = (EditText)findViewById(R.id.editTextMummyName);
        editTextIC = (EditText)findViewById(R.id.editTextIC);
        editTextRace = (EditText)findViewById(R.id.editTextOtherRace);
        editTextAddress = (EditText)findViewById(R.id.editTextAddress);
        editTextPhone = (EditText)findViewById(R.id.editTextPhone);
        editTextEmail = (EditText)findViewById(R.id.editTextEmail);
        editTextAge = (EditText)findViewById(R.id.editTextAge);
        editTextOccupation = (EditText)findViewById(R.id.editTextOccupation);
        editTextEducation = (EditText)findViewById(R.id.editTextEducation);
        chkBoxStatus = (CheckBox)findViewById(R.id.chkBoxStatus);
        editTextAppointment = (EditText)findViewById(R.id.editTextAppointment);
        editTextAppTime = (EditText)findViewById(R.id.editTextAppTime);
        radioGroupRace = (RadioGroup)findViewById(R.id.radioGrpRace);
        radioBtnMalay = (RadioButton)findViewById(R.id.radioMalay);
        radioBtnChinese = (RadioButton)findViewById(R.id.radioChinese);
        radioBtnIndian = (RadioButton)findViewById(R.id.radioIndian);
        radioBtnOtherRaces = (RadioButton)findViewById(R.id.radioOtherRace);
        txtIinputLayoutOtherRace = (TextInputLayout)findViewById(R.id.txtInputLayoutOtherRace);
        spinnerNational = (Spinner)findViewById(R.id.spinnerNationalInfo);

        txtInputLayoutDisease = (TextInputLayout)findViewById(R.id.txtInputLayoutDiseaseInfo);
        txtInputLayoutEDD = (TextInputLayout)findViewById(R.id.txtInputLayoutEDDInfo);
        txtLayoutHusbandPhone = (TextInputLayout)findViewById(R.id.txtLayoutHusbandPhoneInfo);
        txtLayoutHusbandName = (TextInputLayout)findViewById(R.id.txtLayoutHusbandNameInfo);
        txtLayoutHusbandIC = (TextInputLayout)findViewById(R.id.txtLayoutHusbandICInfo);
        txtLayoutHusbandWork = (TextInputLayout)findViewById(R.id.txtLayoutHusbandWorkInfo);
        txtLayoutHusbandWorkPlacr = (TextInputLayout)findViewById(R.id.txtLayoutHusbandWorkAddressInfo);
        editTextEDD = (EditText)findViewById(R.id.editTextEDDInfo);
        editTextDisease = (EditText)findViewById(R.id.editTextDiseaseInfo);
        editTextLNMP = (EditText)findViewById(R.id.editTextLNMPInfo);
        editTextEDP = (EditText)findViewById(R.id.editTextEDPInfo);
        editTextHeight = (EditText)findViewById(R.id.editTextHeight);
        editTextWeight = (EditText)findViewById(R.id.editTextWeight);
        editTextHusbandName = (EditText)findViewById(R.id.editTextHusbandNameInfo);
        editTextHusbandIC = (EditText)findViewById(R.id.editTextHusbandICInfo);
        editTextHusbandWork = (EditText)findViewById(R.id.editTextHusbandWorkInfo);
        editTextHusbandWorkAddress = (EditText)findViewById(R.id.editTextHusbandWorkAddressInfo);
        editTextHusbandPhone = (EditText)findViewById(R.id.editTextHusbandPhoneInfo);
        radioGroupYesNo = (RadioGroup)findViewById(R.id.radioGroupYesNoInfo);
        radioGroupMarriage = (RadioGroup)findViewById(R.id.radioGroupMarriageInfo);
        radioBtnNo = (RadioButton)findViewById(R.id.radioBtnNoInfo);
        radioBtnYes = (RadioButton)findViewById(R.id.radioBtnYesInfo);
        radioBtnMarried = (RadioButton)findViewById(R.id.radioBtnMarriedInfo);
        radioBtnSingle = (RadioButton)findViewById(R.id.radioBtnSingleInfo);

        buttonCancel = (Button)findViewById(R.id.buttonCancel);
        buttonCancelInfo = (Button)findViewById(R.id.buttonCancelInfo);
        buttonUpdateInfo = (Button)findViewById(R.id.buttonUpdateInfo);
        buttonUpdate = (Button)findViewById(R.id.buttonUpdate);
        btnUpdateApp = (Button)findViewById(R.id.btnUpdateApp);
        btnCancelApp = (Button)findViewById(R.id.btnCancelApp);

        SetVisibility();
        DisableInfoEditText();
        DisableDetailInfoEditText();
        DisableAppointmentText();

        layoutDetailInfo.setVisibility(View.GONE);
        imageViewUp.setVisibility(View.GONE);
        imageViewDown1.setVisibility(View.GONE);
        buttonCancel.setVisibility(View.GONE);
        buttonCancelInfo.setVisibility(View.GONE);
        imageViewDownApp.setVisibility(View.GONE);
        btnCancelApp.setVisibility(View.GONE);

        editTextEDD.setFocusable(false);
        editTextEDD.setClickable(false);
        editTextLNMP.setFocusable(false);
        editTextLNMP.setClickable(false);
        editTextEDP.setFocusable(false);
        editTextEDP.setClickable(false);
        editTextAppointment.setFocusable(false);
        editTextAppointment.setClickable(false);
        editTextAppTime.setFocusable(false);
        editTextAppTime.setClickable(false);

        final ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(MommyInfoActivity.this ,R.array.national, R.layout.support_simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spinnerNational.setAdapter(adapter);

        radioGroupRace.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(radioBtnOtherRaces.isChecked())
                {
                    txtIinputLayoutOtherRace.setVisibility(View.VISIBLE);
                }else{
                    txtIinputLayoutOtherRace.setVisibility(View.GONE);
                    editTextRace.setText("");
                }
            }
        });

        editTextAppTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
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
                                editTextLNMP.setText(dayOfMonth + "/" +(month + 1) + "/" + year);
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
                                editTextEDP.setText(dayOfMonth + "/" +(month + 1) + "/" + year);
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
                    editTextDisease.setText("");
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
                    editTextHusbandName.setText("");
                    editTextHusbandIC.setText("");
                    editTextHusbandWork.setText("");
                    editTextHusbandWorkAddress.setText("");
                    editTextHusbandPhone.setText("");
                }
            }
        });

        Intent intent = getIntent();
        id = intent.getStringExtra("MommyID");
        mFirebaseFirestore = FirebaseFirestore.getInstance();
        mCollectionReference = mFirebaseFirestore.collection("Mommy");
        oCollectionReference = mFirebaseFirestore.collection("MommyHealthInfo");

        progressBarMummyInfo.setVisibility(View.VISIBLE);
        layoutAllView.setVisibility(View.GONE);

        oCollectionReference.whereEqualTo("healthInfoId", SaveSharedPreference.getHealthInfoId(MommyInfoActivity.this)).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for(QueryDocumentSnapshot documentSnapshots : queryDocumentSnapshots)
                {
                    healthInfoId = documentSnapshots.getId();
                }
            }
        });
        mCollectionReference.whereEqualTo("mommyId", id).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for(QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots)
                {
                    int check = 0;
                    Mommy mommy = documentSnapshot.toObject(Mommy.class);
                    key = documentSnapshot.getId();
                    textViewMummyInfoName.setText(mommy.getMommyName());
                    textViewMummyInfoAge.setText(mommy.getAge()+"");
                    textViewMummyInfoID.setText(mommy.getMommyId());
                    if(mommy.getMummyImage().equals(""))
                    {
                        imageViewMummyInfo.setImageResource(R.drawable.user);
                    }else{
                        Glide.with(MommyInfoActivity.this).load(mommy.getMummyImage()).into(imageViewMummyInfo);
                    }
                    editTextMummyName.setText(mommy.getMommyName());
                    editTextIC.setText(mommy.getMommyIC());
                    ArrayAdapter<CharSequence> arrayAdapter = ArrayAdapter.createFromResource(MommyInfoActivity.this ,R.array.national, R.layout.support_simple_spinner_dropdown_item);
                    spinnerNational.setSelection(arrayAdapter.getPosition(mommy.getNationality()));
                    for(int i=0; i<radioGroupRace.getChildCount();i++)
                    {
                        if(((RadioButton)radioGroupRace.getChildAt(i)).getText().toString().equals(mommy.getRace()))
                        {
                            check++;
                            ((RadioButton)radioGroupRace.getChildAt(i)).setChecked(true);
                        }
                        if(check == 0){
                            txtIinputLayoutOtherRace.setVisibility(View.VISIBLE);
                            editTextRace.setText(mommy.getRace());
                            radioBtnOtherRaces.setChecked(true);
                        }

                    }
                    editTextAddress.setText(mommy.getAddress());
                    editTextPhone.setText(mommy.getPhoneNo());
                    editTextEmail.setText(mommy.getEmail());
                    editTextOccupation.setText(mommy.getOccupation());
                    editTextAge.setText(mommy.getAge()+"");
                    editTextEducation.setText(mommy.getEducation());
                    if(mommy.getStatus().equals("Active"))
                    {
                        chkBoxStatus.setChecked(true);
                    }else{
                        chkBoxStatus.setChecked(false);
                    }

                    GetMummyDetail(key);
                    GetAppointmentDate(key);
                }
            }
        });

        buttonUpdateInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickInfo++;
                if(clickInfo == 1)
                {
                    EnableInfoEditText();
                    buttonCancelInfo.setVisibility(View.VISIBLE);
                }else if(clickInfo == 2)
                {
                    mDocumentReference = mFirebaseFirestore.document("Mommy/"+key);
                    nDocumentReference = mFirebaseFirestore.document("MommyHealthInfo/"+healthInfoId);
                    mDocumentReference.update("mommyName",editTextMummyName.getText().toString());
                    mDocumentReference.update("mommyIC",editTextIC.getText().toString());
                    mDocumentReference.update("nationality", spinnerNational.getSelectedItem().toString());
                    if(radioBtnMalay.isChecked())
                    {
                        mDocumentReference.update("race", radioBtnMalay.getText().toString());
                    }else if(radioBtnChinese.isChecked())
                    {
                        mDocumentReference.update("race", radioBtnChinese.getText().toString());
                    }else if(radioBtnIndian.isChecked())
                    {
                        mDocumentReference.update("race", radioBtnIndian.getText().toString());
                    }else{
                        mDocumentReference.update("race", editTextRace.getText().toString());
                    }
                    mDocumentReference.update("address", editTextAddress.getText().toString());
                    mDocumentReference.update("phoneNo", editTextPhone.getText().toString());
                    mDocumentReference.update("email", editTextEmail.getText().toString());
                    mDocumentReference.update("occupation", editTextOccupation.getText().toString());
                    mDocumentReference.update("age", Integer.parseInt(editTextAge.getText().toString()));
                    mDocumentReference.update("education", editTextEducation.getText().toString());
                    if(chkBoxStatus.isChecked()){
                        mDocumentReference.update("status", "Active");
                        nDocumentReference.update("status", "Active");
                    }else{
                        mDocumentReference.update("status", "Inactive");
                        nDocumentReference.update("status", "Inactive");
                    }
                    clickInfo = 0;
                    Toast.makeText(MommyInfoActivity.this,"Successfully Updated!", Toast.LENGTH_LONG).show();
                    DisableInfoEditText();
                    buttonCancelInfo.setVisibility(View.GONE);
                }
            }
        });

        buttonCancelInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DisableInfoEditText();
                buttonCancelInfo.setVisibility(View.GONE);
                clickInfo = 0;
            }
        });

        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickInfoDetail++;
                if(clickInfoDetail == 1)
                {
                    EnableDetailInfoEditText();
                    buttonCancel.setVisibility(View.VISIBLE);
                }else if(clickInfoDetail == 2){
                    Date dateEDD = null, dateLNMP = null, dateEDP = null;
                    mDocumentReference = mFirebaseFirestore.document("Mommy/"+key+"/MommyDetail/"+keyDetail);
                    mDocumentReference.update("height",Double.parseDouble(editTextHeight.getText().toString()));
                    mDocumentReference.update("weight", Double.parseDouble(editTextWeight.getText().toString()));
                    if(radioBtnYes.isChecked()){
                        mDocumentReference.update("familyDisease","");
                    }else{
                        mDocumentReference.update("familyDisease",editTextDisease.getText().toString());
                    }
                    try {
                        dateEDD = new SimpleDateFormat("dd/MM/yyyy").parse(editTextEDD.getText().toString());
                        dateLNMP = new SimpleDateFormat("dd/MM/yyyy").parse(editTextLNMP.getText().toString());
                        dateEDP = new SimpleDateFormat("dd/MM/yyyy").parse(editTextEDP.getText().toString());
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    mDocumentReference.update("edd", dateEDD);
                    mDocumentReference.update("lnmp", dateLNMP);
                    mDocumentReference.update("edp", dateEDP);
                    if(radioBtnSingle.isChecked())
                    {
                        mDocumentReference.update("marriageStatus", radioBtnSingle.getText().toString());
                    }else{
                        mDocumentReference.update("marriageStatus", radioBtnMarried.getText().toString());
                    }
                    mDocumentReference.update("husbandName", editTextHusbandName.getText().toString());
                    mDocumentReference.update("husbandIC", editTextHusbandIC.getText().toString());
                    mDocumentReference.update("husbandWork", editTextHusbandWork.getText().toString());
                    mDocumentReference.update("husbandWorkAddress", editTextHusbandWorkAddress.getText().toString());
                    mDocumentReference.update("husbandPhone", editTextHusbandPhone.getText().toString());
                    Toast.makeText(MommyInfoActivity.this,"Successfully Updated!", Toast.LENGTH_LONG).show();
                    clickInfoDetail = 0;
                    DisableDetailInfoEditText();
                    buttonCancel.setVisibility(View.GONE);
                }
            }
        });

        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DisableDetailInfoEditText();
                buttonCancel.setVisibility(View.GONE);
                clickInfoDetail = 0;
            }
        });

        btnUpdateApp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickApp++;
                if(clickApp == 1)
                {
                    EnableAppointmentText();
                    btnCancelApp.setVisibility(View.VISIBLE);
                }else if(clickApp == 2)
                {
                    qCollectionReference = mFirebaseFirestore.collection("MommyHealthInfo").document(healthInfoId).collection("Notification");
                    if(isEmpty == false)
                    {
                        mDocumentReference = mFirebaseFirestore.document("Mommy/"+key+"/AppointmentDate/"+appointmentKey);
                        Date appDate = null;
                        Date timeApp = null;
                        try {
                            appDate = new SimpleDateFormat("dd/MM/yyyy").parse(editTextAppointment.getText().toString());
                            timeApp = new SimpleDateFormat("HH:mm").parse(editTextAppTime.getText().toString());
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        String medicalPersonnelId = SaveSharedPreference.getID(MommyInfoActivity.this);
                        Date createdDate = new Date();
                        mDocumentReference.update("appointmentDate", appDate);
                        mDocumentReference.update("appointmentTime", timeApp);
                        String notificationDetai = getResources().getString(R.string.NotificationAppointment) + editTextAppointment.getText().toString() + " " + editTextAppTime.getText().toString();
                        Date notificationDate = new Date();
                        Notification notification = new Notification(notificationDetai, notificationDate, medicalPersonnelId, createdDate, "new", key);
                        qCollectionReference.add(notification).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {
                                Toast.makeText(MommyInfoActivity.this,"Successfully Updated!", Toast.LENGTH_LONG).show();
                            }
                        });
                        clickApp = 0;
                        DisableAppointmentText();
                        btnCancelApp.setVisibility(View.GONE);
                    }else{
                        Date appDate = null;
                        Date timeApp = null;
                        try {
                            appDate = new SimpleDateFormat("dd/MM/yyyy").parse(editTextAppointment.getText().toString());
                            timeApp = new SimpleDateFormat("HH:mm").parse(editTextAppTime.getText().toString());
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        String medicalPersonnelId = SaveSharedPreference.getID(MommyInfoActivity.this);
                        Date createdDate = new Date();
                        String notificationDetai = getResources().getString(R.string.NotificationAppointment) + editTextAppointment.getText().toString() + " " + editTextAppTime.getText().toString();
                        Date notificationDate = new Date();
                        final AppointmentDate ad = new AppointmentDate(appDate, timeApp, id, medicalPersonnelId, createdDate);
                        Notification notification = new Notification(notificationDetai, notificationDate, medicalPersonnelId, createdDate, "new", key);
                        qCollectionReference.add(notification).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {
                                pCollectionReference.add(ad).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                    @Override
                                    public void onSuccess(DocumentReference documentReference) {
                                        AlertDialog.Builder builder = new AlertDialog.Builder(MommyInfoActivity.this);
                                        builder.setTitle("Save Successfully");
                                        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) {
                                                return;
                                            }
                                        });
                                        builder.setMessage("Appointment Save Successful!");
                                        AlertDialog alert = builder.create();
                                        alert.show();
                                    }
                                });
                            }
                        });
                        clickApp = 0;
                        DisableAppointmentText();
                        btnCancelApp.setVisibility(View.GONE);
                    }
                }
            }
        });

        btnCancelApp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DisableAppointmentText();
                btnCancelApp.setVisibility(View.GONE);
                clickApp = 0;
            }
        });

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void GetAppointmentDate(String key)
    {
        pCollectionReference = mFirebaseFirestore.collection("Mommy/"+key+"/AppointmentDate");
        pCollectionReference.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                if(queryDocumentSnapshots.isEmpty())
                {
                    isEmpty = true;
                    editTextAppointment.setText("No appointment has been made yet");
                    editTextAppTime.setText("No appointment has been made yet");
                }else{
                    isEmpty = false;
                    for(QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots)
                    {
                        appointmentKey = documentSnapshot.getId();
                        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
                        AppointmentDate ad = documentSnapshot.toObject(AppointmentDate.class);
                        editTextAppointment.setText(dateFormat.format(ad.getAppointmentDate()));
                        editTextAppTime.setText(timeFormat.format(ad.getAppointmentTime()));
                    }
                }
            }
        });
    }

    private void GetMummyDetail(String key)
    {
        nCollectionReference = mFirebaseFirestore.collection("Mommy").document(key).collection("MommyDetail");
        nCollectionReference.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for(QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots)
                {
                    MommyDetail mommyDetail = documentSnapshot.toObject(MommyDetail.class);
                    keyDetail = documentSnapshot.getId();
                    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                    SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
                    editTextHeight.setText(mommyDetail.getHeight()+"");
                    editTextWeight.setText(mommyDetail.getWeight()+"");
                    if(mommyDetail.getFamilyDisease().equals(""))
                    {
                        radioBtnNo.setChecked(true);
                    }else{
                        txtInputLayoutDisease.setVisibility(View.VISIBLE);
                        editTextDisease.setText(mommyDetail.getFamilyDisease());
                    }
                    editTextEDD.setText(dateFormat.format(mommyDetail.getEdd()));
                    editTextLNMP.setText(dateFormat.format(mommyDetail.getLnmp()));
                    editTextEDP.setText(dateFormat.format(mommyDetail.getEdp()));
                    if(mommyDetail.getMarriageStatus().equals("Single"))
                    {
                        radioBtnSingle.setChecked(true);
                    }else{
                        radioBtnMarried.setChecked(true);
                        txtLayoutHusbandName.setVisibility(View.VISIBLE);
                        txtLayoutHusbandIC.setVisibility(View.VISIBLE);
                        txtLayoutHusbandWork.setVisibility(View.VISIBLE);
                        txtLayoutHusbandWorkPlacr.setVisibility(View.VISIBLE);
                        txtLayoutHusbandPhone.setVisibility(View.VISIBLE);
                        editTextHusbandName.setText(mommyDetail.getHusbandName());
                        editTextHusbandIC.setText(mommyDetail.getHusbandIC());
                        editTextHusbandPhone.setText(mommyDetail.getHusbandPhone());
                        editTextHusbandWork.setText(mommyDetail.getHusbandWork());
                        editTextHusbandWorkAddress.setText(mommyDetail.getHusbandWorkAddress());
                    }
                    progressBarMummyInfo.setVisibility(View.GONE);
                    layoutAllView.setVisibility(View.VISIBLE);
                }
            }
        });
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
        editTextAppointment.setTextColor(Color.parseColor("#000000"));
        editTextAppTime.setEnabled(false);
        editTextAppTime.setTextColor(Color.parseColor("#000000"));
    }

    private void EnableAppointmentText()
    {
        editTextAppointment.setEnabled(true);
        editTextAppTime.setEnabled(true);
    }

    private void DisableInfoEditText()
    {
        editTextMummyName.setEnabled(false);
        editTextMummyName.setTextColor(Color.parseColor("#000000"));
        editTextIC.setEnabled(false);
        editTextIC.setTextColor(Color.parseColor("#000000"));
        editTextRace.setEnabled(false);
        editTextRace.setTextColor(Color.parseColor("#000000"));
        editTextAddress.setEnabled(false);
        editTextAddress.setTextColor(Color.parseColor("#000000"));
        editTextPhone.setEnabled(false);
        editTextPhone.setTextColor(Color.parseColor("#000000"));
        editTextEmail.setEnabled(false);
        editTextEmail.setTextColor(Color.parseColor("#000000"));
        editTextAge.setEnabled(false);
        editTextAge.setTextColor(Color.parseColor("#000000"));
        editTextOccupation.setEnabled(false);
        editTextOccupation.setTextColor(Color.parseColor("#000000"));
        editTextEducation.setEnabled(false);
        editTextEducation.setTextColor(Color.parseColor("#000000"));
        chkBoxStatus.setEnabled(false);
        spinnerNational.setEnabled(false);
        for(int i=0; i<radioGroupRace.getChildCount(); i++) {
            ((RadioButton)radioGroupRace.getChildAt(i)).setEnabled(false);
        }
    }

    private void DisableDetailInfoEditText()
    {
        editTextEDD.setEnabled(false);
        editTextEDD.setTextColor(Color.parseColor("#000000"));
        editTextDisease.setEnabled(false);
        editTextDisease.setTextColor(Color.parseColor("#000000"));
        editTextLNMP.setEnabled(false);
        editTextLNMP.setTextColor(Color.parseColor("#000000"));
        editTextEDP.setEnabled(false);
        editTextEDP.setTextColor(Color.parseColor("#000000"));
        editTextHeight.setEnabled(false);
        editTextHeight.setTextColor(Color.parseColor("#000000"));
        editTextWeight.setEnabled(false);
        editTextWeight.setTextColor(Color.parseColor("#000000"));
        editTextHusbandName.setEnabled(false);
        editTextHusbandName.setTextColor(Color.parseColor("#000000"));
        editTextHusbandIC.setEnabled(false);
        editTextHusbandIC.setTextColor(Color.parseColor("#000000"));
        editTextHusbandWork.setEnabled(false);
        editTextHusbandWork.setTextColor(Color.parseColor("#000000"));
        editTextHusbandWorkAddress.setEnabled(false);
        editTextHusbandWorkAddress.setTextColor(Color.parseColor("#000000"));
        editTextHusbandPhone.setEnabled(false);
        editTextHusbandPhone.setTextColor(Color.parseColor("#000000"));
        for(int i=0; i<radioGroupYesNo.getChildCount(); i++){
            ((RadioButton)radioGroupYesNo.getChildAt(i)).setEnabled(false);
        }
        for(int i=0; i<radioGroupMarriage.getChildCount(); i++){
            ((RadioButton)radioGroupMarriage.getChildAt(i)).setEnabled(false);
        }
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
        for(int i=0; i<radioGroupYesNo.getChildCount(); i++){
            ((RadioButton)radioGroupYesNo.getChildAt(i)).setEnabled(true);
        }
        for(int i=0; i<radioGroupMarriage.getChildCount(); i++){
            ((RadioButton)radioGroupMarriage.getChildAt(i)).setEnabled(true);
        }
    }

    private void EnableInfoEditText()
    {
        editTextMummyName.setEnabled(true);
        editTextIC.setEnabled(true);
        editTextRace.setEnabled(true);
        editTextAddress.setEnabled(true);
        editTextPhone.setEnabled(true);
        editTextEmail.setEnabled(true);
        editTextAge.setEnabled(true);
        editTextOccupation.setEnabled(true);
        editTextEducation.setEnabled(true);
        chkBoxStatus.setEnabled(true);
        spinnerNational.setEnabled(true);
        for(int i=0; i<radioGroupRace.getChildCount(); i++) {
            ((RadioButton)radioGroupRace.getChildAt(i)).setEnabled(true);
        }
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
