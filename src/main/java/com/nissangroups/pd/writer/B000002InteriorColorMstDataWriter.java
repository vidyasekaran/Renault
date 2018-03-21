/*
 * System Name     :Post Dragon 
 * Sub system Name :Batch / Interface
 * Function ID     :PST-DRG-B000002InteriorColorMstDataWriter
 * Module          :@Create Spec Masters
 * Process Outline :@populate interior Colour Code details
 *
 * <Revision History>
 * Date       			Name(RNTBCI)                 Description 
 * ---------- ------------------------------ ---------------------
 * @10 July 2015  	  @author(z013576)               New Creation
 *
 */
package com.nissangroups.pd.writer;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.AfterWrite;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.batch.item.ItemWriter;

import com.nissangroups.pd.model.MstIntClr;

import static com.nissangroups.pd.util.B000002QueryConstants.Query_20_updateEitemErrorFlag;
import static com.nissangroups.pd.util.PDConstants.DOLLAR;
import static com.nissangroups.pd.util.PDConstants.INSIDE_METHOD;
import static com.nissangroups.pd.util.PDConstants.OUTSIDE_METHOD;
import static com.nissangroups.pd.util.PDConstants.PERSISTENCE_NAME;
import static com.nissangroups.pd.util.PDConstants.PRMTR_PORCD;
import static com.nissangroups.pd.util.PDConstants.PRMTR_CURRENT_TIME;
import static com.nissangroups.pd.util.PDConstants.BATCH_2_ID;
import static com.nissangroups.pd.util.PDConstants.PRMTR_UPDT_BY;

import static com.nissangroups.pd.util.PDConstants.WRITER_START_MSG;
import static com.nissangroups.pd.util.PDConstants.WRITER_STOP_MSG;

/**
 * Writer Class to write the data in Interior Color Code Mst Table
 * Process Id - P0007
 * @version V1.0
 */
public class B000002InteriorColorMstDataWriter implements
		ItemWriter<List<Object[]>> {
	
    /** Constant LOG. */
    private static final Log LOG = LogFactory.getLog(B000002InteriorColorMstDataWriter.class);
    
	/** Object emgr. */
	@PersistenceContext(name = PERSISTENCE_NAME)
	private EntityManager eMgr;
	
	/** Variable jobexec. */
	private JobExecution jobExec;
	
	/** Variable timestamp. */
	private Timestamp timeStamp;
	
	/** Variable currentdate. */
	private Date currentDate;

	/**
	 * Method to write the data in Interior_Color_Mst table.
	 *
	 * @param eiInteriorColorCodeDetailsList the ei interior color code details list
	 * @throws Exception the exception
	 * @Param Ei_InteriorColorCode_Details_List(Fetched Data List)
	 */
	@Override
	public void write(List<? extends List<Object[]>> eiInteriorColorCodeDetailsList)
			throws Exception {
		LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR);
		LOG.info(WRITER_START_MSG);
		MstIntClr intClr = new MstIntClr();
		/* Iterating the fetched List */
		for (List<Object[]> val : eiInteriorColorCodeDetailsList) {
			for (Object[] res : val) {
				intClr.setIntClrCd((String) res[0]);
				intClr.setIntClrDesc((String) res[1]);
				eMgr.merge(intClr);
				}
		}
		LOG.info(WRITER_STOP_MSG);
		LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);

	}
	
	/**
	 * AfterWrite method to Update the Processed End Item Error Flag to 3.
	 * This Method will execute after data write ends successfully in Interior Color Mst table
	 * @throws ParseException 
	 */
	@AfterWrite
	public void afterwrite() throws ParseException {
		LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR);
		String porCd = jobExec.getJobParameters().getString("porCd");
		currentDate = new Date();
        timeStamp = new Timestamp(currentDate.getTime());
	   	/* Query String */
		String eItemUpdateQuery = Query_20_updateEitemErrorFlag.toString();
		eMgr.createNativeQuery(eItemUpdateQuery)
			.setParameter(PRMTR_PORCD, porCd)
			.setParameter(PRMTR_CURRENT_TIME, timeStamp)
			.setParameter(PRMTR_UPDT_BY, BATCH_2_ID)
			.executeUpdate();
		eMgr.close();
		LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);
	}

	/**
	 * Before Stpe method to set the stepExecution to jobExecution Object.
	 *
	 * @param stepExecution the step execution
	 */
	@BeforeStep
	public void beforeStep(StepExecution stepExecution) {
		LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR);
		jobExec = stepExecution.getJobExecution();
		LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);
	}
	
}
