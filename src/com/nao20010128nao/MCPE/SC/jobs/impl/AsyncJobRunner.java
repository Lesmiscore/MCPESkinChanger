package com.nao20010128nao.MCPE.SC.jobs.impl;
import com.nao20010128nao.MCPE.SC.jobs.*;
import com.nao20010128nao.MCPE.SC.jobs.JobRunner.*;
import java.util.*;

public class AsyncJobRunner extends JobRunner{
	Set<JobDataHandler> jdhs=new HashSet<>();
	Set<JobResultHandler> jrhs=new HashSet<>();
	Job[] jobs=new Job[0];
	@Override
	public void clearAllDataHandler() {
		// TODO: Implement this method
		jdhs.clear();
	}
	@Override
	public void clearAllResultHandler() {
		// TODO: Implement this method
		jrhs.clear();
	}
	@Override
	public void removeDataHandler(JobRunner.JobDataHandler handler) {
		// TODO: Implement this method
		jdhs.remove(handler);
	}
	@Override
	public void removeResultHandler(JobRunner.JobResultHandler handler) {
		// TODO: Implement this method
		jrhs.remove(handler);
	}
	@Override
	public void addResultHandlers(JobRunner.JobResultHandler[] handler) {
		// TODO: Implement this method
		for(JobRunner.JobResultHandler jrh:handler)
			jrhs.add(jrh);
	}
	@Override
	public void addDataHandlers(JobRunner.JobDataHandler[] handler) {
		// TODO: Implement this method
		for(JobRunner.JobDataHandler jdh:handler)
			jdhs.add(jdh);
	}
	@Override
	public void addDataHandlers(List<JobRunner.JobDataHandler> handler) {
		// TODO: Implement this method
		jdhs.addAll(handler);
	}
	@Override
	public void setJobs(Job[] jobs) {
		// TODO: Implement this method
		Job[] clone=new Job[jobs.length];
		System.arraycopy(jobs,0,clone,0,jobs.length);
		this.jobs=jobs;
	}

	@Override
	public void setAsync(boolean value) {
		// TODO: Implement this method
	}
	@Override
	public boolean isAsync() {
		// TODO: Implement this method
		return false;
	}
	@Override
	public JobRunner.JobDataHandler[] getDataHandlers() {
		// TODO: Implement this method
		return jdhs.toArray(new JobRunner.JobDataHandler[jdhs.size()]);
	}
	@Override
	public JobRunner.JobResultHandler[] getResultHandlers() {
		// TODO: Implement this method
		return jrhs.toArray(new JobRunner.JobResultHandler[jrhs.size()]);
	}
	@Override
	public void stopJobs() {
		// TODO: Implement this method
	}
	@Override
	public void addResultHandler(JobRunner.JobResultHandler handler) {
		// TODO: Implement this method
		jrhs.add(handler);
	}
	@Override
	public void addResultHandlers(List<JobRunner.JobResultHandler> handler) {
		// TODO: Implement this method
		jrhs.addAll(handler);
	}
	@Override
	public boolean canStopJobs() {
		// TODO: Implement this method
		return false;
	}
	@Override
	public void addDataHandler(JobRunner.JobDataHandler handler) {
		// TODO: Implement this method
		jdhs.add(handler);
	}
	@Override
	public void restartJobs() {
		// TODO: Implement this method
	}
	@Override
	public void runJobs() {
		// TODO: Implement this method
	}
}
