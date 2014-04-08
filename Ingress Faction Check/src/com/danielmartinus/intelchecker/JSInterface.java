package com.danielmartinus.intelchecker;

import android.util.Log;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;

public class JSInterface {
	
	WebView webView;
	OnLoginHandler onLogin;
	
	JSInterface(OnLoginHandler onLogin, WebView webView) {
		this.webView = webView;
		this.onLogin = onLogin;
	}
	
	public void setOnLoginHandler(OnLoginHandler onLogin) {
		this.onLogin = onLogin;
	}

	@JavascriptInterface
	public void sendToAndroid(String agentName, String faction, String email, String ap, String energy, String invites) {
		IntelUser user = new IntelUser(agentName, faction, email, ap, energy, invites);

		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) { e.printStackTrace(); }
		if(onLogin != null) { onLogin.onLoginSucces(user); }
	}
	
	@JavascriptInterface
	public void loadUrl(final String url) {
		Log.e("HUNTER", "javascript callback: (loadurl)" + url);
		//webView.loadUrl(url);
		webView.post(new Runnable() {
		    @Override
		    public void run() {
		    	webView.loadUrl(url);
		    }
		});
	}
	
	//More javascript methods between webview and native here...	
}
