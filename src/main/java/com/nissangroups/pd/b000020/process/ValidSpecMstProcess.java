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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.nissangroups.pd.b000020.output.B000020ParamOutput;
import com.nissangroups.pd.b000020.output.ExtractByrGrpAndCrSrsOutput;
import com.nissangroups.pd.b000020.output.ExtractMstSpecOutput;
import com.nissangroups.pd.exception.PdApplicationException;
import com.nissangroups.pd.util.CommonUtil;
import com.nissangroups.pd.util.PDConstants;
import com.nissangroups.pd.util.PDMessageConsants;

public class ValidSpecMstProcess {

	private static final Log LOG = LogFactory.getLog(ValidSpecMstProcess.class
			.getName());

	Map<String, String> crSrsMp = new HashMap<String, String>();
	List<Object[]> validMstSpec = new ArrayList<Object[]>();

	/**
	 * This method used to the execute the regional OCF limit update logic.
	 * 
	 * @param b000020ParamOutput
	 * @return
	 * @throws PdApplicationException
	 */
	public ExtractMstSpecOutput executeProcess(
			ExtractByrGrpAndCrSrsOutput extractByrGrpAndCrSrsOutput,
			ExtractMstSpecOutput extractMstSpecOutput,
			B000020ParamOutput b000020ParamOutput)
			throws PdApplicationException {
		LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR);
		List<Object[]> carSrsLst = extractByrGrpAndCrSrsOutput.getCrSrs();
		addCrSrsToMap(carSrsLst);
		List<Object[]> mstSpecLst = extractMstSpecOutput.getMstSpecDtls();
		validateMstspecData(mstSpecLst);
		extractMstSpecOutput.setMstSpecDtls(validMstSpec);
		if (mstSpecLst.isEmpty() || validMstSpec.isEmpty()) {
			CommonUtil.logMessage(PDMessageConsants.M00169,
					PDConstants.CONSTANT_V3, new String[] {
							PDConstants.BATCH_20_ID,
							PDConstants.CAR_SERIES + " FOR POR_CD: "
									+ b000020ParamOutput.getPorCd(),
							PDConstants.MESSAGE_MASTER_END_ITEM_SPEC });
			throw new PdApplicationException(PDConstants.NO_DATA_FOUND);
		}
		LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);
		return extractMstSpecOutput;
	}

	public void addCrSrsToMap(List<Object[]> carSrsLst) {
		LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR);
		for (Object[] carSrsLstArr : carSrsLst) {
			String carSrs = (String) carSrsLstArr[0];
			String ablshDt = (String) carSrsLstArr[4];
			crSrsMp.put(carSrs, ablshDt);
		}
		LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);
	}

	/**
	 * @param mstSpecLst
	 */
	public void validateMstspecData(List<Object[]> mstSpecLst) {
		LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR);
		for (Object[] mstSpecLstArr : mstSpecLst) {
			int ablshDt = 0;
			String prdMnthTemp = (String) mstSpecLstArr[3];
			int prdMnth = CommonUtil.stringtoInt(prdMnthTemp
					+ PDConstants.ELEVEN);
			String carSrs = (String) mstSpecLstArr[5];
			String ablshDtTemp = crSrsMp.get(carSrs);
			if (ablshDtTemp != null) {
				ablshDt = CommonUtil.stringtoInt(ablshDtTemp);
			}
			if (crSrsMp.containsKey(carSrs) && ablshDt != 0
					&& ablshDt >= prdMnth) {
				validMstSpec.add(mstSpecLstArr);
			}

		}
		LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);
	}
}
