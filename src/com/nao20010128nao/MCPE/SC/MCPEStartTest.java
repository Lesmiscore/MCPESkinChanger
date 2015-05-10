package com.nao20010128nao.MCPE.SC;
import android.app.*;
import android.os.*;
import android.content.pm.*;
import android.content.*;

public class MCPEStartTest extends ActivityGroup{
	@Override
	protected void onCreate(Bundle savedInstanceState){
		// TODO: Implement this method
		super.onCreate(savedInstanceState);
		for(ResolveInfo i:getPackageManager().queryIntentActivities(new Intent(Intent.ACTION_MAIN).addCategory(Intent.CATEGORY_LAUNCHER),0)){
			if(i.activityInfo.packageName.equals("com.mojang.minecraftpe")){
				setContentView(getLocalActivityManager().startActivity("main",new Intent(Intent.ACTION_MAIN)
					.addCategory(Intent.CATEGORY_LAUNCHER)
					.setClassName(i.activityInfo.packageName,i.activityInfo.name)).getDecorView());
			}
		}
	}
}
