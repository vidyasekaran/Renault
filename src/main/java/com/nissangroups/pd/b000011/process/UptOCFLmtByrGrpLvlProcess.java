/*
 * System Name     :Post Dragon 
 * Sub system Name :Batch
 * Function ID     :PST-DRG-B000011
 * Module          : Ordering					
 * Process Outline : RHQ/NSC-wise Volume/OCF Allocation
 *
 * <Revision History>
 * Date       Name(Company Name)             Description 
 * ---------- ------------------------------ ---------------------
 * 04-11-2015  	  z015060(RNTBCI)               New Creation
 *
 */
package com.nissangroups.pd.b000011.process;

import java.util.ArrayList;
import java.util.List;

import com.nissangroups.pd.b000011.output.B000011Output;
import com.nissangroups.pd.repository.MnthlyOCFTrnRepository;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;


/**
 * The Class UptOCFLmtByrGrpLvlProcess.
 *
 * @author z015060
 */
public class UptOCFLmtByrGrpLvlProcess {

	/** Constant LOG */
	private static final Log LOG = LogFactory.getLog(UptOCFLmtByrGrpLvlProcess.class
			.getName());
	
	@Autowired(required = false)
	private MnthlyOCFTrnRepository mnthOcfRep;
	
	
	/** Constructor for UptOCFLmtByrGrpLvlProcess */
	public UptOCFLmtByrGrpLvlProcess() {
	}
	
	
	
	/**
	 * P0005 business logic
	 * @param objB000011Output P0003
	 * @return
	 */
	public B000011Output executeProcess(B000011Output objB000011Output) {
		LOG.info(" Proces UpdatePrdStageCdProcess");
		List<String> ordrTkBsMnth= new ArrayList();
		for(Object[] data:objB000011Output.getOcfLmt()){
			ordrTkBsMnth.add(data[1].toString());
		}
		
		mnthOcfRep.updateIntialMnthReg(objB000011Output,ordrTkBsMnth);
	  
		mnthOcfRep.insertMnthReg(objB000011Output);
		objB000011Output.setOrdrTkBsMnthLst(ordrTkBsMnth);
		return objB000011Output;
	}

}
