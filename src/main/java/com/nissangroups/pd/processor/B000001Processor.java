/*
 * System Name     :Post Dragon 
 * Sub system Name :Batch / Interface
 * Function ID     :PST-DRG-B000001/PST-DRG-R000001
 * Module          :SP SPEC
 * Process Outline :Validating the interface fields with Master and generating the error report
 *
 * <Revision History>
 * Date       Name(Company Name)             Description 
 * ---------- ------------------------------ ---------------------
 * 2015/07/25  	  @author(RNTBCI)               New Creation
 *
 */
package com.nissangroups.pd.processor;

import static com.nissangroups.pd.util.PDConstants.BUYER_CODE_ERROR_FLAG;
import static com.nissangroups.pd.util.PDConstants.COLOR_ERROR_FLAG;
import static com.nissangroups.pd.util.PDConstants.COMMENTS_1;
import static com.nissangroups.pd.util.PDConstants.COMMENTS_2;
import static com.nissangroups.pd.util.PDConstants.COMMENTS_3;
import static com.nissangroups.pd.util.PDConstants.COMMENTS_4;
import static com.nissangroups.pd.util.PDConstants.ERROR_FLAG;
import static com.nissangroups.pd.util.PDConstants.ERROR_TYPE_ZERO;
import static com.nissangroups.pd.util.PDConstants.ERROR_TYPE_ONE;
import static com.nissangroups.pd.util.PDConstants.INTERNAL_SUCCESS;
import static com.nissangroups.pd.util.PDMessageConsants.M00003;
import static com.nissangroups.pd.util.PDMessageConsants.M00163;
import static com.nissangroups.pd.util.PDMessageConsants.M00164;
import static com.nissangroups.pd.util.PDConstants.POR_ERROR_FLAG;
import static com.nissangroups.pd.util.PDConstants.SPEC_DEST_ERROR_FLAG;
import static com.nissangroups.pd.util.PDConstants.STEP_ID;
import static com.nissangroups.pd.util.PDConstants.STEP_ID_1;
import static com.nissangroups.pd.util.PDConstants.STEP_ID_2;
import static com.nissangroups.pd.util.PDConstants.STEP_ID_3;
import static com.nissangroups.pd.util.PDConstants.STEP_ID_4;
import static com.nissangroups.pd.util.PDConstants.STEP_ID_5;
import static com.nissangroups.pd.util.PDConstants.STEP_STATUS_FAIL;
import static com.nissangroups.pd.util.PDConstants.SUCCESS_FLAG;
import static com.nissangroups.pd.util.PDConstants.WARNING_FLAG;
import static com.nissangroups.pd.util.PDConstants.WARNING_REPORT;
import static com.nissangroups.pd.util.PDConstants.WARNING_REPORT_EXTCODE;
import static com.nissangroups.pd.util.PDConstants.BATCH_1;
import static com.nissangroups.pd.util.PDConstants.PERSISTENCE_NAME;
import static com.nissangroups.pd.util.PDConstants.STEP_START;
import static com.nissangroups.pd.util.PDConstants.STEP_AFTER_START;
import static com.nissangroups.pd.util.PDConstants.STAR;
import static com.nissangroups.pd.util.PDConstants.READ_COUNT;
import static com.nissangroups.pd.util.PDConstants.READ_SKIPPED_COUNT;
import static com.nissangroups.pd.util.PDConstants.WRITE_COUNT;
import static com.nissangroups.pd.util.PDConstants.WRITE_SKIPPED_COUNT;
import static com.nissangroups.pd.util.PDConstants.DOLLAR;
import static com.nissangroups.pd.util.PDConstants.STEP_EXECUTION_STATUS;
import static com.nissangroups.pd.util.PDConstants.STEP_AFTER_END;
import static com.nissangroups.pd.util.PDConstants.POR_CODE;
import static com.nissangroups.pd.util.PDConstants.BUYER_CODE;
import static com.nissangroups.pd.util.PDConstants.SPEC_DESTINATION_CODE;
import static com.nissangroups.pd.util.PDConstants.EXTERIOR_COLOR_CODE;
import static com.nissangroups.pd.util.PDConstants.BEFORE_CHUNK_METHOD_START;
import static com.nissangroups.pd.util.PDConstants.BEFORE_CHUNK_METHOD_END;







import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.AfterStep;
import org.springframework.batch.core.annotation.BeforeChunk;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;

import com.nissangroups.pd.dao.B000001ReportDao;
import com.nissangroups.pd.model.MstEndItmSpec;
import com.nissangroups.pd.util.CommonUtil;
import com.nissangroups.pd.util.PDConstants;
import com.nissangroups.pd.util.PDMessageConsants;
import com.nissangroups.pd.util.SpecMstQuery;


/**
 * The Class B000001Processor.
 *
 * @author z002548
 */

public class B000001Processor implements
		ItemProcessor<MstEndItmSpec, MstEndItmSpec> {

	/** Constant LOG. */
	private static final Log LOG = LogFactory.getLog
			(B000001Processor.class);

	/** Variable entity manager. */
	@PersistenceContext(name = PERSISTENCE_NAME )
	private EntityManager entityManager;

	/** Variable spec mst query. */
	private SpecMstQuery specMstQuery;

	/** Variable step execution id. */
	private String stepExecutionID;

	/** Variable list report dao. */
	@Autowired(required = false)
	private B000001ReportDao listReportDao;
	
	/** Variable list Common Util. */
	private CommonUtil commonUtil;
	
	/**
	 * In this method to get the Step Name and assign this value to some String value.
	 *
	 * @param stepExecution the step execution
	 */
	@BeforeStep
	public void beforeStep(StepExecution stepExecution) {
		stepExecutionID = stepExecution.getStepName();
		LOG.info(STEP_START);
		LOG.info(STEP_ID + stepExecutionID);
	}

	/**
	 * After Step Complete to get the Record count of Read and Write
	 * Based on count values to capture the Log Informations.
	 *
	 * @param stepExecution the step execution
	 */
	@AfterStep
	public void afterStep(StepExecution stepExecution) {
		LOG.info(STEP_AFTER_START);
		LOG.info(STAR+STEP_ID + stepExecution.getId()+STAR);				
		if (stepExecution.getReadCount() == 0) {
			LOG.info(M00003);
		}
		if (stepExecution.getWriteCount() > 0) {
			LOG.info(M00163.replace(PDConstants.ERROR_MESSAGE_1, PDConstants.BATCH_1)
					.replace(PDConstants.ERROR_MESSAGE_2, PDConstants.MESSAGE_UPDATED)
        			.replace(PDConstants.ERROR_MESSAGE_3, PDConstants.MESSAGE_MASTER_END_ITEM_SPEC));
		}
		if (stepExecution.getExitStatus().getExitCode()
				.equals(STEP_STATUS_FAIL)) {
			LOG.error(M00164.replace(PDConstants.ERROR_MESSAGE_1, PDConstants.BATCH_1)
					.replace(PDConstants.ERROR_MESSAGE_2, PDConstants.MESSAGE_UPDATED)
        			.replace(PDConstants.ERROR_MESSAGE_3, PDConstants.MESSAGE_MASTER_END_ITEM_SPEC));
		}
		LOG.info(READ_COUNT + stepExecution.getReadCount());
		LOG.info(READ_SKIPPED_COUNT + stepExecution.getReadSkipCount());
		LOG.info(WRITE_COUNT + stepExecution.getWriteCount());
		LOG.info(WRITE_SKIPPED_COUNT + stepExecution.getWriteSkipCount());
		LOG.info(DOLLAR+STEP_EXECUTION_STATUS+
				stepExecution.getExitStatus().getExitCode());
		LOG.info(STEP_AFTER_END);

	}

	/**
	 * In this method to Extract the End Item Spec table and set respective Error Flag & Error Code.
	 *
	 * @param item the item
	 * @return the mst end itm spec
	 * @throws Exception the exception
	 * @see org.springframework.batch.item.ItemProcessor#process(java.lang.Object) To
	 * Process the END_ITEM_SPEC_MST data Create Error Report based on
	 * availability of Prior Master tables
	 */
	@Override
	public MstEndItmSpec process(MstEndItmSpec item) throws Exception {

		   /** Process ID : 1 & 2 */
	    
	   /*     if(item.getEiSpecErrFlag().equals(ERROR_FLAG) || item.getEiSpecErrFlag().equals(WARNING_FLAG)) {
	            return null;
	        }*/
	        
			B000001ReportDao reportDao = errorReport(item, stepExecutionID);
			/** To get the Current Date & Time */
			commonUtil = new CommonUtil();
			item.setUpdtdBy(BATCH_1);
			item.setUpdtdDt(commonUtil.currentDateTime());
			/* The Step ID is Equal to Step 1 then Set the Error Flag & Internal Error Code to 1 & 1 */
			if (stepExecutionID.equals(STEP_ID_1)) {
				item.setEiSpecErrFlag(ERROR_FLAG);
				item.setEiSpecIntErrCd(POR_ERROR_FLAG);
				reportDao.setComments(COMMENTS_1);
				reportDao.setInternalErrorcd(POR_ERROR_FLAG);
				listReportDao.getReportList().add(reportDao);
				doErrorLog(item);
			}
			/* The Step ID is Equal to Step 2 then Set the Error Flag & Internal Error Code to 1 & 2 */
			else if (stepExecutionID.equals(STEP_ID_2)) {
				item.setEiSpecErrFlag(ERROR_FLAG);
				item.setEiSpecIntErrCd(BUYER_CODE_ERROR_FLAG);
				reportDao.setComments(COMMENTS_2);
				reportDao.setInternalErrorcd(BUYER_CODE_ERROR_FLAG);
				listReportDao.getReportList().add(reportDao);
				doErrorLog(item);
			} 
			/* The Step ID is Equal to Step 3 then Set the Error Flag & Internal Error Code to 1 & 3 */
			else if (stepExecutionID.equals(STEP_ID_3)) {
				item.setEiSpecErrFlag(ERROR_FLAG);
				item.setEiSpecIntErrCd(SPEC_DEST_ERROR_FLAG);
				reportDao.setComments(COMMENTS_3);
				reportDao.setInternalErrorcd(SPEC_DEST_ERROR_FLAG);
				listReportDao.getReportList().add(reportDao);
				doErrorLog(item);
			} 
			/* The Step ID is Equal to Step 4 then Set the Error Flag & Internal Error Code to 4 & 4 */
			else if (stepExecutionID.equals(STEP_ID_4)) {

				item.setEiSpecErrFlag(WARNING_FLAG);
				item.setEiSpecIntErrCd(COLOR_ERROR_FLAG);
				reportDao.setComments(COMMENTS_4);
				reportDao.setInternalErrorcd(COLOR_ERROR_FLAG);
				listReportDao.getReportList().add(reportDao);
				doErrorLog(item);
			} 
			/* The Step ID is Equal to Step 5 then No Error occurred in the End Item Spec table, so set Error Flag & internal Error code 0 & 0 */ 
			else if (stepExecutionID.equals(STEP_ID_5)) {
				item.setEiSpecErrFlag(SUCCESS_FLAG);
				item.setEiSpecIntErrCd(INTERNAL_SUCCESS);
				doErrorLog(item);
			}


		return item;
	}

	/**
	 * Do error log.
	 *
	 * @param item the item
	 */
	private void doErrorLog(MstEndItmSpec item) {

		LOG.info(POR_CODE + item.getId().getPorCd() + BUYER_CODE
				+ item.getId().getBuyerCd() + SPEC_DESTINATION_CODE
				+ item.getId().getSpecDestnCd() + EXTERIOR_COLOR_CODE
				+ item.getId().getExtClrCd());

	}

	/**
	 * In this method Executed only once in the Each Step 
	 * Based on availability of Master table to Change the Update Flag 
	 * Spec Master Table.
	 *
	 * @param chunkContext the chunk context
	 */
	@BeforeChunk
	public void beforeChunk(ChunkContext chunkContext) {
		LOG.info(DOLLAR+BEFORE_CHUNK_METHOD_START+ STEP_ID
				+ stepExecutionID + DOLLAR);
		String stepName = chunkContext.getStepContext().getStepName();
		specMstQuery = new SpecMstQuery();

		specMstQuery.setEntityManager(entityManager);
		/* P0007.1 */
		if (stepName.equals(STEP_ID_1)) {
			int updateCount = specMstQuery.porUpdateCheck();
			LOG.info("MST_POR Update Count : " + updateCount);
		}
		/* P0007.2 */
		else if (stepName.equals(STEP_ID_2)) {
			int updateCount = specMstQuery.buyerUpdateCheck();
			LOG.info("MST_BUYER update Count" + updateCount);
		}

		/* P0007.3 */
		else if (stepName.equals(STEP_ID_3)) {
			int updateCount = specMstQuery.specBuyerUpdateCheck();
			LOG.info("MST_BUYER_SPEC_DESTIN update count" + updateCount);
		}
		/* P0007.4 */
		else if (stepName.equals(STEP_ID_4)) {
			int updateCount = specMstQuery.exteriorUpdateCheck();
			LOG.info("MST_EXT_CLR update count" + updateCount);
		}
		LOG.info(BEFORE_CHUNK_METHOD_END);

	}

	/**
	 * To Generate Error Report Process ID : P0008.1
	 *
	 * @param item the item
	 * @param stepExecutionID the step execution id
	 * @return the b000001 report dao
	 */
	public B000001ReportDao errorReport(MstEndItmSpec item,
			String stepExecutionID) {
	    CommonUtil util = new CommonUtil();
		B000001ReportDao reportDao = new B000001ReportDao();
		reportDao.setPorCode(item.getId().getPorCd());
		reportDao.setSeqId(item.getId().getSeqId().toString());
		reportDao.setProductionFamilyCode(item.getId().getProdFmyCd());
		reportDao.setProductionStageCode(item.getId().getProdStageCd());
		if (!stepExecutionID.equals(STEP_ID_4)) {
			reportDao.setBuyerCode(item.getId().getBuyerCd());
			reportDao.setEndItemModelCode(item.getId().getAppldMdlCd());
			reportDao.setColorCode(item.getId().getExtClrCd()
					+ item.getId().getIntClrCd());
			reportDao.setAdditionSpecCode(item.getId().getAdtnlSpecCd());
			reportDao.setSpecDestinationCode(item.getId().getSpecDestnCd());
			reportDao.setAdoptMonth(util.convertYearMonth(item.getId().getEimSpecAdptDate()));
			reportDao.setAbolistMonth(util.convertYearMonth(item.getId().getEimSpecAblshDate()));
		} else {
			reportDao.setBuyerCode(WARNING_REPORT);
			reportDao.setEndItemModelCode(WARNING_REPORT);
			reportDao.setColorCode(item.getId().getExtClrCd()
					+ WARNING_REPORT_EXTCODE);
			reportDao.setAdditionSpecCode(WARNING_REPORT);
			reportDao.setSpecDestinationCode(WARNING_REPORT);
			reportDao.setAdoptMonth(WARNING_REPORT);
			reportDao.setAbolistMonth(WARNING_REPORT);
		}
		if(item.getEiSpecErrFlag().equals(ERROR_FLAG) || item.getEiSpecErrFlag().equals(WARNING_FLAG)) {
		    reportDao.setErrorTypeCode(ERROR_TYPE_ONE);
		}
		else {
		    reportDao.setErrorTypeCode(ERROR_TYPE_ZERO);
		}
		
		return reportDao;

	}

	/**
	 * Gets the entity manager.
	 *
	 * @return the entity manager
	 */
	public EntityManager getEntityManager() {
		return entityManager;
	}

	/**
	 * Sets the entity manager.
	 *
	 * @param entityManager the new entity manager
	 */
	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}
}
