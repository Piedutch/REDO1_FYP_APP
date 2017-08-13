package com.example.stanley.redo1_fyp_app;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.liuguangqiang.swipeback.SwipeBackActivity;
import com.liuguangqiang.swipeback.SwipeBackLayout;

/**
 * Created by Stanley on 13/7/2017.
 */

public class PicNDescActivity_Archive extends SwipeBackActivity {
    private EventsData events;
    byte[] photo;
    ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picndesc);
        events = new EventsData(this);
        setDragEdge(SwipeBackLayout.DragEdge.LEFT);

        imageView =(ImageView)findViewById(R.id.phone_icon);
        TextView asset_no = (TextView) findViewById(R.id.itemid);
        TextView item_name = (TextView) findViewById(R.id.itemname);
        TextView timestamp = (TextView) findViewById(R.id.timestamp);

        Intent i = getIntent();

        String alert_no1 = i.getStringExtra("alertno");
        String asset_no1 = i.getStringExtra("assetno");
        String item_name1 = i.getStringExtra("itemname");
        String timestamp1 = i.getStringExtra("time");
        String date1 = i.getStringExtra("date");

        SQLiteDatabase db = events.getReadableDatabase();

        Cursor c = db.rawQuery("SELECT Image1 FROM Archive WHERE alert_no = '"+alert_no1+"'", null);
        if(c.moveToFirst()){
            do{
            photo = c.getBlob(0);
            }while (c.moveToNext());
        }c.close();
        db.close();

        /*Bitmap decoded = BitmapFactory.decodeByteArray(photo, 0, photo.length);
        imageView.setImageBitmap(decoded);*/

        String time_date = date1+"   "+timestamp1;

        asset_no.setText(asset_no1);
        item_name.setText(item_name1);
        timestamp.setText(time_date);
    }

}
