/***
  Copyright (c) 2008-2012 CommonsWare, LLC
  Licensed under the Apache License, Version 2.0 (the "License"); you may not
  use this file except in compliance with the License. You may obtain a copy
  of the License at http://www.apache.org/licenses/LICENSE-2.0. Unless required
  by applicable law or agreed to in writing, software distributed under the
  License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS
  OF ANY KIND, either express or implied. See the License for the specific
  language governing permissions and limitations under the License.
  
  From _The Busy Coder's Guide to Advanced Android Development_
    http://commonsware.com/AdvAndroid
*/

package com.commonsware.android.geoweb2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.logging.Logger;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import org.json.JSONException;
import org.json.JSONObject;

public class GeoWebTwo extends Activity {
  private static String PROVIDER="gps";
  private WebView browser;
  private LocationManager myLocationManager=null;
  private boolean isFirst = true;
  
  @SuppressLint("NewApi")
@Override
  public void onCreate(Bundle icicle) {
    super.onCreate(icicle);
    setContentView(R.layout.main);
    browser=(WebView)findViewById(R.id.webkit);
    
    myLocationManager=(LocationManager)getSystemService(Context.LOCATION_SERVICE);
    
    browser.getSettings().setJavaScriptEnabled(true);
    browser.getSettings().setDomStorageEnabled(true);
    browser.getSettings().setAllowFileAccess(true);
    
    browser.setWebViewClient(new WebViewClient() {

    	   public void onPageFinished(WebView view, String url) {
    		   if(isFirst) {
    			  // view.loadUrl(getJsFromAsset("intel-inj.js"));
    			   isFirst = false;
    		   }
    	    }
    	});
    browser.loadUrl("https://www.ingress.com/intel/?vp=f");
    
  }
  
  protected String getJsFromAsset(String filename) {
      InputStream is = null;
      try {
          is = this.getResources().getAssets().open(filename);
          BufferedReader reader = new BufferedReader(new InputStreamReader(is));

          StringBuffer sb = new StringBuffer();
          String line = reader.readLine();
          while (line != null) {
              sb.append(line).append("\n");
              line = reader.readLine();
          }
          return sb.toString();
      } catch (IOException e) {
         //Logger.w("Cannot read the changelog", e);
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