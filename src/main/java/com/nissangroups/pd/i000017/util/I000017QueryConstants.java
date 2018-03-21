/*
 * System Name     :Post Dragon 
 * Sub system Name :Batch 
 * Function ID     :PST-DRG-I000017
 * Module          :SP SPEC MASTER
 * Process Outline :Send OSEI Feature CCF Interface to NSC (Standard Layout) 
 *
 * <Revision History>
 * Date       Name(Company Name)             Description 
 * ---------- ------------------------------ ---------------------
 * 28-01-2016  	  z015895(RNTBCI)               Initial Version
 *
 */
package com.nissangroups.pd.i000017.util;

import com.nissangroups.pd.util.IFConstants;

/**
 * This class contains constant query for I000017
 * 
 * @author z015895
 *
 */
public class I000017QueryConstants {

	/**
	 * Instantiates a new I000017 query constants.
	 */
	private I000017QueryConstants() {
		super();
	}

	/** I000017 Constant Extract condition */
	public static final StringBuilder baseQueryCondition = new StringBuilder()
			.append(IFConstants.param_buyer_buyerGrpCD)
			.append(IFConstants.param_ocfRegionCd)
			.append(IFConstants.param_ocfBuyerGrpCd)
			.append(IFConstants.param_rhqCd).append(IFConstants.param_porCd);

	/** I000017 Constant Extract condition query String */
	public static final StringBuilder baseQueryCondition1 = new StringBuilder()
			.append("AND MST_BUYER.BUYER_GRP_CD =MST_BUYER_GRP.BUYER_GRP_CD AND MST_BUYER.BUYER_CD =MST_OEI_BUYER.BUYER_CD AND MST_OEI_BUYER.OEI_SPEC_ID =MST_OEI_SPEC.OEI_SPEC_ID AND ")
			.append("MST_OSEI.OEI_BUYER_ID =MST_OEI_BUYER.OEI_BUYER_ID AND MST_BUYER.PROD_REGION_CD =MST_POR.PROD_REGION_CD AND MST_OEI_BUYER.POR_CD =MST_OEI_SPEC.POR_CD AND ")
			.append("MST_OEI_SPEC.POR_CD =MST_OSEI_PROD_TYPE.POR_CD AND MST_OEI_BUYER.POR_CD =MST_POR . POR_CD AND MST_OSEI.OEI_BUYER_ID  =MST_OEI_BUYER.OEI_BUYER_ID  AND ")
			.append("MST_OSEI_FEAT.OSEI_ID  = MST_OSEI.OSEI_ID  AND MST_FEAT.POR_CD   =MST_OEI_SPEC.POR_CD AND MST_FEAT.CAR_SRS =MST_OEI_SPEC.CAR_SRS  AND ")
			.append("MST_FEAT.FEAT_CD =MST_OSEI_FEAT.FEAT_CD AND MST_OEI_SPEC.POR_CD  = MST_POR.POR_CD AND MST_POR_CAR_SRS.POR_CD  =MST_OEI_SPEC.POR_CD AND ")
			.append("MST_POR_CAR_SRS.PROD_FMY_CD =MST_OEI_SPEC.PROD_FMY_CD AND MST_POR_CAR_SRS.CAR_SRS =MST_OEI_SPEC.CAR_SRS ");

	/** I000017 Constant Extract Base Query String */
	public static final StringBuilder baseQuery = new StringBuilder()
			.append("SELECT DISTINCT MST_OEI_SPEC.POR_CD,MST_OEI_SPEC.CAR_SRS,MST_OEI_BUYER.BUYER_CD,MST_OEI_SPEC.APPLD_MDL_CD, MST_OEI_SPEC.PCK_CD,")
			.append("MST_OEI_SPEC.SPEC_DESTN_CD,MST_OEI_SPEC.ADTNL_SPEC_CD,MST_OSEI.EXT_CLR_CD,MST_OSEI.INT_CLR_CD,MST_OSEI_FEAT.OSEIF_ADPT_DATE,")
			.append("MST_OSEI_FEAT.OSEIF_ABLSH_DATE,MST_OSEI_FEAT.FEAT_TYPE_CD,MST_OSEI_FEAT.FEAT_CD,MST_OSEI_FEAT.OCF_FRME_CD,")
			.append("MST_FEAT.FEAT_SHRT_DESC,MST_FEAT.OCF_BUYER_GRP_CD,MST_POR_CAR_SRS.CAR_GRP,MST_OEI_SPEC.CRTD_BY,MST_OEI_SPEC.CRTD_DT,MST_OEI_SPEC.UPDTD_BY,MST_OEI_SPEC.UPDTD_DT ")
			.append("FROM MST_POR, MST_BUYER, MST_OSEI_FEAT, MST_FEAT, MST_OSEI,MST_OEI_SPEC, MST_OEI_BUYER, MST_POR_CAR_SRS, MST_BUYER_GRP,MST_OSEI_PROD_TYPE WHERE ");

}
