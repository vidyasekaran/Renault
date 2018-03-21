package com.nissangroups.pd.repository;

import static com.nissangroups.pd.util.PDConstants.DATE_FORMAT;
import static com.nissangroups.pd.util.PDConstants.DATE_FORMAT_MONTH;
import static com.nissangroups.pd.util.PDConstants.PERSISTENCE_NAME;
import static com.nissangroups.pd.util.PDConstants.PRMTR_CAR_SRS;
import static com.nissangroups.pd.util.PDConstants.PRMTR_ERROR_CD;
import static com.nissangroups.pd.util.PDConstants.PRMTR_ERROR_MSG;
import static com.nissangroups.pd.util.PDConstants.PRMTR_ORDR_TK_BS_MNTH;
import static com.nissangroups.pd.util.PDConstants.PRMTR_OSEI_ID;
import static com.nissangroups.pd.util.PDConstants.PRMTR_PORCD;
import static com.nissangroups.pd.util.PDConstants.PRMTR_PRD_MNTH;
import static com.nissangroups.pd.util.PDConstants.PRMTR_SEQ_NO;
import static com.nissangroups.pd.util.PDConstants.STEP_AFTER_END;
import static com.nissangroups.pd.util.PDConstants.STEP_AFTER_START;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.nissangroups.pd.exception.PdApplicationException;
import com.nissangroups.pd.exception.PdApplicationNonFatalException;
import com.nissangroups.pd.model.MstExNo;
import com.nissangroups.pd.model.MstExNoPK;
import com.nissangroups.pd.model.MstOseiDtl;
import com.nissangroups.pd.model.MstPrmtr;
import com.nissangroups.pd.model.TrnMnthProdShdlIf;
import com.nissangroups.pd.r000020.bean.R000020ExNoBean;
import com.nissangroups.pd.r000020.bean.R000020InputParamBean;
import com.nissangroups.pd.r000020.util.R000020QueryConstants;
import com.nissangroups.pd.util.CommonUtil;
import com.nissangroups.pd.util.PDConstants;
import com.nissangroups.pd.util.QueryConstants;

public class MnthlySchdlIfTrnRepository {
	/** Object emgr. */
	@PersistenceContext(name = PERSISTENCE_NAME)
	private EntityManager eMgr;

	@Autowired(required = false)
	private WeekNoCalendarRepository wkNoCalendarRepositoryObj;

	private static final Log LOG = LogFactory.getLog
			(MnthlySchdlIfTrnRepository.class);

	/**
	 * This method is used to update the error code.
	 * 
	 * @param porCd
	 * @param prdOrdrStgCd
	 * @return - List of Row No's having POR CD present in TRN MONTHLY ORDER
	 *         INTERFACE and not present in POR MST
	 * @throws PdApplicationNonFatalException
	 */
	public void updateErrorCd(List<Integer> seqNo, String errorCd,
			String errorMessage, R000020InputParamBean input)
			throws PdApplicationException {
		String errMsg = null;
		if (errorCd.equalsIgnoreCase(PDConstants.PRMTR_R20_WRN_CD)
				|| errorCd.equalsIgnoreCase(PDConstants.PRMTR_R20_WRN_CD_SKIP)) {
			errMsg = PDConstants.R20_WARN + errorMessage;
		} else {
			errMsg = PDConstants.R20_ERROR + errorMessage;
		}

		if (seqNo != null && !(seqNo.isEmpty())) {
			StringBuilder dynamicQuery = new StringBuilder();

			dynamicQuery.append(QueryConstants.updateErrorCdR20);
			dynamicQuery.append(QueryConstants.updateInvldPorCdCndR20);
			String queryStr = dynamicQuery.toString();
			queryStr = queryStr.replaceAll(PDConstants.TABLE_NAME,
					input.getTableName());

			// split list in to 10 size lists
			Query query = eMgr.createNativeQuery(queryStr.toString());
			query.setParameter(PRMTR_ERROR_CD, errorCd);
			query.setParameter(PRMTR_ERROR_MSG, errMsg);
			int count = 10;
			List seqNoList = new ArrayList<>();
			int i = 0;
			for (int j = 0; j < seqNo.size(); j++) {
				if (i < count) {
					seqNoList.add(seqNo.get(j));
					i++;
				} else {
					i = 0;
					query.setParameter(PRMTR_SEQ_NO, seqNoList);
					query.executeUpdate();
					seqNoList = new ArrayList<>();
				}

			}
			if (seqNoList != null && !(seqNoList.isEmpty())) {
				query.setParameter(PRMTR_SEQ_NO, seqNoList);
				query.executeUpdate();
			}

		}

	}

	public void updateErrorCd(Integer seqNo, String errorCd,
			String errorMessage, R000020InputParamBean input)
			throws PdApplicationException {
		String erMsg = null;
		if (errorCd.equalsIgnoreCase(PDConstants.PRMTR_R20_WRN_CD)
				|| errorCd.equalsIgnoreCase(PDConstants.PRMTR_R20_WRN_CD_SKIP)) {
			erMsg = PDConstants.R20_WARN + errorMessage;
		} else {
			erMsg = PDConstants.R20_ERROR + errorMessage;
		}
		StringBuilder dynamicQuery = new StringBuilder();
		dynamicQuery.append(QueryConstants.updateErrorCdR20);
		dynamicQuery.append(QueryConstants.updateInvldPorCdCndR20);
		String queryStr = dynamicQuery.toString();
		queryStr = queryStr.replaceAll(PDConstants.TABLE_NAME,
				input.getTableName());
		// split list in to 10 size lists
		Query query = eMgr.createNativeQuery(queryStr.toString());
		query.setParameter(PRMTR_ERROR_CD, errorCd);
		query.setParameter(PRMTR_ERROR_MSG, erMsg);
		query.setParameter(PRMTR_SEQ_NO, seqNo);
		query.executeUpdate();

	}

	/**
	 * This method is used to fetch the distinct car series.
	 * 
	 * @param input
	 * @return
	 */
	public List<Object[]> fetchDistinctCarSrs(R000020InputParamBean input) {

		StringBuilder dynamicQuery = new StringBuilder();

		dynamicQuery.append(R000020QueryConstants.fetchDistinctCarSrs);

		String queryStr = dynamicQuery.toString();
		queryStr = queryStr.replaceAll(PDConstants.TABLE_NAME,
				input.getTableName());

		Query query = eMgr.createNativeQuery(queryStr);
		query.setParameter(PRMTR_PORCD, input.getPorCd());
		List<Object[]> result = query.getResultList();

		return result;

	}

	/**
	 * This method is used to attach the valid OSEI Id.
	 * 
	 * @param porCd
	 * @return
	 */
	public List<Object[]> fetchValidOseiID(String porCd) {
		StringBuilder dynamicQuery = new StringBuilder();
		dynamicQuery.append(R000020QueryConstants.fetchOseiDtls);
		String queryStr = dynamicQuery.toString();
		Query query = eMgr.createNativeQuery(queryStr);
		query.setParameter(PRMTR_PORCD, porCd);
		List<Object[]> result = query.getResultList();
		return result;

	}

	/**
	 * This method is used to attach the UKOSEI Id.
	 * 
	 * @param qryPrmBean
	 */
	public void attachOseiId(QueryParamBean qryPrmBean) {
		StringBuilder dynamicQuery = new StringBuilder();
		dynamicQuery.append(R000020QueryConstants.attachOseiDtls);
		Query query = eMgr.createNativeQuery(dynamicQuery.toString());
		query.setParameter(PDConstants.PRMTR_PORCD, qryPrmBean.getPorCd());
		query.setParameter(PDConstants.PRMTR_OSEI_ID, qryPrmBean.getOseiId());
		query.setParameter(PDConstants.PRMTR_PRODUCTION_FAMILY_CODE,
				qryPrmBean.getProdFmlyCd());
		query.setParameter(PDConstants.PRMTR_CAR_SRS, qryPrmBean.getCarSrs());
		query.setParameter(PDConstants.PRMTR_BYR_CD, qryPrmBean.getByrCd());
		query.setParameter(PDConstants.PRMTR_APPLD_MDL_CD,
				qryPrmBean.getAppldMdlCd());
		query.setParameter(PDConstants.PRMTR_PCK_CD, qryPrmBean.getPckCd());
		query.setParameter(PDConstants.PRMTR_SPEC_DEST_CD,
				qryPrmBean.getSpecDstnCd());
		query.setParameter(PDConstants.PRMTR_ADTNL_SPEC_CD,
				qryPrmBean.getAdntlSpecCd());
		query.setParameter(PDConstants.PRMTR_EXT_CLR_CD,
				qryPrmBean.getExtClrCd());
		query.setParameter(PDConstants.PRMTR_INT_CLR_CD,
				qryPrmBean.getIntClrCd());
		query.executeUpdate();
	}

	public List<Object[]> getErrorData(QueryParamBean qryPrmBean) {
		String errorDataQry = R000020QueryConstants.fetchErrorData.toString();
		Query query = eMgr.createNativeQuery(errorDataQry);
		query.setParameter(PDConstants.PRMTR_PORCD, qryPrmBean.getPorCd());
		return query.getResultList();
	}
	
	public List<Object[]> getErrorDataCount(QueryParamBean qryPrmBean) {
		String errorDataQry = R000020QueryConstants.fetchErrorDataCount.toString();
		Query query = eMgr.createNativeQuery(errorDataQry);
		query.setParameter(PDConstants.PRMTR_PORCD, qryPrmBean.getPorCd());
		return query.getResultList();
	}

	public void updateInVldPrdMnth(String errorCd, String errorMessage,
			R000020InputParamBean input, String carSrs, String ordrTkBsMnth,
			String prdMnth, String dateDtls, Object[] obj)
			throws PdApplicationException {
		String errMsg = null;
		StringBuilder dynamicQuery = new StringBuilder();
		dynamicQuery.append(R000020QueryConstants.updateErrorCdFrInVldPrdMnth);

		String queryStr = dynamicQuery.toString();
		queryStr = queryStr.replaceAll(PDConstants.TABLE_NAME,
				input.getTableName());
		if (errorCd.equalsIgnoreCase(PDConstants.PRMTR_R20_WRN_CD)
				|| errorCd.equalsIgnoreCase(PDConstants.PRMTR_R20_WRN_CD_SKIP)) {
			errMsg = PDConstants.R20_WARN + errorMessage;
		} else {
			errMsg = PDConstants.R20_ERROR + errorMessage;
		}
		Query query = eMgr.createNativeQuery(queryStr.toString());
		query.setParameter(PRMTR_ERROR_CD, errorCd);
		query.setParameter(PRMTR_ERROR_MSG, errMsg);
		query.setParameter(PRMTR_PORCD, input.getPorCd());
		query.setParameter(PRMTR_ORDR_TK_BS_MNTH, ordrTkBsMnth);
		query.setParameter(PRMTR_CAR_SRS, carSrs);
		query.setParameter(PRMTR_PRD_MNTH, prdMnth);
		query.executeUpdate();
	}

	public String getProdStgCdList(QueryParamBean qryPrmBean) {
		StringBuilder dynamicQuery = new StringBuilder();
		dynamicQuery.append(R000020QueryConstants.getPrdStgCd);
		String queryStr = dynamicQuery.toString();
		Query query = eMgr.createNativeQuery(queryStr.toString());
		query.setParameter(PDConstants.PRMTR_PRMTR_CD, qryPrmBean.getPrmtrCd());
		query.setParameter(PDConstants.PRMTR_KEY1, qryPrmBean.getKey1());
		query.setParameter(PDConstants.PRMTR_KEY2, qryPrmBean.getKey2());
		return (String) query.getSingleResult();
	}

	public List<String> fetchOseiIdFrInVldPrdMnth(R000020InputParamBean input,
			String carSrs, String ordrTkBsMnth, String prdMnth)
			throws PdApplicationException {

		StringBuilder dynamicQuery = new StringBuilder();

		dynamicQuery.append(R000020QueryConstants.fetchOseiIdFrInVldPrdMnth);

		String queryStr = dynamicQuery.toString();
		queryStr = queryStr.replaceAll(PDConstants.TABLE_NAME,
				input.getTableName());

		Query query = eMgr.createNativeQuery(queryStr);

		query.setParameter(PRMTR_PORCD, input.getPorCd());
		query.setParameter(PRMTR_ORDR_TK_BS_MNTH, ordrTkBsMnth);
		query.setParameter(PRMTR_CAR_SRS, carSrs);
		query.setParameter(PRMTR_PRD_MNTH, prdMnth);

		List<String> result = query.getResultList();

		return result;
	}

	public Object[] fetchNearestAbolishAdoptDates(R000020InputParamBean input,
			String carSrs, String ordrTkBsMnth, String prdMnth, String oseiId,
			String carSrsAdptDate, String carSrsAblshDate)
			throws PdApplicationNonFatalException, ParseException {

		prdMnth = prdMnth + "11";
		StringBuilder dynamicQuery = new StringBuilder();
		dynamicQuery.append(QueryConstants.fetchAdoptAbolishDatesByOseiId);

		String queryStr = dynamicQuery.toString();
		Query query = eMgr.createNativeQuery(queryStr, MstOseiDtl.class);

		String nearstAdptPrd = "", nearstAblshPrd = "";

		query.setParameter(PRMTR_OSEI_ID, oseiId);
		List<MstOseiDtl> distinctOseiDtl = query.getResultList();
		int flag = 0;
		/*
		 * Adopt Date YYYYMMDD Adopt Period YYYYMMWD Abolish Date YYYYMMDD
		 * Abolish Period YYYYMMWD Abolish Month YYYYMM
		 */
		Date prdMnthDate = CommonUtil.convertStringToDate(prdMnth);
		long min = 1000000000;
		for (MstOseiDtl oseiDtl : distinctOseiDtl) {

			if (prdMnth.compareTo(oseiDtl.getId().getOseiAdptDate()) >= 0
					&& prdMnth.compareTo(oseiDtl.getId().getOseiAblshDate()) <= 0) {
				flag = 1;
				break;
			} else {
				if (nearstAdptPrd.equalsIgnoreCase("")) {
					nearstAdptPrd = oseiDtl.getId().getOseiAdptDate();
				}
				if (nearstAblshPrd.equalsIgnoreCase("")) {
					nearstAblshPrd = oseiDtl.getId().getOseiAblshDate();
				}
				Date oseiAdptPrdDate = CommonUtil.convertStringToDate(oseiDtl
						.getId().getOseiAdptDate());
				Date oseiAblshPrdDate = CommonUtil.convertStringToDate(oseiDtl
						.getId().getOseiAblshDate());
				Date nearstAdptPrdDate = CommonUtil
						.convertStringToDate(nearstAdptPrd);
				Date nearstAblshPrdDate = CommonUtil
						.convertStringToDate(nearstAblshPrd);

				long diffInOseiAdptDate = prdMnthDate.getTime()
						- oseiAdptPrdDate.getTime();
				// long diffInNearestAdptDate = prdMnthDate.getTime() -
				// nearstAdptPrdDate.getTime();
				long diffInOseiAblshDate = oseiAblshPrdDate.getTime()
						- prdMnthDate.getTime();
				// long diffInNearestAblshDate = nearstAblshPrdDate.getTime() -
				// prdMnthDate.getTime();

				if (diffInOseiAdptDate < min || diffInOseiAblshDate < min) {
					nearstAdptPrdDate = oseiAdptPrdDate;
					nearstAblshPrdDate = oseiAblshPrdDate;
					if (diffInOseiAdptDate < diffInOseiAblshDate) {
						min = diffInOseiAdptDate;
					} else {
						min = diffInOseiAblshDate;
					}
				}

			}

		}

		if (flag == 1) {
			Object[] obj = { "true" };
			return obj;
		}

		String ablshMnth = wkNoCalendarRepositoryObj.fetchEIMDate(
				input.getPorCd(), nearstAblshPrd, DATE_FORMAT_MONTH);
		String nearstAblshDate = wkNoCalendarRepositoryObj.fetchEIMDate(
				input.getPorCd(), nearstAblshPrd, DATE_FORMAT);
		String nearstAdptDate = wkNoCalendarRepositoryObj.fetchEIMDate(
				input.getPorCd(), nearstAdptPrd, DATE_FORMAT);

		Object[] obj = { "false", nearstAdptDate, nearstAdptPrd,
				nearstAblshDate, nearstAblshPrd, ablshMnth };
		return obj;
	}

	public List<MstPrmtr> getFrznVldFlg(QueryParamBean qryPrmBean) {
		StringBuilder dynamicQuery = new StringBuilder();
		dynamicQuery.append(R000020QueryConstants.getFrznCdFlg);
		String queryStr = dynamicQuery.toString();
		Query query = eMgr.createNativeQuery(queryStr, MstPrmtr.class);
		query.setParameter(PDConstants.PRMTR_PRMTR_CD, qryPrmBean.getPrmtrCd());
		query.setParameter(PDConstants.PRMTR_KEY1, qryPrmBean.getKey1());
		query.setParameter(PDConstants.PRMTR_KEY2, qryPrmBean.getKey2());
		return query.getResultList();
	}

	public List<MstPrmtr> getExNoVldnFlg(QueryParamBean qryPrmBean) {

		LOG.info(STEP_AFTER_START);
		StringBuilder dynamicQuery = new StringBuilder();
		dynamicQuery.append(R000020QueryConstants.getExNoVldnFlg);
		String queryStr = dynamicQuery.toString();
		Query query = eMgr.createNativeQuery(queryStr, MstPrmtr.class);
		query.setParameter(PDConstants.PRMTR_PRMTR_CD, qryPrmBean.getPrmtrCd());
		query.setParameter(PDConstants.PRMTR_KEY1, qryPrmBean.getKey1());
		LOG.info(STEP_AFTER_END);
		return query.getResultList();
	}

	public boolean validateExNo(R000020ExNoBean exNoBean) {
		StringBuilder dynamicQuery = new StringBuilder();
		dynamicQuery.append(R000020QueryConstants.getVldExNo);
		String queryStr = dynamicQuery.toString();
		Query query = eMgr.createNativeQuery(queryStr);
		query.setParameter(PDConstants.PRMTR_PRMTR_CD,
				PDConstants.PRMTR_R20_EX_NO_VALIDATION);
		query.setParameter(PDConstants.PRMTR_KEY1, exNoBean.getPorCd());
		query.setParameter(PDConstants.PRMTR_VAL_1, exNoBean.getExNo()
				.substring(1, 5));
		query.setParameter(PDConstants.PRMTR_VAL_2, exNoBean.getExNo()
				.substring(1, 5));
		List<Object[]> validateExNoList = query.getResultList();
		if (validateExNoList.isEmpty()) {
			return false;
		}
		return true;
	}

	public List<String> extractVldOcfCd(QueryParamBean qryPrmBn) {
		StringBuilder dynamicQuery = new StringBuilder();
		dynamicQuery.append(R000020QueryConstants.extractVldOcfCd);
		String queryStr = dynamicQuery.toString();
		Query query = eMgr.createNativeQuery(queryStr);
		query.setParameter(PDConstants.PRMTR_PORCD, qryPrmBn.getPorCd());
		query.setParameter(PDConstants.PRMTR_OCFRGNCD, qryPrmBn.getOcfRgnCd());
		return query.getResultList();
	}
}
