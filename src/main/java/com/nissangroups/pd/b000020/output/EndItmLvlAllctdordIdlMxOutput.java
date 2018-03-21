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
package com.nissangroups.pd.b000020.output;


/**
 * @author z011479
 *
 */
public class EndItmLvlAllctdordIdlMxOutput {

	private EndItmOeiByrIdLvlOutput endItmOeiByrIdLvlOutput;

	public EndItmOeiByrIdLvlOutput getEndItmOeiByrIdLvlOutput() {
		return endItmOeiByrIdLvlOutput;
	}

	public void setEndItmOeiByrIdLvlOutput(
			EndItmOeiByrIdLvlOutput endItmOeiByrIdLvlOutput) {
		this.endItmOeiByrIdLvlOutput = endItmOeiByrIdLvlOutput;
	}
	
}
