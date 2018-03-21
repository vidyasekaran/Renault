/*
 * System Name     :Post Dragon 
 * Sub system Name :Batch
 * Function ID     :PST-DRG-B000008
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

import static com.nissangroups.pd.b000008.util.B000008Constants.CONSTANT_V6;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import com.nissangroups.pd.b000008.output.B000008P1Output;
import com.nissangroups.pd.b000008.util.*;
import com.nissangroups.pd.model.MstMnthOrdrTakeBasePd;
import com.nissangroups.pd.repository.MnthlyOrdrRepository;
import com.nissangroups.pd.util.CommonUtil;
import com.nissangroups.pd.util.PDConstants;
import com.nissangroups.pd.util.PDMessageConsants;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;


/**
 * The Class OverLapPeriodProcess.
 *
 * @author z015060
 */
public class OverLapPrdProcess {

	/** Constant LOG */
	private static final Log LOG = LogFactory.getLog(OverLapPrdProcess.class
			.getName());

	/** Variable overLapExits */
	List<Object[]> overLapExists = new ArrayList<Object[]>();

	@Autowired(required = false)
	private MnthlyOrdrRepository mnthRep;
	
	/** Constructor with MstMnthOrdrTakeBasePd and entityManagerFac parameters */
	public OverLapPrdProcess (){
	}
	
	/**
	 * This method is to check whether overLap Period exists or not
	 * for the extracted OrderTakeBaseMonth
	 * ProcessId : P0002.1
	 * @param item 
	 * @param prdStgeCd 
	 * @param B000008P1Output the object
	 * @return the B000008P1Output class
	 * @throws ParseException 
	 * @throws Exception the exception
	 */
	
    public  B000008P1Output executeProcess(B000008P1Output objB000008P1, MstMnthOrdrTakeBasePd item, String prdStgeCd) throws ParseException{
    	LOG.info("OverLapPeriodProcess P0002.1");
		overLapExists=mnthRep.overLapCheck(item);
		
		/**if Overlap Exists, set the overlapFlag as Y else N
		 * returns OrdertakeBaseMnth and StageCode  
		 */
		if(!overLapExists.isEmpty()){
			objB000008P1.setSelectOverlap(overLapExists);
			objB000008P1.setOverLapFlg(PDConstants.Y);
		}else{
			objB000008P1.setOverLapFlg(PDConstants.N);
		}
		
		if(objB000008P1.getOverLapFlg().equals(PDConstants.Y)){
			int prodStgCdCnst=0;
			for(Object[] overlap: objB000008P1.getSelectOverlap()){
				if(overlap[1].toString().equals(PDConstants.STG_CD_D1) || 
						overlap[1].toString().equals(PDConstants.STG_CD_D2)){
					prodStgCdCnst=PDConstants.PROD_ORDER_STAGE_CD_DRAFT;
				}
				else if(overlap[1].toString().equals(PDConstants.STG_CD_F1) || 
						overlap[1].toString().equals(PDConstants.STG_CD_F2)){
					prodStgCdCnst=PDConstants.PROD_ORDER_STAGE_CD_FINAL;
				}
				if(prdStgeCd.equals(prodStgCdCnst+"")){
				  CommonUtil.logMessage(PDMessageConsants.M00221, CONSTANT_V6, new String[]{B000008Constants.BATCH_ID_B000008,
						  item.getStageCd(),item.getId().getPorCd(),item.getId().getOrdrTakeBaseMnth(),
						  (String) overlap[0],PDConstants.MONTHLY_ORDER_TAKE_BASE_PERIOD_MST});
					CommonUtil.stopBatch();
				}
			}
		}
		LOG.info("Overlap FLag is "+objB000008P1.getOverLapFlg());
		return objB000008P1;
		
	}
    

}

