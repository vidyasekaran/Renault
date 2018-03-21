/*
 * System Name     :Post Dragon 
 * Sub system Name :Batch 
 * Function ID     :PST-DRG-I000110
 * Module          :CM Common
 * Process Outline :This Interface extracts Feature data from the master tables and stores it in the Common Layer Data
 *
 * <Revision History>
 * Date       Name(Company Name)             Description 
 * ---------- ------------------------------ ---------------------
 * 27-01-2016  	  z015895(RNTBCI)               Initial Version
 * 
 */
package com.nissangroups.pd.i000110.util;

import com.nissangroups.pd.util.IFConstants;

/**
 * Constant file to keep the queries related to the Interface I000110
 * 
 * @author z015895
 *
 */

public class I000110QueryConstants {

	/**
	 * Instantiates a new I000110 query constants.
	 */
	private I000110QueryConstants() {
		super();
	}

	/** I00011 Constant Extract condition */
	public static final StringBuilder baseQueryCondition = new StringBuilder()
			.append(IFConstants.param_buyer_buyerGrpCD)
			.append(" " + IFConstants.param_rhqCd)
			.append(" " + IFConstants.param_ocfBuyerGrpCd)
			.append(" " + IFConstants.param_ocfRegionCd)
			.append(" " + IFConstants.param_porCd)
			.append(" " + IFConstants.param_mstFeat_porCd)
			.append(" " + IFConstants.param_mstFeat_ocfBuyerGrpCd);

	/** I00011 Additional values for Extract condition */
	public static final StringBuilder baseQueryCondition1 = new StringBuilder()
			.append(" MST_BUYER.BUYER_GRP_CD = MST_BUYER_GRP.BUYER_GRP_CD ")
			.append("AND MST_POR.PROD_REGION_CD	= MST_BUYER.PROD_REGION_CD ")
			.append("AND MST_PRMTR.PRMTR_CD	= '" + IFConstants.nscFeatAblhLmt
					+ "' AND MST_PRMTR.KEY1 = MST_BUYER_GRP.BUYER_GRP_CD ")
			.append("AND MST_FEAT.FEAT_ABLSH_DATE < "
					+ IFConstants.ablshDate_Param
					+ " AND MST_OEI_SPEC.CAR_SRS	=MST_FEAT.CAR_SRS ");

	/** I00011 Additional values for Extract condition */
	public static final StringBuilder baseQuery = new StringBuilder()
			.append("SELECT DISTINCT MST_FEAT.POR_CD,MST_BUYER.BUYER_GRP_CD,	MST_FEAT.CAR_SRS,MST_FEAT.FEAT_CD,MST_FEAT.FEAT_TYPE_CD,")
			.append("MST_FEAT.FEAT_SHRT_DESC,MST_FEAT.FEAT_LNG_DESC,MST_FEAT.OCF_FRME_CD, MST_FEAT.OCF_REGION_CD,MST_FEAT.OCF_BUYER_GRP_CD,")
			.append("MST_FEAT.FEAT_ADPT_DATE,MST_FEAT.FEAT_ABLSH_DATE,MST_FEAT.CRTD_BY,")
			.append("MST_FEAT.CRTD_DT,	MST_FEAT.UPDTD_BY,	MST_FEAT.UPDTD_DT FROM MST_BUYER,MST_POR, MST_FEAT,MST_PRMTR,MST_BUYER_GRP,  MST_OEI_SPEC WHERE ");

}
