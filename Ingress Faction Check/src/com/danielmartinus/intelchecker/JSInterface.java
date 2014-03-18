package com.danielmartinus.intelchecker;

import android.content.Context;
import android.util.Log;
import android.webkit.JavascriptInterface;
import android.widget.Toast;

public class JSInterface {
	
	OnLoginHandler onLogin;
	
	JSInterface(OnLoginHandler onLogin) {
		this.onLogin = onLogin;
	}
	
	public void setOnLoginHandler(OnLoginHandler onLogin) {
		this.onLogin = onLogin;
	}

	@JavascriptInterface
	public void sendToAndroid(String agentName, String faction, String email, String ap, String energy, String invites) {
		IntelUser user = new IntelUser(agentName, faction, email, ap, energy, invites);
		if(onLogin != null) { onLogin.onLoginSucces(user); }
	}
	
	//More javascript methods between webview and native here...	
}
