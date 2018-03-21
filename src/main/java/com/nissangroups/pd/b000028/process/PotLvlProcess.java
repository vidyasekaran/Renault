/*
 * System Name     :Post Dragon 
 * Sub system Name :Batch
 * Function ID     :PST-DRG-B000028
 * Module          :OR Ordering					
 * Process Outline :Automatic_order_adjustment_to_OCF_Limit
 *
 * <Revision History>
 * Date       Name(Company Name)             Description 
 * ---------- ------------------------------ ---------------------
 * 24-11-2015  	  z015060(RNTBCI)               New Creation
 *
 */
package com.nissangroups.pd.b000028.process;

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

import com.nissangroups.pd.b000028.output.B000028Output;
import com.nissangroups.pd.b000028.util.B000028Constants;
import com.nissangroups.pd.repository.SpecRepository;
import com.nissangroups.pd.util.CommonUtil;
import com.nissangroups.pd.util.PDConstants;
import com.nissangroups.pd.util.PDMessageConsants;

/**
 * The Class OverLapPeriodProcess.
 *
 * @author z015060
 */
public class PotLvlProcess {

	/** Constant LOG */
	private static final Log LOG = LogFactory.getLog(PotLvlProcess.class
			.getName());

	Map<String, String> potLvlMap = new HashMap<String, String>();
	Map<String,  Integer> potLvlCountMap = new HashMap<String,  Integer>();
	Map<String, String> clrLvlMap = new HashMap<String, String>();

	@Autowired(required = false)
	private SpecRepository specRep;

	/** Constructor with PotLvlProcess and entityManagerFac parameters */
	public PotLvlProcess() {
	}

	public B000028Output executeProcess(B000028Output object) {
		LOG.info("PotLvlProcess");

		object.setPotLvl(specRep.getPotLvl(object));
		addDataTogetPotLvlMap(object.getPotLvl());
		addDataClrLvlMap(object);

		try{
		for (Object[] potLvl : object.getPotLvl()) {
			String prdMnth = potLvl[2].toString();
			String carSrs = potLvl[1].toString();
			String byrGrpCd = potLvl[4].toString();
			String byrCd = potLvl[5].toString();
			String oeiByrId = potLvl[6].toString();
			String oseiId = potLvl[7].toString();
			String potCd = potLvl[8].toString();
			float ratio = 0;
			float potOrdr = Integer.parseInt(potLvlMap.get(carSrs + prdMnth
					+ byrGrpCd + byrCd + oeiByrId + oseiId + potCd));
			float sumClrLvl = Integer.parseInt(clrLvlMap.get(carSrs + prdMnth
					+ byrGrpCd + byrCd + oeiByrId + oseiId));
			float clrLvlMnthlyTrn= Integer.parseInt(object.getClrLvlMap().get(carSrs + prdMnth
					+ byrGrpCd + byrCd+  oeiByrId + oseiId));
			float potLvlTrn= potLvlCountMap.get(carSrs + prdMnth
					+ byrGrpCd + byrCd+  oeiByrId + oseiId);
			if (Float.floatToRawIntBits(potOrdr) == 0) {
				ratio= (1/potLvlTrn)*100;
			} else {
				ratio = (potOrdr / clrLvlMnthlyTrn) * 100;
			}
			float allocation = (ratio / 100) * sumClrLvl;
			float allocationRound = Math.round(allocation);
			int flag = 7;
			specRep.insertTempAutoAdjust(object.getObjB000028ParamOutput()
					.getPorCd(), potLvl, Math.round(ratio), allocationRound, flag);
		}
		}catch(Exception e){
			LOG.info(e);
			CommonUtil.logMessage(PDMessageConsants.M00076,
					PDConstants.CONSTANT_V1, new String[] {
							B000028Constants.BATCH_ID_B000028 });
		}
		reAllocatedPortionPotLvl(object);
		return object;

	}

	private void reAllocatedPortionPotLvl(B000028Output object) {

		for (Object[] temp : object.getClrLvl()) {
			String prdMnth = temp[2].toString();
			String carSrs = temp[1].toString();
			String byrGrpCd = temp[4].toString();
			String byrCd = temp[5].toString();
			String oeiByrId = temp[6].toString();
			String oseiId = temp[7].toString();
			float sumByrCd = Integer.parseInt(clrLvlMap.get(carSrs + prdMnth
					+ byrGrpCd + byrCd + oeiByrId + oseiId));
			List<Object[]> getDatafrmTemp = specRep
					.getdataPotLvlAutoAlloc(temp);
			float roundedAlloc = Float.parseFloat(specRep
					.getRoundedAllocPotLvl(temp));
			float diff = sumByrCd - roundedAlloc;
			reallocateOrdersPotLvl(diff, getDatafrmTemp);

		}

	}

	public void reallocateOrdersPotLvl(float ordrDiff,
			List<Object[]> calculatedOrdQty) {
		LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR);
		int ordrDiffTemp = (int) ordrDiff;
		int ordrDiffTempNw = (int) ordrDiff;
		boolean flag = false;
		if (ordrDiffTemp != 0) {
			if (ordrDiffTemp > 0) {
				for (Object[] alloctdQty : calculatedOrdQty) {
					// To increase the order quantity one by one.
					if (ordrDiffTempNw == 0) {
						break;
					} else {
						BigDecimal allcOrdQty = (BigDecimal) alloctdQty[8];
						int allcOrdQtyTemp = ((BigDecimal) alloctdQty[8])
								.intValue();
						if (allcOrdQtyTemp != 0 || flag == false) {
							BigDecimal one = new BigDecimal(1);
							alloctdQty[8] = allcOrdQty.add(one);
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
						BigDecimal allcOrdQty = (BigDecimal) alloctdQty[8];
						int allcOrdQtyTemp = ((BigDecimal) alloctdQty[8])
								.intValue();
						if (allcOrdQtyTemp != 0 || flag == false) {
							BigDecimal one = new BigDecimal(-1);
							alloctdQty[8] = allcOrdQty.add(one);
							ordrDiffTempNw = ++ordrDiffTemp;
							flag = true;
						}
					}

				}
			}
		}
		specRep.insertListNewOrdrDtls(calculatedOrdQty, 8);
		LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);
	}

	private void addDataClrLvlMap(B000028Output object) {
		List<Object[]> clrLvlLst = specRep.getClrLvlAct(object);
		// POR_CD,CAR_SRS,PROD_MNTH,BUYER_GRP_CD,BUYER_CD,OEI_BUYER_ID,OSEI_ID,ALLOCATION
		for (Object[] temp : clrLvlLst) {
			String prdMnth = temp[2].toString();
			String carSrs = temp[1].toString();
			String byrGrpCd = temp[3].toString();
			String byrCd = temp[4].toString();
			String oeiByrId = temp[5].toString();
			String oseiId = temp[6].toString();
			clrLvlMap.put(carSrs + prdMnth + byrGrpCd + byrCd + oeiByrId
					+ oseiId, temp[7].toString());
		}

	}

	private void addDataTogetPotLvlMap(List<Object[]> potLvl) {
		for (Object[] temp : potLvl) {
			// select
			// tn.por_Cd,mp.CAR_SRS,tn.PROD_MNTH,sum(tn.ORDR_QTY),mbb.BUYER_GRP_CD,mbb.BUYER_CD,mb.OEI_BUYER_ID,mo.OSEI_ID
			String prdMnth = temp[2].toString();
			String carSrs = temp[1].toString();
			String byrGrpCd = temp[4].toString();
			String byrCd = temp[5].toString();
			String oeiByrId = temp[6].toString();
			String oseiId = temp[7].toString();
			String potCd = temp[8].toString();
			potLvlMap.put(carSrs + prdMnth + byrGrpCd + byrCd + oeiByrId
					+ oseiId + potCd, temp[3].toString());
			if(potLvlCountMap.get(carSrs + prdMnth + byrGrpCd + byrCd + oeiByrId
					+ oseiId) != null){
				int i=potLvlCountMap.get(carSrs + prdMnth + byrGrpCd + byrCd + oeiByrId
						+ oseiId)+1;
				potLvlCountMap.put(carSrs + prdMnth + byrGrpCd + byrCd + oeiByrId
						+ oseiId,i);
			}else {
				potLvlCountMap.put(carSrs + prdMnth + byrGrpCd + byrCd + oeiByrId
						+ oseiId,1);
			}
		}

	}

}
