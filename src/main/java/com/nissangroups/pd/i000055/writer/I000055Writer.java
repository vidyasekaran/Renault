/*
 * System Name            : Post Dragon 
 * Sub system Name 		  : Interface
 * Function ID            : PST-DRG-I000055
 * Module                 : Ordering		
 * Process Outline     	  : Interface To Receive Weekly OCF from Plant															
 *
 * <Revision History>
 * Date       Name(Company Name)             Description 
 * ---------- ------------------------------ ---------------------
 * 05-01-2016  	  z015895(RNTBCI)               Initial Version
 *
 */
package com.nissangroups.pd.i000055.writer;

import static com.nissangroups.pd.util.PDConstants.DOLLAR;
import static com.nissangroups.pd.util.PDConstants.INSIDE_METHOD;
import static com.nissangroups.pd.util.PDConstants.OUTSIDE_METHOD;
import static com.nissangroups.pd.util.PDConstants.PERSISTENCE_NAME;
import static com.nissangroups.pd.util.PDConstants.UNABLE_TO_OBTAIN_TRANS_ENTITY_MGR_MSG;
import static com.nissangroups.pd.util.PDMessageConsants.M00171;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessResourceFailureException;

import com.nissangroups.pd.bean.IfErrorReport;
import com.nissangroups.pd.exception.PdApplicationException;
import com.nissangroups.pd.i000055.util.I000055QueryConstants;
import com.nissangroups.pd.model.TrnDailyOcfLmt;
import com.nissangroups.pd.util.CommonUtil;
import com.nissangroups.pd.util.IFConstants;
import com.nissangroups.pd.util.IfCommonUtil;
import com.nissangroups.pd.util.PDConstants;

/**
 * This Class I000055Writer using to write the data from TrnDailyOcfLmt class
 * table and process it.
 * 
 * @author z015895-dev
 * 
 */
public class I000055Writer implements ItemWriter<TrnDailyOcfLmt> {

	/** Constant LOG. */
	private static final Log LOG = LogFactory.getLog(I000055Writer.class
			.getName());

	/** Variable entity manager. */
	@PersistenceContext(unitName = PERSISTENCE_NAME)
	private EntityManager entityManager;

	/** Variable common utility. */
	@Autowired(required = false)
	IfCommonUtil commonutility;

	/** Variable Job Execution. */
	private JobExecution jobExec;

	/** The Input parameter ocfLmtQty */
	private String ocfLmtQty;

	/** The Input parameter ifFileId */
	private String ifFileId;

	/** The Input parameter porCd */
	private String porCd;

	/**
	 * Marks a method to be called before a Step is executed, which comes after
	 * a StepExecution is created and persisted, but before the first item is
	 * read.
	 * 
	 * @param stepExecution
	 */
	@BeforeStep
	public void beforeStep(StepExecution stepExecution) {
		LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR);
		jobExec = stepExecution.getJobExecution();
		this.porCd = jobExec.getJobParameters().getString(IFConstants.POR_CD);
		this.ifFileId = jobExec.getJobParameters().getString(
				IFConstants.INTERFACE_FILE_ID);
		LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);
	}

	/*
	 * This method is use for write the TrnDailyOcflmt data and process.
	 * Process the supplied data element. Will not be called with any null items
	 * in normal operation.
	 * 
	 * @param items
	 * @throws Exception
	 *             if there are errors(Unable to obtain a transactional Entity
	 *             Manager).
	 */
	@Override
	public void write(List<? extends TrnDailyOcfLmt> items) throws Exception {

		LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR);
		if (entityManager == null) {
			throw new DataAccessResourceFailureException(
					UNABLE_TO_OBTAIN_TRANS_ENTITY_MGR_MSG);
		}
		ocfLmtQty = jobExec.getExecutionContext().get("ocfLmtQty") + "";
		if (!items.isEmpty()) {
			for (TrnDailyOcfLmt dailyOcfLmt : items) {

				String porCd = dailyOcfLmt.getId().getPorCd();
				String prodMnth = dailyOcfLmt.getId().getProdMnth();

				// Get CarGrp Details
				Object[] carGrpDtls = getCrGrpDetails(dailyOcfLmt.getId()
						.getCarSrs(), dailyOcfLmt.getId().getOcfRegionCd(),
						dailyOcfLmt.getId().getOcfBuyerGrpCd(),
						dailyOcfLmt.getOcfFrmeCd(), dailyOcfLmt.getId()
								.getOcfIdntctnCode(),
						dailyOcfLmt.getOrdrTakeBaseMnth(), dailyOcfLmt.getId()
								.getProdMnth(), dailyOcfLmt.getFeatCd(),
						dailyOcfLmt.getOcfLmtQty() + "");
				dailyOcfLmt.getId().setOcfMdlGrp(carGrpDtls[0] + "");

				// Get Feat Details
				// getFeatDtls(carSrs, ocfRgnCd, byrGrpCd, ocfFrmeCd,
				// featShrtDesc, ordrTakBasMnth, prodMnth, featCd, ocfLmt);
				Object[] featDtls = getFeatDtls(
						dailyOcfLmt.getId().getCarSrs(), dailyOcfLmt.getId()
								.getOcfRegionCd(), dailyOcfLmt.getId()
								.getOcfBuyerGrpCd(),
						dailyOcfLmt.getOcfFrmeCd(), dailyOcfLmt.getId()
								.getOcfIdntctnCode(),
						dailyOcfLmt.getOrdrTakeBaseMnth(), dailyOcfLmt.getId()
								.getProdMnth(), dailyOcfLmt.getFeatCd(),
						dailyOcfLmt.getOcfLmtQty() + "");
				dailyOcfLmt.setFeatCd(featDtls[0] + "");

				// delete old records based on porCD and prodMtnh
				if(commonutility.getMapData().get(porCd+""+prodMnth) == null){
					deleteByPorCdAndProdMnth(porCd, prodMnth);
					commonutility.getMapData().put(porCd+""+prodMnth, porCd);
				}			

				if (ocfLmtQty.length() >= 350) {
					int prodWkNo = 1;
					int prodDayNo = 1;
					for (int i = 1; i <= 35; i++) {

						long rowCount = commonutility.getRowCount() + 1;
						String[] qty = calLmtAndStdQty(i);

						dailyOcfLmt.getId().setProdDayNo(prodDayNo + "");
						dailyOcfLmt.getId().setProdWkNo(prodWkNo + "");
						dailyOcfLmt.setOcfLmtQty(new BigDecimal(qty[0]));
						dailyOcfLmt.setStndQty(new BigDecimal(qty[1]));

						// Check Adopt and Abolish Date with ProdMnth if
						// OcfLmtQty != 0
						if (Integer.valueOf(qty[0]) != 0) {
							checkProdMnth(carGrpDtls, featDtls, dailyOcfLmt
									.getId().getProdMnth());
						}

						// insert each new record
						insertTrnDailyOcfLmt(dailyOcfLmt);

						prodWkNo = (prodDayNo == 7) ? prodWkNo + 1 : prodWkNo;
						prodDayNo = (prodDayNo == 7) ? 1 : prodDayNo + 1;

						commonutility.setRowCount(rowCount);
					}
				}
			}
			entityManager.flush();
			LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);

		}
	}

	/**
	 * P0003 Insert the extracted list of data by iteration which fetched from common pool
	 * @param dailyOcfLmt
	 * @throws Exception
	 */
	public void insertTrnDailyOcfLmt(TrnDailyOcfLmt dailyOcfLmt)
			throws Exception {
		// LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR);

		Query query = entityManager
				.createNativeQuery(I000055QueryConstants.insertDailyOcfLmtIfTrn
						.toString());
		query.setParameter(IFConstants.POR_CD, dailyOcfLmt.getId().getPorCd());
		query.setParameter(IFConstants.PROD_MNTH, dailyOcfLmt.getId()
				.getProdMnth());
		query.setParameter(IFConstants.CAR_SRS, dailyOcfLmt.getId().getCarSrs());
		query.setParameter(IFConstants.OCF_REGION_CD, dailyOcfLmt.getId()
				.getOcfRegionCd());
		query.setParameter(IFConstants.OCF_BUYER_GRP_CD, dailyOcfLmt.getId()
				.getOcfBuyerGrpCd());
		query.setParameter(IFConstants.PROD_PLNT_CD, dailyOcfLmt.getId()
				.getProdPlntCd());
		query.setParameter(IFConstants.LINE_CLASS, dailyOcfLmt.getId()
				.getLineClass());
		query.setParameter(IFConstants.OCF_FRME_SRT_CD, dailyOcfLmt.getId()
				.getOcfFrmeSrtCd());
		query.setParameter(IFConstants.OCF_MDL_GRP, dailyOcfLmt.getId()
				.getOcfMdlGrp());
		query.setParameter(IFConstants.OCF_IDNTCTN_CD, dailyOcfLmt.getId()
				.getOcfIdntctnCode());
		query.setParameter(IFConstants.PROD_DAY_NO, dailyOcfLmt.getId()
				.getProdDayNo());
		query.setParameter(IFConstants.OCF_FRME_CD, dailyOcfLmt.getOcfFrmeCd());
		query.setParameter(IFConstants.OCF_DATE, dailyOcfLmt.getOcfDate());
		query.setParameter(IFConstants.FEAT_CD, dailyOcfLmt.getFeatCd());
		query.setParameter(IFConstants.OCF_LMT_QTY, dailyOcfLmt.getOcfLmtQty());
		query.setParameter(IFConstants.STD_QTY, dailyOcfLmt.getStndQty());
		query.setParameter(IFConstants.PROD_WK_NO, dailyOcfLmt.getId()
				.getProdWkNo());
		query.setParameter(IFConstants.ORDR_TAK_BAS_MNTH,
				dailyOcfLmt.getOrdrTakeBaseMnth());
		query.setParameter(IFConstants.ORDR_TAK_BAS_WK_NO,
				dailyOcfLmt.getOrdrBaseBaseWkNo());
		query.setParameter(IFConstants.LAST_WK_SYMBL,
				dailyOcfLmt.getLastWkSymbl());
		query.setParameter(IFConstants.PROCESS_STTS_CD,
				dailyOcfLmt.getProcessSttsCd());
		query.setParameter(IFConstants.CRTD_BY, dailyOcfLmt.getCrtdBy());
		query.setParameter(IFConstants.UPDTD_BY, dailyOcfLmt.getUpdtdBy());
		query.executeUpdate();

		// LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);
	}

	/**
	 * P0005.5 Delete all the records from the DAILY_OCF_LIMIT_TRN table based
	 * on the given POR Code & Production Month
	 */
	public void deleteByPorCdAndProdMnth(String porCd, String prodMnth)
			throws Exception {
		LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR);

		String queryStr = I000055QueryConstants.deleteDailyOcfLmtTrn.toString();		
		Query query = entityManager.createNativeQuery(queryStr);
		query.setParameter(IFConstants.POR_CD, porCd);
		query.setParameter(IFConstants.PROD_MNTH, prodMnth);
		query.executeUpdate();

		LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);
	}

	/**
	 * 5.3 Calculate the number of weeks/days in production month
	 * @param count
	 * @return
	 */
	private String[] calLmtAndStdQty(int count) {

		String[] value = new String[2];
		int lmtQtyStart = (count * 10) - 10;
		int lmtQtyEnd = (count * 10) - 5;
		int stdQtyEnd = count * 10;

		String lmtQty = ocfLmtQty.substring(lmtQtyStart, lmtQtyEnd);
		String stdQty = ocfLmtQty.substring(lmtQtyEnd, stdQtyEnd);
		value[0] = lmtQty;
		value[1] = stdQty;
		return value;
	}

	/**
	 * P0005.2 Query to extract data from MST_POR_CAR_SRS table based on
	 * conditions
	 * @param carSrs
	 * @param ocfRgnCd
	 * @param byrGrpCd
	 * @param ocfFrmeCd
	 * @param featShrtDesc
	 * @param ordrTakBasMnth
	 * @param prodMnth
	 * @param featCd
	 * @param ocfLmt
	 * @throws Exception  PdApplicationException, ParseException
	 */
	@SuppressWarnings("unchecked")
	private Object[] getCrGrpDetails(String carSrs, String ocfRgnCd,
			String byrGrpCd, String ocfFrmeCd, String featShrtDesc,
			String ordrTakBasMnth, String prodMnth, String featCd, String ocfLmt)
			throws PdApplicationException, ParseException {

		LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR);
		String carGrp = null;
		List<Object[]> crGrpQryRslt = null;

		Query crGrpQry = entityManager
				.createNativeQuery(I000055QueryConstants.carGroupQuery
						.toString());

		crGrpQry.setParameter(PDConstants.PRMTR_PORCD, porCd);
		crGrpQry.setParameter(PDConstants.PRMTR_CAR_SRS, carSrs);
		crGrpQryRslt = crGrpQry.getResultList();

		Object[] carGrpDtls = null;

		if (crGrpQryRslt.size() > 0) {
			carGrpDtls = crGrpQryRslt.get(0);
			carGrp = (String) crGrpQryRslt.get(0)[0];
		} else {
			String errMsg = M00171
					.replace(PDConstants.ERROR_MESSAGE_1, ifFileId)
					.replace(PDConstants.ERROR_MESSAGE_2, "data for " + porCd)
					.replace(PDConstants.ERROR_MESSAGE_3,
							PDConstants.POR_CAR_SERIES_MST)
					.replace(PDConstants.ERROR_MESSAGE_4, PDConstants.P0006);
			// writeErrorReport(ordrTakBasPrd, prodPrd, carSrs, ocfRegCd,
			// ocfByrGrpCd, ocfFrmeCd, featCd, ocfShortDesc, ocfLmt, errMsg);
			writeErrorReport(ordrTakBasMnth, prodMnth, carSrs, ocfRgnCd,
					byrGrpCd, ocfFrmeCd, featCd, featShrtDesc, ocfLmt, errMsg);
			commonutility.setRemarks(errMsg);
			throw new PdApplicationException(errMsg);
		}
		if (carGrp == null) {
			carGrp = carSrs.substring(0, 4);
			carGrpDtls[0] = carGrp;
		}

		LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);
		return crGrpQryRslt.get(0);
	}

	/**
	 * P0005.4 Query to extract data from MST_FEAT table
	 * @param carSrs
	 * @param ocfRgnCd
	 * @param byrGrpCd
	 * @param ocfFrmeCd
	 * @param featShrtDesc
	 * @param ordrTakBasMnth
	 * @param prodMnth
	 * @param featCd
	 * @param ocfLmt
	 * @return
	 * @throws PdApplicationException
	 * @throws ParseException
	 */
	@SuppressWarnings("unchecked")
	private Object[] getFeatDtls(String carSrs, String ocfRgnCd,
			String byrGrpCd, String ocfFrmeCd, String featShrtDesc,
			String ordrTakBasMnth, String prodMnth, String featCd, String ocfLmt)
			throws PdApplicationException, ParseException {

		LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR);
		List<Object[]> dtlsQryRslt = null;

		Query dtlsQry = entityManager
				.createNativeQuery(I000055QueryConstants.featureDtlsQuery
						.toString());
		dtlsQry.setParameter(PDConstants.PRMTR_PORCD, porCd);
		dtlsQry.setParameter(PDConstants.PRMTR_CAR_SRS, carSrs);
		dtlsQry.setParameter(PDConstants.PRMTR_OCFRGNCD, ocfRgnCd);
		dtlsQry.setParameter(PDConstants.PRMTR_BYR_GRP_CD, byrGrpCd);
		dtlsQry.setParameter(PDConstants.PRMTR_OCF_FRAME_CD, ocfFrmeCd);
		dtlsQry.setParameter(PDConstants.PRMTR_FEAT_SHRT_DESC, featShrtDesc);
		dtlsQryRslt = dtlsQry.getResultList();

		if (dtlsQryRslt.size() == 0 || dtlsQryRslt.get(0)[0] == null
				|| ("").equals(dtlsQryRslt.get(0)[0])) {
			String errMsg = M00171
					.replace(PDConstants.ERROR_MESSAGE_1, ifFileId)
					.replace(PDConstants.ERROR_MESSAGE_2, "data for " + porCd)
					.replace(PDConstants.ERROR_MESSAGE_3,
							PDConstants.FEATURE_MST)
					.replace(PDConstants.ERROR_MESSAGE_4, PDConstants.P0006);
			// writeErrorReport(ordrTakBasPrd, prodPrd, carSrs, ocfRegCd,
			// ocfByrGrpCd, ocfFrmeCd, featCd, ocfShortDesc, ocfLmt, errMsg);
			writeErrorReport(ordrTakBasMnth, prodMnth, carSrs, ocfRgnCd,
					byrGrpCd, ocfFrmeCd, featCd, featShrtDesc, ocfLmt, errMsg);
			commonutility.setRemarks(errMsg);
			throw new PdApplicationException(errMsg);
		}

		LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);
		return dtlsQryRslt.get(0);

	}

	/**
	 * P0005.3 check the production month based on CAR GROUP,FEATURE CD and Production Month 
	 * @param carGrpDtls
	 * @param featDtls
	 * @param prodMnth
	 * @throws PdApplicationException
	 * @throws ParseException
	 */
	private void checkProdMnth(Object[] carGrpDtls, Object[] featDtls,
			String prodMnth) throws PdApplicationException, ParseException {

		String maxAdDt = "";
		String minAbDt = "";

		LOG.info("***ProdMnth check. FeatAdpt&Ablsh Date :" + featDtls[1] + ","
				+ featDtls[2] + " - CarAdpt&Ablsh Date :" + carGrpDtls[1] + ","
				+ carGrpDtls[2]);

		Date featAdDt = CommonUtil.convertStringToDate((String) featDtls[1]);
		Date carAdDt = CommonUtil.convertStringToDate((String) carGrpDtls[1]);

		/** a) Get Maximum Adopt Date */
		if (featAdDt.compareTo(carAdDt) > 0) {
			maxAdDt = CommonUtil.convertDateToString(featAdDt,
					PDConstants.DATE_FORMAT_MONTH);
		} else {
			maxAdDt = CommonUtil.convertDateToString(carAdDt,
					PDConstants.DATE_FORMAT_MONTH);
		}

		Date featAbDt = CommonUtil.convertStringToDate((String) featDtls[2]);
		Date carAbDt = CommonUtil.convertStringToDate((String) carGrpDtls[2]);
		/** b) Get Minimum Abolish Date */
		if (featAbDt.compareTo(carAbDt) < 0) {
			minAbDt = CommonUtil.convertDateToString(featAbDt,
					PDConstants.DATE_FORMAT_MONTH);
		} else {
			minAbDt = CommonUtil.convertDateToString(carAbDt,
					PDConstants.DATE_FORMAT_MONTH);
		}
		int adptDt = CommonUtil.stringtoInt(maxAdDt);
		int ablDt = CommonUtil.stringtoInt(minAbDt);
		int prdMnth = CommonUtil.stringtoInt(prodMnth);

		if (adptDt <= prdMnth && ablDt >= prdMnth) {
			// do nothing
		} else {
			String errMsg = PDConstants.OCF_LMT_ELIMINATED;
			commonutility.setRemarks(errMsg);
			throw new PdApplicationException(errMsg);
		}
		LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);
	}
    /**
     * This method is used to set the values to  error report file
     * @param ordrTakBasPrd
     * @param prodPrd
     * @param carSrs
     * @param ocfRegCd
     * @param ocfByrGrpCd
     * @param ocfFrmeCd
     * @param featCd
     * @param ocfShortDesc
     * @param ocfLmt
     * @param errMsg
     * @throws ParseException
     */
	private void writeErrorReport(String ordrTakBasPrd, String prodPrd,
			String carSrs, String ocfRegCd, String ocfByrGrpCd,
			String ocfFrmeCd, String featCd, String ocfShortDesc,
			String ocfLmt, String errMsg) throws ParseException {
		LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR);

		IfErrorReport errorReport = new IfErrorReport();
		errorReport.setPorCd(porCd);
		errorReport.setCarSrs(carSrs);
		errorReport.setFeatCd(featCd);
		errorReport.setOcfBuyerGrpCd(ocfByrGrpCd);
		errorReport.setOcfFrmeCd(ocfFrmeCd);
		errorReport.setOcfLmt(ocfLmt);
		errorReport.setOcfRegionCd(ocfRegCd);
		errorReport.setOcfShortDesc(ocfShortDesc);
		errorReport.setOrdrTakBasPrd(ordrTakBasPrd);
		errorReport.setProdPrd(prodPrd);
		errorReport.setErrorMsg(errMsg);

		commonutility.getIfErrList().add(errorReport);

		LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);
	}

}
