package com.nao20010128nao.MCPE.SC;
import java.io.*;
import android.content.pm.*;
import java.util.*;
import java.lang.reflect.*;
import android.app.*;
import android.content.res.*;
import android.graphics.drawable.*;

public class AndroidPackage
{
	static int necessaryFlags=PackageManager.GET_CONFIGURATIONS|PackageManager.GET_INSTRUMENTATION|PackageManager.GET_META_DATA|PackageManager.GET_SIGNATURES;
	static String pPrefix="android.content.pm.";
	static Class packageParser=declare("PackageParser");
	static Class packagE=declare("PackageParser$Package");
	static Class packageUserState=declare("PackageUserState");
	static Class inCl=int.class;
	static Class declare(String name){
		try {
			return Class.forName(pPrefix + name);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	Object parser,packaGE;
	PackageInfo info;
	public AndroidPackage(String file){
		this(new File(file));
	}
	public AndroidPackage(File file){
		Throwable ex=null;
		try {
			parser = packageParser.newInstance();
			packaGE = packageParser.getMethod("parseMonolithicPackage", File.class, inCl).invoke(parser, file, 0);
			packageParser.getMethod("collectManifestDigest", packagE).invoke(parser, packaGE);
			info = (PackageInfo)packageParser.getMethod("generatePackageInfo", packagE, int[].class, inCl, long.class, long.class, Class.forName("android.util.ArraySet"), packageUserState).invoke(null, packaGE, null, necessaryFlags, 0, 0, null, packageUserState.newInstance());
		} catch (InvocationTargetException e) {
			(ex=e).printStackTrace();
		} catch (IllegalArgumentException e) {
			(ex=e).printStackTrace();
		} catch (InstantiationException e) {
			(ex=e).printStackTrace();
		} catch (IllegalAccessException e) {
			(ex=e).printStackTrace();
		} catch (NoSuchMethodException e) {
			(ex=e).printStackTrace();
		} catch (ClassNotFoundException e) {
			(ex=e).printStackTrace();
		}
		if(info==null){
			throw new RuntimeException("An error occured while parsing the file.",ex);
		}
	}
	public PackageInfo getResult(){
		return info;
	}
	
	static public class AppSnippet {
        CharSequence label;
        Drawable icon;
        public AppSnippet(CharSequence label, Drawable icon) {
            this.label = label;
            this.icon = icon;
        }
    }

    /**
     * Utility method to load application label
     *
     * @param pContext context of package that can load the resources
     * @param appInfo ApplicationInfo object of package whose resources are to be loaded
     * @param snippetId view id of app snippet view
     */
    public static AppSnippet getAppSnippet(
		Activity pContext, ApplicationInfo appInfo, File sourceFile) {
        final String archiveFilePath = sourceFile.getAbsolutePath();
        Resources pRes = pContext.getResources();
		AssetManager assmgr=null;
        try {
			assmgr = AssetManager.class.newInstance();
			AssetManager.class.getMethod("addAssetPath", String.class).invoke(assmgr, archiveFilePath);
		} catch (InvocationTargetException e) {
			
		} catch (IllegalArgumentException e) {
			
		} catch (InstantiationException e) {
			
		} catch (IllegalAccessException e) {
			
		} catch (NoSuchMethodException e) {
			
		}
        Resources res = new Resources(assmgr, pRes.getDisplayMetrics(), pRes.getConfiguration());
        CharSequence label = null;
        // Try to load the label from the package's resources. If an app has not explicitly
        // specified any label, just use the package name.
        if (appInfo.labelRes != 0) {
            try {
                label = res.getText(appInfo.labelRes);
            } catch (Resources.NotFoundException e) {
            }
        }
        if (label == null) {
            label = (appInfo.nonLocalizedLabel != null) ?
				appInfo.nonLocalizedLabel : appInfo.packageName;
        }
        Drawable icon = null;
        // Try to load the icon from the package's resources. If an app has not explicitly
        // specified any resource, just use the default icon for now.
        if (appInfo.icon != 0) {
            try {
                icon = res.getDrawable(appInfo.icon);
            } catch (Resources.NotFoundException e) {
            }
        }
        if (icon == null) {
            icon = pContext.getPackageManager().getDefaultActivityIcon();
        }
        return new AppSnippet(label, icon);
    }
}
