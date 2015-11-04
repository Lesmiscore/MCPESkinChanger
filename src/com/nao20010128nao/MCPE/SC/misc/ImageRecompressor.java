package com.nao20010128nao.MCPE.SC.misc;
import java.io.*;
import android.graphics.*;
import java.lang.reflect.*;

public class ImageRecompressor
{
	public static void main(String[] args){
		String frm,dst,cf;
		cf="PNG";
		if(args.length==3){
			cf=args[3];
		}
		frm=args[0];
		dst=args[1];
		recompress(new File(frm),new File(dst),resolveFormat(cf));
	}
	public static boolean recompress(File img,File dst,Bitmap.CompressFormat cf){
		FileOutputStream fos=null;
		try {
			Bitmap bmp=BitmapFactory.decodeFile(img.toString());
			fos=new FileOutputStream(dst);
			bmp.compress(cf,100, fos);
			return true;
		} catch (Throwable e) {
			return false;
		}finally{
			try {
				fos.close();
			} catch (Throwable e) {}
		}
	}
	private static Bitmap.CompressFormat resolveFormat(String s){
		try {
			return (Bitmap.CompressFormat)Bitmap.CompressFormat.class.getField(s).get(null);
		} catch (NoSuchFieldException e) {
			
		} catch (IllegalAccessException e) {
			
		} catch (IllegalArgumentException e) {
			
		}
		try {
			for (Field ft:Bitmap.CompressFormat.class.getFields()) {
				if (ft.getName().equalsIgnoreCase(s)) {
					return (Bitmap.CompressFormat)ft.get(null);
				}
			}
		} catch (IllegalAccessException e) {
			
		} catch (IllegalArgumentException e) {
			
		}
		return null;
	}
}
