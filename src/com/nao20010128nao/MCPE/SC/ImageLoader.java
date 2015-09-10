package com.nao20010128nao.MCPE.SC;
import java.io.*;
import java.util.zip.*;
import android.graphics.*;

public class ImageLoader {
	private ImageLoader() {

	}
	public static void startLoadImages(String path) throws IOException {
		ZipInputStream zis=null;
		try {
			zis = new ZipInputStream(new FileInputStream(path));
			ZipEntry ze=null;
			while ((ze = zis.getNextEntry()) != null) {
				if (ze.getName().endsWith(".png")) {
					try {
						Bitmap load=BitmapFactory.decodeStream(zis);
						if (load != null) {
							String[] arr;
							ImageHandler.observe((arr = ze.getName().split("¥¥/"))[arr.length - 1], load);
						}
					} catch (Throwable e) {
						e.printStackTrace();
					}
				}
			}
		} finally {
			if (zis != null)zis.close();
		}
	}
	public static void startLoadImagesAsync(final String path){
		new Thread(){
			public void run(){
				try {
					startLoadImages(path);
				} catch (IOException e) {
					
				}
			}
		}.start();
	}
}
