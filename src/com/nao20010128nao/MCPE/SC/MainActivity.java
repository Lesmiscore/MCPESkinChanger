package com.nao20010128nao.MCPE.SC;

import android.app.*;
import android.os.*;
import android.view.*;
import android.widget.*;
import android.preference.*;
import com.nao20010128nao.ToolBox.*;
import com.nao20010128nao.ToolBox.HandledPreference.*;
import android.util.Log;
import java.util.*;
import android.content.*;
import android.content.pm.*;
import android.net.*;
import java.lang.ref.*;
import java.net.*;
import com.nao20010128nao.MC_PE.SkinChanger.*;

public class MainActivity extends PreferenceActivity{
	public static WeakReference<MainActivity> instance=new WeakReference<>(null);
	Map<String,URI> skins=ModificateActivity.skins;
	String changeTmp=null;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
	{
        super.onCreate(savedInstanceState);
		instance=new WeakReference<MainActivity>(this);
		if(ModificationService.instance.get()!=null){
			startActivity(new Intent(this,ModificateActivity.class).putExtra("mode","noservice"));
			finish();
		}else
			startActivity(new Intent(this,SupportCheckerActivity.class));
       	addPreferencesFromResource(R.xml.pref_main);
		sH("startChange",new OnClickListener(){
			public void onClick(String p1,String p2,String p3){
				if(skins.size()==0){
					new AlertDialog.Builder(MainActivity.this)
						.setTitle(R.string.err_title)
						.setCancelable(true)
						.setMessage(R.string.err_no_skins)
						.setNegativeButton(android.R.string.ok,new DialogInterface.OnClickListener(){
							public void onClick(DialogInterface a,int b){
								a.cancel();
							}
						})
						.create()
						.show();
					return;
				}
				startActivity(new Intent(MainActivity.this,ModificateActivity.class).putExtra("mode","normal"));
			}
		});
		sH("wantUpdate",new OnClickListener(){
				public void onClick(String p1,String p2,String p3){
					for(ResolveInfo i:getPackageManager().queryIntentActivities(new Intent().setAction("android.intent.action.VIEW").setData(Uri.parse("http://play.google.com/store/apps/details?id=com.mojang.minecraftpe")),0)){
						Log.d("dbg",i.activityInfo.packageName+";"+i.activityInfo.name);
						if(i.activityInfo.packageName.equals("com.android.vending")){
							startActivity(new Intent().setClassName(i.activityInfo.packageName,i.activityInfo.name).setAction("android.intent.action.VIEW").setData(Uri.parse("http://play.google.com/store/apps/details?id=com.mojang.minecraftpe")));
						}
					}
				}
			});
		sH("selectSteve",new OnClickListener(){
				public void onClick(String p1,String p2,String p3){
					Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
					intent.setType("image/png");
					changeTmp="assets/images/mob/char.png";
					startActivityForResult(intent, 123);
				}
			});
		sH("selectZombie",new OnClickListener(){
				public void onClick(String p1,String p2,String p3){
					Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
					intent.setType("image/png");
					changeTmp="assets/images/mob/zombie.png";
					startActivityForResult(intent, 123);
				}
			});
		sH("selectZombiePigman",new OnClickListener(){
				public void onClick(String p1,String p2,String p3){
					Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
					intent.setType("image/png");
					changeTmp="assets/images/mob/pigzombie.png";
					startActivityForResult(intent, 123);
				}
			});
    }
	void sH(Preference pref,HandledPreference.OnClickListener handler){
		if(!(pref instanceof HandledPreference))return;
		((HandledPreference)pref).setOnClickListener(handler);
	}
	void sH(String pref,HandledPreference.OnClickListener handler){
		sH(findPreference(pref),handler);
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data){
		// TODO: Implement this method
		super.onActivityResult(requestCode, resultCode, data);
		switch(requestCode){
			case 123:
				if(resultCode==RESULT_OK){
					Log.d("dbg","INTENT:"+data.getDataString());
					try{
						skins.put(changeTmp, new URI(data.getDataString()));
					}catch (URISyntaxException e){
						
					}
				}
				changeTmp=null;
				break;
		}
	}
}
