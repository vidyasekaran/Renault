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
package com.nissangroups.pd.b000040.writer;

import static com.nissangroups.pd.util.PDConstants.DOLLAR;
import static com.nissangroups.pd.util.PDConstants.INSIDE_METHOD;
import static com.nissangroups.pd.util.PDConstants.OUTSIDE_METHOD;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Session;
import org.hibernate.engine.jdbc.connections.spi.ConnectionProvider;
import org.hibernate.engine.spi.SessionFactoryImplementor;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;

import com.nissangroups.pd.b000040.output.B000040OrdrDtlsOutputBean;
import com.nissangroups.pd.b000040.util.B000040QueryConstants;
import com.nissangroups.pd.b000040.util.B000040QueryDataService;
import com.nissangroups.pd.b000059.util.B000059QueryConstants;
import com.nissangroups.pd.exception.PdApplicationNonFatalException;

/**
 * The Class B000059CommonLayerJpaItemWriter.
 */
public class B000040AttachExNoMapSymbolsFalseWriter implements
		ItemWriter<B000040OrdrDtlsOutputBean> {
	/* Constant LOG */
	private static final Log LOG = LogFactory
			.getLog(B000040AttachExNoMapSymbolsFalseWriter.class.getName());
	/**
	 * Common utility service bean injection
	 */
	@Autowired(required = false)
	B000040QueryDataService queryDataService;
	
	PreparedStatement preparedStmt;

	/**
	 * Stores row count
	 */
	private static int rowCount;

	private int recordcount = 1;

	@BeforeStep
	public void beforeStep(StepExecution stepExecution)
			throws PdApplicationNonFatalException {

		String updtOrdrDtilsTmp = B000040QueryConstants.updateExNoMapSymbolFalse.toString();

		Connection connection = null;

		Session session = (Session) queryDataService.getEntityManager()
				.getDelegate();
		SessionFactoryImplementor sessionFactoryImplementation = (SessionFactoryImplementor) session
				.getSessionFactory();
		ConnectionProvider connectionProvider = sessionFactoryImplementation
				.getConnectionProvider();

		try {
			LOG.info("Prepared Query********* " + updtOrdrDtilsTmp);
			connection = connectionProvider.getConnection();
			preparedStmt = connection.prepareStatement(updtOrdrDtilsTmp);			
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
	public void write(List<? extends B000040OrdrDtlsOutputBean> items) throws Exception {

		LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR);		

		for (Iterator iterator = items.iterator(); iterator.hasNext();) {
			rowCount = rowCount + 1;			
			B000040OrdrDtlsOutputBean fileVo = (B000040OrdrDtlsOutputBean) iterator.next();
			callForAddBatchProcess(fileVo);
			LOG.info("***********************Row Count :::" + rowCount);							
		}
		preparedStmt.executeBatch();
		LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);
	}

	/*
	 * Insert records using add batch function and execute with maxLimit as set in constants
	 */
	private void callForAddBatchProcess(B000040OrdrDtlsOutputBean item) throws SQLException {
		if (recordcount < B000059QueryConstants.maxLimit) {
			addRecord(item);
			recordcount++;
		} else {
			preparedStmt.executeBatch();
			preparedStmt.clearBatch();
			recordcount = 1;
			addRecord(item);
		}
	}
	
	//To Add records into prepared stmt using add batch
	//POR_CD,PROD_MNTH, PROD_WK_NO, OSEI_ID, POT_CD, PROD_ORDR_NO, OFFLN_PLAN_DATE, PROD_PLNT_CD, LINE_CLASS, PROD_DAY_NO
	public void addRecord(B000040OrdrDtlsOutputBean item) {
		try {
			
			/*	list.add("OCF REGION CODE");
			list.add("End Item");
			list.add("PRODUCTION FAMILY CODE");
			list.add("ADDITIONAL SPEC CODE");
			list.add("SPEC DESTIANTION CODE");
			list.add("BUYER CODE");
			list.add("BUYER GROUP CODE");
			list.add("CAR SERIES");			*/
			
			Map<String,String> resultMap =  queryDataService.getExNoMapSymbolsDtls(item,true);
			
			preparedStmt.setString(1, resultMap.get("OCF REGION CODE"));								
			preparedStmt.setString(2, resultMap.get("End Item"));	
			preparedStmt.setString(3, resultMap.get("PRODUCTION FAMILY CODE"));	
			preparedStmt.setString(4, resultMap.get("ADDITIONAL SPEC CODE"));	
			preparedStmt.setString(5, resultMap.get("SPEC DESTIANTION CODE"));	
			preparedStmt.setString(6, resultMap.get("BUYER CODE"));	
			preparedStmt.setString(7, resultMap.get("BUYER GROUP CODE"));
			preparedStmt.setString(8, resultMap.get("CAR SERIES"));	
			
			String salsNo = queryDataService.getSalesNotesNumber(item);
			preparedStmt.setString(9, salsNo);//SALES_NOTE_NO
			preparedStmt.setString(10, item.getPorCd());			
			preparedStmt.setString(11, item.getOseiId());	
			
			
			preparedStmt.addBatch();
		} catch (SQLException e) {
			LOG.error(e);			
		}

	}
}