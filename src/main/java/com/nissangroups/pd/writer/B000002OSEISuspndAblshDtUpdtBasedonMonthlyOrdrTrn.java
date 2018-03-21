/*
 * System Name     :Post Dragon 
 * Sub system Name :Batch / Interface
 * Function ID     :PST-DRG-B000002OSEISuspndAblshDtUpdtBasedonMonthlyOrdrTrn
 * Module          :@Create Spec Masters
 * Process Outline :@Update Osei Details
 *
 * <Revision History>
 * Date       			Name(RNTBCI)                 Description 
 * ---------- ------------------------------ ---------------------
 * @10 July 2015  	  @author(z013576)               New Creation
 *
 */
package com.nissangroups.pd.writer;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.item.ItemWriter;

import static com.nissangroups.pd.util.B000002QueryConstants.Query_31_FetchOngoingOrderTakeBaseMonth;
import static com.nissangroups.pd.util.B000002QueryConstants.Query_33_UpdateOseiDetailMstBasedonMnthlyOrdrTrn;
import static com.nissangroups.pd.util.B000002QueryConstants.Query_32_FetchMonthlyOrderTrnData;
import static com.nissangroups.pd.util.CommonUtil.bigDecimaltoInt;
import static com.nissangroups.pd.util.PDConstants.DOLLAR;
import static com.nissangroups.pd.util.PDConstants.INSIDE_METHOD;
import static com.nissangroups.pd.util.PDConstants.OUTSIDE_METHOD;
import static com.nissangroups.pd.util.PDConstants.PRMTR_PORCD;
import static com.nissangroups.pd.util.PDConstants.PERSISTENCE_NAME;
import static com.nissangroups.pd.util.PDConstants.PRMTR_UK_OSEI_ID;
import static com.nissangroups.pd.util.PDConstants.PRMTRT_ORDER_TAKE_BASE_MONTH;
import static com.nissangroups.pd.util.PDConstants.PRMTR_ABLSHDATE;
import static com.nissangroups.pd.util.PDConstants.PRMTR_SUSPNDED_DT;
import static com.nissangroups.pd.util.PDConstants.PRMTR_ADPTDATE;
import static com.nissangroups.pd.util.PDConstants.NOORDRTKEBASE;
import static com.nissangroups.pd.util.PDConstants.ORDEREXISTS;;

/**
 * Writer Class to update the MST_OSEI_DTL based on Monthly Order Trn.
 * Process Id - P0010
 * @version V1.0
 */

public class B000002OSEISuspndAblshDtUpdtBasedonMonthlyOrdrTrn implements ItemWriter<List<Object[]>>{
	
	/** Constant LOG. */
	private static final Log LOG = LogFactory.getLog(B000002OSEISuspndAblshDtUpdtBasedonMonthlyOrdrTrn.class);
	
	/** Object emgr. */
	@PersistenceContext(name = PERSISTENCE_NAME)
	private EntityManager eMgr;

	/** Variable fetch ordertakebasemonth. */
	String fetchOrderTakeBasemonth = Query_31_FetchOngoingOrderTakeBaseMonth.toString();
	
	/** Variable oseidetailmstupdatequery. */
	String oSEIdetailMstUpdateQuery = Query_33_UpdateOseiDetailMstBasedonMnthlyOrdrTrn.toString();
	
	/**
	 * Method to update the MST_OSEI_DTL.
	 *
	 * @param eiUpdtdUkOseiDetailsList the ei updtd uk osei details list
	 * @throws Exception the exception
	 */
	@Override
	public void write(List<? extends List<Object[]>> eiUpdtdUkOseiDetailsList) throws Exception {
		LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR);
		/* Iterating processed List */
		for(List<Object[]> oseiDetails : eiUpdtdUkOseiDetailsList)
		{
			for(Object[] updatedUkOseiId : oseiDetails)
			{
				
				Object monthlyOrdertakeBaseMonth = eMgr.createNativeQuery(fetchOrderTakeBasemonth)
													   .setParameter(PRMTR_PORCD, (String) updatedUkOseiId[4]).getSingleResult();
				String ordrTkeBseMnth = String.valueOf(monthlyOrdertakeBaseMonth);
				if(monthlyOrdertakeBaseMonth==null)
				{
					LOG.info(NOORDRTKEBASE);
				}
				else{
				updateOseiDetails(getMonthlyorderCount(updatedUkOseiId,ordrTkeBseMnth),updatedUkOseiId);
				}
					
			}
		}
		LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);
		
	}
	
	/**
	 * Method to get MonthLy Orders Count.
	 *
	 * @param updatedUkOseiId the updated uk osei id
	 * @param ordrTkeBseMnth the ordr tke bse mnth
	 * @return the monthlyorder count
	 */

	private int getMonthlyorderCount(Object[] updatedUkOseiId, String ordrTkeBseMnth) {
		LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR);
		/* Declaring Variables */
		String porCd		 	= (String) updatedUkOseiId[4];
		String abolishDt 		= (String) updatedUkOseiId[2];
		String ukOseiId 		= (String) updatedUkOseiId[0];
		String fetchMonthlyOrderTrnData = Query_32_FetchMonthlyOrderTrnData.toString();
		Object recordCount 		= eMgr.createNativeQuery(fetchMonthlyOrderTrnData)
								.setParameter(PRMTR_PORCD, porCd)
								.setParameter(PRMTRT_ORDER_TAKE_BASE_MONTH, ordrTkeBseMnth)
								.setParameter(PRMTR_ABLSHDATE, abolishDt)
								.setParameter(PRMTR_UK_OSEI_ID, ukOseiId).getSingleResult();
		LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);
		/* returning record Count */
		return bigDecimaltoInt(recordCount);
	}
	
	/**
	 * Method to update the OSEI Details.
	 *
	 * @param rCount the r count
	 * @param updatedUkOseiId the updated uk osei id
	 */
	private void updateOseiDetails(int rCount, Object[] updatedUkOseiId) {
		LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR);
		/* Declaring Variables */
		String suspendedDate 	= (String) updatedUkOseiId[2];
		String oeiSpecId 		= (String) updatedUkOseiId[0];
		String adptDt			= (String) updatedUkOseiId[1];
		String por				= (String) updatedUkOseiId[4];
		/* checking for Count */
		if(rCount == 0)
		{
			
			eMgr.createNativeQuery(oSEIdetailMstUpdateQuery)
				.setParameter(PRMTR_SUSPNDED_DT, suspendedDate)
				.setParameter(PRMTR_UK_OSEI_ID, oeiSpecId)
				.setParameter(PRMTR_ADPTDATE, adptDt)
				.setParameter(PRMTR_PORCD, por).executeUpdate();
		}
		else
		{
			LOG.info(ORDEREXISTS);
		}
		LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);
		
	}

}
