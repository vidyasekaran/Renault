/*
 * System Name     :Post Dragon 
 * Sub system Name :Interface 
 * Function ID     :PST-DRG-I000047
 * Module          :SP SPEC MASTER
 *  
 * Process Outline :Receive Week No Calendar Interface from Plant. 
 *
 * <Revision History>
 * Date       Name(Company Name)             Description 
 * ---------- ------------------------------ ---------------------
 * 17-01-2016  	  z014029(RNTBCI)               New Creation
 *
 */
package com.nissangroups.pd.i000047.writer;

import static com.nissangroups.pd.util.PDConstants.DATE_FORMAT_MONTH;
import static com.nissangroups.pd.util.PDConstants.DOLLAR;
import static com.nissangroups.pd.util.PDConstants.INSIDE_METHOD;
import static com.nissangroups.pd.util.PDConstants.OUTSIDE_METHOD;
import static com.nissangroups.pd.util.PDConstants.PERSISTENCE_NAME;
import static com.nissangroups.pd.util.PDConstants.UNABLE_TO_OBTAIN_TRANS_ENTITY_MGR_MSG;
import static com.nissangroups.pd.util.PDMessageConsants.M00098;
import static com.nissangroups.pd.util.PDMessageConsants.M00141;
import static com.nissangroups.pd.util.PDMessageConsants.M00164;
import static com.nissangroups.pd.util.PDMessageConsants.M00306;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessResourceFailureException;

import com.nissangroups.pd.exception.PdApplicationException;
import com.nissangroups.pd.i000047.output.I000047OutputBean;
import com.nissangroups.pd.i000047.util.I000047QueryConstants;
import com.nissangroups.pd.model.MstWkNoClndr;
import com.nissangroups.pd.model.MstWkNoClndrPK;
import com.nissangroups.pd.util.IFConstants;
import com.nissangroups.pd.util.IfCommonUtil;
import com.nissangroups.pd.util.PDConstants;

public class I000047Writer implements ItemWriter<I000047OutputBean> {

	/** Constant LOG. */
	private static final Log LOG = LogFactory.getLog(I000047Writer.class
			.getName());

	/** Variable entity manager. */
	@PersistenceContext(unitName = PERSISTENCE_NAME)
	private EntityManager entityManager;

	/** Common utility service bean injection */
	@Autowired(required = false)
	IfCommonUtil commonutility;

	/** Variable Job Execution */
	private JobExecution jobExec;

	/** Variable Interface FileId */
	private String ifFileId;

	/** Variable porCd */
	private String porCd;

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
		String porCd = jobExec.getJobParameters().getString(IFConstants.POR_CD);
		String ifFileId = jobExec.getJobParameters().getString(
				IFConstants.INTERFACE_FILE_ID);
		this.ifFileId = ifFileId;
		this.porCd = porCd;
		LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);
	}

	/**
	 * This method is to process all the extracted Common Interface Data records
	 * except the error records and insert / update into WEEK_NO_CALENDAR_MST
	 * table
	 * 
	 * @param item
	 *            I000047OutputBean
	 * @return the string
	 * @throws Exception
	 *             the exception
	 */
	@Override
	public void write(List<? extends I000047OutputBean> items) throws Exception {

		LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR);
		if (entityManager == null) {
			throw new DataAccessResourceFailureException(
					UNABLE_TO_OBTAIN_TRANS_ENTITY_MGR_MSG);
		}

		if (!items.isEmpty()) {
			for (I000047OutputBean item : items) {

				Date date = new Date();
				// long rowCount = commonutility.getRowCount() + 1;

				// P0002 Need to get the values from I000047OutputBean item and
				// set it into MstWkNoClndr

				MstWkNoClndr mstWkNoClndr = new MstWkNoClndr();
				mstWkNoClndr.setId(new MstWkNoClndrPK());
				mstWkNoClndr.getId().setPorCd(porCd);
				mstWkNoClndr.getId().setProdMnth(
						prdMnthValidation(item.getCol1(), (item.getCol2())));
				mstWkNoClndr.setCrtdBy(ifFileId);
				mstWkNoClndr.setCrtdDt(new Timestamp(date.getTime()));
				mstWkNoClndr.setUpdtdBy(ifFileId);
				mstWkNoClndr.setUpdtdDt(new Timestamp(date.getTime()));

				/**
				 * P0002.a Insert Week No 01:- Condition1: Allowed non
				 * operational flag values are "B", "F" , "*" ," "
				 */
				if (("B").equals(item.getCol3())
						|| ("F").equals(item.getCol3())
						|| ("*").equals(item.getCol3())
						|| (" ").equals(item.getCol3())) {
					/**
					 * Condition: P0001 - Col3 not equal "B" then process the
					 * below condition.
					 */
					if (!"B".equals(item.getCol3())) {

						mstWkNoClndr.setNonOprtnlFlag(item.getCol3());
						mstWkNoClndr.getId().setProdWkNo(
								PDConstants.CONSTANT_ONE);
						mstWkNoClndr.setWkNoYear(item.getCol4());
						mstWkNoClndr.setWkStrtDate(item.getCol1().concat(
								item.getCol5()));
						mstWkNoClndr.setWkEndDate(item.getCol1().concat(
								item.getCol6()));
						insertMstWkNoClndr(mstWkNoClndr);
					}
					/**
					 * Condition: P0001 - Col3 == "B" then below error message
					 * will be written in log file.
					 */
					else {
						String errMsg = M00306
								.replace(PDConstants.ERROR_MESSAGE_1,
										PDConstants.IF_ID_47)
								.replace(PDConstants.ERROR_MESSAGE_2,
										PDConstants.IF_ID_B)
								.replace(PDConstants.ERROR_MESSAGE_3, porCd)
								.replace(PDConstants.ERROR_MESSAGE_4,
										item.getCol1().concat(item.getCol2()))
								.replace(PDConstants.ERROR_MESSAGE_5,
										PDConstants.CONSTANT_ONE);
						LOG.error(errMsg);
						commonutility.setRemarks(errMsg);
						throw new PdApplicationException();
					}
				}
				/**
				 * If the system identifies incorrect "Non operational Flag"
				 * values then written the following error message in log file.
				 */
				else {
					String errMsg = M00098
							.replace(PDConstants.ERROR_MESSAGE_1,
									PDConstants.IF_ID_47)
							.replace(PDConstants.ERROR_MESSAGE_2, porCd)
							.replace(PDConstants.ERROR_MESSAGE_3,
									item.getCol1().concat(item.getCol2()))
							.replace(PDConstants.ERROR_MESSAGE_4,
									PDConstants.CONSTANT_ONE);
					LOG.error(errMsg);
					commonutility.setRemarks(errMsg);
					throw new PdApplicationException();
				}

				/**
				 * P0002.b Insert Week No 02:- Condition1: Allowed non
				 * operational flag values are "B", "F" , "*" ," "
				 */
				if (("B").equals(item.getCol7())
						|| ("F").equals(item.getCol7())
						|| ("*").equals(item.getCol7())
						|| (" ").equals(item.getCol7())) {

					mstWkNoClndr.setNonOprtnlFlag(item.getCol7());
					mstWkNoClndr.getId().setProdWkNo(PDConstants.CONSTANT_TWO);
					mstWkNoClndr.setWkNoYear(item.getCol8());
					mstWkNoClndr.setWkStrtDate(item.getCol1().concat(
							item.getCol9()));
					mstWkNoClndr.setWkEndDate(item.getCol1().concat(
							item.getCol10()));
					insertMstWkNoClndr(mstWkNoClndr);
				}
				/**
				 * If the system identifies incorrect "Non operational Flag"
				 * values then written the following error message in log file.
				 */
				else {
					String errMsg = M00098
							.replace(PDConstants.ERROR_MESSAGE_1,
									PDConstants.IF_ID_47)
							.replace(PDConstants.ERROR_MESSAGE_2, porCd)
							.replace(PDConstants.ERROR_MESSAGE_3,
									item.getCol1().concat(item.getCol2()))
							.replace(PDConstants.ERROR_MESSAGE_4,
									PDConstants.CONSTANT_TWO);
					LOG.error(errMsg);
					commonutility.setRemarks(errMsg);
					throw new PdApplicationException();
				}

				/**
				 * P0002.c Insert Week No 03:- Condition1: Allowed non
				 * operational flag values are "B", "F" , "*" ," "
				 */
				if (("B").equals(item.getCol11())
						|| ("F").equals(item.getCol11())
						|| ("*").equals(item.getCol11())
						|| (" ").equals(item.getCol11())) {

					mstWkNoClndr.getId().setProdWkNo(PDConstants.CONSTANT_3);
					mstWkNoClndr.setWkNoYear(item.getCol12());
					mstWkNoClndr.setWkStrtDate(item.getCol1().concat(
							item.getCol13()));
					mstWkNoClndr.setWkEndDate(item.getCol1().concat(
							item.getCol14()));
					mstWkNoClndr.setNonOprtnlFlag(item.getCol11());
					insertMstWkNoClndr(mstWkNoClndr);
				}
				/**
				 * If the system identifies incorrect "Non operational Flag"
				 * values then written the following error message in log file.
				 */
				else {
					String errMsg = M00098
							.replace(PDConstants.ERROR_MESSAGE_1,
									PDConstants.IF_ID_47)
							.replace(PDConstants.ERROR_MESSAGE_2, porCd)
							.replace(PDConstants.ERROR_MESSAGE_3,
									item.getCol1().concat(item.getCol2()))
							.replace(PDConstants.ERROR_MESSAGE_4,
									PDConstants.CONSTANT_3);
					LOG.error(errMsg);
					commonutility.setRemarks(errMsg);
					throw new PdApplicationException();
				}

				/**
				 * P0002.d Insert Week No 04:- Condition1: Allowed non
				 * operational flag values are "B", "F" , "*" ," "
				 */
				if (("B").equals(item.getCol15())
						|| ("F").equals(item.getCol15())
						|| ("*").equals(item.getCol15())
						|| (" ").equals(item.getCol15())) {
					/**
					 * Condition: P0001 - Col15 not equal F and Col20 not equal
					 * null then process the below condition
					 */
					if ((item.getCol20() == null && ("F").equals(item
							.getCol15()))) {

						String errMsg = M00306
								.replace(PDConstants.ERROR_MESSAGE_1,
										PDConstants.IF_ID_47)
								.replace(PDConstants.ERROR_MESSAGE_2,
										PDConstants.IF_ID_F)
								.replace(PDConstants.ERROR_MESSAGE_3, porCd)
								.replace(PDConstants.ERROR_MESSAGE_4,
										item.getCol1().concat(item.getCol2()))
								.replace(PDConstants.ERROR_MESSAGE_5,
										PDConstants.CONSTANT_4);
						LOG.error(errMsg);
						commonutility.setRemarks(errMsg);
						throw new PdApplicationException();

					}
					/**
					 * Condition: P0001 - Col15 is "F" and Col20 is null then
					 * below error message will be written in log file.
					 */
					else {
						mstWkNoClndr.getId()
								.setProdWkNo(PDConstants.CONSTANT_4);
						mstWkNoClndr.setWkNoYear(item.getCol16());
						mstWkNoClndr.setWkStrtDate(item.getCol1().concat(
								item.getCol17()));
						mstWkNoClndr.setWkEndDate(item.getCol1().concat(
								item.getCol18()));
						mstWkNoClndr.setNonOprtnlFlag(item.getCol15());
						insertMstWkNoClndr(mstWkNoClndr);
					}
				}
				/**
				 * If the system identifies incorrect "Non operational Flag"
				 * values then written the following error message in log file.
				 */
				else {
					String errMsg = M00098
							.replace(PDConstants.ERROR_MESSAGE_1,
									PDConstants.IF_ID_47)
							.replace(PDConstants.ERROR_MESSAGE_2, porCd)
							.replace(PDConstants.ERROR_MESSAGE_3,
									item.getCol1().concat(item.getCol2()))
							.replace(PDConstants.ERROR_MESSAGE_4,
									PDConstants.CONSTANT_4);
					LOG.error(errMsg);
					commonutility.setRemarks(errMsg);
					throw new PdApplicationException();
				}

				/**
				 * Col19,Col20,Col21,Col22 value is not null then process the
				 * below condition.
				 */
				if (item.getCol19() != null && item.getCol20() != null
						&& item.getCol21() != null && item.getCol22() != null) {
					/**
					 * P0002.e Insert Week No 05:- Condition1: Allowed non
					 * operational flag values are "B", "F" , "*" ," "
					 */
					if (("B").equals(item.getCol19())
							|| ("F").equals(item.getCol19())
							|| ("*").equals(item.getCol19())
							|| (" ").equals(item.getCol19())) {
						/**
						 * Condition: P0001 - Col19 is not equal to "F" then
						 * process the below condition
						 */
						if (!"F".equals(item.getCol19())) {

							mstWkNoClndr.setNonOprtnlFlag(item.getCol19());
							mstWkNoClndr.getId().setProdWkNo(
									PDConstants.CONSTANT_FIVE);
							mstWkNoClndr.setWkNoYear(item.getCol20());
							mstWkNoClndr
									.setWkStrtDate((item.getCol21() != null) ? item
											.getCol1().concat(item.getCol21())
											: item.getCol1());
							mstWkNoClndr
									.setWkEndDate((item.getCol22() != null) ? item
											.getCol1().concat(item.getCol22())
											: item.getCol1());
							insertMstWkNoClndr(mstWkNoClndr);
						}

						/**
						 * Condition: P0001 - Col19 is "F" then below error
						 * message will be written in log file.
						 */
						else {
							String errMsg = M00306
									.replace(PDConstants.ERROR_MESSAGE_1,
											PDConstants.IF_ID_47)
									.replace(PDConstants.ERROR_MESSAGE_2,
											PDConstants.IF_ID_F)
									.replace(PDConstants.ERROR_MESSAGE_3, porCd)
									.replace(
											PDConstants.ERROR_MESSAGE_4,
											item.getCol1().concat(
													item.getCol2()))
									.replace(PDConstants.ERROR_MESSAGE_5,
											PDConstants.CONSTANT_FIVE);
							LOG.error(errMsg);
							commonutility.setRemarks(errMsg);
							throw new PdApplicationException();
						}
					}
					/**
					 * If the system identifies incorrect "Non operational Flag"
					 * values then written the following error message in log
					 * file.
					 */
					else {
						String errMsg = M00098
								.replace(PDConstants.ERROR_MESSAGE_1,
										PDConstants.IF_ID_47)
								.replace(PDConstants.ERROR_MESSAGE_2, porCd)
								.replace(PDConstants.ERROR_MESSAGE_3,
										item.getCol1().concat(item.getCol2()))
								.replace(PDConstants.ERROR_MESSAGE_4,
										PDConstants.CONSTANT_FIVE);
						LOG.error(errMsg);
						commonutility.setRemarks(errMsg);
						throw new PdApplicationException();
					}
				}
				/** If Insertion failed then process the below error message */
				/*else {
					String errMsg = M00164
							.replace(PDConstants.ERROR_MESSAGE_1,
									PDConstants.IF_ID_47)
							.replace(PDConstants.ERROR_MESSAGE_2, porCd)
							.replace(PDConstants.ERROR_MESSAGE_3,
									PDConstants.MST_WK_NO_CLNDR);
					LOG.error(errMsg);
					commonutility.setRemarks(errMsg);
				}*/
				// commonutility.setRowCount(rowCount);
				LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);
			}
			entityManager.flush();
			LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);
		}
	}

	/**
	 * This method is Validating the production month length
	 * 
	 * @param String
	 *            mnth
	 * @param String
	 *            prodYr
	 * 
	 * @return the string
	 * @throws Exception
	 *             the exception
	 */
	public String prdMnthValidation(String mnth, String prodYr)
			throws Exception {

		boolean error = false;
		String prdMnth = "";
		try {
			prdMnth = mnth.trim().concat(prodYr.trim());
			if (prdMnth.length() == 6) {

				SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
						DATE_FORMAT_MONTH);
				simpleDateFormat.parse(prdMnth.trim());

			} else {
				throw new Exception("");
			}
		} catch (Exception e) {
			error = true;
			LOG.error(e);
		}
		if (error) {
			String errMsg = M00141.replace(PDConstants.ERROR_MESSAGE_1,
					PDConstants.IF_ID_47);

			LOG.error(errMsg);
			commonutility.setRemarks(errMsg);
			throw new Exception(
					"M00141 : Production Month value should be in YYYYMM format");
		}
		return prdMnth;
	}

	/**
	 * This method is inserting the Processed values into WEEK_NO_CALENDAR_MST
	 * table
	 * 
	 * @param MstWkNoClndr
	 *            wkNoClndr
	 * @throws Exception
	 *             the exception
	 */
	public void insertMstWkNoClndr(MstWkNoClndr wkNoClndr) throws Exception {

		LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR);

		Query query = entityManager
				.createNativeQuery(I000047QueryConstants.insertMstWkNoClndr
						.toString());
		query.setParameter(IFConstants.POR_CD, wkNoClndr.getId().getPorCd());
		query.setParameter(IFConstants.PROD_MNTH, wkNoClndr.getId()
				.getProdMnth());
		query.setParameter(IFConstants.PROD_WK_NO, wkNoClndr.getId()
				.getProdWkNo());
		query.setParameter(IFConstants.WK_NO_YEAR, wkNoClndr.getWkNoYear());
		query.setParameter(IFConstants.WK_STRT_DATE, wkNoClndr.getWkStrtDate());
		query.setParameter(IFConstants.WK_END_DATE, wkNoClndr.getWkEndDate());
		query.setParameter(IFConstants.NON_OPRTNL_FLAG,
				wkNoClndr.getNonOprtnlFlag());

		query.setParameter(IFConstants.CRTD_BY, wkNoClndr.getCrtdBy());
		query.setParameter(IFConstants.UPDTD_BY, wkNoClndr.getUpdtdBy());
		query.executeUpdate();

		LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);
	}
}