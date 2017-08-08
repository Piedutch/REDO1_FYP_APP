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
    public static final String Maintenance_status1 = "maintenance_status";

    //columns in the database for SETTINGS
    public static final String REFRESHVALUE = "refreshvalue";
    public static final String COUNT = "count";

    //url links
    public static final String GETTINGALERTS_URL = "http://128.199.75.229/alertspost.php";
    public static final String RETRIEVEFORNOTIF_URL = "http://128.199.75.229/getlastrow.php";
    public static final String RETRIEVEFORSYSDIAGNOTIF_URL = "http://128.199.75.229/get_pi_status.php";
    public static final String MAINTENANCEMODE_URL = "http://128.199.75.229/items.php";
    public static final String ALERTNOTIFICATIONS_URL = "http://128.199.75.229/push_notification.php";
    public static final String STATS_URL = "http://128.199.75.229/piechart.php";
    public static final String DYNAMICCOUNT_URL = "http://128.199.75.229/current_count_of_alerts.php";
    public static final String SYSDIAG_URL = "http://128.199.75.229/status_app.php";
    public static final String REGISTERFIREBASE_URL = "http://128.199.75.229/registerfirebase.php";
    public static final String SYSDIAGNOTIFICATIONS_URL = "http://128.199.75.229/push_notification_sysdiag.php";

}

