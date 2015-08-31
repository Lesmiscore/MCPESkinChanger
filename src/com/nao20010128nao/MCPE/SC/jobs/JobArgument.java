package com.nao20010128nao.MCPE.SC.jobs;
import android.os.*;

public final class JobArgument
{
	public final JobRunner currentRunner;
	public final Job nextJob;
	public final Object argument;
	private final Handler handler;
	public Object result;
	public JobArgument(JobRunner jbr,Job next,Object arg,Handler han){
		currentRunner=jbr;
		nextJob=next;
		argument=arg;
		handler=han;
	}
}
