/*
 * System Name     :Post Dragon 
 * Sub system Name :Interface
 * Function ID     :PST-DRG-I000004
 * Module          :SP Spec Master					
 * Process Outline :Interface for Receive PL20 Spec Master Interface from DRG-VSM
 *
 * <Revision History>
 * Date       Name(Company Name)             Description 
 * ---------- ------------------------------ ---------------------
 * 29-06-2015  	  z002548(RNTBCI)               New Creation
 *
 */ 
package com.nissangroups.pd.processor;

import static com.nissangroups.pd.util.PDConstants.CREATE_BY;
import static com.nissangroups.pd.util.PDConstants.DOLLAR;
import static com.nissangroups.pd.util.PDConstants.EI_SPEC_ERROR_FLAG;
import static com.nissangroups.pd.util.PDConstants.EI_SPEC_INTERNAL_ERROR_FLAG;
import static com.nissangroups.pd.util.PDConstants.ERROR_DATA_MSG;
import static com.nissangroups.pd.util.PDConstants.FAILED;
import static com.nissangroups.pd.util.PDConstants.INSIDE_METHOD;
import static com.nissangroups.pd.util.PDConstants.OUTSIDE_METHOD;
import static com.nissangroups.pd.util.PDConstants.PERSISTENCE_NAME;
import static com.nissangroups.pd.util.PDConstants.READ_COUNT;
import static com.nissangroups.pd.util.PDConstants.READ_SKIPPED_COUNT;
import static com.nissangroups.pd.util.PDConstants.SINGLE_COMMA;
import static com.nissangroups.pd.util.PDConstants.STAR;
import static com.nissangroups.pd.util.PDConstants.STEP_ID;
import static com.nissangroups.pd.util.PDConstants.WRITE_COUNT;
import static com.nissangroups.pd.util.PDConstants.WRITE_SKIPPED_COUNT;
import static com.nissangroups.pd.util.PDConstants.WEEK_NO_CALEDAR;
import static com.nissangroups.pd.util.PDMessageConsants.M00003;
import static com.nissangroups.pd.util.PDMessageConsants.M00043;
import static com.nissangroups.pd.util.PDMessageConsants.M00076;
import static com.nissangroups.pd.util.PDMessageConsants.M00113;


import java.math.BigDecimal;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.AfterStep;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.batch.core.annotation.OnWriteError;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;

import com.nissangroups.pd.mapper.I000004Mapper;
import com.nissangroups.pd.model.MstEndItmSpec;
import com.nissangroups.pd.model.MstEndItmSpecPK;
import com.nissangroups.pd.repository.WeekNoCalendarRepository;
import com.nissangroups.pd.util.CommonUtil;
import com.nissangroups.pd.util.PDConstants;
import com.nissangroups.pd.util.SpecMstQuery;

/**
 * The Class I000004Processor.
 *
 * @author z002548
 */
public class I000004Processor implements
		ItemProcessor<I000004Mapper, MstEndItmSpec> {

	/** Constant LOG. */
	private static final Log LOG = LogFactory.getLog
			(I000004Processor.class);

	/** Variable spec mst. */
	private MstEndItmSpec specMst;

	/** Variable spec mst query. */
	private SpecMstQuery specMstQuery;

	@Autowired(required=false)
	private WeekNoCalendarRepository weekNoCalendarRepositoryObj ;
	

	/** Variable max seq id. */
	private long maxSeqID;
	
	/** Variable entity manager. */
	@PersistenceContext(name = PERSISTENCE_NAME)
	private EntityManager entityManager;
	
	/** Variable list Common Util. */
    private CommonUtil commonUtil;

	/**
	 * To get the Max Seq ID in the MST_END_ITEM_SPEC Table
	 * And increment the Seq ID.
	 *
	 * @param stepExecution the step execution
	 */
	@BeforeStep
	public void beforeStep(StepExecution stepExecution) {
		LOG.info(DOLLAR+INSIDE_METHOD+DOLLAR);
		LOG.info(stepExecution.getStepName());
		specMstQuery = new SpecMstQuery();
		specMstQuery.setEntityManager(entityManager);
		List<BigDecimal> maxSeqIDList = specMstQuery.getEISMMaxSeqID();
		long seqID = maxSeqIDList.get(0).longValue();
		maxSeqID = ++ seqID;
		LOG.info(DOLLAR+OUTSIDE_METHOD+DOLLAR);
	}

	/**
	 * Insert Data into Master Tables[P0002].
	 *
	 * @param item the item
	 * @return the mst end itm spec
	 * @throws Exception the exception
	 * @see org.springframework.batch.item.ItemProcessor#process(java.lang.Object)
	 * 
	 * To Process Common Layer table data to End Item Spec Master table
	 */
	@Override
	public MstEndItmSpec process(I000004Mapper item) throws Exception {
		LOG.info(DOLLAR+INSIDE_METHOD+DOLLAR);
		commonUtil = new CommonUtil();
		specMst = new MstEndItmSpec();
		specMst.setId(new MstEndItmSpecPK());
		specMst.getId().setSeqId(BigDecimal.valueOf(maxSeqID));
		specMst.getId().setPorCd(item.getId().getCol1());
		specMst.getId().setProdFmyCd(item.getId().getCol2());
		specMst.getId().setProdStageCd(item.getId().getCol3());
		specMst.getId().setGsisRegionGrnd(item.getId().getCol4());
		specMst.getId().setGsisAppldMdlNo(item.getId().getCol5());
		specMst.getId().setAppldMdlCd(item.getId().getCol6());
		specMst.getId().setPckCd(item.getId().getCol7());
		specMst.getId().setSpecDestnCd(item.getId().getCol8());
		specMst.getId().setExtClrCd(item.getId().getCol9());
		specMst.getId().setIntClrCd(item.getId().getCol10());
		
		String adptDate = item.getId().getCol11();
		String ablshDate = item.getId().getCol12();
		
		String adptPrd = weekNoCalendarRepositoryObj.convertYYYYMMDDtoYYYYMMWD(adptDate, item.getId().getCol1());
		String ablshPrd = weekNoCalendarRepositoryObj.convertYYYYMMDDtoYYYYMMWD(ablshDate, item.getId().getCol1());
		
		if(adptPrd== null || adptPrd.equalsIgnoreCase("")){
			LOG.info(M00003 + " IN " + WEEK_NO_CALEDAR + " For POR "+ specMst.getId().getPorCd()+ " AND DATE " + adptDate);
			throw new Exception();
			
		}
		
		if(ablshPrd== null || ablshPrd.equalsIgnoreCase("")){
			LOG.info(M00003 + "IN " + WEEK_NO_CALEDAR + " For POR "+ specMst.getId().getPorCd()+ " AND DATE " + adptDate);
			throw new Exception();
		}
		
		specMst.getId().setEimSpecAdptDate(adptPrd);
		specMst.getId().setEimSpecAblshDate(ablshPrd);
		
		
		specMst.getId().setBuyerCd(item.getId().getCol13());
		specMst.setPckgeName(item.getCol14());
		specMst.setLclNote(item.getCol15());
		specMst.setMdfdFlag(item.getCol16());
		specMst.getId().setAdtnlSpecCd(item.getId().getCol17());
		specMst.setTokusoName(item.getCol18());
		specMst.setOptnlSpecCd(item.getCol19());
		specMst.setCrtdBy(CREATE_BY);
		specMst.setCrtdDt(commonUtil.currentDateTime());
		specMst.setUpdtdBy(CREATE_BY);
		specMst.setUpdtdDt(commonUtil.currentDateTime());
		specMst.setEiSpecErrFlag(EI_SPEC_ERROR_FLAG);
		specMst.setEiSpecIntErrCd(EI_SPEC_INTERNAL_ERROR_FLAG);
		LOG.info(DOLLAR+OUTSIDE_METHOD+DOLLAR);
		return specMst;
	}

	/**
	 * If any Exceptions or Error occurred this method called and write the.
	 *
	 * @param e the e
	 * @param items the items
	 */
	@OnWriteError
	public void onWriteError(Exception e, List<? extends MstEndItmSpec> items) {
		LOG.info(DOLLAR+INSIDE_METHOD+DOLLAR);
		LOG.error(M00043, e);
		for (MstEndItmSpec mstEndItmSpec : items) {
			LOG.info(ERROR_DATA_MSG+mstEndItmSpec.getId().getPorCd()+SINGLE_COMMA+mstEndItmSpec.getId().getSeqId());
		}
		LOG.info(DOLLAR+OUTSIDE_METHOD+DOLLAR);
	}

	/**
	 * This method executed Each step Execution
	 * To get the count of Reader, Writer
	 * Based on the count values  and write the Log.
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
		//Else condition will not occur as the execution falls in anyone of the above conditions
		LOG.info(DOLLAR+OUTSIDE_METHOD+DOLLAR);
		
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

}
