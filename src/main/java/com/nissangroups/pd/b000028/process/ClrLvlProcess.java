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
 * The Class ClrLvlProcess.
 *
 * @author z015060
 */
public class ClrLvlProcess {

	/** Constant LOG */
	private static final Log LOG = LogFactory.getLog(ClrLvlProcess.class
			.getName());
	
	Map<String, String> clrLvlMap = new HashMap<String, String>();
	Map<String, String> oeiByrIdMap = new HashMap<String, String>();
	Map<String,  Integer> clrLvlCountMap = new HashMap<String,  Integer>();

	@Autowired(required = false)
	private SpecRepository specRep;

	public ClrLvlProcess() {
	}

	public B000028Output executeProcess(B000028Output object){
		LOG.info("ClrLvlProcess ");
		
		object.setClrLvl(specRep.getClrLvl(object));
		addDataTogetClrLvlMap(object.getClrLvl());
		object.setClrLvlMap(clrLvlMap);
		addDataOeiByrCdMap(object);
		
		try{
		for(Object[] clrLvl: object.getClrLvl()){
			//select tn.por_Cd,mp.CAR_SRS,tn.PROD_MNTH,sum(tn.ORDR_QTY),mbb.BUYER_GRP_CD,mbb.BUYER_CD,mb.OEI_BUYER_ID,mo.OSEI_ID
			String prdMnth = clrLvl[2].toString();
			String carSrs = clrLvl[1].toString();
			String byrGrpCd = clrLvl[4].toString();
			String byrCd = clrLvl[5].toString();
			String oeiByrId = clrLvl[6].toString();
			String oseiId = clrLvl[7].toString();
			float ratio = 0;
			float oseiOrdr = Integer.parseInt(clrLvlMap.get(carSrs
					+ prdMnth + byrGrpCd + byrCd + oeiByrId+oseiId));
			float sumOeiByrId = Integer.parseInt(oeiByrIdMap.get(carSrs + prdMnth
					+ byrGrpCd + byrCd+oeiByrId));
			float oeiByrIsMnthlyTrn= Integer.parseInt(object.getOeiByrIdLvlMap().get(carSrs + prdMnth
					+ byrGrpCd + byrCd+oeiByrId));
			float clrLvlCount= clrLvlCountMap.get(carSrs + prdMnth
					+ byrGrpCd + byrCd+oeiByrId);
			if(Float.floatToRawIntBits(oseiOrdr) == 0){
				ratio=(1/clrLvlCount)*100;
			}
			else {
				ratio=(oseiOrdr/oeiByrIsMnthlyTrn)*100;
			}
			float allocation = (ratio/100)*sumOeiByrId;
			float allocationRound = Math.round(allocation);
			int flag=5;
			specRep.insertTempAutoAdjust(object
					.getObjB000028ParamOutput().getPorCd(),clrLvl,Math.round(ratio),allocationRound,flag);
		}
		}catch(Exception e){
			LOG.info(e);
			CommonUtil.logMessage(PDMessageConsants.M00076,
					PDConstants.CONSTANT_V1, new String[] {
							B000028Constants.BATCH_ID_B000028 });
		}
		reAllocatedPortionClrLvl(object);
		return object;
	}

	private void reAllocatedPortionClrLvl(B000028Output object) {
	
		for(Object[] temp: object.getOeiBuyerId()){
			String prdMnth = temp[2].toString();
			String carSrs = temp[1].toString();
			String byrGrpCd = temp[4].toString();
			String byrCd = temp[5].toString();
			String oeiByrId = temp[6].toString();
			float sumByrCd = Integer.parseInt(oeiByrIdMap.get(carSrs + prdMnth
					+ byrGrpCd + byrCd+oeiByrId));
			List<Object[]> getDatafrmTemp=specRep.getdataClrLvlAutoAlloc(temp);
			float roundedAlloc= Float.parseFloat(specRep.getRoundedAllocClrLvl(temp));
			float diff= sumByrCd-roundedAlloc;
			reallocateOrdersClrLvl(diff,getDatafrmTemp);
			
		}
		
	}

	private void addDataOeiByrCdMap(B000028Output object) {
		List<Object[]> byrCdLst = specRep.getOeiByrAct(object);
		for (Object[] temp : byrCdLst) {
			String prdMnth = temp[2].toString();
			String carSrs = temp[1].toString();
			String byrGrpCd = temp[3].toString();
			String byrCd = temp[4].toString();
			String oeiByrId = temp[5].toString();
			oeiByrIdMap.put(carSrs + prdMnth + byrGrpCd + byrCd+oeiByrId,
					temp[6].toString());
		}
	}

	private void addDataTogetClrLvlMap(List<Object[]> clrLvl) {
		for (Object[] temp : clrLvl) {
			//select tn.por_Cd,mp.CAR_SRS,tn.PROD_MNTH,sum(tn.ORDR_QTY),mbb.BUYER_GRP_CD,mbb.BUYER_CD,mb.OEI_BUYER_ID,mo.OSEI_ID
			String prdMnth = temp[2].toString();
			String carSrs = temp[1].toString();
			String byrGrpCd = temp[4].toString();
			String byrCd = temp[5].toString();
			String oeiByrId = temp[6].toString();
			String oseiId = temp[7].toString();
			clrLvlMap.put(carSrs + prdMnth + byrGrpCd + byrCd + oeiByrId+oseiId,
					temp[3].toString());
			if(clrLvlCountMap.get(carSrs + prdMnth + byrGrpCd + byrCd + oeiByrId) != null){
				int i=clrLvlCountMap.get(carSrs + prdMnth + byrGrpCd + byrCd + oeiByrId)+1;
				clrLvlCountMap.put(carSrs + prdMnth + byrGrpCd + byrCd + oeiByrId,i);
			}else {
				clrLvlCountMap.put(carSrs + prdMnth + byrGrpCd + byrCd + oeiByrId,1);
			}
		}
		
	}
	
	public void reallocateOrdersClrLvl(float ordrDiff,
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
						BigDecimal allcOrdQty = (BigDecimal) alloctdQty[7];
						int allcOrdQtyTemp = ((BigDecimal) alloctdQty[7])
								.intValue();
						if (allcOrdQtyTemp != 0 || flag == false) {
							BigDecimal one = new BigDecimal(1);
							alloctdQty[7] = allcOrdQty.add(one);
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
						BigDecimal allcOrdQty = (BigDecimal) alloctdQty[7];
						int allcOrdQtyTemp = ((BigDecimal) alloctdQty[7])
								.intValue();
						if (allcOrdQtyTemp != 0 || flag == false) {
							BigDecimal one = new BigDecimal(-1);
							alloctdQty[7] = allcOrdQty.add(one);
							ordrDiffTempNw =++ordrDiffTemp;
							flag = true;
						}
					}

				}
			}
		}
		specRep.insertListNewOrdrDtls(calculatedOrdQty,6);
		LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);
	}

	

}
