/*
 * System Name     :Post Dragon 
 * Sub system Name :Batch
 * Function ID     :PST-DRG-B000058
 * Module          :CM Common
 * Process Outline :Email repository which communicates with Database
 *
 * <Revision History>
 * Date       Name(Company Name)             Description 
 * ---------- ------------------------------ ---------------------
 * 17-12-15  	 z015883              		New Creation					
 */

package com.nissangroups.pd.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.nissangroups.pd.b000056.util.B000056Constants;
import com.nissangroups.pd.b000058.util.B000058Constants;
import com.nissangroups.pd.b000058.util.B000058QueryConstants;
import com.nissangroups.pd.util.CommonUtil;
import com.nissangroups.pd.util.PDConstants;
import com.nissangroups.pd.util.PDMessageConsants;

/**
 * @author z015883
 *This class is used as a Repository layer for batch 58 process
 */
public class EmailNotificationRepository {

	@PersistenceContext 
	private EntityManager entityManager;
	
	 
	private static final Log LOG = LogFactory.getLog(EmailNotificationRepository.class);
	
	/*
	 * below method is use to extract Destination_Cd based on Jobexecutionseqid(B58-P0001) to Trigger Email
	 */
	
	/*
	*@param jobExecSeqId
	*@param jobExecStts
	*@return boolean
	*/
	public boolean checkMailCndtn(long jobExecSeqId,String 	jobExecStts)
	{
		boolean check=false;
		try{
		Query query=entityManager.createNativeQuery(B000058QueryConstants.checkMailCndtn.toString());
		query.setParameter(PDConstants.JOB_EXEC_SEQ_ID, jobExecSeqId);
		query.setParameter(PDConstants.JOB_EXEC_STTS, jobExecStts);
		int count=(int) query.getSingleResult();
		if(count!=0){
			check=true;
			LOG.info("Mail Sending condition returns True");
		}
			
		else
			LOG.error(B000058Constants.M00003);
		}
		catch(Exception e)
		{
			LOG.info("Exception occured while retrieving MailSendingConditon...");
			LOG.error(PDConstants.EXCEPTION+e);

		}
		return check;
		
	}
	
	/*
	 * below method is to Extract details along with all mail ids to whom email has to send (P00002)
	 */
	
	/*
	*@param jobExecSeqId
	*@return List<Object[]>
	*/
	public List<Object[]> getEmailDtls(long jobExecSeqId)
	{
		LOG.info("trying to fetch Email Related Details");
		List<Object[]> result = null;
		try{
		Query query=entityManager.createNativeQuery(B000058QueryConstants.getEmailAddressToSend.toString());
		query.setParameter(PDConstants.JOB_EXEC_SEQ_ID, jobExecSeqId);
		result=query.getResultList();
		//object[] contains=MAIL_ADDR,POR_DESC,POR_CD,JOB_EXEC_STTS,JOBSTRM_DESC,JOB_DESC,UPDTD_DT,ST_DT,ST_TIME
		if(result!=null && result.size()!=0)
			LOG.info("Retrieved Email Addresses Successfully");
		else{
			LOG.info("Getting MailAddress Details returned Null(No Values) ");
			CommonUtil.logErrorMessage(PDMessageConsants.M00003, B000056Constants.CONSTANT_V2, new String[]{B000058Constants.B000058,B000058Constants.MAIL_ADDRESS});
			return null;
		}
		}
		
		catch(Exception e)
		{
			LOG.error(PDConstants.EXCEPTION+e);
			LOG.info("Exception occured while Getting Email Details ");
		}
		return result;
	}
	
	/*
	 * Below method is to get the "From Email Address(sender)"  
	 */
	
	/*
	*@param prmCd
	*@param key1
	*@return String
	*/
	public String getSenderEmail(String prmCd,String key1)
	{
		String sender=null;
		try
		{
		Query query=entityManager.createNativeQuery(B000058QueryConstants.getFrmEmailAddrss.toString());
		query.setParameter(B000058Constants.EMAIL, prmCd);
		query.setParameter(B000058Constants.DEFAULT_FROM_ADDRS, key1);
		sender=(String) query.getSingleResult();
		 
		if(sender!=null)
		{
			
			LOG.info("Sender Email address is "+sender);
		}
		else
		{
			LOG.info("No Sender Email found");
		}
		}
		catch(Exception e)
		{
			LOG.error(PDConstants.EXCEPTION+e);
			LOG.info("Failed(Exception) to get Sender Email Address");
		}
		return sender;
	}
}
