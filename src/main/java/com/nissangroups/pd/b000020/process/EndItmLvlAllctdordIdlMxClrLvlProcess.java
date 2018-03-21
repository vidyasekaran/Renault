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

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.nissangroups.pd.b000020.output.B000020ParamOutput;
import com.nissangroups.pd.b000020.output.EndItmLvlAllctdordIdlMxOutput;
import com.nissangroups.pd.b000020.output.EndItmOeiByrIdLvlOutput;
import com.nissangroups.pd.b000020.output.ExtractFrcstBaseVolOutput;
import com.nissangroups.pd.repository.CommonRepository;
import com.nissangroups.pd.util.PDConstants;

/**
 * This class is used calculate the Ideal Mix Process for Color Level Orders.
 * 
 * @author z011479
 *
 */
public class EndItmLvlAllctdordIdlMxClrLvlProcess {

	private static final Log LOG = LogFactory
			.getLog(EndItmLvlAllctdordIdlMxClrLvlProcess.class.getName());

	@Autowired(required = false)
	private CommonRepository cmnRep;

	@Autowired(required = false)
	private EndItmLvlAllctdordAvgMxClrLvlProcess endItmLvlAllctdordAvgMxClrLvlProcess;

	@Autowired(required = false)
	private EqualSpltClrLvlProcess equalSpltClrLvlProcess;

	Map<String, List<Object>> endItemOeiByrIdLvlRatio = new HashMap<String, List<Object>>();
	EndItmOeiByrIdLvlOutput endItmOeiByrIdLvlOutput = new EndItmOeiByrIdLvlOutput();
	EndItmLvlAllctdordIdlMxOutput endItmLvlAllctdordIdlMxOutput = new EndItmLvlAllctdordIdlMxOutput();

	boolean idlMxFlg = true;

	/**
	 * P0009.1
	 * 
	 * @param mstSpecDtls
	 * @param byrGrpPrBd
	 * @return
	 * @throws ParseException
	 */
	public EndItmLvlAllctdordIdlMxOutput executeProcess(Object[] mstSpecDtls,
			Map<String, Object[]> byrGrpPrBd,
			B000020ParamOutput b000020ParamOutput, boolean avgMxFlg,
			ExtractFrcstBaseVolOutput extractFrcstBaseVolOutput,
			List<Object[]> mstSpecDtlsLst) throws ParseException {
		LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR);
		Object[] idlMxFrcstVol = cmnRep.extractIdlMxVolClrLvl(mstSpecDtls);
		float extractedIdlMxFrcstVol = 0;
		if (idlMxFrcstVol == null ? false : idlMxFrcstVol[2] == null ? false
				: ((BigDecimal) idlMxFrcstVol[2]).intValue() == 0 ? false
						: true) {
			extractedIdlMxFrcstVol = ((BigDecimal) idlMxFrcstVol[2]).intValue();

			// Ideal Mix Process P00012.1

			float extractedIdlMxFrcstByrGrpCdLvlVol = extractIdlMxFrcstByrGrpCdLvl(mstSpecDtls);
			float calculatedIdlMxRatio = calculateIdlMxRatio(
					extractedIdlMxFrcstVol, extractedIdlMxFrcstByrGrpCdLvlVol);
			float allcOrdQty = calculateAllcOrdQty(calculatedIdlMxRatio, mstSpecDtls);
			// Insert the calculated details to the temp table
			if (Float.floatToRawIntBits(extractedIdlMxFrcstByrGrpCdLvlVol) != 0 && Float.floatToRawIntBits(allcOrdQty) != 0) {
				cmnRep.insertRatioClrLvlDtls(mstSpecDtls, calculatedIdlMxRatio,
						allcOrdQty, PDConstants.IDL_PRCSS_CLR_FLG);
			}
			endItmLvlAllctdordIdlMxOutput
					.setEndItmOeiByrIdLvlOutput(endItmOeiByrIdLvlOutput);

		} else if (!avgMxFlg) {
			endItmLvlAllctdordAvgMxClrLvlProcess.executeProcess(mstSpecDtls,
					byrGrpPrBd, b000020ParamOutput, idlMxFlg,
					extractFrcstBaseVolOutput, mstSpecDtlsLst);
		} else {
			equalSpltClrLvlProcess.executeProcess(mstSpecDtls, 
					b000020ParamOutput, 
					mstSpecDtlsLst);
		}
		LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);
		return endItmLvlAllctdordIdlMxOutput;
	}

	/**
	 * @param mstSpecDtls
	 * @return
	 */
	public int extractIdlMxFrcstByrGrpCdLvl(Object[] mstSpecDtls) {
		return cmnRep.extractIdlMxFrcstByrGrpCdClrLvl(mstSpecDtls);
	}

	/**
	 * This method is used calculate the average Ideal Ratio.
	 * 
	 * @param extractedIdlMxFrcstVol
	 * @param extractedIdlMxFrcstByrGrpCdLvlVol
	 * @return
	 */
	public float calculateIdlMxRatio(float extractedIdlMxFrcstVol,
			float extractedIdlMxFrcstByrGrpCdLvlVol) {
		// Ideal Mix Ratio calculation Process P00012.2
		LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR);
		float idlMxRatio = (extractedIdlMxFrcstVol / extractedIdlMxFrcstByrGrpCdLvlVol) * 100;
		LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);
		return idlMxRatio;
	}

	/**
	 * This method is used to calculate the Allocated Order Quantity.
	 * 
	 * @param calculatedRatio
	 * @param extractFrcstBaseVolOutput
	 * @param b000020ParamOutput
	 * @param mstSpecDtls
	 * @return
	 */
	public float calculateAllcOrdQty(float calculatedRatio, Object[] mstSpecDtls) {
		LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR);
		int allcdOrdQty = 0;
		float allcdOrdQtyTemp = 0;
		int ukOeiByrLvlQty = cmnRep.extractoeiByrIdLvlAllcOrdQty(mstSpecDtls);
		if(ukOeiByrLvlQty == 0){
			return 0;
		}
		allcdOrdQtyTemp = (int) (ukOeiByrLvlQty * calculatedRatio) / 100;
		allcdOrdQty = Math.round(allcdOrdQtyTemp);
		LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);
		return allcdOrdQty;
	}

}
