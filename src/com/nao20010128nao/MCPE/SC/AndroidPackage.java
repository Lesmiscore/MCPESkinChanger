package com.nao20010128nao.MCPE.SC;
import java.io.*;
import android.content.pm.*;
import java.util.*;
import java.lang.reflect.*;

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
			packageParser.getMethod("collectManifestDigest", packagE).invoke(parser, packagE);
			info = (PackageInfo)packageParser.getMethod("generatePackageInfo", packagE, int[].class, inCl, long.class, long.class, HashSet.class, packageUserState).invoke(null, packaGE, null, necessaryFlags, 0, 0, null, packageUserState.newInstance());
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
		}
		if(info==null){
			throw new RuntimeException("An error occured while parsing the file.",ex);
		}
	}
	public PackageInfo getResult(){
		return info;
	}
}
