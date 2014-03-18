package com.danielmartinus.intelchecker;

public abstract class OnLoginHandler {

	public abstract void onLoginSucces(IntelUser user);
	
	public abstract void onLoginFailed(String error);
	
	public abstract void onLoginNeedAuthentication();
}
