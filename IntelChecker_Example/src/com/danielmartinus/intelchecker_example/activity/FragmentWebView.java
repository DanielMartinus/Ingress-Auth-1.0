package com.danielmartinus.intelchecker_example.activity;

import com.danielmartinus.intelchecker.Auth_WebViewClient;
import com.danielmartinus.intelchecker.AuthenticationWebView;
import com.danielmartinus.intelchecker.AutoDeviceAuthentication;
import com.danielmartinus.intelchecker.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

public class FragmentWebView extends Fragment {
	
	private AuthenticationWebView mWebView;
	private AutoDeviceAuthentication mAuthentication;
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_webview, container, false);

		mWebView = (AuthenticationWebView) view.findViewById(R.id.webkit);
		
		//Custom webview client handling its own js injection
		mWebView.setWebViewClient(new Auth_WebViewClient(getWebViewActivity()));
		mWebView.setViewFitInScreen(true);
        return view;
    }

	//Load url to intel
	public void LoadIntel() {
		mWebView.loadUrl(WebViewActivity.URL_INTEL);
	}
	
	private WebViewActivity getWebViewActivity() {
		return (WebViewActivity)getActivity();
	}
	
	 /**
     * called by Auth_WebViewClient when the Google login form is opened.
     */
    public void onReceivedLoginRequest(final AuthenticationWebView client, final WebView view, final String realm,
            final String account, final String args) {
//    	mAuthentication = new AutoDeviceAuthentication(getWebViewActivity(), mWebView);
//    	mAuthentication.startLogin(realm, account, args);
    	
    	//TODO: define pages as static position
    	getWebViewActivity().changePage(1); //Change page to webview for login
    }
}
