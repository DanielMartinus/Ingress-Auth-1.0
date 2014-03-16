package com.danielmartinus.intelchecker;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;

import android.accounts.Account;
import android.accounts.AccountManagerCallback;
import android.accounts.AccountManagerFuture;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class AutoDeviceAuthentication implements AccountManagerCallback<Bundle>, ResponseHandler {

    private Account mAccount;
    private Account[] mAccounts;
    private Context context;
	
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
        public View getView(final int position, final View convertView, final ViewGroup parent) {
            final View v = mInflater.inflate(android.R.layout.simple_list_item_1, parent, false);

            final TextView tv = (TextView) v.findViewById(android.R.id.text1);
            tv.setText(mAccounts[position].name);

            return tv;
        }
    }
    
    /**
     * This listener is invoked when an item in the account list is selected.
     * (It is also used when the 'cancel' button is clicked, (in which case `index` is <0)
     */
    private final DialogInterface.OnClickListener onClickListener =
            new DialogInterface.OnClickListener() {
                @Override
                public void onClick(final DialogInterface dialog, final int index) {
                    if (index >= 0 && index < mAccounts.length) {
                        mAccount = mAccounts[index];
                        //startAuthentication();
                    }
                    dialog.cancel();
                }
            };
	
	@Override
	public Object handleResponse(HttpResponse arg0)
			throws ClientProtocolException, IOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void run(AccountManagerFuture<Bundle> arg0) {
		// TODO Auto-generated method stub
		
	}

}
