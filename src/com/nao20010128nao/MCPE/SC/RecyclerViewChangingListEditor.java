package com.nao20010128nao.MCPE.SC;
import android.app.*;
import android.os.*;
import android.support.v7.widget.*;
import com.nao20010128nao.MC_PE.SkinChanger.*;
import java.util.*;
import java.net.*;
import android.view.*;
import android.widget.*;
import android.support.v7.widget.RecyclerView.*;
import android.view.ViewGroup.*;

public class RecyclerViewChangingListEditor extends Activity
{
	RecyclerView rv;
	RVAdapter currentAdapter;
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// TODO: Implement this method
		super.onCreate(savedInstanceState);
		setContentView(R.layout.recyclerchglist);
		rv=(RecyclerView)findViewById(R.id.receclerview);
		rv.setLayoutManager(new LinearLayoutManager(this));
		rv.setAdapter(currentAdapter=new RVAdapter());
	}
	private class RVAdapter extends RecyclerView.Adapter<RVHolder>
	{
		int obtained;
		Map<String,URI> skins=ModificateActivity.skins;
		List<Map.Entry<String,URI>> datas=new ArrayList<>();
		{
			for(Map.Entry<String,URI> obj:skins.entrySet())datas.add(obj);
		}
		List<RVHolder> holders=new ArrayList<>();
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
			return obtain();
		}
		public void resetObtainedCount(){
			obtained=0;
		}
		public RVHolder obtain(){
			if(holders.size()>=obtained){
				return holders.get(obtained++);
			}else{
				obtained++;
				RVHolder h=new RVHolder(null);
				holders.add(h);
				return h;
			}
		}
	}
	private class RVHolder extends RecyclerView.ViewHolder{
		LinearLayout cv;
		Map.Entry<String,URI> data;
		TextView mimg,name;
		ImageView delete,reselect,preview;
		private RVHolder(Map.Entry<String,URI> data,LinearLayout cv){
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
			this(data,createView());
		}
		public void setData(Map.Entry<String,URI> data){
			if(data==null)return;
			mimg.setText(data.getKey());
			name.setText(data.getValue().toString());
			cv.setTag(this.data=data);
		}
	}
	LinearLayout createView(){
		return (LinearLayout)getLayoutInflater().inflate(R.layout.carddata,null);	
	}
	private class RVLayoutManager extends RecyclerView.LayoutManager
	{
		@Override
		public RecyclerView.LayoutParams generateDefaultLayoutParams()
		{
			// TODO: Implement this method
			return null;
		}
	}
	public void doUpdate(){
		currentAdapter.resetObtainedCount();
		rv.setAdapter(currentAdapter);
	}
}
