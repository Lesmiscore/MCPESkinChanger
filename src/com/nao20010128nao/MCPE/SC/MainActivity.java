package com.nao20010128nao.MCPE.SC;

import android.app.*;
import android.content.*;
import android.content.pm.*;
import android.net.*;
import android.os.*;
import android.preference.*;
import android.util.*;
import android.widget.*;
import com.nao20010128nao.MC_PE.SkinChanger.*;
import com.nao20010128nao.SpoofBrowser.classes.*;
import com.nao20010128nao.ToolBox.*;
import com.nao20010128nao.ToolBox.HandledPreference.*;
import java.io.*;
import java.lang.ref.*;
import java.net.*;
import java.util.*;

public class MainActivity extends PreferenceActivity {
	public static WeakReference<MainActivity> instance=new WeakReference<>(null);
	static boolean preventStart=false;;
	Map<String,URI> skins=ModificateActivity.skins;
	String changeTmp=null;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
		instance = new WeakReference<MainActivity>(this);
		if (ModificationService.instance.get() != null) {
			startActivity(new Intent(this, ModificateActivity.class).putExtra("mode", "noservice"));
			MainActivity.preventStart();
		} else
			startActivity(new Intent(this, SupportCheckerActivity.class));
		if(preventStart){
			finish();
			return;
		}
       	addPreferencesFromResource(R.xml.pref_main);
		sH("startChange", new OnClickListener(){
				public void onClick(String p1, String p2, String p3) {
					if (skins.size() == 0) {
						new AlertDialog.Builder(MainActivity.this)
							.setTitle(R.string.err_title)
							.setCancelable(true)
							.setMessage(R.string.err_no_skins)
							.setNegativeButton(android.R.string.ok, new DialogInterface.OnClickListener(){
								public void onClick(DialogInterface a, int b) {
									a.cancel();
								}
							})
							.create()
							.show();
						return;
					}
					startActivity(new Intent(MainActivity.this, ModificateActivity.class).putExtra("mode", "normal"));
				}
			});
		sH("wantUpdate", new OnClickListener(){
				public void onClick(String p1, String p2, String p3) {
					for (ResolveInfo i:getPackageManager().queryIntentActivities(new Intent().setAction("android.intent.action.VIEW").setData(Uri.parse("http://play.google.com/store/apps/details?id=com.mojang.minecraftpe")), 0)) {
						Log.d("dbg", i.activityInfo.packageName + ";" + i.activityInfo.name);
						if (i.activityInfo.packageName.equals("com.android.vending")) {
							startActivity(new Intent().setClassName(i.activityInfo.packageName, i.activityInfo.name).setAction("android.intent.action.VIEW").setData(Uri.parse("http://play.google.com/store/apps/details?id=com.mojang.minecraftpe")));
						}
					}
				}
			});
		sH("selectSteve", createListenerForPerf("assets/images/mob/char.png"));
		sH("selectSteveN", createListenerForPerf("assets/images/mob/steve.png"));
		sH("selectAlexN", createListenerForPerf("assets/images/mob/alex.png"));
		sH("selectZombie", createListenerForPerf("assets/images/mob/zombie.png"));
		sH("selectZombiePigman", createListenerForPerf("assets/images/mob/pigzombie.png"));
		sH("selectCow", createListenerForPerf("assets/images/mob/cow.png"));
		sH("selectCreeper", createListenerForPerf("assets/images/mob/creeper.png"));
		sH("selectPig", createListenerForPerf("assets/images/mob/pig.png"));
		sH("selectSheep0", createListenerForPerf("assets/images/mob/sheep_0.png"));
		sH("selectSheep1", createListenerForPerf("assets/images/mob/sheep_1.png"));
		sH("selectSheep2", createListenerForPerf("assets/images/mob/sheep_2.png"));
		sH("selectSheep3", createListenerForPerf("assets/images/mob/sheep_3.png"));
		sH("selectSheep4", createListenerForPerf("assets/images/mob/sheep_4.png"));
		sH("selectSheep5", createListenerForPerf("assets/images/mob/sheep_5.png"));
		sH("selectSheep6", createListenerForPerf("assets/images/mob/sheep_6.png"));
		sH("selectSheep7", createListenerForPerf("assets/images/mob/sheep_7.png"));
		sH("selectSheep8", createListenerForPerf("assets/images/mob/sheep_8.png"));
		sH("selectSheep9", createListenerForPerf("assets/images/mob/sheep_9.png"));
		sH("selectSheep10", createListenerForPerf("assets/images/mob/sheep_10.png"));
		sH("selectSheep11", createListenerForPerf("assets/images/mob/sheep_11.png"));
		sH("selectSheep12", createListenerForPerf("assets/images/mob/sheep_12.png"));
		sH("selectSheep13", createListenerForPerf("assets/images/mob/sheep_13.png"));
		sH("selectSheep14", createListenerForPerf("assets/images/mob/sheep_14.png"));
		sH("selectSheep15", createListenerForPerf("assets/images/mob/sheep_15.png"));
		sH("deleteCache", new OnClickListener(){
				public void onClick(String p1, String p2, String p3) {
					new File(Environment.getExternalStorageDirectory(), "games/com.mojang/minecraft/skinchanger").delete();
					new File(getFilesDir(), "vanilla.apk").delete();
					new File(getFilesDir(), "modded.apk").delete();
					new File(getFilesDir(), "signed.apk").delete();
					Toast.makeText(MainActivity.this, R.string.deletedCache, Toast.LENGTH_LONG).show();
				}
			});
		sH("inputFrom", new OnClickListener(){
				public void onClick(String p1, String p2, String p3) {
					final int revert=Tools.getSettings("input.mode", 0, MainActivity.this);
					new AlertDialog.Builder(MainActivity.this).
						setTitle(R.string.inputFrom).
						setSingleChoiceItems(R.array.inputList, revert, new DialogInterface.OnClickListener(){
							public void onClick(DialogInterface di, int where) {
								Tools.setSettings("input.mode", where, MainActivity.this);
							}
						}).
						setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener(){
							public void onClick(DialogInterface di, int where) {
								Tools.setSettings("input.mode", revert, MainActivity.this);
							}
						}).
						setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener(){
							public void onClick(DialogInterface di, int where) {
								switch (Tools.getSettings("input.mode", 0, MainActivity.this)) {
									case 0://installed
										startActivity(new Intent(MainActivity.this, SupportCheckerActivity.class));
										break;
									case 1://select
										startActivityForResult(new Intent(Intent.ACTION_GET_CONTENT).setType("application/vnd.android.package-archive"), 456);
										break;
								}
							}
						}).
						show();
				}
			});
		sH("outputTo", new OnClickListener(){
				public void onClick(String p1, String p2, String p3) {
					final int revert=Tools.getSettings("output.mode", 0, MainActivity.this);
					new AlertDialog.Builder(MainActivity.this).
						setTitle(R.string.outputTo).
						setSingleChoiceItems(R.array.outputList, revert, new DialogInterface.OnClickListener(){
							public void onClick(DialogInterface di, int where) {
								Tools.setSettings("output.mode", where, MainActivity.this);
							}
						}).
						setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener(){
							public void onClick(DialogInterface di, int where) {
								Tools.setSettings("output.mode", revert, MainActivity.this);
							}
						}).
						setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener(){
							public void onClick(DialogInterface di, int where) {
								switch (Tools.getSettings("output.mode", 0, MainActivity.this)) {
									case 0://install
										break;
									case 1://select
										startActivityForResult(new Intent(Intent.ACTION_CREATE_DOCUMENT).setType("application/vnd.android.package-archive"), 789);
										break;
								}
							}
						}).
						show();
				}
			});
		sH("chgList", new OnClickListener(){
				public void onClick(String p1, String p2, String p3) {
					startActivity(new Intent(MainActivity.this, ChangingListEditor.class));
				}
			});
    }
	void sH(Preference pref, HandledPreference.OnClickListener handler) {
		if (!(pref instanceof HandledPreference))return;
		((HandledPreference)pref).setOnClickListener(handler);
	}
	void sH(String pref, HandledPreference.OnClickListener handler) {
		sH(findPreference(pref), handler);
	}
	void selectFileForSkin(String name){
		Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
		intent.setType("image/png");
		changeTmp = name;
		startActivityForResult(intent, 123);
	}
	OnClickListener createListenerForPerf(final String name){
		return new OnClickListener(){
			public void onClick(String p1, String p2, String p3) {
				selectFileForSkin(name);
			}
		};
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO: Implement this method
		super.onActivityResult(requestCode, resultCode, data);
		switch (requestCode) {
			case 123:
				if (resultCode == RESULT_OK) {
					Log.d("dbg", "INTENT:" + data.getDataString());
					try {
						skins.put(changeTmp, new URI(data.getDataString()));
					} catch (URISyntaxException e) {

					}
					startActivityForResult(new Intent(this, ContentFileLocalCopyActivity.class).
										   setData(Uri.parse(data.getDataString())).
										   putExtra("path", changeTmp), 1231);
				}
				changeTmp = null;
				break;
			case 1231:
				try {
					skins.put(data.getStringExtra("path"), new File(data.getStringExtra("result")).toURI());
				} catch (Throwable e) {

				}
				break;
			case 456:
				if (resultCode == RESULT_OK) {
					Tools.setSettings("input.where", data.getDataString(), this);
					startActivityForResult(data.setClass(this, ContentFileLocalCopyActivity.class).putExtra("dest", new File(getFilesDir(), "mcpeCopy_unchecked.apk") + ""), 4561);
				} else if (Tools.getSettings("input.where", (String)null, this) == null)
					Tools.setSettings("input.mode", 0, this);
				break;
			case 4561:
				final File unchecked=new File(data.getStringExtra("result"));
				File checked=new File(getFilesDir(), "mcpeCopy.apk");
				if(unchecked.exists()){
					AndroidPackage pak=new AndroidPackage(unchecked);
					PackageInfo info=pak.getResult();
					if("com.mojang.minecraftpe".equals(info.packageName)){
						unchecked.renameTo(checked);
					}else{
						ApplicationInfo appInfo=info.applicationInfo;
						AndroidPackage.AppSnippet as=AndroidPackage.getAppSnippet(this,appInfo,unchecked);
						CharSequence s=as.label;
						String mes=getResources().getString(R.string.fakeapp).replace("@APP@",s);
						new AlertDialog.Builder(this).
							setMessage(mes).
							setPositiveButton(android.R.string.ok,new DialogInterface.OnClickListener(){
								public void onClick(DialogInterface a,int w){
									a.cancel();
									unchecked.delete();
								}
							}).
							show();
					}
				}else{
					return;
				}
				Tools.setSettings("input.where", data.getStringExtra("result"), this);
				break;
			case 789:
				if (resultCode == RESULT_OK)
					Tools.setSettings("output.where", data.getDataString(), this);
				else if (Tools.getSettings("output.where", (String)null, this) == null)
					Tools.setSettings("output.mode", 0, this);
				break;
		}
	}
	public static void preventStart(){
		preventStart=true;
	}
}
