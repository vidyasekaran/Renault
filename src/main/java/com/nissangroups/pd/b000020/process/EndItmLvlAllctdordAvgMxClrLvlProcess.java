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
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.nissangroups.pd.b000020.output.B000020ParamOutput;
import com.nissangroups.pd.b000020.output.EndItmLvlAllctdordAvgMxOutput;
import com.nissangroups.pd.b000020.output.ExtractFrcstBaseVolOutput;
import com.nissangroups.pd.model.DevB000020;
import com.nissangroups.pd.repository.CommonRepository;
import com.nissangroups.pd.util.PDConstants;

/**
 * This class is used to Calculate the Average Mix Ratio for color level orders.
 * 
 * @author z011479
 *
 */
public class EndItmLvlAllctdordAvgMxClrLvlProcess {
	/** Constant LOG */
	private static final Log LOG = LogFactory
			.getLog(EndItmLvlAllctdordAvgMxClrLvlProcess.class.getName());

	@Autowired(required = false)
	private CommonRepository cmnRep;

	@Autowired(required = false)
	private EndItmLvlAllctdordIdlMxClrLvlProcess endItmLvlAllctdordIdlMxClrLvlProcess;

	@Autowired(required = false)
	private EqualSpltClrLvlProcess equalSpltClrLvlProcess;

	boolean avgMxFlg = true;

	DevB000020 devB000020 = new DevB000020();

	/**
	 * This method is used to calculate the End Item Level Average Mix Ratio.
	 * 
	 * @param mstSpecDtls
	 * @param byrGrpPrBd
	 * @param b000020ParamOutput
	 * @param idlMxFlg
	 * @param extractFrcstBaseVolOutput
	 * @return EndItmLvlAllctdordAvgMxOutput
	 * @throws ParseException
	 */
	public EndItmLvlAllctdordAvgMxOutput executeProcess(Object[] mstSpecDtls,
			Map<String, Object[]> byrGrpPrBd,
			B000020ParamOutput b000020ParamOutput, boolean idlMxFlg,
			ExtractFrcstBaseVolOutput extractFrcstBaseVolOutput,
			List<Object[]> mstSpecDtlsLst) throws ParseException {
		LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR);
		// P00010.1 Extract the Summarized the N-N+2 Production Month EI Order
		// Qty
		Object[] nMonthOrdrVol = cmnRep.extractNMnthprdMnthQtyClrLevel(
				mstSpecDtls, b000020ParamOutput);

		if (nMonthOrdrVol == null ? false : nMonthOrdrVol[0] == null ? false
				: ((BigDecimal) nMonthOrdrVol[0]).intValue() == 0 ? false
						: true) {

			float nMonthOrdrQty = ((BigDecimal) nMonthOrdrVol[0]).intValue();
			float extractednMonthOrdrByrGrpCdLvlVol = extractMonthOrdrByrGrpCdLvlVol(
					mstSpecDtls, b000020ParamOutput);
			float calculatedAvgMxRatio = calculateIdlMxRatio(nMonthOrdrQty,
					extractednMonthOrdrByrGrpCdLvlVol);
			float allcOrdQty = calculateAllcOrdQty(calculatedAvgMxRatio,
					mstSpecDtls);
			// Insert the calculated details to the temp table
			if (Float.floatToRawIntBits(extractednMonthOrdrByrGrpCdLvlVol) != 0 && Float.floatToRawIntBits(allcOrdQty) != 0) {
				cmnRep.insertRatioClrLvlDtls(mstSpecDtls, calculatedAvgMxRatio,
						allcOrdQty, PDConstants.AVG_PRCSS_CLR_FLG);
			}

		} else if (!idlMxFlg) {
			endItmLvlAllctdordIdlMxClrLvlProcess.executeProcess(mstSpecDtls,
					byrGrpPrBd, b000020ParamOutput, avgMxFlg,
					extractFrcstBaseVolOutput, mstSpecDtlsLst);

		} else {
			equalSpltClrLvlProcess.executeProcess(mstSpecDtls, 
					b000020ParamOutput, 
					mstSpecDtlsLst);
		}
		LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);
		return null;
	}

	/**
	 * This method is used to extract the Monthly Order Count details at Buyer
	 * Group Code level
	 * 
	 * @param mstSpecDtls
	 * @return
	 * @throws ParseException
	 */
	public int extractMonthOrdrByrGrpCdLvlVol(Object[] mstSpecDtls,
			B000020ParamOutput b000020ParamOutput) throws ParseException {
		LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR);
		Object[] sumOfOrdBuyrCdlvl = cmnRep
				.extractNMnthprdMnthClrAndOeiByrIdLvlQty(mstSpecDtls,
						b000020ParamOutput);
		LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);
		return ((BigDecimal) sumOfOrdBuyrCdlvl[0]).intValue();
	}

	/**
	 * Method is used to calculate the Ideal Mix Ratio.
	 * 
	 * @param extractedIdlMxFrcstVol
	 * @param extractedIdlMxFrcstByrGrpCdLvlVol
	 * @return
	 */
	public float calculateIdlMxRatio(float nMonthOrdrQty,
			float extractednMonthOrdrByrGrpCdLvlVol) {
		LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR);
		// Ideal Mix Ratio calculation Process P0009.2
		float idlMxRatio = (nMonthOrdrQty / extractednMonthOrdrByrGrpCdLvlVol) * 100;
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
	 * @return float
	 */
	public float calculateAllcOrdQty(float calculatedRatio, Object[] mstSpecDtls) {
		LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR);
		int allcdOrdQty = 0;
		float allcdOrdQtyTemp = 0;
		float frcstVol = cmnRep.extractoeiByrIdLvlAllcOrdQty(mstSpecDtls);
		if (Float.floatToRawIntBits(frcstVol) == 0) {
			return 0;
		}
		allcdOrdQtyTemp = (int) (frcstVol * calculatedRatio) / 100;
		allcdOrdQty = Math.round(allcdOrdQtyTemp);
		LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);
		return allcdOrdQty;
	}

}
