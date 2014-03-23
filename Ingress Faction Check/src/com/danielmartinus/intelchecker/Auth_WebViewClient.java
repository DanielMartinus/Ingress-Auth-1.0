package com.danielmartinus.intelchecker;

import android.content.Context;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class Auth_WebViewClient extends WebViewClient {

	private Context context;
	private boolean isInjected = false;
	private String js_user_retrieval = "user-retrieval.js";
	private String js_redirect_first_page = "redirect-first-page.js";

    private OnLoginHandler onLogin;
	
	public Auth_WebViewClient(Context ctx) {
		this.context = ctx;
	}
	
	public void setOnLoginHandler(OnLoginHandler onLogin) {
		this.onLogin = onLogin;
	}
	
	public void onPageFinished(WebView view, String url) {
		if(url.equals(IntelManager.URL_INTEL)) {
			if(isInjected) return;
			//load javascript file from assets
			view.loadUrl("javascript:{" + AssetsFileManager.getJsFromAsset(context, js_user_retrieval) + "}");
			view.loadUrl("javascript:{" + AssetsFileManager.getJsFromAsset(context, js_redirect_first_page) + "}");
			isInjected = true;
		}
	}
	
    /**
     * this method is called automatically when the Google login form is opened.
     */
    @Override
    public void onReceivedLoginRequest(final WebView view, final String realm, final String account, final String args) {
    	isInjected = false;
        Log.d("HUNTER", "Login requested: " + realm + " " + account + " " + args);
        if(onLogin != null) { onLogin.onLoginNeedAuthentication(); }
        //mActivity.getWebViewFragment().onReceivedLoginRequest(null, view, realm, account, args);
        //TODO: fire listener
    }
	
}
