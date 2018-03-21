/*
 * System Name     :Post Dragon 
 * Sub system Name :Batch / Interface
 * Function ID     :PST-DRG-B000002EndItemInteriorColorCodeDetailsProcessor
 * Module          :@Create Spec Masters
 * Process Outline :@skipping already present data
 *
 * <Revision History>
 * Date       		  Name(RNTBCI)             		Description 
 * ---------- ------------------------------ ---------------------
 * @10 July 2015  	  @author(z013576)              New Creation
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

import static com.nissangroups.pd.util.B000002QueryConstants.Query_19_getEiColorCdRecordCount;
import static com.nissangroups.pd.util.PDConstants.EXISTINGCOLORREMOVAL;
import static com.nissangroups.pd.util.CommonUtil.bigDecimaltoInt;
import static com.nissangroups.pd.util.PDConstants.DOLLAR;
import static com.nissangroups.pd.util.PDConstants.INSIDE_METHOD;
import static com.nissangroups.pd.util.PDConstants.OUTSIDE_METHOD;
import static com.nissangroups.pd.util.PDConstants.PERSISTENCE_NAME;
import static com.nissangroups.pd.util.PDConstants.INT_ONE;
import static com.nissangroups.pd.util.PDConstants.PRMTR_INTRCLRCD;

/**
 * Processor Class to skip already present data
 * Process Id : P0007.
 * @version V1.0
 */

public class B000002EndItemInteriorColorCodeDetailsProcessor implements
		ItemProcessor<List<Object[]>, List<Object[]>> {

	/** Constant LOG. */
	private static final Log LOG = LogFactory.getLog(B000002EndItemInteriorColorCodeDetailsProcessor.class);
	
	/** Object entitymgr. */
	@PersistenceContext(name = PERSISTENCE_NAME)
	private EntityManager entityMgr;

	/** Variable to remove. */
	private List<Object[]> toRemove = new ArrayList<Object[]>();
	
	/**
	 * Process method to remove the already existing data.
	 *
	 * @param eiInteriorColorCodeDetailsList the ei interior color code details list
	 * @return the list
	 * @throws Exception the exception
	 * @ param eiInteriorColorCodeDetailsList
	 */
	@Override
	public List<Object[]> process(List<Object[]> eiInteriorColorCodeDetailsList) throws Exception {
		LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR);
		/* Iterating fetched List */
		for (Object[] fetchedData : eiInteriorColorCodeDetailsList) {
			if (existingColorCount(fetchedData) == INT_ONE) {
				toRemove.add(fetchedData);
			}
		}
		/* removing already present data */
		eiInteriorColorCodeDetailsList.removeAll(toRemove);
		
		LOG.info(EXISTINGCOLORREMOVAL);
		LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);
		/* returning processed list */
		return eiInteriorColorCodeDetailsList;
		
	}

	/**
	 * Method to fetch the already present record Count from the table.
	 *
	 * @param fetchedData the fetched data
	 * @return int
	 */
	private int existingColorCount( Object[] fetchedData) {
		LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR);
		String intrColorCd = (String) fetchedData[0];
		String fetchCount = Query_19_getEiColorCdRecordCount.toString();
		/* executing query */
		Object recordCount = entityMgr.createNativeQuery(fetchCount)
				.setParameter(PRMTR_INTRCLRCD, intrColorCd)
				.getSingleResult();
		LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);
		/* returning record Count */
		return bigDecimaltoInt(recordCount);
		}
	}
	


