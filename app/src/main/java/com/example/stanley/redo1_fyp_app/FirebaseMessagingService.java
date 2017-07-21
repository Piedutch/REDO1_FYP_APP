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

        Intent i_alerts = new Intent(this, AlertsActivity1.class);
        i_alerts.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        Intent i_sysdiag = new Intent(this, SystemDiagnosticsActivity.class);
        i_sysdiag.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntentSysDiag = PendingIntent.getActivity(this,0,i_sysdiag,PendingIntent.FLAG_UPDATE_CURRENT);
        PendingIntent pendingIntentAlerts = PendingIntent.getActivity(this,0,i_alerts,PendingIntent.FLAG_UPDATE_CURRENT);

        if(message.equals("Setup error, please check and contact Admin!")) {
            NotificationCompat.Builder SysDiagbuilder = new NotificationCompat.Builder(this)
                    .setAutoCancel(true)
                    .setContentTitle("Alert!")
                    .setTicker("Alert! Watch Out!")
                    .setContentInfo("Problem(s) with setup")
                    .setContentText(message)
                    .setSmallIcon(R.drawable.favicon)
                    .setContentIntent(pendingIntentSysDiag)
                    .setDefaults(Notification.DEFAULT_ALL)
                    .setPriority(Notification.PRIORITY_HIGH);
            NotificationManager SysDiagmanager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

            SysDiagmanager.notify(0, SysDiagbuilder.build());
            //https://stackoverflow.com/questions/39174818/vibrate-on-push-notification
        } else{
            NotificationCompat.Builder alerts_builder = new NotificationCompat.Builder(this)
                    .setAutoCancel(true)
                    .setContentTitle("Alert!")
                    .setTicker("Alert! Watch Out!")
                    .setContentInfo("Item/ items have been taken")
                    .setContentText(message)
                    .setSmallIcon(R.drawable.favicon)
                    .setContentIntent(pendingIntentAlerts)
                    .setDefaults(Notification.DEFAULT_ALL)
                    .setPriority(Notification.PRIORITY_HIGH);
            //https://stackoverflow.com/questions/39174818/vibrate-on-push-notification
            NotificationManager alerts_manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

            alerts_manager.notify(0, alerts_builder.build());
        }

    }
}
