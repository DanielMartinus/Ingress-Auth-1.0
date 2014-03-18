package com.danielmartinus.activity;

import java.util.Vector;

import com.danielmartinus.intelchecker.Auth_WebViewClient;
import com.danielmartinus.intelchecker.AuthenticationWebView;
import com.danielmartinus.intelchecker.AutoDeviceAuthentication;
import com.danielmartinus.intelchecker.R;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.webkit.WebView;

public class WebViewActivity extends FragmentActivity {
	private ViewPager mPager;
	private AuthenticationWebView mWebView;
	private AutoDeviceAuthentication mAuthentication;
    private final Vector<ResponseHandler> mResponseHandlers = new Vector<ResponseHandler>();
    public static String URL_INTEL = "https://www.ingress.com/intel/?vp=f";

    public interface ResponseHandler {
        void onActivityResult(int resultCode, Intent data);
    }
    
	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		mWebView = (AuthenticationWebView) findViewById(R.id.webkit);

		//Custom webview client handling its own js injection
		mWebView.setWebViewClient(new Auth_WebViewClient(this));
		mWebView.setViewFitInScreen(true);
		//Load url to intel
		mWebView.loadUrl(URL_INTEL);
	}
	
	private ViewPager getViewPager() {
		if(mPager == null) {
			this.mPager = (ViewPager)findViewById(R.id.pager);
		}
		return mPager;
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
