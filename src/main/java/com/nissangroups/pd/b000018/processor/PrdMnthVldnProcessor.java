/*
 * System Name     :Post Dragon 
 * Sub system Name :Batch / Interface
 * Function ID     :PST-DRG-B000001/PST-DRG-R000001
 * Module          :SP SPEC
 * Process Outline :Validating the interface fields with Master and generating the error report
 *
 * <Revision History>
 * Date       Name(Company Name)             Description 
 * ---------- ------------------------------ ---------------------
 * 2015/07/25  	  @author(RNTBCI)               New Creation
 *
 */
package com.nissangroups.pd.b000018.processor;

import static com.nissangroups.pd.util.PDConstants.STEP_AFTER_END;
import static com.nissangroups.pd.util.PDConstants.STEP_AFTER_START;
import static com.nissangroups.pd.util.PDMessageConsants.M00139;
import static com.nissangroups.pd.util.PDMessageConsants.M00068;



import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;

import com.nissangroups.pd.b000018.bean.InputBean;
import com.nissangroups.pd.repository.MnthlyOrdrIfTrnRepository;
import com.nissangroups.pd.util.CommonUtil;



public class PrdMnthVldnProcessor implements
		ItemProcessor<InputBean, InputBean> {

	/** Constant LOG. */
	private static final Log LOG = LogFactory.getLog
			(PrdMnthVldnProcessor.class);

	@Autowired(required=false)
	private MnthlyOrdrIfTrnRepository mnthlyOrdrIfTrnRepositoryObj ;
	
	private String errorCd;
	
	@Override
	public InputBean process(InputBean input) throws Exception {
		
		LOG.info(STEP_AFTER_START);	
		
		List<Object[]> distinctCarSrsdtls = mnthlyOrdrIfTrnRepositoryObj.fetchDistinctCarSrs(input);
		  
		for(Object[] obj:distinctCarSrsdtls){
			int horizon;
			
			String carSrs = CommonUtil.convertObjectToString(obj[0]);
			String ordrTkMnthString = CommonUtil.convertObjectToString(obj[1]);
			String byrGrpHrzn = CommonUtil.convertObjectToString(obj[2]);
			String carSrsHrzn = CommonUtil.convertObjectToString(obj[3]);
			String prdMnthString = CommonUtil.convertObjectToString(obj[4])	;
			String byrGrpCd = CommonUtil.convertObjectToString(obj[5]);
			String carSrsAdptDateStr = CommonUtil.convertObjectToString(obj[6]);
			String carSrsAblshDateStr = CommonUtil.convertObjectToString(obj[7]);
			
			if(byrGrpHrzn!=null && ("F" ).equalsIgnoreCase(byrGrpHrzn)) {
				horizon = Integer.parseInt(carSrsHrzn);
			} else {
				horizon = Integer.parseInt(byrGrpHrzn);	
			}
			
			Date prdMnth = CommonUtil.convertStringToDate(prdMnthString);
			
			Date carSrsAdptDate = CommonUtil.convertStringToDate(carSrsAdptDateStr.substring(0, 6));
			Date carSrsAblshDate = CommonUtil.convertStringToDate(carSrsAblshDateStr.substring(0, 6));
			
			
			
			if(prdMnth.compareTo(carSrsAdptDate) >= 0 && prdMnth.compareTo(carSrsAblshDate) <=0 ){
				List<String> oseiIdList = mnthlyOrdrIfTrnRepositoryObj.fetchOseiIdFrInVldPrdMnth(input,byrGrpCd,carSrs,ordrTkMnthString,prdMnthString);
				for(String oseiId : oseiIdList){

					Object[] nearestAblshAdptDates = mnthlyOrdrIfTrnRepositoryObj.fetchNearestAbolishAdoptDates(input,byrGrpCd,carSrs,ordrTkMnthString,prdMnthString,oseiId,carSrsAdptDateStr,carSrsAblshDateStr);
					if(nearestAblshAdptDates[0].toString().equalsIgnoreCase("false")){
						mnthlyOrdrIfTrnRepositoryObj.updateInVldPrdMnth(errorCd,M00068,input,byrGrpCd,carSrs,ordrTkMnthString,prdMnthString,"true",nearestAblshAdptDates);		
					} else {
						mnthlyOrdrIfTrnRepositoryObj.updateHorizon(horizon,input,byrGrpCd,carSrs,ordrTkMnthString,prdMnthString);	
					}
					
					
				}
				
			} else {
				mnthlyOrdrIfTrnRepositoryObj.updateInVldPrdMnth(errorCd,M00139,input,byrGrpCd,carSrs,ordrTkMnthString,prdMnthString,"false",null);
			}
			
			
		}
		LOG.info(STEP_AFTER_END);

		return input;
	}

	
	
	public String getErrorCd() {
		return errorCd;
	}


	public void setErrorCd(String errorCd) {
		this.errorCd = errorCd;
	}
	
	
}
