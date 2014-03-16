package com.danielmartinus.intelchecker;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Vector;
import java.util.logging.Logger;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings.PluginState;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import org.apache.http.client.ResponseHandler;
import org.json.JSONException;
import org.json.JSONObject;

import com.commonsware.android.geoweb2.R;

public class WebViewActivity extends Activity {
	private static String PROVIDER = "gps";
	private WebView browser;
	private LocationManager myLocationManager = null;
	private boolean isFirst = true;
    private final Vector<ResponseHandler> mResponseHandlers = new Vector<ResponseHandler>();

    public interface ResponseHandler {
        void onActivityResult(int resultCode, Intent data);
    }
    
	@SuppressLint("NewApi")
	@Override
	public void onCreate(Bundle icicle) {
		super.onCreate(icicle);
		setContentView(R.layout.main);
		browser = (WebView) findViewById(R.id.webkit);

		myLocationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		browser.getSettings().setJavaScriptEnabled(true);
		browser.setInitialScale(1);
		browser.getSettings().setLoadWithOverviewMode(true);
		browser.getSettings().setUseWideViewPort(true);
		browser.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
		browser.setScrollbarFadingEnabled(false);
		browser.getSettings().setPluginState(PluginState.ON);
		browser.getSettings().setDomStorageEnabled(true);
		browser.getSettings().setAllowFileAccess(true);
		browser.setWebChromeClient(new WebChromeClient());
		browser.addJavascriptInterface(new Auth_JSInterface(this), "cpjs");

		browser.setWebViewClient(new WebViewClient() {

			public void onPageFinished(WebView view, String url) {
				// view.loadUrl("javascript:loadScript("+getJsFromAsset("intel-inj.js")
				// + ")");
				view.loadUrl("javascript:{" + AssetsFileManager.getJsFromAsset(getApplicationContext(), "test.js") + "}");
				// view.loadUrl("javascript:alert('ass'))");
			}
		});
		browser.loadUrl("https://www.ingress.com/intel/?vp=f");

	}
	
    /**
     * called after successful login
     */
    public void loginSucceeded() {
        // garbage collection
//        mLogin = null;
//        setLoadingState(true);
    }
	
    public void startActivityForResult(final Intent launch, final ResponseHandler handler) {
        int index = mResponseHandlers.indexOf(handler);
        if (index == -1) {
            mResponseHandlers.add(handler);
            index = mResponseHandlers.indexOf(handler);
        }

        startActivityForResult(launch, RESULT_FIRST_USER + index);
    }
    
    public void deleteResponseHandler(final ResponseHandler handler) {
        final int index = mResponseHandlers.indexOf(handler);
        if (index != -1) {
            // set value to null to enable garbage collection, but don't remove it to keep indexes
            mResponseHandlers.set(index, null);
        }
    }

    @Override
    protected void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
        final int index = requestCode - RESULT_FIRST_USER;

        try {
            final ResponseHandler handler = mResponseHandlers.get(index);
            handler.onActivityResult(resultCode, data);
        } catch (final ArrayIndexOutOfBoundsException e) {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

}