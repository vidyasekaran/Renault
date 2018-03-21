package com.nissangroups.pd.b000052.util;

import static com.nissangroups.pd.util.PDConstants.DOLLAR;
import static com.nissangroups.pd.util.PDConstants.INSIDE_METHOD;
import static com.nissangroups.pd.util.PDConstants.OUTSIDE_METHOD;
import static com.nissangroups.pd.util.PDConstants.PERSISTENCE_NAME;






import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;







import com.nissangroups.pd.repository.OrdrTkBsPrdMstRepository;
import com.nissangroups.pd.util.CommonUtil;
import com.nissangroups.pd.util.IFConstants;
import com.nissangroups.pd.util.IfCommonUtil;
import com.nissangroups.pd.util.PDConstants;
import com.nissangroups.pd.util.PDMessageConsants;


public class B000052QuerySetTasklet implements Tasklet, InitializingBean {

	/* Constant LOG */
	private static final Log LOG = LogFactory
			.getLog(B000052QuerySetTasklet.class.getName());
	
	

	@Autowired(required = false)
	IfCommonUtil commonutility;
	
	@PersistenceContext(name = PERSISTENCE_NAME)
	private EntityManager eMgr;
	
	@Autowired(required=false)
	private OrdrTkBsPrdMstRepository ordrTkBsPrdMstRepositoryObj;

	

	private  StringBuilder finalQuery;
	
	String val1 ;
	String val2;
	String mnthlyOrdrTkBsMnth;
	boolean insrthdr;
	@Override
	public void afterPropertiesSet() throws Exception {

	}

	@Override
	public RepeatStatus execute(StepContribution contribution,
			ChunkContext chunkContext) throws Exception {

		LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR);
		// getting job param
		JobParameters jobParameters = chunkContext.getStepContext()
				.getStepExecution().getJobParameters();
	
		String porCd = jobParameters.getString("PORCD");
		String type = jobParameters.getString("TYPE");
		
		LOG.info("#######porcd :"+porCd);
		String query=null;
		if(type.equalsIgnoreCase("M")){	
		 query = fetchMnthlyOrdrTkBs(porCd);
		}
		if(type.equalsIgnoreCase("W")){
			query = fetchWklyOrdrTkBs(porCd);
		}
		
		chunkContext.getStepContext().getStepExecution().getJobExecution()
		.getExecutionContext().put("dynaQuery", query);
		
		LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);

		return RepeatStatus.FINISHED;
	}
	

 	public String fetchMnthlyOrdrTkBs(String porCd){
 		
 		StringBuilder queryString = new StringBuilder()
 		.append(B000052QueryConstants.fetchMnthlyOrdrtk)
 		.append("'")
 		.append(porCd)
 		.append("'")
 		.append(B000052QueryConstants.fetchMnthlyOrdrtk2);
 		
 		return queryString.toString();
 	}
 	
public String fetchWklyOrdrTkBs(String porCd){
 		
 		StringBuilder queryString = new StringBuilder()
 		.append(B000052QueryConstants.fetchWklyOrdrtk)
 		.append("'")
 		.append(porCd)
 		.append("'")
 		.append(B000052QueryConstants.fetchWklyOrdrtk2);
 		
 		return queryString.toString();
 	}




}
