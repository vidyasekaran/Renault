/*
 * System Name     :Post Dragon 
 * Sub system Name :Batch 
 * Function ID     :PST-DRG-B000018/PST-DRG-R000007,R000008,R000009
 * Module          :MONTHLY ORDERING
 * Process Outline : Initialise and Update the Montly Order TRN Table for the valid records present in the Monthly Order Interface TRN 
 *
 * <Revision History>
 * Date       Name(Company Name)             Description 
 * ---------- ------------------------------ ---------------------
 * 06-OCT-2015  	 z001870(RNTBCI)               New Creation
 *
 */

package com.nissangroups.pd.b000018.processor;

import static com.nissangroups.pd.util.PDConstants.PERSISTENCE_NAME;
import static com.nissangroups.pd.util.PDConstants.STEP_AFTER_END;
import static com.nissangroups.pd.util.PDConstants.STEP_AFTER_START;
import static com.nissangroups.pd.util.PDMessageConsants.M00174;

import java.text.ParseException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;

import com.nissangroups.pd.b000018.bean.InputBean;
import com.nissangroups.pd.exception.PdApplicationException;
import com.nissangroups.pd.repository.ByrMnthlyOcfUsgRepository;
import com.nissangroups.pd.repository.MnthlyOrdrIfTrnRepository;
import com.nissangroups.pd.repository.MnthlyOrdrTrnRepository;



public class ByrMnthlyOcfUsgProcessor implements
		ItemProcessor<InputBean, InputBean> {

	/** Constant LOG. */
	private static final Log LOG = LogFactory.getLog
			(ByrMnthlyOcfUsgProcessor.class);

	@Autowired(required=false)
	private ByrMnthlyOcfUsgRepository byrMnthlyOcfUsgRepositoryObj;
	
	@Autowired(required=false)
	private MnthlyOrdrIfTrnRepository mnthlyOrdrIfTrnRepositoryObj;
	
	@Autowired(required=false)
	private MnthlyOrdrTrnRepository mnthlyOrdrTrnRepositoryObj;
	
	@Override
	public InputBean process(InputBean input) throws PdApplicationException, ParseException {

		LOG.info(STEP_AFTER_START);
		
		
		List<Object[]> distinctOseiIdList = mnthlyOrdrIfTrnRepositoryObj.fetchDistinctOseiId(input,"false");
		byrMnthlyOcfUsgRepositoryObj.initialisation(input,distinctOseiIdList);
		
		for(Object[] obj  : distinctOseiIdList){
			String oseiId = obj[0].toString();
			String ordrTkBsMnth = obj[2].toString().trim();
			String prdMnth = obj[4].toString().trim();
			
			List<String> features = byrMnthlyOcfUsgRepositoryObj.fetchFeatureCdForOseiId(input.getPorCd(),oseiId,prdMnth,ordrTkBsMnth);
			if(features == null || features.isEmpty()){
				//stop the batch
				LOG.info(M00174.replaceAll("&1", oseiId));
				return null;
			}
			
			int byrUsgQty = mnthlyOrdrTrnRepositoryObj.fetchSumOrdrQty(oseiId, input.getPorCd(), ordrTkBsMnth, prdMnth);
			byrMnthlyOcfUsgRepositoryObj.updateFeatureUsgFrOseiId(input.getPorCd(),oseiId,prdMnth,ordrTkBsMnth,byrUsgQty);
			
			
			
			
		}
		
		
		
		LOG.info(STEP_AFTER_END);
		
		return input;
	}	

	
}
