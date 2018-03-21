/*
 * System Name     :Post Dragon 
 * Sub system Name :Batch 
 * Function ID     :PST-DRG-B000018/PST-DRG-R000007,R000008,R000009
 * Module          :MONTHLY ORDERING
 * Process Outline :Validate the Por CD from Monthly Order Interface TRN with Master Tables. 
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
import static com.nissangroups.pd.util.PDMessageConsants.M00003;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;

import com.nissangroups.pd.b000018.bean.InputBean;
import com.nissangroups.pd.exception.PdApplicationException;
import com.nissangroups.pd.repository.MnthlyOrdrIfTrnRepository;
import com.nissangroups.pd.repository.VldnRepository;

public class SingleRecordProcessor implements
		ItemProcessor<Long, InputBean> {

	/** Constant LOG. */
	private static final Log LOG = LogFactory.getLog
			(SingleRecordProcessor.class);

	@Autowired(required=false)
	private MnthlyOrdrIfTrnRepository mnthlyOrdrIfTrnRepositoryObj ;
	
	
	private InputBean input = new InputBean();
	
	private String porCd;
	private String singleRecordFlag;
	private String fileId;
	private String prdOrdrStgCd;
	private String tableName;

	@Override
	public InputBean process(Long item) throws PdApplicationException {

		input.setFileId(fileId);
		input.setPorCd(porCd);
		input.setPrdOrdrStgCd(prdOrdrStgCd);
		input.setSingleRecordFlag(singleRecordFlag);
		input.setTableName(tableName);
		input.setSeqNo(String.valueOf(item));
		if(item!=null && item>0){
			LOG.info(STEP_AFTER_START);
			if(singleRecordFlag!=null && "true".equalsIgnoreCase(singleRecordFlag)){
				mnthlyOrdrIfTrnRepositoryObj.insrtIntoDevMnthlyOrdrIf(input);
				input.setTableName("DEV_MNTHLY_ORDR_IF");
			} else {
				input.setTableName(tableName);
				
			}	
			
			LOG.info(STEP_AFTER_END);
		} else {
			LOG.info(M00003);
			return null;
			// Stop the Batch No records Found Message
		}
		return input;
	}
	

	
	public String getFileId() {
		return fileId;
	}



	public void setFileId(String fileId) {
		this.fileId = fileId;
	}


	public InputBean getInput() {
		return input;
	}



	public void setInput(InputBean input) {
		this.input = input;
	}



	public String getPorCd() {
		return porCd;
	}



	public void setPorCd(String porCd) {
		this.porCd = porCd;
	}



	public String getSingleRecordFlag() {
		return singleRecordFlag;
	}



	public void setSingleRecordFlag(String singleRecordFlag) {
		this.singleRecordFlag = singleRecordFlag;
	}



	public String getPrdOrdrStgCd() {
		return prdOrdrStgCd;
	}



	public void setPrdOrdrStgCd(String prdOrdrStgCd) {
		this.prdOrdrStgCd = prdOrdrStgCd;
	}

	
	public MnthlyOrdrIfTrnRepository getMnthlyOrdrIfTrnRepositoryObj() {
		return mnthlyOrdrIfTrnRepositoryObj;
	}

	public void setMnthlyOrdrIfTrnRepositoryObj(
			MnthlyOrdrIfTrnRepository mnthlyOrdrIfTrnRepositoryObj) {
		this.mnthlyOrdrIfTrnRepositoryObj = mnthlyOrdrIfTrnRepositoryObj;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	
}
