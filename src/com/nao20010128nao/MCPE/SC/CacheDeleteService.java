package com.nao20010128nao.MCPE.SC;
import android.app.*;
import android.content.*;
import android.os.*;
import java.io.*;
import android.util.*;

public class CacheDeleteService extends Service {

	@Override
	public IBinder onBind(Intent p1) {
		// TODO: Implement this method
		return null;
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// TODO: Implement this method
		new AsyncTask<Void,Void,Void>(){
			public Void doInBackground(Void... a) {
				try {
					File cacheDir=new File(getFilesDir(), "cache");
					cacheDir.mkdirs();
					new ProcessBuilder().
						command(new String[]{"/system/bin/rm","-rf",cacheDir.getAbsolutePath()}).
						directory(cacheDir).
						start().
						waitFor();
				} catch (Throwable ex) {

				}
				return null;
			}
		}.execute();
		return START_NOT_STICKY;
	}
}
