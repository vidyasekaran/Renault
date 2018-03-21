/*
 * System Name     : Post Dragon 
 * Sub system Name : Interface
 * Function ID     : PST-DRG-I000065
 * Module          : OR ORDERING					
 * Process Outline : Interface to Send Weekly Order Error Interface to NSC (Standard)
 *
 * <Revision History>
 * Date       Name(Company Name)             Description 
 * ---------- ------------------------------ ---------------------
 * 24-12-2015  	  z014676(RNTBCI)              Initial Version
 *
 */
package com.nissangroups.pd.i000065.util;

import com.nissangroups.pd.util.IFConstants;

/**
 * This class is used to construct the SQL Query for I000065
 * @author z015847
 *
 */
public class I000065QueryConstants 
{

	// Constructing the SQL condition to Extract the Error Interface data based on input
	// parameters POR CD and Buyer group cd
	public static final StringBuilder baseQueryCondition = new StringBuilder()
			.append("TRN_WKLY_ORDR_ERR_IF.POR_CD = " + IFConstants.porCd_Param
					+ " AND ").append(
					"TRN_WKLY_ORDR_ERR_IF.BUYER_GRP_CD = "
							+ IFConstants.buyer_buyerGrpCD_Param + "");
	// P002 Extract the Error Interface data from TRN_WEEKLY_ORDR_ERR_IF
	public static final StringBuilder baseQuery = new StringBuilder()
			.append("select POR_CD,ORDRTK_BASE_PRD_TYPE,ORDRTK_BASE_PRD,PROD_PRD_TYPE,PROD_PRD,CAR_SRS,BUYER_GRP_CD,")
			.append("BUYER_CD,SPEC_DESTN_CD,FEAT_SHRT_DESC,FEAT_LNG_DESC,FEAT_CD,ADOPTDATE,ADOPTPRD,ABOLISHDATE,ABOLISHPRD,ABOLISHMNTH,")
			.append("APPLD_MDL_CD,PACK_CD,EXT_CLR_CD,INT_CLR_CD,ADD_SPEC_CD,POT_CD,PROD_PLANT_CD,LINE_CLASS,LCL_PROD_ORDR_NO,")
			.append("PROD_ORDR_NO,ORDR_QTY,EXP_QTY,SIGN,VARIANCE,OCF_LMT,OCF_USG,DIFF,ERR_CD,ERR_MSG from TRN_WKLY_ORDR_ERR_IF WHERE ");

	/*
	 * Private Constructor will prevent the instantiation of this class directly
	 */
	private I000065QueryConstants() {
		super();
	}
}
