package com.example.mommyhealthapp.Nurse;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.LinearLayoutCompat;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;

import com.example.mommyhealthapp.AlertService;
import com.example.mommyhealthapp.Class.AppointmentDate;
import com.example.mommyhealthapp.Class.MedicalPersonnel;
import com.example.mommyhealthapp.Class.Notification;
import com.example.mommyhealthapp.Class.PregnancyExamination;
import com.example.mommyhealthapp.Class.ProblemPE;
import com.example.mommyhealthapp.MainActivity;
import com.example.mommyhealthapp.NotifyService;
import com.example.mommyhealthapp.R;
import com.example.mommyhealthapp.ReminderService;
import com.example.mommyhealthapp.SaveSharedPreference;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.w3c.dom.Text;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class PregnancyExaminationActivity extends AppCompatActivity {

    private EditText editTextDueDate, editTextProblem, editTextTreatment, bodyWeightEditText, bloodPressureEditText, pulseEditText, editTextBookingWeight, editTextBookingBMI,
            editTextBookingBP, editTextLKKR, editTextNextAppTime, editTextUrineAlb, editTextUrineSugar, editTextHB, editTextEdema, editTextPregnancyPeriod, editTextUterineHeight,
            editTextPresentPosition, editTextPEHeart, editTextPEMotion;
    DatePickerDialog datePickerDialog;
    TimePickerDialog timePickerDialog;
    private TextInputLayout bodyWeightInputLayout, bloodPressureInputLayout, pulseInputLayout, layoutBookingWeight, layoutBookingBMI, layoutBookingBP,
            layoutLKKR, layoutUrineAlb, layoutUrineSugar, layoutHB, layoutPregnancyPeriod, txtInputLayoutDueDate, layoutNextAppTime, layoutPEProblem, layoutPETreatment;
    private RelativeLayout relativeLayoutPE;
    private LinearLayoutCompat layoutPE;
    private ProgressBar progressBarPE;
    private FloatingActionButton btnAddProblem;
    private Button btnProblemSave, btnProblemEdit, btnSavePE, btnCancelPE;
    private ListView listViewProblem;
    private ProblemAdapter problemAdapter;
    private List<ProblemPE> problemList;
    private Dialog problemDialog;

    private String pregnancyExamId, healthInfoId, medicalPersonnelname, key, nextAppDate, nextAppTime, appKey, mommyKey;
    private Boolean isEmpty, appIsEmpty;
    private int check = 0;

    private FirebaseFirestore mFirebaseFirestore;
    private CollectionReference mCollectionReference, nCollectionReference, oCollectionReference, pCollectionReference;
    private DocumentReference mDocumentReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pregnancy_examination);

        editTextDueDate = (EditText)findViewById(R.id.editTextDueDate);
        bodyWeightEditText = (EditText)findViewById(R.id.bodyWeightEditText);
        bloodPressureEditText = (EditText)findViewById(R.id.bloodPressureEditText);
        pulseEditText = (EditText)findViewById(R.id.pulseEditText);
        editTextBookingWeight = (EditText)findViewById(R.id.editTextBookingWeight);
        editTextBookingBMI = (EditText)findViewById(R.id.editTextBookingBMI);
        editTextBookingBP = (EditText)findViewById(R.id.editTextBookingBP);
        editTextLKKR = (EditText)findViewById(R.id.editTextLKKR);
        editTextNextAppTime = (EditText)findViewById(R.id.editTextNextAppTime);
        editTextUrineAlb = (EditText)findViewById(R.id.editTextUrineAlb);
        editTextUrineSugar = (EditText)findViewById(R.id.editTextUrineSugar);
        editTextHB = (EditText)findViewById(R.id.editTextHB);
        editTextEdema = (EditText)findViewById(R.id.editTextEdema);
        editTextPregnancyPeriod = (EditText)findViewById(R.id.editTextPregnancyPeriod);
        editTextUterineHeight = (EditText)findViewById(R.id.editTextUterineHeight);
        editTextPresentPosition = (EditText)findViewById(R.id.editTextPresentPosition);
        editTextPEHeart = (EditText)findViewById(R.id.editTextPEHeart);
        editTextPEMotion = (EditText)findViewById(R.id.editTextPEMotion);

        relativeLayoutPE = (RelativeLayout)findViewById(R.id.relativeLayoutPE);
        layoutPE = (LinearLayoutCompat)findViewById(R.id.layoutPE);
        progressBarPE = (ProgressBar)findViewById(R.id.progressBarPE);
        listViewProblem = (ListView)findViewById(R.id.listViewProblem);

        bodyWeightInputLayout = (TextInputLayout)findViewById(R.id.bodyWeightInputLayout);
        bloodPressureInputLayout = (TextInputLayout)findViewById(R.id.bloodPressureInputLayout);
        pulseInputLayout = (TextInputLayout)findViewById(R.id.pulseInputLayout);
        layoutBookingWeight = (TextInputLayout)findViewById(R.id.layoutBookingWeight);
        layoutBookingBMI = (TextInputLayout)findViewById(R.id.layoutBookingBMI);
        layoutBookingBP = (TextInputLayout)findViewById(R.id.layoutBookingBP);
        layoutLKKR = (TextInputLayout)findViewById(R.id.layoutLKKR);
        txtInputLayoutDueDate = (TextInputLayout)findViewById(R.id.txtInputLayoutDueDate);
        layoutNextAppTime = (TextInputLayout)findViewById(R.id.layoutNextAppTime);
        layoutUrineAlb = (TextInputLayout)findViewById(R.id.layoutUrineAlb);
        layoutUrineSugar = (TextInputLayout)findViewById(R.id.layoutUrineSugar);
        layoutHB = (TextInputLayout)findViewById(R.id.layoutHB);
        layoutPregnancyPeriod = (TextInputLayout)findViewById(R.id.layoutPregnancyPeriod);

        btnSavePE = (Button)findViewById(R.id.btnSavePE);
        btnCancelPE = (Button)findViewById(R.id.btnCancelPE);
        btnAddProblem = (FloatingActionButton)findViewById(R.id.btnAddProblem);

        problemList = new ArrayList<>();

        editTextDueDate.setFocusable(false);
        editTextDueDate.setClickable(false);
        editTextNextAppTime.setFocusable(false);
        editTextNextAppTime.setClickable(false);

        progressBarPE.setVisibility(View.VISIBLE);
        layoutPE.setVisibility(View.GONE);
        btnCancelPE.setVisibility(View.GONE);

        Intent intent = getIntent();
        pregnancyExamId = intent.getStringExtra("pregnancyExamId");
        healthInfoId = intent.getStringExtra("healthInfoId");
        mFirebaseFirestore = FirebaseFirestore.getInstance();
        nCollectionReference = mFirebaseFirestore.collection("MommyHealthInfo").document(healthInfoId).collection("Notification");
        oCollectionReference = mFirebaseFirestore.collection("Mommy");
        oCollectionReference.whereEqualTo("mommyId", SaveSharedPreference.getMummyId(PregnancyExaminationActivity.this)).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for(QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots)
                {
                    mommyKey = documentSnapshot.getId();
                }
                pCollectionReference = mFirebaseFirestore.collection("Mommy").document(mommyKey).collection("AppointmentDate");
                pCollectionReference.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        if(queryDocumentSnapshots.isEmpty())
                        {
                            appIsEmpty = true;
                        }else{
                            appIsEmpty = false;
                            for(QueryDocumentSnapshot documentSnapshotss : queryDocumentSnapshots)
                            {
                                appKey = documentSnapshotss.getId();
                            }
                        }
                    }
                });
            }
        });
        if(!SaveSharedPreference.getUser(PregnancyExaminationActivity.this).equals("Mommy"))
        {
            mDocumentReference = mFirebaseFirestore.collection("MedicalPersonnel").document(SaveSharedPreference.getID(PregnancyExaminationActivity.this));
            mDocumentReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    MedicalPersonnel md = documentSnapshot.toObject(MedicalPersonnel.class);
                    medicalPersonnelname = md.getName();
                }
            });
        }
        mCollectionReference = mFirebaseFirestore.collection("MommyHealthInfo").document(healthInfoId).collection("PregnancyExamination");
        mCollectionReference.whereEqualTo("pregnancyExamId", pregnancyExamId).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                if(queryDocumentSnapshots.isEmpty())
                {
                    isEmpty = true;
                    progressBarPE.setVisibility(View.GONE);
                    layoutPE.setVisibility(View.VISIBLE);
                }else{
                    isEmpty = false;
                    DisableField();
                    if(SaveSharedPreference.getUser(PregnancyExaminationActivity.this).equals("Mommy"))
                    {
                        btnSavePE.setVisibility(View.GONE);
                        btnCancelPE.setVisibility(View.GONE);
                        btnAddProblem.setVisibility(View.GONE);
                    }
                    for(QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots)
                    {
                        key = documentSnapshot.getId();
                        PregnancyExamination pe = documentSnapshot.toObject(PregnancyExamination.class);
                        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
                        editTextDueDate.setText(dateFormat.format(pe.getNextAppointmentDate()));
                        editTextNextAppTime.setText(timeFormat.format(pe.getGetNextAppointmentTime()));
                        nextAppDate = editTextDueDate.getText().toString();
                        nextAppTime = editTextNextAppTime.getText().toString();
                        bodyWeightEditText.setText(pe.getBodyWeight()+"");
                        bloodPressureEditText.setText(pe.getBloodPressure()+"");
                        pulseEditText.setText(pe.getPulse()+"");
                        editTextBookingWeight.setText(pe.getBookingWeight()+"");
                        editTextBookingBMI.setText(pe.getBookingBMI()+"");
                        editTextBookingBP.setText(pe.getBookingBP()+"");
                        editTextLKKR.setText(pe.getLkkr());
                        editTextUrineAlb.setText(pe.getUrineAlb()+"");
                        editTextUrineSugar.setText(pe.getUrineSugar()+"");
                        editTextHB.setText(pe.getHb()+"");
                        editTextEdema.setText(pe.getEdema());
                        editTextPregnancyPeriod.setText(pe.getPregnancyWeek()+"");
                        editTextUterineHeight.setText(pe.getUterineHeight()+"");
                        editTextPresentPosition.setText(pe.getPresentPosition());
                        editTextPEHeart.setText(pe.getHeart());
                        editTextPEMotion.setText(pe.getMotion());
                        problemList = pe.getPeProblem();
                        problemAdapter = new ProblemAdapter(PregnancyExaminationActivity.this, R.layout.activity_pregnancy_examination, problemList);
                        listViewProblem.setAdapter(problemAdapter);
                    }
                    progressBarPE.setVisibility(View.GONE);
                    layoutPE.setVisibility(View.VISIBLE);
                    btnSavePE.setText("Update");
                }
            }
        });

        editTextDueDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR); // current year
                int mMonth = c.get(Calendar.MONTH); // current month
                int mDay = c.get(Calendar.DAY_OF_MONTH); // current day
                datePickerDialog = new DatePickerDialog(PregnancyExaminationActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                editTextDueDate.setText(dayOfMonth + "/" +(month + 1) + "/" + year);
                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });

        editTextNextAppTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                timePickerDialog = new TimePickerDialog(PregnancyExaminationActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        editTextNextAppTime.setText( selectedHour + ":" + selectedMinute);
                    }
                }, hour, minute, true);//Yes 24 hour time
                timePickerDialog.setTitle("Select Time");
                timePickerDialog.show();
            }
        });

        listViewProblem.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int action = event.getAction();
                switch (action) {
                    case MotionEvent.ACTION_DOWN:
                        // Disallow ScrollView to intercept touch events.
                        v.getParent().requestDisallowInterceptTouchEvent(true);
                        break;

                    case MotionEvent.ACTION_UP:
                        // Allow ScrollView to intercept touch events.
                        v.getParent().requestDisallowInterceptTouchEvent(false);
                        break;
                }

                // Handle ListView touch events.
                v.onTouchEvent(event);
                return true;
            }
        });

        listViewProblem.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final ProblemPE problemPE = problemAdapter.getItem(position);

                problemDialog = new Dialog(PregnancyExaminationActivity.this);
                problemDialog.setContentView(R.layout.customize_problem_dialog);
                problemDialog.setTitle("Problem Dialog");
                editTextProblem = (EditText)problemDialog.findViewById(R.id.editTextProblem);
                editTextTreatment = (EditText)problemDialog.findViewById(R.id.editTextTreatment);
                layoutPEProblem = (TextInputLayout)problemDialog.findViewById(R.id.layoutPEProblem);
                layoutPETreatment = (TextInputLayout)problemDialog.findViewById(R.id.layoutPETreatment);
                btnProblemSave = (Button)problemDialog.findViewById(R.id.btnProblemSave);
                btnProblemEdit = (Button)problemDialog.findViewById(R.id.btnProblemEdit);

                CheckRequiredDialog();

                btnProblemSave.setVisibility(View.GONE);
                editTextProblem.setText(problemPE.getProblem());
                editTextTreatment.setText(problemPE.getTreatment());
                if(isEmpty == false || SaveSharedPreference.getUser(PregnancyExaminationActivity.this).equals("Mommy"))
                {
                    if(check == 0 || SaveSharedPreference.getUser(PregnancyExaminationActivity.this).equals("Mommy"))
                    {
                        btnProblemEdit.setVisibility(View.GONE);
                        editTextProblem.setEnabled(false);
                        editTextProblem.setTextColor(Color.parseColor("#000000"));
                        editTextTreatment.setEnabled(false);
                        editTextTreatment.setTextColor(Color.parseColor("#000000"));
                    }else if(check == 1){
                        btnProblemEdit.setVisibility(View.VISIBLE);
                        editTextProblem.setEnabled(true);
                        editTextTreatment.setEnabled(true);
                    }
                }
                btnProblemEdit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(!SaveCheckDialogRequired())
                        {
                            for(int i=0; i<problemList.size(); i++)
                            {
                                if(problemList.get(i).equals(problemPE))
                                {
                                    String problem = editTextProblem.getText().toString();
                                    String treatment = editTextTreatment.getText().toString();
                                    SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
                                    Date date = new Date();
                                    String pDate = formatter.format(date);
                                    problemList.get(i).setProblem(problem);
                                    problemList.get(i).setTreatment(treatment);
                                    problemList.get(i).setDate(pDate);
                                    problemList.get(i).setPersonnel(medicalPersonnelname);
                                    problemAdapter.notifyDataSetChanged();
                                }
                            }
                            problemDialog.cancel();
                        }
                    }
                });

                problemDialog.show();
                Window window =problemDialog.getWindow();
                window.setLayout(800, 800);
            }
        });

        btnAddProblem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                problemDialog = new Dialog(PregnancyExaminationActivity.this);
                problemDialog.setContentView(R.layout.customize_problem_dialog);
                problemDialog.setTitle("Problem Dialog");
                editTextProblem = (EditText)problemDialog.findViewById(R.id.editTextProblem);
                editTextTreatment = (EditText)problemDialog.findViewById(R.id.editTextTreatment);
                layoutPEProblem = (TextInputLayout)problemDialog.findViewById(R.id.layoutPEProblem);
                layoutPETreatment = (TextInputLayout)problemDialog.findViewById(R.id.layoutPETreatment);
                btnProblemSave = (Button)problemDialog.findViewById(R.id.btnProblemSave);
                btnProblemEdit = (Button)problemDialog.findViewById(R.id.btnProblemEdit);

                CheckRequiredDialog();

                btnProblemEdit.setVisibility(View.GONE);

                btnProblemSave.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(!SaveCheckDialogRequired())
                        {
                            String problem = editTextProblem.getText().toString();
                            String treatment = editTextTreatment.getText().toString();
                            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
                            Date date = new Date();
                            String pDate = formatter.format(date);
                            ProblemPE ppe = new ProblemPE(problem, treatment, pDate, medicalPersonnelname);
                            problemList.add(ppe);
                            problemAdapter = new ProblemAdapter(PregnancyExaminationActivity.this, R.layout.activity_pregnancy_examination, problemList);
                            listViewProblem.setAdapter(problemAdapter);
                            problemDialog.cancel();
                        }
                    }
                });

                problemDialog.show();
                Window window =problemDialog.getWindow();
                window.setLayout(800, 800);
            }
        });

        CheckRequiredTextChange();

        btnSavePE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isEmpty == true)
                {
                    if(!SaveCheckRequiredField())
                    {
                        progressBarPE.setVisibility(View.VISIBLE);
                        layoutPE.setVisibility(View.GONE);
                        String bodyWeight = bodyWeightEditText.getText().toString(),
                                bloodPressure = bloodPressureEditText.getText().toString(),
                                pulse = pulseEditText.getText().toString(),
                                bookingWeight = editTextBookingWeight.getText().toString(),
                                bookingBMI = editTextBookingBMI.getText().toString(),
                                bookingBP = editTextBookingBP.getText().toString(),
                                lkkr = editTextLKKR.getText().toString(),
                                urineAlb = editTextUrineAlb.getText().toString(),
                                urineSugar = editTextUrineSugar.getText().toString(),
                                hb = editTextHB.getText().toString(),
                                edema = editTextEdema.getText().toString(),
                                pregnancyWeek = editTextPregnancyPeriod.getText().toString(),
                                uterineHeight = editTextUterineHeight.getText().toString(),
                                presentPosition = editTextPresentPosition.getText().toString(),
                                heart = editTextPEHeart.getText().toString(),
                                motion = editTextPEMotion.getText().toString(),
                                pregnancyExamId = UUID.randomUUID().toString().replace("-", ""),
                                medicalPersonnelName = medicalPersonnelname;
                        String notificationDetail = getResources().getString(R.string.NotificationAppointment)+editTextDueDate.getText().toString()+ " " + editTextNextAppTime.getText().toString();
                        Date notificationDate = new Date();
                        Date nextAppointmentDate = null, getNextAppointmentTime = null, createdDate = new Date();
                        try {
                            nextAppointmentDate = new SimpleDateFormat("dd/MM/yyyy").parse(editTextDueDate.getText().toString());
                            getNextAppointmentTime = new SimpleDateFormat("HH:mm").parse(editTextNextAppTime.getText().toString());
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        final Notification notification = new Notification(notificationDetail, notificationDate, SaveSharedPreference.getID(PregnancyExaminationActivity.this), createdDate, "new", mommyKey);
                        final AppointmentDate appDate = new AppointmentDate(nextAppointmentDate, getNextAppointmentTime, SaveSharedPreference.getMummyId(PregnancyExaminationActivity.this), SaveSharedPreference.getID(PregnancyExaminationActivity.this), createdDate);
                        PregnancyExamination pe = new PregnancyExamination(Double.parseDouble(bookingWeight), Double.parseDouble(bookingBMI), Double.parseDouble(bookingBP), lkkr,
                                nextAppointmentDate, getNextAppointmentTime, Double.parseDouble(urineAlb), Double.parseDouble(urineSugar), Double.parseDouble(hb),
                                Double.parseDouble(bodyWeight), Double.parseDouble(bloodPressure), Double.parseDouble(pulse), edema, Integer.parseInt(pregnancyWeek),
                                Double.parseDouble(uterineHeight), presentPosition, heart, motion, problemList, medicalPersonnelName, createdDate, pregnancyExamId);
                        mCollectionReference.add(pe).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {
                                nCollectionReference.add(notification).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                    @Override
                                    public void onSuccess(DocumentReference documentReference) {
                                        if(appIsEmpty == true)
                                        {
                                            pCollectionReference.add(appDate).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                                @Override
                                                public void onSuccess(DocumentReference documentReference) {
                                                    progressBarPE.setVisibility(View.GONE);
                                                    layoutPE.setVisibility(View.VISIBLE);
                                                    AlertDialog.Builder builder = new AlertDialog.Builder(PregnancyExaminationActivity.this);
                                                    builder.setTitle("Save Successfully");
                                                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                                        @Override
                                                        public void onClick(DialogInterface dialogInterface, int i) {
                                                            finish();
                                                        }
                                                    });
                                                    builder.setMessage("Save Successful!");
                                                    AlertDialog alert = builder.create();
                                                    alert.setCanceledOnTouchOutside(false);
                                                    alert.show();
                                                }
                                            });
                                        }else{
                                            Date nextAppointmentDate = null, getNextAppointmentTime = null, createdDate = new Date();
                                            try {
                                                nextAppointmentDate = new SimpleDateFormat("dd/MM/yyyy").parse(editTextDueDate.getText().toString());
                                                getNextAppointmentTime = new SimpleDateFormat("HH:mm").parse(editTextNextAppTime.getText().toString());
                                            } catch (ParseException e) {
                                                e.printStackTrace();
                                            }
                                            DocumentReference pDocumentReference = mFirebaseFirestore.collection("Mommy").document(mommyKey).collection("AppointmentDate").document(appKey);
                                            pDocumentReference.update("appointmentDate", nextAppointmentDate);
                                            pDocumentReference.update("appointmentTime", getNextAppointmentTime);
                                            pDocumentReference.update("mommyId", SaveSharedPreference.getMummyId(PregnancyExaminationActivity.this));
                                            pDocumentReference.update("medicalPersonnelId", SaveSharedPreference.getID(PregnancyExaminationActivity.this));
                                            pDocumentReference.update("createdDate", createdDate);
                                            progressBarPE.setVisibility(View.GONE);
                                            layoutPE.setVisibility(View.VISIBLE);
                                            AlertDialog.Builder builder = new AlertDialog.Builder(PregnancyExaminationActivity.this);
                                            builder.setTitle("Save Successfully");
                                            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialogInterface, int i) {
                                                    finish();
                                                }
                                            });
                                            builder.setMessage("Save Successful!");
                                            AlertDialog alert = builder.create();
                                            alert.setCanceledOnTouchOutside(false);
                                            alert.show();
                                        }
                                    }
                                });
                            }
                        });
                    }
                }else{
                    check++;
                    if(check == 1)
                    {
                        btnCancelPE.setVisibility(View.VISIBLE);
                        EnableField();
                    }else if(check == 2)
                    {
                        if(!SaveCheckRequiredField())
                        {
                            DocumentReference mDocumentReference = mFirebaseFirestore.collection("MommyHealthInfo").document(healthInfoId).collection("PregnancyExamination").document(key);
                            String bodyWeight = bodyWeightEditText.getText().toString(),
                                    bloodPressure = bloodPressureEditText.getText().toString(),
                                    pulse = pulseEditText.getText().toString(),
                                    bookingWeight = editTextBookingWeight.getText().toString(),
                                    bookingBMI = editTextBookingBMI.getText().toString(),
                                    bookingBP = editTextBookingBP.getText().toString(),
                                    lkkr = editTextLKKR.getText().toString(),
                                    urineAlb = editTextUrineAlb.getText().toString(),
                                    urineSugar = editTextUrineSugar.getText().toString(),
                                    hb = editTextHB.getText().toString(),
                                    edema = editTextEdema.getText().toString(),
                                    pregnancyWeek = editTextPregnancyPeriod.getText().toString(),
                                    uterineHeight = editTextUterineHeight.getText().toString(),
                                    presentPosition = editTextPresentPosition.getText().toString(),
                                    heart = editTextPEHeart.getText().toString(),
                                    motion = editTextPEMotion.getText().toString(),
                                    pregnancyExamId = UUID.randomUUID().toString().replace("-", ""),
                                    medicalPersonnelName = medicalPersonnelname;
                            Date nextAppointmentDate = null, getNextAppointmentTime = null, createdDate = new Date();
                            try {
                                nextAppointmentDate = new SimpleDateFormat("dd/MM/yyyy").parse(editTextDueDate.getText().toString());
                                getNextAppointmentTime = new SimpleDateFormat("HH:mm").parse(editTextNextAppTime.getText().toString());
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                            if(!nextAppDate.equals(editTextDueDate.getText().toString()) || !nextAppTime.equals(editTextNextAppTime.getText().toString()))
                            {
                                String notificationDetail = getResources().getString(R.string.NotificationAppointment)+editTextDueDate.getText().toString()+ " " + editTextNextAppTime.getText().toString();
                                Date notificationDate = new Date();
                                AppointmentDate appDate = new AppointmentDate(nextAppointmentDate, getNextAppointmentTime, SaveSharedPreference.getMummyId(PregnancyExaminationActivity.this), SaveSharedPreference.getID(PregnancyExaminationActivity.this), createdDate);
                                final Notification notification = new Notification(notificationDetail, notificationDate, SaveSharedPreference.getID(PregnancyExaminationActivity.this), createdDate, "new", mommyKey);
                                Log.i("Testing", appIsEmpty+"");
                                Log.i("Testing2", appKey);
                                if(appIsEmpty == true)
                                {
                                    pCollectionReference.add(appDate).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                        @Override
                                        public void onSuccess(DocumentReference documentReference) {
                                            Snackbar snackbar = Snackbar.make(relativeLayoutPE, "Appointment Updated!", Snackbar.LENGTH_LONG);
                                            snackbar.show();
                                            nCollectionReference.add(notification).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                                @Override
                                                public void onSuccess(DocumentReference documentReference) {
                                                    Snackbar snackbar = Snackbar.make(relativeLayoutPE, "Notification Send!", Snackbar.LENGTH_LONG);
                                                    snackbar.show();
                                                }
                                            });
                                        }
                                    });
                                }else{
                                    DocumentReference pDocumentReference = mFirebaseFirestore.collection("Mommy").document(mommyKey).collection("AppointmentDate").document(appKey);
                                    pDocumentReference.update("appointmentDate", nextAppointmentDate);
                                    pDocumentReference.update("appointmentTime", getNextAppointmentTime);
                                    pDocumentReference.update("mommyId", SaveSharedPreference.getMummyId(PregnancyExaminationActivity.this));
                                    pDocumentReference.update("medicalPersonnelId", SaveSharedPreference.getID(PregnancyExaminationActivity.this));
                                    pDocumentReference.update("createdDate", createdDate);
                                    Snackbar snackbar = Snackbar.make(relativeLayoutPE, "Appointment Updated!", Snackbar.LENGTH_LONG);
                                    snackbar.show();
                                    nCollectionReference.add(notification).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                        @Override
                                        public void onSuccess(DocumentReference documentReference) {
                                            Snackbar snackbar = Snackbar.make(relativeLayoutPE, "Notification Send!", Snackbar.LENGTH_LONG);
                                            snackbar.show();
                                        }
                                    });
                                }
                            }
                            mDocumentReference.update("bodyWeight", Double.parseDouble(bodyWeight));
                            mDocumentReference.update("bloodPressure", Double.parseDouble(bloodPressure));
                            mDocumentReference.update("pulse", Double.parseDouble(pulse));
                            mDocumentReference.update("bookingWeight", Double.parseDouble(bookingWeight));
                            mDocumentReference.update("bookingBMI", Double.parseDouble(bookingBMI));
                            mDocumentReference.update("bookingBP", Double.parseDouble(bookingBP));
                            mDocumentReference.update("lkkr", lkkr);
                            mDocumentReference.update("nextAppointmentDate", nextAppointmentDate);
                            mDocumentReference.update("getNextAppointmentTime", getNextAppointmentTime);
                            mDocumentReference.update("urineAlb", Double.parseDouble(urineAlb));
                            mDocumentReference.update("urineSugar", Double.parseDouble(urineSugar));
                            mDocumentReference.update("hb", Double.parseDouble(hb));
                            mDocumentReference.update("edema", edema);
                            mDocumentReference.update("pregnancyWeek", Integer.parseInt(pregnancyWeek));
                            mDocumentReference.update("uterineHeight", Double.parseDouble(uterineHeight));
                            mDocumentReference.update("presentPosition", presentPosition);
                            mDocumentReference.update("heart", heart);
                            mDocumentReference.update("motion", motion);
                            mDocumentReference.update("peProblem", problemList);
                            mDocumentReference.update("medicalPersonnelName", medicalPersonnelName);
                            mDocumentReference.update("createdDate", createdDate);
                            Snackbar snackbar = Snackbar.make(relativeLayoutPE, "Updated Successfully!", Snackbar.LENGTH_LONG);
                            snackbar.show();
                            DisableField();
                            btnCancelPE.setVisibility(View.GONE);
                            check = 0;
                        }else{
                            check = 1;
                        }
                    }
                }
            }
        });

        btnCancelPE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnCancelPE.setVisibility(View.GONE);
                DisableField();
                check = 0;
            }
        });

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    private void DisableField()
    {
        bodyWeightEditText.setEnabled(false);
        bodyWeightEditText.setTextColor(Color.parseColor("#000000"));
        bloodPressureEditText.setEnabled(false);
        bloodPressureEditText.setTextColor(Color.parseColor("#000000"));
        pulseEditText.setEnabled(false);
        pulseEditText.setTextColor(Color.parseColor("#000000"));
        editTextBookingWeight.setEnabled(false);
        editTextBookingWeight.setTextColor(Color.parseColor("#000000"));
        editTextBookingBMI.setEnabled(false);
        editTextBookingBMI.setTextColor(Color.parseColor("#000000"));
        editTextBookingBP.setEnabled(false);
        editTextBookingBP.setTextColor(Color.parseColor("#000000"));
        editTextLKKR.setEnabled(false);
        editTextLKKR.setTextColor(Color.parseColor("#000000"));
        editTextUrineAlb.setEnabled(false);
        editTextUrineAlb.setTextColor(Color.parseColor("#000000"));
        editTextUrineSugar.setEnabled(false);
        editTextUrineSugar.setTextColor(Color.parseColor("#000000"));
        editTextHB.setEnabled(false);
        editTextHB.setTextColor(Color.parseColor("#000000"));
        editTextEdema.setEnabled(false);
        editTextEdema.setTextColor(Color.parseColor("#000000"));
        editTextPregnancyPeriod.setEnabled(false);
        editTextPregnancyPeriod.setTextColor(Color.parseColor("#000000"));
        editTextUterineHeight.setEnabled(false);
        editTextUterineHeight.setTextColor(Color.parseColor("#000000"));
        editTextPresentPosition.setEnabled(false);
        editTextPresentPosition.setTextColor(Color.parseColor("#000000"));
        editTextPEHeart.setEnabled(false);
        editTextPEHeart.setTextColor(Color.parseColor("#000000"));
        editTextPEMotion.setEnabled(false);
        editTextPEMotion.setTextColor(Color.parseColor("#000000"));
        editTextDueDate.setEnabled(false);
        editTextDueDate.setTextColor(Color.parseColor("#000000"));
        editTextNextAppTime.setEnabled(false);
        editTextNextAppTime.setTextColor(Color.parseColor("#000000"));
        btnAddProblem.setEnabled(false);
    }

    private void EnableField()
    {
        bodyWeightEditText.setEnabled(true);
        bloodPressureEditText.setEnabled(true);
        pulseEditText.setEnabled(true);
        editTextBookingWeight.setEnabled(true);
        editTextBookingBMI.setEnabled(true);
        editTextBookingBP.setEnabled(true);
        editTextLKKR.setEnabled(true);
        editTextUrineAlb.setEnabled(true);
        editTextUrineSugar.setEnabled(true);
        editTextHB.setEnabled(true);
        editTextEdema.setEnabled(true);
        editTextPregnancyPeriod.setEnabled(true);
        editTextUterineHeight.setEnabled(true);
        editTextPresentPosition.setEnabled(true);
        editTextPEHeart.setEnabled(true);
        editTextPEMotion.setEnabled(true);
        editTextDueDate.setEnabled(true);
        editTextNextAppTime.setEnabled(true);
        btnAddProblem.setEnabled(true);
    }

    private boolean SaveCheckDialogRequired()
    {
        String problem = editTextProblem.getText().toString();
        String treatment = editTextTreatment.getText().toString();
        if(problem.isEmpty() || treatment.isEmpty())
        {
            if(problem.isEmpty())
            {
                layoutPEProblem.setErrorEnabled(true);
                layoutPEProblem.setError("This field is required!");
            }else{
                layoutPEProblem.setErrorEnabled(false);
                layoutPEProblem.setError(null);
            }

            if(treatment.isEmpty())
            {
                layoutPETreatment.setErrorEnabled(true);
                layoutPETreatment.setError("This field is required!");
            }else{
                layoutPETreatment.setErrorEnabled(false);
                layoutPETreatment.setError(null);
            }
            return true;
        }else{
            return false;
        }
    }

    private boolean SaveCheckRequiredField()
    {
        if(bodyWeightEditText.getText().toString().isEmpty() || bloodPressureEditText.getText().toString().isEmpty() || pulseEditText.getText().toString().isEmpty()
                || editTextBookingWeight.getText().toString().isEmpty() || editTextBookingBMI.getText().toString().isEmpty() || editTextBookingBP.getText().toString().isEmpty()
                || editTextLKKR.getText().toString().isEmpty() || editTextUrineAlb.getText().toString().isEmpty() || editTextUrineSugar.getText().toString().isEmpty()
                || editTextHB.getText().toString().isEmpty() || editTextPregnancyPeriod.getText().toString().isEmpty() || editTextDueDate.getText().toString().isEmpty()
                || editTextNextAppTime.getText().toString().isEmpty())
        {
            if(bodyWeightEditText.getText().toString().equals(""))
            {
                bodyWeightInputLayout.setErrorEnabled(true);
                bodyWeightInputLayout.setError("This field is required!");
            } else{
                bodyWeightInputLayout.setErrorEnabled(false);
                bodyWeightInputLayout.setError(null);
            }

            if(bloodPressureEditText.getText().toString().equals(""))
            {
                bloodPressureInputLayout.setErrorEnabled(true);
                bloodPressureInputLayout.setError("This field is required!");
            }else{
                bloodPressureInputLayout.setErrorEnabled(false);
                bloodPressureInputLayout.setError(null);
            }

            if(pulseEditText.getText().toString().equals(""))
            {
                pulseInputLayout.setErrorEnabled(true);
                pulseInputLayout.setError("This field is required!");
            }else{
                pulseInputLayout.setErrorEnabled(false);
                pulseInputLayout.setError(null);
            }

            if(editTextBookingWeight.getText().toString().isEmpty())
            {
                layoutBookingWeight.setErrorEnabled(true);
                layoutBookingWeight.setError("This field is required!");
            }else{
                layoutBookingWeight.setErrorEnabled(false);
                layoutBookingWeight.setError(null);
            }

            if(editTextBookingBMI.getText().toString().isEmpty())
            {
                layoutBookingBMI.setErrorEnabled(true);
                layoutBookingBMI.setError("This field is required!");
            }else{
                layoutBookingBMI.setErrorEnabled(false);
                layoutBookingBMI.setError(null);
            }

            if(editTextBookingBP.getText().toString().isEmpty())
            {
                layoutBookingBP.setErrorEnabled(true);
                layoutBookingBP.setError("This field is required!");
            }else{
                layoutBookingBP.setErrorEnabled(false);
                layoutBookingBP.setError(null);
            }

            if(editTextLKKR.getText().toString().isEmpty())
            {
                layoutLKKR.setErrorEnabled(true);
                layoutLKKR.setError("This field is required!");
            }else{
                layoutLKKR.setErrorEnabled(false);
                layoutLKKR.setError(null);
            }

            if(editTextUrineAlb.getText().toString().isEmpty())
            {
                layoutUrineAlb.setErrorEnabled(true);
                layoutUrineAlb.setError("This field is required!");
            }else{
                layoutUrineAlb.setErrorEnabled(false);
                layoutUrineAlb.setError(null);
            }

            if(editTextUrineSugar.getText().toString().isEmpty())
            {
                layoutUrineSugar.setErrorEnabled(true);
                layoutUrineSugar.setError("This field is required!");
            }else{
                layoutUrineSugar.setErrorEnabled(false);
                layoutUrineSugar.setError(null);
            }

            if(editTextHB.getText().toString().isEmpty())
            {
                layoutHB.setErrorEnabled(true);
                layoutHB.setError("This field is required!");
            }else{
                layoutHB.setErrorEnabled(false);
                layoutHB.setError(null);
            }

            if(editTextPregnancyPeriod.getText().toString().isEmpty())
            {
                layoutPregnancyPeriod.setErrorEnabled(true);
                layoutPregnancyPeriod.setError("This field is required!");
            }else{
                layoutPregnancyPeriod.setErrorEnabled(false);
                layoutPregnancyPeriod.setError(null);
            }

            if(editTextDueDate.getText().toString().isEmpty())
            {
                txtInputLayoutDueDate.setErrorEnabled(true);
                txtInputLayoutDueDate.setError("This field is required!");
            }else{
                txtInputLayoutDueDate.setErrorEnabled(false);
                txtInputLayoutDueDate.setError(null);
            }

            if(editTextNextAppTime.getText().toString().isEmpty())
            {
                layoutNextAppTime.setErrorEnabled(true);
                layoutNextAppTime.setError("This field is required!");
            }else{
                layoutNextAppTime.setErrorEnabled(false);
                layoutNextAppTime.setError(null);
            }
            return true;
        }else{
            return false;
        }
    }

    private void CheckRequiredDialog()
    {
        editTextProblem.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(editTextProblem.getText().toString().isEmpty())
                {
                    layoutPEProblem.setErrorEnabled(true);
                    layoutPEProblem.setError("This field is required1");
                }else{
                    layoutPEProblem.setErrorEnabled(false);
                    layoutPEProblem.setError(null);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        editTextTreatment.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(editTextTreatment.getText().toString().isEmpty())
                {
                    layoutPETreatment.setErrorEnabled(true);
                    layoutPETreatment.setError("This field is required1");
                }else{
                    layoutPETreatment.setErrorEnabled(false);
                    layoutPETreatment.setError(null);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void CheckRequiredTextChange()
    {
        bodyWeightEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(bodyWeightEditText.getText().toString().equals(""))
                {
                    bodyWeightInputLayout.setErrorEnabled(true);
                    bodyWeightInputLayout.setError("This field is required!");
                } else{
                    bodyWeightInputLayout.setErrorEnabled(false);
                    bodyWeightInputLayout.setError(null);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        bloodPressureEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(bloodPressureEditText.getText().toString().equals(""))
                {
                    bloodPressureInputLayout.setErrorEnabled(true);
                    bloodPressureInputLayout.setError("This field is required!");
                }else{
                    bloodPressureInputLayout.setErrorEnabled(false);
                    bloodPressureInputLayout.setError(null);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        pulseEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(pulseEditText.getText().toString().equals(""))
                {
                    pulseInputLayout.setErrorEnabled(true);
                    pulseInputLayout.setError("This field is required!");
                }else{
                    pulseInputLayout.setErrorEnabled(false);
                    pulseInputLayout.setError(null);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        editTextBookingWeight.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(editTextBookingWeight.getText().toString().isEmpty())
                {
                    layoutBookingWeight.setErrorEnabled(true);
                    layoutBookingWeight.setError("This field is required!");
                }else{
                    layoutBookingWeight.setErrorEnabled(false);
                    layoutBookingWeight.setError(null);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        editTextBookingBMI.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(editTextBookingBMI.getText().toString().isEmpty())
                {
                    layoutBookingBMI.setErrorEnabled(true);
                    layoutBookingBMI.setError("This field is required!");
                }else{
                    layoutBookingBMI.setErrorEnabled(false);
                    layoutBookingBMI.setError(null);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        editTextBookingBP.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(editTextBookingBP.getText().toString().isEmpty())
                {
                    layoutBookingBP.setErrorEnabled(true);
                    layoutBookingBP.setError("This field is required!");
                }else{
                    layoutBookingBP.setErrorEnabled(false);
                    layoutBookingBP.setError(null);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        editTextLKKR.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(editTextLKKR.getText().toString().isEmpty())
                {
                    layoutLKKR.setErrorEnabled(true);
                    layoutLKKR.setError("This field is required!");
                }else{
                    layoutLKKR.setErrorEnabled(false);
                    layoutLKKR.setError(null);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        editTextUrineAlb.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(editTextUrineAlb.getText().toString().isEmpty())
                {
                    layoutUrineAlb.setErrorEnabled(true);
                    layoutUrineAlb.setError("This field is required!");
                }else{
                    layoutUrineAlb.setErrorEnabled(false);
                    layoutUrineAlb.setError(null);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        editTextUrineSugar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(editTextUrineSugar.getText().toString().isEmpty())
                {
                    layoutUrineSugar.setErrorEnabled(true);
                    layoutUrineSugar.setError("This field is required!");
                }else{
                    layoutUrineSugar.setErrorEnabled(false);
                    layoutUrineSugar.setError(null);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        editTextHB.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(editTextHB.getText().toString().isEmpty())
                {
                    layoutHB.setErrorEnabled(true);
                    layoutHB.setError("This field is required!");
                }else{
                    layoutHB.setErrorEnabled(false);
                    layoutHB.setError(null);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        editTextPregnancyPeriod.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(editTextPregnancyPeriod.getText().toString().isEmpty())
                {
                    layoutPregnancyPeriod.setErrorEnabled(true);
                    layoutPregnancyPeriod.setError("This field is required!");
                }else{
                    layoutPregnancyPeriod.setErrorEnabled(false);
                    layoutPregnancyPeriod.setError(null);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        editTextDueDate.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(editTextDueDate.getText().toString().isEmpty())
                {
                    txtInputLayoutDueDate.setErrorEnabled(true);
                    txtInputLayoutDueDate.setError("This field is required!");
                }else{
                    txtInputLayoutDueDate.setErrorEnabled(false);
                    txtInputLayoutDueDate.setError(null);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        editTextNextAppTime.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(editTextNextAppTime.getText().toString().isEmpty())
                {
                    layoutNextAppTime.setErrorEnabled(true);
                    layoutNextAppTime.setError("This field is required!");
                }else{
                    layoutNextAppTime.setErrorEnabled(false);
                    layoutNextAppTime.setError(null);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void CancelAlarm()
    {
        AlarmManager alarmManager = (AlarmManager)PregnancyExaminationActivity.this.getSystemService(Context.ALARM_SERVICE );
        Intent intent = new Intent(PregnancyExaminationActivity.this, NotifyService.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(PregnancyExaminationActivity.this, 9000, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        alarmManager.cancel(pendingIntent);
        pendingIntent.cancel();

        Intent intentReminder = new Intent(PregnancyExaminationActivity.this, ReminderService.class);
        PendingIntent pendingIntentReminder = PendingIntent.getBroadcast(PregnancyExaminationActivity.this, 101, intentReminder, PendingIntent.FLAG_UPDATE_CURRENT);
        alarmManager.cancel(pendingIntentReminder);
        pendingIntentReminder.cancel();

        Intent intentAlert = new Intent(PregnancyExaminationActivity.this, AlertService.class);
        PendingIntent pendingIntentAlert = PendingIntent.getBroadcast(PregnancyExaminationActivity.this, 1000, intentAlert, 0);
        alarmManager.cancel(pendingIntentAlert);
        pendingIntentAlert.cancel();

        Log.i("TestingAlarmCancel", "Alarm Cancel");
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.action_logout:
                AlertDialog.Builder builder = new AlertDialog.Builder(PregnancyExaminationActivity.this);
                builder.setTitle("Logout");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if(SaveSharedPreference.getUser(PregnancyExaminationActivity.this).equals("Mommy"))
                        {
                            CancelAlarm();
                        }
                        SaveSharedPreference.clearUser(PregnancyExaminationActivity.this);
                        Intent intent = new Intent(PregnancyExaminationActivity.this, MainActivity.class);
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

    public class ProblemAdapter extends ArrayAdapter<ProblemPE>{
        private final List<ProblemPE> list;
        private Activity context;

        public ProblemAdapter(Activity context, int resource, List<ProblemPE> list)
        {
            super(context, resource, list);
            this.list = list;
            this.context = context;
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = inflater.inflate(R.layout.problem_listview, parent, false);

            TextView textProblemDate = (TextView)view.findViewById(R.id.textProblemDate);
            TextView textProblemPersonnel = (TextView)view.findViewById(R.id.textProblemPersonnel);

            ProblemPE problemPE = getItem(position);
            textProblemDate.setText(problemPE.getDate());
            textProblemPersonnel.setText(problemPE.getPersonnel());

            return view;
        }
    }
}


