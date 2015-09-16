package com.nao20010128nao.MCPE.SC.plugin;
import com.nao20010128nao.MCPE.SC.misc.*;
import android.os.*;
import android.widget.*;
import android.content.pm.*;
import java.util.*;
import android.app.*;
import android.content.*;
import android.view.*;
import java.io.*;
import com.nao20010128nao.SpoofBrowser.classes.*;
import java.net.*;
import com.nao20010128nao.MCPE.SC.*;

public class PluginLauncher extends SmartFindViewListActivity
{
	final Intent lookupIntent=new Intent();
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO: Implement this method
		super.onCreate(savedInstanceState);
		lookupIntent.
			setAction(getPackageName()+".plugins.LAUNCH");
		setListAdapter(new PluginList());
		getListView().setOnItemClickListener(new AdapterView.OnItemClickListener(){
			public void onItemClick(AdapterView av,View v,int i,long l){
				ResolveInfo ri=(ResolveInfo)getListView().getItemAtPosition(i);
				ActivityInfo ai=ri.activityInfo;
				Intent toStart=(Intent)lookupIntent.clone();
				toStart.setClassName(ai.packageName,ai.name);
				startActivityForResult(toStart,1);
			}
		});
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

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO: Implement this method
		switch(requestCode){
			case 1:
				if(resultCode==RESULT_OK){
					
				}
				break;
		}
	}
	DiffMap<String,byte[]> deserializeDiff(Map<String,byte[]> base,byte[] data){
		try {
			DataInputStream dis=new DataInputStream(new ByteArrayInputStream(data));
			Set<String> removes=new HashSet<>();
			Map<String,byte[]> additions=new HashMap<>();
			int remSize=dis.readInt();
			for (int i=0;i < remSize;i++) {
				removes.add(dis.readUTF());
			}
			int addSize=dis.readInt();
			for (int i=0;i < addSize;i++) {
				String k=dis.readUTF();
				byte[] v=new byte[dis.readShort()];
				dis.readFully(v);
				additions.put(k, v);
			}
			DiffMap<String,byte[]> result=new DiffMap<>(base);
			for (String s:removes)
				result.remove(s);
			for (Map.Entry<String,byte[]> entry:additions.entrySet())
				result.put(entry.getKey(), entry.getValue());
			return result;
		} catch (IOException e) {
			
		}
		return new DiffMap<>(Collections.emptyMap());
	}
	public byte[] serializeMap(Map<String,URI> map){
		try {
			ByteArrayOutputStream baos=new ByteArrayOutputStream(map.size() * 100);
			DataOutputStream dos=new DataOutputStream(baos);
			ByteArrayOutputStream tmp=new ByteArrayOutputStream(1000);
			dos.writeInt(map.size());
			for (Map.Entry<String,URI> entry:map.entrySet()) {
				dos.writeUTF(entry.getKey());
				InputStream is=null;
				try {
					byte[] data=new byte[1000];
					is = entry.getValue().toURL().openConnection().getInputStream();
					while (true) {
						int r=is.read(data);
						if (r <= 0) {
							break;
						}
						tmp.write(data, 0, r);
					}
				} catch (IOException e) {

				} finally {
					try {
						is.close();
					} catch (Throwable e) {

					}
				}
				dos.writeShort(tmp.size());
				dos.write(tmp.toByteArray());
				tmp.reset();
				dos.flush();
			}
			return baos.toByteArray();
		} catch (IOException e) {
			
		}
		return new byte[]{0,0,0,0};
	}
	Byte[] primToWrap(byte[] prim){
		Byte[] wrap=new Byte[prim.length];
		for(int i=0;i<prim.length;i++){
			wrap[i]=prim[i];
		}
		return wrap;
	}
}
