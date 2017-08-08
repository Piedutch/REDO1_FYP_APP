package com.example.stanley.redo1_fyp_app;

/**
 * Created by jonat on 07/10/17.
 */

import android.app.Activity;
import android.os.Bundle;
import android.webkit.HttpAuthHandler;
import android.webkit.WebView;
import android.webkit.WebViewClient;

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
        //mWebView.loadUrl("http://10.10.10.65:8081/");
        mWebView.loadUrl("http://175.156.209.207:8081/");
//        mWebView.setWebViewClient(new MyWebViewClient());

    }
//    private class MyWebViewClient extends WebViewClient {
//        @Override
//        public void onReceivedHttpAuthRequest(WebView view,
//                                              HttpAuthHandler handler, String host, String realm) {
//
//            handler.proceed("pi", "1qwer$#@!");
//
//        }
//    }
}
