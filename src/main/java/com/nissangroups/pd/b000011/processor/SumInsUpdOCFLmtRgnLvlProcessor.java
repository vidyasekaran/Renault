/*
 * System Name     :Post Dragon 
 * Sub system Name :Batch
 * Function ID     :PST-DRG-B000011
 * Module          : Ordering					
 * Process Outline : RHQ/NSC-wise Volume/OCF Allocation
 *
 * <Revision History>
 * Date       Name(Company Name)             Description 
 * ---------- ------------------------------ ---------------------
 * 04-11-2015  	  z015060(RNTBCI)               New Creation
 *
 */
package com.nissangroups.pd.b000011.processor;

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

import static com.nissangroups.pd.b000011.util.B000011Constants.*;
import static com.nissangroups.pd.util.PDConstants.*;

import com.nissangroups.pd.b000011.output.B000011Output;
import com.nissangroups.pd.b000011.output.B000011ParamOutput;
import com.nissangroups.pd.b000011.process.SumOCFLmtRgnLvlProcess;
import com.nissangroups.pd.b000011.process.UptOCFLmtByrGrpLvlProcess;

/**
 * The Class SumInsUpdOCFLmtRgnLvlProcessor.
 *
 * @author z015060
 */

@Configuration
@PropertySource(BATCH_CONFIG_CLASSPATH)
@Component(BATCH_ID_B000011)
public class SumInsUpdOCFLmtRgnLvlProcessor implements
ItemProcessor<String, B000011Output> {
	
	/** Constant LOG */
	private static final Log LOG = LogFactory.getLog(SumInsUpdOCFLmtRgnLvlProcessor.class
			.getName());
			
	/** Constant porCd */
	private String porCd;
	
	/** Constant prcsOlyFlg */
	private String prcsOlyFlg;

	/** Variable entity manager. */
	@PersistenceContext(name = PERSISTENCE_NAME)
	private EntityManager entityManager;
	
	@Autowired(required = false)
	private SumOCFLmtRgnLvlProcess objSumOCFLmtRgnLvlProcess;
	
	@Autowired(required = false)
	private UptOCFLmtByrGrpLvlProcess objUptOCFLmtByrGrpLvlProcess;
	
	/**
	 * Process P0001,P0002,P0003
	 * Extract the order take base period,Extract the summarized Regional level OCF Limit from MONTHLY OCF TRN 
	 * and update ocf_limit TO regional_ocf_lmt_monthly_trn
	 * @param item
	 * @return
	 * @throws Exception
	 */
	@Override
	public B000011Output process(String item) throws Exception {
		
		LOG.info("ExtractOrdrTkBsMnthProcessor "+item);
		B000011Output objB000011Output=new B000011Output();
		B000011ParamOutput objB000011ParamOutput= new B000011ParamOutput();
		
		objB000011ParamOutput.setOrdrTkBsMnth(item);
		objB000011ParamOutput.setPorCd(porCd);
		objB000011ParamOutput.setPrcsOlyFlg(prcsOlyFlg);
		objB000011Output.setObjB000011ParamOutput(objB000011ParamOutput);
		
		LOG.info(" Process P0002");
		objB000011Output=objSumOCFLmtRgnLvlProcess.executeProcess(objB000011Output);
		
		LOG.info("Process P0003");
		objB000011Output=objUptOCFLmtByrGrpLvlProcess.executeProcess(objB000011Output);
		
		
		
		return objB000011Output;
	}

	
	/** gets the entityManager and @return the entityManager */
	public EntityManager getEntityManager() {
		return entityManager;
	}


	/** sets the entityManager and @return the entityManager */
	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}



	/** gets the prcsOlyFlg and @return the prcsOlyFlg */
	public String getPrcsOlyFlg() {
		return prcsOlyFlg;
	}

	/** sets the prcsOlyFlg and @return the prcsOlyFlg */
	public void setPrcsOlyFlg(String prcsOlyFlg) {
		this.prcsOlyFlg = prcsOlyFlg;
	}

	/** gets the porCd and @return the porCd */
	public String getPorCd() {
		return porCd;
	}

	/** sets the porCd and @return the porCd */
	public void setPorCd(String porCd) {
		this.porCd = porCd;
	}
	
	
}
