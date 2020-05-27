package com.example.mommyhealthapp.Nurse;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.SearchManager;
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
import android.widget.SearchView;
import android.widget.TextView;

import com.example.mommyhealthapp.Class.KickCount;
import com.example.mommyhealthapp.Class.Mommy;
import com.example.mommyhealthapp.Class.PreviousPregnant;
import com.example.mommyhealthapp.MainActivity;
import com.example.mommyhealthapp.R;
import com.example.mommyhealthapp.SaveSharedPreference;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class BabyKickRecordActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {
    private RecyclerView recyclerViewBabyKick;
    private ImageView imgViewBabyKickNoRecord;
    private TextView txtViewBabyKickNoRecord;
    private ProgressBar progressBarBabyKickRecord;
    private SearchView searchViewBabyKick;

    private BabyKickRecordAdapter adapter;
    private List<KickCount> kickCountList;
    private FirebaseFirestore mFirebaseFirestore;
    private CollectionReference mCollectionReference, nCollectionReference;

    private String healthInfoId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_baby_kick_record);

        recyclerViewBabyKick = (RecyclerView)findViewById(R.id.recyclerViewBabyKick);
        imgViewBabyKickNoRecord = (ImageView)findViewById(R.id.imgViewBabyKickNoRecord);
        txtViewBabyKickNoRecord = (TextView)findViewById(R.id.txtViewBabyKickNoRecord);
        progressBarBabyKickRecord = (ProgressBar)findViewById(R.id.progressBarBabyKickRecord);
        searchViewBabyKick = (SearchView)findViewById(R.id.searchViewBabyKick);

        kickCountList = new ArrayList<>();
        progressBarBabyKickRecord.setVisibility(View.VISIBLE);
        recyclerViewBabyKick.setVisibility(View.GONE);
        imgViewBabyKickNoRecord.setVisibility(View.GONE);
        txtViewBabyKickNoRecord.setVisibility(View.GONE);

        SearchManager searchManager = (SearchManager)BabyKickRecordActivity.this.getSystemService(Context.SEARCH_SERVICE);
        searchViewBabyKick.setSearchableInfo(searchManager.getSearchableInfo(BabyKickRecordActivity.this.getComponentName()));
        searchViewBabyKick.setOnQueryTextListener(this);

        mFirebaseFirestore = FirebaseFirestore.getInstance();
        mCollectionReference = mFirebaseFirestore.collection("MommyHealthInfo");
        if(SaveSharedPreference.getUser(BabyKickRecordActivity.this).equals("Mommy")){
            MommyLogIn();
        }
        else
        {
            mCollectionReference.whereEqualTo("healthInfoId", SaveSharedPreference.getHealthInfoId(BabyKickRecordActivity.this)).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                @Override
                public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                    for(QueryDocumentSnapshot documentSnapshots: queryDocumentSnapshots)
                    {
                        healthInfoId = documentSnapshots.getId();
                    }
                    nCollectionReference = mFirebaseFirestore.collection("MommyHealthInfo").document(healthInfoId).collection("KickCount");
                    nCollectionReference.orderBy("babyKickNumber", Query.Direction.ASCENDING).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                        @Override
                        public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                            if(queryDocumentSnapshots.isEmpty())
                            {
                                progressBarBabyKickRecord.setVisibility(View.GONE);
                                recyclerViewBabyKick.setVisibility(View.GONE);
                                imgViewBabyKickNoRecord.setVisibility(View.VISIBLE);
                                txtViewBabyKickNoRecord.setVisibility(View.VISIBLE);
                            }else{
                                for(QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots)
                                {
                                    KickCount kc = documentSnapshot.toObject(KickCount.class);
                                    kickCountList.add(kc);
                                }
                                adapter = new BabyKickRecordAdapter(kickCountList);
                                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(BabyKickRecordActivity.this);
                                recyclerViewBabyKick.setLayoutManager(mLayoutManager);
                                recyclerViewBabyKick.setItemAnimator(new DefaultItemAnimator());
                                recyclerViewBabyKick.addItemDecoration(new DividerItemDecoration(BabyKickRecordActivity.this, LinearLayoutManager.VERTICAL));
                                recyclerViewBabyKick.setAdapter(adapter);
                                progressBarBabyKickRecord.setVisibility(View.GONE);
                                recyclerViewBabyKick.setVisibility(View.VISIBLE);
                                imgViewBabyKickNoRecord.setVisibility(View.GONE);
                                txtViewBabyKickNoRecord.setVisibility(View.GONE);
                            }
                        }
                    });
                }
            });
        }

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void MommyLogIn()
    {
        mCollectionReference.whereEqualTo("healthInfoId", SaveSharedPreference.getHealthInfoId(BabyKickRecordActivity.this)).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for(QueryDocumentSnapshot documentSnapshots: queryDocumentSnapshots)
                {
                    healthInfoId = documentSnapshots.getId();
                }
                nCollectionReference = mFirebaseFirestore.collection("MommyHealthInfo").document(healthInfoId).collection("KickCount");
                nCollectionReference.orderBy("babyKickNumber", Query.Direction.ASCENDING).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        if(queryDocumentSnapshots.isEmpty())
                        {
                            progressBarBabyKickRecord.setVisibility(View.GONE);
                            recyclerViewBabyKick.setVisibility(View.GONE);
                            imgViewBabyKickNoRecord.setVisibility(View.VISIBLE);
                            txtViewBabyKickNoRecord.setVisibility(View.VISIBLE);
                        }else{
                            for(QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots)
                            {
                                KickCount kc = documentSnapshot.toObject(KickCount.class);
                                kickCountList.add(kc);
                            }
                            adapter = new BabyKickRecordAdapter(kickCountList);
                            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(BabyKickRecordActivity.this);
                            recyclerViewBabyKick.setLayoutManager(mLayoutManager);
                            recyclerViewBabyKick.setItemAnimator(new DefaultItemAnimator());
                            recyclerViewBabyKick.addItemDecoration(new DividerItemDecoration(BabyKickRecordActivity.this, LinearLayoutManager.VERTICAL));
                            recyclerViewBabyKick.setAdapter(adapter);
                            progressBarBabyKickRecord.setVisibility(View.GONE);
                            recyclerViewBabyKick.setVisibility(View.VISIBLE);
                            imgViewBabyKickNoRecord.setVisibility(View.GONE);
                            txtViewBabyKickNoRecord.setVisibility(View.GONE);
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
                AlertDialog.Builder builder = new AlertDialog.Builder(BabyKickRecordActivity.this);
                builder.setTitle("Logout");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        SaveSharedPreference.clearUser(BabyKickRecordActivity.this);
                        Intent intent = new Intent(BabyKickRecordActivity.this, MainActivity.class);
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

    @Override
    public boolean onQueryTextSubmit(final String query) {
        progressBarBabyKickRecord.setVisibility(View.VISIBLE);
        kickCountList.clear();
        nCollectionReference = mFirebaseFirestore.collection("MommyHealthInfo").document(healthInfoId).collection("KickCount");
        nCollectionReference.orderBy("babyKickNumber", Query.Direction.ASCENDING).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                if(queryDocumentSnapshots.isEmpty())
                {
                    progressBarBabyKickRecord.setVisibility(View.GONE);
                    recyclerViewBabyKick.setVisibility(View.GONE);
                    imgViewBabyKickNoRecord.setVisibility(View.VISIBLE);
                    txtViewBabyKickNoRecord.setVisibility(View.VISIBLE);
                }else{
                    for(QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots)
                    {
                        KickCount kc = documentSnapshot.toObject(KickCount.class);
                        kickCountList.add(kc);
                    }
                    adapter = new BabyKickRecordAdapter(kickCountList);
                    adapter.searchFilter(query);
                    RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(BabyKickRecordActivity.this);
                    recyclerViewBabyKick.setLayoutManager(mLayoutManager);
                    recyclerViewBabyKick.setItemAnimator(new DefaultItemAnimator());
                    recyclerViewBabyKick.addItemDecoration(new DividerItemDecoration(BabyKickRecordActivity.this, LinearLayoutManager.VERTICAL));
                    recyclerViewBabyKick.setAdapter(adapter);
                    progressBarBabyKickRecord.setVisibility(View.GONE);
                    recyclerViewBabyKick.setVisibility(View.VISIBLE);
                    imgViewBabyKickNoRecord.setVisibility(View.GONE);
                    txtViewBabyKickNoRecord.setVisibility(View.GONE);
                }
            }
        });

        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        adapter.searchFilter(newText);
        recyclerViewBabyKick.setVisibility(View.VISIBLE);
        imgViewBabyKickNoRecord.setVisibility(View.GONE);
        txtViewBabyKickNoRecord.setVisibility(View.GONE);
        return false;
    }

    public class BabyKickRecordAdapter extends RecyclerView.Adapter<BabyKickRecordAdapter.MyViewHolder>{
        private List<KickCount> listP;
        private ArrayList<KickCount> arrayList;

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View resultView = LayoutInflater.from(parent.getContext()).inflate(R.layout.babykick_record_view, parent, false);
            return new MyViewHolder(resultView);
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, int position) {
            KickCount kc = listP.get(position);
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");

            if(kc.getLastKick() == 0 || kc.getLastKickDate() == null || kc.getLastKickTime() == null)
            {
                holder.txtViewKickCount.setText(kc.getFirstKick()+"");
                holder.txtViewLatestKickTime.setText(timeFormat.format(kc.getFirstKickTime()));
                holder.txtViewLatestKickDate.setText(dateFormat.format(kc.getFirstKickDate()));
            }else{
                holder.txtViewKickCount.setText(kc.getLastKick()+"");
                holder.txtViewLatestKickTime.setText(timeFormat.format(kc.getLastKickTime()));
                holder.txtViewLatestKickDate.setText(dateFormat.format(kc.getLastKickDate()));
            }
        }

        @Override
        public int getItemCount() {
            return listP.size();
        }

        public class MyViewHolder extends RecyclerView.ViewHolder{
            public TextView txtViewKickCount, txtViewLatestKickTime, txtViewLatestKickDate;

            public MyViewHolder(View view)
            {
                super(view);
                txtViewKickCount = (TextView)view.findViewById(R.id.txtViewKickCount);
                txtViewLatestKickTime = (TextView)view.findViewById(R.id.txtViewLatestKickTime);
                txtViewLatestKickDate = (TextView)view.findViewById(R.id.txtViewLatestKickDate);
            }
        }

        public BabyKickRecordAdapter(List<KickCount> list)
        {
            listP = list;
            this.arrayList = new ArrayList<KickCount>();
            this.arrayList.addAll(listP);
        }

        public void searchFilter(String text)
        {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
            text = text.toLowerCase(Locale.getDefault());
            listP.clear();
            if(text.length() == 0)
            {
                listP.addAll(arrayList);
            }else{
                for(KickCount kc : arrayList)
                {
                    if( kc.getLastKickDate() == null || kc.getLastKickTime() == null)
                    {
                        if(dateFormat.format(kc.getFirstKickDate()).contains(text) || timeFormat.format(kc.getFirstKickTime()).contains(text))
                        {
                            listP.add(kc);
                        }
                    }else{
                        if(dateFormat.format(kc.getLastKickDate()).contains(text) || timeFormat.format(kc.getLastKickTime()).contains(text))
                        {
                            listP.add(kc);
                        }
                    }
                }
            }
            notifyDataSetChanged();
        }
    }
}
