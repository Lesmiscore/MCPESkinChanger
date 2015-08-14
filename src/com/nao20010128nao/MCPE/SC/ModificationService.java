package com.nao20010128nao.MCPE.SC;
import android.app.*;
import android.content.*;
import android.content.pm.*;
import android.net.*;
import android.os.*;
import android.util.*;
import com.nao20010128nao.MC_PE.SkinChanger.*;
import com.nao20010128nao.SpoofBrowser.classes.*;
import com.nao20010128nao.ToolBox.*;
import java.io.*;
import java.lang.ref.*;
import java.net.*;
import java.util.*;
import java.util.zip.*;
import kellinwood.security.zipsigner.*;
import android.widget.*;

public class ModificationService extends ServiceX {
	Map<String,URI> skins=ModificateActivity.skins;
	public static WeakReference<ModificationService> instance=new WeakReference<>(null);
	@Override
	public IBinder onBind() {
		// TODO: Implement this method
		return null;
	}

	@Override
	public int onStartCommand(int flags, int startId) {
		// TODO: Implement this method
		instance = new WeakReference<ModificationService>(this);
		final Notification n=new Notification();
		n.setLatestEventInfo(this, getResources().getString(R.string.app_name), "", PendingIntent.getActivity(this, -1, new Intent().setClass(this, ModificateActivity.class).putExtra("mode", "noservice"), Intent.FLAG_ACTIVITY_CLEAR_TOP));
		n.icon = R.drawable.paw;
		startForeground(100, n);
		final NotificationManager mNM=(NotificationManager)getSystemService(NOTIFICATION_SERVICE);
		mNM.notify(100, n);
		//Toast.makeText(this,"",Toast.LENGTH_LONG).show();
		new AsyncTask<Void,Integer,Void>(){
			public Void doInBackground(Void[] p) {
				ModificateActivity.set(-1, -1, -1, -1, null);
				ModificateActivity.set(getResources().getStringArray(R.array.modSteps).length - 1, 0, -1, -1, null);
				/*Step1*/
				InputStream is=null;
				OutputStream os=null;
				publishProgress(0);
				try {
					int tmp=0;
					ModificateActivity.set(-1, -1, (int)new File(createPackageContext("com.mojang.minecraftpe", CONTEXT_IGNORE_SECURITY).getPackageCodePath()).length(), 0, null);
					is = openAPK();
					os = openFileOutput("vanilla.apk", MODE_MULTI_PROCESS | MODE_WORLD_READABLE);
					byte[] buf=new byte[10000];
					while (true) {
						int i=is.read(buf);
						if (i <= 0)break;
						tmp += i;
						ModificateActivity.set(-1, -1, -1, tmp, null);
						os.write(buf, 0, i);
					}
				} catch (Throwable ex) {
					ex.printStackTrace(System.out);
				} finally {
					try {
						if (is != null)is.close();
						if (os != null)os.close();
					} catch (IOException e) {

					}
				}
				/*Step2*/
				publishProgress(1);
				ZipOutputStream zos=null;
				ZipInputStream zis=null;
				ModificateActivity.set(-1, 1, 0, 0, null);
				Set<String> toCheck=new HashSet<>();
				try {
					ModificateActivity.set(-1, -1, countZipEntries("vanilla.apk"), 0, null);
					is = openFileInput("vanilla.apk");
					os = openFileOutput("modded.apk", MODE_MULTI_PROCESS | MODE_WORLD_READABLE);
					zos = new ZipOutputStream(new BufferedOutputStream(os));
					zis = new ZipInputStream(new BufferedInputStream(is));
					ZipEntry ze;
					int tmp=0;
					byte[] buf=new byte[10000];
					while ((ze = zis.getNextEntry()) != null) {
						if (ze.getName().startsWith("META-INF")) {
							ModificateActivity.set(-1, -1, -1, ++tmp, null);
							continue;//don't copy sign data
						}
						toCheck.add(ze.getName());
						InputStream source=zis;
						if (skins.containsKey(ze.getName())) {
							source = tryOpen(skins.get(ze.getName()).toString());
						}
						ze = new ZipEntry(ze.getName());//by private issue reporting
						zos.putNextEntry(ze);
						while (true) {
							int i=source.read(buf);
							if (i <= 0)break;
							zos.write(buf, 0, i);
						}
						if (skins.containsKey(ze.getName())) {
							source.close();
						}
						ModificateActivity.set(-1, -1, -1, ++tmp, null);
					}
				} catch (Throwable ex) {
					ex.printStackTrace(System.out);
				} finally {
					try {
						if (zis != null)zis.close();
						if (zos != null)zos.close();
					} catch (IOException e) {

					}
				}
				/*Step3*/
				publishProgress(2);
				try {
					ModificateActivity.set(-1, -1, 100, -1, null);
					ZipSigner zs=new ZipSigner();
					zs.setKeymode("auto-testkey");
					zs.addProgressListener(new ProgressListener(){
							public void onProgress(ProgressEvent pe) {
								ModificateActivity.set(-1, -1, -1, pe.getPercentDone(), null);
							}
						});
					zs.signZip(new File(getFilesDir(), "modded.apk") + "",
							   new File(getFilesDir(), "signed.apk") + "");
				} catch (Throwable e) {
					e.printStackTrace(System.out);
				}
				/*Step4*/
				publishProgress(3);
				try {
					int tmp=0;
					ModificateActivity.set(-1, -1, (int)new File(getFilesDir(), "signed.apk").length(), 0, null);
					is = openFileInput("signed.apk");
					os = saveAPK();
					byte[] buf=new byte[10000];
					while (true) {
						int i=is.read(buf);
						if (i <= 0)break;
						tmp += i;
						ModificateActivity.set(-1, -1, -1, tmp, null);
						os.write(buf, 0, i);
					}
				} catch (Throwable ex) {
					ex.printStackTrace(System.out);
				} finally {
					try {
						if (is != null)is.close();
						if (os != null)os.close();
					} catch (IOException e) {

					}
				}
				/*Step5*/
				publishProgress(4);
				try {
					int count=countZipEntries(new ZipInputStream(openFileInput("signed.apk")));
					if (count == -1) {
						Log.d("apkCheck", "count == -1");
					}
					ModificateActivity.set(-1, -1, count, 0, null);
				} catch (FileNotFoundException e) {
					
				}
				int time=0;
				try {
					zis=new ZipInputStream(openFileInput("signed.apk"));
					ZipEntry ze=null;
					while((ze=zis.getNextEntry())!=null){
						toCheck.remove(ze.getName());
						ModificateActivity.set(-1,-1,-1,++time,null);
					}
				} catch (IOException e) {
					e.printStackTrace();
					
				}finally{
					try {
						zis.close();
					} catch (Throwable e) {

					}
				}
				if(toCheck.size()==0){
					Log.d("apkCheck","toCheck.size() == 0");
					
				}else{
					Log.d("apkCheck","toCheck.size() != 0");
					Toast.makeText(ModificationService.this,R.string.check_apk,1).show();
					return null;
				}
				/*Step6*/
				publishProgress(5);
				//ModificateActivity.set(-1,5,-1,-1,null);
				if (ModificateActivity.instance.get() != null){
					ModificateActivity.instance.get().runOnUiThread(new Thread(){
							public void run(){
								ModificateActivity.instance.get().finish();
							}
						});
				}
				Intent data=new Intent(ModificationService.this, ModificateActivity.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK).putExtra("mode", "last");
				startActivity(data);
				stopForeground(true);
				return null;
			}
			public InputStream tryOpen(String uri) throws IOException {
				Log.d("dbg", "tryOpen:" + uri);
				if (uri.startsWith("content://")) {
					return getContentResolver().openInputStream(Uri.parse(uri));
				} else if (uri.startsWith("/")) {
					return new FileInputStream(uri);
				} else {
					return URI.create(uri).toURL().openConnection().getInputStream();
				}
			}
			public OutputStream trySave(String uri) throws IOException {
				Log.d("dbg", "trySave:" + uri);
				if (uri.startsWith("content://")) {
					return getContentResolver().openOutputStream(Uri.parse(uri));
				} else if (uri.startsWith("/")) {
					return new FileOutputStream(uri);
				} else {
					return URI.create(uri).toURL().openConnection().getOutputStream();
				}
			}
			public void onProgressUpdate(Integer[] a) {
				n.setLatestEventInfo(ModificationService.this, getResources().getString(R.string.app_name), getResources().getStringArray(R.array.modSteps)[a[0]], PendingIntent.getActivity(ModificationService.this, -1, new Intent().setClass(ModificationService.this, ModificateActivity.class).putExtra("mode", "noservice"), Intent.FLAG_ACTIVITY_CLEAR_TOP));
				mNM.notify(100, n);
				ModificateActivity.set(-1, a[0], -1, -1, getResources().getStringArray(R.array.modSteps)[a[0]]);
			}
			public int countZipEntries(String path) throws IOException {
				int count=0;
				InputStream is=openFileInput("vanilla.apk");
				ZipInputStream zis=new ZipInputStream(new BufferedInputStream(is));
				while (zis.getNextEntry() != null)
					count++;
				return count;
			}
			public int countZipEntries(ZipInputStream zis) {
				try {
					int count=0;
					while (zis.getNextEntry() != null)
						count++;
					return count;
				} catch (IOException e) {
					return -1;
				}finally{
					try {
						zis.close();
					} catch (Throwable e) {

					}
				}
			}
			InputStream openAPK() throws IOException,PackageManager.NameNotFoundException {
				switch (Tools.getSettings("input.mode", 0, ModificationService.this)) {
					case 0://installed
						return new FileInputStream(createPackageContext("com.mojang.minecraftpe", CONTEXT_IGNORE_SECURITY).getPackageCodePath());
					case 1://select
						return tryOpen(Tools.getSettings("input.where", "", ModificationService.this));
					default:
						return null;
				}
			}
			OutputStream saveAPK() throws IOException {
				switch (Tools.getSettings("input.mode", 0, ModificationService.this)) {
					case 0://installed
						File f=new File(Environment.getExternalStorageDirectory(), "games/com.mojang");
						(f = new File(f, "skinchanger")).mkdirs();
						return new FileOutputStream(new File(f, "signed.apk"));
					case 1://select
						return trySave(Tools.getSettings("output.where", "", ModificationService.this));
					default:
						return null;
				}
			}
		}.execute();
		return START_NOT_STICKY;
	}
}
