package com.example.stanley.redo1_fyp_app;


/**
 * Created by jonat on 07/19/17.
 */

import android.app.Activity;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import java.text.SimpleDateFormat;
import java.util.Date;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LegendEntry;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IPieDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.liuguangqiang.swipeback.SwipeBackActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by jonat on 07/14/17.
 */

public class StatisticsActivity extends SwipeBackActivity {

    private String TAG = StatisticsActivity.class.getSimpleName();
    private String url = "http://128.199.75.229/piechart.php";
    private String url1 = "http://128.199.75.229/current_count_of_alerts.php";
    private String item_name, date;
    private int no_of_alerts;
    TextView today_alerts;
    private PieChart pieChart;
    private int frequency, resultcount;
    private String [] labels;

    private ArrayList<String> allitem_name;
    private ArrayList<Integer> allfrequency;
    private ArrayList<String> xVals, xValstosetdata;
    private ArrayList<PieEntry> yVals, yValstosetdata;



    PieDataSet pieDataSet;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.stats_piechart);

        today_alerts = (TextView) findViewById(R.id.today_alerts);

        pieChart = (PieChart) findViewById(R.id.piechart);
        pieChart.setCenterText("Items taken out");
        Description description = new Description();
        description.setTextColor(ColorTemplate.PASTEL_COLORS[2]);
        description.setTextSize(10);
        description.setText("Pie Chart");
        pieChart.setDescription(description);

        new GetPieChart().execute();
        pieChart.animateY(1000);
        pieChart.setDrawEntryLabels(false);
        pieChart.invalidate();

    }
    private class GetPieChart extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPostExecute(Void aVoid) {
            setTextView(no_of_alerts);
        }

        @Override
        protected Void doInBackground(Void... voids) {
            HttpHandler sh = new HttpHandler();
            String jsonStr = sh.makeServiceCall(url);
            allitem_name = new ArrayList<String>();
            allfrequency = new ArrayList<Integer>();
            resultcount = 0;
            Log.e(TAG, "Response from url: " + jsonStr);

            if (jsonStr != null) {
                try {
                    JSONObject jsonObject = new JSONObject(jsonStr);

                    //getting json array node
                    JSONArray array = jsonObject.getJSONArray("itemsalerts");

                    //looping through all contacts
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject c = array.getJSONObject(i);

                        item_name= c.getString("item_name");
                        frequency = c.getInt("frequency_of_alerts");

                        item_name = item_name + ""; //30 will exceed //5 Fs to 28
                        if (item_name.length() > 15) {
                            item_name = item_name.substring(0, 15);
                        }

                        allitem_name.add(item_name);
                        allfrequency.add(frequency);
                        resultcount++;

                    }
                } catch (final JSONException e) {
                    Log.e(TAG, "JSON parsing error: " + e.getMessage());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(StatisticsActivity.this,
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
                        Toast.makeText(StatisticsActivity.this,
                                "Couldn't get json from server.",
                                Toast.LENGTH_SHORT).show();
                    }
                });
            }

            xValstosetdata = setXAxisValues(resultcount, allitem_name);
            yValstosetdata = setYAxisValues(resultcount, allfrequency, xVals);
            setData(yValstosetdata, xVals);

            HttpHandler sh1 = new HttpHandler();
            String jsonStr1 = sh1.makeServiceCall(url1);
            Log.e(TAG, "Response from url: " + jsonStr1);

            if (jsonStr1 != null) {
                try {
                    JSONObject jsonObject = new JSONObject(jsonStr1);

                    //getting json array node?
                    JSONArray array = jsonObject.getJSONArray("currentcount");

                    //looping through all contacts
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject c = array.getJSONObject(i);

                        no_of_alerts= c.getInt("alerts_today");
                        date = c.getString("date");

                    }
                } catch (final JSONException e) {
                    Log.e(TAG, "JSON parsing error: " + e.getMessage());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(StatisticsActivity.this,
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
                        Toast.makeText(StatisticsActivity.this,
                                "Couldn't get json from server.",
                                Toast.LENGTH_SHORT).show();
                    }
                });
            }

            return null;
        }
    }

    private void setTextView(int no_of_alerts){
//        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
//        String Date = sdf.format(new Date());
        if(no_of_alerts==0){
            today_alerts.setText("There are no alerts today.");
        } else {
            today_alerts.setText("There are " + no_of_alerts + " alerts today - " + date + ".");
        }
    }

    private void setData(ArrayList<PieEntry> yArray, ArrayList<String> xVals) {
        String [] labels = new String[xVals.size()];
        xVals.toArray(labels);
        ArrayList<PieEntry> yValues = yArray;
        PieDataSet pieDataSet;

//        HashMap<Integer, String>numMap = new HashMap<>();
//        for(int i=0; i<xArray.size(); i++){
//            numMap.put(i, xArray.get(i));
//        }
        pieDataSet = new PieDataSet(yValues, "Item Name");
        Log.d(TAG, "Data set: " + pieDataSet);
//        List<IPieDataSet> datasets = new ArrayList<IPieDataSet>();
//        datasets.add(pieDataSet);
        pieDataSet.setColors(Color.rgb(193, 37, 82), Color.rgb(255, 102, 0), Color.rgb(245, 199, 0),
                Color.rgb(106, 150, 31), Color.rgb(179, 100, 53), Color.rgb(255,0,255), Color.rgb(0,191,255));
        PieData data = new PieData(pieDataSet);
        data.setValueTextSize(20f);

        Legend l = pieChart.getLegend();
        l.setForm(Legend.LegendForm.CIRCLE);
        l.setEnabled(true);
        l.setFormSize(10f);
        l.setTextColor(ColorTemplate.PASTEL_COLORS[1]);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
        l.setWordWrapEnabled(true);




//        List<LegendEntry> entries = new ArrayList<>();
//        for(int i =0; i< xValues.size(); i++){
//            LegendEntry entry = new LegendEntry();
//            entry.label = xValues.get(i);
//            entries.add(entry);
//        }
//
//        l.setCustom(entries);

        pieChart.setData(data);


    }

    private ArrayList<PieEntry> setYAxisValues(int resultcount, ArrayList<Integer> allfrequency, ArrayList<String> xVals) {
        this.xVals = new ArrayList<String>();
        this.yVals = new ArrayList<PieEntry>();
        for(int i = 0; i < resultcount;i++){
            int y = Integer.valueOf(allfrequency.get(i));
            String xVal = String.valueOf(xVals.get(i));
            yVals.add(new PieEntry(y,xVal));
        }
        return yVals;
    }

    private ArrayList<String> setXAxisValues(int resultcount, ArrayList<String> allitem_name) {
        this.xVals = new ArrayList<String>();
        for (int i = 0; i < resultcount; i++){
            xVals.add(allitem_name.get(i));
        }
        return xVals;
    }


}