package com.danielmartinus.intelchecker;

import android.content.Context;

public class IntelManager {

	private Context context;
	private AuthenticationWebView mWebView;
	private AutoDeviceAuthentication mAuthentication;

    public static String URL_INTEL = "https://www.ingress.com/intel/?vp=f";
    
	public IntelManager(Context ctx, AuthenticationWebView webview) {
		this.context = ctx;
		
		mWebView = webview;
		if(webview == null) return;
		
		//Custom webview client handling its own js injection
		mWebView.setWebViewClient(new Auth_WebViewClient(context));
	}
	
	/**
	 * Method to login to the intel
	 * Returns account information 
	 */
	public void onLogin(/*onLoginListener*/) {
		
	}
}
