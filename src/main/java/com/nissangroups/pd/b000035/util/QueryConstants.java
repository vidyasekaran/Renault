/*
 * System Name       : Post Dragon 
 * Sub system Name : Batch
 * Function ID            : PST-DRG-B000035/B000036
 * Module                  : Ordering		
 * Process Outline     : Create Weekly order stage open	/Update Weekly order stage close														
 *
 * <Revision History>
 * Date       Name(Company Name)             Description 
 * ---------- ------------------------------ ---------------------
 * 28-12-2015  	  z014433(RNTBCI)               Initial Version
 *
 */  
package com.nissangroups.pd.b000035.util;

/**
 * Constant file to keep the queries related to the batch B000035/B000036. 
 * @author z014433
 *
 */
public class QueryConstants {

/** Constant update stage code and stage status code details in Weekly order take base period Mst table */
public static final StringBuilder updateStgStsCdWklyOrdrTkBsPrd = new StringBuilder()
       .append("update MstWklyOrdrTakeBase m set m.stageCd = :stageCd, m.stageSttsCd=:stageStatusCd, m.updtdBy=:updtBy, m.updtdDt=sysdate  where m.id.por = :porCd and m.id.ordrTakeBaseMnth=:OTBM and m.id.ordrTakeBaseWkNo=:OTWN");


/** Constant update stage status code details in Weekly order take base period Mst table */
public static final StringBuilder updateStsCdWklyOrdrTkBsPrd = new StringBuilder()
       .append("update MstWklyOrdrTakeBase m set m.stageSttsCd=:stageStatusCd, m.updtdBy=:updtBy, m.updtdDt=sysdate  where m.id.por = :porCd and m.id.ordrTakeBaseMnth=:OTBM and m.id.ordrTakeBaseWkNo=:OTWN");

/**
 * Instantiates a new b000035/36 query constants.
 */
private QueryConstants() {
	
}

}


