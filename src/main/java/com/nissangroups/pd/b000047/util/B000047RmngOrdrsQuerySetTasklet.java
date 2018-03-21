/*
 * System Name     :Post Dragon 
 * Sub system Name :B Batch 
 * Function ID     :PST-DRG-B000047
 * Module          :Ordering 	
 * Process Outline :VIN Allocation to Logical Pipeline
 *
 * <Revision History>
 * Date       Name(Company Name)             Description 
 * ---------- ------------------------------ ---------------------
 * 03-02-2016  	  z015847(RNTBCI)               New Creation
 *
 */
package com.nissangroups.pd.b000047.util;

import static com.nissangroups.pd.util.PDConstants.DOLLAR;
import static com.nissangroups.pd.util.PDConstants.INSIDE_METHOD;
import static com.nissangroups.pd.util.PDConstants.OUTSIDE_METHOD;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;

import com.nissangroups.pd.b000047.output.B000047QueryOutputVO;
import com.nissangroups.pd.model.MstPrmtr;
import com.nissangroups.pd.util.PDConstants;

/**
 * This Class B000047RmngOrdrsQuerySetTasklet is Create the custom query and store in ChunkContext for B000047 reader to do further processing.
 * 
 * @author z016127
 *
 */
public class B000047RmngOrdrsQuerySetTasklet implements Tasklet, InitializingBean {

	/* Constant LOG */
	private static final Log LOG = LogFactory
			.getLog(B000047RmngOrdrsQuerySetTasklet.class.getName());
	
	/** Variable por cd. */
	String porCd = null;
	
	/** B000047QueryOutputVO bean injection */
	@Autowired(required = false)
	B000047QueryOutputVO queryOutputVO;
	
	/** B000047CommonUtiltyService bean injection */
	@Autowired(required = false)
	private B000047CommonUtiltyService commonUtil;
	
	/** Variable prmtr mwd calculation value */
	String prmtrMwdCalculatnVal ="";
	
	/** Variable prmtr mwd calculation */
	StringBuilder prmtrMwdCalculatn = null;
	
	/**
	 * Indicates that a method declaration is intended to override a method
	 * declaration in a supertype.
	 */
	@Override
	public void afterPropertiesSet() throws Exception {
		// Do nothing
	}

	/**
	 * P0007 Create the custom query and store in ChunkContext
	 * 
	 * @param contribution
	 *            StepContribution object
	 * @param chunkContext
	 *            ChunkContext object
	 *            
	 * @return RepeatStatus object
	 */
	@Override
	public RepeatStatus execute(StepContribution contribution,
			ChunkContext chunkContext) throws Exception {

		LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR);
		// Getting job parameter
		JobParameters jobParameters = chunkContext.getStepContext()
				.getStepExecution().getJobParameters();
	
		porCd =  jobParameters.getString(PDConstants.PRMTR_PORCD);
		
		/** Process Id - P0007 */
		prmtrMwdCalculatn();
		String remngOrdrsQuery = fetchRmngOrdrs(porCd);
		
		chunkContext.getStepContext().getStepExecution().getJobExecution()
			.getExecutionContext().put(B000047Constants.CONSTANT_DYNAQRY, remngOrdrsQuery);
		
		LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);

		return RepeatStatus.FINISHED;
	}
	
	/**
	 * P0007 Query to extract Records from Logical Pipeline Trn where VIN_NO not allocated
	 *  
	 * @param porCd
	 * 				string
	 * 
	 * @return Logical Pipeline extraction Query String
	 */
	public String fetchRmngOrdrs(String porCd){
	 		
	 		StringBuilder queryString = new StringBuilder()
				 		.append(B000047QueryConstants.extctRmngOrdrs)
				 		.append(PDConstants.SINGLE_QUOTE)
				 		.append(porCd)
				 		.append(PDConstants.SINGLE_QUOTE)
				 		.append(prmtrMwdCalculatn)
				 		.append(B000047Constants.LESS)
				 		.append(prmtrMwdCalculatnVal);
	 		
	 		LOG.info("Logical Pipeline Extraction Query String : "+ queryString);
	 		
	 		return queryString.toString();
	 	}
	
	/**
	 * P0007 Method to generate query to extract Records from Logical Pipeline Trn where VIN_NO not allocated
	 */
	private void prmtrMwdCalculatn(){
		//P0006.5
		String calculatdDate = commonUtil.cnvrtOflnDateToYYYYMMWD();
		prmtrMwdCalculatnVal = calculatdDate.substring(0, 6);
		//P0007
		prmtrMwdCalculatn = new StringBuilder()
				.append(B000047QueryConstants.extctRmngOrdrsWhrCndtn1);
		
		List<MstPrmtr> mstPrmtrLst = queryOutputVO.getMstPrmtrLst();
		
		for(MstPrmtr prmtr:mstPrmtrLst){
			 if(prmtr.getId().getKey2().equalsIgnoreCase(B000047Constants.WEEK) && prmtr.getVal1().equalsIgnoreCase(B000047Constants.YES)){
				 
				 prmtrMwdCalculatn.append(B000047QueryConstants.extctRmngOrdrsWhrCndtn2);
				 prmtrMwdCalculatnVal = prmtrMwdCalculatnVal + calculatdDate.substring(6, 7);
				 
			}else if(prmtr.getId().getKey2().equalsIgnoreCase(B000047Constants.DAY) && prmtr.getVal1().equalsIgnoreCase(B000047Constants.YES)){
				
				prmtrMwdCalculatn.append(B000047QueryConstants.extctRmngOrdrsWhrCndtn3);
				 prmtrMwdCalculatnVal = prmtrMwdCalculatnVal + calculatdDate.substring(7, 8);
			}
		}
	}
	
}
