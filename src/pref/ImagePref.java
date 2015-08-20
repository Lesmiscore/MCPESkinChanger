package pref;
import android.util.*;
import android.content.*;
import android.view.*;
import com.nao20010128nao.MC_PE.SkinChanger.*;

public class ImagePref extends StartPref
{
	public ImagePref(Context c,AttributeSet attrs){
		super(c,attrs);
		setLayoutResource(R.layout.imagepref);
	}
/*
	@Override
	public View getView(View convertView, ViewGroup parent) {
		// TODO: Implement this method
		convertView=((LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.imagepref,null);
		convertView.findViewById(android.R.id.icon).setId(getIdWithPackage("com.android.internal","icon",android.R.id.icon));
		convertView.findViewById(android.R.id.title).setId(getIdWithPackage("com.android.internal","title",android.R.id.title));
		convertView.findViewById(android.R.id.summary).setId(getIdWithPackage("com.android.internal","summary",android.R.id.summary));
		return convertView;
	}
	private int getIdWithPackage(String pack,String name,int def){
		try {
			return Class.forName(pack + ".R$id").getField(name).getInt(null);
		} catch (ClassNotFoundException e) {
			
		} catch (NoSuchFieldException e) {
			
		} catch (IllegalAccessException e) {
			
		} catch (IllegalArgumentException e) {
			
		}
		return def;
	}*/
}
