package com.nao20010128nao.MCPE.SC;
import android.app.*;
import android.os.*;
import com.nao20010128nao.MC_PE.SkinChanger.*;
import android.widget.*;
import com.nao20010128nao.MCPE.SC.misc.*;

public class SignFileActivity extends SmartFindViewActivity{
	ProgressBar progress;
	EditText input,output;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO: Implement this method
		super.onCreate(savedInstanceState);
		setContentView(R.layout.signer);
		progress=find("signerProgress");
		input=find("signerInput");
		output=find("signerOutput");
	}
}
