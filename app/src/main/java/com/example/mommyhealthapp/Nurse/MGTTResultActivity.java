package com.example.mommyhealthapp.Nurse;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.LinearLayoutCompat;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.mommyhealthapp.AlertService;
import com.example.mommyhealthapp.Class.MGTTResult;
import com.example.mommyhealthapp.MainActivity;
import com.example.mommyhealthapp.NotifyService;
import com.example.mommyhealthapp.R;
import com.example.mommyhealthapp.ReminderService;
import com.example.mommyhealthapp.SaveSharedPreference;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.w3c.dom.Text;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class MGTTResultActivity extends AppCompatActivity {
    private EditText editTextFirstTrimesterDate, editTextFirstPOA, editTextFirstBloodSugar, editTextFirstPostprandial, editTextSecTrimesterDate, editTextSecPOA, editTextSecBloodSugar,
            editTextSecPostprandial, editTextThirdTrimesterDate, editTextThirdPOA, editTextThirdBloodSugar, editTextThirdPostprandial;
    private TextInputLayout layourFirstTrimesterDate, layoutSecTrimesterDate, layoutThirdTrimesterDate;
    private Button btnMGTTResultSave, btnMGTTResultCancel;
    private RelativeLayout relativeLayoutMGTTResult;
    private LinearLayoutCompat layoutMGTTResult;
    private ProgressBar progressBarMGTTResult;
    DatePickerDialog datePickerDialog;

    private FirebaseFirestore mFirebaseFirestore;
    private CollectionReference mCollectionReference;

    private String healthInfoId, MGTTKey, key;
    private int check = 0;
    private Boolean isEmpty;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mgttresult);

        editTextFirstTrimesterDate = (EditText)findViewById(R.id.editTextFirstTrimesterDate);
        editTextFirstPOA = (EditText)findViewById(R.id.editTextFirstPOA);
        editTextFirstBloodSugar = (EditText)findViewById(R.id.editTextFirstBloodSugar);
        editTextFirstPostprandial = (EditText)findViewById(R.id.editTextFirstPostprandial);
        editTextSecTrimesterDate = (EditText)findViewById(R.id.editTextSecTrimesterDate);
        editTextSecPOA = (EditText)findViewById(R.id.editTextSecPOA);
        editTextSecBloodSugar = (EditText)findViewById(R.id.editTextSecBloodSugar);
        editTextSecPostprandial = (EditText)findViewById(R.id.editTextSecPostprandial);
        editTextThirdTrimesterDate = (EditText)findViewById(R.id.editTextThirdTrimesterDate);
        editTextThirdPOA = (EditText)findViewById(R.id.editTextThirdPOA);
        editTextThirdBloodSugar = (EditText)findViewById(R.id.editTextThirdBloodSugar);
        editTextThirdPostprandial = (EditText)findViewById(R.id.editTextThirdPostprandial);

        layourFirstTrimesterDate = (TextInputLayout)findViewById(R.id.layourFirstTrimesterDate);
        layoutSecTrimesterDate = (TextInputLayout)findViewById(R.id.layoutSecTrimesterDate);
        layoutThirdTrimesterDate = (TextInputLayout)findViewById(R.id.layoutThirdTrimesterDate);

        btnMGTTResultSave = (Button)findViewById(R.id.btnMGTTResultSave);
        btnMGTTResultCancel = (Button)findViewById(R.id.btnMGTTResultCancel);

        relativeLayoutMGTTResult = (RelativeLayout)findViewById(R.id.relativeLayoutMGTTResult);
        layoutMGTTResult = (LinearLayoutCompat)findViewById(R.id.layoutMGTTResult);
        progressBarMGTTResult = (ProgressBar)findViewById(R.id.progressBarMGTTResult);

        editTextFirstTrimesterDate.setClickable(false);
        editTextFirstTrimesterDate.setFocusable(false);
        editTextSecTrimesterDate.setClickable(false);
        editTextSecTrimesterDate.setFocusable(false);
        editTextThirdTrimesterDate.setClickable(false);
        editTextThirdTrimesterDate.setFocusable(false);
        btnMGTTResultCancel.setVisibility(View.GONE);
        progressBarMGTTResult.setVisibility(View.VISIBLE);
        layoutMGTTResult.setVisibility(View.GONE);

        GetDateText();

        Intent intent = getIntent();
        healthInfoId = intent.getStringExtra("healthInfoId");
        MGTTKey = intent.getStringExtra("MGTTKey");
        mFirebaseFirestore = FirebaseFirestore.getInstance();
        mCollectionReference = mFirebaseFirestore.collection("MommyHealthInfo").document(healthInfoId).collection("MGTT").document(MGTTKey).collection("MGTTResult");
        if(SaveSharedPreference.getUser(MGTTResultActivity.this).equals("Mommy")){
            MommyLogIn();
        }
        else {
            mCollectionReference.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                @Override
                public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                    if(queryDocumentSnapshots.isEmpty())
                    {
                        isEmpty = true;
                        progressBarMGTTResult.setVisibility(View.GONE);
                        layoutMGTTResult.setVisibility(View.VISIBLE);
                    }else{
                        isEmpty = false;
                        DisableField();
                        for(QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots)
                        {
                            key = documentSnapshot.getId();
                            MGTTResult result = documentSnapshot.toObject(MGTTResult.class);
                            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                            if(result.getFirstTrimesterDate() == null)
                            {
                                editTextFirstTrimesterDate.setText("");
                            }else{
                                editTextFirstTrimesterDate.setText(dateFormat.format(result.getFirstTrimesterDate()));
                            }

                            if(result.getSecTrimesterDate() == null)
                            {
                                editTextSecTrimesterDate.setText("");
                            }else{
                                editTextSecTrimesterDate.setText(dateFormat.format(result.getSecTrimesterDate()));
                            }

                            if(result.getThirdTrimesterDate() == null)
                            {
                                editTextThirdTrimesterDate.setText("");
                            }else{
                                editTextThirdTrimesterDate.setText(dateFormat.format(result.getThirdTrimesterDate()));
                            }
                            editTextFirstPOA.setText(result.getFirstPOA());
                            editTextFirstBloodSugar.setText(result.getFirstBloodSugar());
                            editTextFirstPostprandial.setText(result.getFirstPostprandial());

                            editTextSecPOA.setText(result.getSecPOA());
                            editTextSecBloodSugar.setText(result.getSecBloodSugar());
                            editTextSecPostprandial.setText(result.getSecPostprandial());

                            editTextThirdPOA.setText(result.getThirdPOA());
                            editTextThirdBloodSugar.setText(result.getThirdBloodSugar());
                            editTextThirdPostprandial.setText(result.getThirdPostprandial());
                        }
                        progressBarMGTTResult.setVisibility(View.GONE);
                        layoutMGTTResult.setVisibility(View.VISIBLE);
                        btnMGTTResultSave.setText("Update");
                    }
                }
            });
        }

        btnMGTTResultSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isEmpty == true)
                {
                    String firstPOA = editTextFirstPOA.getText().toString(),
                            firstBloodSugar = editTextFirstBloodSugar.getText().toString(),
                            firstPostprandial = editTextFirstPostprandial.getText().toString(),
                            SecPOA = editTextSecPOA.getText().toString(),
                            SecBloodSugar = editTextSecBloodSugar.getText().toString(),
                            SecPostprandial = editTextSecPostprandial.getText().toString(),
                            thirdPOA = editTextThirdPOA.getText().toString(),
                            thirdBloodSugar = editTextThirdBloodSugar.getText().toString(),
                            thirdPostprandial = editTextThirdPostprandial.getText().toString();
                    Date firstTrimesterDate = null, SecTrimesterDate = null, thirdTrimesterDate = null;
                    String medicalPersonnelId = SaveSharedPreference.getID(MGTTResultActivity.this);
                    Date createdDate = new Date();
                    try {
                        firstTrimesterDate = new SimpleDateFormat("dd/MM/yyyy").parse(editTextFirstTrimesterDate.getText().toString());
                        SecTrimesterDate = new SimpleDateFormat("dd/MM/yyyy").parse(editTextSecTrimesterDate.getText().toString());
                        thirdTrimesterDate = new SimpleDateFormat("dd/MM/yyyy").parse(editTextThirdTrimesterDate.getText().toString());
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    if(CheckAllEmpty())
                    {
                        AlertDialog.Builder builder = new AlertDialog.Builder(MGTTResultActivity.this);
                        builder.setTitle("Error");
                        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                return;
                            }
                        });
                        builder.setMessage("At least one trimester must written");
                        AlertDialog alert = builder.create();
                        alert.show();
                    }else{
                        MGTTResult result = new MGTTResult(firstTrimesterDate, firstPOA, firstBloodSugar, firstPostprandial, SecTrimesterDate, SecPOA, SecBloodSugar,
                                SecPostprandial, thirdTrimesterDate, thirdPOA, thirdBloodSugar, thirdPostprandial, medicalPersonnelId, createdDate);

                        mCollectionReference.add(result).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {
                                AlertDialog.Builder builder = new AlertDialog.Builder(MGTTResultActivity.this);
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
                    }
                }else{
                    check++;
                    if(check == 1)
                    {
                        EnableField();
                        btnMGTTResultCancel.setVisibility(View.VISIBLE);
                    }else{
                        if(CheckAllEmpty())
                        {
                            check = 1;
                            AlertDialog.Builder builder = new AlertDialog.Builder(MGTTResultActivity.this);
                            builder.setTitle("Error");
                            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    return;
                                }
                            });
                            builder.setMessage("At least one trimester must written");
                            AlertDialog alert = builder.create();
                            alert.show();
                        }else{
                            DocumentReference mDocumentReference = mFirebaseFirestore.collection("MommyHealthInfo").document(healthInfoId).collection("MGTT").document(MGTTKey).collection("MGTTResult").document(key);
                            String firstPOA = editTextFirstPOA.getText().toString(),
                                    firstBloodSugar = editTextFirstBloodSugar.getText().toString(),
                                    firstPostprandial = editTextFirstPostprandial.getText().toString(),
                                    SecPOA = editTextSecPOA.getText().toString(),
                                    SecBloodSugar = editTextSecBloodSugar.getText().toString(),
                                    SecPostprandial = editTextSecPostprandial.getText().toString(),
                                    thirdPOA = editTextThirdPOA.getText().toString(),
                                    thirdBloodSugar = editTextThirdBloodSugar.getText().toString(),
                                    thirdPostprandial = editTextThirdPostprandial.getText().toString();
                            Date firstTrimesterDate = null, SecTrimesterDate = null, thirdTrimesterDate = null;
                            String medicalPersonnelId = SaveSharedPreference.getID(MGTTResultActivity.this);
                            Date createdDate = new Date();
                            try {
                                firstTrimesterDate = new SimpleDateFormat("dd/MM/yyyy").parse(editTextFirstTrimesterDate.getText().toString());
                                SecTrimesterDate = new SimpleDateFormat("dd/MM/yyyy").parse(editTextSecTrimesterDate.getText().toString());
                                thirdTrimesterDate = new SimpleDateFormat("dd/MM/yyyy").parse(editTextThirdTrimesterDate.getText().toString());
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }

                            mDocumentReference.update("firstTrimesterDate", firstTrimesterDate);
                            mDocumentReference.update("firstPOA", firstPOA);
                            mDocumentReference.update("firstBloodSugar", firstBloodSugar);
                            mDocumentReference.update("firstPostprandial", firstPostprandial);
                            mDocumentReference.update("SecTrimesterDate", SecTrimesterDate);
                            mDocumentReference.update("SecPOA", SecPOA);
                            mDocumentReference.update("SecBloodSugar", SecBloodSugar);
                            mDocumentReference.update("SecPostprandial", SecPostprandial);
                            mDocumentReference.update("thirdTrimesterDate", thirdTrimesterDate);
                            mDocumentReference.update("thirdPOA", thirdPOA);
                            mDocumentReference.update("thirdBloodSugar", thirdBloodSugar);
                            mDocumentReference.update("thirdPostprandial", thirdPostprandial);
                            mDocumentReference.update("medicalPersonnelId", medicalPersonnelId);
                            mDocumentReference.update("createdDate", createdDate);
                            Snackbar snackbar = Snackbar.make(relativeLayoutMGTTResult, "Updated Successfully!", Snackbar.LENGTH_LONG);
                            snackbar.show();
                            DisableField();
                            btnMGTTResultCancel.setVisibility(View.GONE);
                            check = 0;
                        }
                    }
                }

            }
        });

        btnMGTTResultCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DisableField();
                btnMGTTResultCancel.setVisibility(View.GONE);
                check = 0;
            }
        });

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void MommyLogIn()
    {
        mCollectionReference.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                if(queryDocumentSnapshots.isEmpty())
                {
                    isEmpty = true;
                    progressBarMGTTResult.setVisibility(View.GONE);
                    layoutMGTTResult.setVisibility(View.VISIBLE);
                    btnMGTTResultSave.setVisibility(View.GONE);
                    btnMGTTResultCancel.setVisibility(View.GONE);
                    DisableField();
                }else{
                    isEmpty = false;
                    DisableField();
                    for(QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots)
                    {
                        key = documentSnapshot.getId();
                        MGTTResult result = documentSnapshot.toObject(MGTTResult.class);
                        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                        if(result.getFirstTrimesterDate() == null)
                        {
                            editTextFirstTrimesterDate.setText("");
                        }else{
                            editTextFirstTrimesterDate.setText(dateFormat.format(result.getFirstTrimesterDate()));
                        }

                        if(result.getSecTrimesterDate() == null)
                        {
                            editTextSecTrimesterDate.setText("");
                        }else{
                            editTextSecTrimesterDate.setText(dateFormat.format(result.getSecTrimesterDate()));
                        }

                        if(result.getThirdTrimesterDate() == null)
                        {
                            editTextThirdTrimesterDate.setText("");
                        }else{
                            editTextThirdTrimesterDate.setText(dateFormat.format(result.getThirdTrimesterDate()));
                        }
                        editTextFirstPOA.setText(result.getFirstPOA());
                        editTextFirstBloodSugar.setText(result.getFirstBloodSugar());
                        editTextFirstPostprandial.setText(result.getFirstPostprandial());

                        editTextSecPOA.setText(result.getSecPOA());
                        editTextSecBloodSugar.setText(result.getSecBloodSugar());
                        editTextSecPostprandial.setText(result.getSecPostprandial());

                        editTextThirdPOA.setText(result.getThirdPOA());
                        editTextThirdBloodSugar.setText(result.getThirdBloodSugar());
                        editTextThirdPostprandial.setText(result.getThirdPostprandial());
                    }
                    progressBarMGTTResult.setVisibility(View.GONE);
                    layoutMGTTResult.setVisibility(View.VISIBLE);
                    btnMGTTResultSave.setVisibility(View.GONE);
                    btnMGTTResultCancel.setVisibility(View.GONE);
                }
            }
        });
    }

    private boolean CheckAllEmpty()
    {
        String firstPOA = editTextFirstPOA.getText().toString(),
                firstBloodSugar = editTextFirstBloodSugar.getText().toString(),
                firstPostprandial = editTextFirstPostprandial.getText().toString(),
                SecPOA = editTextSecPOA.getText().toString(),
                SecBloodSugar = editTextSecBloodSugar.getText().toString(),
                SecPostprandial = editTextSecPostprandial.getText().toString(),
                thirdPOA = editTextThirdPOA.getText().toString(),
                thirdBloodSugar = editTextThirdBloodSugar.getText().toString(),
                thirdPostprandial = editTextThirdPostprandial.getText().toString(),
                firstTrimesterDate = editTextFirstTrimesterDate.getText().toString(),
                SecTrimesterDate = editTextSecTrimesterDate.getText().toString(),
                thirdTrimesterDate = editTextThirdTrimesterDate.getText().toString();

        if(firstPOA.isEmpty() && firstBloodSugar.isEmpty() && firstPostprandial.isEmpty() && SecPOA.isEmpty() && SecBloodSugar.isEmpty()
                && SecPostprandial.isEmpty() && thirdPOA.isEmpty() && thirdBloodSugar.isEmpty() && thirdPostprandial.isEmpty()
                && firstTrimesterDate.isEmpty() && SecTrimesterDate.isEmpty() && thirdTrimesterDate.isEmpty())
        {
            return true;
        }else{
            return false;
        }
    }

    private void GetDateText()
    {
        editTextFirstTrimesterDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR); // current year
                int mMonth = c.get(Calendar.MONTH); // current month
                int mDay = c.get(Calendar.DAY_OF_MONTH); // current day
                datePickerDialog = new DatePickerDialog(MGTTResultActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                editTextFirstTrimesterDate.setText(dayOfMonth + "/" +(month + 1) + "/" + year);
                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });
        editTextSecTrimesterDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR); // current year
                int mMonth = c.get(Calendar.MONTH); // current month
                int mDay = c.get(Calendar.DAY_OF_MONTH); // current day
                datePickerDialog = new DatePickerDialog(MGTTResultActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                editTextSecTrimesterDate.setText(dayOfMonth + "/" +(month + 1) + "/" + year);
                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });
        editTextThirdTrimesterDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR); // current year
                int mMonth = c.get(Calendar.MONTH); // current month
                int mDay = c.get(Calendar.DAY_OF_MONTH); // current day
                datePickerDialog = new DatePickerDialog(MGTTResultActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                editTextThirdTrimesterDate.setText(dayOfMonth + "/" +(month + 1) + "/" + year);
                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });

    }

    private void DisableField()
    {
        editTextFirstTrimesterDate.setEnabled(false);
        editTextFirstTrimesterDate.setTextColor(Color.parseColor("#000000"));
        editTextFirstPOA.setEnabled(false);
        editTextFirstPOA.setTextColor(Color.parseColor("#000000"));
        editTextFirstBloodSugar.setEnabled(false);
        editTextFirstBloodSugar.setTextColor(Color.parseColor("#000000"));
        editTextFirstPostprandial.setEnabled(false);
        editTextFirstPostprandial.setTextColor(Color.parseColor("#000000"));
        editTextSecTrimesterDate.setEnabled(false);
        editTextSecTrimesterDate.setTextColor(Color.parseColor("#000000"));
        editTextSecPOA.setEnabled(false);
        editTextSecPOA.setTextColor(Color.parseColor("#000000"));
        editTextSecBloodSugar.setEnabled(false);
        editTextSecBloodSugar.setTextColor(Color.parseColor("#000000"));
        editTextSecPostprandial.setEnabled(false);
        editTextSecPostprandial.setTextColor(Color.parseColor("#000000"));
        editTextThirdTrimesterDate.setEnabled(false);
        editTextThirdTrimesterDate.setTextColor(Color.parseColor("#000000"));
        editTextThirdPOA.setEnabled(false);
        editTextThirdPOA.setTextColor(Color.parseColor("#000000"));
        editTextThirdBloodSugar.setEnabled(false);
        editTextThirdBloodSugar.setTextColor(Color.parseColor("#000000"));
        editTextThirdPostprandial.setEnabled(false);
        editTextThirdPostprandial.setTextColor(Color.parseColor("#000000"));

        //for(int i=0; i<layoutMGTTResult.getChildCount(); i++)
        //{
        //    View view = layoutMGTTResult.getChildAt(i);
        //    if(view instanceof  TextInputLayout)
        //    {
        //        for(int n=0; n<((TextInputLayout)view).getChildCount(); n++)
        //        {
        //            View v = ((TextInputLayout)view).getChildAt(n);
        //            Log.i("Testing1", ((TextInputLayout)view).getChildAt(n).toString());
        //            if(v instanceof EditText)
        //            {
        //                ((EditText)v).setEnabled(false);
        //            }
        //        }
        //    }
        //}
    }

    private  void EnableField()
    {
        editTextFirstTrimesterDate.setEnabled(true);
        editTextFirstPOA.setEnabled(true);
        editTextFirstBloodSugar.setEnabled(true);
        editTextFirstPostprandial.setEnabled(true);
        editTextSecTrimesterDate.setEnabled(true);
        editTextSecPOA.setEnabled(true);
        editTextSecBloodSugar.setEnabled(true);
        editTextSecPostprandial.setEnabled(true);
        editTextThirdTrimesterDate.setEnabled(true);
        editTextThirdPOA.setEnabled(true);
        editTextThirdBloodSugar.setEnabled(true);
        editTextThirdPostprandial.setEnabled(true);
    }

    private void CancelAlarm()
    {
        AlarmManager alarmManager = (AlarmManager)MGTTResultActivity.this.getSystemService(Context.ALARM_SERVICE );
        Intent intent = new Intent(MGTTResultActivity.this, NotifyService.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(MGTTResultActivity.this, 9000, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        alarmManager.cancel(pendingIntent);
        pendingIntent.cancel();

        Intent intentReminder = new Intent(MGTTResultActivity.this, ReminderService.class);
        PendingIntent pendingIntentReminder = PendingIntent.getBroadcast(MGTTResultActivity.this, 101, intentReminder, PendingIntent.FLAG_UPDATE_CURRENT);
        alarmManager.cancel(pendingIntentReminder);
        pendingIntentReminder.cancel();

        Intent intentAlert = new Intent(MGTTResultActivity.this, AlertService.class);
        PendingIntent pendingIntentAlert = PendingIntent.getBroadcast(MGTTResultActivity.this, 1000, intentAlert, 0);
        alarmManager.cancel(pendingIntentAlert);
        pendingIntentAlert.cancel();

        Log.i("TestingAlarmCancel", "Alarm Cancel");
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.action_logout:
                AlertDialog.Builder builder = new AlertDialog.Builder(MGTTResultActivity.this);
                builder.setTitle("Logout");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if(SaveSharedPreference.getUser(MGTTResultActivity.this).equals("Mommy"))
                        {
                            CancelAlarm();
                        }
                        SaveSharedPreference.clearUser(MGTTResultActivity.this);
                        Intent intent = new Intent(MGTTResultActivity.this, MainActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
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
