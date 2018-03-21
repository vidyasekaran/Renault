/*
 * System Name     :Post Dragon 
 * Sub system Name :Batch 
 * Function ID     :PST-DRG-I000014
 * Module          :CM Common
 * Process Outline :Send OEI Feature OCF Interface to NSC (Standard) 
 *
 * <Revision History>
 * Date       Name(Company Name)             Description 
 * ---------- ------------------------------ ---------------------
 * 27-01-2016  	  z015895(RNTBCI)               Initial Version
 *
 */
package com.nissangroups.pd.i000014.util;

import com.nissangroups.pd.util.IFConstants;

/**
 * Constant file to keep the queries related to the Interface I000014
 * 
 * @author z015895
 *
 */
public class I000014QueryConstants {

	/**
	 * Instantiates a new I000014 query constants.
	 */
	private I000014QueryConstants() {
		super();
	}

	/** I000014 Constant Extract condition */
	public static final StringBuilder baseQueryCondition = new StringBuilder()
			.append("" + IFConstants.param_buyerCD + "")
			.append("" + IFConstants.param_rhqCd + "")
			.append("MST_BUYER.BUYER_GRP_CD = MST_BUYER_GRP.BUYER_GRP_CD AND ")
			.append("" + IFConstants.param_ocfRegionCd + "")
			.append("" + IFConstants.param_ocfBuyerGrpCd + "")
			.append("" + IFConstants.param_porCd + "")
			.append("MST_POR.PROD_REGION_CD = MST_BUYER.PROD_REGION_CD AND ")
			.append("MST_POR_CAR_SRS.POR_CD = MST_POR.POR_CD AND ")
			.append("MST_OEI_SPEC.POR_CD = MST_POR.POR_CD AND ")
			.append("MST_OEI_BUYER.OEI_SPEC_ID =	MST_OEI_SPEC.OEI_SPEC_ID AND ")
			.append("MST_OEI_BUYER.POR_CD =	MST_OEI_SPEC.POR_CD AND ")
			.append("MST_OEI_FEAT.OEI_BUYER_ID = MST_OEI_BUYER.OEI_BUYER_ID AND ")
			.append("MST_OEI_FEAT.POR_CD = MST_OEI_BUYER.POR_CD AND ")
			.append("MST_FEAT.POR_CD = MST_OEI_FEAT.POR_CD AND ")
			.append("MST_FEAT.OCF_FRME_CD = MST_OEI_FEAT.OCF_FRME_CD AND ")
			.append("MST_FEAT.FEAT_CD = MST_OEI_FEAT.FEAT_CD");

	/** I000014 Constant Extract Base Query String */
	public static final StringBuilder baseQuery = new StringBuilder()
			.append("SELECT DISTINCT MST_POR.POR_CD,MST_OEI_SPEC.CAR_SRS,MST_BUYER.BUYER_CD,MST_OEI_SPEC.APPLD_MDL_CD,")
			.append("MST_OEI_SPEC.PCK_CD,MST_OEI_SPEC.SPEC_DESTN_CD,MST_OEI_SPEC.ADTNL_SPEC_CD,MST_OEI_FEAT.OEIF_ADPT_DATE,")
			.append("MST_OEI_FEAT.OEIF_ABLSH_DATE,MST_OEI_FEAT.FEAT_TYPE_CD,MST_OEI_FEAT.FEAT_CD,MST_OEI_FEAT.OCF_FRME_CD,")
			.append("MST_FEAT.FEAT_SHRT_DESC,MST_FEAT.OCF_BUYER_GRP_CD,MST_POR_CAR_SRS.CAR_GRP,MST_OEI_FEAT.CRTD_BY,")
			.append("MST_OEI_FEAT.CRTD_DT,MST_OEI_FEAT.UPDTD_BY,MST_OEI_FEAT.UPDTD_DT ")
			.append("FROM MST_BUYER,MST_POR,MST_BUYER_GRP,MST_OEI_SPEC,MST_OEI_FEAT,MST_FEAT,MST_POR_CAR_SRS,MST_OEI_BUYER WHERE ");

}
