package com.nao20010128nao.MCPE.SC;
import android.app.*;
import android.os.*;
import java.util.*;
import java.io.*;
import android.content.*;
import android.util.*;
import com.nao20010128nao.MC_PE.SkinChanger.*;
import java.util.zip.*;
import android.widget.*;

public class APKVerifyActivity extends Activity {
	volatile transient static int totalMax,totalVal,compMax,compVal;
	ProgressBar total,component;TextView state;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO: Implement this method
		super.onCreate(savedInstanceState);
		setContentView(R.layout.modder);
		
		total = (ProgressBar)findViewById(R.id.pbTotal);
		component = (ProgressBar)findViewById(R.id.pbComponent);
		state = (TextView)findViewById(R.id.tvState);
		
		final Set<String> data=restoreFromIntent(getIntent());
		final String target=getIntent().getStringExtra("target");
		if(data==null){
			setResult(RESULT_CANCELED);
			finish();
			return;
		}else if(data.size()==0){
			setResult(RESULT_CANCELED);
			finish();
			return;
		}
		new AsyncTask<Void,Void,ReturnObject>(){
			public int countZipEntries(File path) {
				try {
					int count=0;
					InputStream is=new FileInputStream(path);
					ZipInputStream zis=new ZipInputStream(new BufferedInputStream(is));
					while (zis.getNextEntry() != null)
						count++;
					return count;
				} catch (IOException e) {
					return -1;
				}
			}
			public ReturnObject doInBackground(Void[] a){
				File targetFile=new File(target);
				if(!targetFile.exists()){
					return new ReturnObject(false,false);
				}
				ZipInputStream zis=null;
				Set<String> check=new HashSet<>(data);//copy for safe
				int count=countZipEntries(targetFile);
				if(count==-1){
					return new ReturnObject(false,false);
				}
				set(count,0,count,0);
				int time=0;
				try {
					zis=new ZipInputStream(new FileInputStream(targetFile));
					ZipEntry ze=null;
					while((ze=zis.getNextEntry())!=null){
						check.remove(ze.getName());
						set(-1,++time,-1,time);
					}
				} catch (IOException e) {
					return new ReturnObject(false,false);
				}finally{
					try {
						zis.close();
					} catch (Throwable e) {
						
					}
				}
				if(check.size()==0){
					return new ReturnObject(true,true);
				}else{
					return new ReturnObject(true,false);
				}
			}
			public void onPostExecute(ReturnObject res){
				setResult(res.success?RESULT_OK:RESULT_CANCELED,new Intent().putExtra("valid",res.valid));
			}
		}.execute();
	}
	class ReturnObject{
		public ReturnObject(){
			
		}
		public ReturnObject(boolean s,boolean v){
			success=s;
			valid=v;
		}
		public boolean success;
		public boolean valid;
	}
	public static void writeTo(Set<String> sl,OutputStream os) throws IOException{
		DataOutputStream dos=new DataOutputStream(os);
		dos.writeInt(sl.size());
		for(String s:sl){
			byte[] array=s.getBytes();
			dos.writeInt(array.length);
			dos.write(array);
		}
	}
	public static Set<String> readFrom(InputStream is) throws IOException{
		DataInputStream dis=new DataInputStream(is);
		HashSet<String> set=new HashSet<>();
		int size=dis.readInt();
		for(int i=0;i<size;i++){
			int bytes=dis.readInt();
			byte[] buf=new byte[bytes];
			dis.readFully(buf);
			set.add(new String(buf));
		}
		return set;
	}
	
	public static void putIntoIntent(Set<String> files,Intent dest){
		ByteArrayOutputStream baos=new ByteArrayOutputStream();
		try {
			writeTo(files, baos);
		} catch (IOException e) {
			//unreachable, because ByteArrayOutputStream won't throw IOException.
		}
		String base64=Base64.encodeToString(baos.toByteArray(),Base64.NO_WRAP);
		dest.putExtra("checkList",base64);
	}
	public static Set<String> restoreFromIntent(Intent from){
		String base64=from.getStringExtra("checkList");
		ByteArrayInputStream bais=new ByteArrayInputStream(Base64.decode(base64,Base64.NO_WRAP));
		try {
			return readFrom(bais);
		} catch (IOException e) {
			//unreachable, because ByteArrayOutputStream won't throw IOException.
			return new HashSet<String>();
		}
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
					state.setText(R.string.verifying);
					findViewById(android.R.id.content).invalidate();
				}
			});
	}
	public void set(int tM, int tV, int cM, int cV) {
		totalMax = tM == -1 ?totalMax: tM;
		totalVal = tV == -1 ?totalVal: tV;
		compMax = cM == -1 ?compMax: cM;
		compVal = cV == -1 ?compVal: cV;
		update();
	}
}
