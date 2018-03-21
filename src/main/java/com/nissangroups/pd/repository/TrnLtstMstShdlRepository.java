package com.nissangroups.pd.repository;

import static com.nissangroups.pd.util.PDConstants.DOLLAR;
import static com.nissangroups.pd.util.PDConstants.INSIDE_METHOD;
import static com.nissangroups.pd.util.PDConstants.OUTSIDE_METHOD;
import static com.nissangroups.pd.util.PDConstants.PERSISTENCE_NAME;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.nissangroups.pd.b000052.util.B000052QueryConstants;
import com.nissangroups.pd.repository.QueryParamBean;
import com.nissangroups.pd.repository.VldnRepository;
import com.nissangroups.pd.util.PDConstants;

public class TrnLtstMstShdlRepository {
	/** Object emgr. */
	@PersistenceContext(name = PERSISTENCE_NAME)
	private EntityManager eMgr;
	
	public EntityManager geteMgr() {
		return eMgr;
	}


	public void seteMgr(EntityManager eMgr) {
		this.eMgr = eMgr;
	}


	/** Constant LOG. */
	private static final Logger LOG = LoggerFactory
			.getLogger(VldnRepository.class);
	
	
	public List<Object[]> fetchByrLvlFeatCdInfo(QueryParamBean qpBean){
		
		LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR);
		List<Object[]> dataList = new ArrayList<Object[]>();
		String ordrTkBsMnth = qpBean.getOrdrTkBsPrd().substring(0, 6);
		StringBuilder finalquery = new StringBuilder();
		if(qpBean.getPlntLneSummary().equalsIgnoreCase(PDConstants.B52YES)){
			finalquery
			.append(B000052QueryConstants.fetchByrLvlFtCdMnthlyY1)
			.append(B000052QueryConstants.fetchByrLvlFtCdMnthly2)
			.append(B000052QueryConstants.fetchByrLvlFtCdMnthly3)
			.append("'")
			.append(qpBean.getPorCd())
			.append("'")
			.append(B000052QueryConstants.fetchByrLvlFtCdMnthly4)
			.append("'")
			.append(ordrTkBsMnth)
			.append("'")
			.append(B000052QueryConstants.fetchByrLvlFtCdMnthly5)
			.append("'")
			.append(ordrTkBsMnth)
			.append("'")
			.append(B000052QueryConstants.fetchByrLvlFtCdMnthlySGrpBy);
			Query query = eMgr.createNativeQuery(finalquery.toString());
			query.setParameter(B000052QueryConstants.PLANTCD, qpBean.getPlntCd());
			query.setParameter(B000052QueryConstants.LINECLS, qpBean.getLineClass());
			query.setParameter(B000052QueryConstants.CNSTDAY, qpBean.getCnstDayNo());
			dataList = query.getResultList();
		}
	if(qpBean.getPlntLneSummary().equalsIgnoreCase(PDConstants.B52NO)){
		finalquery
		.append(B000052QueryConstants.fetchByrLvlFtCdMnthlyN1)
		.append(B000052QueryConstants.fetchByrLvlFtCdMnthly2)
		.append(B000052QueryConstants.fetchByrLvlFtCdMnthly3)
		.append("'")
		.append(qpBean.getPorCd())
		.append("'")
		.append(B000052QueryConstants.fetchByrLvlFtCdMnthly4)
		.append("'")
		.append(ordrTkBsMnth)
		.append("'")
		.append(B000052QueryConstants.fetchByrLvlFtCdMnthly5)
		.append("'")
		.append(ordrTkBsMnth)
		.append("'")
		.append(B000052QueryConstants.fetchByrLvlFtCdMnthlyNGrpBy);
		Query query = eMgr.createNativeQuery(finalquery.toString());
		dataList = query.getResultList();
		}
	LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);
		return dataList;
		
	}
	
	public List<Object[]> fetchByrLvlFeatCdInfoWkly(QueryParamBean qpBean){
		 LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR);
		
		List<Object[]> dataList = new ArrayList<Object[]>();
		String ordrTkBsMnth = qpBean.getOrdrTkBsPrd().substring(0, 6);
		String prodWkNo = qpBean.getOrdrTkBsPrd().substring(6);
		StringBuilder finalquery = new StringBuilder();
		if(qpBean.getPlntLneSummary().equalsIgnoreCase(PDConstants.B52YES)){
			finalquery
			.append(B000052QueryConstants.fetchByrLvlFtCdMnthlyY1)
			.append(B000052QueryConstants.fetchByrLvlFtCdMnthly2)
			.append(B000052QueryConstants.fetchByrLvlFtCdMnthly3)
			.append("'")
			.append(qpBean.getPorCd())
			.append("'")
			.append(B000052QueryConstants.fetchByrLvlFtCdWkly4)
			.append("'")
			.append(ordrTkBsMnth)
			.append("'")
			.append(B000052QueryConstants.fetchByrLvlFtCdMnthly5)
			.append("'")
			.append(ordrTkBsMnth)
			.append("'")
			.append(B000052QueryConstants.fetchByrLvlFtCdWkly6)
			.append("'")
			.append(prodWkNo)
			.append("'")
			.append(B000052QueryConstants.fetchByrLvlFtCdMnthlySGrpBy);
			Query query = eMgr.createNativeQuery(finalquery.toString());
			query.setParameter(B000052QueryConstants.PLANTCD, qpBean.getPorCd());
			query.setParameter(B000052QueryConstants.LINECLS, qpBean.getLineClass());
			query.setParameter(B000052QueryConstants.CNSTDAY, qpBean.getCnstDayNo());
			dataList = query.getResultList();
		}
	if(qpBean.getPlntLneSummary().equalsIgnoreCase(PDConstants.B52NO)){
		finalquery
		.append(B000052QueryConstants.fetchByrLvlFtCdMnthlyN1)
		.append(B000052QueryConstants.fetchByrLvlFtCdMnthly2)
		.append(B000052QueryConstants.fetchByrLvlFtCdMnthly3)
		.append("'")
		.append(qpBean.getPorCd())
		.append("'")
		.append(B000052QueryConstants.fetchByrLvlFtCdWkly4)
		.append("'")
		.append(ordrTkBsMnth)
		.append("'")
		.append(B000052QueryConstants.fetchByrLvlFtCdMnthly5)
		.append("'")
		.append(ordrTkBsMnth)
		.append("'")
		.append(B000052QueryConstants.fetchByrLvlFtCdWkly6)
		.append("'")
		.append(prodWkNo)
		.append("'")
		.append(B000052QueryConstants.fetchByrLvlFtCdMnthlyNGrpBy);
		Query query = eMgr.createNativeQuery(finalquery.toString());
		dataList = query.getResultList();
		}
	LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);
		return dataList;
		
	}
	
	
	public List<Object[]> fetchByrGrgUsgQty(QueryParamBean qpBean) {
		LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR);

		List<Object[]> dataList = new ArrayList<Object[]>();
		String ordrTkBsMnth = qpBean.getOrdrTkBsPrd().substring(0, 6);
		String prodWkNo = qpBean.getOrdrTkBsPrd().substring(6);
		StringBuilder finalquery = new StringBuilder();
		if (qpBean.getPlntLneSummary().equalsIgnoreCase(PDConstants.B52YES)) {
			finalquery.append(B000052QueryConstants.byrGrpUsgQty1);
			if (qpBean.getPrcsType().equalsIgnoreCase(PDConstants.B52MONTH)) {
				finalquery.append(B000052QueryConstants.byrGrpUsgQtyMnthly1);
			}
			if (qpBean.getPrcsType().equalsIgnoreCase(PDConstants.B52WEEK)) {
				finalquery.append(B000052QueryConstants.byrGrpUsgQtyWkly1);
			}
			finalquery.append(B000052QueryConstants.byrGrpUsgQtyGrpBy);

			Query query = eMgr.createNativeQuery(finalquery.toString());
			query.setParameter(B000052QueryConstants.PLANTCD, qpBean.getPlntCd());
			query.setParameter(B000052QueryConstants.LINECLS, qpBean.getLineClass());
			query.setParameter(B000052QueryConstants.CNSTDAY, qpBean.getCnstDayNo());
			query.setParameter(B000052QueryConstants.POR, qpBean.getPorCd());
			query.setParameter(B000052QueryConstants.ORDRTKBSPRD, ordrTkBsMnth);
			if (qpBean.getPrcsType().equalsIgnoreCase(PDConstants.B52WEEK)) {
				query.setParameter(B000052QueryConstants.PRODWK, prodWkNo);
			}
			query.setParameter(B000052QueryConstants.PRCSTYPE, qpBean.getPrcsType());
			dataList = query.getResultList();
		}
		if (qpBean.getPlntLneSummary().equalsIgnoreCase(PDConstants.B52NO)) {
			finalquery.append(B000052QueryConstants.byrGrpUsgQty2);
			if (qpBean.getPrcsType().equalsIgnoreCase(PDConstants.B52MONTH)) {
				finalquery.append(B000052QueryConstants.byrGrpUsgQtyMnthly1);
			}
			if (qpBean.getPrcsType().equalsIgnoreCase(PDConstants.B52WEEK)) {
				finalquery.append(B000052QueryConstants.byrGrpUsgQtyWkly1);
			}
			finalquery.append(B000052QueryConstants.byrGrpUsgQtyGrpByN1)
					.append(B000052QueryConstants.byrGrpUsgQtyGrpByN2);

			Query query = eMgr.createNativeQuery(finalquery.toString());
			query.setParameter(B000052QueryConstants.POR, qpBean.getPorCd());
			query.setParameter(B000052QueryConstants.ORDRTKBSPRD, ordrTkBsMnth);
			if (qpBean.getPrcsType().equalsIgnoreCase(PDConstants.B52WEEK)) {
				query.setParameter(B000052QueryConstants.PRODWK, prodWkNo);
			}
			query.setParameter(B000052QueryConstants.PRCSTYPE, qpBean.getPrcsType());
			dataList = query.getResultList();
		}

		LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);

		return dataList;

	}

}
