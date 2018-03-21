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

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.nissangroups.pd.i000103.bean.I000103UserMstParam;
import com.nissangroups.pd.i000103.bean.I000103RoleMstrParam;
import com.nissangroups.pd.i000103.output.I000103OutputBean;
import com.nissangroups.pd.util.IfCommonUtil;


/**
 * This Class I000103CommonUtil is used to set the extracted values in different Bean based on Line type
 * 
 * @author z016127
 *
 */
public class I000103CommonUtil {

	/** Constant LOG */
	private static final Log LOG = LogFactory
			.getLog(I000103CommonUtil.class.getName());

	/** Common utility service bean injection */
	@Autowired(required = false)
	IfCommonUtil commonutility;

	/** Variable I000103UserMstParam list */
	private List<I000103UserMstParam> userMstDtls = new ArrayList<I000103UserMstParam>();	
	
	/**Variable I000103RoleMstrParam list*/
	private List<I000103RoleMstrParam> roleMstDtls = new ArrayList<I000103RoleMstrParam>();
	
	/**
	 * Instantiates a new I000103CommonUtil.
	 */
	private I000103CommonUtil() {
	}
	
	/** Method to set the extracted role and user data  in I000103RoleMstrParam and I000103UserMstParam 
	 * 
	 * @param outputBeanList
	 * 						list of I000103OutputBean
	 * @param ifFileId
	 * 					string
	 * @return boolean
	 */
	public boolean executeProcess(
			List<I000103OutputBean> outputBeanList, String ifFileId)  {
		LOG.info(DOLLAR +INSIDE_METHOD + DOLLAR);
		
		for(I000103OutputBean item : outputBeanList)
		{
			//
			if(("ZVMS_AROLEASGN").equalsIgnoreCase(item.getCol1())){
				extractUserData(item,ifFileId);
			}else if(("ZVMS_ACUSATTR").equalsIgnoreCase(item.getCol1())){
				extractRoleData(item,ifFileId);
			}
		}
		return true;
	}
		
	/**
	 * Method to set the extracted role data in I000103RoleMstrParam
	 * 
	 * @param paramOutput
	 * 					I000103OutputBean			
	 * @param ifFileId
	 * 					string
	 * @return the result set
	 */
	public List<I000103RoleMstrParam> extractRoleData(I000103OutputBean paramOutput, String ifFileId){
		
		I000103RoleMstrParam inputPrm = new I000103RoleMstrParam();
		
		inputPrm.setRoleId((paramOutput.getCol3() == null)?"":paramOutput.getCol3().trim());
		inputPrm.setRoleDesc((paramOutput.getCol4() == null)?"":paramOutput.getCol4().trim());
		inputPrm.setCrtdBy((ifFileId == null)?"":ifFileId);
		inputPrm.setProcsFlg((paramOutput.getCol2() == null)?"":paramOutput.getCol2().trim());
		inputPrm.setUpdtdBy((ifFileId == null)?"":ifFileId);
		roleMstDtls.add(inputPrm);
		
		return roleMstDtls;
	}
	
	/**
	 * Method to set the extracted user data in I000103UserMstParam
	 * 
	 * @param paramOutput
	 * 					I000103OutputBean
	 * @param ifFileId
	 * 					string
	 * @return the result set
	 */
	public List<I000103UserMstParam> extractUserData(I000103OutputBean paramOutput, String ifFileId){
		
		I000103UserMstParam inputPrm = new I000103UserMstParam();
		
		inputPrm.setUserId((paramOutput.getCol3() == null)?"":paramOutput.getCol3().trim());
		inputPrm.setUserName( (paramOutput.getCol4() == null)?"":paramOutput.getCol4().trim());
		inputPrm.setTimeZone((paramOutput.getCol5() == null)?"":paramOutput.getCol5().trim());
		inputPrm.setCrtdBy((ifFileId == null)?"":ifFileId);
		inputPrm.setProcsFlg((paramOutput.getCol2() == null)?"":paramOutput.getCol2().trim());
		inputPrm.setUpdtdBy((ifFileId == null)?"":ifFileId);
		userMstDtls.add(inputPrm);
		
		return userMstDtls;
	}


	/**
	 * Get the userMstDtls
	 *
	 * @return the userMstDtls
	 */
	public List<I000103UserMstParam> getUserMstDtls() {
		return userMstDtls;
	}


	/**
	 * Sets the userMstDtls
	 *
	 * @param userMstDtls the userMstDtls to set
	 */
	public void setUserMstDtls(List<I000103UserMstParam> userMstDtls) {
		this.userMstDtls = userMstDtls;
	}


	/**
	 * Get the roleMstDtls
	 *
	 * @return the roleMstDtls
	 */
	public List<I000103RoleMstrParam> getRoleMstDtls() {
		return roleMstDtls;
	}


	/**
	 * Sets the roleMstDtls
	 *
	 * @param roleMstDtls the roleMstDtls to set
	 */
	public void setRoleMstDtls(List<I000103RoleMstrParam> roleMstDtls) {
		this.roleMstDtls = roleMstDtls;
	}

	
}
