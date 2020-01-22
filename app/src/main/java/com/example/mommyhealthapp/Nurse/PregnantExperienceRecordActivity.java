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

import com.example.mommyhealthapp.Class.MommyHealthInfo;
import com.example.mommyhealthapp.Class.PreviousPregnant;
import com.example.mommyhealthapp.R;
import com.example.mommyhealthapp.RecyclerTouchListener;
import com.example.mommyhealthapp.SaveSharedPreference;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class PregnantExperienceRecordActivity extends AppCompatActivity {
    private RecyclerView recyclerViewPPRecord;
    private FloatingActionButton btnAddPPRecord;
    private ImageView imgViewPPNoRecordFound;
    private TextView txtViewPPNoRecordFound;
    private ProgressBar progressBarPPRecord;

    private FirebaseFirestore mFirebaseFirestore;
    private CollectionReference mCollectionReference;

    private List<PreviousPregnant> ppList;
    private String healthInfoId, bloodTestId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pregnant_experience_record);

        recyclerViewPPRecord = (RecyclerView)findViewById(R.id.recyclerViewPPRecord);
        btnAddPPRecord = (FloatingActionButton)findViewById(R.id.btnAddPPERecord);
        imgViewPPNoRecordFound = (ImageView)findViewById(R.id.imgViewPPNoRecordFound);
        txtViewPPNoRecordFound = (TextView)findViewById(R.id.txtViewPPNoRecordFound);
        progressBarPPRecord = (ProgressBar)findViewById(R.id.progressBarPPRecord);

        ppList = new ArrayList<>();
        Intent intent = getIntent();
        healthInfoId = intent.getStringExtra("healthInfoId");
        bloodTestId = intent.getStringExtra("bloodTestId");
        mFirebaseFirestore = FirebaseFirestore.getInstance();
        mCollectionReference = mFirebaseFirestore.collection("MommyHealthInfo/"+healthInfoId+"/BloodTest/"+bloodTestId+"/PreviousPregnant");

        progressBarPPRecord.setVisibility(View.VISIBLE);
        imgViewPPNoRecordFound.setVisibility(View.GONE);
        txtViewPPNoRecordFound.setVisibility(View.GONE);
        btnAddPPRecord.setVisibility(View.GONE);

        recyclerViewPPRecord.addOnItemTouchListener(new RecyclerTouchListener(PregnantExperienceRecordActivity.this, recyclerViewPPRecord, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                SaveSharedPreference.setPreviousPregnant(PregnantExperienceRecordActivity.this, "Exist");
                PreviousPregnant pp = ppList.get(position);
                Intent intent = new Intent(PregnantExperienceRecordActivity.this, SectionAActivity.class);
                intent.putExtra("healthInfoId", healthInfoId);
                intent.putExtra("bloodTestId", bloodTestId);
                intent.putExtra("ppId", pp.getPreviousPregnantId());
                startActivity(intent);
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

        mCollectionReference.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for(QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots)
                {
                    PreviousPregnant pp = documentSnapshot.toObject(PreviousPregnant.class);
                    ppList.add(pp);
                }
                PregnantExperienceAdapter adapter = new PregnantExperienceAdapter(ppList);
                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(PregnantExperienceRecordActivity.this);
                recyclerViewPPRecord.setLayoutManager(mLayoutManager);
                recyclerViewPPRecord.setItemAnimator(new DefaultItemAnimator());
                recyclerViewPPRecord.addItemDecoration(new DividerItemDecoration(PregnantExperienceRecordActivity.this, LinearLayoutManager.VERTICAL));
                recyclerViewPPRecord.setAdapter(adapter);
                progressBarPPRecord.setVisibility(View.GONE);
                if(ppList.isEmpty())
                {
                    recyclerViewPPRecord.setVisibility(View.GONE);
                    imgViewPPNoRecordFound.setVisibility(View.VISIBLE);
                    txtViewPPNoRecordFound.setVisibility(View.VISIBLE);
                    btnAddPPRecord.setVisibility(View.VISIBLE);
                }else{
                    recyclerViewPPRecord.setVisibility(View.VISIBLE);
                    imgViewPPNoRecordFound.setVisibility(View.GONE);
                    txtViewPPNoRecordFound.setVisibility(View.GONE);
                    btnAddPPRecord.setVisibility(View.VISIBLE);
                }
            }
        });

        btnAddPPRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SaveSharedPreference.setPreviousPregnant(PregnantExperienceRecordActivity.this, "New");
                Intent intent = new Intent(PregnantExperienceRecordActivity.this, SectionAActivity.class);
                intent.putExtra("healthInfoId", healthInfoId);
                intent.putExtra("bloodTestId", bloodTestId);
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

    public class PregnantExperienceAdapter extends RecyclerView.Adapter<PregnantExperienceAdapter.MyViewHolder>{
        private List<PreviousPregnant> listP;

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View resultView = LayoutInflater.from(parent.getContext()).inflate(R.layout.pregnant_experience_view, parent, false);
            return new MyViewHolder(resultView);
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, int position) {
            PreviousPregnant pp = listP.get(position);
            holder.txtViewPPRecordYear.setText(pp.getYear());
            holder.txtViewPPRecordGender.setText(pp.getGender());
        }

        @Override
        public int getItemCount() {
            return listP.size();
        }

        public class MyViewHolder extends RecyclerView.ViewHolder{
            public TextView txtViewPPRecordYear, txtViewPPRecordGender;

            public MyViewHolder(View view)
            {
                super(view);
                txtViewPPRecordYear = (TextView)view.findViewById(R.id.txtViewPPRecordYear);
                txtViewPPRecordGender = (TextView)view.findViewById(R.id.txtViewPPRecordGender);
            }
        }

        public PregnantExperienceAdapter(List<PreviousPregnant> list)
        {
            listP = list;
        }
    }
}
