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

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.nissangroups.pd.b000008.output.B000008Output;
import com.nissangroups.pd.b000008.output.B000008P2Output;
import com.nissangroups.pd.repository.MnthlyOrdrRepository;


/**
 * The Class UpdatePrdStageCdProcess.
 *
 * @author z015060
 */
public class UpdatePrdStageCdProcess {

	/** Constant LOG */
	private static final Log LOG = LogFactory.getLog(UpdatePrdStageCdProcess.class
			.getName());
	
	
	/** Variable getOrdrInfo */
	List<Object[]> getOrdrInfo = new ArrayList<Object[]>();
	
	@Autowired(required = false)
	private MnthlyOrdrRepository mnthRep;
	
	
	/** Constructor for UpdatePrdStageCdProcess */
	public UpdatePrdStageCdProcess() {
	}
	
	


	/**
	 *  This process is to Update PRODUCTION_STAGE_CD from draft  to final,
	 *    Insert Into Monthly order Trn or update Monthly order Trn
	 * ProcessId : P0005.1.a, P0005.1.b, P0005.2
	 * @param objOutput 
	 * @param objB000008P2Output 
	 * @param B000008P2Output the object
	 * @return the B000008P2Output class
	 * @throws BatchFailedException 
	 * @throws Exception the exception
	 */
	public void executeProcess(B000008Output objOutput, B000008P2Output objB000008P2Output) {
		LOG.info(" Proces UpdatePrdStageCdProcess");
		
		/** P0005.1.a - Update PRODUCTION_STAGE_CD from draft  to final */
	mnthRep.updatePrdStageCd(objOutput);
	
	/** P0005.1.b, P0005.2 - Insert Into Monthly order Trn or update Monthly order Trn*/
	if(!objB000008P2Output.getOrdrInfo().isEmpty()){
	mnthRep.insertMthOrdrTrnOrdrInfo(objOutput.getObjB000008ParamOutput().getPrdStgCd(),objOutput.getObjReaderOutput().getOrdrTkBsMnth(),objB000008P2Output);
	}
	
	}

}
