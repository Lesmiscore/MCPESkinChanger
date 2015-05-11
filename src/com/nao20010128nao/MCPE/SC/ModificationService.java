package com.nao20010128nao.MCPE.SC;
import com.nao20010128nao.ToolBox.*;
import android.os.*;
import android.app.*;
import android.content.*;
import java.io.*;
import java.util.*;
import android.widget.*;
import android.util.*;
import java.lang.ref.*;
import java.util.zip.*;
import java.net.*;
import android.net.*;
import kellinwood.security.zipsigner.*;
import kellinwood.security.zipsigner.optional.*;
import com.nao20010128nao.MC_PE.SkinChanger.*;

public class ModificationService extends ServiceX{
	Map<String,URI> skins=ModificateActivity.skins;
	public static WeakReference<ModificationService> instance=new WeakReference<>(null);
	@Override
	public IBinder onBind(){
		// TODO: Implement this method
		return null;
	}

	@Override
	public int onStartCommand(int flags, int startId){
		// TODO: Implement this method
		instance=new WeakReference<ModificationService>(this);
		final Notification n=new Notification();
		n.setLatestEventInfo(this,getResources().getString(R.string.app_name),"",PendingIntent.getActivity(this,-1,new Intent().setClass(this,ModificateActivity.class).putExtra("mode","noservice"),Intent.FLAG_ACTIVITY_CLEAR_TOP));
		n.icon=R.drawable.paw;
		startForeground(100,n);
		final NotificationManager mNM=(NotificationManager)getSystemService(NOTIFICATION_SERVICE);
		mNM.notify(100,n);
		//Toast.makeText(this,"",Toast.LENGTH_LONG).show();
		new AsyncTask<Void,Integer,Void>(){
			AsyncTask<Void,Void,Void> inderminate=new AsyncTask<Void,Void,Void>(){
				public Void doInBackground(Void... A){
					while(!this.isCancelled()){
						ModificateActivity.set(-1,-1,1,0,null);
						Thread.sleep(500);
						ModificateActivity.set(-1,-1,1,1,null);
						Thread.sleep(500);
					}
					return null;
				}
			};
			public Void doInBackground(Void[] p){
				ModificateActivity.set(-1,-1,-1,-1,null);
				ModificateActivity.set(getResources().getStringArray(R.array.modSteps).length-1,0,-1,-1,null);
				/*Step1*/
				InputStream is=null;
				OutputStream os=null;
				publishProgress(0);
				try{
					int tmp=0;
					ModificateActivity.set(-1,-1,(int)new File(createPackageContext("com.mojang.minecraftpe",CONTEXT_IGNORE_SECURITY).getPackageCodePath()).length(),0,null);
					is=new FileInputStream(createPackageContext("com.mojang.minecraftpe",CONTEXT_IGNORE_SECURITY).getPackageCodePath());
					os=openFileOutput("vanilla.apk",MODE_MULTI_PROCESS|MODE_WORLD_READABLE);
					byte[] buf=new byte[10000];
					while(true){
						int i=is.read(buf);
						if(i<=0)break;
						tmp+=i;
						ModificateActivity.set(-1,-1,-1,tmp,null);
						os.write(buf,0,i);
					}
				}catch(Throwable ex){
					ex.printStackTrace(System.out);
				}finally{
					try{
						if (is != null)is.close();
						if (os != null)os.close();
					}catch (IOException e){
						
					}
				}
				/*Step2*/
				publishProgress(1);
				ZipOutputStream zos=null;
				ZipInputStream zis=null;
				ModificateActivity.set(-1,1,0,0,null);
				try{
					ModificateActivity.set(-1,-1,countZipEntries("vanilla.apk"),0,null);
					is=openFileInput("vanilla.apk");
					os=openFileOutput("modded.apk",MODE_MULTI_PROCESS|MODE_WORLD_READABLE);
					zos=new ZipOutputStream(new BufferedOutputStream(os));
					zis=new ZipInputStream(new BufferedInputStream(is));
					ZipEntry ze;
					int tmp=0;
					byte[] buf=new byte[10000];
					while((ze=zis.getNextEntry())!=null){
						if(ze.getName().startsWith("META-INF")){
							ModificateActivity.set(-1,-1,-1,++tmp,null);
							continue;//don't copy sign data
						}
						InputStream source=zis;
						if(skins.containsKey(ze.getName())){
							source=tryOpen(skins.get(ze.getName()).toString());
						}
						zos.putNextEntry(ze);
						while(true){
							int i=source.read(buf);
							if(i<=0)break;
							zos.write(buf,0,i);
						}
						if(skins.containsKey(ze.getName())){
							source.close();
						}
						ModificateActivity.set(-1,-1,-1,++tmp,null);
					}
				}catch(Throwable ex){
					ex.printStackTrace(System.out);
				}finally{
					try{
						if (zis != null)zis.close();
						if (zos != null)zos.close();
					}catch (IOException e){

					}
				}
				/*Step3*/
				publishProgress(2);
				try{
					ModificateActivity.set(-1, -1, 100, -1, null);
					ZipSigner zs=new ZipSigner();
					zs.setKeymode("auto-testkey");
					zs.addProgressListener(new ProgressListener(){
						public void onProgress(ProgressEvent pe){
							ModificateActivity.set(-1, -1, -1, pe.getPercentDone(), null);
						}
					});
					zs.signZip(new File(getFilesDir(), "modded.apk") + "",
							   new File(getFilesDir(), "signed.apk") + "");
				}catch (Throwable e){
					e.printStackTrace(System.out);
				}
				inderminate.cancel(true);
				/*Step4*/
				publishProgress(3);
				try{
					int tmp=0;
					ModificateActivity.set(-1,-1,(int)new File(getFilesDir(),"signed.apk").length(),0,null);
					is=openFileInput("signed.apk");
					File f=new File(Environment.getExternalStorageDirectory(),"games/com.mojang");
					(f=new File(f,"skinchanger")).mkdirs();
					os=new FileOutputStream(new File(f,"signed.apk"));
					byte[] buf=new byte[10000];
					while(true){
						int i=is.read(buf);
						if(i<=0)break;
						tmp+=i;
						ModificateActivity.set(-1,-1,-1,tmp,null);
						os.write(buf,0,i);
					}
				}catch(Throwable ex){
					ex.printStackTrace(System.out);
				}finally{
					try{
						if (is != null)is.close();
						if (os != null)os.close();
					}catch (IOException e){

					}
				}
				/*Step5*/
				publishProgress(4);
				//ModificateActivity.set(-1,5,-1,-1,null);
				if(ModificateActivity.instance.get()==null)
					startActivity(new Intent(ModificationService.this,ModificateActivity.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK).putExtra("mode","last"));
				else
					ModificateActivity.instance.get().doLast();
				stopForeground(true);
				return null;
			}
			public InputStream tryOpen(String uri) throws IOException{
				if(uri.startsWith("content://")){
					return getContentResolver().openInputStream(Uri.parse(uri));
				}else if(uri.startsWith("/")){
					return new FileInputStream(uri);
				}else{
					return URI.create(uri).toURL().openConnection().getInputStream();
				}
			}
			public void onProgressUpdate(Integer[] a){
				n.setLatestEventInfo(ModificationService.this,getResources().getString(R.string.app_name),getResources().getStringArray(R.array.modSteps)[a[0]],PendingIntent.getActivity(ModificationService.this,-1,new Intent().setClass(ModificationService.this,ModificateActivity.class).putExtra("mode","noservice"),Intent.FLAG_ACTIVITY_CLEAR_TOP));
				mNM.notify(100,n);
				ModificateActivity.set(-1,a[0],-1,-1,getResources().getStringArray(R.array.modSteps)[a[0]]);
			}
			public int countZipEntries(String path) throws IOException{
				int count=0;
				InputStream is=openFileInput("vanilla.apk");
				ZipInputStream zis=new ZipInputStream(new BufferedInputStream(is));
				while(zis.getNextEntry()!=null)
					count++;
				return count;
			}
		}.execute();
		return START_NOT_STICKY;
	}
}
