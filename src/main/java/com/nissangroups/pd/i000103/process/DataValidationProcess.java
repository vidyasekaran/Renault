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

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

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
import com.nissangroups.pd.util.PDMessageConsants;

/**
 * This class DataValidationProcess is to check whether the extracted value exist in the Master tables and log messages
 * 
 * @author z016127
 *
 */
public class DataValidationProcess {


	/** Constant LOG */
	private static final Log LOG = LogFactory
			.getLog(DataValidationProcess.class.getName());
	
	/** Common utility service bean injection */
	@Autowired(required = false)
	IfCommonUtil commonutility;
	
	/** Variable entity manager. */
	@PersistenceContext(name = PERSISTENCE_NAME)
	private EntityManager entityManager;

	/** I000103 Common utility service bean injection */
	@Autowired(required = false)
	private I000103CommonUtil commonUtil;
	
	/** Variable interface field id */
	private String ifFileId;
	
	/**Variable is valid */
	private boolean valid =true;
	
	/**
	 * Instantiates a new DataValidationProcess.
	 */
	private DataValidationProcess() {
	}
	
	/**
	 * P0001 Method to check whether the extracted value exist in the Master tables and log messages if the value does not exist in the table
	 * 
	 * @param outputBeanList
	 * 						list of I000103OutputBean
	 * @param ifFileId
	 * 						string
	 * @return boolean
	 */
	public boolean executeProcess(List<I000103OutputBean> outputBeanList, String ifFileId)  {
		
		LOG.info(DOLLAR +INSIDE_METHOD + DOLLAR);
		// Process P0001
		this.ifFileId =ifFileId;
		for(I000103OutputBean paramOutput : outputBeanList)
		{
			//To check if the value exist in Master tables based on the Line type
			switch (paramOutput.getCol1()) {
			
				case I000103Constants.ZVMS_AROLEASGN:
					extractUserData(paramOutput);
					extractRoleData(paramOutput);
				break;
				
				case I000103Constants.ZVMS_ACUSPORASGN:
					extractData(paramOutput,I000103QueryConstants.condition1.toString(),IFConstants.POR_CD,PDMessageConsants.M00075);
					extractUserData(paramOutput);
				break;
	
				case I000103Constants.ZVMS_ACUSRHQASGN:
					extractData(paramOutput,I000103QueryConstants.condition3.toString(),IFConstants.RHQ_CD,PDMessageConsants.M00104);
					extractUserData(paramOutput);
				break;
	
				case I000103Constants.ZVMS_ACUSBYRGRPASGN:
					extractData(paramOutput,I000103QueryConstants.condition2.toString(),IFConstants.oeiBuyer_buyerCD,PDMessageConsants.M00103);
					extractUserData(paramOutput);
				break;
				
				default:
					//For any other Line Types then the record will be skipped.
				break;
			}
		}
		LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);
		return this.valid;
	}
				
	/**
	 * P0001 Method to check whether Por code exist in the Master Por table and log message if the value does not exist
	 * 
	 * @param paramOutput
	 * 					the I000103OutputBean
	 * @param queryString
	 * 					the string
	 * @param param
	 * 					String
	 * @param message
	 * 					String
	 * @return the Result set
	 */
	public List extractData(I000103OutputBean paramOutput, String queryString, String param,String message){
		
		LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR);
		Query query = entityManager.createNativeQuery(queryString);
			query.setParameter(param, (paramOutput.getCol4() == null)?"''":"'"+paramOutput.getCol4().trim()+"'");
			
			List selectResultSet = query.getResultList();
			
			if(selectResultSet == null || selectResultSet.isEmpty() || (String)selectResultSet.get(0) == null){
					valid = false;
					LOG.error((message).replace(PDConstants.ERROR_MESSAGE_1,
							PDConstants.INTERFACE_FILE_ID+ ifFileId+ "-" + paramOutput.getCol4()));
			}
		LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);	
			return selectResultSet;
	}
		
	/**
	 * P0001 Method to check whether User id exist in the Master User table and log message if the value does not exist 
	 * 
	 * @param paramOutput
	 * 					I000103OutputBean
	 * @return the Result Set
	 */
	public List extractUserData(I000103OutputBean paramOutput){
		
		LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR);
		Query query = entityManager.createNativeQuery(I000103QueryConstants.condition4.toString());
				query.setParameter(IFConstants.USER_ID, (paramOutput.getCol3() == null)?"''":"'"+paramOutput.getCol3().trim()+"'");
			
		List selectResultSet = query.getResultList();
			 
			 if(selectResultSet == null || selectResultSet.isEmpty() || (String)selectResultSet.get(0) == null){
				 
				 List<I000103UserMstParam> userDtls = commonUtil.getUserMstDtls();
				if(userDtls != null && !(userDtls.isEmpty())){
					for(int i=0; i< userDtls.size() ;i++){
						LOG.info("UserId :" +userDtls.get(i).getUserId()+ "  " +paramOutput.getCol3().trim());
						isUserDataValid(userDtls.get(i),paramOutput);
					}
				}else{
					valid = false;
					 LOG.error((PDMessageConsants.M00105).replace(PDConstants.ERROR_MESSAGE_1,
							 PDConstants.INTERFACE_FILE_ID+ ifFileId+ "-" + paramOutput.getCol3())); 
				}
			}
		LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);
		return selectResultSet;
	}
	
	/**
	 * P0001 Method to check whether User id exist in the I000103UserMstParam and log message if the value does not exist 
	 * @param userDtls
	 * 					I000103UserMstParam
	 * @param paramOutput
	 * 					I000103OutputBean
	 */
	public void isUserDataValid(I000103UserMstParam userDtls,I000103OutputBean paramOutput){
		
		if(!(userDtls.getUserId().equalsIgnoreCase(paramOutput.getCol3().trim()))){
			valid = false;
			 LOG.error((PDMessageConsants.M00105).replace(PDConstants.ERROR_MESSAGE_1,
				 PDConstants.INTERFACE_FILE_ID+ ifFileId+ "-" + paramOutput.getCol3())); 
		}
	}
	
	/**
	 * P0001 Method to check whether Role id exist in the Master Role table and log message if the value does not exist 
	 * 
	 * @param paramOutput
	 * 					I000103OutputBean
	 * @return the result set
	 */
	public List extractRoleData(I000103OutputBean paramOutput){
	
		LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR);
		Query query = entityManager.createNativeQuery(I000103QueryConstants.selectRole.toString());
		query.setParameter(IFConstants.ROLE_ID, (paramOutput.getCol6() == null)?"''":"'"+paramOutput.getCol6().trim()+"'");
		
		List selectResultSet = query.getResultList();
		
		if(selectResultSet == null || selectResultSet.isEmpty() || (String)selectResultSet.get(0) == null){
			 List<I000103RoleMstrParam> roleDtls = commonUtil.getRoleMstDtls();
			if(roleDtls != null && !(roleDtls.isEmpty())){
				for(int i=0; i< roleDtls.size() ;i++){
					 LOG.info("Role Id :" +roleDtls.get(i).getRoleId()+ "  " +paramOutput.getCol6().trim());
					 isRoleDataValid(roleDtls.get(i),paramOutput);
				  }
			}else{
				valid = false;
				 LOG.error((PDMessageConsants.M00105).replace(PDConstants.ERROR_MESSAGE_1,
					 PDConstants.INTERFACE_FILE_ID+ ifFileId+ "-" + paramOutput.getCol3())); 
			}
			 
			}
		LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);
		return selectResultSet;
	}
	
	/**
	 * P0001 Method to check whether Role id exist in the I000103RoleMstrParam and log message if the value does not exist 
	 * 
	 * @param roleDtls
	 * 					I000103RoleMstrParam
	 * @param paramOutput
	 * 					I000103OutputBean
	 */
	public void isRoleDataValid(I000103RoleMstrParam roleDtls,I000103OutputBean paramOutput){
		
		 if(!(roleDtls.getRoleId().equals(paramOutput.getCol6().trim()))){
			valid = false;
			 LOG.error((PDMessageConsants.M00105).replace(PDConstants.ERROR_MESSAGE_1,
				 PDConstants.INTERFACE_FILE_ID+ ifFileId+ "-" + paramOutput.getCol3())); 
			}
	}
}
