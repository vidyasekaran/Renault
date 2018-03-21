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
 * The Class EndItmLvlProcess.
 *
 * @author z015060
 */
public class EndItmLvlProcess {

	@Autowired(required = false)
	private SpecRepository specRep;

	private static final Log LOG = LogFactory.getLog(ByrGrpLvlProcess.class
			.getName());
	Map<String, String> oeiByrIdLvlMap = new HashMap<String, String>();
	Map<String, String> byrCdLvlMap = new HashMap<String, String>();
	Map<String, Integer> byrCdCountMap = new HashMap<String, Integer>();
	Map<String, String> byrCdMnthTrnLvlMap = new HashMap<String, String>();

	/** Constructor of EndItmLvlProcess */
	public EndItmLvlProcess() {
	}

	public B000028Output executeProcess(B000028Output object) {

		object.setOeiBuyerId(specRep.getOeiBuyerId(object));
		addDataTogetOEIByrIdLvlMap(object.getOeiBuyerId());
		addDataTogetByrCdMap();
		addDataTogetByrCdMnthTrnMap(object);
		object.setOeiByrIdLvlMap(oeiByrIdLvlMap);

		try{
		for (Object[] oeiLvl : object.getOeiBuyerId()) {
			String prdMnth = oeiLvl[2].toString();
			String carSrs = oeiLvl[1].toString();
			String byrGrp = oeiLvl[4].toString();
			String byrCd = oeiLvl[5].toString();
			String oeiByrId = oeiLvl[6].toString();
			float ratio = 0;
			float oeiOrdr = Integer.parseInt(oeiByrIdLvlMap.get(carSrs
					+ prdMnth + byrGrp + byrCd + oeiByrId));
			float sumByrCd = Integer.parseInt(byrCdLvlMap.get(carSrs + prdMnth
					+ byrGrp + byrCd));
			float totalByrCd=Integer.parseInt(byrCdMnthTrnLvlMap.get(carSrs + prdMnth
					+ byrGrp + byrCd));
			float byrCdCount = byrCdCountMap.get(carSrs + prdMnth + byrGrp + byrCd);
			//byrCdCountMap
			LOG.info(oeiOrdr + "*************" + sumByrCd);
			if (Float.floatToRawIntBits(oeiOrdr) == 0) {
				ratio=(1/byrCdCount)*100;
			} else {
				ratio=(oeiOrdr/totalByrCd)*100;
			}
			LOG.info(oeiOrdr +"TOTAL>>>>>>"+totalByrCd+"SUM*************"+sumByrCd+ "Ratio*************" + ratio);
			float allocation = (ratio/100)*sumByrCd;
			float allocationRound = Math.round(allocation);
			LOG.info("allocationRound----------------->"+allocationRound);
			int flag=3;
			specRep.insertTempAutoAdjust(object
					.getObjB000028ParamOutput().getPorCd(),oeiLvl,Math.round(ratio),allocationRound,flag);
		}
		}catch(Exception e){
			LOG.info(e);
			CommonUtil.logMessage(PDMessageConsants.M00076,
					PDConstants.CONSTANT_V1, new String[] {
							B000028Constants.BATCH_ID_B000028 });
		}
		reAllocatedPortionEndItmLvl(object);

		return object;
	}

	private void addDataTogetByrCdMnthTrnMap(B000028Output object) {
		List<Object[]> byrCdLst = object.getByrCdLvl();
		for (Object[] temp : byrCdLst) {
			String prdMnth = temp[2].toString();
			String carSrs = temp[1].toString();
			String byrGrp = temp[4].toString();
			String byrCd = temp[5].toString();
			byrCdMnthTrnLvlMap.put(carSrs + prdMnth + byrGrp + byrCd,
					temp[3].toString());
		}
		
	}

	private void reAllocatedPortionEndItmLvl(B000028Output object) {
		
		for(Object[] temp: object.getByrCdLvl()){
			String prdMnth = temp[2].toString();
			String carSrs = temp[1].toString();
			String byrGrp = temp[4].toString();
			String byrCd = temp[5].toString();
			float sumByrCd = Integer.parseInt(byrCdLvlMap.get(carSrs + prdMnth
					+ byrGrp + byrCd));
			List<Object[]> getDatafrmTemp=specRep.getdataEimAutoAlloc(temp);
			float roundedAlloc= Float.parseFloat(specRep.getRoundedAllocOeiBuyer(temp));
			float diff= sumByrCd-roundedAlloc;
			LOG.info(sumByrCd+"-----------"+roundedAlloc+"=============="+diff);
			reallocateOrdersEndItm(diff,getDatafrmTemp);
			
		}
	}

	
	public void reallocateOrdersEndItm(float ordrDiff,
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
						BigDecimal allcOrdQty = (BigDecimal) alloctdQty[6];
						int allcOrdQtyTemp = ((BigDecimal) alloctdQty[6])
								.intValue();
						if (allcOrdQtyTemp != 0 || flag == false) {
							BigDecimal one = new BigDecimal(1);
							alloctdQty[6] = allcOrdQty.add(one);
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
						BigDecimal allcOrdQty = (BigDecimal) alloctdQty[6];
						int allcOrdQtyTemp = ((BigDecimal) alloctdQty[6])
								.intValue();
						if (allcOrdQtyTemp != 0 || flag == false) {
							BigDecimal one = new BigDecimal(-1);
							alloctdQty[6] = allcOrdQty.add(one);
							ordrDiffTempNw =++ordrDiffTemp;
							flag = true;
						}
					}

				}
			}
		}
		specRep.insertListNewOrdrDtls(calculatedOrdQty,4);
		LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);
	}
	
	private void addDataTogetByrCdMap() {
		List<Object[]> byrCdLst = specRep.getByrCdAct();
		for (Object[] temp : byrCdLst) {
			String prdMnth = temp[2].toString();
			String carSrs = temp[1].toString();
			String byrGrp = temp[3].toString();
			String byrCd = temp[4].toString();
			byrCdLvlMap.put(carSrs + prdMnth + byrGrp + byrCd,
					temp[5].toString());
		}
	}

	private void addDataTogetOEIByrIdLvlMap(List<Object[]> oeiBuyerId) {
		for (Object[] temp : oeiBuyerId) {
			String prdMnth = temp[2].toString();
			String carSrs = temp[1].toString();
			String byrGrp = temp[4].toString();
			String byrCd = temp[5].toString();
			String oeiByrId = temp[6].toString();
			oeiByrIdLvlMap.put(carSrs + prdMnth + byrGrp + byrCd + oeiByrId,
					temp[3].toString());
			if(byrCdCountMap.get(carSrs + prdMnth + byrGrp + byrCd) != null){
				int i=byrCdCountMap.get(carSrs + prdMnth + byrGrp + byrCd )+1;
				byrCdCountMap.put(carSrs + prdMnth + byrGrp + byrCd ,i);
			}else {
				byrCdCountMap.put(carSrs + prdMnth + byrGrp + byrCd ,1);
			}
		}

	}
}
