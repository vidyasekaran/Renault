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

import java.text.ParseException;


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
public class NscForecstVolProcess {

	/** Constant LOG */
	private static final Log LOG = LogFactory.getLog(NscForecstVolProcess.class
			.getName());
	
	@Autowired(required = false)
	private MnthlyOrdrRepository mnthRep;
	
	/** Constructor with B000008Output and EntityManager  parameters */
	public NscForecstVolProcess() {
	}
	
	
	/**
	 * Extract Monthly order Trn for updating NSC_FORCAST_VOLUME, Extract Monthly order Trn for updating
	 * NSC_CONFIRMATION_MONTHLY_TRN
	 * ProcessId : P0008,P0009,P0010,P0011,
	 * @param outObject 
	 * @param B000008P3Output the object
	 * @return the B000008P3Output class
	 * @throws ParseException 
	 * @throws Exception the exception
	 */
	public B000008P3Output executeProcess(B000008Output outObject, B000008P3Output object) {
		LOG.info("Inside NscForecstVolProcess ");
		
	/** P0008, P0009 - Extract Monthly order Trn for updating NSC_FORCAST_VOLUME */
	object.setNscFrcstVol(mnthRep.extractNscFrcstVol(outObject.getObjReaderOutput().getOrdrTkBsMnth(),
			outObject.getObjB000008P1Output().getMaxPrdList()));
	if(!object.getNscFrcstVol().isEmpty()){
	mnthRep.insertNScFrcstVol(object.getNscFrcstVol());
	}
	else {
		CommonUtil.logMessage(PDMessageConsants.M00171, B000008Constants.CONSTANT_V4, new String[]{B000008Constants.BATCH_ID_B000008,
				 PDConstants.NSC_FORECAST_DATA,PDConstants.MONTHLY_ORDER_TRN,B000008Constants.PROCESS_P10});	
	}
	
	
	/** P0010, P0011 - Extract Monthly order Trn for updating NSC_CONFIRMATION_MONTHLY_TRN */
	object.setNscConf(mnthRep.extractNscConf(outObject));
	if(!object.getNscConf().isEmpty()){
	mnthRep.insertNScConf(outObject.getObjB000008ParamOutput().getPrdStgCd(),object.getNscConf());
	}
	else {
		CommonUtil.logMessage(PDMessageConsants.M00171, B000008Constants.CONSTANT_V4, new String[]{B000008Constants.BATCH_ID_B000008,
				 PDConstants.NSC_CONFIRMATION_DATA,PDConstants.MONTHLY_ORDER_TRN,B000008Constants.PROCESS_P10});		
	}
	
	return object;
	}

}
