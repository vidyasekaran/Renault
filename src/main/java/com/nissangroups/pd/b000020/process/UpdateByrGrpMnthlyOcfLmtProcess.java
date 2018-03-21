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

import static com.nissangroups.pd.util.PDConstants.DOLLAR;
import static com.nissangroups.pd.util.PDConstants.INSIDE_METHOD;
import static com.nissangroups.pd.util.PDConstants.OUTSIDE_METHOD;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.nissangroups.pd.b000020.output.B000020ParamOutput;
import com.nissangroups.pd.exception.PdApplicationException;
import com.nissangroups.pd.repository.CommonRepository;

public class UpdateByrGrpMnthlyOcfLmtProcess {

	@Autowired(required = false)
	private CommonRepository cmnRep;
	private static final Log LOG = LogFactory
			.getLog(UpdateByrGrpMnthlyOcfLmtProcess.class.getName());

	/**
	 * @param mstSpecDtls
	 * @param byrGrpPrBd
	 * @param b000020ParamOutput
	 * @param idlMxFlg
	 * @param extractFrcstBaseVolOutput
	 * @return
	 * @throws PdApplicationException 
	 */
	
	public B000020ParamOutput executeProcess(B000020ParamOutput b000020ParamOutput) throws PdApplicationException {
		LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR);
		String ordrTkBsMnth = b000020ParamOutput.getOrdTkBsMnth();
		List<Object[]> byrGrpLst = cmnRep.extractDistinctByrGrpCd();
		b000020ParamOutput.setDstnctByrGrpLst(byrGrpLst);
		for (Object[] distntByrGrpCdArry : byrGrpLst) {
			List<Object[]> byrGrpLvlOcfUsg = cmnRep.extractbyrGrpLvlOcfUsg(
					distntByrGrpCdArry, ordrTkBsMnth);
			for (Object[] byrGrpLvlOcfUsgArry : byrGrpLvlOcfUsg) {
				updateByrGrpMnthlyOcfUsgLmt(byrGrpLvlOcfUsgArry);
			}
		}
		LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);
		return b000020ParamOutput;
	}

	/**
	 * @param byrGrpLvlOcfUsgArry
	 * @throws PdApplicationException
	 */
	public void updateByrGrpMnthlyOcfUsgLmt(Object[] byrGrpLvlOcfUsgArry) throws PdApplicationException{
		LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR);
		cmnRep.updateByrGrpMnthlyOcfUsgLmt(byrGrpLvlOcfUsgArry);
		LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);
	}
}
