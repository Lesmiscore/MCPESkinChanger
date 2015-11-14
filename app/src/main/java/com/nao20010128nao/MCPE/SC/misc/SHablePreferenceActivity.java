package com.nao20010128nao.MCPE.SC.misc;
import android.preference.*;
import com.nao20010128nao.ToolBox.*;

public abstract class SHablePreferenceActivity extends PreferenceActivity{
	protected void sH(Preference pref,HandledPreference.OnClickListener handler){
		if(!(pref instanceof HandledPreference))return;
		((HandledPreference)pref).setOnClickListener(handler);
	}
	protected void sH(String pref,HandledPreference.OnClickListener handler){
		sH(findPreference(pref),handler);
	}
}
