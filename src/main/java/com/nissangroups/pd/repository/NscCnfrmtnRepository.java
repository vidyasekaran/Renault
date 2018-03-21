package com.nissangroups.pd.repository;

import static com.nissangroups.pd.util.PDConstants.PERSISTENCE_NAME;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
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

import com.nissangroups.pd.b000018.bean.InputBean;
import com.nissangroups.pd.exception.PdApplicationNonFatalException;

public class NscCnfrmtnRepository {

	/** Object emgr. */
	@PersistenceContext(name = PERSISTENCE_NAME)
	private EntityManager eMgr;

	

	public EntityManager geteMgr() {
		return eMgr;
	}


	public void seteMgr(EntityManager eMgr) {
		this.eMgr = eMgr;
	}



}
