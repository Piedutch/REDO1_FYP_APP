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

public class MaintenanceMode extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private String TAG = MaintenanceMode.class.getSimpleName();

    private ProgressDialog pDialog;
    private SwipeMenuListView lv;
    private String alert_no1;
    private String globalvar_alertno;
    private String globalvar_assetno;
    private String globalvar_itemname;
    private String globalvar_time;
    private String globalvar_date;
    private String globalvar_maintenance;
    private String globalvar_map_status;

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

    private EventsData events;

    //URL of json
    private static String url = "http://128.199.75.229/items.php";


    ArrayList<HashMap<String, String>> contactList;
    List<String> alertnolist=new ArrayList<String>();
    List<String> assetnolist=new ArrayList<String>();
    List<String> itemnamelist=new ArrayList<String>();
    List<String> timelist=new ArrayList<String>();
    List<String> datelist=new ArrayList<String>();

    List<String> maintenancelist=new ArrayList<String>();
    List<String> map_statuslist=new ArrayList<String>();

    private SimpleAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maintenance);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar1);
        TextView mTitle = (TextView) toolbar.findViewById(R.id.toolbar_title1);
        mTitle.setText("All Assets");
        //  toolbar.setTitle("Yo it works");
        setSupportActionBar(toolbar);

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
                        Intent myIntent = new Intent(getApplicationContext(),MaintenanceMode.class);
                        finish();
                        startActivity(myIntent);
                    }
                }, 100);
            }
        });

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position==position){

                    for(int j  = 0; j < assetnolist.size(); j++){
                        if(j == position){
                            globalvar_assetno = assetnolist.get(position);
                            break;
                        }
                    }
                    for(int j  = 0; j < maintenancelist.size(); j++){
                        if(j == position){
                            globalvar_maintenance = maintenancelist.get(position);
                            break;
                        }
                    }

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

            }
        };
        lv.setMenuCreator(creator);
        lv.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
                switch (index) {
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
            pDialog = new ProgressDialog(MaintenanceMode.this);
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
                    JSONArray contacts  = jsonObject.getJSONArray("Items");

                    //looping through all contacts
                    for(int i =0; i< contacts.length(); i++){
                        JSONObject c = contacts.getJSONObject(i);

                        String item_name = c.getString("item_name");
                        String asset_no = c.getString("asset_no");
                        String maintenance_mode = c.getString("maintenance_mode");
                        String map_status = c.getString("map_status");

                        if(maintenance_mode.equals("0")){
                            maintenance_mode = "";
                        }
                        if(maintenance_mode.equals("1")){
                            maintenance_mode = "Under Maintenance";
                        }
                        if(map_status.equals("1")){
                            map_status = "Mapped";
                        } else {
                            map_status = "";
                        }

                        itemnamelist.add(item_name);

                        HashMap<String, String> contact = new HashMap<>();

                        //adding each child node to hashmap
                        map_statuslist.add(map_status);
                        maintenancelist.add(maintenance_mode);
                        assetnolist.add(asset_no);
                        contact.put("map_status",map_status);
                        contact.put("item_name",item_name);
                        contact.put("asset_no",asset_no);
                        contact.put("maintenance_mode",maintenance_mode);

                        //adding contact to contact list
                        contactList.add(contact);
                    }
                }catch(final JSONException e){
                    Log.e(TAG, "JSON parsing error: " + e.getMessage());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(MaintenanceMode.this,
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
                        Toast.makeText(MaintenanceMode.this,
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
                    MaintenanceMode.this, contactList,
                    R.layout.list_item_maintenance, new String[]{"item_name","asset_no","maintenance_mode","map_status"},
                    new int[]{R.id.description_alerts, R.id.asset, R.id.maintenancestatus, R.id.map_status});
            lv.setAdapter(adapter);

            /*runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(MainActivity.this,
                            "lv = "+lv,
                            Toast.LENGTH_LONG).show();
                }
            });*/
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
