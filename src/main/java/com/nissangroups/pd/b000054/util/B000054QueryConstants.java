package com.nissangroups.pd.b000054.util;

/**
 * This is class is used to have all the Query Strings for the batch B000054.
 * 
 * @author z011479
 *
 */
public class B000054QueryConstants {

	public B000054QueryConstants() {
	}

	public static final StringBuilder extractTempWeeklyOrd = new StringBuilder()
			.append("SELECT TWOT.POR_CD,TWOT.PROD_MNTH,TWOT.PROD_WK_NO,TWOT.OSEI_ID,TWOT.POT_CD, ")
			.append("TWOT.ORGNL_ORDR_QTY,TWOT.REQTD_ORDR_QTY,TWOT.ACCPTD_ORDR_QTY,TWOT.SIMU_ORDR_QTY, ")
			.append("TWOT.FRZN_TYPE_CD,TWOT.PROD_MTHD_CD,TWOT.SUSPENDED_ORDR_FLAG, ")
			.append("MB.BUYER_GRP_CD,MOS.CAR_SRS,MOB.OEI_BUYER_ID,MOB.OEI_SPEC_ID, ")
			.append("MO.EXT_CLR_CD,MO.INT_CLR_CD ")
			.append("FROM TRN_WKLY_ORDR_TEMP TWOT ")
			.append("INNER JOIN MST_OEI_SPEC MOS ")
			.append("ON TWOT.POR_CD = MOS.POR_CD ")
			.append("INNER JOIN MST_OEI_BUYER MOB ")
			.append("ON MOB.OEI_SPEC_ID = MOS.OEI_SPEC_ID ")
			.append("AND MOB.POR_CD     = MOS.POR_CD ")
			.append("INNER JOIN MST_OSEI MO  ")
			.append("ON MO.POR_CD        = MOB.POR_CD ")
			.append("AND MO.OEI_BUYER_ID = MOB.OEI_BUYER_ID ")
			.append("AND MO.OSEI_ID      = TWOT.OSEI_ID ")
			.append("INNER JOIN MST_BUYER MB ")
			.append("ON MB.BUYER_CD = MOB.BUYER_CD ")
			.append("INNER JOIN MST_POR MP ")
			.append("ON MP.POR_CD          = TWOT.POR_CD ")
			.append("AND MP.PROD_REGION_CD = MB.PROD_REGION_CD ")
			.append("WHERE TWOT.POR_CD     = :porCd ")
			.append("AND TWOT.PROD_MNTH    = :prdMnth ")
			.append("AND TWOT.PROD_WK_NO  >= :wkNo ")
			.append("AND MOS.CAR_SRS       = :carSrs ")
			.append("AND MB.BUYER_GRP_CD   = :byrGrpCd  ");

	public static final StringBuilder andExtractMstSpecDtlsHrznQryAndCarSrs = new StringBuilder()
			.append("AND MOS.CAR_SRS = :carSrs ");

}
