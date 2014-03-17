package com.danielmartinus.intelchecker;

import java.util.Vector;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebView;

public class WebViewActivity extends Activity {
	private AuthenticationWebView mWebView;
	private AutoDeviceAuthentication mAuthentication;
    private final Vector<ResponseHandler> mResponseHandlers = new Vector<ResponseHandler>();
    public static String URL_INTEL = "https://www.ingress.com/intel/?vp=f";

    public interface ResponseHandler {
        void onActivityResult(int resultCode, Intent data);
    }
    
	@SuppressLint("NewApi")
	@Override
	public void onCreate(Bundle icicle) {
		super.onCreate(icicle);
		setContentView(R.layout.main);
		mWebView = (AuthenticationWebView) findViewById(R.id.webkit);

		mWebView.setWebViewClient(new Auth_WebViewClient(this));
//		mWebView.setWebViewClient(new WebViewClient() {
//			public void onPageFinished(WebView view, String url) {
//				//load javscript file from assets
//				view.loadUrl("javascript:{" + AssetsFileManager.getJsFromAsset(getApplicationContext(), "test.js") + "}");
//			}
//		});
//		//load intel url
		mWebView.loadUrl("https://www.ingress.com/intel/?vp=f");
	}
	
	   /**
     * called by IITC_WebViewClient when the Google login form is opened.
     */
    public void onReceivedLoginRequest(final AuthenticationWebView client, final WebView view, final String realm,
            final String account, final String args) {
    	mAuthentication = new AutoDeviceAuthentication(this, mWebView);
    	mAuthentication.startLogin(realm, account, args);
    }
	
    /**
     * called after successful login
     */
//    public void loginSucceeded() {
//        // garbage collection
////        mLogin = null;
////        setLoadingState(true);
//    }
//	
//    public void startActivityForResult(final Intent launch, final ResponseHandler handler) {
//        int index = mResponseHandlers.indexOf(handler);
//        if (index == -1) {
//            mResponseHandlers.add(handler);
//            index = mResponseHandlers.indexOf(handler);
//        }
//
//        startActivityForResult(launch, RESULT_FIRST_USER + index);
//    }
//    
//    public void deleteResponseHandler(final ResponseHandler handler) {
//        final int index = mResponseHandlers.indexOf(handler);
//        if (index != -1) {
//            // set value to null to enable garbage collection, but don't remove it to keep indexes
//            mResponseHandlers.set(index, null);
//        }
//    }
//
//    @Override
//    protected void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
//        final int index = requestCode - RESULT_FIRST_USER;
//
//        try {
//            final ResponseHandler handler = mResponseHandlers.get(index);
//            handler.onActivityResult(resultCode, data);
//        } catch (final ArrayIndexOutOfBoundsException e) {
//            super.onActivityResult(requestCode, resultCode, data);
//        }
//    }
}