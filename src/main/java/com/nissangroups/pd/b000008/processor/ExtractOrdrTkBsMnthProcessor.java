/*
 * System Name     :Post Dragon 
 * Sub system Name :Batch
 * Function ID     :PST-DRG-B000008
 * Module          :Monthly Ordering					
 * Process Outline :Create Monthly Order Base Data
 *
 * <Revision History>
 * Date       Name(Company Name)             Description 
 * ---------- ------------------------------ ---------------------
 * 21-10-2015  	  z015060(RNTBCI)               New Creation
 *
 */
package com.nissangroups.pd.b000008.processor;

import static com.nissangroups.pd.util.PDConstants.BATCH_CONFIG_CLASSPATH;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import com.nissangroups.pd.b000008.output.B000008Output;
import com.nissangroups.pd.b000008.output.B000008P1Output;
import com.nissangroups.pd.b000008.output.ReaderOutput;
import com.nissangroups.pd.b000008.process.ExtractHrznProcess;
import com.nissangroups.pd.b000008.process.OverLapPrdProcess;
import com.nissangroups.pd.b000008.process.PrdMnthCalProcess;

import static com.nissangroups.pd.b000008.util.B000008Constants.*;

import com.nissangroups.pd.model.MstMnthOrdrTakeBasePd;

import static com.nissangroups.pd.util.PDConstants.*;

import com.nissangroups.pd.b000008.output.B000008ParamOutput;

/**
 * The Class ExtractOrdrTkBsMnthProcessor.
 *
 * @author z015060
 */

@Configuration
@PropertySource(BATCH_CONFIG_CLASSPATH)
@Component(BATCH_ID_B000008)
public class ExtractOrdrTkBsMnthProcessor implements
ItemProcessor<MstMnthOrdrTakeBasePd, B000008Output> {
	
	/** Constant LOG */
	private static final Log LOG = LogFactory.getLog(ExtractOrdrTkBsMnthProcessor.class
			.getName());
			
	/** Constant porCd */
	private String porCd;
	
	/** Constant updateOnlyFlag */
	private String updateOnlyFlg;
	
	/** Constant overlapMsQtyFlag */
	private String overlapMsQtyFlg;
	
	/** Constant prdStgeCd */
	private String prdStgeCd;
	
	/** Variable entity manager. */
	@PersistenceContext(name = PERSISTENCE_NAME)
	private EntityManager entityManager;
	
	@Autowired(required = false)
	private OverLapPrdProcess objOverLapPrdProcess;
	
	@Autowired(required = false)
	private ExtractHrznProcess objExtractHrznProcess;
	
	/**
	 * This method is to Extract OrderTakeBaseMonth and calculating Maximum production month 
	 * for the monthly order base data 
	 * ProcessId : P0002.1,P0003.1,P0003.2,P0003.3
	 * @param MstMnthOrdrTakeBasePd the item
	 * @return the B000008Output class
	 * @throws Exception the exception
	 */
	@Override
	public B000008Output process(MstMnthOrdrTakeBasePd item) throws Exception {
		
		LOG.info("ExtractOrdrTkBsMnthProcessor "+item.getId().getOrdrTakeBaseMnth());
		/**Initializes the output classes */
		B000008Output objB000008Output= new B000008Output();
		B000008ParamOutput objB000008ParamOutput= new B000008ParamOutput();
		ReaderOutput objReaderOutput= new ReaderOutput();
		B000008P1Output objB000008P1Output = new B000008P1Output();
		
		/** Process Id P0002  setting values of the reader output to the ReaderOutput class */
		objReaderOutput.setPorCd(porCd);
		objReaderOutput.setPrdStgCd(prdStgeCd);
		objReaderOutput.setStgCd(item.getStageCd());
		objReaderOutput.setOrdrTkBsMnth(item.getId().getOrdrTakeBaseMnth());
		
		LOG.info("--------------"+objReaderOutput.getOrdrTkBsMnth()+"-----------"+objReaderOutput.getStgCd());
		/** Process Id P0001  setting values of the input argument to the B000008ParamOutput class */
		objB000008ParamOutput.setPorCd(porCd);	
		if(prdStgeCd == CONSTANT_ZERO){
			if(item.getStageCd().equals(STG_CD_D1) || item.getStageCd().equals(STG_CD_D2)){
				prdStgeCd=PROD_ORDER_STAGE_CODE_DRAFT;
			}else{
				prdStgeCd=PROD_ORDER_STAGE_CODE_FINAL;
			}
		}
		objB000008ParamOutput.setPrdStgCd(prdStgeCd);
		objB000008ParamOutput.setUpdateOnlyFlg(updateOnlyFlg);
		objB000008ParamOutput.setOverlapMsQtyFlg(overlapMsQtyFlg);
		
		/** Process Id P0002.1 Check whether  Overlap exists  only for UpdateOnlyFlag N*/
		if(objB000008ParamOutput.getUpdateOnlyFlg().equals(N)){
		objB000008P1Output =objOverLapPrdProcess.executeProcess(objB000008P1Output,item,prdStgeCd);
	    }
		
		/** Process Id P0003.1, P0003.2 Extract the Horizon value 
		 * output porCd, prodFmyCd, .carSrs, carSrsOrdrHrzn , OrdrTakBsMnth, StgCd */
		objB000008P1Output=objExtractHrznProcess.executeProcess(objB000008P1Output,item,objB000008ParamOutput);
		
		/** Process Id P0003.3 Maximum Production Month Calculation 
		 * output PORCD, productionFamilyCode, carSeries, MaximumProductionMonth,
		 *  OrderTakeBaseMonth, StageCode,ProductionStageCode */
		PrdMnthCalProcess objPrdMnthCal= new PrdMnthCalProcess(objB000008ParamOutput);
		objB000008P1Output=objPrdMnthCal.executeProcess(objB000008P1Output);
		
		/** setting Processor1 Output class to the main Output class */
		objB000008Output.setObjB000008P1Output(objB000008P1Output);
		objB000008Output.setObjB000008ParamOutput(objB000008ParamOutput);
		objB000008Output.setObjReaderOutput(objReaderOutput);
		return objB000008Output;
	}

	/** gets the porCd and @return the porCd */
	public String getPorCd() {
		return porCd;
	}
	
	/** sets the porCd manager and @return the porCd */
	public void setPorCd(String porCd) {
		this.porCd = porCd;
	}

	/** gets the entity manager and @return the entity manager */
	public EntityManager getEntityManager() {
		return entityManager;
	}

	/** Sets the entity manager and @return the entity manager */
	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	/** gets the updateOnlyFlg and @return the updateOnlyFlg */
	public String getUpdateOnlyFlg() {
		return updateOnlyFlg;
	}

	/** sets the updateOnlyFlg and @return the updateOnlyFlg */
	public void setUpdateOnlyFlg(String updateOnlyFlg) {
		this.updateOnlyFlg = updateOnlyFlg;
	}

	/** sets the overlapMsQtyFlg and @return the overlapMsQtyFlg */
	public String getOverlapMsQtyFlg() {
		return overlapMsQtyFlg;
	}

	/** sets the overlapMsQtyFlg and @return the overlapMsQtyFlg */
	public void setOverlapMsQtyFlg(String overlapMsQtyFlg) {
		this.overlapMsQtyFlg = overlapMsQtyFlg;
	}

	/** sets the prdStgeCd and @return the prdStgeCd */
	public String getPrdStgeCd() {
		return prdStgeCd;
	}

	/** sets the prdStgeCd and @return the prdStgeCd */
	public void setPrdStgeCd(String prdStgeCd) {
		this.prdStgeCd = prdStgeCd;
	}
	
	
	
}
