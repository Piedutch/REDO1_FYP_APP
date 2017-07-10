package com.example.stanley.redo1_fyp_app;

import android.content.DialogInterface;
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
import java.util.concurrent.RunnableFuture;

import android.os.Handler;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;

public class AlertsActivity1 extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private String TAG = MainActivity.class.getSimpleName();

    private ProgressDialog pDialog;
    private String globalvar_alertno;
    private String globalvar_itemname;
   // private ListView lv;
    private SwipeMenuListView lv;
    List<String> alertnolist=new ArrayList<String>();
    List<String> itemnamelist=new ArrayList<String>();
    List<Integer> testlist = new ArrayList<Integer>();

    //URL of json
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
                // create "delete" item
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
                menu.addMenuItem(deleteItem);

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
                    case 0:

                        /*for(final int item1:testlist){
                            //System.out.println(item);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(AlertsActivity1.this,
                                            "Toast for yes"+ item1,
                                            Toast.LENGTH_LONG).show();
                                }
                            });
                        }*/

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
                        break;

                    case 1:
                        // This is for archive
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(AlertsActivity1.this,
                                        "Toast for Archive",
                                        Toast.LENGTH_LONG).show();
                            }
                        });
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
                        String date = c.getString("date");

                        int test = c.getInt("alert_no");

                        testlist.add(test);
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
                        alertnolist.add(alert_no);
                        contact.put("time",time);
                        contact.put("item_name",item_name);
                        contact.put("asset_no",asset_no);
                        contact.put("date",date);
                        contact.put("alert_no",alert_no);

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
                    R.layout.list_item, new String[]{"time","item_name","asset_no","alert_no","date"},
                    new int[]{R.id.timestamp_alerts, R.id.description_alerts, R.id.asset,R.id.alert_no,R.id.date});
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
            Intent myIntent = new Intent(getApplicationContext(),HomeActivity.class);
            startActivity(myIntent);
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {
            Intent myIntent = new Intent(getApplicationContext(),AlertsActivity1.class);
            startActivity(myIntent);
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
