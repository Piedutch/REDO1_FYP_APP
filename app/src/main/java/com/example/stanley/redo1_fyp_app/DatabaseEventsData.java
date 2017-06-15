package com.example.stanley.redo1_fyp_app;
import static android.provider.BaseColumns._ID;
import static com.example.stanley.redo1_fyp_app.DatabaseConstants.TABLE_NAME;
import static com.example.stanley.redo1_fyp_app.DatabaseConstants.ITEMID;
import static com.example.stanley.redo1_fyp_app.DatabaseConstants.ITEMNAME;
import static com.example.stanley.redo1_fyp_app.DatabaseConstants.TIMESTAMP;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


/**
 * Created by Stanley on 15/6/2017.
 */

public class DatabaseEventsData extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "eventrecords.db";
        private static final int DATABASE_VERSION = 1;

        /* Create a helper object for the Events database */

        public DatabaseEventsData(Context ctx){
            super(ctx, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db){
            db.execSQL("CREATE TABLE " + TABLE_NAME + " (" + _ID
            + " INTEGER PRIMARY KEY AUTOINCREMENT, " + ITEMID
            + " TEXT NOT NULL," + ITEMNAME + " TEXT NOT NULL,"
                    + TIMESTAMP + "INTEGER);");
            //Should timestamp be INTEGER?
        }
        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
            onCreate(db);
        }
}
