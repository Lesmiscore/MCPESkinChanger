package com.nao20010128nao.MCPE.SC;
import java.util.*;

public class FilteredMap extends HashMap<String,String> {
	boolean quickDel;
	public FilteredMap(){
		this(false);
	}
	public FilteredMap(boolean qd){
		quickDel=qd;
	}
	@Override
	public String put(String key, String value) {
		// TODO: Implement this method
		if(key==null)
			throw new IllegalArgumentException("key == null");
		if(key.length()==0)
			throw new IllegalArgumentException("key == \"\"");
		if("".equals(value==null?"":value)){
			if(quickDel){
				return remove(key);
			}else{
				throw new IllegalArgumentException("value == \"\" or null");
			}
		}
		return super.put(key, value);
	}

	@Override
	public void putAll(Map<? extends String, ? extends String> map) {
		// TODO: Implement this method
		super.putAll(map);
	}
}
