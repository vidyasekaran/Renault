/*
 * System Name     :Post Dragon 
 * Sub system Name :Batch / Interface
 * Function ID     :PST-DRG-B000002EndItemBuyerCodeDetailsProcessor
 * Module          :@Create Spec Masters
 * Process Outline :@Adding OEI_Buyer_ID details for each End Item
 *
 * <Revision History>
 * Date       		  Name(RNTBCI)             		Description 
 * ---------- ------------------------------ ---------------------
 * @08 July 2015  	  @author(z013576)              New Creation
 *
 *
 */
package com.nissangroups.pd.processor;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.item.ItemProcessor;

import static com.nissangroups.pd.util.B000002QueryConstants.Query_06_FetchMaxUkOeiBuyerId;
import static com.nissangroups.pd.util.B000002QueryConstants.Query_15_getEiBuyerCdRecordCount;
import static com.nissangroups.pd.util.CommonUtil.appendvalue;
import static com.nissangroups.pd.util.CommonUtil.zeroPadding;
import static com.nissangroups.pd.util.CommonUtil.bigDecimaltoInt;
import static com.nissangroups.pd.util.CommonUtil.stringtoInt;
import static com.nissangroups.pd.util.PDConstants.DOLLAR;
import static com.nissangroups.pd.util.PDConstants.INSIDE_METHOD;
import static com.nissangroups.pd.util.PDConstants.OUTSIDE_METHOD;
import static com.nissangroups.pd.util.PDConstants.PRMTR_BUYERCD;
import static com.nissangroups.pd.util.PDConstants.PRMTR_OEISPECID;
import static com.nissangroups.pd.util.PDConstants.PERSISTENCE_NAME;
import static com.nissangroups.pd.util.PDConstants.OEIBUYERID_PREFIX;
import static com.nissangroups.pd.util.PDConstants.INT_ONE;
import static com.nissangroups.pd.util.PDConstants.INT_SIXTEEN;

/**
 * Processor class to process the Reader data
 * Process Id - P0003.
 * @version V1.0
 */

public class B000002EndItemBuyerCodeDetailsProcessor implements
		ItemProcessor<List<Object[]>, List<Object>> {

	/** Constant LOG. */
	private static final Log LOG = LogFactory.getLog(B000002EndItemBuyerCodeDetailsProcessor.class);
	
	/** Object entitymgr. */
	@PersistenceContext(name = PERSISTENCE_NAME)
	private EntityManager entityMgr;

	/** Variable oeibyrid. */
	private String oeiByrId = OEIBUYERID_PREFIX;
	
	/** Variable max ukoeibuyerid. */
	private String maxUkOeiBuyerId;
	
	/** Variable incrementaloeibyrid. */
	private int incrementalOeiByrId;
	
	/** Variable ei spec buyercdprocessedlist. */
	private List<Object> eiSpecBuyerCdProcessedList = new ArrayList<Object>();
	
	/** Variable toremove. */
	private List<Object[]> toRemove = new ArrayList<Object[]>();

	/**
	 * Process method to attach the UK_OEI_BUYER_ID to the Buyer Code List.
	 *
	 * @param eiBuyerCodeDetailsList the ei buyer code details list
	 * @return List<Object>
	 * @throws Exception the exception
	 */
	@Override
	public List<Object> process(List<Object[]> eiBuyerCodeDetailsList)
			throws Exception {
		    LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR);
			/* Iterating the fetched data */
			for (Object[] fetchedData : eiBuyerCodeDetailsList) {
				/* existing count check */
				if (existingBuyerCount(fetchedData) == INT_ONE) {
					toRemove.add(fetchedData);
				}
			}
			eiBuyerCodeDetailsList.removeAll(toRemove);
			String fetchMaxUKOeiBuyerId = Query_06_FetchMaxUkOeiBuyerId
					.toString();
			maxUkOeiBuyerId = (String) entityMgr.createNativeQuery(
					fetchMaxUKOeiBuyerId).getSingleResult();
			if (maxUkOeiBuyerId == null) {
				incrementalOeiByrId = INT_ONE;
			} else {
				String oeiIdsubstr = maxUkOeiBuyerId.substring(4, 20);
				incrementalOeiByrId = stringtoInt(oeiIdsubstr);
				incrementalOeiByrId++;
			}
			/* iterating and adding OEIBuyer_Id */
			for (Object[] val : eiBuyerCodeDetailsList) {
				String incOeiBuyerId = zeroPadding(incrementalOeiByrId,
						INT_SIXTEEN);
				String ukOseiId = oeiByrId + incOeiBuyerId;
				Object oSeid = ukOseiId;
				Object[] crtdOeiBuyerId = appendvalue(val, oSeid);
				eiSpecBuyerCdProcessedList.add(crtdOeiBuyerId);
				incrementalOeiByrId++;
				
			}
			LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);
			/* returning processed List */
			return eiSpecBuyerCdProcessedList;

		}

	/**
	 * Method to fetch the already present record Count from the table.
	 *
	 * @param fetchedData the fetched data
	 * @return the int
	 */
	private int existingBuyerCount(Object[] fetchedData) {
		LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR);
		String ukOeiSpecId = (String) fetchedData[1];
		String buyerCd = (String) fetchedData[0];
		String fetchCount = Query_15_getEiBuyerCdRecordCount.toString();
		/* Query execution */
		Object recordCount = entityMgr.createNativeQuery(fetchCount)
				.setParameter(PRMTR_OEISPECID, ukOeiSpecId)
				.setParameter(PRMTR_BUYERCD, buyerCd).getSingleResult();
		LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);
		/* returning count */
		return bigDecimaltoInt(recordCount);
	}

}
