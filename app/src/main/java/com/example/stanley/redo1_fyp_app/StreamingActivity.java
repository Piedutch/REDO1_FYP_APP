package com.example.stanley.redo1_fyp_app;

/**
 * Created by jonat on 07/10/17.
 */

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebView;

import com.liuguangqiang.swipeback.SwipeBackActivity;
import com.liuguangqiang.swipeback.SwipeBackLayout;

public class StreamingActivity extends SwipeBackActivity
{
    private WebView mWebView = null;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_streaming);
        setDragEdge(SwipeBackLayout.DragEdge.LEFT);

        mWebView = (WebView) findViewById(R.id.webView);
        mWebView.getSettings().setJavaScriptEnabled(true);
//        mWebView.loadUrl("http://10.10.10.64:8081/");
        mWebView.loadUrl("http://175.156.246.183:8081/");
    }
}
