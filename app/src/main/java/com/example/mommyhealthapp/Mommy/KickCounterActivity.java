package com.example.mommyhealthapp.Mommy;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.LinearLayoutCompat;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.SafeBrowsingResponse;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.mommyhealthapp.Class.BabyKickCount;
import com.example.mommyhealthapp.Class.KickCount;
import com.example.mommyhealthapp.R;
import com.example.mommyhealthapp.SaveSharedPreference;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

public class KickCounterActivity extends AppCompatActivity {
    private ImageButton kickCount;
    private TextView firstRecord, kickTime, lastRecord, lastKicksTime;
    private LinearLayoutCompat layoutKickCount;
    private ProgressBar progressBarKickCount;
    private int counter = 0, counterKick;
    private String IdKey;
    private Boolean isEmpty;

    private FirebaseFirestore mFirebaseFirestore;
    private CollectionReference mCollectionReference, nCollectionReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kick_counter);
        kickCount = (ImageButton)findViewById(R.id.kickCount);
        firstRecord = (TextView)findViewById(R.id.firstRecord);
        kickTime = (TextView)findViewById(R.id.kickTime);
        lastRecord = (TextView)findViewById(R.id.lastRecord);
        lastKicksTime = (TextView)findViewById(R.id.lastKicksTime);
        layoutKickCount = (LinearLayoutCompat)findViewById(R.id.layoutKickCount);
        progressBarKickCount = (ProgressBar)findViewById(R.id.progressBarKickCount);

        progressBarKickCount.setVisibility(View.VISIBLE);
        layoutKickCount.setVisibility(View.GONE);

        mFirebaseFirestore = FirebaseFirestore.getInstance();
        nCollectionReference = mFirebaseFirestore.collection("BabyKickCount");
        nCollectionReference.whereEqualTo("BabyKickInfoId", SaveSharedPreference.getBabykickinfoId(KickCounterActivity.this)).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                if(queryDocumentSnapshots.isEmpty())
                {
                    isEmpty = true;
                }else{
                    isEmpty = false;
                    for(QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots)
                    {
                        IdKey = documentSnapshot.getId();
                    }
                    mCollectionReference = mFirebaseFirestore.collection("BabyKickCount").document(IdKey).collection("KickCount");
                    mCollectionReference.orderBy("lastKickTime", Query.Direction.DESCENDING).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                        @Override
                        public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                            for(QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots)
                            {
                                KickCount kc = documentSnapshot.toObject(KickCount.class);
                                SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                                SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
                                kickTime.setText(kc.getFirstKick()+" "+dateFormat.format(kc.getFirstKickDate())+" "+timeFormat.format(kc.getFirstKickTime()));
                                if(kc.getLastKick() == 0 || kc.getLastKickDate() == null || kc.getLastKickTime() == null)
                                {
                                    lastKicksTime.setText("");
                                }else{
                                    counterKick = kc.getLastKick();
                                    Log.i("Testing", counterKick+"");
                                    lastKicksTime.setText(kc.getLastKick()+" "+dateFormat.format(kc.getLastKickDate())+" "+timeFormat.format(kc.getLastKickTime()));
                                }
                                progressBarKickCount.setVisibility(View.GONE);
                                layoutKickCount.setVisibility(View.VISIBLE);
                                break;
                            }
                        }
                    });
                }
            }
        });

        kickCount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isEmpty == true) {
                    counter++;
                    if(counter == 1)
                    {
                        GetBabyInfoId(new BabyInfoIdCallBack() {
                            @Override
                            public void onBabyInfoIdCallBack(String key) {
                                IdKey = key;
                            }
                        });
                    }else{
                        if(counter == 10)
                        {
                            kickCount.setEnabled(false);
                            Snackbar snackbar = Snackbar.make(layoutKickCount, "10 record has been added", Snackbar.LENGTH_LONG);
                            snackbar.show();
                        }else{
                            mCollectionReference = mFirebaseFirestore.collection("BabyKickCount").document(IdKey).collection("KickCount");
                            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                            SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
                            String[] firstKickElement = kickTime.getText().toString().split(" ",0);
                            String firstKick = firstKickElement[0];
                            Date firstKickDate = null;
                            Date firstKickTime = null;
                            try {
                                firstKickDate = new SimpleDateFormat("dd/MM/yyyy").parse(firstKickElement[1]);
                                firstKickTime = new SimpleDateFormat("HH:mm").parse(firstKickElement[2]);
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                            int lastKick = counter;
                            Date lastKickDate = new Date();
                            Date lastKickTime = new Date();
                            KickCount kc = new KickCount(Integer.parseInt(firstKick), firstKickDate, firstKickTime, lastKick, lastKickDate, lastKickTime);
                            lastKicksTime.setText(lastKick+" "+dateFormat.format(lastKickDate)+" "+timeFormat.format(lastKickTime));
                            mCollectionReference.add(kc).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                @Override
                                public void onSuccess(DocumentReference documentReference) {
                                    Snackbar snackbar = Snackbar.make(layoutKickCount, "Latest Kick Updated", Snackbar.LENGTH_LONG);
                                    snackbar.show();
                                }
                            });
                        }
                    }
                }else{
                    if(counterKick == 10)
                    {
                        kickCount.setEnabled(false);
                        Snackbar snackbar = Snackbar.make(layoutKickCount, "10 record has been added", Snackbar.LENGTH_LONG);
                        snackbar.show();
                    }else{
                        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
                        String[] firstKickElement = kickTime.getText().toString().split(" ",0);
                        String firstKick = firstKickElement[0];
                        Date firstKickDate = null;
                        Date firstKickTime = null;
                        try {
                            firstKickDate = new SimpleDateFormat("dd/MM/yyyy").parse(firstKickElement[1]);
                            firstKickTime = new SimpleDateFormat("HH:mm").parse(firstKickElement[2]);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        int lastKick = ++counterKick;
                        Log.i("Testing1", lastKick+"");
                        Date lastKickDate = new Date();
                        Date lastKickTime = new Date();
                        KickCount kc = new KickCount(Integer.parseInt(firstKick), firstKickDate, firstKickTime, lastKick, lastKickDate, lastKickTime);
                        lastKicksTime.setText(lastKick+" "+dateFormat.format(lastKickDate)+" "+timeFormat.format(lastKickTime));
                        mCollectionReference.add(kc).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {
                                Snackbar snackbar = Snackbar.make(layoutKickCount, "Latest Kick Updated", Snackbar.LENGTH_LONG);
                                snackbar.show();
                            }
                        });
                    }
                }
            }
        });

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public interface BabyInfoIdCallBack{
        void onBabyInfoIdCallBack(String key);
    }

    public void GetBabyInfoId(final BabyInfoIdCallBack callBack)
    {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
        int firstKick = counter;
        Date firstKickDate = new Date();
        Date firstKickTime = new Date();
        int lastKick = 0;
        Date lastKickDate = null;
        Date lastKickTime = null;
        Date today = new Date();
        String babyKickInfoId = UUID.randomUUID().toString().replace("-", "");
        SaveSharedPreference.setBabykickinfoId(KickCounterActivity.this, babyKickInfoId);
        BabyKickCount bkc = new BabyKickCount(babyKickInfoId, SaveSharedPreference.getID(KickCounterActivity.this), today);
        final KickCount kc = new KickCount(firstKick, firstKickDate, firstKickTime, lastKick, lastKickDate, lastKickTime);
        kickTime.setText(firstKick+" "+dateFormat.format(firstKickDate)+ " "+timeFormat.format(firstKickTime));
        nCollectionReference.add(bkc).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                String key = documentReference.getId();
                callBack.onBabyInfoIdCallBack(key);
                mCollectionReference = mFirebaseFirestore.collection("BabyKickCount").document(documentReference.getId()).collection("KickCount");
                mCollectionReference.add(kc).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Snackbar snackbar = Snackbar.make(layoutKickCount, "First Kick Saved", Snackbar.LENGTH_LONG);
                        snackbar.show();
                    }
                });
            }
        });
    }

    private Boolean CheckTime()
    {
        Boolean timeReach;
        Date dat  = new Date();//initializes to now
        Calendar cal_alarm = Calendar.getInstance();
        Calendar cal_now = Calendar.getInstance();
        cal_now.setTime(dat);
        cal_alarm.setTime(dat);
        cal_alarm.set(Calendar.HOUR_OF_DAY,9);//set the alarm time
        cal_alarm.set(Calendar.MINUTE,0);
        cal_alarm.set(Calendar.SECOND,0);
        if(cal_alarm.before(cal_now)){//if its in the past increment
            cal_alarm.add(Calendar.DATE,1);
        }
        if(cal_alarm.equals(cal_now) || cal_alarm.after(cal_now))
        {
            counterKick = 0;
            kickCount.setEnabled(true);
            timeReach = true;
        }else{
            timeReach = false;
        }
        return timeReach;
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
