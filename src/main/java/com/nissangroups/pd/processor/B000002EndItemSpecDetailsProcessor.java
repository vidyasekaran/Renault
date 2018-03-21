/*
 * System Name     :Post Dragon 
 * Sub system Name :Batch / Interface
 * Function ID     :PST-DRG-B000002EndItemSpecDetailsProcessor
 * Module          :@Create Spec Masters
 * Process Outline :@Adding OEI_SPEC_ID details for each End Item
 *
 * <Revision History>
 * Date       		  Name(RNTBCI)             		Description 
 * ---------- ------------------------------ ---------------------
 * @08 July 2015  	  @author(z013576)              New Creation
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

import com.nissangroups.pd.util.B000002QueryConstants;

import static com.nissangroups.pd.util.CommonUtil.appendvalue;
import static com.nissangroups.pd.util.CommonUtil.zeroPadding;
import static com.nissangroups.pd.util.CommonUtil.bigDecimaltoInt;
import static com.nissangroups.pd.util.PDConstants.DOLLAR;
import static com.nissangroups.pd.util.PDConstants.INSIDE_METHOD;
import static com.nissangroups.pd.util.PDConstants.OUTSIDE_METHOD;
import static com.nissangroups.pd.util.PDConstants.PERSISTENCE_NAME;
import static com.nissangroups.pd.util.PDConstants.PRMTR_PORCD;
import static com.nissangroups.pd.util.PDConstants.PRMTR_PROD_FMLY_CD;
import static com.nissangroups.pd.util.PDConstants.PRMTR_PRODUCTION_STAGE_CODE;
import static com.nissangroups.pd.util.PDConstants.PRMTR_APPLDMDLCD;
import static com.nissangroups.pd.util.PDConstants.PRMTR_PACKCD;
import static com.nissangroups.pd.util.PDConstants.PRMTR_SPECDSTNCD;
import static com.nissangroups.pd.util.PDConstants.PRMTR_ADDITIONALSPECCD;
import static com.nissangroups.pd.util.PDConstants.OEISPECID_PREFIX;
import static com.nissangroups.pd.util.PDConstants.INT_ONE;
import static com.nissangroups.pd.util.PDConstants.INT_SEVENTEEN;

/**
 * Processor class to process the Reader data
 * Process Id - P0002.
 * @version V1.0
 */
public class B000002EndItemSpecDetailsProcessor implements
		ItemProcessor<List<Object[]>, List<Object>> {
			
	
	/** Constant LOG. */
	private static final Log LOG = LogFactory.getLog(B000002EndItemSpecDetailsProcessor.class);
			
	/** Object entitymgr. */
	@PersistenceContext(name = PERSISTENCE_NAME)
	private EntityManager entityMgr;
	
	/** Variable eispecprocessedlist. */
	/* Declaring Variables */
	private List<Object> eiSpecProcessedList = new ArrayList<Object>();
	
	/** Variable incrementaloeispecid. */
	private int incrementalOeiSpecId;
	
	/**
	 * Method process the Reader List Data & returns the Orderable End Item Spec Mst Object.
	 *
	 * @param eiSpecDtlsList the ei spec dtls list
	 * @return ei_Spec_Processed_List
	 * @throws Exception the exception
	 */
	@Override
	public List<Object> process(List<Object[]> eiSpecDtlsList)
			throws Exception {
		LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR);
		String oseiId = OEISPECID_PREFIX;
		String maxUkOeiSpecId;
		List<Object[]> toRemove = new ArrayList<Object[]>();
		String fetchMaxUKOseiId = B000002QueryConstants.Query_05_FetchMaxUkOeiSpecId.toString();
		
			/* Iterating the fetched data list */
			for (Object[] fetchedData : eiSpecDtlsList) {
				if (getRecordCount(fetchedData) == INT_ONE) {
					toRemove.add(fetchedData);
				}
			}
			
			eiSpecDtlsList.removeAll(toRemove);
			/* fetching Max UK OSEI_ID */
			maxUkOeiSpecId = (String) entityMgr.createNativeQuery(fetchMaxUKOseiId).getSingleResult();
			
			if (maxUkOeiSpecId == null) {
				incrementalOeiSpecId = INT_ONE;
			} else {
				String oeiIidSubstr = maxUkOeiSpecId.substring(4, 20);
				incrementalOeiSpecId = Integer.parseInt(oeiIidSubstr);
				incrementalOeiSpecId++;
			}
			
			/* iterating the fetched list to add oeiSpecId */
			for (Object[] val : eiSpecDtlsList) {
				String incOeiSpecId = zeroPadding(incrementalOeiSpecId, INT_SEVENTEEN);
				String ukOseiId = oseiId + incOeiSpecId;
				Object oseid = ukOseiId;
				/* calling append value method to add the created osei id */
				Object[] crtdOseiId = appendvalue(val, oseid);
				eiSpecProcessedList.add(crtdOseiId);
				incrementalOeiSpecId++;
			}
			LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);
			/* returning the processed list */
			return eiSpecProcessedList;

		
	}

	/**
	 * Method to fetch the already present record Count from the table.
	 *
	 * @param fetchedData the fetched data
	 * @return recordCount
	 */
	private int getRecordCount(Object[] fetchedData) {
		LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR);
		String porCd 		= (String) fetchedData[1];
		String prodFmlyCd 	= (String) fetchedData[2];
		String prodStgCd	= (String) fetchedData[3];
		String appldMdl 	= (String) fetchedData[0];
		String pckCd 		= (String) fetchedData[5];
		String specDestCd 	= (String) fetchedData[6];
		String addtnlSpecCd = (String) fetchedData[7];
		/* Query to fetch the record count */
		String fetchCount 	= B000002QueryConstants.Query_14_getEiSpecRecordCount.toString();
		Object recordCount 	= entityMgr.createNativeQuery(fetchCount)
							.setParameter(PRMTR_PORCD, porCd)
							.setParameter(PRMTR_PROD_FMLY_CD, prodFmlyCd)
							.setParameter(PRMTR_PRODUCTION_STAGE_CODE, prodStgCd)
							.setParameter(PRMTR_APPLDMDLCD, appldMdl)
							.setParameter(PRMTR_PACKCD, pckCd)
							.setParameter(PRMTR_SPECDSTNCD, specDestCd)
							.setParameter(PRMTR_ADDITIONALSPECCD, addtnlSpecCd)
							.getSingleResult();
		LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);
		/* returning int count */
		return bigDecimaltoInt(recordCount);
		
		}
	
	

}
