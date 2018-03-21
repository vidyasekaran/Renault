/*
 * System Name       : Post Dragon 
 * Sub system Name : Interface
 * Function ID            : PST-DRG-I000030
 * Module                  : Ordering		
 * Process Outline     : Receive Monthly Order  Interface from NSC(Standard)															
 *
 * <Revision History>
 * Date       Name(Company Name)             Description 
 * ---------- ------------------------------ ---------------------
 * 23-10-2015  	  z014433(RNTBCI)               Initial Version
 * 06-11-2015	      z014433(RNTBCI)				 Updated spec destination code column # from 16 to 6 to fix Redmine bug # 1368
 * 06-11-2015	      z014433(RNTBCI)				 Added trim() to order quantity to fix Redmine Bug # 1373
 * 12-11-2015		  z014433(RNTBCI)	             Assigned col23 to dragon individual order number to fix Redmine Bug # 1430
 */
package com.nissangroups.pd.processor;
import static com.nissangroups.pd.util.PDConstants.INSIDE_METHOD;
import static com.nissangroups.pd.util.PDConstants.OUTSIDE_METHOD;
import static com.nissangroups.pd.util.PDConstants.PERSISTENCE_NAME;
import static com.nissangroups.pd.util.PDConstants.DOLLAR;

import java.math.BigDecimal;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.AfterStep;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.batch.item.ItemProcessor;

import com.nissangroups.pd.common.CmnAfterStepDtls;
import com.nissangroups.pd.model.CmnInterfaceData;
import com.nissangroups.pd.model.TrnMnthlyOrdrIf;
import com.nissangroups.pd.model.TrnMnthlyOrdrIfPK;
import com.nissangroups.pd.util.PDConstants;
import com.nissangroups.pd.util.CommonUtil;

/**
 * This class Extract data from CommonLayerData Mst Set to the TrnMnthlyOrdrIf.
 *
 * @author z014433
 */
public class I000030Processor implements ItemProcessor<CmnInterfaceData, TrnMnthlyOrdrIf> {
	
	/** Constant LOG. */
	private static final  Log LOG = LogFactory.getLog(I000030Processor.class
			.getName());
	
	/** Variable entity manager. */
	@PersistenceContext(name = PERSISTENCE_NAME)
	private EntityManager entityManager;

	/** Variable interface id. */
	private static String interfaceId = null;
	
	/** Variable list Common Util. */
    private CommonUtil commonUtil;
	
	/**
	 * Before step.
	 *
	 * @param stepExecution the step execution
	 */
	/*
	 * Before Step process
	 * Process No-P0002
	 * Get the corresponding  Variables from the PDConstants
	 * 
	 */
	@BeforeStep
	public void beforeStep(StepExecution stepExecution) {
		LOG.info(DOLLAR+INSIDE_METHOD+DOLLAR);
		interfaceId = stepExecution.getJobParameters().getString(PDConstants.INTERFACE_FILE_ID);
		LOG.info(DOLLAR+OUTSIDE_METHOD+DOLLAR + interfaceId);
	}
	
	
	/* (non-Javadoc)
	 * @see org.springframework.batch.item.ItemProcessor#process(java.lang.Object)
	 */
	/*
	 * Insert the extracted data from the Common Layer Data
	 * Process No- P0003
	 * @param - item - Object Data Type JpaPagingItemReader Class Start
	 * Return - Record from CommonLayerData and set into TrnMnthlyOrdrIf
	 */
	@Override
	public TrnMnthlyOrdrIf  process(CmnInterfaceData cmnInterfaceData) throws Exception {
		
		LOG.info(DOLLAR+INSIDE_METHOD+DOLLAR+cmnInterfaceData.toString());
		
		TrnMnthlyOrdrIf trnMnthlyOrdrIf=new TrnMnthlyOrdrIf();
		TrnMnthlyOrdrIfPK trnMnthlyOrdrIfPK=new TrnMnthlyOrdrIfPK();
		commonUtil = new CommonUtil();
		
		BigDecimal ordrQty = CommonUtil.convertStringToBigDecimal(cmnInterfaceData.getCol19().trim()); //Redmine Bug # 1373

		trnMnthlyOrdrIfPK.setIfFileId(interfaceId);
		trnMnthlyOrdrIfPK.setRowNo(cmnInterfaceData.getId().getRowNo()); 
		trnMnthlyOrdrIfPK.setSeqNo(cmnInterfaceData.getId().getSeqNo());
		
		trnMnthlyOrdrIf.setPorCd(cmnInterfaceData.getCol1());
		trnMnthlyOrdrIf.setCarSrs(cmnInterfaceData.getCol2());
		trnMnthlyOrdrIf.setBuyerCd(cmnInterfaceData.getCol3());
		trnMnthlyOrdrIf.setAppldMdlCd(cmnInterfaceData.getCol4());
		trnMnthlyOrdrIf.setPackCd(cmnInterfaceData.getCol5());
		trnMnthlyOrdrIf.setSpecDestnCd(cmnInterfaceData.getCol6()); //Redmine Bug # 1368
		trnMnthlyOrdrIf.setAddSpecCd(cmnInterfaceData.getCol7());
		trnMnthlyOrdrIf.setExtClrCd(cmnInterfaceData.getCol8());
		trnMnthlyOrdrIf.setIntClrCd(cmnInterfaceData.getCol9());
		trnMnthlyOrdrIf.setOrdrtkBasePrdType(PDConstants.CF_CONSTANT_M);
		trnMnthlyOrdrIf.setOrdrtkBasePrd(cmnInterfaceData.getCol11()); //order take base month
		trnMnthlyOrdrIf.setProductionPeriodType(PDConstants.CF_CONSTANT_M); 
		trnMnthlyOrdrIf.setProductionPeriod(cmnInterfaceData.getCol13()); //production month
		trnMnthlyOrdrIf.setOfflinePlanDt(cmnInterfaceData.getCol14());
		trnMnthlyOrdrIf.setProductionOrderStageCd(cmnInterfaceData.getCol15());
		trnMnthlyOrdrIf.setPotCd(cmnInterfaceData.getCol16());
		trnMnthlyOrdrIf.setProdPlntCd(cmnInterfaceData.getCol17());
		trnMnthlyOrdrIf.setLineClass(cmnInterfaceData.getCol18());
		trnMnthlyOrdrIf.setOrdrQty(ordrQty);
		trnMnthlyOrdrIf.setDueDateFrm(cmnInterfaceData.getCol20());
		trnMnthlyOrdrIf.setDueDateTo(cmnInterfaceData.getCol21());
		trnMnthlyOrdrIf.setProdOrdrNo(cmnInterfaceData.getCol22()); // local production order number
		trnMnthlyOrdrIf.setDragonIndvdlOrdrNo(cmnInterfaceData.getCol23()); //DRAGON_INDVDL_ORDR_NO
		trnMnthlyOrdrIf.setCrtdBy(interfaceId);
		trnMnthlyOrdrIf.setCrtdDt(commonUtil.currentDateTime());
		trnMnthlyOrdrIf.setUpdtdBy(interfaceId);
		trnMnthlyOrdrIf.setUpdtdDt(commonUtil.currentDateTime());
		
		/** All the below fields are present in database table but not in IF Layout, hence values will be set as null
		ERR_CD
        FRZN_TYPE_CD 
		OSEI_ID
		BUYER_GRP_CD
		OEI_BUYER_ID
		MS_QTY
		 *
		 */
		trnMnthlyOrdrIf.setId(trnMnthlyOrdrIfPK);
		
        LOG.info(DOLLAR+OUTSIDE_METHOD+DOLLAR);
		return trnMnthlyOrdrIf;
	}
	
	
	/**
 * Gets the entity manager.
 *
 * @return the entity manager
 */

	public EntityManager getEntityManager() {
		LOG.info(DOLLAR+INSIDE_METHOD+DOLLAR);
		return entityManager;
	}
	
	/**
	 * Sets the entity manager.
	 *
	 * @param entityManager the new entity manager
	 */
	
	public void setEntityManager(EntityManager entityManager) {
		LOG.info(DOLLAR+INSIDE_METHOD+DOLLAR);
		this.entityManager = entityManager;
	}
	
	/**
	 * To get the count of row affected in Each Step.
	 *
	 * @param stepExecution the step execution
	 */
	@AfterStep
	public void afterStep(StepExecution stepExecution) {
		
		LOG.info(DOLLAR+INSIDE_METHOD+DOLLAR);
		CmnAfterStepDtls.retrieveAfterStepDtls(stepExecution);
		 LOG.info(DOLLAR+OUTSIDE_METHOD+DOLLAR);
	}

}
