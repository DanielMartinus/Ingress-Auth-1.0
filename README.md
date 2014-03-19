Ingress-Faction-Check-Android
=============================

Add the following permission to your manifest

```Java
<uses-permission android:name="android.permission.INTERNET" /> 
```

When using auto login via Device Accounts add these two permission along with the **Internet** permission to the manifest:

```Java
<uses-permission android:name="android.permission.GET_ACCOUNTS" />
<uses-permission android:name="android.permission.USE_CREDENTIALS" />
```

Initialize the webview with an Activity and **AuthenticationWebView**

```Java
import com.danielmartinus.intelchecker.AuthenticationWebView;
...
AuthenticationWebView webView = (AuthenticationWebView) view.findViewById(R.id.webview);
IntelManager intelManager = new IntelManager(getActivity(), mWebView);
```

To start the login procedure use the following snipper:

```Java
mIntelManager.onLogin(new OnLoginHandler() {

		@Override
		public void onLoginSucces(IntelUser user) {
			Log.e("FactionCheck", "RECEIVED: " + user.getAgentName());
		}

		@Override
		public void onLoginNeedAuthentication() {
			getWebViewActivity().changePage(1);
		}

		@Override
		public void onLoginFailed(String error) {
			Log.e("FactionCheck", error));
		}
});
```
