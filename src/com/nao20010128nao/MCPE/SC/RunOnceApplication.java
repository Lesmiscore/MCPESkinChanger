package com.nao20010128nao.MCPE.SC;
import android.app.*;
import android.content.res.*;
import android.content.*;

public class RunOnceApplication extends Application {
	boolean isCheckedMCPE=false;
	public static RunOnceApplication instance;
	@Override
	public void onCreate() {
		// TODO: Implement this method
		super.onCreate();
		instance = this;
		if (ModificationService.instance.get() != null) {
			startActivity(new Intent(this, ModificateActivity.class).putExtra("mode", "noservice"));
			MainActivity.preventStart();
		} else
			startActivity(new Intent(this, SupportCheckerActivity.class));
		startService(new Intent(this, CacheDeleteService.class));
	}
	public boolean isCheckedMCPE() {
		return isCheckedMCPE;
	}
	public void completeCheckMCPE() {
		isCheckedMCPE = true;
	}
}
