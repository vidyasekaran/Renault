package com.nissangroups.pd.test;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


/**
 * Junit test case for B000002 -Job
 * 
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
		"file:src/main/resources/B000002/B000002_Batch_Config.xml",
		"classpath:spring/batch/config/test-context.xml" })
public class B000002JobTest {
	
	@Autowired
	private JobLauncherTestUtils jobLauncherTestUtils;
	
	@Test
	public void batch2() throws Exception {

		String por = "07";

		JobParameters jobParam = new JobParametersBuilder().addString("porCd", por).toJobParameters();

		JobExecution jobExecution = jobLauncherTestUtils.launchJob(jobParam);

		assertEquals(BatchStatus.COMPLETED, jobExecution.getStatus());

	}

}
