package com.danielmartinus.intelchecker_example.activity;

import com.danielmartinus.intelchecker.AuthenticationWebView;
import com.danielmartinus.intelchecker.IntelManager;
import com.danielmartinus.intelchecker.IntelUser;
import com.danielmartinus.intelchecker.OnLoginHandler;
import com.danielmartinus.intelchecker_example.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class AuthenticationActivity extends Activity {

	private boolean isConnection = true;
	
	private TextView lblLoadingTask;
	private ImageView mAnimation;
	private RelativeLayout mAuthLayer;
	private AuthenticationWebView mWebView;
	private IntelManager mIntelManager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_authenticate);
		getActionBar().hide();

		TextView lblAboutIAuth = (TextView) findViewById(R.id.lblAbout);
		lblAboutIAuth.setText(Html.fromHtml("This is the demo of <b><u>Ingress Auth 1.0</u></b>."));
		lblLoadingTask = (TextView) findViewById(R.id.lblLoadingTask);
		mAnimation = (ImageView) findViewById(R.id.anim_loading);

		mAuthLayer = (RelativeLayout) findViewById(R.id.AuthenticateLayer);
		mAuthLayer.setOnClickListener(onClickTryAgain());
		mWebView = (AuthenticationWebView) findViewById(R.id.webkit);
		//mWebView.setViewFitInScreen(true);

		mIntelManager = new IntelManager(this, mWebView);

		startLogin();
	}

	public void startLogin() {
		LoadIntel();
		startAnimation();
	}
	
	// Load url to intel
	public void LoadIntel() {
		lblLoadingTask.setText(getResources().getString(R.string.lbl_auth_verifying));
		
		mIntelManager.onLogin(new OnLoginHandler() {

			@Override
			public void onLoginSucces(final IntelUser user) {
				Log.e("HUNTER", "RECEIVED: " + user.getAgentName());
				isConnection = true;
				runOnUiThread(new Runnable() {
					public void run() {
						String format = String.format(getResources().getString(R.string.lbl_auth_user_found), user.getAgentName());
						lblLoadingTask.setText(format);
						Intent i =  new Intent(getApplicationContext(), UserActivity.class);
						i.putExtra("user", user);
						startActivity(i);
					}
				});
			}

			@Override
			public void onLoginNeedAuthentication() {
				isConnection = true;
				lblLoadingTask.setText(getResources().getString(R.string.lbl_auth_required));
				mAuthLayer.setVisibility(View.GONE);
			}

			@Override
			public void onLoginFailed(final int errorCode, String error) {
				runOnUiThread(new Runnable() {
					public void run() {
						if(errorCode == IntelManager.ERROR_CODE_NO_INTERNET || errorCode == IntelManager.ERROR_CODE_TIMED_OUT) {
							isConnection = false;
							lblLoadingTask.setText(getResources().getString(R.string.lbl_auth_no_connection));
						}
					}
				});
			}
		}); 
	}

	private AuthenticationActivity getWebViewActivity() {
		return (AuthenticationActivity) this;
	}

	public void startAnimation() {
		// Create an animation
		RotateAnimation rotation = new RotateAnimation(0f, 360f,
				Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
				0.5f);
		rotation.setDuration(1200);
		rotation.setInterpolator(new LinearInterpolator());
		rotation.setRepeatMode(Animation.RESTART);
		rotation.setRepeatCount(Animation.INFINITE);

		// and apply it to your imageview
		// findViewById(R.id.anim_loading).startAnimation(rotation);
		mAnimation.startAnimation(rotation);
	}
	
	private OnClickListener onClickTryAgain() {
		return new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				if(!isConnection) { 
					startLogin(); 
					lblLoadingTask.setText(getResources().getString(R.string.lbl_auth_verifying));
				}
			}
		};
	}
}
