package com.nissangroups.pd.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.nissangroups.pd.b000028.util.B000028QueryConstants;
import com.nissangroups.pd.b000053.util.B000053Constants;
import com.nissangroups.pd.b000053.util.B000053QueryConstants;
import com.nissangroups.pd.util.PDConstants;

public class PipelineProcessRepository {
	
	/** Variable entity manager. */
	@PersistenceContext(name = PDConstants.PERSISTENCE_NAME)
	private EntityManager entityMngr;
	
	
	private static final Log LOG = LogFactory.getLog(PipelineProcessRepository.class
			.getName());
	
	

	/** P0001 Extraction. */
	public List<Object[]> getWeeklyBatchProcessStatus(String porCd, String ordrTkBseMnth,String ordrTkBseWkno,String batchId,String seqId) {
		
		
		String wklyTriggerInfo = B000053QueryConstants.getTriggerInfo.toString();
		Query query = entityMngr.createNativeQuery(wklyTriggerInfo);
		query.setParameter(B000053Constants.POR_INPT_PRMTR, porCd);
		query.setParameter(B000053Constants.MNTH_INPT_PRMTR,ordrTkBseMnth);
		query.setParameter(B000053Constants.WKNO_INPT_PRMTR,ordrTkBseWkno);
		query.setParameter(B000053Constants.BATCH_INPT_PRMTR,batchId.trim());
		query.setParameter(B000053Constants.SEQ_INPT_PRMTR,seqId.trim());
		List<Object[]> wklyTriggerList = query.getResultList();
		return wklyTriggerList;
		
		
	}

}
