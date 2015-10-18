package com.nao20010128nao.MCPE.SC;
import java.util.*;
import java.net.*;
import joptsimple.*;
import java.io.*;
import java.util.zip.*;
import kellinwood.security.zipsigner.*;
import com.nao20010128nao.MC_PE.SkinChanger.*;

public class IsolatedChanger
{
	static String apk,changes,output,cache;
	static Map<String,URI> converts=ModificateActivity.skins;
	public static void main(String[] args){
		// Phase 1 - Parsing Arguments
		OptionParser op=new OptionParser();
		op.accepts("apk").withRequiredArg();
		op.accepts("changes").withRequiredArg();
		op.accepts("output").withRequiredArg();
		op.accepts("cache").withRequiredArg();
		OptionSet os=op.parse(args);
		if(os.has("apk")|os.hasArgument("apk")){
			apk=os.valueOf("apk").toString();
		}else{
			System.out.println("Input APK was not set in the arguments.");
			return;
		}
		if(os.has("changes")|os.hasArgument("changes")){
			changes=os.valueOf("changes").toString();
		}else{
			System.out.println("Change Files was not set in the arguments.");
			return;
		}
		if(os.has("output")|os.hasArgument("output")){
			output=os.valueOf("output").toString();
		}else{
			System.out.println("Input APK was not set in the arguments.");
			return;
		}
		if(os.has("cache")|os.hasArgument("cache")){
			changes=os.valueOf("cache").toString();
		}else{
			changes=System.getProperty("user.dir");
		}
		
		// Phase 2 - Parsing Changes
		for(String s:changes.split("\\;")){
			String[] oneConv=s.split("\\:");
			String vfile=oneConv[0];
			String rfile=oneConv[1];
			try {
				converts.put(vfile, new URI(rfile));
			} catch (URISyntaxException e) {
				System.err.println("Error:");
				e.printStackTrace();
			}
		}
		
		// Phase 3 - Launching Changer
		runChange();
		System.out.println();
	}
	static private void runChange(){
		/*Step1*/
		InputStream is=null;
		OutputStream os=null;
		publishProgress(0);
		try {
			int tmp=0;
			set(-1, -1, calcLength(openAPK()), 0, null);
			is = openAPK();
			os = openFileOutput("vanilla.apk");
			byte[] buf=new byte[10000];
			while (true) {
				int i=is.read(buf);
				if (i <= 0)break;
				tmp += i;
				set(-1, -1, -1, tmp, null);
				os.write(buf, 0, i);
			}
		} catch (Throwable ex) {
			System.err.println("Error:");
			ex.printStackTrace();
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
		set(-1, 1, 0, 0, null);
		Set<String> toCheck=new HashSet<>();
		try {
			set(-1, -1, countZipEntries("vanilla.apk"), 0, null);
			is = openFileInput("vanilla.apk");
			os = openFileOutput("modded.apk");
			zos = new ZipOutputStream(new BufferedOutputStream(os));
			zis = new ZipInputStream(new BufferedInputStream(is));
			zos.setLevel(8);
			ZipEntry ze;
			int tmp=0;
			byte[] buf=new byte[10000];
			while ((ze = zis.getNextEntry()) != null) {
				if (ze.getName().startsWith("META-INF")) {
					set(-1, -1, -1, ++tmp, null);
					continue;//don't copy sign data
				}
				toCheck.add(ze.getName());
				InputStream source=zis;
				if (converts.containsKey(ze.getName())) {
					source = tryOpen(converts.get(ze.getName()).toString());
				}
				ze = new ZipEntry(ze.getName());//by private issue reporting
				zos.putNextEntry(ze);
				while (true) {
					int i=source.read(buf);
					if (i <= 0)break;
					zos.write(buf, 0, i);
				}
				if (converts.containsKey(ze.getName())) {
					source.close();
				}
				set(-1, -1, -1, ++tmp, null);
			}
		} catch (Throwable ex) {
			System.err.println("Error:");
			ex.printStackTrace();
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
			set(-1, -1, 100, -1, null);
			ZipSigner zs=new ZipSigner();
			zs.setKeymode("auto-testkey");
			zs.addProgressListener(new ProgressListener(){
					public void onProgress(ProgressEvent pe) {
						set(-1, -1, -1, pe.getPercentDone(), null);
					}
				});
			zs.signZip(new File(cache,"modded.apk") + "",
					   new File(cache,"signed.apk") + "");
		} catch (Throwable e) {
			System.err.println("Error:");
			e.printStackTrace();
		}
		/*Step4*/
		publishProgress(3);
		try {
			int tmp=0;
			set(-1, -1, (int)new File(cache, "signed.apk").length(), 0, null);
			is = openFileInput("signed.apk");
			os = saveAPK();
			byte[] buf=new byte[10000];
			while (true) {
				int i=is.read(buf);
				if (i <= 0)break;
				tmp += i;
				set(-1, -1, -1, tmp, null);
				os.write(buf, 0, i);
			}
		} catch (Throwable ex) {
			System.err.println("Error:");
			ex.printStackTrace();
		} finally {
			try {
				if (is != null)is.close();
				if (os != null)os.close();
			} catch (IOException e) {

			}
		}
		/*Step5*/
		publishProgress(4);
		set(-1, -1, -1, 0, null);
		try {
			int count=countZipEntries(new ZipInputStream(openFileInput("signed.apk")));
			if (count == -1) {
				System.out.println("ErrorToast:"+R.string.check_apk);
				return;
			}
			set(-1, -1, count, 0, null);
		} catch (FileNotFoundException e) {
			System.err.println("Error:");
			e.printStackTrace();
		}
		int time=0;
		try {
			zis=new ZipInputStream(openFileInput("signed.apk"));
			ZipEntry ze=null;
			while((ze=zis.getNextEntry())!=null){
				toCheck.remove(ze.getName());
				set(-1,-1,-1,++time,null);
			}
		} catch (IOException e) {
			System.err.println("Error:");
			e.printStackTrace();
		}finally{
			try {
				zis.close();
			} catch (Throwable e) {

			}
		}
		if(toCheck.size()==0){
			
		}else{
			System.out.println("ErrorToast:"+R.string.check_apk);
			return;
		}
		/*Step6*/
		publishProgress(5);
		return;// Main process does above
	}
	public static void publishProgress(int prog){
		set(-1, prog, -1, -1, prog);
	}
	public static void set(int tM, int tV, int cM, int cV, Integer state) {
		System.out.println("Status:"+tM+";"+tV+";"+cM+";"+cV+";"+state);
	}
	public static int calcLength(InputStream is){
		int len=0,err=0;
		try {
			if (is.available() <= 1)
				return is.available();
		} catch (IOException e) {

		}
		byte[] buf=new byte[10000];
		int l=0;
		while(true){
			try {
				if ((l=is.read(buf)) != -1)len+=l;
				else{
					is.close();
					return len;
				}
			} catch (IOException e) {
				e.printStackTrace();
				err++;
			}
			if(err>10){
				return -1;
			}
		}
	}
	public static int countZipEntries(ZipInputStream zis) {
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
	public static int countZipEntries(String path) throws IOException {
		int count=0;
		InputStream is=openFileInput("vanilla.apk");
		ZipInputStream zis=new ZipInputStream(new BufferedInputStream(is));
		while (zis.getNextEntry() != null)
			count++;
		return count;
	}
	public static InputStream openFileInput(String s)throws FileNotFoundException{
		return new FileInputStream(new File(cache,s));
	}
	public static OutputStream openFileOutput(String s)throws FileNotFoundException{
		return new FileOutputStream(new File(cache,s));
	}
	public static InputStream openAPK()throws FileNotFoundException{
		return new FileInputStream(apk);
	}
	public static OutputStream saveAPK()throws FileNotFoundException{
		return new FileOutputStream(output);
	}
	public static InputStream tryOpen(String s)throws FileNotFoundException{
		return new FileInputStream(s);
	}
}
