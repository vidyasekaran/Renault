/*
 * System Name     :Post Dragon 
 * Sub system Name :Batch
 * Function ID     :PST-DRG-B000017
 * Module          :O Ordering
 * Process Outline :OCF/CCF Usage Calculation for Ordered Volume
 *
 * <Revision History>
 * Date       Name(Company Name)             Description 
 * ---------- ------------------------------ ---------------------
 *12-11-2015      z015399(RNTBCI)               Initial Version
 *
 */
package com.nissangroups.pd.b000017.util;

public class Constants {
	
	public static final String SC = "SC";
	public static final String SO = "SO";
	 
	public static final String PROD_ORDER_STAGE_CD_10 = "10";
	public static final String PROD_ORDER_STAGE_CD_20 = "20";
	
	public static final String PARAM_PORCD = "porCd";
	public static final String PARAM_OTBM = "otbm";
	public static final String PARAM_PRDMNTH = "prdMnth";
	public static final String PARAM_OSEIID = "oseiID";
	public static final String PARAM_BYRID = "byrID";
	public static final String PARAM_FEATURECD = "featCd";
	public static final String PARAM_OCFFRMCD = "ocfFrmCd";
	public static final String PARAM_CARSRS = "carSrs";
	public static final String PARAM_BYRGRPCD = "byrGrpCd";
	public static final String PARAM_BYROCFUSGQTY = "byrOCFUsgQty";
	public static final String PARAM_BYRGRPOCFUSGQTY = "byrGrpOCFUsgQty";
	public static final String PARAM_REGIONCD = "ocfRegionCd";
	public static final String PARAM_TBLNAME = "tblName";
	public static final String PARAM_FEATURETYPECD = "featureTypeCd";
	public static final String PARAM_REGOCFUSGQTY = "regOCFUsgQty";
	public static final String PARAM_REF_TIME = "prsRefTime";	
	public static final String PARAM_PROCESS_EXE_TIME = "prsExeTime";
	public static final String PARAM_FEATURE_TBL ="featureTbl";
	public static final String BATCH_ID = "B000017";
	public static final String TRN_REGIONAL_MNTHLY_OCF_LMT = "TRN_REGIONAL_MNTHLY_OCF_LMT";
	public static final String TRN_BUYER_GRP_MNTHLY_OCF_LMT = "TRN_BUYER_GRP_MNTHLY_OCF_LMT";
	public static final String TRN_BUYER_MNTHLY_OCF_USG = "TRN_BUYER_MNTHLY_OCF_USG";
	public static final String MST_OEI_FEAT = "MST_OEI_FEAT";
	public static final String SPEC_REEXECUTE_STATUS = "SPEC_REEXECUTE_STATUS";
	public static final String P1 = "P1";
	public static final String BATCH_CONFIG = "B000017/B000017_Batch_Config.xml";
	public static final String BEANVAL = "b000017";
	public static final String DELETION = "Deletion";
	public static final String DELETED = "Deleted";
	
	private Constants(){
		
	}

}
