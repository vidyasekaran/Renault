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

import org.springframework.beans.factory.annotation.Autowired;

import com.nissangroups.pd.b000008.output.B000008Output;
import com.nissangroups.pd.b000008.output.B000008P2Output;
import com.nissangroups.pd.repository.MnthlyOrdrRepository;

/**
 * The Class InsertMnthlyordrTrnProcess.
 *
 * @author z015060
 */
public class InsertMnthlyordrTrnProcess {
	
	@Autowired(required = false)
	private MnthlyOrdrRepository mnthRep;
	
	/** Constructor of InsertMnthlyordrTrnProcess */
	public InsertMnthlyordrTrnProcess() {
	}

	/**
	 *  This process is to Insert Into Month Order Trn
	 * Process Id P0007
	 * @param objOutput 
	 * @param B000008P2Output the object
	 * @return 
	 * @throws Exception the exception
	 */
	public void executeProcess(B000008Output objOutput, B000008P2Output object) {
		
		/** P0007 - Insert Into Month Order Trn */
		mnthRep.insertMnthOrdrTrnOEIData(objOutput.getObjB000008ParamOutput().getPrdStgCd(),object.getPotOrderableEndItm());
	}

	

}
