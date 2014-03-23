package com.danielmartinus.intelchecker_example.activity;

import com.danielmartinus.intelchecker.Auth_WebViewClient;
import com.danielmartinus.intelchecker.AuthenticationWebView;
import com.danielmartinus.intelchecker.AutoDeviceAuthentication;
import com.danielmartinus.intelchecker.IntelManager;
import com.danielmartinus.intelchecker.IntelUser;
import com.danielmartinus.intelchecker.OnLoginHandler;
import com.danielmartinus.intelchecker_example.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

public class FragmentWebView extends Fragment {
	
	private AuthenticationWebView mWebView;
	private AutoDeviceAuthentication mAuthentication;
	private IntelManager mIntelManager;
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_webview, container, false);

		mWebView = (AuthenticationWebView) view.findViewById(R.id.webkit);

		mIntelManager = new IntelManager(getActivity(), mWebView);
		mWebView.setViewFitInScreen(true);
        return view;
    }

	//Load url to intel
	public void LoadIntel() {
		mIntelManager.onLogin(new OnLoginHandler() {
			
			@Override
			public void onLoginSucces(IntelUser user) {
				Log.e("HUNTER", "RECEIVED: " + user.getAgentName());
			}
			
			@Override
			public void onLoginNeedAuthentication() {
				getWebViewActivity().changePage(1);
			}
			
			@Override
			public void onLoginFailed(String error) {}
		});
	}
	
	public void logout() {
		mIntelManager.logout();
	}
	
	private WebViewActivity getWebViewActivity() {
		return (WebViewActivity)getActivity();
	}
}
