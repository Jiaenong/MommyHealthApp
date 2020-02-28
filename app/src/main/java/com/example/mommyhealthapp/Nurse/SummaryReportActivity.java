package com.example.mommyhealthapp.Nurse;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.LinearLayoutCompat;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.mommyhealthapp.Class.PregnancyExamination;
import com.example.mommyhealthapp.R;
import com.example.mommyhealthapp.SaveSharedPreference;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
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
    private CollectionReference mCollectionReference, nCollectionReference;

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
        mCollectionReference = mFirebaseFirestore.collection("MommyHealthInfo");
        mCollectionReference.whereEqualTo("mommyId", SaveSharedPreference.getMummyId(SummaryReportActivity.this)).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for(QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots)
                {
                    healthInfoId = documentSnapshot.getId();
                }
                nCollectionReference = mFirebaseFirestore.collection("MommyHealthInfo").document(healthInfoId).collection("PregnancyExamination");
                nCollectionReference.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
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

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
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
