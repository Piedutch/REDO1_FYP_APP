package com.example.stanley.redo1_fyp_app;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.widget.SwipeRefreshLayout;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;


import android.os.Handler;
import android.widget.Toast;

import static android.provider.BaseColumns._ID;
import static com.example.stanley.redo1_fyp_app.Constants.COUNT;
import static com.example.stanley.redo1_fyp_app.Constants.REFRESHVALUE;
import static com.example.stanley.redo1_fyp_app.Constants.SETTINGS_TABLE_NAME;

public class HomeActivity1 extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,View.OnClickListener {
    private int old_refreshTime = 0;
    private int new_refreshTime = 0;
    private int count = 0;
    private EventsData dbHelper;

    private String TAG = AlertsActivity1.class.getSimpleName();

    //URL of json
    private static String url = "http://128.199.75.229/alertspost.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar2);
        TextView mTitle = (TextView) toolbar.findViewById(R.id.toolbar_title2);
        mTitle.setText("Ostiarius");
        setSupportActionBar(toolbar);


       // new GetContacts().execute();

/*        Button alertbutton = (Button)findViewById(R.id.button_alert);
        alertbutton.setOnClickListener(this);
        Button archivealertbutton = (Button)findViewById(R.id.button_archivealert);
        archivealertbutton.setOnClickListener(this);
        Button maintenancebutton = (Button)findViewById(R.id.button_maintenance);
        maintenancebutton.setOnClickListener(this);
        Button sysdiag = (Button)findViewById(R.id.button_sysdiag);
        sysdiag.setOnClickListener(this);
        Button webcam = (Button)findViewById(R.id.button_webcam);
        webcam.setOnClickListener(this);
        Button statistics = (Button)findViewById(R.id.button_statistics);
        statistics.setOnClickListener(this);*/

//        final SwipeRefreshLayout swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swiperefresh);
//        swipeRefreshLayout.setColorSchemeResources(R.color.refresh,R.color.refresh1,R.color.refresh2);
//        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
//                swipeRefreshLayout.setRefreshing(true);
//                (new Handler()).postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        swipeRefreshLayout.setRefreshing(false);
//                        Intent myIntent = new Intent(getApplicationContext(),HomeActivity1.class);
//                        finish();
//                        startActivity(myIntent);
//                    }
//                }, 100);
//            }
//        });

        dbHelper = new EventsData(this);
        // TO-DO select refreshtime from table
        loadRefreshValue();


        Log.d(TAG, "This is oldValue: " + old_refreshTime);
        Log.d(TAG, "This is count: " + count);


        java.util.Calendar cal = java.util.Calendar.getInstance();
        cal.setTimeInMillis(System.currentTimeMillis());
        Intent intent = new Intent(HomeActivity1.this, GetNotifications.class);
        PendingIntent pi_default = PendingIntent.getService(HomeActivity1.this, 0, intent, 0);
        AlarmManager alarm_default = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent2 = new Intent(HomeActivity1.this, GetSysDiagNotifications.class);
        PendingIntent pi_sysdiag = PendingIntent.getService(HomeActivity1.this, 0, intent2, 0);
        AlarmManager alarm_sysdiag = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        alarm_sysdiag.setRepeating(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), 10800000, pi_sysdiag);
        if (count==0) {
            alarm_default.setRepeating(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), new_refreshTime, pi_default);
            old_refreshTime = new_refreshTime;
            count++;

        }
        Bundle getTime = getIntent().getExtras();
        if (getTime != null){
            new_refreshTime = getTime.getInt("fetchOption");
            Log.d(TAG, "This is newValue: " +new_refreshTime);
            if(new_refreshTime==old_refreshTime){
                Toast.makeText(getApplicationContext(), "Cannot input time interval that is the same as before!", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(HomeActivity1.this, SettingsActivity.class);
                startActivity(i);
            }else if (new_refreshTime!=old_refreshTime){
                Toast.makeText(getApplicationContext(), "Changes saved!", Toast.LENGTH_SHORT).show();
                Log.d(TAG, "Got from bundle extras: " + new_refreshTime);

                PendingIntent pi_opt2 = PendingIntent.getService(HomeActivity1.this, 0, intent, 0);
                AlarmManager alarm_opt2 = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                PendingIntent pi_opt1 = PendingIntent.getService(HomeActivity1.this, 0, intent, 0);
                AlarmManager alarm_opt1 = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
                PendingIntent pi_opt3 = PendingIntent.getService(HomeActivity1.this, 0, intent, 0);
                AlarmManager alarm_opt3 = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
                PendingIntent pi_opt4 = PendingIntent.getService(HomeActivity1.this, 0, intent, 0);
                AlarmManager alarm_opt4 = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
                PendingIntent pi_opt5 = PendingIntent.getService(HomeActivity1.this, 0, intent, 0);
                AlarmManager alarm_opt5 = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
                PendingIntent pi_opt6 = PendingIntent.getService(HomeActivity1.this, 0, intent, 0);
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
                        alarm_default.cancel(pi_default);
                        alarm_opt2.setRepeating(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), new_refreshTime, pi_opt2);
                        old_refreshTime = new_refreshTime;
                        break;
                    case 15000:
                        alarm_opt2.cancel(pi_opt2);
                        alarm_opt3.cancel(pi_opt3);
                        alarm_opt4.cancel(pi_opt4);
                        alarm_opt5.cancel(pi_opt5);
                        alarm_opt6.cancel(pi_opt6);
                        alarm_default.cancel(pi_default);
                        alarm_opt1.setRepeating(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), new_refreshTime, pi_opt1);
                        Log.d(TAG, "I went into 15000");
                        old_refreshTime = new_refreshTime;
                        break;
                    case 300000:
                        alarm_opt2.cancel(pi_opt2);
                        alarm_opt1.cancel(pi_opt1);
                        alarm_opt4.cancel(pi_opt4);
                        alarm_opt5.cancel(pi_opt5);
                        alarm_opt6.cancel(pi_opt6);
                        alarm_default.cancel(pi_default);
                        alarm_opt3.setRepeating(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), new_refreshTime, pi_opt3);
                        old_refreshTime = new_refreshTime;
                        break;
                    case 600000:
                        alarm_opt2.cancel(pi_opt2);
                        alarm_opt3.cancel(pi_opt3);
                        alarm_opt1.cancel(pi_opt1);
                        alarm_opt5.cancel(pi_opt5);
                        alarm_opt6.cancel(pi_opt6);
                        alarm_default.cancel(pi_default);
                        alarm_opt4.setRepeating(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), new_refreshTime, pi_opt4);
                        old_refreshTime = new_refreshTime;
                        break;
                    case 180000:
                        alarm_opt2.cancel(pi_opt2);
                        alarm_opt3.cancel(pi_opt3);
                        alarm_opt4.cancel(pi_opt4);
                        alarm_opt1.cancel(pi_opt1);
                        alarm_opt6.cancel(pi_opt6);
                        alarm_default.cancel(pi_default);
                        alarm_opt5.setRepeating(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), new_refreshTime, pi_opt5);
                        old_refreshTime = new_refreshTime;
                        break;
                    case 3600000:
                        alarm_opt2.cancel(pi_opt2);
                        alarm_opt3.cancel(pi_opt3);
                        alarm_opt4.cancel(pi_opt4);
                        alarm_opt5.cancel(pi_opt5);
                        alarm_opt1.cancel(pi_opt1);
                        alarm_default.cancel(pi_default);
                        alarm_opt6.setRepeating(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), new_refreshTime, pi_opt6);
                        old_refreshTime = new_refreshTime;
                        break;
                }

                //to do update table set refresh value as new
                updateRefreshValue(old_refreshTime);
            }
        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        /*SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
            String Date = sdf.format(new Date());

            TextView overview_alerts = (TextView) findViewById(R.id.overview_alerts);
            overview_alerts.setText(Date);*/
    }
    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.alerts_row:
                startActivity(new Intent(this, AlertsActivity1.class));
                break;
            case R.id.archivealerts_row:
                startActivity(new Intent(this, ArchiveAlerts.class));
                break;
            case R.id.assetsinfo_row:
                startActivity(new Intent(this, MaintenanceMode.class));
                break;
            case R.id.sysdia_row:
                startActivity(new Intent(this, SystemDiagnosticsActivity1.class));
                break;
            case R.id.livestream_row:
                startActivity(new Intent(this, StreamingActivity.class));
                break;
            case R.id.statistics_row:
                startActivity(new Intent(this, StatisticsActivity.class));
                break;
        }}
/*    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_search, menu);
        MenuItem item = menu.findItem(R.id.menuSearch);
        SearchView searchView = (SearchView)item.getActionView();

        searchView .setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }*/

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

/*    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
*//*        if(item.getItemId() == R.id.archive_button){
            Intent myIntent = new Intent(getApplicationContext(),ArchiveAlerts.class);
            startActivity(myIntent);
        }

        return super.onOptionsItemSelected(item);*//*
    }*/

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            Intent myIntent = new Intent(getApplicationContext(),HomeActivity1.class);
            startActivity(myIntent);
        } else if (id == R.id.nav_alerts) {
            Intent myIntent = new Intent(getApplicationContext(),AlertsActivity1.class);
            startActivity(myIntent);
        } else if (id == R.id.nav_archivedalerts) {
            Intent myIntent = new Intent(getApplicationContext(),ArchiveAlerts.class);
            startActivity(myIntent);
        } else if (id == R.id.nav_maintenance) {
            Intent myIntent = new Intent(getApplicationContext(),MaintenanceMode.class);
            startActivity(myIntent);
        } else if (id == R.id.nav_livestream) {
            Intent myIntent = new Intent(getApplicationContext(),StreamingActivity.class);
            startActivity(myIntent);
        } else if (id == R.id.nav_systemdiag) {
            Intent myIntent = new Intent(getApplicationContext(), SystemDiagnosticsActivity1.class);
            startActivity(myIntent);
        }else if (id == R.id.nav_settings) {
            Intent myIntent = new Intent(getApplicationContext(), SettingsActivity.class);
            startActivity(myIntent);
        }else if (id == R.id.nav_about) {
            Intent myIntent = new Intent(getApplicationContext(), AboutActivity.class);
            startActivity(myIntent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    private void loadRefreshValue(){

//        String selectQuery = "Select " + REFRESHVALUE + " from " + SETTINGS_TABLE_NAME;

        SQLiteDatabase db = dbHelper.getReadableDatabase();
//        Cursor cursor = db.rawQuery(selectQuery, null);
        Cursor cursor = db.query(SETTINGS_TABLE_NAME, new String[]{REFRESHVALUE, COUNT}, null, null, null, null, null);

        if(cursor.moveToFirst()){
            do{
                old_refreshTime = cursor.getInt(0);
                count = cursor.getInt(1);
            } while (cursor.moveToNext());
        }

        cursor.close();;
        db.close();

    }

    private void updateRefreshValue(int refreshValue){
        Log.d(TAG, "Here is count at updateRefreshValue: " +count);
        ContentValues cv = new ContentValues();
        cv.put(REFRESHVALUE, refreshValue);
        cv.put(COUNT, count);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.update(SETTINGS_TABLE_NAME, cv, _ID+"=1", null);
        db.close();
        Toast.makeText(this, "oldvalue: " +old_refreshTime +" = newvalue: " +new_refreshTime , Toast.LENGTH_SHORT).show();
    }
}
