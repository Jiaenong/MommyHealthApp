package com.example.mommyhealthapp.Mommy;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.cardview.widget.CardView;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.mommyhealthapp.AlertService;
import com.example.mommyhealthapp.Class.KickCount;
import com.example.mommyhealthapp.Class.Mommy;
import com.example.mommyhealthapp.MainActivity;
import com.example.mommyhealthapp.NotifyService;
import com.example.mommyhealthapp.R;
import com.example.mommyhealthapp.ReminderService;
import com.example.mommyhealthapp.SaveSharedPreference;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class KickCounterActivity extends AppCompatActivity {
    private CardView kickCount;
    private TextView firstRecord, kickTime, lastRecord, lastKicksTime, txtViewKickTimes, txtViewFirstKickDate, txtViewLastKickDate;
    private LinearLayoutCompat layoutKickCount;
    private ProgressBar progressBarKickCount;
    private int counter = 0, counterKick, babyKickNumber;
    private String healthInfoId;
    private Boolean isEmpty;

    private FirebaseFirestore mFirebaseFirestore;
    private CollectionReference mCollectionReference, nCollectionReference, pCollectionReference;
    private DocumentReference mDocumentReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kick_counter);
        kickCount = (CardView)findViewById(R.id.kickCount);
        txtViewKickTimes = (TextView)findViewById(R.id.txtViewKickTimes);
        firstRecord = (TextView)findViewById(R.id.firstRecord);
        kickTime = (TextView)findViewById(R.id.kickTime);
        lastRecord = (TextView)findViewById(R.id.lastRecord);
        lastKicksTime = (TextView)findViewById(R.id.lastKicksTime);
        txtViewFirstKickDate = (TextView)findViewById(R.id.txtViewFirstKickDate);
        txtViewLastKickDate = (TextView)findViewById(R.id.txtViewLastKickDate);
        layoutKickCount = (LinearLayoutCompat)findViewById(R.id.layoutKickCount);
        progressBarKickCount = (ProgressBar)findViewById(R.id.progressBarKickCount);

        progressBarKickCount.setVisibility(View.VISIBLE);
        layoutKickCount.setVisibility(View.GONE);

        mFirebaseFirestore = FirebaseFirestore.getInstance();
        mDocumentReference = mFirebaseFirestore.collection("Mommy").document(SaveSharedPreference.getID(KickCounterActivity.this));
        Log.i("Testing", SaveSharedPreference.getID(KickCounterActivity.this));
        mDocumentReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                Mommy mommy = documentSnapshot.toObject(Mommy.class);
                healthInfoId = mommy.getHealthInfoId();
                Log.i("Testing1", healthInfoId);
                mCollectionReference = mFirebaseFirestore.collection("MommyHealthInfo");
                mCollectionReference.whereEqualTo("healthInfoId", healthInfoId).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        String id = "";
                        for(QueryDocumentSnapshot documentSnapshots: queryDocumentSnapshots)
                        {
                            id = documentSnapshots.getId();
                        }
                        pCollectionReference = mFirebaseFirestore.collection("MommyHealthInfo").document(id).collection("KickCount");
                        pCollectionReference.orderBy("babyKickNumber", Query.Direction.DESCENDING).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                            @Override
                            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                                if(queryDocumentSnapshots.isEmpty())
                                {
                                    isEmpty = true;
                                    babyKickNumber = 0;
                                    progressBarKickCount.setVisibility(View.GONE);
                                    layoutKickCount.setVisibility(View.VISIBLE);
                                }else{
                                    isEmpty = false;
                                    for(QueryDocumentSnapshot documentSnapshotss : queryDocumentSnapshots)
                                    {
                                        KickCount kc = documentSnapshotss.toObject(KickCount.class);
                                        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                                        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
                                        babyKickNumber = kc.getBabyKickNumber();
                                        kickTime.setText(timeFormat.format(kc.getFirstKickTime()));
                                        txtViewFirstKickDate.setText(dateFormat.format(kc.getFirstKickDate()));
                                        if(kc.getLastKick() == 0 || kc.getLastKickDate() == null || kc.getLastKickTime() == null)
                                        {
                                            txtViewKickTimes.setText(kc.getFirstKick()+"");
                                            lastKicksTime.setText(getResources().getString(R.string.latestKickCount));
                                            txtViewLastKickDate.setText(getResources().getString(R.string.lastKickDate));
                                        }else{
                                            txtViewKickTimes.setText(kc.getLastKick()+"");
                                            counterKick = kc.getLastKick();
                                            txtViewLastKickDate.setText(dateFormat.format(kc.getLastKickDate()));
                                            lastKicksTime.setText(timeFormat.format(kc.getLastKickTime()));
                                        }
                                        break;
                                    }
                                    CheckTime();
                                    if(!txtViewKickTimes.getText().toString().equals("10"))
                                    {
                                        SetKickCountAlert();
                                    }else{
                                        CancelKickCountAlert();
                                    }
                                    progressBarKickCount.setVisibility(View.GONE);
                                    layoutKickCount.setVisibility(View.VISIBLE);
                                }
                            }
                        });
                    }
                });
            }
        });

        CheckKickCountTextChange();

        kickCount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isEmpty == true) {
                    counter++;
                    if(counter == 1)
                    {
                        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
                        int firstKick = counter;
                        Date firstKickDate = new Date();
                        Date firstKickTime = new Date();
                        int lastKick = 0;
                        Date lastKickDate = null;
                        Date lastKickTime = null;
                        babyKickNumber++;
                        final KickCount kc = new KickCount(firstKick, firstKickDate, firstKickTime, lastKick, lastKickDate, lastKickTime, babyKickNumber);
                        txtViewKickTimes.setText(firstKick+"");
                        kickTime.setText(timeFormat.format(firstKickTime));
                        txtViewFirstKickDate.setText(dateFormat.format(firstKickDate));
                        mCollectionReference = mFirebaseFirestore.collection("MommyHealthInfo");
                        mCollectionReference.whereEqualTo("healthInfoId", healthInfoId).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                            @Override
                            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                                String id = "";
                                for(QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots)
                                {
                                    id = documentSnapshot.getId();
                                }
                                nCollectionReference = mFirebaseFirestore.collection("MommyHealthInfo").document(id).collection("KickCount");
                                nCollectionReference.add(kc).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                    @Override
                                    public void onSuccess(DocumentReference documentReference) {
                                        Snackbar snackbar = Snackbar.make(layoutKickCount, "First Kick Saved", Snackbar.LENGTH_LONG);
                                        snackbar.show();
                                    }
                                });
                            }
                        });
                    }else{
                        if(counter == 11)
                        {
                            kickCount.setEnabled(false);
                            AlertDialog.Builder builder = new AlertDialog.Builder(KickCounterActivity.this);
                            builder.setTitle("Count Complete");
                            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    return;
                                }
                            });
                            builder.setMessage("Maximum 10 count per day");
                            AlertDialog alert = builder.create();
                            alert.show();
                        }else{
                            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                            SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
                            int firstKick = 1;
                            Date firstKickDate = null;
                            Date firstKickTime = null;
                            try {
                                firstKickTime = new SimpleDateFormat("HH:mm").parse(kickTime.getText().toString());
                                firstKickDate = new SimpleDateFormat("dd/MM/yyyy").parse(txtViewFirstKickDate.getText().toString());
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                            int lastKick = counter;
                            Date lastKickDate = new Date();
                            Date lastKickTime = new Date();
                            babyKickNumber++;
                            final KickCount kc = new KickCount(firstKick, firstKickDate, firstKickTime, lastKick, lastKickDate, lastKickTime, babyKickNumber);
                            txtViewKickTimes.setText(lastKick+"");
                            txtViewLastKickDate.setText(dateFormat.format(lastKickDate));
                            lastKicksTime.setText(timeFormat.format(lastKickTime));
                            mCollectionReference = mFirebaseFirestore.collection("MommyHealthInfo");
                            mCollectionReference.whereEqualTo("healthInfoId", healthInfoId).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                                @Override
                                public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                                    String id = "";
                                    for(QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots)
                                    {
                                        id = documentSnapshot.getId();
                                    }
                                    nCollectionReference = mFirebaseFirestore.collection("MommyHealthInfo").document(id).collection("KickCount");
                                    nCollectionReference.add(kc).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                        @Override
                                        public void onSuccess(DocumentReference documentReference) {
                                            Snackbar snackbar = Snackbar.make(layoutKickCount, "Latest Kick Saved", Snackbar.LENGTH_LONG);
                                            snackbar.show();
                                        }
                                    });
                                }
                            });
                        }
                    }
                }else{
                    int latestKickCount = Integer.parseInt(txtViewKickTimes.getText().toString());
                    ++latestKickCount;
                    if(latestKickCount == 1)
                    {
                        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
                        int firstKick = latestKickCount;
                        Date firstKickDate = new Date();
                        Date firstKickTime = new Date();
                        int lastKick = 0;
                        Date lastKickDate = null;
                        Date lastKickTime = null;
                        babyKickNumber++;
                        final KickCount kc = new KickCount(firstKick, firstKickDate, firstKickTime, lastKick, lastKickDate, lastKickTime, babyKickNumber);
                        txtViewKickTimes.setText(firstKick+"");
                        kickTime.setText(timeFormat.format(firstKickTime));
                        txtViewFirstKickDate.setText(dateFormat.format(firstKickDate));
                        pCollectionReference.add(kc).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {
                                Snackbar snackbar = Snackbar.make(layoutKickCount, "First Kick Saved", Snackbar.LENGTH_LONG);
                                snackbar.show();
                            }
                        });
                    }else{
                        if(latestKickCount == 11)
                        {
                            kickCount.setEnabled(false);
                            AlertDialog.Builder builder = new AlertDialog.Builder(KickCounterActivity.this);
                            builder.setTitle("Count Complete");
                            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    return;
                                }
                            });
                            builder.setMessage("Maximum 10 count per day");
                            AlertDialog alert = builder.create();
                            alert.show();
                        }else{
                            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                            SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
                            int firstKick = 1;
                            Date firstKickDate = null;
                            Date firstKickTime = null;
                            try {
                                firstKickTime = new SimpleDateFormat("HH:mm").parse(kickTime.getText().toString());
                                firstKickDate = new SimpleDateFormat("dd/MM/yyyy").parse(txtViewFirstKickDate.getText().toString());
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                            int lastKick = latestKickCount;
                            Log.i("Testing1", lastKick+"");
                            Date lastKickDate = new Date();
                            Date lastKickTime = new Date();
                            babyKickNumber++;
                            KickCount kc = new KickCount(firstKick, firstKickDate, firstKickTime, lastKick, lastKickDate, lastKickTime, babyKickNumber);
                            txtViewKickTimes.setText(lastKick+"");
                            txtViewLastKickDate.setText(dateFormat.format(lastKickDate));
                            lastKicksTime.setText(timeFormat.format(lastKickTime));
                            pCollectionReference.add(kc).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                @Override
                                public void onSuccess(DocumentReference documentReference) {
                                    Snackbar snackbar = Snackbar.make(layoutKickCount, "Latest Kick Updated", Snackbar.LENGTH_LONG);
                                    snackbar.show();
                                }
                            });
                        }
                    }
                }
            }
        });

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }


    private void CheckTime()
    {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date dat  = new Date();//initializes to now
        Calendar cal_alarm = Calendar.getInstance();
        Calendar cal_now = Calendar.getInstance();
        cal_now.setTime(dat);
        cal_alarm.set(Calendar.HOUR_OF_DAY,9);//set the alarm time
        cal_alarm.set(Calendar.MINUTE,0);
        cal_alarm.set(Calendar.SECOND,0);
        String currentDate = dateFormat.format(dat);
        Log.i("TestingDate", txtViewLastKickDate.getText().toString());
        if(txtViewLastKickDate.getText().toString().equals(getResources().getString(R.string.lastKickDate)))
        {
            if(!currentDate.equals(txtViewFirstKickDate.getText().toString()))
            {
                if(cal_now.equals(cal_alarm) || cal_now.after(cal_alarm))
                {
                    txtViewKickTimes.setText("0");
                    kickTime.setText(getResources().getString(R.string.firstKickCount));
                    txtViewFirstKickDate.setText(getResources().getString(R.string.firstKickDate));
                    txtViewLastKickDate.setText(getResources().getString(R.string.lastKickDate));
                    lastKicksTime.setText(getResources().getString(R.string.latestKickCount));
                    kickCount.setEnabled(true);
                }
            }
        }else{
            if(!currentDate.equals(txtViewLastKickDate.getText().toString()))
            {
                if(cal_now.equals(cal_alarm) || cal_now.after(cal_alarm))
                {
                    txtViewKickTimes.setText("0");
                    kickTime.setText(getResources().getString(R.string.firstKickCount));
                    txtViewFirstKickDate.setText(getResources().getString(R.string.firstKickDate));
                    txtViewLastKickDate.setText(getResources().getString(R.string.lastKickDate));
                    lastKicksTime.setText(getResources().getString(R.string.latestKickCount));
                    kickCount.setEnabled(true);
                }
            }
        }
    }

    private void SetKickCountAlert()
    {
        Intent myIntent = new Intent(this, AlertService.class);
        AlarmManager alarmManager = (AlarmManager)getSystemService( ALARM_SERVICE );
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 1000, myIntent, 0);
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.HOUR, 0);
        calendar.set(Calendar.AM_PM, Calendar.AM);
        calendar.add(Calendar.DAY_OF_MONTH, 1);
        alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
        Log.i("Testing KickCount Alert", calendar.getTime().toString()+"");
    }

    private void CancelKickCountAlert()
    {
        AlarmManager alarmManager = (AlarmManager)getSystemService(ALARM_SERVICE);
        Intent intent = new Intent(this, AlertService.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 1000, intent, 0);
        alarmManager.cancel(pendingIntent);
        pendingIntent.cancel();
        Log.i("Testing cancel Alert", "Alert Canceled");
    }

    private void CheckKickCountTextChange()
    {
        txtViewKickTimes.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!txtViewKickTimes.getText().toString().equals("10"))
                {
                    SetKickCountAlert();
                }else{
                    CancelKickCountAlert();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void CancelAlarm()
    {
        AlarmManager alarmManager = (AlarmManager)KickCounterActivity.this.getSystemService(Context.ALARM_SERVICE );
        Intent intent = new Intent(KickCounterActivity.this, NotifyService.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(KickCounterActivity.this, 9000, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        alarmManager.cancel(pendingIntent);
        pendingIntent.cancel();

        Intent intentReminder = new Intent(KickCounterActivity.this, ReminderService.class);
        PendingIntent pendingIntentReminder = PendingIntent.getBroadcast(KickCounterActivity.this, 101, intentReminder, PendingIntent.FLAG_UPDATE_CURRENT);
        alarmManager.cancel(pendingIntentReminder);
        pendingIntentReminder.cancel();

        Intent intentAlert = new Intent(KickCounterActivity.this, AlertService.class);
        PendingIntent pendingIntentAlert = PendingIntent.getBroadcast(KickCounterActivity.this, 1000, intentAlert, 0);
        alarmManager.cancel(pendingIntentAlert);
        pendingIntentAlert.cancel();

        Log.i("TestingAlarmCancel", "Alarm Cancel");
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.action_logout:
                AlertDialog.Builder builder = new AlertDialog.Builder(KickCounterActivity.this);
                builder.setTitle("Logout");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        CancelAlarm();
                        SaveSharedPreference.clearUser(KickCounterActivity.this);
                        Intent intent = new Intent(KickCounterActivity.this, MainActivity.class);
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
