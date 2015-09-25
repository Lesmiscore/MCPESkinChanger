package com.nao20010128nao.MCPE.SC.view;
import android.widget.*;
import android.content.*;
import android.graphics.*;
import android.util.*;

public class FillProgressBar extends ProgressBar
{
	Paint back,prog,secd;
	int second,secondMax;
	public FillProgressBar(Context c,AttributeSet as){
		super(c,as);
		this.back = new Paint();
		this.prog = new Paint();
		this.secd = new Paint();
	}
	@Override
	protected void onDraw(Canvas canvas) {
		// TODO: Implement this method
		double first;
		canvas.drawRect(getLeft(),getTop(),getRight(),getBottom(),back);
		canvas.drawRect(getLeft(),getTop(),getLeft()+(float)(first=((getRight()-getLeft())*getProgressRatio())),getBottom(),prog);
		canvas.drawRect(getLeft(),getTop(),getLeft()+(float)(first*getProgressRatio2()),getBottom(),secd);
	}
	public void setBackgroundColor(int back) {
		this.back.setColor(back);
		invalidate();
	}
	public void setProgressColor(int prog) {
		this.prog.setColor(prog);
		invalidate();
	}
	public void setSecondProgressColor(int prog) {
		this.secd.setColor(prog);
		invalidate();
	}
	private double getProgressRatio(){
		if(getProgress()==0)return 0;
		return getProgress()/getMax();
	}
	private double getProgressRatio2(){
		if(getSecondProgress()==0)return 0;
		return getSecondProgress()/getSecondMax();
	}
	public void setSecondProgress(int second) {
		this.second = second;
		invalidate();
	}
	public int getSecondProgress() {
		return second;
	}
	public void setSecondMax(int secondMax) {
		this.secondMax = secondMax;
		invalidate();
	}
	public int getSecondMax() {
		return secondMax;
	}
}
