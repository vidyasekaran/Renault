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

import com.nissangroups.pd.b000008.util.B000008Constants;
import com.nissangroups.pd.b000011.output.B000011Output;
import com.nissangroups.pd.b000011.util.B000011Constants;
import com.nissangroups.pd.repository.MnthlyOCFTrnRepository;
import com.nissangroups.pd.util.CommonUtil;
import com.nissangroups.pd.util.PDConstants;
import com.nissangroups.pd.util.PDMessageConsants;

/**
 * The Class ExtByrGrpCdAutoAllctnProcess.
 *
 * @author z015060
 */
public class ExtByrGrpCdAutoAllctnProcess {
	
	/** Constant LOG */
	private static final Log LOG = LogFactory.getLog(ExtByrGrpCdAutoAllctnProcess.class
			.getName());

	@Autowired(required = false)
	private MnthlyOCFTrnRepository mnthOcfRep;


	/** Variable buyerGrpUsge */
	List<Object[]> buyerGrpUsge = new ArrayList<Object[]>();
	
	
	/**
	 * P0004 business logic
	 * @param object
	 * @return
	 */
	public B000011Output executeProcess(B000011Output object)  {

		//Process P0004
		LOG.info("Process P0004 ");
		buyerGrpUsge=mnthOcfRep.getBuyerGrpLvlOCF(object);
		
		if(!buyerGrpUsge.isEmpty()){
			object.setBuyerGrpUsge(buyerGrpUsge);
		}else{
			CommonUtil.logMessage(PDMessageConsants.M00160, B000008Constants.CONSTANT_V4, new String[]{B000011Constants.BATCH_ID_B000011,
					 PDConstants.BUYER_GROUP_CD,object.getObjB000011ParamOutput().getPorCd(),PDConstants.MST_BUYER});
			CommonUtil.stopBatch();
		}
		object.setBuyerGrpUsge(buyerGrpUsge);
		
		LOG.info("Process P0005");
		
		mnthOcfRep.insertBuyerGrpOcfLmt(object);
		
		
		return object;
	}



}
