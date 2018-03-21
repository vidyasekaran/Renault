/*
 * System Name     : Post Dragon 
 * Sub system Name : Interface
 * Function ID     : PST-DRG-I000033
 * Module          : OR ORDERING					
 * Process Outline : Send Monthly Order Error Interface to NSC
 *
 * <Revision History>
 * Date       Name(Company Name)             Description 
 * ---------- ------------------------------ ---------------------
 * 24-12-2015  	  z014676(RNTBCI)              Initial Version
 *
 */
package com.nissangroups.pd.i000033.util;

import com.nissangroups.pd.util.IFConstants;
/**
 *  Constant file to keep the queries related to the Interface I000033
 * @author z014676
 *
 */
public class I000033QueryConstants {
	  
    public static final String ORDRTK_PRD_TYPE = "M";
	// Add where condition to Extract the Error Interface data  POR CD value
	public static final StringBuilder baseQueryCondition = new StringBuilder()
			.append("TRN_MNTHLY_ORDR_ERR_IF.POR	= " + IFConstants.porCd_Param);
	//P002 Extract the Error Interface data from TRN_MNTHLY_ORDR_ERR_IF and conditions 
	public static final StringBuilder baseQuery = new StringBuilder()
			.append("SELECT TRN_MNTHLY_ORDR_ERR_IF.POR,TRN_MNTHLY_ORDR_ERR_IF.ORDRTK_PRD_TYPE,TRN_MNTHLY_ORDR_ERR_IF.ORDRTK_PRD, ")
			.append("TRN_MNTHLY_ORDR_ERR_IF.PROD_PRD_TYPE,TRN_MNTHLY_ORDR_ERR_IF.PROD_PRD,TRN_MNTHLY_ORDR_ERR_IF.CAR_SRS, ")
			.append("TRN_MNTHLY_ORDR_ERR_IF.BUYER_GRP_CD,TRN_MNTHLY_ORDR_ERR_IF.BUYER_CD,TRN_MNTHLY_ORDR_ERR_IF.SPEC_DESTN_CD,")
			.append("TRN_MNTHLY_ORDR_ERR_IF.OCF_SHRT_DESC,TRN_MNTHLY_ORDR_ERR_IF.OCF_DESC,TRN_MNTHLY_ORDR_ERR_IF.OCF_FEAT_CD,")
			.append("TRN_MNTHLY_ORDR_ERR_IF.ADPT_DT,TRN_MNTHLY_ORDR_ERR_IF.ADPT_PRD,TRN_MNTHLY_ORDR_ERR_IF.ABLSH_DT, ")
			.append("TRN_MNTHLY_ORDR_ERR_IF.ABLSH_PRD,TRN_MNTHLY_ORDR_ERR_IF.ABLSH_MNTH,TRN_MNTHLY_ORDR_ERR_IF.APPLD_MDL_CD,")
			.append("TRN_MNTHLY_ORDR_ERR_IF.PACK_CD,TRN_MNTHLY_ORDR_ERR_IF.EXT_CLR_CD,TRN_MNTHLY_ORDR_ERR_IF.INT_CLR_CD, ")
			.append("TRN_MNTHLY_ORDR_ERR_IF.ADD_SPEC_CD,TRN_MNTHLY_ORDR_ERR_IF.POT_CD AS POT, TRN_MNTHLY_ORDR_ERR_IF.PROD_ORDR_NO,")
			.append("TRN_MNTHLY_ORDR_ERR_IF.QTY,TRN_MNTHLY_ORDR_ERR_IF.EXPCTD_QTY,TRN_MNTHLY_ORDR_ERR_IF.ORDR_SIGN,")
			.append("TRN_MNTHLY_ORDR_ERR_IF.VARIANCE,TRN_MNTHLY_ORDR_ERR_IF.OCF_LMT, TRN_MNTHLY_ORDR_ERR_IF.OCF_USG,")
			.append("TRN_MNTHLY_ORDR_ERR_IF.OCF_SIGN,TRN_MNTHLY_ORDR_ERR_IF.DIFF,TRN_MNTHLY_ORDR_ERR_IF.ERR_CD, ")
			.append("TRN_MNTHLY_ORDR_ERR_IF.ERR_MSG,TRN_MNTHLY_ORDR_ERR_IF.PROD_PLNT_CD,TRN_MNTHLY_ORDR_ERR_IF.LCL_PROD_ORDR_NO,TRN_MNTHLY_ORDR_ERR_IF.LINE_CLASS FROM TRN_MNTHLY_ORDR_ERR_IF WHERE ");

	/*Private Constructor will prevent 
     * the instantiation of this class directly*/
	private I000033QueryConstants() {
		super();
	}

}
