/*
 * System Name     :Post Dragon 
 * Sub system Name :Batch
 * Function ID     :PST-DRG-B000051
 * Module          :Weekly Ordering					
 * Process Outline :Create Weekly Order Base Data
 *
 * <Revision History>
 * Date       Name(Company Name)             Description 
 * ---------- ------------------------------ ---------------------
 * 21-12-2015  	  z015060(RNTBCI)               New Creation
 *
 */
package com.nissangroups.pd.b000051.util;

/**
 * Constant file to keep the queries related to the batch B000051.
 * 
 * @author z015060
 * 
 *
 */
public class B000051QueryConstants {

	/**
	 * Instantiates a new B000051 query constants.
	 */
	private B000051QueryConstants() {

	}

	public static final StringBuilder getWkFxCsQry = new StringBuilder()
			.append(" select VAL1,VAL2 from MST_PRMTR where PRMTR_CD= :parameterCd and KEY1= :key1 ");

	public static final StringBuilder getMstPrmtr = new StringBuilder()
			.append(" select VAL1,VAL2 from MST_PRMTR where PRMTR_CD= :parameterCd and KEY1= :key1 and KEY2= :key2 ");

	public static final StringBuilder getPrdMnthQry = new StringBuilder()
			.append(" select ORDR_TAKE_BASE_MNTH,POR_CD from MST_MNTH_ORDR_TAKE_BASE_PD where POR_CD= :porCd and ")
			.append(" ORDR_TAKE_BASE_MNTH > :ordrTkBsMnth and STAGE_CD='SC' and STAGE_STTS_CD='C' order by ORDR_TAKE_BASE_MNTH ASC ");

	public static final StringBuilder getWkNumCalenderQry = new StringBuilder()
			.append(" select PROD_WK_NO,PROD_MNTH from MST_WK_NO_CLNDR where PROD_MNTH= :ordrTkBsMnth and POR_CD= :porCd and NON_OPRTNL_FLAG='0' ");

	public static final StringBuilder getOrdrInfoMstSchdule = new StringBuilder()
			.append("select POR_CD,PROD_ORDR_NO,PROD_MNTH,PROD_WK_NO,OSEI_ID,POT_CD,FRZN_TYPE_CD,PROD_MTHD_CD,count(*) as msQty from TRN_LTST_MST_SHDL ")
			.append(" where POR_CD= :porCd and PROD_MNTH= :prdMnth and PROD_WK_NO in ( :wkNo )")
			.append(" group by POR_CD,PROD_ORDR_NO,PROD_MNTH,PROD_WK_NO,OSEI_ID,POT_CD,FRZN_TYPE_CD,PROD_MTHD_CD ");

	public static final StringBuilder updtOrdrQtyInitQry = new StringBuilder()
			.append(" update TRN_WKLY_ORDR set ORGNL_ORDR_QTY='0', ACCPTD_ORDR_QTY='0',UPDTD_BY= :updtBy , UPDTD_DT= sysdate where POR_CD= :porCd and PROD_MNTH= :prdMnth and PROD_WK_NO in ( :wkNo )");

	public static final StringBuilder getOrdrInfoWklyOrdrTrnQry = new StringBuilder()
			.append(" select mo.POR_CD,md.OSEI_ADPT_DATE,md.OSEI_ABLSH_DATE,mo.OSEI_ID,mb.BUYER_CD,mbb.BUYER_GRP_CD from MST_OSEI mo")
			.append(" inner join MST_OSEI_DTL md on (md.POR_CD=mo.POR_CD and md.OSEI_ID=mo.OSEI_ID)")
			.append(" inner join MST_OEI_BUYER mb on (mb.OEI_BUYER_ID=mo.OEI_BUYER_ID and mb.POR_CD=mo.POR_CD)")
			.append(" inner join MST_BUYER mbb on (mbb.BUYER_CD=mb.BUYER_CD)")
			.append(" inner join MST_POR mp on (mp.POR_CD=mo.POR_CD and mp.PROD_REGION_CD=mbb.PROD_REGION_CD) ")
			.append(" where mo.POR_CD= :porCd and md.OSEI_ABLSH_DATE > :wkNo and md.OSEI_ADPT_DATE <= :wkNo ");

	public static final StringBuilder getPrePrdMnthWKQry = new StringBuilder()
			.append(" SELECT Max(Concat(ORDR_TAKE_BASE_MNTH,ORDR_TAKE_BASE_WK_NO)) as prdMnth from MST_WKLY_ORDR_TAKE_BASE where por= :porCd ")
			.append(" and ((ORDR_TAKE_BASE_MNTH=:ordrTkBsMnth and ORDR_TAKE_BASE_WK_NO < :ordrTkBsWkNo ) ")
			.append(" OR (ORDR_TAKE_BASE_MNTH < :ordrTkBsMnth)) order by ORDR_TAKE_BASE_MNTH,ORDR_TAKE_BASE_WK_NO ");

	public static final StringBuilder updtOrdrAccptQty = new StringBuilder()
			.append("UPDATE TRN_WKLY_ORDR SET REQTD_ORDR_QTY=ACCPTD_ORDR_QTY, ACCPTD_ORDR_QTY= :accptOrdrqty, UPDTD_BY= :updtBy , UPDTD_DT= sysdate ")
			.append(" where POR_CD= :porCd and PROD_MNTH= :prdMnth and PROD_WK_NO= :wkNo and OSEI_ID= :oseiId and POT_CD= :potCd");

	public static final StringBuilder getPrdWkNoNotIn = new StringBuilder()
			.append(" select Concat(PROD_MNTH,PROD_WK_NO),POR_CD from TRN_WKLY_ORDR where concat(PROD_MNTH,PROD_WK_NO)  not in ( :wkNo ) and")
			.append(" POR_CD=:porCd group by PROD_MNTH,PROD_WK_NO,POR_CD ");

	public static final StringBuilder updtSusPEndedFLgZero = new StringBuilder()
			.append(" update TRN_WKLY_ORDR set SUSPENDED_ORDR_FLAG='0', UPDTD_BY= :updtBy , UPDTD_DT= sysdate where POR_CD= :porCd and PROD_MNTH= :prdMnth and PROD_WK_NO= :wkNo")
			.append(" and OSEI_ID= :oseiId and OSEI_ID =( select os.osei_ID from MST_OSEI_DTL os ")
			.append(" where os.POR_CD= :porCd and os.osei_ID= :oseiId and os.OSEI_ADPT_DATE= :oseiAdptDt and os.OSEI_ABLSH_DATE= :oseiAblshDt) ");

	public static final StringBuilder updtSusPEndedFLgOne = new StringBuilder()
			.append(" update TRN_WKLY_ORDR set SUSPENDED_ORDR_FLAG='1',UPDTD_BY= :updtBy , UPDTD_DT= sysdate  where POR_CD= :porCd and PROD_MNTH= :prdMnth and PROD_WK_NO= :wkNo")
			.append(" and ACCPTD_ORDR_QTY >'0'");

	public static final StringBuilder getOcfDetailsQry = new StringBuilder()
			.append("Select ms.CAR_SRS,mf.POR_CD,mbb.BUYER_GRP_CD,mf.FEAT_CD,mo.OSEI_ID,mf.FEAT_TYPE_CD,mf.OCF_FRME_CD ")
			.append(" from MST_OEI_FEAT mf inner join MST_OSEI mo on (mo.POR_CD=mf.POR_CD and mo.OEI_BUYER_ID=mf.OEI_BUYER_ID) ")
			.append(" inner join MST_OEI_BUYER mb on (mb.POR_CD=mo.POR_CD and mb.OEI_BUYER_ID=mo.OEI_BUYER_ID) ")
			.append(" inner join MST_OEI_SPEC ms on (ms.POR_CD=mb.POR_CD and ms.OEI_SPEC_ID=mb.OEI_SPEC_ID) inner join MST_POR mp on (mp.POR_CD=ms.POR_CD)")
			.append(" inner join MST_BUYER mbb on (mbb.BUYER_CD= mb.BUYER_CD and mbb.PROD_REGION_CD=mp.PROD_REGION_CD) ")
			.append(" where  mf.FEAT_TYPE_CD= :featTypeCd and mf.POR_CD= :porCd  and mf.OEIF_ABLSH_DATE > :wkNo and mf.OEIF_ADPT_DATE <= :wkNo ");

	public static final StringBuilder getCCFDetailsQry = new StringBuilder()
			.append(" Select ms.CAR_SRS,mf.POR_CD,mbb.BUYER_GRP_CD,mf.FEAT_CD,mo.OSEI_ID,mf.FEAT_TYPE_CD,mf.OCF_FRME_CD ")
			.append(" from MST_OSEI_FEAT mf inner join MST_OSEI mo on (mo.POR_CD=mf.POR_CD and mo.OSEI_ID=mf.OSEI_ID) ")
			.append(" inner join MST_OEI_BUYER mb on (mb.POR_CD=mo.POR_CD and mb.OEI_BUYER_ID=mo.OEI_BUYER_ID) ")
			.append(" inner join MST_OEI_SPEC ms on  (ms.POR_CD=mb.POR_CD and ms.OEI_SPEC_ID=mb.OEI_SPEC_ID) inner join MST_POR mp on (mp.POR_CD=ms.POR_CD) ")
			.append(" inner join MST_BUYER mbb on (mbb.BUYER_CD= mb.BUYER_CD and mbb.PROD_REGION_CD=mp.PROD_REGION_CD)")
			.append(" where  mf.FEAT_TYPE_CD= :featTypeCd and mf.POR_CD= :porCd and mf.OSEIF_ABLSH_DATE> :wkNo and mf.OSEIF_ADPT_DATE <= :wkNo");

	public static final StringBuilder delByrCdUsgQry = new StringBuilder()
			.append(" delete from TRN_BUYER_WKLY_OCF_USG where POR_CD= :porCd and PROD_MNTH= :prdMnth and PROD_WK_NO= :wkNo ");

	public static final StringBuilder getUsgQtyByrGrpLvlp1 = new StringBuilder()
			.append("select tl.POR_CD,tl.PROD_MNTH,tl.PROD_WK_NO,mbb.Buyer_CD,count(tl.VHCL_SEQ_ID)  as vqty,mf.FEAT_TYPE_CD,mf.FEAT_CD,mf.OCF_FRME_CD,ms.CAR_SRS,mbb.BUYER_GRP_CD ");

	public static final StringBuilder getUsgQtyByrGrpLvlp2 = new StringBuilder()
			.append(" ,tl.LINE_CLASS,tl.PROD_PLNT_CD,tl.PROD_DAY_NO ");

	public static final StringBuilder getUsgQtyByrGrpLvlp3 = new StringBuilder()
			.append(" from TRN_LGCL_PPLN tl inner join MST_OSEI mo on (mo.POR_CD=tl.POR_CD and mo.OSEI_ID=tl.OSEI_ID)")
			.append(" inner join MST_OEI_BUYER mb on (mb.POR_CD=mo.POR_CD and mb.OEI_BUYER_ID=mo.OEI_BUYER_ID)")
			.append(" inner join MST_OEI_SPEC ms on  (ms.POR_CD=mb.POR_CD and ms.OEI_SPEC_ID=mb.OEI_SPEC_ID) ")
			.append(" inner join MST_POR mp on (mp.POR_CD=ms.POR_CD) inner join MST_BUYER mbb on (mbb.BUYER_CD= mb.BUYER_CD and mbb.PROD_REGION_CD=mp.PROD_REGION_CD)")
			.append(" inner join MST_OEI_FEAT mf on (tl.POR_CD=tl.POR_CD and mf.OEI_BUYER_ID=mb.OEI_BUYER_ID)")
			.append(" inner join MST_OSEI_FEAT msf on (msf.POR_CD=tl.POR_CD and msf.OSEI_ID=mo.OSEI_ID)")
			.append(" inner join MST_OCF_REGION mr on (mr.PROD_REGION_CD=mp.PROD_REGION_CD and mr.OCF_BUYER_GRP_CD=mbb.OCF_BUYER_GRP_CD and mr.OCF_REGION_CD=mbb.OCF_REGION_CD)")
			.append(" where  tl.POR_CD= :porCd and mf.OEIF_ABLSH_DATE > :wkNo and mf.OEIF_ADPT_DATE <= :wkNo and msf.OSEIF_ABLSH_DATE > :wkNo and msf.OSEIF_ADPT_DATE <= :wkNo  ")
			.append(" and Concat(tl.PROD_MNTH,tl.PROD_WK_NO) >= :wkNo group by tl.POR_CD,tl.PROD_MNTH,tl.PROD_WK_NO,mbb.Buyer_CD,mf.FEAT_TYPE_CD")
			.append(" ,mf.FEAT_CD,mf.OCF_FRME_CD,ms.CAR_SRS,mbb.BUYER_GRP_CD ");

	public static final StringBuilder UpdtByrWklyOcfLmtQry = new StringBuilder()
			.append("update TRN_BUYER_GRP_WKLY_OCF_LMT set BUYER_GRP_OCF_USG_QTY='0',UPDTD_BY= :updtBy , UPDTD_DT= sysdate where PROD_MNTH >= :prdMnth and PROD_WK_NO >= :wkNo and POR_CD= :porCd ");

	public static final StringBuilder getUsgQtyRgnLvlp1 = new StringBuilder()
			.append("select tb.POR_CD,tb.PROD_MNTH,tb.PROD_WK_NO,tb.CAR_SRS,mbb.OCF_REGION_CD,mbb.OCF_BUYER_GRP_CD,")
			.append(" tb.FEAT_CD,tb.OCF_FRME_CD,sum(tb.BUYER_GRP_OCF_USG_QTY) as qty,tb.FEAT_TYPE_CD,sum(tb.BUYER_GRP_OCF_LMT_QTY)  as Ocfqty ");

	public static final StringBuilder getUsgQtyRgnLvlp2 = new StringBuilder()
			.append(" ,tb.PLANT_CD,tb.LINE_CLASS,tb.PROD_DAY_NO ");

	public static final StringBuilder getUsgQtyRgnLvlp3 = new StringBuilder()
			.append(" from TRN_BUYER_GRP_WKLY_OCF_LMT tb inner join MST_POR mp on (mp.POR_CD=tb.POR_CD) ")
			.append(" inner join MST_BUYER mbb on (mbb.BUYER_GRP_CD=tb.BUYER_GRP_CD and mbb.PROD_REGION_CD=mp.PROD_REGION_CD)")
			.append(" inner join MST_OCF_REGION mo on (mo.OCF_REGION_CD=mbb.OCF_REGION_CD ")
			.append(" and mo.PROD_REGION_CD=mbb.PROD_REGION_CD) where tb.POR_CD= :porCd and CONCAT(tb.PROD_MNTH,tb.PROD_WK_NO) > :wkNo")
			.append(" group by tb.POR_CD,tb.PROD_MNTH,tb.PROD_WK_NO,tb.PROD_WK_NO,tb.CAR_SRS,mbb.OCF_REGION_CD,mbb.OCF_BUYER_GRP_CD, ")
			.append(" tb.FEAT_CD,tb.OCF_FRME_CD,tb.FEAT_TYPE_CD");

	
	public static final StringBuilder UpdtRgnlWklyOcfLmtQry = new StringBuilder()
	.append("update TRN_REGIONAL_WKLY_OCF_LMT set REGIONAL_OCF_USG_QTY='0',UPDTD_BY= :updtBy , UPDTD_DT= sysdate where POR_CD= :porCd and PROD_MNTH= :prdMnth and PROD_WK_NO= :wkNo")
	.append(" and CAR_SRS= :carSrs and OCF_BUYER_GRP_CD= :ocfByrGrpCd and OCF_REGION_CD= :ocfRgnCd  and PLANT_CD= :plantCD and LINE_CLASS= :lneClass ")
	.append(" and PROD_DAY_NO= :prdDayNo  ");

	public static final StringBuilder getPorHrzn = new StringBuilder()
		.append(" select POR_HRZN,POR_CD from MST_POR where por_cd= :porCd ");

	
}
