package com.example.stanley.redo1_fyp_app;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.icu.util.Calendar;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;

/**
 * Created by Stanley on 9/6/2017.
 */

public class HomeActivity extends Activity implements View.OnClickListener{
    private int old_refreshTime = 60000;
    private int new_refreshTime = 0;
    private String TAG = HomeActivity.class.getSimpleName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        View homeicon = findViewById(R.id.home_icon);
        homeicon.setOnClickListener(this);
        View hometext = findViewById(R.id.clickhome);
        hometext.setOnClickListener(this);

        View alertsicon = findViewById(R.id.alertsicon);
        alertsicon.setOnClickListener(this);
        View alertstext = findViewById(R.id.clickalerts);
        alertstext.setOnClickListener(this);

        View settingsicon = findViewById(R.id.settingsicon);
        settingsicon.setOnClickListener(this);
        View settingstext = findViewById(R.id.clicksettings);
        settingstext.setOnClickListener(this);

        View searchicon = findViewById(R.id.searchicon);
        searchicon.setOnClickListener(this);
        View searchtext = findViewById(R.id.clicksearch);
        searchtext.setOnClickListener(this);

        java.util.Calendar cal = java.util.Calendar.getInstance();
        cal.setTimeInMillis(System.currentTimeMillis());
        Log.d(TAG, "Currently refreshTime value is: " +old_refreshTime);
        Bundle getTime = getIntent().getExtras();
        if (getTime != null){
            new_refreshTime = getTime.getInt("fetchOption");
            if(new_refreshTime==old_refreshTime){
                Toast.makeText(getApplicationContext(), "Cannot input time interval that is the same as before!", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(HomeActivity.this, SettingsActivity.class);
                startActivity(i);
            }else {
                Toast.makeText(getApplicationContext(), "Changes saved!", Toast.LENGTH_SHORT).show();
                Log.d(TAG, "Got from bundle extras: " + new_refreshTime);

                Intent intent = new Intent(HomeActivity.this, GetNotifications.class);
                PendingIntent pi_opt2 = PendingIntent.getService(HomeActivity.this, 0, intent, 0);
                AlarmManager alarm_opt2 = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                PendingIntent pi_opt1 = PendingIntent.getService(HomeActivity.this, 0, intent, 0);
                AlarmManager alarm_opt1 = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
                PendingIntent pi_opt3 = PendingIntent.getService(HomeActivity.this, 0, intent, 0);
                AlarmManager alarm_opt3 = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
                PendingIntent pi_opt4 = PendingIntent.getService(HomeActivity.this, 0, intent, 0);
                AlarmManager alarm_opt4 = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
                PendingIntent pi_opt5 = PendingIntent.getService(HomeActivity.this, 0, intent, 0);
                AlarmManager alarm_opt5 = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
                PendingIntent pi_opt6 = PendingIntent.getService(HomeActivity.this, 0, intent, 0);
                AlarmManager alarm_opt6 = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
//                boolean isWorking_opt1 = (PendingIntent.getService(HomeActivity.this, 0, intent, PendingIntent.FLAG_NO_CREATE) != null);
//                boolean isWorking_opt2 = (PendingIntent.getService(HomeActivity.this, 0, intent, PendingIntent.FLAG_NO_CREATE) != null);
//                boolean isWorking_opt3 = (PendingIntent.getService(HomeActivity.this, 0, intent, PendingIntent.FLAG_NO_CREATE) != null);
//                boolean isWorking_opt4 = (PendingIntent.getService(HomeActivity.this, 0, intent, PendingIntent.FLAG_NO_CREATE) != null);
//                boolean isWorking_opt5 = (PendingIntent.getService(HomeActivity.this, 0, intent, PendingIntent.FLAG_NO_CREATE) != null);
//                boolean isWorking_opt6 = (PendingIntent.getService(HomeActivity.this, 0, intent, PendingIntent.FLAG_NO_CREATE) != null);


                switch(new_refreshTime){
                    case 60000:

                        alarm_opt1.cancel(pi_opt1);
                        alarm_opt3.cancel(pi_opt3);
                        alarm_opt4.cancel(pi_opt4);
                        alarm_opt5.cancel(pi_opt5);
                        alarm_opt6.cancel(pi_opt6);
                        alarm_opt2.setRepeating(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), new_refreshTime, pi_opt2);
                        new_refreshTime = old_refreshTime;
                        break;
                    case 15000:
                        alarm_opt2.cancel(pi_opt2);
                        alarm_opt3.cancel(pi_opt3);
                        alarm_opt4.cancel(pi_opt4);
                        alarm_opt5.cancel(pi_opt5);
                        alarm_opt6.cancel(pi_opt6);
                        alarm_opt1.setRepeating(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), new_refreshTime, pi_opt1);
                        new_refreshTime = old_refreshTime;
                        break;
                    case 300000:
                        alarm_opt2.cancel(pi_opt2);
                        alarm_opt1.cancel(pi_opt1);
                        alarm_opt4.cancel(pi_opt4);
                        alarm_opt5.cancel(pi_opt5);
                        alarm_opt6.cancel(pi_opt6);
                        alarm_opt3.setRepeating(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), new_refreshTime, pi_opt3);
                        new_refreshTime = old_refreshTime;
                        break;
                    case 600000:
                        alarm_opt2.cancel(pi_opt2);
                        alarm_opt3.cancel(pi_opt3);
                        alarm_opt1.cancel(pi_opt1);
                        alarm_opt5.cancel(pi_opt5);
                        alarm_opt6.cancel(pi_opt6);
                        alarm_opt4.setRepeating(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), new_refreshTime, pi_opt4);
                        new_refreshTime = old_refreshTime;
                        break;
                    case 180000:
                        alarm_opt2.cancel(pi_opt2);
                        alarm_opt3.cancel(pi_opt3);
                        alarm_opt4.cancel(pi_opt4);
                        alarm_opt1.cancel(pi_opt1);
                        alarm_opt6.cancel(pi_opt6);
                        alarm_opt5.setRepeating(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), new_refreshTime, pi_opt5);
                        new_refreshTime = old_refreshTime;
                        break;
                    case 3600000:
                        alarm_opt2.cancel(pi_opt2);
                        alarm_opt3.cancel(pi_opt3);
                        alarm_opt4.cancel(pi_opt4);
                        alarm_opt5.cancel(pi_opt5);
                        alarm_opt1.cancel(pi_opt1);
                        alarm_opt6.setRepeating(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), new_refreshTime, pi_opt6);
                        new_refreshTime = old_refreshTime;
                        break;
                }
            }
        }

//        Intent intent = new Intent(HomeActivity.this, GetNotifications.class);
//        PendingIntent pintent = PendingIntent.getService(HomeActivity.this, 0, intent, 0);
//        AlarmManager alarm = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
////        alarm.setRepeating(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), 60*1000, pintent);
//        alarm.setRepeating(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), refreshTime, pintent);
//        Bundle getTime = getIntent().getExtras();
//            if (getTime != null){
//                Toast.makeText(getApplicationContext(), "Changes saved!", Toast.LENGTH_SHORT).show();
//                refreshTime = getTime.getInt("fetchOption");
//                Log.d(TAG, "Got from bundle extras: " + refreshTime);
//            }
//            if(refreshTime!=60000){
//                alarm.cancel(pintent);
//                PendingIntent updatedIntent = PendingIntent.getService(HomeActivity.this, 0, intent, 0);
//                AlarmManager updatedAlarm = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
//                updatedAlarm.setRepeating(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), refreshTime, updatedIntent);
//            }


            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
            String Date = sdf.format(new Date());

            TextView overview_alerts = (TextView) findViewById(R.id.overview_alerts);
            overview_alerts.setText(Date);


//        FirebaseMessaging.getInstance().subscribeToTopic("test");
//        FirebaseInstanceId.getInstance().getToken();
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){

            case R.id.home_icon:
                startActivity(new Intent(this, MainActivity.class));
                break;

            case R.id.clickhome:
                startActivity(new Intent(this, MainActivity.class));
                break;

            case R.id.alertsicon:
                startActivity(new Intent(this, AlertsActivity1.class));
                break;

            case R.id.clickalerts:
                startActivity(new Intent(this, AlertsActivity1.class));
                break;

            case R.id.settingsicon:
//                startActivity(new Intent(this, SettingsActivity.class));
                break;

            case R.id.clicksettings:
//                startActivity(new Intent(this, SettingsActivity.class));
                break;

            case R.id.searchicon:
                startActivity(new Intent(this, StreamingActivity.class));
                break;

            case R.id.clicksearch:
//                startActivity(new Intent(this, SearchActivity.class));
                break;

        }
    }
}
