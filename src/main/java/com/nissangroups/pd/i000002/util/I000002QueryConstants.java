/*
 * System Name     :Post Dragon 
 * Sub system Name :Interface 
 * Function ID     :PST-DRG-I000002
 * Module          :CM COMMON
 * Process Outline : 
 *
 * <Revision History>
 * Date       Name(Company Name)             Description 
 * ---------- ------------------------------ ---------------------
 * 19-11-2015  	  z015887(RNTBCI)               Initial Version
 *
 */

package com.nissangroups.pd.i000002.util;

/**
 * Constant file to keep the queries related to the Interface
 * 
 * @author z015887
 * 
 */
public class I000002QueryConstants {

	/** Add where condition to Extract the Buyer master data */
	public static final StringBuilder baseQueryCondition = new StringBuilder()

			.append("MST_BUYER.PROD_REGION_CD IN (:prodRegCd) AND ")
			.append("MST_BUYER.PROD_REGION_CD=MST_BUYER_SPEC_DESTN.PROD_REGION_CD AND ")
			.append("MST_BUYER_SPEC_DESTN.BUYER_CD = MST_BUYER.BUYER_CD");

	/** P002 Extract the Buyer master data from tables */
	public static final StringBuilder baseQuery = new StringBuilder()

			.append("SELECT ROWNUM, MST_BUYER.PROD_REGION_CD,MST_BUYER.BUYER_CD,MST_BUYER_SPEC_DESTN.SPEC_DESTN_CD,")
			.append("MST_BUYER.BUYER_DESC,MST_BUYER.OCF_REGION_CD,MST_BUYER.OCF_BUYER_GRP_CD,MST_BUYER.NSC_EIM_ODER_HRZN_FLAG,")
			.append("MST_BUYER.END_OF_PPLN_ACHV,MST_BUYER.PRFX_SHPMNT_INSPCTN,MST_BUYER.BUYER_GRP_CD,")
			.append("MST_BUYER.CRTD_BY,MST_BUYER.CRTD_DT,MST_BUYER.UPDTD_DT,MST_BUYER.UPDTD_BY ")
			.append("FROM MST_BUYER,MST_BUYER_SPEC_DESTN WHERE ");

	/**
	 * Instantiates a new I000002.
	 */
	private I000002QueryConstants() {
		super();

	}

}
