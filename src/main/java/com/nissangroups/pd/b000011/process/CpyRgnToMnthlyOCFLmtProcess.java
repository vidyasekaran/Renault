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


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.nissangroups.pd.b000011.output.B000011Output;
import com.nissangroups.pd.repository.MnthlyOCFTrnRepository;
import com.nissangroups.pd.util.PDConstants;


/**
 * The Class CpyRgnToMnthlyOCFLmtProcess.
 *
 * @author z015060
 */

public class CpyRgnToMnthlyOCFLmtProcess {

	/** Constant LOG */
	private static final Log LOG = LogFactory.getLog(CpyRgnToMnthlyOCFLmtProcess.class
			.getName());
	
	@Autowired(required = false)
	private MnthlyOCFTrnRepository mnthOcfRep;
	
	/** Constructor CpyRgnToMnthlyOCFLmtProcess */
	public CpyRgnToMnthlyOCFLmtProcess() {
	}
	

	/**
	 * P0006, P0007, P0008 business logic
	 * @param object
	 */
	public void executeProcess(B000011Output object) {
		LOG.info("Process P0006");
		object=mnthOcfRep.getByrGrpCdAutoAlloctn(object);

		LOG.info("Process P0007.1");
		object=mnthOcfRep.cpyRgnMnthly(object);
		
		if(object.getRgnlMnthlyFlg().equals(PDConstants.Y)){
			
			LOG.info("Process P0007.2.a");
			mnthOcfRep.intialiseMnthlyBuyerOcf(object);
			
			LOG.info("Process P0007.2.b");
			mnthOcfRep.updatelmtBuyrGrp(object);
		}
		
		LOG.info("Process P0008");
		mnthOcfRep.updateProcessRec(object);
	}

}
