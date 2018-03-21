/*
 * System Name     :Post Dragon 
 * Sub system Name :Interface
 * Function ID     :PST-DRG-I00008
 * Module          :Spec Master
 * Process Outline :Query Constants for I000008 Interface	
 *
 * <Revision History>
 * Date       Name(Company Name)             Description 
 * ---------- ------------------------------ ---------------------
 * 29-06-2015  	  z010356(RNTBCI)               New Creation
 *
 */
package com.nissangroups.pd.util;

/**
 * The Class I00008QueryConstants.
 */
public class I00008QueryConstants {
	

/** Constant DeleteQuery. */
public static final StringBuilder DeleteQuery = new StringBuilder()
.append("DELETE FROM MST_OCF_CLASSFTN WHERE POR_CD =:POR AND MNL_OCF_FLAG = 'N' AND VS_MNTR_FLAG = 'N' "
				+ "AND ORDR_TAKE_BASE_MNTH =:OrdertakeBaseMonth");

/** Constant DeleteQuery. */
public static final StringBuilder getDistinctOrdrTkBsMnth = new StringBuilder()
.append("select distinct m.col24 from CMN_INTERFACE_DATA m  where m.IF_FILE_ID=:ifFileId"
		+ "  and m.SEQ_NO=(Select min(cm.SEQ_NO) from CMN_FILE_HDR cm where cm.IF_FILE_ID= :ifFileId"
		+ "  and cm.STTS='U' ) ");   


/** Constant CarGroupQuery. */
public static final StringBuilder CarGroupQuery = new StringBuilder()
.append("SELECT CAR_GRP FROM MST_POR_CAR_SRS WHERE POR_CD=:POR AND CAR_SRS=:CarSrs");

/** Constant CheckQuery. */
public static final StringBuilder CheckQuery = new StringBuilder()
.append("SELECT FEAT_LNG_DESC FROM MST_FEAT WHERE POR_CD=:porCd AND CAR_SRS=:carSrs AND OCF_BUYER_GRP_CD=:ocfByrGrpCd"
				+ " AND OCF_REGION_CD =:ocfRgnCd AND FEAT_TYPE_CD=:featTypeCd AND OCF_FRME_CD=:ocfFrameCD AND FEAT_SHRT_DESC=:shrtDesc");

/** Constant SelectOcfQuery. */ 
//Redmine issue # 951
public static final StringBuilder SelectOcfQuery = new StringBuilder()
.append("select DISTINCT ocf.POR_CD, ocf.CAR_SRS, ocf.SHRT_DESC, ocf.OCF_FRME_CD ,ocf.VS_MNTR_FLAG from MST_OCF_CLASSFTN ocf,CMN_INTERFACE_DATA cid where ocf.ERR_FLAG is null and ocf.POR_CD =:porCd and cid.SEQ_NO =:seqNo and ocf.CAR_SRS = cid.COL1");

/** Constant SelectFeatQuery. */
//Redmine issue # 1014
public static final StringBuilder SelectFeatQuery = new StringBuilder()
.append("select FEAT_CD from MST_FEAT where POR_CD =:porCd and CAR_SRS =:carSrs"
							  + " and FEAT_SHRT_DESC=:shrtDesc and OCF_FRME_CD =:ocfFrameCD and substr(FEAT_CD,0,1)<>'V'");

/** Constant SelectMaxQuery. */
public static final StringBuilder SelectMaxQuery = new StringBuilder()
.append("select MAX(FEAT_CD) from MST_FEAT where POR_CD =:POR and CAR_SRS =:CarSrs");

/** Constant UpdateStatusQuery. */
public static final StringBuilder UpdateStatusQuery = new StringBuilder()
.append("update CMN_FILE_HDR set STTS = 'P', REMARKS = 'Processed Record' where SEQ_NO in (select min(SEQ_NO) from CMN_FILE_HDR n "
		 + "where IF_FILE_ID =:FileID and STTS = 'U' )");

/** Constant SelectQuery. */
public static final StringBuilder SelectQuery = new StringBuilder()
.append("SELECT FEAT_ADPT_DATE, FEAT_ABLSH_DATE, FEAT_LNG_DESC "
					+ " FROM MST_FEAT "
					+ " WHERE POR_CD =:porCd AND CAR_SRS =:carSrs "
					+ " AND TRIM(FEAT_CD) =:featCd " 
					+ " AND FEAT_TYPE_CD =:featTypeCd "
					+ " AND OCF_REGION_CD =:ocfRgnCd AND OCF_BUYER_GRP_CD =:ocfByrGrpCd");

/** Constant SelectAbolishDateFromPrmMst. */
public static final StringBuilder SelectAbolishDateFromPrmMst = new StringBuilder().append
                         ("SELECT VAL1 FROM MST_PRMTR WHERE PRMTR_CD='Default_AbolishDate'");


/** Constant SelectAdoptedDateFromPrmMst. */
public static final StringBuilder SelectAdoptedDateFromPrmMst = new StringBuilder().append
                         ("SELECT VAL1 FROM MST_PRMTR WHERE PRMTR_CD='Default_AbolishDate'");


/** Constant CheckShortDescription. */
public static final StringBuilder CheckShortDescription = new StringBuilder()

.append("SELECT OCF_SHORT_DESC FROM MST_OCF_CLASSFTN WHERE POR_CD=OR AND CAR_SRS=:CarSrs AND OCF_FRME_CD=cfFrameCd");

/** Constant SelectMaxFC00. */
//Redmine issue # 1014
public static final StringBuilder SelectMaxFC00 = new StringBuilder()
.append("select MAX(FEAT_CD) from MST_FEAT where  OCF_FRME_CD ='00' and substr(FEAT_CD,0,1)<>'V'");

/** Constant SelectMaxFCNot00. */
//Redmine issue # 1014
public static final StringBuilder SelectMaxFCNot00 = new StringBuilder()
.append("select MAX(FEAT_CD) from MST_FEAT where  OCF_FRME_CD !='00' and substr(FEAT_CD,0,1)<>'V'");

public static final StringBuilder InsertMSTFeatQuery = new StringBuilder()
.append("INSERT INTO MST_FEAT (POR_CD, CAR_SRS, FEAT_CD, OCF_FRME_CD, FEAT_TYPE_CD, FEAT_GRP_CD, FEAT_SHRT_DESC, FEAT_LNG_DESC, CRTD_BY, OCF_REGION_CD, OCF_BUYER_GRP_CD, FEAT_ADPT_DATE, FEAT_ABLSH_DATE) ");

//Redmine issue # 951
public static final StringBuilder SeqNoQuery = new StringBuilder()
.append("Select MIN(cm.SEQ_NO) from CMN_FILE_HDR cm where cm.IF_FILE_ID=:FileIDParam and cm.stts='U'");
/**
 * Instantiates a new i00008 query constants.
 */
private I00008QueryConstants(){
	
}


}
