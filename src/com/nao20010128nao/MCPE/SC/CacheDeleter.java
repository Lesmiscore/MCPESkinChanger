package com.nao20010128nao.MCPE.SC;
import joptsimple.*;
import java.io.*;
import android.os.*;

public class CacheDeleter
{
	static String cache,data;
	static boolean deleteCopy;
	public static void main(String args[]){
		OptionParser op=new OptionParser();
		op.accepts("data").withRequiredArg();
		op.accepts("cache").withRequiredArg();
		op.accepts("copdel").withRequiredArg();
		OptionSet os=op.parse(args);
		if(os.has("data")|os.hasArgument("data")){
			data=os.valueOf("data").toString();
		}else{
			System.out.println("Data was not set in the arguments.");
			return;
		}
		if(os.has("cache")|os.hasArgument("cache")){
			cache=os.valueOf("cache").toString();
		}else{
			System.out.println("Cache was not set in the arguments.");
			return;
		}
		if(os.has("copdel")|os.hasArgument("copdel")){
			deleteCopy=new Boolean(os.valueOf("copdel").toString());
		}else{
			deleteCopy=false;
		}
		{
			try {
				File cacheDir=new File(data, "cache");
				cacheDir.mkdirs();
				new ProcessBuilder().
					command(new String[]{"/system/bin/rm","-rf",cacheDir.getAbsolutePath()}).
					directory(cacheDir).
					start().
					waitFor();
			} catch (Throwable ex) {

			}
			try{
				File sco=new File(Environment.getExternalStorageDirectory(), "games/com.mojang/minecraft/skinchanger");
				new ProcessBuilder().
					command(new String[]{"/system/bin/rm","-rf",sco.getAbsolutePath()}).
					directory(sco).
					start().
					waitFor();
				sco.delete();
				new File(data, "vanilla.apk").delete();
				new File(data, "modded.apk").delete();
				new File(data, "signed.apk").delete();
			}catch(Throwable e){

			}
			try{
				new File(cache, "vanilla.apk").delete();
				new File(cache, "modded.apk").delete();
				new File(cache, "signed.apk").delete();
			}catch(Throwable e){

			}
			try {
				new ProcessBuilder().
					command(new String[]{"/system/bin/rm","-rf",new File(cache).getAbsolutePath()}).
					directory(new File(cache).getAbsoluteFile()).
					start().
					waitFor();
			} catch (IOException e) {
				
			} catch (InterruptedException e) {
				
			}
			try {
				new File(data,"mcpeCopy_unchecked.apk").delete();
				if(deleteCopy){
					new File(data,"mcpeCopy.apk").delete();
				}
			}catch(Throwable e){

			}
		}
	}
}
