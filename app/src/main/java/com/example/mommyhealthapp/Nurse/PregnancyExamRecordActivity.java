package com.example.mommyhealthapp.Nurse;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.TextView;

import com.example.mommyhealthapp.Class.BloodTest;
import com.example.mommyhealthapp.Class.MommyHealthInfo;
import com.example.mommyhealthapp.Class.PregnancyExamination;
import com.example.mommyhealthapp.MainActivity;
import com.example.mommyhealthapp.R;
import com.example.mommyhealthapp.RecyclerTouchListener;
import com.example.mommyhealthapp.SaveSharedPreference;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class PregnancyExamRecordActivity extends AppCompatActivity {
    private RecyclerView recyclerViewPregnantRecord;
    private ImageView imgViewExamNoRecordFound;
    private TextView txtViewExamNoRecordFound;
    private ProgressBar progressBarPregRecord;
    private FloatingActionButton btnAddNewPERecord;

    private FirebaseFirestore mFirebaseFirestore;
    private CollectionReference mCollectionReference, nCollectionReference;

    private String healthInfoId;
    private List<PregnancyExamination> peList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pregnancy_exam_record);

        recyclerViewPregnantRecord = (RecyclerView)findViewById(R.id.recyclerViewPregnantRecord);
        imgViewExamNoRecordFound = (ImageView)findViewById(R.id.imgViewExamNoRecordFound);
        txtViewExamNoRecordFound = (TextView)findViewById(R.id.txtViewExamNoRecordFound);
        progressBarPregRecord = (ProgressBar)findViewById(R.id.progressBarPregRecord);
        btnAddNewPERecord = (FloatingActionButton)findViewById(R.id.btnAddPERecord);

        recyclerViewPregnantRecord.setVisibility(View.GONE);
        imgViewExamNoRecordFound.setVisibility(View.GONE);
        txtViewExamNoRecordFound.setVisibility(View.GONE);
        progressBarPregRecord.setVisibility(View.VISIBLE);
        btnAddNewPERecord.setVisibility(View.GONE);

        peList = new ArrayList<>();
        mFirebaseFirestore = FirebaseFirestore.getInstance();
        mCollectionReference = mFirebaseFirestore.collection("MommyHealthInfo");
        if(SaveSharedPreference.getUser(PregnancyExamRecordActivity.this).equals("Mommy")){
            MommyLogIn();
        }
        else{
            mCollectionReference.whereEqualTo("healthInfoId", SaveSharedPreference.getHealthInfoId(PregnancyExamRecordActivity.this)).addSnapshotListener(new EventListener<QuerySnapshot>() {
                @Override
                public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                    for(QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots)
                    {
                        healthInfoId = documentSnapshot.getId();
                    }
                    nCollectionReference = mFirebaseFirestore.collection("MommyHealthInfo").document(healthInfoId).collection("PregnancyExamination");
                    nCollectionReference.addSnapshotListener(new EventListener<QuerySnapshot>() {
                        @Override
                        public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                            peList.clear();
                            for(QueryDocumentSnapshot documentSnapshots : queryDocumentSnapshots)
                            {
                                PregnancyExamination pe = documentSnapshots.toObject(PregnancyExamination.class);
                                peList.add(pe);
                            }
                            PregnancyExamRecordAdapter adapter = new PregnancyExamRecordAdapter(peList);
                            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(PregnancyExamRecordActivity.this);
                            recyclerViewPregnantRecord.setLayoutManager(mLayoutManager);
                            recyclerViewPregnantRecord.setItemAnimator(new DefaultItemAnimator());
                            recyclerViewPregnantRecord.addItemDecoration(new DividerItemDecoration(PregnancyExamRecordActivity.this, LinearLayoutManager.VERTICAL));
                            recyclerViewPregnantRecord.setAdapter(adapter);
                            if(peList.isEmpty())
                            {
                                recyclerViewPregnantRecord.setVisibility(View.GONE);
                                imgViewExamNoRecordFound.setVisibility(View.VISIBLE);
                                txtViewExamNoRecordFound.setVisibility(View.VISIBLE);
                                progressBarPregRecord.setVisibility(View.GONE);
                                btnAddNewPERecord.setVisibility(View.VISIBLE);
                            }else{
                                recyclerViewPregnantRecord.setVisibility(View.VISIBLE);
                                imgViewExamNoRecordFound.setVisibility(View.GONE);
                                txtViewExamNoRecordFound.setVisibility(View.GONE);
                                progressBarPregRecord.setVisibility(View.GONE);
                                btnAddNewPERecord.setVisibility(View.VISIBLE);
                            }
                        }
                    });
                }
            });
        }

        recyclerViewPregnantRecord.addOnItemTouchListener(new RecyclerTouchListener(PregnancyExamRecordActivity.this, recyclerViewPregnantRecord, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                PregnancyExamination pe = peList.get(position);
                Intent intent = new Intent(PregnancyExamRecordActivity.this, PregnancyExaminationActivity.class);
                intent.putExtra("healthInfoId", healthInfoId);
                intent.putExtra("pregnancyExamId", pe.getPregnancyExamId());
                startActivity(intent);
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

        btnAddNewPERecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PregnancyExamRecordActivity.this, PregnancyExaminationActivity.class);
                intent.putExtra("healthInfoId", healthInfoId);
                startActivity(intent);
            }
        });

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void MommyLogIn(){
        mCollectionReference.whereEqualTo("healthInfoId", SaveSharedPreference.getHealthInfoId(PregnancyExamRecordActivity.this)).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
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
                        for(QueryDocumentSnapshot documentSnapshots : queryDocumentSnapshots)
                        {
                            PregnancyExamination pe = documentSnapshots.toObject(PregnancyExamination.class);
                            peList.add(pe);
                        }
                        PregnancyExamRecordAdapter adapter = new PregnancyExamRecordAdapter(peList);
                        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(PregnancyExamRecordActivity.this);
                        recyclerViewPregnantRecord.setLayoutManager(mLayoutManager);
                        recyclerViewPregnantRecord.setItemAnimator(new DefaultItemAnimator());
                        recyclerViewPregnantRecord.addItemDecoration(new DividerItemDecoration(PregnancyExamRecordActivity.this, LinearLayoutManager.VERTICAL));
                        recyclerViewPregnantRecord.setAdapter(adapter);
                        if(peList.isEmpty())
                        {
                            recyclerViewPregnantRecord.setVisibility(View.GONE);
                            imgViewExamNoRecordFound.setVisibility(View.VISIBLE);
                            txtViewExamNoRecordFound.setVisibility(View.VISIBLE);
                            progressBarPregRecord.setVisibility(View.GONE);
                            btnAddNewPERecord.setVisibility(View.GONE);
                        }else{
                            recyclerViewPregnantRecord.setVisibility(View.VISIBLE);
                            imgViewExamNoRecordFound.setVisibility(View.GONE);
                            txtViewExamNoRecordFound.setVisibility(View.GONE);
                            progressBarPregRecord.setVisibility(View.GONE);
                            btnAddNewPERecord.setVisibility(View.GONE);
                        }
                    }
                });
            }
        });
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.action_logout:
                AlertDialog.Builder builder = new AlertDialog.Builder(PregnancyExamRecordActivity.this);
                builder.setTitle("Logout");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        SaveSharedPreference.clearUser(PregnancyExamRecordActivity.this);
                        Intent intent = new Intent(PregnancyExamRecordActivity.this, MainActivity.class);
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

    public class PregnancyExamRecordAdapter extends RecyclerView.Adapter<PregnancyExamRecordAdapter.MyViewHolder>{
        private List<PregnancyExamination> listP;

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View resultView = LayoutInflater.from(parent.getContext()).inflate(R.layout.pregnancy_examination_record, parent, false);
            return new MyViewHolder(resultView);
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, int position) {
            PregnancyExamination pe = listP.get(position);
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

            holder.txtViewMeetDate.setText(dateFormat.format(pe.getCreatedDate()));
            holder.txtViewMedicalPersonnelMeet.setText(pe.getMedicalPersonnelName());
        }

        @Override
        public int getItemCount() {
            return listP.size();
        }

        public class MyViewHolder extends RecyclerView.ViewHolder{
            public TextView txtViewMeetDate, txtViewMedicalPersonnelMeet;

            public MyViewHolder(View view)
            {
                super(view);
                txtViewMeetDate = (TextView)view.findViewById(R.id.txtViewMeetDate);
                txtViewMedicalPersonnelMeet = (TextView)view.findViewById(R.id.txtViewMedicalPersonnelMeet);
            }
        }

        public PregnancyExamRecordAdapter(List<PregnancyExamination> list)
        {
            listP = list;
        }
    }
}
