/*
 * System Name     :Post Dragon 
 * Sub system Name :Batch 
 * Function ID     :PST-DRG-B000021
 * Module          :O Ordering
 * Process Outline :Monthly Process Stage Open or Close 
 *
 * <Revision History>
 * Date       Name(Company Name)             Description 
 * ---------- ------------------------------ ---------------------
 * 28-10-2015  	  z014433(RNTBCI)               Initial Version
 * 12-11-2015		  z014433(RNTBCI)				 Updated to fix UT - Black box defects		
 *
 */
package com.nissangroups.pd.b000021.processor;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import com.nissangroups.pd.b000021.bean.MstLckCnfgFlgDtls;
import com.nissangroups.pd.b000021.util.B000021CommonUtil;
import com.nissangroups.pd.repository.MnthlyOrdrRepository;
import com.nissangroups.pd.repository.MnthlyStgOpnClsRepository;
import com.nissangroups.pd.util.CommonUtil;
import com.nissangroups.pd.util.PDConstants;
import com.nissangroups.pd.util.PDMessageConsants;

import static com.nissangroups.pd.util.PDConstants.*;


/**
 * This class is used to process to do the business logic for the batch B000021.
 *
 * @author z014433
 */
@Configuration
@PropertySource(BATCH_CONFIG_CLASSPATH)
@Component(B000021_PROCESSOR)
public class MnthlyStgOpnClsProcessor implements
		ItemProcessor<MstLckCnfgFlgDtls, MstLckCnfgFlgDtls> {
	
	/** Constant LOG. */
	private static final Log LOG = LogFactory.getLog
			(MnthlyStgOpnClsProcessor.class);
	
	/** Variable entity manager. */
	@PersistenceContext(name = PERSISTENCE_NAME)
	private EntityManager entityManager;
	
	/** Variable environment. */
    @Autowired(required = false)
    Environment environment;

	/** Variable job param por. */
	String jobParamPor = null;
	
	/** Variable job param update flag. */
	String jobParamUpdtOnlyFlg = null;
	
	/** Variable job param system lock flag. */
	String jobParamSysLckFlg = null;
	
	/** Variable job param stage code. */
	String jobParamStgCd = null;
	
	/** Variable job param stage status code. */
	String jobParamStgStsCd = null;
	
	/** Variable job param stage update flag. */
	String jobParamStgUpdtFlg = null;
	
	/** Variable production stage code. */
	List<String> prdStgCdVal = null;
	
	/** Variable car series horizon details. */
	List<Object[]> crSrsList = new ArrayList<Object[]>();
	
	/** Variable NSC, RHQ, Exporter flag details */
	List<Object[]> flgDtlsLst = new ArrayList<Object[]>();
	
	/** Variable Car Series, Buyer Group */
	List<Object[]> crSrsByrGrpDtls = new ArrayList<Object[]>();
	
	/** Variable car series, buyer group combination. */
	List<Object[]> allCrSrsByrGrpDtls = new ArrayList<Object[]>();
	
	/** Variable unique car series, buyer group code key. */
	String uniqueCrSrsByrGrpKey = null;
	
	/** Variable uniqueCrSrsByrGrpMap */
	Map<Object, Object> uniqueCrSrsByrGrpMap = new HashMap<>();

	/** Variable Unique Car Series, Buyer Group */
	List<Object[]> uniqueCrSrsByrGrpDtls = new ArrayList<Object[]>();
	
	/** Variable Unique Car Series, Buyer Group records*/
	List<Object[]> uniqueRcds = new ArrayList<Object[]>();
	
	@Autowired(required = false)
	private MnthlyStgOpnClsRepository mnthOpnClsRepo;	
	
	@Autowired(required = false)
	private MnthlyOrdrRepository mnthOrdrRepo;
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.batch.item.ItemProcessor#process(java.lang.Object)
	 */
	@Override
	public MstLckCnfgFlgDtls process(MstLckCnfgFlgDtls lckCngDtls) throws Exception {
		LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR );
		/** Temp variable get hold the car series record processing **/
		int crSrsIterationCnt = 0; 
		
		String ordrTkBsMnth = lckCngDtls.getOrdrTkBsMnth();
		
		crSrsList = extractCarSrsDtls(jobParamPor);
		
		prdStgCdVal = extractPrdStgCdVal(jobParamPor);
		
		 if (!crSrsList.isEmpty()){
			String maxPrdMnth = null;
			
			for (Object[] carSrsobj : crSrsList) {
				
				LOG.info("Processing Car Series List - Record Number :: "+crSrsIterationCnt);
				crSrsIterationCnt++; 
				
				String crSrsStrVal = CommonUtil.convertObjectToString(carSrsobj[2]);
				
				carSrsobj[3] = validateHrzn(crSrsStrVal,carSrsobj[3]);
				
				/** Process Id - P0002.1 calculate max production months*/
				maxPrdMnth = CommonUtil.prdMnthCal(ordrTkBsMnth,carSrsobj[3].toString());
				LOG.info("Max production month for Car series : "+carSrsobj[2].toString()+" and Horizon : " +carSrsobj[3].toString() +" is " +maxPrdMnth);
				
				/** Process Id - P0002.2 Extract Car Series and Buyer Group Code*/
				crSrsByrGrpDtls = getCarSrsByrGrpRslt(carSrsobj,maxPrdMnth,ordrTkBsMnth,prdStgCdVal);
				
				LOG.info("All Car Series , Buyer group code combination count :   "+allCrSrsByrGrpDtls.size());
			}
			
				/** Process Id - P0004 */
				saveExtractedLckDtls(allCrSrsByrGrpDtls,lckCngDtls);
		} 
		 
		LOG.info(DOLLAR+OUTSIDE_METHOD+DOLLAR);
		return lckCngDtls; 
	}

	/**
	 * @param carSrsobj
	 * @param maxPrdMnth
	 * @param ordrTkBsMnth
	 * @param prdStgCdVal
	 * @return car series, buyer group list
	 * 
	 * This method is used to extract the car series and buyer group code details from spec master table
	 */
	private List<Object[]> getCarSrsByrGrpRslt(Object[] carSrsobj,String maxPrdMnth, String ordrTkBsMnth, List<String> prdStgCdVal) {
		
		LOG.info(DOLLAR+INSIDE_METHOD+DOLLAR);
		
		 //crSrsByrGrpDtls will have distinct CAR_SRS, BUYER_GRP_CD column values FROM Spec Master tables
		crSrsByrGrpDtls = mnthOpnClsRepo.getCarSeriesBuyerGrpDtls(carSrsobj,maxPrdMnth,ordrTkBsMnth,prdStgCdVal );
		LOG.info("Extracted Car series/Buyer Group Code list size is : " +crSrsByrGrpDtls.size());
		
		if(crSrsByrGrpDtls == null || crSrsByrGrpDtls.isEmpty())
    	{
        	String[] messageParams = {PDConstants.BATCH_21_ID,"CAR SERIES : "+carSrsobj[2].toString(),TBL_NM_ORDERABLE_END_ITEM_SPEC_MST,"POR CD : "+carSrsobj[0].toString()+" ,"+"PRODUCTION MONTH : "+maxPrdMnth};
        	B000021CommonUtil.logMessage(PDMessageConsants.M00190, PDConstants.P0002, messageParams);
        	
    	} else
    		/** Add to existing buyer code list **/
    		allCrSrsByrGrpDtls.addAll(crSrsByrGrpDtls);	
		
		LOG.info(DOLLAR+OUTSIDE_METHOD+DOLLAR);
		
		return allCrSrsByrGrpDtls;
	}

	/**
	 * @param porCd
	 * @return value1
	 * 
	 * This method is to extract value1 from paramter mst table
	 */
	private List<String> extractPrdStgCdVal(String porCd) {
		
		LOG.info(DOLLAR+INSIDE_METHOD+DOLLAR);
		
		/** Process Id - P0002.2  Extract Production Stage Code */
		if(prdStgCdVal== null){
			 prdStgCdVal = mnthOrdrRepo.extractValFrmMstPrmtr(PDConstants.PRODUCTION_STAGE_CD,porCd);
			 if(prdStgCdVal!= null)
			 LOG.info("Extracted Production stage code parameter value is  : " +prdStgCdVal);
			 else {
			 /** Added for Unit Test case Black box Defect for PD-[B000021]-UT-012
				 *  This error scenario is NOT in design document**/
				 LOG.info(PDConstants.NOORDRFOUND+" in "+MESSAGE_MST_PARAMETER);
					B000021CommonUtil.stopBatch();
			 }
		}
		
		LOG.info(DOLLAR+OUTSIDE_METHOD+DOLLAR);
		
		return prdStgCdVal;
	}


	/**
	 * @param porCd
	 * @return car series details
	 * 
	 * This method is used to extract the car series details from por car series mst table
	 */
	private List<Object[]> extractCarSrsDtls(String porCd) {
		
		LOG.info(DOLLAR+INSIDE_METHOD+DOLLAR);
		
		 /** Process Id - P0002.1  Extract car series details */
		if(crSrsList== null || crSrsList.isEmpty()){
			crSrsList = mnthOrdrRepo.chkHrzn(porCd);
			if(!crSrsList.isEmpty()){
			LOG.info("Extracted Car series list size is : " +crSrsList.size());
			}
			else
			{
				/** Added for Unit Test case Black box Defect for PD-[B000021]-UT-009 
				 *  This error scenario is NOT in design document**/
				LOG.info(PDConstants.NOORDRFOUND+" in "+MESSAGE_POR_CAR_SERIES_MST);
				B000021CommonUtil.stopBatch();
			}
		}
		
		LOG.info(DOLLAR+OUTSIDE_METHOD+DOLLAR);
		return crSrsList;
	}


	/**
	 * @param crSrsStrVal
	 * @param carSrsobject
	 * @return por horizon
	 * 
	 * This method is used to extract the por horizon value (if car series horizon is null)
	 */
	private Object validateHrzn(String crSrsStrVal, Object carSrsobject) {
		
		LOG.info(DOLLAR+INSIDE_METHOD+DOLLAR);
		
		if (carSrsobject == null) {
			// This error message not in DLD
				String[] messageParams = {PDConstants.BATCH_21_ID,PDConstants.CAR_SERIES_HORIZON,jobParamPor,crSrsStrVal,PDConstants.TBL_NM_POR_CAR_SERIES_MST, PDConstants.POR_HORIZON_EXTRACTION};
				
            	B000021CommonUtil.logMessage(PDMessageConsants.M00161, PDConstants.P0002, messageParams);
				
				/** Extract por horizon */
			  LOG.info("Extracted Car Series Horizon is null, hence POR Horizon will be extracted");
			  carSrsobject = mnthOrdrRepo.getPorHorizon(jobParamPor);
				LOG.info("POR Horizon value is : " +carSrsobject);
			}
		//till here not here
		LOG.info(DOLLAR+OUTSIDE_METHOD+DOLLAR);

		return carSrsobject;
	}


	/**
	 * @param crSrsByrGrpCdDtls
	 * @param lckCngDtls
	 * @param maxPrdMnth
	 * 
	 * This method is to Store the Extracted information in Monthly Order Take Lock Mst table
	 */
	private void saveExtractedLckDtls(List<Object[]> allCrSrsByrGrpDtlsLst, MstLckCnfgFlgDtls lckCngDtls) {
		
		LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR );
		
			uniqueRcds = getUniqueCrSrsByrGrpDtls(allCrSrsByrGrpDtlsLst);
			
			LOG.info("Unique Records size  is : " +uniqueRcds.size());
		
    		/** Process Id - P0004 - Store extracted information to monthly order take lock mst*/
			LOG.info("System Lock Flag is : " +jobParamSysLckFlg);

    		if(jobParamSysLckFlg.equalsIgnoreCase(PDConstants.NO) && !uniqueRcds.isEmpty())
    		{
    		mnthOpnClsRepo.saveLckFlgDtls(lckCngDtls, uniqueRcds, jobParamUpdtOnlyFlg, jobParamStgCd);  
    		} 
		
		LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR );

	}

	/**
	 * @param allCrSrsByrGrpDtlsLst
	 * @return uniqueCrSrsByrGrpDtls list
	 * 
	 * This method is to get the unique car series buyer group combination (removing duplicates)
	 */
	private List<Object[]> getUniqueCrSrsByrGrpDtls(List<Object[]> allCrSrsByrGrpDtlsLst) {
		
		LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR );
		
		LOG.info("All Car Series Buyer Group Code combination (duplicates inclusive) size  is : " +allCrSrsByrGrpDtlsLst.size());
		
		if (allCrSrsByrGrpDtlsLst!= null && !allCrSrsByrGrpDtlsLst.isEmpty()){
			
			for(Object[] obj : allCrSrsByrGrpDtlsLst){
				
				if(obj[1]!=null){
				
				uniqueCrSrsByrGrpKey = obj[0] + SINGLE_QUOTE + obj[1];

				if (!uniqueCrSrsByrGrpMap.containsKey(uniqueCrSrsByrGrpKey)) {
					uniqueCrSrsByrGrpMap.put(uniqueCrSrsByrGrpKey, obj);
					uniqueCrSrsByrGrpDtls.add(obj);
				}
			}
		}
			
			LOG.info("UNIQUECar Series Buyer Group Code combination size  is : " +uniqueCrSrsByrGrpDtls.size());
			
			for (Object[] obj : uniqueCrSrsByrGrpDtls){
				LOG.info("Unique Car Series  is : :" +obj[0] +  " and its Buyer Group Code  is : :" +obj[1]);
			}
		}
		
		LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR );
		
		return uniqueCrSrsByrGrpDtls;
	}

	/**
	 * Gets the jobParamPor
	 *
	 * @return the jobParamPor
	 */
	
	public String getJobParamPor() {
		return jobParamPor;
	}

	/**
	 * Sets the jobParamPor
	 *
	 * @param jobParamPor the jobParamPor to set
	 */
	
	public void setJobParamPor(String jobParamPor) {
		this.jobParamPor = jobParamPor;
	}

	/**
	 * Gets the jobParamUpdtOnlyFlg
	 *
	 * @return the jobParamUpdtOnlyFlg
	 */
	
	public String getJobParamUpdtOnlyFlg() {
		return jobParamUpdtOnlyFlg;
	}

	/**
	 * Sets the jobParamUpdtOnlyFlg
	 *
	 * @param jobParamUpdtOnlyFlg the jobParamUpdtOnlyFlg to set
	 */
	
	public void setJobParamUpdtOnlyFlg(String jobParamUpdtOnlyFlg) {
		this.jobParamUpdtOnlyFlg = jobParamUpdtOnlyFlg;
	}

	/**
	 * Gets the jobParamSysLckFlg
	 *
	 * @return the jobParamSysLckFlg
	 */
	
	public String getJobParamSysLckFlg() {
		return jobParamSysLckFlg;
	}

	/**
	 * Sets the jobParamSysLckFlg
	 *
	 * @param jobParamSysLckFlg the jobParamSysLckFlg to set
	 */
	
	public void setJobParamSysLckFlg(String jobParamSysLckFlg) {
		this.jobParamSysLckFlg = jobParamSysLckFlg;
	}

	/**
	 * Gets the jobParamStgCd
	 *
	 * @return the jobParamStgCd
	 */
	
	public String getJobParamStgCd() {
		return jobParamStgCd;
	}

	/**
	 * Sets the jobParamStgCd
	 *
	 * @param jobParamStgCd the jobParamStgCd to set
	 */
	
	public void setJobParamStgCd(String jobParamStgCd) {
		this.jobParamStgCd = jobParamStgCd;
	}

	/**
	 * Gets the jobParamStgStsCd
	 *
	 * @return the jobParamStgStsCd
	 */
	
	public String getJobParamStgStsCd() {
		return jobParamStgStsCd;
	}

	/**
	 * Sets the jobParamStgStsCd
	 *
	 * @param jobParamStgStsCd the jobParamStgStsCd to set
	 */
	
	public void setJobParamStgStsCd(String jobParamStgStsCd) {
		this.jobParamStgStsCd = jobParamStgStsCd;
	}

	/**
	 * Gets the jobParamStgUpdtFlg
	 *
	 * @return the jobParamStgUpdtFlg
	 */
	
	public String getJobParamStgUpdtFlg() {
		return jobParamStgUpdtFlg;
	}

	/**
	 * Sets the jobParamStgUpdtFlg
	 *
	 * @param jobParamStgUpdtFlg the jobParamStgUpdtFlg to set
	 */
	
	public void setJobParamStgUpdtFlg(String jobParamStgUpdtFlg) {
		this.jobParamStgUpdtFlg = jobParamStgUpdtFlg;
	}

	/**
	 * Gets the entityManager
	 *
	 * @return the entityManager
	 */
	
	public EntityManager getEntityManager() {
		return entityManager;
	}

	/**
	 * Sets the entityManager
	 *
	 * @param entityManager the entityManager to set
	 */
	
	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

}