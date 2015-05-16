package com.nao20010128nao.MCPE.SC;
import android.app.*;
import android.os.*;
import android.support.v7.widget.*;
import com.nao20010128nao.MC_PE.SkinChanger.*;
import java.util.*;
import java.net.*;
import android.view.*;
import android.widget.*;

public class RecyclerViewChangingListEditor extends Activity
{
	RecyclerView rv;
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// TODO: Implement this method
		super.onCreate(savedInstanceState);
		setContentView(R.layout.recyclerchglist);
		rv=(RecyclerView)findViewById(R.id.receclerview);
		rv.setAdapter();
	}
	private class RVAdapter extends RecyclerView.Adapter<RVHolder>{
		
	}
	private class RVHolder extends RecyclerView.ViewHolder{
		CardView cv;
		private RVHolder(Map.Entry<String,URI> data,CardView cv){
			super(cv);
			this.cv=cv;
			TextView mimg=(TextView)cv.findViewById(R.id.mobimgname);
			mimg.setText(data.getKey());
			TextView name=(TextView)cv.findViewById(R.id.fname);
			name.setText(data.getValue().toString());
		}
		public RVHolder(Map.Entry<String,URI> data){
			this(data,createCardView());
		}
	}
	CardView createCardView(){
		CardView cv=new CardView(this);
		LinearLayout content=(LinearLayout)getLayoutInflater().inflate(R.layout.carddata,null);
		cv.addView(content);
		return cv;
	}
}
