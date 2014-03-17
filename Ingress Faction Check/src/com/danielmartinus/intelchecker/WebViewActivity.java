package com.danielmartinus.intelchecker;

import java.util.Vector;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebView;

public class WebViewActivity extends Activity {
	private AuthenticationWebView mWebView;
	private AutoDeviceAuthentication mAuthentication;
    private final Vector<ResponseHandler> mResponseHandlers = new Vector<ResponseHandler>();
    public static String URL_INTEL = "https://www.ingress.com/intel/?vp=f";

    public interface ResponseHandler {
        void onActivityResult(int resultCode, Intent data);
    }
    
	@SuppressLint("NewApi")
	@Override
	public void onCreate(Bundle icicle) {
		super.onCreate(icicle);
		setContentView(R.layout.main);
		mWebView = (AuthenticationWebView) findViewById(R.id.webkit);

		//Custom webview client handling its own js injection
		mWebView.setWebViewClient(new Auth_WebViewClient(this));
		mWebView.setViewFitInScreen(true);
		//Load url to intel
		mWebView.loadUrl(URL_INTEL);
	}
	
	   /**
     * called by Auth_WebViewClient when the Google login form is opened.
     */
    public void onReceivedLoginRequest(final AuthenticationWebView client, final WebView view, final String realm,
            final String account, final String args) {
    	mAuthentication = new AutoDeviceAuthentication(this, mWebView);
    	mAuthentication.startLogin(realm, account, args);
    }
	
}