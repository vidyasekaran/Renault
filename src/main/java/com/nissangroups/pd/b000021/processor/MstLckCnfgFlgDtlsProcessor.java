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
 * 12-11-2015		  z014433(RNTBCI)				 Updated to fix UT - Black box and JT defects
 *
 */
package com.nissangroups.pd.b000021.processor;


import java.util.ArrayList;
import java.util.List;

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
import com.nissangroups.pd.b000021.mapper.MstMnthlyOrdrTkBsPrdRowMapper;
import com.nissangroups.pd.b000021.util.B000021CommonUtil;
import com.nissangroups.pd.repository.MnthlyStgOpnClsRepository;
import com.nissangroups.pd.util.PDConstants;
import com.nissangroups.pd.util.PDMessageConsants;

import static com.nissangroups.pd.util.PDConstants.*;

/**
 * This class is used to Extract the NSC,RHQ,Exporter Flag based on the POR and STAGE_CD for the batch B000021.
 *
 * @author z014433
 */
@Configuration
@PropertySource(BATCH_CONFIG_CLASSPATH)
@Component(GET_LOCK_FLAG_DETAILS_PROCESSOR)
public class MstLckCnfgFlgDtlsProcessor implements
		ItemProcessor<MstMnthlyOrdrTkBsPrdRowMapper, MstLckCnfgFlgDtls> {
	
	/** Constant LOG. */
	private static final Log LOG = LogFactory.getLog
			(MstLckCnfgFlgDtlsProcessor.class);
	
	/** Variable environment. */
    @Autowired(required = false)
    Environment environment;

	/** Variable job param por. */
	String jobParamPor = null;
	
	/** Variable job param update flag. */
	String jobParamUpdtOnlyFlg = null;
	
	/** Variable job param stage code. */
	String jobParamStgCd = null;
	
	/** Variable job param stage status code. */
	String jobParamStgStsCd = null;
	
	/** Variable entity manager. */
	@PersistenceContext(name = PERSISTENCE_NAME)
	private EntityManager entityManager;
	
	/** Variable NSC, RHQ, Exporter flag details */
	List<Object[]> lckCnfgflgDtlsLst = new ArrayList<Object[]>();
	
	/** Variable MstLckCnfgFlgDtls obj. */
	MstLckCnfgFlgDtls flgDtlsObj = null;
	
	@Autowired(required = false)
	private MnthlyStgOpnClsRepository mnthOpnClsRepo;
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.batch.item.ItemProcessor#process(java.lang.Object)
	 */
	@Override
	public MstLckCnfgFlgDtls process(MstMnthlyOrdrTkBsPrdRowMapper item) throws Exception {
		LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR+" MstLckCnfgFlgDtls Inside process" );
		
		String ordrTkBsMnth = item.getId().getORDR_TAKE_BASE_MNTH();
		String porCd = item.getId().getPOR_CD();
		
		LOG.info("Job inputs Update Only Flags are  : "+jobParamUpdtOnlyFlg+ " and Stage Code  is :" +jobParamStgCd +" and stage status code is : "+jobParamStgStsCd);

    	/** Process Id - P0003 - Extract NSC, RHQ, Exporter Flag details*/
		lckCnfgflgDtlsLst = mnthOpnClsRepo.getNscRhqExpFlgDtls(item,jobParamUpdtOnlyFlg, jobParamStgCd, jobParamStgStsCd);  
		
		 //lckCnfgflgDtlsLst will have porCd,exptrFlg,nscFlg,ordrTrnsmssnFlg,rhqFlg,stgCd,stgSttsCd column values FROM MstLckCnfgurtn
    	
		if(lckCnfgflgDtlsLst == null || lckCnfgflgDtlsLst.isEmpty())
    	{
			
        	String[] messageParams = {PDConstants.BATCH_21_ID,PDConstants.M000190_LOCK_INFO,TBL_NM_LOCK_CONFIG_MST,jobParamPor+" , "+jobParamStgCd};
        	B000021CommonUtil.logMessage(PDMessageConsants.M00190, PDConstants.P0003, messageParams);
        	B000021CommonUtil.stopBatch();
    	}
		else
		{
			if (!lckCnfgflgDtlsLst.isEmpty()){
			for(Object[] lckFlgObject : lckCnfgflgDtlsLst) {
				flgDtlsObj =new MstLckCnfgFlgDtls();
				flgDtlsObj.setPorCd(lckFlgObject[0].toString());
				flgDtlsObj.setnSCFlg(lckFlgObject[1].toString());
				flgDtlsObj.setrHQFlg(lckFlgObject[2].toString());
				flgDtlsObj.setExpFlg(lckFlgObject[3].toString());
				flgDtlsObj.setOrdrTsmnFlg(lckFlgObject[4].toString());
				flgDtlsObj.setOrdrTkBsMnth(ordrTkBsMnth); 
			}
			} else {
				flgDtlsObj =new MstLckCnfgFlgDtls();
				flgDtlsObj.setPorCd(porCd);
				flgDtlsObj.setOrdrTkBsMnth(ordrTkBsMnth); 
			}
		}
		
		LOG.info("Lock Config Flag Details is : "+flgDtlsObj);
		
		LOG.info(DOLLAR+OUTSIDE_METHOD+DOLLAR);
		return flgDtlsObj; 
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