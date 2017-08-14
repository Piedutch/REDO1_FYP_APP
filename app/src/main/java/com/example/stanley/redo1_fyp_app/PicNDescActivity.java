package com.example.stanley.redo1_fyp_app;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
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
import android.widget.ImageView;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.RunnableFuture;

import android.os.Handler;

import com.liuguangqiang.swipeback.SwipeBackActivity;
import com.liuguangqiang.swipeback.SwipeBackLayout;

import static android.R.attr.bitmap;

/**
 * Created by Stanley on 27/6/2017.
 */

public class PicNDescActivity extends SwipeBackActivity {

    private String TAG = PicNDescActivity.class.getSimpleName();
    private ProgressDialog pDialog;
    Bitmap bitmap;
    ImageView imageView;
    String photobase64;
    String encodeImage;
    String alert_no1;

    //URL of json

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picndesc);
        setDragEdge(SwipeBackLayout.DragEdge.LEFT);

        imageView = (ImageView)findViewById(R.id.phone_icon);

        TextView asset_no = (TextView) findViewById(R.id.itemid);
        TextView item_name = (TextView) findViewById(R.id.itemname);
        TextView timestamp = (TextView) findViewById(R.id.timestamp);

        Intent i = getIntent();

        String asset_no1 = i.getStringExtra("asset1");
        String item_name1 = i.getStringExtra("descriptionalert1");
        String timestamp1 = i.getStringExtra("time1");
        alert_no1 = i.getStringExtra("alert_no1");
        String date1 = i.getStringExtra("date1");

        String time_date = date1+"   "+timestamp1;

        asset_no.setText(asset_no1);
        item_name.setText(item_name1);
        timestamp.setText(time_date);


        new PicNDescActivity.GetContacts().execute();

    }
    private class GetContacts extends AsyncTask<Void, Void, Void>{
        @Override
        protected void onPreExecute(){
            super.onPreExecute();
            //show loading dialog
            pDialog = new ProgressDialog(PicNDescActivity.this);
            pDialog.setMessage("loading...");
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected Void doInBackground(Void... voids){
            HttpHandler sh = new HttpHandler();
            final String url = "http://128.199.75.229/alertsdetails.php?alertid="+alert_no1;
            String jsonStr = sh.makeServiceCall(url);
            Log.e(TAG, "Response from url: "+ jsonStr);

/*            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(PicNDescActivity.this,
                            "Getting image from "+url,
                            Toast.LENGTH_LONG).show();
                }
            });*/


            if(jsonStr != null){
                try{
                    JSONObject jsonObject = new JSONObject(jsonStr);

                    //getting json array node
                    JSONArray contacts  = jsonObject.getJSONArray("PhotoKey");

                    //looping through all contacts
                    for(int i =0; i< contacts.length(); i++){
                        JSONObject c = contacts.getJSONObject(i);
                        photobase64 = c.getString("photo");

                        if(photobase64!=null){

                        }

                        //   HashMap<String, String> contact = new HashMap<>();

                        //adding each child node to hashmap
                        //  contact.put("time",time);

                        //adding contact to contact list
                        //  contactList.add(contact);
                    }
                    // here to do things i think
                    byte[] decodedstring = Base64.decode(photobase64, Base64.DEFAULT);
                    bitmap = BitmapFactory.decodeByteArray(decodedstring, 0, decodedstring.length);

                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                    byte[] b = baos.toByteArray();
                    encodeImage = Base64.encodeToString(b, Base64.DEFAULT);


                }catch(final JSONException e){
                    Log.e(TAG, "JSON parsing error: " + e.getMessage());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(PicNDescActivity.this,
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
                        Toast.makeText(PicNDescActivity.this,
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
            //text.setText(encodeImage);

            //This part onwards is for image
            byte[] decodeString = Base64.decode(encodeImage, Base64.DEFAULT);
            Bitmap decoded = BitmapFactory.decodeByteArray(decodeString, 0, decodeString.length);
            imageView.setImageBitmap(decoded);
            //updating json data to listview
            //  adapter = new SimpleAdapter(
            //          MainActivity.this, contactList,
            //          R.layout.list_item, new String[]{"time","item_name","asset_no","alert_no"},
            //          new int[]{R.id.timestamp_alerts, R.id.description_alerts, R.id.asset,R.id.alert_no});
            //  lv.setAdapter(adapter);

        }
    }
}
