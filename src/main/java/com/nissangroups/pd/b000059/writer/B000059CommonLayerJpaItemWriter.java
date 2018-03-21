/*
 * System Name     :Post Dragon 
 * Sub system Name :Batch
 * Function ID     :B000059/B000062
 * Module          :
 * Process Outline :This program is used to Write the processed data from input file to CmnInterfaceData table.
 *
 * <Revision History>
 * Date       Name(Company Name)             Description 
 * ---------- ------------------------------ ---------------------
 * @Date      z015847(RNTBCI)               New Creation
 *
 */
package com.nissangroups.pd.b000059.writer;

import static com.nissangroups.pd.util.PDConstants.DOLLAR;
import static com.nissangroups.pd.util.PDConstants.INSIDE_METHOD;
import static com.nissangroups.pd.util.PDConstants.OUTSIDE_METHOD;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Session;
import org.hibernate.engine.jdbc.connections.spi.ConnectionProvider;
import org.hibernate.engine.spi.SessionFactoryImplementor;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;

import com.nissangroups.pd.b000059.util.B000059CommonUtilityService;
import com.nissangroups.pd.b000059.util.B000059QueryConstants;
import com.nissangroups.pd.exception.PdApplicationNonFatalException;
import com.nissangroups.pd.model.CmnInterfaceData;
import com.nissangroups.pd.util.CommonUtil;

/**
 * The Class B000059CommonLayerJpaItemWriter.
 */
public class B000059CommonLayerJpaItemWriter implements
		ItemWriter<CmnInterfaceData> {
	/* Constant LOG */
	private static final Log LOG = LogFactory
			.getLog(B000059CommonLayerJpaItemWriter.class.getName());
	/**
	 * Common utility service bean injection
	 */
	@Autowired(required = false)
	B000059CommonUtilityService commonutility;
	private String interfaceFileId = null;

	PreparedStatement preparedStmt;

	/**
	 * Stores row count
	 */
	private static int rowCount;

	private int recordcount = 1;

	@BeforeStep
	public void beforeStep(StepExecution stepExecution)
			throws PdApplicationNonFatalException {

		String wholeQuery = commonutility.getFileSpecVO().getCmnIFInsertQuery();

		Connection connection = null;

		Session session = (Session) commonutility.getEntityManager()
				.getDelegate();
		SessionFactoryImplementor sessionFactoryImplementation = (SessionFactoryImplementor) session
				.getSessionFactory();
		ConnectionProvider connectionProvider = sessionFactoryImplementation
				.getConnectionProvider();

		try {
			LOG.info("Prepared Query********* " + wholeQuery);
			connection = connectionProvider.getConnection();
			preparedStmt = connection.prepareStatement(wholeQuery);			
		} catch (SQLException e) {
			LOG.error(e);			
		}
	}

	/**
	 * This method used to write processed data into common interface data
	 * 
	 * @param items
	 *            list of interface data
	 */
	@Override
	public void write(List<? extends CmnInterfaceData> items) throws Exception {

		LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR);

		Integer seqNo = null;

		String wholeQuery = commonutility.getFileSpecVO().getCmnIFInsertQuery();

		int totalCols = commonutility.getFileSpecVO().getTotalOrderCols();

		interfaceFileId = commonutility.getFileSpecVO().getInterfaceFileId();

		for (Iterator iterator = items.iterator(); iterator.hasNext();) {
			rowCount = rowCount + 1;
			seqNo = commonutility.getSeqNumber(rowCount);
			if (null != seqNo) {
				try {
					CmnInterfaceData fileVo = (CmnInterfaceData) iterator
							.next();
					callForAddBatchProcess(totalCols,fileVo,wholeQuery,seqNo);
					LOG.info("***********************Row Count :::" + rowCount);
				} catch (Exception e) {
					LOG.info("Error : " + e);
				}
			} else {
				LOG.info("Error :: ########## SeqNumber NULL ##########");
			}
		}
		preparedStmt.executeBatch();
		LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);
	}

	/*
	 * Insert records using add batch function and execute with maxLimit as set in constants
	 */
	private void callForAddBatchProcess(int totalCols, CmnInterfaceData fileVo, String wholeQuery,
			Integer seqNo) throws SQLException {
		if (recordcount < B000059QueryConstants.maxLimit) {
			addRecord(totalCols, fileVo, seqNo);
			recordcount++;
		} else {
			preparedStmt.executeBatch();
			preparedStmt.clearBatch();
			recordcount = 1;
			addRecord(totalCols, fileVo, seqNo);
		}
	}
	
	//To Add records into prepared stmt using add batch
	public void addRecord(Integer totalCols, CmnInterfaceData fileVo,Integer seqNo) {
		try {
			preparedStmt.setString(1, interfaceFileId);
			preparedStmt.setLong(2, seqNo);
			preparedStmt.setLong(3, rowCount);
			for (int i = 1; i <= totalCols; i++) {
				preparedStmt.setString(i + 3,
						CommonUtil.getBeanValue(fileVo, i));
			}
			preparedStmt.addBatch();
		} catch (SQLException e) {
			LOG.error(e);			
		}

	}
}