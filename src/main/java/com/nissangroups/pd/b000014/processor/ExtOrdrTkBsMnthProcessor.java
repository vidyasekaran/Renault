/*
 * System Name       : Post Dragon 
 * Sub system Name : Batch
 * Function ID            : PST-DRG-B000014
 * Module                  : Ordering		
 * Process Outline     : RHQ/NSC wise Volume/OCF allocation															
 *
 * <Revision History>
 * Date       Name(Company Name)             Description 
 * ---------- ------------------------------ ---------------------
 * 6-11-2015  	  z015399(RNTBCI)               Initial Version
 *
 */
package com.nissangroups.pd.b000014.processor;

import static com.nissangroups.pd.util.PDConstants.CONSTANT_V2;
import static com.nissangroups.pd.util.PDConstants.CONSTANT_V4;
import static com.nissangroups.pd.util.PDConstants.PERSISTENCE_NAME;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.item.ItemProcessor;

import com.nissangroups.pd.b000014.output.B000014Output;
import com.nissangroups.pd.b000014.output.ExtMnthlyBtchPrsSttsTblOutput;
import com.nissangroups.pd.b000014.output.ExtOrdrTkBsMnthOutput;
import com.nissangroups.pd.b000014.output.TrnExtract;
import com.nissangroups.pd.b000014.util.B000014Constants;
import com.nissangroups.pd.b000014.mapper.B000014InputMapper;
import com.nissangroups.pd.exception.PdApplicationException;
import com.nissangroups.pd.model.TrnBuyerGrpMnthlyOcfLmt;
import com.nissangroups.pd.model.TrnBuyerGrpMnthlyOcfLmtPK;
import com.nissangroups.pd.repository.PlantOCFRepository;
import com.nissangroups.pd.repository.QueryParamBean;
import com.nissangroups.pd.util.CommonUtil;
import com.nissangroups.pd.util.PDConstants;
import com.nissangroups.pd.util.PDMessageConsants;

import static com.nissangroups.pd.util.PDConstants.*;

/**
*
* @author z015399
* 
* Processor class for B000014
*/

/** P0001*/
public class ExtOrdrTkBsMnthProcessor implements
								ItemProcessor<B000014InputMapper,B000014Output>{
	
	/** Constant LOG. */
	private static final Log LOG = LogFactory.getLog(ExtOrdrTkBsMnthProcessor.class);
	
	private String porCd;
	private String productionStageCd;
	
	/** Variable entity manager. */
	@PersistenceContext(name=PERSISTENCE_NAME)
	private EntityManager entityManager;
	
	QueryParamBean qryParamBean = new QueryParamBean();
	

   public String getPorCd() {
	return porCd;
   }


	public void setPorCd(String porCd) {
	this.porCd = porCd;
	}

	public String getProductionStageCd() {
	return productionStageCd;
	}


	public void setProductionStageCd(String productionStageCd) {
	this.productionStageCd = productionStageCd;
	}

	
	/* (non-Javadoc)
	 * @see org.springframework.batch.item.ItemProcessor#process(java.lang.Object)
	 */
	@Override
	public B000014Output process(B000014InputMapper objInputMapper) throws Exception{
		
		LOG.info(DOLLAR + "Inside ExtOrdrTkBsMnthProcessor" + DOLLAR);
		
		LOG.info("POR_CD "+ objInputMapper.getId().getPOR_CD() + 
				 "STAGE_CD" + objInputMapper.getSTAGE_CD()+
				"ORDER_TAKE_BASE_MONTH "+ objInputMapper.getId().getORDR_TAKE_BASE_MNTH());
		
		String otbm = objInputMapper.getId().getORDR_TAKE_BASE_MNTH();
		
		B000014Output objB000014Output = new B000014Output();
		ExtOrdrTkBsMnthOutput objExtOrdrTkBsMnthOutput = new ExtOrdrTkBsMnthOutput();
		objExtOrdrTkBsMnthOutput.setOtbm(objInputMapper.getId().getORDR_TAKE_BASE_MNTH());
		
		objB000014Output.setObjExtOrdrTkBsMnthOutput(objExtOrdrTkBsMnthOutput);
		
		PlantOCFRepository objPlantOCFRepository = new PlantOCFRepository();
		
		List<ExtMnthlyBtchPrsSttsTblOutput> lstExtMnthlyBtchPrsSttsTblOutput = null;
		
		/** Process Id P002.1 - EXTRACT DETAILS FROM MONTHLY BATCH PROCESS STATUS TABLE  **/
		lstExtMnthlyBtchPrsSttsTblOutput = objPlantOCFRepository.extMnthlyBtchPrsSttsDtls(this.getPorCd(), otbm, entityManager);
		
		if ( lstExtMnthlyBtchPrsSttsTblOutput.isEmpty()){
			String[] strMsgParams = {B000014Constants.FUNCTION_ID,porCd};
			CommonUtil.logMessage(PDMessageConsants.M00358, CONSTANT_V2 , strMsgParams);
			throw new PdApplicationException();
		}
		
		/** Process Id P002.2 - Update MONTHLY BATCH PROCESS STATUS TABLE  **/
		objPlantOCFRepository.uptMnthlyBtchPrsSttsTblInProgress(this.getPorCd(), otbm, entityManager);
		
		int intRegOCFLmt = 0;
		ExtMnthlyBtchPrsSttsTblOutput objExtMnthlyBtchPrsSttsTblOutput = lstExtMnthlyBtchPrsSttsTblOutput.get(0);
		
			String[] prdMnthArr = getPrdMnthArr(objExtMnthlyBtchPrsSttsTblOutput.getStrPrdMnthFrm(),
					objExtMnthlyBtchPrsSttsTblOutput.getStrPrdMnthTo());
			
			List<TrnExtract> lstTrnExtract = null;
		
		    qryParamBean.setPorCd(this.getPorCd());
		    qryParamBean.setOrdrTkBsMnth(otbm);
		    qryParamBean.setPrdnStgCd(this.getProductionStageCd());
		    qryParamBean.setPrdnMnthFrm(objExtMnthlyBtchPrsSttsTblOutput.getStrPrdMnthFrm());
		    qryParamBean.setPrdnMnthTo(objExtMnthlyBtchPrsSttsTblOutput.getStrPrdMnthTo());
		    qryParamBean.setPrmtr4(objExtMnthlyBtchPrsSttsTblOutput.getStrPrmtr4());
		    qryParamBean.setPrmtr5(objExtMnthlyBtchPrsSttsTblOutput.getStrPrmtr5());
		
		    
			/** Process Id P003 - Extract spec master table information  **/
		    lstTrnExtract = objPlantOCFRepository.getTrnDetails1(qryParamBean,entityManager);
			
			if (lstTrnExtract.isEmpty()){
				String[] strMsgParams = {B000014Constants.FUNCTION_ID,B000014Constants.P1TBL,porCd,this.getProductionStageCd()};
				CommonUtil.logMessage(PDMessageConsants.M00359, CONSTANT_V4 , strMsgParams);
				throw new PdApplicationException();
			}
			
			for (TrnExtract objTrnExtract : lstTrnExtract){
				
				for (int i=0 ; i < prdMnthArr.length ; i++){
					
				
				LOG.info("************** Before getting Regional OCF Limit *********");
				
				qryParamBean.setPorCd(objTrnExtract.getStrPorCd());
				qryParamBean.setOrdrTkBsMnth(objTrnExtract.getStrOtbm());
				qryParamBean.setCarSrs(objTrnExtract.getStrCarSrs());
				qryParamBean.setOcfRgnCd(objTrnExtract.getStrOCFRgnCd());
				qryParamBean.setPrmtr4(objExtMnthlyBtchPrsSttsTblOutput.getStrPrmtr4());
				
				/** Process Id P0006.1 - Extract Regional OCF limit from REGIONAL_MONTHLY_OCF_LIMIT_TRN **/
				intRegOCFLmt = objPlantOCFRepository.getRegOCFLmt(qryParamBean, prdMnthArr[i], objTrnExtract.getStrOCFByrGrpCd(), objTrnExtract.getStrFeatureCd(),entityManager);
				
				List<TrnExtract> lstTrnExtract1 = null;
				
				
				/** Process Id P0004  **/
				lstTrnExtract1 = objPlantOCFRepository.getTrnDetails2(qryParamBean, objTrnExtract.getStrOCFByrGrpCd(), objTrnExtract.getStrFeatureCd(), prdMnthArr[i] , entityManager);
				
				if (lstTrnExtract1.isEmpty()){
					String[] strMsgParams = {B000014Constants.FUNCTION_ID,B000014Constants.P1TBL,porCd,this.getProductionStageCd()};
					CommonUtil.logMessage(PDMessageConsants.M00359, CONSTANT_V4 , strMsgParams);
					throw new PdApplicationException();
				}
				
				int intIndRegOCFLmt = 0;
				if (! lstTrnExtract1.isEmpty()){
				 intIndRegOCFLmt = intRegOCFLmt/lstTrnExtract1.size();
				}
				for (TrnExtract objTrnExtract1 : lstTrnExtract1){
					
					TrnBuyerGrpMnthlyOcfLmt objTrnBuyerGrpMnthlyOcfLmt = new TrnBuyerGrpMnthlyOcfLmt();
					
					TrnBuyerGrpMnthlyOcfLmtPK objTrnBuyerGrpMnthlyOcfLmtPK = new TrnBuyerGrpMnthlyOcfLmtPK();
					objTrnBuyerGrpMnthlyOcfLmtPK.setBuyerGrpCd(objTrnExtract1.getStrByrGrpCd());
					objTrnBuyerGrpMnthlyOcfLmtPK.setPorCd(objTrnExtract1.getStrPorCd());
					objTrnBuyerGrpMnthlyOcfLmtPK.setOrdrTakeBaseMnth(objTrnExtract1.getStrOtbm());
					objTrnBuyerGrpMnthlyOcfLmtPK.setProdMnth(objTrnExtract1.getStrPrdMnth());
					objTrnBuyerGrpMnthlyOcfLmtPK.setCarSrs(objTrnExtract1.getStrCarSrs());
					objTrnBuyerGrpMnthlyOcfLmtPK.setFeatCd(objTrnExtract1.getStrFeatureCd());
					
					objTrnBuyerGrpMnthlyOcfLmt.setId(objTrnBuyerGrpMnthlyOcfLmtPK);
					
					TrnBuyerGrpMnthlyOcfLmt objTrnBuyerGrpMnthlyOcfLmtOld = entityManager.find(TrnBuyerGrpMnthlyOcfLmt.class,objTrnBuyerGrpMnthlyOcfLmtPK);
					
					if (objTrnBuyerGrpMnthlyOcfLmtOld != null){
						
						objTrnBuyerGrpMnthlyOcfLmt = objTrnBuyerGrpMnthlyOcfLmtOld;
					}
					
					/** Process Id P0004.2 - Initialization of usage quantity **/
					objTrnBuyerGrpMnthlyOcfLmt.setBuyerGrpSimuQty(new BigDecimal(intIndRegOCFLmt));
					
					try{
						if (objTrnBuyerGrpMnthlyOcfLmtOld != null){
							entityManager.merge(objTrnBuyerGrpMnthlyOcfLmt);
						}
					}
					catch(Exception e){
						LOG.info(e);
						CommonUtil.logMessage(PDMessageConsants.M00164, 
								PDConstants.CONSTANT_V4, new String[] {
				    			B000014Constants.FUNCTION_ID,
								PDConstants.UPDATION,
								PDConstants.TRN_BUYER_GRP_MNTHLY_OCF_LMT });
					}
					
				}
				}	
		}
		LOG.info(DOLLAR + "Outside ExtOrdrTkBsMnthProcessor" + DOLLAR);
		
		return objB000014Output;
		
	}
	
	/**
	 * @param prdMnthFrm
	 * @param prdMnthTo
	 * @return list of production months
	 */
	private  String[] getPrdMnthArr(String prdMnthFrm, String prdMnthTo){
		int intPrdMnthTo = Integer.parseInt(prdMnthTo);
		int intPrdMnthFrm = Integer.parseInt(prdMnthFrm);
		
		int diff = intPrdMnthTo - intPrdMnthFrm;
		
		if ( diff > 11 ){
			diff = (diff - (88*Math.abs(diff/88)));
		}
		
		String[] prdMnthArr = new String[diff+1];
		String tmp = prdMnthFrm;
		for (int i=0 ; i <= diff ; i++){
			
			if (( tmp.substring(4, 6)).equals(B000014Constants.TWELVE)){
				prdMnthArr[i] = tmp;
				int temp1 = Integer.parseInt(tmp.substring(0, 4)) +1;
				tmp = temp1  +PDConstants.ZERO_ONE;
			}
			else{
				prdMnthArr[i] = tmp;
				int tmp1 = Integer.parseInt(tmp);
				tmp1++;
				tmp = Integer.toString(tmp1);
			}
			
		}
		
		return prdMnthArr;
	}

}
