package com.nao20010128nao.MCPE.SC;
import java.util.*;

public class MyRandom
{
	public static final MyRandom staticInstance=new MyRandom();
	int tmp;
	Queue<Integer> buf=new LinkedList<>();
	Random r=new Random();
	public MyRandom(){
		getMore();
		next();
		next();
		next();
		next();
	}
	public byte next(){
		tmp=poll()^(tmp<<8);
		giveValue(tmp);
		return (byte)(tmp&0xff);
	}
	public void giveValue(byte value){
		int tmp=value<<24|value<<16|value<<8|value;
		buf.offer(tmp);
	}
	public void giveValue(short value){
		int tmp=value<<16|value<<8|value;
		buf.offer(tmp);
		giveValue((byte)(value&0xff));
		giveValue((byte)((value>>8)&0xff));
	}
	public void giveValue(int value){
		buf.offer(value);
		giveValue((byte)(value&0xff));
		giveValue((byte)((value>>8)&0xff));
		giveValue((byte)((value>>16)&0xff));
		giveValue((byte)((value>>24)&0xff));
		giveValue((short)(value&0xffff));
		giveValue((short)((value>>16)&0xffff));
	}
	public void giveValue(long value){
		buf.offer((int)(value&0xffffffff));
		buf.offer((int)((value>>32)&0xffffffff));
		giveValue((byte)(value&0xff));
		giveValue((byte)((value>>8)&0xff));
		giveValue((byte)((value>>16)&0xff));
		giveValue((byte)((value>>24)&0xff));
		giveValue((byte)((value>>32)&0xff));
		giveValue((byte)((value>>40)&0xff));
		giveValue((byte)((value>>48)&0xff));
		giveValue((byte)((value>>56)&0xff));
		giveValue((short)(value&0xffff));
		giveValue((short)((value>>16)&0xffff));
		giveValue((short)((value>>32)&0xffff));
		giveValue((short)((value>>48)&0xffff));
	}
	private int poll(){
		if(buf.size()==0)getMore();
		return buf.poll();
	}
	private void getMore(){
		giveValue(System.currentTimeMillis());
		giveValue(r.nextLong());
	}
}
