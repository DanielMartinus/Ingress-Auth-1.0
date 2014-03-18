package com.danielmartinus.intelchecker_example.activity;

import java.util.Vector;

import com.danielmartinus.intelchecker.R;
import com.danielmartinus.intelchecker_example.adapter.ViewPagerAdapter;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.webkit.WebView;

public class WebViewActivity extends FragmentActivity {
	
	private ViewPager mPager;
    

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
	
	private ViewPagerAdapter getViewPagerAdapter() {
		return (ViewPagerAdapter)getViewPager().getAdapter();
	}
	
	public FragmentWebView getWebViewFragment() {
		return getViewPagerAdapter().getWebViewFragment();
	}
	
	public FragmentLogin getLoginFragment() {
		return getViewPagerAdapter().getLoginFragment();
	}
	
	public void changePage(int pos) {
		getViewPager().setCurrentItem(pos);
	}
}
