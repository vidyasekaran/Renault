package com.nissangroups.pd.repository;

import static com.nissangroups.pd.util.PDConstants.PERSISTENCE_NAME;

import java.util.Arrays;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.nissangroups.pd.util.PDConstants;
import com.nissangroups.pd.util.QueryConstants;

public class FeatMstRepository {
	
	/** Object emgr. */
	@PersistenceContext(name = PERSISTENCE_NAME)
	private EntityManager eMgr;


	public FeatMstRepository() {
	}
	
	public List<Object[]> getFeatDtls(QueryParamBean qryParamBean){
		Query dtlsQry = eMgr
				.createNativeQuery(QueryConstants.ExtractFeatureDtlsQuery
						.toString());
		List<String> names = Arrays.asList("10", "20", "40", "50", "70",
				"80");
		dtlsQry.setParameter(PDConstants.BATCH_POR_CODE, qryParamBean.getPorCd());
		dtlsQry.setParameter(PDConstants.PRMTR_CAR_SRS, qryParamBean.getCarSrs());
		dtlsQry.setParameter(PDConstants.PRMTR_OCFRGNCD, qryParamBean.getOcfRgnCd());
		dtlsQry.setParameter(PDConstants.PRMTR_BYR_GRP_CD, qryParamBean.getByrGrpCd());
		dtlsQry.setParameter(PDConstants.PRMTR_OCF_FRAME_CD, qryParamBean.getOcfFrmCd());
		dtlsQry.setParameter(PDConstants.PRMTR_FEAT_SHRT_DESC, qryParamBean.getFeatShrtDesc());
		dtlsQry.setParameter(PDConstants.PRMTR_FEAT_TYPE_CD, names);
		return  dtlsQry.getResultList();
	}

}
