package com.example.stanley.redo1_fyp_app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

/**
 * Created by Stanley on 27/6/2017.
 */

public class PicNDescActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picndesc);

        TextView asset_no = (TextView) findViewById(R.id.itemid);
        TextView item_name = (TextView) findViewById(R.id.itemname);
        TextView timestamp = (TextView) findViewById(R.id.timestamp);

        Intent i = getIntent();

        String asset_no1 = i.getStringExtra("asset1");
        String item_name1 = i.getStringExtra("descriptionalert1");
        String timestamp1 = i.getStringExtra("time1");

        asset_no.setText(asset_no1);
        item_name.setText(item_name1);
        timestamp.setText(timestamp1);
    }
}
