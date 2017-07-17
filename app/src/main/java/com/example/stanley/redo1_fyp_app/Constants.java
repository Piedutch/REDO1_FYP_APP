package com.example.stanley.redo1_fyp_app;
import android.provider.BaseColumns;

/**
 * Created by þïèÐú†ÇH on 1/16/2017.
 */

public interface Constants extends BaseColumns {
    public static final String TABLE_NAME = "Archive";
    public static final String SETTINGS_TABLE_NAME = "Settings";

    // columns in the Archive table
    public static final String Asset_No1 = "asset_no";
    public static final String Alert_No1 = "alert_no";
    public static final String Item_Name1 = "item_name";
    public static final String Time1 = "time";
    public static final String Date1 = "date";

    //columns in the database for SETTINGS
    public static final String REFRESHVALUE = "refreshvalue";

}

