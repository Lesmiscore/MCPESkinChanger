package com.nao20010128nao.MCPE.SC;
import android.graphics.*;
import java.util.*;
import java.io.*;

public class ImageHandler
{
	static Map<String,HashSet<ImageHandlerReceiver>> objects=new HashMap<String,HashSet<ImageHandlerReceiver>>();
	public static void registerReceiver(String tag,ImageHandlerReceiver receiver){
		if(tag==null)
			throw new NullPointerException("tag == null");
		if(receiver==null)
			throw new NullPointerException("receiver == null");
		if(!objects.containsKey(tag)){
			objects.put(tag,new HashSet());
		}
		objects.get(tag).add(receiver);
	}
	public static void unregisterReceiver(String tag,ImageHandlerReceiver receiver){
		if(tag==null)
			throw new NullPointerException("tag == null");
		if(receiver==null)
			throw new NullPointerException("receiver == null");
		if(!objects.containsKey(tag))
			return;
		objects.get(tag).remove(receiver);
	}
	public static void observe(String tag,Bitmap bmp){
		if(tag==null)
			throw new NullPointerException("tag == null");
		if(!objects.containsKey(tag))
			return;
		for(ImageHandlerReceiver ihr:objects.get(tag)){
			try {
				ihr.setImage(bmp);
			} catch (Throwable e) {
				
			}
		}
	}
	public static interface ImageHandlerReceiver{
		void setImage(Bitmap bmp) throws IOException;
	}
}
