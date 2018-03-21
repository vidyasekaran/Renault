/*
 * System Name     :Post Dragon 
 * Sub system Name :Batch
 * Function ID     :B000059/B000062
 * Module          :
 * Process Outline :This program is used to fetch and sets the incoming file fixed length range to Spring ExecutionContext.
 *
 * <Revision History>
 * Date       Name(Company Name)             Description 
 * ---------- ------------------------------ ---------------------
 * @Date      z015847(RNTBCI)               New Creation
 *
 */
package com.nissangroups.pd.b000059.util;
import static com.nissangroups.pd.util.PDConstants.DOLLAR;
import static com.nissangroups.pd.util.PDConstants.INSIDE_METHOD;
import static com.nissangroups.pd.util.PDConstants.UNCHECKED;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.scope.context.StepContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import com.nissangroups.pd.model.MstIfLayout;
public class B000059ColumnRangeTasklet implements Tasklet {

	/**
	 * Common utility service bean injection
	 */
	@Autowired(required = false)
	B000059CommonUtilityService commonutility;

	/* Constant LOG */
	private static final Log LOG = LogFactory
			.getLog(B000059ColumnRangeTasklet.class.getName());
	
	/**
	 * Get the records from Interface Master Layout and prepare the column
	 * trimRange based on Starting and and Ending Position
	 * 
	 * @param contribution
	 *            StepContribution object
	 * @param chunkContext
	 *            ChunkContext object
	 * @return RepeatStatus object
	 */
	@Override
	@SuppressWarnings(UNCHECKED)
	public RepeatStatus execute(StepContribution contribution,
			ChunkContext chunkContext) throws Exception {
		
		LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR);
		StepContext stepContext =  chunkContext.getStepContext();
		String interfaceFileID = (String) stepContext.getJobParameters().get(
				B000059Constants.INTERFACE_FILE_ID);

		LOG.info("ColumnRangeTasklet : In Execute method.......");
		StringBuilder range = new StringBuilder();
		List<MstIfLayout> ex = (ArrayList<MstIfLayout>) commonutility.getEntityManager()
				.createQuery(
						"SELECT  f FROM MstIfLayout  f where f.id.ifFileId='"
								+ interfaceFileID + "' order by f.columnOrdr")
				.getResultList();

		for (Iterator iterator = ex.iterator(); iterator.hasNext();) {
			MstIfLayout mstLayout = (MstIfLayout) iterator.next();
			range.append(mstLayout.getStrtPosition()).append("-")
					.append(mstLayout.getEndPosition()).append(",");
		}

		String trimRange = range.toString().replaceFirst(".$", "");
		
		LOG.info("MstIfLayout COLS - Range " + trimRange);
		chunkContext.getStepContext().getStepExecution().getJobExecution()
				.getExecutionContext().put("COLS", trimRange);

		return RepeatStatus.FINISHED;
	}
}
