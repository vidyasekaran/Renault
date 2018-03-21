/*
 * System Name     :Post Dragon 
 * Sub system Name :Interface
 * Function ID     :PST-DRG-I000010
 * Module          :Ordering		
 * Process Outline :Receive Frozen Classification Interface from Plant																
 *
 * <Revision History>
 * Date       Name(Company Name)             Description 
 * ---------- ------------------------------ ---------------------
 * 29-06-2015  	  z014159(RNTBCI)               New Creation
 *
 */
package com.nissangroups.pd.processor;
import static com.nissangroups.pd.util.PDConstants.FAILED;
import static com.nissangroups.pd.util.PDMessageConsants.M00003;
import static com.nissangroups.pd.util.PDMessageConsants.M00076;
import static com.nissangroups.pd.util.PDMessageConsants.M00113;
import static com.nissangroups.pd.util.PDMessageConsants.M00043;
import static com.nissangroups.pd.util.PDConstants.INSIDE_METHOD;
import static com.nissangroups.pd.util.PDConstants.OUTSIDE_METHOD;
import static com.nissangroups.pd.util.PDConstants.PERSISTENCE_NAME;
import static com.nissangroups.pd.util.PDConstants.STEP_ID;
import static com.nissangroups.pd.util.PDConstants.STAR;
import static com.nissangroups.pd.util.PDConstants.READ_COUNT;
import static com.nissangroups.pd.util.PDConstants.READ_SKIPPED_COUNT;
import static com.nissangroups.pd.util.PDConstants.WRITE_COUNT;
import static com.nissangroups.pd.util.PDConstants.WRITE_SKIPPED_COUNT;
import static com.nissangroups.pd.util.PDConstants.DOLLAR;
import static com.nissangroups.pd.util.PDConstants.I000010_END_MSG;





import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.AfterStep;
import org.springframework.batch.core.annotation.BeforeProcess;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.batch.item.ItemProcessor;

import com.nissangroups.pd.model.CmnInterfaceData;
import com.nissangroups.pd.model.MstFrzn;
import com.nissangroups.pd.model.MstFrznPK;
import com.nissangroups.pd.util.CommonUtil;
import com.nissangroups.pd.util.PDConstants;
import com.nissangroups.pd.util.QueryConstants;

/**
 * This class Extract data from CommonLayerData Mst Set to the FrozenMst.
 *
 * @author z014159,z015060
 */
public class I000010Processor implements ItemProcessor<CmnInterfaceData, MstFrzn> {
	
	/** Constant LOG. */
	private static final  Log LOG = LogFactory.getLog(I000010Processor.class
			.getName());
	
	/** Variable entity manager. */
	@PersistenceContext(name = PERSISTENCE_NAME)
	private EntityManager entityManager;
	
	/** Variable seq no. */
	String seqNo = null;
	
	/** Variable interface id. */
	private static String interfaceId = null;
	
	/** Variable interface por cd. */
	private static String interfacePorCd = null;
	
	/** Variable color lenght. */
	int colorLenght = 4;
	
	/** Variable spec destn length. */
	int specDestnLength = 3;
	
	/** Variable color code with space. */
	private String colorCodeWithSpace = null;
	
	/** Variable spec destn cd with space. */
	private String specDestnCdWithSpace = null;
	
	/** Variable min ordr take base mnth. */
	String minOrdrTakeBaseMnth = null;
	
	/** Variable date. */
	Date date= new Date();
	
	/** Variable create date. */
	Timestamp createDate =new Timestamp(date.getTime());
	
	boolean dltFlg = false;
	
	
	/**
	 * Before step.
	 *
	 * @param stepExecution the step execution
	 */
	/*
	 * Before Step process
	 * Process No-P0002
	 * Get the corresponding  Variables from the PDConstants
	 * 
	 */
	@BeforeStep
	public void beforeStep(StepExecution stepExecution) {
		LOG.info(DOLLAR+INSIDE_METHOD+DOLLAR);
		interfaceId = stepExecution.getJobParameters().getString(
				PDConstants.INTERFACE_FILE_ID);
		interfacePorCd = stepExecution.getJobParameters().getString(
				PDConstants.BATCH_POR_CODE);
		LOG.info(DOLLAR+OUTSIDE_METHOD+DOLLAR);
	}
	
	
	/* (non-Javadoc)
	 * @see org.springframework.batch.item.ItemProcessor#process(java.lang.Object)
	 */
	/*
	 * Insert the extracted data from the Common Layer Data
	 * Process No- P0003
	 * @param - item - Object Data Type JpaPagingItemReader Class Start
	 * 14-07-2015 Last Modified 15-8-2015 
	 * Return - Record from CommonLayerData and set into MstFrozen
	 */
	@Override
	public MstFrzn  process(CmnInterfaceData cmnInterfaceData) throws Exception {
		LOG.info(DOLLAR+INSIDE_METHOD+DOLLAR);
		MstFrzn frozenMst=new MstFrzn();
		MstFrznPK frozenMstPk=new MstFrznPK();
		seqNo = String.valueOf(cmnInterfaceData.getId().getSeqNo());
		colorCodeWithSpace = CommonUtil.splitStringWithSpace(cmnInterfaceData.getCol17(),colorLenght);
		specDestnCdWithSpace = CommonUtil.splitStringWithSpace(cmnInterfaceData.getCol18(),specDestnLength);
		frozenMstPk.setPorCd(interfacePorCd);
		frozenMstPk.setCarSrs(cmnInterfaceData.getCol5());
		frozenMstPk.setFrznPrityCd(cmnInterfaceData.getCol6());
		frozenMst.setProdFmyCd(cmnInterfaceData.getCol8());
		frozenMstPk.setFrznOrdrTakeBaseMnth(cmnInterfaceData.getCol9());
		frozenMstPk.setFrznProdMnth(cmnInterfaceData.getCol10());
		frozenMstPk.setFrznTypeCd(cmnInterfaceData.getCol11());
		frozenMstPk.setOcfRegionCd(cmnInterfaceData.getCol12());
		frozenMst.setPrfxYes(cmnInterfaceData.getCol13());
		frozenMst.setPrfxNo(cmnInterfaceData.getCol14());
		frozenMst.setSffxYes(cmnInterfaceData.getCol15());
		frozenMst.setSffxNo(cmnInterfaceData.getCol16());
		frozenMst.setFrznClrCodeCndtn(colorCodeWithSpace);
		frozenMst.setFrznSpecDestnCdCndtn(specDestnCdWithSpace);
		frozenMst.setCrtdDt(createDate);
		frozenMst.setUpdtdDt(createDate);
		frozenMst.setFrznDelFlag(PDConstants.DEFAULT_FROZEN_DELETE_FLAG);
		frozenMst.setMnlFrznFlag(PDConstants.DEFAULT_FROZEN_MANUAL_FLAG);
		interfaceId= PDConstants.INTERFACE_ID_I000010;
		frozenMst.setUpdtdBy(interfaceId);
		frozenMst.setCrtdBy(interfaceId);
        frozenMst.setId(frozenMstPk);
        LOG.info(DOLLAR+OUTSIDE_METHOD+DOLLAR);
		return frozenMst;
	}
	
	
	/**
 * Gets the entity manager.
 *
 * @return the entity manager
 */

	public EntityManager getEntityManager() {
		LOG.info(DOLLAR+INSIDE_METHOD+DOLLAR);
		return entityManager;
	}
	
	/**
	 * Sets the entity manager.
	 *
	 * @param entityManager the new entity manager
	 */
	
	public void setEntityManager(EntityManager entityManager) {
		LOG.info(DOLLAR+INSIDE_METHOD+DOLLAR);
		this.entityManager = entityManager;
	}
	
	/**
	 * Before process.
	 */
	/*
	 * Delete all the records from the FROZEN_MST table based on the given POR Code &   
	 * Order Take base month value extracted from the interface file
	 * Process No-P0002
	 * @param - no arg 
	 * Start 24-07-2015 Last Modified 15-08-2015
	 * Author -z014159 , z015060
	 */
	@BeforeProcess
	public void beforeProcess() {
		LOG.info(DOLLAR+INSIDE_METHOD+DOLLAR);
		if( !dltFlg){
			dltFlg = true;
		minOrdrTakeBaseMnth = getMinOrdrTakeBaseMnth();
		if(minOrdrTakeBaseMnth !=  null){
		 String dltMstProdTypeQry = QueryConstants.FrznMstProdTyp.toString();
				 Query deleteOrder = entityManager
				 .createNativeQuery(dltMstProdTypeQry);
				 deleteOrder.setParameter(PDConstants.PRMTR_PORCD, interfacePorCd);
				 deleteOrder.setParameter(PDConstants
						 .PRMTRT_ORDER_TAKE_BASE_MONTH, minOrdrTakeBaseMnth);
				 deleteOrder.executeUpdate();
				 }
	}
		LOG.info(DOLLAR+OUTSIDE_METHOD+DOLLAR);
	}
	
	/**
	 * Gets the min ordr take base mnth.
	 *
	 * @return the min ordr take base mnth
	 */
	/*
	 * Method to get Minimum order take base month for I000010 processor with the conditions of InterfaceId,
	 * Unprocessed status and por_cd values 
	 * Return to the beforeProcess() method to delete the existing data in MstFrozen with conditions
	 */
	public String getMinOrdrTakeBaseMnth(){
		LOG.info(DOLLAR+INSIDE_METHOD+DOLLAR);
		String minOrdrTkeMnthQuery = QueryConstants.minOrdrTkeMnthQueryi10.toString();
		Query query = entityManager.createQuery(minOrdrTkeMnthQuery);
		query.setParameter(PDConstants.PRMTRT_INTERFACE_FILE_ID, interfaceId);
		query.setParameter(PDConstants.PRMTRT_INTERFACE_STATUS, PDConstants.INTERFACE_UNPROCESSED_STATUS);
		query.setParameter(PDConstants.PRMTR_PORCD, interfacePorCd);
		LOG.info(DOLLAR+OUTSIDE_METHOD+DOLLAR);
		return (String) query.getSingleResult();
	}
	
	/**
	 * To get the count of row affected in Each Step.
	 *
	 * @param stepExecution the step execution
	 */
	@AfterStep
	public void afterStep(StepExecution stepExecution) {
		LOG.info(DOLLAR+INSIDE_METHOD+DOLLAR);
		LOG.info(STAR+STEP_ID + stepExecution.getId()+STAR);
		LOG.info(STAR+STEP_ID + stepExecution.getId()+STAR);
		LOG.info(READ_COUNT + stepExecution.getReadCount());
		LOG.info(READ_SKIPPED_COUNT + stepExecution.getReadSkipCount());
		LOG.info(WRITE_COUNT + stepExecution.getWriteCount());
		LOG.info(WRITE_SKIPPED_COUNT + stepExecution.getWriteSkipCount());

		if (stepExecution.getReadCount() == 0) {
			LOG.info(M00003);
		}
		else if (stepExecution.getExitStatus().getExitCode().equals(FAILED)) {

			LOG.error(M00076.replace(PDConstants.ERROR_MESSAGE_1,stepExecution.getFailureExceptions().toString()));
						
		}
		else if (stepExecution.getReadCount() == stepExecution.getWriteCount()) {
			LOG.info(M00113);
		}
		else if (stepExecution.getReadCount() != stepExecution.getWriteCount()) {
			LOG.info(M00043);
		}
		LOG.info(DOLLAR+OUTSIDE_METHOD+DOLLAR);
		LOG.info(DOLLAR+I000010_END_MSG+DOLLAR);

	}

}
