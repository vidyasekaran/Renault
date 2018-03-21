/*
 * System Name       : Post Dragon 
 * Sub system Name   : I Interface
 * Function ID       : PST_DRG_I000103
 * Module            : CM Common		
 * Process Outline   : Interface for Receive User Master from SAP															
 *
 * <Revision History>
 * Date       Name(Company Name)             Description 
 * ---------- ------------------------------ ---------------------
 * 11-01-2016  	  z016127(RNTBCI)               Initial Version
 *
 */
package com.nissangroups.pd.i000103.processor;

import static com.nissangroups.pd.util.PDConstants.DOLLAR;
import static com.nissangroups.pd.util.PDConstants.INSIDE_METHOD;
import static com.nissangroups.pd.util.PDConstants.PERSISTENCE_NAME;

import java.util.ArrayList;
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

import com.nissangroups.pd.i000103.output.I000103OutputBean;
import com.nissangroups.pd.i000103.process.DataValidationProcess;
import com.nissangroups.pd.i000103.process.I000103CommonUtil;
import com.nissangroups.pd.util.CommonUtil;
import com.nissangroups.pd.util.IFConstants;
import com.nissangroups.pd.util.IfCommonUtil;
import com.nissangroups.pd.util.PDConstants;
import com.nissangroups.pd.util.PDMessageConsants;

/**
 * This Class I000103Decider is used to decide the execution of steps based on the condition.
 * The return value will be used as a status to determine the next step in the job.
 * 
 * @author z016127
 *
 */
public class I000103Decider implements JobExecutionDecider {

	/** Common utility service bean injection */
	@Autowired(required = false)
	IfCommonUtil commonutility;

	/** DataValidationProcess service bean injection */
	@Autowired(required = false)
	private DataValidationProcess dataValidationProcess;
	
	/** I000103CommonUtil service bean injection */
	@Autowired(required = false)
	private I000103CommonUtil commonUtil;
	
	/** Variable entity manager. */
	@PersistenceContext(unitName = PERSISTENCE_NAME)
	private EntityManager entityManagerFactory;
	
	/* Constant LOG */
	private static final Log LOG = LogFactory
			.getLog(I000103Decider.class.getName());

	/**
	 * P0001 Method to decide if there is any error in the extracted records,if not then the control will be moved into P0004 process.
	 * If return value is Fail it goes to step Fail else if Success it goes to Step2
	 * 
	 * @param jobExecution
	 *            JobExecution object
	 * @param stepExecution
	 *            StepExecution object
	 * @return FlowExecutionStatus object
	 */
	@Override
	public FlowExecutionStatus decide(JobExecution jobExecution,
			StepExecution stepExecution) 
	{

		LOG.info( DOLLAR + INSIDE_METHOD + DOLLAR);
		String queryString = (String) jobExecution.getExecutionContext().get("dynaQuery");
		String ifFileId = jobExecution.getJobParameters().getString(
				IFConstants.INTERFACE_FILE_ID);
		boolean valid =  true;
		List<I000103OutputBean> outputBeanList = new ArrayList<I000103OutputBean>();
		
			Query query = entityManagerFactory
					.createNativeQuery(queryString);
				List<Object[]> selectResultSet = query.getResultList();
				
				if(selectResultSet != null && !(selectResultSet.isEmpty()))
				{
					// set the extracted value to the bean
					outputBeanList =getOutputBean(selectResultSet);
					
					commonUtil.executeProcess(outputBeanList, ifFileId);
					//Check if there is any error in the extracted records	
					valid = dataValidationProcess.executeProcess(outputBeanList, ifFileId);
					if(valid)
					{
						return new FlowExecutionStatus("Success");
					}
					else
					{
						commonutility.setStatus(PDConstants.INTERFACE_FAILURE_STATUS);
						commonutility.setRemarks(PDMessageConsants.M00076.replace(PDConstants.ERROR_MESSAGE_1, 
													PDConstants.INTERFACE_FILE_ID + ifFileId));
						LOG.info(PDMessageConsants.M00076.replace(PDConstants.ERROR_MESSAGE_1, 
																	PDConstants.INTERFACE_FILE_ID + ifFileId));
						return new FlowExecutionStatus("Fail");
					}
				}
				else
				{
					commonutility.setStatus(PDConstants.INTERFACE_UNPROCESSED_STATUS);
					commonutility.setRemarks(PDMessageConsants.M00003);
					LOG.info(PDMessageConsants.M00003);
					return new FlowExecutionStatus("Fail");
			}
	}
	
	/**
	 * Method to set the list of objects to I000103OutputBean list
	 * 
	 * @param selectResultSet
	 * 						list of object
	 * @return list of I000103OutputBean
	 */
	public List<I000103OutputBean> getOutputBean(List<Object[]> selectResultSet){
		List<I000103OutputBean> outputBeanList = new ArrayList<I000103OutputBean>();
		for(Object[] item : selectResultSet){
			I000103OutputBean outputBean= new I000103OutputBean();
			outputBean.setSeqNo(CommonUtil.convertObjectToString(item[1]));//Common Interface Data Seq Number
			outputBean.setCol1(CommonUtil.convertObjectToString(item[2]));//Common Interface Data Column 1
			outputBean.setCol2(CommonUtil.convertObjectToString(item[3]));//Common Interface Data Column 2
			outputBean.setCol3(CommonUtil.convertObjectToString(item[4]));//Common Interface Data Column 3
			outputBean.setCol4(CommonUtil.convertObjectToString(item[5]));//Common Interface Data Column 4
			outputBean.setCol5(CommonUtil.convertObjectToString(item[6]));//Common Interface Data Column 5
			outputBean.setCol6(CommonUtil.convertObjectToString(item[7]));//Common Interface Data Column 6
			outputBeanList.add(outputBean);
		}
		return outputBeanList;
	}
}
