package com.example.mommyhealthapp.Nurse;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.mommyhealthapp.Class.Mommy;
import com.example.mommyhealthapp.Class.MommyHealthInfo;
import com.example.mommyhealthapp.Nurse.MommyProfileActivity;
import com.example.mommyhealthapp.Nurse.ui.SearchMother.SearchMotherFragment;
import com.example.mommyhealthapp.R;
import com.example.mommyhealthapp.RecyclerTouchListener;
import com.example.mommyhealthapp.SaveSharedPreference;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
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
    private CollectionReference mCollectionReference;

    private List<MommyHealthInfo> mommyRecordList;
    private String id;
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

            }
        }));

        progressBarMommyRecord.setVisibility(View.VISIBLE);
        recycleViewMommyRecord.setVisibility(View.GONE);
        imgViewNoRecordFound.setVisibility(View.GONE);
        txtViewNoRecordFound.setVisibility(View.GONE);
        btnAddNewRecord.setVisibility(View.GONE);
        mCollectionReference.whereEqualTo("mommyId", id).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
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
            holder.txtViewRecordStatus.setText(mhi.getStatus());
        }

        @Override
        public int getItemCount() {
            return listP.size();
        }

        public class MyViewHolder extends RecyclerView.ViewHolder{
            public TextView txtViewRecordYear, txtViewRecordMonth, txtViewRecordStatus;

            public MyViewHolder(View view)
            {
                super(view);
                txtViewRecordYear = (TextView)view.findViewById(R.id.txtViewRecordYear);
                txtViewRecordMonth = (TextView)view.findViewById(R.id.txtViewRecordMonth);
                txtViewRecordStatus = (TextView)view.findViewById(R.id.txtViewRecordStatus);
            }
        }

        public MommyRecordAdapter(List<MommyHealthInfo> list)
        {
            listP = list;
        }
    }
}
