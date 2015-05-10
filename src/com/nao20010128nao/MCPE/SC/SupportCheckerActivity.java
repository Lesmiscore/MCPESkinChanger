package com.nao20010128nao.MCPE.SC;
import android.app.*;
import android.os.*;
import android.content.pm.*;
import android.content.*;
import android.content.pm.PackageManager.*;
import java.io.*;
import android.util.*;
import android.widget.*;
import com.nao20010128nao.MC_PE.SkinChanger.*;

public class SupportCheckerActivity extends Activity{
	@Override
	protected void onCreate(Bundle savedInstanceState){
		// TODO: Implement this method
		super.onCreate(savedInstanceState);
		setContentView(R.layout.checker);
		final ProgressBar progress=(ProgressBar)findViewById(R.id.pbProgress);
		final TextView state=(TextView)findViewById(R.id.tvState);
		new AsyncTask<Void,Void,Void>(){
			public Void doInBackground(Void[] p){
				runOnUiThread(new Runnable(){
					public void run(){
						progress.setMax(2);
						state.setText(getResources().getStringArray(R.array.checkName)[0]);
					}
				});
				boolean ok=false;
				/*Step 1*/
				for(PackageInfo i:getPackageManager().getInstalledPackages(PackageManager.GET_ACTIVITIES|PackageManager.GET_CONFIGURATIONS)){
					if(i.packageName.equals("com.mojang.minecraftpe")){
						ok=true;
						break;
					}
					Log.d("dbg_sca",i.packageName);
				}
				if(ok){
					runOnUiThread(new Runnable(){
							public void run(){
								progress.setProgress(1);
								state.setText(getResources().getStringArray(R.array.checkName)[1]);
							}
						});
				}else{
					new AlertDialog.Builder(SupportCheckerActivity.this)
						.setTitle(R.string.err_title)
						.setCancelable(false)
						.setMessage(getResources().getStringArray(R.array.errors)[0])
						.setNegativeButton(android.R.string.ok,new DialogInterface.OnClickListener(){
							public void onClick(DialogInterface a,int b){
								a.cancel();
								runOnUiThread(new Runnable(){
										public void run(){
											MainActivity.instance.get().finish();
											finish();
										}
									});
							}
						})
						.create()
						.show();
					return null;
				}
				ok=false;
				/*Step 2*/
				try{
					Context c=createPackageContext("com.mojang.minecraftpe", CONTEXT_IGNORE_SECURITY);
					String s=c.getPackageCodePath();
					new FileInputStream(s).close();
					Log.d("dbg_sca",s);
					s=c.getPackageResourcePath();
					new FileInputStream(s).close();
					Log.d("dbg_sca",s);
					ok=true;
				}catch (PackageManager.NameNotFoundException e){
					e.printStackTrace();
				}catch (IOException err){
					err.printStackTrace(System.out);
				}
				if (ok){
					runOnUiThread(new Runnable(){
							public void run(){
								progress.setProgress(2);
							}
						});
				}else{
					new AlertDialog.Builder(SupportCheckerActivity.this)
						.setTitle(R.string.err_title)
						.setCancelable(false)
						.setMessage(getResources().getStringArray(R.array.errors)[1])
						.setNegativeButton(android.R.string.ok,new DialogInterface.OnClickListener(){
							public void onClick(DialogInterface a,int b){
								a.cancel();
								runOnUiThread(new Runnable(){
										public void run(){
											MainActivity.instance.get().finish();
											finish();
										}
									});
							}
						})
						.create()
						.show();
					return null;
				}
				return null;
			}
			public void onPostExecute(Void r){
				finish();
			}
		}.execute();
	}
}
