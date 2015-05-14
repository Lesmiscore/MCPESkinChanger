package com.nao20010128nao.MCPE.SC;
import android.app.*;
import android.os.*;
import com.nao20010128nao.MC_PE.SkinChanger.*;
import android.widget.*;
import java.util.*;
import java.net.*;
import android.view.*;
import android.content.*;
import android.util.*;

public class ChangingListEditor extends ListActivity
{
	String changeTmp=null;
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// TODO: Implement this method
		super.onCreate(savedInstanceState);
		setContentView(R.layout.chglist);
		setListAdapter(new InternalListAdapter());
		getListView().setOnItemClickListener(new AdapterView.OnItemClickListener(){
			public void onItemClick(AdapterView view,View v,int i,long l){
				Map.Entry<String,URI> entry=(Map.Entry<String,URI>)v.getTag();
				final String key=entry.getKey();
				new AlertDialog.Builder(ChangingListEditor.this).
					setTitle(R.string.confirm).
					setMessage(getResources().getString(R.string.changeDeleteConfirm).replace("@KEY@",key)).
					setPositiveButton(android.R.string.yes,new DialogInterface.OnClickListener(){
						public void onClick(DialogInterface di,int w){
							ModificateActivity.skins.remove(key);
							getListView().setAdapter(new InternalListAdapter());
						}
					}).
					setNeutralButton(R.string.select,new DialogInterface.OnClickListener(){
						public void onClick(DialogInterface di,int w){
							Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
							intent.setType("image/png");
							changeTmp=key;
							startActivityForResult(intent, 123);
						}
					}).
					setNegativeButton(android.R.string.no,new DialogInterface.OnClickListener(){
						public void onClick(DialogInterface di,int w){}
					}).
					show();
			}
		});
	}
	private class InternalListAdapter extends ArrayAdapter<Map.Entry<String,URI>>{
		public InternalListAdapter(){
			super(ChangingListEditor.this,0,getList());
		}
		@Override
		public View getView(int position, View convertView, ViewGroup parent)
		{
			// TODO: Implement this method
			if(convertView!=null)return convertView;
			convertView=getLayoutInflater().inflate(R.layout.chglistcomponent,null);
			convertView.setTag(getItem(position));
			TextView mimg=(TextView)convertView.findViewById(R.id.mobimgname);
			mimg.setText(getItem(position).getKey());
			TextView name=(TextView)convertView.findViewById(R.id.fname);
			name.setText(getItem(position).getValue().toString());
			return convertView;
		}
	}
	private List<Map.Entry<String,URI>> getList(){
		List<Map.Entry<String,URI>> result=new ArrayList<Map.Entry<String,URI>>();
		for(Map.Entry<String,URI> o:ModificateActivity.skins.entrySet())
			result.add(o);
		return result;
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		// TODO: Implement this method
		super.onActivityResult(requestCode, resultCode, data);
		switch(requestCode){
			case 123:
				if(resultCode==RESULT_OK){
					try{
						ModificateActivity.skins.put(changeTmp, new URI(data.getDataString()));
					}catch (URISyntaxException e){

					}
					getListView().setAdapter(new InternalListAdapter());
				}
				changeTmp=null;
				break;
		}
	}
}
