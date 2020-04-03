package com.example.mommyhealthapp;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;

import androidx.core.app.NotificationCompat;

import com.example.mommyhealthapp.Mommy.KickCounterActivity;

public class AlertService extends BroadcastReceiver {
    public static final String NOTIFICATION_CHANNEL_ID = "10003" ;
    private final static String default_notification_channel_id = "default" ;
    @Override
    public void onReceive(Context context, Intent intent) {
        Intent notificationIntent = new Intent(context, KickCounterActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, notificationIntent, 0);
        NotificationManager mNotificationManager = (NotificationManager) context.getSystemService( context.NOTIFICATION_SERVICE ) ;
        Uri defaultSoundUri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context , default_notification_channel_id ) ;
        mBuilder.setContentTitle("Alert");
        mBuilder.setContentIntent(pendingIntent);
        mBuilder.setContentText("Baby kick count not enough 10, please contact doctor");
        mBuilder.setSmallIcon(R.mipmap.ic_launcher);
        mBuilder.setSound(defaultSoundUri);
        mBuilder.setAutoCancel(true);
        if (android.os.Build.VERSION. SDK_INT >= android.os.Build.VERSION_CODES. O ) {
            int importance = NotificationManager. IMPORTANCE_HIGH ;
            NotificationChannel notificationChannel = new
                    NotificationChannel( NOTIFICATION_CHANNEL_ID , "NOTIFICATION_CHANNEL_NAME" , importance) ;
            mBuilder.setChannelId( NOTIFICATION_CHANNEL_ID ) ;
            mNotificationManager.createNotificationChannel(notificationChannel) ;
        }
        mNotificationManager.notify(( int ) System. currentTimeMillis () , mBuilder.build()) ;
        throw new UnsupportedOperationException( "Not yet implemented" ) ;
    }
}
