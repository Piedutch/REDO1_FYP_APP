package com.example.stanley.redo1_fyp_app;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static android.provider.BaseColumns._ID;
import static com.example.stanley.redo1_fyp_app.Constants.Alert_No1;
import static com.example.stanley.redo1_fyp_app.Constants.Asset_No1;
import static com.example.stanley.redo1_fyp_app.Constants.COUNT;
import static com.example.stanley.redo1_fyp_app.Constants.Date1;
import static com.example.stanley.redo1_fyp_app.Constants.Item_Name1;
import static com.example.stanley.redo1_fyp_app.Constants.Maintenance_status1;
import static com.example.stanley.redo1_fyp_app.Constants.REFRESHVALUE;
import static com.example.stanley.redo1_fyp_app.Constants.SETTINGS_TABLE_NAME;
import static com.example.stanley.redo1_fyp_app.Constants.TABLE_NAME;
import static com.example.stanley.redo1_fyp_app.Constants.Time1;


/**
 * Created by þïèÐú†ÇH on 1/16/2017.
 */

public class EventsData extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "events.db"; //events2, events1, 9
        private static final int DATABASE_VERSION = 1;

    /*Create a helper object for the Events database*/
    public EventsData(Context ctx) {
        super(ctx, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        db.execSQL("CREATE TABLE " + TABLE_NAME +" (" + _ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + Alert_No1 + " TEXT NOT NULL,"
                + Asset_No1 + " TEXT NOT NULL," +Item_Name1+ " TEXT NOT NULL,"+Time1+ " TEXT NOT NULL," +Date1+" TEXT NOT NULL,"+Maintenance_status1+" TEXT,"+"Image1"+" BLOB);");


        db.execSQL("CREATE TABLE " + SETTINGS_TABLE_NAME + " (" + _ID
                + " INTEGER PRIMARY KEY AUTOINCREMENT, " + REFRESHVALUE
                + " INT NOT NULL, " + COUNT + " INT NOT NULL);");

        db.execSQL("INSERT INTO " + SETTINGS_TABLE_NAME + " (" + REFRESHVALUE
                + ", " +COUNT + " ) VALUES(60000, 0);");
    }

    //@Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + SETTINGS_TABLE_NAME);
        onCreate(db);
    }
}
