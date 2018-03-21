/*
 * System Name       : Post Dragon 
 * Sub system Name   : I Interface
 * Function ID       : PST_DRG_I000103
 * Module            : CM Common		
 * Process Outline   : Interface for Receive User Master from SAP															
 *
 * <Revision History>
 * Date       Name(Company Name)             Description 
 * ---------- ------------------------------ ---------------------
 * 11-01-2016  	  z016127(RNTBCI)               Initial Version
 *
 */
package com.nissangroups.pd.i000103.process;

import static com.nissangroups.pd.util.PDConstants.DOLLAR;
import static com.nissangroups.pd.util.PDConstants.INSIDE_METHOD;
import static com.nissangroups.pd.util.PDConstants.OUTSIDE_METHOD;
import static com.nissangroups.pd.util.PDConstants.PERSISTENCE_NAME;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.nissangroups.pd.i000103.bean.I000103RoleMstrParam;
import com.nissangroups.pd.i000103.bean.I000103UserMstParam;
import com.nissangroups.pd.i000103.output.I000103OutputBean;
import com.nissangroups.pd.i000103.util.I000103Constants;
import com.nissangroups.pd.i000103.util.I000103QueryConstants;
import com.nissangroups.pd.util.IFConstants;
import com.nissangroups.pd.util.IfCommonUtil;
import com.nissangroups.pd.util.PDConstants;

/**
 * This Class UpdateBasedOnLineTypeProcess is to insert / update all the records except the error records into different tables depending on the Line Type values 
 * 
 * @author z016127
 *
 */
@Transactional
public class UpdateBasedOnLineTypeProcess {

	/** Constant LOG */
	private static final Log LOG = LogFactory
			.getLog(UpdateBasedOnLineTypeProcess.class.getName());

	/** Common utility service bean injection */
	@Autowired(required = false)
	IfCommonUtil commonutility;
	
	/** Variable  I000103OutputBean */
	private I000103OutputBean paramOutput;
	
	/** Variable entity manager. */
	@PersistenceContext(name = PERSISTENCE_NAME)
	private EntityManager entityManager;
	
	/** Variable Interface file Id */
	private String ifFileId;
	
	/** I000103Common utility service bean injection */
	@Autowired(required = false)
	private I000103CommonUtil commonUtil;
	
	/**
	 * Instantiates a new UpdateBasedOnLineTypeProcess.
	 */
	public UpdateBasedOnLineTypeProcess() {
	}
	
	/**
	 * P0003,P0004,P0005,P0006,P0007 Method to insert / update all the records except the error records into different tables depending on the Line Type values 
	 * 
	 * @param paramOutput
	 * 					I000103OutputBean
	 * @param ifFileId 
	 * 					string
	 */
	public void executeProcess(
			I000103OutputBean paramOutput, String ifFileId)  {
		LOG.info(DOLLAR +INSIDE_METHOD + DOLLAR);
		this.paramOutput =paramOutput;
		this.ifFileId= ifFileId;
		
		List<String> userRoleData = new ArrayList<String>();
		userRoleData.add(I000103Constants.ZVMS_AROLEASGN);
		userRoleData.add(I000103Constants.ZVMS_ACUSPORASGN);
		userRoleData.add(I000103Constants.ZVMS_ACUSRHQASGN);
		userRoleData.add(I000103Constants.ZVMS_ACUSBYRGRPASGN);
		userRoleData.add(I000103Constants.ZVMS_ACUSATTR);
		
		//P0002 Based on the line type values records will be inserted or deleted 
		if(userRoleData.contains(paramOutput.getCol1())){
			switch (paramOutput.getCol1().trim()) {
			
				case I000103Constants.ZVMS_AROLEASGN:
					insetUserRoleDtls();
				break;
				
				case I000103Constants.ZVMS_ACUSPORASGN:
					insertPorMapingDtls();
				break;
	
				case I000103Constants.ZVMS_ACUSRHQASGN:
					insertRhqMapingDtls();
				break;
	
				case I000103Constants.ZVMS_ACUSBYRGRPASGN:
					insertBuyerGrpMapngDtls();
				break;
			
				default:
					//For any other Line Types then the record will be skipped.
				break;
			}
		}
		LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);
	}
				
	/**
	 * P0003 Method to insert / update the User master details into the User Master table on this process
	 */
	public void insertUserMstrDtls() {
			
		LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR);
		
			List<I000103UserMstParam> userDtls = commonUtil.getUserMstDtls();
			 String queryString ="";
			 
			for(int i=0 ; i<userDtls.size();i++){
				Query query1 = entityManager.createNativeQuery(I000103QueryConstants.condition4.toString());
				query1.setParameter(IFConstants.USER_ID, userDtls.get(i).getUserId());
				
				List selectResultSet = query1.getResultList();
				 //If the resultset is empty then insert the record else update the record in user master table
				if(selectResultSet == null || selectResultSet.isEmpty()){
					queryString =I000103QueryConstants.insertUserData.toString();
				}else{
					queryString = I000103QueryConstants.updateUserData.toString();
				}
				
				Query query = entityManager.createNativeQuery(queryString);
					query.setParameter(IFConstants.USER_ID,getValue(userDtls.get(i).getUserId()));
					query.setParameter(IFConstants.USER_NAME, getValue(userDtls.get(i).getUserName()));
					query.setParameter(IFConstants.TIME_ZONE,getValue(userDtls.get(i).getTimeZone()));
					query.setParameter(IFConstants.UPDTD_BY, getValue(userDtls.get(i).getUpdtdBy()));
					query.setParameter(PDConstants.PRMTR_PROCESS_FLAG, getValue(userDtls.get(i).getProcsFlg()));
				query.executeUpdate();
			}
			LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);	
	}
	
	/**
	 * Method to check whether the value is null or not and return the string
	 * 
	 * @param item
	 * 				string
	 * @return the string
	 */
	public String getValue(String item){
		
		return (item == null)?"''":"'"+item.trim()+"'";
		
	}
	
	/**
	 * P0003 Method to insert / update the User Master details and User Role Relationship master details into the master table on this process.
	 */
	public void insetUserRoleDtls(){
	
		LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR);
			String queryString ="";
			Query query = entityManager.createNativeQuery(I000103QueryConstants.selectUserRole
					.toString());
			query.setParameter("userId", paramOutput.getCol3().trim());
			query.setParameter("roleId", paramOutput.getCol6().trim());
			List selectResultSet = query.getResultList();
					
			//If the resultset is empty then insert the record else update the record in user master table
					if(selectResultSet == null || selectResultSet.isEmpty()){
						queryString = I000103QueryConstants.insertUserRole.toString();
						
					}else{
						queryString = I000103QueryConstants.updateUserRole.toString();
					}
					
					Query query1 = entityManager.createNativeQuery(queryString);
						query.setParameter(IFConstants.USER_ID, getValue(paramOutput.getCol3()));
						query.setParameter(IFConstants.ROLE_ID, getValue(paramOutput.getCol6()));
						query.setParameter(IFConstants.UPDTD_BY, getValue(ifFileId));
						query.setParameter(PDConstants.PRMTR_PROCESS_FLAG, getValue(paramOutput.getCol2()));
					query1.executeUpdate();
					
		LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);			
	}
	
	/**
	 * P0004 Method to insert/ update all the User-POR Mapping details into master table.All Exporters should be mapped with the POR codes.
	 */
	public void insertPorMapingDtls() {
		
		LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR);
		
			String queryString ="";
			Query query1 = entityManager.createNativeQuery(I000103QueryConstants.selectUserPorMapping
					.toString());
			query1.setParameter(IFConstants.USER_ID, paramOutput.getCol3().trim());
			query1.setParameter(IFConstants.POR_CD,paramOutput.getCol4().trim());
			List selectResultSet = query1.getResultList();
				
			//If the resultset is empty then insert the record else update the record in user master table
				if(selectResultSet == null || selectResultSet.isEmpty()){
					queryString = I000103QueryConstants.insertUserPorMapping.toString();
				}else{
					queryString = I000103QueryConstants.updateUserPorMapping.toString();
				}
				
				Query query = entityManager.createNativeQuery(queryString);
					query.setParameter(IFConstants.USER_ID, getValue(paramOutput.getCol3()));
					query.setParameter(IFConstants.POR_CD,getValue(paramOutput.getCol4()));
					query.setParameter(IFConstants.UPDTD_BY, getValue(ifFileId));
					query.setParameter(PDConstants.PRMTR_PROCESS_FLAG, getValue(paramOutput.getCol2()));
				query.executeUpdate();
				
		LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);		
	}
	
	/**
	 * P0005 Method to insert / update all the  User-  RHQ code Mapping details into master table.All Importers (RHQ) should be mapped with the RHQ codes.
	 */
	public void insertRhqMapingDtls() {
		
		LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR);
			String queryString ="";
			Query query1 = entityManager.createNativeQuery(I000103QueryConstants.selectUserRhqMapping
					.toString());
			query1.setParameter(IFConstants.USER_ID, paramOutput.getCol3().trim());
			query1.setParameter(IFConstants.RHQ_CD,paramOutput.getCol4().trim());
			List selectResultSet = query1.getResultList();
	
			//If the resultset is empty then insert the record else update the record in user master table
				if(selectResultSet == null || selectResultSet.isEmpty()){
					queryString = I000103QueryConstants.insertUserRhqMapping.toString();
				}else{
					queryString = I000103QueryConstants.updateUserRhqMapping.toString();
				}
				
				Query query = entityManager.createNativeQuery(queryString);
					query.setParameter(IFConstants.USER_ID,getValue(paramOutput.getCol3()));
					query.setParameter(IFConstants.RHQ_CD, getValue(paramOutput.getCol4()));
					query.setParameter(IFConstants.UPDTD_BY, getValue(ifFileId));
					query.setParameter(PDConstants.PRMTR_PROCESS_FLAG, getValue(paramOutput.getCol2()));
				query.executeUpdate();
				
		LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);		
	}
	
	/**
	 * P0006 Method to insert /update all the  User-  Buyer Group code Mapping details into master table.All Importers  should be mapped with the Buyer Group codes. 
	 */
	public void insertBuyerGrpMapngDtls() {
		
		LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR);
			String queryString ="";
			Query query1 = entityManager.createNativeQuery(I000103QueryConstants.selectUserBuyerGrpMapping
					.toString());
			query1.setParameter(IFConstants.USER_ID, paramOutput.getCol3().trim());
			query1.setParameter(IFConstants.BUYER_GRP_CD,paramOutput.getCol4().trim());
			List selectResultSet = query1.getResultList();
				
				if(selectResultSet == null || selectResultSet.isEmpty()){
					queryString = I000103QueryConstants.insertUserBuyerGrpMapping.toString();
				} else{
					queryString = I000103QueryConstants.updateUserBuyerGrpMapping.toString();
			    }
				 Query query = entityManager.createNativeQuery(queryString);
					query.setParameter(IFConstants.USER_ID, getValue(paramOutput.getCol3()));
					query.setParameter(IFConstants.BUYER_GRP_CD, getValue(paramOutput.getCol4()));
					query.setParameter(IFConstants.UPDTD_BY, getValue(ifFileId));
					query.setParameter(PDConstants.PRMTR_PROCESS_FLAG, getValue(paramOutput.getCol2()));
				query.executeUpdate();
				
			LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);	
	}
	
	/**
	 * P0007 Method to insert / Update all the  Role master details into master table.
    */
	public void insertRoleDtls() {
		
			LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);
				String queryString ="";
				List<I000103RoleMstrParam> roleDtls = commonUtil.getRoleMstDtls();
				for(int i=0 ; i<roleDtls.size();i++){
					Query query1 = entityManager.createNativeQuery(I000103QueryConstants.selectRole.toString());
					query1.setParameter("roleId", roleDtls.get(i).getRoleId());
					 List selectResultSet = query1.getResultList();
					 //If the resultset is empty then insert the record else update the record in role master table
					 if(selectResultSet == null || selectResultSet.isEmpty()){
						 queryString = I000103QueryConstants.insertRole.toString();
					}else{
						 queryString = I000103QueryConstants.updateRole.toString();
						 
					}
					 Query query = entityManager.createNativeQuery(queryString);
						query.setParameter(IFConstants.ROLE_ID, getValue(roleDtls.get(i).getRoleId()));
						query.setParameter(IFConstants.ROLE_DESC, getValue(roleDtls.get(i).getRoleDesc()));
						query.setParameter(IFConstants.UPDTD_BY, getValue(roleDtls.get(i).getUpdtdBy()));
						query.setParameter(PDConstants.PRMTR_PROCESS_FLAG, getValue(roleDtls.get(i).getProcsFlg()));
						query.executeUpdate();
						
			 LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);
		 }
	}
}
