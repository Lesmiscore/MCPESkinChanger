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

	@Override
	public View getView(View convertView, ViewGroup parent) {
		// TODO: Implement this method
		return ((LayoutInflater)getContext()
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE))
			.inflate(R.layout.imagepref,null);
	}
}
