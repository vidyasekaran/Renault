/*
 * System Name     :Post Dragon 
 * Sub system Name :Batch
 * Function ID     :PST-DRG-B000028
 * Module          :OR Ordering					
 * Process Outline :Automatic_order_adjustment_to_OCF_Limit
 *
 * <Revision History>
 * Date       Name(Company Name)             Description 
 * ---------- ------------------------------ ---------------------
 * 24-11-2015  	  z015060(RNTBCI)               New Creation
 *
 */
package com.nissangroups.pd.b000028.process;

import java.text.ParseException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.nissangroups.pd.b000028.output.B000028Output;
import com.nissangroups.pd.repository.SpecRepository;

/**
 * The Class UpdtAutoAdjustProcess.
 *
 * @author z015060
 */
public class UpdtAutoAdjustProcess {
	
	/** Constant LOG */
	private static final Log LOG = LogFactory.getLog(UpdtAutoAdjustProcess.class
			.getName());
	
	@Autowired(required = false)
	private SpecRepository specRep;
	
	
	public B000028Output executeProcess(B000028Output object) throws NumberFormatException, ParseException  {
		LOG.info("UpdtAutoAdjustProcess");
		object.setPotAdjustLvl(specRep.getPotAdjustLvlData());
		object.setPotLvlByrGrp(specRep.getPotLvlByrGrp());
		
		specRep.updateMnthOrdrTrnInit(object.getObjB000028ParamOutput().getPorCd(),object.getObjB000028ParamOutput().getOrdrTkBsMnth());
		specRep.updateMnthOrdrTrnOrdr(object.getObjB000028ParamOutput().getOrdrTkBsMnth(),object.getObjB000028ParamOutput().getPorCd(),
				object.getCarSrsHrzn());
		specRep.updateMnthOrdrTrn(object.getObjB000028ParamOutput().getOrdrTkBsMnth(),object.getObjB000028ParamOutput().getPorCd(),object.getPotAdjustLvl());
		return object;
	}



}
