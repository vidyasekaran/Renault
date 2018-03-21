/*
 * System Name     :Post Dragon 
 * Sub system Name :Batch / Interface
 * Function ID     :PST-DRG-B000002OSEISuspndAblshUpdtBasedonWklyOrdrTrn
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

import static com.nissangroups.pd.util.PDConstants.DOLLAR;
import static com.nissangroups.pd.util.PDConstants.INSIDE_METHOD;
import static com.nissangroups.pd.util.PDConstants.OUTSIDE_METHOD;
import static com.nissangroups.pd.util.PDConstants.PRMTR_PORCD;
import static com.nissangroups.pd.util.PDConstants.PERSISTENCE_NAME;
import static com.nissangroups.pd.util.PDConstants.EMPTY_STRING;
import static com.nissangroups.pd.util.PDConstants.PRMTR_BASE_PERIOD_WEEKLY;
import static com.nissangroups.pd.util.PDConstants.PRODUCTION_MONTH;
import static com.nissangroups.pd.util.PDConstants.PRMTR_UK_OSEI_ID;
import static com.nissangroups.pd.util.PDConstants.PRMTR_SUSPNDED_DT;
import static com.nissangroups.pd.util.PDConstants.PRMTR_ADPTDATE;
import static com.nissangroups.pd.util.PDConstants.WKLYORDEREXISTS;
import static com.nissangroups.pd.util.PDConstants.NOWKLYORDRTKEBASE;
import static com.nissangroups.pd.util.PDConstants.NOWKLYOPENORDRTKEBASE;
import static com.nissangroups.pd.util.B000002QueryConstants.Query_34_FetchOngoingOrderTakeBasePeriod;
import static com.nissangroups.pd.util.B000002QueryConstants.Query_42_FetchMaxOrderTakeBasePeriod;
import static com.nissangroups.pd.util.B000002QueryConstants.Query_33_UpdateOseiDetailMstBasedonMnthlyOrdrTrn;
import static com.nissangroups.pd.util.B000002QueryConstants.Query_35_FetchWklyOrderTrnData;
import static com.nissangroups.pd.util.CommonUtil.bigDecimaltoInt;

/**
 * Writer Class to update the OSEI Suspended Date Based on WEekly_Order_TRn.
 * Process Id - P0011
 * @version V1.0
 */

public class B000002OSEISuspndAblshUpdtBasedonWklyOrdrTrn implements ItemWriter<List<Object[]>>{
	
	/** Constant LOG. */
	private static final Log LOG = LogFactory.getLog(B000002OSEISuspndAblshUpdtBasedonWklyOrdrTrn.class);
	
	/** Object emgr. */
	@PersistenceContext(name = PERSISTENCE_NAME)
	private EntityManager eMgr;

	/** Variable fetchordertakebaseperiod. */
	String fetchOrderTakeBaseperiod = Query_34_FetchOngoingOrderTakeBasePeriod.toString();
	
	/** Variable fetchmaxordertakebaseperiod. */
	String fetchMaxOrderTakeBaseperiod = Query_42_FetchMaxOrderTakeBasePeriod.toString();
	
	/** Variable oseidetailmstupdatequery. */
	String oSEIdetailMstUpdateQuery = Query_33_UpdateOseiDetailMstBasedonMnthlyOrdrTrn.toString();
	
	
	/**
	 * Writer Method to update the OSEIDEtail MSt.
	 *
	 * @param eiUpdtdUkOseiDetailsList the ei updtd uk osei details list
	 * @throws Exception the exception
	 */
	@Override
	public void write(List<? extends List<Object[]>> eiUpdtdUkOseiDetailsList) throws Exception {
		LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR);
		String ordrTakePrd = EMPTY_STRING;
		String nullString = "null";
			
		/* Iterating the Fetched Lsit */
		for(List<Object[]> oseiDetails : eiUpdtdUkOseiDetailsList)
		{
			for(Object[] updatedUkOseiId : oseiDetails)
			{
				   Object wklyOrdertakeBaseprd = eMgr.createNativeQuery(fetchOrderTakeBaseperiod)
							.setParameter(PRMTR_PORCD, (String) updatedUkOseiId[4])
							.getSingleResult();
				   if(wklyOrdertakeBaseprd==null){
						LOG.info(NOWKLYOPENORDRTKEBASE);
						Object maxWklyOrdertakeBaseprd = eMgr.createNativeQuery(fetchMaxOrderTakeBaseperiod)
								.setParameter(PRMTR_PORCD, (String) updatedUkOseiId[4])
								.getSingleResult();
						ordrTakePrd = String.valueOf(maxWklyOrdertakeBaseprd);
					}
				   else{
					    ordrTakePrd = String.valueOf(wklyOrdertakeBaseprd);
				   }
				   
				 if(ordrTakePrd.equalsIgnoreCase(nullString)){
					 LOG.info(NOWKLYORDRTKEBASE);
				 }else{
				String ftchdOrdrTkBsePrd = ordrTakePrd.substring(0, 6);
				String updtdsuspended = (String) updatedUkOseiId[2];
				String updtdSusDate = updtdsuspended.substring(0, 6);
				if(ftchdOrdrTkBsePrd.equals(updtdSusDate))
					{
					/* Calling the Update Method */
					updateOseiDetails(getWklyOrderCount(updatedUkOseiId,ordrTakePrd,updtdSusDate),updatedUkOseiId);
									
					}}
				
				}
			}
		
		eMgr.close();
		LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);
	}
	
	/**
	 * Method to get Weekly Order Record Count.
	 *
	 * @param updatedUkOseiId the updated uk osei id
	 * @param ordrTkeBseprd the ordr tke bseprd
	 * @param updtdSusDate the updtd sus date
	 * @return the wkly order count
	 */
	
	private int getWklyOrderCount(Object[] updatedUkOseiId, String ordrTkeBseprd,String updtdSusDate) {
		LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR);
		String porCd = (String) updatedUkOseiId[4];
		String ukOseiId = (String) updatedUkOseiId[0];
		String fetchwklyOrderTrnData = Query_35_FetchWklyOrderTrnData.toString();
		/* Fetching Order Count */
		Object recordCount = eMgr.createNativeQuery(fetchwklyOrderTrnData)
				.setParameter(PRMTR_PORCD, porCd)
				.setParameter(PRMTR_BASE_PERIOD_WEEKLY, ordrTkeBseprd)
				.setParameter(PRODUCTION_MONTH, updtdSusDate)
				.setParameter(PRMTR_UK_OSEI_ID, ukOseiId).getSingleResult();
		LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);
		/* returning record Count */
		return bigDecimaltoInt(recordCount);

	}
	
	/**
	 * Method to update OSEI Details.
	 *
	 * @param rCount the r count
	 * @param updatedUkOseiId the updated uk osei id
	 */
private void updateOseiDetails(int rCount, Object[] updatedUkOseiId) {
	LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR);
	String suspendedDate = (String) updatedUkOseiId[2];
	String oeiSpecId = (String) updatedUkOseiId[0];
	String adptDt = (String) updatedUkOseiId[1];
	String por = (String) updatedUkOseiId[4];
		if(rCount == 0)
		{
			/* Updating osei details */
			eMgr.createNativeQuery(oSEIdetailMstUpdateQuery)
				.setParameter(PRMTR_SUSPNDED_DT, suspendedDate)
				.setParameter(PRMTR_UK_OSEI_ID, oeiSpecId)
				.setParameter(PRMTR_ADPTDATE, adptDt)
				.setParameter(PRMTR_PORCD, por).executeUpdate();
		}
		else
		{
			LOG.info(WKLYORDEREXISTS);
		}
		LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);
		
	}

}
