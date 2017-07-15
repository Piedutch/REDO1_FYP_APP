package com.example.stanley.redo1_fyp_app;

import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
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

public class ArchiveAlerts extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private long globalvar_ID;
    private String globalvar_alertno;
    private String globalvar_assetno;
    private String globalvar_itemname;
    private String globalvar_time;
    private String globalvar_date;

    private long id_;
    private String alertno_;
    private String assetno_;
    private String itemname_;
    private String time_;
    private String date_;
    private ProgressDialog pDialog;
    private String TAG = MainActivity.class.getSimpleName();

    List<Long> IDlist=new ArrayList<Long>();
    List<String> alertnolist=new ArrayList<String>();
    List<String> assetnolist=new ArrayList<String>();
    List<String> itemnamelist=new ArrayList<String>();
    List<String> timelist=new ArrayList<String>();
    List<String> datelist=new ArrayList<String>();

    private static String[] FROM= {_ID, Alert_No1, Asset_No1, Item_Name1, Time1,Date1,"Image1"};
    private static String ORDER_BY = Alert_No1 + " DESC";
    private EventsData events;

    ArrayList<HashMap<String, String>> contactList;
    private SwipeMenuListView lv;

    private SimpleAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alerts1);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar1);
        TextView mTitle = (TextView) toolbar.findViewById(R.id.toolbar_title1);
        mTitle.setText("Archive Alerts");
        setSupportActionBar(toolbar);
        //this.deleteDatabase("eventrecords.db");

        contactList = new ArrayList<>();

        lv = (SwipeMenuListView) findViewById(R.id.listView_alerts1);

        //DB
        events = new EventsData(this);

        try{
            // Try to retrieve object cursor from database
            Cursor cursor = getEvents();
            showEvents(cursor);
        }finally{
            //events.close();
        }

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
                        Intent myIntent = new Intent(getApplicationContext(),ArchiveAlerts.class);
                        finish();
                        startActivity(myIntent);
                    }
                }, 100);
            }
        });

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position==position){
                    Intent myIntent = new Intent(getApplicationContext(),PicNDescActivity_Archive.class);
                    for(int j  = 0; j < alertnolist.size(); j++){
                        if(j == position){
                            globalvar_alertno = alertnolist.get(position);
                            break;
                        }
                    }
                    for(int j  = 0; j < itemnamelist.size(); j++){
                        if(j == position){
                            globalvar_itemname = itemnamelist.get(position);
                            break;
                        }
                    }
                    for(int j  = 0; j < assetnolist.size(); j++){
                        if(j == position){
                            globalvar_assetno = assetnolist.get(position);
                            break;
                        }
                    }
                    for(int j  = 0; j < timelist.size(); j++){
                        if(j == position){
                            globalvar_time = timelist.get(position);
                            break;
                        }
                    }for(int j  = 0; j < datelist.size(); j++){
                        if(j == position){
                            globalvar_date = datelist.get(position);
                            break;
                        }
                    }

                    myIntent.putExtra("alertno",globalvar_alertno);
                    myIntent.putExtra("assetno",globalvar_assetno);
                    myIntent.putExtra("itemname",globalvar_itemname);
                    myIntent.putExtra("time",globalvar_time);
                    myIntent.putExtra("date",globalvar_date);
                    //myIntent.putExtra("photo",globalvar_photo);

                    startActivity(myIntent);

                    /*runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(ArchiveAlerts.this,
                                    ""+globalvar_alertno+ " \n" + globalvar_assetno + "\n" + globalvar_itemname + "\n" +globalvar_time + "\n" + globalvar_date,
                                    Toast.LENGTH_LONG).show();
                        }
                    });*/

                }}});

        SwipeMenuCreator creator = new SwipeMenuCreator() {

            @Override
            public void create(SwipeMenu menu) {
                // create "archive" item
                SwipeMenuItem archiveItem = new SwipeMenuItem(
                        getApplicationContext());
                // set item background
                archiveItem.setBackground(new ColorDrawable(Color.rgb(0xF9,
                        0x3F, 0x25)));
                // set item width
                archiveItem.setWidth(170);
                // set a icon
                archiveItem.setIcon(R.mipmap.ic_trash);
                // add to menu
                menu.addMenuItem(archiveItem);
            }
        };
        lv.setMenuCreator(creator);
        lv.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(final int position, SwipeMenu menu, int index) {
                switch (index) {
                    case 0:
                        if (position==position){
                            for(int j  = 0; j < IDlist.size(); j++){
                                if(j == position){
                                    globalvar_ID = IDlist.get(position);
                                    break;
                                }
                            }

                        }
                        final String s = Objects.toString(globalvar_ID, null);

                        removeEvent(s);
                }
                return false;
            }});


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    private Cursor getEvents(){
        // Perform a managed query. The Activity will handle closing
        // and re-querying the cursor when needed.
        SQLiteDatabase db = events.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME, FROM, null, null, null,
                null, ORDER_BY);
        return cursor;
    }
    private void removeEvent(String placeid){
        SQLiteDatabase db = events.getWritableDatabase();
        try{
            db.delete(TABLE_NAME,"_ID = ?",new String[]{placeid});
        }finally {
            Intent myIntent = new Intent(getApplicationContext(),ArchiveAlerts.class);
            finish();
            startActivity(myIntent);
            db.close();
        }
    runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(ArchiveAlerts.this,
                                        "Successfully removed from archive",
                                        Toast.LENGTH_LONG).show();
                            }
                        });
    }
    private void showEvents(Cursor cursor) {
        while (cursor.moveToNext()){
            //Could use getColumnIndexOrThrow() to get indexes
            id_ = cursor.getLong(0);
            alertno_ = cursor.getString(1);
            assetno_ = cursor.getString(2);
            itemname_ = cursor.getString(3);
            time_ = cursor.getString(4);
            date_ = cursor.getString(5);

            itemnamelist.add(itemname_);

            if(itemname_.length() > 28){
                itemname_ = itemname_.substring(0,28);
            }

            //For intenting to PicNDescActivity_Archive
            IDlist.add(id_);
            alertnolist.add(alertno_);
            assetnolist.add(assetno_);
            timelist.add(time_);
            datelist.add(date_);

            HashMap<String, String> contact = new HashMap<>();
            //For displaying
            contact.put("alertno_",alertno_);
            contact.put("assetno_",assetno_);
            contact.put("itemname_",itemname_);
            contact.put("time_",time_);
            contact.put("date_",date_);

            contactList.add(contact);
        }

        /*runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(ArchiveAlerts.this,
                        "All things in DB = "+lv,
                        Toast.LENGTH_LONG).show();
            }
        });*/
        adapter = new SimpleAdapter(
                ArchiveAlerts.this, contactList,
                R.layout.list_item_archive, new String[]{"time_","itemname_","assetno_","alertno_"},
                new int[]{R.id.timestamp_alerts, R.id.description_alerts, R.id.asset,R.id.alert_no});
        lv.setAdapter(adapter);


    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_search1, menu);
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
        if(item.getItemId() == R.id.archive_button_up){
            Intent myIntent = new Intent(getApplicationContext(),AlertsActivity1.class);
            startActivity(myIntent);
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            Intent myIntent = new Intent(getApplicationContext(),HomeActivity.class);
            startActivity(myIntent);
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {
            Intent myIntent = new Intent(getApplicationContext(),AlertsActivity1.class);
            startActivity(myIntent);
        } else if (id == R.id.nav_slideshow) {
            Intent myIntent = new Intent(getApplicationContext(), AboutActivity.class);
            startActivity(myIntent);
        } else if (id == R.id.nav_manage) {
            Intent myIntent = new Intent(getApplicationContext(), SettingsActivity.class);
            startActivity(myIntent);
        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
