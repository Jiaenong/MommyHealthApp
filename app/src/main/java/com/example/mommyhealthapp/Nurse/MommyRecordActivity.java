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
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.mommyhealthapp.Class.Mommy;
import com.example.mommyhealthapp.Class.MommyHealthInfo;
import com.example.mommyhealthapp.MainActivity;
import com.example.mommyhealthapp.Nurse.MommyProfileActivity;
import com.example.mommyhealthapp.Nurse.ui.SearchMother.SearchMotherFragment;
import com.example.mommyhealthapp.R;
import com.example.mommyhealthapp.RecyclerTouchListener;
import com.example.mommyhealthapp.SaveSharedPreference;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.mikhaellopez.circularimageview.CircularImageView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MommyRecordActivity extends AppCompatActivity {

    private FloatingActionButton btnAddNewRecord;
    private ImageView imgViewNoRecordFound;
    private TextView txtViewNoRecordFound;
    private RecyclerView recycleViewMommyRecord;
    private ProgressBar progressBarMommyRecord;

    private FirebaseFirestore mFirebaseFirestore;
    private CollectionReference mCollectionReference, nCollectionReference;
    private DocumentReference mDocumentReference;

    private List<MommyHealthInfo> mommyRecordList;
    private String id, mummyKey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mommy_record);

        btnAddNewRecord = (FloatingActionButton)findViewById(R.id.btnAddNewRecord);
        imgViewNoRecordFound = (ImageView)findViewById(R.id.imgViewExamNoRecordFound);
        txtViewNoRecordFound = (TextView)findViewById(R.id.txtViewExamNoRecordFound);
        recycleViewMommyRecord = (RecyclerView)findViewById(R.id.recyclerViewPregnantRecord);
        progressBarMommyRecord = (ProgressBar)findViewById(R.id.progressBarMommyRecord);

        mFirebaseFirestore = FirebaseFirestore.getInstance();
        mCollectionReference = mFirebaseFirestore.collection("MommyHealthInfo");
        nCollectionReference = mFirebaseFirestore.collection("Mommy");

        mommyRecordList = new ArrayList<>();
        Intent intent = getIntent();
        id = intent.getStringExtra("MommyID");;

        recycleViewMommyRecord.addOnItemTouchListener(new RecyclerTouchListener(MommyRecordActivity.this, recycleViewMommyRecord, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                MommyHealthInfo mi = mommyRecordList.get(position);
                SaveSharedPreference.setHealthInfoId(MommyRecordActivity.this, mi.getHealthInfoId());
                SaveSharedPreference.setEarlyTest(MommyRecordActivity.this, "Old");
                Intent intent = new Intent(MommyRecordActivity.this, MommyProfileActivity.class);
                intent.putExtra("MommyID", mi.getMommyId());
                startActivity(intent);
            }

            @Override
            public void onLongClick(View view, int position) {
                final MommyHealthInfo mi = mommyRecordList.get(position);
                AlertDialog.Builder builder = new AlertDialog.Builder(MommyRecordActivity.this);
                builder.setTitle("Update Pregnant Record Status");
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        progressBarMommyRecord.setVisibility(View.VISIBLE);
                        recycleViewMommyRecord.setVisibility(View.GONE);
                        mCollectionReference.whereEqualTo("mommyId", id).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                            @Override
                            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                                for(QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots)
                                {
                                    MommyHealthInfo mhi = documentSnapshot.toObject(MommyHealthInfo.class);
                                    DocumentReference mDocumentReference = mFirebaseFirestore.collection("MommyHealthInfo").document(documentSnapshot.getId());
                                    DocumentReference nDocumentReference = mFirebaseFirestore.collection("Mommy").document(mummyKey);
                                    if(mi.getHealthInfoId().equals(mhi.getHealthInfoId()))
                                    {
                                        if(mhi.getStatus().equals("Active"))
                                        {
                                            mDocumentReference.update("status", "Inactive");
                                        }else{
                                            mDocumentReference.update("status", "Active");
                                            nDocumentReference.update("healthInfoId", mhi.getHealthInfoId());
                                        }
                                    }
                                }
                                progressBarMommyRecord.setVisibility(View.GONE);
                                recycleViewMommyRecord.setVisibility(View.VISIBLE);
                            }
                        });
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        return;
                    }
                });
                builder.setMessage("Change Pregnant Record Status?");
                AlertDialog alert = builder.create();
                alert.show();
            }
        }));

        progressBarMommyRecord.setVisibility(View.VISIBLE);
        recycleViewMommyRecord.setVisibility(View.GONE);
        imgViewNoRecordFound.setVisibility(View.GONE);
        txtViewNoRecordFound.setVisibility(View.GONE);
        btnAddNewRecord.setVisibility(View.GONE);
        nCollectionReference.whereEqualTo("mommyId", id).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for(QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots)
                {
                    mummyKey = documentSnapshot.getId();
                }
            }
        });
        mCollectionReference.whereEqualTo("mommyId", id).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                mommyRecordList.clear();
                for(QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots)
                {
                    MommyHealthInfo mhi = documentSnapshot.toObject(MommyHealthInfo.class);
                    mommyRecordList.add(mhi);
                }
                MommyRecordAdapter adapter = new MommyRecordAdapter(mommyRecordList);
                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(MommyRecordActivity.this);
                recycleViewMommyRecord.setLayoutManager(mLayoutManager);
                recycleViewMommyRecord.setItemAnimator(new DefaultItemAnimator());
                recycleViewMommyRecord.addItemDecoration(new DividerItemDecoration(MommyRecordActivity.this, LinearLayoutManager.VERTICAL));
                recycleViewMommyRecord.setAdapter(adapter);
                progressBarMommyRecord.setVisibility(View.GONE);
                if(mommyRecordList.isEmpty())
                {
                    imgViewNoRecordFound.setVisibility(View.VISIBLE);
                    txtViewNoRecordFound.setVisibility(View.VISIBLE);
                    btnAddNewRecord.setVisibility(View.VISIBLE);
                }else {
                    recycleViewMommyRecord.setVisibility(View.VISIBLE);
                    imgViewNoRecordFound.setVisibility(View.GONE);
                    txtViewNoRecordFound.setVisibility(View.GONE);
                    btnAddNewRecord.setVisibility(View.VISIBLE);
                }
            }
        });

        btnAddNewRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SaveSharedPreference.setEarlyTest(MommyRecordActivity.this, "New");
                SaveSharedPreference.setHealthInfoId(MommyRecordActivity.this, "");
                Intent intent = new Intent(MommyRecordActivity.this, MommyProfileActivity.class);
                intent.putExtra("MommyID", id);
                startActivity(intent);
            }
        });

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.action_logout:
                AlertDialog.Builder builder = new AlertDialog.Builder(MommyRecordActivity.this);
                builder.setTitle("Logout");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        SaveSharedPreference.clearUser(MommyRecordActivity.this);
                        Intent intent = new Intent(MommyRecordActivity.this, MainActivity.class);
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

    public class MommyRecordAdapter extends RecyclerView.Adapter<MommyRecordAdapter.MyViewHolder>{
        private List<MommyHealthInfo> listP;

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View resultView = LayoutInflater.from(parent.getContext()).inflate(R.layout.pregnant_record_view, parent, false);
            return new MyViewHolder(resultView);
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, int position) {
            MommyHealthInfo mhi = listP.get(position);
            holder.txtViewRecordYear.setText(mhi.getYear());
            holder.txtViewRecordMonth.setText(mhi.getMonth());
            if(mhi.getStatus().equals("Active"))
            {
                holder.txtViewRecordStatus.setText(mhi.getStatus());
                holder.txtViewRecordStatus.setTextColor(Color.parseColor("#008000"));
            }else if(mhi.getStatus().equals("Inactive")) {
                holder.txtViewRecordStatus.setText(mhi.getStatus());
                holder.txtViewRecordStatus.setTextColor(Color.parseColor("#FF0000"));
            }

            if(mhi.getColorCode().equals("red"))
            {
                holder.textViewRecordColorCode.setText("Red Code");
                holder.textViewRecordColorCode.setTextColor(Color.RED);
            }else if(mhi.getColorCode().equals("yellow"))
            {
                holder.textViewRecordColorCode.setText("Yellow Code");
                holder.textViewRecordColorCode.setTextColor(Color.parseColor("#CCCC00"));
            }else if(mhi.getColorCode().equals("green"))
            {
                holder.textViewRecordColorCode.setText("Green Code");
                holder.textViewRecordColorCode.setTextColor(Color.parseColor("#006400"));
            }else if(mhi.getColorCode().equals("white"))
            {
                holder.textViewRecordColorCode.setText("White Code");
                holder.textViewRecordColorCode.setTextColor(Color.DKGRAY);
            }else{
                holder.textViewRecordColorCode.setText("");
            }
        }

        @Override
        public int getItemCount() {
            return listP.size();
        }

        public class MyViewHolder extends RecyclerView.ViewHolder{
            public TextView txtViewRecordYear, txtViewRecordMonth, txtViewRecordStatus, textViewRecordColorCode;

            public MyViewHolder(View view)
            {
                super(view);
                txtViewRecordYear = (TextView)view.findViewById(R.id.txtViewRecordYear);
                txtViewRecordMonth = (TextView)view.findViewById(R.id.txtViewRecordMonth);
                txtViewRecordStatus = (TextView)view.findViewById(R.id.txtViewRecordStatus);
                textViewRecordColorCode = (TextView)view.findViewById(R.id.textViewRecordColorCode);
            }
        }

        public MommyRecordAdapter(List<MommyHealthInfo> list)
        {
            listP = list;
        }
    }
}
