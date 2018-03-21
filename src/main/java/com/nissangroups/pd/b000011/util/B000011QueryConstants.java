/*
 * System Name     :Post Dragon 
 * Sub system Name :Batch
 * Function ID     :PST-DRG-B000011
 * Module          : Ordering					
 * Process Outline : RHQ/NSC-wise Volume/OCF Allocation
 *
 * <Revision History>
 * Date       Name(Company Name)             Description 
 * ---------- ------------------------------ ---------------------
 * 04-11-2015  	  z015060(RNTBCI)               New Creation
 *
 */
package com.nissangroups.pd.b000011.util;

/**
 * Constant file to keep the queries related to the batch B000011
 * 
 * @author z015060
 *
 */
public class B000011QueryConstants {

	/** query Constant getOCFTrnQry . */
	public static final StringBuilder getOCFTrnQry = new StringBuilder()
			.append(" select POR_CD,ORDRTK_BASE_MNTH,PROD_MNTH,CAR_SRS,OCF_BUYER_GRP_CD,FEAT_CD, OCF_REGION_CD,SUM(OCF_MAX_QTY) as OCF_MAX_QTY,OCF_FRME_CD  ")
			.append(" from TRN_MNTHLY_OCF  where ORDRTK_BASE_MNTH >= :ORDER_TAKE_BASE_MONTH and por_cd= :porCd");

	public static final StringBuilder getOCFTrnQryp2 = new StringBuilder()
			.append(" group by POR_CD,ORDRTK_BASE_MNTH,PROD_MNTH,CAR_SRS,OCF_BUYER_GRP_CD,FEAT_CD,OCF_REGION_CD,OCF_FRME_CD");

	public static final StringBuilder updateLmtMnthRegQry = new StringBuilder()
			.append("update TRN_REGIONAL_MNTHLY_OCF_LMT set REGIONAL_OCF_LMT_QTY='0'  ")
			.append(" where por_cd= :porCd and Ordr_take_base_mnth ");

	public static final StringBuilder getBuyerGrpLvlOCFQry = new StringBuilder()
			.append(" select tr.Ordr_take_base_mnth,tr.PROD_MNTH,tr.CAR_SRS,tr.FEAT_CD,tr.OCF_FRME_CD,tr.REGIONAL_OCF_LMT_QTY,mb.BUYER_GRP_CD,tr.OCF_REGION_CD,tr.OCF_BUYER_GRP_CD ")
			.append(" from TRN_REGIONAL_MNTHLY_OCF_LMT tr  INNER JOIN MST_BUYER mb on (mb.OCF_BUYER_GRP_CD=tr.OCF_BUYER_GRP_CD)")
			.append(" INNER JOIN MST_POR mp on (mp.POR_CD=TR.POR_CD) where  tr.POR_CD= :porCd and")
			.append(" mb.PROD_REGION_CD=mp.PROD_REGION_CD   and ")
			.append(" Ordr_take_base_mnth ");

	public static final StringBuilder getBuyerGrpLvlOCFQryp2 = new StringBuilder()
			.append(" group by  tr.Ordr_take_base_mnth,tr.PROD_MNTH,tr.CAR_SRS,tr.FEAT_CD,tr.OCF_FRME_CD,tr.REGIONAL_OCF_LMT_QTY,mb.BUYER_GRP_CD,tr.OCF_REGION_CD,tr.OCF_BUYER_GRP_CD ");

	public static final StringBuilder getByrGrpCdAutoAlloctnQry = new StringBuilder()
			.append("select mb.BUYER_GRP_CD,mo.OCF_REGION_CD,mo.OCF_BUYER_GRP_CD from MST_BUYER mb  ")
			.append(" inner join MST_POR mp on (mp.PROD_REGION_CD=mb.PROD_REGION_CD) ")
			.append(" inner join MST_OCF_REGION mo on(mo.OCF_REGION_CD=mb.OCF_REGION_CD and mo.OCF_BUYER_GRP_CD=mb.OCF_BUYER_GRP_CD)")
			.append(" where mp.PROD_REGION_CD=mo.PROD_REGION_CD and mp.POR_CD= :porCd and mo.OCF_AUTO_ALLCTN_FLAG='1'")
			.append(" group by mb.BUYER_GRP_CD,mo.OCF_REGION_CD,mo.OCF_BUYER_GRP_CD");

	public static final StringBuilder getRgnlOcfLmtQuery = new StringBuilder()
			.append(" select REGIONAL_OCF_LMT_QTY,PROD_MNTH,CAR_SRS,FEAT_CD,OCF_FRME_CD,FEAT_TYPE_CD,OCF_BUYER_GRP_CD,ORDR_TAKE_BASE_MNTH from TRN_REGIONAL_MNTHLY_OCF_LMT ")
			.append(" where POR_CD= :porCd and OCF_REGION_CD= :OcfRgn and OCF_BUYER_GRP_CD= :ocfByrGrpCd and ORDR_TAKE_BASE_MNTH");

	public static final StringBuilder intialiseMnthlyBuyerOcfQuery = new StringBuilder()
			.append(" update TRN_BUYER_GRP_MNTHLY_OCF_LMT set BUYER_GRP_OCF_LMT_QTY='0' where POR_CD= :porCd ")
			.append(" and BUYER_GRP_CD= :byrGrpCd and ORDR_TAKE_BASE_MNTH ");

	public static final StringBuilder updatelmtBuyrGrpQuery = new StringBuilder()
			.append(" update TRN_BUYER_GRP_MNTHLY_OCF_LMT  set BUYER_GRP_OCF_LMT_QTY= :byrGrpLmtQty where POR_CD= :porCd ")
			.append(" and BUYER_GRP_CD= :byrGrpCd and PROD_MNTH= :ProdMonth and OCF_FRME_CD = :ocfFrameCD and FEAT_CD = :featCd and ")
			.append(" CAR_SRS= :carSrs and ORDR_TAKE_BASE_MNTH = :ORDER_TAKE_BASE_MONTH ");

	public static final StringBuilder updateProcessRecQuery = new StringBuilder()
			.append("update TRN_MNTHLY_OCF set PROCESS_STTS_CD='1' where POR_CD= :porCd ");

	/**
	 * Instantiates a new B000011 query constants.
	 */
	private B000011QueryConstants() {

	}

}
