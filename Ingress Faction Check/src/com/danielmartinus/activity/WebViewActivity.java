package com.danielmartinus.activity;

import java.util.Vector;

import com.danielmartinus.adapter.ViewPagerAdapter;
import com.danielmartinus.intelchecker.Auth_WebViewClient;
import com.danielmartinus.intelchecker.AuthenticationWebView;
import com.danielmartinus.intelchecker.AutoDeviceAuthentication;
import com.danielmartinus.intelchecker.R;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.webkit.WebView;

public class WebViewActivity extends FragmentActivity {
	
	private ViewPager mPager;
    
    public static String URL_INTEL = "https://www.ingress.com/intel/?vp=f";

    public interface ResponseHandler {
        void onActivityResult(int resultCode, Intent data);
    }
    
	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		getViewPager().setAdapter(new ViewPagerAdapter(this.getSupportFragmentManager(), this));
	}
	
	private ViewPager getViewPager() {
		if(mPager == null) {
			this.mPager = (ViewPager)findViewById(R.id.pager);
		}
		return mPager;
	}
	
	public void changePage(int pos) {
		getViewPager().setCurrentItem(pos);
	}
}
