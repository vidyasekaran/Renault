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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.nissangroups.pd.b000020.output.B000020ParamOutput;
import com.nissangroups.pd.b000020.output.EndItmLvlAllctdordAvgMxOutput;
import com.nissangroups.pd.b000020.output.ExtractFrcstBaseVolOutput;
import com.nissangroups.pd.exception.PdApplicationException;
import com.nissangroups.pd.repository.CommonRepository;

public class UpdateMonthlyOrderProcess {
	private static final Log LOG = LogFactory
			.getLog(UpdateMonthlyOrderProcess.class.getName());

	@Autowired(required = false)
	private CommonRepository cmnRep;

	ExtractFrcstBaseVolOutput extractFrcstBaseVolOutput = new ExtractFrcstBaseVolOutput();
	B000020ParamOutput b000020ParamOutput = new B000020ParamOutput();
	Map<String, String> potCdMp = new HashMap<String, String>();

	/**
	 * @param b000020ParamOutput
	 * @return
	 * @throws PdApplicationException
	 */
	public EndItmLvlAllctdordAvgMxOutput executeProcess(
			B000020ParamOutput b000020ParamOutput) throws PdApplicationException
			 {
		boolean potFlg = true;
		LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR);
		extractAllPotCd();
		List<Object[]> oseiLvlAllcOrdQty = cmnRep.extractOseiLvlAllcOrdQty();
		for (Object[] oseiLvlAllcOrdQtyArr : oseiLvlAllcOrdQty) {
			String porCd = (String) oseiLvlAllcOrdQtyArr[0];
			float oseiLvlAllocOrdrQty = ((BigDecimal) oseiLvlAllcOrdQtyArr[6])
					.intValue();
			LOG.info("oseiLvlAllocOrdrQty" + oseiLvlAllocOrdrQty);
			String potCd = potCdMp.get(porCd);
			if (potCd != null) {
				cmnRep.updateMnthlyOrdr(oseiLvlAllcOrdQtyArr, potCd,
						b000020ParamOutput,potFlg);
			}else{
				potFlg = false;
				cmnRep.updateMnthlyOrdr(oseiLvlAllcOrdQtyArr, potCd,
						b000020ParamOutput,potFlg);
			}
			cmnRep.updateByrMnthlyOcfUsg(oseiLvlAllcOrdQtyArr,
					b000020ParamOutput);
		}
		LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);
		return null;
	}

	public void extractAllPotCd() {
		LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR);
		List<Object[]> potCdLst = cmnRep.extractAllPotCd();
		for (Object[] potCdArry : potCdLst) {
			String porCd = (String) potCdArry[0];
			String potCd = (String) potCdArry[1];
			potCdMp.put(porCd, potCd);
		}
		LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);
	}
}
