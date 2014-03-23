package com.danielmartinus.intelchecker;

import java.io.File;
import java.util.Date;

import android.app.Activity;
import android.content.Context;
import android.text.format.DateUtils;
import android.util.Log;

public class IntelManager {

	private Context context;
	private AuthenticationWebView mWebView;
	private Auth_WebViewClient mWebClient;
	private AutoDeviceAuthentication mAuthentication;
	private OnLoginHandler mOnLoginHandler;

    public static String URL_INTEL = "https://www.ingress.com/intel/?vp=f";
    
	public IntelManager(Context ctx, AuthenticationWebView webview) {
		this.context = ctx;
		
		mWebView = webview;
		if(webview == null) return;

		//Custom webview client handling its own js injection
		mWebClient = new Auth_WebViewClient(context);
		mWebView.setWebViewClient(mWebClient);
	}
	
	/**
	 * Method to login to the intel
	 * Returns account information 
	 */
	public void onLogin(OnLoginHandler onLoginHandler) {
		mOnLoginHandler = onLoginHandler;
		mWebView.setOnLoginHandler(onLoginHandler);
		mWebClient.setOnLoginHandler(onLoginHandler);
		mWebView.loadUrl(URL_INTEL);
	}
	
	public void logout() {
		clearCache(context, 0);
	}
	
	//helper method for clearCache() , recursive
	//returns number of deleted files
	private int clearCacheFolder(final File dir, final int numDays) {

	    int deletedFiles = 0;
	    if (dir!= null && dir.isDirectory()) {
	        try {
	            for (File child:dir.listFiles()) {

	                //first delete subdirectories recursively
	                if (child.isDirectory()) {
	                    deletedFiles += clearCacheFolder(child, numDays);
	                }

	                //then delete the files and subdirectories in this dir
	                //only empty directories can be deleted, so subdirs have been done first
	                if (child.lastModified() < new Date().getTime() - numDays * DateUtils.DAY_IN_MILLIS) {
	                    if (child.delete()) {
	                        deletedFiles++;
	                    }
	                }
	            }
	        }
	        catch(Exception e) {
	            Log.e("HUNTER", String.format("Failed to clean the cache, error %s", e.getMessage()));
	        }
	    }
	    return deletedFiles;
	}

	/*
	 * Delete the files older than numDays days from the application cache
	 * 0 means all files.
	 */
	public void clearCache(final Context context, final int numDays) {
	    File file = new File("/data/data/com.danielmartinus.intelchecker_example/app_webview");
	    int numDeletedFiles = clearCacheFolder(file, numDays);
	    context.deleteDatabase("webviewCookiesChromium.db");
	    context.deleteDatabase("webviewCookiesChromiumPrivate.db");
	   // Log.i("HUNTER", String.format("Cache pruning completed, %d files deleted", numDeletedFiles));
	}
}
