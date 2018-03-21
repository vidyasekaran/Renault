/*
 * System Name     :Post Dragon 
 * Sub system Name :Batch / Interface
 * Function ID     :PST-DRG-B000002EndItemColourCodeDetailsProcessor
 * Module          :@Create Spec Masters
 * Process Outline :@Adding OEI_SPEC_ID details for each End Item
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

import static com.nissangroups.pd.util.B000002QueryConstants.Query_08_FetchMaxUkOseiId;
import static com.nissangroups.pd.util.B000002QueryConstants.Query_16_getEiColorCdRecordCount;
import static com.nissangroups.pd.util.CommonUtil.appendvalue;
import static com.nissangroups.pd.util.CommonUtil.zeroPadding;
import static com.nissangroups.pd.util.CommonUtil.bigDecimaltoInt;
import static com.nissangroups.pd.util.CommonUtil.stringtoInt;
import static com.nissangroups.pd.util.CommonUtil.getSubstr;
import static com.nissangroups.pd.util.PDConstants.DOLLAR;
import static com.nissangroups.pd.util.PDConstants.INSIDE_METHOD;
import static com.nissangroups.pd.util.PDConstants.OUTSIDE_METHOD;
import static com.nissangroups.pd.util.PDConstants.PERSISTENCE_NAME;
import static com.nissangroups.pd.util.PDConstants.OSEIID_PREFIX;
import static com.nissangroups.pd.util.PDConstants.INT_ONE;
import static com.nissangroups.pd.util.PDConstants.INT_FIVE;
import static com.nissangroups.pd.util.PDConstants.INT_SIXTEEN;
import static com.nissangroups.pd.util.PDConstants.INT_TWENTY;
import static com.nissangroups.pd.util.PDConstants.PRMTR_EXTRCLRCD;
import static com.nissangroups.pd.util.PDConstants.PRMTR_INTRCLRCD;
import static com.nissangroups.pd.util.PDConstants.PRMTR_PORCD;
import static com.nissangroups.pd.util.PDConstants.PRMTR_UK_OEI_BUYEER_ID;;

/**
 * Processor class to process the Reader data
 * Process Id : P0004.
 * @version V1.0
 */
public class B000002EndItemColourCodeDetailsProcessor implements
		ItemProcessor<List<Object[]>, List<Object>> {
	
	/** Constant LOG. */
	private static final Log LOG = LogFactory.getLog(B000002EndItemColourCodeDetailsProcessor.class);
	
	/** Object entitymgr. */
	@PersistenceContext(name = PERSISTENCE_NAME)
	private EntityManager entityMgr;
	
	/** Variable oseiid. */
	private String oseiId = OSEIID_PREFIX;
	
	/** Variable maxukoseiid. */
	private String maxUkOseiId;
	
	/** Variable incrementaloseiid. */
	private int incrementalOseiId;
	
	/** Variable eicolor cd processed list. */
	private List<Object> eicolorCdProcessedList = new ArrayList<Object>();
	
	/** Variable to remove. */
	private List<Object[]> toRemove = new ArrayList<Object[]>();

	/**
	 * Method to Process the List of Fetched Colour Code details and attach
	 * ukOseiId to each Colour details.
	 *
	 * @param eiColorCodeDetailsList the ei color code details list
	 * @return ei_colorCd_Processed_List
	 * @throws Exception the exception
	 */
	@Override
	public List<Object> process(List<Object[]> eiColorCodeDetailsList)
			throws Exception {
		    LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR);
			/* Iterating fetched Data */
			for (Object[] fetchedData : eiColorCodeDetailsList) {
				if(existingEIRecordCount(fetchedData) == INT_ONE){
					toRemove.add(fetchedData);
				}
			}
			
			eiColorCodeDetailsList.removeAll(toRemove);
			String fetchMaxUKOseiId = Query_08_FetchMaxUkOseiId.toString();
			maxUkOseiId = (String) entityMgr.createNativeQuery(fetchMaxUKOseiId).getSingleResult();
			if (maxUkOseiId == null) {
				incrementalOseiId = INT_ONE;
			} else {
				String oeiIdsubstr = getSubstr(maxUkOseiId,INT_FIVE, INT_TWENTY);
				incrementalOseiId = stringtoInt(oeiIdsubstr);
				incrementalOseiId++;
			}
			/* Iterating the feteched list to add oseiid */
			for (Object[] val : eiColorCodeDetailsList) {
				String incOseiId = zeroPadding(incrementalOseiId, INT_SIXTEEN);
				String ukOseiId = oseiId + incOseiId;
				Object oSeid = ukOseiId;
				Object[] crtdOseiId = appendvalue(val, oSeid);
				eicolorCdProcessedList.add(crtdOseiId);
				incrementalOseiId++;
				
			}
			LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);
			/* returning processed data */
			return eicolorCdProcessedList;
		
	}

	/**
	 * Method to fetch the already present data in the table.
	 *
	 * @param fetchedData the fetched data
	 * @return the int
	 */
	private int existingEIRecordCount(Object[] fetchedData) {
		LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR);
		String ukOeiBuyerId 	= (String) fetchedData[0];
		String extColorCd		= (String) fetchedData[1];
		String intColorCd		= (String) fetchedData[2];
		String por				= (String) fetchedData[3];
		/* Executing Query */
		String fetchCount 		= Query_16_getEiColorCdRecordCount.toString();
		
		Object recordCount 		= entityMgr.createNativeQuery(fetchCount)
								  .setParameter(PRMTR_UK_OEI_BUYEER_ID, ukOeiBuyerId)
				                  .setParameter(PRMTR_EXTRCLRCD, extColorCd)
				                  .setParameter(PRMTR_INTRCLRCD, intColorCd)
				                  .setParameter(PRMTR_PORCD, por)
				                  .getSingleResult();
		LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);
		/* returning record count */
		return bigDecimaltoInt(recordCount);

	}

}
