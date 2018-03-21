/*
 * System Name     :Post Dragon 
 * Sub system Name :Batch
 * Function ID     :PST-DRG-B000051
 * Module          :Weekly Ordering					
 * Process Outline :Create Weekly Order Base Data
 *
 * <Revision History>
 * Date       Name(Company Name)             Description 
 * ---------- ------------------------------ ---------------------
 * 21-12-2015  	  z015060(RNTBCI)               New Creation
 *
 */
package com.nissangroups.pd.b000051.processor;

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

import static com.nissangroups.pd.b000051.util.B000051Constants.*;
import static com.nissangroups.pd.util.PDConstants.*;
import com.nissangroups.pd.b000051.output.B000051Output;
import com.nissangroups.pd.b000051.output.B000051ParamOutput;
import com.nissangroups.pd.b000051.process.CalPrdMnthProcess;
import com.nissangroups.pd.b000051.process.ExtractOrdrInfoProcess;
import com.nissangroups.pd.b000051.process.InsertWklyOrdrTrnProcess;
import com.nissangroups.pd.b000051.process.UpdateOrdrQtyProcess;
import com.nissangroups.pd.b000051.process.UpdateWklyOrdrTrnProcess;
import com.nissangroups.pd.model.MstWklyOrdrTakeBase;

/**
 * The Class ExtractOrdrTkBsMnthProcessor.
 *
 * @author z015060
 */

@Configuration
@PropertySource(BATCH_CONFIG_CLASSPATH)
@Component(BATCH_ID_B000051)
public class ProdMnthProcessor implements ItemProcessor<MstWklyOrdrTakeBase, B000051Output> {
	
	/** Constant LOG */
	private static final Log LOG = LogFactory.getLog(ProdMnthProcessor.class
			.getName());
	
	private String reRunFlg;
	

	@Autowired(required = false)
	private CalPrdMnthProcess objCalPrdMnth;
	
	@Autowired(required = false)
	private ExtractOrdrInfoProcess objExtractOrdrInfo;
	
	@Autowired(required = false)
	private InsertWklyOrdrTrnProcess objInsertWklyOrdrTrn;
	
	@Autowired(required = false)
	private UpdateOrdrQtyProcess objUpdateOrdrQty;
	
	@Autowired(required = false)
	private UpdateWklyOrdrTrnProcess objUpdateWklyOrdrTrn;
	
	/** Variable entity manager. */
	@PersistenceContext(name = PERSISTENCE_NAME)
	private EntityManager entityManager;

	
	
	@Override
	public B000051Output process(MstWklyOrdrTakeBase item) throws Exception {
		B000051Output obj = new B000051Output();
		B000051ParamOutput param = new B000051ParamOutput();
		
		LOG.info(item.getId().getOrdrTakeBaseWkNo());
		param.setPorCd(item.getId().getPor());
		param.setOrdrTkBsMnth(item.getId().getOrdrTakeBaseMnth());
		param.setOrdrTkWkNo(item.getId().getOrdrTakeBaseWkNo());
		param.setReRunFlg(reRunFlg);
		obj.setObjB000051Param(param);
		
		//Process P0003
		obj =objCalPrdMnth.executeProcess(obj);
		
		//Process P0004
		obj=objExtractOrdrInfo.executeProcess(obj);
		
		//Process P0005
		obj=objUpdateOrdrQty.executeProcess(obj);
		
		//Process P0006
		obj=objInsertWklyOrdrTrn.executeProcess(obj);
		
		//Process P0007,P0008
		obj=objUpdateWklyOrdrTrn.executeProcess(obj);
		
		
		return obj;
	}



	public String getReRunFlg() {
		return reRunFlg;
	}



	public void setReRunFlg(String reRunFlg) {
		this.reRunFlg = reRunFlg;
	}



	public EntityManager getEntityManager() {
		return entityManager;
	}



	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}


	
	
	
}
