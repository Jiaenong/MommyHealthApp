package com.example.mommyhealthapp.Nurse.ui.SearchMother;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.mommyhealthapp.Class.Mommy;
import com.example.mommyhealthapp.Nurse.MommyRecordActivity;
import com.example.mommyhealthapp.Nurse.ScanQRCodeActivity;
import com.example.mommyhealthapp.R;
import com.example.mommyhealthapp.RecyclerTouchListener;
import com.example.mommyhealthapp.SaveSharedPreference;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.mikhaellopez.circularimageview.CircularImageView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.Executor;

public class SearchMotherFragment extends Fragment implements SearchView.OnQueryTextListener {

    private ImageView imageViewQRCode, imageViewNoRecordFound;
    private RecyclerView recycleViewMummy;
    private TextView textViewNoRecordFound;
    private FirebaseFirestore mFirebaseFirestore;
    private CollectionReference mCollectionReference;
    private SearchResultAdapter adapter;
    private List<Mommy> mommyList;
    private ProgressBar progressBarMummyRecord;
    private SearchView searchViewMother;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_search_mummy, container, false);
        imageViewQRCode = (ImageView)root.findViewById(R.id.imageViewQRCode);
        imageViewNoRecordFound = (ImageView)root.findViewById(R.id.imageViewNoRecordFound);
        recycleViewMummy = (RecyclerView)root.findViewById(R.id.recycleViewMummy);
        textViewNoRecordFound = (TextView)root.findViewById(R.id.textViewNoRecordFound);
        progressBarMummyRecord = (ProgressBar)root.findViewById(R.id.progressBarMummyRecord);
        searchViewMother = (SearchView)root.findViewById(R.id.searchViewMother);

        mommyList = new ArrayList<>();
        mFirebaseFirestore = FirebaseFirestore.getInstance();
        mCollectionReference = mFirebaseFirestore.collection("Mommy");

        progressBarMummyRecord.setVisibility(View.VISIBLE);
        imageViewNoRecordFound.setVisibility(View.GONE);
        textViewNoRecordFound.setVisibility(View.GONE);

        SearchManager searchManager = (SearchManager)getActivity().getSystemService(Context.SEARCH_SERVICE);
        searchViewMother.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));
        searchViewMother.setOnQueryTextListener(this);

        imageViewQRCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ScanQRCodeActivity.class);
                startActivity(intent);
            }
        });

        recycleViewMummy.addOnItemTouchListener(new RecyclerTouchListener(getActivity(), recycleViewMummy, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Mommy mommy = mommyList.get(position);
                SaveSharedPreference.setMummyId(getActivity(), mommy.getMommyId());
                Intent intent = new Intent(getActivity(), MommyRecordActivity.class);
                intent.putExtra("MommyID", mommy.getMommyId());
                startActivity(intent);
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

        mCollectionReference.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                mommyList.clear();
                for(QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots)
                {
                    Mommy mommy = documentSnapshot.toObject(Mommy.class);
                    mommyList.add(mommy);
                }
                adapter  = new SearchResultAdapter(mommyList);
                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
                recycleViewMummy.setLayoutManager(mLayoutManager);
                recycleViewMummy.setItemAnimator(new DefaultItemAnimator());
                recycleViewMummy.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));
                recycleViewMummy.setAdapter(adapter);
                if(mommyList.isEmpty())
                {
                    imageViewNoRecordFound.setVisibility(View.VISIBLE);
                    textViewNoRecordFound.setVisibility(View.VISIBLE);
                    recycleViewMummy.setVisibility(View.GONE);
                    progressBarMummyRecord.setVisibility(View.GONE);
                }else{
                    imageViewNoRecordFound.setVisibility(View.GONE);
                    textViewNoRecordFound.setVisibility(View.GONE);
                    recycleViewMummy.setVisibility(View.VISIBLE);
                    progressBarMummyRecord.setVisibility(View.GONE);
                }
            }
        });

        /*mCollectionReference.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for(QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots)
                {
                    Mommy mommy = documentSnapshot.toObject(Mommy.class);
                    mommyList.add(mommy);
                }
                adapter  = new SearchResultAdapter(mommyList);
                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
                recycleViewMummy.setLayoutManager(mLayoutManager);
                recycleViewMummy.setItemAnimator(new DefaultItemAnimator());
                recycleViewMummy.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));
                recycleViewMummy.setAdapter(adapter);
                if(mommyList.isEmpty())
                {
                    imageViewNoRecordFound.setVisibility(View.VISIBLE);
                    textViewNoRecordFound.setVisibility(View.VISIBLE);
                    recycleViewMummy.setVisibility(View.GONE);
                    progressBarMummyRecord.setVisibility(View.GONE);
                }else{
                    imageViewNoRecordFound.setVisibility(View.GONE);
                    textViewNoRecordFound.setVisibility(View.GONE);
                    recycleViewMummy.setVisibility(View.VISIBLE);
                    progressBarMummyRecord.setVisibility(View.GONE);
                }
            }
        });*/

        return root;
    }

    @Override
    public boolean onQueryTextSubmit(final String query) {
        Log.i("Testing", "test");
        progressBarMummyRecord.setVisibility(View.VISIBLE);
        mommyList.clear();
        mCollectionReference.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for(QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots)
                {
                    Mommy mommy = documentSnapshot.toObject(Mommy.class);
                    mommyList.add(mommy);
                }
                adapter = new SearchResultAdapter(mommyList);
                adapter.searchFilter(query);
                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
                recycleViewMummy.setLayoutManager(mLayoutManager);
                recycleViewMummy.setItemAnimator(new DefaultItemAnimator());
                recycleViewMummy.setAdapter(adapter);
                if(mommyList.isEmpty())
                {
                    imageViewNoRecordFound.setVisibility(View.VISIBLE);
                    textViewNoRecordFound.setVisibility(View.VISIBLE);
                    recycleViewMummy.setVisibility(View.GONE);
                    progressBarMummyRecord.setVisibility(View.GONE);
                }else{
                    imageViewNoRecordFound.setVisibility(View.GONE);
                    textViewNoRecordFound.setVisibility(View.GONE);
                    recycleViewMummy.setVisibility(View.VISIBLE);
                    progressBarMummyRecord.setVisibility(View.GONE);
                }
            }
        });
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        Log.i("Testing", "test");
        adapter.searchFilter(newText);
        imageViewNoRecordFound.setVisibility(View.GONE);
        textViewNoRecordFound.setVisibility(View.GONE);
        recycleViewMummy.setVisibility(View.VISIBLE);
        return false;
    }

    public class SearchResultAdapter extends RecyclerView.Adapter<SearchResultAdapter.MyViewHolder>{
        private List<Mommy> listP;
        private ArrayList<Mommy> arrayList;

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View resultView = LayoutInflater.from(parent.getContext()).inflate(R.layout.mummy_record_view, parent, false);
            return new MyViewHolder(resultView);
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, int position) {
            Mommy mommy = listP.get(position);
            holder.textViewName.setText(mommy.getMommyName());
            holder.textViewAge.setText(mommy.getAge()+"");
            holder.textViewId.setText(mommy.getMommyId());
            if(mommy.getStatus().equals("Active"))
            {
                holder.textViewStatusActive.setText(mommy.getStatus());
                holder.textViewStatusActive.setTextColor(Color.parseColor("#008000"));
            }else if(mommy.getStatus().equals("Inactive"))
            {
                holder.textViewStatusActive.setText(mommy.getStatus());
                holder.textViewStatusActive.setTextColor(Color.parseColor("#FF0000"));
            }
            if(mommy.getColorCode().equals("red"))
            {
                holder.textViewColorCode.setText("Red Code");
                holder.textViewColorCode.setTextColor(Color.RED);
            }else if(mommy.getColorCode().equals("yellow"))
            {
                holder.textViewColorCode.setText("Yellow Code");
                holder.textViewColorCode.setTextColor(Color.YELLOW);
            }else if(mommy.getColorCode().equals("green"))
            {
                holder.textViewColorCode.setText("Green Code");
                holder.textViewColorCode.setTextColor(Color.GREEN);
            }else if(mommy.getColorCode().equals("white"))
            {
                holder.textViewColorCode.setText("White Code");
                holder.textViewColorCode.setTextColor(Color.DKGRAY);
            }else{
                holder.textViewColorCode.setText("");
            }

            if(mommy.getMummyImage().equals(""))
            {
                holder.imageViewMummyPic.setImageResource(R.drawable.user);
            }else{
                Glide.with(getActivity()).load(mommy.getMummyImage()).into(holder.imageViewMummyPic);
            }
        }

        @Override
        public int getItemCount() {
            return listP.size();
        }

        public class MyViewHolder extends RecyclerView.ViewHolder{
            public TextView textViewName, textViewAge, textViewId, textViewStatusActive, textViewColorCode;
            public CircularImageView imageViewMummyPic;

            public MyViewHolder(View view)
            {
                super(view);
                textViewName = (TextView)view.findViewById(R.id.textViewName);
                textViewAge = (TextView)view.findViewById(R.id.textViewAge);
                textViewId = (TextView)view.findViewById(R.id.textViewId);
                textViewStatusActive = (TextView)view.findViewById(R.id.textViewStatusActive);
                textViewColorCode = (TextView)view.findViewById(R.id.textViewColorCode);
                imageViewMummyPic = (CircularImageView)view.findViewById(R.id.imageViewMummyPic);
            }
        }

        public SearchResultAdapter(List<Mommy> list)
        {
            listP = list;
            this.arrayList = new ArrayList<Mommy>();
            this.arrayList.addAll(listP);
        }

        public void searchFilter(String text)
        {
            text = text.toLowerCase(Locale.getDefault());
            listP.clear();
            if(text.length() == 0)
            {
                listP.addAll(arrayList);
            }else{
                for(Mommy mommy : arrayList)
                {
                    if(mommy.getMommyName().toLowerCase(Locale.getDefault()).contains(text))
                    {
                        listP.add(mommy);
                    }
                }
            }
            notifyDataSetChanged();
        }
    }
}