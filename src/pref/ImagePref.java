package pref;
import android.util.*;
import android.content.*;
import android.view.*;
import com.nao20010128nao.MC_PE.SkinChanger.*;
import com.nao20010128nao.MCPE.SC.*;
import android.graphics.*;
import java.io.*;
import android.os.*;
import android.app.*;
import android.graphics.drawable.*;

public class ImagePref extends StartPref implements ImageHandler.ImageHandlerReceiver{
	Handler h=new Handler();
	public ImagePref(Context c,AttributeSet attrs){
		super(c,attrs);
	}

	@Override
	public void setImage(Bitmap bmp) {
		// TODO: Implement this method
		final Drawable draw=new BitmapDrawable(bmp);
		Runnable r=new Runnable(){
			public void run(){
				setIcon(draw);
			}
		};
		Context ctx=getContext();
		if(ctx instanceof Activity){
			Activity act=(Activity)ctx;
			act.runOnUiThread(r);
		}else{
			h.post(r);
		}
	}
}
