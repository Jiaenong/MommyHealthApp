package com.example.mommyhealthapp;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.IBinder;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import com.example.mommyhealthapp.Mommy.MommyHomeActivity;

public class ReminderService extends BroadcastReceiver {
    public static final String REMINDER_CHANNEL_ID = "10001" ;
    private final static String default_reminder_channel_id = "default" ;

    @Override
    public void onReceive(Context context, Intent intent) {
        Intent notificationIntent = new Intent(context, MommyHomeActivity.class);
        notificationIntent.putExtra( "reminderNotification" , "FromNotify");
        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, notificationIntent, 0);
        NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(context.NOTIFICATION_SERVICE) ;
        Uri defaultSoundUri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context , default_reminder_channel_id ) ;
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
}
