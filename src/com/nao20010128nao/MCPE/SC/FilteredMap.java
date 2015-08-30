package com.nao20010128nao.MCPE.SC;
import java.util.*;
import java.net.*;

public class FilteredMap extends HashMap<String,URI> {
	static Set<String> defaultBans=new HashSet<>();
	static{
		defaultBans.add("AndroidManifest.xml");
		defaultBans.add("META-INF/MANIFEST.MF");
		defaultBans.add("META-INF/CERT.SF");
		defaultBans.add("META-INF/CERT.RSA");
		defaultBans.add("lib/armeabi-v7a/libminecraft.so");
	}
	boolean quickDel;
	Set<String> banned;
	public FilteredMap(){
		this(false,defaultBans);
	}
	public FilteredMap(boolean qd){
		this(qd,defaultBans);
	}
	public FilteredMap(Set<String> bans){
		this(false,bans);
	}
	public FilteredMap(boolean qd,Set<String> bans){
		quickDel=qd;
		banned=new PathSet<>(bans);
	}
	@Override
	public URI put(String key, URI value) {
		// TODO: Implement this method
		if(key==null)
			throw new IllegalArgumentException("key == null");
		if(key.length()==0)
			throw new IllegalArgumentException("key == \"\"");
		if(value==null){
			if(quickDel){
				return remove(key);
			}else{
				throw new IllegalArgumentException("value == null");
			}
		}
		if(!canPut(key,value))
			throw new IllegalArgumentException("\""+key+"\" can't put.");
		return super.put(key, value);
	}

	@Override
	public void putAll(Map<? extends String, ? extends URI> map) {
		// TODO: Implement this method
		for(Map.Entry<? extends String, ? extends URI> o:map.entrySet()){
			put(o.getKey(),o.getValue());
		}
	}
	private boolean canPut(String key,URI value){
		if(banned.contains(key)){
			return false;
		}
		return true;
	}
	private class PathSet extends HashSet<String> {
		public PathSet(Set<String> base){
			addAll(base);
		}
		@Override
		public boolean add(String object) {
			// TODO: Implement this method
			if(object==null)return false;
			object=object.replace('¥','/').replace('\\','/').toLowerCase();
			return super.add(object);
		}

		@Override
		public boolean addAll(Collection<? extends String> collection) {
			// TODO: Implement this method
			boolean result=true;
			for(String s:collection){
				if(!add(s)){
					result=false;
				}
			}
			return result;
		}

		@Override
		public boolean contains(Object object) {
			// TODO: Implement this method
			if(object==null){
				return false;
			}
			if(!(object instanceof String)){
				return false;
			}
			String data=((String)object).replace('¥','/').replace('\\','/').toLowerCase();
			return super.contains(data);
		}

		@Override
		public boolean containsAll(Collection<?> collection) {
			// TODO: Implement this method
			boolean result=true;
			for(Object s:collection){
				if(!contains(s)){
					result=false;
				}
			}
			return result;
		}
	}
}
