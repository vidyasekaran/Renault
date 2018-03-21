/*
 * System Name     : Post Dragon 
 * Sub system Name : I Interface
 * Function ID     : PST_DRG_I000113
 * Module          : CM COMMON
 * Process Outline : Logical Pipeline Update from SAP
 *
 * <Revision History>
 * Date       Name(Company Name)             Description 
 * ---------- ------------------------------ ---------------------
 * @Date      z015895(RNTBCI)               New Creation
 *
 */
package com.nissangroups.pd.i000113.writer;

import static com.nissangroups.pd.util.PDConstants.DOLLAR;
import static com.nissangroups.pd.util.PDConstants.INSIDE_METHOD;
import static com.nissangroups.pd.util.PDConstants.JPA_WRITE_ITEM_SIZE;
import static com.nissangroups.pd.util.PDConstants.OUTSIDE_METHOD;
import static com.nissangroups.pd.util.PDConstants.PERSISTENCE_NAME;
import static com.nissangroups.pd.util.PDConstants.UNABLE_TO_OBTAIN_TRANS_ENTITY_MGR_MSG;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessResourceFailureException;

import com.nissangroups.pd.exception.PdApplicationException;
import com.nissangroups.pd.i000113.util.I000113QueryConstants;
import com.nissangroups.pd.model.TrnLgclPpln;
import com.nissangroups.pd.util.IFConstants;
import com.nissangroups.pd.util.IfCommonUtil;
import com.nissangroups.pd.util.PDConstants;
import com.nissangroups.pd.util.PDMessageConsants;

/**
 * This Class I000113Writer using to write the data from logical pipeline transaction
 * table and process it.
 * 
 * @author z015895-dev
 * 
 */
public class I000113Writer implements ItemWriter<TrnLgclPpln> {

	/** Constant LOG. */
	private static final Log LOG = LogFactory.getLog(I000113Writer.class
			.getName());

	/** Variable entity manager. */
	@PersistenceContext(unitName = PERSISTENCE_NAME)
	private EntityManager entityManager;

	/** Common utility service bean injection */
	@Autowired(required = false)
	IfCommonUtil commonutility;

	/**
	 * 
	 * Process the supplied data element. Will not be called with any null items
	 * in normal operation.
	 * 
	 * @param items
	 * @throws Exception
	 *             if there are errors(Unable to obtain a transactional Entity
	 *             Manager).
	 */
	@Override
	public void write(List<? extends TrnLgclPpln> items) throws Exception {

		LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR);
		if (entityManager == null) {
			throw new DataAccessResourceFailureException(
					UNABLE_TO_OBTAIN_TRANS_ENTITY_MGR_MSG);
		}
		mergeData(items);
		entityManager.flush();
		LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);

	}

	/*
	 * This method use for merge update or Insert the data into Logical Pipeline
	 * trn table
	 * 
	 * @param items
	 */
	@SuppressWarnings("unchecked")
	public void mergeData(List<? extends TrnLgclPpln> items) throws Exception {
		LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR);
		if (LOG.isDebugEnabled()) {
			LOG.debug(JPA_WRITE_ITEM_SIZE + items.size());
		}
		for (TrnLgclPpln item : items) {
			String queryString = I000113QueryConstants.getLgclPplnTrnById
					.toString().replace(":vhclSeqId",
							item.getVhclSeqId().trim());
			Query query = entityManager.createQuery(queryString);
			List<TrnLgclPpln> resultList = query.getResultList();
			if (!resultList.isEmpty()) {
				String error = errorCheck(resultList.get(0), item);
				if (!("").equals(error)) {
					commonutility.setRemarks(error);
					throw new PdApplicationException(error);
				}
				updateLgclPplTrn(item);
			} else {
				// insert new data
				insertLgclPplTrn(item);
			}
		}

		LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);
	}

	/**
	 * insert the data inot Logical Pipeline Transaction table based on input
	 * parameters
	 * 
	 * @param item
	 */
	public void insertLgclPplTrn(TrnLgclPpln item) throws Exception {
		LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR);
		String queryString = I000113QueryConstants.insertLgclPplTrn.toString();
		Query query = entityManager.createNativeQuery(queryString);
		query.setParameter(IFConstants.VHCL_SEQ_ID, item.getVhclSeqId().trim());
		query.setParameter(IFConstants.POR_CD, item.getPorCd());
		query.setParameter(IFConstants.PROD_PLNT_CD, item.getProdPlntCd());
		query.setParameter(IFConstants.OFFLN_PLAN_DATE, item.getOfflnPlanDate());
		query.setParameter(IFConstants.LGCL_PPLN_STAGE_CD,
				item.getLgclPplnStageCd());
		query.setParameter(IFConstants.SLS_NOTE_NO, item.getSlsNoteNo());
		query.setParameter(IFConstants.EX_NO, item.getExNo());
		query.setParameter(IFConstants.PROD_MNTH, item.getProdMnth());
		query.setParameter(IFConstants.POT_CD, item.getPotCd());
		query.setParameter(IFConstants.PROD_ORDR_NO, item.getProdOrdrNo());
		if (item.getProdOrdrNo() == null || ("").equals(item.getProdOrdrNo())) {
			query.setParameter(IFConstants.PROD_ORDR_NO,
					generateProdOrdrNo(item.getPorCd(), item.getProdMnth()));
		}
		query.setParameter(IFConstants.ORDR_DEL_FLAG, item.getOrdrDelFlag());
		query.setParameter(IFConstants.MS_FXD_FLAG, item.getMsFxdFlag());
		query.setParameter(IFConstants.LINE_CLASS, item.getLineClass());
		query.setParameter(IFConstants.FRZN_TYPE_CD, item.getFrznTypeCd());
		query.setParameter(IFConstants.PROD_MNTH_CD, item.getProdMthdCd());
		query.setParameter(IFConstants.VIN_NO, item.getVinNo());
		query.setParameter(IFConstants.CRTD_BY, item.getCrtdBy());
		query.setParameter(IFConstants.UPDTD_BY, item.getUpdtdBy());
		if (item.getVinNo() != null || !("").equals(item.getVinNo())) {
			updateVinFlag(item.getVhclSeqId().trim());
		}
		query.setParameter(IFConstants.OSEI_ID, item.getOseiId());

		query.executeUpdate();
		LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);
	}

	/**
	 * update the Logical Pipeline Transaction table based on given inputs.
	 * 
	 * @param item
	 */
	public void updateLgclPplTrn(TrnLgclPpln item) {
		LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR);

		String queryString = I000113QueryConstants.updateLgclPplTrn.toString();

		// value replace
		queryString = queryString
				.replace(":" + IFConstants.VHCL_SEQ_ID,(item.getVhclSeqId() == null) ? "''" : "'" + item.getVhclSeqId() + "'")
				.replace(":" + IFConstants.PROD_PLNT_CD,(item.getProdPlntCd() == null) ? "''" : "'"+ item.getProdPlntCd() + "'")
				.replace(":" + IFConstants.OFFLN_PLAN_DATE,(item.getOfflnPlanDate() == null) ? "''" : "'"+ item.getOfflnPlanDate() + "'")
				.replace(":" + IFConstants.LGCL_PPLN_STAGE_CD,(item.getLgclPplnStageCd() == null) ? "''" : "'"+ item.getLgclPplnStageCd() + "'")
				.replace(":" + IFConstants.SLS_NOTE_NO,(item.getSlsNoteNo() == null) ? "''" : "'"+ item.getSlsNoteNo() + "'")
				.replace(":" + IFConstants.EX_NO,(item.getExNo() == null) ? "''" : "'" + item.getExNo()+ "'")
				.replace(":" + IFConstants.PROD_MNTH,(item.getProdMnth() == null) ? "''" : "'"+ item.getProdMnth() + "'")
				.replace(":" + IFConstants.POT_CD,(item.getPotCd() == null) ? "''" : "'"+ item.getPotCd() + "'")
				.replace(":" + IFConstants.PROD_ORDR_NO,(item.getProdOrdrNo() == null) ? "''" : "'"+ item.getProdOrdrNo() + "'")
				.replace(":" + IFConstants.ORDR_DEL_FLAG,(item.getOrdrDelFlag() == null) ? "''" : "'"+ item.getOrdrDelFlag() + "'")
				.replace(":" + IFConstants.MS_FXD_FLAG,(item.getMsFxdFlag() == null) ? "''" : "'"+ item.getMsFxdFlag() + "'")
				.replace(":" + IFConstants.LINE_CLASS,(item.getLineClass() == null) ? "''" : "'"+ item.getLineClass() + "'")
				.replace(":" + IFConstants.FRZN_TYPE_CD,(item.getFrznTypeCd() == null) ? "''" : "'"+ item.getFrznTypeCd() + "'")
				.replace(":" + IFConstants.PROD_MNTH_CD,(item.getProdMthdCd() == null) ? "''" : "'"+ item.getProdMthdCd() + "'")
				.replace(":" + IFConstants.VIN_NO,(item.getVinNo() == null) ? "''" : "'"+ item.getVinNo() + "'")
				.replace(":" + IFConstants.OSEI_ID,(item.getOseiId() == null || ("").equals(item.getOseiId())) ? "''" : "'" + item.getOseiId()+ "'")
				.replace(":" + IFConstants.UPDTD_BY,(item.getUpdtdBy() == null || ("").equals(item.getUpdtdBy())) ? "''" : "'"+ item.getUpdtdBy() + "'");
		Query query = entityManager.createNativeQuery(queryString);
		query.executeUpdate();
		LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);
	}

	/**
	 * This method used for check the Error for logical pipeline trn data
	 * 
	 * @param makeDate
	 * 
	 * @param sapData
	 * 
	 * @return errorMsg
	 */
	public String errorCheck(TrnLgclPpln makeData, TrnLgclPpln sapData)
			throws PdApplicationException {

		LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR);
		String errorMsg = "";

		try {

			if (makeData != null
					&& sapData != null
					&& makeData.getVhclSeqId().trim()
							.equalsIgnoreCase(sapData.getVhclSeqId().trim())) {

				// check ms_sshedule_flag
				if (("0").equals(sapData.getMsFxdFlag())) {
					errorMsg = PDMessageConsants.M00329.replace(
							PDConstants.ERROR_MESSAGE_2,
							makeData.getVhclSeqId());

					// check duplicate production number
				} else if ((makeData.getProdOrdrNo() == null && sapData
						.getProdOrdrNo() != null)
						|| (!makeData.getProdOrdrNo().trim()
								.equalsIgnoreCase(sapData.getProdOrdrNo()))
						&& ("0").equals(sapData.getOrdrDelFlag())) {
					errorMsg = PDMessageConsants.M00330.replace(
							PDConstants.ERROR_MESSAGE_2,
							makeData.getVhclSeqId());

					// check vin_no null
				} else if (makeData.getVinNo() != null
						&& !("").equals(makeData.getVinNo())
						&& (sapData.getVinNo() == null || ("").equals(sapData
								.getVinNo()))) {
					errorMsg = PDMessageConsants.M00331.replace(
							PDConstants.ERROR_MESSAGE_2,
							makeData.getVhclSeqId());
				}

			}

		} catch (Exception e) {
			LOG.error("**Exception in check errors**" + e);
			throw new PdApplicationException(e.getMessage());
		}

		LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);
		return errorMsg;
	}

	/**
	 * this methos generate Production order number based on some contions
	 * 
	 * @param porCd
	 * 
	 * @param prodMon
	 * 
	 * @return prodOrdrNo
	 */
	private String generateProdOrdrNo(String porCd, String prodMon)
			throws PdApplicationException {

		LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR);
		try {

			String prodOrdrNo = "";
			int month = 0;
			String yrStr = "";
			if (prodMon != null && prodMon.length() == IFConstants.PROD_MNTH_CHAR_LENGTH) {
				month = Integer.valueOf(prodMon.substring(IFConstants.PROD_MNTH_START_INDEX, IFConstants.PROD_MNTH_END_INDEX));
				yrStr = prodMon.charAt(IFConstants.PROD_MNTH_YEAR_INDEX) + "";
			} else {
				LOG.error("*** Invalid Production Month for generating new Production Order ***");
			}
			String seqNo = "";
			int seqNumber = 0;
			Object seq = commonutility.getMapData().get(porCd);
			if (seq == null) {
				seqNo = IFConstants.INIT_SEQ_NO;
				seqNumber = 1;
			} else {
				seqNumber = Integer.valueOf(seq + "") + 1;
				String seqStr = "" + seqNumber;
				seqNo = generateSeqNo(seqStr.length(), seqNumber);
			}

			prodOrdrNo = getMonCd(month) + "" + yrStr + "" + porCd + "" + seqNo;
			commonutility.getMapData().put(porCd, seqNumber);
			LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);
			return prodOrdrNo;

		} catch (Exception e) {
			LOG.error("**Exception in generating ProdOrdrNo**" + e);
			throw new PdApplicationException(e.getMessage());
		}

	}

	/**
	 * this method use for update the vehicle Sequence id
	 * 
	 * @param vhclSeqNo
	 */
	public void updateVinFlag(String vhclSeqNo) {

		LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR);
		String vinFlagUpdateQuery = I000113QueryConstants.updateFlgInPhysclPplTrn
				.toString();
		vinFlagUpdateQuery = vinFlagUpdateQuery.replace(":"
				+ IFConstants.VHCL_SEQ_ID, vhclSeqNo);
		entityManager.createNativeQuery(vinFlagUpdateQuery).executeUpdate();
		LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);
	}

	/**
	 * this method used to get month character based on the month value
	 * 
	 * @parm month
	 * 
	 * @return monthstr
	 */
	public String getMonCd(int month) {

		String monthStr;
		switch (month) {
		case 1:
			monthStr = IFConstants.ALPHABET_A;
			break;
		case 2:
			monthStr = IFConstants.ALPHABET_B;
			break;
		case 3:
			monthStr = IFConstants.ALPHABET_C;
			break;
		case 4:
			monthStr = IFConstants.ALPHABET_D;
			break;
		case 5:
			monthStr = IFConstants.ALPHABET_E;
			break;
		case 6:
			monthStr = IFConstants.ALPHABET_F;
			break;
		case 7:
			monthStr = IFConstants.ALPHABET_G;
			break;
		case 8:
			monthStr = IFConstants.ALPHABET_H;
			break;
		case 9:
			monthStr = IFConstants.ALPHABET_I;
			break;
		case 10:
			monthStr = IFConstants.ALPHABET_J;
			break;
		case 11:
			monthStr = IFConstants.ALPHABET_K;
			break;
		case 12:
			monthStr = IFConstants.ALPHABET_L;
			break;
		default:
			monthStr = "";
			break;
		}
		return monthStr;
	}

	/**
	 * this method form the Sequence number based on input parameters
	 * 
	 * @param length
	 * 
	 * @param seq
	 * 
	 * @return seqno
	 */
	public String generateSeqNo(int length, int seq) {

		String seqNo;
		switch (length) {
		case 1:
			seqNo = IFConstants.FIVE_ZERO + seq;
			break;
		case 2:
			seqNo = IFConstants.FOUR_ZERO + seq;
			break;
		case 3:
			seqNo = IFConstants.TRI_ZERO + seq;
			break;
		case 4:
			seqNo = IFConstants.DOUBLE_ZERO + seq;
			break;
		case 5:
			seqNo = IFConstants.ZERO + seq;
			break;
		case 6:
			seqNo = "" + seq;
			break;
		default:
			seqNo = "";
			break;
		}
		return seqNo;

	}

}
