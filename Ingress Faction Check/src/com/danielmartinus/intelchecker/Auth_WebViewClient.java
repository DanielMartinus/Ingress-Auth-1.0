package com.danielmartinus.intelchecker;

import com.danielmartinus.intelchecker.autodevicelogin.AutoDeviceAuthentication;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class Auth_WebViewClient extends WebViewClient {

	private Context context;
	private boolean isInjected = false;
	private String js_user_retrieval = "user-retrieval.js";

    private OnLoginHandler onLogin;
    private AutoDeviceAuthentication mDeviceAuth;
	
	public Auth_WebViewClient(Context ctx) {
		this.context = ctx;
	}
	
	public void setAutoDeviceAuthentication(Activity activity, WebView webView) {
		mDeviceAuth = new AutoDeviceAuthentication(activity, webView);
	}
	
	public void setOnLoginHandler(OnLoginHandler onLogin) {
		this.onLogin = onLogin;
	}
	
	@Override
	public void onPageFinished(WebView view, String url) {
		if(url.equals(IntelManager.URL_INTEL)) {
			Log.e("HUNTER" , "onpagefinishesd");
			//if(isInjected) return;
			//load javascript file from assets
			view.loadUrl("javascript:{" + AssetsFileManager.getJsFromAsset(context, js_user_retrieval) + "}");
			//isInjected = true;
		} 
	}
	
	@Override
	public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
		if(errorCode == IntelManager.ERROR_CODE_NO_INTERNET) {
			onLogin.onLoginFailed(errorCode, "No internet connection");
		} else if(errorCode == IntelManager.ERROR_CODE_TIMED_OUT) {
			onLogin.onLoginFailed(errorCode, "Request timed out");
		} else {
			Log.e("HUNTER", "Unknown error code: " + String.valueOf(errorCode) + " Response: " +  description + " failed at: " + failingUrl);
		}
	}
	
    /**
     * this method is called automatically when the Google login form is opened.
     */
    @Override
    public void onReceivedLoginRequest(final WebView view, final String realm, final String account, final String args) {
    	isInjected = false;
        Log.d("HUNTER", "Login requested: " + realm + " " + account + " " + args);
        if(mDeviceAuth != null) { mDeviceAuth.startLogin(realm, account, args); }
        if(onLogin != null) { onLogin.onLoginNeedAuthentication(); }
    }
}
