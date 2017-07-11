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
import android.view.View;
import android.widget.TextView;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;

/**
 * Created by Stanley on 9/6/2017.
 */

public class HomeActivity extends Activity implements View.OnClickListener{
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
        Intent intent = new Intent(HomeActivity.this, GetNotifications.class);
        PendingIntent pintent = PendingIntent.getService(HomeActivity.this, 0, intent, 0);
        AlarmManager alarm = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
//        alarm.setRepeating(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), 60*1000, pintent);
        alarm.setRepeating(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), 300, pintent);

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
