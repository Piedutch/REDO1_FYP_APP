package com.example.stanley.redo1_fyp_app;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
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
import java.util.concurrent.RunnableFuture;

import android.os.Handler;

public class AlertsActivity1 extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private String TAG = MainActivity.class.getSimpleName();

    private ProgressDialog pDialog;
    private ListView lv;

    //URL of json
    // private static String url = "http://api.androidhive.info/contacts/";
    private static String url = "http://128.199.75.229/alertspost.php";

    ArrayList<HashMap<String, String>> contactList;

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

        contactList = new ArrayList<>();

        lv = (ListView) findViewById(R.id.listView_alerts1);

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
                    Toast.makeText(AlertsActivity1.this,""+position, Toast.LENGTH_SHORT).show();
                    Intent myIntent = new Intent(getApplicationContext(),PicNDescActivity.class);
                    //   Intent myIntent = new Intent(this, PicNDescActivity.class);
                    TextView asset = (TextView)view.findViewById(R.id.asset);  //initialize text view withid label
                    TextView descriptionalert = (TextView)view.findViewById(R.id.description_alerts);  //initialize text view withid label
                    TextView time = (TextView)view.findViewById(R.id.timestamp_alerts);  //initialize text view withid label
                    String asset1 = asset.getText().toString(); //get the text
                    String descriptionalert1 = descriptionalert.getText().toString(); //get the text
                    String time1 = time.getText().toString(); //get the text


                    myIntent.putExtra("asset1",asset1);
                    myIntent.putExtra("descriptionalert1",descriptionalert1);
                    myIntent.putExtra("time1",time1);

                    startActivity(myIntent);
                }
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

                        String alert_no = c.getString("alert_no");
                        String time = c.getString("time");
                        String item_name = c.getString("item_name");
                        String asset_no = c.getString("asset_no");
                        asset_no = asset_no;
                        item_name = item_name+""; //30 will excceed //5 Fs to 28
                        if(item_name.length() > 28){
                            item_name = item_name.substring(0,28);
                        }

                        //    String in_an = item_name + ", #" + asset_no + " has left the room.";

                        //Phone node is JSON object
/*                        JSONObject phone = c.getJSONObject("phone");
                        String mobile = phone.getString("mobile");
                        String home = phone.getString("home");
                        String office = phone.getString("office");*/

                        HashMap<String, String> contact = new HashMap<>();

                        //adding each child node to hashmap
                        contact.put("time",time);
                        // contact.put("in_an",in_an);
                        contact.put("item_name",item_name);
                        contact.put("asset_no",asset_no);
                        //  contact.put("item_name",item_name);
                        //  contact.put("asset_no",asset_no);
                        //       contact.put("mobile",mobile);

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
                    R.layout.list_item, new String[]{"time","item_name","asset_no"},
                    new int[]{R.id.timestamp_alerts, R.id.description_alerts, R.id.asset});
            lv.setAdapter(adapter);
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
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }*/

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
/*        if (id == R.id.action_settings) {
            return true;
        }*/

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
