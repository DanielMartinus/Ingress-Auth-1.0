package com.danielmartinus.intelchecker;

import android.content.Context;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class Auth_WebViewClient extends WebViewClient {

	private WebViewActivity mActivity;
	private boolean isInjected = false;
	private String js_user_retrieval = "user-retrieval.js";
	private String js_redirect_first_page = "redirect-first-page.js";
	
	public Auth_WebViewClient(WebViewActivity activity) {
		this.mActivity = activity;
	}
	
	public void onPageFinished(WebView view, String url) {
		if(url.equals(WebViewActivity.URL_INTEL)) {
			if(isInjected) return;
			//load javascript file from assets
			view.loadUrl("javascript:{" + AssetsFileManager.getJsFromAsset(mActivity, js_redirect_first_page) + "}");
			view.loadUrl("javascript:{" + AssetsFileManager.getJsFromAsset(mActivity, js_user_retrieval) + "}");
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
    	mActivity.onReceivedLoginRequest(null, view, realm, account, args);
    }
	
}
