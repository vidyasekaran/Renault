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

import static com.nissangroups.pd.util.PDConstants.DOLLAR;
import static com.nissangroups.pd.util.PDConstants.INSIDE_METHOD;
import static com.nissangroups.pd.util.PDConstants.OUTSIDE_METHOD;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.nissangroups.pd.b000020.output.B000020ParamOutput;
import com.nissangroups.pd.b000020.output.B000020ReportDao;
import com.nissangroups.pd.b000020.output.EndItmLvlAllctdordIdlMxOutput;
import com.nissangroups.pd.b000020.output.EndItmOeiByrIdLvlOutput;
import com.nissangroups.pd.b000020.output.ExtractFrcstBaseVolOutput;
import com.nissangroups.pd.exception.PdApplicationException;
import com.nissangroups.pd.repository.CommonRepository;
import com.nissangroups.pd.util.PDConstants;
import com.nissangroups.pd.util.PDMessageConsants;

/**
 * This is class is used to Allocate the order using the ideal mix process.
 * 
 * @author z011479
 *
 */
public class EndItmLvlAllctdordIdlMxProcess {
	/** Constant LOG */
	private static final Log LOG = LogFactory
			.getLog(EndItmLvlAllctdordIdlMxProcess.class.getName());

	Date date = new Date();
	Timestamp createDate = new Timestamp(date.getTime());
	Map<String, List<Object>> endItemOeiByrIdLvlRatio = new HashMap<String, List<Object>>();
	EndItmOeiByrIdLvlOutput endItmOeiByrIdLvlOutput = new EndItmOeiByrIdLvlOutput();
	EndItmLvlAllctdordIdlMxOutput endItmLvlAllctdordIdlMxOutput = new EndItmLvlAllctdordIdlMxOutput();

	boolean idlMxFlg = true;
	boolean noWarnFlg = false;
	
	@Autowired(required = false)
	private CommonRepository cmnRep;

	@Autowired(required = false)
	private EndItmLvlAllctdordAvgMxProcess endItmLvlAllctdordAvgMxProcess;

	@Autowired(required = false)
	private EqualSpltProcess equalSpltProcess;

	@Autowired(required = false)
	private B000020ReportDao objB000020Report;

	/**
	 * P0009.1
	 * 
	 * @param mstSpecDtls
	 * @param byrGrpPrBd
	 * @return
	 * @throws ParseException
	 * @throws PdApplicationException
	 */
	public EndItmLvlAllctdordIdlMxOutput executeProcess(Object[] mstSpecDtls,
			Map<String, Object[]> byrGrpPrBd,
			B000020ParamOutput b000020ParamOutput, boolean avgMxFlg,
			ExtractFrcstBaseVolOutput extractFrcstBaseVolOutput,
			List<Object[]> mstSpecDtlsLst) throws ParseException,
			PdApplicationException {
		Object[] idlMxFrcstVol = cmnRep.extractIdlMxFrcstVol(mstSpecDtls);
		float extractedIdlMxFrcstVol = 0;
		if (idlMxFrcstVol == null ? false : idlMxFrcstVol[3] == null ? false
				: ((BigDecimal) idlMxFrcstVol[3]).intValue() == 0 ? false
						: true) {
			extractedIdlMxFrcstVol = ((BigDecimal) idlMxFrcstVol[3]).intValue();

			// Ideal Mix Process P0009.1

			float extractedIdlMxFrcstByrGrpCdLvlVol = extractIdlMxFrcstByrGrpCdLvl(mstSpecDtls);
			float calculatedIdlMxRatio = calculateIdlMxRatio(
					extractedIdlMxFrcstVol, extractedIdlMxFrcstByrGrpCdLvlVol);
			float allcOrdQty = calculateAllcOrdQty(calculatedIdlMxRatio,
					extractFrcstBaseVolOutput, b000020ParamOutput, mstSpecDtls);
			// Insert the calculated details to the temp table
			if (noWarnFlg && Float.floatToRawIntBits(extractedIdlMxFrcstByrGrpCdLvlVol) != 0) {
				cmnRep.insertRatioDtls(mstSpecDtls, calculatedIdlMxRatio,
						allcOrdQty, PDConstants.IDL_PRCSS_FLG);
			}
			endItmLvlAllctdordIdlMxOutput
					.setEndItmOeiByrIdLvlOutput(endItmOeiByrIdLvlOutput);

		} else {
			Object[] idlMxVol = cmnRep.extractIdlMxVol(mstSpecDtls);
			float extractedIdlMxVol = 0;
			// Ideal Mix Process P0009.2
			if (idlMxVol == null ? false : idlMxVol[2] == null ? false
					: ((BigDecimal) idlMxVol[2]).intValue() == 0 ? false : true) {
				extractedIdlMxVol = ((BigDecimal) idlMxVol[2]).intValue();
				float extractedIdlMxByrGrpCdLvlVol = extractIdlMxByrGrpCdLvl(mstSpecDtls);
				float calculatedIdlMxRatio = calculateIdlMxRatio(
						extractedIdlMxVol, extractedIdlMxByrGrpCdLvlVol);
				float allcOrdQty = calculateAllcOrdQty(calculatedIdlMxRatio,
						extractFrcstBaseVolOutput, b000020ParamOutput,
						mstSpecDtls);
				// Insert the calculated details to the temp table
				if (Float.floatToRawIntBits(extractedIdlMxByrGrpCdLvlVol) != 0) {
					cmnRep.insertRatioDtls(mstSpecDtls, calculatedIdlMxRatio,
							allcOrdQty, PDConstants.IDL_PRCSS_FLG);
				}
				endItmLvlAllctdordIdlMxOutput
						.setEndItmOeiByrIdLvlOutput(endItmOeiByrIdLvlOutput);

			} else if (!avgMxFlg) {
				calculateAvgMxRatio(mstSpecDtls, byrGrpPrBd,
						b000020ParamOutput, extractFrcstBaseVolOutput,
						mstSpecDtlsLst);
			} else {
				equalSpltProcess.executeProcess(mstSpecDtls, 
						b000020ParamOutput, extractFrcstBaseVolOutput,
						mstSpecDtlsLst);
			}

		}
		LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);

		return endItmLvlAllctdordIdlMxOutput;
	}

	/**
	 * @param mstSpecDtls
	 * @return
	 */
	public int extractIdlMxFrcstByrGrpCdLvl(Object[] mstSpecDtls) {
		LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR);
		LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);
		return cmnRep.extractIdlMxFrcstByrGrpCdLvl(mstSpecDtls);

	}

	
	/**
	 * @param mstSpecDtls
	 * @return
	 */
	public int extractIdlMxByrGrpCdLvl(Object[] mstSpecDtls) {
		LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR);
		LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);
		return cmnRep.extractIdlMxByrGrpCdLvl(mstSpecDtls);

	}
	
	/**
	 * This method is used calculate the average Ideal Ratio.
	 * 
	 * @param extractedIdlMxFrcstVol
	 * @param extractedIdlMxFrcstByrGrpCdLvlVol
	 * @return
	 */
	public float calculateIdlMxRatio(float extractedIdlMxFrcstVol,
			float extractedIdlMxFrcstByrGrpCdLvlVol) {
		LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR);
		// Ideal Mix Ratio calculation Process P0009.2
		float idlMxRatio = (extractedIdlMxFrcstVol / extractedIdlMxFrcstByrGrpCdLvlVol) * 100;
		LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);
		return idlMxRatio;
	}

	/**
	 * This method is used calculate the average Mix Ratio.
	 * 
	 * @param mstSpecDtls
	 * @param byrGrpPrBd
	 * @param b000020ParamOutput
	 * @throws ParseException
	 * @throws PdApplicationException
	 * @throws PdApplicationException
	 */
	public void calculateAvgMxRatio(Object[] mstSpecDtls,
			Map<String, Object[]> byrGrpPrBd,
			B000020ParamOutput b000020ParamOutput,
			ExtractFrcstBaseVolOutput extractFrcstBaseVolOutput,
			List<Object[]> mstSpecDtlsLst) throws ParseException,
			PdApplicationException {
		LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR);
		endItmLvlAllctdordAvgMxProcess.executeProcess(mstSpecDtls, byrGrpPrBd,
				b000020ParamOutput, idlMxFlg, extractFrcstBaseVolOutput,
				mstSpecDtlsLst);
		LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);
	}

	/**
	 * This method is used to calculate the Allocated Order Quantity.
	 * 
	 * @param calculatedRatio
	 * @param extractFrcstBaseVolOutput
	 * @param b000020ParamOutput
	 * @param mstSpecDtls
	 * @return
	 */
	public float calculateAllcOrdQty(float calculatedRatio,
			ExtractFrcstBaseVolOutput extractFrcstBaseVolOutput,
			B000020ParamOutput b000020ParamOutput, Object[] mstSpecDtls) {
		LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR);
		int allcdOrdQty = 0;
		float allcdOrdQtyTemp = 0;
		if (b000020ParamOutput.getProductionStageCd().equalsIgnoreCase(
				PDConstants.TEN)) {
			String porCd = (String) mstSpecDtls[0];
			String prdMnth = (String) mstSpecDtls[3];
			String byrGrpCd = (String) mstSpecDtls[4];
			String crSrs = (String) mstSpecDtls[5];
			String ordTkBsMnth = b000020ParamOutput.getOrdTkBsMnth();
			Map<String, Object[]> nscFrcstVolMap = extractFrcstBaseVolOutput
					.getNscFrcstVol();
			Object[] nscFrcstVolArry = nscFrcstVolMap.get(porCd + prdMnth
					+ byrGrpCd + crSrs + ordTkBsMnth);
			if (nscFrcstVolArry != null) {
				float frcstVol = ((BigDecimal) nscFrcstVolArry[5]).intValue();
				allcdOrdQtyTemp = (int) (frcstVol * calculatedRatio) / 100;
				allcdOrdQty = Math.round(allcdOrdQtyTemp);
				noWarnFlg = true;
			} else {
				// Add to data to error report as warning message
				B000020ReportDao reportDao = new B000020ReportDao();
				String m196 = porCd + ":" + byrGrpCd + ";" + crSrs + ";";
				reportDao.setPor(porCd);
				reportDao.setCarSrs(crSrs);
				reportDao.setByrGrp(byrGrpCd);
				reportDao.setOrdrStg(b000020ParamOutput.getProductionStageCd()
						+ "-" + b000020ParamOutput.getStgCode());
				reportDao.setAvgCalBy(b000020ParamOutput.getAvgCalBy());
				reportDao.setEiBrkPntPrity(PDConstants.CONSTANT_N_TO_N_PLUS_3);
				reportDao
						.setColorBrkdwnPrity(PDConstants.CONSTANT_N_TO_N_PLUS_3);
				reportDao.setErrType(PDConstants.PRMTR_WARNING);
				reportDao.setErrMsg(PDMessageConsants.M00196
						.replace(PDConstants.ERROR_MESSAGE_1,
								PDConstants.BATCH_20_ID)
						.replace(PDConstants.ERROR_MESSAGE_2,
								PDConstants.FORECAST_VOLUME)
						.replace(PDConstants.ERROR_MESSAGE_3,
								PDConstants.NSC_FORECAST_VOLUME_TRN)
						.replace(PDConstants.ERROR_MESSAGE_4, m196));
				reportDao.setBatchId(PDConstants.BATCH_20_ID);
				reportDao.setErrId(PDConstants.M196);
				reportDao.setProdMnth(prdMnth);
				reportDao.setOrdrTkBSMnth(b000020ParamOutput.getOrdTkBsMnth());
				reportDao.setTime(createDate + "");
				objB000020Report.getReportList().add(reportDao);
			}
		} else if (b000020ParamOutput.getProductionStageCd().equalsIgnoreCase(
				PDConstants.TWENTY)) {
			String porCd = (String) mstSpecDtls[0];
			String prdMnth = (String) mstSpecDtls[3];
			String byrGrpCd = (String) mstSpecDtls[4];
			String crSrs = (String) mstSpecDtls[5];
			Map<String, Object[]> nscByrGrpOcfLmt = extractFrcstBaseVolOutput
					.getNscByrGrpOcfLmt();
			Object[] nscByrGrpOcfLmtArry = nscByrGrpOcfLmt.get(porCd + prdMnth
					+ byrGrpCd + crSrs);
			if (nscByrGrpOcfLmtArry != null) {
				float ocfLimit = ((BigDecimal) nscByrGrpOcfLmtArry[4])
						.intValue();
				allcdOrdQtyTemp = (ocfLimit * calculatedRatio) / 100;
				allcdOrdQty = Math.round(allcdOrdQtyTemp);
				noWarnFlg = true;
			}
		}
		LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);
		return allcdOrdQty;
	}
}
