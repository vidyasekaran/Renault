/*
 * System Name     :Post Dragon 
 * Sub system Name :Batch 
 * Function ID     :PST-DRG-I000020
 * Module          :CM Common
 * Process Outline :Send OSEI Production Type Master Interface to NSC(Standard)
 *
 * <Revision History>
 * Date       Name(Company Name)             Description 
 * ---------- ------------------------------ ---------------------
 * 28-01-2016  	  z015895(RNTBCI)               Initial Version
 *
 */
package com.nissangroups.pd.i000020.util;

import com.nissangroups.pd.util.IFConstants;

/**
 * Constant file to keep the queries related to the Interface I000020
 * 
 * @author z015895
 *
 */
public class I000020QueryConstants {

	/**
	 * Instantiates a new I000020 query constants.
	 */
	private I000020QueryConstants() {
		super();
	}

	/** I000020 Constant Extract condition */
	public static final StringBuilder baseQueryCondition = new StringBuilder()
			.append(IFConstants.param_buyer_buyerGrpCD)
			.append(IFConstants.param_ocfRegionCd)
			.append(IFConstants.param_ocfBuyerGrpCd)
			.append(IFConstants.param_rhqCd).append(IFConstants.param_porCd);

	/** I000020 Constant Extract condition query String */
	public static final StringBuilder baseQueryCondition1 = new StringBuilder()
			.append("AND MST_BUYER.BUYER_GRP_CD = MST_BUYER_GRP.BUYER_GRP_CD AND ")
			.append("MST_BUYER.BUYER_CD = MST_OEI_BUYER.BUYER_CD AND ")
			.append("MST_OEI_BUYER.OEI_SPEC_ID = MST_OEI_SPEC.OEI_SPEC_ID AND  ")
			.append("MST_OSEI.OEI_BUYER_ID = MST_OEI_BUYER.OEI_BUYER_ID AND ")
			.append("MST_OSEI_PROD_TYPE.POR_CD = MST_OSEI.POR_CD AND ")
			.append("MST_OSEI_PROD_TYPE.OSEI_ID = MST_OSEI.OSEI_ID  AND ")
			.append("MST_BUYER.PROD_REGION_CD = MST_POR.PROD_REGION_CD AND ")
			.append("MST_POR.POR_CD = MST_OSEI_PROD_TYPE.POR_CD AND ")
			.append("MST_OEI_BUYER.POR_CD = MST_OEI_SPEC.POR_CD AND ")
			.append("MST_OEI_SPEC.POR_CD = MST_OSEI_PROD_TYPE.POR_CD");

	/** I000020 Constant Extract Base Query String */
	public static final StringBuilder baseQuery = new StringBuilder()
			.append("SELECT MST_OSEI_PROD_TYPE.POR_CD,MST_OEI_SPEC.CAR_SRS,MST_OEI_SPEC.APPLD_MDL_CD,MST_OEI_SPEC.PCK_CD,MST_OEI_BUYER.BUYER_CD,")
			.append("MST_OEI_SPEC.SPEC_DESTN_CD,MST_OEI_SPEC.ADTNL_SPEC_CD,MST_OSEI.EXT_CLR_CD,MST_OSEI.INT_CLR_CD,MST_OSEI_PROD_TYPE.PROD_PLNT_CD,")
			.append("MST_OSEI_PROD_TYPE.ORDR_TAKE_BASE_MNTH,MST_OSEI_PROD_TYPE.PROD_MNTH,MST_OSEI_PROD_TYPE.PROD_WK_NO,MST_OSEI_PROD_TYPE.PROD_MTHD_CD,")
			.append("MST_OSEI_PROD_TYPE.CRTD_BY,MST_OSEI_PROD_TYPE.CRTD_DT,MST_OSEI_PROD_TYPE.UPDTD_BY,MST_OSEI_PROD_TYPE.UPDTD_DT ")
			.append("FROM MST_OSEI_PROD_TYPE,MST_OEI_SPEC,MST_OEI_BUYER,MST_OSEI,MST_BUYER,MST_POR,MST_BUYER_GRP WHERE");
}
