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
 * The Class ByrCdLvlProcess.
 *
 * @author z015060
 */
public class ByrCdLvlProcess {

	/** Constant LOG */
	private static final Log LOG = LogFactory.getLog(ByrCdLvlProcess.class
			.getName());

	Map<String, String> byrCDLvlMap = new HashMap<String, String>();
	Map<String, Integer> byrCdCount = new HashMap<String, Integer>();

	@Autowired(required = false)
	private SpecRepository specRep;

	public ByrCdLvlProcess() {
	}

	public B000028Output executeProcess(B000028Output objB000028) {
		LOG.info("ByrCdLvlProcess");

		objB000028.setByrCdLvl(specRep.getByrGrpCdLvlData(objB000028
				.getObjB000028ParamOutput().getPorCd(), objB000028
				.getObjB000028ParamOutput().getOrdrTkBsMnth(), objB000028));
		addDataTogetByrGrpCdLvlMap(objB000028.getByrCdLvl());
		specRep.delTempAutoAdjust();
		try{
		for (Object[] byrCdArry : objB000028.getByrCdLvl()) {
			String prdMnth = byrCdArry[2].toString();
			String carSrs = byrCdArry[1].toString();
			String byrGrpCd = byrCdArry[4].toString();
			String byrCd = byrCdArry[5].toString();
			float ratio = 0;
			float byrCdOrdr = Integer.parseInt(byrCDLvlMap.get(carSrs
					+ prdMnth + byrGrpCd + byrCd));
			float byrGrp = Integer.parseInt(objB000028.getByrGrpLvlMap().get(
					carSrs + prdMnth + byrGrpCd));
			float byrGrpdiff = Integer.parseInt(objB000028.getByrGrpDiffMap()
					.get(carSrs + prdMnth + byrGrpCd));
			float byrGrpCount = byrCdCount.get(carSrs + prdMnth + byrGrpCd);
			if (Float.floatToRawIntBits(byrCdOrdr) == 0) {
				ratio=(1/byrGrpCount)*100;
			} else {
				ratio = (byrCdOrdr / byrGrp) * 100;
			}
			float allocation = (ratio / 100) * byrGrpdiff;
			LOG.info("byrGrp---------->"+byrGrp + "byrGrpDif------>" + byrGrpdiff
					+ "------byrCodeLVLORdrQty--->" + byrCdOrdr+ "------ratio-------->" + ratio+"---alloc--->"+allocation );
			float allocationRound = Math.round(allocation);
			LOG.info("allocationRound----------------->"+allocationRound);
			int flag=1;
			specRep.insertTempAutoAdjust(objB000028
					.getObjB000028ParamOutput().getPorCd(),byrCdArry,Math.round(ratio),allocationRound,flag);
		}
		}catch(Exception e){
			LOG.info(e);
			CommonUtil.logMessage(PDMessageConsants.M00076,
					PDConstants.CONSTANT_V1, new String[] {
							B000028Constants.BATCH_ID_B000028 });
		}
		
		reAllocatedPortion(objB000028);
		
		return objB000028;
	}

	private void reAllocatedPortion(B000028Output objB000028) {
		
		for(Object[] byrCdArry : objB000028.getByrGrpDiffList()){
			
			//carSrs,prdMnth,byrGrpCd,diff  
			String prdMnth = byrCdArry[1].toString();
			String carSrs = byrCdArry[0].toString();
			String byrGrpCd = byrCdArry[2].toString();
			float byrGrp = Integer.parseInt(objB000028.getByrGrpDiffMap().get(
					carSrs + prdMnth + byrGrpCd));
			List<Object[]> getDatafrmTemp=specRep.getdatafromTempBuyrCD(byrCdArry);
			float roundedAlloc= Float.parseFloat(specRep.getRoundedAlloc(byrCdArry));
			float diff= byrGrp-roundedAlloc;
			reallocateOrders(diff,getDatafrmTemp);
		}
		
		
	}

	private void addDataTogetByrGrpCdLvlMap(List<Object[]> byrGrpCdLvl) {
		for (Object[] byrGrpCdArry : byrGrpCdLvl) {
			String prdMnth = byrGrpCdArry[2].toString();
			String carSrs = byrGrpCdArry[1].toString();
			String byrGrpCd = byrGrpCdArry[4].toString();
			String byrCd = byrGrpCdArry[5].toString();
			byrCDLvlMap.put(carSrs + prdMnth + byrGrpCd + byrCd,
					byrGrpCdArry[3].toString());
			if(byrCdCount.get(carSrs + prdMnth + byrGrpCd ) != null){
				int i=byrCdCount.get(carSrs + prdMnth + byrGrpCd  )+1;
				byrCdCount.put(carSrs + prdMnth + byrGrpCd  ,i);
			}else {
				byrCdCount.put(carSrs + prdMnth + byrGrpCd  ,1);
			}
		}

	}
	
	public void reallocateOrders(float ordrDiff,
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
						BigDecimal allcOrdQty = (BigDecimal) alloctdQty[5];
						int allcOrdQtyTemp = ((BigDecimal) alloctdQty[5])
								.intValue();
						if (allcOrdQtyTemp != 0 || flag == false) {
							BigDecimal one = new BigDecimal(1);
							alloctdQty[5] = allcOrdQty.add(one);
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
						BigDecimal allcOrdQty = (BigDecimal) alloctdQty[5];
						int allcOrdQtyTemp = ((BigDecimal) alloctdQty[5])
								.intValue();
						if (allcOrdQtyTemp != 0 || flag == false) {
							BigDecimal one = new BigDecimal(-1);
							alloctdQty[5] = allcOrdQty.add(one);
							ordrDiffTempNw =++ordrDiffTemp;
							flag = true;
						}
					}

				}
			}
		}
		specRep.insertListNewOrdrDtls(calculatedOrdQty,2);
		LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);
	}

}
