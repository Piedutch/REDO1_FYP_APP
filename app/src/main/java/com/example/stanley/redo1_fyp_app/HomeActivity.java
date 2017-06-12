package com.example.stanley.redo1_fyp_app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

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
                startActivity(new Intent(this, AlertsActivity.class));
                break;

            case R.id.clickalerts:
                startActivity(new Intent(this, AlertsActivity.class));
                break;

            case R.id.settingsicon:
//                startActivity(new Intent(this, SettingsActivity.class));
                break;

            case R.id.clicksettings:
//                startActivity(new Intent(this, SettingsActivity.class));
                break;

            case R.id.searchicon:
//                startActivity(new Intent(this, SearchActivity.class));
                break;

            case R.id.clicksearch:
//                startActivity(new Intent(this, SearchActivity.class));
                break;

        }
    }
}
