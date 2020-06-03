package com.example.mommyhealthapp.Nurse;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.LinearLayoutCompat;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.example.mommyhealthapp.AlertService;
import com.example.mommyhealthapp.Class.DocumentImage;
import com.example.mommyhealthapp.MainActivity;
import com.example.mommyhealthapp.NotifyService;
import com.example.mommyhealthapp.R;
import com.example.mommyhealthapp.ReminderService;
import com.example.mommyhealthapp.SaveSharedPreference;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.Date;
import java.util.UUID;

public class UploadDocumentActivity extends AppCompatActivity {
    private RelativeLayout relativeLayoutUploadDocument;
    private ProgressBar progressBarUploadDocument, progressBarUploadPhoto;
    private LinearLayoutCompat layoutUploadDocument, layoutUploadPhoto, layoutUndoPhoto;
    private TextInputLayout txtInputLayoutDocTitle, txtInputLayoutDocDesc;
    private EditText editTextDocTitle, editTextDocDesc;
    private ImageView imageViewDocImage;
    private Button btnUploadPh0toSave, btnUploadPhotoCancel;

    private static final int RC_PHOTO_PICKER = 2;
    private int check = 0;
    private String documentImageUrl, healthInfoId, imageId, key;
    private Boolean isEmpty;

    private FirebaseStorage mFirebaseStorage;
    private StorageReference mStorageReference;
    private FirebaseFirestore mFirebaseFirestore;
    private CollectionReference mCollectionReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_document);

        relativeLayoutUploadDocument = (RelativeLayout)findViewById(R.id.relativeLayoutUploadDocument);
        progressBarUploadDocument = (ProgressBar)findViewById(R.id.progressBarUploadDocument);
        progressBarUploadPhoto = (ProgressBar)findViewById(R.id.progressBarUploadPhoto);
        layoutUploadDocument = (LinearLayoutCompat)findViewById(R.id.layoutUploadDocument);
        layoutUploadPhoto = (LinearLayoutCompat)findViewById(R.id.layoutUploadPhoto);
        layoutUndoPhoto = (LinearLayoutCompat)findViewById(R.id.layoutUndoPhoto);
        txtInputLayoutDocTitle = (TextInputLayout)findViewById(R.id.txtInputLayoutDocTitle);
        txtInputLayoutDocDesc = (TextInputLayout)findViewById(R.id.txtInputLayoutDocDesc);
        editTextDocTitle = (EditText)findViewById(R.id.editTextDocTitle);
        editTextDocDesc = (EditText)findViewById(R.id.editTextDocDesc);
        imageViewDocImage = (ImageView)findViewById(R.id.imageViewDocImage);
        btnUploadPh0toSave = (Button)findViewById(R.id.btnUploadPh0toSave);
        btnUploadPhotoCancel = (Button)findViewById(R.id.btnUploadPhotoCancel);

        Intent intent = getIntent();
        healthInfoId = intent.getStringExtra("healthInfoId");
        imageId = intent.getStringExtra("imageId");

        progressBarUploadDocument.setVisibility(View.VISIBLE);
        layoutUploadDocument.setVisibility(View.GONE);
        btnUploadPhotoCancel.setVisibility(View.GONE);

        CheckChangeEmptyField();

        mFirebaseStorage = FirebaseStorage.getInstance();
        mStorageReference = mFirebaseStorage.getReference().child("upload_document");
        mFirebaseFirestore = FirebaseFirestore.getInstance();
        mCollectionReference = mFirebaseFirestore.collection("MommyHealthInfo").document(healthInfoId).collection("DocumentImage");
        if(SaveSharedPreference.getUser(UploadDocumentActivity.this).equals("Mommy"))
        {
            MommyLogIn();
        }else{
            mCollectionReference.whereEqualTo("imageId", imageId).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                @Override
                public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                    if(queryDocumentSnapshots.isEmpty())
                    {
                        isEmpty = true;
                        progressBarUploadDocument.setVisibility(View.GONE);
                        layoutUploadDocument.setVisibility(View.VISIBLE);
                    }else{
                        isEmpty = false;
                        DisableField();
                        for(QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots)
                        {
                            key = documentSnapshot.getId();
                            DocumentImage di = documentSnapshot.toObject(DocumentImage.class);
                            editTextDocTitle.setText(di.getTitle());
                            editTextDocDesc.setText(di.getDescription());
                            documentImageUrl = di.getImageURL();
                            Glide.with(UploadDocumentActivity.this).load(di.getImageURL()).into(imageViewDocImage);
                        }
                        progressBarUploadDocument.setVisibility(View.GONE);
                        layoutUploadDocument.setVisibility(View.VISIBLE);
                        layoutUploadPhoto.setVisibility(View.GONE);
                        layoutUndoPhoto.setVisibility(View.GONE);
                        btnUploadPh0toSave.setText("Update");
                    }
                }
            });
        }

        btnUploadPh0toSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isEmpty == true)
                {
                    if(CheckEmptyField() == true)
                    {
                        AlertDialog.Builder builder = new AlertDialog.Builder(UploadDocumentActivity.this);
                        builder.setTitle("Error");
                        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                return;
                            }
                        });
                        builder.setMessage("All the field must be filled in and Photo must be selected");
                        AlertDialog alert = builder.create();
                        alert.show();
                    }else{
                        String title = editTextDocTitle.getText().toString();
                        String desc = editTextDocDesc.getText().toString();
                        String imageId = UUID.randomUUID().toString().replace("-", "");
                        String medicalPersonnelId = SaveSharedPreference.getID(UploadDocumentActivity.this);
                        Date today = new Date();
                        DocumentImage di = new DocumentImage(imageId, title, desc, documentImageUrl, medicalPersonnelId, today);

                        mCollectionReference.add(di).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {
                                AlertDialog.Builder builder = new AlertDialog.Builder(UploadDocumentActivity.this);
                                builder.setTitle("Save Successfully");
                                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        finish();
                                    }
                                });
                                builder.setMessage("Save Successful!");
                                AlertDialog alert = builder.create();
                                alert.setCanceledOnTouchOutside(false);
                                alert.show();
                            }
                        });
                    }
                }else{
                    check++;
                    if(check == 1)
                    {
                        EnableField();
                        layoutUploadPhoto.setVisibility(View.VISIBLE);
                        layoutUndoPhoto.setVisibility(View.VISIBLE);
                        btnUploadPhotoCancel.setVisibility(View.VISIBLE);
                    }else if(check == 2)
                    {
                        if(CheckEmptyField() == true)
                        {
                            AlertDialog.Builder builder = new AlertDialog.Builder(UploadDocumentActivity.this);
                            builder.setTitle("Error");
                            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    return;
                                }
                            });
                            builder.setMessage("All the field must be filled in and Photo must be selected");
                            AlertDialog alert = builder.create();
                            alert.show();
                            check = 1;
                        }else{
                            DocumentReference mDocumentReference = mFirebaseFirestore.collection("MommyHealthInfo").document(healthInfoId).collection("DocumentImage").document(key);
                            String title = editTextDocTitle.getText().toString();
                            String desc = editTextDocDesc.getText().toString();
                            String medicalPersonnelId = SaveSharedPreference.getID(UploadDocumentActivity.this);
                            Date today = new Date();
                            mDocumentReference.update("title", title);
                            mDocumentReference.update("description", desc);
                            mDocumentReference.update("imageURL", documentImageUrl);
                            mDocumentReference.update("medicalPersonnelId", medicalPersonnelId);
                            mDocumentReference.update("createdDate", today);
                            Snackbar snackbar = Snackbar.make(relativeLayoutUploadDocument, "Update Successfully", Snackbar.LENGTH_LONG);
                            snackbar.show();
                            DisableField();
                            check = 0;
                            btnUploadPhotoCancel.setVisibility(View.GONE);
                        }
                    }
                }
            }
        });

        btnUploadPhotoCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                check = 0;
                DisableField();
                btnUploadPhotoCancel.setVisibility(View.GONE);
                layoutUploadPhoto.setVisibility(View.GONE);
                layoutUndoPhoto.setVisibility(View.GONE);
            }
        });

        layoutUploadPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                String [] mimeTypes = {"image/png", "image/jpg","image/jpeg"};
                intent.setType("*/*");
                intent.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes);
                if(isEmpty == true)
                {
                    startActivityForResult(Intent.createChooser(intent, "Complete the action using"), RC_PHOTO_PICKER);
                }else{
                    if(documentImageUrl == null)
                    {
                        startActivityForResult(Intent.createChooser(intent, "Complete the action using"), RC_PHOTO_PICKER);
                    }else{
                        AlertDialog.Builder builder = new AlertDialog.Builder(UploadDocumentActivity.this);
                        builder.setTitle("Error");
                        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                return;
                            }
                        });
                        builder.setMessage("Please undo photo 1st");
                        AlertDialog alert = builder.create();
                        alert.show();
                    }
                }
            }
        });

        layoutUndoPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(documentImageUrl == null)
                {
                    Snackbar snackbar = Snackbar.make(relativeLayoutUploadDocument, "No photo to undo", Snackbar.LENGTH_LONG);
                    snackbar.show();
                }else{
                    StorageReference mStorageReference = mFirebaseStorage.getReferenceFromUrl(documentImageUrl);
                    mStorageReference.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            documentImageUrl = null;
                            imageViewDocImage.setImageDrawable(getResources().getDrawable(R.drawable.no_image));
                            Snackbar snackbar = Snackbar.make(relativeLayoutUploadDocument, "Photo Undo Success", Snackbar.LENGTH_LONG);
                            snackbar.show();
                        }
                    });
                }
            }
        });

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == RC_PHOTO_PICKER && resultCode == RESULT_OK)
        {
            progressBarUploadPhoto.setVisibility(View.VISIBLE);
            imageViewDocImage.setVisibility(View.GONE);
            Uri selectedImageUri = data.getData();
            final StorageReference storeRef = mStorageReference.child(selectedImageUri.getLastPathSegment());
            Snackbar snackbar = Snackbar.make(relativeLayoutUploadDocument, "Uploading..", Snackbar.LENGTH_LONG);
            snackbar.show();
            storeRef.putFile(selectedImageUri).continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                @Override
                public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                    if(!task.isSuccessful())
                    {
                        throw task.getException();
                    }
                    return storeRef.getDownloadUrl();
                }
            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull final Task<Uri> task) {
                    if(task.isSuccessful())
                    {
                        Uri downloadUri = task.getResult();
                        documentImageUrl = downloadUri.toString();
                        Glide.with(UploadDocumentActivity.this).load(documentImageUrl).into(imageViewDocImage);
                        progressBarUploadPhoto.setVisibility(View.GONE);
                        imageViewDocImage.setVisibility(View.VISIBLE);
                        Snackbar snackbar = Snackbar.make(relativeLayoutUploadDocument, "Upload Success", Snackbar.LENGTH_LONG);
                        snackbar.show();
                    }else{
                        progressBarUploadPhoto.setVisibility(View.GONE);
                        imageViewDocImage.setVisibility(View.VISIBLE);
                        Snackbar snackbar = Snackbar.make(relativeLayoutUploadDocument, "Upload fail", Snackbar.LENGTH_LONG);
                        snackbar.show();
                    }
                }
            });
        }
    }

    private void MommyLogIn()
    {
        mCollectionReference.whereEqualTo("imageId", imageId).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                if(queryDocumentSnapshots.isEmpty())
                {
                    isEmpty = true;
                    progressBarUploadDocument.setVisibility(View.GONE);
                    layoutUploadDocument.setVisibility(View.VISIBLE);
                    layoutUploadPhoto.setVisibility(View.GONE);
                    layoutUndoPhoto.setVisibility(View.GONE);
                    btnUploadPh0toSave.setVisibility(View.GONE);
                }else{
                    isEmpty = false;
                    DisableField();
                    for(QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots)
                    {
                        key = documentSnapshot.getId();
                        DocumentImage di = documentSnapshot.toObject(DocumentImage.class);
                        editTextDocTitle.setText(di.getTitle());
                        editTextDocDesc.setText(di.getDescription());
                        documentImageUrl = di.getImageURL();
                        Glide.with(UploadDocumentActivity.this).load(di.getImageURL()).into(imageViewDocImage);
                    }
                    progressBarUploadDocument.setVisibility(View.GONE);
                    layoutUploadDocument.setVisibility(View.VISIBLE);
                    layoutUploadPhoto.setVisibility(View.GONE);
                    layoutUndoPhoto.setVisibility(View.GONE);
                    btnUploadPh0toSave.setVisibility(View.GONE);
                }
            }
        });
    }

    private void CheckChangeEmptyField()
    {
        editTextDocTitle.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(editTextDocTitle.getText().toString().equals(""))
                {
                    txtInputLayoutDocTitle.setErrorEnabled(true);
                    txtInputLayoutDocTitle.setError("This field is required");
                }else{
                    txtInputLayoutDocTitle.setErrorEnabled(false);
                    txtInputLayoutDocTitle.setError(null);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        editTextDocDesc.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(editTextDocDesc.getText().toString().equals(""))
                {
                    txtInputLayoutDocDesc.setErrorEnabled(true);
                    txtInputLayoutDocDesc.setError("This field is required");
                }else{
                    txtInputLayoutDocDesc.setErrorEnabled(false);
                    txtInputLayoutDocDesc.setError(null);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private Boolean CheckEmptyField()
    {
        String title = editTextDocTitle.getText().toString();
        String desc = editTextDocDesc.getText().toString();
        if(title.equals("") || desc.equals("") || documentImageUrl == null)
        {
            if(title.equals(""))
            {
                txtInputLayoutDocTitle.setErrorEnabled(true);
                txtInputLayoutDocTitle.setError("This field is required");
            }
            if(desc.equals(""))
            {
                txtInputLayoutDocDesc.setErrorEnabled(true);
                txtInputLayoutDocDesc.setError("This field is required");
            }
            return true;
        }else{
            return false;
        }
    }

    private void DisableField()
    {
        editTextDocTitle.setEnabled(false);
        editTextDocTitle.setTextColor(Color.parseColor("#000000"));
        editTextDocDesc.setEnabled(false);
        editTextDocDesc.setTextColor(Color.parseColor("#000000"));
        layoutUploadPhoto.setEnabled(false);
        layoutUndoPhoto.setEnabled(false);
    }

    private void EnableField()
    {
        editTextDocTitle.setEnabled(true);
        editTextDocDesc.setEnabled(true);
        layoutUploadPhoto.setEnabled(true);
        layoutUndoPhoto.setEnabled(true);
    }

    private void CancelAlarm()
    {
        AlarmManager alarmManager = (AlarmManager)UploadDocumentActivity.this.getSystemService(Context.ALARM_SERVICE );
        Intent intent = new Intent(UploadDocumentActivity.this, NotifyService.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(UploadDocumentActivity.this, 9000, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        alarmManager.cancel(pendingIntent);
        pendingIntent.cancel();

        Intent intentReminder = new Intent(UploadDocumentActivity.this, ReminderService.class);
        PendingIntent pendingIntentReminder = PendingIntent.getBroadcast(UploadDocumentActivity.this, 101, intentReminder, PendingIntent.FLAG_UPDATE_CURRENT);
        alarmManager.cancel(pendingIntentReminder);
        pendingIntentReminder.cancel();

        Intent intentAlert = new Intent(UploadDocumentActivity.this, AlertService.class);
        PendingIntent pendingIntentAlert = PendingIntent.getBroadcast(UploadDocumentActivity.this, 1000, intentAlert, 0);
        alarmManager.cancel(pendingIntentAlert);
        pendingIntentAlert.cancel();

        Log.i("TestingAlarmCancel", "Alarm Cancel");
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.action_logout:
                AlertDialog.Builder builder = new AlertDialog.Builder(UploadDocumentActivity.this);
                builder.setTitle("Logout");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if(SaveSharedPreference.getUser(UploadDocumentActivity.this).equals("Mommy"))
                        {
                            CancelAlarm();
                        }
                        SaveSharedPreference.clearUser(UploadDocumentActivity.this);
                        Intent intent = new Intent(UploadDocumentActivity.this, MainActivity.class);
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
}
