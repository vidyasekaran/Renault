/*
 * System Name     :Post Dragon 
 * Sub system Name :Interface
 * Function ID     :PST-DRG-I000026
 * Module          :Ordering
 * Process Outline :Query Constants for I000026 Interface	
 *
 * <Revision History>
 * Date       Name(Company Name)             Description 
 * ---------- ------------------------------ ---------------------
 * 29-10-2015  	  z014029(RNTBCI)               New Creation
 *
 */
package com.nissangroups.pd.i000026.util;

/**
 * Constant file to keep the queries related to the Interface I000026
 */

public class I000026QueryConstants {	
	
	/** DeleteQuery for TRN_MNTHLY_OCF_IF. */
	public static final StringBuilder DeleteQuery = new StringBuilder()
	.append("DELETE FROM TRN_MNTHLY_OCF_IF WHERE POR_CD = :porCd ");
	
	/** Query to insert data into TRN_MNTHLY_OCF_IF Table. */
	public static final StringBuilder Query_Mnthly_OCF_IF_Insrt = new StringBuilder()
			.append("INSERT into TRN_MNTHLY_OCF_IF"
					+ " ( POR_CD,PROD_PLNT_CD,ORDR_TAKE_BASE_MNTH,CAR_SERIES,OCF_FRME_CD,SHRT_DESC,OCF_BUYER_GRP_CD,CAR_GRP,OCF_MAX_QTY,OCF_STND_QTY,FRME_SRT_CD,TERMINAL_ID,MAINTAIN_DT,MAINTAIN_UPD_DT,OCF_REGION_CD,APPLICATION_USER_ID,NOTES)"
					+ " values( :POR_CODE,:prdPlntCd,:ordrTkBsMnth,:carSrs,:ocfFrameCD,:shrtDesc,:byrGrpCd,:crGrp,:maxQty,:stndQty,:frmSrtCd,:trmnlId,:maintDt,:maintUpdtdDt,:ocfRgnCd,:usrId,:notes)");
	
	/** Query to extract data from TRN_MNTHLY_OCF_IF Table. */
	public static final StringBuilder ExtractStageTable = new StringBuilder()
		.append(" SELECT ocfif.POR_CD,ocfif.PROD_PLNT_CD,ocfif.ORDR_TAKE_BASE_MNTH,ocfif.CAR_SERIES,ocfif.OCF_FRME_CD,ocfif.SHRT_DESC,ocfif.OCF_BUYER_GRP_CD,")
		.append(" ocfif.CAR_GRP,ocfif.OCF_MAX_QTY,ocfif.OCF_STND_QTY,ocfif.FRME_SRT_CD,ocfif.TERMINAL_ID,ocfif.MAINTAIN_DT,ocfif.MAINTAIN_UPD_DT,")
		.append(" ocfif.OCF_REGION_CD,ocfif.APPLICATION_USER_ID,ocfif.NOTES")
		.append(" FROM TRN_MNTHLY_OCF_IF ocfif")
		.append(" WHERE ocfif.POR_CD =:POR_CODE ")
		.append(" AND ocfif.ORDR_TAKE_BASE_MNTH =:ordrTkBsMnth");
	
	/** Query to extract data from MST_POR_CAR_SRS table. */
	public static final StringBuilder CarGroupQuery = new StringBuilder()
	.append("SELECT CAR_GRP, CAR_SRS_ADPT_DATE, CAR_SRS_ABLSH_DATE FROM MST_POR_CAR_SRS WHERE POR_CD=:POR_CODE AND CAR_SRS=:carSrs");
	

	/** Query to extract data from MST_FEAT table. */
	public static final StringBuilder FeatureDtlsQuery = new StringBuilder()
	.append("SELECT FEAT_CD, FEAT_ADPT_DATE, FEAT_ABLSH_DATE,FEAT_TYPE_CD FROM MST_FEAT WHERE POR_CD=:POR_CODE AND CAR_SRS=:carSrs AND OCF_REGION_CD =:ocfRgnCd "+
	"AND OCF_BUYER_GRP_CD=:byrGrpCd AND OCF_FRME_CD=:ocfFrameCD AND FEAT_SHRT_DESC=:shrtDesc AND FEAT_TYPE_CD in (:featTypeCd)");
	
	/** Query to Select data into MNTHLY_OCF_TRN Table */
	public static final StringBuilder Query_Mnthly_OCF_Select = new StringBuilder()
			.append("SELECT * FROM TRN_MNTHLY_OCF m WHERE " + 
					" m.POR_CD=:porCd AND m.ORDRTK_BASE_MNTH=:ordrTkBsMnth AND m.PROD_MNTH =:prdMnth AND m.CAR_SRS=:carSrs AND m.PROD_PLNT_CD =:prdPlntCd AND " + 
					" m.OCF_REGION_CD=:OcfRgn AND m.OCF_BUYER_GRP_CD =:ocfByrGrpCd AND m.FRME_SRT_CD=:frmSrtCd AND m.OCF_MDL_GRP =:crGrp AND m.OCF_IDNTCTN_CD=:idntctnCd ");


	/** Query to Update data into MNTHLY_OCF_TRN Table */
	public static final StringBuilder Query_Mnthly_OCF_Update = new StringBuilder()
			.append("UPDATE TRN_MNTHLY_OCF SET  OCF_MAX_QTY =:maxQty, OCF_STND_QTY=:stndQty, UPDTD_BY =:updtBy, UPDTD_DT = SYSDATE WHERE "	+ 
					" POR_CD=:porCd AND PROD_PLNT_CD =:prdPlntCd AND ORDRTK_BASE_MNTH=:ordrTkBsMnth AND CAR_SRS=:carSrs AND OCF_FRME_CD=:ocfFrameCD AND OCF_IDNTCTN_CD=:idntctnCd AND OCF_BUYER_GRP_CD =:ocfByrGrpCd AND OCF_MDL_GRP =:crGrp AND " +
					" FRME_SRT_CD=:frmSrtCd AND OCF_REGION_CD=:OcfRgn AND FEAT_CD =:featCd AND FEAT_TYPE_CD =:featTypeCd AND PROD_MNTH =:prdMnth AND" + 
					" PROCESS_STTS_CD =:sttsCd ");	
	
	/** Query to insert data into MNTHLY_OCF_TRN Table */
	public static final StringBuilder Query_Mnthly_OCF_Insrt = new StringBuilder()
			.append("INSERT into TRN_MNTHLY_OCF"
					+ " ( POR_CD,PROD_PLNT_CD,ORDRTK_BASE_MNTH,CAR_SRS,OCF_FRME_CD,OCF_IDNTCTN_CD,OCF_BUYER_GRP_CD,OCF_MDL_GRP,OCF_MAX_QTY,OCF_STND_QTY,FRME_SRT_CD,OCF_REGION_CD,FEAT_CD,FEAT_TYPE_CD,PROD_MNTH,CRTD_BY,UPDTD_BY,UPDTD_DT,CRTD_DT,PROCESS_STTS_CD )"
					+ " values( :porCd,:prdPlntCd,:ordrTkBsMnth,:carSrs,:ocfFrameCD,:idntctnCd,:ocfByrGrpCd,:crGrp,:maxQty,:stndQty,:frmSrtCd,:OcfRgn,:featCd,:featTypeCd,:prdMnth,:crtdBy,:updtBy,sysdate,sysdate,:sttsCd )");
}
