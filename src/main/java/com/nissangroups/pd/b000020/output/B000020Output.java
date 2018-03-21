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
 * This Class is used to maintain the B000020 Processor Outputs.
 * 
 * @author z011479
 *
 */
public class B000020Output {

	ExtractByrGrpAndCrSrsOutput objExtractByrGrpAndCrSrsOutput;
	B000020ParamOutput b000020ParamOutput;
	ExtractMstSpecOutput extractMstSpecOutput;
	ExtractFrcstBaseVolOutput extractFrcstBaseVolOutput;
	EndItmLvlAllctdordIdlMxOutput endItmLvlAllctdordIdlMxOutput;
	
	

	public EndItmLvlAllctdordIdlMxOutput getEndItmLvlAllctdordIdlMxOutput() {
		return endItmLvlAllctdordIdlMxOutput;
	}

	public void setEndItmLvlAllctdordIdlMxOutput(
			EndItmLvlAllctdordIdlMxOutput endItmLvlAllctdordIdlMxOutput) {
		this.endItmLvlAllctdordIdlMxOutput = endItmLvlAllctdordIdlMxOutput;
	}

	public ExtractByrGrpAndCrSrsOutput getObjExtractByrGrpAndCrSrsOutput() {
		return objExtractByrGrpAndCrSrsOutput;
	}

	public void setObjExtractByrGrpAndCrSrsOutput(
			ExtractByrGrpAndCrSrsOutput objExtractByrGrpAndCrSrsOutput) {
		this.objExtractByrGrpAndCrSrsOutput = objExtractByrGrpAndCrSrsOutput;
	}

	public B000020ParamOutput getB000020ParamOutput() {
		return b000020ParamOutput;
	}

	public void setB000020ParamOutput(B000020ParamOutput b000020ParamOutput) {
		this.b000020ParamOutput = b000020ParamOutput;
	}

	public ExtractMstSpecOutput getExtractMstSpecOutput() {
		return extractMstSpecOutput;
	}

	public void setExtractMstSpecOutput(
			ExtractMstSpecOutput extractMstSpecOutput) {
		this.extractMstSpecOutput = extractMstSpecOutput;
	}

	public ExtractFrcstBaseVolOutput getExtractFrcstBaseVolOutput() {
		return extractFrcstBaseVolOutput;
	}

	public void setExtractFrcstBaseVolOutput(
			ExtractFrcstBaseVolOutput extractFrcstBaseVolOutput) {
		this.extractFrcstBaseVolOutput = extractFrcstBaseVolOutput;
	}

}
