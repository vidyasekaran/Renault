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
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.nissangroups.pd.b000020.output.B000020ParamOutput;
import com.nissangroups.pd.b000020.output.EndItmLvlAllctdordAvgMxOutput;
import com.nissangroups.pd.b000020.output.ExtractFrcstBaseVolOutput;
import com.nissangroups.pd.repository.CommonRepository;
import com.nissangroups.pd.util.PDConstants;

/**
 * This class is used to allocate orders based on the OCF limit.
 * 
 * @author z011479
 *
 */
public class OrderAllocationProcess {
	private static final Log LOG = LogFactory
			.getLog(OrderAllocationProcess.class.getName());

	@Autowired(required = false)
	private CommonRepository cmnRep;

	ExtractFrcstBaseVolOutput extractFrcstBaseVolOutput = new ExtractFrcstBaseVolOutput();
	B000020ParamOutput b000020ParamOutput = new B000020ParamOutput();

	/**
	 * Method to do the Average Mix process at EI Leevel.
	 * 
	 * @param mstSpecDtls
	 * @param byrGrpPrBd
	 * @param b000020ParamOutput
	 * @param idlMxFlg
	 * @param extractFrcstBaseVolOutput
	 * @return EndItmLvlAllctdordAvgMxOutput
	 */
	public EndItmLvlAllctdordAvgMxOutput executeProcess(
			ExtractFrcstBaseVolOutput extractFrcstBaseVolOutput,
			B000020ParamOutput b000020ParamOutput) {
		LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR);
		List<Object[]> byrGrpLst = cmnRep.extractAllByrGrpFrmTempTbl();
		reAllocateOrder(extractFrcstBaseVolOutput, b000020ParamOutput,
				byrGrpLst);
		LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);
		return null;
	}

	/**
	 * This method is used to reallocate the order.
	 */
	public void reAllocateOrder(
			ExtractFrcstBaseVolOutput extractFrcstBaseVolOutput,
			B000020ParamOutput b000020ParamOutput, List<Object[]> byrGrpLst) {
		LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR);
		Map<String, Object[]> nscFrcstVolMap = extractFrcstBaseVolOutput
				.getNscFrcstVol();
		Map<String, Object[]> nscByrGrpOcfLmt = extractFrcstBaseVolOutput
				.getNscByrGrpOcfLmt();

		for (Object[] objArry : byrGrpLst) {

			String porCd = (String) objArry[0];
			String prdMnth = (String) objArry[1];
			String byrGrpCd = (String) objArry[3];
			String crSrs = (String) objArry[2];
			String ordTkBsMnth = b000020ParamOutput.getOrdTkBsMnth();
			float allocOrdrQty = ((BigDecimal) objArry[4]).intValue();

			if (b000020ParamOutput.getProductionStageCd().equalsIgnoreCase(
					PDConstants.TEN)) {
				String uniqkey = porCd + prdMnth + byrGrpCd + crSrs
						+ ordTkBsMnth;
				List<Object[]> calculatedOrdQty = cmnRep
						.extractAllcordrQtyByrGrpCdLVl(objArry);
				Object[] nscFrcstVolArry = nscFrcstVolMap.get(uniqkey);

				float frcstVol = ((BigDecimal) nscFrcstVolArry[5]).intValue();
				float ordrDiff = frcstVol - allocOrdrQty;
				reallocateOrders(ordrDiff, calculatedOrdQty);

			} else if (b000020ParamOutput.getProductionStageCd()
					.equalsIgnoreCase(PDConstants.TWENTY)) {
				String uniqkey = porCd + prdMnth + byrGrpCd + crSrs;
				List<Object[]> calculatedOrdQty = cmnRep
						.extractAllcordrQtyByrGrpCdLVl(objArry);
				Object[] byrGrpOcfLmtArry = nscByrGrpOcfLmt.get(uniqkey);
				if (byrGrpOcfLmtArry != null) {
					float nscByrGrpOcfLmtVol = ((BigDecimal) byrGrpOcfLmtArry[4])
							.intValue();
					float ordrDiff = nscByrGrpOcfLmtVol - allocOrdrQty;
					reallocateOrders(ordrDiff, calculatedOrdQty);
				}

			}
		}
		LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);
	}

	/**
	 * This method is used reallocate the Order Quantity Based OCF availability.
	 * 
	 * @param ordrDiff
	 * @param calculatedOrdQty
	 * @param byrGrpDtld
	 */
	public void reallocateOrders(float ordrDiff, List<Object[]> calculatedOrdQty) {
		LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR);
		int ordrDiffTemp = (int) ordrDiff;
		int ordrDiffTempNw = (int) ordrDiff;
		boolean flag = false;
		while (ordrDiffTemp != 0) {
			if (ordrDiffTemp > 0) {
				for (Object[] alloctdQty : calculatedOrdQty) {
					// To increase the order quantity one by one.
					if (ordrDiffTempNw == 0) {
						break;
					} else {
						BigDecimal allcOrdQty = (BigDecimal) alloctdQty[4];
						int allcOrdQtyTemp = ((BigDecimal) alloctdQty[4])
								.intValue();
						if (allcOrdQtyTemp != 0 || flag == false) {
							BigDecimal one = new BigDecimal(1);
							alloctdQty[4] = allcOrdQty.add(one);
							ordrDiffTempNw = --ordrDiffTemp;
							flag = true;
						}
					}
				}
			} else if (ordrDiffTemp < 0) {
				// To decrease the order quantity one by on
				for (Object[] alloctdQty : calculatedOrdQty) {
					if (ordrDiffTempNw == 0) {
						break;
					} else {
						BigDecimal allcOrdQty = (BigDecimal) alloctdQty[4];
						int allcOrdQtyTemp = ((BigDecimal) alloctdQty[4])
								.intValue();
						if (allcOrdQtyTemp != 0 || flag == false) {
							BigDecimal one = new BigDecimal(-1);
							alloctdQty[4] = allcOrdQty.add(one);
							ordrDiffTempNw = ++ordrDiffTemp;
							flag = true;
						}
					}

				}
			}

		}
		cmnRep.insertListNewOrdrDtls(calculatedOrdQty);
		LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);
	}

}
