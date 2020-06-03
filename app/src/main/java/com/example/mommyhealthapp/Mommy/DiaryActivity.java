package com.example.mommyhealthapp.Mommy;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.mommyhealthapp.AlertService;
import com.example.mommyhealthapp.Class.DiaryImage;
import com.example.mommyhealthapp.MainActivity;
import com.example.mommyhealthapp.NotifyService;
import com.example.mommyhealthapp.Nurse.PregnancyExamRecordActivity;
import com.example.mommyhealthapp.R;
import com.example.mommyhealthapp.RecyclerTouchListener;
import com.example.mommyhealthapp.ReminderService;
import com.example.mommyhealthapp.SaveSharedPreference;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class DiaryActivity extends AppCompatActivity {
    private RecyclerView recyclerViewImageRecord;
    private FloatingActionButton btnAddImageRecord;
    private ImageView imgViewImageNoRecordFound;
    private TextView txtVieImageNoRecordFound;
    private ProgressBar progressBarImageRecord;

    private String healthInfoId;
    private List<DiaryImage> diList;

    private FirebaseFirestore mFirebaseFirestore;
    private CollectionReference mCollectionReference, nCollectionReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diary);

        recyclerViewImageRecord = (RecyclerView)findViewById(R.id.recyclerViewImageRecord);
        btnAddImageRecord = (FloatingActionButton)findViewById(R.id.btnAddImageRecord);
        imgViewImageNoRecordFound = (ImageView)findViewById(R.id.imgViewImageNoRecordFound);
        txtVieImageNoRecordFound = (TextView)findViewById(R.id.txtVieImageNoRecordFound);
        progressBarImageRecord = (ProgressBar)findViewById(R.id.progressBarImageRecord);

        recyclerViewImageRecord.setVisibility(View.GONE);
        imgViewImageNoRecordFound.setVisibility(View.GONE);
        txtVieImageNoRecordFound.setVisibility(View.GONE);
        progressBarImageRecord.setVisibility(View.VISIBLE);
        btnAddImageRecord.setVisibility(View.GONE);

        diList = new ArrayList<>();
        mFirebaseFirestore = FirebaseFirestore.getInstance();
        mCollectionReference = mFirebaseFirestore.collection("MommyHealthInfo");
        mCollectionReference.whereEqualTo("healthInfoId", SaveSharedPreference.getHealthInfoId(DiaryActivity.this)).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                for(QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots)
                {
                    healthInfoId = documentSnapshot.getId();
                }
                nCollectionReference = mFirebaseFirestore.collection("MommyHealthInfo").document(healthInfoId).collection("DiaryImage");
                nCollectionReference.orderBy("createdDate", Query.Direction.DESCENDING).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        diList.clear();
                        for(QueryDocumentSnapshot documentSnapshots : queryDocumentSnapshots)
                        {
                            DiaryImage di = documentSnapshots.toObject(DiaryImage.class);
                            diList.add(di);
                        }
                        DiaryImageRecordAdapter adapter = new DiaryImageRecordAdapter(diList);
                        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(DiaryActivity.this);
                        recyclerViewImageRecord.setLayoutManager(mLayoutManager);
                        recyclerViewImageRecord.setItemAnimator(new DefaultItemAnimator());
                        recyclerViewImageRecord.addItemDecoration(new DividerItemDecoration(DiaryActivity.this, LinearLayoutManager.VERTICAL));
                        recyclerViewImageRecord.setAdapter(adapter);
                        if(diList.isEmpty())
                        {
                            recyclerViewImageRecord.setVisibility(View.GONE);
                            imgViewImageNoRecordFound.setVisibility(View.VISIBLE);
                            txtVieImageNoRecordFound.setVisibility(View.VISIBLE);
                            progressBarImageRecord.setVisibility(View.GONE);
                            btnAddImageRecord.setVisibility(View.VISIBLE);
                        }else{
                            recyclerViewImageRecord.setVisibility(View.VISIBLE);
                            imgViewImageNoRecordFound.setVisibility(View.GONE);
                            txtVieImageNoRecordFound.setVisibility(View.GONE);
                            progressBarImageRecord.setVisibility(View.GONE);
                            btnAddImageRecord.setVisibility(View.VISIBLE);
                        }
                    }
                });

            }
        });

        recyclerViewImageRecord.addOnItemTouchListener(new RecyclerTouchListener(DiaryActivity.this, recyclerViewImageRecord, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                DiaryImage di = diList.get(position);
                Intent intent = new Intent(DiaryActivity.this, UploadDiaryActivity.class);
                intent.putExtra("healthInfoId", healthInfoId);
                intent.putExtra("imageId", di.getImageId());
                startActivity(intent);
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

        btnAddImageRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DiaryActivity.this, UploadDiaryActivity.class);
                intent.putExtra("healthInfoId", healthInfoId);
                startActivity(intent);
            }
        });

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void CancelAlarm()
    {
        AlarmManager alarmManager = (AlarmManager)DiaryActivity.this.getSystemService(Context.ALARM_SERVICE );
        Intent intent = new Intent(DiaryActivity.this, NotifyService.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(DiaryActivity.this, 9000, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        alarmManager.cancel(pendingIntent);
        pendingIntent.cancel();

        Intent intentReminder = new Intent(DiaryActivity.this, ReminderService.class);
        PendingIntent pendingIntentReminder = PendingIntent.getBroadcast(DiaryActivity.this, 101, intentReminder, PendingIntent.FLAG_UPDATE_CURRENT);
        alarmManager.cancel(pendingIntentReminder);
        pendingIntentReminder.cancel();

        Intent intentAlert = new Intent(DiaryActivity.this, AlertService.class);
        PendingIntent pendingIntentAlert = PendingIntent.getBroadcast(DiaryActivity.this, 1000, intentAlert, 0);
        alarmManager.cancel(pendingIntentAlert);
        pendingIntentAlert.cancel();

        Log.i("TestingAlarmCancel", "Alarm Cancel");
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.action_logout:
                AlertDialog.Builder builder = new AlertDialog.Builder(DiaryActivity.this);
                builder.setTitle("Logout");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        CancelAlarm();
                        SaveSharedPreference.clearUser(DiaryActivity.this);
                        Intent intent = new Intent(DiaryActivity.this, MainActivity.class);
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


    public class DiaryImageRecordAdapter extends RecyclerView.Adapter<DiaryImageRecordAdapter.MyViewHolder>{

        private  List<DiaryImage> listP;

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View resultView = LayoutInflater.from(parent.getContext()).inflate(R.layout.diary_recordview, parent, false);
            return new MyViewHolder(resultView);
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, int position) {
            DiaryImage di = listP.get(position);
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

            holder.txtViewImageRecordDate.setText(dateFormat.format(di.getCreatedDate()));
            holder.txtViewImageRecordTitle.setText(di.getTitle());
        }

        @Override
        public int getItemCount() {
            return listP.size();
        }

        public class MyViewHolder extends RecyclerView.ViewHolder{
            public TextView txtViewImageRecordDate, txtViewImageRecordTitle;

            public MyViewHolder(View view)
            {
                super(view);
                txtViewImageRecordDate = (TextView)view.findViewById(R.id.txtViewImageRecordDate);
                txtViewImageRecordTitle = (TextView)view.findViewById(R.id.txtViewImageRecordTitle);
            }
        }

        public DiaryImageRecordAdapter(List<DiaryImage> list)
        {
            listP = list;
        }
    }
}
