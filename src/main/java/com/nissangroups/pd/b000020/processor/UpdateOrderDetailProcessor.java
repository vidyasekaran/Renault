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

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;

import com.nissangroups.pd.b000020.output.B000020Output;
import com.nissangroups.pd.b000020.output.B000020ParamOutput;
import com.nissangroups.pd.b000020.output.ExtractMstSpecOutput;
import com.nissangroups.pd.b000020.process.DataInitializationProcess;
import com.nissangroups.pd.b000020.process.OcfValidationProcess;
import com.nissangroups.pd.b000020.process.UpdateByrGrpMnthlyOcfLmtProcess;
import com.nissangroups.pd.b000020.process.UpdateMonthlyOrderProcess;
import com.nissangroups.pd.b000020.process.UpdateRgnlOcfLmtProcess;

/**
 * This class is used to Calculate and allocated the orders.
 * 
 * @author z011479
 *
 */
public class UpdateOrderDetailProcessor implements
		ItemProcessor<B000020Output, B000020Output> {
	/** Constant LOG. */
	private static final Log LOG = LogFactory
			.getLog(UpdateOrderDetailProcessor.class.getName());


	@Autowired(required = false)
	private UpdateMonthlyOrderProcess updateMonthlyOrderProcess;

	@Autowired(required = false)
	UpdateByrGrpMnthlyOcfLmtProcess updateByrGrpMnthlyOcfLmtProcess;

	@Autowired(required = false)
	UpdateRgnlOcfLmtProcess updateRgnlOcfLmtProcess;

	@Autowired(required = false)
	DataInitializationProcess dataInitializationProcess;

	@Autowired(required = false)
	OcfValidationProcess ocfValidationProcess;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.batch.item.ItemProcessor#process(java.lang.Object)
	 */
	@Override
	public B000020Output process(B000020Output b000020Output) throws Exception {
		LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR);
		B000020ParamOutput b000020ParamOutput = new B000020ParamOutput();
		ExtractMstSpecOutput extractMstSpecOutput = new ExtractMstSpecOutput();
		extractMstSpecOutput = b000020Output.getExtractMstSpecOutput();
		b000020ParamOutput = b000020Output.getB000020ParamOutput();
		dataInitializationProcess.executeProcess(
				extractMstSpecOutput.getMstSpecDtlsAll(), b000020ParamOutput);
		
			updateMonthlyOrderProcess.executeProcess(b000020ParamOutput);
		b000020ParamOutput = updateByrGrpMnthlyOcfLmtProcess
				.executeProcess(b000020ParamOutput);
		// P00017
		ocfValidationProcess.executeProcess(b000020ParamOutput);
		// P00018
		updateRgnlOcfLmtProcess.executeProcess(b000020ParamOutput);
		b000020Output.setB000020ParamOutput(b000020ParamOutput);
		LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);
		return b000020Output;
	}

}
