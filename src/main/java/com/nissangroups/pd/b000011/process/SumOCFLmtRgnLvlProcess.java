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

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.nissangroups.pd.b000011.output.B000011Output;
import com.nissangroups.pd.b000011.util.B000011Constants;
import com.nissangroups.pd.repository.MnthlyOCFTrnRepository;
import com.nissangroups.pd.util.CommonUtil;
import com.nissangroups.pd.util.PDConstants;
import com.nissangroups.pd.util.PDMessageConsants;

/**
 * The Class SumOCFLmtRgnLvlProcess.
 *
 * @author z015060
 */
public class SumOCFLmtRgnLvlProcess {

	/** Constant LOG */
	private static final Log LOG = LogFactory
			.getLog(SumOCFLmtRgnLvlProcess.class.getName());

	@Autowired(required = false)
	private MnthlyOCFTrnRepository mnthOcfRep;

	/** Variable ocfLmt */
	List<Object[]> ocfLmt = new ArrayList<Object[]>();
	
	/** Constructor of SumOCFLmtRgnLvlProcess */
	public SumOCFLmtRgnLvlProcess() {
	}

	/**
	 * P0002 business logic
	 * @param objB000011Output
	 * @return
	 */
	public B000011Output executeProcess(B000011Output objB000011Output) {
		LOG.info("SumOCFLmtRgnLvlProcess ------------------ P0002");
		ocfLmt = mnthOcfRep.getOCFTrnData(objB000011Output
				.getObjB000011ParamOutput());
		if (ocfLmt.isEmpty()) {
			CommonUtil.logMessage(PDMessageConsants.M00160,
					B000011Constants.CONSTANT_V4, new String[] {
							B000011Constants.BATCH_ID_B000011,
							PDConstants.OCF_LIMIT,
							objB000011Output.getObjB000011ParamOutput()
									.getPorCd(), PDConstants.MONTHLY_OCF_TRN });
			CommonUtil.stopBatch();
		}

		objB000011Output.setOcfLmt(ocfLmt);

		return objB000011Output;
	}

}
