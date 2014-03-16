package com.danielmartinus.intelchecker;

import android.content.Context;
import android.util.AttributeSet;
import android.webkit.WebView;

public class AuthenticationWebView extends WebView {

	// extended webview constructors //
	public AuthenticationWebView(Context context) {
		super(context);
	}

	public AuthenticationWebView(final Context context, final AttributeSet attrs) {
        super(context, attrs);

    }

    public AuthenticationWebView(final Context context, final AttributeSet attrs, final int defStyle) {
        super(context, attrs, defStyle);
    }
    // end of webview constructors //
    
}
