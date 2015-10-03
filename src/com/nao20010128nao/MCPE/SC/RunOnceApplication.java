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

public class RunOnceApplication extends Application {
	boolean isCheckedMCPE=false;
	UUIDs uuids=new UUIDs();
	public static byte[] mcpeApk;
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
	public void loadMcpeApk(){
		InputStream is=null;
		ByteArrayOutputStream baos=new ByteArrayOutputStream();
		try {
			is = openAPK();
			byte[]buf=new byte[10000];
			while (true) {
				int r=is.read(buf);
				if(r<=0){
					break;
				}
				baos.write(buf,0,r);
			}
		} catch (Throwable e) {

		}finally{
			try {
				is.close();
			} catch (Throwable e) {

			}
		}
		mcpeApk=baos.toByteArray();
	}
	InputStream openAPK() throws IOException,PackageManager.NameNotFoundException {
		switch (Tools.getSettings("input.mode", 0, this)) {
			case 0://installed
				return new FileInputStream(createPackageContext("com.mojang.minecraftpe", CONTEXT_IGNORE_SECURITY).getPackageCodePath());
			case 1://select
				return tryOpen(Tools.getSettings("input.where", "", this));
			default:
				return null;
		}
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
	public InputStream tryOpen(String uri) throws IOException {
		Log.d("dbg", "tryOpen:" + uri);
		if (uri.startsWith("content://")) {
			return getContentResolver().openInputStream(Uri.parse(uri));
		} else if (uri.startsWith("/")) {
			return new FileInputStream(uri);
		} else {
			return URI.create(uri).toURL().openConnection().getInputStream();
		}
	}
}
