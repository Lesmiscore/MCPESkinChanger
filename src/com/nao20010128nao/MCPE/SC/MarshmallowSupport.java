package com.nao20010128nao.MCPE.SC;
import android.os.*;
import android.content.*;
import android.content.pm.*;
import android.content.pm.PackageManager.*;
import android.app.*;
import com.nao20010128nao.MC_PE.SkinChanger.*;
import android.widget.*;
import android.view.*;
import java.util.*;

public class MarshmallowSupport
{
	private static String[] require;
	private static Runnable cont;
	public static void alertPerms(final Context ctx,List<String> required,final Runnable toContinue){
		if(Build.VERSION.SDK_INT<23){
			return;//Not marshmallow
		}
		final PackageManager pm=ctx.getPackageManager();
		if(required==null){
			required=Arrays.asList(Utils.getPermissions(ctx));
		}
		if(checkDeprivated(ctx,required)){
			final List<String> deprivated=new ArrayList<>(required);
			listDeprivated(ctx,deprivated);
			new AlertDialog.Builder(ctx)
				.setTitle(R.string.permNotFull)
				.setAdapter(
				new ArrayAdapter<String>(ctx,0,deprivated){
					public View getView(int pos,View conv,ViewGroup parent){
						if(conv==null){
							conv=((LayoutInflater)ctx.getSystemService(Activity.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.permnotfull,null);
						}
						String perm=getItem(pos);
						try {
							PermissionInfo pinfo = pm.getPermissionInfo(perm, 0);
							((TextView)conv.findViewById(R.id.permName)).setText(perm);//pinfo.loadDescription(pm).toString());
							((ImageView)conv.findViewById(R.id.permImage)).setImageDrawable(pinfo.loadLogo(pm));
						} catch (Throwable e) {
							((TextView)conv.findViewById(R.id.permName)).setText("Error");
						}
						return conv;
					}
				},
				new DialogInterface.OnClickListener(){
					public void onClick(DialogInterface di,int p2){
					}
				})
				.setPositiveButton(R.string.next,new DialogInterface.OnClickListener(){
					public void onClick(DialogInterface di,int p2){
						require=deprivated.toArray(new String[deprivated.size()]);
						cont=toContinue;
						ctx.startActivity(new Intent(ctx,PermActivity.class));
						di.cancel();
					}
				})
				.show();
		}else{
			cont.run();
		}
	}
	private static boolean checkDeprivated(Context ctx,List<String> check) {
		if(check==null){
			check=Arrays.asList(Utils.getPermissions(ctx));
		}
		for (String s:Utils.getPermissions(ctx)) {
			if (ctx.checkSelfPermission(s) == PackageManager.PERMISSION_DENIED) {
				return true;
			}
		}
		return false;
	}
	private static void listDeprivated(Context ctx,List<String> check) {
		for (String s:Utils.getPermissions(ctx)) {
			if (ctx.checkSelfPermission(s) != PackageManager.PERMISSION_DENIED) {
				check.remove(s);
			}
		}
	}
	public static class PermActivity extends Activity {
		@Override
		protected void onCreate(Bundle savedInstanceState) {
			// TODO: Implement this method
			super.onCreate(savedInstanceState);
			requestPermissions(require,0);
			setContentView(new LinearLayout(this));
		}

		@Override
		public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
			// TODO: Implement this method
			super.onRequestPermissionsResult(requestCode, permissions, grantResults);
			for(int i:grantResults){
				if(i==PackageManager.PERMISSION_DENIED){
					//Retry
					requestPermissions(require,0);
					return;
				}
			}
			try{
				cont.run();
			}finally{
				cont=null;
				require=null;
				finish();
			}
		}

		@Override
		public void onBackPressed() {
			// TODO: Implement this method
			super.onBackPressed();
			finish();
		}
		
	}
}
