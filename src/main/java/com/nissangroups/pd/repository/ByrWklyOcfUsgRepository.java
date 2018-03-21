package com.nissangroups.pd.repository;

import static com.nissangroups.pd.util.PDConstants.PERSISTENCE_NAME;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.nissangroups.pd.b000052.util.B000052Constants;
import com.nissangroups.pd.b000052.util.B000052QueryConstants;

public class ByrWklyOcfUsgRepository {
	/** Object emgr. */
	@PersistenceContext(name = PERSISTENCE_NAME)
	private EntityManager eMgr;
	
	public List<Object[]> findDuplicateMnthly(QueryParamBean qpBean){
		
		Query query = eMgr.createNativeQuery(B000052QueryConstants.fetchWklyocfUsgTrn.toString());
		 query.setParameter(B000052Constants.porCd,qpBean.getPorCd() );
		 query.setParameter(B000052Constants.prodMnth,qpBean.getPrdMnth());
		 query.setParameter(B000052Constants.prodWkNo,qpBean.getPrdMnthWkNo() );
		 query.setParameter(B000052Constants.oseiID,qpBean.getOseiId() );
		 query.setParameter(B000052Constants.featCd,qpBean.getFeatCd());
		 query.setParameter(B000052Constants.ocfFrmCd,qpBean.getOcfFrmCd());
		 /*query.setParameter("carSrs",carSrs);
		 query.setParameter("byrGrpCd",byrGrpCd);*/
		 return query.getResultList();
		
		
	}
	public int insrtWklyocfUsg(QueryParamBean qpBean){
		 Query query2 = eMgr.createNativeQuery(B000052QueryConstants.insrtWklyocfUsgTrnTest.toString());
		 query2.setParameter(B000052Constants.porCd,qpBean.getPorCd() );
		 query2.setParameter(B000052Constants.prodMnth,qpBean.getPrdMnth());
		 query2.setParameter(B000052Constants.prodWkNo,qpBean.getPrdMnthWkNo() );
		 query2.setParameter(B000052Constants.oseiID,qpBean.getOseiId() );
		 query2.setParameter(B000052Constants.featCd, qpBean.getFeatCd());
		 query2.setParameter(B000052Constants.ocfFrmCd,qpBean.getOcfFrmCd());
		 query2.setParameter(B000052Constants.carSrs,qpBean.getCarSrs());
		 query2.setParameter(B000052Constants.byrGrpCd,qpBean.getByrGrpCd() );
		
		return query2.executeUpdate();
		
	}

}
