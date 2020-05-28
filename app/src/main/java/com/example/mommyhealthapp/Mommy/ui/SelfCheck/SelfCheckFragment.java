package com.example.mommyhealthapp.Mommy.ui.SelfCheck;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.mommyhealthapp.Class.AppointmentDate;
import com.example.mommyhealthapp.Class.Mommy;
import com.example.mommyhealthapp.Class.MommyDetail;
import com.example.mommyhealthapp.Mommy.DiaryActivity;
import com.example.mommyhealthapp.Mommy.KickCounterActivity;
import com.example.mommyhealthapp.Mommy.MotherGuidebook;
import com.example.mommyhealthapp.Mommy.PregnancyWeightGainActivity;
import com.example.mommyhealthapp.R;
import com.example.mommyhealthapp.SaveSharedPreference;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link SelfCheckFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link SelfCheckFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SelfCheckFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private TextView apptDate, appTtime, babyBday, countdownDate;
    private LinearLayoutCompat myDashboard;
    private String appointmentKey;
    private Date targetDate;
    private Boolean isEmpty;
    private FirebaseFirestore mFirebaseFirestore;
    private CollectionReference mCollectionReference, nCollectionReference;
    private CardView kickCounterMain, cardViewPregnancyWeight, cardViewBabyDiary, cardViewGuidebook;
    private ProgressBar progressBarDiary;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public SelfCheckFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SelfCheckFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SelfCheckFragment newInstance(String param1, String param2) {
        SelfCheckFragment fragment = new SelfCheckFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_self_check, container, false);

        progressBarDiary = (ProgressBar)v.findViewById(R.id.progressBarDiary);
        myDashboard = (LinearLayoutCompat)v.findViewById(R.id.myDashboard);
        apptDate = (TextView)v.findViewById(R.id.apptDate);
        appTtime = (TextView)v.findViewById(R.id.apptTime);
        babyBday = (TextView)v.findViewById(R.id.babyBday);
        countdownDate = (TextView)v.findViewById(R.id.countdownDate);

        String mommyID = SaveSharedPreference.getID(getActivity());
        mFirebaseFirestore = FirebaseFirestore.getInstance();
        myDashboard.setVisibility(View.GONE);
        progressBarDiary.setVisibility(View.VISIBLE);
        mCollectionReference = mFirebaseFirestore.collection("Mommy/"+mommyID+"/AppointmentDate");
        mCollectionReference.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                if(queryDocumentSnapshots.isEmpty())
                {
                    isEmpty = true;
                    apptDate.setText("No appointment has been made yet");
                    appTtime.setText("");
                }else{
                    isEmpty = false;
                    for(QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots)
                    {
                        appointmentKey = documentSnapshot.getId();
                        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
                        AppointmentDate ad = documentSnapshot.toObject(AppointmentDate.class);
                        apptDate.setText(dateFormat.format(ad.getAppointmentDate()));
                        appTtime.setText(timeFormat.format(ad.getAppointmentTime()));
                    }
                }
            }
        });

        nCollectionReference = mFirebaseFirestore.collection("Mommy").document(mommyID).collection("MommyDetail");
        nCollectionReference.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots)
                {
                    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                    MommyDetail mommyDetail = documentSnapshot.toObject(MommyDetail.class);
                    babyBday.setText(dateFormat.format(mommyDetail.getEdd()));
                    targetDate = mommyDetail.getEdd();
                    Log.i("titleTD",targetDate+"");
                }
                Date today = new Date();
                long diff = targetDate.getTime() - today.getTime();
                int diffDays = (int)(diff / (24 * 60 * 60 * 1000) + 1);
                countdownDate.setText(diffDays + "");
                myDashboard.setVisibility(View.VISIBLE);
                progressBarDiary.setVisibility(View.GONE);

            }
        });

        kickCounterMain = (CardView)v.findViewById(R.id.kickCounterMain);
        cardViewPregnancyWeight = (CardView)v.findViewById(R.id.cardViewPregnancyWeight);
        cardViewBabyDiary = (CardView)v.findViewById(R.id.cardViewBabyDiary);
        cardViewGuidebook = (CardView)v.findViewById(R.id.cardViewGuidebook);

        kickCounterMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), KickCounterActivity.class);
                startActivity(intent);
            }
        });
        cardViewPregnancyWeight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), PregnancyWeightGainActivity.class);
                startActivity(intent);
            }
        });
        cardViewBabyDiary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), DiaryActivity.class);
                startActivity(intent);
            }
        });
        cardViewGuidebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MotherGuidebook.class);
                startActivity(intent);
            }
        });
        return v;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
