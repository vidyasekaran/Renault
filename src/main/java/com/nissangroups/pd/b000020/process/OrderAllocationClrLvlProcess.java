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
import static com.nissangroups.pd.util.PDConstants.OUTSIDE_METHOD;
import static com.nissangroups.pd.util.PDConstants.INSIDE_METHOD;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.nissangroups.pd.b000020.output.B000020ParamOutput;
import com.nissangroups.pd.b000020.output.EndItmLvlAllctdordAvgMxOutput;
import com.nissangroups.pd.b000020.output.ExtractFrcstBaseVolOutput;
import com.nissangroups.pd.repository.CommonRepository;

/**
 * This class is used to allocate orders based on the OCF limit.
 * 
 * @author z011479
 *
 */
public class OrderAllocationClrLvlProcess {
	private static final Log LOG = LogFactory
			.getLog(OrderAllocationClrLvlProcess.class.getName());

	@Autowired(required = false)
	private CommonRepository cmnRep;

	ExtractFrcstBaseVolOutput extractFrcstBaseVolOutput = new ExtractFrcstBaseVolOutput();
	B000020ParamOutput b000020ParamOutput = new B000020ParamOutput();
	List<Object[]> result = new ArrayList<Object[]>();

	/**
	 * @param mstSpecDtls
	 * @param byrGrpPrBd
	 * @param idlMxFlg
	 * @param extractFrcstBaseVolOutput
	 * @return EndItmLvlAllctdordAvgMxOutput
	 */
	public EndItmLvlAllctdordAvgMxOutput executeProcess() {
		LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR);
		List<Object[]> oseiIdLst = cmnRep.extractAllOseiIdFrmTempTbl();
		reAllocateOrder(oseiIdLst);
		LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);
		return null;
	}

	/**
	 * This method is used to reallocate the order.
	 * @param b000020ParamOutput
	 * @param byrGrpLst
	 */
	public void reAllocateOrder(List<Object[]> byrGrpLst) {
		LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR);
		for (Object[] objArry : byrGrpLst) {
			float oseiLvlAllocOrdrQty = ((BigDecimal) objArry[5]).intValue();
			Object[] oeiByrIdLvlQty = cmnRep.extractOeiByrIdLvlAllcQty(objArry);
			float oeiByrIdLvlAllcOrdr = ((BigDecimal) oeiByrIdLvlQty[0])
					.intValue();
			float ordrDiff = oeiByrIdLvlAllcOrdr - oseiLvlAllocOrdrQty;
			List<Object[]> calculatedOrdQty = cmnRep
					.extractAllcordrQtyOeiByrIdLVl(objArry);
			reallocateOrders(ordrDiff, calculatedOrdQty);

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
	public void reallocateOrders(float ordrDiff,
			List<Object[]> calculatedOrdQty) {
		LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR);
		boolean flag = true;
		int ordrDiffTemp = (int) ordrDiff;
		int ordrDiffTempNw = (int) ordrDiff;
		
		for (Object[] alloctdQtyaa : calculatedOrdQty) {
			BigDecimal allcOrdQty = (BigDecimal) alloctdQtyaa[4];
			LOG.info(" Befor Process " + allcOrdQty);
		}
		while (ordrDiffTemp != 0) {
			if (ordrDiffTemp > 0) {
				for (Object[] alloctdQty : calculatedOrdQty) {
					if (ordrDiffTempNw == 0) {
						break;
					} else {
						BigDecimal allcOrdQty = (BigDecimal) alloctdQty[4];
						int allcOrdQtyTemp = ((BigDecimal) alloctdQty[4])
								.intValue();
						if (allcOrdQtyTemp != 0 || flag) {
							BigDecimal one = new BigDecimal(1);
							alloctdQty[4] = allcOrdQty.add(one);
							ordrDiffTempNw = --ordrDiffTemp;
							flag = false;
						}
					}

				}
			} else if (ordrDiffTemp < 0) {
				for (Object[] alloctdQty : calculatedOrdQty) {
					if (ordrDiffTempNw == 0) {
						break;
					} else {
						BigDecimal allcOrdQty = (BigDecimal) alloctdQty[4];
						int allcOrdQtyTemp = ((BigDecimal) alloctdQty[4])
								.intValue();
						if (allcOrdQtyTemp != 0 || flag) {
							BigDecimal one = new BigDecimal(-1);
							alloctdQty[4] = allcOrdQty.add(one);
							ordrDiffTempNw = 	++ordrDiffTemp;
							flag = false;
						}
					}

				}
			}

		}
		cmnRep.insertListOseiIdNewOrdrDtls(calculatedOrdQty);
		for (Object[] afterAllocArr : calculatedOrdQty) {
			BigDecimal allcOrdQty = (BigDecimal) afterAllocArr[4];
			LOG.info(" Befor After " + allcOrdQty);
			result.add(afterAllocArr);
		}
		LOG.info("ordrDiff " + ordrDiff);
		LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);

	}
}
