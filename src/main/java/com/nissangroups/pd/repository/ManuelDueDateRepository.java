/*
 * System Name     :Post Dragon 
 * Sub system Name :Batch 
 * Function ID     :PST-DRG-B000018/PST-DRG-R000007,R000008,R000009
 * Module          :MONTHLY ORDERING
 * Process Outline : Repository/Business Layer Class to Co-Ordinate with DB.
 *   
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

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;

import com.nissangroups.pd.b000018.bean.InputBean;
import com.nissangroups.pd.exception.PdApplicationNonFatalException;
import com.nissangroups.pd.util.PDConstants;
import com.nissangroups.pd.util.QueryConstants;

public class ManuelDueDateRepository {

	/** Object emgr. */
	@PersistenceContext(name = PERSISTENCE_NAME)
	private EntityManager eMgr;

	
	
	@Autowired(required=false)
	private MnthlyOrdrIfTrnRepository mnthlyOrdrIfTrnRepositoryObj ;
	
	

	public void delete(InputBean input)
			throws PdApplicationNonFatalException {

		StringBuilder dynamicQuery = new StringBuilder();
		
		dynamicQuery.append(QueryConstants.deleteFrmManuelDueDatePrmtr);
		
		executeQuery(dynamicQuery.toString(),input);
		
	}
	
	public void insert(InputBean input)
			throws PdApplicationNonFatalException {

		StringBuilder dynamicQuery = new StringBuilder();
		
		dynamicQuery.append(QueryConstants.insertIntoManuelDueDatePrmtr);
		
		executeQuery(dynamicQuery.toString(),input);
		
		
						
	}
	
	public void executeQuery(String queryString,InputBean input){
		
		queryString = queryString.replaceAll(PDConstants.TABLE_NAME, input.getTableName());
		Query query = eMgr.createNativeQuery(queryString);
		
		query.setParameter(FILE_ID, input.getFileId());
		query.setParameter(PRMTR_SEQ_NO, input.getSeqNo());
		query.setParameter(PRMTR_PORCD, input.getPorCd());
		
		query.executeUpdate();

		
	}
	
	


	public EntityManager geteMgr() {
		return eMgr;
	}


	public void seteMgr(EntityManager eMgr) {
		this.eMgr = eMgr;
	}



}
