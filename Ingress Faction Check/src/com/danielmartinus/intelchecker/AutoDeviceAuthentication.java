package com.danielmartinus.intelchecker;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.accounts.AccountManagerCallback;
import android.accounts.AccountManagerFuture;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class AutoDeviceAuthentication implements
		AccountManagerCallback<Bundle>, ResponseHandler {

	private WebView mWebView;
	private Account mAccount;
	private Account[] mAccounts;
    private String mAuthToken;
    private AccountAdapter mAccountAdapter;
    private final AccountManager mAccountManager;
	private WebViewActivity mWebViewActivity;

	private class AccountAdapter extends BaseAdapter {

		private LayoutInflater mInflater;

		public AccountAdapter(Context ctx) {
			mInflater = LayoutInflater.from(ctx);
		}

		@Override
		public int getCount() {
			return mAccounts.length;
		}

		@Override
		public Account getItem(final int position) {
			return mAccounts[position];
		}

		@Override
		public long getItemId(final int position) {
			return position;
		}

		@Override
		public View getView(final int position, final View convertView,
				final ViewGroup parent) {
			final View v = mInflater.inflate(
					android.R.layout.simple_list_item_1, parent, false);

			final TextView tv = (TextView) v.findViewById(android.R.id.text1);
			tv.setText(mAccounts[position].name);

			return tv;
		}
	}
	
	public AutoDeviceAuthentication(WebViewActivity wViewActivity, WebView webView) {
        mWebView = webView;
        mWebViewActivity = wViewActivity;
        mAccountManager = AccountManager.get(wViewActivity);
        mAccountAdapter = new AccountAdapter(wViewActivity);
	}

	/**
	 * This listener is invoked when an item in the account list is selected.
	 * (It is also used when the 'cancel' button is clicked, (in which case
	 * `index` is <0)
	 */
	private final DialogInterface.OnClickListener onClickListener = new DialogInterface.OnClickListener() {
		@Override
		public void onClick(final DialogInterface dialog, final int index) {
			if (index >= 0 && index < mAccounts.length) {
				mAccount = mAccounts[index];
				// startAuthentication();
			}
			dialog.cancel();
		}
	};

	/**
	 * display all available accounts to the user
	 */
	private void displayAccountList() {
		new AlertDialog.Builder(mWebViewActivity)
				.setTitle("Choose account to login")
				.setSingleChoiceItems(mAccountAdapter, 0, onClickListener)
				.setNegativeButton(android.R.string.cancel, onClickListener)
				.create().show();
	}
	
    /**
     * called when something failed. Shows a toast message. Classic login is still available
     */
    private void onLoginFailed() {
        Toast.makeText(mWebViewActivity, "failed login via device account", Toast.LENGTH_SHORT).show();
    }

    /**
     * called to start authenticating using AccountManager.
     * <p/>
     * After a token is created, AccountManager will call the run() method.
     */
    private void startAuthentication() {
        mAccountManager.getAuthToken(mAccount, mAuthToken, null, mWebViewActivity, this, null);
    }

    /**
     * called by IITC_Mobile when the authentication activity has finished.
     */
    @Override
    public void onActivityResult(final int resultCode, final Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            // authentication activity succeeded, request token again
            startAuthentication();
        } else {
            onLoginFailed();
        }
    }
    
	@Override
	public Object handleResponse(HttpResponse arg0)
			throws ClientProtocolException, IOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void run(AccountManagerFuture<Bundle> bundle) {
        try {
            final Intent launch = (Intent) bundle.getResult().get(AccountManager.KEY_INTENT);
            if (launch != null) {
                // There is a reason we need to start the given activity if we want an
                // authentication token. (Could be user confirmation or something else. Whatever,
                // we have to start it) IITC_Mobile will call it using startActivityForResult
            	mWebViewActivity.startActivityForResult(launch, this);
                return;
            }

            final String result = bundle.getResult().getString(AccountManager.KEY_AUTHTOKEN);
            if (result != null) {
                // authentication succeeded, we can load the given url, which will redirect
                // back to the intel map
                mWebView.loadUrl(result);
                mWebViewActivity.loginSucceeded();
            } else {
                onLoginFailed();
            }
        } catch (final Exception e) {
            onLoginFailed();
        }
	}
}
