package com.nao20010128nao.MCPE.SC;
import android.app.*;
import android.content.res.*;
import android.content.*;

public class RunOnceApplication extends Application {
	boolean isCheckedMCPE=false;
	UUIDs uuids=new UUIDs();
	public static RunOnceApplication instance;

	@Override
	public void onCreate() {
		// TODO: Implement this method
		super.onCreate();
		instance = this;
	}
	public boolean isCheckedMCPE() {
		return isCheckedMCPE;
	}
	public void completeCheckMCPE() {
		isCheckedMCPE = true;
	}
	public UUIDs getUuids() {
		return uuids;
	}
}
