/*
 * System Name     :Post Dragon 
 * Sub system Name :Batch 
 * Function ID     :PST-DRG-I000014
 * Module          :
 * Process Outline : Repository/Business Layer Class to Co-Ordinate with DB.
 * This Class performs all the necessary   
 *
 * <Revision History>
 * Date       Name(Company Name)             Description 
 * ---------- ------------------------------ ---------------------
 * 13-NOV-2015  	 z015847(RNTBCI)               New Creation
 *
 */
package com.nissangroups.pd.repository;

import static com.nissangroups.pd.util.PDConstants.PERSISTENCE_NAME;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;

public class OEIFeatureOcftoNscRepository 
{
	
	/** Object emgr. */
	@PersistenceContext(name = PERSISTENCE_NAME)
	private EntityManager eMgr;

	@Autowired(required=false)
	private MnthlyOrdrIfTrnRepository mnthlyOrdrIfTrnRepositoryObj ;
	
	@Autowired(required=false)
	private FrznVldnRepository	frznVldnRepositoryObj;
	
	

}
