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

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.nissangroups.pd.b000020.output.B000020ParamOutput;
import com.nissangroups.pd.b000020.output.ExtractMstSpecOutput;
import com.nissangroups.pd.exception.PdApplicationException;
import com.nissangroups.pd.repository.CommonRepository;
import com.nissangroups.pd.util.CommonUtil;
import com.nissangroups.pd.util.PDConstants;
import com.nissangroups.pd.util.PDMessageConsants;

/**
 * This class is used to extract the Master Spec Data.
 * 
 * @author z011479
 *
 */
public class ExtractMstSpecProcess {

	/** Constant LOG. */
	private static final Log LOG = LogFactory
			.getLog(ExtractMstSpecProcess.class.getName());

	List<Object[]> mstSpedDtls = new ArrayList<Object[]>();
	Set<String> uniqueCarSrs = new HashSet<>();
	Set<Object[]> byrGrpCd = new HashSet<>();
	
	@Autowired(required = false)
	private CommonRepository cmnRep;

	/**
	 * @param paramOutput
	 * @return
	 * @throws ParseException
	 * @throws PdApplicationException
	 */
	public ExtractMstSpecOutput executeProcess(B000020ParamOutput paramOutput)
			throws ParseException,PdApplicationException {
		LOG.info(DOLLAR +INSIDE_METHOD + DOLLAR);
		String porCdStrVal = " FOR POR_CD: ";
		ExtractMstSpecOutput extractMstSpecOutput = new ExtractMstSpecOutput();
		mstSpedDtls = cmnRep.getSpecMstDtls(paramOutput);
		if(mstSpedDtls.isEmpty()){
			CommonUtil.logMessage(PDMessageConsants.M00169,
					PDConstants.CONSTANT_V3, new String[] {
							PDConstants.BATCH_20_ID ,
						PDConstants.CAR_SERIES + porCdStrVal +paramOutput.getPorCd(),PDConstants.MESSAGE_MASTER_END_ITEM_SPEC });
			throw new PdApplicationException(PDConstants.NO_DATA_FOUND);
		}
		extractMstSpecOutput.setMstSpecDtls(mstSpedDtls);
		extractMstSpecOutput.setMstSpecDtlsAll(mstSpedDtls);
		LOG.info(DOLLAR +OUTSIDE_METHOD + DOLLAR);
		return extractMstSpecOutput;
	}

	public void setUniqueCarSrs() {
		LOG.info(DOLLAR +INSIDE_METHOD + DOLLAR);
		for (Object[] mstSpedDtlsArry : mstSpedDtls) {
			byrGrpCd.add((Object[]) mstSpedDtlsArry[5]);
		}
		LOG.info(DOLLAR +OUTSIDE_METHOD + DOLLAR);
	}
}
