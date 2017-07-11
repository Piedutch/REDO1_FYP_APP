package com.example.stanley.redo1_fyp_app;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

import com.google.firebase.messaging.RemoteMessage;

/**
 * Created by jonat on 07/10/17.
 */

public class FirebaseMessagingService extends com.google.firebase.messaging.FirebaseMessagingService {

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage){

        showNotification(remoteMessage.getData().get("message"));
    }

    private void showNotification(String message){

        Intent i = new Intent(this, AlertsActivity1.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        PendingIntent pendingIntent = PendingIntent.getActivity(this,0,i,PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                .setAutoCancel(true)
                .setContentTitle("Alert!")
                .setTicker("Alert! Watch Out!")
                .setContentInfo("item/item(s) have been taken out")
                .setContentText(message)
                .setSmallIcon(R.drawable.favicon)
                .setContentIntent(pendingIntent)
                .setDefaults(Notification.DEFAULT_ALL)
                .setPriority(Notification.PRIORITY_HIGH);
        //https://stackoverflow.com/questions/39174818/vibrate-on-push-notification

        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        manager.notify(0, builder.build());
    }
}
