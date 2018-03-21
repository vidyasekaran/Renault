/*
 * System Name     :Post Dragon 
 * Sub system Name :Batch
 * Function ID     :PST-DRG-B000008
 * Module          :Monthly Ordering					
 * Process Outline :Create Monthly Order Base Data
 *
 * <Revision History>
 * Date       Name(Company Name)             Description 
 * ---------- ------------------------------ ---------------------
 * 21-10-2015  	  z015060(RNTBCI)               New Creation
 *
 */
package com.nissangroups.pd.b000008.process;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.nissangroups.pd.b000008.output.B000008Output;
import com.nissangroups.pd.b000008.output.B000008P3Output;
import com.nissangroups.pd.b000008.util.B000008Constants;
import com.nissangroups.pd.repository.MnthlyOrdrRepository;
import com.nissangroups.pd.util.CommonUtil;
import com.nissangroups.pd.util.PDConstants;
import com.nissangroups.pd.util.PDMessageConsants;


/**
 * The Class NscForecstVolProcess.
 *
 * @author z015060
 */

public class UpdateSuspendedProcess {

	/** Constant LOG */
	private static final Log LOG = LogFactory.getLog(UpdateSuspendedProcess.class
			.getName());

	@Autowired(required = false)
	private MnthlyOrdrRepository mnthRep;
	
	/** Constructor UpdateSuspendedProcess */
	public UpdateSuspendedProcess() {
	}
	
	/**
	 * Extract Suspended /non suspended Data  ,update suspended and non-suspended and
	 * Update the Spec Reference Time
	 * ProcessId : P0012,P0013,P0014,P0015,P0016
	 * @param item 
	 * @param B000008P3Output the object
	 * @return the B000008P3Output class
	 * @throws Exception the exception
	 */
	public B000008P3Output executeProcess(B000008Output outputObject, B000008P3Output object) {
		LOG.info("Inside UpdateSuspendedProcess");
		
		/** P0015 - Update Suspended Data */
		mnthRep.updateSuspndData(outputObject.getObjB000008P1Output().getMaxPrdList());
		
		/** P0012 - Extract Suspended /non suspended Data */
		object.setNonSuspndData(mnthRep.extractNonSuspndData(outputObject.getObjB000008P1Output().getMaxPrdList()));
		if(!object.getNonSuspndData().isEmpty()){
			
			/** P0014 - Update Non Suspended Data */
		mnthRep.updateNonSuspnd(object.getNonSuspndData());
		}else {
			CommonUtil.logMessage(PDMessageConsants.M00228,
					B000008Constants.CONSTANT_V6, new String[] {
							B000008Constants.BATCH_ID_B000008,
							PDConstants.NON_SUSPENDED_DATA,
							PDConstants.MONTHLY_ORDER_TRN,
							B000008Constants.PROCESS14,
							outputObject.getObjB000008ParamOutput().getPorCd(),
							outputObject.getObjReaderOutput().getOrdrTkBsMnth()});
		}
			
		/** P0016 - Update Spec Reference Time */
		mnthRep.updateSpecRefTime(outputObject.getObjB000008ParamOutput().getPorCd());
		
		return object;
	}

}
