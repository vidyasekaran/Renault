/*
 * System Name     :Post Dragon 
 * Sub system Name :Interface
 * Function ID     :PST-DRG-I000042
 * Module          :Ordering
 * Process Outline :Interface To Receive Monthly Production Schedule from Plant
 *
 * <Revision History>
 * Date       Name(Company Name)             Description 
 * ---------- ------------------------------ ---------------------
 * 29-10-2015  	  z014029(RNTBCI)               New Creation
 *
 */
package com.nissangroups.pd.i000042.writer;

import static com.nissangroups.pd.util.B000002QueryConstants.Query_24_getOEISpecDetailsforUKOSEIID;
import static com.nissangroups.pd.util.B000002QueryConstants.Query_25_OseiAblshdtUpdt;
import static com.nissangroups.pd.util.B000002QueryConstants.Query_42_InsertIntoOSEIDTL;
import static com.nissangroups.pd.util.PDConstants.DOLLAR;
import static com.nissangroups.pd.util.PDConstants.EMPTY_STRING;
import static com.nissangroups.pd.util.PDConstants.INSIDE_METHOD;
import static com.nissangroups.pd.util.PDConstants.OUTSIDE_METHOD;
import static com.nissangroups.pd.util.PDConstants.PERSISTENCE_NAME;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
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

import com.nissangroups.pd.exception.PdApplicationNonFatalException;
import com.nissangroups.pd.model.TrnMnthProdShdlIf;
import com.nissangroups.pd.util.QueryConstants;

/**
 * Writer Class to write the data in OrderableSalesEndItemDetailMst table
 * Process Id - P0005.
 * 
 * @version V1.0
 */

public class I000042CustomWriter implements ItemWriter<List<TrnMnthProdShdlIf>> {

	/** Constant LOG. */
	private static final Log LOG = LogFactory.getLog(I000042CustomWriter.class);

	/** Object emgr. */
	@PersistenceContext(name = PERSISTENCE_NAME)
	private EntityManager eMgr;

	/** Variable fetchoseidataforukoseid. */
	String fetchOSEIdataforUKOSEID = Query_24_getOEISpecDetailsforUKOSEIID
			.toString();

	/** Variable oseiablshupdt. */
	String oseiAblshUpdt = Query_25_OseiAblshdtUpdt.toString();

	/** Variableeimablshdt. */
	String eimablshDt = EMPTY_STRING;

	/** Variableeimsttscd. */
	String eimsttsCd = EMPTY_STRING;

	String insertIntoOSEIDTL = Query_42_InsertIntoOSEIDTL;

	PreparedStatement preparedStmt;

	/**
	 * This method to be called before a Step is executed, which comes after a
	 * StepExecution is created and persisted, but before the first item is
	 * read.
	 * 
	 * @param stepExecution
	 * @throws PdApplicationNonFatalException
	 */
	@BeforeStep
	public void beforeStep(StepExecution stepExecution)
			throws PdApplicationNonFatalException {

		Connection connection = null;
		LOG.info("LOg- " + stepExecution);
		Session session = (Session) eMgr.getDelegate();
		SessionFactoryImplementor sessionFactoryImplementation = (SessionFactoryImplementor) session
				.getSessionFactory();
		ConnectionProvider connectionProvider = sessionFactoryImplementation
				.getConnectionProvider();
		try {
			connection = connectionProvider.getConnection();
			preparedStmt = connection
					.prepareStatement(QueryConstants.I42InsrtQry.toString());
		} catch (SQLException e) {
			LOG.info("SQLException - " + e);
			LOG.info("SQLException - " + e.getMessage());
		}

	}

	/**
	 * Method to write the Processed data in MST_OSEI_DTL Table.
	 * 
	 * @param eiOseiProcessedList
	 *            the ei osei processed list
	 */
	@Override
	public void write(
			List<? extends List<TrnMnthProdShdlIf>> eiOseiProcessedList) {
		LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR);
		LOG.info("List size  1 : " + eiOseiProcessedList.size());
		for (List<TrnMnthProdShdlIf> prcssdObjList : eiOseiProcessedList) {
			LOG.info("List size  2 : " + prcssdObjList.size());
			for (TrnMnthProdShdlIf prcssdList : prcssdObjList) {
				mnthlyProdScdl(prcssdList);

			}
			LOG.info("Outside for loop : ");
		}

		LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);
	}

	/**
	 * Method for OSEI DEtails MST creation.
	 * 
	 * @param count
	 *            the count
	 * @param prcssdLsitArray
	 *            the prcssd lsit array
	 */

	private void mnthlyProdScdl(TrnMnthProdShdlIf trnMnthProdShdlIf) {
		LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR);
		/* checking for count */
		try {
			preparedStmt.setString(1, trnMnthProdShdlIf.getId().getPorCd());
			preparedStmt.setString(2, trnMnthProdShdlIf.getId()
					.getOrdrtkBaseMnth());
			preparedStmt.setString(3, trnMnthProdShdlIf.getId().getProdMnth());
			preparedStmt.setString(4, trnMnthProdShdlIf.getId().getSeqId());
			preparedStmt.setString(5, trnMnthProdShdlIf.getPotCd());
			preparedStmt.setString(6, trnMnthProdShdlIf.getOfflnPlanDate());
			preparedStmt.setString(7, trnMnthProdShdlIf.getProdWkNo());
			preparedStmt.setString(8, trnMnthProdShdlIf.getProdDayNo());
			preparedStmt.setBigDecimal(9, trnMnthProdShdlIf.getOrdrQty());
			preparedStmt.setString(10, trnMnthProdShdlIf.getProdPlntCd());
			preparedStmt.setString(11, trnMnthProdShdlIf.getLineClass());
			preparedStmt.setString(12, trnMnthProdShdlIf.getExNo());
			preparedStmt.setString(13, trnMnthProdShdlIf.getProdMthdCd());
			preparedStmt.setString(14, trnMnthProdShdlIf.getFrznTypeCd());
			preparedStmt.setString(15, trnMnthProdShdlIf.getSlsNoteNo());
			preparedStmt.setString(16, trnMnthProdShdlIf.getTyreMkrCd());
			preparedStmt.setString(17, trnMnthProdShdlIf.getDealerLst());
			preparedStmt.setString(18, trnMnthProdShdlIf.getOwnrMnl());

			preparedStmt.setString(19, trnMnthProdShdlIf.getWrntyBklt());
			preparedStmt.setString(20, trnMnthProdShdlIf.getBdyPrtctnCd());
			preparedStmt.setString(21, trnMnthProdShdlIf.getOcfRegionCd());
			preparedStmt.setString(22, trnMnthProdShdlIf.getProdOrdrNo());
			preparedStmt.setString(23, trnMnthProdShdlIf.getVinNo());
			preparedStmt.setString(24, trnMnthProdShdlIf.getWkNoOfYear());
			preparedStmt.setString(25, trnMnthProdShdlIf.getFxdSymbl());
			preparedStmt.setString(26, trnMnthProdShdlIf.getPrtypeFlag());
			preparedStmt.setString(27, trnMnthProdShdlIf.getIntrnlOrTrdFlag());
			preparedStmt.setString(28, trnMnthProdShdlIf.getCrtdBy());
			preparedStmt.setTimestamp(29, trnMnthProdShdlIf.getCrtdDt());
			preparedStmt.setString(30, trnMnthProdShdlIf.getUpdtdBy());
			preparedStmt.setTimestamp(31, trnMnthProdShdlIf.getUpdtdDt());
			preparedStmt.setString(32, trnMnthProdShdlIf.getProdFmlyCd());
			preparedStmt.setString(33, trnMnthProdShdlIf.getCarSrs());
			preparedStmt.setString(34, trnMnthProdShdlIf.getBuyerCd());
			preparedStmt.setString(35, trnMnthProdShdlIf.getAppldMdlCd());
			preparedStmt.setString(36, trnMnthProdShdlIf.getPackCd());
			preparedStmt.setString(37, trnMnthProdShdlIf.getSpecDestnCd());
			preparedStmt.setString(38, trnMnthProdShdlIf.getAddSpecCd());
			preparedStmt.setString(39, trnMnthProdShdlIf.getExtClrCd());
			preparedStmt.setString(40, trnMnthProdShdlIf.getIntClrCd());
			preparedStmt.setString(41, trnMnthProdShdlIf.getErrSttsCd());
			preparedStmt.setString(42, trnMnthProdShdlIf.getLocalProdOrdrNo());
			preparedStmt.setString(43, trnMnthProdShdlIf.getErrormessage());
			preparedStmt.setString(44, trnMnthProdShdlIf.getOseiId());
			preparedStmt.executeUpdate();

		} catch (SQLException e) {
			LOG.info("SQLException - " + e);
			LOG.info("SQLException - " + e.getMessage());
		}

		LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);

	}

}
