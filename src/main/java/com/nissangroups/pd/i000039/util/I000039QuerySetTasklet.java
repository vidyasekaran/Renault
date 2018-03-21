/*
 * System Name            : Post Dragon 
 * Sub system Name 		  : Interface
 * Function ID            : PST-DRG-I000039
 * Module                 : OR Ordering		
 * Process Outline     	  : Send Monthly Production Order Interface to Plant															
 *
 * <Revision History>
 * Date       Name(Company Name)             Description 
 * ---------- ------------------------------ ---------------------
 * 05-01-2016  	  z014135(RNTBCI)               Initial Version
 *
 */
package com.nissangroups.pd.i000039.util;

import static com.nissangroups.pd.util.PDConstants.DOLLAR;
import static com.nissangroups.pd.util.PDConstants.INSIDE_METHOD;
import static com.nissangroups.pd.util.PDConstants.OUTSIDE_METHOD;
import static com.nissangroups.pd.util.PDConstants.PERSISTENCE_NAME;
import static com.nissangroups.pd.util.PDMessageConsants.M00003;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
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
import com.nissangroups.pd.util.IFQueryConstants;
import com.nissangroups.pd.util.IfCommonUtil;
import com.nissangroups.pd.util.PDConstants;
import com.nissangroups.pd.util.PDMessageConsants;

/**
 * This Class I000039QuerySetTasklet is to generate the sequence number and
 * insert a record in Common File Header and also create the custom query and
 * store in ChunkContext for I000039 reader to do further processing.
 * 
 * @author z014135
 * 
 */
public class I000039QuerySetTasklet implements Tasklet, InitializingBean {

	/* Constant LOG */
	private static final Log LOG = LogFactory
			.getLog(I000039QuerySetTasklet.class.getName());

	/** Common utility service bean injection */
	@Autowired(required = false)
	IfCommonUtil commonutility;

	/**
	 * Store Entity manager and associated persistence context.
	 */
	@PersistenceContext(name = PERSISTENCE_NAME)
	private EntityManager eMgr;

	/** Order Take Base Period Mst service Bean Injection */
	@Autowired(required = false)
	private OrdrTkBsPrdMstRepository ordrTkBsPrdMstRepositoryObj;

	private StringBuilder finalQuery;

	/** Variable val1 value Y or N */
	String val1;

	/** Variable val1 value Y or N */
	String val2;

	/** Variable order take base month */
	String ordrTkBsMnth;

	/** Variable insert header */
	boolean insrthdr;

	@Override
	public void afterPropertiesSet() throws Exception {

		/**
		 * Indicates that a method declaration is intended to override a method
		 * declaration in a supertype.
		 */
	}

	/**
	 * P0001 Creates the custom query and store in ChunkContext
	 * 
	 * @param contribution
	 *            StepContribution object
	 * @param chunkContext
	 *            ChunkContext object
	 * 
	 * @return RepeatStatus object
	 */
	@Override
	public RepeatStatus execute(StepContribution contribution,
			ChunkContext chunkContext) throws Exception {

		LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR);
		// getting job param
		JobParameters jobParameters = chunkContext.getStepContext()
				.getStepExecution().getJobParameters();

		String ifFileId = jobParameters
				.getString(IFConstants.INTERFACE_FILE_ID);
		String porCd = jobParameters.getString(PDConstants.PRMTR_PORCD);
		String fileName = jobParameters.getString(IFConstants.FILE_NAME);

		// generate seqno and insert header data
		long seqNo = commonutility.getSequenceNoForInterfaceId(ifFileId);

		insrthdr = commonutility.insertCmnFileHdr(ifFileId, seqNo, fileName,
				'S');
		if (!insrthdr) {
			CommonUtil.stopBatch();
		}//Else condition no need
		ordrTkBsMnth = ordrTkBsPrdMstRepositoryObj
				.fetchLatestOrdrTkBsOrdDataForStageClose(porCd, "20");

		if (ordrTkBsMnth == null) {

			LOG.info(PDMessageConsants.M00041.replace(
					PDConstants.ERROR_MESSAGE_1, porCd));

			CommonUtil.stopBatch();
		}//Else condition will not occur as the execution falls above condition
		List<Object[]> forFlag = new ArrayList<Object[]>();
		forFlag = sndTypInfo(porCd);
		if (forFlag == null || forFlag.isEmpty()) {
			LOG.error(I000039QueryConstants.SNDTYPINFO);
			CommonUtil.stopBatch();
		}//Else condition will not required here
		for (Object[] rowObject : forFlag) {
			if (rowObject[0] != null && rowObject[1] != null) {
				val1 = rowObject[0].toString();
				val2 = rowObject[1].toString();
			} else {
				LOG.error(M00003);
				CommonUtil.stopBatch();
			}
		}

		String query = buildQuery(ordrTkBsMnth, val1, val2, porCd);

		// updating query and seqno in context
		chunkContext.getStepContext().getStepExecution().getJobExecution()
				.getExecutionContext().put(IFConstants.SEQ_NO, seqNo);

		chunkContext.getStepContext().getStepExecution().getJobExecution()
				.getExecutionContext()
				.put(I000039QueryConstants.ORDRTKBSMNTH, ordrTkBsMnth);
		chunkContext.getStepContext().getStepExecution().getJobExecution()
				.getExecutionContext().put("dynaQuery", query);

		LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);

		return RepeatStatus.FINISHED;
	}

	/**
	 * P0003.2 Extract Monthly production order data from Monthly Production order transaction table based on conditions and 
	 * aggregation function, Please note the * in a.* returns only the columns selected in the subquery so it does not return 
	 * all the columns in this case.
	 * 
	 * @param ordrTkBsMnth
	 * @param val1
	 * @param val2
	 * @param porCd2
	 * @return
	 */
	private String buildQuery(String ordrTkBsMnth, String val1, String val2,
			String porCd2) {
		
		finalQuery = new StringBuilder();
		
		if (I000039QueryConstants.valueY.equalsIgnoreCase(val1)
				&& I000039QueryConstants.valueY.equalsIgnoreCase(val2)) {
			LOG.info("inside Y condition");

			finalQuery.append("select rownum, a.* from ( ")
					.append(I000039QueryConstants.fetchMnthlyPrdOrdrPart1)
					.append(I000039QueryConstants.prodOrdrYFlag)
					.append(I000039QueryConstants.fetchMnthlyPrdOrdrPart2)
					.append(I000039QueryConstants.whereClause)
					.append(I000039QueryConstants.porVlaue).append(porCd2)
					.append(I000039QueryConstants.ordrVlaue)
					.append(ordrTkBsMnth).append(" )a ");
		}
		//Else condition will not occur as the Value "YES" and "NO" been handled in a seperate if conditions
		
		if (I000039QueryConstants.valueN.equalsIgnoreCase(val1)
				&& I000039QueryConstants.valueN.equalsIgnoreCase(val2)) {
			LOG.info(" inside N condition");

			finalQuery.append("select rownum, a.* from ( ")
					.append(I000039QueryConstants.fetchMnthlyPrdOrdrPart1)
					.append(I000039QueryConstants.ordrQtyNFlag)
					.append(I000039QueryConstants.fetchMnthlyPrdOrdrPart2)
					.append(I000039QueryConstants.whereClause)
					.append(I000039QueryConstants.porVlaue).append(porCd2)
					.append(I000039QueryConstants.ordrVlaue)
					.append(ordrTkBsMnth)
					.append(I000039QueryConstants.fetchMnthlyPrdOrdrGroupBy)
					.append(" )a ");
		}
		//Else condition will not occur as the Value "YES" and "NO" been handled in a seperate if conditions

		return finalQuery.toString();
	}

	/**
	 * P0003.1 set Type information flag to parameter MST table.
	 * @param porCd
	 * @return value
	 */
	private List<Object[]> sndTypInfo(String porCd) {
		List<Object[]> value = new ArrayList<Object[]>();
		StringBuilder typeInfoQuery = new StringBuilder()
				.append(I000039QueryConstants.fetchSndgTypInfo);
		Query result = eMgr.createNativeQuery(typeInfoQuery.toString());
		result.setParameter(1, porCd);
		value = result.getResultList();

		return value;
	}

	/**
	 * P0001 Insert the data into Common file header  
	 * @param ifFileId
	 * @param seqNo
	 * @param fileName
	 * @param trnType
	 * @return boolean  true/false
	 */
	public boolean insertCmnFileHdr(String ifFileId, long seqNo,
			String fileName, char trnType) {
		LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR);
		try {
			Query hdrInsert = eMgr
					.createNativeQuery(IFQueryConstants.insertCmnHeader
							.toString());
			hdrInsert.setParameter("IF_FILE_ID", ifFileId);
			hdrInsert.setParameter("SEQ_NO", seqNo);
			hdrInsert.setParameter("FILE_NAME", fileName);
			hdrInsert.setParameter("TRN_TYPE", trnType);
			hdrInsert.executeUpdate();

		} catch (Exception e) {
			LOG.info(M00003);
			LOG.error(e);
			return false;
		}
		LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);

		return true;
	}

}
