package com.example.mommyhealthapp.Mommy;

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
import com.example.mommyhealthapp.Class.DiaryImage;
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

public class UploadDiaryActivity extends AppCompatActivity {
    private RelativeLayout relativeLayoutUploadDiary;
    private ProgressBar progressBarUploadDiary, progressBarUploadPhoto;
    private LinearLayoutCompat layoutUploadDiary, layoutUploadPhoto, layoutUndoPhoto;
    private TextInputLayout txtInputLayoutDiaryTitle, txtInputLayoutDiaryDesc;
    private EditText editTextDiaryTitle, editTextDiaryDesc;
    private ImageView imageViewDiaryImage;
    private Button btnUploadPhotoSave, btnUploadPhotoCancel;

    private static final int RC_PHOTO_PICKER = 2;
    private int check = 0;
    private String diaryImageUrl, healthInfoId, imageId, key;
    private Boolean isEmpty;

    private FirebaseStorage mFirebaseStorage;
    private StorageReference mStorageReference;
    private FirebaseFirestore mFirebaseFirestore;
    private CollectionReference mCollectionReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_diary);

        relativeLayoutUploadDiary = (RelativeLayout)findViewById(R.id.relativeLayoutUploadDiary);
        progressBarUploadDiary = (ProgressBar)findViewById(R.id.progressBarUploadDiary);
        progressBarUploadPhoto = (ProgressBar)findViewById(R.id.progressBarUploadPhoto);
        layoutUploadDiary = (LinearLayoutCompat)findViewById(R.id.layoutUploadDiary);
        layoutUploadPhoto = (LinearLayoutCompat)findViewById(R.id.layoutUploadPhoto);
        layoutUndoPhoto = (LinearLayoutCompat)findViewById(R.id.layoutUndoPhoto);
        txtInputLayoutDiaryTitle = (TextInputLayout)findViewById(R.id.txtInputLayoutDiaryTitle);
        txtInputLayoutDiaryDesc = (TextInputLayout)findViewById(R.id.txtInputLayoutQuotes);
        editTextDiaryTitle = (EditText)findViewById(R.id.editTextDiaryTitle);
        editTextDiaryDesc = (EditText)findViewById(R.id.editTextQuotes);
        imageViewDiaryImage = (ImageView)findViewById(R.id.imageViewDiaryImage);
        btnUploadPhotoSave = (Button)findViewById(R.id.btnUploadPh0toSave);
        btnUploadPhotoCancel = (Button)findViewById(R.id.btnUploadPhotoCancel);

        Intent intent = getIntent();
        healthInfoId = intent.getStringExtra("healthInfoId");
        imageId = intent.getStringExtra("imageId");

        progressBarUploadDiary.setVisibility(View.VISIBLE);
        layoutUploadDiary.setVisibility(View.GONE);
        btnUploadPhotoCancel.setVisibility(View.GONE);

        CheckChangeEmptyField();

        mFirebaseStorage = FirebaseStorage.getInstance();
        mStorageReference = mFirebaseStorage.getReference().child("upload_diary");
        mFirebaseFirestore = FirebaseFirestore.getInstance();
        mCollectionReference = mFirebaseFirestore.collection("MommyHealthInfo").document(healthInfoId).collection("DiaryImage");
        mCollectionReference.whereEqualTo("imageId", imageId).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                if(queryDocumentSnapshots.isEmpty())
                {
                    isEmpty = true;
                    progressBarUploadDiary.setVisibility(View.GONE);
                    layoutUploadDiary.setVisibility(View.VISIBLE);
                }else{
                    isEmpty = false;
                    DisableField();
                    for(QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots)
                    {
                        key = documentSnapshot.getId();
                        DiaryImage di = documentSnapshot.toObject(DiaryImage.class);
                        editTextDiaryTitle.setText(di.getTitle());
                        editTextDiaryDesc.setText(di.getDescription());
                        diaryImageUrl = di.getImageURL();
                        Glide.with(UploadDiaryActivity.this).load(di.getImageURL()).into(imageViewDiaryImage);
                        Log.i("url",diaryImageUrl);
                    }
                    progressBarUploadDiary.setVisibility(View.GONE);
                    layoutUploadDiary.setVisibility(View.VISIBLE);
                    layoutUploadPhoto.setVisibility(View.GONE);
                    layoutUndoPhoto.setVisibility(View.GONE);
                    btnUploadPhotoSave.setText("Update");
                }
            }
        });

        btnUploadPhotoSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isEmpty == true)
                {
                    if(CheckEmptyField() == true)
                    {
                        AlertDialog.Builder builder = new AlertDialog.Builder(UploadDiaryActivity.this);
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
                        String title = editTextDiaryTitle.getText().toString();
                        String desc = editTextDiaryDesc.getText().toString();
                        String imageId = UUID.randomUUID().toString().replace("-", "");
                        String motherId = SaveSharedPreference.getID(UploadDiaryActivity.this);
                        Date today = new Date();
                        DiaryImage di = new DiaryImage(imageId, title, desc, diaryImageUrl, motherId, today);

                        mCollectionReference.add(di).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {
                                AlertDialog.Builder builder = new AlertDialog.Builder(UploadDiaryActivity.this);
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
                            AlertDialog.Builder builder = new AlertDialog.Builder(UploadDiaryActivity.this);
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
                            DocumentReference mDocumentReference = mFirebaseFirestore.collection("MommyHealthInfo").document(healthInfoId).collection("DiaryImage").document(key);
                            String title = editTextDiaryTitle.getText().toString();
                            String desc = editTextDiaryDesc.getText().toString();
                            String motherId = SaveSharedPreference.getID(UploadDiaryActivity.this);
                            Date today = new Date();
                            mDocumentReference.update("title", title);
                            mDocumentReference.update("description", desc);
                            mDocumentReference.update("imageURL", diaryImageUrl);
                            mDocumentReference.update("motherId", motherId);
                            mDocumentReference.update("createdDate", today);
                            Snackbar snackbar = Snackbar.make(relativeLayoutUploadDiary, "Update Successfully", Snackbar.LENGTH_LONG);
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
                layoutUploadPhoto.setVisibility(View.GONE);
                layoutUndoPhoto.setVisibility(View.GONE);
                btnUploadPhotoCancel.setVisibility(View.GONE);
            }
        });

        layoutUploadPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/jpeg");
                intent.putExtra(Intent.EXTRA_LOCAL_ONLY, true);
                if(isEmpty == true)
                {
                    startActivityForResult(Intent.createChooser(intent, "Complete the action using"),RC_PHOTO_PICKER);
                }else{
                    if(diaryImageUrl == null)
                    {
                        startActivityForResult(Intent.createChooser(intent, "Complete the action using"), RC_PHOTO_PICKER);
                    }else{
                        AlertDialog.Builder builder = new AlertDialog.Builder(UploadDiaryActivity.this);
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
                if(diaryImageUrl == null)
                {
                    Snackbar snackbar = Snackbar.make(relativeLayoutUploadDiary, "No photo to undo", Snackbar.LENGTH_LONG);
                    snackbar.show();
                }else{
                    StorageReference mStorageReference = mFirebaseStorage.getReferenceFromUrl(diaryImageUrl);
                    mStorageReference.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            diaryImageUrl = null;
                            imageViewDiaryImage.setImageDrawable(getResources().getDrawable(R.drawable.no_image));
                            Snackbar snackbar = Snackbar.make(relativeLayoutUploadDiary, "Photo Undo Success", Snackbar.LENGTH_LONG);
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
            imageViewDiaryImage.setVisibility(View.GONE);
            Uri selectedImageUri = data.getData();
            final StorageReference storeRef = mStorageReference.child(selectedImageUri.getLastPathSegment());
            Snackbar snackbar = Snackbar.make(relativeLayoutUploadDiary, "Uploading..", Snackbar.LENGTH_LONG);
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
                        diaryImageUrl = downloadUri.toString();
                        Glide.with(UploadDiaryActivity.this).load(diaryImageUrl).into(imageViewDiaryImage);
                        progressBarUploadPhoto.setVisibility(View.GONE);
                        imageViewDiaryImage.setVisibility(View.VISIBLE);
                        Snackbar snackbar = Snackbar.make(relativeLayoutUploadDiary, "Upload Success", Snackbar.LENGTH_LONG);
                        snackbar.show();
                    }else{
                        progressBarUploadPhoto.setVisibility(View.GONE);
                        imageViewDiaryImage.setVisibility(View.VISIBLE);
                        Snackbar snackbar = Snackbar.make(relativeLayoutUploadDiary, "Upload fail", Snackbar.LENGTH_LONG);
                        snackbar.show();
                    }
                }
            });
        }
    }

    private void CheckChangeEmptyField()
    {
        editTextDiaryTitle.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(editTextDiaryTitle.getText().toString().equals(""))
                {
                    txtInputLayoutDiaryTitle.setErrorEnabled(true);
                    txtInputLayoutDiaryTitle.setError("This field is required");
                }else{
                    txtInputLayoutDiaryTitle.setErrorEnabled(false);
                    txtInputLayoutDiaryTitle.setError(null);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        editTextDiaryDesc.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(editTextDiaryDesc.getText().toString().equals(""))
                {
                    txtInputLayoutDiaryDesc.setErrorEnabled(true);
                    txtInputLayoutDiaryDesc.setError("This field is required");
                }else{
                    txtInputLayoutDiaryDesc.setErrorEnabled(false);
                    txtInputLayoutDiaryDesc.setError(null);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void DisableField()
    {
        editTextDiaryTitle.setEnabled(false);
        editTextDiaryTitle.setTextColor(Color.parseColor("#000000"));
        editTextDiaryDesc.setEnabled(false);
        editTextDiaryDesc.setTextColor(Color.parseColor("#000000"));
        layoutUploadPhoto.setEnabled(false);
        layoutUndoPhoto.setEnabled(false);
    }

    private Boolean CheckEmptyField()
    {
        String title = editTextDiaryTitle.getText().toString();
        String desc = editTextDiaryDesc.getText().toString();
        if(title.equals("") || desc.equals("") || diaryImageUrl == null)
        {
            if(title.equals(""))
            {
                txtInputLayoutDiaryTitle.setErrorEnabled(true);
                txtInputLayoutDiaryTitle.setError("This field is required");
            }
            if(desc.equals(""))
            {
                txtInputLayoutDiaryDesc.setErrorEnabled(true);
                txtInputLayoutDiaryDesc.setError("This field is required");
            }
            return true;
        }else{
            return false;
        }
    }

    private void EnableField()
    {
        editTextDiaryTitle.setEnabled(true);
        editTextDiaryDesc.setEnabled(true);
        layoutUploadPhoto.setEnabled(true);
        layoutUndoPhoto.setEnabled(true);
    }

    private void CancelAlarm()
    {
        AlarmManager alarmManager = (AlarmManager)UploadDiaryActivity.this.getSystemService(Context.ALARM_SERVICE );
        Intent intent = new Intent(UploadDiaryActivity.this, NotifyService.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(UploadDiaryActivity.this, 9000, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        alarmManager.cancel(pendingIntent);
        pendingIntent.cancel();

        Intent intentReminder = new Intent(UploadDiaryActivity.this, ReminderService.class);
        PendingIntent pendingIntentReminder = PendingIntent.getBroadcast(UploadDiaryActivity.this, 101, intentReminder, PendingIntent.FLAG_UPDATE_CURRENT);
        alarmManager.cancel(pendingIntentReminder);
        pendingIntentReminder.cancel();

        Intent intentAlert = new Intent(UploadDiaryActivity.this, AlertService.class);
        PendingIntent pendingIntentAlert = PendingIntent.getBroadcast(UploadDiaryActivity.this, 1000, intentAlert, 0);
        alarmManager.cancel(pendingIntentAlert);
        pendingIntentAlert.cancel();

        Log.i("TestingAlarmCancel", "Alarm Cancel");
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.action_logout:
                AlertDialog.Builder builder = new AlertDialog.Builder(UploadDiaryActivity.this);
                builder.setTitle("Logout");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        CancelAlarm();
                        SaveSharedPreference.clearUser(UploadDiaryActivity.this);
                        Intent intent = new Intent(UploadDiaryActivity.this, MainActivity.class);
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
