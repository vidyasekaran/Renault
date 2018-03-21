/*
 * System Name     :Post Dragon 
 * Sub system Name :Batch
 * Function ID     :PST-DRG-b000008
 * Module          : Monthly Ordering					
 * Process Outline :Create Monthly Order Base Data
 *
 * <Revision History>
 * Date       Name(Company Name)             Description 
 * ---------- ------------------------------ ---------------------
 * 21-10-2015  	  z015060(RNTBCI)               New Creation
 *
 */ 
package com.nissangroups.pd.b000008.process;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;




import org.springframework.beans.factory.annotation.Autowired;

import com.nissangroups.pd.b000008.output.B000008P1Output;
import com.nissangroups.pd.b000008.output.B000008ParamOutput;
import com.nissangroups.pd.b000008.util.B000008Constants;
import com.nissangroups.pd.model.MstMnthOrdrTakeBasePd;
import com.nissangroups.pd.repository.MnthlyOrdrRepository;
import com.nissangroups.pd.util.CommonUtil;
import com.nissangroups.pd.util.PDConstants;
import com.nissangroups.pd.util.PDMessageConsants;


/**
 * The Class ExtractHrznProcess.
 *
 * @author z015060
 */
public class ExtractHrznProcess {


	/** Constant LOG */
	private static final Log LOG = LogFactory.getLog(ExtractHrznProcess.class
			.getName());
	
	@Autowired(required = false)
	private MnthlyOrdrRepository mnthRep;
	
	/** Constructor with MstMnthOrdrTakeBasePd item and B000008ParamOutput objB000008ParamOutput2 and entityManagerFac parameters */
	public ExtractHrznProcess() {
	}

	/**
	 * This process is to extract Horizon value 
	 * @param object B000008P1Output
	 * Process P0003.2
	 * @param objB000008ParamOutput2 
	 * @param item 
	 * @return PORCD, productionFamilyCode, carSeries, carSeriesHrzn, OrderTakeBaseMonth, StageCode, ProductionStageCode
	 */
	public B000008P1Output executeProcess(B000008P1Output object, MstMnthOrdrTakeBasePd item, B000008ParamOutput objB000008ParamOutput2) {
		LOG.info("ExtractHrznProcess P0003.1, P0003.2");
		
		
		/** Variable maxProdMonthCal	 */
		List<Object[]> maxProdMonthCal = new ArrayList<Object[]>();
		
		/** Extract car series horizon  P0003.1 */
		object.setCarSrsHrzn(mnthRep.chkHrzn(item.getId().getPorCd()));
		
		for(Object[] horizonList : object.getCarSrsHrzn()){
			if (horizonList[3] == null) {
				CommonUtil.logWarningMessage(PDMessageConsants.M00161, B000008Constants.CONSTANT_V6,
						new String[]{B000008Constants.BATCH_ID_B000008,
						 B000008Constants.CAR_SERIES_HORIZON,item.getId().getPorCd(),
						 (String) horizonList[2],PDConstants.MESSAGE_POR_CAR_SERIES_MST,B000008Constants.PROCESS_P3});
				
				/** Extract por horizon  P0003.2 */
				 horizonList[3] = mnthRep.getPorHorizon(item.getId().getPorCd());
				 
				
			}
			
			maxProdMonthCal.add(new Object[] {  item.getId().getPorCd(),
					(String) horizonList[1], (String) horizonList[2],
					horizonList[3],
					item.getId().getOrdrTakeBaseMnth(),
					item.getStageCd(), objB000008ParamOutput2.getPrdStgCd() });
		}
		object.setHrznList(maxProdMonthCal);
		LOG.info("OUTPUT of ExtractHrznProcess is "+object.getHrznList().size());
		return object;
	}

}
