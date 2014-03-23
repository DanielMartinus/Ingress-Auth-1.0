package com.danielmartinus.intelchecker;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;

@SuppressLint("SetJavaScriptEnabled")
public class AuthenticationWebView extends WebView {

    private WebSettings mSettings;
    private OnLoginHandler onLogin;
    private JSInterface mJSInterface;
    
	private void initWebView(Context ctx) {
		if(isInEditMode()) return;
		 mSettings = getSettings();
	     mSettings.setJavaScriptEnabled(true);
	     mSettings.setDomStorageEnabled(true);
	     mSettings.setAllowFileAccess(true);
	     mSettings.setGeolocationEnabled(true);
	     mSettings.setAppCacheEnabled(true);
	     mSettings.setCacheMode(WebSettings.LOAD_DEFAULT);
	     mSettings.setAppCachePath(getContext().getCacheDir().getAbsolutePath());
	     mSettings.setDatabasePath(getContext().getApplicationInfo().dataDir + "/databases/");

	     setWebChromeClient(new WebChromeClient());
	     
	     // add javascript interface for communication between webview and native\
	     mJSInterface = new JSInterface(onLogin);
	     addJavascriptInterface(mJSInterface, "JSInterface");
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {

	    if (event.getAction() == MotionEvent.ACTION_DOWN){

	        int temp_ScrollY = getScrollY();
	        scrollTo(getScrollX(), getScrollY() + 1);
	        scrollTo(getScrollX(), temp_ScrollY);

	    }

	    return super.onTouchEvent(event);
	}
	
	public void setOnLoginHandler(OnLoginHandler onLogin) {
		this.onLogin = onLogin;
		mJSInterface.setOnLoginHandler(onLogin);
	}
	
	// extended webview constructors //
	public AuthenticationWebView(Context context) {
		super(context);
		
		initWebView(context);
	}

	public AuthenticationWebView(final Context context, final AttributeSet attrs) {
		super(context, attrs);

		initWebView(context);
	}

	public AuthenticationWebView(final Context context, final AttributeSet attrs, final int defStyle) {
		super(context, attrs, defStyle);

		initWebView(context);
	}
	// end of webview constructors //
    
    // Option to enable view to fit in screen
    public void setViewFitInScreen(boolean fitInScreen) {
    	if(!fitInScreen) return;
    	
    	setInitialScale(1);
    	getSettings().setLoadWithOverviewMode(true);
    	getSettings().setUseWideViewPort(true);
    	setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
    	setScrollbarFadingEnabled(false);
    }
    
}
