/*
 * System Name     :Post Dragon 
 * Sub system Name :Interface
 * Function ID     :PST-DRG-I000009
 * Module          :Spec Master
 * Process Outline :Receive Vehicle Production Type Master from Plant																	
 *
 * <Revision History>
 * Date       Name(Company Name)             Description 
 * ---------- ------------------------------ ---------------------
 * 29-06-2015  	  z014159(RNTBCI)               New Creation
 *
 */
package com.nissangroups.pd.processor;

import static com.nissangroups.pd.util.PDConstants.DOLLAR;
import static com.nissangroups.pd.util.PDConstants.FAILED;
import static com.nissangroups.pd.util.PDConstants.INSIDE_METHOD;
import static com.nissangroups.pd.util.PDConstants.OUTSIDE_METHOD;
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
import static com.nissangroups.pd.util.PDConstants.PERSISTENCE_NAME;
import static com.nissangroups.pd.util.PDConstants.I000009_END_MSG;
import static com.nissangroups.pd.util.PDConstants.DATA_AVAILABLE_MSG;

import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.AfterStep;
import org.springframework.batch.core.annotation.AfterWrite;
import org.springframework.batch.core.annotation.BeforeProcess;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.batch.item.ItemProcessor;

import com.nissangroups.pd.model.CmnInterfaceData;
import com.nissangroups.pd.model.MstProdType;
import com.nissangroups.pd.model.MstProdTypePK;
import com.nissangroups.pd.util.CommonUtil;
import com.nissangroups.pd.util.PDConstants;
import com.nissangroups.pd.util.QueryConstants;

/**
 * This class Extract data from CommonLayerData Mst Set to the ProductionTypeMst.
 *
 * @author z014159,z011479
 */
public class I000009Processor implements
		ItemProcessor<CmnInterfaceData, MstProdType> {
	
	/** Constant LOG. */
	private static final Log LOG = LogFactory.getLog(I000009Processor.class
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
	
	/** Variable ordr take base mnth. */
	private String ordrTakeBaseMnth = null;
	
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
	
	boolean dltFlg = false;

	/**
	 * Before step.
	 *
	 * @param stepExecution the step execution
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
	@Override
	/*
	 * Delete the existing data from Production Type MST and set the values for
	 * write input entity class Process No-P0002 & partial P0003
	 * 
	 * @param - item - Object Data Type JpaPagingItemReader Class Start
	 * 08-07-2015 Last Modified 12-7-2015 Return - Record from CommonLayerData `
	 */
	public MstProdType process(CmnInterfaceData cmnIntrfceData)
			throws Exception {
		LOG.info(DOLLAR+INSIDE_METHOD+DOLLAR);
		seqNo = String.valueOf(cmnIntrfceData.getId().getSeqNo());
		colorCodeWithSpace = CommonUtil.splitStringWithSpace(
				cmnIntrfceData.getCol12(), colorLenght);
		specDestnCdWithSpace = CommonUtil.splitStringWithSpace(
				cmnIntrfceData.getCol13(), specDestnLength);
		ordrTakeBaseMnth = cmnIntrfceData.getCol2().trim()
				+ cmnIntrfceData.getCol3().trim();
		MstProdType mstProdType = new MstProdType();
		MstProdTypePK mstProdTypePk = new MstProdTypePK();
		mstProdTypePk.setPorCd(interfacePorCd);
		mstProdTypePk.setOrdrTakeBaseMnth(ordrTakeBaseMnth);
		mstProdTypePk.setProdPlntCd(cmnIntrfceData.getCol4());
		mstProdTypePk.setProdFmyCd(cmnIntrfceData.getCol5());
		mstProdTypePk.setCarSrsPrityCd(cmnIntrfceData.getCol6());
		mstProdType.setPrfxYes(cmnIntrfceData.getCol7().substring(0, 7));
		mstProdType.setPrfxNo(cmnIntrfceData.getCol8().substring(0, 7));
		mstProdType.setSffxYes(cmnIntrfceData.getCol9().substring(0, 8));
		mstProdType.setSffxNo(cmnIntrfceData.getCol10().substring(0, 8));
		mstProdType.setPckSymblCdCndtn(cmnIntrfceData.getCol11());
		mstProdType.setClrCodeCndtn(colorCodeWithSpace);
		mstProdType.setSpecDestnCdCndtn(specDestnCdWithSpace);
		mstProdType.setCarSrs(cmnIntrfceData.getCol14());
		mstProdType.setProdMthdCd(cmnIntrfceData.getCol15());
		mstProdType.setOcfRegionCd(cmnIntrfceData.getCol16());
		mstProdType.setUpdtdBy(interfaceId);
		mstProdType.setCrtdBy(interfaceId);
		Date date = new Date();
		Timestamp createDate = new Timestamp(date.getTime());
		mstProdType.setCrtdDt(createDate);
		mstProdType.setUpdtdDt(createDate);
		mstProdType.setId(mstProdTypePk);
		LOG.info(DATA_AVAILABLE_MSG);
		LOG.info(DOLLAR+OUTSIDE_METHOD+DOLLAR);
		return mstProdType;
	}

	/**
	 * Gets the entity manager.
	 *
	 * @return the entity manager
	 */
	/** loading entities from the database */
	public EntityManager getEntityManager() {
		LOG.info(DOLLAR+INSIDE_METHOD+DOLLAR);
		return entityManager;
	}

	/**
	 * Sets the entity manager.
	 *
	 * @param entityManager the new entity manager
	 */
	/** loading entities from the database */
	public void setEntityManager(EntityManager entityManager) {
		LOG.info(DOLLAR+INSIDE_METHOD+DOLLAR);
		this.entityManager = entityManager;
	}

	/**
	 * Spring Batch Framework method.
	 */
	@BeforeProcess
	public void beforeProcess() {
		LOG.info(DOLLAR+INSIDE_METHOD+DOLLAR);
		if( !dltFlg){
			dltFlg = true;
		minOrdrTakeBaseMnth = getMinOrdrTakeBaseMnth();
		if( minOrdrTakeBaseMnth != null){
		String dltMstProdTypeQry = QueryConstants.intrfcDelMstProdTyp
				.toString();
		Query deleteOrder = entityManager.createNativeQuery(dltMstProdTypeQry);
		deleteOrder.setParameter(PDConstants.PRMTR_PORCD, interfacePorCd);
		deleteOrder.setParameter(PDConstants.PRMTRT_ORDER_TAKE_BASE_MONTH,
				minOrdrTakeBaseMnth);
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
	public String getMinOrdrTakeBaseMnth() {
		LOG.info(DOLLAR+INSIDE_METHOD+DOLLAR);
		String minOrderTkeBaseMnth = null;
		String minOrdrTkeMnthQuery = QueryConstants.minOrdrTkeMnthQuery
				.toString();
		Query query = entityManager.createQuery(minOrdrTkeMnthQuery);
		query.setParameter(PDConstants.PRMTRT_INTERFACE_FILE_ID, interfaceId);
		query.setParameter(PDConstants.PRMTRT_INTERFACE_STATUS,
				PDConstants.INTERFACE_UNPROCESSED_STATUS);
		query.setParameter(PDConstants.PRMTR_PORCD, interfacePorCd);
		minOrderTkeBaseMnth = (String) query.getSingleResult();
		LOG.info(DOLLAR+OUTSIDE_METHOD+DOLLAR);
		return minOrderTkeBaseMnth;
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
		LOG.info(DOLLAR+I000009_END_MSG+DOLLAR);

		

	}
		
}
