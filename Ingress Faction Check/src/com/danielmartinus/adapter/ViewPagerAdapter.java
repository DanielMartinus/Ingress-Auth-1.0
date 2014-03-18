package com.danielmartinus.adapter;

import java.util.HashMap;

import com.danielmartinus.activity.FragmentLogin;
import com.danielmartinus.activity.FragmentWebView;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.LayoutInflater;
import android.view.View;

public class ViewPagerAdapter extends FragmentPagerAdapter {

	private Context mContext;
	private LayoutInflater mInflater;
	private FragmentLogin fragmentLogin;
	private FragmentWebView fragmentWebView;

	public ViewPagerAdapter(FragmentManager fm, Context ctx) {
		super(fm);
		this.mContext = ctx;
		this.mInflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public int getCount() {
		return 2;
	}
	
	public FragmentLogin getLoginFragment() {
		return fragmentLogin;
	}
	
	public FragmentWebView getWebViewFragment() {
		return fragmentWebView;
	}

	@Override
	public Fragment getItem(int page) {
		switch (page) {
		case 0:
			if(fragmentLogin == null) {
				fragmentLogin = new FragmentLogin();
			}
			return fragmentLogin;
		case 1:
			if(fragmentWebView == null) {
				fragmentWebView = new FragmentWebView();
			}
			return fragmentWebView;
		}
		return null;
	}
}

