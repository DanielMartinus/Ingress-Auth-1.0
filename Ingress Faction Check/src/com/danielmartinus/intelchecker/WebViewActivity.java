package com.danielmartinus.intelchecker;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
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

import org.json.JSONException;
import org.json.JSONObject;

import com.commonsware.android.geoweb2.R;

public class WebViewActivity extends Activity {
	private static String PROVIDER = "gps";
	private WebView browser;
	private LocationManager myLocationManager = null;
	private boolean isFirst = true;

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
		browser.addJavascriptInterface(new IJavascriptHandler(), "cpjs");

		browser.setWebViewClient(new WebViewClient() {

			public void onPageFinished(WebView view, String url) {
				// view.loadUrl("javascript:loadScript("+getJsFromAsset("intel-inj.js")
				// + ")");
				view.loadUrl("javascript:{" + getJsFromAsset("test.js") + "}");
				// view.loadUrl("javascript:alert('ass'))");
			}
		});
		browser.loadUrl("https://www.ingress.com/intel/?vp=f");

	}

	final class IJavascriptHandler {
		IJavascriptHandler() {
		}

		// This annotation is required in Jelly Bean and later:
		@JavascriptInterface
		public void sendToAndroid(String text) {
			// this is called from JS with passed value
			Toast t = Toast.makeText(getApplicationContext(), text, 2000);
			t.show();
		}
	}

	protected String getJsFromAsset(String filename) {
		InputStream is = null;
		try {
			is = this.getResources().getAssets().open(filename);
			BufferedReader reader = new BufferedReader(
					new InputStreamReader(is));

			StringBuffer sb = new StringBuffer();
			String line = reader.readLine();
			while (line != null) {
				sb.append(line).append("\n");
				line = reader.readLine();
			}
			return sb.toString();
		} catch (IOException e) {
			// Logger.w("Cannot read the changelog", e);
		} finally {
			if (is != null) {
				try {
					is.close();
				} catch (IOException e) {
				}
			}
		}
		return "";
	}

}