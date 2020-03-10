package com.example.mommyhealthapp;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.IBinder;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import com.example.mommyhealthapp.Mommy.MommyHomeActivity;

public class ReminderService extends Service {
    public static final String REMINDER_CHANNEL_ID = "10001" ;
    private final static String default_reminder_channel_id = "default" ;

    @Override
    public void onCreate() {
        Intent notificationIntent = new Intent(getApplicationContext(), MommyHomeActivity.class);
        notificationIntent.putExtra( "fromNotification" , true );
        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);
        NotificationManager mNotificationManager = (NotificationManager) getSystemService( NOTIFICATION_SERVICE ) ;
        Uri defaultSoundUri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(getApplicationContext() , default_reminder_channel_id ) ;
        mBuilder.setContentTitle("Appointment Reminder");
        mBuilder.setContentIntent(pendingIntent);
        mBuilder.setContentText("Today is the Appointment Date");
        mBuilder.setSmallIcon(R.mipmap.ic_launcher);
        mBuilder.setSound(defaultSoundUri);
        mBuilder.setAutoCancel(true);
        if (android.os.Build.VERSION. SDK_INT >= android.os.Build.VERSION_CODES.O ) {
            int importance = NotificationManager. IMPORTANCE_HIGH ;
            NotificationChannel notificationChannel = new
                    NotificationChannel( REMINDER_CHANNEL_ID , "NOTIFICATION_CHANNEL_NAME" , importance) ;
            mBuilder.setChannelId( REMINDER_CHANNEL_ID ) ;
            mNotificationManager.createNotificationChannel(notificationChannel) ;
        }
        mNotificationManager.notify(( int ) System. currentTimeMillis () , mBuilder.build()) ;
        throw new UnsupportedOperationException( "Not yet implemented" ) ;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
