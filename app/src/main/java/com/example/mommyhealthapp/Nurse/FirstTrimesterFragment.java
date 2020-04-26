package com.example.mommyhealthapp.Nurse;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.mommyhealthapp.Class.FirstTrimester;
import com.example.mommyhealthapp.Class.SecondTrimester;
import com.example.mommyhealthapp.Class.ThirdTrimester;
import com.example.mommyhealthapp.R;
import com.example.mommyhealthapp.SaveSharedPreference;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class FirstTrimesterFragment extends Fragment {
    private RelativeLayout relativeLayoutFirstTrimester;
    private LinearLayoutCompat layoutFT, layoutRedCode, layoutYellowCode, layoutGreenCode, layoutWhiteCode;
    private ProgressBar progressBarFirstTri;
    private Button btnFTSave, btnFTCancel;

    private FirebaseFirestore mFirebaseFirestore;
    private CollectionReference mCollectionReference, nCollectionReference, pCollectionReference, secCollectionReference, thirdCollectionReference;

    private String healthInfoId, mommyKey, key;
    private int check = 0;
    private Boolean isEmpty, isSecEmpty, isThirdEmpty;

    public FirstTrimesterFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View v = inflater.inflate(R.layout.fragment_first_trimester, container, false);

        relativeLayoutFirstTrimester = (RelativeLayout) v.findViewById(R.id.relativeLayoutFirstTrimester);
        layoutFT = (LinearLayoutCompat)v.findViewById(R.id.layoutFT);
        layoutRedCode = (LinearLayoutCompat)v.findViewById(R.id.layoutRedCode);
        layoutYellowCode = (LinearLayoutCompat)v.findViewById(R.id.layoutYellowCode);
        layoutGreenCode = (LinearLayoutCompat)v.findViewById(R.id.layoutGreenCode);
        layoutWhiteCode = (LinearLayoutCompat)v.findViewById(R.id.layoutWhiteCode);
        progressBarFirstTri = (ProgressBar)v.findViewById(R.id.progressBarFirstTri);
        btnFTSave = (Button)v.findViewById(R.id.btnFTSave);
        btnFTCancel = (Button)v.findViewById(R.id.btnFTCancel);

        progressBarFirstTri.setVisibility(View.VISIBLE);
        layoutFT.setVisibility(View.GONE);
        btnFTCancel.setVisibility(View.GONE);

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
        }else {
            mCollectionReference.whereEqualTo("healthInfoId", SaveSharedPreference.getHealthInfoId(getActivity())).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                @Override
                public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                    for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                        healthInfoId = documentSnapshot.getId();
                    }
                    checkSecThirdTrimester();
                    nCollectionReference = mFirebaseFirestore.collection("MommyHealthInfo").document(healthInfoId).collection("FirstTrimester");
                    nCollectionReference.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                        @Override
                        public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                            if (queryDocumentSnapshots.isEmpty()) {
                                isEmpty = true;
                                progressBarFirstTri.setVisibility(View.GONE);
                                layoutFT.setVisibility(View.VISIBLE);
                            } else {
                                isEmpty = false;
                                DisableField();
                                for (QueryDocumentSnapshot documentSnapshots : queryDocumentSnapshots) {
                                    key = documentSnapshots.getId();
                                    FirstTrimester ft = documentSnapshots.toObject(FirstTrimester.class);
                                    for (int i = 0; i < ft.getRedCode().size(); i++) {
                                        CheckBox checkBox = (CheckBox) v.findViewById(Integer.parseInt(ft.getRedCode().get(i)));
                                        checkBox.setChecked(true);
                                    }
                                    for (int i = 0; i < ft.getYellowCode().size(); i++) {
                                        CheckBox checkBox = (CheckBox) v.findViewById(Integer.parseInt(ft.getYellowCode().get(i)));
                                        checkBox.setChecked(true);
                                    }
                                    for (int i = 0; i < ft.getGreenCode().size(); i++) {
                                        CheckBox checkBox = (CheckBox) v.findViewById(Integer.parseInt(ft.getGreenCode().get(i)));
                                        checkBox.setChecked(true);
                                    }
                                    for (int i = 0; i < ft.getWhiteCode().size(); i++) {
                                        CheckBox checkBox = (CheckBox) v.findViewById(Integer.parseInt(ft.getWhiteCode().get(i)));
                                        checkBox.setChecked(true);
                                    }
                                }
                                progressBarFirstTri.setVisibility(View.GONE);
                                layoutFT.setVisibility(View.VISIBLE);
                            }
                        }
                    });
                }
            });
        }

        btnFTSave.setOnClickListener(new View.OnClickListener() {
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
                    for(int i=0; i<layoutRedCode.getChildCount(); i++)
                    {
                        View view = layoutRedCode.getChildAt(i);
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

                    for(int i=0; i<layoutYellowCode.getChildCount(); i++)
                    {
                        View view = layoutYellowCode.getChildAt(i);
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

                    for(int i=0; i<layoutGreenCode.getChildCount(); i++)
                    {
                        View view = layoutGreenCode.getChildAt(i);
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

                    for(int i=0; i<layoutWhiteCode.getChildCount(); i++)
                    {
                        View view = layoutWhiteCode.getChildAt(i);
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
                    FirstTrimester ft = new FirstTrimester(red, yellow, green, white, medicalPersonnelId, createdDate);
                    final String finalColorCode = DetermineColorCode(red, yellow, green, white);
                    Boolean emptyField = DetermineEmptyCheckBox(red, yellow, green, white);
                    if(emptyField == false)
                    {
                        nCollectionReference.add(ft).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
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
                        EnableField();
                        btnFTCancel.setVisibility(View.VISIBLE);
                    }else if(check == 2)
                    {
                        DocumentReference mDocumentReference = mFirebaseFirestore.collection("MommyHealthInfo").document(healthInfoId).collection("FirstTrimester").document(key);
                        DocumentReference nDocumentReference = mFirebaseFirestore.collection("Mommy").document(mommyKey);
                        DocumentReference pDocumentReference = mFirebaseFirestore.collection("MommyHealthInfo").document(healthInfoId);
                        List<String> red = new ArrayList<>();
                        List<String> yellow = new ArrayList<>();
                        List<String> green = new ArrayList<>();
                        List<String> white = new ArrayList<>();
                        String medicalPersonnelId = SaveSharedPreference.getID(getContext());
                        Date createdDate = new Date();
                        for(int i=0; i<layoutRedCode.getChildCount(); i++)
                        {
                            View view = layoutRedCode.getChildAt(i);
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

                        for(int i=0; i<layoutYellowCode.getChildCount(); i++)
                        {
                            View view = layoutYellowCode.getChildAt(i);
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

                        for(int i=0; i<layoutGreenCode.getChildCount(); i++)
                        {
                            View view = layoutGreenCode.getChildAt(i);
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

                        for(int i=0; i<layoutWhiteCode.getChildCount(); i++)
                        {
                            View view = layoutWhiteCode.getChildAt(i);
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
                            if(isSecEmpty == true || isThirdEmpty == true)
                            {
                                nDocumentReference.update("colorCode", colorCode);
                                pDocumentReference.update("colorCode", colorCode);
                            }
                            mDocumentReference.update("redCode", red);
                            mDocumentReference.update("yellowCode", yellow);
                            mDocumentReference.update("greenCode", green);
                            mDocumentReference.update("whiteCode", white);
                            mDocumentReference.update("medicalPersonnelId", medicalPersonnelId);
                            mDocumentReference.update("createdDate", createdDate);
                            Snackbar snackbar = Snackbar.make(relativeLayoutFirstTrimester, "Updated Successfully!", Snackbar.LENGTH_LONG);
                            snackbar.show();
                            check = 0;
                            btnFTCancel.setVisibility(View.GONE);
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

        btnFTCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                check = 0;
                btnFTCancel.setVisibility(View.GONE);
                DisableField();
            }
        });

        return v;
    }

    private void checkSecThirdTrimester()
    {
        secCollectionReference = mFirebaseFirestore.collection("MommyHealthInfo").document(healthInfoId).collection("SecondTrimester");
        thirdCollectionReference = mFirebaseFirestore.collection("MommyHealthInfo").document(healthInfoId).collection("ThirdTrimester");
        secCollectionReference.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                if(queryDocumentSnapshots.isEmpty())
                {
                    isSecEmpty = true;
                }else{
                    for(QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots)
                    {
                        SecondTrimester st = documentSnapshot.toObject(SecondTrimester.class);
                        if(st.getRedCode().isEmpty() && st.getGreenCode().isEmpty() && st.getYellowCode().isEmpty() && st.getWhiteCode().isEmpty())
                        {
                            isSecEmpty = true;
                        }else{
                            isSecEmpty = false;
                        }
                    }
                }
            }
        });
        thirdCollectionReference.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                if(queryDocumentSnapshots.isEmpty())
                {
                    isThirdEmpty = true;
                }else{
                    for(QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots)
                    {
                        ThirdTrimester tt = documentSnapshot.toObject(ThirdTrimester.class);
                        if(tt.getRedCode().isEmpty() && tt.getGreenCode().isEmpty() && tt.getYellowCode().isEmpty() && tt.getWhiteCode().isEmpty())
                        {
                            isThirdEmpty = true;
                        }else{
                            isThirdEmpty = false;
                        }
                    }
                }
            }
        });
    }

    private void MommyLogIn(final View v)
    {
        mCollectionReference.whereEqualTo("healthInfoId", SaveSharedPreference.getHealthInfoId(getActivity())).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                    healthInfoId = documentSnapshot.getId();
                }
                nCollectionReference = mFirebaseFirestore.collection("MommyHealthInfo").document(healthInfoId).collection("FirstTrimester");
                nCollectionReference.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        if (queryDocumentSnapshots.isEmpty()) {
                            isEmpty = true;
                            progressBarFirstTri.setVisibility(View.GONE);
                            layoutFT.setVisibility(View.VISIBLE);
                            btnFTSave.setVisibility(View.GONE);
                            btnFTCancel.setVisibility(View.GONE);
                            DisableField();
                        } else {
                            isEmpty = false;
                            DisableField();
                            for (QueryDocumentSnapshot documentSnapshots : queryDocumentSnapshots) {
                                key = documentSnapshots.getId();
                                FirstTrimester ft = documentSnapshots.toObject(FirstTrimester.class);
                                for (int i = 0; i < ft.getRedCode().size(); i++) {
                                    CheckBox checkBox = (CheckBox) v.findViewById(Integer.parseInt(ft.getRedCode().get(i)));
                                    checkBox.setChecked(true);
                                }
                                for (int i = 0; i < ft.getYellowCode().size(); i++) {
                                    CheckBox checkBox = (CheckBox) v.findViewById(Integer.parseInt(ft.getYellowCode().get(i)));
                                    checkBox.setChecked(true);
                                }
                                for (int i = 0; i < ft.getGreenCode().size(); i++) {
                                    CheckBox checkBox = (CheckBox) v.findViewById(Integer.parseInt(ft.getGreenCode().get(i)));
                                    checkBox.setChecked(true);
                                }
                                for (int i = 0; i < ft.getWhiteCode().size(); i++) {
                                    CheckBox checkBox = (CheckBox) v.findViewById(Integer.parseInt(ft.getWhiteCode().get(i)));
                                    checkBox.setChecked(true);
                                }
                            }
                            progressBarFirstTri.setVisibility(View.GONE);
                            layoutFT.setVisibility(View.VISIBLE);
                            btnFTSave.setVisibility(View.GONE);
                            btnFTCancel.setVisibility(View.GONE);
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
        for(int i = 0; i<layoutRedCode.getChildCount(); i++)
        {
            View view = layoutRedCode.getChildAt(i);
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
        for(int i = 0; i<layoutYellowCode.getChildCount(); i++)
        {
            View view = layoutYellowCode.getChildAt(i);
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
        for(int i = 0; i<layoutGreenCode.getChildCount(); i++)
        {
            View view = layoutGreenCode.getChildAt(i);
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
        for(int i = 0; i<layoutWhiteCode.getChildCount(); i++)
        {
            View view = layoutWhiteCode.getChildAt(i);
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
        for(int i = 0; i<layoutRedCode.getChildCount(); i++)
        {
            View view = layoutRedCode.getChildAt(i);
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
        for(int i = 0; i<layoutYellowCode.getChildCount(); i++)
        {
            View view = layoutYellowCode.getChildAt(i);
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
        for(int i = 0; i<layoutGreenCode.getChildCount(); i++)
        {
            View view = layoutGreenCode.getChildAt(i);
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
        for(int i = 0; i<layoutWhiteCode.getChildCount(); i++)
        {
            View view = layoutWhiteCode.getChildAt(i);
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
