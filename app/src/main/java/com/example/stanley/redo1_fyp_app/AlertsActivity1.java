package com.example.stanley.redo1_fyp_app;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.util.Base64;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.TextureView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.RunnableFuture;

import android.os.Handler;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;

import static android.provider.BaseColumns._ID;
import static com.example.stanley.redo1_fyp_app.Constants.Alert_No1;
import static com.example.stanley.redo1_fyp_app.Constants.Asset_No1;
import static com.example.stanley.redo1_fyp_app.Constants.Date1;
import static com.example.stanley.redo1_fyp_app.Constants.Item_Name1;
import static com.example.stanley.redo1_fyp_app.Constants.TABLE_NAME;
import static com.example.stanley.redo1_fyp_app.Constants.Time1;

public class AlertsActivity1 extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private String TAG = AlertsActivity1.class.getSimpleName();

    private ProgressDialog pDialog;
    private SwipeMenuListView lv;
    private String alert_no1;
    private String globalvar_alertno;
    private String globalvar_assetno;
    private String globalvar_itemname;
    private String globalvar_time;
    private String globalvar_date;
    private String globalvar_maintenance;

    private long id_;
    private String alertno_;
    private String assetno_;
    private String itemname_;
    private String time_;
    private String date_;
    String photobase64;
    String encodeImage;
    Bitmap bitmap;
    byte[] decodeString;

    private static String[] FROM= {_ID, Alert_No1, Asset_No1, Item_Name1, Time1,Date1};
    private static String ORDER_BY = Alert_No1 + " DESC";
    private EventsData events;

    //URL of json
    private static String url = "http://128.199.75.229/alertspost.php";


    ArrayList<HashMap<String, String>> contactList;
    List<String> alertnolist=new ArrayList<String>();
    List<String> assetnolist=new ArrayList<String>();
    List<String> itemnamelist=new ArrayList<String>();
    List<String> timelist=new ArrayList<String>();
    List<String> datelist=new ArrayList<String>();
    List<String> picturelist=new ArrayList<String>();
    List<String> maintenancelist=new ArrayList<String>();

    private SimpleAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alerts1);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar1);
        TextView mTitle = (TextView) toolbar.findViewById(R.id.toolbar_title1);
        mTitle.setText("Alerts");
        //  toolbar.setTitle("Yo it works");
        setSupportActionBar(toolbar);

        //DB
        //Archive = new DatabaseEventsData(this);
        events = new EventsData(this);
        try{
            // Try to retrieve object cursor from database
            //removeEvent("2");
            Cursor cursor = getEvents();
            showEvents(cursor);
            //this.deleteDatabase("events1.db");
        }finally{
            events.close();
        }

        contactList = new ArrayList<>();

       //lv = (ListView) findViewById(R.id.listView_alerts1);
        lv = (SwipeMenuListView) findViewById(R.id.listView_alerts1);

        new GetContacts().execute();

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
                        Intent myIntent = new Intent(getApplicationContext(),AlertsActivity1.class);
                        finish();
                        startActivity(myIntent);
                    }
                }, 100);
            }
        });

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position==position){

                    // get your itemnamelist here
                    String name;
                    for(int j  = 0; j < itemnamelist.size(); j++){
                        if(j == position){
                            name = itemnamelist.get(position);
                            globalvar_itemname = name;
                            break;
                        }
                    }

                    //Toast.makeText(AlertsActivity1.this,""+position, Toast.LENGTH_SHORT).show();
                    Intent myIntent = new Intent(getApplicationContext(),PicNDescActivity.class);
                    TextView asset = (TextView)view.findViewById(R.id.asset);  //initialize text view withid label
                    //TextView descriptionalert = (TextView)view.findViewById(R.id.description_alerts);  //initialize text view withid label
                    TextView time = (TextView)view.findViewById(R.id.timestamp_alerts);  //initialize text view withid label
                    TextView alert_no = (TextView)view.findViewById(R.id.alert_no);
                    TextView date = (TextView)view.findViewById(R.id.date);

                    String asset1 = asset.getText().toString(); //get the text
                    String descriptionalert1 = globalvar_itemname; //get the text
                    String time1 = time.getText().toString(); //get the text
                    String alert_no1= alert_no.getText().toString();
                    String date1 = date.getText().toString();

                    myIntent.putExtra("asset1",asset1);
                    myIntent.putExtra("descriptionalert1",descriptionalert1);
                    myIntent.putExtra("time1",time1);
                    myIntent.putExtra("alert_no1",alert_no1);
                    myIntent.putExtra("date1",date1);

                    startActivity(myIntent);
                }
            }
        });

        SwipeMenuCreator creator = new SwipeMenuCreator() {

            @Override
            public void create(SwipeMenu menu) {
/*                // create "delete" item
                SwipeMenuItem deleteItem = new SwipeMenuItem(
                        getApplicationContext());
                // set item background
                deleteItem.setBackground(new ColorDrawable(Color.rgb(0xF9,
                        0x3F, 0x25)));
                // set item width
                deleteItem.setWidth(170);
                // set a icon
                deleteItem.setIcon(R.mipmap.ic_trash);
                // add to menu
                menu.addMenuItem(deleteItem);*/

                // create "delete" item
                SwipeMenuItem archiveItem = new SwipeMenuItem(
                        getApplicationContext());
                // set item background
                archiveItem.setBackground(new ColorDrawable(Color.rgb(0x41,
                        0x69, 0xE1)));
                // set item width
                archiveItem.setWidth(170);
                // set a icon
                archiveItem.setIcon(R.mipmap.ic_archive);
                // add to menu
                menu.addMenuItem(archiveItem);
            }
        };
        lv.setMenuCreator(creator);
        lv.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
                switch (index) {
/*                    case 0:

                        *//*for(final int item1:testlist){
                            //System.out.println(item);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(AlertsActivity1.this,
                                            "Toast for yes"+ item1,
                                            Toast.LENGTH_LONG).show();
                                }
                            });
                        }*//*

                        final String appo;
                        if(position==position){
                            for(int j  = 0; j < alertnolist.size(); j++){
                                if(j == position){
                                    appo = alertnolist.get(position);
                                    globalvar_alertno = appo;
                                    break;
                                }
                            }
                        }

                        Log.d(TAG, "onMenuItemClick: clicked item " + index);

                        AlertDialog.Builder a_builder = new AlertDialog.Builder(AlertsActivity1.this, R.style.YourDialogStyle);
                        a_builder.setMessage("Are you sure you want to delete this alert?").setCancelable(false)

                                .setNegativeButton("Yes", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        //finish();
                                        Response.Listener<String> responseListener = new Response.Listener<String>(){

                                            @Override
                                            public void onResponse(String response){
                                                try{
                                                    JSONObject jsonResponse = new JSONObject(response);
                                                    boolean success = jsonResponse.getBoolean("success");

                                                    if(success){
                                                        runOnUiThread(new Runnable() {
                                                            @Override
                                                            public void run() {
                                                                Toast.makeText(AlertsActivity1.this,
                                                                        "Successfully deleted!",
                                                                        Toast.LENGTH_LONG).show();

                                                                Intent myIntent = new Intent(getApplicationContext(),AlertsActivity1.class);
                                                                finish();
                                                                startActivity(myIntent);


                                                            }
                                                        });
                                                    } else {
                                                        runOnUiThread(new Runnable() {
                                                            @Override
                                                            public void run() {
                                                                Toast.makeText(AlertsActivity1.this,
                                                                        "Not successful",
                                                                        Toast.LENGTH_LONG).show();
                                                            }
                                                        });
                                                    }
                                                }catch (JSONException e){
                                                    e.printStackTrace();
                                                }
                                            }
                                        };
                                        DeleteAlertRequest deleteAlertRequest = new DeleteAlertRequest(globalvar_alertno, responseListener);
                                        RequestQueue queue = Volley.newRequestQueue(AlertsActivity1.this);
                                        queue.add(deleteAlertRequest);

                                    }
                                })
                                .setPositiveButton("No", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.cancel();

                                    }
                                });
                        AlertDialog alert = a_builder.create();
                        alert.setTitle("Warning!");
                        alert.show();
                        break;*/

                    case 0:
                        // This is for archive
                        if(position==position){

                            for(int j  = 0; j < alertnolist.size(); j++){
                                if(j == position){
                                    globalvar_alertno = alertnolist.get(position);
                                    break;
                                }
                            }
                            for(int j  = 0; j < assetnolist.size(); j++){
                                if(j == position){
                                    globalvar_assetno = assetnolist.get(position);
                                    break;
                                }
                            }
                            for(int j  = 0; j < itemnamelist.size(); j++){
                                if(j == position){
                                    globalvar_itemname = itemnamelist.get(position);
                                    break;
                                }
                            }
                            for(int j  = 0; j < timelist.size(); j++){
                                if(j == position){
                                    globalvar_time = timelist.get(position);
                                    break;
                                }
                            }
                            for(int j  = 0; j < datelist.size(); j++){
                                if(j == position){
                                    globalvar_date = datelist.get(position);
                                    break;
                                }
                            }
                            // Retrieving Picture from URL
                            new GetPhoto().execute();
                        }
                }
                return false;
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
    private void addEvent (byte[] image){
        //Insert a new record into the Events data source.
        // You would do something similar for delete and update
        SQLiteDatabase db = events.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Alert_No1, globalvar_alertno);
        values.put(Asset_No1, globalvar_assetno);
        values.put(Item_Name1, globalvar_itemname);
        values.put(Time1, globalvar_time);
        values.put(Date1,globalvar_date);
        values.put("Image1",image);
        db.insertOrThrow(TABLE_NAME, null, values);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(AlertsActivity1.this,
                        "Successfully added to archive",
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    private Cursor getEvents(){
        // Perform a managed query. The Activity will handle closing
        // and re-querying the cursor when needed.
        SQLiteDatabase db = events.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME, FROM, null, null, null,
                null, ORDER_BY);
        return cursor;
    }
    private void showEvents(Cursor cursor) {
        // Stuff them all into a big string
        StringBuilder builder = new StringBuilder("Saved events: \n");


        while (cursor.moveToNext()){
            //Could use getColumnIndexOrThrow() to get indexes
            id_ = cursor.getLong(0);
            alertno_ = cursor.getString(1);
            assetno_ = cursor.getString(2);
            itemname_ = cursor.getString(3);
            time_ = cursor.getString(4);
            date_ = cursor.getString(5);

            /*runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(MainActivity.this,
                            "Database:" + id_ + "\n" + alertno_ + "\n" + assetno_+ "\n" + itemname_ + "\n" + time_ + "\n" + date_ + "\n",
                            Toast.LENGTH_LONG).show();
                }
            });*/
        }
    }
    @Override
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
    }

    private class GetContacts extends AsyncTask<Void, Void, Void>{
        @Override
        protected void onPreExecute(){
            super.onPreExecute();
            //show loading dialog
            pDialog = new ProgressDialog(AlertsActivity1.this);
            pDialog.setMessage("loading...");
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected Void doInBackground(Void... voids){
            HttpHandler sh = new HttpHandler();
            String jsonStr = sh.makeServiceCall(url);
            Log.e(TAG, "Response from url: "+ jsonStr);

            if(jsonStr != null){
                try{
                    JSONObject jsonObject = new JSONObject(jsonStr);

                    //getting json array node
                    JSONArray contacts  = jsonObject.getJSONArray("Alerts");

                    //looping through all contacts
                    for(int i =0; i< contacts.length(); i++){
                        JSONObject c = contacts.getJSONObject(i);

                        String alert_no = c.getString("id");
                        String time = c.getString("time");
                        String item_name = c.getString("item_name");
                        String asset_no = c.getString("asset_no");
                        String date = c.getString("date");
                        String maintenance_mode = c.getString("maintenance_mode");

                        if(maintenance_mode.equals("0")){
                            maintenance_mode = "";
                        }
                        if(maintenance_mode.equals("1")){
                            maintenance_mode = "Under Maintenance";
                        }

                        try {
                            SimpleDateFormat _24HourSDF = new SimpleDateFormat("HH:mm:ss");
                            SimpleDateFormat _12HourSDF = new SimpleDateFormat("hh:mm:ss a");

                            Date _24HourDt = _24HourSDF.parse(time);
                            time = _12HourSDF.format(_24HourDt);

                        }catch(final Exception e){
                            e.printStackTrace();
                        }

                        //  time = time.substring(9, 12);
                        String PMnAM = time.substring(9, 12);

                        if(PMnAM.equals("a.m")){
                            time = time.substring(0,8);
                            time = time + " AM";
                        } else {
                            time = time.substring(0,8);
                            time = time + " PM";
                        }

                        itemnamelist.add(item_name);

                        item_name = item_name+""; //30 will excceed //5 Fs to 28
                        if(item_name.length() > 28){
                            item_name = item_name.substring(0,28);
                        }

                        //Phone node is JSON object
/*                        JSONObject phone = c.getJSONObject("phone");
                        String mobile = phone.getString("mobile");
                        String home = phone.getString("home");
                        String office = phone.getString("office");*/

                        HashMap<String, String> contact = new HashMap<>();

                        //adding each child node to hashmap
                        maintenancelist.add(maintenance_mode);
                        alertnolist.add(alert_no);
                        assetnolist.add(asset_no);
                        timelist.add(time);
                        datelist.add(date);
                        contact.put("time",time);
                        contact.put("item_name",item_name);
                        contact.put("asset_no",asset_no);
                        contact.put("date",date);
                        contact.put("alert_no",alert_no);
                        contact.put("maintenance_mode",maintenance_mode);

                        //adding contact to contact list
                        contactList.add(contact);
                    }
                }catch(final JSONException e){
                    Log.e(TAG, "JSON parsing error: " + e.getMessage());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(AlertsActivity1.this,
                                    "JSON parsing error: " + e.getMessage(),
                                    Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            } else {
                Log.e(TAG, "Couldn't get json from server.");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(AlertsActivity1.this,
                                "Couldn't get json from server.",
                                Toast.LENGTH_SHORT).show();
                    }
                });
            }

            return null;
        }
        @Override
        protected void onPostExecute(Void aVoid){
            super.onPostExecute(aVoid);
            //Dismiss the dialog
            if(pDialog.isShowing()){
                pDialog.dismiss();
            }
            //updating json data to listview
            adapter = new SimpleAdapter(
                    AlertsActivity1.this, contactList,
                    R.layout.list_item, new String[]{"time","item_name","asset_no","alert_no","date","maintenance_mode"},
                    new int[]{R.id.timestamp_alerts, R.id.description_alerts, R.id.asset,R.id.alert_no,R.id.date,R.id.maintenancestatus1});
            lv.setAdapter(adapter);
        }
    }

    private class GetPhoto extends AsyncTask<Void, Void, Void>{
        @Override
        protected void onPreExecute(){
            super.onPreExecute();
            //show loading dialog
            pDialog = new ProgressDialog(AlertsActivity1.this);
            pDialog.setMessage("loading...");
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected Void doInBackground(Void... voids){
            String urlphoto = "http://128.199.75.229/alertsdetails.php?alertid="+globalvar_alertno;
            HttpHandler sh = new HttpHandler();
            String jsonStr = sh.makeServiceCall(urlphoto);
            Log.e(TAG, "Response from url: "+ jsonStr);

            if(jsonStr != null){
                try{
                    JSONObject jsonObject = new JSONObject(jsonStr);
                    JSONArray contacts  = jsonObject.getJSONArray("PhotoKey");
                    //looping through all contacts
                    for(int i =0; i< contacts.length(); i++){
                        JSONObject c = contacts.getJSONObject(i);
                        photobase64 = c.getString("photo");
                    }
                    if(photobase64!=null){
                        byte[] decodedstring = Base64.decode(photobase64, Base64.DEFAULT);
                        bitmap = BitmapFactory.decodeByteArray(decodedstring, 0, decodedstring.length);

                        ByteArrayOutputStream baos = new ByteArrayOutputStream();
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                        byte[] b = baos.toByteArray();
                        encodeImage = Base64.encodeToString(b, Base64.DEFAULT);
                    } else {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(AlertsActivity1.this,
                                        "Photo is null",
                                        Toast.LENGTH_SHORT).show();
                            }
                        });
                        Intent myIntent = new Intent(getApplicationContext(),AlertsActivity1.class);
                        finish();
                        startActivity(myIntent);

                    }

                }catch(final JSONException e){
                    Log.e(TAG, "JSON parsing error: " + e.getMessage());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(AlertsActivity1.this,
                                    "JSON parsing error: " + e.getMessage(),
                                    Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            } else {
                Log.e(TAG, "Couldn't get json from server.");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(AlertsActivity1.this,
                                "Couldn't get json from server.",
                                Toast.LENGTH_SHORT).show();
                    }
                });
            }
            return null;
        }
        @Override
        protected void onPostExecute(Void aVoid){
            super.onPostExecute(aVoid);
            //Dismiss the dialog
            if(pDialog.isShowing()){
                pDialog.dismiss();
            }

            decodeString = Base64.decode(encodeImage, Base64.DEFAULT);
            if(decodeString!=null){
                addEvent(decodeString);
            }
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        /*if(item.getItemId() == R.id.archive_button){
            Intent myIntent = new Intent(getApplicationContext(),ArchiveAlerts.class);
            startActivity(myIntent);
        }*/

        return super.onOptionsItemSelected(item);
    }

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
