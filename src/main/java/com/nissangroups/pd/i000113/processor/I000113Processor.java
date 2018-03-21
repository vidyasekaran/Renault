/*
 * System Name     : Post Dragon 
 * Sub system Name : I Interface
 * Function ID     : PST_DRG_I000113
 * Module          : CM COMMON
 * Process Outline : Logical Pipeline Update from SAP
 *
 * <Revision History>
 * Date       Name(Company Name)             Description 
 * ---------- ------------------------------ ---------------------
 * @Date      z015895(RNTBCI)               New Creation
 *
 */
package com.nissangroups.pd.i000113.processor;

import static com.nissangroups.pd.util.PDConstants.DOLLAR;
import static com.nissangroups.pd.util.PDConstants.FAILED;
import static com.nissangroups.pd.util.PDConstants.INSIDE_METHOD;
import static com.nissangroups.pd.util.PDConstants.OUTSIDE_METHOD;
import static com.nissangroups.pd.util.PDConstants.PERSISTENCE_NAME;
import static com.nissangroups.pd.util.PDConstants.READ_COUNT;
import static com.nissangroups.pd.util.PDConstants.READ_SKIPPED_COUNT;
import static com.nissangroups.pd.util.PDConstants.STAR;
import static com.nissangroups.pd.util.PDConstants.STEP_ID;
import static com.nissangroups.pd.util.PDConstants.WRITE_COUNT;
import static com.nissangroups.pd.util.PDConstants.WRITE_SKIPPED_COUNT;
import static com.nissangroups.pd.util.PDMessageConsants.M00003;
import static com.nissangroups.pd.util.PDMessageConsants.M00043;
import static com.nissangroups.pd.util.PDMessageConsants.M00076;
import static com.nissangroups.pd.util.PDMessageConsants.M00113;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.AfterStep;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;

import com.nissangroups.pd.i000113.bean.I000113InputParam;
import com.nissangroups.pd.i000113.output.I000113OutputBean;
import com.nissangroups.pd.i000113.util.I000113QueryConstants;
import com.nissangroups.pd.model.TrnLgclPpln;
import com.nissangroups.pd.util.IFConstants;
import com.nissangroups.pd.util.IfCommonUtil;
import com.nissangroups.pd.util.PDConstants;

/**
 *  This class I000113Processor is to process the extracted Common file header & common interface data 
 *  and insert the data into Logical pipeline transaction
 *
 * @author z015895
 */
public class I000113Processor implements
		ItemProcessor<I000113OutputBean, TrnLgclPpln> {

	/** Constant LOG. */
	private static final Log LOG = LogFactory.getLog
			(I000113Processor.class);

	/** Stores entity manager */
	@PersistenceContext(unitName = PERSISTENCE_NAME)
	private EntityManager entityManager;
   
	/** IfCommonUtil bean injection */
	@Autowired(required = false)
	IfCommonUtil commonutility;

	/** Variable Job Execution. */
	private JobExecution jobExec;

	/** Variable lgclPpln. */
	private TrnLgclPpln lgclPpln;
    
	/** Variable  I000113InputParam list */
	private I000113InputParam i000113InputParam;
    
	/**
	 * This method will be called just before each step execution
	 * Get stepExecution and assign into instance variable
	 * 
	 * @param stepExecution 
	 * 					the step execution
	 */
	@BeforeStep
	public void beforeStep(StepExecution stepExecution) {
		LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR);
		jobExec = stepExecution.getJobExecution();
		LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);
	}
	
	/** 
	 * This method is to process the extracted data from common interface data 
	 * and insert the list into I000113InputParam and TrnLgclPpln data.
	 * P0001, P0002
	 * 
	 * @param I000113OutputBean 
	 * 						the item
	 * @return the list of TrnLgclPpln
	 * 						the class
	 * @throws Exception the exception
	 */
	@Override
	public TrnLgclPpln process(I000113OutputBean item) throws Exception {

		LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR);

		String ifFileId = jobExec.getJobParameters().getString(
				IFConstants.INTERFACE_FILE_ID);
		
		long rowCount = commonutility.getRowCount() + 1;
        
		i000113InputParam = new I000113InputParam();
		i000113InputParam.setExtClr(item.getCol7());
		i000113InputParam.setIntClr(item.getCol8());
		i000113InputParam.setBuyerCd(item.getCol11());
		i000113InputParam.setSpecDestnCd(item.getCol12());
		i000113InputParam.setCarSrs(item.getCol4());
		i000113InputParam.setAdtnSpecCd(item.getCol9());
		i000113InputParam.setPckCd(item.getCol6());
		i000113InputParam.setPrdFamilyCd(item.getCol21());
		i000113InputParam.setApplMdlCd(item.getCol5());		

		lgclPpln = new TrnLgclPpln();
		lgclPpln.setVhclSeqId(item.getCol1().trim());
		lgclPpln.setPorCd(item.getCol2());
		lgclPpln.setProdPlntCd(item.getCol3());
		lgclPpln.setOfflnPlanDate(item.getCol10());
		lgclPpln.setLgclPplnStageCd(item.getCol13());
		lgclPpln.setSlsNoteNo(item.getCol14());
		lgclPpln.setExNo(item.getCol15());
		lgclPpln.setProdMnth(item.getCol16());
		lgclPpln.setPotCd(item.getCol17());
		lgclPpln.setProdOrdrNo(item.getCol18());
		lgclPpln.setOrdrDelFlag(item.getCol19());
		lgclPpln.setMsFxdFlag(item.getCol20());
		lgclPpln.setLineClass(item.getCol22());
		lgclPpln.setFrznTypeCd(item.getCol23());
		lgclPpln.setProdMthdCd(item.getCol24());
		lgclPpln.setVinNo(item.getCol25());
		lgclPpln.setOseiId(this.getOseiId());
		lgclPpln.setCrtdBy(ifFileId);
		lgclPpln.setUpdtdBy(ifFileId);

		commonutility.setRowCount(rowCount);
		LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);
		
		return lgclPpln;
	}
	
    /*
     * P0001 Extract OSEI ID from MST_OSEI table based on input parameters
     * 
     * @return seiId
     */
	@SuppressWarnings("unchecked")
	public String getOseiId() {
		LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR);
		String seiId = "";
		try {
			
			String oseiIdQuery = I000113QueryConstants.oseiIdQuery.toString();			
			
			oseiIdQuery = oseiIdQuery.replace(":"+IFConstants.osei_extClrCD,
					((i000113InputParam.getExtClr() == null)?"''":"'"+i000113InputParam.getExtClr()+"'"))
			.replace(":"+IFConstants.osei_intClrCD,
					((i000113InputParam.getIntClr() == null)?"''":"'"+i000113InputParam.getIntClr()+"'"))
			.replace(":"+IFConstants.oeiBuyer_buyerCD,
					((i000113InputParam.getBuyerCd() == null)?"''":"'"+i000113InputParam.getBuyerCd()+"'"))
			.replace(":"+IFConstants.oeiSpec_specDestnCD,
					((i000113InputParam.getSpecDestnCd() == null)?"''":"'"+i000113InputParam.getSpecDestnCd()+"'"))
			.replace(":"+IFConstants.oeiSpec_carSrs,
					((i000113InputParam.getCarSrs() == null)?"''":"'"+i000113InputParam.getCarSrs()+"'"))
			.replace(":"+IFConstants.oeiSpec_adtnlSpecCD,
					((i000113InputParam.getAdtnSpecCd() == null)?"''":"'"+i000113InputParam.getAdtnSpecCd()+"'"))
			.replace(":"+IFConstants.oeiSpec_pckCD,
					((i000113InputParam.getPckCd() == null)?"''":"'"+i000113InputParam.getPckCd()+"'"))
			.replace(":"+IFConstants.oeiSpec_prodFamilyCD, ((i000113InputParam.getPrdFamilyCd() == null)?"''":"'"+i000113InputParam.getPrdFamilyCd()+"'"))
			.replace(":"+IFConstants.oeiSpec_appldMdlCD, ((i000113InputParam.getApplMdlCd() == null)?"''":"'"+i000113InputParam.getApplMdlCd()+"'"));
									
			List<String> oseiId = entityManager.createNativeQuery(oseiIdQuery).getResultList();

			if (oseiId == null || oseiId.size() == 0) {				
				LOG.info("***OSEI value is null");
				LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);
				return "";
			}			
			LOG.info("***OSEI value : " + oseiId.get(0).toString());			
			seiId =  oseiId.get(0).toString();

		} catch (Exception e) {
			LOG.error("**Error in find OSEI_Id**"+e);					
		}
		LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);
		return seiId;
	}

	/**
	 * P0003:update the overall status in common file header.
	 * This method executed Each step Execution To get the count of Reader,
	 * Writer Based on the count values and write the Log.
	 *
	 * @param stepExecution
	 *            the step execution
	 */
	@AfterStep
	public void afterStep(StepExecution stepExecution) {
		LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR);
		LOG.info(STAR + STEP_ID + stepExecution.getId() + STAR);
		LOG.info(READ_COUNT + stepExecution.getReadCount());
		LOG.info(READ_SKIPPED_COUNT + stepExecution.getReadSkipCount());
		LOG.info(WRITE_COUNT + stepExecution.getWriteCount());
		LOG.info(WRITE_SKIPPED_COUNT + stepExecution.getWriteSkipCount());

		String ifFileId = jobExec.getJobParameters().getString(
				IFConstants.INTERFACE_FILE_ID);

		if (stepExecution.getReadCount() == 0) {
			commonutility.setStatus(PDConstants.INTERFACE_PROCESSED_STATUS);
			commonutility.setRemarks(M00003);
			LOG.info(M00003);
		} else if (stepExecution.getExitStatus().getExitCode().equals(FAILED)) {

			commonutility.setStatus(PDConstants.INTERFACE_FAILURE_STATUS);
			if (("").equals(commonutility.getRemarks())
					|| commonutility.getRemarks() == null) {
				commonutility.setRemarks(M00076.replace(
						PDConstants.ERROR_MESSAGE_1, ""));
				LOG.error(M00076.replace(PDConstants.ERROR_MESSAGE_1,
						stepExecution.getFailureExceptions().toString()));
			} else {
				commonutility.setRemarks(commonutility.getRemarks().replace(
						PDConstants.ERROR_MESSAGE_1, ifFileId));
				LOG.error(commonutility.getRemarks().replace(
						PDConstants.ERROR_MESSAGE_1, ifFileId));
			}

		} else if (stepExecution.getReadCount() == stepExecution
				.getWriteCount()) {
			// write count in header
			commonutility.setWriteCount(stepExecution.getWriteCount());
			commonutility.setStatus(PDConstants.INTERFACE_PROCESSED_STATUS);
			commonutility.setRemarks(M00113);
			LOG.info(M00113);
		} else if (stepExecution.getReadCount() != stepExecution
				.getWriteCount()) {
			commonutility.setStatus(PDConstants.INTERFACE_FAILURE_STATUS);
			commonutility.setRemarks(M00043);
			LOG.info(M00043);
		}
		//Else condition will not occur as the execution falls in anyone of the about condition
		LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);

	}

	/**
	 * Gets the entityManager
	 *
	 * @return the entityManager
	 */
	public EntityManager getEntityManager() {
		return entityManager;
	}

	/**
	 * Sets the entityManager
	 *
	 * @param entityManager the entityManager to set
	 */
	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

}
