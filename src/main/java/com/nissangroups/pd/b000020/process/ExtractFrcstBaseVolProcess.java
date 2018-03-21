/*
 * System Name     :Post Dragon 
 * Sub system Name :Batch
 * Function ID     :PST-DRG-B000020
 * Module          :O Ordering
 * Process Outline :Forecast Order Creation (N+3) Onwards (Draft & Final)
 *
 * <Revision History>
 * Date       Name(Company Name)             Description 
 * ---------- ------------------------------ ---------------------
 * 29-10-2015      z011479(RNTBCI)               New Creation
 *
 */
package com.nissangroups.pd.b000020.process;

import static com.nissangroups.pd.b000008.util.B000008Constants.CONSTANT_V4;
import static com.nissangroups.pd.util.PDConstants.DOLLAR;
import static com.nissangroups.pd.util.PDConstants.INSIDE_METHOD;
import static com.nissangroups.pd.util.PDConstants.OUTSIDE_METHOD;

import java.sql.Timestamp;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.nissangroups.pd.b000020.output.B000020ParamOutput;
import com.nissangroups.pd.b000020.output.B000020ReportDao;
import com.nissangroups.pd.b000020.output.ExtractFrcstBaseVolOutput;
import com.nissangroups.pd.b000020.output.ExtractMstSpecOutput;
import com.nissangroups.pd.exception.PdApplicationException;
import com.nissangroups.pd.repository.CommonRepository;
import com.nissangroups.pd.util.CommonUtil;
import com.nissangroups.pd.util.PDConstants;
import com.nissangroups.pd.util.PDMessageConsants;

/**
 * This class is used to extract the forecast base volumes. Process -
 * P0006,P0007
 * 
 * @author z011479
 *
 */
public class ExtractFrcstBaseVolProcess {

	/** Constant LOG. */
	private static final Log LOG = LogFactory
			.getLog(ExtractFrcstBaseVolProcess.class.getName());

	Date date = new Date();
	Timestamp createDate = new Timestamp(date.getTime());

	Map<String, Object[]> nscFrcstVolMap = new HashMap<String, Object[]>();
	Map<String, Object[]> nscByrGrpOcfLmtMap = new HashMap<String, Object[]>();
	Map<String, Object[]> byrGrpPrMap = new HashMap<String, Object[]>();
	Set<String> unqSet = new HashSet<String>();
	
	@Autowired(required = false)
	private CommonRepository cmnRep;

	@Autowired(required = false)
	private B000020ReportDao objB000020Report;


	/**
	 * @param paramOutput
	 * @param mstSpecOutput
	 * @return
	 * @throws ParseException
	 * @throws PdApplicationException
	 */
	public ExtractFrcstBaseVolOutput executeProcess(
			B000020ParamOutput paramOutput, ExtractMstSpecOutput mstSpecOutput)
			throws ParseException, PdApplicationException {
		LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR);
		ExtractFrcstBaseVolOutput extractFrcstBaseVolOutput = new ExtractFrcstBaseVolOutput();
		List<Object[]> brkDwnFrByrAndCrSrsTemp = new ArrayList<Object[]>();

		if (paramOutput.getProductionStageCd()
				.equalsIgnoreCase(PDConstants.TEN)) {

			for (Object[] mstSpecDtls : mstSpecOutput.getMstSpecDtls()) {
				Object[] nscFrcstVolTemp = cmnRep.extractNscFrcstVolDtls(
						paramOutput, mstSpecDtls);
				brkDwnFrByrAndCrSrsTemp = getBrkDwnFrByrAndCrSrs(
						brkDwnFrByrAndCrSrsTemp, paramOutput, mstSpecDtls);

				// TO-DO remove this based on the business condition
				if (nscFrcstVolTemp != null) {
					addNscFrcstVolToMap(nscFrcstVolTemp);
				}
				if (brkDwnFrByrAndCrSrsTemp.size() != 0) {
					// TO-DO logic to be implemented if data is not available.
					addDataToByrGrpOcfMap(brkDwnFrByrAndCrSrsTemp);
				}

			}

		} else if (paramOutput.getProductionStageCd().equalsIgnoreCase(
				PDConstants.TWENTY)) {

			for (Object[] mstSpecDtls : mstSpecOutput.getMstSpecDtls()) {
				List<Object[]> nscByrGrpOcfLmtTemp = cmnRep
						.extractByrGrpOcfDtls(paramOutput, mstSpecDtls);
				brkDwnFrByrAndCrSrsTemp = getBrkDwnFrByrAndCrSrs(
						brkDwnFrByrAndCrSrsTemp, paramOutput, mstSpecDtls);
				if (nscByrGrpOcfLmtTemp != null) {
					addNscByrGrpOcfLmtToMap(nscByrGrpOcfLmtTemp, paramOutput);
				}
				if (brkDwnFrByrAndCrSrsTemp.size() != 0) {
					addDataToByrGrpOcfMap(brkDwnFrByrAndCrSrsTemp);
				}
			}

		}
		extractFrcstBaseVolOutput.setNscFrcstVol(nscFrcstVolMap);
		extractFrcstBaseVolOutput.setNscByrGrpOcfLmt(nscByrGrpOcfLmtMap);
		extractFrcstBaseVolOutput.setByrGrpPrMap(byrGrpPrMap);
		LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);
		return extractFrcstBaseVolOutput;
	}

	/**
	 * This method is used to extract the Break Down Priority data for Car
	 * series and Buyer group condition. Process-P0007 z01889
	 * 
	 * @param brkDwnFrByrAndCrSrsTemp
	 * @param paramOutput
	 * @param mstSpecDtls
	 * @return
	 * @throws PdApplicationException
	 */
	public List<Object[]> getBrkDwnFrByrAndCrSrs(
			List<Object[]> brkDwnFrByrAndCrSrsTemp,
			B000020ParamOutput paramOutput, Object[] mstSpecDtls)
			throws PdApplicationException {
		LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR);
		brkDwnFrByrAndCrSrsTemp = cmnRep.extractBrkDwnFrByrAndCrSrs(
				paramOutput, mstSpecDtls);
		LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);
		return brkDwnFrByrAndCrSrsTemp;
	}

	/**
	 * This method is used to add the Buyer group priority to the map based on
	 * Por Cd,Car Series,Buyer Group Cd.
	 * 
	 * @param byrGrpPr
	 */
	public void addDataToByrGrpOcfMap(List<Object[]> byrGrpPr) {
		LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);
		for (Object[] byrGrpArry : byrGrpPr) {
			String porCd = (String) byrGrpArry[0];
			String carSrs = (String) byrGrpArry[1];
			String byrGrpCd = (String) byrGrpArry[2];
			byrGrpPrMap.put(porCd + carSrs + byrGrpCd, byrGrpArry);
		}
		LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR);
	}

	/**
	 * @param nscFrcstVol
	 */
	public void addNscFrcstVolToMap(Object[] nscFrcstVol) {
		LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR);
		String porCd = (String) nscFrcstVol[0];
		String prdMnth = (String) nscFrcstVol[1];
		String byrGrpCd = (String) nscFrcstVol[2];
		String crSrs = (String) nscFrcstVol[3];
		String ordTkBsMnth = (String) nscFrcstVol[4];
		nscFrcstVolMap.put(porCd + prdMnth + byrGrpCd + crSrs + ordTkBsMnth,
				nscFrcstVol);
		LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);

	}

	/**
	 * @param nscByrGrpOcfLmt
	 * @param b000020ParamOutput
	 * @throws PdApplicationException
	 */
	public void addNscByrGrpOcfLmtToMap(List<Object[]> nscByrGrpOcfLmt,
			B000020ParamOutput b000020ParamOutput)
			throws PdApplicationException {
		LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR);
		String msgStrVal = "M00219";
		unqSet.clear();
		for (Object[] nscByrGrpOcfLmtArry : nscByrGrpOcfLmt) {
			String porCd = (String) nscByrGrpOcfLmtArry[0];
			String prdMnth = (String) nscByrGrpOcfLmtArry[3];
			String byrGrpCd = (String) nscByrGrpOcfLmtArry[1];
			String crSrs = (String) nscByrGrpOcfLmtArry[2];
			String unqKy = porCd + prdMnth + byrGrpCd + crSrs;

			if (unqSet.add(unqKy)) {
				nscByrGrpOcfLmtMap.put(unqKy, nscByrGrpOcfLmtArry);
			} else {
				nscByrGrpOcfLmtMap.remove(unqKy);
				CommonUtil.logMessage(PDMessageConsants.M00219, CONSTANT_V4,
						new String[] {
								PDConstants.BATCH_20_ID,
								PDConstants.FEATURE_CD,
								PDConstants.BUYER_GROUP_MONTHLY_OCF_LIMIT_TRN,
								porCd + ";" + crSrs + ";" + byrGrpCd + ";"
										+ prdMnth });
				// Add to data to error report as warning message
				B000020ReportDao reportDao = new B000020ReportDao();
				String m219 = porCd + ":" + byrGrpCd + ";" + crSrs + ";";
				reportDao.setPor(porCd);
				reportDao.setCarSrs(crSrs);
				reportDao.setByrGrp(byrGrpCd);
				reportDao.setOrdrStg(b000020ParamOutput.getProductionStageCd() + "-"
						+ b000020ParamOutput.getStgCode());
				reportDao.setAvgCalBy(b000020ParamOutput.getAvgCalBy());
				reportDao.setEiBrkPntPrity(PDConstants.CONSTANT_N_TO_N_PLUS_3);
				reportDao
						.setColorBrkdwnPrity(PDConstants.CONSTANT_N_TO_N_PLUS_3);
				reportDao.setErrType(PDConstants.PRMTR_WARNING);
				String errMsg = PDMessageConsants.M00219
						.replace(PDConstants.ERROR_MESSAGE_1,
								PDConstants.BATCH_20_ID)
						.replace(PDConstants.ERROR_MESSAGE_2,
								PDConstants.FEATURE_CD)
						.replace(PDConstants.ERROR_MESSAGE_3,
								PDConstants.BUYER_GROUP_MONTHLY_OCF_LIMIT_TRN)
						.replace(PDConstants.ERROR_MESSAGE_4, m219);
				reportDao.setErrMsg(errMsg);
				reportDao.setBatchId(PDConstants.BATCH_20_ID);
				reportDao.setErrId(msgStrVal);
				reportDao.setProdMnth(prdMnth);
				reportDao.setOrdrTkBSMnth(b000020ParamOutput.getOrdTkBsMnth());
				reportDao.setTime(createDate + "");
				objB000020Report.getReportList().add(reportDao);
				LOG.error("Error:" + errMsg);
				throw new PdApplicationException(PDConstants.NO_DATA_FOUND);
			}
			LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);
		}
	}
}
