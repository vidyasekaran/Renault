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
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.nissangroups.pd.b000020.output.B000020ParamOutput;
import com.nissangroups.pd.b000020.output.B000020ReportDao;
import com.nissangroups.pd.b000020.output.EndItmLvlAllctdordAvgMxOutput;
import com.nissangroups.pd.b000020.output.ExtractFrcstBaseVolOutput;
import com.nissangroups.pd.exception.PdApplicationException;
import com.nissangroups.pd.model.DevB000020;
import com.nissangroups.pd.repository.CommonRepository;
import com.nissangroups.pd.util.PDConstants;
import com.nissangroups.pd.util.PDMessageConsants;

/**
 * 
 * This class is used to do Calculate the End ItemLevel Average Mix Process.
 * 
 * @author z011479
 *
 */
public class EndItmLvlAllctdordAvgMxProcess {

	/** Constant LOG */
	private static final Log LOG = LogFactory
			.getLog(EndItmLvlAllctdordAvgMxProcess.class.getName());

	boolean avgMxFlg = true;

	DevB000020 devB000020 = new DevB000020();

	@Autowired(required = false)
	private B000020ReportDao objB000020Report;

	boolean noWarnFlg = false;

	Date date = new Date();
	Timestamp createDate = new Timestamp(date.getTime());
	
	@Autowired(required = false)
	private CommonRepository cmnRep;

	@Autowired(required = false)
	private EndItmLvlAllctdordIdlMxProcess endItmLvlAllctdordIdlMxProcess;

	@Autowired(required = false)
	private EqualSpltProcess equalSpltProcess;

	/**
	 * This method is used to calculate the End Item Level Average Mix Ratio.
	 * @param mstSpecDtls
	 * @param byrGrpPrBd
	 * @param b000020ParamOutput
	 * @param idlMxFlg
	 * @param extractFrcstBaseVolOutput
	 * @return EndItmLvlAllctdordAvgMxOutput
	 * @throws ParseException
	 * @throws PdApplicationException
	 */
	public EndItmLvlAllctdordAvgMxOutput executeProcess(Object[] mstSpecDtls,
			Map<String, Object[]> byrGrpPrBd,
			B000020ParamOutput b000020ParamOutput, boolean idlMxFlg,
			ExtractFrcstBaseVolOutput extractFrcstBaseVolOutput,
			List<Object[]> mstSpecDtlsLst) throws ParseException,
			PdApplicationException {
		LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR);
		// P00010.1 Extract the Summarized the N-N+2 Production Month EI Order
		// Qty
		Object[] nMonthOrdrVol = cmnRep.extractNMnthprdMnthQty(mstSpecDtls,
				b000020ParamOutput);

		if (nMonthOrdrVol == null ? false : nMonthOrdrVol[0] == null ? false
				: ((BigDecimal) nMonthOrdrVol[0]).intValue() == 0 ? false
						: true) {

			float nMonthOrdrQty = ((BigDecimal) nMonthOrdrVol[0]).intValue();
			float extractednMonthOrdrByrGrpCdLvlVol = extractMonthOrdrByrGrpCdLvlVol(
					mstSpecDtls, b000020ParamOutput);
			float calculatedAvgMxRatio = calculateIdlMxRatio(nMonthOrdrQty,
					extractednMonthOrdrByrGrpCdLvlVol);
			float allcOrdQty = calculateAllcOrdQty(calculatedAvgMxRatio,
					extractFrcstBaseVolOutput, b000020ParamOutput, mstSpecDtls);
			// Insert the calculated details to the temp table
			if (noWarnFlg && Float.floatToRawIntBits(extractednMonthOrdrByrGrpCdLvlVol) != 0 ) {
				cmnRep.insertRatioDtls(mstSpecDtls, calculatedAvgMxRatio,
						allcOrdQty, PDConstants.AVG_PRCSS_FLG);
			}

		} else if (!idlMxFlg) {
			endItmLvlAllctdordIdlMxProcess.executeProcess(mstSpecDtls,
					byrGrpPrBd, b000020ParamOutput, avgMxFlg,
					extractFrcstBaseVolOutput, mstSpecDtlsLst);

		} else {
			equalSpltProcess.executeProcess(mstSpecDtls, 
					b000020ParamOutput, extractFrcstBaseVolOutput,
					mstSpecDtlsLst);
		}
		LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);

		return null;
	}

	/**
	 * This method is used to extract the Monthly Order Count details at Buyer
	 * Group Code level
	 * @param mstSpecDtls
	 * @return int
	 * @throws ParseException
	 */
	public int extractMonthOrdrByrGrpCdLvlVol(Object[] mstSpecDtls,
			B000020ParamOutput b000020ParamOutput) throws ParseException {
		LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR);
		Object[] sumOfOrdBuyrCdlvl = cmnRep.extractNMnthprdMnthByrGrpLvlQty(
				mstSpecDtls, b000020ParamOutput);
		LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);
		return ((BigDecimal) sumOfOrdBuyrCdlvl[0]).intValue();
	}

	/**
	 * Method is used to calculate Ideal Mix Ratio.
	 * @param extractedIdlMxFrcstVol
	 * @param extractedIdlMxFrcstByrGrpCdLvlVol
	 * @return float.
	 */
	public float calculateIdlMxRatio(float extractedIdlMxFrcstVol,
			float extractedIdlMxFrcstByrGrpCdLvlVol) {
		LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR);
		// Ideal Mix Ratio calculation Process P0009.2
		float idlMxRatio = ((float)extractedIdlMxFrcstVol / extractedIdlMxFrcstByrGrpCdLvlVol) * 100;
		LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);
		return idlMxRatio;
	}

	/**
	 * This method is used to calculate the Allocated Order Quantity.
	 * @param calculatedRatio
	 * @param extractFrcstBaseVolOutput
	 * @param b000020ParamOutput
	 * @param mstSpecDtls
	 * @return float calculated order quantity.
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
				reportDao.setOrdrStg(b000020ParamOutput.getProductionStageCd() + "-"
						+ b000020ParamOutput.getStgCode());
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
			Map<String, Object[]> nscByrGrpOcfLmt = extractFrcstBaseVolOutput.getNscByrGrpOcfLmt();
			Object[] nscByrGrpOcfLmtArry = nscByrGrpOcfLmt.get(porCd + prdMnth
					+ byrGrpCd + crSrs);
			if (nscByrGrpOcfLmtArry != null && ((BigDecimal) nscByrGrpOcfLmtArry[4])
					.intValue() !=0 ) {
				float ocfLimit = ((BigDecimal) nscByrGrpOcfLmtArry[4])
						.intValue();
				allcdOrdQtyTemp = (ocfLimit * calculatedRatio) / 100;
				allcdOrdQty = Math.round(allcdOrdQtyTemp);
				noWarnFlg = true;
			}else{
				// Add to data to error report as warning message
				B000020ReportDao reportDao = new B000020ReportDao();
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
				reportDao.setErrMsg(PDConstants.NO_00_OCF_FOUND);
				reportDao.setBatchId(PDConstants.BATCH_20_ID);
				reportDao.setProdMnth(prdMnth);
				reportDao.setOrdrTkBSMnth(b000020ParamOutput.getOrdTkBsMnth());

				reportDao.setTime(createDate + "");
				objB000020Report.getReportList().add(reportDao);
			}
		}
		LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);
		return allcdOrdQty;
	}
}