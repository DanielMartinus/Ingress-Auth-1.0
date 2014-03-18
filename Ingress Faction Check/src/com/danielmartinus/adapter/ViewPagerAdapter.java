package com.danielmartinus.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;


public class ViewPagerAdapter extends PagerAdapter {

	private static int sLoginFragment = 0;
	private static int sWegViewFragment = 1;
	
	@Override
	public int getCount() {
		return 2;
	}

	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		if(getCount() == sLoginFragment) {
			
		} else if (getCount() == sWegViewFragment) {
			
		}
		return false;
	}

}
