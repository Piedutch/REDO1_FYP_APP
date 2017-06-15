package com.example.stanley.redo1_fyp_app;

import android.provider.BaseColumns;

/**
 * Created by Stanley on 15/6/2017.
 */

public interface DatabaseConstants extends BaseColumns {
    public static final String TABLE_NAME = "Records";

    //columns in the database
    public static final String ITEMID = "itemid";
    public static final String ITEMNAME = "itemname";
    public static final String TIMESTAMP = "timestamp";

}
