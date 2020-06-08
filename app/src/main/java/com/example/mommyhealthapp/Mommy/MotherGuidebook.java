package com.example.mommyhealthapp.Mommy;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.mommyhealthapp.AlertService;
import com.example.mommyhealthapp.Class.Guidebook;
import com.example.mommyhealthapp.MainActivity;
import com.example.mommyhealthapp.NotifyService;
import com.example.mommyhealthapp.R;
import com.example.mommyhealthapp.ReminderService;
import com.example.mommyhealthapp.SaveSharedPreference;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class MotherGuidebook extends AppCompatActivity {

    private TextView guidebookDetails;
    private TextInputLayout txtInputLayoutGuidebook;
    private FirebaseFirestore mFirebaseFirestore;
    private CollectionReference mCollectionReference;
    private DocumentReference mDocumentReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mother_guidebook);

        txtInputLayoutGuidebook = (TextInputLayout)findViewById(R.id.txtInputLayoutGuidebook);
        guidebookDetails = (TextView)findViewById(R.id.guidebookDetails);

        mFirebaseFirestore = FirebaseFirestore.getInstance();
        mDocumentReference = mFirebaseFirestore.collection("Guideline").document("KoFc6Vg20dgeDiJFRdRi");
        mDocumentReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                Guidebook guidebook = documentSnapshot.toObject(Guidebook.class);
                guidebookDetails.setText(guidebook.getContent());
            }
        });

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void CancelAlarm()
    {
        AlarmManager alarmManager = (AlarmManager)MotherGuidebook.this.getSystemService(Context.ALARM_SERVICE );
        Intent intent = new Intent(MotherGuidebook.this, NotifyService.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(MotherGuidebook.this, 9000, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        alarmManager.cancel(pendingIntent);
        pendingIntent.cancel();

        Intent intentReminder = new Intent(MotherGuidebook.this, ReminderService.class);
        PendingIntent pendingIntentReminder = PendingIntent.getBroadcast(MotherGuidebook.this, 101, intentReminder, PendingIntent.FLAG_UPDATE_CURRENT);
        alarmManager.cancel(pendingIntentReminder);
        pendingIntentReminder.cancel();

        Intent intentAlert = new Intent(MotherGuidebook.this, AlertService.class);
        PendingIntent pendingIntentAlert = PendingIntent.getBroadcast(MotherGuidebook.this, 1000, intentAlert, 0);
        alarmManager.cancel(pendingIntentAlert);
        pendingIntentAlert.cancel();

        Log.i("TestingAlarmCancel", "Alarm Cancel");
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.action_logout:
                AlertDialog.Builder builder = new AlertDialog.Builder(MotherGuidebook.this);
                builder.setTitle("Logout");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        CancelAlarm();
                        SaveSharedPreference.clearUser(MotherGuidebook.this);
                        Intent intent = new Intent(MotherGuidebook.this, MainActivity.class);
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
