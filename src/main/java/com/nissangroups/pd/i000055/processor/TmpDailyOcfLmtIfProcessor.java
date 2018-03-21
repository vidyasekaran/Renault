package com.nissangroups.pd.i000055.processor;

import static com.nissangroups.pd.util.PDConstants.DOLLAR;
import static com.nissangroups.pd.util.PDConstants.FAILED;
import static com.nissangroups.pd.util.PDConstants.INSIDE_METHOD;
import static com.nissangroups.pd.util.PDConstants.OUTSIDE_METHOD;
import static com.nissangroups.pd.util.PDConstants.PERSISTENCE_NAME;
import static com.nissangroups.pd.util.PDConstants.READ_COUNT;
import static com.nissangroups.pd.util.PDConstants.READ_SKIPPED_COUNT;
import static com.nissangroups.pd.util.PDConstants.STAR;
import static com.nissangroups.pd.util.PDConstants.STEP_ID;
import static com.nissangroups.pd.util.PDConstants.WRITE_COUNT;
import static com.nissangroups.pd.util.PDConstants.WRITE_SKIPPED_COUNT;
import static com.nissangroups.pd.util.PDMessageConsants.M00003;
import static com.nissangroups.pd.util.PDMessageConsants.M00043;
import static com.nissangroups.pd.util.PDMessageConsants.M00076;
import static com.nissangroups.pd.util.PDMessageConsants.M00356;

import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.AfterStep;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;

import com.nissangroups.pd.model.TmpDailyOcfLmtIf;
import com.nissangroups.pd.model.TrnDailyOcfLmt;
import com.nissangroups.pd.model.TrnDailyOcfLmtPK;
import com.nissangroups.pd.util.IFConstants;
import com.nissangroups.pd.util.IfCommonUtil;
import com.nissangroups.pd.util.PDConstants;

/**
 * This method is to process all the extracted TmpDailyOcfLmtIf records
 * and insert into TrnDailyOcfLmt
 * 
 * @author z015895
 *
 */
public class TmpDailyOcfLmtIfProcessor implements
		ItemProcessor<TmpDailyOcfLmtIf, TrnDailyOcfLmt> {

	/** Constant LOG. */
	private static final Log LOG = LogFactory.getLog
			(TmpDailyOcfLmtIfProcessor.class);

	/** Common utility service bean injection */
	@Autowired(required = false)
	IfCommonUtil commonutility;	
	
	/** Variable jobExec. */
	private JobExecution jobExec;
	
	/** Variable interface file id. */
	private String ifFileId;	
		
	/** Variable por cd. */
	private String porCd;		
	
	/** Variable entity manager. */
	@PersistenceContext(name = PERSISTENCE_NAME)
	private EntityManager entityManager;
	

	/**
	 * This method will be called just before each step execution Get
	 * stepExecution and assign into instance variable
	 * 
	 * @param stepExecution
	 *            the step execution
	 */
	@BeforeStep
	public void beforeStep(StepExecution stepExecution) {
		LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR);
		jobExec = stepExecution.getJobExecution();				
		String ifFileId = jobExec.getJobParameters().getString(
				IFConstants.INTERFACE_FILE_ID);
		String porCd = jobExec.getJobParameters().getString(
				IFConstants.POR_CD);		
		this.ifFileId = ifFileId;		
		this.porCd = porCd;		
		LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);
	}

	
	/**
	 * This method is to process all the extract TmpDailyOcfLmtIf records
	 * and set to TrnDailyOcfLmt
	 * 
	 * @param item
	 * @return dailyOcfLmt
	 * @throws Exception
	 */
	public TrnDailyOcfLmt process(TmpDailyOcfLmtIf item) throws Exception {

		LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR);
		
		Date date= new Date();		
		
		jobExec.getExecutionContext().put("ocfLmtQty", item.getOcfLimit());
		// Need to get the values from I000055OutputBean item and set it in
	    // TrnDailyOcfLmtIf
		TrnDailyOcfLmt dailyOcfLmt = new TrnDailyOcfLmt();
		dailyOcfLmt.setId(new TrnDailyOcfLmtPK());
				
		dailyOcfLmt.getId().setCarSrs(item.getId().getCarSrs());
		dailyOcfLmt.getId().setLineClass(item.getId().getLineClass());
		dailyOcfLmt.getId().setOcfBuyerGrpCd(item.getId().getOcfRegIdntctnCd());
		dailyOcfLmt.getId().setOcfFrmeSrtCd(item.getId().getFrmeSrtCd());
		dailyOcfLmt.getId().setOcfIdntctnCode(item.getId().getOcfIdntctnCd());
		dailyOcfLmt.getId().setOcfRegionCd(item.getId().getMapsSymbl());
		dailyOcfLmt.getId().setPorCd(porCd);
		dailyOcfLmt.getId().setProdMnth(item.getId().getPeriod());
		dailyOcfLmt.getId().setProdPlntCd(item.getId().getPlntCd());	
									
		dailyOcfLmt.setOcfFrmeCd(item.getFrmeCd());		
		dailyOcfLmt.setCrtdBy(ifFileId);
		dailyOcfLmt.setCrtdDt(new Timestamp(date.getTime()));
		dailyOcfLmt.setUpdtdBy(ifFileId);
		dailyOcfLmt.setUpdtdDt(new Timestamp(date.getTime()));	
	
		LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);				
				
		return dailyOcfLmt;
	}	
	

	/**
	 * This method executed Each step Execution
	 * To get the count of Reader, Writer
	 * Based on the count values  and write the Log.
	 *
	 * @param stepExecution the step execution
	 */
	@AfterStep
	public void afterStep(StepExecution stepExecution) {
		LOG.info(DOLLAR+INSIDE_METHOD+DOLLAR);
		LOG.info(STAR+STEP_ID + stepExecution.getId()+STAR);
		LOG.info(READ_COUNT + stepExecution.getReadCount());
		LOG.info(READ_SKIPPED_COUNT + stepExecution.getReadSkipCount());
		LOG.info(WRITE_COUNT + stepExecution.getWriteCount());
		LOG.info(WRITE_SKIPPED_COUNT + stepExecution.getWriteSkipCount());				
		
		 if (stepExecution.getExitStatus().getExitCode().equals(FAILED)) {

			commonutility.setStatus(PDConstants.INTERFACE_FAILURE_STATUS);
			if(commonutility.getRemarks() == null || ("").equals(commonutility.getRemarks())){
				commonutility.setRemarks(M00076.replace(PDConstants.ERROR_MESSAGE_1,""));
				LOG.error(M00076.replace(PDConstants.ERROR_MESSAGE_1,stepExecution.getFailureExceptions().toString()));	
			}else{
				LOG.error(commonutility.getRemarks());	
			}			
								
		}
		else if (stepExecution.getReadCount() == 0) {
			commonutility.setStatus(PDConstants.INTERFACE_PROCESSED_STATUS);
			commonutility.setRemarks(M00003);
			LOG.info(M00003);
		}
		
		else if (stepExecution.getReadCount() == stepExecution.getWriteCount()) {
			//write count in header
			commonutility.setWriteCount(stepExecution.getWriteCount());
			commonutility.setStatus(PDConstants.INTERFACE_PROCESSED_STATUS);
			String remarks= M00356.replace(PDConstants.ERROR_MESSAGE_1, ifFileId)
					.replace(PDConstants.ERROR_MESSAGE_2, PDConstants.DAILY_OCF_LIMIT_TRN)
					.replace(PDConstants.ERROR_MESSAGE_3, porCd);
			commonutility.setRemarks(remarks);
			LOG.info(remarks);
		}
		else if (stepExecution.getReadCount() != stepExecution.getWriteCount()) {
			commonutility.setStatus(PDConstants.INTERFACE_FAILURE_STATUS);
			commonutility.setRemarks(M00043);
			LOG.info(M00043);
		}
		LOG.info(DOLLAR+OUTSIDE_METHOD+DOLLAR);
		
	}
	

}
