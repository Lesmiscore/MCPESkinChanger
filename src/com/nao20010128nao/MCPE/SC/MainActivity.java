package com.nao20010128nao.MCPE.SC;
import android.app.*;
import android.os.*;
import android.content.*;

public class MainActivity extends Activity
{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO: Implement this method
		super.onCreate(savedInstanceState);
		startActivity(new Intent(this,SplashActivity.class));
		finish();
	}
}
