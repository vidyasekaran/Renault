/*
 * System Name     :Post Dragon 
 * Sub system Name :Batch
 * Function ID     :PST-DRG-B000058
 * Module          :CM Common
 * Process Outline :Main class to call EmailService
 *
 * <Revision History>
 * Date       Name(Company Name)             Description 
 * ---------- ------------------------------ ---------------------
 * @Date  	  @author(RNTBCI)               New Creation
 * 17-12-15		z015883						Execute B000058	
 */
package com.nissangroups.pd.b000058.main;
 
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.nissangroups.pd.b000058.util.B000058Constants;
import com.nissangroups.pd.util.CommonUtil;

/**
 * @author z015883
 *
 */
public class B000058Main {

	private static final Log LOG = LogFactory.getLog(B000058Main.class);
	 
	private static ApplicationContext applicationContext;
	/**
	 * @param args
	 * @return void
	 */
	public static void main(String[] args) {
		System.setProperty(B000058Constants.LOGFILENAME, B000058Constants.B000058);
		String batchConfig[]={ B000058Constants.XML_FILE };
		applicationContext=new ClassPathXmlApplicationContext(batchConfig);
		 
		EmailService emailService=(EmailService) applicationContext.getBean(B000058Constants.EMAIL_SERVICE);
		if(args.length==B000058Constants.THREE && args!=null)
		{
		String jobStatus=args[0];
		String jobSeqId=args[1];
		String jobExecSeqId=args[2];
		String result=emailService.sendEmailNotice(jobStatus, jobSeqId,jobExecSeqId);
		LOG.info("Email Result is "+result);
		}
		else
		{
			LOG.error("Three Input Parameters JobStatus,JobSeqId and JobExecSeqId are required");
			CommonUtil.stopBatch();
		}

	}

}
