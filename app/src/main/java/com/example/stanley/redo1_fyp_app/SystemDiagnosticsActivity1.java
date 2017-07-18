package com.example.stanley.redo1_fyp_app;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.Handler;

public class SystemDiagnosticsActivity1 extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private String TAG = SystemDiagnosticsActivity1.class.getSimpleName();

    private static String urlParams = "http://128.199.75.229/status_app.php";
    private ImageView tempStatus_status, cameraStatus_status, armStatus_status, gpuStatus_status;
    private TextView tempStatus_tv, cameraStatus_tv, armStatus_tv, gpuStatus_tv;
    private String tempInput, cameraInput, armInput, gpuInput;

    private EventsData events;

    //URL of json
    private static String url = "http://128.199.75.229/items.php";


    private SimpleAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sysdiag_toolbar);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar1);
        TextView mTitle = (TextView) toolbar.findViewById(R.id.toolbar_title1);
        mTitle.setText("System Diagnostics");
        //  toolbar.setTitle("Yo it works");
        setSupportActionBar(toolbar);

        //Initialize
        tempStatus_status = (ImageView) findViewById(R.id.tempStatus_status);
        cameraStatus_status = (ImageView) findViewById(R.id.cameraStatus_status);
        armStatus_status = (ImageView) findViewById(R.id.armStatus_status);
        gpuStatus_status = (ImageView) findViewById(R.id.gpuStatus_status);

        tempStatus_tv = (TextView) findViewById(R.id.tempStatus_data);
        cameraStatus_tv = (TextView) findViewById(R.id.cameraStatus_data);
        armStatus_tv = (TextView) findViewById(R.id.armStatus_data);
        gpuStatus_tv = (TextView) findViewById(R.id.gpuStatus_data);

        Bitmap bMap = BitmapFactory.decodeResource(getResources(), R.mipmap.status);
        Bitmap bMapScaled = Bitmap.createScaledBitmap(bMap, 75, 75, true);
        tempStatus_status.setImageBitmap(bMapScaled);
        cameraStatus_status.setImageBitmap(bMapScaled);
        armStatus_status.setImageBitmap(bMapScaled);
        gpuStatus_status.setImageBitmap(bMapScaled);

        tempStatus_status.setColorFilter(getResources().getColor(R.color.unavailable));
        cameraStatus_status.setColorFilter(getResources().getColor(R.color.unavailable));
        armStatus_status.setColorFilter(getResources().getColor(R.color.unavailable));
        gpuStatus_status.setColorFilter(getResources().getColor(R.color.unavailable));

        new GetParams().execute();

        final SwipeRefreshLayout swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swiperefresh);
        swipeRefreshLayout.setColorSchemeResources(R.color.refresh,R.color.refresh1,R.color.refresh2);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(true);
                (new Handler()).postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        swipeRefreshLayout.setRefreshing(false);
                        Intent myIntent = new Intent(getApplicationContext(),SystemDiagnosticsActivity1.class);
                        finish();
                        startActivity(myIntent);
                    }
                }, 100);
            }
        });


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

/*    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.search, menu);
        MenuItem item = menu.findItem(R.id.menuSearch1);
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

    private class GetParams extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPostExecute(Void aVoid) {
            setParams();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            HttpHandler sh = new HttpHandler();
            String jsonStr = sh.makeServiceCall(urlParams);
            Log.e(TAG, "Response from url: " + jsonStr);

            if (jsonStr != null) {
                try {
                    JSONObject jsonObject = new JSONObject(jsonStr);
                    JSONArray params = jsonObject.getJSONArray("Status");

                    for (int i = 0; i < params.length(); i++) {
                        JSONObject p = params.getJSONObject(i);

                        tempInput = p.getString("temp_status");
                        cameraInput = p.getString("camera_status");
                        armInput = p.getString("arm_usage");
                        gpuInput = p.getString("gpu_usage");
                        Log.d(TAG, "Value of Camera input: " +cameraInput);
                    }

                }catch (final JSONException e) {
                    Log.e(TAG, "JSON parsing error: " + e.getMessage());
                }
            } else if (jsonStr.equals(null)){
//                AlertDialog alertDialog = new AlertDialog.Builder(SystemDiagnosticsActivity.this).create();
//                alertDialog.setTitle("Information");
//                alertDialog.setMessage("No information retrieved. Is your pi turned on? \nPlease try again");
//                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        dialog.dismiss();
//                        Intent i = new Intent(SystemDiagnosticsActivity.this, HomeActivity.class);
//                        startActivity(i);
//                    }
//                });
//                alertDialog.show();
            }

            return null;
        }
    }

    private void setParams() {

        if(tempInput!=null){
            tempStatus_tv.setText(tempInput);
            tempStatus_status.setColorFilter(getResources().getColor(R.color.available));
        }

        if(cameraInput!=null){
            cameraStatus_tv.setText(cameraInput);
            if(cameraInput.equals("Online")){
                cameraStatus_status.setColorFilter(getResources().getColor(R.color.available));
            }
        }

        if(armInput!=null){
            armStatus_tv.setText(armInput);
            armStatus_status.setColorFilter(getResources().getColor(R.color.available));
        }

        if(gpuInput!=null){
            gpuStatus_tv.setText(gpuInput);
            gpuStatus_status.setColorFilter(getResources().getColor(R.color.available));
        }
    }

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
        if(item.getItemId() == R.id.archive_button){
            Intent myIntent = new Intent(getApplicationContext(),ArchiveAlerts.class);
            startActivity(myIntent);
        }

        return super.onOptionsItemSelected(item);
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
}
