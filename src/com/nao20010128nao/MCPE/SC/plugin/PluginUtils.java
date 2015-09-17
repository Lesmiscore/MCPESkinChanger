package com.nao20010128nao.MCPE.SC.plugin;
import java.util.*;
import android.content.*;
import java.io.*;
import android.util.*;

public class PluginUtils
{
	public static DiffMap<String,byte[]> getMapFromIntent(Intent intent)throws IOException{
		Map<String,byte[]> base=new HashMap<>();
		byte[] data=Base64.decode(intent.getStringExtra("map"),Base64.NO_WRAP);
		ByteArrayInputStream bais=new ByteArrayInputStream(data);
		DataInputStream dis=new DataInputStream(bais);
		int size=dis.readInt();
		for(int i=0;i<size;i++){
			String mapKey=dis.readUTF();
			int aSize=dis.readShort();
			byte[] arr=new byte[aSize];
			dis.readFully(arr);
			base.put(mapKey,arr);
		}
		return new DiffMap<String,byte[]>(base);
	}
	public static void putDiffIntoIntent(DiffMap<String,byte[]> diffMap,Intent intent)throws IOException{
		ByteArrayOutputStream baos=new ByteArrayOutputStream();
		Base64OutputStream bos=new Base64OutputStream(baos,Base64.NO_WRAP);
		DataOutputStream dos=new DataOutputStream(bos);
		dos.writeInt(diffMap.getRemoves().size());
		for(String rem:diffMap.getRemoves()){
			dos.writeUTF(rem);
		}
		dos.writeInt(diffMap.getAdds().size());
		for(Map.Entry<String,byte[]> entry:diffMap.getAdds().entrySet()){
			dos.writeUTF(entry.getKey());
			dos.writeInt(entry.getValue().length);
			dos.write(entry.getValue());
		}
		dos.flush();
		intent.putExtra("mapResult",new String(baos.toByteArray()));
	}
}
