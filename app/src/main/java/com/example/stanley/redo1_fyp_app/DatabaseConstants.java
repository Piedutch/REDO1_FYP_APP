package com.example.stanley.redo1_fyp_app;

import android.provider.BaseColumns;

/**
 * Created by Stanley on 15/6/2017.
 */

public interface DatabaseConstants extends BaseColumns {

    //Table Names
    public static final String RECORDS_TABLE_NAME = "Records";
    public static final String ITEMS_TABLE_NAME = "Items";

    //columns in the database for RECORDS
    public static final String ITEMID = "itemid";
    public static final String ITEMNAME = "itemname";
    public static final String TIMESTAMP = "timestamp";

    //columns in the database for ITEMS
    public static final String ASSET_NO = "asset_no";
    public static final String ASSET_NAME = "asset_name";

}
