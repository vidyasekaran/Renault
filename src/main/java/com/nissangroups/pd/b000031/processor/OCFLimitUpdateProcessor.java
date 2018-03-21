/*
 * System Name       : Post Dragon 
 * Sub system Name : Batch
 * Function ID            : PST-DRG-B000031
 * Module                  : Ordering		
 * Process Outline     : Create Weekly OCF Limit and auto allocation															
 *
 * <Revision History>
 * Date       Name(Company Name)             Description 
 * ---------- ------------------------------ ---------------------
 * 09-12-2015  	  z015399(RNTBCI)               Initial Version
 *
 */
package com.nissangroups.pd.b000031.processor;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;

import com.nissangroups.pd.b000031.mapper.B000031InputMapper;
import com.nissangroups.pd.b000031.output.BuyerGrpInfo;
import com.nissangroups.pd.b000031.output.TrnDailyOCFLmtInfo;
import com.nissangroups.pd.b000031.output.TrnRgnWklyOCFInfo;
import com.nissangroups.pd.b000031.util.B000031Constants;
import com.nissangroups.pd.model.TrnBuyerGrpWklyOcfLmt;
import com.nissangroups.pd.model.TrnBuyerGrpWklyOcfLmtPK;
import com.nissangroups.pd.model.TrnRegionalWklyOcfLmt;
import com.nissangroups.pd.model.TrnRegionalWklyOcfLmtPK;
import com.nissangroups.pd.repository.ParameterMstRepository;
import com.nissangroups.pd.repository.QueryParamBean;
import com.nissangroups.pd.repository.WeeklyOCFRepository;
import com.nissangroups.pd.util.CommonUtil;
import com.nissangroups.pd.util.PDConstants;
import com.nissangroups.pd.util.PDMessageConsants;

import static com.nissangroups.pd.util.PDConstants.CONSTANT_V4;
import static com.nissangroups.pd.util.PDConstants.CONSTANT_V5;
import static com.nissangroups.pd.util.PDConstants.EMPTY_STRING;
import static com.nissangroups.pd.util.PDConstants.PERSISTENCE_NAME;


/**
 * Processor class for B000031
 *
 * @author z015399
 */
public class OCFLimitUpdateProcessor implements ItemProcessor<B000031InputMapper,B000031InputMapper>{
	
	/** Constant LOG. */
	private static final Log LOG = LogFactory.getLog
			(OCFLimitUpdateProcessor.class);
	
	/** Job input paramaters */
	private String porCd;
	private String ocfRegionCd;
	
	/** Variable entity manager. */
	@PersistenceContext(name=PERSISTENCE_NAME)
	private EntityManager entityManager;
	
	@Autowired(required=false)
	private ParameterMstRepository parameterMstRepositoryObj;
	
	private WeeklyOCFRepository objWeeklyOCFRepository = new WeeklyOCFRepository();
	
	public String getPorCd() {
		return porCd;
	}


	public void setPorCd(String porCd) {
		this.porCd = porCd;
	}
	
	
	
	public String getOcfRegionCd() {
		return ocfRegionCd;
	}


	public void setOcfRegionCd(String ocfRegionCd) {
		this.ocfRegionCd = ocfRegionCd;
	}


	/* (non-Javadoc)
	 * @see org.springframework.batch.item.ItemProcessor#process(java.lang.Object)
	 */
	@Override
	public B000031InputMapper process(B000031InputMapper objInputMapper) throws Exception{
		
		QueryParamBean qryPrmBn = new QueryParamBean();
		
		LOG.info("POR_CD "+ objInputMapper.getId().getPOR_CD() + 
				" Order Take Base Month " + objInputMapper.getId().getORDR_TAKE_BASE_MNTH() +
				" Order Take Base Week No " + objInputMapper.getId().getORDR_TAKE_BASE_WK_NO());
		
		String otbm = objInputMapper.getId().getORDR_TAKE_BASE_MNTH();
		String otbwn = objInputMapper.getId().getORDR_TAKE_BASE_WK_NO();
		
		// Process 1.2 - Plant Line Summary extraction
		String strPltLnSummry = parameterMstRepositoryObj.fetchValue2(B000031Constants.WKLY_SMRY, 
				objInputMapper.getId().getPOR_CD(), B000031Constants.PLNT_SMRY);
		
		if ( strPltLnSummry == null || EMPTY_STRING.equals(strPltLnSummry) ){
			String[] strMsgParams = {B000031Constants.PRMTR1,B000031Constants.PLNT_LN_SMRY,this.getPorCd(),B000031Constants.PRMTR_TBL_NM};
			CommonUtil.logMessage(PDMessageConsants.M00160, CONSTANT_V4 , strMsgParams);			
			CommonUtil.stopBatch();
		}
		
		LOG.info("The value of strPltLnSummry is "+strPltLnSummry);
		
		// Process 1.2 - Plant Cd , Line Class
		
		String[] strPltCdLnCls = parameterMstRepositoryObj.fetchValue1Value2(				
				B000031Constants.WKLY_SMRY, objInputMapper.getId().getPOR_CD(), B000031Constants.CNST_LN_CLS);
		
		if ( strPltCdLnCls == null || EMPTY_STRING.equals(strPltCdLnCls[0]) || EMPTY_STRING.equals(strPltCdLnCls[1]) ){
			
			String[] strMsgParams = {B000031Constants.PRMTR1,B000031Constants.PLNT_LN_CLS,B000031Constants.PRMTR_TBL_NM,this.getPorCd()};
			CommonUtil.logMessage(PDMessageConsants.M00190, CONSTANT_V4 , strMsgParams);	
			CommonUtil.stopBatch();
		}
		else{
			
			LOG.info("The value of Plant Cd is"+ strPltCdLnCls[0] + " and Line Class is "+ strPltCdLnCls[1]);
		}
		
		// Process 1.2 - Constant Day No
		
		String strCnstDayNo = parameterMstRepositoryObj.fetchValue2(
				B000031Constants.WKLY_SMRY, objInputMapper.getId().getPOR_CD(), B000031Constants.CNST_DY_NO);
		
		if (strCnstDayNo == null || EMPTY_STRING.equals(strCnstDayNo)){			
			String[] strMsgParams = {B000031Constants.PRMTR1,B000031Constants.CONST_DAY_NO,B000031Constants.PRMTR_TBL_NM,this.getPorCd()};
			CommonUtil.logMessage(PDMessageConsants.M00190, CONSTANT_V4 , strMsgParams);	
			CommonUtil.stopBatch();
		}
		else{
			
			LOG.info("The value of Constant Day No is "+strCnstDayNo);
		}
		
		//P0002.1
		List<TrnDailyOCFLmtInfo> lstTrnDailyOCFLmtInfo = null;
		
		qryPrmBn.setPorCd(this.getPorCd());
		qryPrmBn.setOcfRgnCd(this.getOcfRegionCd());
		qryPrmBn.setOrdrTkBsMnth(otbm);
		qryPrmBn.setOrdrTkBsWkNo(otbwn);
		
		lstTrnDailyOCFLmtInfo = objWeeklyOCFRepository.getTrnDailyOCFInfo(qryPrmBn,strPltLnSummry,strPltCdLnCls[0],strPltCdLnCls[1],strCnstDayNo,false, entityManager);
		
		if ( lstTrnDailyOCFLmtInfo.isEmpty()){
			String[] strMsgParams = {B000031Constants.PRMTR1,this.getPorCd(),otbm,otbwn,B000031Constants.TRN_LMT_TBL_NM};
			CommonUtil.logMessage(PDMessageConsants.M00165, CONSTANT_V5 , strMsgParams);	
			CommonUtil.stopBatch();
		}
		
		//P0002.2
		List<TrnDailyOCFLmtInfo> lstTrnDailyOCFLmtInfoFrmCd00 = null;
		
		lstTrnDailyOCFLmtInfoFrmCd00 = objWeeklyOCFRepository.getTrnDailyOCFInfo(qryPrmBn,strPltLnSummry,strPltCdLnCls[0],strPltCdLnCls[1],strCnstDayNo,true, entityManager);
		
		LOG.info("After getting Trn Daily Details for  00 frame code");
		if ( lstTrnDailyOCFLmtInfoFrmCd00.isEmpty()){
			String[] strMsgParams = {B000031Constants.PRMTR1,this.getPorCd(),otbm,otbwn,B000031Constants.TRN_LMT_TBL_NM};
			CommonUtil.logMessage(PDMessageConsants.M00165, CONSTANT_V5 , strMsgParams);	
			CommonUtil.stopBatch();
		}
		
		//P0003.1
		for (TrnDailyOCFLmtInfo objTrnDailyOCFLmtInfo : lstTrnDailyOCFLmtInfo){
			
			TrnRegionalWklyOcfLmt objTrnRegionalWklyOcfLmt = new TrnRegionalWklyOcfLmt();
			TrnRegionalWklyOcfLmtPK objTrnRegionalWklyOcfLmtPK = new TrnRegionalWklyOcfLmtPK();
			objTrnRegionalWklyOcfLmtPK.setPorCd(this.getPorCd());
			objTrnRegionalWklyOcfLmtPK.setProdMnth(objTrnDailyOCFLmtInfo.getStrPrdMnth());
			objTrnRegionalWklyOcfLmtPK.setProdWkNo(objTrnDailyOCFLmtInfo.getStrPrdWkNo());
			objTrnRegionalWklyOcfLmtPK.setOcfRegionCd(this.getOcfRegionCd());
			objTrnRegionalWklyOcfLmtPK.setCarSrs(objTrnDailyOCFLmtInfo.getStrCarSrs());
			objTrnRegionalWklyOcfLmtPK.setFeatCd(objTrnDailyOCFLmtInfo.getStrFeatureCd());
			objTrnRegionalWklyOcfLmtPK.setOcfBuyerGrpCd(objTrnDailyOCFLmtInfo.getStrOCFByrGrpCd());
			objTrnRegionalWklyOcfLmtPK.setLineClass(objTrnDailyOCFLmtInfo.getStrLnCls());
			objTrnRegionalWklyOcfLmtPK.setPlantCd(objTrnDailyOCFLmtInfo.getStrPrdPltCd());
			objTrnRegionalWklyOcfLmtPK.setProdDayNo(objTrnDailyOCFLmtInfo.getStrPrdDayNo());
			objTrnRegionalWklyOcfLmt = entityManager.find(TrnRegionalWklyOcfLmt.class, objTrnRegionalWklyOcfLmtPK);
			
			if ( objTrnRegionalWklyOcfLmt != null)
			{
				entityManager.remove(objTrnRegionalWklyOcfLmt);
			}
		}
		
		//P0003.2
		
		
		for (TrnDailyOCFLmtInfo objTrnDailyOCFLmtInfo : lstTrnDailyOCFLmtInfo){
			
			TrnRegionalWklyOcfLmt objTrnRegionalWklyOcfLmt = new TrnRegionalWklyOcfLmt();
			TrnRegionalWklyOcfLmtPK objTrnRegionalWklyOcfLmtPK = new TrnRegionalWklyOcfLmtPK();
			objTrnRegionalWklyOcfLmtPK.setPorCd(this.getPorCd());
			objTrnRegionalWklyOcfLmtPK.setProdMnth(objTrnDailyOCFLmtInfo.getStrPrdMnth());
			objTrnRegionalWklyOcfLmtPK.setProdWkNo(objTrnDailyOCFLmtInfo.getStrPrdWkNo());
			objTrnRegionalWklyOcfLmtPK.setOcfRegionCd(this.getOcfRegionCd());
			objTrnRegionalWklyOcfLmtPK.setCarSrs(objTrnDailyOCFLmtInfo.getStrCarSrs());
			objTrnRegionalWklyOcfLmtPK.setFeatCd(objTrnDailyOCFLmtInfo.getStrFeatureCd());
			objTrnRegionalWklyOcfLmtPK.setOcfBuyerGrpCd(objTrnDailyOCFLmtInfo.getStrOCFByrGrpCd());
			objTrnRegionalWklyOcfLmtPK.setLineClass(objTrnDailyOCFLmtInfo.getStrLnCls());
			objTrnRegionalWklyOcfLmtPK.setPlantCd(objTrnDailyOCFLmtInfo.getStrPrdPltCd());
			objTrnRegionalWklyOcfLmtPK.setProdDayNo(objTrnDailyOCFLmtInfo.getStrPrdDayNo());
			
			TrnRegionalWklyOcfLmt objTrnRegionalWklyOcfLmtOld = entityManager.find(TrnRegionalWklyOcfLmt.class, objTrnRegionalWklyOcfLmtPK);
			
			int insUpt =0;
			
			if ( objTrnRegionalWklyOcfLmtOld != null){
				
				insUpt = 1;
			}
			else{
				insUpt = 2;
			}
			objTrnRegionalWklyOcfLmt.setId(objTrnRegionalWklyOcfLmtPK);
			objTrnRegionalWklyOcfLmt.setOcfFrmeCd(objTrnDailyOCFLmtInfo.getStrOCFFrameCd());
			
			objTrnRegionalWklyOcfLmt.setRegionalOcfLmtQty(new BigDecimal(objTrnDailyOCFLmtInfo.getIntOCFLmtQty()));
			objTrnRegionalWklyOcfLmt.setRegionalOcfUsgQty(new BigDecimal(0));
			objTrnRegionalWklyOcfLmt.setUpdtdBy(B000031Constants.BATCHID);
			objTrnRegionalWklyOcfLmt.setCrtdBy(B000031Constants.BATCHID);
			
			try{
				    
					entityManager.merge(objTrnRegionalWklyOcfLmt);
					if (insUpt == 1){
						
						String[] strMsgParams = {B000031Constants.PRMTR1,PDConstants.UPDATED,B000031Constants.RGNL_WKLY_TBL};
						CommonUtil.logMessage(PDMessageConsants.M00163, CONSTANT_V4 , strMsgParams);	
						
					}
					else {
						String[] strMsgParams = {B000031Constants.PRMTR1,PDConstants.INSERTED,B000031Constants.RGNL_WKLY_TBL};
						CommonUtil.logMessage(PDMessageConsants.M00163, CONSTANT_V4 , strMsgParams);
				
					}
			}
			catch(Exception e){
				LOG.info(e);
				String[] strMsgParams = {B000031Constants.PRMTR1,PDConstants.FAILED,B000031Constants.RGNL_WKLY_TBL};
				CommonUtil.logMessage(PDMessageConsants.M00164, CONSTANT_V4 , strMsgParams);
				CommonUtil.stopBatch();
				}
			}
		
		//P0004.1
		
		List<BuyerGrpInfo> lstBuyerGrpInfo = null;
		
		for (TrnDailyOCFLmtInfo objTrnDailyOCFLmtInfo : lstTrnDailyOCFLmtInfo){
			
			lstBuyerGrpInfo = objWeeklyOCFRepository.getByrGrpInfo(this.getPorCd(),objTrnDailyOCFLmtInfo.getStrOCFRgnCd() ,objTrnDailyOCFLmtInfo.getStrOCFByrGrpCd() , entityManager);
			
			if (lstBuyerGrpInfo.isEmpty()){
				String[] strMsgParams = {B000031Constants.PRMTR1,B000031Constants.BYR_GRP_CD,this.getPorCd(),PDConstants.MST_BUYER};
				CommonUtil.logMessage(PDMessageConsants.M00160, CONSTANT_V4 , strMsgParams);			
				CommonUtil.stopBatch();
			}
			
			for (BuyerGrpInfo objBuyerGrpInfo : lstBuyerGrpInfo ){
				
				LOG.info("The count of Buyer Group Code is .............."+ objBuyerGrpInfo.getCount());
				if (objBuyerGrpInfo.getCount() >1){
					
					LOG.info("StrByrGrpCd...."+objTrnDailyOCFLmtInfo.getStrByrGrpCd()+"OCFRegionCd........"+this.getOcfRegionCd()+"OCFByrGrpCd............"+objTrnDailyOCFLmtInfo.getStrOCFByrGrpCd());
					String[] strMsgParams = {B000031Constants.PRMTR1};				
					CommonUtil.logMessage(PDMessageConsants.M00333, CONSTANT_V4 , strMsgParams);			
					CommonUtil.stopBatch();
				} 
				
				
				// P0005- Initialize OCF Limit Qty 0 or "" 
				TrnBuyerGrpWklyOcfLmt objTrnBuyerGrpWklyOcfLmt = new TrnBuyerGrpWklyOcfLmt();
				TrnBuyerGrpWklyOcfLmtPK objTrnBuyerGrpWklyOcfLmtPK = new TrnBuyerGrpWklyOcfLmtPK();
				
				objTrnBuyerGrpWklyOcfLmtPK.setPorCd(this.getPorCd());
				objTrnBuyerGrpWklyOcfLmtPK.setProdMnth(objTrnDailyOCFLmtInfo.getStrPrdMnth());
				objTrnBuyerGrpWklyOcfLmtPK.setProdWkNo(objTrnDailyOCFLmtInfo.getStrPrdWkNo());
				objTrnBuyerGrpWklyOcfLmtPK.setCarSrs(objTrnDailyOCFLmtInfo.getStrCarSrs());
				objTrnBuyerGrpWklyOcfLmtPK.setFeatCd(objTrnDailyOCFLmtInfo.getStrFeatureCd());
				objTrnBuyerGrpWklyOcfLmtPK.setLineClass(objTrnDailyOCFLmtInfo.getStrLnCls());
				objTrnBuyerGrpWklyOcfLmtPK.setPlantCd(objTrnDailyOCFLmtInfo.getStrPrdPltCd());
				objTrnBuyerGrpWklyOcfLmtPK.setProdDayNo(objTrnDailyOCFLmtInfo.getStrPrdDayNo());
				objTrnBuyerGrpWklyOcfLmtPK.setBuyerGrpCd(objBuyerGrpInfo.getStrByrGrpCd());
				
				objTrnBuyerGrpWklyOcfLmt.setId(objTrnBuyerGrpWklyOcfLmtPK);
				objTrnBuyerGrpWklyOcfLmt.setOcfFrmeCd(objTrnDailyOCFLmtInfo.getStrOCFFrameCd());
				
				if ( PDConstants.FEATURE_CODE_00.equals(objTrnDailyOCFLmtInfo.getStrOCFFrameCd()) ){					
					objTrnBuyerGrpWklyOcfLmt.setBuyerGrpOcfLmtQty(new BigDecimal(0));
				}
				
				objTrnBuyerGrpWklyOcfLmt.setBuyerGrpOcfUsgQty(new BigDecimal(0));
				objTrnBuyerGrpWklyOcfLmt.setCrtdBy(B000031Constants.BATCHID);
				objTrnBuyerGrpWklyOcfLmt.setUpdtdBy(B000031Constants.BATCHID);
				try{
					entityManager.merge(objTrnBuyerGrpWklyOcfLmt);
				}
				catch(Exception e){
					LOG.info(e);
					String[] strMsgParams = {B000031Constants.PRMTR1,PDConstants.FAILED,PDConstants.TRN_BUYER_GRP_WKLY_OCF_LMT};
					CommonUtil.logMessage(PDMessageConsants.M00164, CONSTANT_V4 , strMsgParams);
					CommonUtil.stopBatch();
				}
				String[] strMsgParams = {B000031Constants.PRMTR1,PDConstants.UPDATED,PDConstants.TRN_BUYER_GRP_WKLY_OCF_LMT};
				CommonUtil.logMessage(PDMessageConsants.M00163, CONSTANT_V4 , strMsgParams);
				
			
				
				// P0006.1 - Extract Regional OCF Limit Qty
				
				qryPrmBn.setPorCd(this.getPorCd());
				qryPrmBn.setOcfRgnCd(objBuyerGrpInfo.getStrOcfRgnCd());
				qryPrmBn.setOrdrTkBsMnth(otbm);
				qryPrmBn.setOrdrTkBsWkNo(otbwn);
				
				List<TrnRgnWklyOCFInfo> lstTrnRgnWklyOCFInfo= objWeeklyOCFRepository.extractRgnWklyOCFLmt(
						qryPrmBn,objBuyerGrpInfo.getStrOcfByrGrpCd(),strPltCdLnCls[0],strPltCdLnCls[1],objTrnDailyOCFLmtInfo.getStrPrdDayNo(), entityManager);
				
				for (TrnRgnWklyOCFInfo objTrnRgnWklyOCFInfo : lstTrnRgnWklyOCFInfo){
				if (PDConstants.CONSTANT_ONE.equals(objBuyerGrpInfo.getStrAutoAllocFlag())){
				TrnBuyerGrpWklyOcfLmt objTrnBuyerGrpWklyOcfLmt1 = new TrnBuyerGrpWklyOcfLmt();
				TrnBuyerGrpWklyOcfLmtPK objTrnBuyerGrpWklyOcfLmtPK1 = new TrnBuyerGrpWklyOcfLmtPK();
				
				objTrnBuyerGrpWklyOcfLmtPK1.setPorCd(this.getPorCd());
				objTrnBuyerGrpWklyOcfLmtPK1.setProdMnth(objTrnRgnWklyOCFInfo.getStrPrdMnth());
				objTrnBuyerGrpWklyOcfLmtPK1.setProdWkNo(objTrnRgnWklyOCFInfo.getStrPrdWkNo());
				objTrnBuyerGrpWklyOcfLmtPK1.setCarSrs(objTrnRgnWklyOCFInfo.getStrCarSrs());
				objTrnBuyerGrpWklyOcfLmtPK1.setFeatCd(objTrnRgnWklyOCFInfo.getStrFeatureCd());
				objTrnBuyerGrpWklyOcfLmtPK1.setLineClass(strPltCdLnCls[1]);
				objTrnBuyerGrpWklyOcfLmtPK1.setPlantCd(strPltCdLnCls[0]);
				objTrnBuyerGrpWklyOcfLmtPK1.setProdDayNo(objTrnDailyOCFLmtInfo.getStrPrdDayNo());
				objTrnBuyerGrpWklyOcfLmtPK1.setBuyerGrpCd(objBuyerGrpInfo.getStrByrGrpCd());
				
				objTrnBuyerGrpWklyOcfLmt1.setId(objTrnBuyerGrpWklyOcfLmtPK1);
				
				TrnBuyerGrpWklyOcfLmt objTrnBuyerGrpWklyOcfLmtOld = entityManager.find(TrnBuyerGrpWklyOcfLmt.class, objTrnBuyerGrpWklyOcfLmtPK1);
				
				if ( objTrnBuyerGrpWklyOcfLmtOld != null){
					
					objTrnBuyerGrpWklyOcfLmt1 = objTrnBuyerGrpWklyOcfLmtOld;
				}
				LOG.info("Regional Wkly OCF Lmt Qty value is ...... "+objTrnRgnWklyOCFInfo.getIntRgnWklyOcfLmtQty());
				objTrnBuyerGrpWklyOcfLmt1.setBuyerGrpOcfLmtQty(new BigDecimal(objTrnRgnWklyOCFInfo.getIntRgnWklyOcfLmtQty()));
				objTrnBuyerGrpWklyOcfLmt1.setOcfFrmeCd(objTrnRgnWklyOCFInfo.getStrOcfFrmCd());
				objTrnBuyerGrpWklyOcfLmt1.setUpdtdBy(B000031Constants.BATCHID);
				objTrnBuyerGrpWklyOcfLmt1.setCrtdBy(B000031Constants.BATCHID);
				
					try{
						LOG.info("Before merge");
						entityManager.merge(objTrnBuyerGrpWklyOcfLmt1);
						String[] strMsgParams1 = {B000031Constants.PRMTR1,PDConstants.UPDATED,PDConstants.TRN_BUYER_GRP_WKLY_OCF_LMT};
						CommonUtil.logMessage(PDMessageConsants.M00163, CONSTANT_V4 , strMsgParams1);
					}
					catch(Exception e){
						LOG.info(e);
						String[] strMsgParams1 = {B000031Constants.PRMTR1,PDConstants.FAILED,PDConstants.TRN_BUYER_GRP_WKLY_OCF_LMT};
						CommonUtil.logMessage(PDMessageConsants.M00164, CONSTANT_V4 , strMsgParams1);
						CommonUtil.stopBatch();
						
					}
			}	
		}
	}
	}
		
		return objInputMapper;
		
	}

}
