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

import static com.nissangroups.pd.util.PDConstants.*;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.nissangroups.pd.exception.PdApplicationNonFatalException;
import com.nissangroups.pd.model.MstMnthOrdrTakeBasePd;
import com.nissangroups.pd.util.QueryConstants;

public class OrdrTkBsPrdMstRepository {

	/** Object emgr. */
	@PersistenceContext(name = PERSISTENCE_NAME)
	private EntityManager eMgr;

	/** Variable order Take Base Period Mst list. */
	List<MstMnthOrdrTakeBasePd> ordrTkBsPrdLst = new ArrayList<MstMnthOrdrTakeBasePd>();
	
	List<String> ordrTkBsMnthLst = new ArrayList<String>();

	public List<MstMnthOrdrTakeBasePd> fetchOrdrTkBsOrdData(String porCd, String prdOrdrStgCd)
			throws PdApplicationNonFatalException {
		
		StringBuilder dynamicQuery = new StringBuilder(); 
		dynamicQuery.append(QueryConstants.fetchOrdrTkBsOrdData);
		dynamicQuery.append(QueryConstants.fetchOrdrTkBsOrdDataCnd);
		
		if("10".equalsIgnoreCase(prdOrdrStgCd)) {
			dynamicQuery.append(QueryConstants.draftOrdrStgCdCondition);
		} else if("20".equalsIgnoreCase(prdOrdrStgCd)) {
			dynamicQuery.append(QueryConstants.finalOrdrStgCdCondition);
		} else {
			dynamicQuery.append(QueryConstants.stgCdNotClsdCondition);
		}
	
		
		Query result = eMgr.createNativeQuery(dynamicQuery.toString(),MstMnthOrdrTakeBasePd.class);
		
		
		result.setParameter(PRMTR_PORCD, porCd);

		ordrTkBsPrdLst = result.getResultList();

		return ordrTkBsPrdLst;
	}

	
	public String fetchLatestOrdrTkBsOrdDataForStageClose(String porCd, String prdOrdrStgCd)
			throws PdApplicationNonFatalException {
		
		StringBuilder dynamicQuery = new StringBuilder(); 
		dynamicQuery.append(QueryConstants.fetchLatestOrdrTkBsOrdData);
		dynamicQuery.append(QueryConstants.fetchOrdrTkBsOrdDataCnd);
		
		if("10".equalsIgnoreCase(prdOrdrStgCd)) {
			dynamicQuery.append(QueryConstants.draftOrdrStgCdCondition);
		} else if("20".equalsIgnoreCase(prdOrdrStgCd)) {
			dynamicQuery.append(QueryConstants.finalOrdrStgCdCondition);
		} 
	
		dynamicQuery.append(QueryConstants.stgStatusCdClsdCondition);
		
		Query result = eMgr.createNativeQuery(dynamicQuery.toString());
		
		result.setParameter(PRMTR_PORCD, porCd);

		ordrTkBsMnthLst = result.getResultList();
		
		String maxOrdrTkBsMnth = null;
		if(ordrTkBsMnthLst!=null && !(ordrTkBsMnthLst.isEmpty()) && ordrTkBsMnthLst.get(0)!=null){
			maxOrdrTkBsMnth = ordrTkBsMnthLst.get(0);
			
		}
		return maxOrdrTkBsMnth;
	}

	
}
