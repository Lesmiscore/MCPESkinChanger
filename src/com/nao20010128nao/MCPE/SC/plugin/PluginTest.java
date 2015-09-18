package com.nao20010128nao.MCPE.SC.plugin;
import com.nao20010128nao.MCPE.SC.misc.*;
import android.os.*;
import android.widget.*;
import android.view.*;
import android.app.*;
import java.lang.reflect.*;
import android.util.*;
import android.content.*;

public class PluginTest extends SmartFindViewActivity
{
	TextView tv1,tv2;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO: Implement this method
		super.onCreate(savedInstanceState);
		ScrollView sv=createView(ScrollView.class);
		LinearLayout ll=createView(LinearLayout.class);
		ll.setOrientation(LinearLayout.VERTICAL);
		tv1=createView(TextView.class);
		ll.addView(tv1);
		tv2=createView(TextView.class);
		ll.addView(tv2);
		tv1.setText(getIntent().getStringExtra("map"));
		tv2.setText(new String(Base64.decode(getIntent().getStringExtra("map"),Base64.NO_WRAP)));
		sv.addView(ll);
		setContentView(sv);
	}
	public <T extends View> T createView(T... d){
		try {
			return (T)d.getClass().getComponentType().getConstructor(Context.class).newInstance(this);
		} catch (InstantiationException e) {
			
		} catch (NoSuchMethodException e) {
			
		} catch (IllegalArgumentException e) {
			
		} catch (InvocationTargetException e) {
			
		} catch (IllegalAccessException e) {
			
		}
		return null;
	}
	public <T extends View> T createView(Class<T> cls){
		try {
			return (T)cls.getConstructor(Context.class).newInstance(this);
		} catch (InstantiationException e) {

		} catch (NoSuchMethodException e) {

		} catch (IllegalArgumentException e) {

		} catch (InvocationTargetException e) {

		} catch (IllegalAccessException e) {

		}
		return null;
	}
}
