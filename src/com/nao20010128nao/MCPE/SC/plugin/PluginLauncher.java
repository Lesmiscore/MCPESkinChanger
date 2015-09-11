package com.nao20010128nao.MCPE.SC.plugin;
import com.nao20010128nao.MCPE.SC.misc.*;
import android.os.*;
import android.widget.*;
import android.content.pm.*;
import java.util.*;
import android.app.*;
import android.content.*;
import android.view.*;

public class PluginLauncher extends SmartFindViewListActivity
{
	final Intent lookupIntent=null;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO: Implement this method
		super.onCreate(savedInstanceState);
		setListAdapter(new PluginList());
	}
	private class PluginList extends ArrayAdapter<ResolveInfo>{
		public PluginList(){
			super(PluginLauncher.this,0,listPlugins());
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO: Implement this method
			if(convertView==null)
				convertView=getLayoutInflater().inflate(look("layout","pluginlistcomp"),null);
			ResolveInfo ri=getItem(position);
			ActivityInfo ai=ri.activityInfo;
			convertView.setTag(ri);
			PluginLauncher.this.<ImageView>find("appImage",convertView).setImageDrawable(ai.loadLogo(getPackageManager()));
			PluginLauncher.this.<TextView>find("appName",convertView).setText(ai.loadLabel(getPackageManager()));
			PluginLauncher.this.<TextView>find("appDetails",convertView).setText(ai.packageName+"/"+ai.name);
			return convertView;
		}
	}
	List<ResolveInfo> listPlugins(){
		return getPackageManager().queryIntentActivities(lookupIntent,0);
	}
}
