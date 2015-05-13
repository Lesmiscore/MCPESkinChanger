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
		sH("selectCow",new OnClickListener(){
				public void onClick(String p1,String p2,String p3){
					Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
					intent.setType("image/png");
					changeTmp="assets/images/mob/cow.png";
					startActivityForResult(intent, 123);
				}
			});
		sH("selectCreeper",new OnClickListener(){
				public void onClick(String p1,String p2,String p3){
					Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
					intent.setType("image/png");
					changeTmp="assets/images/mob/creeper.png";
					startActivityForResult(intent, 123);
				}
			});
		sH("selectPig",new OnClickListener(){
				public void onClick(String p1,String p2,String p3){
					Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
					intent.setType("image/png");
					changeTmp="assets/images/mob/pig.png";
					startActivityForResult(intent, 123);
				}
			});
		sH("selectSheep0",new OnClickListener(){
				public void onClick(String p1,String p2,String p3){
					Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
					intent.setType("image/png");
					changeTmp="assets/images/mob/sheep_0.png";
					startActivityForResult(intent, 123);
				}
			});
		sH("selectSheep1",new OnClickListener(){
				public void onClick(String p1,String p2,String p3){
					Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
					intent.setType("image/png");
					changeTmp="assets/images/mob/sheep_1.png";
					startActivityForResult(intent, 123);
				}
			});
		sH("selectSheep2",new OnClickListener(){
				public void onClick(String p1,String p2,String p3){
					Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
					intent.setType("image/png");
					changeTmp="assets/images/mob/sheep_2.png";
					startActivityForResult(intent, 123);
				}
			});
		sH("selectSheep3",new OnClickListener(){
				public void onClick(String p1,String p2,String p3){
					Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
					intent.setType("image/png");
					changeTmp="assets/images/mob/sheep_3.png";
					startActivityForResult(intent, 123);
				}
			});
		sH("selectSheep4",new OnClickListener(){
				public void onClick(String p1,String p2,String p3){
					Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
					intent.setType("image/png");
					changeTmp="assets/images/mob/sheep_4.png";
					startActivityForResult(intent, 123);
				}
			});
		sH("selectSheep5",new OnClickListener(){
				public void onClick(String p1,String p2,String p3){
					Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
					intent.setType("image/png");
					changeTmp="assets/images/mob/sheep_5.png";
					startActivityForResult(intent, 123);
				}
			});
		sH("selectSheep6",new OnClickListener(){
				public void onClick(String p1,String p2,String p3){
					Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
					intent.setType("image/png");
					changeTmp="assets/images/mob/sheep_6.png";
					startActivityForResult(intent, 123);
				}
			});
		sH("selectSheep7",new OnClickListener(){
				public void onClick(String p1,String p2,String p3){
					Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
					intent.setType("image/png");
					changeTmp="assets/images/mob/sheep_7.png";
					startActivityForResult(intent, 123);
				}
			});
		sH("selectSheep8",new OnClickListener(){
				public void onClick(String p1,String p2,String p3){
					Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
					intent.setType("image/png");
					changeTmp="assets/images/mob/sheep_8.png";
					startActivityForResult(intent, 123);
				}
			});
		sH("selectSheep9",new OnClickListener(){
				public void onClick(String p1,String p2,String p3){
					Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
					intent.setType("image/png");
					changeTmp="assets/images/mob/sheep_9.png";
					startActivityForResult(intent, 123);
				}
			});
		sH("selectSheep10",new OnClickListener(){
				public void onClick(String p1,String p2,String p3){
					Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
					intent.setType("image/png");
					changeTmp="assets/images/mob/sheep_10.png";
					startActivityForResult(intent, 123);
				}
			});
		sH("selectSheep11",new OnClickListener(){
				public void onClick(String p1,String p2,String p3){
					Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
					intent.setType("image/png");
					changeTmp="assets/images/mob/sheep_11.png";
					startActivityForResult(intent, 123);
				}
			});
		sH("selectSheep12",new OnClickListener(){
				public void onClick(String p1,String p2,String p3){
					Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
					intent.setType("image/png");
					changeTmp="assets/images/mob/sheep_12.png";
					startActivityForResult(intent, 123);
				}
			});
		sH("selectSheep13",new OnClickListener(){
				public void onClick(String p1,String p2,String p3){
					Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
					intent.setType("image/png");
					changeTmp="assets/images/mob/sheep_13.png";
					startActivityForResult(intent, 123);
				}
			});
		sH("selectSheep14",new OnClickListener(){
				public void onClick(String p1,String p2,String p3){
					Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
					intent.setType("image/png");
					changeTmp="assets/images/mob/sheep_14.png";
					startActivityForResult(intent, 123);
				}
			});
		sH("selectSheep15",new OnClickListener(){
				public void onClick(String p1,String p2,String p3){
					Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
					intent.setType("image/png");
					changeTmp="assets/images/mob/sheep_15.png";
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
