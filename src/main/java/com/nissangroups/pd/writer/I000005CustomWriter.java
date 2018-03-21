/*
 * System Name     :Post Dragon 
 * Sub system Name :Interface
 * Function ID     :PST-DRG-I000005
 * Module          :SP Spec Master				
 * Process Outline :Receive Exterior Color master Interface from DRG-VSM													
 *
 * <Revision History>
 * Date       Name(Company Name)             Description 
 * ---------- ------------------------------ ---------------------
 * 29-06-2015  	  z011479(RNTBCI)               New Creation
 *
 */
package com.nissangroups.pd.writer;

import static com.nissangroups.pd.util.PDConstants.DOLLAR;
import static com.nissangroups.pd.util.PDConstants.INSIDE_METHOD;
import static com.nissangroups.pd.util.PDConstants.OUTSIDE_METHOD;
import static com.nissangroups.pd.util.PDConstants.PERSISTENCE_NAME;
import static com.nissangroups.pd.util.PDConstants.UNABLE_TO_OBTAIN_TRANS_ENTITY_MGR_MSG;
import static com.nissangroups.pd.util.PDConstants.JPA_WRITE_ITEM_SIZE;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.item.ItemWriter;
import org.springframework.dao.DataAccessResourceFailureException;

import com.nissangroups.pd.model.MstExtClr;
import com.nissangroups.pd.util.PDConstants;
import com.nissangroups.pd.util.QueryConstants;


/**
 * This is the custom writer to write the interface data in Master Exterior Color database.
 *
 * @author z011479
 */
public class I000005CustomWriter implements ItemWriter<MstExtClr> {
	
	/** Constant LOG. */
	private static final Log LOG = LogFactory.getLog(I000005CustomWriter.class
			.getName());
	
	/** Variable entity manager. */
	@PersistenceContext(unitName = PERSISTENCE_NAME)
	private EntityManager entityManager;

	/* (non-Javadoc)
	 * @see org.springframework.batch.item.ItemWriter#write(java.util.List)
	 */
	@Override
	public void write(List<? extends MstExtClr> items) throws Exception {
		LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR);
		if (entityManager == null) {
			throw new DataAccessResourceFailureException(
					UNABLE_TO_OBTAIN_TRANS_ENTITY_MGR_MSG);
		}
		mergeData(items);
		entityManager.flush();
		LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);

	}

	/**
	 * This method used to merge the data in to the Master Table.
	 *
	 * @param items the items
	 */
	public void mergeData(List<? extends MstExtClr> items) {
		LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR);
		if (LOG.isDebugEnabled()) {
			LOG.debug(JPA_WRITE_ITEM_SIZE + items.size() );
		}
		if (!items.isEmpty()) {
			for (MstExtClr item : items) {
				String queryString = QueryConstants.selectMstExClr.toString();
				Query query = entityManager.createQuery(queryString);
				query.setParameter(PDConstants.PRMTRT_PRD_FMLY_CD, item.getId().getProdFmyCd());
				query.setParameter(PDConstants.PRMTRT_EXT_CLR, item.getId().getExtClrCd());
				query.setParameter(PDConstants.GSIS_REG_GRND, item.getId().getGsisRegionGrnd());
				query.setParameter(PDConstants.PROD_STAG_CD, item.getId().getProdStageCd());
				List<Object> resultList = query.getResultList();
				if (!resultList.isEmpty()) {
					updateExtClr(item);
				} else {
					insertExtClr(item);
				}
			}
		}
		LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);
	}

	/**
	 * This method used update data if key combination already available in the Master table.
	 *
	 * @param item the item
	 */
	private void updateExtClr(MstExtClr item) {
		LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR);
		
		String queryString = QueryConstants.updateExtClr.toString();
		Query query = entityManager.createQuery(queryString);
		query.setParameter(PDConstants.PRMTRT_PRD_FMLY_CD, item.getId().getProdFmyCd());
		query.setParameter(PDConstants.PRMTRT_EXT_CLR, item.getId().getExtClrCd());
		query.setParameter(PDConstants.GSIS_REG_GRND, item.getId().getGsisRegionGrnd());
		query.setParameter(PDConstants.PROD_STAG_CD, item.getId().getProdStageCd());
		query.setParameter(PDConstants.PRMTRT_EXT_CLR_DESC, item.getExtClrDesc());
		query.setParameter(PDConstants.PRMTRT_INTERFACE_FILE_ID, PDConstants.INTERFACE_5_ID);
		
		query.executeUpdate();
		LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);
	}

	/**
	 * This method used insert data if key combination not available in the Master table.
	 *
	 * @param item the item
	 */
	public void insertExtClr(MstExtClr item) {
		LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR);
		String queryString = QueryConstants.insertExtClr.toString();
		Query query = entityManager.createNativeQuery(queryString);
		query.setParameter(PDConstants.PRMTRT_PRD_FMLY_CD, item.getId().getProdFmyCd());
		query.setParameter(PDConstants.PRMTRT_EXT_CLR, item.getId().getExtClrCd());
		query.setParameter(PDConstants.GSIS_REG_GRND, item.getId().getGsisRegionGrnd());
		query.setParameter(PDConstants.PROD_STAG_CD, item.getId().getProdStageCd());
		query.setParameter(PDConstants.PRMTRT_EXT_CLR_DESC, item.getExtClrDesc());
		query.setParameter(PDConstants.PRMTRT_INTERFACE_FILE_ID, PDConstants.INTERFACE_5_ID);
		query.executeUpdate();
		LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);
	}

}
