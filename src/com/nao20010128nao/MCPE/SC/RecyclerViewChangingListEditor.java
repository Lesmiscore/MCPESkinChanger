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
		rv.setAdapter(new RVAdapter());
	}
	private class RVAdapter extends RecyclerView.Adapter<RVHolder>
	{
		Map<String,URI> skins=ModificateActivity.skins;
		List<Map.Entry<String,URI>> datas=new ArrayList<>();
		{
			for(Map.Entry<String,URI> obj:skins.entrySet())datas.add(obj);
		}
		List<RVHolder> holders;
		@Override
		public int getItemCount()
		{
			// TODO: Implement this method
			return skins.size();
		}
		@Override
		public void onBindViewHolder(RecyclerViewChangingListEditor.RVHolder p1, int p2)
		{
			// TODO: Implement this method
			p1.setData(datas.get(p2));
		}
		@Override
		public RecyclerViewChangingListEditor.RVHolder onCreateViewHolder(ViewGroup p1, int p2)
		{
			// TODO: Implement this method
			RVHolder h=new RVHolder(null);
			holders.add(h);
			return h;
		}
	}
	private class RVHolder extends RecyclerView.ViewHolder{
		CardView cv;
		Map.Entry<String,URI> data;
		TextView mimg,name;
		ImageView delete,reselect,preview;
		private RVHolder(Map.Entry<String,URI> data,CardView cv){
			super(cv);
			this.cv=cv;
			this.data=data;
			mimg=(TextView)cv.findViewById(R.id.mobimgname);
			name=(TextView)cv.findViewById(R.id.fname);
			delete=(ImageView)cv.findViewById(R.id.delete);
			reselect=(ImageView)cv.findViewById(R.id.reselect);
			preview=(ImageView)cv.findViewById(R.id.preview);
		}
		public RVHolder(Map.Entry<String,URI> data){
			this(data,createCardView());
		}
		public void setData(Map.Entry<String,URI> data){
			if(data==null)return;
			mimg.setText(data.getKey());
			name.setText(data.getValue().toString());
			cv.setTag(this.data=data);
		}
	}
	CardView createCardView(){
		CardView cv=new CardView(this);
		LinearLayout content=(LinearLayout)getLayoutInflater().inflate(R.layout.carddata,null);
		cv.addView(content);
		return cv;
	}
}
