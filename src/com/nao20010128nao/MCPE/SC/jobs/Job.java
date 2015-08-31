package com.nao20010128nao.MCPE.SC.jobs;

public interface Job{
	/**
	@param jobData A JobArgument.
	@return is the job finished completely?
	*/
	public boolean run(JobArgument jobData);
}
