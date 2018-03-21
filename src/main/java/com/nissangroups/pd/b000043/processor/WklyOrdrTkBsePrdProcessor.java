/*
 * System Name     :Post Dragon 
 * Sub system Name :Batch
 * Function ID     :PST-DRG-B000043
 * Module          :Ordering		
 * Process Outline :Create_Weekly_Order_Take_Base_Period																
 *
 * <Revision History>
 * Date       Name(Company Name)             Description 
 * ---------- ------------------------------ ---------------------
 * 05-01-2016  	  z015060(RNTBCI)               New Creation
 *
 */
package com.nissangroups.pd.b000043.processor;


import static com.nissangroups.pd.util.PDConstants.BATCH_CONFIG_CLASSPATH;
import static com.nissangroups.pd.util.PDConstants.WRITE_COUNT;
import static com.nissangroups.pd.util.PDConstants.READ_COUNT;
import static com.nissangroups.pd.util.PDConstants.PERSISTENCE_NAME;
import static com.nissangroups.pd.util.PDMessageConsants.M00043;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.AfterStep;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import com.nissangroups.pd.model.MstWklyOrdrTakeBase;
import com.nissangroups.pd.model.MstWklyOrdrTakeBasePK;
import com.nissangroups.pd.repository.QueryParamBean;
import com.nissangroups.pd.repository.WeeklyOrderRepository;
import com.nissangroups.pd.util.CommonUtil;
import com.nissangroups.pd.util.PDConstants;
import com.nissangroups.pd.util.PDMessageConsants;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Configuration
@PropertySource(BATCH_CONFIG_CLASSPATH)
@Component(PDConstants.B000043_ID)
public class WklyOrdrTkBsePrdProcessor implements
ItemProcessor<MstWklyOrdrTakeBase, MstWklyOrdrTakeBase>{
	
	/* Constant LOG */
	private static final Log LOG = LogFactory.getLog(WklyOrdrTkBsePrdProcessor.class
			.getName());
	
	private String porCd= null;
	private String ordrTkBsMnthNw= null;
	
	
	@PersistenceContext(name = PERSISTENCE_NAME)
	private EntityManager entityManager;
	
	@Autowired(required = false)
	private WeeklyOrderRepository wklyRep;
	
	QueryParamBean qryParamBean = new QueryParamBean();
	
	@BeforeStep
	public void beforeStep(StepExecution stepExecution) {
		porCd = stepExecution.getJobParameters().getString(
				PDConstants.POR_CD);
	}
	
	@Override
	public MstWklyOrdrTakeBase process(MstWklyOrdrTakeBase item) throws Exception {
		MstWklyOrdrTakeBase wklyOrdr=new MstWklyOrdrTakeBase();
		MstWklyOrdrTakeBasePK wklyOrdrPk=new MstWklyOrdrTakeBasePK();
		if(item.getStageCd().equals(PDConstants.CONSTANT_SC)){
			
			qryParamBean.setPorCd(porCd);
			qryParamBean.setOrdrTkBsMnth(item.getId().getOrdrTakeBaseMnth());
			qryParamBean.setOrdrTkBsWkNo(item.getId().getOrdrTakeBaseWkNo());
		
			String selectResultSet = wklyRep.getJobSchdlMst(qryParamBean);
			if(selectResultSet == null){
				CommonUtil.logMessage(PDMessageConsants.M00157,
						PDConstants.CONSTANT_V4, new String[] { PDConstants.B000043_ID,
						item.getId().getOrdrTakeBaseMnth(),item.getId().getOrdrTakeBaseWkNo(),porCd });
				CommonUtil.stopBatch();
			}
			ordrTkBsMnthNw=selectResultSet.toString();
			wklyOrdrPk.setPor(porCd);
			wklyOrdrPk.setOrdrTakeBaseMnth(ordrTkBsMnthNw.substring(0,6));
			wklyOrdrPk.setOrdrTakeBaseWkNo(ordrTkBsMnthNw.substring(6,7));
			wklyOrdr.setStageCd(PDConstants.SO);
			wklyOrdr.setCrtdBy(PDConstants.B000043_ID);
			wklyOrdr.setUpdtdBy(PDConstants.B000043_ID);
			wklyOrdr.setId(wklyOrdrPk);

		}else {
			CommonUtil.logMessage(PDMessageConsants.M00158,
					PDConstants.CONSTANT_V3, new String[] { PDConstants.B000043_ID,
					porCd,item.getId().getOrdrTakeBaseMnth() });
			CommonUtil.stopBatch();
		}
		return wklyOrdr;
	}

	@AfterStep
	public void afterStep(StepExecution stepExecution) {
		LOG.info(READ_COUNT + stepExecution.getReadCount());
		if (stepExecution.getReadCount() == 0) {
			CommonUtil.logMessage(PDMessageConsants.M00159,
					PDConstants.CONSTANT_V4, new String[] { PDConstants.B000043_ID,
					PDConstants.CLOSED_STAGE,porCd,
					PDConstants.WEEKLY_ORDER_TAKE_BASE_PERIOD_MST });
			CommonUtil.stopBatch();
		}
		LOG.info(WRITE_COUNT + stepExecution.getWriteCount());
		if (stepExecution.getReadCount() == stepExecution.getWriteCount()) {
			CommonUtil.logMessage(PDMessageConsants.M00121,
					PDConstants.CONSTANT_V4, new String[] { PDConstants.B000043_ID,
					ordrTkBsMnthNw.substring(0,6),ordrTkBsMnthNw.substring(6,7),porCd});
		}
		else if (stepExecution.getReadCount() != stepExecution.getWriteCount()) {
			LOG.info(M00043);
			CommonUtil.stopBatch();
		}
	}
}
