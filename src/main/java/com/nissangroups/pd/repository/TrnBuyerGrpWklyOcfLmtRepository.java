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

import com.nissangroups.pd.b000052.util.B000052Constants;
import com.nissangroups.pd.b000052.util.B000052QueryConstants;
import com.nissangroups.pd.model.TrnBuyerGrpWklyOcfLmt;
import com.nissangroups.pd.model.TrnBuyerGrpWklyOcfLmtPK;
import com.nissangroups.pd.util.PDConstants;

public class TrnBuyerGrpWklyOcfLmtRepository {
	
	@PersistenceContext(name = PERSISTENCE_NAME)
	private EntityManager eMgr;
	
	public void initUsgQty(String porCd, String wkNo, String prodMnth) {
	
		Query query = eMgr.createNativeQuery(B000052QueryConstants.initZero
				.toString());

		query.setParameter(B000052QueryConstants.POR, porCd);
		query.setParameter(B000052QueryConstants.PRODWK, wkNo);
		query.setParameter(B000052QueryConstants.PRODMNTH, prodMnth);
		query.executeUpdate();
		

	}
	
	public boolean isListExist(QueryParamBean qpBean) {

		
		boolean resultFlag = true;
		
		TrnBuyerGrpWklyOcfLmt objTrnBuyerGrpWklyOcfLmt = null;
		TrnBuyerGrpWklyOcfLmtPK objTrnBuyerGrpWklyOcfLmtPK = new TrnBuyerGrpWklyOcfLmtPK();
		objTrnBuyerGrpWklyOcfLmtPK.setBuyerGrpCd(qpBean.getByrGrpCd());
		objTrnBuyerGrpWklyOcfLmtPK.setCarSrs(qpBean.getCarSrs());
		objTrnBuyerGrpWklyOcfLmtPK.setFeatCd(qpBean.getFeatCd());
		objTrnBuyerGrpWklyOcfLmtPK.setLineClass(qpBean.getLineClass());
		objTrnBuyerGrpWklyOcfLmtPK.setPlantCd(qpBean.getPlntCd());
		objTrnBuyerGrpWklyOcfLmtPK.setPorCd(qpBean.getPorCd());

		objTrnBuyerGrpWklyOcfLmtPK.setProdDayNo(qpBean.getPrdDayNo());
		objTrnBuyerGrpWklyOcfLmtPK.setProdMnth(qpBean.getPrdMnth());
		objTrnBuyerGrpWklyOcfLmtPK.setProdWkNo(qpBean.getPrdMnthWkNo());

		objTrnBuyerGrpWklyOcfLmt = eMgr.find(TrnBuyerGrpWklyOcfLmt.class,
				objTrnBuyerGrpWklyOcfLmtPK);

		if (objTrnBuyerGrpWklyOcfLmt == null) {
			resultFlag = false;
		}
		
		return resultFlag;
	}
	
	public List<String> ocftrnlimitDaySelect(QueryParamBean qpBean){
		Query query = eMgr
				.createNativeQuery(B000052QueryConstants.ocftrnlimitDaySelect
						.toString());

		query.setParameter(B000052QueryConstants.POR, qpBean.getPorCd());
		query.setParameter(B000052QueryConstants.PRODWK, qpBean.getPrdMnthWkNo());
		query.setParameter(B000052QueryConstants.PRODMNTH, qpBean.getPrdMnth());
		query.setParameter(B000052QueryConstants.CARSRS, qpBean.getCarSrs());
		query.setParameter(B000052QueryConstants.BYRGRPCD, qpBean.getByrGrpCd());
		query.setParameter(B000052QueryConstants.FEATCD, qpBean.getFeatCd());
		query.setParameter(B000052QueryConstants.PLANTCD, qpBean.getPlntCd());
		query.setParameter(B000052QueryConstants.LINECLS, qpBean.getLineClass());


		return query.getResultList();
		 
		
	} 
	
	public List<Object[]> extractionFetch(QueryParamBean qpBean) {
		
		Query query = eMgr.createNativeQuery(B000052QueryConstants.extraFetch
				.toString());
		query.setParameter(B000052QueryConstants.POR, qpBean.getPorCd());
		query.setParameter(B000052QueryConstants.PLANTCD, qpBean.getPlntCd());
		query.setParameter(B000052QueryConstants.LINECLS, qpBean.getLineClass());

		query.setParameter(B000052QueryConstants.PRODMNTH, qpBean.getPrdMnth());
		query.setParameter(B000052QueryConstants.CARSRS, qpBean.getCarSrs());
		query.setParameter(B000052QueryConstants.PRODDAYNO, qpBean.getPrdDayNo());

		return query.getResultList();

	}
	
	public int ocfLimitInsertQuery(QueryParamBean qpBean){
		Query query = eMgr
				.createNativeQuery(B000052QueryConstants.ocftrnlimitinsert
						.toString());

		query.setParameter(B000052QueryConstants.QTY, qpBean.getQty());
		if (PDConstants.B52DBLZERO.equals(qpBean.getOcfFrmCd())
				&& B000052Constants.OCFALLOCFLAGN.equals(qpBean.getOcfFrmCd())) {
			query.setParameter(B000052QueryConstants.LQTY, qpBean.getQty());
		}
		if (PDConstants.B52DBLZERO.equals(qpBean.getOcfFrmCd())
				&& B000052Constants.OCFALLOCFLAGY.equals(qpBean.getOcfFrmCd())) {
			query.setParameter(B000052QueryConstants.LQTY, PDConstants.B52ZERO);
		}
		if (!PDConstants.B52DBLZERO.equals(qpBean.getOcfFrmCd())) {
			query.setParameter(B000052QueryConstants.LQTY, PDConstants.B52BLANK);

		}
		query.setParameter(B000052QueryConstants.POR, qpBean.getPorCd());
		query.setParameter(B000052QueryConstants.PRODWK, qpBean.getPrdMnthWkNo());
		query.setParameter(B000052QueryConstants.PRODMNTH, qpBean.getPrdMnth());
		query.setParameter(B000052QueryConstants.CARSRS, qpBean.getCarSrs());
		query.setParameter(B000052QueryConstants.BYRGRPCD, qpBean.getByrGrpCd());
		query.setParameter(B000052QueryConstants.FEATCD, qpBean.getFeatCd());
		query.setParameter(B000052QueryConstants.FEATTYPECD, qpBean.getFeatTypCd());
		query.setParameter(B000052QueryConstants.OCFFRAMECD, qpBean.getOcfFrmCd());
		query.setParameter(B000052QueryConstants.PLANTCD, qpBean.getPlntCd());
		query.setParameter(B000052QueryConstants.LINECLS, qpBean.getLineClass());
		query.setParameter(B000052QueryConstants.PRODDAYNO, qpBean.getPrdDayNo());
		
		
		return query.executeUpdate();

	}
	public int  ocfLimitInsertQuery(int dayNo, QueryParamBean qpBean){
		String prodDayLc = String.valueOf(dayNo);
		if (prodDayLc.length() == 1) {

			prodDayLc = prodDayLc + " ";

		}

		Query query = eMgr
				.createNativeQuery(B000052QueryConstants.ocftrnlimitinsert
						.toString());

		query.setParameter(B000052QueryConstants.QTY, qpBean.getQty());
		
		if (PDConstants.B52DBLZERO.equals(qpBean.getOcfFrmCd())
				&& B000052Constants.OCFALLOCFLAGN.equals(qpBean.getOcfAllocFlg())) {
			query.setParameter(B000052QueryConstants.LQTY, qpBean.getQty());
		}
		if (PDConstants.B52DBLZERO.equals(qpBean.getOcfFrmCd())
				&& B000052Constants.OCFALLOCFLAGY.equals(qpBean.getOcfAllocFlg())) {
			query.setParameter(B000052QueryConstants.LQTY, PDConstants.B52ZERO);
		}
		if (!PDConstants.B52DBLZERO.equals(qpBean.getOcfFrmCd())) {
			query.setParameter(B000052QueryConstants.LQTY, PDConstants.B52BLANK);

		}
		
		query.setParameter(B000052QueryConstants.POR, qpBean.getPorCd());
		query.setParameter(B000052QueryConstants.PRODWK, qpBean.getPrdMnthWkNo());
		query.setParameter(B000052QueryConstants.PRODMNTH, qpBean.getPrdMnth());
		query.setParameter(B000052QueryConstants.CARSRS, qpBean.getCarSrs());
		query.setParameter(B000052QueryConstants.BYRGRPCD, qpBean.getByrGrpCd());
		query.setParameter(B000052QueryConstants.FEATCD, qpBean.getFeatCd());
		query.setParameter(B000052QueryConstants.FEATTYPECD, qpBean.getFeatTypCd());
		query.setParameter(B000052QueryConstants.OCFFRAMECD, qpBean.getOcfFrmCd());
		query.setParameter(B000052QueryConstants.PLANTCD, qpBean.getPlntCd());
		query.setParameter(B000052QueryConstants.LINECLS, qpBean.getLineClass());
		query.setParameter(B000052QueryConstants.PRODDAYNO, prodDayLc);
		return query.executeUpdate();
		
	}
	
	public int ocfLmtUpdtQuery(int dayNo, QueryParamBean qpBean){
		String prodDayLc = String.valueOf(dayNo);
		if (prodDayLc.length() == 1) {

			prodDayLc = prodDayLc + " ";

		}
		Query query = eMgr.createNativeQuery(B000052QueryConstants.update1
				.toString());
		query.setParameter(B000052QueryConstants.QTY, qpBean.getQty());
		if (PDConstants.B52DBLZERO.equals(qpBean.getOcfFrmCd())
				&& B000052Constants.OCFALLOCFLAGN.equals(qpBean.getOcfAllocFlg())) {
			query.setParameter(B000052QueryConstants.LQTY,qpBean.getQty());
		}
		if (PDConstants.B52DBLZERO.equals(qpBean.getOcfFrmCd())
				&& B000052Constants.OCFALLOCFLAGY.equals(qpBean.getOcfAllocFlg())) {
			query.setParameter(B000052QueryConstants.LQTY, PDConstants.B52ZERO);
		}
		if (!PDConstants.B52DBLZERO.equals(qpBean.getOcfFrmCd())) {
			query.setParameter(B000052QueryConstants.LQTY, PDConstants.B52BLANK);

		}
		query.setParameter(B000052QueryConstants.POR, qpBean.getPorCd());
		query.setParameter(B000052QueryConstants.PRODWK, qpBean.getPrdMnthWkNo());
		query.setParameter(B000052QueryConstants.PRODMNTH, qpBean.getPrdMnth());
		query.setParameter(B000052QueryConstants.CARSRS, qpBean.getCarSrs());
		query.setParameter(B000052QueryConstants.BYRGRPCD, qpBean.getByrGrpCd());
		query.setParameter(B000052QueryConstants.FEATCD, qpBean.getFeatCd());
		query.setParameter(B000052QueryConstants.FEATTYPECD, qpBean.getFeatTypCd());
		query.setParameter(B000052QueryConstants.OCFFRAMECD, qpBean.getOcfFrmCd());
		query.setParameter(B000052QueryConstants.PLANTCD, qpBean.getPlntCd());
		query.setParameter(B000052QueryConstants.LINECLS, qpBean.getLineClass());
		query.setParameter(B000052QueryConstants.PRODDAYNO, prodDayLc);
		return query.executeUpdate();
	}
	
	
	public List<Object[]> fetchrngnlUsgQty(Object[] rowObject, QueryParamBean qpBean) {
		
		String tempCarSrs;
		String tempOcfRngCd;
		String tempOcfByrGrpCd;
		String tempOcfFrmCd;

		List<Object[]> dataList = new ArrayList<Object[]>();
		String ordrTkBsMnth = qpBean.getOrdrTkBsPrd().substring(0, 6);
		String weekNo = qpBean.getOrdrTkBsPrd().substring(6);

		

		// for(Object tempRowObject:rowObject){
		tempCarSrs = String.valueOf(rowObject[7]);
		tempOcfRngCd = String.valueOf(rowObject[15]);
		tempOcfByrGrpCd = String.valueOf(rowObject[17]);
		tempOcfFrmCd = String.valueOf(rowObject[6]);
		
		StringBuilder finalquery = new StringBuilder();
		if (qpBean.getPlntLneSummary().equalsIgnoreCase(PDConstants.B52YES)) {
			finalquery.append(B000052QueryConstants.rnglUsgQtyY);
			if (qpBean.getPrcsType().equalsIgnoreCase(PDConstants.B52WEEK)) {
				finalquery.append(B000052QueryConstants.rgnlUsgQtyWkly);
			}
			finalquery.append(B000052QueryConstants.rnglUsgQtyY2);

			Query query = eMgr.createNativeQuery(finalquery.toString());
			
			query.setParameter(B000052QueryConstants.POR, qpBean.getPorCd());
			query.setParameter(B000052QueryConstants.ORDRTKBSPRD, ordrTkBsMnth);

			query.setParameter(B000052Constants.carSrs, tempCarSrs);
			query.setParameter(B000052Constants.ocfFrmCd, tempOcfFrmCd);
			query.setParameter(B000052Constants.ocfRgnCd, tempOcfRngCd);
			query.setParameter(B000052Constants.ocfByrGrpCd, tempOcfByrGrpCd);

			if (qpBean.getPrcsType().equalsIgnoreCase(PDConstants.B52WEEK)) {
				query.setParameter(B000052QueryConstants.PRODWK, weekNo);
			}
			dataList = query.getResultList();
		}
		if (qpBean.getPlntLneSummary().equalsIgnoreCase(PDConstants.B52NO)) {

			finalquery.append(B000052QueryConstants.rnglUsgQtyN);
			if (qpBean.getPrcsType().equalsIgnoreCase(PDConstants.B52WEEK)) {
				finalquery.append(B000052QueryConstants.rgnlUsgQtyWkly);
			}
			finalquery.append(B000052QueryConstants.rnglUsgQtyN2);

			Query query = eMgr.createNativeQuery(finalquery.toString());
			query.setParameter(B000052QueryConstants.POR, qpBean.getPorCd());
			query.setParameter(B000052QueryConstants.ORDRTKBSPRD, ordrTkBsMnth);
			query.setParameter("carSrs", tempCarSrs);
			query.setParameter("ocfFrmCd", tempOcfFrmCd);
			query.setParameter("ocfRgnCd", tempOcfRngCd);
			query.setParameter("ocfByrGrpCd", tempOcfByrGrpCd);

			if (qpBean.getPrcsType().equalsIgnoreCase(PDConstants.B52WEEK)) {
				query.setParameter(B000052QueryConstants.PRODWK, weekNo);
			}
			dataList = query.getResultList();
		}

		// }//

		

		return dataList;

	}

}
