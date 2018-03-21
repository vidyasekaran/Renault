/*
 * System Name     : Post Dragon 
 * Sub system Name : I Interface
 * Function ID     : PST_DRG_I000091
 * Module          : OR Ordering
 * Process Outline : Send _Weekly_OCF_to_NSC(Standard_layout)
 *
 * <Revision History>
 * Date       Name(Company Name)             Description 
 * ---------- ------------------------------ ---------------------
 * @Date      z016127(RNTBCI)               New Creation
 *
 */
package com.nissangroups.pd.i000091.processor;

import static com.nissangroups.pd.util.PDConstants.DOLLAR;
import static com.nissangroups.pd.util.PDConstants.INSIDE_METHOD;
import static com.nissangroups.pd.util.PDConstants.PERSISTENCE_NAME;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.job.flow.FlowExecutionStatus;
import org.springframework.batch.core.job.flow.JobExecutionDecider;
import org.springframework.beans.factory.annotation.Autowired;

import com.nissangroups.pd.i000091.util.I000091Constants;
import com.nissangroups.pd.i000091.util.I000091QueryConstants;
import com.nissangroups.pd.util.IFConstants;
import com.nissangroups.pd.util.IfCommonUtil;
import com.nissangroups.pd.util.PDConstants;
import com.nissangroups.pd.util.PDMessageConsants;

/**
 * This Class is used to decide the execution of steps based on the condition.
 * The return value will be used as a status to determine the next step in the job. 
 * 
 * @author z016127
 *
 */
public class I000091Decider implements JobExecutionDecider {

	/**
	 * Common utility service bean injection
	 */
	@Autowired(required = false)
	IfCommonUtil commonutility;

	/* Constant LOG */
	private static final Log LOG = LogFactory
			.getLog(I000091Decider.class.getName());
	
	/** Variable entity manager. */
	@PersistenceContext(name = PERSISTENCE_NAME)
	private EntityManager entityManager;

	/**
	 * This method is used to decide which step to execute next based on the Input parameter
	 * If return value is dataPrcsFailed it goes to step Fail else if Completed it goes to Step2
	 * 
	 * @param jobExecution
	 *            JobExecution object
	 * @param stepExecution
	 *            StepExecution object
	 * @return FlowExecutionStatus object
	 */
	@Override
	public FlowExecutionStatus decide(JobExecution jobExecution,
			StepExecution stepExecution) {

		LOG.info( DOLLAR + INSIDE_METHOD + DOLLAR);
		/**Variable Por code */
		String porCd = stepExecution.getJobExecution().getJobParameters().getString(I000091Constants.PORCD);
		String ordrTkeBseMnth = "";
		
		Query query = entityManager
					.createNativeQuery(I000091QueryConstants.getOrderTakeBasePeriod
							.toString());
				query.setParameter(IFConstants.POR_CD, ("*").equals(porCd)? "" :porCd.trim());
				List selectResultSet = query.getResultList();
				if(selectResultSet != null && !(selectResultSet.isEmpty()) && (String)selectResultSet.get(0) != null){
					ordrTkeBseMnth = (String)selectResultSet.get(0);
					stepExecution.getJobExecution().getExecutionContext()
					.put(I000091Constants.ORDR_TAK_BSE_MNTH, ordrTkeBseMnth);
					return new FlowExecutionStatus(I000091Constants.COMPLETED);
				}else{
					
					commonutility.setStatus(PDConstants.INTERFACE_UNPROCESSED_STATUS);
					commonutility.setRemarks(PDMessageConsants.M00354.replace(PDConstants.ERROR_MESSAGE_1, PDConstants.EXTRACT_PORCD + " "+porCd)
	 								.replace(PDConstants.ERROR_MESSAGE_2, PDConstants.WEEKLY_ORDER_TAKE_BASE_PERIOD_MST));
					LOG.error(PDMessageConsants.M00354.replace(PDConstants.ERROR_MESSAGE_1, PDConstants.EXTRACT_PORCD + " "+porCd)
							 		.replace(PDConstants.ERROR_MESSAGE_2, PDConstants.WEEKLY_ORDER_TAKE_BASE_PERIOD_MST));
					return new FlowExecutionStatus(I000091Constants.NO_ORDR_TAK_BSE_PRD);
				}
		}

}
