package com.example.mommyhealthapp.Nurse;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.LinearLayoutCompat;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.mommyhealthapp.AlertService;
import com.example.mommyhealthapp.Class.Mommy;
import com.example.mommyhealthapp.Class.PregnancyExamination;
import com.example.mommyhealthapp.MainActivity;
import com.example.mommyhealthapp.NotifyService;
import com.example.mommyhealthapp.R;
import com.example.mommyhealthapp.ReminderService;
import com.example.mommyhealthapp.SaveSharedPreference;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.helper.DateAsXAxisLabelFormatter;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class SummaryReportActivity extends AppCompatActivity {
    private GraphView bloodPressureGraph, hemoglobinGraph;
    private ProgressBar progressBarSummaryReport;
    private LinearLayoutCompat layoutSummaryReport;
    private ImageView imageViewNoRecordFoundSR;
    private TextView txtViewNoRecordFoundSR;

    private FirebaseFirestore mFirebaseFirestore;
    private CollectionReference mCollectionReference, nCollectionReference, pCollectionReference;

    private String healthInfoId;
    private List<PregnancyExamination> peList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summary_report);

        bloodPressureGraph = (GraphView)findViewById(R.id.bloodPressureGraph);
        hemoglobinGraph = (GraphView)findViewById(R.id.hemoglobinGraph);
        progressBarSummaryReport = (ProgressBar)findViewById(R.id.progressBarSummaryReport);
        layoutSummaryReport = (LinearLayoutCompat)findViewById(R.id.layoutSummaryReport);
        imageViewNoRecordFoundSR = (ImageView)findViewById(R.id.imageViewNoRecordFoundSR);
        txtViewNoRecordFoundSR = (TextView)findViewById(R.id.txtViewNoRecordFoundSR);

        progressBarSummaryReport.setVisibility(View.VISIBLE);
        layoutSummaryReport.setVisibility(View.GONE);

        peList = new ArrayList<>();

        mFirebaseFirestore = FirebaseFirestore.getInstance();
        if(SaveSharedPreference.getUser(SummaryReportActivity.this).equals("Mommy"))
        {
            MummyViewReport();
        }else{
            mCollectionReference = mFirebaseFirestore.collection("MommyHealthInfo");
            mCollectionReference.whereEqualTo("healthInfoId", SaveSharedPreference.getHealthInfoId(SummaryReportActivity.this)).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                @Override
                public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                    for(QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots)
                    {
                        healthInfoId = documentSnapshot.getId();
                    }
                    nCollectionReference = mFirebaseFirestore.collection("MommyHealthInfo").document(healthInfoId).collection("PregnancyExamination");
                    nCollectionReference.orderBy("createdDate", Query.Direction.ASCENDING).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                        @Override
                        public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                            if(queryDocumentSnapshots.isEmpty())
                            {
                                progressBarSummaryReport.setVisibility(View.GONE);
                                layoutSummaryReport.setVisibility(View.GONE);
                                imageViewNoRecordFoundSR.setVisibility(View.VISIBLE);
                                txtViewNoRecordFoundSR.setVisibility(View.VISIBLE);
                            }else{
                                for(QueryDocumentSnapshot documentSnapshots : queryDocumentSnapshots)
                                {
                                    PregnancyExamination pe = documentSnapshots.toObject(PregnancyExamination.class);
                                    peList.add(pe);
                                }
                                progressBarSummaryReport.setVisibility(View.GONE);
                                layoutSummaryReport.setVisibility(View.VISIBLE);
                                imageViewNoRecordFoundSR.setVisibility(View.GONE);
                                txtViewNoRecordFoundSR.setVisibility(View.GONE);

                                GenerateBloodPressureGraph();
                                GenerateHemoglobinGraph();
                            }
                        }
                    });
                }
            });
        }

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void MummyViewReport()
    {
        mCollectionReference = mFirebaseFirestore.collection("Mommy");
        mCollectionReference.whereEqualTo("mommyId", SaveSharedPreference.getMummyId(SummaryReportActivity.this)).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                String mommyHealthInfoId = "";
                for(QueryDocumentSnapshot documentSnapshot: queryDocumentSnapshots)
                {
                    Mommy mommy = documentSnapshot.toObject(Mommy.class);
                    mommyHealthInfoId = mommy.getHealthInfoId();
                }
                nCollectionReference = mFirebaseFirestore.collection("MommyHealthInfo");
                nCollectionReference.whereEqualTo("healthInfoId", mommyHealthInfoId).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        for(QueryDocumentSnapshot documentSnapshots : queryDocumentSnapshots)
                        {
                            healthInfoId = documentSnapshots.getId();
                        }
                        pCollectionReference = mFirebaseFirestore.collection("MommyHealthInfo").document(healthInfoId).collection("PregnancyExamination");
                        pCollectionReference.orderBy("createdDate", Query.Direction.ASCENDING).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                            @Override
                            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                                if(queryDocumentSnapshots.isEmpty())
                                {
                                    progressBarSummaryReport.setVisibility(View.GONE);
                                    layoutSummaryReport.setVisibility(View.GONE);
                                    imageViewNoRecordFoundSR.setVisibility(View.VISIBLE);
                                    txtViewNoRecordFoundSR.setVisibility(View.VISIBLE);
                                }else{
                                    for(QueryDocumentSnapshot documentSnapshotss : queryDocumentSnapshots)
                                    {
                                        PregnancyExamination pe = documentSnapshotss.toObject(PregnancyExamination.class);
                                        peList.add(pe);
                                    }
                                    progressBarSummaryReport.setVisibility(View.GONE);
                                    layoutSummaryReport.setVisibility(View.VISIBLE);
                                    imageViewNoRecordFoundSR.setVisibility(View.GONE);
                                    txtViewNoRecordFoundSR.setVisibility(View.GONE);

                                    GenerateBloodPressureGraph();
                                    GenerateHemoglobinGraph();
                                }
                            }
                        });
                    }
                });
            }
        });
    }

    private void GenerateBloodPressureGraph()
    {
        LineGraphSeries<DataPoint> series = new LineGraphSeries<>();
        for(int i=0; i<peList.size(); i++)
        {
            DataPoint point = new DataPoint(peList.get(i).getCreatedDate(), peList.get(i).getBloodPressure());
            series.appendData(point, true, peList.size());
        }
        bloodPressureGraph.addSeries(series);
        bloodPressureGraph.getGridLabelRenderer().setLabelFormatter(new DateAsXAxisLabelFormatter(SummaryReportActivity.this));
        bloodPressureGraph.getGridLabelRenderer().setNumHorizontalLabels(3);
        bloodPressureGraph.getGridLabelRenderer().setHumanRounding(false);
        bloodPressureGraph.getViewport().setScalable(true);
        bloodPressureGraph.getViewport().setScalableY(true);
    }

    private void GenerateHemoglobinGraph()
    {
        LineGraphSeries<DataPoint> series = new LineGraphSeries<>();
        for(int i=0; i<peList.size(); i++)
        {
            DataPoint point = new DataPoint(peList.get(i).getCreatedDate(), peList.get(i).getHb());
            series.appendData(point, true, peList.size());
        }
        hemoglobinGraph.addSeries(series);
        hemoglobinGraph.getGridLabelRenderer().setLabelFormatter(new DateAsXAxisLabelFormatter(SummaryReportActivity.this));
        hemoglobinGraph.getGridLabelRenderer().setNumHorizontalLabels(3);
        hemoglobinGraph.getGridLabelRenderer().setHumanRounding(false);
        hemoglobinGraph.getViewport().setScalable(true);
        hemoglobinGraph.getViewport().setScalableY(true);
    }

    private void CancelAlarm()
    {
        AlarmManager alarmManager = (AlarmManager)SummaryReportActivity.this.getSystemService(Context.ALARM_SERVICE );
        Intent intent = new Intent(SummaryReportActivity.this, NotifyService.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(SummaryReportActivity.this, 9000, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        alarmManager.cancel(pendingIntent);
        pendingIntent.cancel();

        Intent intentReminder = new Intent(SummaryReportActivity.this, ReminderService.class);
        PendingIntent pendingIntentReminder = PendingIntent.getBroadcast(SummaryReportActivity.this, 101, intentReminder, PendingIntent.FLAG_UPDATE_CURRENT);
        alarmManager.cancel(pendingIntentReminder);
        pendingIntentReminder.cancel();

        Intent intentAlert = new Intent(SummaryReportActivity.this, AlertService.class);
        PendingIntent pendingIntentAlert = PendingIntent.getBroadcast(SummaryReportActivity.this, 1000, intentAlert, 0);
        alarmManager.cancel(pendingIntentAlert);
        pendingIntentAlert.cancel();

        Log.i("TestingAlarmCancel", "Alarm Cancel");
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.action_logout:
                AlertDialog.Builder builder = new AlertDialog.Builder(SummaryReportActivity.this);
                builder.setTitle("Logout");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if(SaveSharedPreference.getUser(SummaryReportActivity.this).equals("Mommy"))
                        {
                            CancelAlarm();
                        }
                        SaveSharedPreference.clearUser(SummaryReportActivity.this);
                        Intent intent = new Intent(SummaryReportActivity.this, MainActivity.class);
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
