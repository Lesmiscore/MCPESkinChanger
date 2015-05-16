package com.nao20010128nao.MCPE.SC;
import android.app.*;
import android.content.res.*;

public class RunOnceApplication extends Application
{
	boolean isCheckedMCPE=false;
	public static RunOnceApplication instance;
	@Override
	public void onCreate()
	{
		// TODO: Implement this method
		super.onCreate();
		instance=this;
	}
	public boolean isCheckedMCPE(){
		return isCheckedMCPE;
	}
	public void completeCheckMCPE(){
		isCheckedMCPE=true;
	}
}
