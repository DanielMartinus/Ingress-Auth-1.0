package com.danielmartinus.intelchecker_example.activity;

import com.danielmartinus.intelchecker.IntelManager;
import com.danielmartinus.intelchecker.IntelUser;
import com.danielmartinus.intelchecker_example.R;
import com.danielmartinus.intelchecker_example.R.id;
import com.danielmartinus.intelchecker_example.R.layout;
import com.danielmartinus.intelchecker_example.R.menu;

import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.os.Build;

public class UserActivity extends Activity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_user);

		Intent intent = getIntent();
		IntelUser user = (IntelUser)intent.getSerializableExtra("user");
		if (savedInstanceState == null) {
			PlaceholderFragment holderFragment = new PlaceholderFragment();
			holderFragment.setUser(user);
			getFragmentManager().beginTransaction().add(R.id.container, holderFragment).commit();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.user, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			IntelManager.logout(getApplicationContext());
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {
		IntelUser user;
		public PlaceholderFragment() {}
		
		public void setUser(IntelUser user) {
			this.user = user;
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_user, container,	false);
			TextView lblText = (TextView)rootView.findViewById(R.id.lblResult);
			lblText.setText(Html.fromHtml("<b>Welcome: </b> " + user.getAgentName() +"<br/> <b>faction:</b> " +user.getFaction()));
			return rootView;
		}
	}

}
