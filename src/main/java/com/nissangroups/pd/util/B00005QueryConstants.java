/*
 * System Name     :Post Dragon 
 * Sub system Name :Batch 
 * Function ID     :PST-DRG-B000005
 * Module          :Cretae Orderable Sales  Enditem Feature MST
 * Process Outline :Spec Master 
 *
 * <Revision History>
 * Date       Name(Company Name)             Description 
 * ---------- ------------------------------ ---------------------
 * @Date  	  z010343(RNTBCI)               New Creation
 *
 */
package com.nissangroups.pd.util;

import static com.nissangroups.pd.util.PDConstants.BATCH000004;
import static com.nissangroups.pd.util.PDConstants.BATCH_4_ID;
import static com.nissangroups.pd.util.PDConstants.BATCH_5_ID;
import static com.nissangroups.pd.util.PDConstants.FEATURE_FEAT_ABLSH_DATE;
import static com.nissangroups.pd.util.PDConstants.FEATURE_FEAT_ADPT_DATE;
import static com.nissangroups.pd.util.PDConstants.FEATURE_FEAT_CD;
import static com.nissangroups.pd.util.PDConstants.FEATURE_FEAT_TYPE_CD;
import static com.nissangroups.pd.util.PDConstants.FEATURE_OCF_FRME_CD;
import static com.nissangroups.pd.util.PDConstants.FEATURE_OEI_ID;
import static com.nissangroups.pd.util.PDConstants.FEATURE_POR_CD;
import static com.nissangroups.pd.util.PDConstants.PRMTR_ONE;
import static com.nissangroups.pd.util.PDConstants.PRMTR_ZERO;

import java.util.List;

/**
 * The Class B00005QueryConstants.
 */
public class B00005QueryConstants {
    
    /** Constant SELECT_QUERY. */
    public static final StringBuilder END_ITEM_SELECT        = new StringBuilder()
                                                                 .append("SELECT DISTINCT OEISM.POR_CD,OEIBM.BUYER_CD,OEISM.APPLD_MDL_CD,OEISM.PCK_CD,OEISM.ADTNL_SPEC_CD,OEISM.SPEC_DESTN_CD,OEIBM.OEI_BUYER_ID,")
                                                                 .append("(SELECT LISTAGG(OBOSCM1.OPTN_SPEC_CODE) WITHIN GROUP (ORDER BY OBOSCM1.OPTN_SPEC_CODE) AS OPTN_SPEC_CODE")
                                                                 
                                                                 .append(" FROM MST_OEI_BUYER_OPTN_SPEC_CD OBOSCM1 WHERE OBOSCM1.OEI_BUYER_ID = OBOSCM.OEI_BUYER_ID")
                                                                 .append(" GROUP BY OBOSCM.OEI_BUYER_ID) AS OPTN_SPEC_CODE,OEISM.CAR_SRS,ODM.OSEI_ID,")
                                                                 .append(" BM.OCF_BUYER_GRP_CD,BM.OCF_REGION_CD,")
                                                                 
                                                                 .append(" ODM.END_ITM_STTS_CD ,");
    
    /** Constant SELECT_QUERY_B5. */
    public static final StringBuilder END_ITEM_SELECT_B5      = new StringBuilder()
                                                                 .append("ODM.OSEI_ADPT_DATE,OSEIM.EXT_CLR_CD,OSEIM.INT_CLR_CD,LEAST((SELECT min(ODM1.OSEI_ABLSH_DATE) FROM MST_OSEI_DTL ODM1 WHERE ODM.OSEI_ADPT_DATE=ODM1.OSEI_ADPT_DATE and ODM1.OSEI_ID = ODM.OSEI_ID AND ODM1.END_ITM_STTS_CD =ODM.END_ITM_STTS_CD),(SELECT MIN(PCSM1.CAR_SRS_ABLSH_DATE) FROM MST_POR_CAR_SRS PCSM1 WHERE PCSM1.CAR_SRS = PCSM.CAR_SRS AND PCSM1.POR_CD = PCSM.POR_CD)) AS ABOLISH_DATE");
    
    /** Constant SELECT_QUERY_B4. */
    public static final StringBuilder END_ITEM_SELECT_B4      = new StringBuilder()
                                                                 .append("OBPM.EIM_MIN_ADPT_DATE,LEAST((SELECT min(OBPM1.EIM_MAX_ABLSH_DATE) FROM MST_OEI_BUYER_PRD OBPM1 WHERE OBPM.EIM_MIN_ADPT_DATE=OBPM1.EIM_MIN_ADPT_DATE and OBPM1.OEI_BUYER_ID = OBPM1.OEI_BUYER_ID),(SELECT MIN(PCSM1.CAR_SRS_ABLSH_DATE) FROM MST_POR_CAR_SRS PCSM1 WHERE PCSM1.CAR_SRS = PCSM.CAR_SRS AND PCSM1.POR_CD = PCSM.POR_CD)) AS ABOLISH_DATE");
    
    /** Constant FROM_QUERY_B5. */
    public static final StringBuilder END_ITEM_FROM_B5        = new StringBuilder()
                                                                 .append(" FROM MST_OEI_SPEC OEISM")
                                                                 .append(" INNER JOIN MST_OEI_BUYER OEIBM ON OEISM.OEI_SPEC_ID = OEIBM.OEI_SPEC_ID")
                                                                 .append(" INNER JOIN MST_POR_CAR_SRS PCSM ON OEISM.CAR_SRS = PCSM.CAR_SRS AND PCSM.POR_CD = OEISM.POR_CD AND PCSM.CAR_SRS_ABLSH_DATE > :ablshDate ")
                                                                 .append(" INNER JOIN MST_OEI_BUYER_PRD OBPM ON OEIBM.OEI_BUYER_ID = OBPM.OEI_BUYER_ID ")
                                                                 .append(" LEFT JOIN MST_OEI_BUYER_OPTN_SPEC_CD OBOSCM ON OEIBM.OEI_BUYER_ID = OBOSCM.OEI_BUYER_ID")
                                                                 .append(" INNER JOIN MST_OSEI OSEIM ON OEIBM.OEI_BUYER_ID = OSEIM.OEI_BUYER_ID")
                                                                 .append(" INNER JOIN MST_OSEI_DTL ODM ON OSEIM.OSEI_ID = ODM.OSEI_ID  ")
                                                                 .append(" INNER JOIN MST_BUYER BM ON OEIBM.BUYER_CD = BM.BUYER_CD")
                                                                 .append(" INNER JOIN MST_POR PM ON BM.PROD_REGION_CD = PM.PROD_REGION_CD AND PM.POR_CD = OEISM.POR_CD");
    
    
    
    /**
     * Where query.
     *
     * @param batchName the batch name
     * @param prdStgCd the prd stg cd
     * @return the string
     */
    public static String endItemWhereQuery(String batchName,String prdStgCd) {
        if (BATCH000004.equals(batchName)) {
           
           return " WHERE ODM.END_ITM_STTS_CD IN ('20','30') AND OEISM.POR_CD = :porCd  AND OBPM.EIM_MAX_ABLSH_DATE > :ablshDate AND oeism.PROD_STAGE_CD in ("+prdStgCd+") AND OBPM.EIM_MAX_ABLSH_DATE > :ablshDate ";

        }
        
        else
        {
        return " WHERE ODM.END_ITM_STTS_CD IN ('20','30') AND OEISM.POR_CD = :porCd  AND ODM.OSEI_ABLSH_DATE > :ablshDate AND oeism.PROD_STAGE_CD in ("+prdStgCd+") AND ODM.OSEI_ABLSH_DATE > :ablshDate "; 
        }
        
        
    }
    
    /** Constant FROM_QUERY_UPDATE_FLG. */
    public static final StringBuilder FROM_QUERY_UPDATE_FLG = new StringBuilder(
                                                                 " INNER JOIN SPEC_REEXECUTE_STATUS SRS ON ODM.UPDTD_DT > SRS.REFERENCE_TIME and SRS.POR = :porCd and SRS.TABLE_NAME = 'MST_OSEI_DTL'  and TRIM(SRS.BATCH_ID) = :BATCH_ID");
    
    /**
     * Ocf extraction.
     *
     * @param basePeriod the base period
     * @param batchName the batch name
     * @return the string
     */
    public String ocfExtraction(String basePeriod, String batchName) {
        String queryString = "SELECT  distinct POR_CD,TRIM(CAR_SRS),SHRT_DESC,LNG_DESC,OCF_FRME_CD,OCF_REGION_CD,OCF_BUYER_GRP_CD,OCF_ADPT_DATE,OCF_ABLSH_DATE,TRIM(OPTN_SPEC_CD_CNDTN),FEAT_TYPE_CD,OCF_PRITY_CD from MST_OCF_CLASSFTN  ocfClassMst WHERE (SUBSTR( :appldMdlCd, 0, 7) not like NVL2(REPLACE( ocfClassMst.PRFX_NO, ' ', ''), REPLACE( ocfClassMst.PRFX_NO, ' ', '_'), ' ')"
                + " AND ((SUBSTR( :appldMdlCd, 0, 7) like REPLACE( ocfClassMst.PRFX_YES, ' ', '_') OR (ocfClassMst.PRFX_YES IS NULL) ))"
                + " AND (SUBSTR( :appldMdlCd || :packCd , 11, 8) not like NVL2(REPLACE( ocfClassMst.SFFX_NO, ' ', ''), REPLACE( ocfClassMst.SFFX_NO, ' ', '_'), ' ')"
                + " AND ((SUBSTR( :appldMdlCd || :packCd , 11, 8) like REPLACE( ocfClassMst.SFFX_YES, ' ', '_'))  OR (ocfClassMst.SFFX_YES IS NULL))))"
                + " AND (((CASE NVL2( ocfClassMst.OCF_ADPT_DATE|| ocfClassMst.OCF_ABLSH_DATE,nvl2( ocfClassMst.OCF_ADPT_DATE,NVL2( ocfClassMst.OCF_ABLSH_DATE,'Both_were_not_null','Abolish_date_is_null'),'Adopt_date_is_null'),'Both_were_Null')"
                + " WHEN 'Both_were_Null' THEN NULL"
                + " WHEN 'Adopt_date_is_null' THEN (case when  ocfClassMst.OCF_ABLSH_DATE> :adptDate and  ocfClassMst.OCF_ABLSH_DATE> :basePeriod then 'OCF_ADPT_DATE_IS_NULL' end)"
                + " when 'Both_were_not_null' then (case when  ocfClassMst.OCF_ADPT_DATE< :ablshDate and  ocfClassMst.OCF_ABLSH_DATE> :adptDate and  ocfClassMst.OCF_ABLSH_DATE> :basePeriod then 'OCF_ADPT_DATE and OCF_ABLSH_DATE were not NULL' end)"
                + " when 'Abolish_date_is_null' then (case when  ocfClassMst.OCF_ADPT_DATE< :ablshDate then 'OCF_ABLSH_DATE is NULL'  end)"
                + " END)is not null))"
                + " AND (((trim( ocfClassMst.SPEC_DESTN_CD_CNDTN) is null) OR"
                + " (SUBSTR( trim(ocfClassMst.SPEC_DESTN_CD_CNDTN),1,4)= :specDstnCd) OR"
                + " (SUBSTR(trim( ocfClassMst.SPEC_DESTN_CD_CNDTN),5,4)= :specDstnCd ) OR"
                + " (SUBSTR( trim(ocfClassMst.SPEC_DESTN_CD_CNDTN),9,4)= :specDstnCd) OR"
                + " (SUBSTR( trim(ocfClassMst.SPEC_DESTN_CD_CNDTN),13,4)= :specDstnCd) OR"
                + " (SUBSTR( trim(ocfClassMst.SPEC_DESTN_CD_CNDTN),17,4)= :specDstnCd)"
                + " ))"
                + " AND  (ocfClassMst.POR_CD= :porCd)"
                + " AND  (TRIM(ocfClassMst.CAR_SRS)= :carSrs)"
                + " AND  ((TRIM(ocfClassMst.ORDR_TAKE_BASE_MNTH)) IS NULL OR ocfClassMst.ORDR_TAKE_BASE_MNTH = '"
                + basePeriod.substring(0, 6)
                + "') "
                + " AND  (ocfClassMst.OCF_BUYER_GRP_CD= :ocfByrGrpCd)"
                + " AND  (ocfClassMst.OCF_REGION_CD= :ocfRgnCd)"
                + " AND  ocfClassMst.OCF_DEL_FLAG='N'";
        if (BATCH000004.equals(batchName)) {
            queryString += " AND ((TRIM( ocfClassMst.CLR_CD_CNDTN) is null ))";
        } else {
            queryString += " AND ((TRIM( ocfClassMst.CLR_CD_CNDTN) is not null"
                    + " OR (SUBSTR(  trim(ocfClassMst.CLR_CD_CNDTN),1,3)= :extrClrCd  "
                    + " AND TRIM(SUBSTR( trim(ocfClassMst.CLR_CD_CNDTN),4,2))= :intrClrCd) OR (SUBSTR( trim(ocfClassMst.CLR_CD_CNDTN),6,3)= :extrClrCd "
                    + " AND TRIM(SUBSTR( trim(ocfClassMst.CLR_CD_CNDTN),9,2))= :intrClrCd) OR (SUBSTR( trim(ocfClassMst.CLR_CD_CNDTN),11,3)= :extrClrCd "
                    + " AND TRIM(SUBSTR( trim(ocfClassMst.CLR_CD_CNDTN),14,2))= :intrClrCd) OR (SUBSTR( trim(ocfClassMst.CLR_CD_CNDTN),16,3)= :extrClrCd "
                    + " AND TRIM(SUBSTR( trim(ocfClassMst.CLR_CD_CNDTN),19,2))= :intrClrCd) OR (SUBSTR( trim(ocfClassMst.CLR_CD_CNDTN),21,3)= :extrClrCd "
                    + " AND TRIM(SUBSTR( trim(ocfClassMst.CLR_CD_CNDTN),24,2))= :intrClrCd))) and ocfClassMst.OCF_FRME_CD != '00' ";
        }
        
        return queryString;
    }
    
    
    /**
     * Gets the feature lst.
     *
     * @return the feature lst
     */
    public String getFeatureLst() {
        
        return " SELECT distinct FTRE.* FROM MST_FEAT FTRE  WHERE "
                + "FTRE.POR_CD= :porCd AND "
                + "(TRIM(FTRE.CAR_SRS)= :carSrs) AND "
                + "FTRE.FEAT_TYPE_CD= :featTypeCd AND "
                + "FTRE.OCF_REGION_CD= :ocfRgnCd AND "
                + "TRIM(FTRE.FEAT_SHRT_DESC) IN(:shortDsc , :carSrs)  AND "
                + "FTRE.OCF_BUYER_GRP_CD= :ocfByrGrpCd AND "
                + "FTRE.OCF_FRME_CD= :ocfFrameCD ";
        
    }
    
    /**
     * Gets the other region feature lst.
     *
     * @return the other region feature lst
     */
    public String getOtherRegionFeatureLst() {
        
        return " SELECT distinct FTRE.* FROM MST_FEAT FTRE  WHERE "
                + "FTRE.POR_CD= :porCd AND "
                + "(TRIM(FTRE.CAR_SRS)= :carSrs) AND "
                + "FTRE.FEAT_TYPE_CD IN ('10','20','30') AND "
                + "FTRE.OCF_FRME_CD = '00' order by FTRE.FEAT_TYPE_CD asc";
    }
    
    /**
     * Gets the no ocf feature lst.
     *
     * @return the no ocf feature lst
     */
    public String getNoOcfFeatureLst() {
        
        return " SELECT distinct FTRE.* FROM MST_FEAT FTRE WHERE "
                + "FTRE.POR_CD= :porCd AND " + "TRIM(FTRE.CAR_SRS)= :carSrs AND "
                + "FTRE.OCF_REGION_CD= :ocfRgnCd AND "
                + "FTRE.OCF_BUYER_GRP_CD= :ocfByrGrpCd AND "
                + "FTRE.FEAT_TYPE_CD IN ('20','30') AND "
                + "FTRE.OCF_FRME_CD = '00'";
        
    }
    
    /**
     * Gets the feature without car series.
     *
     * @return the feature without car series
     */
    public String getFeatureWithoutCarSeries() {
        
        return " SELECT distinct FTRE.* FROM MST_FEAT FTRE  WHERE "
        // + "FTRE.POR_CD= :porCd AND "
                + "FTRE.OCF_FRME_CD = '00' ORDER BY FEAT_CD DESC";
    }
    
    /**
     * Delete orderable sales end item.
     *
     * @param updateFlag the update flag
     * @return the string
     */
    public String deleteOrderableSalesEndItem(String updateFlag) {
        
        String queryString = " DELETE FROM MST_OSEI_FEAT WHERE "
                + "POR_CD= :porCd ";
        if (updateFlag.equals(PRMTR_ONE)) {
            queryString += " AND OSEI_ID = :ukOseiID ";
        }
        
        return queryString;
        
    }
    
    /**
     * Delete orderable end item.
     *
     * @param updateFlag the update flag
     * @return the string
     */
    public String deleteOrderableEndItem(String updateFlag) {
        String queryString = " DELETE FROM MST_OEI_FEAT WHERE "
                + "POR_CD= :porCd ";
        if (updateFlag.equals(PRMTR_ONE)) {
            queryString += " AND OEI_BUYER_ID = :ukOeiID ";
        }
        return queryString;
        
    }
    
    /**
     * Update end item sts.
     *
     * @return the string
     */
    public String updateEndItemSts() {
        return "UPDATE MST_OSEI_DTL ODM  SET ODM.END_ITM_STTS_CD = '30' , UPDTD_DT = sysdate , UPDTD_BY = :B000004 WHERE ODM.END_ITM_STTS_CD = '20' and ODM.POR_CD = :porCd and ODM.OSEI_ID in (SELECT OSEI_ID FROM MST_OSEI WHERE OEI_BUYER_ID = :ukOeiBuyerID ) and  ODM.OSEI_ABLSH_DATE > :ablshDate ";
        
    }
    
    /**
     * Select oseifm.
     *
     * @return the string
     */
    public String selectOSEIFM() {
        
        return " Select POR_CD,FEAT_CD,FEAT_TYPE_CD,OCF_FRME_CD,OSEIF_ABLSH_DATE,OSEI_ID FROM MST_OSEI_FEAT WHERE "
                + " POR_CD= :porCd "
                + " AND FEAT_CD= :featCd "
                + " AND FEAT_TYPE_CD= :featTypeCd "
                + " AND OCF_FRME_CD= :ocfFrameCD "
                + " AND (OSEIF_ADPT_DATE <= :ftreAdoptDate"
                + " AND OSEIF_ABLSH_DATE >= :ftreAdoptDate)"
                + " AND OSEI_ID= :ukOeiID ";
        
    }
    
    /**
     * Select oeifm.
     *
     * @return the string
     */
    public String selectOEIFM() {
        
        return " Select POR_CD,FEAT_CD,FEAT_TYPE_CD,OCF_FRME_CD,OEIF_ABLSH_DATE,OEI_BUYER_ID FROM MST_OEI_FEAT WHERE "
                + " TRIM(POR_CD)= :porCd"
                + " AND TRIM(FEAT_CD)= :featCd "
                + " AND TRIM(FEAT_TYPE_CD)= :featTypeCd "
                + " AND TRIM(OCF_FRME_CD)= :ocfFrameCD "
                + " AND TRIM(OEI_BUYER_ID)=:ukOeiID "
                + " AND (TRIM(OEIF_ADPT_DATE) <= :ftreAdoptDate"
                + " AND TRIM(OEIF_ABLSH_DATE) >= :ftreAdoptDate)";
        
    }
    
    /**
     * Update oseifm.
     *
     * @return the string
     */
    public String updateOSEIFM() {
        
        return " UPDATE MST_OSEI_FEAT SET OSEIF_ABLSH_DATE = :ftreAbolishDate , UPDTD_BY = '"
                + BATCH_5_ID
                + "' , UPDTD_DT = sysdate"
                + " WHERE "
                + " TRIM(POR_CD)= :porCd "
                + " AND TRIM(FEAT_CD)= :featCd "
                + " AND TRIM(FEAT_TYPE_CD)= :featTypeCd "
                + " AND TRIM(OCF_FRME_CD)= :ocfFrameCD "
                + " AND TRIM(OSEI_ID)= :ukOeiID "
                + " AND (TRIM(OSEIF_ADPT_DATE) <= :ftreAdoptDate "
                + "AND TRIM(OSEIF_ABLSH_DATE) >= :ftreAdoptDate )";
        
    }
    
    /**
     * Update oeifm.
     *
     * @return the string
     */
    public String updateOEIFM() {
        return " UPDATE MST_OEI_FEAT SET OEIF_ABLSH_DATE = :ftreAbolishDate , UPDTD_BY = '"
                + BATCH_4_ID
                + "' , UPDTD_DT = sysdate "
                + " WHERE "
                + " POR_CD= :porCd "
                + " AND FEAT_CD= :featCd "
                + " AND FEAT_TYPE_CD= :featTypeCd "
                + " AND OCF_FRME_CD= :ocfFrameCD "
                + " AND OEI_BUYER_ID= :ukOeiID "
                + " AND (OEIF_ADPT_DATE <= :ftreAdoptDate "
                + "AND OEIF_ABLSH_DATE >= :ftreAdoptDate )"
                + " AND OEIF_ABLSH_DATE < :ftreAbolishDate ";
        
    }
    
    /**
     * Insert oseifm.
     *
     * @return the string
     */
    public String insertOSEIFM() {
        return " INSERT INTO MST_OSEI_FEAT (POR_CD,FEAT_CD,FEAT_TYPE_CD,OCF_FRME_CD,OSEI_ID,OSEIF_ADPT_DATE,OSEIF_ABLSH_DATE,CRTD_BY,CRTD_DT) VALUES ( :porCd , :featCd , :featTypeCd , :ocfFrameCD, :ukOeiID , :ftreAdoptDate , :ftreAbolishDate, '"
                + BATCH_5_ID + "' , sysdate )";
        
    }
    
    /**
     * Gets the base period.
     *
     * @return the base period
     */
    /*
     * Process ID :P0001.1,P0001.2,P0001.3,P0001.4,P0001.5 Query used to extract
     * the Order take base period weekly and Monthly
     */
    public String getBasePeriod() {
        return "SELECT TO_CHAR(ADD_MONTHS(TO_DATE(WMS.ORDR_TAKE_BASE_MNTH,'YYYYMM'),PMS.VAL1),'YYYYMM') WEEKLY_BASE_PERIOD,PMS.VAL1,"
                + "(SELECT MAX(BASE_MONTH) FROM (SELECT DISTINCT POR_CD, STAGE_CD,"
                + "CASE"
                + " WHEN STAGE_CD NOT LIKE 'SC' THEN (SELECT MIN(ORDR_TAKE_BASE_MNTH) FROM MST_MNTH_ORDR_TAKE_BASE_PD WHERE POR_CD = :porCd"
                + " AND STAGE_CD <> 'SC')"
                + " WHEN STAGE_CD LIKE 'SC' THEN (SELECT MAX(ORDR_TAKE_BASE_MNTH) FROM MST_MNTH_ORDR_TAKE_BASE_PD WHERE POR_CD = :porCd "
                + " AND STAGE_CD = 'SC')"
                + " END AS BASE_MONTH"
                + " FROM MST_MNTH_ORDR_TAKE_BASE_PD"
                + " WHERE POR_CD = :porCd "
                + ")) MONTHLY_BASE_PERIOD FROM MST_WKLY_ORDR_TAKE_BASE WMS INNER JOIN MST_PRMTR PMS ON WMS.POR = PMS.KEY1 WHERE TRIM(PMS.PRMTR_CD) = 'FEATURE ATTACH LIMIT' AND TRIM(PMS.KEY2) = 'MINIMUM PERIOD'"
                + " AND WMS.POR = :porCd "
                + " AND WMS.ORDR_TAKE_BASE_MNTH = (SELECT MAX(ORDR_TAKE_BASE_MNTH) FROM MST_WKLY_ORDR_TAKE_BASE WHERE POR = :porCd )";
        
    }
    
    /**
     * Gets the base period weekly.
     *
     * @return the base period weekly
     */
    public String getBasePeriodWeekly() {
        return 
                 "SELECT MAX(ORDR_TAKE_BASE_MNTH) FROM MST_WKLY_ORDR_TAKE_BASE WHERE POR = :porCd ";
        
    }
    
    /**
     * Gets the parameter value.
     *
     * @return the parameter value
     */
    public String getParameterValue() {
        return 
                 "SELECT PMS.VAL1 FROM MST_PRMTR PMS  WHERE TRIM(PMS.PRMTR_CD) = 'FEATURE ATTACH LIMIT' AND TRIM(PMS.KEY2) = 'MINIMUM PERIOD' AND TRIM(PMS.KEY1) = :porCd ";
        
    }
    
    /**
     * Gets the base period monthly.
     *
     * @return the base period monthly
     */
    /*
     * Process ID :P0001.1,P0001.2,P0001.3,P0001.4,P0001.5 Query used to extract
     * the Order take base period and Monthly
     */
    public String getBasePeriodMonthly() {
        return 
                 "(SELECT MAX(BASE_MONTH) FROM (SELECT DISTINCT POR_CD, STAGE_CD,"
                + "CASE"
                + " WHEN STAGE_CD NOT LIKE 'SC' THEN (SELECT MIN(ORDR_TAKE_BASE_MNTH) FROM MST_MNTH_ORDR_TAKE_BASE_PD WHERE POR_CD = :porCd"
                + " AND STAGE_CD <> 'SC')"
                + " WHEN STAGE_CD LIKE 'SC' THEN (SELECT MAX(ORDR_TAKE_BASE_MNTH) FROM MST_MNTH_ORDR_TAKE_BASE_PD WHERE POR_CD = :porCd "
                + " AND STAGE_CD = 'SC')"
                + " END AS BASE_MONTH"
                + " FROM MST_MNTH_ORDR_TAKE_BASE_PD"
                + " WHERE POR_CD = :porCd "
                + ")) ";
        
    }
    
    /**
     * Gets the ftre abolish date.
     *
     * @return the ftre abolish date
     */
    public String getFtreAbolishDate() {
        
        return "SELECT VAL1 as FEATURE_ABOLISH_DATE FROM MST_PRMTR WHERE TRIM(PRMTR_CD) = 'FEATURE_ABOLISH_DATE' AND TRIM(KEY1) = :porCd ";
    }
    
    /**
     * Insert feature.
     *
     * @return the string
     */
    public String insertFeature() {
        return "INSERT INTO MST_FEAT (POR_CD,CAR_SRS,FEAT_CD,OCF_FRME_CD,FEAT_TYPE_CD,FEAT_GRP_CD,FEAT_SHRT_DESC,FEAT_LNG_DESC,OCF_REGION_CD,OCF_BUYER_GRP_CD,FEAT_ADPT_DATE,FEAT_ABLSH_DATE,CRTD_BY,CRTD_DT) VALUES ( :porCd , :carSrs , :featCd , :ocfFrameCD , :featTypeCd , :featGrpCd , :shrtDesc , :longDesc , :ocfRgnCd , :ocfByrGrpCd , :ftreAdoptDate , :ftreAbolishDate, '"
                + BATCH_4_ID + "' , sysdate )";
    }
    
    /**
     * Insert oeifm.
     *
     * @param feature the feature
     * @return the string
     */
    public String insertOEIFM(Object[] feature) {
        return " INSERT INTO MST_OEI_FEAT (POR_CD,FEAT_CD,FEAT_TYPE_CD,OCF_FRME_CD,OEI_BUYER_ID,OEIF_ADPT_DATE,OEIF_ABLSH_DATE,CRTD_BY,CRTD_DT) VALUES ('"
                + feature[FEATURE_POR_CD]
                + "','"
                + feature[FEATURE_FEAT_CD]
                + "','"
                + feature[FEATURE_FEAT_TYPE_CD]
                + "','"
                + feature[FEATURE_OCF_FRME_CD]
                + "','"
                + ((String) feature[FEATURE_OEI_ID]).trim()
                + "','"
                + feature[FEATURE_FEAT_ADPT_DATE]
                + "','"
                + feature[FEATURE_FEAT_ABLSH_DATE]
                + "','"
                + BATCH_4_ID
                + "' , sysdate )";
        
    }
    
    /**
     * Update feature.
     *
     * @param eiAdptDate the ei adpt date
     * @param eiAblshDate the ei ablsh date
     * @return the string
     */
    public String updateFeature( String eiAdptDate,
            String eiAblshDate) {
        String queryString = "UPDATE MST_FEAT SET ";
        if (!eiAdptDate.isEmpty()) {
            queryString += "FEAT_ADPT_DATE = :ftreAdoptDate ";
        }
        if (!eiAblshDate.isEmpty()) {
            if (!eiAdptDate.isEmpty()) {
                queryString += " , FEAT_ABLSH_DATE = :ftreAbolishDate , UPDTD_BY = '"
                        + BATCH_4_ID + "' , UPDTD_DT = sysdate";
            } else {
                queryString += "FEAT_ABLSH_DATE = :ftreAbolishDate ,UPDTD_BY = '"
                        + BATCH_4_ID + "' , UPDTD_DT = sysdate ";
            }
        }
        queryString += " WHERE " + " POR_CD= :porCd  "
                + " AND CAR_SRS= :carSrs " + " AND FEAT_CD= :featCd "
                + " AND FEAT_TYPE_CD= :featTypeCd "
                + " AND OCF_FRME_CD= :ocfFrameCD "
                + " AND OCF_REGION_CD= :ocfRgnCd "
                + " AND OCF_BUYER_GRP_CD= :ocfByrGrpCd ";
        
        return queryString;
    }
    public String updateFeatureAblsh() {
        String queryString = "UPDATE MST_FEAT SET ";
       
            queryString += " FEAT_ABLSH_DATE = :ftreAbolishDate , UPDTD_BY = '"
                    + BATCH_4_ID + "' , UPDTD_DT = sysdate";
        
       
        queryString += " WHERE " + " POR_CD= :porCd  "
                + " AND CAR_SRS= :carSrs " 
                + " AND FEAT_CD= :featCd "
                + " AND FEAT_TYPE_CD= :featTypeCd "
                + " AND OCF_FRME_CD= '00' "
                + " AND OCF_REGION_CD= :ocfRgnCd "
                + " AND OCF_BUYER_GRP_CD= :ocfByrGrpCd "
                +" AND FEAT_ABLSH_DATE < :ftreAbolishDate ";;
        
        return queryString;
    }
    public String updateFeatureAdpt() {
        String queryString = "UPDATE MST_FEAT SET ";
        
            queryString += "FEAT_ADPT_DATE = :ftreAdoptDate , UPDTD_BY = '"
                        + BATCH_4_ID + "' , UPDTD_DT = sysdate";
        
       
        queryString += " WHERE " + " POR_CD= :porCd  "
                + " AND CAR_SRS= :carSrs " 
                + " AND FEAT_CD= :featCd "
                + " AND FEAT_TYPE_CD= :featTypeCd "
                + " AND OCF_FRME_CD= '00' "
                + " AND OCF_REGION_CD= :ocfRgnCd "
                + " AND OCF_BUYER_GRP_CD= :ocfByrGrpCd "
                +" AND FEAT_ADPT_DATE > :ftreAdoptDate ";
        
        return queryString;
    }
    
    /** Constant INSERT_BATCH_UPDATED_TIME. */
    public static final StringBuilder INSERT_BATCH_UPDATED_TIME = new StringBuilder()
                                                                     .append("merge into SPEC_REEXECUTE_STATUS sh using (SELECT 1 FROM DUAL) s  on (sh.por = :porCd and  TRIM(sh.BATCH_ID) = :BATCH_ID ")
                                                                     .append(" and sh.TABLE_NAME = :tableName ) when matched then  update set sh.PROCESS_EXECUTED_TIME = sysdate, sh.REFERENCE_TIME = (select MAX(updtd_dt) from mst_osei_dtl) ,")
                                                                     .append("sh.UPDTD_BY= :BATCH_ID ,sh.UPDTD_DT = sysdate when not matched then insert")
                                                                     .append("(POR,BATCH_ID,TABLE_NAME,PROCESS_EXECUTED_TIME,REFERENCE_TIME , CRTD_BY,CRTD_DT) values (:porCd,:BATCH_ID,:tableName,sysdate,(select MAX(updtd_dt) from mst_osei_dtl),:BATCH_ID,sysdate)");

    /**
     * Gets the prod stage cd.
     *
     * @return the prod stage cd
     */
    public String getProdStageCd() {
        
        return "(SELECT distinct(p.VAL1) FROM MST_PRMTR p where  TRIM(p.key1)= :porCd and  TRIM(p.PRMTR_CD)='PRODUCTION_STAGE_CD')";
    }

    /**
     * Gets the no ocf weekly monthly features.
     *
     * @return the no ocf weekly monthly features
     */
    public String getNoOcfWeeklyMonthlyFeatures() {
        
        return "SELECT * FROM MST_FEAT WHERE POR_CD= :porCd  "
                + " AND CAR_SRS= :carSrs " 
                + " AND FEAT_CD= :featCd "
                + " AND FEAT_TYPE_CD= :featTypeCd "
                + " AND OCF_FRME_CD= :ocfFrameCD "
                + " AND OCF_REGION_CD= :ocfRgnCd "
                + " AND OCF_BUYER_GRP_CD= :ocfByrGrpCd ";
    }
    
}
