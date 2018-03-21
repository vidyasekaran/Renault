/*
 * System Name     :Post Dragon 
 * Sub system Name :Batch
 * Function ID     :PST-DRG-B000058
 * Module          :CM Common
 * Process Outline :To send Email
 *
 * <Revision History>
 * Date       Name(Company Name)             Description 
 * ---------- ------------------------------ ---------------------
 * @Date  	  @author(RNTBCI)               New Creation
 * 17-12-15		z015883						Execute B000058	
 */
package com.nissangroups.pd.b000058.main;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.nissangroups.pd.b000056.util.B000056Constants;
import com.nissangroups.pd.b000058.util.B000058Constants;
import com.nissangroups.pd.b000056.util.ExecuteFile;
import com.nissangroups.pd.repository.EmailNotificationRepository;
import com.nissangroups.pd.util.CommonUtil;
import com.nissangroups.pd.util.PDConstants;

/**
 * @author z015883
 *Email service is used to send email notifications
 */

public class EmailService {

	@Autowired(required=false)
	private EmailNotificationRepository emailNotificationRepository;
	@Autowired
	CommonUtil commonUtil;
	private static final Log LOG = LogFactory.getLog(EmailService.class);
	
	/*
	 * Method to Execute the Batch 58(email Notifications)
	 */
	
	/*
	*@param jobSeqId
	*@param stts
	*@param latestLogFilePath
	*@param jobExecSeqId
	*@return String
	*/
	public String sendEmail(long jobSeqId, int stts,String latestLogFilePath, long jobExecSeqId)
	{
		String result="";
		String jobExecStts="";
		if(stts==B000058Constants.ZERO)
			jobExecStts=B000058Constants.SUCCESS;
		else
			jobExecStts=B000058Constants.FAILURE;
		
		try{
			boolean check=emailNotificationRepository.checkMailCndtn(jobSeqId, jobExecStts);//P0001
			if(!check)
			{
				 LOG.info("No Data Found to send Email");
				 return B000058Constants.NO_DATA;
			}
			String sender=emailNotificationRepository.getSenderEmail(B000058Constants.EMAIL,B000058Constants.DEFAULT_FROM_ADDRS );//P0003.1
				 List<Object[]> emailDtls=emailNotificationRepository.getEmailDtls(jobExecSeqId);
				 //object[] contains=porCd,mailAddrs,jobStts,jobStrmDesc,jobDesc,updtdDt,sender
				  Map<String, String> param=new HashMap<>();
				 if(emailDtls!=null)
				 {
					 for (Object[] ed : emailDtls) {
						//here has to decide how to send mail
						 param.put(B000058Constants.POR_CD, ed[2].toString());
						 param.put(B000058Constants.MAIL_ADDRS, ed[0].toString());
						 param.put(B000058Constants.JOB_STTS, ed[3].toString());
						 param.put(B000058Constants.JOB_STRM_DESC, ed[4].toString());
						 param.put(B000058Constants.JOB_DESC, ed[5].toString());
						 param.put(B000058Constants.UPDTD_DT, ed[6].toString());
						 param.put(B000058Constants.SENDER, sender);
						result= triggerEmail(param);
					}
				 }
		}
		catch(Exception e)
		{
			result=B000058Constants.NO_DATA;
			LOG.error(e);
		}
		return result;
	}

	/** This method will format the required data into mail message and connects with Transport class to send mail
	 * @param Map<String String> param
	 * @return string
	 */
	private String triggerEmail(Map<String, String> param) {
		String result="";
		 String host=B000058Constants.HOST;
		 String from=param.get(B000058Constants.SENDER);
		 String to=param.get(B000058Constants.MAIL_ADDRS);
		 String body="<html><body><b><u><center>To Whom It May Concern</center></u></b><br><br>" 
		 		+ "POST-DRAGON system got <b>"+param.get("jobStts")+"</b> during Batch-56 processing.<br>" 
		 		+ "You can confirm details as follows. <br>" 
		 		
		 		+"<table><tbody><tr>"
		 		+ "<td>[Job Stream Name]</td><td>=<b>"+param.get(B000058Constants.JOB_STRM_DESC).trim()+"</b></td></tr>" 
		 		+ "<tr><td>[Job Name]</td><td>=<b>"+param.get(B000058Constants.JOB_DESC).trim()+"</b></td></tr>" 
		 		+ "<tr><td>[Status]</td><td>=<b>"+param.get(B000058Constants.JOB_STTS).trim()+"</b></td></tr>" 
		 		+ "<tr><td>[POR]</td><td>=<b>"+param.get(B000058Constants.POR_CD).trim()+"</b></td></tr>" 
		 		+ "<tr><td>[Date and Time]</td><td>=<b>"+param.get(B000058Constants.UPDTD_DT).trim()+"</b></td></tr>"
		 		+"</tbody></table><br>"
		 		+ "Note:You are expected to communicate with responsible person Incharge and resolve errors(If any) asap.<br>"
		 		+ "<br><br>Thanks<br>"
		 		+ "<b>Post-Dragon Team</b></body></html>";
		 														

		 Properties prop=new Properties();
		 prop.put(B000058Constants.SMTP_HOST, host);
		 prop.put(B000058Constants.SMTP_AUTH, false);
		 
		 Session session=Session.getDefaultInstance(prop,null);
		 session.setDebug(true);
		 LOG.info("Start Compsoing Mail");
		
		 try
		 {
			 MimeMessage message=new MimeMessage(session);
			 message.saveChanges();
			 message.setFrom(new InternetAddress(from));
			 message.addRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
			 message.setSubject(ExecuteFile.filename+B000058Constants.HIFEN+param.get(B000058Constants.JOB_STTS).trim()+B000058Constants.PD);
			 MimeBodyPart bodyPart=new MimeBodyPart();
			 MimeBodyPart attachLogFile=new MimeBodyPart();
			 Multipart multipart=new MimeMultipart();
			 bodyPart.setContent(body,"text/html");
			 multipart.addBodyPart(bodyPart);
			 if(ExecuteFile.exitcode==0 || ExecuteFile.exitcode==1)
			 {
				 if(B000056Constants.LATEST_LOG_FILE !="")
				 {
					 try{
					 attachLogFile.attachFile(B000056Constants.LATEST_LOG_FILE);
					 attachLogFile.setFileName(ExecuteFile.filename+"-LogFile");
					 multipart.addBodyPart(attachLogFile);
					 }
					 catch(Exception e)
					 {
						 LOG.error(e);
					 }
				 }
			 }
			 else
			 {		
				
				 LOG.info("Files cannot be attach as Job Terminated in an Abnormal way.");
			 }
			 message.setContent(multipart);
			 Transport.send(message);
			LOG.info("Mail Sent Successfully to ["+to+" ]");
			result=B000058Constants.SENT;
		 }
		 catch(Exception e)
		 {
			 LOG.info("Exception in Sending Mail");
			LOG.error(B000058Constants.M00314);
			result=B000058Constants.FAIL;
			LOG.error(e);
		 }
		 return result;
		
	}
	
	/*
	 * Method which may called by other than B000056 to send mail with different requirement
	 */
	 
	/**
	 * @params @param stts
	 * @params @param jobSeqId
	 * @params @param jobExecSeqId
	 * @return String
	 */
	public String sendEmailNotice(String stts, String jobSeqId,String jobExecSeqId)
	{
		String result="";
		String jobExecStts="";
		LOG.info("status "+stts);
		if(stts.equalsIgnoreCase(B000058Constants.ZERO_STRING))
			jobExecStts=B000058Constants.SUCCESS;
		else if(stts.equalsIgnoreCase(B000058Constants.ONE))
			jobExecStts=B000058Constants.FAILURE;
		else
			jobExecStts=B000058Constants.NA;
		try{
			long jobExecSId=Long.parseLong(jobExecSeqId);
			long jobId=Long.parseLong(jobSeqId);
			boolean check=emailNotificationRepository.checkMailCndtn(jobId, jobExecStts);//P0001
			if(!check)
			{
				 LOG.error(B000058Constants.M00003);
				 return B000058Constants.NO_DATA;
			}
			String sender=emailNotificationRepository.getSenderEmail(B000058Constants.EMAIL,B000058Constants.DEFAULT_FROM_ADDRS);//P0003.1
			if(sender!=null){	 
			List<Object[]> emailDtls=emailNotificationRepository.getEmailDtls(jobExecSId);
				  Map<String, String> param=new HashMap<>();
				  
				 if(emailDtls!=null)
				 {
					 for (Object[] ed : emailDtls) {
						//here has to decide how to send mail
						 
						 param.put(B000058Constants.POR_CD, ed[2].toString());
						 param.put(B000058Constants.MAIL_ADDRS, ed[0].toString());
						 param.put(B000058Constants.JOB_STTS, ed[3].toString());
						 param.put(B000058Constants.JOB_STRM_DESC, ed[4].toString());
						 param.put(B000058Constants.JOB_DESC, ed[5].toString());
						 param.put(B000058Constants.UPDTD_DT, ed[6].toString());
						 param.put(B000058Constants.SENDER, sender);
						result= triggerEmail(param);
					}
				 }
				 else
				 {
					 LOG.info("Cannot Send mail as there are no email address available for given input values ");
				 }
			}
			else
			{
				LOG.info("Message: Sender Emailaddress is not found in Database for given inputs");
			}
		}
		catch(Exception e)
		{
			result=B000058Constants.NO_DATA;
			LOG.info("Exception Occurred while Processing the Email Batch");
			LOG.error(e);
		}
		return result;
	}

}
