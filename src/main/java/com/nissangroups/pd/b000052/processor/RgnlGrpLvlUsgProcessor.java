package com.nissangroups.pd.b000052.processor;

import static com.nissangroups.pd.util.PDConstants.DATE_TIME_FORMAT;
import static com.nissangroups.pd.util.PDConstants.DOLLAR;
import static com.nissangroups.pd.util.PDConstants.EXCEPTION;
import static com.nissangroups.pd.util.PDConstants.FAILED;
import static com.nissangroups.pd.util.PDConstants.FILE_EXT_XLS;
import static com.nissangroups.pd.util.PDConstants.INSIDE_METHOD;
import static com.nissangroups.pd.util.PDConstants.OUTSIDE_METHOD;
import static com.nissangroups.pd.util.PDConstants.PERSISTENCE_NAME;
import static com.nissangroups.pd.util.PDConstants.READ_COUNT;
import static com.nissangroups.pd.util.PDConstants.READ_SKIPPED_COUNT;
import static com.nissangroups.pd.util.PDConstants.REPORT_HEADER_CAR_SRS;
import static com.nissangroups.pd.util.PDConstants.REPORT_HEADER_ERROR_MSG;
import static com.nissangroups.pd.util.PDConstants.REPORT_HEADER_ORDR_TK_BS_PRD;
import static com.nissangroups.pd.util.PDConstants.REPORT_HEADER_WEEK_NO;
import static com.nissangroups.pd.util.PDConstants.REPORT_OCF_RGN_B52;
import static com.nissangroups.pd.util.PDConstants.REPORT_OCF_BYR_GRP;
import static com.nissangroups.pd.util.PDConstants.REPORT_HEADER_CAR_SRS;
import static com.nissangroups.pd.util.PDConstants.REPORT_PLANT_CD_B52;
import static com.nissangroups.pd.util.PDConstants.REPORT_LINE_CLASS;
import static com.nissangroups.pd.util.PDConstants.REPORT_HEADER_OCF_FEAT_CD;
import static com.nissangroups.pd.util.PDConstants.REPORT_HEADER_OCF_FRAME_CD;
import static com.nissangroups.pd.util.PDConstants.REPORT_HEADER_FEAT_DESC_SHRT;
import static com.nissangroups.pd.util.PDConstants.REPORT_HEADER_FEAT_DESC_LONG;
import static com.nissangroups.pd.util.PDConstants.REPORT_HEADER_OCF_LMT;
import static com.nissangroups.pd.util.PDConstants.REPORT_HEADER_OCF_USAGE;
import static com.nissangroups.pd.util.PDConstants.REPORT_HEADER_PROD_PRD;
import static com.nissangroups.pd.util.PDConstants.REPORT_HEADER_BREACH;
import static com.nissangroups.pd.util.PDConstants.REPORT_HEADER_SPEC_DEST;
import static com.nissangroups.pd.util.PDConstants.REPORT_OCF_RGN_CD;
import static com.nissangroups.pd.util.PDConstants.REPORT_PROD_WEEK_NO;
import static com.nissangroups.pd.util.PDConstants.REPORT_QUANTITY;
import static com.nissangroups.pd.util.PDConstants.REPORT_HEADER_POR;
import static com.nissangroups.pd.util.PDConstants.REPORT_HEADER_PRD_MNTH_B52;
import static com.nissangroups.pd.util.PDConstants.STAR;
import static com.nissangroups.pd.util.PDConstants.STEP_ID;
import static com.nissangroups.pd.util.PDConstants.WRITE_COUNT;
import static com.nissangroups.pd.util.PDConstants.WRITE_SKIPPED_COUNT;
import static com.nissangroups.pd.util.PDMessageConsants.M00003;
import static com.nissangroups.pd.util.PDMessageConsants.M00043;
import static com.nissangroups.pd.util.PDMessageConsants.M00076;
import static com.nissangroups.pd.util.PDMessageConsants.M00113;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.AfterStep;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;

import com.nissangroups.pd.b000052.output.B000052MnthlyOutputBean;
import com.nissangroups.pd.b000052.util.B000052Constants;
import com.nissangroups.pd.b000052.util.B000052QueryConstants;
import com.nissangroups.pd.i000039.processor.I000039Processor;
import com.nissangroups.pd.model.TrnBuyerGrpWklyOcfLmt;
import com.nissangroups.pd.model.TrnBuyerGrpWklyOcfLmtPK;
import com.nissangroups.pd.model.TrnMnthProdShdlIf;
import com.nissangroups.pd.model.TrnRegionalWklyOcfLmt;
import com.nissangroups.pd.model.TrnRegionalWklyOcfLmtPK;
import com.nissangroups.pd.repository.QueryParamBean;
import com.nissangroups.pd.repository.RegionalWklyOcfLimitTrnRepository;
import com.nissangroups.pd.repository.TrnBuyerGrpWklyOcfLmtRepository;
import com.nissangroups.pd.util.CommonExcelItemWriter;
import com.nissangroups.pd.util.CommonUtil;
import com.nissangroups.pd.util.IfCommonUtil;
import com.nissangroups.pd.util.PDConstants;
import com.nissangroups.pd.util.PDMessageConsants;

public class RgnlGrpLvlUsgProcessor implements
		ItemProcessor<B000052MnthlyOutputBean, B000052MnthlyOutputBean> {

	@PersistenceContext(name = PERSISTENCE_NAME)
	private EntityManager eMgr;

	private static final Log LOG = LogFactory.getLog
			(RgnlGrpLvlUsgProcessor.class);

	@Autowired(required = false)
	IfCommonUtil commonutility;

	/** Variable environment. */
	@Autowired(required = false)
	Environment environment;
	@Autowired(required = false)
	TrnBuyerGrpWklyOcfLmtRepository trnBuyerGrpWklyOcfLmtRepository;
	@Autowired(required = false)
	RegionalWklyOcfLimitTrnRepository regionalWklyOcfLimitTrnRepository;
	
	QueryParamBean qryParamBean = new QueryParamBean();

	/** Variable POR CD. */
	private String porCd;

	/** Variable Stage Code. */
	private String prcsType;

	private String ordrTkBsMnth;
	private String ordrTkBsPd;
	private String pltLnSmry;
	private String plntCd;
	private String lnCls;
	private String cnstntDay;
	private String featTypeCd;
	private String featTypeCd2;

	private String prodMnth;
	private String prodWkNo;
	private String oseiId;
	private String featCd;

	private String ocfFrameCd;
	private String carSrs;
	private String ocfByrGrpCd;
	private String byrGrpCd;
	private String ocfRngCd;
	private String ocfAllocFlg;
	private String qty;
	private String lqty;

	private String prodDay;

	private boolean updtFlag;
	private int maxProdMnth;
	private int maxProdWk;
	private String weekNo;
	String tempDay = " ";

	

	List<Object[]> errorRprtLst = new ArrayList<>();
	List<Object[]> p6List = new ArrayList<Object[]>();
	List<Object[]> errorList = new ArrayList<Object[]>();

	boolean initFlag;

	public boolean isInitFlag() {
		return initFlag;
	}

	public void setInitFlag(boolean initFlag) {
		this.initFlag = initFlag;
	}

	@Override
	public B000052MnthlyOutputBean process(B000052MnthlyOutputBean item)
			throws Exception {
		LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR);

		porCd = item.getPorCd();
		prcsType = item.getPrcsType();
		ordrTkBsPd = item.getOrdrtkBasePd();
		plntCd = item.getPlntCd();
		lnCls = item.getLnCls();
		pltLnSmry = item.getPltLnSmry();
		cnstntDay = item.getCnstntDay();
		maxProdMnth = item.getMaxProdMnth();
		maxProdWk = item.getMaxProdWk();
		
		qryParamBean.setPorCd(porCd);
		qryParamBean.setPrcsType(prcsType);
		qryParamBean.setOrdrTkBsPrd(ordrTkBsPd);
		qryParamBean.setPlntCd(plntCd);
		qryParamBean.setLineClass(lnCls);
		qryParamBean.setPlntLneSummary(pltLnSmry);
		qryParamBean.setCnstDayNo(cnstntDay);
		qryParamBean.setMaxProdMnth(maxProdMnth);
		qryParamBean.setMaxProdWk(maxProdWk);

		setInitFlag(true);

		LOG.info("COnstant day value from rgnl lvl :" + cnstntDay);
		LOG.info("order take base period  :" + ordrTkBsPd);
		LOG.info("P5 list :" + item.getP5List().size());

		List<Object[]> breachList = new ArrayList<>();

		for (Object[] rowObject : item.getP5List()) {

			errorList = trnBuyerGrpWklyOcfLmtRepository.fetchrngnlUsgQty(rowObject, qryParamBean);
			p6List.addAll(errorList);

		}
		LOG.info("Size of P6 :" + p6List.size());

		if (p6List == null || p6List.size() == 0) {
			LOG.info(B000052Constants.M00158
					.replace(PDConstants.ERROR_MESSAGE_1, PDConstants.B000052)
					.replace(PDConstants.ERROR_MESSAGE_2,
							B000052Constants.RGNLGRPLVLUSAGE)
					.replace(PDConstants.ERROR_MESSAGE_3, porCd)
					.replace(PDConstants.ERROR_MESSAGE_4,
							B000052Constants.BYRWKLYOCFUSAGE));
			CommonUtil.stopBatch();
		}

		for (Object[] rowObject : p6List) {
			if (rowObject[0] != null && rowObject[1] != null) {
				porCd = (String) rowObject[0];
				prodMnth = (String) rowObject[1];
				prodWkNo = String.valueOf(rowObject[2]);
				carSrs = (String) rowObject[3];
				featCd = String.valueOf(rowObject[4]);
				ocfFrameCd = (String) rowObject[5];
				byrGrpCd = String.valueOf(rowObject[6]);
				lqty = String.valueOf(rowObject[7]);
				qty = String.valueOf(rowObject[8]);
				featTypeCd = (String) rowObject[9];
				plntCd = String.valueOf(rowObject[10]);
				lnCls = String.valueOf(rowObject[11]);
				prodDay = String.valueOf(rowObject[12]);
				ocfRngCd = String.valueOf(rowObject[13]);
				ocfByrGrpCd = String.valueOf(rowObject[14]);

				if (prodDay.length() == 1) {
					prodDay = prodDay + " ";
					LOG.info("Length of prod Day after space included :"
							+ prodDay.length());
				}

				// P6.2

				qryParamBean.setPorCd(porCd);
				qryParamBean.setPrdMnth(prodMnth);
				qryParamBean.setPrdMnthWkNo(prodWkNo);
				qryParamBean.setFeatCd(featCd);
				qryParamBean.setFeatTypCd(featTypeCd);
				qryParamBean.setOcfFrmCd(ocfFrameCd);
				qryParamBean.setCarSrs(carSrs);
				qryParamBean.setByrGrpCd(byrGrpCd);
				qryParamBean.setPlntCd(plntCd);
				qryParamBean.setLineClass(lnCls);
				qryParamBean.setPrdDayNo(prodDay);
				qryParamBean.setOcfRgnCd(ocfRngCd);
				
				qryParamBean.setOcfByrGrpCd(ocfByrGrpCd);
				qryParamBean.setQty(qty);
				qryParamBean.setLimtQty(lqty);


				if (isInitFlag()) {
					initUsgQty(qryParamBean);
					setInitFlag(false);

				}

				updtFlag = regionalWklyOcfLimitTrnRepository.isListExist(qryParamBean);

				if (updtFlag) {

					int updtSts = rnglLvlLimitUpdate(qryParamBean);
					LOG.info("Updated Flag value :" + updtFlag);
					if (updtSts > 0) {
						LOG.info(PDMessageConsants.M00163
								.replace(PDConstants.ERROR_MESSAGE_1, PDConstants.B000052)
								.replace(PDConstants.ERROR_MESSAGE_2,
										PDConstants.UPDATED)
								.replace(PDConstants.ERROR_MESSAGE_3,
										B000052Constants.RNGLVLOCFUSAGE));

					} else {

						LOG.error(PDMessageConsants.M00164
								.replace(PDConstants.ERROR_MESSAGE_1, PDConstants.B000052)
								.replace(PDConstants.ERROR_MESSAGE_2,
										PDConstants.UPDATED)
								.replace(PDConstants.ERROR_MESSAGE_3,
										B000052Constants.RNGLVLOCFUSAGE));
						CommonUtil.stopBatch();

					}
				} else {

					int insrtFlag = rnglLvlLimitInsert(qryParamBean);
					if (insrtFlag > 0) {
						LOG.info(PDMessageConsants.M00163
								.replace(PDConstants.ERROR_MESSAGE_1, PDConstants.B000052)
								.replace(PDConstants.ERROR_MESSAGE_2,
										PDConstants.INSERTED)
								.replace(PDConstants.ERROR_MESSAGE_3,
										B000052Constants.RNGLVLOCFUSAGE));

					} else {

						LOG.error(PDMessageConsants.M00164
								.replace(PDConstants.ERROR_MESSAGE_1, PDConstants.B000052)
								.replace(PDConstants.ERROR_MESSAGE_2,
										PDConstants.INSERTED)
								.replace(PDConstants.ERROR_MESSAGE_3,
										B000052Constants.RNGLVLOCFUSAGE));
						CommonUtil.stopBatch();

					}

				}

			}

			else {
				// LOG.error(M00003);
				CommonUtil.stopBatch();
			}

			breachList.add(new Object[] { porCd, prodMnth, prodWkNo, carSrs,
					ocfRngCd, ocfByrGrpCd, featCd, ocfFrameCd, qty, featTypeCd,
					plntCd, lnCls, prodDay, lqty, byrGrpCd });

		}

		report(maxProdMnth,maxProdWk);
		LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);
		return item;
	}

	public void report(int maxProdMnth,int maxProdWk) {

		int test = p6List.size();
		//QueryParamBean qryParamBean = new QueryParamBean();

		for (Object[] rowObject : p6List) {

			if (rowObject[0] != null && rowObject[1] != null) {
				porCd = (String) rowObject[0];
				prodMnth = (String) rowObject[1];
				prodWkNo = String.valueOf(rowObject[2]);
				carSrs = (String) rowObject[3];
				featCd = String.valueOf(rowObject[4]);
				ocfFrameCd = (String) rowObject[5];
				byrGrpCd = String.valueOf(rowObject[6]);
				lqty = String.valueOf(rowObject[7]);
				qty = String.valueOf(rowObject[8]);
				featTypeCd = (String) rowObject[9];
				plntCd = String.valueOf(rowObject[10]);
				lnCls = String.valueOf(rowObject[11]);
				prodDay = String.valueOf(rowObject[12]);
				ocfRngCd = String.valueOf(rowObject[13]);
				ocfByrGrpCd = String.valueOf(rowObject[14]);
				
				qryParamBean.setPorCd(porCd);
				qryParamBean.setPrdMnth(prodMnth);
				qryParamBean.setPrdMnthWkNo(prodWkNo);
				qryParamBean.setFeatCd(featCd);
				qryParamBean.setFeatTypCd(featTypeCd);
				qryParamBean.setOcfFrmCd(ocfFrameCd);
				qryParamBean.setCarSrs(carSrs);
				qryParamBean.setByrGrpCd(byrGrpCd);
				qryParamBean.setPlntCd(plntCd);
				qryParamBean.setLineClass(lnCls);
				qryParamBean.setPrdDayNo(prodDay);
				qryParamBean.setOcfRgnCd(ocfRngCd);
				
				qryParamBean.setOcfByrGrpCd(ocfByrGrpCd);
				qryParamBean.setQty(qty);
				qryParamBean.setLimtQty(lqty);
				
				
				int lqtyNum;
				// int ocffrmNum = CommonUtil.stringtoInt(ocfFrameCd);
				int qtyNum = CommonUtil.stringtoInt(qty);

				if ("null".equalsIgnoreCase(lqty) || PDConstants.B52ZERO.equalsIgnoreCase(lqty)) {
					lqtyNum = 0;
				} else {
					lqtyNum = CommonUtil.stringtoInt(lqty);
				}

				if (ocfFrameCd.equalsIgnoreCase(PDConstants.B52DBLZERO)) {
					if (lqtyNum == 0 || lqtyNum != qtyNum) {
						breachReport(qryParamBean,maxProdMnth, maxProdWk);
					}
				}
				if (!(ocfFrameCd.equalsIgnoreCase(PDConstants.B52DBLZERO))) {
					if (qtyNum > lqtyNum) {
						breachReport(qryParamBean,maxProdMnth, maxProdWk);
					}
				}

			}

		}

		LOG.info("size of error report list befor createReport()  :"
				+ errorRprtLst.size());
		createErrorReport();
	}

	

	public void initUsgQty(QueryParamBean qpBean) {

		LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR);
		int initFlag = regionalWklyOcfLimitTrnRepository.initUsgQty(qpBean);
		LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);

	}

	public int rnglLvlLimitUpdate(QueryParamBean qpBean) {
		LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR);
		
		String ordrTkBsMnth = qpBean.getOrdrTkBsPrd().substring(0, 6);
		String weekNo = qpBean.getOrdrTkBsPrd().substring(6);
		int updtCount = regionalWklyOcfLimitTrnRepository.rnglLvlLimitUpdateQuery(qpBean);
		
		LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);
		return updtCount;
	}

	public int rnglLvlLimitInsert(QueryParamBean qpBean) {
		LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR);
		int insrtCount= regionalWklyOcfLimitTrnRepository.rnglLvlLimitInsertQuery(qpBean);
		
		LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);
		return insrtCount;
	}

	

	
	
	public void breachReport(QueryParamBean qpBean,int maxProdMnth, int maxProdWk) {
		LOG.info("Breach report in Progress");
		writeDataToErrorReport(qpBean,maxProdMnth, maxProdWk);

	}

	public void writeDataToErrorReport(QueryParamBean qpBean,int maxProdMnth,int maxProdWk) {
		List<Object[]> errorList = regionalWklyOcfLimitTrnRepository.getErrorData(qpBean,maxProdMnth, maxProdWk);
		LOG.info("size of error report list from getErrorData() :"
				+ errorList.size());
		if (errorList.size() == 0) {
			LOG.info(B000052Constants.M00158
					.replace(PDConstants.ERROR_MESSAGE_1, PDConstants.B000052)
					.replace(PDConstants.ERROR_MESSAGE_2,
							B000052Constants.BREACHINFO)
					.replace(PDConstants.ERROR_MESSAGE_3, porCd)
					.replace(PDConstants.ERROR_MESSAGE_4,
							B000052Constants.REPORTTABLE));
			CommonUtil.stopBatch();
		}
		for (Object[] errorData : errorList) {
			Object[] errorArr = new Object[17];

			errorArr[0] = errorData[0]; // por
			errorArr[1] = ordrTkBsPd;
			errorArr[2] = errorData[1]; // prod mnth
			errorArr[3] = errorData[2]; // prod wk
			errorArr[4] = errorData[3]; // ocf rgn cd
			errorArr[5] = errorData[4]; // ocf byrgrp cd
			errorArr[6] = errorData[5]; // car srs
			errorArr[7] = errorData[6]; // plant cd
			errorArr[8] = errorData[7]; // line class
			errorArr[9] = errorData[8];// feat cd
			errorArr[10] = errorData[9]; // ocf frame cd
			errorArr[11] = errorData[10]; // short
			errorArr[12] = errorData[11]; // long
			errorArr[13] = errorData[12]; // ocf limit qty
			errorArr[14] = errorData[13]; // usg Qty
			// errorArr[15]=errorData[14]; //feat type cd

			String tempUsgQty = String.valueOf(errorArr[14]);
			String tempLmtQty = String.valueOf(errorArr[13]);

			if (tempUsgQty.equalsIgnoreCase("null")) {
				tempUsgQty = PDConstants.B52ZERO;
			}
			if (tempLmtQty.equalsIgnoreCase("null")) {
				tempLmtQty = PDConstants.B52ZERO;
			}

			int rptUsgQty = Integer.parseInt(tempUsgQty);
			int rptLmtQty = Integer.parseInt(tempUsgQty);
			if(!PDConstants.B52BLANK.equals(rptLmtQty)){
				int diffQty = rptUsgQty - rptLmtQty;
				errorArr[15] = diffQty;
				if (diffQty == 0) {
					errorArr[15] = PDConstants.B52HASH;
				}
				
			}
			
			if (!String.valueOf(errorArr[10]).equalsIgnoreCase(PDConstants.B52DBLZERO)) {
				if (rptUsgQty > rptLmtQty) {
					errorArr[16] = B000052Constants.errorMsg1;
				}

			}
			if (String.valueOf(errorArr[10]).equalsIgnoreCase(PDConstants.B52DBLZERO)) {
				if (rptUsgQty != rptLmtQty) {
					errorArr[16] = B000052Constants.errorMsg2;
					if (rptLmtQty == 0) {
						errorArr[16] = B000052Constants.errorMsg2 + " " + B000052Constants.errorMsg3;
					}
				}
				if (rptLmtQty == 0) {
					errorArr[16] = B000052Constants.errorMsg3;
				}
			}
			if(!" ".equals(rptLmtQty)){
			errorRprtLst.add(errorArr);
			}
			LOG.info("size of error report list in side write() :"
					+ errorRprtLst.size());
		}

	}

	

	public void createErrorReport() {
		String fileName = null;
		String errorPath = environment
				.getProperty(B000052Constants.BREACH_REPORT_PATH);
		DateFormat dateFormat = new SimpleDateFormat(DATE_TIME_FORMAT);
		if (prcsType.equalsIgnoreCase(PDConstants.B52MONTH)) {
			fileName = errorPath.trim()
					+ B000052Constants.REPORT_SUFFIX_BREACH_MONTHLY
					+ dateFormat.format(new Date()) + FILE_EXT_XLS;
		}
		if (prcsType.equalsIgnoreCase(PDConstants.B52WEEK)) {
			fileName = errorPath.trim()
					+ B000052Constants.REPORT_SUFFIX_BREACH_WEEKLY
					+ dateFormat.format(new Date()) + FILE_EXT_XLS;
		}
		CommonExcelItemWriter excelItemWriter = new CommonExcelItemWriter();
		excelItemWriter.setFilePath(fileName);

		excelItemWriter.setHeaders(new String[] { REPORT_HEADER_POR,
				REPORT_HEADER_ORDR_TK_BS_PRD, REPORT_HEADER_PRD_MNTH_B52,
				REPORT_HEADER_WEEK_NO, REPORT_OCF_RGN_B52, REPORT_OCF_BYR_GRP,
				REPORT_HEADER_CAR_SRS, REPORT_PLANT_CD_B52, REPORT_LINE_CLASS,
				REPORT_HEADER_OCF_FEAT_CD, REPORT_HEADER_OCF_FRAME_CD,
				REPORT_HEADER_FEAT_DESC_SHRT, REPORT_HEADER_FEAT_DESC_LONG,
				REPORT_HEADER_OCF_LMT, REPORT_HEADER_OCF_USAGE,
				REPORT_HEADER_BREACH, REPORT_HEADER_ERROR_MSG });

		try {
			Map<String, String> formatMap = new HashMap<String, String>();
			/*
			 * formatMap.put("0", "YYYY-MM"); formatMap.put("2", "YYYY-MM");
			 */

			excelItemWriter.createReport(errorRprtLst, formatMap,
					"Error Report");
		} catch (IOException e) {
			LOG.error(EXCEPTION + e);

		}

	}

}
