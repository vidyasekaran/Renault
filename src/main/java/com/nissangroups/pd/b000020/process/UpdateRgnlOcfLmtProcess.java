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
package com.nissangroups.pd.b000020.process;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.nissangroups.pd.b000020.output.B000020ParamOutput;
import com.nissangroups.pd.exception.PdApplicationException;
import com.nissangroups.pd.repository.CommonRepository;

/**
 * This class is used to update the Regional Level OCF limit base on the
 * allocated quantity.
 * 
 * @author z011479
 *
 */
public class UpdateRgnlOcfLmtProcess {

	@Autowired(required = false)
	private CommonRepository cmnRep;

	/**
	 * This method used to the execute the regional OCF limit update logic.
	 * 
	 * @param b000020ParamOutput
	 * @return
	 * @throws PdApplicationException 
	 */
	public B000020ParamOutput executeProcess(
			B000020ParamOutput b000020ParamOutput) throws PdApplicationException {
		List<Object[]> dscntByrGrp = b000020ParamOutput.getDstnctByrGrpLst();
		for (Object[] dscntByrGrpArry : dscntByrGrp) {
			List<Object[]>  byrGrpLvlOflmt = cmnRep.extractByrGrpMnthlyOcfLmt(
					dscntByrGrpArry, b000020ParamOutput);
			for(Object[] byrGrpLvlOflmtArry : byrGrpLvlOflmt){
				cmnRep.updateRgnlMnthlyLmt(byrGrpLvlOflmtArry);
				}
			}
		// P00020
		return b000020ParamOutput;
	}

}
