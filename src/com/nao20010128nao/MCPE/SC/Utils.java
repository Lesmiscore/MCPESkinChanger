package com.nao20010128nao.MCPE.SC;
import android.content.*;
import android.content.pm.*;
import android.content.pm.PackageManager.*;
import com.nao20010128nao.MC_PE.SkinChanger.*;
import android.content.res.*;

public class Utils
{
	public static int getVersionCode(Context context){
        PackageManager pm = context.getPackageManager();
        int versionCode = 0;
        try{
            PackageInfo packageInfo = pm.getPackageInfo(context.getPackageName(), 0);
            versionCode = packageInfo.versionCode;
        }catch(NameNotFoundException e){
            e.printStackTrace();
        }
        return versionCode;
    }

    public static String getVersionName(Context context){
        PackageManager pm = context.getPackageManager();
        String versionName = "";
        try{
            PackageInfo packageInfo = pm.getPackageInfo(context.getPackageName(), 0);
            versionName = packageInfo.versionName;
        }catch(NameNotFoundException e){
            e.printStackTrace();
        }
        return versionName;
	}
	
	public static String getBetaVersion(Context c){
		Resources res=c.getResources();
		int build=res.getInteger(R.integer.beta);
		if(build==0){
			return res.getString(R.string.notbeta);
		}else{
			return res.getString(R.string.onbeta).replace("@BETA@",Integer.toString(build));
		}
	}
}
