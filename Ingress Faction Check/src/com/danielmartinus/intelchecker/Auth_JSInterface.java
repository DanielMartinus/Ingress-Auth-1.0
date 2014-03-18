package com.danielmartinus.intelchecker;

import android.content.Context;
import android.webkit.JavascriptInterface;
import android.widget.Toast;

public class Auth_JSInterface {

	private Context context;
	
	Auth_JSInterface(Context ctx) {
		this.context = ctx;
	}
		
	@JavascriptInterface
	public void sendToAndroid(String text) {
		Toast t = Toast.makeText(context, text, 2000);
		t.show();
	}
	
	//More javascript methods between webview and native here...	
}
