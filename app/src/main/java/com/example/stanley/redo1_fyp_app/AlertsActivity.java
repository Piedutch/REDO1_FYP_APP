package com.example.stanley.redo1_fyp_app;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Context;
import android.content.res.Resources;
import android.media.Image;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextClock;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Stanley on 12/6/2017.
 */

public class AlertsActivity extends Activity {

    private MenuItem searchMenuItem;
    private SearchView searchView;
    private ListView list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alerts);
        ListView list = (ListView) findViewById(R.id.listView_alerts);
        list.setAdapter(new VivzAdapter(this));
/*        String[] foods = {"Nasi Lemak","Chicken Rice","Mango","Pineapple"};
        ListAdapter buckysAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, foods);
        ListView buckysListView = (ListView) findViewById(R.id.listView);
        buckysListView.setAdapter(buckysAdapter);*/
        setActionBar();
    }

    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.search_menu, menu);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchMenuItem = menu.findItem(R.id.search);
        searchView = (SearchView) searchMenuItem.getActionView();

        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setSubmitButtonEnabled(true);

        return true;
    }
    private void setActionBar(){
        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setTitle("Alerts");
    }
}
class SingleRow{
    String time;
    String title;
    String description;
    int image;
    SingleRow(String titles,String descriptions,int images){
        this.title=titles;
        this.description=descriptions;
        this.image=images;
        this.time=time;
    }
}
class VivzAdapter extends BaseAdapter{

    ArrayList<SingleRow> list;
    Context context;
    VivzAdapter(Context c){
        context=c;
        list=new ArrayList<SingleRow>();
        Resources res = c.getResources();
        String[] titles=res.getStringArray(R.array.titles);
        String[] descriptions=res.getStringArray(R.array.description);
        int[] images={R.drawable.alerticon1,R.drawable.alerticon1,R.drawable.alerticon1,R.drawable.alerticon1,R.drawable.alerticon1};

        for(int i =0;i<4;i++){
            list.add(new SingleRow (titles[i],descriptions[i],images[i]));
        }
    }
/*
    String titles;
    String[] description;
    int[] images;*/

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    public static String getCurrentTimeStamp(){
        try {

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String currentDateTime = dateFormat.format(new Date()); // Find todays date

            return currentDateTime;
        } catch (Exception e) {
            e.printStackTrace();

            return null;
        }
    }


    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater inflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row = inflater.inflate(R.layout.single_row,viewGroup,false);
        ImageView image = (ImageView) row.findViewById(R.id.imageView_alerts);
        TextView title = (TextView) row.findViewById(R.id.textviewtitle_alerts); //Alert Title
      //  TextView timestamp = (TextView) row.findViewById(R.id.timestamp); // Find out how to retrieve current time
        TextClock timestamp = (TextClock) row.findViewById(R.id.timestamp_alerts);
        TextView description = (TextView) row.findViewById(R.id.description_alerts);

        SingleRow temp = list.get(i);

        String time = getCurrentTimeStamp();
        System.out.print(time);
        System.out.print(time);
        System.out.print(time);
        System.out.print(time);
        System.out.print(time);


       // timestamp.setText(temp.time());
        title.setText(temp.title);
        description.setText(temp.description);
        image.setImageResource(temp.image);
        return row;
    }
}