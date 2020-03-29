package com.example.mommyhealthapp.Nurse;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

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

import com.example.mommyhealthapp.Class.ThirdTrimester;
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
public class ThridTrimesterFragment extends Fragment {
    private RelativeLayout relativeLayoutThirdTrimester;
    private ProgressBar progressBarThirdTri;
    private LinearLayoutCompat layoutTT, layoutThirdRedCode, layoutThirdYellowCode, layoutThirdGreenCode, layoutThirdWhiteCode;
    private Button btnTTSave, btnTTCancel;

    private String healthInfoId, mommyKey, key;
    private int check = 0;
    private Boolean isEmpty;

    private FirebaseFirestore mFirebaseFirestore;
    private CollectionReference mCollectionReference, nCollectionReference, pCollectionReference;

    public ThridTrimesterFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View v = inflater.inflate(R.layout.fragment_thrid_trimester, container, false);

        relativeLayoutThirdTrimester = (RelativeLayout)v.findViewById(R.id.relativeLayoutThirdTrimester);
        progressBarThirdTri = (ProgressBar)v.findViewById(R.id.progressBarThirdTri);
        layoutTT = (LinearLayoutCompat)v.findViewById(R.id.layoutTT);
        layoutThirdRedCode = (LinearLayoutCompat)v.findViewById(R.id.layoutThirdRedCode);
        layoutThirdYellowCode = (LinearLayoutCompat)v.findViewById(R.id.layoutThirdYellowCode);
        layoutThirdGreenCode = (LinearLayoutCompat)v.findViewById(R.id.layoutThirdGreenCode);
        layoutThirdWhiteCode = (LinearLayoutCompat)v.findViewById(R.id.layoutThirdWhiteCode);
        btnTTSave = (Button)v.findViewById(R.id.btnTTSave);
        btnTTCancel = (Button)v.findViewById(R.id.btnTTCancel);

        progressBarThirdTri.setVisibility(View.VISIBLE);
        layoutTT.setVisibility(View.GONE);
        btnTTCancel.setVisibility(View.GONE);

        mFirebaseFirestore = FirebaseFirestore.getInstance();
        pCollectionReference = mFirebaseFirestore.collection("Mommy");
        pCollectionReference.whereEqualTo("mommyId", SaveSharedPreference.getMummyId(getActivity())).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for(QueryDocumentSnapshot documentSnapshot :queryDocumentSnapshots)
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
                    nCollectionReference = mFirebaseFirestore.collection("MommyHealthInfo").document(healthInfoId).collection("ThirdTrimester");
                    nCollectionReference.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                        @Override
                        public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                            if(queryDocumentSnapshots.isEmpty())
                            {
                                isEmpty = true;
                                progressBarThirdTri.setVisibility(View.GONE);
                                layoutTT.setVisibility(View.VISIBLE);
                            }else{
                                isEmpty = false;
                                DisableField();
                                for(QueryDocumentSnapshot documentSnapshots : queryDocumentSnapshots)
                                {
                                    key = documentSnapshots.getId();
                                    ThirdTrimester tt = documentSnapshots.toObject(ThirdTrimester.class);
                                    for(int i=0; i<tt.getRedCode().size(); i++)
                                    {
                                        CheckBox checkBox = (CheckBox)v.findViewById(Integer.parseInt(tt.getRedCode().get(i)));
                                        checkBox.setChecked(true);
                                    }
                                    for(int i=0; i<tt.getYellowCode().size(); i++)
                                    {
                                        CheckBox checkBox = (CheckBox)v.findViewById(Integer.parseInt(tt.getYellowCode().get(i)));
                                        checkBox.setChecked(true);
                                    }
                                    for(int i=0; i<tt.getGreenCode().size(); i++)
                                    {
                                        CheckBox checkBox = (CheckBox)v.findViewById(Integer.parseInt(tt.getGreenCode().get(i)));
                                        checkBox.setChecked(true);
                                    }
                                    for(int i=0; i<tt.getWhiteCode().size(); i++)
                                    {
                                        CheckBox checkBox = (CheckBox)v.findViewById(Integer.parseInt(tt.getWhiteCode().get(i)));
                                        checkBox.setChecked(true);
                                    }
                                }
                                progressBarThirdTri.setVisibility(View.GONE);
                                layoutTT.setVisibility(View.VISIBLE);
                            }
                        }
                    });
                }
            });
        }

        btnTTSave.setOnClickListener(new View.OnClickListener() {
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
                    for(int i=0; i<layoutThirdRedCode.getChildCount(); i++)
                    {
                        View view = layoutThirdRedCode.getChildAt(i);
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
                    for(int i=0; i<layoutThirdYellowCode.getChildCount(); i++)
                    {
                        View view = layoutThirdYellowCode.getChildAt(i);
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
                    for(int i=0; i<layoutThirdGreenCode.getChildCount(); i++)
                    {
                        View view = layoutThirdGreenCode.getChildAt(i);
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
                    for(int i=0; i<layoutThirdWhiteCode.getChildCount(); i++)
                    {
                        View view = layoutThirdWhiteCode.getChildAt(i);
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
                    ThirdTrimester tt = new ThirdTrimester(red, yellow, green, white, medicalPersonnelId, createdDate);
                    final String finalColorCode = DetermineColorCode(red, yellow, green, white);
                    Boolean emptyField = DetermineEmptyCheckBox(red, yellow, green, white);
                    if(emptyField == false)
                    {
                        nCollectionReference.add(tt).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
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
                        btnTTCancel.setVisibility(View.VISIBLE);
                        EnableField();
                    }else if(check == 2)
                    {
                        DocumentReference mDocumentReference = mFirebaseFirestore.collection("MommyHealthInfo").document(healthInfoId).collection("ThirdTrimester").document(key);
                        DocumentReference nDocumentReference = mFirebaseFirestore.collection("Mommy").document(mommyKey);
                        DocumentReference pDocumentReference = mFirebaseFirestore.collection("MommyHealthInfo").document(healthInfoId);
                        List<String> red = new ArrayList<>();
                        List<String> yellow = new ArrayList<>();
                        List<String> green = new ArrayList<>();
                        List<String> white = new ArrayList<>();
                        String medicalPersonnelId = SaveSharedPreference.getID(getContext());
                        Date createdDate = new Date();
                        for(int i=0; i<layoutThirdRedCode.getChildCount(); i++)
                        {
                            View view = layoutThirdRedCode.getChildAt(i);
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
                        for(int i=0; i<layoutThirdYellowCode.getChildCount(); i++)
                        {
                            View view = layoutThirdYellowCode.getChildAt(i);
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
                        for(int i=0; i<layoutThirdGreenCode.getChildCount(); i++)
                        {
                            View view = layoutThirdGreenCode.getChildAt(i);
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
                        for(int i=0; i<layoutThirdWhiteCode.getChildCount(); i++)
                        {
                            View view = layoutThirdWhiteCode.getChildAt(i);
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
                            Snackbar snackbar = Snackbar.make(relativeLayoutThirdTrimester, "Updated Successfully!", Snackbar.LENGTH_LONG);
                            snackbar.show();
                            btnTTCancel.setVisibility(View.GONE);
                            DisableField();
                            check = 0;
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

        btnTTCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnTTCancel.setVisibility(View.GONE);
                DisableField();
                check = 0;
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
                nCollectionReference = mFirebaseFirestore.collection("MommyHealthInfo").document(healthInfoId).collection("ThirdTrimester");
                nCollectionReference.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        if(queryDocumentSnapshots.isEmpty())
                        {
                            isEmpty = true;
                            progressBarThirdTri.setVisibility(View.GONE);
                            layoutTT.setVisibility(View.VISIBLE);
                            btnTTCancel.setVisibility(View.GONE);
                            btnTTSave.setVisibility(View.GONE);
                            DisableField();
                        }else{
                            isEmpty = false;
                            DisableField();
                            for(QueryDocumentSnapshot documentSnapshots : queryDocumentSnapshots)
                            {
                                key = documentSnapshots.getId();
                                ThirdTrimester tt = documentSnapshots.toObject(ThirdTrimester.class);
                                for(int i=0; i<tt.getRedCode().size(); i++)
                                {
                                    CheckBox checkBox = (CheckBox)v.findViewById(Integer.parseInt(tt.getRedCode().get(i)));
                                    checkBox.setChecked(true);
                                }
                                for(int i=0; i<tt.getYellowCode().size(); i++)
                                {
                                    CheckBox checkBox = (CheckBox)v.findViewById(Integer.parseInt(tt.getYellowCode().get(i)));
                                    checkBox.setChecked(true);
                                }
                                for(int i=0; i<tt.getGreenCode().size(); i++)
                                {
                                    CheckBox checkBox = (CheckBox)v.findViewById(Integer.parseInt(tt.getGreenCode().get(i)));
                                    checkBox.setChecked(true);
                                }
                                for(int i=0; i<tt.getWhiteCode().size(); i++)
                                {
                                    CheckBox checkBox = (CheckBox)v.findViewById(Integer.parseInt(tt.getWhiteCode().get(i)));
                                    checkBox.setChecked(true);
                                }
                            }
                            progressBarThirdTri.setVisibility(View.GONE);
                            layoutTT.setVisibility(View.VISIBLE);
                            btnTTCancel.setVisibility(View.GONE);
                            btnTTSave.setVisibility(View.GONE);
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
        for(int i = 0; i<layoutThirdRedCode.getChildCount(); i++)
        {
            View view = layoutThirdRedCode.getChildAt(i);
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
        for(int i = 0; i<layoutThirdYellowCode.getChildCount(); i++)
        {
            View view = layoutThirdYellowCode.getChildAt(i);
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
        for(int i = 0; i<layoutThirdGreenCode.getChildCount(); i++)
        {
            View view = layoutThirdGreenCode.getChildAt(i);
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
        for(int i = 0; i<layoutThirdWhiteCode.getChildCount(); i++)
        {
            View view = layoutThirdWhiteCode.getChildAt(i);
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
        for(int i = 0; i<layoutThirdRedCode.getChildCount(); i++)
        {
            View view = layoutThirdRedCode.getChildAt(i);
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
        for(int i = 0; i<layoutThirdYellowCode.getChildCount(); i++)
        {
            View view = layoutThirdYellowCode.getChildAt(i);
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
        for(int i = 0; i<layoutThirdGreenCode.getChildCount(); i++)
        {
            View view = layoutThirdGreenCode.getChildAt(i);
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
        for(int i = 0; i<layoutThirdWhiteCode.getChildCount(); i++)
        {
            View view = layoutThirdWhiteCode.getChildAt(i);
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
