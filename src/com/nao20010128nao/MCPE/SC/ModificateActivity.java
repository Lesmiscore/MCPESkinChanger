package com.nao20010128nao.MCPE.SC;
import android.app.*;
import android.content.*;
import android.net.*;
import android.os.*;
import android.widget.*;
import com.nao20010128nao.MC_PE.SkinChanger.*;
import com.nao20010128nao.SpoofBrowser.classes.*;
import java.io.*;
import java.lang.ref.*;
import java.net.*;
import java.util.*;

public class ModificateActivity extends Activity {
	public static WeakReference<ModificateActivity> instance=new WeakReference<>(null);
	public static Map<String,URI> skins=new FilteredMap();
	volatile transient static int totalMax,totalVal,compMax,compVal;
	volatile transient static String log="";
	volatile transient static boolean ready=false;
	ProgressBar total,component;TextView state;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO: Implement this method
		super.onCreate(savedInstanceState);
		instance = new WeakReference<ModificateActivity>(this);
		if (ControllerActivity.instance.get() != null)
			ControllerActivity.instance.get().finish();
		setContentView(R.layout.modder);
		total = (ProgressBar)findViewById(R.id.pbTotal);
		component = (ProgressBar)findViewById(R.id.pbComponent);
		state = (TextView)findViewById(R.id.tvState);
		ready = true;
		update();
		if (getIntent().getStringExtra("mode").equals("normal"))doStart();
		else if (getIntent().getStringExtra("mode").equals("last"))doLast();
		new AsyncTask<Void,Void,Void>(){
			public Void doInBackground(Void... p) {
				while (instance.get() != null) {
					try {
						Thread.sleep(1000L * 1);
					} catch (InterruptedException e) {

					}
					update();
				}
				return null;
			}
		};//.execute();
	}
	public void doLast() {
		switch (Tools.getSettings("output.mode", 0, this)) {
			case 0://installed
				Intent intent = new Intent(Intent.ACTION_DELETE, Uri.fromParts("package", "com.mojang.minecraftpe", null));
				//intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				startActivityForResult(intent, 100);
				break;
			case 1://select
				finish();
				System.exit(0);
		}
	}
	public void doStart() {
		startService(new Intent(this, ModificationService.class));
	}
	public void update() {
		runOnUiThread(new Runnable(){
				public void run() {
					/*prevent errors*/
					total.setProgress(0);
					component.setProgress(0);
					total.setMax(0);
					component.setMax(0);
					/*mainly program*/
					total.setMax(totalMax);
					component.setMax(compMax);
					total.setProgress(totalVal);
					component.setProgress(compVal);
					state.setText(log);
					findViewById(android.R.id.content).invalidate();
				}
			});
	}
	public static void set(int tM, int tV, int cM, int cV, String state) {
		totalMax = tM == -1 ?totalMax: tM;
		totalVal = tV == -1 ?totalVal: tV;
		compMax = cM == -1 ?compMax: cM;
		compVal = cV == -1 ?compVal: cV;
		log = state == null ?log: state;
		if (instance.get() != null)instance.get().update();
	}
	@Override
	protected void onDestroy() {
		// TODO: Implement this method
		super.onDestroy();
		ready = false;
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO: Implement this method
		super.onActivityResult(requestCode, resultCode, data);
		switch (requestCode) {
			case 100:
				Intent intent = new Intent(Intent.ACTION_VIEW);
				File f=new File(Environment.getExternalStorageDirectory(), "games/com.mojang");
				(f = new File(f, "skinchanger")).mkdirs();
				intent.setDataAndType(Uri.fromFile(new File(f, "signed.apk")), "application/vnd.android.package-archive");
				//intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				startActivityForResult(intent, 200);
				break;
			case 200:
				finish();
				break;
		}
	}
}
