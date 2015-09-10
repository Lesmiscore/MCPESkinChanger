package com.nao20010128nao.MCPE.SC.jobs;
import java.util.*;

public abstract class JobRunner{
	public void setJobs(List<Job> jobs){
		setJobs(jobs.toArray(new Job[jobs.size()]));
	}
	public abstract void setJobs(Job[] jobs);
	
	public abstract void addResultHandler(JobResultHandler handler);
	public abstract void addResultHandlers(JobResultHandler[] handler);
	public abstract void addResultHandlers(List<JobResultHandler> handler);
	public abstract void removeResultHandler(JobResultHandler handler);
	public abstract void clearAllResultHandler();
	public abstract JobResultHandler[] getResultHandlers();
	
	public abstract void addDataHandler(JobDataHandler handler);
	public abstract void addDataHandlers(JobDataHandler[] handler);
	public abstract void addDataHandlers(List<JobDataHandler> handler);
	public abstract void removeDataHandler(JobDataHandler handler);
	public abstract void clearAllDataHandler();
	public abstract JobDataHandler[] getDataHandlers();
	
	public abstract boolean isAsync();
	public abstract void setAsync(boolean value);
	public abstract void runJobs();
	public abstract boolean canStopJobs();
	public abstract void stopJobs();
	public abstract void restartJobs();
	
	public static abstract interface JobResultHandler{
		public abstract void onResult(JobResult result);
	}
	public static abstract interface JobDataHandler{
		public abstract void onData(Object data);
	}
	public static class JobResult{
		public final Object result;
		public final boolean jobsSuccess;
		public JobResult(Object res,boolean jobs){
			result=res;
			jobsSuccess=jobs;
		}
	}
}
