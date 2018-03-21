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

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.nissangroups.pd.b000020.output.B000020ParamOutput;
import com.nissangroups.pd.b000020.output.ExtractByrGrpAndCrSrsOutput;
import com.nissangroups.pd.exception.PdApplicationException;
import com.nissangroups.pd.model.MstMnthOrdrTakeBasePd;
import com.nissangroups.pd.repository.CommonRepository;
import com.nissangroups.pd.util.CommonUtil;
import com.nissangroups.pd.util.PDConstants;
import com.nissangroups.pd.util.PDMessageConsants;

/**
 * This class is used as Extract Buyer Group And Car Series Process.
 * 
 * @author z011479
 *
 */
public class ExtractByrGrpAndCrSrsProcess {

	/** Constant LOG */
	private static final Log LOG = LogFactory
			.getLog(ExtractByrGrpAndCrSrsProcess.class.getName());

	@Autowired(required = false)
	private CommonRepository cmnRep;

	List<Object[]> byrGrp = new ArrayList<Object[]>();
	List<Object[]> crSrs = new ArrayList<Object[]>();

	MstMnthOrdrTakeBasePd mstMnthOrdrTakeBasePd = new MstMnthOrdrTakeBasePd();
	ExtractByrGrpAndCrSrsOutput extractByrGrpAndCrSrsOutput = new ExtractByrGrpAndCrSrsOutput();
	
	public ExtractByrGrpAndCrSrsProcess() {
	}

	/**
	 * This method is used to execute process.
	 * 
	 * @param paramOutput
	 * @return
	 * @throws ParseException
	 * @throws IOException 
	 * @throws PdApplicationException 
	 */
	public ExtractByrGrpAndCrSrsOutput executeProcess(
			B000020ParamOutput paramOutput) throws ParseException, PdApplicationException {
		
		String forPorCdStr = " FOR POR_CD: ";
		LOG.info(DOLLAR +INSIDE_METHOD + DOLLAR);
		// Process P0002
		byrGrp = cmnRep.getByrGrp(paramOutput);
		if(byrGrp.isEmpty()){
		
			CommonUtil.logMessage(PDMessageConsants.M00169,
					PDConstants.CONSTANT_V3, new String[] {
							PDConstants.BATCH_20_ID ,
						PDConstants.BUYER_GROUP_CD + forPorCdStr +paramOutput.getPorCd(),PDConstants.MST_BUYER });
			throw new PdApplicationException(PDConstants.NO_DATA_FOUND);
		}
		// Process P0003
		crSrs = cmnRep.getVldCrSrs(paramOutput);
		if(crSrs.isEmpty()){
			CommonUtil.logMessage(PDMessageConsants.M00169,
					PDConstants.CONSTANT_V3, new String[] {
							PDConstants.BATCH_20_ID ,
						PDConstants.CAR_SERIES + forPorCdStr+paramOutput.getPorCd(),PDConstants.MESSAGE_POR_CAR_SERIES_MST });
			throw new PdApplicationException(PDConstants.NO_DATA_FOUND);
		}
		extractByrGrpAndCrSrsOutput.setByrGrp(byrGrp);
		extractByrGrpAndCrSrsOutput.setCrSrs(crSrs);
		LOG.info(DOLLAR +OUTSIDE_METHOD + DOLLAR);
		return extractByrGrpAndCrSrsOutput;
	}

}
