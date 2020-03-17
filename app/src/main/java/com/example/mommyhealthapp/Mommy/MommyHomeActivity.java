package com.example.mommyhealthapp.Mommy;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.mommyhealthapp.Class.AppointmentDate;
import com.example.mommyhealthapp.Mommy.ui.SelfCheck.SelfCheckFragment;
import com.example.mommyhealthapp.NotifyService;
import com.example.mommyhealthapp.R;
import com.example.mommyhealthapp.ReminderService;
import com.example.mommyhealthapp.SaveSharedPreference;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import java.util.Calendar;
import java.util.Date;

public class MommyHomeActivity extends AppCompatActivity implements SelfCheckFragment.OnFragmentInteractionListener {
    private String tag;
    private FirebaseFirestore mFirebaseFirestore;
    private CollectionReference mCollectionReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mommy_home);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        Intent intent = getIntent();
        tag = intent.getStringExtra("tag");

        mFirebaseFirestore = FirebaseFirestore.getInstance();
        mCollectionReference = mFirebaseFirestore.collection("Mommy").document(SaveSharedPreference.getID(MommyHomeActivity.this)).collection("AppointmentDate");
        mCollectionReference.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                Date appointmentDate = null;
                Date today = new Date();
                for(QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots)
                {
                    AppointmentDate app = documentSnapshot.toObject(AppointmentDate.class);
                    appointmentDate = app.getAppointmentDate();
                }
                if(today.equals(appointmentDate))
                {
                    Calendar date = Calendar.getInstance();
                    date.setTime(appointmentDate);
                    setAppointmentReminder(date);
                }
            }
        });
        notifyBabyKick();

        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_self_check, R.id.navigation_pregnancy_record, R.id.navigation_notifications, R.id.navigation_mother_profile)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        if(tag != null && tag.equals("TAG"))
        {
            Bundle bundle = new Bundle();
            bundle.putString("tag", tag);
            navController.navigate(R.id.navigation_mother_profile, bundle);
        }
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);
    }

    private void notifyBabyKick()
    {
        Intent myIntent = new Intent(this, NotifyService.class);
        AlarmManager alarmManager = (AlarmManager)getSystemService( ALARM_SERVICE );
        PendingIntent pendingIntent = PendingIntent.getBroadcast( this, 9000 , myIntent , PendingIntent.FLAG_UPDATE_CURRENT );
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.HOUR, 9);
        calendar.set(Calendar.AM_PM, Calendar.AM);
        calendar.add(Calendar.DAY_OF_MONTH, 1);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP , calendar.getTimeInMillis(),1000 * 60 * 60 * 24, pendingIntent);
        Log.i("Testing Alarm", calendar.getTime().toString()+"");
    }

    private void setAppointmentReminder(Calendar target)
    {
        String fromNotify = "";
        Intent intent = getIntent();
        if(!intent.getStringExtra("reminderNotification").isEmpty())
        {
            fromNotify = intent.getStringExtra("reminderNotification");
        }
        if(fromNotify.equals(""))
        {
            Intent reminderIntent = new Intent(getApplicationContext(), ReminderService.class);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 101, reminderIntent, PendingIntent.FLAG_UPDATE_CURRENT);
            AlarmManager alarmManager = (AlarmManager)getSystemService( ALARM_SERVICE );
            alarmManager.set(AlarmManager.RTC_WAKEUP, target.getTimeInMillis(), pendingIntent);
            Log.i("Testing Alarm", target.getTime().toString()+"");
        }
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
