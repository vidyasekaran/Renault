package com.nissangroups.pd.b000061.util;

import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.InitializingBean;

public class B000061FormatRangeTasklet implements Tasklet, InitializingBean 
{

	@Override
	public void afterPropertiesSet() throws Exception 
	{

	}

	@Override
	public RepeatStatus execute(StepContribution contribution,
			ChunkContext chunkContext) throws Exception 
	{
		
		return null;
	}

}
