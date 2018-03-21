/*
 * System Name     :Post Dragon 
 * Sub system Name :Batch 
 * Function ID     :PST-DRG-B000018/PST-DRG-R000007,R000008,R000009
 * Module          :MONTHLY ORDERING
 * Process Outline : Repository/Business Layer Class to Co-Ordinate with DB.
 * This Class performs all the necessary validations related to Frozen Order Check.  
 *
 * <Revision History>
 * Date       Name(Company Name)             Description 
 * ---------- ------------------------------ ---------------------
 * 06-OCT-2015  	 z001870(RNTBCI)               New Creation
 *
 */

package com.nissangroups.pd.repository;

import static com.nissangroups.pd.util.PDConstants.FILE_ID;
import static com.nissangroups.pd.util.PDConstants.PERSISTENCE_NAME;
import static com.nissangroups.pd.util.PDConstants.PRMTR_PORCD;
import static com.nissangroups.pd.util.PDConstants.PRMTR_SEQ_NO;
import static com.nissangroups.pd.util.PDMessageConsants.M00210;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.nissangroups.pd.b000018.bean.InputBean;
import com.nissangroups.pd.exception.PdApplicationNonFatalException;
import com.nissangroups.pd.model.MstExNo;
import com.nissangroups.pd.model.MstExNoPK;
import com.nissangroups.pd.model.MstMnthlyOrdrTakeLck;
import com.nissangroups.pd.model.MstOseiFrzn;
import com.nissangroups.pd.model.MstOseiProdType;
import com.nissangroups.pd.model.MstPrmtr;
import com.nissangroups.pd.model.MstProdType;
import com.nissangroups.pd.r000020.bean.R000020ExNoBean;
import com.nissangroups.pd.r000020.processor.NoDataVldProcessor;
import com.nissangroups.pd.r000020.util.R000020QueryConstants;
import com.nissangroups.pd.util.CommonUtil;
import com.nissangroups.pd.util.PDConstants;
import com.nissangroups.pd.util.QueryConstants;

public class VldnRepository {

	/** Object emgr. */
	@PersistenceContext(name = PERSISTENCE_NAME)
	private EntityManager eMgr;

	/** Constant LOG. */
	private static final Log LOG = LogFactory.getLog
			(VldnRepository.class);

	/** Variable order Take Base Period Mst list. */

	List<Integer> inVldPorRowNoList = new ArrayList<Integer>();

	List<Integer> inVldCarSrsRowNoList = new ArrayList<Integer>();

	List<Integer> inVldByrCdRowNoList = new ArrayList<Integer>();

	List<Integer> inVldEndItmRowNoList = new ArrayList<Integer>();

	List<Integer> inVldAddtnlSpecCdRowNoList = new ArrayList<Integer>();

	List<Integer> inVldSpecDestnCdRowNoList = new ArrayList<Integer>();

	List<Integer> inVldClrCdRowNoList = new ArrayList<Integer>();

	List<Integer> inVldPotCdRowNoList = new ArrayList<Integer>();

	List<Integer> inVldEndItmFrByrCdRowNoList = new ArrayList<Integer>();

	List<Integer> duplicateRowNoList = new ArrayList<Integer>();

	List<Integer> inVldOrdrTkBsPrdTypList = new ArrayList<Integer>();

	List<Integer> inVldOrdrTkBsMnthList = new ArrayList<Integer>();

	List<Integer> inVldPrdPeriodTypList = new ArrayList<Integer>();

	List<Integer> dueDateNullRowNoList = new ArrayList<Integer>();

	List<Integer> dueDateFrmGrtrList = new ArrayList<Integer>();

	List<Integer> inVldDueDateList = new ArrayList<Integer>();

	List<Object[]> diffDueDateRcrds = new ArrayList<Object[]>();

	@Autowired(required = false)
	private MnthlyOrdrIfTrnRepository mnthlyOrdrIfTrnRepositoryObj;

	/**
	 * 
	 * @param porCd
	 * @param prdOrdrStgCd
	 * @return - List of Row No's having POR CD present in TRN MONTHLY ORDER
	 *         INTERFACE and not present in POR MST
	 * @throws PdApplicationNonFatalException
	 */
	public List<Integer> fetchInVldPorCd(String fileId, String tableName,
			String seqNo) throws PdApplicationNonFatalException {

		StringBuilder dynamicQuery = QueryConstants.fetchInVldPorCd;

		String queryStr = dynamicQuery.toString();
		queryStr = queryStr.replaceAll(PDConstants.TABLE_NAME, tableName);

		Query query = eMgr.createNativeQuery(queryStr);
		query.setParameter(FILE_ID, fileId);
		query.setParameter(PRMTR_SEQ_NO, seqNo);
		inVldPorRowNoList = query.getResultList();

		return inVldPorRowNoList;
	}

	/**
	 * 
	 * @param porCd
	 * @param prdOrdrStgCd
	 * @return - List of Row No's having POR CD present in TRN MONTHLY ORDER
	 *         INTERFACE and not present in POR MST
	 * @throws PdApplicationNonFatalException
	 */
	public List<Integer> fetchInVldCarSrs(String porCd, String fileId,
			String tableName, String seqNo)
			throws PdApplicationNonFatalException {

		StringBuilder dynamicQuery = QueryConstants.fetchInVldCarSrs;
		String queryStr = dynamicQuery.toString();
		queryStr = queryStr.replaceAll(PDConstants.TABLE_NAME, tableName);

		inVldCarSrsRowNoList = executeQuery(queryStr, fileId, porCd, seqNo);

		return inVldCarSrsRowNoList;
	}

	public List<Integer> fetchInVldByrCd(String porCd, String fileId,
			String tableName, String seqNo)
			throws PdApplicationNonFatalException {

		StringBuilder dynamicQuery = QueryConstants.fetchInVldBuyerCd;
		String queryStr = dynamicQuery.toString();
		queryStr = queryStr.replaceAll(PDConstants.TABLE_NAME, tableName);

		inVldByrCdRowNoList = executeQuery(queryStr, fileId, porCd, seqNo);

		return inVldByrCdRowNoList;
	}

	public List<Integer> fetchInVldEndItm(String porCd, String fileId,
			String tableName, String seqNo)
			throws PdApplicationNonFatalException {

		StringBuilder dynamicQuery = new StringBuilder();
		dynamicQuery.append(QueryConstants.fetchInVldEndItem);
		dynamicQuery.append(QueryConstants.fetchInVldEndItemCnd);

		String queryStr = dynamicQuery.toString();
		queryStr = queryStr.replaceAll(PDConstants.TABLE_NAME, tableName);

		inVldEndItmRowNoList = executeQuery(queryStr, fileId, porCd, seqNo);

		dynamicQuery = new StringBuilder();
		dynamicQuery.append(QueryConstants.fetchInVldEndItemforByr);
		dynamicQuery.append(QueryConstants.fetchVldEndItemforByr);

		dynamicQuery.append(QueryConstants.fetchVldEndItemforByrCnd);
		dynamicQuery.append(QueryConstants.fetchInVldEndItemforByrCnd);

		queryStr = dynamicQuery.toString();
		queryStr = queryStr.replaceAll(PDConstants.TABLE_NAME, tableName);

		inVldEndItmFrByrCdRowNoList = executeQuery(queryStr, fileId, porCd,
				seqNo);

		inVldEndItmRowNoList.addAll(inVldEndItmFrByrCdRowNoList);

		return inVldEndItmRowNoList;
	}

	public List<Integer> fetchInVldSpecDestCd(String porCd, String fileId,
			String tableName, String seqNo)
			throws PdApplicationNonFatalException {

		StringBuilder dynamicQuery = new StringBuilder();

		dynamicQuery.append(QueryConstants.fetchInVldEndItem);
		dynamicQuery.append(QueryConstants.fetchInVldAddtnlSpecCd);
		dynamicQuery.append(QueryConstants.fetchInVldSpecDest);

		dynamicQuery.append(QueryConstants.fetchInVldEndItemCnd);
		dynamicQuery.append(QueryConstants.fetchInVldAddtnlSpecCdCnd);
		dynamicQuery.append(QueryConstants.fetchInVldSpecDestCnd);

		String queryStr = dynamicQuery.toString();
		queryStr = queryStr.replaceAll(PDConstants.TABLE_NAME, tableName);

		inVldSpecDestnCdRowNoList = executeQuery(queryStr, fileId, porCd, seqNo);

		dynamicQuery = new StringBuilder();
		dynamicQuery.append(QueryConstants.fetchInVldEndItemforByr);
		dynamicQuery.append(QueryConstants.fetchVldEndItemforByr);

		dynamicQuery.append(QueryConstants.fetchVldEndItemforByrCnd);
		dynamicQuery.append(QueryConstants.fetchInVldAddtnlSpecCdforByrCnd);
		dynamicQuery.append(QueryConstants.fetchInVldSpecDestforByrCnd);

		dynamicQuery.append(QueryConstants.fetchInVldEndItemforByrCnd);

		queryStr = dynamicQuery.toString();
		queryStr = queryStr.replaceAll(PDConstants.TABLE_NAME, tableName);

		inVldEndItmFrByrCdRowNoList = executeQuery(queryStr, fileId, porCd,
				seqNo);

		inVldSpecDestnCdRowNoList.addAll(inVldEndItmFrByrCdRowNoList);

		return inVldSpecDestnCdRowNoList;
	}

	public List<Integer> fetchInVldAddtnlSpecCd(String porCd, String fileId,
			String tableName, String seqNo)
			throws PdApplicationNonFatalException {

		StringBuilder dynamicQuery = new StringBuilder();
		dynamicQuery.append(QueryConstants.fetchInVldEndItem);

		dynamicQuery.append(QueryConstants.fetchInVldAddtnlSpecCd);
		dynamicQuery.append(QueryConstants.fetchInVldEndItemCnd);

		dynamicQuery.append(QueryConstants.fetchInVldAddtnlSpecCdCnd);

		String queryStr = dynamicQuery.toString();
		queryStr = queryStr.replaceAll(PDConstants.TABLE_NAME, tableName);

		inVldAddtnlSpecCdRowNoList = executeQuery(queryStr, fileId, porCd,
				seqNo);

		dynamicQuery = new StringBuilder();
		dynamicQuery.append(QueryConstants.fetchInVldEndItemforByr);
		dynamicQuery.append(QueryConstants.fetchVldEndItemforByr);

		dynamicQuery.append(QueryConstants.fetchVldEndItemforByrCnd);

		dynamicQuery.append(QueryConstants.fetchInVldAddtnlSpecCdforByrCnd);
		dynamicQuery.append(QueryConstants.fetchInVldEndItemforByrCnd);

		queryStr = dynamicQuery.toString();
		queryStr = queryStr.replaceAll(PDConstants.TABLE_NAME, tableName);

		inVldEndItmFrByrCdRowNoList = executeQuery(queryStr, fileId, porCd,
				seqNo);

		inVldAddtnlSpecCdRowNoList.addAll(inVldEndItmFrByrCdRowNoList);

		return inVldAddtnlSpecCdRowNoList;
	}

	public List<Integer> fetchInVldClrCd(String porCd, String fileId,
			String tableName, String seqNo)
			throws PdApplicationNonFatalException {

		StringBuilder dynamicQuery = new StringBuilder();
		dynamicQuery.append(QueryConstants.fetchRowNo);

		dynamicQuery.append(QueryConstants.fetchInVldClrCdPart1Cnd);
		dynamicQuery.append(QueryConstants.fetchRowNo);
		dynamicQuery.append(QueryConstants.fetchVldEIwithClrCdJoin);
		dynamicQuery.append(QueryConstants.fetchInVldClrCdPart2Cnd);

		String queryStr = dynamicQuery.toString();
		queryStr = queryStr.replaceAll(PDConstants.TABLE_NAME, tableName);

		inVldClrCdRowNoList = executeQuery(queryStr, fileId, porCd, seqNo);

		return inVldClrCdRowNoList;
	}

	public List<Integer> fetchInVldPotCd(String porCd, String fileId,
			String tableName, String seqNo)
			throws PdApplicationNonFatalException {

		StringBuilder dynamicQuery = new StringBuilder();
		dynamicQuery.append(QueryConstants.fetchInVldPotCd);

		String queryStr = dynamicQuery.toString();
		queryStr = queryStr.replaceAll(PDConstants.TABLE_NAME, tableName);

		inVldPotCdRowNoList = executeQuery(queryStr, fileId, porCd, seqNo);

		return inVldPotCdRowNoList;
	}

	public List<Integer> fetchDuplicateRcrds(String porCd, String fileId,
			String tableName, String seqNo)
			throws PdApplicationNonFatalException {

		StringBuilder dynamicQuery = new StringBuilder();
		dynamicQuery.append(QueryConstants.fetchDplctRcrds);

		String queryStr = dynamicQuery.toString();
		queryStr = queryStr.replaceAll(PDConstants.TABLE_NAME, tableName);

		duplicateRowNoList = executeQuery(queryStr, fileId, porCd, seqNo);

		return duplicateRowNoList;
	}

	public List<Integer> fetchInVldOrdrTkBsPrdTyp(String porCd, String fileId,
			String tableName, String seqNo)
			throws PdApplicationNonFatalException {

		StringBuilder dynamicQuery = new StringBuilder();
		dynamicQuery.append(QueryConstants.fetchDistinctRowNo);
		dynamicQuery.append(QueryConstants.fetchInVldOrdrTkBsPrdCnd);

		String queryStr = dynamicQuery.toString();
		queryStr = queryStr.replaceAll(PDConstants.TABLE_NAME, tableName);

		inVldOrdrTkBsPrdTypList = executeQuery(queryStr, fileId, porCd, seqNo);

		return inVldOrdrTkBsPrdTypList;
	}

	public List<Integer> fetchInVldPrdPeriodTyp(String porCd, String fileId,
			String tableName, String seqNo)
			throws PdApplicationNonFatalException {

		StringBuilder dynamicQuery = new StringBuilder();
		dynamicQuery.append(QueryConstants.fetchDistinctRowNo);
		dynamicQuery.append(QueryConstants.fetchInVldPrdnPeriodCnd);

		String queryStr = dynamicQuery.toString();
		queryStr = queryStr.replaceAll(PDConstants.TABLE_NAME, tableName);

		inVldPrdPeriodTypList = executeQuery(queryStr, fileId, porCd, seqNo);

		return inVldPrdPeriodTypList;
	}

	public List<Integer> fetchInVldOrdrTkBsMnth(String porCd, String fileId,
			String tableName, Set<String> ordrTkBsMnthLst, String seqNo)
			throws PdApplicationNonFatalException {

		StringBuilder dynamicQuery = new StringBuilder();
		dynamicQuery.append(QueryConstants.fetchDistinctRowNo);
		dynamicQuery.append(QueryConstants.fetchInVldOrdrTkBsMnthCnd);

		String queryStr = dynamicQuery.toString();
		queryStr = queryStr.replaceAll(PDConstants.TABLE_NAME, tableName);

		Query query = eMgr.createNativeQuery(queryStr);
		query.setParameter(FILE_ID, fileId);
		query.setParameter(PRMTR_SEQ_NO, seqNo);
		query.setParameter(PRMTR_PORCD, porCd);

		query.setParameter("ordrTkBsMnthLst", ordrTkBsMnthLst);

		List<Integer> inVldOrdrTkBsMnthList = query.getResultList();

		return inVldOrdrTkBsMnthList;
	}

	public String fetchLockStatus(String porCd, String ordrTkBsMnth,
			String byrGrpCd, String carSrs, String prdOrdrStgCd)
			throws PdApplicationNonFatalException {

		StringBuilder dynamicQuery = new StringBuilder();
		dynamicQuery.append(QueryConstants.fetchOrdrTrnsmssnFlg);

		String queryStr = dynamicQuery.toString();

		Query query = eMgr.createNativeQuery(queryStr,
				MstMnthlyOrdrTakeLck.class);

		query.setParameter(PRMTR_PORCD, porCd);
		query.setParameter("ordrTkBsMnth", ordrTkBsMnth);
		query.setParameter("byrGrpCd", byrGrpCd);
		query.setParameter("carSrs", carSrs + "1");
		query.setParameter("prdOrdrStgCd", prdOrdrStgCd);

		List<MstMnthlyOrdrTakeLck> mnthlyOrdrTakeLckList = query
				.getResultList();
		String lckFlg = "";
		if (null != mnthlyOrdrTakeLckList && !(mnthlyOrdrTakeLckList.isEmpty())) {
			MstMnthlyOrdrTakeLck mnthlyOrdrTakeLck = mnthlyOrdrTakeLckList
					.get(0);
			lckFlg = mnthlyOrdrTakeLck.getOrdrTransFlag();
		}

		return lckFlg;
	}

	public List<Integer> fetchDueDateNullRcrds(String porCd, String fileId,
			String tableName, String seqNo)
			throws PdApplicationNonFatalException {

		StringBuilder dynamicQuery = new StringBuilder();
		dynamicQuery.append(QueryConstants.fetchDueDateFrmOrToNullRcrds);

		String queryStr = dynamicQuery.toString();
		queryStr = queryStr.replaceAll(PDConstants.TABLE_NAME, tableName);

		dueDateNullRowNoList = executeQuery(queryStr, fileId, porCd, seqNo);

		return dueDateNullRowNoList;
	}

	public List<Integer> fetchDueDateFrmGrtrRcrds(String porCd, String fileId,
			String tableName, String seqNo)
			throws PdApplicationNonFatalException {

		StringBuilder dynamicQuery = new StringBuilder();
		dynamicQuery.append(QueryConstants.fetchDueDateFrmGrtrRcrds);

		String queryStr = dynamicQuery.toString();
		queryStr = queryStr.replaceAll(PDConstants.TABLE_NAME, tableName);

		dueDateFrmGrtrList = executeQuery(queryStr, fileId, porCd, seqNo);

		return dueDateFrmGrtrList;
	}

	public List<Integer> fetchInVldDueDateRcrds(String porCd, String fileId,
			String tableName, String seqNo)
			throws PdApplicationNonFatalException {

		StringBuilder dynamicQuery = new StringBuilder();
		dynamicQuery.append(QueryConstants.fetchInVldDueDateRcrds);

		String queryStr = dynamicQuery.toString();
		queryStr = queryStr.replaceAll(PDConstants.TABLE_NAME, tableName);

		inVldDueDateList = executeQuery(queryStr, fileId, porCd, seqNo);

		return inVldDueDateList;
	}

	public List<Object[]> fetchDiffDueDateRcrds(InputBean input)
			throws PdApplicationNonFatalException {

		StringBuilder dynamicQuery = new StringBuilder();
		dynamicQuery.append(QueryConstants.fetchDistinctDueDateRcrds);

		String queryStr = dynamicQuery.toString();

		queryStr = queryStr.replaceAll(PDConstants.TABLE_NAME,
				input.getTableName());

		Query query = eMgr.createNativeQuery(queryStr);
		query.setParameter(FILE_ID, input.getFileId());
		query.setParameter(PRMTR_SEQ_NO, input.getSeqNo());
		query.setParameter(PRMTR_PORCD, input.getPorCd());

		List<Object[]> distinctDueDateRcrds = query.getResultList();

		dynamicQuery = new StringBuilder();
		dynamicQuery.append(QueryConstants.fetchDiffDueDateRcrds);

		queryStr = dynamicQuery.toString();
		queryStr = queryStr.replaceAll(PDConstants.TABLE_NAME,
				input.getTableName());

		Query query1 = eMgr.createNativeQuery(queryStr);
		query1.setParameter(FILE_ID, input.getFileId());
		query1.setParameter(PRMTR_SEQ_NO, input.getSeqNo());
		query1.setParameter(PRMTR_PORCD, input.getPorCd());

		int i;
		for (Object[] obj : distinctDueDateRcrds) {
			i = 0;
			query1.setParameter(PDConstants.PRMTR_PORCD, obj[i++]);
			query1.setParameter(PDConstants.PRMTR_POT_CD, obj[i++]);
			query1.setParameter(PDConstants.PRMTR_APPLDMDLCD, obj[i++]);
			query1.setParameter(PDConstants.PRMTR_PCK_CD, obj[i++]);
			query1.setParameter(PDConstants.PRMTR_CAR_SRS, obj[i++]);
			query1.setParameter(PDConstants.PRMTR_ADTNL_SPEC_CD, obj[i++]);
			query1.setParameter(PDConstants.PRMTR_SPEC_DEST_CD, obj[i++]);
			query1.setParameter(PDConstants.PRMTR_BYR_CD, obj[i++]);
			query1.setParameter(PDConstants.PRMTR_ORDR_TK_BS_MNTH, obj[i++]);
			query1.setParameter(PDConstants.PRMTR_PRD_MNTH, obj[i++]);

			List<BigDecimal> diffDueDateRcrds = query1.getResultList();

			BigDecimal count = diffDueDateRcrds.get(0);

			if (count.intValue() > 1) {
				mnthlyOrdrIfTrnRepositoryObj.updateDueDate(
						distinctDueDateRcrds, "18", input, M00210);
			}

		}

		return diffDueDateRcrds;
	}

	public List<Object[]> vldtPrdMnth(String porCd, String fileId,
			String tableName, String seqNo)
			throws PdApplicationNonFatalException {

		StringBuilder dynamicQuery = new StringBuilder();
		dynamicQuery.append(QueryConstants.fetchDiffDueDateRcrds);

		String queryStr = dynamicQuery.toString();
		queryStr = queryStr.replaceAll(PDConstants.TABLE_NAME, tableName);

		Query query = eMgr.createNativeQuery(queryStr);
		query.setParameter(FILE_ID, fileId);
		query.setParameter(PRMTR_SEQ_NO, seqNo);
		query.setParameter(PRMTR_PORCD, porCd);

		List<Object[]> inVldPrdMnthList = query.getResultList();

		return inVldPrdMnthList;
	}

	public List<Integer> executeQuery(String queryString, String fileId,
			String porCd, String seqNo) {
		Query query = eMgr.createNativeQuery(queryString);
		query.setParameter(FILE_ID, fileId);
		query.setParameter(PRMTR_SEQ_NO, seqNo);
		query.setParameter(PRMTR_PORCD, porCd);

		List<Integer> result = query.getResultList();
		return result;
	}

	/**
	 * 
	 * @param porCd
	 * @param prdOrdrStgCd
	 * @return - List of Row No's having POR CD present in TRN MONTHLY ORDER
	 *         INTERFACE and not present in POR MST
	 * @throws PdApplicationNonFatalException
	 */
	public List<Integer> fetchInVldPorCdR000020(String tableName,
			QueryParamBean qryParamBean) throws PdApplicationNonFatalException {

		StringBuilder dynamicQuery = QueryConstants.fetchInVldPorCdR000020;
		dynamicQuery.append(QueryConstants.whrOrdrTkBsMnthCndtn);

		if (qryParamBean.getOcfRgnCd() != null) {
			dynamicQuery.append(QueryConstants.whrOcfRgnCdCndtn);
		}
		String queryStr = dynamicQuery.toString();
		queryStr = queryStr.replaceAll(PDConstants.TABLE_NAME, tableName);
		Query query = eMgr.createNativeQuery(queryStr);
		query.setParameter(PDConstants.PRMTR_ORDR_TK_BS_MNTH,
				qryParamBean.getOrdrTkBsMnth());
		if (qryParamBean.getOcfRgnCd() != null) {
			query.setParameter(PDConstants.PRMTR_OCFRGNCD,
					qryParamBean.getOcfRgnCd());
		}
		inVldPorRowNoList = query.getResultList();

		return inVldPorRowNoList;
	}

	/**
	 * 
	 * @param porCd
	 * @param prdOrdrStgCd
	 * @return - List of Row No's having POR CD present in TRN MONTHLY ORDER
	 *         INTERFACE and not present in POR MST
	 * @throws PdApplicationNonFatalException
	 */
	public List<Integer> fetchInVldCarSrsR000020(String porCd,
			String tableName, QueryParamBean qryParamBean)
			throws PdApplicationNonFatalException {

		StringBuilder dynamicQuery = R000020QueryConstants.fetchInVldCarSrs;
		dynamicQuery.append(QueryConstants.whrOrdrTkBsMnthCndtn);
		if (qryParamBean.getOcfRgnCd() != null) {
			dynamicQuery.append(QueryConstants.whrOcfRgnCdCndtn);
		}
		dynamicQuery.append(QueryConstants.whrOrdrTkBsMnthCndtn);
		String queryStr = dynamicQuery.toString();
		queryStr = queryStr.replaceAll(PDConstants.TABLE_NAME, tableName);
		Query query = eMgr.createNativeQuery(queryStr);
		if (qryParamBean.getOcfRgnCd() != null) {
			query.setParameter(PDConstants.PRMTR_OCFRGNCD,
					qryParamBean.getOcfRgnCd());
		}

		query.setParameter(PDConstants.PRMTR_ORDR_TK_BS_MNTH,
				qryParamBean.getOrdrTkBsMnth());
		query.setParameter(PRMTR_PORCD, porCd);
		inVldCarSrsRowNoList = query.getResultList();
		return inVldCarSrsRowNoList;
	}

	public List<Integer> fetchInVldByrCdR00020(String porCd, String tableName,
			QueryParamBean qryParamBean) throws PdApplicationNonFatalException {

		StringBuilder dynamicQuery = R000020QueryConstants.fetchInVldBuyerCd;
		if (qryParamBean.getOcfRgnCd() != null) {
			dynamicQuery.append(QueryConstants.whrOcfRgnCdCndtn);
		}
		dynamicQuery.append(QueryConstants.whrOrdrTkBsMnthCndtn);
		String queryStr = dynamicQuery.toString();
		queryStr = queryStr.replaceAll(PDConstants.TABLE_NAME, tableName);
		inVldByrCdRowNoList = executeQuery(porCd, queryStr, qryParamBean);
		return inVldByrCdRowNoList;
	}

	public List<Object[]> getVldData(QueryParamBean qryParamBean,
			String tableName) throws PdApplicationNonFatalException {
		StringBuilder dynamicQuery = R000020QueryConstants.getVldRecds;
		if (qryParamBean.getOcfRgnCd() != null) {
			dynamicQuery.append(QueryConstants.whrOcfRgnCdCndtn);
		}
		dynamicQuery.append(QueryConstants.whrOrdrTkBsMnthCndtn);
		String queryStr = dynamicQuery.toString();
		queryStr = queryStr.replaceAll(PDConstants.TABLE_NAME, tableName);
		List<Object[]> inVldByrCdRowNoList = executeQueryString(
				qryParamBean.getPorCd(), queryStr, qryParamBean);
		return inVldByrCdRowNoList;
	}

	public void deleteOldData(QueryParamBean qryParamBean, String tableName)
			throws PdApplicationNonFatalException {
		StringBuilder dynamicQuery = R000020QueryConstants.deleteOldData;
		if (qryParamBean.getOcfRgnCd() != null) {
			dynamicQuery.append(QueryConstants.whrOcfRgnCdCndtn);
		}
		dynamicQuery.append(QueryConstants.whrOrdrTkBsMnthCndtn);
		String queryStr = dynamicQuery.toString();
		queryStr = queryStr.replaceAll(PDConstants.TABLE_NAME, tableName);
		Query query = eMgr.createNativeQuery(queryStr);
		if (qryParamBean.getOcfRgnCd() != null) {
			query.setParameter(PDConstants.PRMTR_OCFRGNCD,
					qryParamBean.getOcfRgnCd());
		}
		query.setParameter(PDConstants.PRMTR_ORDR_TK_BS_MNTH,
				qryParamBean.getOrdrTkBsMnth());
		query.setParameter(PRMTR_PORCD, qryParamBean.getPorCd());
		query.executeUpdate();
	}

	public List<Integer> executeQuery(String porCd, String queryString,
			QueryParamBean qryParamBean) {
		Query query = eMgr.createNativeQuery(queryString);
		if (qryParamBean.getOcfRgnCd() != null) {
			query.setParameter(PDConstants.PRMTR_OCFRGNCD,
					qryParamBean.getOcfRgnCd());
		}
		if (qryParamBean.getPrdStgCd() != null) {
			query.setParameter(PDConstants.PRMTR_PRDSTGCD,
					qryParamBean.getPrdStgCd());
		}
		query.setParameter(PDConstants.PRMTR_ORDR_TK_BS_MNTH,
				qryParamBean.getOrdrTkBsMnth());
		query.setParameter(PRMTR_PORCD, porCd);
		List<Integer> result = query.getResultList();
		return result;
	}

	public List<Object[]> executeQueryString(String porCd, String queryString,
			QueryParamBean qryParamBean) {
		Query query = eMgr.createNativeQuery(queryString);
		if (qryParamBean.getOcfRgnCd() != null) {
			query.setParameter(PDConstants.PRMTR_OCFRGNCD,
					qryParamBean.getOcfRgnCd());
		}
		if (qryParamBean.getPrdStgCd() != null) {
			query.setParameter(PDConstants.PRMTR_PRDSTGCD,
					qryParamBean.getPrdStgCd());
		}
		query.setParameter(PDConstants.PRMTR_ORDR_TK_BS_MNTH,
				qryParamBean.getOrdrTkBsMnth());
		query.setParameter(PRMTR_PORCD, porCd);
		List<Object[]> result = query.getResultList();
		return result;
	}

	public List<Integer> fetchInVldEndItmR000020(String porCd,
			String tableName, QueryParamBean qryParamBean)
			throws PdApplicationNonFatalException {

		StringBuilder dynamicQuery = new StringBuilder();
		dynamicQuery.append(R000020QueryConstants.fetchInVldEndItem);
		dynamicQuery.append(R000020QueryConstants.fetchInVldEndItemCnd);
		if (qryParamBean.getOcfRgnCd() != null) {
			dynamicQuery.append(QueryConstants.whrOcfRgnCdCndtn);
		}
		dynamicQuery.append(QueryConstants.whrOrdrTkBsMnthCndtn);
		dynamicQuery.append(R000020QueryConstants.wherePrdStgCd);
		String queryStr = dynamicQuery.toString();
		queryStr = queryStr.replaceAll(PDConstants.TABLE_NAME, tableName);
		inVldEndItmRowNoList = executeQuery(porCd, queryStr, qryParamBean);

		dynamicQuery = new StringBuilder();
		dynamicQuery.append(R000020QueryConstants.fetchInVldEndItemforByr);
		dynamicQuery.append(R000020QueryConstants.fetchVldEndItemforByr);

		dynamicQuery.append(R000020QueryConstants.fetchVldEndItemforByrCnd);
		dynamicQuery.append(R000020QueryConstants.fetchInVldEndItemforByrCnd);
		if (qryParamBean.getOcfRgnCd() != null) {
			dynamicQuery.append(QueryConstants.whrOcfRgnCdCndtn);
		}
		dynamicQuery.append(QueryConstants.whrOrdrTkBsMnthCndtn);
		queryStr = dynamicQuery.toString();
		queryStr = queryStr.replaceAll(PDConstants.TABLE_NAME, tableName);

		inVldEndItmFrByrCdRowNoList = executeQuery(porCd, queryStr,
				qryParamBean);

		inVldEndItmRowNoList.addAll(inVldEndItmFrByrCdRowNoList);

		return inVldEndItmRowNoList;
	}

	public List<Integer> fetchInVldAddtnlSpecCdR000020(String porCd,
			String tableName, QueryParamBean qryParamBean)
			throws PdApplicationNonFatalException {

		StringBuilder dynamicQuery = new StringBuilder();
		dynamicQuery.append(R000020QueryConstants.fetchInVldEndItem);

		dynamicQuery.append(QueryConstants.fetchInVldAddtnlSpecCd);
		dynamicQuery.append(R000020QueryConstants.fetchInVldEndItemCnd);

		dynamicQuery.append(QueryConstants.fetchInVldAddtnlSpecCdCnd);
		if (qryParamBean.getOcfRgnCd() != null) {
			dynamicQuery.append(QueryConstants.whrOcfRgnCdCndtn);
		}
		dynamicQuery.append(QueryConstants.whrOrdrTkBsMnthCndtn);
		dynamicQuery.append(R000020QueryConstants.wherePrdStgCd);

		String queryStr = dynamicQuery.toString();
		queryStr = queryStr.replaceAll(PDConstants.TABLE_NAME, tableName);

		inVldAddtnlSpecCdRowNoList = executeQuery(porCd, queryStr, qryParamBean);

		dynamicQuery = new StringBuilder();
		dynamicQuery.append(R000020QueryConstants.fetchInVldEndItemforByr);
		dynamicQuery.append(R000020QueryConstants.fetchVldEndItemforByr);

		dynamicQuery.append(R000020QueryConstants.fetchVldEndItemforByrCnd);

		dynamicQuery.append(QueryConstants.fetchInVldAddtnlSpecCdforByrCnd);
		dynamicQuery.append(R000020QueryConstants.fetchInVldEndItemforByrCnd);
		if (qryParamBean.getOcfRgnCd() != null) {
			dynamicQuery.append(QueryConstants.whrOcfRgnCdCndtn);
		}
		dynamicQuery.append(QueryConstants.whrOrdrTkBsMnthCndtn);
		queryStr = dynamicQuery.toString();
		queryStr = queryStr.replaceAll(PDConstants.TABLE_NAME, tableName);

		inVldEndItmFrByrCdRowNoList = executeQuery(porCd, queryStr,
				qryParamBean);

		inVldAddtnlSpecCdRowNoList.addAll(inVldEndItmFrByrCdRowNoList);

		return inVldAddtnlSpecCdRowNoList;
	}

	public List<Integer> fetchInVldSpecDestCdR000020(String porCd,
			String tableName, QueryParamBean qryParamBean)
			throws PdApplicationNonFatalException {

		StringBuilder dynamicQuery = new StringBuilder();

		dynamicQuery.append(R000020QueryConstants.fetchInVldEndItem);
		dynamicQuery.append(QueryConstants.fetchInVldAddtnlSpecCd);
		dynamicQuery.append(QueryConstants.fetchInVldSpecDest);

		dynamicQuery.append(R000020QueryConstants.fetchInVldEndItemCnd);
		dynamicQuery.append(QueryConstants.fetchInVldAddtnlSpecCdCnd);
		dynamicQuery.append(QueryConstants.fetchInVldSpecDestCnd);
		if (qryParamBean.getOcfRgnCd() != null) {
			dynamicQuery.append(QueryConstants.whrOcfRgnCdCndtn);
		}
		dynamicQuery.append(QueryConstants.whrOrdrTkBsMnthCndtn);
		dynamicQuery.append(R000020QueryConstants.wherePrdStgCd);

		String queryStr = dynamicQuery.toString();
		queryStr = queryStr.replaceAll(PDConstants.TABLE_NAME, tableName);

		inVldSpecDestnCdRowNoList = executeQuery(porCd, queryStr, qryParamBean);

		dynamicQuery = new StringBuilder();
		dynamicQuery.append(R000020QueryConstants.fetchInVldEndItemforByr);
		dynamicQuery.append(R000020QueryConstants.fetchVldEndItemforByr);

		dynamicQuery.append(R000020QueryConstants.fetchVldEndItemforByrCnd);
		dynamicQuery.append(QueryConstants.fetchInVldAddtnlSpecCdforByrCnd);
		dynamicQuery.append(QueryConstants.fetchInVldSpecDestforByrCnd);

		dynamicQuery.append(R000020QueryConstants.fetchInVldEndItemforByrCnd);
		if (qryParamBean.getOcfRgnCd() != null) {
			dynamicQuery.append(QueryConstants.whrOcfRgnCdCndtn);
		}
		dynamicQuery.append(QueryConstants.whrOrdrTkBsMnthCndtn);

		queryStr = dynamicQuery.toString();
		queryStr = queryStr.replaceAll(PDConstants.TABLE_NAME, tableName);

		inVldEndItmFrByrCdRowNoList = executeQuery(porCd, queryStr,
				qryParamBean);

		inVldSpecDestnCdRowNoList.addAll(inVldEndItmFrByrCdRowNoList);

		return inVldSpecDestnCdRowNoList;
	}

	public List<Integer> fetchInVldClrCdR000020(String porCd, String tableName,
			QueryParamBean qryParamBean) throws PdApplicationNonFatalException {

		StringBuilder dynamicQuery = new StringBuilder();
		dynamicQuery.append(QueryConstants.fetchSeqNo);

		dynamicQuery.append(R000020QueryConstants.fetchInVldClrCdPart1Cnd);
		dynamicQuery.append(QueryConstants.fetchSeqNo);
		dynamicQuery.append(QueryConstants.fetchVldEIwithClrCdJoin);
		dynamicQuery.append(R000020QueryConstants.fetchInVldClrCdPart2Cnd);
		if (qryParamBean.getOcfRgnCd() != null) {
			dynamicQuery.append(QueryConstants.whrOcfRgnCdCndtn);
		}
		dynamicQuery.append(QueryConstants.whrOrdrTkBsMnthCndtn);
		String queryStr = dynamicQuery.toString();
		queryStr = queryStr.replaceAll(PDConstants.TABLE_NAME, tableName);

		inVldClrCdRowNoList = executeQuery(porCd, queryStr, qryParamBean);

		return inVldClrCdRowNoList;
	}

	public List<Integer> fetchInVldPotCdR000020(String porCd, String tableName,
			QueryParamBean qryParamBean) throws PdApplicationNonFatalException {

		StringBuilder dynamicQuery = new StringBuilder();
		dynamicQuery.append(R000020QueryConstants.fetchInVldPotCd);
		if (qryParamBean.getOcfRgnCd() != null) {
			dynamicQuery.append(QueryConstants.whrOcfRgnCdCndtn);
		}
		dynamicQuery.append(QueryConstants.whrOrdrTkBsMnthCndtn);
		String queryStr = dynamicQuery.toString();

		queryStr = queryStr.replaceAll(PDConstants.TABLE_NAME, tableName);

		inVldPotCdRowNoList = executeQuery(porCd, queryStr, qryParamBean);

		return inVldPotCdRowNoList;
	}

	public List<Object[]> fetchInVldSlsNoteR000020(String porCd,
			String tableName, QueryParamBean qryParamBean)
			throws PdApplicationNonFatalException {

		StringBuilder dynamicQuery = new StringBuilder();
		dynamicQuery.append(R000020QueryConstants.fetchInVldSalesNoteNo);
		if (qryParamBean.getOcfRgnCd() != null) {
			dynamicQuery.append(QueryConstants.whrOcfRgnCdCndtn);
		}
		dynamicQuery.append(QueryConstants.whrOrdrTkBsMnthCndtn);
		String queryStr = dynamicQuery.toString();
		queryStr = queryStr.replaceAll(PDConstants.TABLE_NAME, tableName);
		List<Object[]> inVldSlsNtRowNoList = executeQueryString(porCd,
				queryStr, qryParamBean);

		return inVldSlsNtRowNoList;
	}

	public List<Integer> fetchInVldOcfRgnCd(String porCd, String tableName,
			QueryParamBean qryParamBean) throws PdApplicationNonFatalException {
		StringBuilder dynamicQuery = new StringBuilder();
		dynamicQuery.append(R000020QueryConstants.fetchInVldOcfRgnCd);
		if (qryParamBean.getOcfRgnCd() != null) {
			dynamicQuery.append(QueryConstants.whrOcfRgnCdCndtn);
		}
		dynamicQuery.append(QueryConstants.whrOrdrTkBsMnthCndtn);
		String queryStr = dynamicQuery.toString();
		queryStr = queryStr.replaceAll(PDConstants.TABLE_NAME, tableName);
		duplicateRowNoList = executeQuery(porCd, queryStr, qryParamBean);
		return duplicateRowNoList;
	}

	public List<Object[]> fetchInVldFrznCdR00020(String porCd,
			String tableName, QueryParamBean qryParamBean)
			throws PdApplicationNonFatalException {

		StringBuilder dynamicQuery = R000020QueryConstants.fetchFrznErrData;
		if (qryParamBean.getOcfRgnCd() != null) {
			dynamicQuery.append(QueryConstants.whrOcfRgnCdCndtn);
		}
		dynamicQuery.append(QueryConstants.whrOrdrTkBsMnthCndtn);
		String queryStr = dynamicQuery.toString();
		queryStr = queryStr.replaceAll(PDConstants.TABLE_NAME, tableName);
		List<Object[]> inVldByrCdRowNoListStr = executeQueryString(porCd,
				queryStr, qryParamBean);
		return inVldByrCdRowNoListStr;
	}

	public List<Object[]> fetchInVldPrdMthdCdR00020(String porCd,
			String tableName, QueryParamBean qryParamBean)
			throws PdApplicationNonFatalException {

		StringBuilder dynamicQuery = R000020QueryConstants.fetchPrdMthdCdData;
		if (qryParamBean.getOcfRgnCd() != null) {
			dynamicQuery.append(QueryConstants.whrOcfRgnCdCndtn);
		}
		dynamicQuery.append(QueryConstants.whrOrdrTkBsMnthCndtn);
		String queryStr = dynamicQuery.toString();
		queryStr = queryStr.replaceAll(PDConstants.TABLE_NAME, tableName);
		List<Object[]> inVldByrCdRowNoListStr = executeQueryString(porCd,
				queryStr, qryParamBean);
		return inVldByrCdRowNoListStr;
	}

	public List<R000020ExNoBean> fetchInVldExNoR00020(String porCd,
			String tableName, QueryParamBean qryParamBean)
			throws PdApplicationNonFatalException {

		StringBuilder dynamicQuery = R000020QueryConstants.fetchInvldExNo;
		if (qryParamBean.getOcfRgnCd() != null) {
			dynamicQuery.append(QueryConstants.whrOcfRgnCdCndtn);
		}
		dynamicQuery.append(QueryConstants.whrOrdrTkBsMnthCndtn);
		String queryStr = dynamicQuery.toString();
		queryStr = queryStr.replaceAll(PDConstants.TABLE_NAME, tableName);
		Query query = eMgr.createNativeQuery(queryStr, R000020ExNoBean.class);
		if (qryParamBean.getOcfRgnCd() != null) {
			query.setParameter(PDConstants.PRMTR_OCFRGNCD,
					qryParamBean.getOcfRgnCd());
		}
		if (qryParamBean.getPrdStgCd() != null) {
			query.setParameter(PDConstants.PRMTR_PRDSTGCD,
					qryParamBean.getPrdStgCd());
		}
		query.setParameter(PDConstants.PRMTR_ORDR_TK_BS_MNTH,
				qryParamBean.getOrdrTkBsMnth());
		query.setParameter(PRMTR_PORCD, porCd);
		List<R000020ExNoBean> result = query.getResultList();
		return result;
	}

	public List<Integer> fetchInVldExNoWtDiffCombn(String porCd,
			String tableName, QueryParamBean qryParamBean)
			throws PdApplicationNonFatalException {

		StringBuilder dynamicQuery = R000020QueryConstants.fetchInvldExNoWithDiffComb;
		if (qryParamBean.getOcfRgnCd() != null) {
			dynamicQuery.append(QueryConstants.whrOcfRgnCdCndtn);
		}
		dynamicQuery.append(QueryConstants.whrOrdrTkBsMnthCndtn);
		String queryStr = dynamicQuery.toString();
		queryStr = queryStr.replaceAll(PDConstants.TABLE_NAME, tableName);
		Query query = eMgr.createNativeQuery(queryStr);
		if (qryParamBean.getOcfRgnCd() != null) {
			query.setParameter(PDConstants.PRMTR_OCFRGNCD,
					qryParamBean.getOcfRgnCd());
		}
		if (qryParamBean.getPrdStgCd() != null) {
			query.setParameter(PDConstants.PRMTR_PRDSTGCD,
					qryParamBean.getPrdStgCd());
		}
		query.setParameter(PDConstants.PRMTR_ORDR_TK_BS_MNTH,
				qryParamBean.getOrdrTkBsMnth());
		query.setParameter(PRMTR_PORCD, porCd);
		List<Integer> result = query.getResultList();
		return result;
	}

	public List<Object[]> fetchInVldExNoFrDiffCombn(
			R000020ExNoBean r00020ExNoBean)
			throws PdApplicationNonFatalException {

		StringBuilder dynamicQuery = R000020QueryConstants.fetchInVldExNoFrDiffCombn;
		Query query = eMgr.createNativeQuery(dynamicQuery.toString());
		query.setParameter(PRMTR_PORCD, r00020ExNoBean.getPorCd());
		query.setParameter(PDConstants.PRMTR_EX_NO, r00020ExNoBean.getExNo());
		query.setParameter(PDConstants.PRMTR_PRD_MNTH,
				r00020ExNoBean.getPrdMnth());
		List<Object[]> result = query.getResultList();
		return result;
	}

	public List<Object[]> chckSameCombtExistsInMnthPrdOrd(
			R000020ExNoBean r00020ExNoBean)
			throws PdApplicationNonFatalException {

		StringBuilder dynamicQuery = R000020QueryConstants.chckSameCombtExistsInMnthPrdOrd;
		Query query = eMgr.createNativeQuery(dynamicQuery.toString());
		query.setParameter(PRMTR_PORCD, r00020ExNoBean.getPorCd());
		query.setParameter(PDConstants.PRMTR_UK_OSEI_ID,
				r00020ExNoBean.getOseiId());
		query.setParameter(PDConstants.PRMTR_PRD_MNTH,
				r00020ExNoBean.getPrdMnth());
		query.setParameter(PDConstants.PRMTR_ORDR_TK_BS_MNTH,
				r00020ExNoBean.getPrdMnth());
		query.setParameter(PDConstants.PRMTR_PRD_MNTH,
				r00020ExNoBean.getPrdMnth());
		query.setParameter(PDConstants.PRMTR_POT_CD, r00020ExNoBean.getPotCd());
		List<Object[]> result = query.getResultList();
		return result;
	}

	public List<Object[]> chckExNoExistsFrDiffCombInMnthly(
			R000020ExNoBean r00020ExNoBean)
			throws PdApplicationNonFatalException {

		StringBuilder dynamicQuery = R000020QueryConstants.chckExNoExistsFrDiffCombInMnthly;
		Query query = eMgr.createNativeQuery(dynamicQuery.toString());
		query.setParameter(PRMTR_PORCD, r00020ExNoBean.getPorCd());
		query.setParameter(PDConstants.PRMTR_EX_NO, r00020ExNoBean.getExNo());
		query.setParameter(PDConstants.PRMTR_PRD_MNTH,
				r00020ExNoBean.getPrdMnth());
		query.setParameter(PDConstants.PRMTR_ORDR_TK_BS_MNTH,
				r00020ExNoBean.getPrdMnth());
		List<Object[]> result = query.getResultList();
		return result;
	}

	public List<MstPrmtr> extractExNoRng() {
		List<String> key2 = Arrays.asList(PDConstants.MONTHLY,
				PDConstants.WEEKLY, PDConstants.MANUAL);
		StringBuilder dynamicQuery = R000020QueryConstants.getExNoRng;
		Query query = eMgr.createNativeQuery(dynamicQuery.toString(),
				MstPrmtr.class);
		query.setParameter(PDConstants.PRMTR_PRMTR_CD,
				PDConstants.PRMTR_CD_EX_NO_RANGE);
		query.setParameter(PDConstants.PRMTR_KEY1,
				PDConstants.PRMTR_EX_NO_VALIDATION);
		query.setParameter(PDConstants.PRMTR_KEY2, key2);
		List<MstPrmtr> result = query.getResultList();
		return result;
	}

	/**
	 * This method is used to Delete the Old Ex No data.
	 * 
	 * @param r000020ExNoBean
	 */
	public void deleteOldExNoData(R000020ExNoBean r000020ExNoBean) {
		String dltOldData = R000020QueryConstants.dltOldExNo.toString();
		Query qry = eMgr.createNativeQuery(dltOldData);
		qry.setParameter(PDConstants.PRMTR_PORCD, r000020ExNoBean.getPorCd());
		qry.setParameter(PDConstants.PRMTR_PRD_MNTH,
				r000020ExNoBean.getPrdMnth());
		qry.setParameter(PDConstants.PRMTR_POT_CD, r000020ExNoBean.getPotCd());
		qry.setParameter(PDConstants.PRMTR_OEI_BYR_ID,
				r000020ExNoBean.getOeiByrId());
		qry.executeUpdate();
	}

	public void insrtExNoData(R000020ExNoBean r000020ExNoBean, String prpseCd) {
		MstExNo mstExno = new MstExNo();
		MstExNoPK mstExnopk = new MstExNoPK();
		mstExnopk.setPorCd(r000020ExNoBean.getPorCd());
		mstExnopk.setProdMnth(r000020ExNoBean.getPrdMnth());
		mstExnopk.setPotCd(r000020ExNoBean.getPotCd());
		mstExnopk.setOeiBuyerId(r000020ExNoBean.getOeiByrId());
		mstExno.setExNo(r000020ExNoBean.getExNo());
		mstExno.setPrpseCd(prpseCd);
		mstExno.setCrtdBy(PDConstants.R000020);
		mstExno.setUpdtdBy(PDConstants.R000020);
		mstExno.setCrtdDt(CommonUtil.createTimeStamp());
		mstExno.setUpdtdDt(CommonUtil.createTimeStamp());
		mstExno.setId(mstExnopk);
		eMgr.merge(mstExno);
	}

	public void updateExNo(R000020ExNoBean r000020ExNoBean, String purposeCd) {
		LOG.info(PDConstants.STEP_AFTER_START);

		String updtExnoStr = R000020QueryConstants.updateExno.toString();
		Query qry = eMgr.createNativeQuery(updtExnoStr);
		qry.setParameter(PDConstants.PRMTR_PORCD, r000020ExNoBean.getPorCd());
		qry.setParameter(PDConstants.PRMTR_PRD_MNTH,
				r000020ExNoBean.getPrdMnth());
		qry.setParameter(PDConstants.PRMTR_POT_CD, r000020ExNoBean.getPotCd());
		qry.setParameter(PDConstants.PRMTR_OEI_BYR_ID,
				r000020ExNoBean.getOeiByrId());
		qry.setParameter(PDConstants.PRMTR_EX_NO, r000020ExNoBean.getExNo());
		qry.setParameter(PDConstants.PRMTR_PRPS_CD, purposeCd);
		qry.executeUpdate();
		LOG.info(PDConstants.STEP_AFTER_END);
	}

	public void intializeMxIndicator(R000020ExNoBean r000020ExNoBean) {
		String intializeStr = R000020QueryConstants.initializeMxIndiCator
				.toString();
		Query qry = eMgr.createNativeQuery(intializeStr);
		qry.setParameter(PDConstants.PRMTR_PORCD, r000020ExNoBean.getPorCd());
		qry.setParameter(PDConstants.PRMTR_PRD_MNTH,
				r000020ExNoBean.getPrdMnth());
		qry.setParameter(PDConstants.PRMTR_POT_CD, r000020ExNoBean.getPotCd());
		qry.setParameter(PDConstants.PRMTR_OEI_BYR_ID,
				r000020ExNoBean.getOeiByrId());
		qry.setParameter(PDConstants.PRMTR_EX_NO, r000020ExNoBean.getExNo()
				.substring(0, 1));
		qry.executeUpdate();
	}

	public void updateMxIndicator(R000020ExNoBean r000020ExNoBean) {
		
		String intializeStr = R000020QueryConstants.initializeMxIndiCator
				.toString();
		Query qry = eMgr.createNativeQuery(intializeStr);
		qry.setParameter(PDConstants.PRMTR_PORCD, r000020ExNoBean.getPorCd());
		qry.setParameter(PDConstants.PRMTR_PRD_MNTH,
				r000020ExNoBean.getPrdMnth());
		qry.setParameter(PDConstants.PRMTR_EX_NO, r000020ExNoBean.getExNo()
				.substring(0, 1));
		qry.executeUpdate();

		// Dont remove this query this is to maintain the JPA flush issue.
		String SELCTAutoMxIndicator = R000020QueryConstants.selectAutoMxIndicator
				.toString();
		Query updateMnlMxIndicatorqry12 = eMgr
				.createNativeQuery(SELCTAutoMxIndicator);

		updateMnlMxIndicatorqry12.setParameter(PDConstants.PRMTR_PORCD,
				r000020ExNoBean.getPorCd());
		updateMnlMxIndicatorqry12.setParameter(PDConstants.PRMTR_PRD_MNTH,
				r000020ExNoBean.getPrdMnth());
		updateMnlMxIndicatorqry12.setParameter(PDConstants.PRMTR_EX_NO,
				r000020ExNoBean.getExNo().substring(0, 1));
		updateMnlMxIndicatorqry12.getResultList();

		// Manual max Indicator Updation
		String updateMnlMxIndicator = R000020QueryConstants.updateMnlMxIndicator
				.toString();
		Query updateMnlMxIndicatorqry = eMgr
				.createNativeQuery(updateMnlMxIndicator);

		updateMnlMxIndicatorqry.setParameter(PDConstants.PRMTR_MX_INDCTR,
				PDConstants.MANUAL_MX_INDCTR);
		updateMnlMxIndicatorqry.setParameter(PDConstants.PRMTR_PORCD,
				r000020ExNoBean.getPorCd());
		updateMnlMxIndicatorqry.setParameter(PDConstants.PRMTR_PRD_MNTH,
				r000020ExNoBean.getPrdMnth());
		updateMnlMxIndicatorqry.setParameter(PDConstants.PRMTR_EX_NO,
				r000020ExNoBean.getExNo().substring(0, 1));
		updateMnlMxIndicatorqry.executeUpdate();

		// Auto Max Indicator Updation
		String updateAutoMxIndicator = R000020QueryConstants.updateAutoMxIndicator
				.toString();
		Query updateAutoMxIndicatorqry = eMgr
				.createNativeQuery(updateAutoMxIndicator);

		updateAutoMxIndicatorqry.setParameter(PDConstants.PRMTR_MX_INDCTR,
				PDConstants.AUTO_MX_INDCTR);
		updateAutoMxIndicatorqry.setParameter(PDConstants.PRMTR_PORCD,
				r000020ExNoBean.getPorCd());
		updateAutoMxIndicatorqry.setParameter(PDConstants.PRMTR_PRD_MNTH,
				r000020ExNoBean.getPrdMnth());
		updateAutoMxIndicatorqry.setParameter(PDConstants.PRMTR_EX_NO,
				r000020ExNoBean.getExNo().substring(0, 1));
		updateAutoMxIndicatorqry.executeUpdate();

	}

	public List<MstOseiFrzn> fetchValidFrnCd(String seqId) {
		StringBuilder dynamicQuery = R000020QueryConstants.fetchValidFrnCd;
		Query query = eMgr.createNativeQuery(dynamicQuery.toString(),
				MstOseiFrzn.class);
		query.setParameter(PDConstants.PRMTRT_SEQ_NO, seqId);
		return query.getResultList();
	}

	public List<MstOseiProdType> fetchValidPrdMtdCd(String seqId, String porCd) {
		StringBuilder dynamicQuery = R000020QueryConstants.fetchValidPrdMthdCdData;
		Query query = eMgr.createNativeQuery(dynamicQuery.toString(),
				MstOseiProdType.class);
		query.setParameter(PDConstants.PRMTRT_SEQ_NO, seqId);
		query.setParameter(PDConstants.PRMTR_PORCD, porCd);
		List<MstOseiProdType> result = query.getResultList();
		return result;
	}

	public void updateFrnCd(String porCd, String ordrTkBsMnth, String frznCd,
			String seqId) {
		StringBuilder dynamicQuery = R000020QueryConstants.updateFrznCd;
		Query query = eMgr.createNativeQuery(dynamicQuery.toString());
		query.setParameter(PDConstants.PRMTR_PORCD, porCd);
		query.setParameter(PDConstants.PRMTR_ORDR_TK_BS_MNTH, ordrTkBsMnth);
		query.setParameter(PDConstants.PRMTR_FRZN_TYPE, frznCd);
		query.setParameter(PDConstants.PRMTRT_SEQ_NO, seqId);
		query.executeUpdate();
	}

	public void updatePrdMthdCd(String porCd, String ordrTkBsMnth,
			String prdtCd, String seqId) {
		StringBuilder dynamicQuery = R000020QueryConstants.updatePrdMthdCd;
		Query query = eMgr.createNativeQuery(dynamicQuery.toString());
		query.setParameter(PDConstants.PRMTR_PORCD, porCd);
		query.setParameter(PDConstants.PRMTR_ORDR_TK_BS_MNTH, ordrTkBsMnth);
		query.setParameter(PDConstants.PRMTR_CD_PRD_MTD_CD, prdtCd);
		query.setParameter(PDConstants.PRMTRT_SEQ_NO, seqId);
		query.executeUpdate();
	}

	public EntityManager geteMgr() {
		return eMgr;
	}

	public void seteMgr(EntityManager eMgr) {
		this.eMgr = eMgr;
	}

	public List<Integer> getInVldPorRowNoList() {
		return inVldPorRowNoList;
	}

	public void setInVldPorRowNoList(List<Integer> inVldPorRowNoList) {
		this.inVldPorRowNoList = inVldPorRowNoList;
	}

	public List<Integer> getInVldCarSrsRowNoList() {
		return inVldCarSrsRowNoList;
	}

	public void setInVldCarSrsRowNoList(List<Integer> inVldCarSrsRowNoList) {
		this.inVldCarSrsRowNoList = inVldCarSrsRowNoList;
	}

	public List<Integer> getInVldByrCdRowNoList() {
		return inVldByrCdRowNoList;
	}

	public void setInVldByrCdRowNoList(List<Integer> inVldByrCdRowNoList) {
		this.inVldByrCdRowNoList = inVldByrCdRowNoList;
	}

	public List<Integer> getInVldEndItmRowNoList() {
		return inVldEndItmRowNoList;
	}

	public void setInVldEndItmRowNoList(List<Integer> inVldEndItmRowNoList) {
		this.inVldEndItmRowNoList = inVldEndItmRowNoList;
	}

	public List<Integer> getInVldAddtnlSpecCdRowNoList() {
		return inVldAddtnlSpecCdRowNoList;
	}

	public void setInVldAddtnlSpecCdRowNoList(
			List<Integer> inVldAddtnlSpecCdRowNoList) {
		this.inVldAddtnlSpecCdRowNoList = inVldAddtnlSpecCdRowNoList;
	}

	public List<Integer> getInVldSpecDestnCdRowNoList() {
		return inVldSpecDestnCdRowNoList;
	}

	public void setInVldSpecDestnCdRowNoList(
			List<Integer> inVldSpecDestnCdRowNoList) {
		this.inVldSpecDestnCdRowNoList = inVldSpecDestnCdRowNoList;
	}

	public List<Integer> getInVldClrCdRowNoList() {
		return inVldClrCdRowNoList;
	}

	public void setInVldClrCdRowNoList(List<Integer> inVldClrCdRowNoList) {
		this.inVldClrCdRowNoList = inVldClrCdRowNoList;
	}

	public List<Integer> getInVldPotCdRowNoList() {
		return inVldPotCdRowNoList;
	}

	public void setInVldPotCdRowNoList(List<Integer> inVldPotCdRowNoList) {
		this.inVldPotCdRowNoList = inVldPotCdRowNoList;
	}

}
