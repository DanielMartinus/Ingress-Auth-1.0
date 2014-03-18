package com.danielmartinus.intelchecker;

public abstract class OnLoginHandler {

	public abstract void onLoginSucces();
	
	public abstract void onLoginFailed();
	
	public abstract void onLoginNeedAuthentication();
}
