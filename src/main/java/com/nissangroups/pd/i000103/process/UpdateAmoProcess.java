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
import static com.nissangroups.pd.util.PDConstants.PERSISTENCE_NAME;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.nissangroups.pd.i000103.output.I000103OutputBean;
import com.nissangroups.pd.i000103.util.I000103QueryConstants;
import com.nissangroups.pd.util.IfCommonUtil;
import com.nissangroups.pd.util.PDConstants;
import com.nissangroups.pd.util.PDMessageConsants;

/**
 * This Class UpdateAmoProcess is to verify whether the User Role ID is part of any user role group and if the Importer,Importer(RHQ),Exporter does not map with any buyer group code 
 * then it should be updated as Error and those information should be shared to AMO Team.
 * 
 * @author z016127
 *
 */
public class UpdateAmoProcess {
	
	/** Constant LOG */
	private static final Log LOG = LogFactory
			.getLog(UpdateAmoProcess.class.getName());

	/** Common utility service bean injection */
	@Autowired(required = false)
	IfCommonUtil commonutility;
	
	/** Variable  I000103OutputBean */
	private I000103OutputBean paramOutput;
	
	/** Variable Interface file Id */
	private String ifFileId;
	
	/** Variable entity manager. */
	@PersistenceContext(name = PERSISTENCE_NAME)
	private EntityManager entityManager;
	
	/**
	 * Instantiates a new UpdateAmoProcess.
	 */
	private UpdateAmoProcess() {
	}
	
	/**
	 * P0001.A, P0001.B, P0001.C, P0001.D Method to verify whether the User Role ID,Importer,Importer(RHQ),Exporter is part of any user group or buyer group code
	 * then it should be updated as Error and those information should be shared to AMO Team.
	 * 
	 * 
	 * @param paramOutput
	 * 					I000103OutputBean	
	 * @param ifFileId 
	 * 					string
	 */
	public void executeProcess(
			I000103OutputBean paramOutput, String ifFileId)  {
		LOG.info(DOLLAR +INSIDE_METHOD + DOLLAR);
		// Process P0002
		this.paramOutput =paramOutput;
		this.ifFileId = ifFileId;
			verifyUserRoleId();
			verifyBuyerRhqPorCode(I000103QueryConstants.verifybuyerGrp.toString(),PDMessageConsants.M00326);
			verifyBuyerRhqPorCode(I000103QueryConstants.verifyRhq.toString(),PDMessageConsants.M00327);
			verifyBuyerRhqPorCode(I000103QueryConstants.verifyPor.toString(),PDMessageConsants.M00328);
	}
				
	/**
	 * P0001.A Method to verify the User Role ID is already part of any user role group in the Post Dragon database or not
	 * 
	 */
	public void verifyUserRoleId(){
		Query query = entityManager.createNativeQuery(I000103QueryConstants.verifyUserRoleId.toString());
		query.setParameter(PDConstants.IF_FILE_ID, ifFileId);
		query.setParameter(PDConstants.PRMTRT_SEQ_NO, paramOutput.getSeqNo());
		List<Object[]> selectResultSet = query.getResultList();
		if(selectResultSet != null && !(selectResultSet.isEmpty())){
			 for (int i = 0; i < selectResultSet.size(); i++) {
				LOG.error(PDMessageConsants.M00325.replace(PDConstants.ERROR_MESSAGE_1, PDConstants.INTERFACE_FILE_ID
						+ ifFileId+ "-" +PDConstants.ROLE_ID+ selectResultSet.get(i)));
			}
				
		}
	}
	
	/**
	 * P0001.B, P0001.C, P0001.D Method to verify if the Importer,Importer(RHQ),Exporter - user does not map with any buyer group code 
	 * then it should be updated as Error and those information should be shared to AMO Team.
	 * 
	 * @param queryString
	 * 					string
	 * @param message
	 * 					string
	 */
	public void verifyBuyerRhqPorCode(String queryString,String message){
		Query query = entityManager.createNativeQuery(queryString);
		query.setParameter(PDConstants.IF_FILE_ID, ifFileId);
		query.setParameter(PDConstants.PRMTRT_SEQ_NO, paramOutput.getSeqNo());
		List<String[]> selectResultSet = query.getResultList();
			if(selectResultSet != null && !(selectResultSet.isEmpty())){
				for (int i = 0; i < selectResultSet.size(); i++) {
					LOG.error((message).replace(PDConstants.ERROR_MESSAGE_1, PDConstants.INTERFACE_FILE_ID
							+ ifFileId+ "-" +PDConstants.USER_ID+ selectResultSet.get(i)));
				}
			}
		}
}
