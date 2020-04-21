package com.example.mommyhealthapp.Nurse.ui.CreateMother;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.mommyhealthapp.Class.Mommy;
import com.example.mommyhealthapp.Class.MommyDetail;
import com.example.mommyhealthapp.R;
import com.example.mommyhealthapp.SaveSharedPreference;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import org.w3c.dom.Text;

import java.io.ByteArrayOutputStream;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class CreateMotherDetailFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private TextInputLayout txtInputLayoutDisease, txtInputLayoutEDD, txtInputLayoutEDP, txtLayoutHusbandPhone, txtLayoutHusbandName,
            txtLayoutHusbandIC, txtLayoutHusbandWork, txtLayoutHusbandWorkPlacr, heightLayout, weightLayout, txtInputLayoutLNMP;
    private EditText editTextEDD, editTextLNMP, editTextEDP, editTextHusbandName, editTextHusbandIC, editTextHusbandWork, editTextHusbandWorkAddress, editTextPhone, heightEditText, weightEditText;
    private EditText editTextDisease;
    DatePickerDialog datePickerDialog;
    private RadioGroup radioGroupYesNo, radioGroupMarriage;
    private RadioButton radioBtnYes, radioBtnNo, radioBtnMarried, radioBtnSingle;
    private Button btnSaveMother;
    private ProgressBar progressBarCreateMother;
    private LinearLayoutCompat layoutCreateMother;

    private Mommy mommy;

    private FirebaseFirestore mFirebaseFirestore;
    private CollectionReference mCollectionReference, mdCollectionReference;
    private FirebaseStorage mFirebaseStorage;
    private StorageReference mStorageReference;

    private String QRimage;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public CreateMotherDetailFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static CreateMotherDetailFragment newInstance(String param1, String param2) {
        CreateMotherDetailFragment fragment = new CreateMotherDetailFragment();
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
        final View root = inflater.inflate(R.layout.fragment_create_mother_detail, container, false);
        txtInputLayoutDisease = (TextInputLayout)root.findViewById(R.id.txtInputLayoutDisease);
        txtInputLayoutEDD = (TextInputLayout)root.findViewById(R.id.txtInputLayoutEDD);
        txtInputLayoutEDP = (TextInputLayout)root.findViewById(R.id.txtInputLayoutEDP);
        txtInputLayoutLNMP = (TextInputLayout)root.findViewById(R.id.txtInputLayoutLNMP);
        txtLayoutHusbandPhone = (TextInputLayout)root.findViewById(R.id.txtLayoutHusbandPhone);
        txtLayoutHusbandName = (TextInputLayout)root.findViewById(R.id.txtLayoutHusbandName);
        txtLayoutHusbandIC = (TextInputLayout)root.findViewById(R.id.txtLayoutHusbandIC);
        txtLayoutHusbandWork = (TextInputLayout)root.findViewById(R.id.txtLayoutHusbandWork);
        txtLayoutHusbandWorkPlacr = (TextInputLayout)root.findViewById(R.id.txtLayoutHusbandWorkAddress);
        editTextEDD = (EditText)root.findViewById(R.id.editTextEDD);
        editTextDisease = (EditText)root.findViewById(R.id.editTextDisease);
        editTextLNMP = (EditText)root.findViewById(R.id.editTextLNMP);
        editTextEDP = (EditText)root.findViewById(R.id.editTextEDP);
        editTextHusbandName = (EditText)root.findViewById(R.id.editTextHusbandName);
        editTextHusbandIC = (EditText)root.findViewById(R.id.editTextHusbandIC);
        editTextHusbandWork = (EditText)root.findViewById(R.id.editTextHusbandWork);
        editTextHusbandWorkAddress = (EditText)root.findViewById(R.id.editTextHusbandWorkAddress);
        editTextPhone = (EditText)root.findViewById(R.id.editTextPhone);
        heightLayout = (TextInputLayout)root.findViewById(R.id.heightLayout);
        heightEditText = (EditText)root.findViewById(R.id.heightEditText);
        weightLayout = (TextInputLayout)root.findViewById(R.id.weightLayout);
        weightEditText = (EditText)root.findViewById(R.id.weightEditText);
        radioGroupYesNo = (RadioGroup)root.findViewById(R.id.radioGroupYesNo);
        radioGroupMarriage = (RadioGroup)root.findViewById(R.id.radioGroupMarriage);
        radioBtnNo = (RadioButton)root.findViewById(R.id.radioBtnNo);
        radioBtnYes = (RadioButton)root.findViewById(R.id.radioBtnYes);
        radioBtnMarried = (RadioButton)root.findViewById(R.id.radioBtnMarried);
        radioBtnSingle = (RadioButton)root.findViewById(R.id.radioBtnSingle);
        btnSaveMother = (Button)root.findViewById(R.id.btnSaveMother);
        progressBarCreateMother = (ProgressBar)root.findViewById(R.id.progressBarCreateMother);
        layoutCreateMother = (LinearLayoutCompat)root.findViewById(R.id.layoutCreateMother);

        mommy = getArguments().getParcelable("mommyinfo");

        mFirebaseFirestore = FirebaseFirestore.getInstance();
        mCollectionReference = mFirebaseFirestore.collection("Mommy");
        mFirebaseStorage = FirebaseStorage.getInstance();
        mStorageReference = mFirebaseStorage.getReference().child("QRCode");

        editTextEDD.setFocusable(false);
        editTextEDD.setClickable(false);
        editTextLNMP.setFocusable(false);
        editTextLNMP.setClickable(false);
        editTextEDP.setFocusable(false);
        editTextEDP.setClickable(false);
        editTextEDD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR); // current year
                int mMonth = c.get(Calendar.MONTH); // current month
                int mDay = c.get(Calendar.DAY_OF_MONTH); // current day
                datePickerDialog = new DatePickerDialog(getActivity(),
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                editTextEDD.setText(dayOfMonth + "/" +(month + 1) + "/" + year);
                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });

        editTextLNMP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR); // current year
                int mMonth = c.get(Calendar.MONTH); // current month
                int mDay = c.get(Calendar.DAY_OF_MONTH); // current day
                datePickerDialog = new DatePickerDialog(getActivity(),
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                editTextLNMP.setText(dayOfMonth + "/" +(month + 1) + "/" + year);
                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });

        editTextEDP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR); // current year
                int mMonth = c.get(Calendar.MONTH); // current month
                int mDay = c.get(Calendar.DAY_OF_MONTH); // current day
                datePickerDialog = new DatePickerDialog(getActivity(),
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                editTextEDP.setText(dayOfMonth + "/" +(month + 1) + "/" + year);
                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });

        radioGroupYesNo.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(radioBtnYes.isChecked())
                {
                    txtInputLayoutDisease.setVisibility(getView().VISIBLE);
                }else{
                    txtInputLayoutDisease.setVisibility(getView().GONE);
                }
            }
        });

        radioGroupMarriage.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(radioBtnMarried.isChecked()){
                    txtLayoutHusbandName.setVisibility(getView().VISIBLE);
                    txtLayoutHusbandIC.setVisibility(getView().VISIBLE);
                    txtLayoutHusbandWork.setVisibility(getView().VISIBLE);
                    txtLayoutHusbandWorkPlacr.setVisibility(getView().VISIBLE);
                    txtLayoutHusbandPhone.setVisibility(getView().VISIBLE);
                }else{
                    txtLayoutHusbandName.setVisibility(getView().GONE);
                    txtLayoutHusbandIC.setVisibility(getView().GONE);
                    txtLayoutHusbandWork.setVisibility(getView().GONE);
                    txtLayoutHusbandWorkPlacr.setVisibility(getView().GONE);
                    txtLayoutHusbandPhone.setVisibility(getView().GONE);
                }
            }
        });

        progressBarCreateMother.setVisibility(View.GONE);
        layoutCreateMother.setVisibility(View.VISIBLE);
        checkReuiredFieldTextChange();

        btnSaveMother.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {

                if(checkRequiredField() == true)
                {
                    Toast.makeText(getContext(), "Field is empty!",Toast.LENGTH_LONG).show();
                }else{
                    progressBarCreateMother.setVisibility(View.VISIBLE);
                    layoutCreateMother.setVisibility(View.GONE);
                    getQRCodeImage(new MyCallBack() {
                        @Override
                        public void onCallBack(Mommy mommy) {
                            Date lnmp = null;
                            Date edd = null;
                            Date edp = null;
                            Double height = Double.parseDouble(heightEditText.getText().toString());
                            Double weight = Double.parseDouble(weightEditText.getText().toString());
                            String disease = "";
                            if(radioBtnYes.isChecked())
                            {
                                disease = editTextDisease.getText().toString();
                            }
                            try {
                                lnmp = new SimpleDateFormat("dd/MM/yyyy").parse(editTextLNMP.getText().toString());
                                if(editTextEDD.getText().toString().isEmpty())
                                {
                                    edd = null;
                                }else{
                                    edd = new SimpleDateFormat("dd/MM/yyyy").parse(editTextEDD.getText().toString());
                                }
                                edp = new SimpleDateFormat("dd/MM/yyyy").parse(editTextEDP.getText().toString());
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                            int radioButtonID = radioGroupMarriage.getCheckedRadioButtonId();
                            RadioButton radioButtonSelected = (RadioButton) root.findViewById(radioButtonID);
                            String radioButtonText = radioButtonSelected.getText().toString();
                            String husbandName = editTextHusbandName.getText().toString();
                            String husbandIC = editTextHusbandIC.getText().toString();
                            String husbandWork = editTextHusbandWork.getText().toString();
                            String husbandWorkAddress = editTextHusbandWorkAddress.getText().toString();
                            String husbandPhone = editTextPhone.getText().toString();
                            Date today = new Date();

                            final MommyDetail mommyDetail = new MommyDetail(height, weight, disease, lnmp, edd, edp, radioButtonText, husbandName, husbandIC, husbandWork, husbandWorkAddress, husbandPhone, today);
                            mCollectionReference.add(mommy).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                @Override
                                public void onSuccess(DocumentReference documentReference) {

                                    String id = documentReference.getId();
                                    mdCollectionReference = mFirebaseFirestore.collection("Mommy").document(id).collection("MommyDetail");
                                    mdCollectionReference.add(mommyDetail).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                        @Override
                                        public void onSuccess(DocumentReference documentReference) {
                                            progressBarCreateMother.setVisibility(View.GONE);
                                            layoutCreateMother.setVisibility(View.VISIBLE);
                                            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                                            builder.setTitle("Register Successfully");
                                            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialogInterface, int i) {

                                                    Navigation.findNavController(v).navigate(R.id.nav_home);
                                                }
                                            });
                                            builder.setMessage("Account register successfully!");
                                            AlertDialog alert = builder.create();
                                            alert.setCanceledOnTouchOutside(false);
                                            alert.show();
                                        }
                                    });
                                }
                            });
                        }
                    });
                }
            }
        });

        return root;
    }

    public void getQRCodeImage(final MyCallBack myCallBack)
    {
        MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
        try {
            BitMatrix bitMatrix = multiFormatWriter.encode(mommy.getMommyId(), BarcodeFormat.QR_CODE,200,200);
            BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
            Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            byte[] data = baos.toByteArray();
            final StorageReference storeRef = mStorageReference.child("QRCode_"+mommy.getMommyId());
            storeRef.putBytes(data).continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                @Override
                public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                    if (!task.isSuccessful()) {
                        throw task.getException();
                    }
                    return storeRef.getDownloadUrl();
                }
            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                    if(task.isSuccessful())
                    {
                        Uri downloadUri = task.getResult();
                        mommy.setQrcodeImage(downloadUri.toString());
                        myCallBack.onCallBack(mommy);
                    }else{
                        Toast.makeText(getActivity(),"Upload Failed: "+task.getException().getMessage(),Toast.LENGTH_LONG).show();
                    }
                }
            });
        } catch (WriterException e) {
            e.printStackTrace();
        }
    }

    public interface MyCallBack{
        void onCallBack(Mommy mommy);
    }

    private void checkReuiredFieldTextChange()
    {
        heightEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(heightEditText.getText().toString().equals(""))
                {
                    heightLayout.setErrorEnabled(true);
                    heightLayout.setError("This field is required!");
                }else{
                    heightLayout.setErrorEnabled(false);
                    heightLayout.setError(null);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        weightEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(weightEditText.getText().toString().equals(""))
                {
                    weightLayout.setErrorEnabled(true);
                    weightLayout.setError("This field is required!");
                }else{
                    weightLayout.setErrorEnabled(false);
                    weightLayout.setError(null);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        editTextLNMP.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(editTextLNMP.getText().toString().equals(""))
                {
                    txtInputLayoutLNMP.setErrorEnabled(true);
                    txtInputLayoutLNMP.setError("This field is required!");
                }else{
                    txtInputLayoutLNMP.setErrorEnabled(false);
                    txtInputLayoutLNMP.setError(null);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        editTextEDP.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(editTextEDP.getText().toString().equals(""))
                {
                    txtInputLayoutEDP.setErrorEnabled(true);
                    txtInputLayoutEDP.setError("This field is required!");
                }else{
                    txtInputLayoutEDP.setErrorEnabled(false);
                    txtInputLayoutEDP.setError(null);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        editTextHusbandName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(editTextHusbandName.getText().toString().equals(""))
                {
                    txtLayoutHusbandName.setErrorEnabled(true);
                    txtLayoutHusbandName.setError("This field is required!");
                }else{
                    txtLayoutHusbandName.setErrorEnabled(false);
                    txtLayoutHusbandName.setError(null);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        editTextHusbandIC.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(editTextHusbandIC.getText().toString().equals(""))
                {
                    txtLayoutHusbandIC.setErrorEnabled(true);
                    txtLayoutHusbandIC.setError("This field is required!");
                }else{
                    txtLayoutHusbandIC.setErrorEnabled(false);
                    txtLayoutHusbandIC.setError(null);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        editTextHusbandWork.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(editTextHusbandWork.getText().toString().equals(""))
                {
                    txtLayoutHusbandWork.setErrorEnabled(true);
                    txtLayoutHusbandWork.setError("This field is required!");
                }else{
                    txtLayoutHusbandWork.setErrorEnabled(false);
                    txtLayoutHusbandWork.setError(null);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        editTextPhone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(editTextPhone.getText().toString().equals(""))
                {
                    txtLayoutHusbandPhone.setErrorEnabled(true);
                    txtLayoutHusbandPhone.setError("This field is required!");
                }else{
                    txtLayoutHusbandPhone.setErrorEnabled(false);
                    txtLayoutHusbandPhone.setError(null);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        editTextHusbandWorkAddress.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(editTextHusbandWorkAddress.getText().toString().equals(""))
                {
                    txtLayoutHusbandWorkPlacr.setErrorEnabled(true);
                    txtLayoutHusbandWorkPlacr.setError("This field is required!");
                }else{
                    txtLayoutHusbandWorkPlacr.setErrorEnabled(false);
                    txtLayoutHusbandWorkPlacr.setError(null);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        editTextDisease.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(editTextDisease.getText().toString().equals(""))
                {
                    txtInputLayoutDisease.setErrorEnabled(true);
                    txtInputLayoutDisease.setError("This field is required!");
                }else{
                    txtInputLayoutDisease.setErrorEnabled(false);
                    txtInputLayoutDisease.setError(null);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private boolean checkRequiredField()
    {
        boolean empty;
        int check = 0;
        if(radioGroupYesNo.getCheckedRadioButtonId()==-1 || radioGroupMarriage.getCheckedRadioButtonId()==-1||heightEditText.getText().toString().equals("")||weightEditText.getText().toString().equals("")||
                editTextLNMP.getText().toString().equals("")||editTextEDP.getText().toString().equals(""))
        {
            if(radioGroupYesNo.getCheckedRadioButtonId() == -1)
            {
                radioBtnYes.setError("Required!");
                radioBtnNo.setError("Required!");
            }else{
                radioBtnYes.setError(null);
                radioBtnNo.setError(null);
            }

            if(radioGroupMarriage.getCheckedRadioButtonId() == -1)
            {
                radioBtnMarried.setError("Required!");
                radioBtnSingle.setError("Required!");
            }else{
                radioBtnMarried.setError(null);
                radioBtnSingle.setError(null);
            }

            if(heightEditText.getText().toString().equals(""))
            {
                heightLayout.setErrorEnabled(true);
                heightLayout.setError("This field is required!");
            }else{
                heightLayout.setErrorEnabled(false);
                heightLayout.setError(null);
            }

            if(weightEditText.getText().toString().equals(""))
            {
                weightLayout.setErrorEnabled(true);
                weightLayout.setError("This field is required!");
            }else{
                weightLayout.setErrorEnabled(false);
                weightLayout.setError(null);
            }

            if(editTextLNMP.getText().toString().equals(""))
            {
                txtInputLayoutLNMP.setErrorEnabled(true);
                txtInputLayoutLNMP.setError("This field is required!");
            }else{
                txtInputLayoutLNMP.setErrorEnabled(false);
                txtInputLayoutLNMP.setError(null);
            }

            if(editTextEDP.getText().toString().equals(""))
            {
                txtInputLayoutEDP.setErrorEnabled(true);
                txtInputLayoutEDP.setError("This field is required!");
            }else{
                txtInputLayoutEDP.setErrorEnabled(false);
                txtInputLayoutEDP.setError(null);
            };
            check++;
        }

        if(radioBtnYes.isChecked())
        {
            if(editTextDisease.getText().toString().equals(""))
            {
                txtInputLayoutDisease.setErrorEnabled(true);
                txtInputLayoutDisease.setError("This field is required!");
                check++;
            }else{
                txtInputLayoutDisease.setErrorEnabled(false);
                txtInputLayoutDisease.setError(null);
            }
        }

        if(radioBtnMarried.isChecked())
        {
            if(editTextHusbandName.getText().toString().equals("")||editTextHusbandIC.getText().toString().equals("")||editTextHusbandWork.getText().toString().equals("")||
                    editTextPhone.getText().toString().equals("") || editTextHusbandWorkAddress.getText().toString().equals(""))
            {
                if(editTextHusbandName.getText().toString().equals(""))
                {
                    txtLayoutHusbandName.setErrorEnabled(true);
                    txtLayoutHusbandName.setError("This field is required!");
                }else{
                    txtLayoutHusbandName.setErrorEnabled(false);
                    txtLayoutHusbandName.setError(null);
                }

                if(editTextHusbandIC.getText().toString().equals(""))
                {
                    txtLayoutHusbandIC.setErrorEnabled(true);
                    txtLayoutHusbandIC.setError("This field is required!");
                }else{
                    txtLayoutHusbandIC.setErrorEnabled(false);
                    txtLayoutHusbandIC.setError(null);
                }

                if(editTextHusbandWork.getText().toString().equals(""))
                {
                    txtLayoutHusbandWork.setErrorEnabled(true);
                    txtLayoutHusbandWork.setError("This field is required!");
                }else{
                    txtLayoutHusbandWork.setErrorEnabled(false);
                    txtLayoutHusbandWork.setError(null);
                }

                if(editTextPhone.getText().toString().equals(""))
                {
                    txtLayoutHusbandPhone.setErrorEnabled(true);
                    txtLayoutHusbandPhone.setError("This field is required!");
                }else{
                    txtLayoutHusbandPhone.setErrorEnabled(false);
                    txtLayoutHusbandPhone.setError(null);
                }

                if(editTextHusbandWorkAddress.getText().toString().equals(""))
                {
                    txtLayoutHusbandWorkPlacr.setErrorEnabled(true);
                    txtLayoutHusbandWorkPlacr.setError("This field is required!");
                }else{
                    txtLayoutHusbandWorkPlacr.setErrorEnabled(false);
                    txtLayoutHusbandWorkPlacr.setError(null);
                }
                check++;
            }
        }
        if(check > 0)
        {
            empty = true;
        }else{
            empty = false;
        }
        return empty;
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
