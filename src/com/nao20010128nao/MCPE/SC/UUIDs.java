package com.nao20010128nao.MCPE.SC;
import java.util.*;
import android.content.*;
import com.nao20010128nao.SpoofBrowser.classes.*;

public class UUIDs
{
	public static final String UUID_NULL="00000000-0000-3000-8000-000000000000";
	public UUID uuid[]=new UUID[]{
		UUID.fromString(UUID_NULL),
		UUID.fromString(UUID_NULL),
		UUID.fromString(UUID_NULL),
		UUID.fromString(UUID_NULL)
	};
	public boolean load(Context ctx){
		boolean res=true;
		String s;
		for(int i=0;i<4;i++){
			uuid[i]=UUID.fromString(s=Tools.getSettings("uuid"+i,UUID_NULL,ctx));
			if(s.equals(UUID_NULL)){
				res=false;
			}
		}
		return res;
	}
	public void save(Context ctx){
		for(int i=0;i<4;i++)
			Tools.setSettings("uuid"+i,uuid[i].toString(),ctx);
	}
	public void regenUuid(){
		for(int i=0;i<4;i++)
			if(uuid[i].toString().equals(UUID_NULL))
				uuid[i]=UUID.randomUUID();
	}
}
