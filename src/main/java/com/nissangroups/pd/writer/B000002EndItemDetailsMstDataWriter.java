/*
 * System Name     :Post Dragon 
 * Sub system Name :Batch / Interface
 * Function ID     :PST-DRG-B000002OSalesEndItemMstDataWriter
 * Module          :@Create Spec Masters
 * Process Outline :@populate OSEI Adopt & abolish details
 *
 * <Revision History>
 * Date       			Name(RNTBCI)                 Description 
 * ---------- ------------------------------ ---------------------
 * @09 July 2015  	  @author(z013576)               New Creation
 *
 **/
package com.nissangroups.pd.writer;


import static com.nissangroups.pd.util.B000002QueryConstants.Query_22_getOEISpecDetailsRecordCount;
import static com.nissangroups.pd.util.B000002QueryConstants.Query_24_getOEISpecDetailsforUKOSEIID;
import static com.nissangroups.pd.util.B000002QueryConstants.Query_25_OseiAblshdtUpdt;
import static com.nissangroups.pd.util.B000002QueryConstants.Query_39_getOEISpecDetails;
import static com.nissangroups.pd.util.B000002QueryConstants.Query_40_getAblshDt;
import static com.nissangroups.pd.util.B000002QueryConstants.Query_42_InsertIntoOSEIDTL;
import static com.nissangroups.pd.util.CommonUtil.bigDecimaltoInt;
import static com.nissangroups.pd.util.CommonUtil.stringtoInt;
import static com.nissangroups.pd.util.PDConstants.BATCH_2_ID;
import static com.nissangroups.pd.util.PDConstants.DOLLAR;
import static com.nissangroups.pd.util.PDConstants.EI_STTS_CD;
import static com.nissangroups.pd.util.PDConstants.EMPTY_STRING;
import static com.nissangroups.pd.util.PDConstants.INSIDE_METHOD;
import static com.nissangroups.pd.util.PDConstants.OUTSIDE_METHOD;
import static com.nissangroups.pd.util.PDConstants.PERSISTENCE_NAME;
import static com.nissangroups.pd.util.PDConstants.PRMTR_ADPTDATE;
import static com.nissangroups.pd.util.PDConstants.PRMTR_OEI_SPEC_ID;
import static com.nissangroups.pd.util.PDConstants.PRMTR_PORCD;
import static com.nissangroups.pd.util.PDConstants.PRMTR_UK_OSEI_ID;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.Session;
import org.hibernate.engine.jdbc.connections.spi.ConnectionProvider;
import org.hibernate.engine.spi.SessionFactoryImplementor;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.batch.item.ItemWriter;

import com.nissangroups.pd.exception.PdApplicationException;
import com.nissangroups.pd.exception.PdApplicationNonFatalException;

/**
 * Writer Class to write the data in OrderableSalesEndItemDetailMst table
 * Process Id - P0005.
 * @version V1.0
 */

public class B000002EndItemDetailsMstDataWriter implements
		ItemWriter<List<Object>> {
	
	
    /** Constant LOG. */
    private static final Log LOG = LogFactory.getLog(B000002EndItemDetailsMstDataWriter.class);
    
	/** Object emgr. */
	@PersistenceContext(name = PERSISTENCE_NAME)
	private EntityManager eMgr;
	
	/** Variable fetchoseidataforukoseid. */
	String fetchOSEIdataforUKOSEID = Query_24_getOEISpecDetailsforUKOSEIID.toString();
	
	/** Variable oseiablshupdt. */
	String oseiAblshUpdt = Query_25_OseiAblshdtUpdt.toString();
	
	/** Variableeimablshdt. */
	String eimablshDt = EMPTY_STRING;
	
	/** Variableeimsttscd. */
	String eimsttsCd = EMPTY_STRING;
	
	/** Variable timestamp. */
	private Timestamp timeStamp;
	
	/** Variable currentdate. */
	private Date currentDate;
	
	private int recordcount=1;
	
	private int maxLimit=100;

	
	String insertIntoOSEIDTL = Query_42_InsertIntoOSEIDTL;
	
	PreparedStatement preparedStmt;
	PreparedStatement  preparedStmtUpdate;
	
	@BeforeStep
	public void beforeStep(StepExecution stepExecution) throws PdApplicationNonFatalException {
		
		
		Connection connection = null;
		
		
		Session session = (Session)eMgr.getDelegate();
		SessionFactoryImplementor sessionFactoryImplementation = (SessionFactoryImplementor) session.getSessionFactory();
		ConnectionProvider connectionProvider = sessionFactoryImplementation.getConnectionProvider();
		
		 
//		connection = eMgr.getEntityManagerFactory().unwrap(Connection.class);
		try {
			connection = connectionProvider.getConnection();
			preparedStmt = connection.prepareStatement(insertIntoOSEIDTL);
			preparedStmtUpdate = connection.prepareStatement(oseiAblshUpdt);
		} catch (SQLException e) {
			LOG.info("SQLException - "+e);
		}
		
	}
	
	/**
	 * Method to write the Processed data in MST_OSEI_DTL Table.
	 *
	 * @param eiOseiProcessedList the ei osei processed list
	 * @throws PdApplicationException the pd application exception
	 */
	@Override
	public void write(List<? extends List<Object>> eiOseiProcessedList)
			throws PdApplicationException {
		LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR);
		for (List<Object> prcssdObjList : eiOseiProcessedList) {
			for (Object prcssdList : prcssdObjList) {
				Object[] prcssdLsitArray 	= 	(Object[]) prcssdList;
				String porCd				=	(String) prcssdLsitArray[7];
				String oseiId  				= 	(String) prcssdLsitArray[0];

				int count = getRecordCount(oseiId,porCd);
				
				oseiDtlCreation(count,prcssdLsitArray);
				
				}
			}
		try{

			preparedStmt.executeBatch();
			preparedStmt.clearBatch();
			preparedStmtUpdate.executeBatch();
			preparedStmtUpdate.clearBatch();
		
		} catch(SQLException e) {
			LOG.info("SQLException - "+e);
		}
		
		
		LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);
	}
	
	/**
	 * Method for OSEI DEtails MST creation.
	 *
	 * @param count the count
	 * @param prcssdLsitArray the prcssd lsit array
	 */

	private void oseiDtlCreation(int count, Object[] prcssdLsitArray) {
		LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR);
		String mdlYr = null;
		String oseiId  				= 	(String) prcssdLsitArray[0];
		String oseiAdptDt 			= 	(String) prcssdLsitArray[1];
		String oseiAblshDt			= 	(String) prcssdLsitArray[2];
		String oseiTokusoNm			= 	(String) prcssdLsitArray[3];
		String oseiPckgeNm 			= 	(String) prcssdLsitArray[4];
		String oseiLclNte			= 	(String) prcssdLsitArray[5];
		String mdfdFlag				=	EMPTY_STRING;
		String porCd				=	(String) prcssdLsitArray[7];
		String gsisRgnCd				=	(String) prcssdLsitArray[8];
		String gsisMdlNo				=	(String) prcssdLsitArray[9];
		
		if(prcssdLsitArray[6] == null )
		{
			mdfdFlag = null;
		}else
		{
			mdfdFlag =	prcssdLsitArray[6].toString();
		}
		/* checking for count */
		if(count == 0)
		{
			
			try {
				preparedStmt.setString(1, oseiId);
				preparedStmt.setString(2, oseiAdptDt);
				preparedStmt.setString(3, oseiAblshDt);
				preparedStmt.setString(4, oseiAblshDt);
				preparedStmt.setString(5, EI_STTS_CD);
				preparedStmt.setString(6, oseiPckgeNm);
				preparedStmt.setString(7, oseiLclNte);
				preparedStmt.setString(8, oseiTokusoNm);
				preparedStmt.setString(9, mdfdFlag);
				preparedStmt.setString(10, mdlYr);
				preparedStmt.setString(11, porCd);
				
				preparedStmt.setString(12, gsisRgnCd);
				preparedStmt.setString(13, gsisMdlNo);
				currentDate = new Date();
		        timeStamp = new Timestamp(currentDate.getTime());
				preparedStmt.setTimestamp(14, timeStamp);
				preparedStmt.setTimestamp(15, timeStamp);
				
				
				if(recordcount<maxLimit){
					preparedStmt.addBatch();	
					recordcount++;
				} else {
					preparedStmt.addBatch();
					preparedStmt.executeBatch();
					preparedStmt.clearBatch();
					recordcount = 1;
				}
				
				
			} catch (SQLException e) {
				LOG.info("SQLException - "+e);
			}
			
			
			
			
			
			
		}
		/* if count is greater than Zero */
		else if (count > 0)
		{
			List<Object> recordDtls = getOeiSpecIdDetails(oseiId,porCd);
			List<Object[]> oSEIdataList = eMgr.createNativeQuery(fetchOSEIdataforUKOSEID)
					.setParameter(PRMTR_UK_OSEI_ID, oseiId)
					.setParameter(PRMTR_ADPTDATE, oseiAdptDt)
					.setParameter(PRMTR_PORCD, porCd)
					.getResultList();
			int endItemAblshDt = getAblshDt(oseiId, porCd);
			/* checking for oseidatalist empty */
			if(oSEIdataList.isEmpty())
			{
				eimablshDt = recordDtls.get(2).toString();
				
				int listadoptdate = Integer.parseInt(oseiAdptDt);
				if(listadoptdate > endItemAblshDt)
				{
					eimsttsCd = EI_STTS_CD;
				}
				else
				{
				eimsttsCd = recordDtls.get(3).toString();
				}
				try {
					
				
				preparedStmt.setString(1, oseiId);
				preparedStmt.setString(2, oseiAdptDt);
				preparedStmt.setString(3, oseiAblshDt);
				preparedStmt.setString(4, oseiAblshDt);
				preparedStmt.setString(5, eimsttsCd);
				preparedStmt.setString(6, oseiPckgeNm);
				preparedStmt.setString(7, oseiLclNte);
				preparedStmt.setString(8, oseiTokusoNm);
				preparedStmt.setString(9, mdfdFlag);
				preparedStmt.setString(10, mdlYr);
				
				preparedStmt.setString(11, porCd);
				
				preparedStmt.setString(12, gsisRgnCd);
				preparedStmt.setString(13, gsisMdlNo);
				currentDate = new Date();
		        timeStamp = new Timestamp(currentDate.getTime());
				preparedStmt.setTimestamp(14, timeStamp);
				preparedStmt.setTimestamp(15, timeStamp);
				
				
				
				if(recordcount<maxLimit){
					preparedStmt.addBatch();
					recordcount++;
				} else {
					preparedStmt.addBatch();
					preparedStmt.executeBatch();
					preparedStmt.clearBatch();
					recordcount = 1;
				}
				
				} catch (SQLException e) {
					LOG.info("SQLException - "+e);
				}	
				
			}
			/* if the list is not empty */
			else if(!oSEIdataList.isEmpty())
			{
				
				eimsttsCd = recordDtls.get(3).toString();
				
				currentDate = new Date();
		        timeStamp = new Timestamp(currentDate.getTime());

				
				for (Object[] oseiData : oSEIdataList) {
					String dbadptdt = (String) oseiData[1];
					String dbsuspndDate = (String) oseiData[3];
					String listadpt = oseiAdptDt;
					String lsitablshdt = oseiAblshDt;
					if (dbadptdt.equals(listadpt)
							&& !dbsuspndDate.equals(lsitablshdt)) {
						try{
						
						preparedStmtUpdate.setString(1, oseiAblshDt);
						preparedStmtUpdate.setString(2, eimsttsCd);
						preparedStmtUpdate.setString(3, oseiId);
						preparedStmtUpdate.setString(4, oseiAdptDt);
						preparedStmtUpdate.setTimestamp(5, timeStamp);
						preparedStmtUpdate.setString(6, BATCH_2_ID);
						preparedStmtUpdate.setString(7, porCd);
						
						if(recordcount<maxLimit){
							preparedStmtUpdate.addBatch();
							recordcount++;
						} else {
							preparedStmtUpdate.addBatch();
							preparedStmtUpdate.executeBatch();
							preparedStmtUpdate.clearBatch();
							recordcount = 1;
						}
						
						
						} catch (SQLException e) {
							LOG.info("SQLException - "+e);
						}
						
					}
					
				}
			}
							    
		}
		LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);
		
	}
	
	/**
	 * Method to get already present OeiSpecDetails.
	 *
	 * @param oseiId the osei id
	 * @param porCd the por cd
	 * @return the oei spec id details
	 */

	@SuppressWarnings("unchecked")
	private List<Object> getOeiSpecIdDetails(String oseiId, String porCd) {
		LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR);
		List<Object> fnlVal = new ArrayList<Object>();
		String oseiDetails = Query_39_getOEISpecDetails.toString();
		List<Object[]> resultSet = eMgr.createNativeQuery(oseiDetails)
								.setParameter(PRMTR_OEI_SPEC_ID, oseiId)
								.setParameter(PRMTR_PORCD, porCd)
								.getResultList();
		
		if (resultSet != null && !resultSet.isEmpty()) {
			Object[] last = resultSet.get(resultSet.size()-1);
			fnlVal = Arrays.asList(last);
			}
		LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);
		return fnlVal;
	}

	/**
	 * Method to fetch the already present data in the table.
	 *
	 * @param oseiId the osei id
	 * @param porCd the por cd
	 * @return int
	 */
	private int getRecordCount(String oseiId, String porCd) {
		LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR); 
		/* declaring variable */
		String fetchCount = Query_22_getOEISpecDetailsRecordCount.toString();
		Object recordCount = eMgr.createNativeQuery(fetchCount)
				.setParameter(PRMTR_OEI_SPEC_ID, oseiId)
				.setParameter(PRMTR_PORCD, porCd)
				.getSingleResult();

		/* returning record count */
		LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);
		return bigDecimaltoInt(recordCount);

	}
	
	/**
	 * Method to fetch the Abolish date for the OEI_SPEC_ID from the table.
	 *
	 * @param oseiId the osei id
	 * @param porCd the por cd
	 * @return int
	 */
	private int getAblshDt(String oseiId, String porCd) {
		LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR);
		String ablshDt = Query_40_getAblshDt.toString();
		/* fetching record count */
		Object absDate = eMgr.createNativeQuery(ablshDt)
				.setParameter(PRMTR_OEI_SPEC_ID, oseiId)
				.setParameter(PRMTR_PORCD, porCd)
				.getSingleResult();
		LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);
		/* returning int */
		return stringtoInt(absDate.toString());

	}

}
