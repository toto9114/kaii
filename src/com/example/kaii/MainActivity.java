package com.example.kaii;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.KeyEvent;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;

public class MainActivity extends Activity{
    
    private WebView mWebView;
     
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
         
        final Context myApp = this;
        
        mWebView = (WebView) findViewById(R.id.webview);

        //javascript useable
        mWebView.getSettings().setJavaScriptEnabled(true);
        
        //jwplayer mp4 dom
        mWebView.getSettings().setDomStorageEnabled(true);
        mWebView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        
        mWebView.setWebChromeClient
        (
        	new WebChromeClient()
        	{        	
	            //alter process
	            @Override
	            public boolean onJsAlert(WebView view, String url, String message, final android.webkit.JsResult result)
	            {
	                new AlertDialog.Builder(myApp)
	                    .setTitle("Alert")
	                    .setMessage(message)
	                    .setPositiveButton(android.R.string.ok,
	                            new AlertDialog.OnClickListener()
	                            {
	                                public void onClick(DialogInterface dialog, int which)
	                                {
	                                    result.confirm();
	                                }
	                            })
	                    .setCancelable(false)
	                    .create()
	                    .show();
	
	                return true;
	            };
	            
	            //confirm process         
	            @Override
	            public boolean onJsConfirm(WebView view, String url, String message, final JsResult result) {
	
	             new AlertDialog.Builder(view.getContext())
	                  .setTitle("Confirm")
	                  .setMessage(message)
	                  .setPositiveButton("네",
	                        new AlertDialog.OnClickListener(){
	                           public void onClick(DialogInterface dialog, int which) {
	                            result.confirm();
	                           }
	                        })
	                  .setNegativeButton("아니오", 
	                    new AlertDialog.OnClickListener(){
	                          public void onClick(DialogInterface dialog, int which) {
	                           result.cancel();
	                          }
	                       })
	                  .setCancelable(false)
	                  .create()
	                  .show();
	             
	              	return true;
	            }
        	}
        );
        
        mWebView.setWebViewClient(new WebViewClientClass());
        
        mWebView.loadUrl("http://kaii.plani.co.kr");                
    }
     
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) { 
        if ((keyCode == KeyEvent.KEYCODE_BACK) && mWebView.canGoBack()) { 
            mWebView.goBack(); 
            return true; 
        } 
        
        return super.onKeyDown(keyCode, event);
    }
     
    private class WebViewClientClass extends WebViewClient { 
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) { 
            view.loadUrl(url); 
            return true; 
        }
        
        
    }
     
    /*
     * Layout
     */
    private void setLayout(){
        mWebView = (WebView) findViewById(R.id.webview);
    }
}