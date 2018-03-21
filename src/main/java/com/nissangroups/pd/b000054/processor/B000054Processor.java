/*
 * System Name     :Post Dragon 
 * Sub system Name :Interface
 * Function ID     :PST-DRG-B000054
 * Module          :Weekly
 * Process Outline :OCF Validation and Actual Order Calculation.															
 *
 * <Revision History>
 * Date       Name(Company Name)             Description 
 * ---------- ------------------------------ ---------------------
 * 29-06-2015  	  z011479(RNTBCI)               New Creation
 *
 */
package com.nissangroups.pd.b000054.processor;

import static com.nissangroups.pd.util.PDConstants.DOLLAR;
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

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.AfterStep;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;

import com.nissangroups.pd.b000054.output.B000054Output;
import com.nissangroups.pd.b000054.output.B000054ParamOutput;
import com.nissangroups.pd.model.WklyBatchProcessStt;

/**
 * This class to process the data extracted from the Reader(Monthly Order Take
 * base Month)
 *
 * @author z011479
 */
public class B000054Processor implements
		ItemProcessor<WklyBatchProcessStt, B000054Output> {

	/** Constant LOG. */
	private static final Log LOG = LogFactory.getLog(B000054Processor.class
			.getName());

	/** Variable entity manager. */
	@PersistenceContext(name = PERSISTENCE_NAME)
	private EntityManager entityManager;

	/** Variable ordr take base mnth. */
	private String orderTkBsMnth;

	/** Variable POR CD. */
	private String porCd;

	/** Variable Order Take Base Week */
	private String ordrTkBsWk;

	/** Variable Sequence Id. */
	private String seqId;


	@Autowired(required = false)
	private B000054ParamOutput b000054ParamOutput;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.batch.item.ItemProcessor#process(java.lang.Object)
	 */
	@Override
	public B000054Output process(WklyBatchProcessStt wklyBtchPrcssStts)
			throws Exception {
		B000054Output b000054Output = new B000054Output();
		LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR);
		LOG.info(wklyBtchPrcssStts.getId().getPorCd());
		LOG.info("Order Take Base Month ::::: "
				+ wklyBtchPrcssStts.getId().getOrdrTakeBaseMnth());
		LOG.info("Production Month ::::: "
				+ wklyBtchPrcssStts.getId().getOrdrTakeBaseWkNo());
		b000054ParamOutput.setPorCd(wklyBtchPrcssStts.getId().getPorCd());
		b000054ParamOutput.setSeqId(wklyBtchPrcssStts.getId().getSeqId());
		b000054ParamOutput.setOrderTkBsMnth(wklyBtchPrcssStts.getId()
				.getOrdrTakeBaseMnth());
		b000054ParamOutput.setOrderTkBsWk(wklyBtchPrcssStts.getId()
				.getOrdrTakeBaseWkNo());
		//Insert data to TRN table
		LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR);
		return b000054Output;
	}

	/**
	 * To get the count of row affected in Each Step.
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

		if (stepExecution.getReadCount() == 0) {
			LOG.info(M00003);
		}
		LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);
	}

	/**
	 * Gets the entity manager.
	 *
	 * @return the entity manager
	 */
	/** loading entities from the database */
	public EntityManager getEntityManager() {
		LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR);
		return entityManager;
	}

	/**
	 * Sets the entity manager.
	 *
	 * @param entityManager
	 *            the new entity manager
	 */
	/** loading entities from the database */
	public void setEntityManager(EntityManager entityManager) {
		LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR);
		this.entityManager = entityManager;
	}

	/**
	 * @return the orderTkBsMnth
	 */
	public String getOrderTkBsMnth() {
		return orderTkBsMnth;
	}

	/**
	 * @param orderTkBsMnth
	 *            the orderTkBsMnth to set
	 */
	public void setOrderTkBsMnth(String orderTkBsMnth) {
		this.orderTkBsMnth = orderTkBsMnth;
	}

	/**
	 * @return the porCd
	 */
	public String getPorCd() {
		return porCd;
	}

	/**
	 * @param porCd
	 *            the porCd to set
	 */
	public void setPorCd(String porCd) {
		this.porCd = porCd;
	}

	/**
	 * @return the ordrTkBsWk
	 */
	public String getOrdrTkBsWk() {
		return ordrTkBsWk;
	}

	/**
	 * @param ordrTkBsWk
	 *            the ordrTkBsWk to set
	 */
	public void setOrdrTkBsWk(String ordrTkBsWk) {
		this.ordrTkBsWk = ordrTkBsWk;
	}

	/**
	 * @return the seqId
	 */
	public String getSeqId() {
		return seqId;
	}

	/**
	 * @param seqId
	 *            the seqId to set
	 */
	public void setSeqId(String seqId) {
		this.seqId = seqId;
	}

}
