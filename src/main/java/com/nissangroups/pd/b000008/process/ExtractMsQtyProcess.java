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
import com.nissangroups.pd.util.PDConstants;

/**
 * The Class NscForecstVolProcess.
 *
 * @author z015060
 */
public class ExtractMsQtyProcess {

	/** Constant LOG */
	private static final Log LOG = LogFactory.getLog(ExtractMsQtyProcess.class
			.getName());

	
	/** Variable getOrdrInfo */
	List<Object[]> getOrdrInfo = new ArrayList<Object[]>();
	
	@Autowired(required = false)
	private MnthlyOrdrRepository mnthRep;
	
	/** Constructor with B000008Output and EntityManager  parameters */
	public ExtractMsQtyProcess() {
	}
	
	/**
	 * Extract Extraction of MS_QTY and ORDER_QTY based on Batch trigger information
	 * ProcessId : P0004.1,P0004.2,P0004.3
	 * @param objOutput 
	 * @param B000008P2Output the object
	 * @return the B000008P2Output class
	 * @throws BatchFailedException 
	 * @throws Exception the exception
	 */
	public B000008P2Output executeProcess(B000008Output objOutput, B000008P2Output objB000008P2) {
		LOG.info("ExtractMsQtyProcess");
		if(objOutput.getObjB000008ParamOutput().getOverlapMsQtyFlg().equals(PDConstants.N) && 
				objOutput.getObjB000008P1Output().getOverLapFlg().equals(PDConstants.Y) && 
				objOutput.getObjB000008ParamOutput().getPrdStgCd().equals(PDConstants.TEN)){
			
			/** P0004.1,P0004.2 Extraction of PREVIOUS STAGE ORDER QTY from Monthly Order Trn */
			objB000008P2.setOrdrInfo(mnthRep.getPreStageOrdrQtyData(objOutput));
		}else{
			
			/** P0004.3  Extract Order Information from LATEST_MASTER_SCHDULE_TRN  */
			objB000008P2.setOrdrInfo(mnthRep.getOrdrInfoData(objOutput));
		}
		LOG.info("ExtractMsQtyProcess OUTPUT "+objB000008P2.getOrdrInfo().size());
		return objB000008P2;
	}


}
