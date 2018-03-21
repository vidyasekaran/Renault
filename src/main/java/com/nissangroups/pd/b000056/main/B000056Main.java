/**
 *System Name :Post Dragon
 * Sub system Name :Batch 
 * Function ID:PST-DRG-B000056
 * Module :CM Common
 * Process Outline :Main class which will call Quartz Job to Run.
 * <Revision History>
 *  Date 			Name(Company Name) 					Description 
 *  ------------------------------ --------------------- ----------
 *  18-11-15		z015883(RNTBCI)						New Creation
 *        
 *  */


package com.nissangroups.pd.b000056.main;

import java.util.ArrayList;
import java.util.List;

import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

import com.nissangroups.pd.b000056.util.B000056Constants;
import com.nissangroups.pd.b000056.util.B000056TriggerCoreJob;
import com.nissangroups.pd.b000056.util.B000056TriggerScreenJob;
import com.nissangroups.pd.util.PDConstants;

/**
 * @author z015883
 * Class to execute Scheduler
 */
@Component
public class B000056Main {

	private static ApplicationContext context = null;
	private static final Log LOG = LogFactory.getLog
			(B000056Main.class);
	public static void main(String[] args) {
		// calling job
		System.setProperty(B000056Constants.LOGFILENAME, B000056Constants.BATCH_56);
		String[] batchConfig = {B000056Constants.B000056_XML };

		context = new ClassPathXmlApplicationContext(batchConfig);
		if(args.length>=B000056Constants.INT_ONE)
		{
			LOG.info("Batch Triggered from Screen 79");
			executeS079Job(args);
		}
		else if(args.length==0)
		{
			LOG.info("Batch 56 Triggered Internally");
			scheduleJob();
		}
		

	}

	/*use to execute jobs triggered from Screen 76
	*@param string
	*void
	*/
	public static void executeS079Job(String[] param) {
		try{
			B000056TriggerScreenJob b000056TriggerScreenJob=(B000056TriggerScreenJob) context.getBean("B000056TriggerScreenJob");
			for (String string : param) {
				String parameter[]=string.split(B000056Constants.COMMA_DELIMETER);
				long jobStrmSeqId=Integer.parseInt(parameter[0]);
				String porCd=parameter[1]; 
				LOG.info("Calling Execution From Screen-79 for porCd ["+porCd+"] and JobStremSeqId ["+jobStrmSeqId+"] combination");
				b000056TriggerScreenJob.executeFromS079(jobStrmSeqId,porCd);
				LOG.info("SCREEN-79 Job Execution for porCd ["+porCd+"] and JobStremSeqId ["+jobStrmSeqId+"] combination Completed");
			}
		}
		catch(Exception e)
		{
			LOG.info("Input Parameter is in invalid Format");
			LOG.error(e);
		}
		
		
	}

	//It is configuration method for job and its respective trigger
	public static void scheduleJob() {
		SchedulerFactory sf = new StdSchedulerFactory();
		Scheduler schedule;

		try {
			schedule = sf.getScheduler();

			JobDetail job = JobBuilder.newJob(B000056TriggerCoreJob.class)
					.withIdentity(B000056Constants.BATCH_JOB_NAME, B000056Constants.BATCH).build();

			List<String> stts = new ArrayList<String>();
			stts.add(B000056Constants.WAITING_FOR_FILE);
			job.getJobDataMap().put(B000056Constants.tmp_STATUS, stts);
			Trigger trigger = TriggerBuilder
					.newTrigger()
					.withIdentity(B000056Constants.BATCH_TRIGGER, B000056Constants.BATCH)
					.withSchedule(
							SimpleScheduleBuilder.repeatMinutelyForever(B000056Constants.INT_MINUTES).withRepeatCount(B000056Constants.INT_ZERO))
					.build();
			schedule.start();
			schedule.scheduleJob(job, trigger);
		} catch (SchedulerException e) {
			LOG.error(e);
			LOG.error(PDConstants.EXCEPTION+e.getMessage());
		}

	}
	
	
	
}

