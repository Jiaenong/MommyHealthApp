package com.example.mommyhealthapp.Nurse;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.example.mommyhealthapp.Class.SecondTrimester;
import com.example.mommyhealthapp.R;
import com.example.mommyhealthapp.SaveSharedPreference;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class SecondTrimesterFragment extends Fragment {
    private RelativeLayout relativeLayoutSecondTrimester;
    private ProgressBar progressBarSecondTri;
    private LinearLayoutCompat layoutST, layoutSecondRedCode, layoutSecondYellowCode, layoutSecondGreenCode, layoutSecondWhiteCode;
    private Button btnSTSave, btnSTCancel;

    private String healthInfoId, mommyKey, key;
    private int check = 0;
    private Boolean isEmpty;

    private FirebaseFirestore mFirebaseFirestore;
    private CollectionReference mCollectionReference, nCollectionReference, pCollectionReference;

    public SecondTrimesterFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View v = inflater.inflate(R.layout.fragment_second_trimester, container, false);

        relativeLayoutSecondTrimester = (RelativeLayout)v.findViewById(R.id.relativeLayoutSecondTrimester);
        progressBarSecondTri = (ProgressBar)v.findViewById(R.id.progressBarSecondTri);
        layoutST = (LinearLayoutCompat)v.findViewById(R.id.layoutST);
        layoutSecondRedCode = (LinearLayoutCompat)v.findViewById(R.id.layoutSecondRedCode);
        layoutSecondYellowCode = (LinearLayoutCompat)v.findViewById(R.id.layoutSecondYellowCode);
        layoutSecondGreenCode = (LinearLayoutCompat)v.findViewById(R.id.layoutSecondGreenCode);
        layoutSecondWhiteCode = (LinearLayoutCompat)v.findViewById(R.id.layoutSecondWhiteCode);
        btnSTSave = (Button)v.findViewById(R.id.btnSTSave);
        btnSTCancel = (Button)v.findViewById(R.id.btnSTCancel);

        progressBarSecondTri.setVisibility(View.VISIBLE);
        layoutST.setVisibility(View.GONE);
        btnSTCancel.setVisibility(View.GONE);

        mFirebaseFirestore = FirebaseFirestore.getInstance();
        pCollectionReference = mFirebaseFirestore.collection("Mommy");
        pCollectionReference.whereEqualTo("mommyId", SaveSharedPreference.getMummyId(getActivity())).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for(QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots)
                {
                    mommyKey = documentSnapshot.getId();
                }
            }
        });
        mCollectionReference = mFirebaseFirestore.collection("MommyHealthInfo");
        if(SaveSharedPreference.getUser(getActivity()).equals("Mommy"))
        {
            MommyLogIn(v);
        }else{
            mCollectionReference.whereEqualTo("healthInfoId", SaveSharedPreference.getHealthInfoId(getActivity())).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                @Override
                public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                    for(QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots)
                    {
                        healthInfoId = documentSnapshot.getId();
                    }
                    nCollectionReference = mFirebaseFirestore.collection("MommyHealthInfo").document(healthInfoId).collection("SecondTrimester");
                    nCollectionReference.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                        @Override
                        public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                            if(queryDocumentSnapshots.isEmpty())
                            {
                                isEmpty = true;
                                progressBarSecondTri.setVisibility(View.GONE);
                                layoutST.setVisibility(View.VISIBLE);
                            }else{
                                isEmpty = false;
                                DisableField();
                                for(QueryDocumentSnapshot documentSnapshots : queryDocumentSnapshots)
                                {
                                    key = documentSnapshots.getId();
                                    SecondTrimester st = documentSnapshots.toObject(SecondTrimester.class);
                                    for(int i=0; i<st.getRedCode().size(); i++)
                                    {
                                        CheckBox checkBox = (CheckBox)v.findViewById(Integer.parseInt(st.getRedCode().get(i)));
                                        checkBox.setChecked(true);
                                    }
                                    for(int i=0; i<st.getYellowCode().size(); i++)
                                    {
                                        CheckBox checkBox = (CheckBox)v.findViewById(Integer.parseInt(st.getYellowCode().get(i)));
                                        checkBox.setChecked(true);
                                    }
                                    for(int i=0; i<st.getGreenCode().size(); i++)
                                    {
                                        CheckBox checkBox = (CheckBox)v.findViewById(Integer.parseInt(st.getGreenCode().get(i)));
                                        checkBox.setChecked(true);
                                    }
                                    for(int i=0; i<st.getWhiteCode().size(); i++)
                                    {
                                        CheckBox checkBox = (CheckBox)v.findViewById(Integer.parseInt(st.getWhiteCode().get(i)));
                                        checkBox.setChecked(true);
                                    }
                                }
                                progressBarSecondTri.setVisibility(View.GONE);
                                layoutST.setVisibility(View.VISIBLE);
                            }
                        }
                    });
                }
            });
        }

        btnSTSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isEmpty == true)
                {
                    List<String> red = new ArrayList<>();
                    List<String> yellow = new ArrayList<>();
                    List<String> green = new ArrayList<>();
                    List<String> white = new ArrayList<>();
                    String medicalPersonnelId = SaveSharedPreference.getID(getContext());
                    Date createdDate = new Date();
                    for(int i=0; i<layoutSecondRedCode.getChildCount(); i++)
                    {
                        View view = layoutSecondRedCode.getChildAt(i);
                        if(view instanceof RelativeLayout)
                        {
                            for(int n=0; n<((RelativeLayout) view).getChildCount(); n++)
                            {
                                View views = ((RelativeLayout) view).getChildAt(n);
                                if(views instanceof CheckBox)
                                {
                                    if(((CheckBox)views).isChecked())
                                    {
                                        red.add(views.getId()+"");
                                    }
                                }
                            }
                        }
                    }
                    for(int i=0; i<layoutSecondYellowCode.getChildCount(); i++)
                    {
                        View view = layoutSecondYellowCode.getChildAt(i);
                        if(view instanceof RelativeLayout)
                        {
                            for(int n=0; n<((RelativeLayout) view).getChildCount(); n++)
                            {
                                View views = ((RelativeLayout) view).getChildAt(n);
                                if(views instanceof CheckBox)
                                {
                                    if(((CheckBox)views).isChecked())
                                    {
                                        yellow.add(views.getId()+"");
                                    }
                                }
                            }
                        }
                    }
                    for(int i=0; i<layoutSecondGreenCode.getChildCount(); i++)
                    {
                        View view = layoutSecondGreenCode.getChildAt(i);
                        if(view instanceof RelativeLayout)
                        {
                            for(int n=0; n<((RelativeLayout) view).getChildCount(); n++)
                            {
                                View views = ((RelativeLayout) view).getChildAt(n);
                                if(views instanceof CheckBox)
                                {
                                    if(((CheckBox)views).isChecked())
                                    {
                                        green.add(views.getId()+"");
                                    }
                                }
                            }
                        }
                    }
                    for(int i=0; i<layoutSecondWhiteCode.getChildCount(); i++)
                    {
                        View view = layoutSecondWhiteCode.getChildAt(i);
                        if(view instanceof RelativeLayout)
                        {
                            for(int n=0; n<((RelativeLayout) view).getChildCount(); n++)
                            {
                                View views = ((RelativeLayout) view).getChildAt(n);
                                if(views instanceof CheckBox)
                                {
                                    if(((CheckBox)views).isChecked())
                                    {
                                        white.add(views.getId()+"");
                                    }
                                }
                            }
                        }
                    }
                    SecondTrimester st = new SecondTrimester(red, yellow, green, white, medicalPersonnelId, createdDate);
                    final String finalColorCode = DetermineColorCode(red, yellow, green, white);
                    Boolean emptyFeild = DetermineEmptyCheckBox(red, yellow, green, white);
                    if(emptyFeild == false)
                    {
                        nCollectionReference.add(st).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {
                                DocumentReference mDocumentReference = mFirebaseFirestore.collection("Mommy").document(mommyKey);
                                DocumentReference nDocumentReference = mFirebaseFirestore.collection("MommyHealthInfo").document(healthInfoId);
                                mDocumentReference.update("colorCode", finalColorCode);
                                nDocumentReference.update("colorCode", finalColorCode);
                                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                                builder.setTitle("Save Successfully");
                                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        getActivity().finish();
                                    }
                                });
                                builder.setMessage("Save Successful!");
                                AlertDialog alert = builder.create();
                                alert.setCanceledOnTouchOutside(false);
                                alert.show();
                            }
                        });
                    }else{
                        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                        builder.setTitle("Error");
                        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                return;
                            }
                        });
                        builder.setMessage("At least one check box must be checked!");
                        AlertDialog alert = builder.create();
                        alert.show();
                    }
                }else{
                    check++;
                    if(check == 1)
                    {
                        btnSTCancel.setVisibility(View.VISIBLE);
                        EnableField();
                    }else if(check == 2)
                    {
                        DocumentReference mDocumentReference = mFirebaseFirestore.collection("MommyHealthInfo").document(healthInfoId).collection("SecondTrimester").document(key);
                        DocumentReference nDocumentReference = mFirebaseFirestore.collection("Mommy").document(mommyKey);
                        DocumentReference pDocumentReference = mFirebaseFirestore.collection("MommyHealthInfo").document(healthInfoId);
                        List<String> red = new ArrayList<>();
                        List<String> yellow = new ArrayList<>();
                        List<String> green = new ArrayList<>();
                        List<String> white = new ArrayList<>();
                        String medicalPersonnelId = SaveSharedPreference.getID(getContext());
                        Date createdDate = new Date();
                        for(int i=0; i<layoutSecondRedCode.getChildCount(); i++)
                        {
                            View view = layoutSecondRedCode.getChildAt(i);
                            if(view instanceof RelativeLayout)
                            {
                                for(int n=0; n<((RelativeLayout) view).getChildCount(); n++)
                                {
                                    View views = ((RelativeLayout) view).getChildAt(n);
                                    if(views instanceof CheckBox)
                                    {
                                        if(((CheckBox)views).isChecked())
                                        {
                                            red.add(views.getId()+"");
                                        }
                                    }
                                }
                            }
                        }
                        for(int i=0; i<layoutSecondYellowCode.getChildCount(); i++)
                        {
                            View view = layoutSecondYellowCode.getChildAt(i);
                            if(view instanceof RelativeLayout)
                            {
                                for(int n=0; n<((RelativeLayout) view).getChildCount(); n++)
                                {
                                    View views = ((RelativeLayout) view).getChildAt(n);
                                    if(views instanceof CheckBox)
                                    {
                                        if(((CheckBox)views).isChecked())
                                        {
                                            yellow.add(views.getId()+"");
                                        }
                                    }
                                }
                            }
                        }
                        for(int i=0; i<layoutSecondGreenCode.getChildCount(); i++)
                        {
                            View view = layoutSecondGreenCode.getChildAt(i);
                            if(view instanceof RelativeLayout)
                            {
                                for(int n=0; n<((RelativeLayout) view).getChildCount(); n++)
                                {
                                    View views = ((RelativeLayout) view).getChildAt(n);
                                    if(views instanceof CheckBox)
                                    {
                                        if(((CheckBox)views).isChecked())
                                        {
                                            green.add(views.getId()+"");
                                        }
                                    }
                                }
                            }
                        }
                        for(int i=0; i<layoutSecondWhiteCode.getChildCount(); i++)
                        {
                            View view = layoutSecondWhiteCode.getChildAt(i);
                            if(view instanceof RelativeLayout)
                            {
                                for(int n=0; n<((RelativeLayout) view).getChildCount(); n++)
                                {
                                    View views = ((RelativeLayout) view).getChildAt(n);
                                    if(views instanceof CheckBox)
                                    {
                                        if(((CheckBox)views).isChecked())
                                        {
                                            white.add(views.getId()+"");
                                        }
                                    }
                                }
                            }
                        }
                        String colorCode = DetermineColorCode(red, yellow, green, white);
                        Boolean emptyField = DetermineEmptyCheckBox(red, yellow, green, white);
                        if(emptyField == false)
                        {
                            nDocumentReference.update("colorCode", colorCode);
                            pDocumentReference.update("colorCode", colorCode);
                            mDocumentReference.update("redCode", red);
                            mDocumentReference.update("yellowCode", yellow);
                            mDocumentReference.update("greenCode", green);
                            mDocumentReference.update("whiteCode", white);
                            mDocumentReference.update("medicalPersonnelId", medicalPersonnelId);
                            mDocumentReference.update("createdDate", createdDate);
                            Snackbar snackbar = Snackbar.make(relativeLayoutSecondTrimester, "Updated Successfully!", Snackbar.LENGTH_LONG);
                            snackbar.show();
                            check = 0;
                            btnSTCancel.setVisibility(View.GONE);
                            DisableField();
                        }else{
                            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                            builder.setTitle("Error");
                            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    return;
                                }
                            });
                            builder.setMessage("At least one check box must be checked!");
                            AlertDialog alert = builder.create();
                            alert.show();
                            check = 1;
                        }
                    }
                }
            }
        });

        btnSTCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnSTCancel.setVisibility(View.GONE);
                check = 0;
                DisableField();
            }
        });

        return v;
    }

    private void MommyLogIn(final View v)
    {
        mCollectionReference.whereEqualTo("healthInfoId", SaveSharedPreference.getHealthInfoId(getActivity())).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for(QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots)
                {
                    healthInfoId = documentSnapshot.getId();
                }
                nCollectionReference = mFirebaseFirestore.collection("MommyHealthInfo").document(healthInfoId).collection("SecondTrimester");
                nCollectionReference.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        if(queryDocumentSnapshots.isEmpty())
                        {
                            isEmpty = true;
                            progressBarSecondTri.setVisibility(View.GONE);
                            layoutST.setVisibility(View.VISIBLE);
                            btnSTSave.setVisibility(View.GONE);
                            btnSTCancel.setVisibility(View.GONE);
                        }else{
                            isEmpty = false;
                            DisableField();
                            for(QueryDocumentSnapshot documentSnapshots : queryDocumentSnapshots)
                            {
                                key = documentSnapshots.getId();
                                SecondTrimester st = documentSnapshots.toObject(SecondTrimester.class);
                                for(int i=0; i<st.getRedCode().size(); i++)
                                {
                                    CheckBox checkBox = (CheckBox)v.findViewById(Integer.parseInt(st.getRedCode().get(i)));
                                    checkBox.setChecked(true);
                                }
                                for(int i=0; i<st.getYellowCode().size(); i++)
                                {
                                    CheckBox checkBox = (CheckBox)v.findViewById(Integer.parseInt(st.getYellowCode().get(i)));
                                    checkBox.setChecked(true);
                                }
                                for(int i=0; i<st.getGreenCode().size(); i++)
                                {
                                    CheckBox checkBox = (CheckBox)v.findViewById(Integer.parseInt(st.getGreenCode().get(i)));
                                    checkBox.setChecked(true);
                                }
                                for(int i=0; i<st.getWhiteCode().size(); i++)
                                {
                                    CheckBox checkBox = (CheckBox)v.findViewById(Integer.parseInt(st.getWhiteCode().get(i)));
                                    checkBox.setChecked(true);
                                }
                            }
                            progressBarSecondTri.setVisibility(View.GONE);
                            layoutST.setVisibility(View.VISIBLE);
                            btnSTSave.setVisibility(View.GONE);
                            btnSTCancel.setVisibility(View.GONE);
                        }
                    }
                });
            }
        });
    }

    private Boolean DetermineEmptyCheckBox(List<String> red, List<String> yellow, List<String> green, List<String> white)
    {
        if(red.isEmpty() && yellow.isEmpty() && green.isEmpty() && white.isEmpty())
        {
            return true;
        }else{
            return false;
        }
    }

    private String DetermineColorCode(List<String> red, List<String> yellow, List<String> green, List<String> white)
    {
        String colorCode = "";
        if(!red.isEmpty())
        {
            colorCode = "red";
        }else {
            if(!yellow.isEmpty())
            {
                colorCode = "yellow";
            }else{
                if(!green.isEmpty())
                {
                    colorCode = "green";
                }else{
                    if(!white.isEmpty())
                    {
                        colorCode = "white";
                    }
                }
            }
        }
        return colorCode;
    }

    private void DisableField()
    {
        for(int i = 0; i<layoutSecondRedCode.getChildCount(); i++)
        {
            View view = layoutSecondRedCode.getChildAt(i);
            if(view instanceof RelativeLayout){
                for(int n = 0; n<((RelativeLayout) view).getChildCount(); n++)
                {
                    View v = ((RelativeLayout) view).getChildAt(n);
                    if(v instanceof CheckBox)
                    {
                        ((CheckBox)v).setEnabled(false);
                    }
                }
            }
        }
        for(int i = 0; i<layoutSecondYellowCode.getChildCount(); i++)
        {
            View view = layoutSecondYellowCode.getChildAt(i);
            if(view instanceof RelativeLayout){
                for(int n = 0; n<((RelativeLayout) view).getChildCount(); n++)
                {
                    View v = ((RelativeLayout) view).getChildAt(n);
                    if(v instanceof CheckBox)
                    {
                        ((CheckBox)v).setEnabled(false);
                    }
                }
            }
        }
        for(int i = 0; i<layoutSecondGreenCode.getChildCount(); i++)
        {
            View view = layoutSecondGreenCode.getChildAt(i);
            if(view instanceof RelativeLayout){
                for(int n = 0; n<((RelativeLayout) view).getChildCount(); n++)
                {
                    View v = ((RelativeLayout) view).getChildAt(n);
                    if(v instanceof CheckBox)
                    {
                        ((CheckBox)v).setEnabled(false);
                    }
                }
            }
        }
        for(int i = 0; i<layoutSecondWhiteCode.getChildCount(); i++)
        {
            View view = layoutSecondWhiteCode.getChildAt(i);
            if(view instanceof RelativeLayout){
                for(int n = 0; n<((RelativeLayout) view).getChildCount(); n++)
                {
                    View v = ((RelativeLayout) view).getChildAt(n);
                    if(v instanceof CheckBox)
                    {
                        ((CheckBox)v).setEnabled(false);
                    }
                }
            }
        }
    }

    private void EnableField()
    {
        for(int i = 0; i<layoutSecondRedCode.getChildCount(); i++)
        {
            View view = layoutSecondRedCode.getChildAt(i);
            if(view instanceof RelativeLayout){
                for(int n = 0; n<((RelativeLayout) view).getChildCount(); n++)
                {
                    View v = ((RelativeLayout) view).getChildAt(n);
                    if(v instanceof CheckBox)
                    {
                        ((CheckBox)v).setEnabled(true);
                    }
                }
            }
        }
        for(int i = 0; i<layoutSecondYellowCode.getChildCount(); i++)
        {
            View view = layoutSecondYellowCode.getChildAt(i);
            if(view instanceof RelativeLayout){
                for(int n = 0; n<((RelativeLayout) view).getChildCount(); n++)
                {
                    View v = ((RelativeLayout) view).getChildAt(n);
                    if(v instanceof CheckBox)
                    {
                        ((CheckBox)v).setEnabled(true);
                    }
                }
            }
        }
        for(int i = 0; i<layoutSecondGreenCode.getChildCount(); i++)
        {
            View view = layoutSecondGreenCode.getChildAt(i);
            if(view instanceof RelativeLayout){
                for(int n = 0; n<((RelativeLayout) view).getChildCount(); n++)
                {
                    View v = ((RelativeLayout) view).getChildAt(n);
                    if(v instanceof CheckBox)
                    {
                        ((CheckBox)v).setEnabled(true);
                    }
                }
            }
        }
        for(int i = 0; i<layoutSecondWhiteCode.getChildCount(); i++)
        {
            View view = layoutSecondWhiteCode.getChildAt(i);
            if(view instanceof RelativeLayout){
                for(int n = 0; n<((RelativeLayout) view).getChildCount(); n++)
                {
                    View v = ((RelativeLayout) view).getChildAt(n);
                    if(v instanceof CheckBox)
                    {
                        ((CheckBox)v).setEnabled(true);
                    }
                }
            }
        }
    }
}
