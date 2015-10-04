package com.nao20010128nao.MCPE.SC;
import android.app.*;
import android.os.*;
import android.webkit.*;
import com.nao20010128nao.MCPE.SC.misc.*;
import com.nao20010128nao.MC_PE.SkinChanger.*;

public class OpenSourceActivity extends SmartFindViewActivity
{
	WebView view;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO: Implement this method
		super.onCreate(savedInstanceState);
		setContentView(R.layout.license);
		view=find("license");
		view.loadUrl("file:///android_asset/osl.html");
	}
}
