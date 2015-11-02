package com.nao20010128nao.MCPE.SC;
import android.app.*;
import android.content.*;
import android.content.pm.*;
import android.os.*;
import android.util.*;
import com.nao20010128nao.MC_PE.SkinChanger.*;
import com.nao20010128nao.SpoofBrowser.classes.*;
import java.io.*;
import android.widget.*;
import java.util.*;

public class SplashActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO: Implement this method
		super.onCreate(savedInstanceState);
		if (RunOnceApplication.instance.isCheckedApp()) {
			finish();
			return;
		}
		setContentView(R.layout.splash);
		((TextView)findViewById(R.id.appName)).setTypeface(RunOnceApplication.instance.accentFontObj);
		hideActionbar();
		new AsyncTask<Void,Void,Void>(){
			public Void doInBackground(Void[] p) {
				Looper.prepare();
				{
					BufferedReader br=null;
					Set<String> corruptClasses=new HashSet<>();
					try {
						br = new BufferedReader(new InputStreamReader(getAssets().open("names.app.txt")));
						String s=null;
						while(null!=(s=br.readLine())){
							try {
								Class.forName(s).getName();
								getClassLoader().loadClass(s).getName();
							} catch (Throwable e) {
								e.printStackTrace();
								corruptClasses.add(s);
							}
						}
					} catch (IOException e) {
						e.printStackTrace();
					}finally{
						try {
							br.close();
						} catch (Throwable e) {}
						//corruptClasses.remove(null);
					}
					Log.d("len",corruptClasses.size()+"");
					if(!corruptClasses.isEmpty()){
						StringBuilder sb=new StringBuilder(BuildConfig.DEBUG?getResources().getString(R.string.contactDev_ANCC):getResources().getString(R.string.contactDev_ANCC));
						sb.append('\n');
						for(String s:corruptClasses){
							sb.append(s).append('\n');
						}
						sb.setLength(sb.length()-1);//Delete the least "\n"
						new AlertDialog.Builder(SplashActivity.this)
							.setTitle(R.string.appNotCompiledCorrectly)
							.setCancelable(false)
							.setMessage(sb.toString())
							.setNegativeButton(android.R.string.ok, new DialogInterface.OnClickListener(){
								public void onClick(DialogInterface a, int b) {
									Log.e("EMERGENCY","THIS APP IS BROKEN!");
									System.exit(0);
								}
							})
							.create()
							.show();
						return null;
					}
				}
				boolean ok=false;
				/*Step 1*/
				for (PackageInfo i:getPackageManager().getInstalledPackages(PackageManager.GET_ACTIVITIES | PackageManager.GET_CONFIGURATIONS)) {
					if (i.packageName.equals("com.mojang.minecraftpe")) {
						ok = true;
						break;
					}
					Log.d("dbg_sca", i.packageName);
				}
				if (!ok) {
					new AlertDialog.Builder(SplashActivity.this)
						.setTitle(R.string.err_title)
						.setCancelable(false)
						.setMessage(getResources().getStringArray(R.array.errors)[0])
						.setNegativeButton(android.R.string.ok, new DialogInterface.OnClickListener(){
							public void onClick(DialogInterface a, int b) {
								a.cancel();
								runOnUiThread(new Runnable(){
										public void run() {
											ControllerActivity.instance.get().finish();
											finish();
										}
									});
							}
						})
						.create()
						.show();
					return null;
				}
				ok = false;
				/*Step 2*/
				try {
					Context c=createPackageContext("com.mojang.minecraftpe", CONTEXT_IGNORE_SECURITY);
					String s=c.getPackageCodePath();
					new FileInputStream(s).close();
					Log.d("dbg_sca", s);
					s = c.getPackageResourcePath();
					new FileInputStream(s).close();
					Log.d("dbg_sca", s);
					ok = true;
				} catch (PackageManager.NameNotFoundException e) {
					e.printStackTrace();
				} catch (IOException err) {
					err.printStackTrace(System.out);
				}
				if (!ok) {
					new AlertDialog.Builder(SplashActivity.this)
						.setTitle(R.string.err_title)
						.setCancelable(false)
						.setMessage(getResources().getStringArray(R.array.errors)[1])
						.setNegativeButton(android.R.string.ok, new DialogInterface.OnClickListener(){
							public void onClick(DialogInterface a, int b) {
								a.cancel();
								runOnUiThread(new Runnable(){
										public void run() {
											ControllerActivity.instance.get().finish();
											finish();
										}
									});
							}
						})
						.create()
						.show();
					return null;
				}
				try {
					List<String> args=new ArrayList<>();
					args.add("/system/bin/dalvikvm");
					args.add("-classpath");
					args.add(getApplicationInfo().sourceDir);
					args.add(CacheDeleter.class.getName());
					args.add("-data");
					args.add(getFilesDir().toString());
					args.add("-cache");
					args.add(getCacheDir().toString());
					new ProcessBuilder()
						.command(args)
						.directory(getCacheDir())
						.redirectErrorStream(true)
						.start();
				} catch (IOException e) {
					e.printStackTrace();
				}

				RunOnceApplication.instance.getUuids().load(SplashActivity.this);
				RunOnceApplication.instance.getUuids().regenUuid();
				RunOnceApplication.instance.getUuids().save(SplashActivity.this);
				
				RunOnceApplication.instance.completeCheckApp();
				return null;
			}
			public void onPostExecute(Void r) {
				startActivity(new Intent(SplashActivity.this,ControllerActivity.class));
				finish();
			}
		}.execute();
	}

	@Override
	public void onBackPressed() {
		
	}
	
	public void hideActionbar(){
		/*It can be called by 1.x and 2.x, but it won't do anything.*/
		try {
			Object actbar=getClass().getMethod("getActionBar").invoke(this);
			actbar.getClass().getMethod("hide").invoke(actbar);
		} catch (Throwable e) {
			
		}
	}
}
