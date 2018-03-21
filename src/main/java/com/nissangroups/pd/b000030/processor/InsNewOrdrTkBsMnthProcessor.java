/*
 * System Name     :Post Dragon 
 * Sub system Name :Batch
 * Function ID     :PST-DRG-B000030
 * Module          : Ordering					
 * Process Outline :Create Monthly Order Take Base Period
 *
 * <Revision History>
 * Date       Name(Company Name)             Description 
 * ---------- ------------------------------ ---------------------
 * 02-11-2015  	  z015060(RNTBCI)               New Creation
 *
 */
package com.nissangroups.pd.b000030.processor;

import static com.nissangroups.pd.util.PDConstants.BATCH_CONFIG_CLASSPATH;
import static com.nissangroups.pd.util.PDConstants.DOLLAR;
import static com.nissangroups.pd.util.PDConstants.INSIDE_METHOD;
import static com.nissangroups.pd.util.PDConstants.OUTSIDE_METHOD;
import static com.nissangroups.pd.util.PDConstants.PERSISTENCE_NAME;
import static com.nissangroups.pd.util.PDConstants.READ_COUNT;
import static com.nissangroups.pd.util.PDConstants.READ_SKIPPED_COUNT;
import static com.nissangroups.pd.util.PDConstants.WRITE_COUNT;
import static com.nissangroups.pd.util.PDConstants.WRITE_SKIPPED_COUNT;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.AfterStep;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import com.nissangroups.pd.b000030.util.B000030Constants;
import com.nissangroups.pd.model.MstMnthOrdrTakeBasePd;
import com.nissangroups.pd.model.MstMnthOrdrTakeBasePdPK;
import com.nissangroups.pd.repository.MnthlyOrdrRepository;
import com.nissangroups.pd.util.CommonUtil;
import com.nissangroups.pd.util.PDConstants;
import com.nissangroups.pd.util.PDMessageConsants;

import static com.nissangroups.pd.util.PDMessageConsants.M00003;
import static com.nissangroups.pd.util.PDMessageConsants.M00113;
import static com.nissangroups.pd.b000030.util.B000030Constants.*;

/**
 * The Class ExtractOrdrTkBsMnthProcessor.
 *
 * @author z015060
 */

@Configuration
@PropertySource(BATCH_CONFIG_CLASSPATH)
@Component(BATCH_ID_B000030)
public class InsNewOrdrTkBsMnthProcessor implements
		ItemProcessor<Long, MstMnthOrdrTakeBasePd> {

	/** Constant LOG */
	private static final Log LOG = LogFactory
			.getLog(InsNewOrdrTkBsMnthProcessor.class.getName());

	/** Constant porCd */
	private String porCd;

	/** Variable entity manager. */
	@PersistenceContext(name = PERSISTENCE_NAME)
	private EntityManager entityManager;

	/** Variable mnthRep */
	@Autowired(required = false)
	private MnthlyOrdrRepository mnthRep;
	
	/** Variable date. */
	Date date = new Date();

	/** Variable create date. */
	Timestamp createDate = new Timestamp(date.getTime());

	@BeforeStep
	public void beforeStep(StepExecution stepExecution) {
		LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR);
		porCd = stepExecution.getJobParameters().getString(
				PDConstants.BATCH_POR_CODE);
		LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);
	}

	/**
	 * This method is to open status count checking for Monthly process and
	 * insertion of new order take base month ProcessId :
	 * P0002,P0003.1,P0003.2,P0003.3
	 * 
	 * @param Long
	 *            the item
	 * @return the MstMnthOrdrTakeBasePd mstMnthOrdr
	 * @throws Exception
	 *             the exception
	 */
	@Override
	public MstMnthOrdrTakeBasePd process(Long item) throws Exception {

		LOG.info("ITEM" + item);
		MstMnthOrdrTakeBasePd mstMnthOrdr = new MstMnthOrdrTakeBasePd();
		MstMnthOrdrTakeBasePdPK mstMnthOrdrTakeBasePdPk = new MstMnthOrdrTakeBasePdPK();

		/** No data Found */
		if (item == 0) {
			LOG.info(M00003);
			CommonUtil.stopBatch();
		}

		/**
		 * P0002.1 Error Message -- Extract the Order take base months in open
		 * status and its stage details.
		 */
		if (item != 1) {
			mnthRep.extractOrdrFrErr(porCd);
		}

		LOG.info("Process P0003");
		/** Extract Maximum Order Take Base Month for given POR */
		String ordrTkBsMnth = mnthRep.extrxctOrdrTkBsMnth(porCd);

		/** Calculating new OrderTKBaseMonth */
		LOG.info("Process P0003.2");
		DateFormat formatter = new SimpleDateFormat(
				PDConstants.DATE_FORMAT_MONTH);
		Date ordrtkBsMnthDt = formatter.parse(ordrTkBsMnth);
		String newMonth = formatter.format(CommonUtil.addMonthToDate(
				ordrtkBsMnthDt, 1));

		LOG.info("Process P0003.3");
		mstMnthOrdrTakeBasePdPk.setOrdrTakeBaseMnth(newMonth);
		mstMnthOrdrTakeBasePdPk.setPorCd(porCd);
		mstMnthOrdr.setStageCd(B000030Constants.STAGE_CD_VAL);
		mstMnthOrdr.setStageSttsCd(B000030Constants.STATUS_STAGE_CD_VAL);
		mstMnthOrdr.setId(mstMnthOrdrTakeBasePdPk);
		mstMnthOrdr.setCrtdBy(B000030Constants.BATCH_ID_B000030);
		mstMnthOrdr.setCrtdDt(createDate);
		mstMnthOrdr.setUpdtdBy(B000030Constants.BATCH_ID_B000030);
		mstMnthOrdr.setUpdtdDt(createDate);
		CommonUtil.logMessage(PDMessageConsants.M00151,
				B000030Constants.CONSTANT_V2, new String[] { newMonth, porCd });
		return mstMnthOrdr;
	}

	/** gets the entity manager and @return the entity manager */
	public EntityManager getEntityManager() {
		return entityManager;
	}

	/** Sets the entity manager and @return the entity manager */
	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	/**
	 * To get the count of row affected in Each Step.
	 *
	 * @param stepExecution
	 *            the step execution
	 */
	@AfterStep
	public void afterStep(StepExecution stepExecution) {
		LOG.info(READ_COUNT + stepExecution.getReadCount());
		LOG.info(WRITE_COUNT + stepExecution.getWriteCount());
		LOG.info(M00113);

	}

}
