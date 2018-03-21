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

import static com.nissangroups.pd.util.PDConstants.BATCH_CONFIG_CLASSPATH;
import static com.nissangroups.pd.util.PDConstants.DATA_INSERTED_MSG;
import static com.nissangroups.pd.util.PDConstants.DATE_TIME_FORMAT;
import static com.nissangroups.pd.util.PDConstants.DOLLAR;
import static com.nissangroups.pd.util.PDConstants.EXCEPTION;
import static com.nissangroups.pd.util.PDConstants.FAILED;
import static com.nissangroups.pd.util.PDConstants.FILE_EXT_XLS;
import static com.nissangroups.pd.util.PDConstants.INSIDE_METHOD;
import static com.nissangroups.pd.util.PDConstants.OUTSIDE_METHOD;
import static com.nissangroups.pd.util.PDConstants.PERSISTENCE_NAME;
import static com.nissangroups.pd.util.PDConstants.R000037_REPORT_PATH;
import static com.nissangroups.pd.util.PDConstants.READ_COUNT;
import static com.nissangroups.pd.util.PDConstants.READ_SKIPPED_COUNT;
import static com.nissangroups.pd.util.PDConstants.REPORT_FEAT_CD;
import static com.nissangroups.pd.util.PDConstants.REPORT_HEADER_CAR_SRS;
import static com.nissangroups.pd.util.PDConstants.REPORT_HEADER_ERROR_MSG;
import static com.nissangroups.pd.util.PDConstants.REPORT_HEADER_OCF_FRAME_CD;
import static com.nissangroups.pd.util.PDConstants.REPORT_HEADER_OCF_LMT;
import static com.nissangroups.pd.util.PDConstants.REPORT_HEADER_ORDR_TK_BS_PRD;
import static com.nissangroups.pd.util.PDConstants.REPORT_HEADER_POR;
import static com.nissangroups.pd.util.PDConstants.REPORT_HEADER_PROD_PRD;
import static com.nissangroups.pd.util.PDConstants.REPORT_OCF_BYR_GRP;
import static com.nissangroups.pd.util.PDConstants.REPORT_OCF_RGN_CD;
import static com.nissangroups.pd.util.PDConstants.REPORT_OCF_SHRT_DES;
import static com.nissangroups.pd.util.PDConstants.REPORT_SUFFIX_R37;
import static com.nissangroups.pd.util.PDConstants.STAR;
import static com.nissangroups.pd.util.PDConstants.STEP_ID;
import static com.nissangroups.pd.util.PDConstants.WRITE_COUNT;
import static com.nissangroups.pd.util.PDConstants.WRITE_SKIPPED_COUNT;
import static com.nissangroups.pd.util.PDMessageConsants.M00171;
import static com.nissangroups.pd.util.PDMessageConsants.M00043;
import static com.nissangroups.pd.util.PDMessageConsants.M00076;
import static com.nissangroups.pd.util.PDMessageConsants.M00113;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import org.springframework.core.env.Environment;

import com.nissangroups.pd.dao.OrderQtyBean;
import com.nissangroups.pd.exception.PdApplicationException;
import com.nissangroups.pd.model.TrnDailyOcfLmt;
import com.nissangroups.pd.model.TrnDailyOcfLmtIf;
import com.nissangroups.pd.model.TrnDailyOcfLmtPK;
import com.nissangroups.pd.repository.FeatMstRepository;
import com.nissangroups.pd.repository.PorCarSrsMstRepository;
import com.nissangroups.pd.repository.QueryParamBean;
import com.nissangroups.pd.util.CommonExcelItemWriter;
import com.nissangroups.pd.util.CommonUtil;
import com.nissangroups.pd.util.IFConstants;
import com.nissangroups.pd.util.PDConstants;
import com.nissangroups.pd.util.PDMessageConsants;

/**
 * 
 * This is the processor class for interface I000043 using the extracted common
 * layer data. Data manipulation will be done.
 * 
 * @author z011479
 *
 */
@Configuration
@PropertySource(BATCH_CONFIG_CLASSPATH)
public class ValidationProcessor implements
		ItemProcessor<TrnDailyOcfLmtIf, List<TrnDailyOcfLmt>> {

	/** Constant LOG. */
	private static final Log LOG = LogFactory.getLog(ValidationProcessor.class
			.getName());

	/** Variable entity manager. */
	@PersistenceContext(name = PERSISTENCE_NAME)
	private EntityManager entityManager;

	@Autowired(required = false)
	private PorCarSrsMstRepository porCarSrsMstRepository;

	@Autowired(required = false)
	private FeatMstRepository featMstRepository;

	/** Variable environment. */
	@Autowired(required = false)
	Environment environment;
	/** Variable interface id. */
	private String interfaceId = null;

	/** Variable seq no. */
	private String seqNo = null;

	/** Variable por cd. */
	private String porCd = null;

	/** Variable Feat Code. */
	private String featCd = null;

	/** Variable Car series */
	private String crSrs = null;

	/** Variable Order Qty */
	private String ordrQty = null;

	QueryParamBean qryParamBean = new QueryParamBean();
	List<Object[]> crGrpQryRslt = new ArrayList<>();
	List<TrnDailyOcfLmt> trnDailyOcfLmtLst = null;
	private String maxAdDt = null;
	private String minAbDt = null;
	private String carGrpCd = null;
	private boolean errorFlg = false;

	private String ordrTkBsMnth = null;
	private String prdMnthError = null;
	private String ocfRgnCd = null;
	private String ocfByrGrp = null;
	private String ocfFrmCd = null;
	private String featureCd = null;
	private String ocfShrtDesc = null;
	private String ocfLmt = null;
	private String errMsg = null;
	private String fileName = null;
	private String weekNo = null;
	List<Object[]> errorRprtLst = new ArrayList<>();
	CommonUtil commonUtil = new CommonUtil();
	String prodDyNo = null;

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
		String errorPath = environment.getProperty(R000037_REPORT_PATH);
		DateFormat dateFormat = new SimpleDateFormat(DATE_TIME_FORMAT);
		fileName = errorPath.trim() + REPORT_SUFFIX_R37
				+ dateFormat.format(new Date()) + FILE_EXT_XLS;
		LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.batch.item.ItemProcessor#process(java.lang.Object) To
	 * Process the COMMON_INTERFACE data
	 */
	@Override
	public List<TrnDailyOcfLmt> process(TrnDailyOcfLmtIf trnDailyOcfLmtIfInpt)
			throws Exception {
		LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR);
		errorFlg = false;
		porCd = trnDailyOcfLmtIfInpt.getId().getPorCd();
		crSrs = trnDailyOcfLmtIfInpt.getId().getCarSrs();
		ordrQty = trnDailyOcfLmtIfInpt.getOcfLimit();
		qryParamBean.setPorCd(porCd);
		qryParamBean.setCarSrs(crSrs);
		qryParamBean.setOcfRgnCd(trnDailyOcfLmtIfInpt.getId().getOcfRegionCd());
		qryParamBean.setByrGrpCd(trnDailyOcfLmtIfInpt.getId()
				.getOcfBuyerGrpCd());
		qryParamBean.setOcfFrmCd(trnDailyOcfLmtIfInpt.getOcfFrmeCd());
		qryParamBean.setFeatShrtDesc(trnDailyOcfLmtIfInpt.getId()
				.getOcfIdntctnCode());
		ordrTkBsMnth = trnDailyOcfLmtIfInpt.getOcfDate().substring(0, 6);
		ocfRgnCd = trnDailyOcfLmtIfInpt.getId().getOcfRegionCd();
		ocfByrGrp = trnDailyOcfLmtIfInpt.getId().getOcfBuyerGrpCd();
		ocfFrmCd = trnDailyOcfLmtIfInpt.getOcfFrmeCd();
		featureCd = trnDailyOcfLmtIfInpt.getFeatCd();
		ocfShrtDesc = trnDailyOcfLmtIfInpt.getId().getOcfIdntctnCode();
		carGrpCd = extractCarGrp();
		extractFeatCd();

		List<TrnDailyOcfLmt> trnDailyOcfLmtLst = vaildateOcfData(trnDailyOcfLmtIfInpt);
		LOG.info(DATA_INSERTED_MSG);
		LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);
		return trnDailyOcfLmtLst;
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
		createErrorReport();
		if (stepExecution.getReadCount() == 0) {
			LOG.info(M00171
					.replace(PDConstants.ERROR_MESSAGE_1,
							PDConstants.INTERFACE43)
					.replace(PDConstants.ERROR_MESSAGE_2, "data")
					.replace(PDConstants.ERROR_MESSAGE_3,
							PDConstants.TBL_CMN_INTRFC_DATA)
					.replace(PDConstants.ERROR_MESSAGE_4, "P0006"));
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
		//Else condition will not occur as the execution falls in anyone of the about condition
		LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);
	}

	/**
	 * P0005.2 This method is used to extract the Car Group Details.
	 * 
	 * @return
	 * @throws PdApplicationException
	 */
	public String extractCarGrp() throws PdApplicationException {

		crGrpQryRslt = porCarSrsMstRepository.getCarGroup(qryParamBean);
		String carGrp = null;
		if (crGrpQryRslt.size() > 0 && crGrpQryRslt.get(0)[0] != null) {
			carGrp = (String) crGrpQryRslt.get(0)[0];
		} else {

			Map<String, String> errorPrm = new HashMap<String, String>();
			errorPrm.put("1", PDConstants.INTERFACE43);
			errorPrm.put("2", IFConstants.DATAPORCD + porCd);
			errorPrm.put("3", IFConstants.PORCARSRSMST);
			errorPrm.put("4", IFConstants.POOO6);
			String errorMessage = commonUtil.getlogErrorMessage(
					PDMessageConsants.M00171, errorPrm);
			throw new PdApplicationException(errorMessage);
		}
		if (carGrp == null) {
			carGrp = crSrs.substring(0, 4);

		}//if carGrp is null so else not required

		return carGrp;
	}

	/**
	 * P0005.4,This method is used to extract the Feature Code details.
	 * 
	 * @return
	 * @throws ParseException
	 */
	public String extractFeatCd() throws ParseException {
		List<Object[]> featDtls = featMstRepository.getFeatDtls(qryParamBean);
		if (featDtls.isEmpty()) {
			Map<String, String> errorPrm = new HashMap<String, String>();
			errorPrm.put("1", PDConstants.INTERFACE43);
			errorPrm.put("2", IFConstants.DATAPORCD + porCd);
			errorPrm.put("3", IFConstants.FEATMST);
			errorPrm.put("4", IFConstants.POOO6);
			LOG.error(commonUtil.getlogErrorMessage(PDMessageConsants.M00171,
					errorPrm));
			errorFlg = true;
			errMsg = PDConstants.FEATURE_CD_NT_EXISTS;
		} else {
			featCd = (String) featDtls.get(0)[0];
			calcDateDtls(featDtls);
		}
		Map<String, String> errorPrm = new HashMap<String, String>();
		errorPrm.put("1", PDConstants.INTERFACE43);
		errorPrm.put("2", IFConstants.DATAPORCD + porCd);
		errorPrm.put("3", IFConstants.FEATMST);
		errorPrm.put("4", IFConstants.POOO6);
		LOG.error(commonUtil.getlogErrorMessage(PDMessageConsants.M00171,
				errorPrm));
		return null;
	}

	/**
	 * P0005.3,This method is used to calculate the Adopt and Abolish date details.
	 * 
	 * @param featDtls
	 * @throws ParseException
	 */
	public void calcDateDtls(List<Object[]> featDtls) throws ParseException {
		for (Object[] featDtlsarr : featDtls) {
			boolean flag = true;
			Date carAdDt = new Date();
			Date featAdDt = CommonUtil
					.convertStringToDate((String) featDtlsarr[1]);
			if (!crGrpQryRslt.isEmpty() && crGrpQryRslt.get(0)[1] != null) {
				carAdDt = CommonUtil.convertStringToDate((String) crGrpQryRslt
						.get(0)[1]);
			}

			/** a) Get Maximum Adopt Date */
			if (featAdDt.compareTo(carAdDt) > 0) {
				maxAdDt = CommonUtil.convertDateToString(featAdDt,
						PDConstants.DATE_FORMAT_MONTH);
			} else {
				maxAdDt = CommonUtil.convertDateToString(carAdDt,
						PDConstants.DATE_FORMAT_MONTH);
			}

			Date featAbDt = CommonUtil
					.convertStringToDate((String) featDtlsarr[2]);
			Date carAbDt = CommonUtil.convertStringToDate((String) crGrpQryRslt
					.get(0)[2]);
			/** b) Get Minimum Abolish Date */
			if (featAbDt.compareTo(carAbDt) < 0) {
				minAbDt = CommonUtil.convertDateToString(featAbDt,
						PDConstants.DATE_FORMAT_MONTH);
			} else {
				minAbDt = CommonUtil.convertDateToString(carAbDt,
						PDConstants.DATE_FORMAT_MONTH);
			}
		}
	}

	/**
	 * This method is used to split the order quantity.
	 * 
	 * @return
	 */
	public List<OrderQtyBean> splitOrdQty() {
		List<OrderQtyBean> ordrQtyLst = new ArrayList<OrderQtyBean>();
		int spltLngth = IFConstants.SPLTLNGTH;
		int mxQtyStart = IFConstants.MXQTYSTART;
		int mxQtyend = IFConstants.MXQTYEND;
		int stndrstart = IFConstants.STNDRSTART;
		int stndrEnd = IFConstants.STNDREND;
		int prdWeekNo = IFConstants.PRDWEEKNO;
		int prdDay = IFConstants.PRDDAY;
		int nxtCount = IFConstants.NXTCOUNT;
		int count = IFConstants.COUNT;
		for (int prdDyNo = PDConstants.PARAM_VALUE; prdDyNo <= ordrQty.length() /spltLngth; prdDyNo++) {
			OrderQtyBean orderQtyBean = new OrderQtyBean();
			if (count > IFConstants.PRODDAYNO) {
				prdWeekNo = prdWeekNo + PDConstants.PARAM_VALUE;
				prdDay = PDConstants.PARAM_VALUE;
				count = PDConstants.PARAM_VALUE;
			}
			orderQtyBean.setPrdDyNo(prdDay);
			orderQtyBean.setPrdWeekNo(prdWeekNo);
			orderQtyBean.setMxQty(ordrQty.substring(mxQtyStart, mxQtyend));
			orderQtyBean.setStndQty(ordrQty.substring(stndrstart, stndrEnd));
			ordrQtyLst.add(orderQtyBean);
			mxQtyStart = mxQtyStart + nxtCount;
			mxQtyend = mxQtyend + nxtCount;
			stndrstart = stndrstart + nxtCount;
			stndrEnd = stndrEnd + nxtCount;
			++count;
			++prdDay;
		}
		return ordrQtyLst;
	}

	/**
	 * This method is used to validate the Ocf date based on the adopt abolish
	 * date.
	 * 
	 * @param trnDailyOcfLmtIfInpt
	 * @return  trnDailyOcfLmtLst
	 */
	public List<TrnDailyOcfLmt> vaildateOcfData(
			TrnDailyOcfLmtIf trnDailyOcfLmtIfInpt) {
		List<TrnDailyOcfLmt> trnDailyOcfLmtLst = new ArrayList<TrnDailyOcfLmt>();
		List<OrderQtyBean> ordrDtls = splitOrdQty();
		int adptDt = IFConstants.ADPTDT;
		int ablshDt = IFConstants.ABLSDT;
		int prdMnth = IFConstants.PRDMNTH;
		if (!errorFlg) {
			adptDt = CommonUtil.stringtoInt(maxAdDt);
			ablshDt = CommonUtil.stringtoInt(minAbDt);
			prdMnth = CommonUtil.stringtoInt(trnDailyOcfLmtIfInpt.getId()
					.getProdMnth());
		}
		if (!(adptDt <= prdMnth && ablshDt >= prdMnth) && !errorFlg) {
			errorFlg = true;
			errMsg = PDConstants.OCF_LMT_ELIMINATED;
		}//if adptDt is always between <= or >= production month else condition not need

		for (OrderQtyBean ordrDtl : ordrDtls) {
			prodDyNo = String.valueOf(ordrDtl.getPrdDyNo());
			TrnDailyOcfLmt trnMnthlyOcf = new TrnDailyOcfLmt();
			TrnDailyOcfLmtPK trnMnthlyOcfPk = new TrnDailyOcfLmtPK();
			trnMnthlyOcfPk.setPorCd(trnDailyOcfLmtIfInpt.getId().getPorCd());
			prdMnthError = trnDailyOcfLmtIfInpt.getId().getProdMnth();
			trnMnthlyOcfPk.setProdMnth(trnDailyOcfLmtIfInpt.getId()
					.getProdMnth());
			trnMnthlyOcfPk.setCarSrs(trnDailyOcfLmtIfInpt.getId().getCarSrs());
			trnMnthlyOcfPk.setOcfRegionCd(trnDailyOcfLmtIfInpt.getId()
					.getOcfRegionCd());
			trnMnthlyOcfPk.setOcfBuyerGrpCd(trnDailyOcfLmtIfInpt.getId()
					.getOcfBuyerGrpCd());
			trnMnthlyOcfPk.setProdPlntCd(trnDailyOcfLmtIfInpt.getId()
					.getProdPlntCd());
			trnMnthlyOcfPk.setLineClass(trnDailyOcfLmtIfInpt.getId()
					.getLineClass());
			trnMnthlyOcfPk.setOcfFrmeSrtCd(trnDailyOcfLmtIfInpt.getId()
					.getOcfFrmeSrtCd());
			trnMnthlyOcfPk.setOcfIdntctnCode(trnDailyOcfLmtIfInpt.getId()
					.getOcfIdntctnCode());
			trnMnthlyOcfPk.setProdDayNo(String.valueOf(ordrDtl.getPrdDyNo()));
			trnMnthlyOcf.setOcfFrmeCd(trnDailyOcfLmtIfInpt.getOcfFrmeCd());
			trnMnthlyOcf.setOcfDate(trnDailyOcfLmtIfInpt.getOcfDate());
			trnMnthlyOcf.setFeatCd(featCd);
			trnMnthlyOcf.setOcfLmtQty(CommonUtil
					.convertStringToBigDecimal(ordrDtl.getMxQty()));
			ocfLmt = ordrDtl.getMxQty();
			trnMnthlyOcf.setStndQty(CommonUtil
					.convertStringToBigDecimal(ordrDtl.getStndQty()));
			trnMnthlyOcf.setOrdrTakeBaseMnth(trnDailyOcfLmtIfInpt.getOcfDate()
					.substring(0, 6));
			trnMnthlyOcf.setOrdrBaseBaseWkNo(trnDailyOcfLmtIfInpt.getOcfDate()
					.substring(6, 7));
			trnMnthlyOcfPk.setProdWkNo(String.valueOf(ordrDtl.getPrdWeekNo()));
			weekNo = String.valueOf(ordrDtl.getPrdWeekNo());
			LOG.info("*************** PRODUCTION MONTH ************************"
					+ prdMnthError);
			LOG.info("*************** Week NO ************************"
					+ weekNo);
			LOG.info("*************** Production Day NO ************************"
					+ ordrDtl.getPrdDyNo());
			
			LOG.info("*************** Order Take Base Month ************************"
					+ ordrTkBsMnth);
			trnMnthlyOcfPk.setOcfMdlGrp(carGrpCd);
			trnMnthlyOcf.setCrtdBy(PDConstants.INTERFACE43);
			Date date = new Date();
			Timestamp createDate = new Timestamp(date.getTime());
			trnMnthlyOcf.setCrtdDt(createDate);
			trnMnthlyOcf.setId(trnMnthlyOcfPk);
			if (!errorFlg) {
				trnDailyOcfLmtLst.add(trnMnthlyOcf);
			} else {
				addDatatoErrorReport();
			}
		}
		return trnDailyOcfLmtLst;
	}

	/**
	 * This method is used to add the data to the error report.
	 */
	public void addDatatoErrorReport() {
		Object[] errObj = new Object[11];
		errObj[0] = porCd;
		errObj[1] = ordrTkBsMnth.substring(0, 4) + "-"
				+ ordrTkBsMnth.substring(4, 6) + "-" + "1";
		errObj[2] = prdMnthError.substring(0, 4) + "-"
				+ prdMnthError.substring(4, 6) + "-" + weekNo.substring(0);
		errObj[3] = crSrs;
		errObj[4] = ocfRgnCd;
		errObj[5] = ocfByrGrp;
		errObj[6] = ocfFrmCd;
		errObj[7] = featureCd;
		errObj[8] = ocfShrtDesc;
		errObj[9] = ocfLmt;
		errObj[10] = errMsg;
		errorRprtLst.add(errObj);
	}

	/**
	 * This method is used to create error report.
	 * 
	 */
	public void createErrorReport() {
		CommonExcelItemWriter excelItemWriter = new CommonExcelItemWriter();
		excelItemWriter.setFilePath(fileName);

		excelItemWriter.setHeaders(new String[] { REPORT_HEADER_POR,
				REPORT_HEADER_ORDR_TK_BS_PRD, REPORT_HEADER_PROD_PRD,
				REPORT_HEADER_CAR_SRS, REPORT_OCF_RGN_CD, REPORT_OCF_BYR_GRP,
				REPORT_HEADER_OCF_FRAME_CD, REPORT_FEAT_CD,
				REPORT_OCF_SHRT_DES, REPORT_HEADER_OCF_LMT,
				REPORT_HEADER_ERROR_MSG });

		try {
			Map<String, String> formatMap = new HashMap<String, String>();
			excelItemWriter.createReport(errorRprtLst, formatMap,
					"Error Report");
		} catch (IOException e) {
			LOG.error(EXCEPTION + e);

		}

	}
}
