package com.nissangroups.pd.repository;

import static com.nissangroups.pd.util.PDConstants.PERSISTENCE_NAME;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.nissangroups.pd.i000026.util.I000026QueryConstants;
import com.nissangroups.pd.i000043.processor.DailyOcfIfProcessor;
import com.nissangroups.pd.util.CommonUtil;
import com.nissangroups.pd.util.PDConstants;
import com.nissangroups.pd.util.PDMessageConsants;

public class PorCarSrsMstRepository {
	
	public PorCarSrsMstRepository(){
	}

	/** Constant LOG. */
	private static final Log LOG = LogFactory.getLog(PorCarSrsMstRepository.class.getName());
	
	
	/** Variable entity manager. */
	@PersistenceContext(name = PERSISTENCE_NAME)
	private EntityManager entityManager;

	public List<Object[]> getCarGroup(QueryParamBean qryParamBean) {
		List<Object[]> crGrpQryRslt = null;
		try {

			Query crGrpQry = entityManager
					.createNativeQuery(I000026QueryConstants.CarGroupQuery
							.toString());
			crGrpQry.setParameter(PDConstants.BATCH_POR_CODE,
					qryParamBean.getPorCd());
			crGrpQry.setParameter(PDConstants.PRMTR_CAR_SRS,
					qryParamBean.getCarSrs());
			crGrpQryRslt = crGrpQry.getResultList();

		} catch (Exception e) {
			LOG.error(e);
		}

		return crGrpQryRslt;
	}

}
