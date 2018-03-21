package com.nissangroups.pd.repository;


import static com.nissangroups.pd.util.PDConstants.PERSISTENCE_NAME;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.nissangroups.pd.b000052.util.B000052QueryConstants;
import com.nissangroups.pd.model.TrnRegionalWklyOcfLmt;
import com.nissangroups.pd.model.TrnRegionalWklyOcfLmtPK;
import com.nissangroups.pd.util.PDConstants;

public class RegionalWklyOcfLimitTrnRepository {
	
	/** Object emgr. */
	@PersistenceContext(name = PERSISTENCE_NAME)
	private EntityManager eMgr;
	
	public int initUsgQty(QueryParamBean qpBean) {
	Query query = eMgr.createNativeQuery(B000052QueryConstants.rngLvlInit
			.toString());

	query.setParameter(B000052QueryConstants.POR, qpBean.getPorCd());
	query.setParameter(B000052QueryConstants.PRODWK, qpBean.getPrdMnthWkNo());
	query.setParameter(B000052QueryConstants.PRODMNTH, qpBean.getPrdMnth());
	query.setParameter(B000052QueryConstants.OCFBYRGRP, qpBean.getOcfByrGrpCd());
	query.setParameter(B000052QueryConstants.OCFRGNCD, qpBean.getOcfRgnCd());
	query.setParameter(B000052QueryConstants.CARSRS, qpBean.getCarSrs());
	query.setParameter(B000052QueryConstants.PLANTCD, qpBean.getPlntCd());
	query.setParameter(B000052QueryConstants.LINECLS, qpBean.getLineClass());
	query.setParameter(B000052QueryConstants.PRODDAYNO, qpBean.getPrdDayNo());
	return query.executeUpdate();
	}
	
	public boolean isListExist(QueryParamBean qpBean) {

		
		boolean resultFlag = true;
		

		TrnRegionalWklyOcfLmt objTrnRegionalWklyOcfLmt = null;
		TrnRegionalWklyOcfLmtPK objTrnRegionalWklyOcfLmtPK = new TrnRegionalWklyOcfLmtPK();
		objTrnRegionalWklyOcfLmtPK.setCarSrs(qpBean.getCarSrs());
		objTrnRegionalWklyOcfLmtPK.setFeatCd(qpBean.getFeatCd());
		objTrnRegionalWklyOcfLmtPK.setLineClass(qpBean.getLineClass());
		objTrnRegionalWklyOcfLmtPK.setOcfBuyerGrpCd(qpBean.getOcfByrGrpCd());
		objTrnRegionalWklyOcfLmtPK.setOcfRegionCd(qpBean.getOcfRgnCd());
		objTrnRegionalWklyOcfLmtPK.setPlantCd(qpBean.getPlntCd());
		objTrnRegionalWklyOcfLmtPK.setPorCd(qpBean.getPorCd());
		objTrnRegionalWklyOcfLmtPK.setProdMnth(qpBean.getPrdMnth());

		objTrnRegionalWklyOcfLmtPK.setProdDayNo(qpBean.getPrdDayNo());
		objTrnRegionalWklyOcfLmtPK.setProdWkNo(qpBean.getPrdMnthWkNo());

		objTrnRegionalWklyOcfLmt = eMgr.find(TrnRegionalWklyOcfLmt.class,
				objTrnRegionalWklyOcfLmtPK);

		if (objTrnRegionalWklyOcfLmt == null) {
			resultFlag = false;
		}

		
		return resultFlag;
	}
	
	public int rnglLvlLimitUpdateQuery(QueryParamBean qpBean) {
		Query query = eMgr.createNativeQuery(B000052QueryConstants.rngLvlUpdate
				.toString());

		query.setParameter(B000052QueryConstants.POR, qpBean.getPorCd());
		query.setParameter(B000052QueryConstants.PRODWK, qpBean.getPrdMnthWkNo());
		query.setParameter(B000052QueryConstants.PRODMNTH, qpBean.getPrdMnth());
		query.setParameter(B000052QueryConstants.OCFBYRGRP, qpBean.getOcfByrGrpCd());
		query.setParameter(B000052QueryConstants.OCFRGNCD, qpBean.getOcfRgnCd());
		query.setParameter(B000052QueryConstants.CARSRS, qpBean.getCarSrs());
		query.setParameter(B000052QueryConstants.PLANTCD, qpBean.getPlntCd());
		query.setParameter(B000052QueryConstants.LINECLS, qpBean.getLineClass());
		query.setParameter(B000052QueryConstants.PRODDAYNO, qpBean.getPrdDayNo());
		query.setParameter(B000052QueryConstants.QTY, qpBean.getQty());
		return query.executeUpdate();
		}
	
	public int rnglLvlLimitInsertQuery(QueryParamBean qpBean) {
		Query query = eMgr.createNativeQuery(B000052QueryConstants.rngLvlInsert
				.toString());
		
		if(qpBean.getQty().isEmpty()|| qpBean.getQty().equalsIgnoreCase("null") || qpBean.getQty()==null){
			query.setParameter(B000052QueryConstants.QTY,PDConstants.B52ZERO);
		}else{
			query.setParameter(B000052QueryConstants.QTY, qpBean.getQty());
		}
		if (PDConstants.B52DBLZERO.equals(qpBean.getOcfFrmCd())) {
			query.setParameter(B000052QueryConstants.LQTY, PDConstants.B52ZERO);
		}
		if (!PDConstants.B52DBLZERO.equals(qpBean.getOcfFrmCd())) {
			query.setParameter(B000052QueryConstants.LQTY, " ");
		}
		query.setParameter(B000052QueryConstants.POR, qpBean.getPorCd());
		query.setParameter(B000052QueryConstants.PRODWK, qpBean.getPrdMnthWkNo());
		query.setParameter(B000052QueryConstants.PRODMNTH, qpBean.getPrdMnth());
		query.setParameter(B000052QueryConstants.OCFBYRGRP, qpBean.getOcfByrGrpCd());
		query.setParameter(B000052QueryConstants.OCFRGNCD, qpBean.getOcfRgnCd());
		query.setParameter(B000052QueryConstants.CARSRS, qpBean.getCarSrs());
		query.setParameter(B000052QueryConstants.FEATCD, qpBean.getFeatCd());
		query.setParameter(B000052QueryConstants.FEATTYPECD, qpBean.getFeatTypCd());
		query.setParameter(B000052QueryConstants.OCFFRAMECD, qpBean.getOcfFrmCd());
		query.setParameter(B000052QueryConstants.PLANTCD, qpBean.getPlntCd());
		query.setParameter(B000052QueryConstants.LINECLS, qpBean.getLineClass());

		query.setParameter(B000052QueryConstants.PRODDAYNO, qpBean.getPrdDayNo());

		return query.executeUpdate();
	}
	
	public void ocfLimitUpdate(QueryParamBean qpBean) {
		
		Query query = eMgr.createNativeQuery(B000052QueryConstants.update1
				.toString());
		if(qpBean.getQty().isEmpty()|| qpBean.getQty().equalsIgnoreCase("null") || qpBean.getQty()==null){
			query.setParameter(B000052QueryConstants.QTY,PDConstants.B52ZERO);
		}else{
			query.setParameter(B000052QueryConstants.QTY, qpBean.getQty());
		}
		if (PDConstants.B52DBLZERO.equals(qpBean.getOcfFrmCd()) && PDConstants.B52ZERO.equals(qpBean.getOcfAllocFlg())) {
			query.setParameter(B000052QueryConstants.LQTY, qpBean.getQty());
		}
		if (PDConstants.B52DBLZERO.equals(qpBean.getOcfFrmCd()) && PDConstants.B52ONE.equals(qpBean.getOcfAllocFlg())) {
			query.setParameter(B000052QueryConstants.LQTY, PDConstants.B52ZERO);
		}
		if (!PDConstants.B52DBLZERO.equals(qpBean.getOcfFrmCd())) {
			query.setParameter(B000052QueryConstants.LQTY, " ");

		}
		query.setParameter(B000052QueryConstants.POR, qpBean.getPorCd());
		query.setParameter(B000052QueryConstants.PRODWK, qpBean.getPrdMnthWkNo());
		query.setParameter(B000052QueryConstants.CARSRS, qpBean.getCarSrs());
		// query.setParameter(B000052QueryConstants.BYRGRPCD, byrGrpCd);
		query.setParameter(B000052QueryConstants.FEATCD, qpBean.getFeatCd());
		query.setParameter(B000052QueryConstants.FEATTYPECD, qpBean.getFeatTypCd());
		query.setParameter(B000052QueryConstants.OCFFRAMECD, qpBean.getOcfFrmCd());
		query.setParameter(B000052QueryConstants.PLANTCD, qpBean.getPlntCd());
		query.setParameter(B000052QueryConstants.LINECLS, qpBean.getLineClass());
		query.setParameter(B000052QueryConstants.PRODDAYNO, qpBean.getPrdDayNo());
		

	}
	
	public List<Object[]> getErrorData(QueryParamBean qpBean,int maxProdMnth,int maxProdWk) {

		

		String ordrTkBsMnth = qpBean.getOrdrTkBsPrd().substring(0, 6);
		String weekNo = qpBean.getOrdrTkBsPrd().substring(6);

		StringBuilder fetchquery = new StringBuilder();
		List<Object[]> resultList = new ArrayList<>();

		if (qpBean.getPlntLneSummary().equalsIgnoreCase(PDConstants.B52YES)) {
			if (qpBean.getPrcsType().equalsIgnoreCase(PDConstants.B52MONTH)) {
				fetchquery.append(B000052QueryConstants.breachRprtLnSmryY)
						.append(B000052QueryConstants.breachRprtMnthly)
						.append(B000052QueryConstants.breachRprtGrpByY);

			}
			if (qpBean.getPrcsType().equalsIgnoreCase(PDConstants.B52WEEK)) {
				fetchquery.append(B000052QueryConstants.breachRprtLnSmryY)
						.append(B000052QueryConstants.breachRprtWkly)
						.append(B000052QueryConstants.breachRprtGrpByY);
			}

		}
		if (qpBean.getPlntLneSummary().equalsIgnoreCase(PDConstants.B52NO)) {
			if (qpBean.getPrcsType().equalsIgnoreCase(PDConstants.B52MONTH)) {
				fetchquery.append(B000052QueryConstants.breachRprtLnSmryN)
						.append(B000052QueryConstants.breachRprtMnthly)
						.append(B000052QueryConstants.breachRprtGrpByN);
			}
			if (qpBean.getPrcsType().equalsIgnoreCase(PDConstants.B52WEEK)) {
				fetchquery.append(B000052QueryConstants.breachRprtLnSmryN)
						.append(B000052QueryConstants.breachRprtWkly)
						.append(B000052QueryConstants.breachRprtGrpByN);
			}

		}
		Query query = eMgr.createNativeQuery(fetchquery.toString());
		query.setParameter(B000052QueryConstants.POR, qpBean.getPorCd());
		query.setParameter(B000052QueryConstants.ORDRTKBSMNTH, ordrTkBsMnth);
		if (qpBean.getPrcsType().equalsIgnoreCase(PDConstants.B52WEEK)) {
			query.setParameter(B000052QueryConstants.WKNO, weekNo);
			/*
			 * query.setParameter(B000052QueryConstants.PRODMNTH, prodMnth);
			 * query.setParameter(B000052QueryConstants.PRODWK, prodWkNo);
			 */

			query.setParameter("maxProdMnth", maxProdMnth);
			query.setParameter("maxProdWk", maxProdWk);
		}

		query.setParameter(B000052QueryConstants.OCFFRAMECD, qpBean.getOcfFrmCd());
		query.setParameter(B000052QueryConstants.FEATCD, qpBean.getFeatCd());
		query.setParameter(B000052QueryConstants.PLANTCD, qpBean.getPlntCd());
		query.setParameter(B000052QueryConstants.LINECLS, qpBean.getLineClass());
		resultList = query.getResultList();

		
		return resultList;

	}



}
