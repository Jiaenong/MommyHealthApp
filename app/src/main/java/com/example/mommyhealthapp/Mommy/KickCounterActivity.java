package com.example.mommyhealthapp.Mommy;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.cardview.widget.CardView;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.mommyhealthapp.Class.KickCount;
import com.example.mommyhealthapp.Class.Mommy;
import com.example.mommyhealthapp.R;
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
                                    progressBarKickCount.setVisibility(View.GONE);
                                    layoutKickCount.setVisibility(View.VISIBLE);
                                }
                            }
                        });
                    }
                });
            }
        });

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
        Log.i("TestingDate", SaveSharedPreference.getBabyKickTime(KickCounterActivity.this));
        if(!currentDate.equals(SaveSharedPreference.getBabyKickTime(KickCounterActivity.this)))
        {
            if(cal_now.equals(cal_alarm) || cal_now.after(cal_alarm))
            {
                txtViewKickTimes.setText("0");
                kickTime.setText(getResources().getString(R.string.firstKickCount));
                txtViewFirstKickDate.setText(getResources().getString(R.string.firstKickDate));
                txtViewLastKickDate.setText(getResources().getString(R.string.lastKickDate));
                lastKicksTime.setText(getResources().getString(R.string.latestKickCount));
                kickCount.setEnabled(true);
                SaveSharedPreference.setBabyKickTime(KickCounterActivity.this, currentDate);
            }
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
