package com.nao20010128nao.MCPE.SC.misc;
import android.app.*;
import android.view.*;

public abstract class SmartFindViewActivity extends Activity {
	@Override
	public <T extends View> T find(int id) {
		// TODO: Implement this method
		return (T)super.findViewById(id);
	}
	@Override
	public <T extends View> T find(String id) {
		// TODO: Implement this method
		try {
			return find((int)Class.forName(getPackageName() + ".R$id").getField(id).get(null));
		} catch (ClassNotFoundException e) {
			
		} catch (NoSuchFieldException e) {
			
		} catch (IllegalAccessException e) {
			
		} catch (IllegalArgumentException e) {
			
		}
		return null;
	}
}
