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

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.nissangroups.pd.b000020.output.B000020ParamOutput;
import com.nissangroups.pd.exception.PdApplicationException;
import com.nissangroups.pd.repository.CommonRepository;

/**
 * This class is used to initialize the data.
 * @author z011479
 *
 */
public class DataInitializationProcess {
	/** Constant LOG */
	private static final Log LOG = LogFactory
			.getLog(DataInitializationProcess.class.getName());

	Set<String> unqKySet = new HashSet<String>();

	@Autowired(required = false)
	private CommonRepository cmnRep;

	/**
	 * Method is used to Initialize the data in the Transaction tables.
	 * @param mstSpecDtlsLst
	 * @param b000020ParamOutput
	 * @throws PdApplicationException 
	 */
	public void executeProcess(List<Object[]> mstSpecDtlsLst,
			B000020ParamOutput b000020ParamOutput) throws PdApplicationException {
		LOG.info(DOLLAR +INSIDE_METHOD + DOLLAR);
		initializeData(mstSpecDtlsLst, b000020ParamOutput);
		LOG.info(DOLLAR +OUTSIDE_METHOD + DOLLAR);
	}

	/**
	 * Method to initialize the data.
	 * @param mstSpecDtlsLst
	 * @param b000020ParamOutput
	 * @throws PdApplicationException 
	 */
	public void initializeData(List<Object[]> mstSpecDtlsLst,
			B000020ParamOutput b000020ParamOutput) throws PdApplicationException {
		LOG.info(DOLLAR +INSIDE_METHOD + DOLLAR);
		for (Object[] mstSpecDtlsLstArr : mstSpecDtlsLst) {
			initializeMnthlyOrdTrn(mstSpecDtlsLstArr, b000020ParamOutput);
			initializeByrMnthlyOcfUsg(mstSpecDtlsLstArr, b000020ParamOutput);
			initializeByrGrpMnthlyOcfUsg(mstSpecDtlsLstArr, b000020ParamOutput);
		}
		LOG.info(DOLLAR +OUTSIDE_METHOD + DOLLAR);
	}

	/**
	 * This method is used to Initialize the Monthly Order TRN table.
	 * @param mstSpecDtlsLstArr
	 * @param b000020ParamOutput
	 * @throws PdApplicationException 
	 */
	public void initializeMnthlyOrdTrn(Object[] mstSpecDtlsLstArr,
			B000020ParamOutput b000020ParamOutput) throws PdApplicationException {
		LOG.info(DOLLAR +INSIDE_METHOD + DOLLAR);
		cmnRep.initializeMnthlyOrdrTrn(mstSpecDtlsLstArr, b000020ParamOutput);
		LOG.info(DOLLAR +OUTSIDE_METHOD + DOLLAR);
	}

	public void initializeByrMnthlyOcfUsg(Object[] mstSpecDtlsLstArr,
			B000020ParamOutput b000020ParamOutput) throws PdApplicationException {
		LOG.info(DOLLAR +INSIDE_METHOD + DOLLAR);
		String porCd = (String) mstSpecDtlsLstArr[0];
		String prdMnth = (String) mstSpecDtlsLstArr[3];
		String oseiId = (String) mstSpecDtlsLstArr[1];
		String ordrTkBsMnth = b000020ParamOutput.getOrdTkBsMnth();
		String unqKy = porCd + prdMnth + oseiId + ordrTkBsMnth;
		if (unqKySet.add(unqKy)) {
			cmnRep.initializeByrMnthlyOcfUsg(mstSpecDtlsLstArr,
					b000020ParamOutput);
		}
		LOG.info(DOLLAR +OUTSIDE_METHOD + DOLLAR);
	}

	/**
	 * This method is used to initialize the Buyer Group Monthly usage Qty.
	 * @param mstSpecDtlsLstArr
	 * @param b000020ParamOutput
	 * @throws PdApplicationException 
	 */
	public void initializeByrGrpMnthlyOcfUsg(Object[] mstSpecDtlsLstArr,
			B000020ParamOutput b000020ParamOutput) throws PdApplicationException {
		LOG.info(DOLLAR +INSIDE_METHOD + DOLLAR);
		String porCd = (String) mstSpecDtlsLstArr[0];
		String prdMnth = (String) mstSpecDtlsLstArr[3];
		String carSrs = (String) mstSpecDtlsLstArr[5];
		String byrGrpCd = (String) mstSpecDtlsLstArr[4];
		String ordrTkBsMnth = b000020ParamOutput.getOrdTkBsMnth();
		String unqKy = porCd + prdMnth + carSrs + byrGrpCd+ordrTkBsMnth;
		if (unqKySet.add(unqKy)) {
			cmnRep.initializeByrGrpMnthlyOcfUsg(mstSpecDtlsLstArr,
					b000020ParamOutput);
		}
		LOG.info(DOLLAR +OUTSIDE_METHOD + DOLLAR);
	}

}
