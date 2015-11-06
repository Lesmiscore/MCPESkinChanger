package com.nao20010128nao.MCPE.SC;
import android.app.*;
import android.content.res.*;
import android.content.*;
import java.io.*;
import com.nao20010128nao.SpoofBrowser.classes.*;
import android.content.pm.*;
import android.util.*;
import java.net.*;
import android.net.*;
import android.content.pm.PackageManager.*;
import com.nao20010128nao.MC_PE.SkinChanger.*;
import android.graphics.*;

public class RunOnceApplication extends Application {
	boolean isCheckedApp=false;
	UUIDs uuids=new UUIDs();
	public Typeface accentFont;
	public static byte[] mcpeApk;
	public static RunOnceApplication instance;

	@Override
	public void onCreate() {
		// TODO: Implement this method
		super.onCreate();
		instance = this;
		accentFont=Typeface.createFromAsset(getAssets(),"font.ttf");
	}
	public boolean isCheckedApp() {
		return isCheckedApp;
	}
	public void completeCheckApp() {
		isCheckedApp = true;
	}
	public int getWorkMode(){
		int wm=Tools.getSettings("workMode",-1,this);
		if(wm==-1){
			wm=getResources().getInteger(R.integer.defaultWorkMode);
		}
		return wm;
	}
	public void setWorkMode(int mode){
		Tools.setSettings("workMode",mode,this);
	}
	public int getChangerImpl(){
		return Tools.getSettings("changer",0,this);
	}
	public void setChangerImpl(int id){
		Tools.setSettings("changer",id,this);
	}
	public UUIDs getUuids() {
		return uuids;
	}
	public String getApkPath() {
		switch (Tools.getSettings("input.mode", 0, this)) {
			case 0://installed
				try {
					return createPackageContext("com.mojang.minecraftpe", CONTEXT_IGNORE_SECURITY).getPackageCodePath();
				} catch (PackageManager.NameNotFoundException e) {
					return null;
				}
			case 1://select
				return Tools.getSettings("input.where", "", this);
			default:
				return null;
		}
	}
}
