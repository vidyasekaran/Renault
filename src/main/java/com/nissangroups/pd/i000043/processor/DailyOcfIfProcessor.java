/*
 * System Name     :Post Dragon 
 * Sub system Name :Interface
 * Function ID     :PST-DRG-I000043
 * Module          :Ordering
 * Process Outline :Interface To Receive Weekly OCF from Plant	
 *
 * <Revision History>
 * Date       Name(Company Name)             Description 
 * ---------- ------------------------------ ---------------------
 * 29-10-2015  	  z014029(RNTBCI)               New Creation
 *
 */
package com.nissangroups.pd.i000043.processor;

import static com.nissangroups.pd.util.PDConstants.DATA_INSERTED_MSG;
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
import org.springframework.beans.factory.annotation.Autowired;

import com.nissangroups.pd.model.CmnInterfaceData;
import com.nissangroups.pd.model.TrnDailyOcfLmtIf;
import com.nissangroups.pd.model.TrnDailyOcfLmtIfPK;
import com.nissangroups.pd.repository.CommonRepository;
import com.nissangroups.pd.repository.QueryParamBean;
import com.nissangroups.pd.util.PDConstants;
import com.nissangroups.pd.util.QueryConstants;

/**
 * 
 * This is the processor class for interface I000043 using the extracted common
 * layer data. Data manipulation will be done.
 * 
 * @author z011479
 * 
 */
public class DailyOcfIfProcessor implements
		ItemProcessor<CmnInterfaceData, TrnDailyOcfLmtIf> {

	/** Constant LOG. */
	private static final Log LOG = LogFactory.getLog(DailyOcfIfProcessor.class
			.getName());

	/** Variable entity manager. */
	@PersistenceContext(name = PERSISTENCE_NAME)
	private EntityManager entityManager;

	/** Variable interface id. */
	private String interfaceId = null;

	/** Variable seq no. */
	private String seqNo = null;

	/** Variable seq no. */
	private String porCd = null;

	/** Variable Feat Code. */
	private String featCd = null;

	/** Variable car series. */
	private String crSrs = null;

	/** Variable production month. */
	private String prdMnth = null;
	/** CommonRepository Service bean injection */
	@Autowired(required = false)
	private CommonRepository cmnRep;

	/** This common interface data is persistent object */
	CmnInterfaceData cmnInterfaceDataTemp = new CmnInterfaceData();

	/**
	 * Variable Query param bean
	 */
	QueryParamBean qryParamBean = new QueryParamBean();

	/* Variable dltflg */
	boolean dltFlg = false;

	/**
	 * Before step.
	 * 
	 * @param stepExecution
	 *            the step execution
	 */
	@BeforeStep
	public void beforeStep(StepExecution stepExecution) {
		LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR);
		interfaceId = stepExecution.getJobParameters().getString(
				PDConstants.INTERFACE_FILE_ID);
		porCd = stepExecution.getJobParameters().getString(
				PDConstants.BATCH_POR_CODE);

		LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);
	}

	/**
	 * P0003 This Method Insert into Daily ocf limit if trn the extracted list
	 * of data by iteration which fetched from the common pool
	 * 
	 * @see org.springframework.batch.item.ItemProcessor#process(java.lang.Object)
	 *      To Process the COMMON_INTERFACE data
	 * @param cmnInterfaceData
	 * @Exception Exception
	 * @return trnMnthlyOcfIf
	 */
	@Override
	public TrnDailyOcfLmtIf process(CmnInterfaceData cmnInterfaceData)
			throws Exception {
		LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR);

		cmnInterfaceDataTemp = cmnInterfaceData;
		crSrs = cmnInterfaceData.getCol3().substring(0, 5);
		seqNo = String.valueOf(cmnInterfaceData.getId().getSeqNo());
		TrnDailyOcfLmtIf trnMnthlyOcfIf = new TrnDailyOcfLmtIf();
		TrnDailyOcfLmtIfPK trnMnthlyOcfIfPk = new TrnDailyOcfLmtIfPK();
		trnMnthlyOcfIfPk.setPorCd(porCd);
		trnMnthlyOcfIfPk.setProdPlntCd(cmnInterfaceData.getCol1());
		trnMnthlyOcfIfPk.setProdMnth(cmnInterfaceData.getCol2());
		trnMnthlyOcfIfPk.setCarSrs(cmnInterfaceData.getCol3());
		trnMnthlyOcfIfPk.setLineClass(cmnInterfaceData.getCol4());
		trnMnthlyOcfIf.setOcfFrmeCd(cmnInterfaceData.getCol5());
		trnMnthlyOcfIfPk.setOcfIdntctnCode(cmnInterfaceData.getCol6());
		trnMnthlyOcfIfPk.setOcfBuyerGrpCd(cmnInterfaceData.getCol7());
		trnMnthlyOcfIf.setOcfLimit(cmnInterfaceData.getCol9());
		trnMnthlyOcfIf.setOcfDate(cmnInterfaceData.getCol11());
		trnMnthlyOcfIfPk.setOcfFrmeSrtCd(cmnInterfaceData.getCol14());
		trnMnthlyOcfIfPk.setOcfRegionCd(cmnInterfaceData.getCol15());
		trnMnthlyOcfIfPk.setOcfMdlGrp(cmnInterfaceData.getCol3()
				.substring(0, 4));
		trnMnthlyOcfIf.setId(trnMnthlyOcfIfPk);
		trnMnthlyOcfIf.setIfFileId(cmnInterfaceData.getCmnFileHdr().getId()
				.getIfFileId());
		trnMnthlyOcfIfPk.setOcfRegionCd(cmnInterfaceData.getCol15());
		LOG.info(DATA_INSERTED_MSG);
		LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);
		return trnMnthlyOcfIf;
	}

	/**
	 * P0006 update the overall status in common file header To get the count of
	 * row affected in Each Step.
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
		} else if (stepExecution.getExitStatus().getExitCode().equals(FAILED)) {

			LOG.error(M00076.replace(PDConstants.ERROR_MESSAGE_1, stepExecution
					.getFailureExceptions().toString()));

		} else if (stepExecution.getReadCount() == stepExecution
				.getWriteCount()) {
			LOG.info(M00113);
		} else if (stepExecution.getReadCount() != stepExecution
				.getWriteCount()) {
			LOG.info(M00043);
		}

		LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);
	}

	/**
	 * This method to be called before an item is passed to an ItemProcessor
	 */
	@BeforeProcess
	public void beforeProcess() {
		LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR);
		prdMnth = getProdMnth();
		if (prdMnth != null && !dltFlg) {
			deleteOldData();
			dltFlg = true;
		}
		LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);
	}

	/**
	 * get Production Month value
	 * 
	 * @return minOrderTkeBaseMnth
	 */
	public String getProdMnth() {
		LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR);
		String minOrderTkeBaseMnth = null;
		String minOrdrTkeMnthQuery = QueryConstants.OrdrTkeMnthQuery.toString();
		Query query = entityManager.createQuery(minOrdrTkeMnthQuery);
		query.setParameter(PDConstants.PRMTRT_INTERFACE_FILE_ID, interfaceId);
		query.setParameter(PDConstants.PRMTRT_INTERFACE_STATUS,
				PDConstants.INTERFACE_UNPROCESSED_STATUS);
		// query.setParameter(PDConstants.PRMTR_PORCD, porCd);
		minOrderTkeBaseMnth = (String) query.getSingleResult();
		LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);
		return minOrderTkeBaseMnth;
	}

	/**
	 * P0005.5: Delete all the records from the DAILY_OCF_LIMIT_IF _TRN table
	 * based on the given POR code and Order take base month
	 */
	public void deleteOldData() {
		cmnRep.deleteDailyOcf(porCd, prdMnth);
	}

}
