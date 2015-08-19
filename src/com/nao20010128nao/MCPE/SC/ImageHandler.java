package com.nao20010128nao.MCPE.SC;
import android.graphics.*;
import java.util.*;

public class ImageHandler
{
	static Map<String,? extends Set<ImageHandlerReceiver>> objects=new HashMap<String,HashSet<ImageHandlerReceiver>>();
	
	public static interface ImageHandlerReceiver{
		void setImage(Bitmap bmp);
	}
}
