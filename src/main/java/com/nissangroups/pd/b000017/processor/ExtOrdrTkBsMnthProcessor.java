/*
 * System Name     :Post Dragon 
 * Sub system Name :Batch
 * Function ID     :PST-DRG-B000017
 * Module          :O Ordering
 * Process Outline :OCF/CCF Usage Calculation for Ordered Volume
 *
 * <Revision History>
 * Date       Name(Company Name)             Description 
 * ---------- ------------------------------ ---------------------
 *12-11-2015      z015399(RNTBCI)               Initial Version
 *
 */
package com.nissangroups.pd.b000017.processor;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.item.ItemProcessor;

import com.nissangroups.pd.b000017.output.B000017Output;
import com.nissangroups.pd.b000017.output.ExtOrdrTkBsMnthOutput;
import com.nissangroups.pd.b000017.mapper.InputMapper;
import static com.nissangroups.pd.util.PDConstants.*;


/** P00001*/
/**
 * @author z015399
 *
 */
public class ExtOrdrTkBsMnthProcessor implements
								ItemProcessor<InputMapper,B000017Output>{
	
	/** Constant LOG. */
	private static final Log LOG = LogFactory.getLog(ExtOrdrTkBsMnthProcessor.class);
	
	/** Job input parameters */
	private String porCd;
	private String updateOnlyFlag;
	private String productionStageCd;
	
   public String getPorCd() {
	return porCd;
   }


	public void setPorCd(String porCd) {
	this.porCd = porCd;
	}


	public String getUpdateOnlyFlag() {
	return updateOnlyFlag;
	}


	public void setUpdateOnlyFlag(String updateOnlyFlag) {
	this.updateOnlyFlag = updateOnlyFlag;
	}


	public String getProductionStageCd() {
	return productionStageCd;
	}


	public void setProductionStageCd(String productionStageCd) {
	this.productionStageCd = productionStageCd;
	}

	/* (non-Javadoc)
	 * @see org.springframework.batch.item.ItemProcessor#process(java.lang.Object)
	 */
	@Override
	public B000017Output process(InputMapper objInputMapper) throws Exception{
		
		LOG.info(DOLLAR + "Inside ExtOrdrTkBsMnthProcessor" + DOLLAR);
		LOG.info("ExtOrdrTkBsMnthProcessor - updateOnlyFlag value is "+ this.updateOnlyFlag);
		
		LOG.info("POR_CD "+ objInputMapper.getId().getPOR_CD() + 
				 "STAGE_CD" + objInputMapper.getSTAGE_CD()+
				"ORDER_TAKE_BASE_MONTH "+ objInputMapper.getId().getORDR_TAKE_BASE_MNTH());
		
		B000017Output objB000017Output = new B000017Output();
		ExtOrdrTkBsMnthOutput objExtOrdrTkBsMnthOutput = new ExtOrdrTkBsMnthOutput();
		objExtOrdrTkBsMnthOutput.setUpdateOnlyFlag(this.updateOnlyFlag);
		objExtOrdrTkBsMnthOutput.setOtbm(objInputMapper.getId().getORDR_TAKE_BASE_MNTH());
		
		objB000017Output.setObjExtOrdrTkBsMnthOutput(objExtOrdrTkBsMnthOutput);
		
		LOG.info(DOLLAR + "Outside ExtOrdrTkBsMnthProcessor");
		
		return objB000017Output;
		
	}

}
