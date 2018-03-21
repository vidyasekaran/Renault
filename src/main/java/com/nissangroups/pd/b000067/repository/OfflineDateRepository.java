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

package com.nissangroups.pd.b000067.repository;

import static com.nissangroups.pd.util.PDConstants.PERSISTENCE_NAME;
import static com.nissangroups.pd.util.PDConstants.PRMTR_OEI_BYR_ID;
import static com.nissangroups.pd.util.PDConstants.PRMTR_ORDR_TK_BS_MNTH;
import static com.nissangroups.pd.util.PDConstants.PRMTR_PORCD;
import static com.nissangroups.pd.util.PDConstants.PRMTR_POT_CD;
import static com.nissangroups.pd.util.PDConstants.PRMTR_PRD_MNTH;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.nissangroups.pd.exception.PdApplicationNonFatalException;
import com.nissangroups.pd.model.TrnMnlDueDatePrmtr;
import com.nissangroups.pd.util.QueryConstants;

public class OfflineDateRepository {

	/** Object emgr. */
	@PersistenceContext(name = PERSISTENCE_NAME)
	private EntityManager eMgr;

	
	List<Object[]> mnthlyPrdnOrdrDtlsList = new ArrayList<Object[]>();
	
	

	public List<Object[]> fetchmnthlyPrdnOrdrExNoDtls(TrnMnlDueDatePrmtr obj,boolean includeMnthlyPrdOrdrTrn)
			throws PdApplicationNonFatalException {
		
		StringBuilder dynamicQuery = new StringBuilder(); 
		dynamicQuery.append(QueryConstants.fetchExNoandSpecDtls);
		if(includeMnthlyPrdOrdrTrn){
			dynamicQuery.append(QueryConstants.fetchMnthlyProdOrdrDtls);
		}
		dynamicQuery.append(QueryConstants.fetchExNoandSpecDtlsJoin);
		if(includeMnthlyPrdOrdrTrn){
			dynamicQuery.append(QueryConstants.fetchMnthlyProdOrdrDtlsJoin);
		}
		dynamicQuery.append(QueryConstants.fetchExNoandSpecDtlsCnd);
		if(includeMnthlyPrdOrdrTrn){
			dynamicQuery.append(QueryConstants.fetchMnthlyProdOrdrDtlsCnd);
		}
		
		Query result = eMgr.createNativeQuery(dynamicQuery.toString());
		
		
		result.setParameter(PRMTR_PORCD, obj.getId().getPorCd());
		result.setParameter(PRMTR_POT_CD, obj.getId().getPotCd());
		result.setParameter(PRMTR_PRD_MNTH, obj.getId().getProdMnth());
		if(includeMnthlyPrdOrdrTrn){
			result.setParameter(PRMTR_ORDR_TK_BS_MNTH, obj.getId().getOrdrTakeBaseMnth());
		}
		result.setParameter(PRMTR_OEI_BYR_ID, obj.getId().getOeiBuyerId());

		mnthlyPrdnOrdrDtlsList = result.getResultList();

		return mnthlyPrdnOrdrDtlsList;
	}
	
		
}
