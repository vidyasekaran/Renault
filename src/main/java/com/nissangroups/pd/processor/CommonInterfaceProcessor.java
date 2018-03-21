/**
 * 
 */
package com.nissangroups.pd.processor;

import static com.nissangroups.pd.util.PDConstants.DOLLAR;
import static com.nissangroups.pd.util.PDConstants.INSIDE_METHOD;
import static com.nissangroups.pd.util.PDConstants.OUTSIDE_METHOD;
import static com.nissangroups.pd.util.PDConstants.PERSISTENCE_NAME;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.AfterStep;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.batch.item.ItemProcessor;

import com.nissangroups.pd.model.CmnFileHdr;
import com.nissangroups.pd.model.MstPrmtr;
import com.nissangroups.pd.util.PDConstants;
import com.nissangroups.pd.util.QueryConstants;

/**
 * @author z015060
 *
 */
public class CommonInterfaceProcessor implements
		ItemProcessor<MstPrmtr, CmnFileHdr> {
	List<String> query = null;
	int countCheck = 0;
	/** Constant LOG. */
	private static final Log LOG = LogFactory
			.getLog(CommonInterfaceProcessor.class.getName());
	String interfaceId = "";
	int exitFlag = 0;
	/** Variable entity manager. */
	@PersistenceContext(name = PERSISTENCE_NAME)
	private EntityManager entityManager;

	@BeforeStep
	public void beforeStep(StepExecution stepExecution) {
		LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR);
		interfaceId = stepExecution.getJobParameters().getString(
				PDConstants.INTERFACE_FILE_ID);
		LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);
	}

	@Override
	public CmnFileHdr process(MstPrmtr mstPrmtr) throws Exception {
		LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR);
		String str = mstPrmtr.getVal1();
		List<String> ColumnList = Arrays.asList(str.split(PDConstants.SINGLE_COMMA));
		countCheck = ColumnList.size() - 1;
		StringBuilder duplicateCondQry = new StringBuilder();
		for (String colmn : ColumnList) {
			duplicateCondQry.append(" b." + colmn + " = a." + colmn + " AND ");
		}

		String minOrdrTkeMnthQuery = QueryConstants.checkCountDuplicateCommonINterface
				.toString().replace(PDConstants.ERROR_MESSAGE_1,
						duplicateCondQry);

		Query query = entityManager.createNativeQuery(minOrdrTkeMnthQuery);
		query.setParameter(PDConstants.PRMTRT_INTERFACE_FILE_ID, interfaceId);
		query.setParameter(PDConstants.PRMTRT_INTERFACE_STATUS,
				PDConstants.INTERFACE_UNPROCESSED_STATUS);
		BigDecimal count = (BigDecimal) query.getSingleResult();
		if (count.intValue() > 0) {
			exitFlag = 1;
			LOG.info(count.intValue()+" Duplicated Data available. Batch Stopped");
		}
		return null;
	}

	/**
	 * @param args
	 */
	@AfterStep
	public void afterStep(StepExecution stepExecution) {
		LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR);

		if (exitFlag == 1) {
			stepExecution.setExitStatus(ExitStatus.FAILED);
		}
		LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);

	}

}
