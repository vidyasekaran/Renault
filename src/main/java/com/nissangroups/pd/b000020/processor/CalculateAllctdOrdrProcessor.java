/*
 * System Name     :Post Dragon 
 * Sub system Name :Batch
 * Function ID     :PST-DRG-B000020
 * Module          :O Ordering
 * Process Outline :Forecast Order Creation (N+3) Onwards (Draft & Final)
 *
 * <Revision History>
 * Date       Name(Company Name)             Description 
 * ---------- ------------------------------ ---------------------
 * 29-10-2015      z011479(RNTBCI)               New Creation
 *
 */
package com.nissangroups.pd.b000020.processor;

import static com.nissangroups.pd.util.PDConstants.DOLLAR;
import static com.nissangroups.pd.util.PDConstants.INSIDE_METHOD;
import static com.nissangroups.pd.util.PDConstants.OUTSIDE_METHOD;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;

import com.nissangroups.pd.b000020.output.B000020Output;
import com.nissangroups.pd.b000020.output.B000020ParamOutput;
import com.nissangroups.pd.b000020.output.ExtractFrcstBaseVolOutput;
import com.nissangroups.pd.b000020.output.ExtractMstSpecOutput;
import com.nissangroups.pd.b000020.process.EndItmLvlAllctdordAvgMxProcess;
import com.nissangroups.pd.b000020.process.EndItmLvlAllctdordIdlMxProcess;
import com.nissangroups.pd.b000020.process.OrderAllocationProcess;
import com.nissangroups.pd.util.PDConstants;

/**
 * This class is used to Calculate and allocated the orders.
 * 
 * @author z011479
 *
 */
public class CalculateAllctdOrdrProcessor implements
		ItemProcessor<B000020Output, B000020Output> {
	/** Constant LOG. */
	private static final Log LOG = LogFactory.getLog(B000020Processor.class
			.getName());


	@Autowired(required = false)
	private EndItmLvlAllctdordIdlMxProcess endItmLvlAllctdordIdlMxProcess;

	@Autowired(required = false)
	private EndItmLvlAllctdordAvgMxProcess endItmLvlAllctdordAvgMxProcess;

	@Autowired(required = false)
	private OrderAllocationProcess orderAllocationProcess;

	boolean idlMxFlg = false;
	boolean avgMxFlg = false;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.batch.item.ItemProcessor#process(java.lang.Object)
	 */
	@Override
	public B000020Output process(B000020Output b000020Output) throws Exception {
		LOG.info(DOLLAR +INSIDE_METHOD + DOLLAR);
		List<Object[]> mstSpecDtls = b000020Output.getExtractMstSpecOutput()
				.getMstSpecDtls();
		ExtractMstSpecOutput extractMstSpecOutput = new ExtractMstSpecOutput();
		extractMstSpecOutput.setMstSpecDtls(mstSpecDtls);
		Map<String, Object[]> byrGrpPrMp = new HashMap<String, Object[]>();
		B000020ParamOutput b000020ParamOutput = new B000020ParamOutput();
		ExtractFrcstBaseVolOutput extractFrcstBaseVolOutput = new ExtractFrcstBaseVolOutput();
		extractFrcstBaseVolOutput = b000020Output
				.getExtractFrcstBaseVolOutput();
		byrGrpPrMp = b000020Output.getExtractFrcstBaseVolOutput()
				.getByrGrpPrMap();
		b000020ParamOutput = b000020Output.getB000020ParamOutput();
		b000020Output.getExtractFrcstBaseVolOutput();
		String mpUnqKey = null;
		for (Object[] mstSpecStlsArry : mstSpecDtls) {
			String porCd = (String) mstSpecStlsArry[0];
			String carSrs = (String) mstSpecStlsArry[5];
			String byrGrpCd = (String) mstSpecStlsArry[4];
			mpUnqKey = porCd + carSrs + byrGrpCd;
				String eiRatioFlg = getEiRatioPrFlg(byrGrpPrMp, mpUnqKey);
				if (eiRatioFlg != null  && eiRatioFlg.equalsIgnoreCase(PDConstants.Y)) {
					endItmLvlAllctdordIdlMxProcess.executeProcess(
							mstSpecStlsArry, byrGrpPrMp, b000020ParamOutput,
							avgMxFlg, extractFrcstBaseVolOutput,mstSpecDtls);
					
				}
				 else {
				 endItmLvlAllctdordAvgMxProcess.executeProcess(
				 mstSpecStlsArry,
				 byrGrpPrMp,b000020ParamOutput,idlMxFlg,extractFrcstBaseVolOutput,mstSpecDtls);
				 }
				orderAllocationProcess.executeProcess(extractFrcstBaseVolOutput,b000020ParamOutput);
	
		}
		LOG.info(DOLLAR +OUTSIDE_METHOD + DOLLAR);
		
		return b000020Output;
	}

	/**
	 * This method is used to get the End Item Ratio priority
	 * @param byrGrpPrMp
	 * @param mpUnqKey
	 * @return String 
	 */
	public String getEiRatioPrFlg(Map<String, Object[]> byrGrpPrMp,
			String mpUnqKey) {
		LOG.info(DOLLAR +INSIDE_METHOD + DOLLAR);
		Object[] byrGrpPrArry = byrGrpPrMp.get(mpUnqKey);
		if(byrGrpPrArry == null){
			return null;
		}
		String eiRatioPrFlg = String.valueOf(byrGrpPrArry[4]);
		LOG.info(DOLLAR +OUTSIDE_METHOD + DOLLAR);
		return eiRatioPrFlg;
	}
}
