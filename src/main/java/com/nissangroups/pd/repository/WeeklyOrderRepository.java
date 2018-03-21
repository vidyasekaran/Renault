package com.nissangroups.pd.repository;

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
import javax.persistence.Query;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.nissangroups.pd.b000043.util.B000043QueryConstants;
import com.nissangroups.pd.b000051.util.B000051Constants;
import com.nissangroups.pd.b000051.util.B000051QueryConstants;
import com.nissangroups.pd.b000052.util.B000052QueryConstants;
import com.nissangroups.pd.b000054.util.B000054QueryConstants;
import com.nissangroups.pd.model.TrnWklyOrdrTemp;
import com.nissangroups.pd.util.CommonUtil;
import com.nissangroups.pd.util.PDConstants;
import com.nissangroups.pd.util.PDMessageConsants;

public class WeeklyOrderRepository {

	@PersistenceContext(name = PDConstants.PERSISTENCE_NAME)
	private EntityManager entityMngr;

	private static final Log LOG = LogFactory
			.getLog(WeeklyOrderRepository.class.getName());

	DateFormat formatter = new SimpleDateFormat(PDConstants.DATE_FORMAT_MONTH);

	CommonUtil cmnUtil = new CommonUtil();

	/** Variable date. */
	Date date = new Date();

	/** Variable create date. */
	Timestamp createDate = new Timestamp(date.getTime());

	public String getWkFxCs(QueryParamBean qryParamBean) {
		String multipleWkCs = null;
		String getMultiplewkFxCs = B000051QueryConstants.getWkFxCsQry
				.toString();
		Query query = entityMngr.createNativeQuery(getMultiplewkFxCs);
		query.setParameter(PDConstants.KEY_1, qryParamBean.getPorCd());
		query.setParameter(PDConstants.PARAMETER_CD,
				PDConstants.MULTIPLE_WEEK_FIX_CASE);
		List<Object[]> results = query.getResultList();
		if (!results.isEmpty()) {
			multipleWkCs = results.get(0)[0].toString();
		} else {
			String errMsg = PDMessageConsants.M00160;
			Map<String, String> errPrm = new HashMap<String, String>();
			errPrm.put("1", B000051Constants.BATCH_ID_B000051);
			errPrm.put("2", PDConstants.MULTIPLE_WEEK_FIX_CASE);
			errPrm.put("3", qryParamBean.getPorCd());
			errPrm.put("4", PDConstants.MESSAGE_MST_PARAMETER);
			LOG.error(cmnUtil.getlogErrorMessage(errMsg, errPrm));
			cmnUtil.stopBatch();
		}
		return multipleWkCs;

	}

	public String getPrdMnth(QueryParamBean qryParamBean) {
		String ordrTkBsMnth = null;
		String getPrdMnth = B000051QueryConstants.getPrdMnthQry.toString();
		Query query = entityMngr.createNativeQuery(getPrdMnth);
		query.setParameter(PDConstants.PRMTR_PORCD, qryParamBean.getPorCd());
		query.setParameter(PDConstants.PRMTR_ORDR_TK_BS_MNTH,
				qryParamBean.getOrdrTkBsMnth());
		List<Object[]> results = query.getResultList();
		if (!results.isEmpty()) {
			ordrTkBsMnth = results.get(0)[0].toString();
		}
		return ordrTkBsMnth;
	}

	public List<String> getWeekNumCal(QueryParamBean qryParamBean,
			String ordrTkBsMnth, int ordrTkBsWkNo) {
		List<String> WkNumLst = new ArrayList();
		String getWkNumCalender = B000051QueryConstants.getWkNumCalenderQry
				.toString();
		Query query = entityMngr.createNativeQuery(getWkNumCalender);
		query.setParameter(PDConstants.PRMTR_PORCD, qryParamBean.getPorCd());
		query.setParameter(PDConstants.PRMTR_ORDR_TK_BS_MNTH, ordrTkBsMnth);
		List<Object[]> results = query.getResultList();
		for (Object[] temp : results) {
			int wkNo = Integer.parseInt(temp[0].toString());
			if (wkNo >= ordrTkBsWkNo) {
				WkNumLst.add(wkNo + "");
			}
		}
		return WkNumLst;
	}

	public String getWkHrzn(QueryParamBean qryParamBean) {
		String hrzn = null;
		String getWkHrzn = B000051QueryConstants.getWkFxCsQry.toString();
		Query query = entityMngr.createNativeQuery(getWkHrzn);
		query.setParameter(PDConstants.KEY_1, qryParamBean.getPorCd());
		query.setParameter(PDConstants.PARAMETER_CD,
				PDConstants.WEEKLY_ORDER_HORIZON);
		List<Object[]> results = query.getResultList();
		if (!results.isEmpty()) {
			hrzn = results.get(0)[0].toString();
		} else {
			String errMsg = PDMessageConsants.M00160;
			Map<String, String> errPrm = new HashMap<String, String>();
			errPrm.put("1", B000051Constants.BATCH_ID_B000051);
			errPrm.put("2", PDConstants.WEEKLY_ORDER_HORIZON);
			errPrm.put("3", qryParamBean.getPorCd());
			errPrm.put("4", PDConstants.MESSAGE_MST_PARAMETER);
			LOG.error(cmnUtil.getlogErrorMessage(errMsg, errPrm));
			cmnUtil.stopBatch();
		}
		return hrzn;
	}

	public List<Object[]> getOrdrInfoMstSchdl(QueryParamBean qryParamBean,
			List<String> prdMnthWkNo) {

		String getOrdrInfo = B000051QueryConstants.getOrdrInfoMstSchdule
				.toString();
		Query query = entityMngr.createNativeQuery(getOrdrInfo);
		query.setParameter(PDConstants.PRMTR_PORCD, qryParamBean.getPorCd());
		query.setParameter(PDConstants.PRMTR_PRD_MNTH,
				qryParamBean.getPrdMnth());
		query.setParameter(PDConstants.PRMTR_WK_NO, prdMnthWkNo);
		return query.getResultList();
	}

	public void updtOrdrInfo(QueryParamBean qryParamBean,
			List<String> prdMnthWkNo) {
		try {
			String updtOrdrQty = B000051QueryConstants.updtOrdrQtyInitQry
					.toString();
			Query query = entityMngr.createNativeQuery(updtOrdrQty);
			query.setParameter(PDConstants.PRMTR_PORCD, qryParamBean.getPorCd());
			query.setParameter(PDConstants.PRMTR_PRD_MNTH,
					qryParamBean.getPrdMnth());
			query.setParameter(PDConstants.PRMTR_WK_NO, prdMnthWkNo);
			query.setParameter(PDConstants.PRMTR_UPDT_BY,
					B000051Constants.BATCH_ID_B000051);
			query.executeUpdate();
		} catch (Exception e) {
			String errMsg = PDMessageConsants.M00164;
			Map<String, String> errPrm = new HashMap<String, String>();
			errPrm.put("1", B000051Constants.BATCH_ID_B000051);
			errPrm.put("2", PDConstants.UPDATION);
			errPrm.put("3", PDConstants.WEEKLY_ORDER_TRN);
			LOG.error(cmnUtil.getlogErrorMessage(errMsg, errPrm));
			LOG.error(e);
			cmnUtil.stopBatch();
		}

	}

	public List<Object[]> getOrdrInfoWklyOrdrTrn(QueryParamBean qryParamBean,
			List<String> prdMnthWkNo) throws ParseException {
		String err = PDMessageConsants.M00160;
		String getOrdrInfo = B000051QueryConstants.getOrdrInfoWklyOrdrTrnQry
				.toString();
		String getPotCd = B000051QueryConstants.getMstPrmtr.toString();
		String getDefPotCd = B000051QueryConstants.getWkFxCsQry.toString();
		String potCd = null;
		List<Object[]> ordrInfo = new ArrayList<Object[]>();

		for (int i = 0; i < prdMnthWkNo.size(); i++) {
			Query query = entityMngr.createNativeQuery(getOrdrInfo);
			query.setParameter(PDConstants.PRMTR_PORCD, qryParamBean.getPorCd());
			query.setParameter(PDConstants.PRMTR_WK_NO,
					qryParamBean.getPrdMnth() + prdMnthWkNo.get(i) + 1);
			List<Object[]> results = query.getResultList();

			// POR_CD,md.OSEI_ADPT_DATE,md.OSEI_ABLSH_DATE,mo.OSEI_ID,mb.BUYER_CD
			for (Object[] temp : results) {
				Query potQuery = entityMngr.createNativeQuery(getPotCd);
				potQuery.setParameter(PDConstants.KEY_1,
						qryParamBean.getPorCd());
				potQuery.setParameter(PDConstants.KEY_2, temp[5].toString());
				potQuery.setParameter(PDConstants.PARAMETER_CD,
						PDConstants.POT_CD);
				List<Object[]> resultsPot = potQuery.getResultList();
				if (!resultsPot.isEmpty()) {
					potCd = resultsPot.get(0)[0].toString();
				} else {
					Map<String, String> errPrm1 = new HashMap<String, String>();
					errPrm1.put("1", B000051Constants.BATCH_ID_B000051);
					errPrm1.put("2", PDConstants.POT_CD);
					errPrm1.put("3", qryParamBean.getPorCd());
					errPrm1.put("4", PDConstants.MESSAGE_MST_PARAMETER);
					LOG.warn(cmnUtil.getlogErrorMessage(err, errPrm1));
					Query DefQuery = entityMngr.createNativeQuery(getDefPotCd);
					DefQuery.setParameter(PDConstants.KEY_1,
							qryParamBean.getPorCd());
					DefQuery.setParameter(PDConstants.PARAMETER_CD,
							PDConstants.DEFAULT_POT_CD);
					List<Object[]> defPotCd = DefQuery.getResultList();
					if (!defPotCd.isEmpty()) {
						potCd = defPotCd.get(0)[0].toString();
					} else {

						Map<String, String> errPrm = new HashMap<String, String>();
						errPrm.put("1", B000051Constants.BATCH_ID_B000051);
						errPrm.put("2", PDConstants.DEFAULT_POT_CD);
						errPrm.put("3", qryParamBean.getPorCd());
						errPrm.put("4", PDConstants.MESSAGE_MST_PARAMETER);
						LOG.error(cmnUtil.getlogErrorMessage(err, errPrm));
						cmnUtil.stopBatch();
					}
				}
				ordrInfo.add(new Object[] { temp[0], qryParamBean.getPrdMnth(),
						prdMnthWkNo.get(i), temp[4], potCd, temp[1], temp[2],
						temp[3] });
			}
		}
		return ordrInfo;
	}

	public String getJobSchdlMst(QueryParamBean qryParamBean) {
		String carSeriesHorizonQuery = B000043QueryConstants.getJobSchdlStrmData
				.toString();
		Query query = entityMngr.createNativeQuery(carSeriesHorizonQuery);
		query.setParameter(PDConstants.PRMTR_PORCD, qryParamBean.getPorCd());
		query.setParameter(PDConstants.PRMTR_ORDR_TK_BS_MNTH,
				qryParamBean.getOrdrTkBsMnth());
		query.setParameter(PDConstants.PRMTR_ORDR_TAKE_BASE_WEEK_NO,
				qryParamBean.getOrdrTkBsWkNo());
		return (String) query.getSingleResult();
	}

	public String getPrePrdMnthWK(QueryParamBean qryParamBean) {
		String prdMnthWk = B000051QueryConstants.getPrePrdMnthWKQry.toString();
		Query query = entityMngr.createNativeQuery(prdMnthWk);
		query.setParameter(PDConstants.PRMTR_PORCD, qryParamBean.getPorCd());
		query.setParameter(PDConstants.PRMTR_ORDR_TK_BS_MNTH,
				qryParamBean.getOrdrTkBsMnth());
		query.setParameter(PDConstants.PRMTR_ORDR_TAKE_BASE_WEEK_NO,
				qryParamBean.getOrdrTkBsWkNo());
		return (String) query.getSingleResult();
	}

	public List<Object[]> getOrdrInfoPreMstSchdl(QueryParamBean qryParamBean) {
		String getOrdrInfo = B000051QueryConstants.getOrdrInfoMstSchdule
				.toString();
		Query query = entityMngr.createNativeQuery(getOrdrInfo);
		query.setParameter(PDConstants.PRMTR_PORCD, qryParamBean.getPorCd());
		query.setParameter(PDConstants.PRMTR_PRD_MNTH,
				qryParamBean.getPrdMnth());
		query.setParameter(PDConstants.PRMTR_WK_NO,
				qryParamBean.getPrdMnthWkNo());
		List<Object[]> ordrInfo = query.getResultList();
		if (ordrInfo.isEmpty()) {
			String err = PDMessageConsants.M00160;
			Map<String, String> errPrm = new HashMap<String, String>();
			errPrm.put("1", B000051Constants.BATCH_ID_B000051);
			errPrm.put("2", PDConstants.REPORT_HEADER_PRD_MNTH);
			errPrm.put("3", qryParamBean.getPorCd());
			errPrm.put("4", PDConstants.LATESET_MASTER_SCHDULE_TRN);
			LOG.error(cmnUtil.getlogErrorMessage(err, errPrm));
			cmnUtil.stopBatch();
		}
		return ordrInfo;
	}

	public void updateAccQty(QueryParamBean qryParamBean) {
		String updtOrdrQty = B000051QueryConstants.updtOrdrAccptQty.toString();
		Query query = entityMngr.createNativeQuery(updtOrdrQty);
		query.setParameter(PDConstants.PRMTR_ACCEPTED_ORDER_QTY,
				qryParamBean.getAccptOrdrQty());
		query.setParameter(PDConstants.PRMTR_PORCD, qryParamBean.getPorCd());
		query.setParameter(PDConstants.PRMTR_PRD_MNTH,
				qryParamBean.getPrdMnth());
		query.setParameter(PDConstants.PRMTR_WK_NO,
				qryParamBean.getPrdMnthWkNo());
		query.setParameter(PDConstants.PRMTR_OSEI_ID, qryParamBean.getOseiId());
		query.setParameter(PDConstants.PRMTR_POT_CD, qryParamBean.getPotCd());
		query.setParameter(PDConstants.PRMTR_UPDT_BY,
				B000051Constants.BATCH_ID_B000051);
		query.executeUpdate();
	}

	public List<Object[]> gePrdMnthWkNot(QueryParamBean qryParamBean,
			List<String> prdMnthWkNoConcat) {
		String prdMthWk = B000051QueryConstants.getPrdWkNoNotIn.toString();
		Query query = entityMngr.createNativeQuery(prdMthWk);
		query.setParameter(PDConstants.PRMTR_PORCD, qryParamBean.getPorCd());
		query.setParameter(PDConstants.PRMTR_WK_NO,
				qryParamBean.getPrdMnthWkNo());
		return query.getResultList();
	}

	public void updateSuspendedFlagZero(QueryParamBean qryParamBean) {
		String updtSusPEndedFLg = B000051QueryConstants.updtSusPEndedFLgZero
				.toString();
		Query query = entityMngr.createNativeQuery(updtSusPEndedFLg);
		query.setParameter(PDConstants.PRMTR_PORCD, qryParamBean.getPorCd());
		query.setParameter(PDConstants.PRMTR_PRD_MNTH,
				qryParamBean.getPrdMnth());
		query.setParameter(PDConstants.PRMTR_WK_NO,
				qryParamBean.getPrdMnthWkNo());
		query.setParameter(PDConstants.PRMTR_OSEI_ID, qryParamBean.getOseiId());
		query.setParameter(PDConstants.PRMTR_OSEI_ADOPT_DT,
				qryParamBean.getAdptDt());
		query.setParameter(PDConstants.PRMTR_OSEI_ABOLISH_DT,
				qryParamBean.getAblshDt());
		query.setParameter(PDConstants.PRMTR_UPDT_BY,
				B000051Constants.BATCH_ID_B000051);
		query.executeUpdate();

	}

	public void updateSuspendedFlagOne(QueryParamBean qryParamBean) {
		String updtSusPEndedFLg = B000051QueryConstants.updtSusPEndedFLgOne
				.toString();
		Query query = entityMngr.createNativeQuery(updtSusPEndedFLg);
		query.setParameter(PDConstants.PRMTR_PORCD, qryParamBean.getPorCd());
		query.setParameter(PDConstants.PRMTR_PRD_MNTH,
				qryParamBean.getPrdMnth());
		query.setParameter(PDConstants.PRMTR_WK_NO,
				qryParamBean.getPrdMnthWkNo());
		query.setParameter(PDConstants.PRMTR_UPDT_BY,
				B000051Constants.BATCH_ID_B000051);
		query.executeUpdate();
	}

	public String getMsrtPrmtrVal(String porCd, String key2, String prmtrCd) {
		String plntSum = null;
		String getPlntSummary = B000051QueryConstants.getMstPrmtr.toString();
		Query query = entityMngr.createNativeQuery(getPlntSummary);
		query.setParameter(PDConstants.KEY_1, porCd);
		query.setParameter(PDConstants.KEY_2, key2);
		query.setParameter(PDConstants.PARAMETER_CD, prmtrCd);
		List<Object[]> result = query.getResultList();
		if (!result.isEmpty()) {
			plntSum = result.get(0)[0].toString();
		} else {
			String errMsg = PDMessageConsants.M00160;
			Map<String, String> errPrm = new HashMap<String, String>();
			errPrm.put("1", B000051Constants.BATCH_ID_B000051);
			errPrm.put("2", key2);
			errPrm.put("3", porCd);
			errPrm.put("4", PDConstants.MESSAGE_MST_PARAMETER);
			LOG.error(cmnUtil.getlogErrorMessage(errMsg, errPrm));
			cmnUtil.stopBatch();
		}
		return plntSum;
	}

	public List<Object[]> getMsrtPrmtrVal2(String porCd, String key2,
			String prmtrCd) {

		String getPlntSummary = B000051QueryConstants.getMstPrmtr.toString();
		Query query = entityMngr.createNativeQuery(getPlntSummary);
		query.setParameter(PDConstants.KEY_1, porCd);
		query.setParameter(PDConstants.KEY_2, key2);
		query.setParameter(PDConstants.PARAMETER_CD, prmtrCd);
		List<Object[]> result = query.getResultList();
		if (result.isEmpty()) {
			String errMsg = PDMessageConsants.M00160;
			Map<String, String> errPrm = new HashMap<String, String>();
			errPrm.put("1", B000051Constants.BATCH_ID_B000051);
			errPrm.put("2", key2);
			errPrm.put("3", porCd);
			errPrm.put("4", PDConstants.MESSAGE_MST_PARAMETER);
			LOG.error(cmnUtil.getlogErrorMessage(errMsg, errPrm));
			cmnUtil.stopBatch();
		}
		return result;
	}

	public List<Object[]> getOCFDetails(QueryParamBean qryParamBean,
			List<String> prdMnthWkNo) throws ParseException {
		List<Object[]> ocfDtails = new ArrayList<Object[]>();
		String getOcfDetails = B000051QueryConstants.getOcfDetailsQry
				.toString();
		for (int i = 0; i < prdMnthWkNo.size(); i++) {
			Query query = entityMngr.createNativeQuery(getOcfDetails);
			query.setParameter(PDConstants.PRMTR_FEAT_TYPE_CD,
					qryParamBean.getFeatTypCd());
			query.setParameter(PDConstants.PRMTR_PORCD, qryParamBean.getPorCd());
			query.setParameter(PDConstants.PRMTR_WK_NO,
					qryParamBean.getPrdMnth() + prdMnthWkNo.get(i) + 1);
			List<Object[]> results = query.getResultList();
			for (Object[] temp : results) {
				if (!temp[6].toString().equals(PDConstants.FEATURE_CODE_00)) {
					String errMsg = PDMessageConsants.M00174;
					Map<String, String> errPrm = new HashMap<String, String>();
					errPrm.put("1", temp[4].toString());
					LOG.error(cmnUtil.getlogErrorMessage(errMsg, errPrm));
					cmnUtil.stopBatch();
				}
				ocfDtails.add(new Object[] { temp[0], temp[1], temp[2],
						temp[3], temp[4], qryParamBean.getPrdMnth(),
						prdMnthWkNo.get(i), temp[5], temp[6] });
			}
		}

		return ocfDtails;
	}

	public List<Object[]> getCCFDetails(QueryParamBean qryParamBean,
			List<String> prdMnthWkNo) throws ParseException {
		List<Object[]> ccfDtails = new ArrayList<Object[]>();
		String getOcfDetails = B000051QueryConstants.getCCFDetailsQry
				.toString();
		for (int i = 0; i < prdMnthWkNo.size(); i++) {
			Query query = entityMngr.createNativeQuery(getOcfDetails);
			query.setParameter(PDConstants.PRMTR_FEAT_TYPE_CD,
					qryParamBean.getFeatTypCd());
			query.setParameter(PDConstants.PRMTR_PORCD, qryParamBean.getPorCd());
			query.setParameter(PDConstants.PRMTR_WK_NO,
					qryParamBean.getPrdMnth() + prdMnthWkNo.get(i) + 1);
			List<Object[]> results = query.getResultList();
			for (Object[] temp : results) {
				if (!temp[6].toString().equals(PDConstants.FEATURE_CODE_00)) {
					String errMsg = PDMessageConsants.M00174;
					Map<String, String> errPrm = new HashMap<String, String>();
					errPrm.put("1", temp[4].toString());
					LOG.error(cmnUtil.getlogErrorMessage(errMsg, errPrm));
					cmnUtil.stopBatch();
				}
				ccfDtails.add(new Object[] { temp[0], temp[1], temp[2],
						temp[3], temp[4], qryParamBean.getPrdMnth(),
						prdMnthWkNo.get(i), temp[5], temp[6] });
			}
		}

		return ccfDtails;
	}

	public void delByrWklyOcf(QueryParamBean qryParamBean) {

		String delByrCdUsg = B000051QueryConstants.delByrCdUsgQry.toString();
		Query query = entityMngr.createNativeQuery(delByrCdUsg);
		query.setParameter(PDConstants.PRMTR_PORCD, qryParamBean.getPorCd());
		query.setParameter(PDConstants.PRMTR_PRD_MNTH,
				qryParamBean.getPrdMnth());
		query.setParameter(PDConstants.PRMTR_WK_NO,
				qryParamBean.getPrdMnthWkNo());
		query.executeUpdate();
	}

	public List<Object[]> getUsgQtyByrLvl(QueryParamBean qryParamBean,
			String getUsgQtyp1) {
		Query query = entityMngr.createNativeQuery(getUsgQtyp1);
		query.setParameter(PDConstants.PRMTR_WK_NO, qryParamBean.getPrdMnth()
				+ qryParamBean.getPrdMnthWkNo());
		query.setParameter(PDConstants.PRMTR_PORCD, qryParamBean.getPorCd());
		return query.getResultList();
	}

	public void UpdtByrWklyOcfLmt(QueryParamBean qryParamBean) {

		String UpdtByrWklyOcfLmt = B000051QueryConstants.UpdtByrWklyOcfLmtQry
				.toString();
		Query query = entityMngr.createNativeQuery(UpdtByrWklyOcfLmt);
		query.setParameter(PDConstants.PRMTR_PORCD, qryParamBean.getPorCd());
		query.setParameter(PDConstants.PRMTR_PRD_MNTH,
				qryParamBean.getPrdMnth());
		query.setParameter(PDConstants.PRMTR_WK_NO,
				qryParamBean.getPrdMnthWkNo());
		query.setParameter(PDConstants.PRMTR_UPDT_BY,
				B000051Constants.BATCH_ID_B000051);
		query.executeUpdate();

	}

	public List<Object[]> getUsgByrOcfLmtInsrt(QueryParamBean qryParamBean) {
		Query query = entityMngr
				.createNativeQuery(B000052QueryConstants.ocftrnlimitDaySelect
						.toString());
		query.setParameter(B000052QueryConstants.POR, qryParamBean.getPorCd());
		query.setParameter(B000052QueryConstants.PRODWK,
				qryParamBean.getPrdMnthWkNo());
		query.setParameter(B000052QueryConstants.PRODMNTH,
				qryParamBean.getPrdMnth());
		query.setParameter(B000052QueryConstants.CARSRS,
				qryParamBean.getCarSrs());
		query.setParameter(B000052QueryConstants.BYRGRPCD,
				qryParamBean.getByrGrpCd());
		query.setParameter(B000052QueryConstants.FEATCD,
				qryParamBean.getFeatTypCd());
		query.setParameter(B000052QueryConstants.PLANTCD,
				qryParamBean.getPlntCd());
		query.setParameter(B000052QueryConstants.LINECLS,
				qryParamBean.getLineClass());
		return query.getResultList();
	}

	public List<Object[]> getRgnlUsgQty(QueryParamBean qryParamBean,
			List<String> prdMnthWkNo, String getUsgQtyRgnp1) {
		Query query = entityMngr.createNativeQuery(getUsgQtyRgnp1);
		query.setParameter(PDConstants.PRMTR_PORCD, qryParamBean.getPorCd());
		query.setParameter(PDConstants.PRMTR_WK_NO,
				qryParamBean.getPrdMnthWkNo() + qryParamBean.getPrdMnthWkNo());
		return query.getResultList();
	}

	public void UpdtRgnlWklyOcfLmt(QueryParamBean qryParamBean) {

		String UpdtByrWklyOcfLmt = B000051QueryConstants.UpdtRgnlWklyOcfLmtQry
				.toString();
		Query query = entityMngr.createNativeQuery(UpdtByrWklyOcfLmt);
		query.setParameter(PDConstants.PRMTR_PORCD, qryParamBean.getPorCd());
		query.setParameter(PDConstants.PRMTR_PRD_MNTH,
				qryParamBean.getPrdMnth());
		query.setParameter(PDConstants.PRMTR_WK_NO,
				qryParamBean.getPrdMnthWkNo());
		query.setParameter(PDConstants.PRMTR_CARSRS, qryParamBean.getCarSrs());
		query.setParameter(PDConstants.PRMTR_OCFBYRGRPCD,
				qryParamBean.getByrGrpCd());
		query.setParameter(PDConstants.PRMTR_OCFRGNCD,
				qryParamBean.getOcfRgnCd());
		query.setParameter(B000052QueryConstants.PLANTCD,
				qryParamBean.getPlntCd());
		query.setParameter(PDConstants.PRMTR_LINE_CLASS,
				qryParamBean.getLineClass());
		query.setParameter(PDConstants.PRMTR_PRODUCTION_DAY_NO,
				qryParamBean.getPrdDayNo());
		query.setParameter(PDConstants.PRMTR_UPDT_BY,
				B000051Constants.BATCH_ID_B000051);
		query.executeUpdate();

	}

	/**
	 * @param queryParamBean
	 */
	public List<TrnWklyOrdrTemp> getTempWeeklyOrdData(QueryParamBean queryParamBean) {
		String getTempWklyOrdStr = B000054QueryConstants.extractTempWeeklyOrd
				.toString();
//		if (queryParamBean.getCarSrs() != null
//				&& !queryParamBean.getCarSrs()
//						.equalsIgnoreCase(PDConstants.ALL)) {
//			getTempWklyOrdStr += B000020QueryConstants.andExtractMstSpecDtlsHrznQryAndCarSrs;
//		}
//		if (queryParamBean.getByrGrpCd() != null
//				&& !queryParamBean.getByrGrpCd().equalsIgnoreCase(
//						PDConstants.ALL)) {
////			getTempWklyOrdStr += andByrGrpCdQryStr;
//		}
		Query getTempWkOrdQry = entityMngr.createNativeQuery(getTempWklyOrdStr,TrnWklyOrdrTemp.class);
		getTempWkOrdQry.setParameter(PDConstants.PRMTR_PORCD,
				queryParamBean.getPorCd());

//
//		if (queryParamBean.getCarSrs() != null
//				&& !queryParamBean.getCarSrs()
//						.equalsIgnoreCase(PDConstants.ALL)) {
			getTempWkOrdQry.setParameter(PDConstants.PRMTR_CAR_SRS,
					queryParamBean.getCarSrs());
////		}
//		if (queryParamBean.getByrGrpCd() != null
//				&& !queryParamBean.getByrGrpCd().equalsIgnoreCase(
//						PDConstants.ALL)) {
			getTempWkOrdQry.setParameter(PDConstants.PRMTR_BYR_GRP_CD,
					queryParamBean.getByrGrpCd());
//		}

		return getTempWkOrdQry.getResultList();

	}
}
