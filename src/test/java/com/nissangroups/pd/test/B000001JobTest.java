package com.nissangroups.pd.test;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


/**
 * Junit test case for B000001 -Job
 * 
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
		"file:src/main/resources/B000001/B000001_Batch_Config.xml",
		"classpath:spring/batch/config/test-context.xml" })
public class B000001JobTest {

	@Autowired
	private JobLauncherTestUtils jobLauncherTestUtils;	

	@Test
	public void batch1() throws Exception {

		// testing a job
		JobExecution jobExecution = jobLauncherTestUtils.launchJob();

		// Testing a individual step
		// JobExecution jobExecution =jobLauncherTestUtils.launchStep("step1");

		assertEquals(BatchStatus.COMPLETED, jobExecution.getStatus());

	}

}
