/*
 * System Name     :Post Dragon 
 * Sub system Name :Interface
 * Function ID     :PST-DRG-B000020
 * Module          :Monthly
 * Process Outline :Receive Vehicle Production Type Master from Plant																	
 *
 * <Revision History>
 * Date       Name(Company Name)             Description 
 * ---------- ------------------------------ ---------------------
 * 29-06-2015  	  z011479(RNTBCI)               New Creation
 *
 */
package com.nissangroups.pd.repository;

import static com.nissangroups.pd.b000008.util.B000008Constants.CONSTANT_V4;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Session;
import org.hibernate.engine.jdbc.connections.spi.ConnectionProvider;
import org.hibernate.engine.spi.SessionFactoryImplementor;
import org.springframework.beans.factory.annotation.Autowired;

import com.nissangroups.pd.b000020.output.B000020ParamOutput;
import com.nissangroups.pd.b000020.output.B000020ReportDao;
import com.nissangroups.pd.b000020.util.B000020QueryConstants;
import com.nissangroups.pd.exception.PdApplicationException;
import com.nissangroups.pd.i000026.util.I000026QueryConstants;
import com.nissangroups.pd.model.DevB000020;
import com.nissangroups.pd.model.DevB000020ClrLvl;
import com.nissangroups.pd.model.DevB000020ClrLvlPK;
import com.nissangroups.pd.model.DevB000020PK;
import com.nissangroups.pd.model.MnthlyBatchProcessStt;
import com.nissangroups.pd.model.MnthlyBatchProcessSttPK;
import com.nissangroups.pd.model.MstWkNoClndr;
import com.nissangroups.pd.util.CommonUtil;
import com.nissangroups.pd.util.PDConstants;
import com.nissangroups.pd.util.PDMessageConsants;
import com.nissangroups.pd.util.QueryConstants;

/**
 * 
 * This Class is used to extract the data from the database.
 * 
 * @author z011479
 *
 */
public class CommonRepository {

	/** Variable entity manager. */
	@PersistenceContext(name = PDConstants.PERSISTENCE_NAME)
	private EntityManager entityMngr;

	private static final Log LOG = LogFactory.getLog(CommonRepository.class
			.getName());

	@Autowired(required = false)
	private B000020ReportDao objB000020Report;

	Date date = new Date();
	BigDecimal zero = new BigDecimal(0);
	Timestamp createDate = new Timestamp(date.getTime());

	String andByrGrpCdQryStr = B000020QueryConstants.andByrGrpCdQry.toString();
	String andOcfRgnCdQryStr = B000020QueryConstants.andOcfRgnCdQry.toString();
	String andOcfByrCdQryStr = B000020QueryConstants.andOcfByrCdQry.toString();

	@SuppressWarnings("unchecked")
	public List<Object[]> getByrGrp(B000020ParamOutput paramOutput) {
		String extractByrGrpStr = B000020QueryConstants.extractByrGrpQry
				.toString();

		if (paramOutput.getByrGrp() != null
				&& !paramOutput.getByrGrp().equals(PDConstants.SCREEN_ALL)) {
			extractByrGrpStr += andByrGrpCdQryStr;
		}
		if (paramOutput.getOcfRgnCd() != null
				&& !paramOutput.getOcfRgnCd().equals(PDConstants.SCREEN_ALL)) {
			extractByrGrpStr += andOcfRgnCdQryStr;
		}
		if (paramOutput.getOcfByrGrp() != null
				&& !paramOutput.getOcfByrGrp().equals(PDConstants.SCREEN_ALL)) {
			extractByrGrpStr += andOcfByrCdQryStr;
		}
		Query extractByrGrpQry = entityMngr.createNativeQuery(extractByrGrpStr);
		extractByrGrpQry.setParameter(PDConstants.PRMTR_PORCD,
				paramOutput.getPorCd());
		extractByrGrpQry.setParameter(PDConstants.PRMTR_NSC_EIM_ODER_HRZN_FLAG,
				PDConstants.CONSTANT_F);
		if (paramOutput.getByrGrp() != null
				&& !paramOutput.getByrGrp().equals(PDConstants.SCREEN_ALL)) {
			extractByrGrpQry.setParameter(PDConstants.PRMTR_BYR_GRP_CD,
					paramOutput.getByrGrp());
		}
		if (paramOutput.getOcfRgnCd() != null
				&& !paramOutput.getOcfRgnCd().equals(PDConstants.SCREEN_ALL)) {
			extractByrGrpQry.setParameter(PDConstants.PRMTR_OCFRGNCD,
					paramOutput.getOcfRgnCd());
		}
		if (paramOutput.getOcfByrGrp() != null
				&& !paramOutput.getOcfByrGrp().equals(PDConstants.SCREEN_ALL)) {
			extractByrGrpQry.setParameter(PDConstants.PRMTR_OCFBYRGRPCD,
					paramOutput.getOcfByrGrp());
		}
		return extractByrGrpQry.getResultList();
	}

	/**
	 * This method is used to extract the valid Car series list from the
	 * database.
	 * 
	 * @param paramOutput
	 * @return
	 * @throws ParseException
	 */
	public List<Object[]> getVldCrSrs(B000020ParamOutput paramOutput)
			throws ParseException {
		String prodMnthFrm = paramOutput.getPrdMnthFrm();
		String extractByrGrpStr = B000020QueryConstants.extractCrSrsAndHrznQry
				.toString();
		String extractCrSrsAndHrznQryAndCarSrs = B000020QueryConstants.andHrznQryAndCarSrs
				.toString();
		if (paramOutput.getCrSrs() != null
				&& !paramOutput.getCrSrs().equals(PDConstants.SCREEN_ALL)) {
			extractByrGrpStr += extractCrSrsAndHrznQryAndCarSrs;
		}
		if (paramOutput.getByrGrp() != null
				&& !paramOutput.getByrGrp().equals(PDConstants.SCREEN_ALL)) {
			extractByrGrpStr += andByrGrpCdQryStr;
		}
		if (paramOutput.getOcfRgnCd() != null
				&& !paramOutput.getOcfRgnCd().equals(PDConstants.SCREEN_ALL)) {
			extractByrGrpStr += andOcfRgnCdQryStr;
		}
		if (paramOutput.getOcfByrGrp() != null
				&& !paramOutput.getOcfByrGrp().equals(PDConstants.SCREEN_ALL)) {
			extractByrGrpStr += andOcfByrCdQryStr;
		}
		Query extractCrSrsQry = entityMngr.createNativeQuery(extractByrGrpStr);
		extractCrSrsQry.setParameter(PDConstants.PRMTR_PORCD,
				paramOutput.getPorCd());
		extractCrSrsQry.setParameter(PDConstants.PRMTR_PRDSTGCD,
				paramOutput.getProductionStageCd());
		extractCrSrsQry.setParameter(PDConstants.PRMTR_NSC_EIM_ODER_HRZN_FLAG,
				PDConstants.CONSTANT_F);
		extractCrSrsQry.setParameter(PDConstants.PRMTR_PRD_MNTH_FRM,
				prodMnthFrm + PDConstants.ELEVEN);

		if (paramOutput.getCrSrs() != null
				&& !paramOutput.getCrSrs().equals(PDConstants.SCREEN_ALL)) {
			extractCrSrsQry.setParameter(PDConstants.PRMTR_CAR_SRS,
					paramOutput.getCrSrs());
		}
		if (paramOutput.getByrGrp() != null
				&& !paramOutput.getByrGrp().equals(PDConstants.SCREEN_ALL)) {
			extractCrSrsQry.setParameter(PDConstants.PRMTR_BYR_GRP_CD,
					paramOutput.getByrGrp());
		}
		if (paramOutput.getOcfRgnCd() != null
				&& !paramOutput.getOcfRgnCd().equals(PDConstants.SCREEN_ALL)) {
			extractCrSrsQry.setParameter(PDConstants.PRMTR_OCFRGNCD,
					paramOutput.getOcfRgnCd());
		}
		if (paramOutput.getOcfByrGrp() != null
				&& !paramOutput.getOcfByrGrp().equals(PDConstants.SCREEN_ALL)) {
			extractCrSrsQry.setParameter(PDConstants.PRMTR_OCFBYRGRPCD,
					paramOutput.getOcfByrGrp());
		}

		return extractCrSrsQry.getResultList();
	}

	/**
	 * This method is used to extract the Spec Master details from the database.
	 * 
	 * @param b000020ParamOutput
	 * @return
	 */
	public List<Object[]> getSpecMstDtls(B000020ParamOutput b000020ParamOutput) {
		String getSpecDtlsQryStr = B000020QueryConstants.extractMstSpecDtls
				.toString();
		if (b000020ParamOutput.getCrSrs() != null
				&& !b000020ParamOutput.getCrSrs().equalsIgnoreCase(
						PDConstants.ALL)) {
			getSpecDtlsQryStr += B000020QueryConstants.andExtractMstSpecDtlsHrznQryAndCarSrs;
		}
		if (b000020ParamOutput.getByrGrp() != null
				&& !b000020ParamOutput.getByrGrp().equalsIgnoreCase(
						PDConstants.ALL)) {
			getSpecDtlsQryStr += andByrGrpCdQryStr;
		}
		if (b000020ParamOutput.getPrdMnthTo() != null
				&& !b000020ParamOutput.getPrdMnthTo().equalsIgnoreCase(
						PDConstants.ALL)) {
			getSpecDtlsQryStr += B000020QueryConstants.extractMstSpecDtlsPrdMnthTo;
		}
		Query getSpecDtlsQry = entityMngr.createNativeQuery(getSpecDtlsQryStr);
		getSpecDtlsQry.setParameter(PDConstants.PRMTR_PRD_MNTH_FRM,
				b000020ParamOutput.getPrdMnthFrm());
		getSpecDtlsQry.setParameter(PDConstants.PRMTR_ORDR_TK_BS_MNTH,
				b000020ParamOutput.getOrdTkBsMnth());
		getSpecDtlsQry.setParameter(PDConstants.PRMTR_PORCD,
				b000020ParamOutput.getPorCd());
		getSpecDtlsQry.setParameter(PDConstants.PRMTR_PRDSTGCD,
				b000020ParamOutput.getProductionStageCd());
		getSpecDtlsQry.setParameter(PDConstants.PRMTR_NSC_EIM_ODER_HRZN_FLAG,
				PDConstants.CONSTANT_F);
		getSpecDtlsQry.setParameter(PDConstants.PRMTR_SUSPND_ORD_FLG,
				PDConstants.PRMTR_ZERO);
		if (b000020ParamOutput.getPrdMnthTo() != null
				&& !b000020ParamOutput.getPrdMnthTo().equalsIgnoreCase(
						PDConstants.ALL)) {
			getSpecDtlsQry.setParameter(PDConstants.PRMTR_PRD_MNTH_TO,
					b000020ParamOutput.getPrdMnthTo());
		}

		if (b000020ParamOutput.getCrSrs() != null
				&& !b000020ParamOutput.getCrSrs().equalsIgnoreCase(
						PDConstants.ALL)) {
			getSpecDtlsQry.setParameter(PDConstants.PRMTR_CAR_SRS,
					b000020ParamOutput.getCrSrs());
		}
		if (b000020ParamOutput.getByrGrp() != null
				&& !b000020ParamOutput.getByrGrp().equalsIgnoreCase(
						PDConstants.ALL)) {
			getSpecDtlsQry.setParameter(PDConstants.PRMTR_BYR_GRP_CD,
					b000020ParamOutput.getByrGrp());
		}

		return getSpecDtlsQry.getResultList();
	}

	/**
	 * This method used to extract the NSC Forecast Volume Details from the
	 * database.
	 * 
	 * @param b000020ParamOutput
	 * @param mstSpecObjArry
	 * @return
	 * @throws PdApplicationException
	 */
	public Object[] extractNscFrcstVolDtls(
			B000020ParamOutput b000020ParamOutput, Object[] mstSpecObjArry)
			throws PdApplicationException {
		
		String errMsgId = "M00196";
		String extractNscFrcstVolDtlsStr = B000020QueryConstants.extractNscFrcstVolDtls
				.toString();
		Query extractNscFrcstVolDtlsQry = entityMngr
				.createNativeQuery(extractNscFrcstVolDtlsStr);
		String porCd = (String) mstSpecObjArry[0];
		String prdMnth = (String) mstSpecObjArry[3];
		String carSrs = (String) mstSpecObjArry[5];
		String byrGrpCd = (String) mstSpecObjArry[4];
		extractNscFrcstVolDtlsQry.setParameter(PDConstants.PRMTR_PORCD, porCd);
		extractNscFrcstVolDtlsQry.setParameter(PDConstants.PRMTR_PRD_MNTH,
				prdMnth);
		extractNscFrcstVolDtlsQry.setParameter(PDConstants.PRMTR_CAR_SRS,
				carSrs);
		extractNscFrcstVolDtlsQry.setParameter(PDConstants.PRMTR_BYR_GRP_CD,
				byrGrpCd);
		extractNscFrcstVolDtlsQry.setParameter(
				PDConstants.PRMTR_ORDR_TK_BS_MNTH,
				b000020ParamOutput.getOrdTkBsMnth());
		List<Object[]> result = extractNscFrcstVolDtlsQry.getResultList();
		if (result.isEmpty()) {
			String m00196 = porCd + ":" + byrGrpCd + ";" + carSrs + ";";
			String errMsg = PDMessageConsants.M00196
					.replace(PDConstants.ERROR_MESSAGE_1,
							PDConstants.BATCH_20_ID)
					.replace(PDConstants.ERROR_MESSAGE_2,
							PDConstants.FORECAST_VOLUME)
					.replace(PDConstants.ERROR_MESSAGE_3,
							PDConstants.NSC_FORECAST_VOLUME_TRN)
					.replace(PDConstants.ERROR_MESSAGE_4, m00196);

			CommonUtil.logMessage(PDMessageConsants.M00196, CONSTANT_V4,
					new String[] { PDConstants.BATCH_20_ID,
							PDConstants.FORECAST_VOLUME,
							PDConstants.NSC_FORECAST_VOLUME_TRN,
							porCd + " ;" + carSrs + " ;" + byrGrpCd });

			// Add to data to error report as warning message
			B000020ReportDao reportDao = new B000020ReportDao();

			reportDao.setPor(porCd);
			reportDao.setCarSrs(carSrs);
			reportDao.setByrGrp(byrGrpCd);
			reportDao.setOrdrStg(b000020ParamOutput.getProductionStageCd()
					+ "-" + b000020ParamOutput.getStgCode());
			reportDao.setAvgCalBy(b000020ParamOutput.getAvgCalBy());
			reportDao.setEiBrkPntPrity(PDConstants.CONSTANT_N_TO_N_PLUS_3);
			reportDao.setColorBrkdwnPrity(PDConstants.CONSTANT_N_TO_N_PLUS_3);
			reportDao.setErrType(PDConstants.PRMTR_WARNING);

			reportDao.setErrMsg(errMsg);
			reportDao.setBatchId(PDConstants.BATCH_20_ID);
			reportDao.setErrId(errMsgId);
			reportDao.setProdMnth(prdMnth);
			reportDao.setOrdrTkBSMnth(b000020ParamOutput.getOrdTkBsMnth());
			reportDao.setTime(createDate + "");
			objB000020Report.getReportList().add(reportDao);
			LOG.error("Error:" + errMsg);
			throw new PdApplicationException(PDConstants.NO_DATA_FOUND);
		}
		return result.get(0);
	}

	/*
	 * This method used to extract the NSC Forecast Volume Details from the
	 * database.
	 * 
	 * @param b000020ParamOutput
	 * 
	 * @param mstSpecObjArry
	 * 
	 * @return
	 */
	public List<Object[]> extractByrGrpOcfDtls(
			B000020ParamOutput b000020ParamOutput, Object[] mstSpecObjArry)
			throws PdApplicationException {
		String extractByrGrpOcfDtlsStr = B000020QueryConstants.extractByrGrpOcfDtls
				.toString();
		Query extractByrGrpOcfDtlsQry = entityMngr
				.createNativeQuery(extractByrGrpOcfDtlsStr);
		String porCd = (String) mstSpecObjArry[0];
		String prdMnth = (String) mstSpecObjArry[3];
		String carSrs = (String) mstSpecObjArry[5];
		String byrGrpCd = (String) mstSpecObjArry[4];
		extractByrGrpOcfDtlsQry.setParameter(PDConstants.PRMTR_PORCD, porCd);
		extractByrGrpOcfDtlsQry.setParameter(PDConstants.PRMTR_PRD_MNTH,
				prdMnth);
		extractByrGrpOcfDtlsQry.setParameter(PDConstants.PRMTR_CAR_SRS, carSrs);
		extractByrGrpOcfDtlsQry.setParameter(PDConstants.PRMTR_BYR_GRP_CD,
				byrGrpCd);
		extractByrGrpOcfDtlsQry.setParameter(PDConstants.PRMTR_ORDR_TK_BS_MNTH,
				b000020ParamOutput.getOrdTkBsMnth());
		extractByrGrpOcfDtlsQry.setParameter(PDConstants.PRMTR_OCF_FRAME_CD,
				PDConstants.OCF_FRAME_CODE_ZERO);

		List<Object[]> result = extractByrGrpOcfDtlsQry.getResultList();
		if (result.size() >= 2) {
			CommonUtil.logMessage(PDMessageConsants.M00219, CONSTANT_V4,
					new String[] {
							PDConstants.BATCH_20_ID,
							PDConstants.FEATURE_CD,
							PDConstants.BUYER_GROUP_MONTHLY_OCF_LIMIT_TRN,
							porCd + ";" + carSrs + ";" + byrGrpCd + ";"
									+ prdMnth });
			throw new PdApplicationException();
		}
		return result;
	}

	/**
	 * This method is used to Extract break down for Buyer Grp and Car series.
	 * 
	 * @param b000020ParamOutput
	 * @param mstSpecObjArry
	 * @return List<Object[]>
	 * @throws PdApplicationException
	 */
	public List<Object[]> extractBrkDwnFrByrAndCrSrs(
			B000020ParamOutput b000020ParamOutput, Object[] mstSpecObjArry)
			throws PdApplicationException {
		String errMsgIdVal = "M00190";
		String extractBrkDwnFrByrAndCrSrsStr = B000020QueryConstants.extractIdlMxPr
				.toString();
		Query extractBrkDwnFrByrAndCrSrsQry = entityMngr
				.createNativeQuery(extractBrkDwnFrByrAndCrSrsStr);
		String porCd = (String) mstSpecObjArry[0];
		String carSrs = (String) mstSpecObjArry[5];
		String byrGrpCd = (String) mstSpecObjArry[4];
		String prdMnth = (String) mstSpecObjArry[3];
		String m190 = porCd + ":" + carSrs + ";" + byrGrpCd + ";";
		String errMsg = PDMessageConsants.M00190
				.replace(PDConstants.ERROR_MESSAGE_1, PDConstants.BATCH_20_ID)
				.replace(PDConstants.ERROR_MESSAGE_2,
						PDConstants.BREAKDOWN_PRIORITY_FLAG)
				.replace(PDConstants.ERROR_MESSAGE_3,
						PDConstants.IDEAL_MIX_PRIORITY_MST).replace(
				PDConstants.ERROR_MESSAGE_4, m190);
		extractBrkDwnFrByrAndCrSrsQry.setParameter(PDConstants.PRMTR_PORCD,
				porCd);
		extractBrkDwnFrByrAndCrSrsQry.setParameter(PDConstants.PRMTR_CAR_SRS,
				carSrs);
		extractBrkDwnFrByrAndCrSrsQry.setParameter(
				PDConstants.PRMTR_BYR_GRP_CD, byrGrpCd);
		if (extractBrkDwnFrByrAndCrSrsQry.getResultList().size() == 0) {
			B000020ReportDao reportDao = new B000020ReportDao();
			reportDao.setPor(porCd);
			reportDao.setCarSrs(carSrs);
			reportDao.setOrdrTkBSMnth(b000020ParamOutput.getOrdTkBsMnth());
			reportDao.setProdMnth(prdMnth);
			reportDao.setAvgCalBy(b000020ParamOutput.getAvgCalBy());
			reportDao.setOrdrStg(b000020ParamOutput.getProductionStageCd()
					+ "-" + b000020ParamOutput.getStgCode());
			reportDao.setBatchId(PDConstants.BATCH_20_ID);
			reportDao.setByrGrp(byrGrpCd);
			reportDao.setErrId(errMsgIdVal);
			reportDao.setErrType(PDConstants.PRMTR_WARNING);
			reportDao.setErrMsg(errMsg);
			reportDao.setTime(createDate + "");
			objB000020Report.getReportList().add(reportDao);
		}

		return extractBrkDwnFrByrAndCrSrsQry.getResultList();
	}

	/**
	 * This method is used to Extract the ideal mix forecast volume.
	 * 
	 * @param mstSpecDtls
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Object[] extractIdlMxFrcstVol(Object[] mstSpecDtls)
			throws PdApplicationException {
		List<Object[]> result = new ArrayList<Object[]>();
		String porCd = (String) mstSpecDtls[0];
		String prdMnth = (String) mstSpecDtls[3];
		String oeiByrId = (String) mstSpecDtls[6];
		String extractIdlMxFrcstVolStr = B000020QueryConstants.extractIdlMxFrcstVol
				.toString();
		Query extractIdlMxFrcstVolQry = entityMngr
				.createNativeQuery(extractIdlMxFrcstVolStr);
		extractIdlMxFrcstVolQry.setParameter(PDConstants.PRMTR_PORCD, porCd);
		extractIdlMxFrcstVolQry.setParameter(PDConstants.PRMTR_PRD_MNTH,
				prdMnth);
		extractIdlMxFrcstVolQry.setParameter(PDConstants.PRMTR_OEI_BYR_ID,
				oeiByrId);
		result = extractIdlMxFrcstVolQry.getResultList();

		if (result.isEmpty()) {
			CommonUtil.logMessage(PDMessageConsants.M00196,
					PDConstants.CONSTANT_V3, new String[] {
							PDConstants.BATCH_20_ID,
							PDConstants.IDEAL_MIX_VOLUME
									+ PDConstants.IDEAL_MIX_PRIORITY_MST,
							porCd + ";" + prdMnth + ";" + oeiByrId + ";"
									+ mstSpecDtls[5].toString() });
			return null;
		}
		return result.get(0);

	}

	/**
	 * This method is used to extract the ideal mix volume.
	 * 
	 * @param mstSpecDtls
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Object[] extractIdlMxVol(Object[] mstSpecDtls) {
		List<Object[]> result = new ArrayList<Object[]>();
		String porCd = (String) mstSpecDtls[0];
		String oeiByrId = (String) mstSpecDtls[6];
		String prdMnth = (String) mstSpecDtls[3];
		String extractIdlMxVolStr = B000020QueryConstants.extractIdlMxVol
				.toString();
		Query extractIdlMxVolQry = entityMngr
				.createNativeQuery(extractIdlMxVolStr);
		extractIdlMxVolQry.setParameter(PDConstants.PRMTR_PORCD, porCd);
		extractIdlMxVolQry.setParameter(PDConstants.PRMTR_OEI_BYR_ID, oeiByrId);
		result = extractIdlMxVolQry.getResultList();
		if (result.isEmpty()) {
			CommonUtil.logMessage(PDMessageConsants.M00196,
					PDConstants.CONSTANT_V3, new String[] {
							PDConstants.BATCH_20_ID,
							PDConstants.IDEAL_MIX_VOLUME
									+ PDConstants.IDEAL_MIX_PRIORITY_MST,
							porCd + ";" + prdMnth + ";" + oeiByrId + ";"
									+ mstSpecDtls[5].toString() });
			return null;
		}
		return result.get(0);

	}

	/**
	 * This method is used to extract the Ideal Mix Forecast at Buyer Code
	 * Level.
	 * 
	 * @param mstSpecDtls
	 * @return
	 */
	public int extractIdlMxFrcstByrGrpCdLvl(Object[] mstSpecDtls) {

		String porCd = (String) mstSpecDtls[0];
		String byrGrpCd = (String) mstSpecDtls[4];
		String prdRgnCd = (String) mstSpecDtls[11];
		String prdMnth = (String) mstSpecDtls[3];
		String extractIdlMxVolStr = B000020QueryConstants.extractIdlMxFrcstByrGrpCdLvl
				.toString();
		Query extractIdlMxVolQry = entityMngr
				.createNativeQuery(extractIdlMxVolStr);
		extractIdlMxVolQry.setParameter(PDConstants.PRMTR_PORCD, porCd);
		extractIdlMxVolQry.setParameter(PDConstants.PRMTR_BYR_GRP_CD, byrGrpCd);
		extractIdlMxVolQry.setParameter(PDConstants.PRMTR_PRD_RGN_CD, prdRgnCd);
		extractIdlMxVolQry.setParameter(PDConstants.PRMTR_PRD_MNTH, prdMnth);
		return ((BigDecimal) extractIdlMxVolQry.getResultList().get(0))
				.intValue();

	}

	/**
	 * This method is used to extract the Ideal Mix at Buyer Code Level.
	 * 
	 * @param mstSpecDtls
	 * @return
	 */
	public int extractIdlMxByrGrpCdLvl(Object[] mstSpecDtls) {

		String porCd = (String) mstSpecDtls[0];
		String byrGrpCd = (String) mstSpecDtls[4];
		String prdRgnCd = (String) mstSpecDtls[11];
		String extractIdlMxByrGrpCdLvlStr = B000020QueryConstants.extractIdlMxByrGrpCdLvl
				.toString();
		Query extractIdlMxByrGrpCdLvlQry = entityMngr
				.createNativeQuery(extractIdlMxByrGrpCdLvlStr);
		extractIdlMxByrGrpCdLvlQry.setParameter(PDConstants.PRMTR_PORCD, porCd);
		extractIdlMxByrGrpCdLvlQry.setParameter(PDConstants.PRMTR_BYR_GRP_CD,
				byrGrpCd);
		extractIdlMxByrGrpCdLvlQry.setParameter(PDConstants.PRMTR_PRD_RGN_CD,
				prdRgnCd);
		return ((BigDecimal) extractIdlMxByrGrpCdLvlQry.getResultList().get(0))
				.intValue();

	}

	/**
	 * This method is used to insert the calculated ratio details to the
	 * temporary tables.
	 * 
	 * @param devB000020
	 */
	public void insertRatioDtls(Object[] mstSpecDtls,
			Float calculatedIdlMxRatio, float allcOrdQty, String prcssFlg) {
		DevB000020 devB000020 = new DevB000020();
		DevB000020PK devB000020PK = new DevB000020PK();
		LOG.info("calculatedIdlMxRatio" + calculatedIdlMxRatio);
		BigDecimal idlMxRatio = BigDecimal.valueOf(calculatedIdlMxRatio);
		BigDecimal allcOrdQtyBd = BigDecimal.valueOf(allcOrdQty);
		String porCd = (String) mstSpecDtls[0];
		String prdMnth = (String) mstSpecDtls[3];
		String byrGrpCd = (String) mstSpecDtls[4];
		String crSrs = (String) mstSpecDtls[5];
		String oeiByrId = (String) mstSpecDtls[6];
		devB000020PK.setPorCd(porCd);
		devB000020PK.setProdMnth(prdMnth);
		devB000020PK.setCarSrs(crSrs);
		devB000020PK.setBuyerGrpCd(byrGrpCd);
		devB000020PK.setOeiBuyerId(oeiByrId);
		devB000020.setIdealMixRatio(idlMxRatio);
		devB000020.setPrcssFlg(prcssFlg);
		devB000020.setAllocatedOrdrQty(allcOrdQtyBd);
		devB000020.setId(devB000020PK);
		entityMngr.merge(devB000020);

	}

	/**
	 * This method is used to fetch the N month Order Qty for each buyer OEI
	 * Buyer Id level
	 * 
	 * @param mstSpecDtls
	 * @param b000020ParamOutput
	 * @return
	 * @throws ParseException
	 */
	@SuppressWarnings("unchecked")
	public Object[] extractNMnthprdMnthQty(Object[] mstSpecDtls,
			B000020ParamOutput b000020ParamOutput) throws ParseException {
		List<Object[]> result = new ArrayList<Object[]>();
		String porCd = (String) mstSpecDtls[0];

		String oeiByrId = (String) mstSpecDtls[6];
		String carSrs = (String) mstSpecDtls[5];
		String byrGrpCd = (String) mstSpecDtls[4];
		String ordTkBsMnth = b000020ParamOutput.getOrdTkBsMnth();
		String prdMnth = CommonUtil.prdMnthCal(ordTkBsMnth,
				PDConstants.CONSTANT_3);
		String prdStgcd = b000020ParamOutput.getProductionStageCd();
		String avgCalBy = b000020ParamOutput.getAvgCalBy();
		String extractNMnthprdMnthQtyStr = B000020QueryConstants.extractNMnthprdMnthQty
				.toString();
		if (prdStgcd.equalsIgnoreCase(PDConstants.TWENTY)
				&& avgCalBy.equalsIgnoreCase(PDConstants.DRAFT)) {
			extractNMnthprdMnthQtyStr = extractNMnthprdMnthQtyStr.replace(
					PDConstants.AMPERSAND_ONE, PDConstants.DRFT_ORDR_QTY);
		} else {
			extractNMnthprdMnthQtyStr = extractNMnthprdMnthQtyStr.replace(
					PDConstants.AMPERSAND_ONE, PDConstants.ORDR_QTY);
		}
		Query extractNMnthprdMnthQtyQry = entityMngr
				.createNativeQuery(extractNMnthprdMnthQtyStr);
		extractNMnthprdMnthQtyQry.setParameter(PDConstants.PRMTR_PORCD, porCd);
		extractNMnthprdMnthQtyQry.setParameter(PDConstants.PRMTR_PRD_MNTH,
				prdMnth);
		extractNMnthprdMnthQtyQry.setParameter(PDConstants.PRMTR_CAR_SRS,
				carSrs);

		extractNMnthprdMnthQtyQry.setParameter(PDConstants.PRMTR_OEI_BYR_ID,
				oeiByrId);
		extractNMnthprdMnthQtyQry.setParameter(
				PDConstants.PRMTR_ORDR_TK_BS_MNTH, ordTkBsMnth);
		extractNMnthprdMnthQtyQry.setParameter(PDConstants.PRMTR_PRDSTGCD,
				prdStgcd);
		extractNMnthprdMnthQtyQry.setParameter(PDConstants.PRMTR_BYR_GRP_CD,
				byrGrpCd);

		result = extractNMnthprdMnthQtyQry.getResultList();

		if (result.isEmpty()) {
			return null;
		}
		return result.get(0);
	}

	/**
	 * This method is used to Count of OEI Buyer Cd at Buyer Group cd level.
	 * 
	 * @param mstSpecDtls
	 * @param b000020ParamOutput
	 * @return
	 */
	public int extractCountByrIdAtByrGrpLvl(Object[] mstSpecDtls,
			B000020ParamOutput b000020ParamOutput) {
		String carSrs = (String) mstSpecDtls[5];
		String byrGrpCd = (String) mstSpecDtls[4];
		String prdMnth = (String) mstSpecDtls[3];
		String extractCountOeiByrIdLvlStr = B000020QueryConstants.extractCountOeiByrIdLvl
				.toString();
		extractCountOeiByrIdLvlStr += B000020QueryConstants.andExtractMstSpecDtlsHrznQryAndCarSrs;
		extractCountOeiByrIdLvlStr += andByrGrpCdQryStr;
		Query extractCountOeiByrIdLvlQry = entityMngr
				.createNativeQuery(extractCountOeiByrIdLvlStr);
		extractCountOeiByrIdLvlQry.setParameter(PDConstants.PRMTR_PRD_MNTH,
				prdMnth);
		extractCountOeiByrIdLvlQry.setParameter(PDConstants.PRMTR_PORCD,
				b000020ParamOutput.getPorCd());
		extractCountOeiByrIdLvlQry.setParameter(PDConstants.PRMTR_PRDSTGCD,
				b000020ParamOutput.getProductionStageCd());
		extractCountOeiByrIdLvlQry.setParameter(
				PDConstants.PRMTR_NSC_EIM_ODER_HRZN_FLAG,
				PDConstants.CONSTANT_F);
		extractCountOeiByrIdLvlQry.setParameter(
				PDConstants.PRMTR_SUSPND_ORD_FLG, PDConstants.N);

		extractCountOeiByrIdLvlQry.setParameter(PDConstants.PRMTR_CAR_SRS,
				carSrs);
		extractCountOeiByrIdLvlQry.setParameter(PDConstants.PRMTR_BYR_GRP_CD,
				byrGrpCd);
		return extractCountOeiByrIdLvlQry.getResultList().size();
	}

	/**
	 * This method is used to extract the Calculated Buyer Group Code from the
	 * temporary table.
	 * 
	 * @return
	 */
	public List<Object[]> extractAllByrGrpFrmTempTbl() {
		String extractAllByrGrpFrmTempTblStr = B000020QueryConstants.extractAllByrGrpFrmTempTbl
				.toString();
		Query extractAllByrGrpFrmTempTblQry = entityMngr
				.createNativeQuery(extractAllByrGrpFrmTempTblStr);
		extractAllByrGrpFrmTempTblQry.setParameter(PDConstants.PARAM_1,
				PDConstants.AVG_PRCSS_FLG);
		extractAllByrGrpFrmTempTblQry.setParameter(PDConstants.PARAM_2,
				PDConstants.IDL_PRCSS_FLG);
		return extractAllByrGrpFrmTempTblQry.getResultList();
	}

	/**
	 * This method is used to extract all the Allocated Order Quantity at Buyer
	 * Group Code Level.
	 * 
	 * @param byrCdDtls
	 * @return
	 */
	public List<Object[]> extractAllcordrQtyByrGrpCdLVl(Object[] byrCdDtls) {
		String porCd = (String) byrCdDtls[0];
		String prdMnth = (String) byrCdDtls[1];
		String carSrs = (String) byrCdDtls[2];
		String byrGrp = (String) byrCdDtls[3];
		String extractAllcordrQtyByrGrpCdLVlStr = B000020QueryConstants.extractAllcordrQtyByrGrpCdLVl
				.toString();
		Query extractAllcordrQtyByrGrpCdLVlQry = entityMngr
				.createNativeQuery(extractAllcordrQtyByrGrpCdLVlStr);
		extractAllcordrQtyByrGrpCdLVlQry.setParameter(PDConstants.PRMTR_PORCD,
				porCd);
		extractAllcordrQtyByrGrpCdLVlQry.setParameter(
				PDConstants.PRMTR_PRD_MNTH, prdMnth);
		extractAllcordrQtyByrGrpCdLVlQry.setParameter(
				PDConstants.PRMTR_CAR_SRS, carSrs);
		extractAllcordrQtyByrGrpCdLVlQry.setParameter(
				PDConstants.PRMTR_BYR_GRP_CD, byrGrp);
		return extractAllcordrQtyByrGrpCdLVlQry.getResultList();
	}

	/**
	 * This method is used to insert the calculated ratio details to the
	 * temporary tables.
	 * 
	 * @param devB000020
	 */
	public void insertListNewOrdrDtls(List<Object[]> nwOrdrDtls) {
		DevB000020 devB000020 = new DevB000020();
		DevB000020PK devB000020PK = new DevB000020PK();
		for (Object[] ordrDtls : nwOrdrDtls) {
			String porCd = (String) ordrDtls[0];
			String prdMnth = (String) ordrDtls[1];
			String crSrs = (String) ordrDtls[2];
			String byrGrpCd = (String) ordrDtls[3];
			String oeiByrCd = (String) ordrDtls[5];
			String prcssFlg = (String) ordrDtls[7];
			BigDecimal idealMixRatio = (BigDecimal) ordrDtls[6];
			BigDecimal nwOrdQty = (BigDecimal) ordrDtls[4];
			devB000020PK.setPorCd(porCd);
			devB000020PK.setProdMnth(prdMnth);
			devB000020PK.setCarSrs(crSrs);
			devB000020PK.setBuyerGrpCd(byrGrpCd);
			devB000020PK.setOeiBuyerId(oeiByrCd);
			devB000020.setAllocatedOrdrQty(nwOrdQty);
			devB000020.setPrcssFlg(prcssFlg);
			devB000020.setIdealMixRatio(idealMixRatio);
			devB000020.setId(devB000020PK);
			entityMngr.merge(devB000020);
		}
	}

	/**
	 * This method is used to extract the ideal mix volume at Color Level.
	 * 
	 * @param mstSpecDtls
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Object[] extractIdlMxVolClrLvl(Object[] mstSpecDtls) {
		List<Object[]> result = new ArrayList<Object[]>();
		String porCd = (String) mstSpecDtls[0];
		String byrGrpCd = (String) mstSpecDtls[4];
		String extClr = (String) mstSpecDtls[8];
		String intClr = (String) mstSpecDtls[9];
		String carSrs = (String) mstSpecDtls[5];
		String extractIdlMxVolStr = B000020QueryConstants.extractIdlMxClrRatio
				.toString();
		Query extractIdlMxVolQry = entityMngr
				.createNativeQuery(extractIdlMxVolStr);
		extractIdlMxVolQry.setParameter(PDConstants.PRMTR_PORCD, porCd);
		extractIdlMxVolQry.setParameter(PDConstants.PRMTR_CAR_SRS, carSrs);
		extractIdlMxVolQry.setParameter(PDConstants.PRMTR_BYR_GRP_CD, byrGrpCd);
		extractIdlMxVolQry.setParameter(PDConstants.PRMTRT_EXT_CLR, extClr);
		extractIdlMxVolQry.setParameter(PDConstants.PRMTR_INT_CLR_CD, intClr);
		result = extractIdlMxVolQry.getResultList();
		if (result.isEmpty()) {
			return null;
		}
		return result.get(0);

	}

	/**
	 * This method is used to extract the Ideal Mix Color Level Forecast Volume
	 * at Buyer Code Level.
	 * 
	 * @param mstSpecDtls
	 * @return
	 */
	public int extractIdlMxFrcstByrGrpCdClrLvl(Object[] mstSpecDtls) {

		String porCd = (String) mstSpecDtls[0];
		String byrGrpCd = (String) mstSpecDtls[4];
		String oeiByrId = (String) mstSpecDtls[6];
		String carSrs = (String) mstSpecDtls[5];
		String extractIdlMxVolStr = B000020QueryConstants.extractIdlMxFrcstByrGrpCdClrLvl
				.toString();
		Query extractIdlMxVolQry = entityMngr
				.createNativeQuery(extractIdlMxVolStr);
		extractIdlMxVolQry.setParameter(PDConstants.PRMTR_PORCD, porCd);
		extractIdlMxVolQry.setParameter(PDConstants.PRMTR_BYR_GRP_CD, byrGrpCd);
		extractIdlMxVolQry.setParameter(PDConstants.PRMTR_CARSRS, carSrs);
		extractIdlMxVolQry.setParameter(PDConstants.PRMTR_OEI_BYR_ID, oeiByrId);

		return ((BigDecimal) extractIdlMxVolQry.getResultList().get(0))
				.intValue();

	}

	/**
	 * This method is used to extract the Ideal Mix Color Level Forecast Volume
	 * at Buyer Code Level.
	 * 
	 * @param mstSpecDtls
	 * @return
	 */
	public int extractoeiByrIdLvlAllcOrdQty(Object[] mstSpecDtls) {
		List<BigDecimal> result = null;
		String porCd = (String) mstSpecDtls[0];
		String byrGrpCd = (String) mstSpecDtls[4];
		String carSrs = (String) mstSpecDtls[5];
		String prdMnth = (String) mstSpecDtls[3];
		String oeiByrId = (String) mstSpecDtls[6];
		String extractIdlMxVolStr = B000020QueryConstants.extractoeiByrIdLvlAllcOrdQty
				.toString();
		Query extractIdlMxVolQry = entityMngr
				.createNativeQuery(extractIdlMxVolStr);
		extractIdlMxVolQry.setParameter(PDConstants.PRMTR_PORCD, porCd);
		extractIdlMxVolQry.setParameter(PDConstants.PRMTR_BYR_GRP_CD, byrGrpCd);
		extractIdlMxVolQry.setParameter(PDConstants.PRMTR_CAR_SRS, carSrs);
		extractIdlMxVolQry.setParameter(PDConstants.PRMTR_PRD_MNTH, prdMnth);
		extractIdlMxVolQry.setParameter(PDConstants.PRMTR_OEI_BYR_ID, oeiByrId);
		result = extractIdlMxVolQry.getResultList();
		if (result == null || result.isEmpty()) {
			return 0;
		}
		
		return ((BigDecimal) result.get(0))
				.intValue();

	}

	/**
	 * This method is used to insert the calculated ratio details to the
	 * temporary tables.
	 * 
	 * @param devB000020
	 */
	public void insertRatioClrLvlDtls(Object[] mstSpecDtls,
			Float calculatedIdlMxRatio, float allcOrdQty, String prcssFlg) {
		DevB000020ClrLvl devB000020 = new DevB000020ClrLvl();
		DevB000020ClrLvlPK devB000020PK = new DevB000020ClrLvlPK();
		BigDecimal idlMxRatio = BigDecimal.valueOf(calculatedIdlMxRatio);
		BigDecimal allcOrdQtyBd = BigDecimal.valueOf(allcOrdQty);
		String porCd = (String) mstSpecDtls[0];
		String prdMnth = (String) mstSpecDtls[3];
		String byrGrpCd = (String) mstSpecDtls[4];
		String crSrs = (String) mstSpecDtls[5];
		String oeiByrId = (String) mstSpecDtls[6];
		String oseiId = (String) mstSpecDtls[1];
		char ocfRgnCd = (char) mstSpecDtls[2];
		String ocfByrGrpCd = (String) mstSpecDtls[12];
		LOG.info("" + ocfRgnCd + ocfByrGrpCd);
		devB000020PK.setPorCd(porCd);
		devB000020PK.setProdMnth(prdMnth);
		devB000020PK.setCarSrs(crSrs);
		devB000020PK.setBuyerGrpCd(byrGrpCd);
		devB000020PK.setOeiBuyerId(oeiByrId);
		devB000020.setIdealMixRatio(idlMxRatio);
		devB000020.setPrcssFlg(prcssFlg);
		devB000020.setAllocatedOrdrQty(allcOrdQtyBd);
		devB000020.setOcfRegionCd(String.valueOf(ocfRgnCd));
		devB000020.setOcfBuyerGrpCd(ocfByrGrpCd);
		devB000020PK.setOseiId(oseiId);
		devB000020.setId(devB000020PK);
		entityMngr.merge(devB000020);

	}

	/**
	 * This method is used to fetch the N month Order Qty for each buyer OEI at
	 * Color Buyer Id level
	 * 
	 * @param mstSpecDtls
	 * @param b000020ParamOutput
	 * @return
	 * @throws ParseException
	 */
	@SuppressWarnings("unchecked")
	public Object[] extractNMnthprdMnthByrGrpLvlQty(Object[] mstSpecDtls,
			B000020ParamOutput b000020ParamOutput) throws ParseException {
		List<Object[]> result = new ArrayList<Object[]>();
		String porCd = (String) mstSpecDtls[0];

		String byrGrpCd = (String) mstSpecDtls[4];
		String crSrs = (String) mstSpecDtls[5];
		String ordTkBsMnth = b000020ParamOutput.getOrdTkBsMnth();
		String prdMnth = CommonUtil.prdMnthCal(ordTkBsMnth,
				PDConstants.CONSTANT_3);
		String prdStgcd = b000020ParamOutput.getProductionStageCd();
		String avgCalBy = b000020ParamOutput.getAvgCalBy();
		String extractNMnthprdMnthQtyStr = B000020QueryConstants.extractNMnthprdMnthByrGrpLvlQty
				.toString();
		if (prdStgcd.equalsIgnoreCase(PDConstants.TWENTY) && avgCalBy == null
				|| avgCalBy.equalsIgnoreCase(PDConstants.DRAFT)) {
			extractNMnthprdMnthQtyStr = extractNMnthprdMnthQtyStr.replace(
					PDConstants.AMPERSAND_ONE, PDConstants.DRFT_ORDR_QTY);
		} else {
			extractNMnthprdMnthQtyStr = extractNMnthprdMnthQtyStr.replace(
					PDConstants.AMPERSAND_ONE, PDConstants.ORDR_QTY);
		}
		Query extractNMnthprdMnthQtyQry = entityMngr
				.createNativeQuery(extractNMnthprdMnthQtyStr);
		extractNMnthprdMnthQtyQry.setParameter(PDConstants.PRMTR_PORCD, porCd);
		extractNMnthprdMnthQtyQry.setParameter(PDConstants.PRMTR_PRD_MNTH,
				prdMnth);
		extractNMnthprdMnthQtyQry.setParameter(PDConstants.PRMTR_BYR_GRP_CD,
				byrGrpCd);
		extractNMnthprdMnthQtyQry.setParameter(
				PDConstants.PRMTR_ORDR_TK_BS_MNTH, ordTkBsMnth);
		extractNMnthprdMnthQtyQry.setParameter(PDConstants.PRMTR_PRDSTGCD,
				prdStgcd);
		extractNMnthprdMnthQtyQry
				.setParameter(PDConstants.PRMTR_CAR_SRS, crSrs);

		result = extractNMnthprdMnthQtyQry.getResultList();

		if (result.isEmpty()) {
			return null;
		}
		return result.get(0);
	}

	/**
	 * This method is used to fetch the N month Order Qty for each buyer OEI at
	 * Color Buyer Id level
	 * 
	 * @param mstSpecDtls
	 * @param b000020ParamOutput
	 * @return
	 * @throws ParseException
	 */
	@SuppressWarnings("unchecked")
	public Object[] extractNMnthprdMnthQtyClrLevel(Object[] mstSpecDtls,
			B000020ParamOutput b000020ParamOutput) throws ParseException {
		List<Object[]> result = new ArrayList<Object[]>();
		String porCd = (String) mstSpecDtls[0];

		String oseiId = (String) mstSpecDtls[1];
		String ordTkBsMnth = b000020ParamOutput.getOrdTkBsMnth();
		String prdStgcd = b000020ParamOutput.getProductionStageCd();
		String prdMnth = CommonUtil.prdMnthCal(ordTkBsMnth,
				PDConstants.CONSTANT_3);
		String avgCalBy = b000020ParamOutput.getAvgCalBy();
		String extractNMnthprdMnthQtyStr = B000020QueryConstants.extractNMnthprdMnthClrLvlQty
				.toString();
		String oeISpecId = (String) mstSpecDtls[7];
		String carSrs = (String) mstSpecDtls[5];
		String byrGrpCd = (String) mstSpecDtls[4];
		if (prdStgcd.equalsIgnoreCase(PDConstants.TWENTY)
				&& avgCalBy.equalsIgnoreCase(PDConstants.DRAFT)) {
			extractNMnthprdMnthQtyStr = extractNMnthprdMnthQtyStr.replace(
					PDConstants.AMPERSAND_ONE, PDConstants.DRFT_ORDR_QTY);
		} else {
			extractNMnthprdMnthQtyStr = extractNMnthprdMnthQtyStr.replace(
					PDConstants.AMPERSAND_ONE, PDConstants.ORDR_QTY);
		}
		Query extractNMnthprdMnthQtyQry = entityMngr
				.createNativeQuery(extractNMnthprdMnthQtyStr);
		extractNMnthprdMnthQtyQry.setParameter(PDConstants.PRMTR_PORCD, porCd);
		extractNMnthprdMnthQtyQry.setParameter(PDConstants.PRMTR_PRD_MNTH,
				prdMnth);
		extractNMnthprdMnthQtyQry.setParameter(PDConstants.PRMTR_OSEI_ID,
				oseiId);
		extractNMnthprdMnthQtyQry.setParameter(
				PDConstants.PRMTR_ORDR_TK_BS_MNTH, ordTkBsMnth);
		extractNMnthprdMnthQtyQry.setParameter(PDConstants.PRMTR_PRDSTGCD,
				prdStgcd);
		extractNMnthprdMnthQtyQry
				.setParameter(PDConstants.PRMTR_CARSRS, carSrs);
		extractNMnthprdMnthQtyQry.setParameter(PDConstants.PRMTR_BYR_GRP_CD,
				byrGrpCd);

		result = extractNMnthprdMnthQtyQry.getResultList();
		if (((BigDecimal) result.get(0)[0]).intValue() == 0) {
			String extractNMnthprdMnthQtyAndCarSrsStr = B000020QueryConstants.extractNMnthprdMnthClrLvlAndCarSrsQty
					.toString();
			if (prdStgcd.equalsIgnoreCase(PDConstants.TWENTY)
					&& avgCalBy.equalsIgnoreCase(PDConstants.DRAFT)) {
				extractNMnthprdMnthQtyAndCarSrsStr = extractNMnthprdMnthQtyAndCarSrsStr
						.replace(PDConstants.AMPERSAND_ONE,
								PDConstants.DRFT_ORDR_QTY);
			} else {
				extractNMnthprdMnthQtyAndCarSrsStr = extractNMnthprdMnthQtyAndCarSrsStr
						.replace(PDConstants.AMPERSAND_ONE,
								PDConstants.ORDR_QTY);
			}
			Query extractNMnthprdMnthQtyAndCrSrsQry = entityMngr
					.createNativeQuery(extractNMnthprdMnthQtyAndCarSrsStr);
			extractNMnthprdMnthQtyAndCrSrsQry.setParameter(
					PDConstants.PRMTR_CARSRS, carSrs);
			extractNMnthprdMnthQtyAndCrSrsQry.setParameter(
					PDConstants.PRMTR_PORCD, porCd);
			extractNMnthprdMnthQtyAndCrSrsQry.setParameter(
					PDConstants.PRMTR_PRD_MNTH, prdMnth);
			extractNMnthprdMnthQtyAndCrSrsQry.setParameter(
					PDConstants.PRMTR_OSEI_ID, oseiId);
			extractNMnthprdMnthQtyAndCrSrsQry.setParameter(
					PDConstants.PRMTR_ORDR_TK_BS_MNTH, ordTkBsMnth);
			extractNMnthprdMnthQtyAndCrSrsQry.setParameter(
					PDConstants.PRMTR_PRDSTGCD, prdStgcd);
			extractNMnthprdMnthQtyAndCrSrsQry.setParameter(
					PDConstants.PRMTR_OEISPECID, oeISpecId);

			result = extractNMnthprdMnthQtyAndCrSrsQry.getResultList();

		}
		if (result.isEmpty()) {
			return null;
		}

		return result.get(0);
	}

	/**
	 * This method is used to fetch the N month Order Qty for each buyer OEI at
	 * Color Buyer Id level
	 * 
	 * @param mstSpecDtls
	 * @param b000020ParamOutput
	 * @return
	 * @throws ParseException
	 */
	@SuppressWarnings("unchecked")
	public Object[] extractNMnthprdMnthClrAndOeiByrIdLvlQty(
			Object[] mstSpecDtls, B000020ParamOutput b000020ParamOutput)
			throws ParseException {
		List<Object[]> result = new ArrayList<Object[]>();
		String porCd = (String) mstSpecDtls[0];

		String byrGrpCd = (String) mstSpecDtls[4];
		String ordTkBsMnth = b000020ParamOutput.getOrdTkBsMnth();
		String prdMnth = CommonUtil.prdMnthCal(ordTkBsMnth,
				PDConstants.CONSTANT_3);
		String prdStgcd = b000020ParamOutput.getProductionStageCd();
		String avgCalBy = b000020ParamOutput.getAvgCalBy();
		String oeiByrId = (String) mstSpecDtls[6];
		String carSrs = (String) mstSpecDtls[5];

		String extractNMnthprdMnthQtyStr = B000020QueryConstants.extractNMnthprdMnthClrAndOeiByrIdLvlQty
				.toString();
		if (prdStgcd.equalsIgnoreCase(PDConstants.TWENTY)
				&& avgCalBy.equalsIgnoreCase(PDConstants.DRAFT)) {
			extractNMnthprdMnthQtyStr = extractNMnthprdMnthQtyStr.replace(
					PDConstants.AMPERSAND_ONE, PDConstants.ORDR_QTY);
		} else {
			extractNMnthprdMnthQtyStr = extractNMnthprdMnthQtyStr.replace(
					PDConstants.AMPERSAND_ONE, PDConstants.ORDR_QTY);
		}
		Query extractNMnthprdMnthQtyQry = entityMngr
				.createNativeQuery(extractNMnthprdMnthQtyStr);
		extractNMnthprdMnthQtyQry.setParameter(PDConstants.PRMTR_PORCD, porCd);
		extractNMnthprdMnthQtyQry.setParameter(PDConstants.PRMTR_PRD_MNTH,
				prdMnth);

		extractNMnthprdMnthQtyQry.setParameter(PDConstants.PRMTR_BYR_GRP_CD,
				byrGrpCd);
		extractNMnthprdMnthQtyQry.setParameter(PDConstants.PRMTR_OEI_BYR_ID,
				oeiByrId);

		extractNMnthprdMnthQtyQry.setParameter(
				PDConstants.PRMTR_ORDR_TK_BS_MNTH, ordTkBsMnth);

		extractNMnthprdMnthQtyQry.setParameter(PDConstants.PRMTR_PRDSTGCD,
				prdStgcd);
		extractNMnthprdMnthQtyQry.setParameter(PDConstants.PRMTR_CAR_SRS,
				carSrs);

		result = extractNMnthprdMnthQtyQry.getResultList();

		if (result.isEmpty()) {
			return null;
		}
		return result.get(0);
	}

	/**
	 * This method is used to extract the Calculated Buyer Group Code from the
	 * temporary table.
	 * 
	 * @return
	 */
	public List<Object[]> extractAllOseiIdFrmTempTbl() {
		String extractAllByrGrpFrmTempTblStr = B000020QueryConstants.extractAllOseiIdFrmTempTbl
				.toString();
		Query extractAllByrGrpFrmTempTblQry = entityMngr
				.createNativeQuery(extractAllByrGrpFrmTempTblStr);
		extractAllByrGrpFrmTempTblQry.setParameter(PDConstants.PARAM_1,
				PDConstants.AVG_PRCSS_CLR_FLG);
		extractAllByrGrpFrmTempTblQry.setParameter(PDConstants.PARAM_2,
				PDConstants.IDL_PRCSS_CLR_FLG);
		return extractAllByrGrpFrmTempTblQry.getResultList();
	}

	/**
	 * This method is used to fetch Sum of order Quantity allocated at Buyer
	 * Group Level.
	 */
	public Object[] extractOeiByrIdLvlAllcQty(Object[] objArry) {
		String extractOeiByrIdLvlAllcQtyStr = B000020QueryConstants.extractOeiByrIdLvlAllcQty
				.toString();
		List<Object[]> result;
		String porCd = (String) objArry[0];
		String carSrs = (String) objArry[2];
		String prdMnth = (String) objArry[1];
		String byrGrpCd = (String) objArry[3];
		String oeiByrId = (String) objArry[4];
		Query extractOeiByrIdLvlAllcQtyQry = entityMngr
				.createNativeQuery(extractOeiByrIdLvlAllcQtyStr);
		extractOeiByrIdLvlAllcQtyQry.setParameter(PDConstants.PRMTR_PORCD,
				porCd);
		extractOeiByrIdLvlAllcQtyQry.setParameter(PDConstants.PRMTR_CAR_SRS,
				carSrs);
		extractOeiByrIdLvlAllcQtyQry.setParameter(PDConstants.PRMTR_PRD_MNTH,
				prdMnth);
		extractOeiByrIdLvlAllcQtyQry.setParameter(PDConstants.PRMTR_BYR_GRP_CD,
				byrGrpCd);
		extractOeiByrIdLvlAllcQtyQry.setParameter(PDConstants.PRMTR_OEI_BYR_ID,
				oeiByrId);
		result = extractOeiByrIdLvlAllcQtyQry.getResultList();

		if (result.isEmpty()) {
			return null;
		}
		return result.get(0);
	}

	/**
	 * This method is used to fetch order quantity to each OEI Buyer Id Level
	 * Orders.
	 */
	public List<Object[]> extractAllcordrQtyOeiByrIdLVl(Object[] objArry) {
		String extractAllcordrQtyOeiByrIdStr = B000020QueryConstants.extractAllcordrQtyOeiByrIdLVl
				.toString();
		String porCd = (String) objArry[0];
		String carSrs = (String) objArry[2];
		String prdMnth = (String) objArry[1];
		String byrGrpCd = (String) objArry[3];
		String oeiByrId = (String) objArry[4];
		Query extractAllcordrQtyOeiByrIdQry = entityMngr
				.createNativeQuery(extractAllcordrQtyOeiByrIdStr);
		extractAllcordrQtyOeiByrIdQry.setParameter(PDConstants.PRMTR_PORCD,
				porCd);
		extractAllcordrQtyOeiByrIdQry.setParameter(PDConstants.PRMTR_CAR_SRS,
				carSrs);
		extractAllcordrQtyOeiByrIdQry.setParameter(PDConstants.PRMTR_PRD_MNTH,
				prdMnth);
		extractAllcordrQtyOeiByrIdQry.setParameter(
				PDConstants.PRMTR_BYR_GRP_CD, byrGrpCd);
		extractAllcordrQtyOeiByrIdQry.setParameter(
				PDConstants.PRMTR_OEI_BYR_ID, oeiByrId);
		return extractAllcordrQtyOeiByrIdQry.getResultList();
	}

	/**
	 * This method is used to insert the calculated ratio details to the
	 * temporary tables.
	 * 
	 * @param devB000020
	 */
	public void insertListOseiIdNewOrdrDtls(List<Object[]> nwOrdrDtls) {
		DevB000020ClrLvl devB000020 = new DevB000020ClrLvl();
		DevB000020ClrLvlPK devB000020PK = new DevB000020ClrLvlPK();
		for (Object[] ordrDtls : nwOrdrDtls) {
			String porCd = (String) ordrDtls[0];
			String prdMnth = (String) ordrDtls[1];
			String crSrs = (String) ordrDtls[2];
			String byrGrpCd = (String) ordrDtls[3];
			String oeiByrCd = (String) ordrDtls[5];
			String prcssFlg = (String) ordrDtls[7];
			String oseiId = (String) ordrDtls[8];
			String ocfByrGrpCd = (String) ordrDtls[10];
			char ocfRgnCd = (char) ordrDtls[9];
			BigDecimal idealMixRatio = (BigDecimal) ordrDtls[6];
			BigDecimal nwOrdQty = (BigDecimal) ordrDtls[4];
			devB000020PK.setPorCd(porCd);
			devB000020PK.setProdMnth(prdMnth);
			devB000020PK.setCarSrs(crSrs);
			devB000020PK.setBuyerGrpCd(byrGrpCd);
			devB000020PK.setOeiBuyerId(oeiByrCd);
			devB000020.setAllocatedOrdrQty(nwOrdQty);
			devB000020.setPrcssFlg(prcssFlg);
			devB000020.setIdealMixRatio(idealMixRatio);
			devB000020.setOcfRegionCd(String.valueOf(ocfRgnCd));
			devB000020.setOcfBuyerGrpCd(ocfByrGrpCd);
			devB000020PK.setOseiId(oseiId);
			devB000020.setId(devB000020PK);
			entityMngr.merge(devB000020);
		}
	}

	/**
	 * This method is used to Count of OEI Buyer Cd at Buyer Group cd level.
	 * 
	 * @param mstSpecDtls
	 * @param b000020ParamOutput
	 * @return
	 */
	public int extractCountOseiIdIdLvl(Object[] mstSpecDtls,
			B000020ParamOutput b000020ParamOutput) {
		String carSrs = (String) mstSpecDtls[5];
		String byrGrpCd = (String) mstSpecDtls[4];
		String prdMnth = (String) mstSpecDtls[3];
		String oeiByrId = (String) mstSpecDtls[6];
		String extractCountOseiIdIdLvlStr = B000020QueryConstants.extractCountOseiIdIdLvl
				.toString();
		extractCountOseiIdIdLvlStr += B000020QueryConstants.andExtractMstSpecDtlsHrznQryAndCarSrs;
		extractCountOseiIdIdLvlStr += andByrGrpCdQryStr;
		Query extractCountOeiByrIdLvlQry = entityMngr
				.createNativeQuery(extractCountOseiIdIdLvlStr);
		extractCountOeiByrIdLvlQry.setParameter(PDConstants.PRMTR_PRD_MNTH,
				prdMnth);
		extractCountOeiByrIdLvlQry.setParameter(PDConstants.PRMTR_PORCD,
				b000020ParamOutput.getPorCd());
		extractCountOeiByrIdLvlQry.setParameter(PDConstants.PRMTR_PRDSTGCD,
				b000020ParamOutput.getProductionStageCd());
		extractCountOeiByrIdLvlQry.setParameter(
				PDConstants.PRMTR_NSC_EIM_ODER_HRZN_FLAG,
				PDConstants.CONSTANT_F);
		extractCountOeiByrIdLvlQry.setParameter(
				PDConstants.PRMTR_SUSPND_ORD_FLG, PDConstants.N);

		extractCountOeiByrIdLvlQry.setParameter(PDConstants.PRMTR_CAR_SRS,
				carSrs);
		extractCountOeiByrIdLvlQry.setParameter(PDConstants.PRMTR_BYR_GRP_CD,
				byrGrpCd);
		extractCountOeiByrIdLvlQry.setParameter(PDConstants.PRMTR_OEI_BYR_ID,
				oeiByrId);
		return extractCountOeiByrIdLvlQry.getResultList().size();
	}

	/**
	 * This method is used to fetch Sum of order Quantity allocated at Buyer
	 * Group Level.
	 */
	@SuppressWarnings("unchecked")
	public Object[] extractOeiByrIdLvlAllcQtyFrEqlSplit(Object[] objArry) {
		String extractOeiByrIdLvlAllcQtyStr = B000020QueryConstants.extractOeiByrIdLvlAllcQty
				.toString();
		List<Object[]> result;
		String porCd = (String) objArry[0];
		String carSrs = (String) objArry[5];
		String prdMnth = (String) objArry[3];
		String byrGrpCd = (String) objArry[4];
		String oeiByrId = (String) objArry[6];
		Query extractOeiByrIdLvlAllcQtyQry = entityMngr
				.createNativeQuery(extractOeiByrIdLvlAllcQtyStr);
		extractOeiByrIdLvlAllcQtyQry.setParameter(PDConstants.PRMTR_PORCD,
				porCd);
		extractOeiByrIdLvlAllcQtyQry.setParameter(PDConstants.PRMTR_CAR_SRS,
				carSrs);
		extractOeiByrIdLvlAllcQtyQry.setParameter(PDConstants.PRMTR_PRD_MNTH,
				prdMnth);
		extractOeiByrIdLvlAllcQtyQry.setParameter(PDConstants.PRMTR_BYR_GRP_CD,
				byrGrpCd);
		extractOeiByrIdLvlAllcQtyQry.setParameter(PDConstants.PRMTR_OEI_BYR_ID,
				oeiByrId);
		result = extractOeiByrIdLvlAllcQtyQry.getResultList();

		if (result.isEmpty()) {
			return null;
		}
		return result.get(0);
	}

	/**
	 * This method is used to get the list of all allocated order list.
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Object[]> extractOseiLvlAllcOrdQty() {
		String extractOseiLvlAllcOrdQtyStr = B000020QueryConstants.extractOseiLvlAllcOrdQty
				.toString();
		Query extractOseiLvlAllcOrdQtyQry = entityMngr
				.createNativeQuery(extractOseiLvlAllcOrdQtyStr);
		return extractOseiLvlAllcOrdQtyQry.getResultList();
	}

	/**
	 * This method is used to get the list of all Pot Code list.
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Object[]> extractAllPotCd() {
		String extractPotCdStr = B000020QueryConstants.extractAllPotCd
				.toString();
		Query extractPotCdQry = entityMngr.createNativeQuery(extractPotCdStr);
		extractPotCdQry.setParameter(PDConstants.PRMTR_PRMTR_CD,
				PDConstants.DEFAULT_POT_CD);
		return extractPotCdQry.getResultList();
	}

	/**
	 * This method is used to update the OCF usage quantity in Monthly Order Trn
	 * Table.
	 * 
	 * @return
	 * @throws PdApplicationException
	 */
	@SuppressWarnings("unchecked")
	public void updateMnthlyOrdr(Object[] oseiLvlAllcOrdQtyArr, String potCd,
			B000020ParamOutput b000020ParamOutput, boolean potFlg)
			throws PdApplicationException {
		String updateMnthlyOrdr = QueryConstants.updateOrdrQtyInmnthlyOrderTrnB000020
				.toString();
		updateMnthlyOrdr += QueryConstants.mnthlyOrderTrnPkCnd.toString();
		if (potFlg) {
			updateMnthlyOrdr += QueryConstants.mnthlyOrderTrnPotCnd.toString();
		} else {
			updateMnthlyOrdr += QueryConstants.mnthlyOrderTrnPotCndAll
					.toString();
		}
		try {
			Query updateMnthlyOrdrQry = entityMngr
					.createNativeQuery(updateMnthlyOrdr);
			String porCd = (String) oseiLvlAllcOrdQtyArr[0];
			String prdMnth = (String) oseiLvlAllcOrdQtyArr[1];
			String oseiId = (String) oseiLvlAllcOrdQtyArr[5];
			float allctdOrdQty = ((BigDecimal) oseiLvlAllcOrdQtyArr[6])
					.intValue();
			updateMnthlyOrdrQry.setParameter(PDConstants.PRMTR_ORDR_QTY,
					allctdOrdQty);
			updateMnthlyOrdrQry.setParameter(PDConstants.PRMTR_PORCD, porCd);
			updateMnthlyOrdrQry.setParameter(PDConstants.PRMTR_ORDR_TK_BS_MNTH,
					b000020ParamOutput.getOrdTkBsMnth());
			updateMnthlyOrdrQry.setParameter(PDConstants.PRMTR_PRD_MNTH,
					prdMnth);
			updateMnthlyOrdrQry.setParameter(PDConstants.PRMTR_PRD_ORDR_STGCD,
					b000020ParamOutput.getProductionStageCd());
			updateMnthlyOrdrQry.setParameter(PDConstants.PRMTR_OSEI_ID, oseiId);
			updateMnthlyOrdrQry.setParameter(PDConstants.PRMTR_UPDTD_BY,
					PDConstants.BATCH_20_ID);
			if (potFlg) {
				updateMnthlyOrdrQry.setParameter(PDConstants.PRMTR_POT_CD,
						potCd);
			}
			int count = updateMnthlyOrdrQry.executeUpdate();
			LOG.info("updateMnthlyOrdrQry::::::::::::::" + count);
		} catch (Exception e) {
			LOG.info(e);
			CommonUtil.logMessage(PDMessageConsants.M00164,
					PDConstants.CONSTANT_V3, new String[] {
							PDConstants.BATCH_20_ID, PDConstants.UPDATION,
							PDConstants.MONTHLY_ORDER_TRN });
			throw new PdApplicationException(PDConstants.NO_DATA_FOUND);
		}
	}

	/**
	 * This method is used to update OCF usage in the buyer monthly Ocf usage
	 * table.
	 * 
	 * @param oseiLvlAllcOrdQtyArr
	 * @param b000020ParamOutput
	 * @throws PdApplicationException
	 * @throws Throwable
	 */
	public void updateByrMnthlyOcfUsg(Object[] oseiLvlAllcOrdQtyArr,
			B000020ParamOutput b000020ParamOutput)
			throws PdApplicationException {
		try {
			String updateFeatStr = QueryConstants.updateByrUsgFrOseiId
					.toString();
			updateFeatStr += B000020QueryConstants.byrGrpLmtCnd.toString();
			Query updateFeatQry = entityMngr.createNativeQuery(updateFeatStr);
			String porCd = (String) oseiLvlAllcOrdQtyArr[0];
			String prdMnth = (String) oseiLvlAllcOrdQtyArr[1];
			String carSrs = (String) oseiLvlAllcOrdQtyArr[2];
			String oseiId = (String) oseiLvlAllcOrdQtyArr[5];
			String byrGrpCd = (String) oseiLvlAllcOrdQtyArr[3];
			float allctdOrdQty = ((BigDecimal) oseiLvlAllcOrdQtyArr[6])
					.intValue();
			updateFeatQry.setParameter(PDConstants.PRMTR_BYR_OCF_USAGE_QTY,
					allctdOrdQty);
			updateFeatQry.setParameter(PDConstants.PRMTR_PORCD, porCd);
			updateFeatQry.setParameter(PDConstants.PRMTR_ORDR_TK_BS_MNTH,
					b000020ParamOutput.getOrdTkBsMnth());
			updateFeatQry.setParameter(PDConstants.PRMTR_PRD_MNTH, prdMnth);
			updateFeatQry.setParameter(PDConstants.PRMTR_OSEI_ID, oseiId);
			updateFeatQry.setParameter(PDConstants.PRMTR_CARSRS, carSrs);
			updateFeatQry.setParameter(PDConstants.PRMTR_BYR_GRP_CD, byrGrpCd);
			updateFeatQry.setParameter(PDConstants.PRMTR_UPDT_BY,
					PDConstants.BATCH_20_ID);
			int count = updateFeatQry.executeUpdate();
			LOG.info("update  Buyer Group Limit::::::::::::::" + count);
		} catch (Exception e) {
			LOG.info(e);
			CommonUtil.logMessage(PDMessageConsants.M00164,
					PDConstants.CONSTANT_V3, new String[] {
							PDConstants.BATCH_20_ID, PDConstants.UPDATION,
							PDConstants.TRN_BUYER_MNTHLY_OCF_USG });
			throw new PdApplicationException(PDConstants.UPDATION_FAILED);
		}
	}

	/**
	 * This method is used to extract the Distinct car series from the Temp
	 * table.
	 * 
	 * @return
	 */
	public List<Object[]> extractDistinctByrGrpCd() {
		String extractDistinctByrGrpCdStr = B000020QueryConstants.extractDistinctByrGrpCd
				.toString();
		Query extractDistinctByrGrpCdQry = entityMngr
				.createNativeQuery(extractDistinctByrGrpCdStr);
		return extractDistinctByrGrpCdQry.getResultList();
	}

	/**
	 * This method is used to get the Buyer Group Level OCF Usage.
	 * 
	 * @param distntByrGrpCd
	 * @param ordrTkBsMnth
	 * @return
	 */
	public List<Object[]> extractbyrGrpLvlOcfUsg(Object[] distntByrGrpCd,
			String ordrTkBsMnth) {
		String porCd = (String) distntByrGrpCd[0];
		String prdMnth = (String) distntByrGrpCd[1];
		String carSrs = (String) distntByrGrpCd[2];
		String byrGrpCd = (String) distntByrGrpCd[3];
		String extractDistinctByrGrpCdStr = B000020QueryConstants.extractbyrGrpLvlOcfUsg
				.toString();
		Query extractDistinctByrGrpCdQry = entityMngr
				.createNativeQuery(extractDistinctByrGrpCdStr);
		extractDistinctByrGrpCdQry.setParameter(PDConstants.PRMTR_PORCD, porCd);
		extractDistinctByrGrpCdQry.setParameter(
				PDConstants.PRMTR_ORDR_TK_BS_MNTH, ordrTkBsMnth);
		extractDistinctByrGrpCdQry.setParameter(PDConstants.PRMTR_PRD_MNTH,
				prdMnth);
		extractDistinctByrGrpCdQry.setParameter(PDConstants.PRMTR_CARSRS,
				carSrs);
		extractDistinctByrGrpCdQry.setParameter(PDConstants.PRMTR_BYR_GRP_CD,
				byrGrpCd);
		return extractDistinctByrGrpCdQry.getResultList();
	}

	/**
	 * This method is used to update the Buyer Group OCF Usage Limit.
	 * 
	 * @param objArry
	 * @throws PdApplicationException
	 */
	public void updateByrGrpMnthlyOcfUsgLmt(Object[] objArry)
			throws PdApplicationException {
		try {
			BigDecimal byrUsgQty = (BigDecimal) objArry[0];
			String porCd = (String) objArry[1];
			String prdMnth = (String) objArry[8];
			String ordTkBsMnth = (String) objArry[2];
			String carSrs = (String) objArry[6];
			String byrGrpCd = (String) objArry[5];
			String ocfFrameCd = (String) objArry[4];
			String featCd = (String) objArry[3];

			String updateByrGrpMnthlyOcfUsgLmtStr = B000020QueryConstants.updateByrGrpMnthlyOcfUsgLmt
					.toString();
			Query updateByrGrpMnthlyOcfUsgLmtQty = entityMngr
					.createNativeQuery(updateByrGrpMnthlyOcfUsgLmtStr);

			updateByrGrpMnthlyOcfUsgLmtQty.setParameter(
					PDConstants.PRMTR_BYR_GRP_OCF_USAGE_QTY, byrUsgQty);
			updateByrGrpMnthlyOcfUsgLmtQty.setParameter(
					PDConstants.PRMTR_PORCD, porCd);
			updateByrGrpMnthlyOcfUsgLmtQty.setParameter(
					PDConstants.PRMTR_PRD_MNTH, prdMnth);
			updateByrGrpMnthlyOcfUsgLmtQty.setParameter(
					PDConstants.PRMTR_ORDR_TK_BS_MNTH, ordTkBsMnth);
			updateByrGrpMnthlyOcfUsgLmtQty.setParameter(
					PDConstants.PRMTR_CARSRS, carSrs);
			updateByrGrpMnthlyOcfUsgLmtQty.setParameter(
					PDConstants.PRMTR_BYR_GRP_CD, byrGrpCd);
			updateByrGrpMnthlyOcfUsgLmtQty.setParameter(
					PDConstants.PRMTR_OCF_FRAME_CD, ocfFrameCd);
			updateByrGrpMnthlyOcfUsgLmtQty.setParameter(
					PDConstants.PRMTR_FEAT_CD, featCd);
			int count = updateByrGrpMnthlyOcfUsgLmtQty.executeUpdate();
			LOG.info(" updateByrGrpMnthlyOcfUsgLmtQty" + count);
		}

		catch (Exception e) {
			LOG.info(e);
			CommonUtil.logMessage(PDMessageConsants.M00164,
					PDConstants.CONSTANT_V3, new String[] {
							PDConstants.BATCH_20_ID, PDConstants.UPDATION,
							PDConstants.TRN_BUYER_GRP_MNTHLY_OCF_LMT });
			throw new PdApplicationException(PDConstants.UPDATION_FAILED);
		}
	}

	/**
	 * This method is used to extract Buyer Group OCF Limit.
	 * 
	 * @param dstnctByrGrp
	 * @param b000020ParamOutput
	 * @return
	 */
	public List<Object[]> extractByrGrpMnthlyOcfLmt(Object[] dstnctByrGrp,
			B000020ParamOutput b000020ParamOutput) {
		String ordTkBsMnth = b000020ParamOutput.getOrdTkBsMnth();
		String porCd = (String) dstnctByrGrp[0];
		String prdMnth = (String) dstnctByrGrp[1];
		String carSrs = (String) dstnctByrGrp[2];
		char ocfRgnCd = (char) dstnctByrGrp[4];
		String ocfByrGrpCd = (String) dstnctByrGrp[5];
		String extractDistinctByrGrpCdStr = QueryConstants.fetchSumFeatUsageByRgnLvl
				.toString();

		extractDistinctByrGrpCdStr += QueryConstants.fetchSumFeatUsageByRgnLvlCnd;

		extractDistinctByrGrpCdStr += QueryConstants.fetchSumFeatUsageByRgnLvlGrpBy;
		Query extractDistinctByrGrpCdQry = entityMngr
				.createNativeQuery(extractDistinctByrGrpCdStr);
		extractDistinctByrGrpCdQry.setParameter(PDConstants.PRMTR_PORCD, porCd);
		extractDistinctByrGrpCdQry.setParameter(
				PDConstants.PRMTR_ORDR_TK_BS_MNTH, ordTkBsMnth);
		extractDistinctByrGrpCdQry.setParameter(PDConstants.PRMTR_PRD_MNTH,
				prdMnth);
		extractDistinctByrGrpCdQry.setParameter(PDConstants.PRMTR_CARSRS,
				carSrs);
		extractDistinctByrGrpCdQry.setParameter(PDConstants.PRMTR_OCFRGNCD,
				String.valueOf(ocfRgnCd));
		extractDistinctByrGrpCdQry.setParameter(PDConstants.PRMTR_OCFBYRGRPCD,
				ocfByrGrpCd);
		return extractDistinctByrGrpCdQry.getResultList();
	}

	/**
	 * This method is used to update the Regional Monthly OCF Limit.
	 * 
	 * @param byrGrpOcfDtls
	 * @throws PdApplicationException
	 */
	public void updateRgnlMnthlyLmt(Object[] byrGrpOcfDtls)
			throws PdApplicationException {

		String porCd = (String) byrGrpOcfDtls[4];
		String carSrs = (String) byrGrpOcfDtls[7];
		String prdMnth = (String) byrGrpOcfDtls[6];
		String ordTkBsMnth = (String) byrGrpOcfDtls[5];
		char ocfRgnCd = (char) byrGrpOcfDtls[8];
		String ocfByrGrpCd = (String) byrGrpOcfDtls[9];
		String ocfFrmCd = (String) byrGrpOcfDtls[2];
		String featCd = (String) byrGrpOcfDtls[1];
		BigDecimal rgnlOcfUsgLmt = (BigDecimal) byrGrpOcfDtls[0];

		String updateRgnlMnthlyLmt = B000020QueryConstants.updateRgnlMnthlyLmt
				.toString();
		try {
			Query updateRgnlMnthlyLmtQry = entityMngr
					.createNativeQuery(updateRgnlMnthlyLmt);
			updateRgnlMnthlyLmtQry.setParameter(PDConstants.PRMTR_PORCD, porCd);
			updateRgnlMnthlyLmtQry.setParameter(
					PDConstants.PRMTR_ORDR_TK_BS_MNTH, ordTkBsMnth);
			updateRgnlMnthlyLmtQry.setParameter(PDConstants.PRMTR_PRD_MNTH,
					prdMnth);
			updateRgnlMnthlyLmtQry.setParameter(PDConstants.PRMTR_CARSRS,
					carSrs);
			updateRgnlMnthlyLmtQry.setParameter(PDConstants.PRMTR_OCFRGNCD,
					ocfRgnCd);
			updateRgnlMnthlyLmtQry.setParameter(PDConstants.PRMTR_OCFBYRGRPCD,
					ocfByrGrpCd);
			updateRgnlMnthlyLmtQry.setParameter(PDConstants.PRMTR_OCF_FRAME_CD,
					ocfFrmCd);
			updateRgnlMnthlyLmtQry.setParameter(PDConstants.PRMTR_FEAT_CD,
					featCd);
			updateRgnlMnthlyLmtQry.setParameter(
					PDConstants.PRMTR_RGNL_MNTHLY_OCF_USAGE_LMT, rgnlOcfUsgLmt);
			int count = updateRgnlMnthlyLmtQry.executeUpdate();
			LOG.info("Regional OCF " + count);
		} catch (Exception e) {
			LOG.info(e);
			CommonUtil.logMessage(PDMessageConsants.M00164,
					PDConstants.CONSTANT_V3, new String[] {
							PDConstants.BATCH_20_ID, PDConstants.UPDATION,
							PDConstants.REGIONAL_MONTHLY_OCF_LIMIT_TRN });
			throw new PdApplicationException(PDConstants.UPDATION_FAILED);
		}

	}

	/**
	 * This method is used to update Batch process status
	 * 
	 * @param b000020ParamOutput
	 */
	public void updateBatchStatus(B000020ParamOutput b000020ParamOutput) {
		String seqIdVal = "B000101";
		try {
			MnthlyBatchProcessStt mnthlyBatchProcessStt = new MnthlyBatchProcessStt();
			MnthlyBatchProcessSttPK mnthlyBatchProcessSttPK = new MnthlyBatchProcessSttPK();
			mnthlyBatchProcessSttPK.setPorCd(b000020ParamOutput.getPorCd());
			mnthlyBatchProcessSttPK.setOrdrtkBaseMnth(b000020ParamOutput
					.getOrdTkBsMnth());
			mnthlyBatchProcessSttPK.setSeqId(seqIdVal);
			mnthlyBatchProcessSttPK.setBatchId(PDConstants.BATCH_20_ID);
			mnthlyBatchProcessStt.setBuyerGrpCd(b000020ParamOutput.getByrGrp());
			mnthlyBatchProcessStt.setCarSrs(b000020ParamOutput.getCrSrs());
			mnthlyBatchProcessStt.setOcfRegionCd(b000020ParamOutput
					.getOcfRgnCd());
			mnthlyBatchProcessStt.setId(mnthlyBatchProcessSttPK);
			entityMngr.merge(mnthlyBatchProcessStt);
		} catch (Exception e) {
			LOG.info(e);
			CommonUtil.logMessage(PDMessageConsants.M00164,
					PDConstants.CONSTANT_V3, new String[] {
							PDConstants.BATCH_20_ID, PDConstants.UPDATION,
							PDConstants.REGIONAL_MONTHLY_OCF_LIMIT_TRN });
		}
	}

	/**
	 * This method is used to initialize the Monthly Order TRN Order Qty.
	 * 
	 * @param mstSpecDtls
	 * @param b000020ParamOutput
	 * @throws PdApplicationException
	 */
	public void initializeMnthlyOrdrTrn(Object[] mstSpecDtls,
			B000020ParamOutput b000020ParamOutput)
			throws PdApplicationException {
		try {
			String porCd = (String) mstSpecDtls[0];
			String prdMnth = (String) mstSpecDtls[3];
			String oseiId = (String) mstSpecDtls[1];
			String initializeMnthlyOrdrTrnQryStr = QueryConstants.updateOrdrQtyInmnthlyOrderTrnB000020
					.toString();
			initializeMnthlyOrdrTrnQryStr += QueryConstants.mnthlyOrderTrnPkCnd
					.toString();
			Query initializeMnthlyOrdrTrnQry = entityMngr
					.createNativeQuery(initializeMnthlyOrdrTrnQryStr);
			initializeMnthlyOrdrTrnQry.setParameter(PDConstants.PRMTR_ORDR_QTY,
					zero);
			initializeMnthlyOrdrTrnQry.setParameter(PDConstants.PRMTR_PORCD,
					porCd);
			initializeMnthlyOrdrTrnQry.setParameter(
					PDConstants.PRMTR_ORDR_TK_BS_MNTH,
					b000020ParamOutput.getOrdTkBsMnth());
			initializeMnthlyOrdrTrnQry.setParameter(PDConstants.PRMTR_PRD_MNTH,
					prdMnth);
			initializeMnthlyOrdrTrnQry.setParameter(
					PDConstants.PRMTR_PRD_ORDR_STGCD,
					b000020ParamOutput.getProductionStageCd());
			initializeMnthlyOrdrTrnQry.setParameter(PDConstants.PRMTR_OSEI_ID,
					oseiId);
			initializeMnthlyOrdrTrnQry.setParameter(PDConstants.PRMTR_UPDTD_BY,
					PDConstants.BATCH_20_ID);
			initializeMnthlyOrdrTrnQry.executeUpdate();
		} catch (Exception e) {
			LOG.info(e);
			CommonUtil.logMessage(PDMessageConsants.M00164,
					PDConstants.CONSTANT_V3, new String[] {
							PDConstants.BATCH_20_ID, PDConstants.UPDATION,
							PDConstants.MONTHLY_ORDER_TRN });
			throw new PdApplicationException(PDConstants.UPDATION_FAILED);
		}

	}

	/**
	 * This method is used to initialize the Buyer Monthly OCF Usage Qty.
	 * 
	 * @param mstSpecDtls
	 * @param b000020ParamOutput
	 * @throws PdApplicationException
	 */
	public void initializeByrMnthlyOcfUsg(Object[] mstSpecDtls,
			B000020ParamOutput b000020ParamOutput)
			throws PdApplicationException {
		try {
			String porCd = (String) mstSpecDtls[0];
			String prdMnth = (String) mstSpecDtls[3];
			String oseiId = (String) mstSpecDtls[1];
			String initializeByrMnthlyOcfUsgStr = QueryConstants.updateByrUsgFrOseiId
					.toString();
			initializeByrMnthlyOcfUsgStr += QueryConstants.fetchByOseiIdCnd
					.toString();

			Query initializeByrMnthlyOcfUsgQry = entityMngr
					.createNativeQuery(initializeByrMnthlyOcfUsgStr);
			initializeByrMnthlyOcfUsgQry.setParameter(
					PDConstants.PRMTR_BYR_OCF_USAGE_QTY, zero);
			initializeByrMnthlyOcfUsgQry.setParameter(PDConstants.PRMTR_PORCD,
					porCd);
			initializeByrMnthlyOcfUsgQry.setParameter(
					PDConstants.PRMTR_ORDR_TK_BS_MNTH,
					b000020ParamOutput.getOrdTkBsMnth());
			initializeByrMnthlyOcfUsgQry.setParameter(
					PDConstants.PRMTR_PRD_MNTH, prdMnth);
			initializeByrMnthlyOcfUsgQry.setParameter(
					PDConstants.PRMTR_OSEI_ID, oseiId);
			initializeByrMnthlyOcfUsgQry.setParameter(
					PDConstants.PRMTR_UPDT_BY, PDConstants.BATCH_20_ID);
			initializeByrMnthlyOcfUsgQry.executeUpdate();
		} catch (Exception e) {
			LOG.info(e);
			CommonUtil.logMessage(PDMessageConsants.M00164,
					PDConstants.CONSTANT_V3, new String[] {
							PDConstants.BATCH_20_ID, PDConstants.UPDATION,
							PDConstants.TRN_BUYER_MNTHLY_OCF_USG });
			throw new PdApplicationException(PDConstants.UPDATION_FAILED);
		}
	}

	/**
	 * This method is used to initialize the Buyer Monthly OCF Usage Qty.
	 * 
	 * @param mstSpecDtls
	 * @param b000020ParamOutput
	 * @throws PdApplicationException
	 */
	public void initializeByrGrpMnthlyOcfUsg(Object[] mstSpecDtls,
			B000020ParamOutput b000020ParamOutput)
			throws PdApplicationException {
		try {
			String porCd = (String) mstSpecDtls[0];
			String prdMnth = (String) mstSpecDtls[3];
			String carSrs = (String) mstSpecDtls[5];
			String byrGrpCd = (String) mstSpecDtls[4];
			String initializeByrGrpMnthlyOcfUsgStr = QueryConstants.updateByrGrpLmt
					.toString();
			initializeByrGrpMnthlyOcfUsgStr += QueryConstants.byrGrpLmtCnd
					.toString();

			Query initializeByrGrpMnthlyOcfUsgQry = entityMngr
					.createNativeQuery(initializeByrGrpMnthlyOcfUsgStr);
			initializeByrGrpMnthlyOcfUsgQry.setParameter(
					PDConstants.PRMTR_BYR_GRP_OCF_USAGE_QTY, zero);
			initializeByrGrpMnthlyOcfUsgQry.setParameter(
					PDConstants.PRMTR_PORCD, porCd);
			initializeByrGrpMnthlyOcfUsgQry.setParameter(
					PDConstants.PRMTR_ORDR_TK_BS_MNTH,
					b000020ParamOutput.getOrdTkBsMnth());
			initializeByrGrpMnthlyOcfUsgQry.setParameter(
					PDConstants.PRMTR_PRD_MNTH, prdMnth);
			initializeByrGrpMnthlyOcfUsgQry.setParameter(
					PDConstants.PRMTR_CARSRS, carSrs);
			initializeByrGrpMnthlyOcfUsgQry.setParameter(
					PDConstants.PRMTR_BYR_GRP_CD, byrGrpCd);
			initializeByrGrpMnthlyOcfUsgQry.executeUpdate();
		} catch (Exception e) {
			LOG.info(e);
			CommonUtil.logMessage(PDMessageConsants.M00164,
					PDConstants.CONSTANT_V3, new String[] {
							PDConstants.BATCH_20_ID, PDConstants.UPDATION,
							PDConstants.TRN_BUYER_GRP_MNTHLY_OCF_LMT });
			throw new PdApplicationException(PDConstants.UPDATION_FAILED);
		}
	}

	/**
	 * Buyer Group Level OCf Limit Check.
	 * 
	 * @param mstSpecDtls
	 * @param b000020ParamOutput
	 * @return
	 */
	public Object[] extractByrGrpMnthlyOcfLmtChck(Object[] mstSpecDtls,
			B000020ParamOutput b000020ParamOutput) {
		String porCd = (String) mstSpecDtls[0];
		String prdMnth = (String) mstSpecDtls[1];
		String carSrs = (String) mstSpecDtls[2];
		String byrGrpCd = (String) mstSpecDtls[3];
		String byrGrpOcfLmtChckStr = B000020QueryConstants.extractByrGrpMnthlyOcfLmtChck
				.toString();
		List<Object[]> result;
		Query byrGrpOcfLmtChckQry = entityMngr
				.createNativeQuery(byrGrpOcfLmtChckStr);
		byrGrpOcfLmtChckQry.setParameter(PDConstants.PRMTR_PORCD, porCd);
		byrGrpOcfLmtChckQry.setParameter(PDConstants.PRMTR_ORDR_TK_BS_MNTH,
				b000020ParamOutput.getOrdTkBsMnth());
		byrGrpOcfLmtChckQry.setParameter(PDConstants.PRMTR_PRD_MNTH, prdMnth);
		byrGrpOcfLmtChckQry.setParameter(PDConstants.PRMTR_CARSRS, carSrs);
		byrGrpOcfLmtChckQry
				.setParameter(PDConstants.PRMTR_BYR_GRP_CD, byrGrpCd);
		result = byrGrpOcfLmtChckQry.getResultList();
		if (result.isEmpty()) {
			return null;
		}
		return result.get(0);
	}

	/**
	 * Buyer Group Level OCf Limit Check.
	 * 
	 * @param mstSpecDtls
	 * @param b000020ParamOutput
	 * @return
	 */
	public Object[] extractNscFrcstLmtChck(Object[] mstSpecDtls,
			B000020ParamOutput b000020ParamOutput) {
		String porCd = (String) mstSpecDtls[0];
		String prdMnth = (String) mstSpecDtls[1];
		String carSrs = (String) mstSpecDtls[2];
		String byrGrpCd = (String) mstSpecDtls[3];
		String byrGrpOcfLmtChckStr = B000020QueryConstants.extractNscFrcstLmtChck
				.toString();
		List<Object[]> result;
		Query byrGrpOcfLmtChckQry = entityMngr
				.createNativeQuery(byrGrpOcfLmtChckStr);
		byrGrpOcfLmtChckQry.setParameter(PDConstants.PRMTR_PORCD, porCd);
		byrGrpOcfLmtChckQry.setParameter(PDConstants.PRMTR_ORDR_TK_BS_MNTH,
				b000020ParamOutput.getOrdTkBsMnth());
		byrGrpOcfLmtChckQry.setParameter(PDConstants.PRMTR_PRD_MNTH, prdMnth);
		byrGrpOcfLmtChckQry.setParameter(PDConstants.PRMTR_CARSRS, carSrs);
		byrGrpOcfLmtChckQry
				.setParameter(PDConstants.PRMTR_BYR_GRP_CD, byrGrpCd);
		result = byrGrpOcfLmtChckQry.getResultList();
		if (result.isEmpty()) {
			return null;
		}
		return result.get(0);
	}

	/**
	 * This method is used to get the Monthly Batch Process Latest Sequence Id.
	 * 
	 * @return String Seq no.
	 */
	public String getMnthlyBtchPrcssSeqNo() {
		String byrGrpOcfLmtChckStr = B000020QueryConstants.MNTHLY_BTCH_STTS_SEQ_ID
				.toString();
		Query mnthkyBtchSttsSqId = entityMngr
				.createNativeQuery(byrGrpOcfLmtChckStr);
		@SuppressWarnings("unchecked")
		List<Object[]> result = mnthkyBtchSttsSqId.getResultList();
		String seqNo = CommonUtil.bigDecimaltoString(result.get(0));
		if (result.isEmpty()) {
			return null;
		}
		return seqNo;

	}

	/**
	 * This method is used to delete the data from the temporary table.
	 * 
	 */
	public void deleteTempData() {
		String deleteQryStr = B000020QueryConstants.deleteTmpTbl.toString();
		Query deleteQry = entityMngr.createNativeQuery(deleteQryStr);
		int count = deleteQry.executeUpdate();
		String deleteQryStrClrLvl = B000020QueryConstants.deleteTmpTblClrLvl
				.toString();
		Query deleteQryClrLvl = entityMngr
				.createNativeQuery(deleteQryStrClrLvl);
		int countclr = deleteQryClrLvl.executeUpdate();
		LOG.info("Delete Count end item level : " + count);
		LOG.info("Color Level Delete Count	  : " + countclr);
	}

	/**
	 * @param porCd
	 */
	public void deleteDailtOcfIf(String porCd) {
		String deleteQryStr = B000020QueryConstants.deleteDailyOcfIf.toString();
		Query deleteQry = entityMngr.createNativeQuery(deleteQryStr);
		deleteQry.setParameter(PDConstants.PRMTR_PORCD, porCd);
		 deleteQry.executeUpdate();
	}

	/**
	 * @param porCd
	 */
	public void deleteMnthlySchdlIf(String porCd) {
		// creating

		Connection connection = null;
		Session session = (Session) entityMngr.getDelegate();
		SessionFactoryImplementor sessionFactoryImplementation = (SessionFactoryImplementor) session
				.getSessionFactory();
		ConnectionProvider connectionProvider = sessionFactoryImplementation
				.getConnectionProvider();
		PreparedStatement preparedStmt;
		try {
			connection = connectionProvider.getConnection();
			preparedStmt = connection
					.prepareStatement(B000020QueryConstants.deleteMnthlySchdlIf
							.toString());
			preparedStmt.setString(1, porCd);
			preparedStmt.executeUpdate();
		} catch (SQLException e) {
			LOG.info("SQLException - " + e);
		}
	}

	/**
	 * @param porCd
	 * @param ProdMnth
	 */
	public void deleteDailyOcf(String porCd, String ProdMnth) {
		String deleteQryStr = B000020QueryConstants.deleteDailyOcf.toString();
		Query deleteQry = entityMngr.createNativeQuery(deleteQryStr);
		deleteQry.setParameter(PDConstants.PRMTR_PORCD, porCd);
		deleteQry.setParameter(PDConstants.PRMTR_PRD_MNTH, ProdMnth);
		 deleteQry.executeUpdate();
	}

	/**
	 * @param porCd
	 * @return
	 */
	public String getPotCd(String porCd) {
		String getPotCdQry = QueryConstants.getPotCdFrmMstPrm.toString();
		String potCd = null;
		Query query = entityMngr.createNativeQuery(getPotCdQry);
		query.setParameter(PDConstants.PRMTR_PORCD, porCd);
		query.setParameter(PDConstants.PRMTR_POT_CD, PDConstants.POT_CD);
		try {
			potCd = CommonUtil.convertObjectToString(query.getSingleResult());
		} catch (Exception e) {
			LOG.error(e);
			return null;
		}
		return potCd;
	}

	/**
	 * @param porCd
	 * @param prdMnth
	 * @return
	 */
	public List<MstWkNoClndr> getWeekNODtls(String porCd, List<String> prdMnth) {
		String extractWkNoCldr = QueryConstants.ExtractWeekNoDtls.toString();
		Query extractWeekNoDtldQry = entityMngr.createNativeQuery(
				extractWkNoCldr, MstWkNoClndr.class);
		extractWeekNoDtldQry.setParameter(PDConstants.PRMTR_PORCD, porCd);
		extractWeekNoDtldQry.setParameter(PDConstants.PRMTR_PRD_MNTH, prdMnth);
		return extractWeekNoDtldQry.getResultList();
	}

	/**
	 * @return
	 */
	public BigDecimal getSqNoMnthlyProdSchdl() {
		String sqNOMnthlyProdStr = QueryConstants.TRN_MNTHLY_PROD_SCHDL_SEQ_ID
				.toString();
		Query sqNOMnthlyProdQry = entityMngr
				.createNativeQuery(sqNOMnthlyProdStr);
		return (BigDecimal) sqNOMnthlyProdQry.getSingleResult();
	}

	/**
	 * @param porCd
	 */
	public void deleteMnthlyOcfIf(String porCd) {
		String deleteQryStr = I000026QueryConstants.DeleteQuery.toString();
		Query deleteQry = entityMngr.createNativeQuery(deleteQryStr);
		deleteQry.setParameter(PDConstants.PRMTR_PORCD, porCd);
		deleteQry.executeUpdate();
	}
}
