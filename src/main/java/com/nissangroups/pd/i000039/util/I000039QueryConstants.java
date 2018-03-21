/*
 * System Name            : Post Dragon 
 * Sub system Name 		  : Interface
 * Function ID            : PST-DRG-I000039
 * Module                 : OR Ordering		
 * Process Outline     	  : Send Monthly Production Order Interface to Plant															
 *
 * <Revision History>
 * Date       Name(Company Name)             Description 
 * ---------- ------------------------------ ---------------------
 * 05-01-2016  	  z014135(RNTBCI)               Initial Version
 *
 */
package com.nissangroups.pd.i000039.util;

/**
 * This Class is Constant file to keep the queries and Constant related to the Interface I000039
 * @author z014135
 */
public class I000039QueryConstants {
	
	/**Variable Forfalg  */
	public static final String FORFLAG = "forFlag";
	
	/** Variable Order take base month */
	public static final String ORDRTKBSMNTH = "ordrTkBsMnth";
	
	/** Variable Sending type information */
	public static final String SNDTYPINFO = "No Sending type Information";
	
	/** P0003.1 Extract the Single unit flag and Production order number flag from Parameter MST table */
	public static  final StringBuilder fetchSndgTypInfo = new StringBuilder()
	.append(" SELECT VAL1,VAL2 ")
	.append(" FROM MST_PRMTR ")
	.append(" WHERE PRMTR_CD='MNTHLY_PRD_ORDR_FLAG' AND KEY1=? ");
	
	/** P0003.2 Extract Monthly production order from Monthly Production order transaction table  */
	public static  final StringBuilder fetchMnthlyPrdOrdrPart1 = new StringBuilder()
	.append("select tmpo.POR_CD, tmpo.ORDRTK_BASE_MNTH,tmpo.POT_CD,tmpo.OSEI_ID, mos.PROD_FMY_CD,mos.CAR_SRS")
	.append(" , CONCAT(mos.APPLD_MDL_CD,mos.PCK_CD) as Model_Code,mosei.EXT_CLR_CD,mosei.INT_CLR_CD,tmpo.EX_NO,mos.SPEC_DESTN_CD,tmpo.PROD_MNTH,tmpo.OCF_REGION_CD")
	.append(" , (tmpo.TYRE_MKR_CD || tmpo.OWNR_MNL || tmpo.WRNTY_BKLT || tmpo.BDY_PRTCTN_CD || tmpo.DEALER_LST) as PROD_SALES_SPEC")
	.append(" , tmpo.DEALER_LST,tmpo.ORDR_QTY");
	
	/** P0003.2 Extract Monthly production order from Monthly Production order transaction table and adding tables joins based on Requirement  */
	public static  final StringBuilder fetchMnthlyPrdOrdrPart2 = new StringBuilder()
	.append(" , tmpo.SLS_NOTE_NO,mos.ADTNL_SPEC_CD,mob.BUYER_CD ")
	.append(" from TRN_MNTHLY_PROD_ORDR tmpo  ")
	.append(" INNER JOIN MST_OSEI mosei on mosei.POR_CD = tmpo.POR_CD and tmpo.OSEI_ID = mosei.OSEI_ID ")
	.append(" INNER JOIN MST_OEI_BUYER mob on mob.POR_CD = tmpo.POR_CD and mob.OEI_BUYER_ID = mosei.OEI_BUYER_ID ")
	.append(" INNER JOIN MST_OEI_SPEC mos on mos.POR_CD = tmpo.POR_CD and mos.OEI_SPEC_ID = mob.OEI_SPEC_ID ");
	
	/** P0003.2 Extract Monthly production order from Monthly Production order transaction table adding group by some column based on requirement  */
	public static  final StringBuilder fetchMnthlyPrdOrdrGroupBy = new StringBuilder()
	.append(" group by tmpo.POR_CD, tmpo.ORDRTK_BASE_MNTH,tmpo.POT_CD,tmpo.OSEI_ID,mos.PROD_FMY_CD,mos.CAR_SRS,mos.APPLD_MDL_CD ")
	.append(" , mos.PCK_CD,mosei.EXT_CLR_CD,mosei.INT_CLR_CD,tmpo.EX_NO,mos.SPEC_DESTN_CD ")
	.append(" , tmpo.PROD_MNTH,tmpo.OCF_REGION_CD,tmpo.ORDR_QTY ")
	.append(" , tmpo.TYRE_MKR_CD,tmpo.OWNR_MNL,tmpo.WRNTY_BKLT,tmpo.BDY_PRTCTN_CD  ")
	.append(" , tmpo.DEALER_LST ")
	.append(" , tmpo.SLS_NOTE_NO,mos.ADTNL_SPEC_CD,mob.BUYER_CD ");
	
	/** Variable WhereClause */
	public static  final StringBuilder whereClause = new StringBuilder()
	.append(" where ");
	
	/** P0003.2 Variable por cd */
	public static  final StringBuilder porVlaue = new StringBuilder()
	.append(" tmpo.POR_CD=");
	/** P0003.2 Variable Order take base month  */
	public static  final StringBuilder ordrVlaue = new StringBuilder()
	.append(" and tmpo.ORDRTK_BASE_MNTH=");
	
	/** P0003.2 Variable production order no */
	public static  final StringBuilder prodOrdrYFlag = new StringBuilder()
	.append(" , tmpo.PROD_ORDR_NO ");
	/** P0003.2 adding Aggregation function */
	public static  final StringBuilder ordrQtyNFlag = new StringBuilder()
	.append(" , SUM(tmpo.ORDR_QTY) as PROD_ORDR_NO ");
	/** Variable value Y */
	public static final String valueY = "Y";
	/** Variable value N */
	public static final String valueN = "N";
	
	
	/**
	 * Instantiates a new I000039QueryConstants query constants.
	 */
	private I000039QueryConstants(){
		
	}

}
