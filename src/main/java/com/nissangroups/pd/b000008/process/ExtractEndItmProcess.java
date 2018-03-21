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

import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.nissangroups.pd.b000008.output.B000008Output;
import com.nissangroups.pd.b000008.output.B000008P2Output;
import com.nissangroups.pd.b000008.util.B000008Constants;
import com.nissangroups.pd.repository.MnthlyOrdrRepository;
import com.nissangroups.pd.util.CommonUtil;
import com.nissangroups.pd.util.PDConstants;
import com.nissangroups.pd.util.PDMessageConsants;

/**
 * The Class ExtractEndItmProcess.
 *
 * @author z015060
 */
public class ExtractEndItmProcess {

	/** Constant LOG */
	private static final Log LOG = LogFactory.getLog(ExtractEndItmProcess.class
			.getName());

	@Autowired(required = false)
	private MnthlyOrdrRepository mnthRep;
	
	/** Constructor of ExtractEndItmProcess */
	public ExtractEndItmProcess() {
	}

	/**
	 * This process is to Extract Feature attached End Items Process Id P0006.1,
	 * P0006.1,P0006.2, P0006.3, P0006.4
	 * @param objOutput 
	 * 
	 * @param B000008P2Output
	 *            the object
	 * @return the B000008P2Output class
	 * @throws BatchFailedException
	 * @throws Exception
	 *             the exception
	 */
	public B000008P2Output executeProcess(B000008Output objOutput, B000008P2Output object) {
		LOG.info(" Process - P0006.1ExtractEndItmProcess process input ");
		
		/** P0006.1 - Extract POR Based Production Stage Code from PARAMETER_MST */
		object.setPorPrdStgeCd(mnthRep.extractValFrmMstPrmtr(
				B000008Constants.PRMTR_PRODUCTION_STAGE_CD, objOutput.getObjB000008ParamOutput().getPorCd()));
		List<String> prdStg= object.getPorPrdStgeCd();
		if (prdStg == null) {
			CommonUtil.logErrorMessage(PDMessageConsants.M00169,
					B000008Constants.CONSTANT_V3, new String[] {
							B000008Constants.BATCH_ID_B000008,
						PDConstants.PRODUCTION_STAGE_CODE,PDConstants.MESSAGE_MST_PARAMETER });
			CommonUtil.stopBatch();
		}

		/**
		 * P0006.2 - Extract orderable End Item output por_cd,OSEI_ID,
		 * BUYER_GRP_CD
		 */
		object.setOrderableEndItm(mnthRep.getOrdrableEndItem(objOutput, object));

		/**
		 * P0006.3, P0006.4 - Extraction of POT_CD from Parameter master based
		 * on POR and Buyer Group CD
		 */
		object.setPotOrderableEndItm(mnthRep.extractPotCd(objOutput, object));

		LOG.info("ExtractEndItmProcess process output "
				+ object.getPotOrderableEndItm().size());
		return object;
	}

	
}
