package com.nao20010128nao.MCPE.SC.plugin;
import java.util.*;
import java.util.Map.*;

public class DiffMap<K,V> implements Map<K,V> {
	Map<K,V> base,diff=new HashMap<>();
	Set<K> remove=new HashSet<>();
	
	boolean cacheValid=false;
	Set<Map.Entry<K,V>> entSetC;
	Set<K> keySetC;
	Collection<V> valColC;
	public DiffMap(Map<K,V> base){
		this.base=Collections.unmodifiableMap(base);
	}
	@Override
	public V put(K p1, V p2) {
		// TODO: Implement this method
		cacheValid=false;
		remove.add(p1);
		return diff.put(p1,p2);
	}
	@Override
	public void putAll(Map<? extends K, ? extends V> p1) {
		// TODO: Implement this method
		cacheValid=false;
		remove.addAll(p1.keySet());
		diff.putAll(p1);
	}
	@Override
	public Set<Map.Entry<K, V>> entrySet() {
		// TODO: Implement this method
		regenCache();
		return entSetC;
	}
	@Override
	public boolean containsKey(Object p1) {
		// TODO: Implement this method
		if(base.containsKey(p1)){
			return !remove.contains(p1);
		}else{
			return diff.containsKey(p1);
		}
	}
	@Override
	public boolean containsValue(Object p1) {
		// TODO: Implement this method
		if(base.containsValue(p1)){
			for(Map.Entry<K,V> entry:diff.entrySet()){
				if(equals(entry.getValue(),p1)){
					return !remove.contains(entry.getKey());
				}
			}
			return true;
		}else{
			return diff.containsValue(p1);
		}
	}
	@Override
	public V get(Object p1) {
		// TODO: Implement this method
		if(base.containsValue(p1)){
			if(remove.contains(p1))return null;
			return base.get(p1);
		}else{
			return diff.get(p1);
		}
	}
	@Override
	public int size() {
		// TODO: Implement this method
		regenCache();
		return entSetC.size();
	}
	@Override
	public V remove(Object p1) {
		// TODO: Implement this method
		cacheValid=false;
		if (p1 == null) {
			return null;
		}else{
			Class c=getClass().getTypeParameters()[0].getGenericDeclaration();
			if(!c.isInstance(p1)){
				return null;
			}
		}
		if(remove.remove((K)p1))
			return diff.remove(p1);
		else
			return null;
	}
	@Override
	public Collection<V> values() {
		// TODO: Implement this method
		regenCache();
		return valColC;
	}
	@Override
	public Set<K> keySet() {
		// TODO: Implement this method
		regenCache();
		return keySetC;
	}
	@Override
	public boolean isEmpty() {
		// TODO: Implement this method
		return base.isEmpty()|remove.isEmpty()|diff.isEmpty();
	}
	@Override
	public void clear() {
		// TODO: Implement this method
		cacheValid=false;
		remove.addAll(base.keySet());
		diff.clear();
	}
	private boolean equals(Object a,Object b){
		if(a==null&b==null){
			return true;
		}
		if(a==null|b==null){
			return false;
		}
		return a.equals(b)|b.equals(a);
	}
	public void regenCache(){
		if(cacheValid)
			return;
		{
			List<V> col=new ArrayList<>();
			col.addAll(base.values());
			for(K k:remove){
				col.remove(base.get(k));
			}
			col.addAll(diff.values());
			valColC=Collections.unmodifiableCollection(col);
		}
		{
			Set<K> keys=new HashSet<>();
			keys.addAll(base.keySet());
			keys.removeAll(remove);
			keys.addAll(diff.keySet());
			keySetC=Collections.unmodifiableSet(keys);
		}
		{
			Set<Map.Entry<K,V>> ents=new HashSet<>();
			ents.addAll(base.entrySet());
			for(Map.Entry<K,V> entry:ents){
				if(remove.contains(entry.getKey())){
					ents.remove(entry);
				}
			}
			ents.addAll(diff.entrySet());
			entSetC=Collections.unmodifiableSet(ents);
		}
		cacheValid=true;
	}
	public Set<K> getRemoves(){
		return Collections.unmodifiableSet(remove);
	}
	public Map<K,V> getAdds(){
		return Collections.unmodifiableMap(diff);
	}
}
