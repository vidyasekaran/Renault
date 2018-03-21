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
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.nissangroups.pd.b000020.output.B000020ParamOutput;
import com.nissangroups.pd.b000020.output.B000020ReportDao;
import com.nissangroups.pd.b000020.output.EndItmLvlAllctdordAvgMxOutput;
import com.nissangroups.pd.b000020.output.ExtractFrcstBaseVolOutput;
import com.nissangroups.pd.repository.CommonRepository;
import com.nissangroups.pd.util.CommonUtil;
import com.nissangroups.pd.util.PDConstants;
import com.nissangroups.pd.util.PDMessageConsants;

/**
 * This class is used to calculate the Equal Split Process.
 * 
 * @author z011479
 *
 */
public class EqualSpltProcess {
	/** Constant LOG */
	private static final Log LOG = LogFactory.getLog(EqualSpltProcess.class
			.getName());
	
	Date date = new Date();

	Timestamp createDate = new Timestamp(date.getTime());

	@Autowired(required = false)
	private CommonRepository cmnRep;
	
	@Autowired(required = false)
	private B000020ReportDao objB000020Report;

	/**
	 * Method used to execute the Equal Split Process.
	 * @param mstSpecDtls
	 * @param byrGrpPrBd
	 * @param b000020ParamOutput
	 * @param extractFrcstBaseVolOutput
	 * @param mstSpecDtlsLst
	 * @return
	 */
	public EndItmLvlAllctdordAvgMxOutput executeProcess(Object[] mstSpecDtls,
			B000020ParamOutput b000020ParamOutput,
			ExtractFrcstBaseVolOutput extractFrcstBaseVolOutput,
			List<Object[]> mstSpecDtlsLst) {
		LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR);
		float countOfUkOeiByrId = getCountByrIdAtByrGrpLvl(mstSpecDtls,
				mstSpecDtlsLst);
		float calculatedEqlSpltRatio = calculateEqlSpltRatio(countOfUkOeiByrId);
		float allcOrdQty = calculateAllcOrdQty(calculatedEqlSpltRatio,
				extractFrcstBaseVolOutput, b000020ParamOutput, mstSpecDtls);
		LOG.info("Allocated Order Qty"+allcOrdQty);
		LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);
		return null;
	}

	/**
	 * This method is used to calculate the equal split ratio.
	 * 
	 * @param countOfUkOeiByrId
	 * @return
	 */
	public float calculateEqlSpltRatio(Float countOfUkOeiByrId) {
		LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR);
		float eqlSpltRatio = (1 / countOfUkOeiByrId) * 100;
		LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);
		return eqlSpltRatio;
	}

	/**
	 * This method is used to calculate the Allocated Order Quantity.
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
		String idlMix="";
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
			float frcstVol = ((BigDecimal) nscFrcstVolArry[5]).intValue();
			allcdOrdQtyTemp = (int) (frcstVol * calculatedRatio)/100;
			allcdOrdQty = Math.round(allcdOrdQtyTemp);
			idlMix="Ideal Mix";
			cmnRep.insertRatioDtls(mstSpecDtls, allcdOrdQtyTemp, allcdOrdQty,
					PDConstants.EQL_PRCSS_FLG);
		} else if (b000020ParamOutput.getProductionStageCd().equalsIgnoreCase(
				PDConstants.TWENTY)) {
			String porCd = (String) mstSpecDtls[0];
			String prdMnth = (String) mstSpecDtls[3];
			String byrGrpCd = (String) mstSpecDtls[4];
			String crSrs = (String) mstSpecDtls[5];
			Map<String, Object[]> nscByrGrpOcfLmt = extractFrcstBaseVolOutput
					.getNscByrGrpOcfLmt();
			Object[] nscFrcstVolArry = nscByrGrpOcfLmt.get(porCd + prdMnth
					+ byrGrpCd + crSrs);
			float ocfLimit = ((BigDecimal) nscFrcstVolArry[4]).intValue();
			allcdOrdQtyTemp = (ocfLimit * calculatedRatio)/100;
			allcdOrdQty = Math.round(allcdOrdQtyTemp);
			cmnRep.insertRatioDtls(mstSpecDtls, allcdOrdQtyTemp, allcdOrdQty,
					PDConstants.EQL_PRCSS_FLG);
			idlMix="'N~N+3";
		}
			
			B000020ReportDao reportDao = new B000020ReportDao();
			String errMsgStrVal = "M00236";
			String m236=mstSpecDtls[0].toString()+"; "+mstSpecDtls[4].toString()+" ;"+mstSpecDtls[5].toString()+"; "+
					mstSpecDtls[3].toString()+" ;"+b000020ParamOutput.getOrdTkBsMnth()+" ;"+b000020ParamOutput.getProductionStageCd()+
					" "+b000020ParamOutput.getAvgCalBy() +" ;"+idlMix+" ;"+idlMix;
			reportDao.setPor(mstSpecDtls[0].toString());
			reportDao.setCarSrs(mstSpecDtls[5].toString());
			reportDao.setByrGrp(mstSpecDtls[4].toString());
			reportDao.setProdMnth(mstSpecDtls[3].toString());
			reportDao.setBatchId(PDConstants.BATCH_20_ID);
			reportDao.setOrdrStg(b000020ParamOutput.getProductionStageCd()+"-"+b000020ParamOutput.getStgCode());
			reportDao.setOrdrTkBSMnth(b000020ParamOutput.getOrdTkBsMnth());
			reportDao.setAvgCalBy(b000020ParamOutput.getAvgCalBy());
			reportDao.setColorBrkdwnPrity(idlMix);
			reportDao.setEiBrkPntPrity(idlMix);
			reportDao.setErrId(errMsgStrVal);
			reportDao.setErrType(PDConstants.PRMTR_WARNING);
			reportDao.setErrMsg(PDMessageConsants.M00236.replace(PDConstants.ERROR_MESSAGE_1, PDConstants.BATCH_20_ID)
					.replace(PDConstants.ERROR_MESSAGE_2, m236));
			reportDao.setTime(createDate+"");
			objB000020Report.getReportList().add(reportDao);
			CommonUtil.logMessage(PDMessageConsants.M00236,
					PDConstants.CONSTANT_V2, new String[] {
							PDConstants.BATCH_20_ID ,m236 });
			LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);
		return allcdOrdQty;
	}

	/**
	 * This method is used get the count of OEI Buyer at Buyer Group level.
	 * @param mstSpecArr
	 * @param mstSpecDtlsLst
	 * @return
	 */
	public float getCountByrIdAtByrGrpLvl(Object[] mstSpecArr,
			List<Object[]> mstSpecDtlsLst) {
		LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR);
		Set<Object[]> oeiByrIdSt = new HashSet<Object[]>();
		String porCdKy = (String) mstSpecArr[0];
		String prdMnthKy = (String) mstSpecArr[3];
		String byrGrpCdKy = (String) mstSpecArr[4];
		String carSrsKy = (String) mstSpecArr[5];
		String key = porCdKy+prdMnthKy+byrGrpCdKy+carSrsKy;
		for (Object[] objArry : mstSpecDtlsLst) {
			String porCd = (String) objArry[0];
			String prdMnth = (String) objArry[3];
			String byrGrpCd = (String) objArry[4];
			String carSrs = (String) objArry[5];
			String key2 = porCd+prdMnth+byrGrpCd+carSrs;
			 if(key.equalsIgnoreCase(key2)){
				 oeiByrIdSt.add(objArry);
			 }
		}
		LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);
		return oeiByrIdSt.size();
	}
}