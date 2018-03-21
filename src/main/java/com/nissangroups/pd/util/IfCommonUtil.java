package com.nissangroups.pd.util;

import static com.nissangroups.pd.util.PDConstants.DOLLAR;
import static com.nissangroups.pd.util.PDConstants.ERROR_MESSAGE;
import static com.nissangroups.pd.util.PDConstants.INSIDE_METHOD;
import static com.nissangroups.pd.util.PDConstants.OUTSIDE_METHOD;
import static com.nissangroups.pd.util.PDConstants.PERSISTENCE_NAME;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.nissangroups.pd.bean.IfErrorReport;
import com.nissangroups.pd.exception.PdApplicationException;

public class IfCommonUtil {
	/**
	 * Constant LOG
	 */
	private static final Log LOG = LogFactory.getLog(IfCommonUtil.class
			.getName());

	/** Stores entity manager */
	@PersistenceContext(unitName = PERSISTENCE_NAME)
	private EntityManager entityManager;

	private long rowCount;

	private int writeCount;

	private String status;

	private String remarks;
	
	private boolean errFlg;

	private Map<String, Object> mapData = new HashMap<String, Object>();
	
	private List<IfErrorReport> ifErrList = new ArrayList<IfErrorReport>();

	public boolean insertCmnFileHdr(String ifFileId, long seqNo,
			String fileName, char trnType) {
		LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR);
		try {
			Query hdrInsert = entityManager
					.createNativeQuery(IFQueryConstants.insertCmnHeader
							.toString());
			hdrInsert.setParameter("IF_FILE_ID", ifFileId);
			hdrInsert.setParameter("SEQ_NO", seqNo);
			hdrInsert.setParameter("FILE_NAME", fileName);
			hdrInsert.setParameter("TRN_TYPE", trnType);
			hdrInsert.executeUpdate();
		} catch (Exception e) {
			LOG.error(ERROR_MESSAGE, e);
			return false;		
		}
		LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);

		return true;
	}

	public boolean updateCmnFileHdr(String ifFileId, long seqNo, String status,
			String remarks) {
		LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR);
		try {
			Query hdrInsert = entityManager
					.createNativeQuery(IFQueryConstants.updateCmnHeader
							.toString());
			hdrInsert.setParameter("IF_FILE_ID", ifFileId);
			hdrInsert.setParameter("SEQ_NO", seqNo);
			hdrInsert.setParameter("STTS", status);
			hdrInsert.setParameter("REMARKS", remarks);
			hdrInsert.executeUpdate();

		} catch (Exception e) {
			LOG.error(ERROR_MESSAGE, e);
			//return false;
			CommonUtil.stopBatch();
		}
		LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);

		return true;
	}

	/*
	 * Get the sequence number for provided interface id
	 */

	@SuppressWarnings("unchecked")
	public long getSequenceNoForInterfaceId(String fileInterfaceID)
			throws PdApplicationException {

		Long seqNo = 0L;
		List<Object> result = null;
		try {
			LOG.info(new StringBuilder("Starint....LOG ")
					.append(this.getClass().getName())
					.append(" in getSequenceNoForInterfaceId").toString());
			if (null != fileInterfaceID) {
				result = (List<Object>) entityManager.createQuery(
						"SELECT MAX(f.id.seqNo) FROM CmnFileHdr  f where f.id.ifFileId='"
								+ fileInterfaceID + "'").getResultList();
				seqNo = ((Long) result.get(0) == null) ? 0L : (Long) result
						.get(0);
				seqNo++;
			} else {
				LOG.info("Interface id should not be null");
				throw new PdApplicationException(
						"Interface id should not be null");
			}

		} catch (Exception e) {
			LOG.error(ERROR_MESSAGE, e);
		}

		return seqNo;

	}

	public String getMaxOrdrTakBasMnthByCd() {

		Query query = entityManager
				.createNativeQuery(IFQueryConstants.maxOrdrTakBasMon.toString());
		Object orderTakBasMnth = query.getSingleResult();
		if (orderTakBasMnth == null) {
			return "";
		}
		return orderTakBasMnth.toString();
	}

	@SuppressWarnings("unchecked")
	public String getVal1FrmPrmtrMasByCd(String prmtrCd) {
		
		Query query = entityManager.createNativeQuery(IFQueryConstants.prmMasVal1.toString());
		query.setParameter(IFConstants.PRMTR_CD, prmtrCd);		
		List<String> val1 = query.getResultList();
		if (val1.isEmpty()) {
			return "";
		}
		return val1.get(0).toString();
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public long getRowCount() {
		return rowCount;
	}

	public void setRowCount(long rowCount) {
		this.rowCount = rowCount;
	}

	public EntityManager getEntityManager() {
		return entityManager;
	}

	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	public int getWriteCount() {
		return writeCount;
	}

	public void setWriteCount(int writeCount) {
		this.writeCount = writeCount;
	}

	public Map<String, Object> getMapData() {
		return mapData;
	}

	public void setMapData(Map<String, Object> mapData) {
		this.mapData = mapData;
	}

	public List<IfErrorReport> getIfErrList() {
		return ifErrList;
	}

	public void setIfErrList(List<IfErrorReport> ifErrList) {
		this.ifErrList = ifErrList;
	}

	public boolean isErrFlg() {
		return errFlg;
	}

	public void setErrFlg(boolean errFlg) {
		this.errFlg = errFlg;
	}	
	

}
