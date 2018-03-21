/*
 * System Name     :Post Dragon 
 * Sub system Name :Batch / Interface
 * Function ID     :PST-DRG-QueryConstants
 * Module          :@Module
 * Process Outline :@Process_Outline
 *
 * <Revision History>
 * Date       Name(Company Name)             Description 
 * ---------- ------------------------------ ---------------------
 * @Date  	  @author(RNTBCI)               New Creation
 *
 */
package com.nissangroups.pd.util;

/**
 * The Class QueryConstants.
 */
public class QueryConstants {

	/** Constant getMaxSpecUpdatedTime. */
	public static final StringBuilder getMaxSpecUpdatedTime = new StringBuilder()
			.append("select MAX(updtd_dt) from mst_osei_dtl");

	/** Constant insertBatchUpdatedTime. */
	public static final StringBuilder insertBatchUpdatedTime = new StringBuilder()
			.append("merge into SPEC_REEXECUTE_STATUS sh using (SELECT 1 FROM DUAL) s  on (sh.por = :porCd and  TRIM(sh.BATCH_ID) = 'B000007' ")
			.append(" and sh.TABLE_NAME = :tableName ) when matched then  update set sh.PROCESS_EXECUTED_TIME = sysdate, sh.REFERENCE_TIME = (select MAX(updtd_dt) from mst_osei_dtl),")
			.append("sh.UPDTD_BY= 'B000007' ,sh.UPDTD_DT = sysdate when not matched then insert ")
			.append("(POR,BATCH_ID,TABLE_NAME,PROCESS_EXECUTED_TIME,REFERENCE_TIME , CRTD_BY,CRTD_DT,UPDTD_BY,UPDTD_DT) values (:porCd,'B000007',:tableName,sysdate,(select MAX(updtd_dt) from mst_osei_dtl),'B000007',sysdate,'B000007',sysdate)");

	/** Constant INSERT_B000003_BATCH_UPDATED_TIME. */
	public static final StringBuilder INSERT_B000003_BATCH_UPDATED_TIME = new StringBuilder()
			.append("Insert into SPEC_REEXECUTE_STATUS (POR,BATCH_ID,TABLE_NAME,PROCESS_EXECUTED_TIME,REFERENCE_TIME,CRTD_DT,CRTD_BY,UPDTD_BY,UPDTD_DT) VALUES")
			.append("(:porCd,'B000003','ORDERABLE_SALES_END_ITEM_DETAIL_MST',:currentTime,:masterUptdTime,:currentTime,'B000003','B000003',:currentTime) ");

	/** Constant selectMstExClr. */
	public static final StringBuilder selectMstExClr = new StringBuilder()
			.append("select m from MstExtClr m WHERE m.id.prodFmyCd =:prdFmlyCd AND m.id.extClrCd=:extClr and m.id.gsisRegionGrnd = :gsisRegGrnd and m.id.prodStageCd = :prodStageCd");

	/** Constant insertExtClr. */
	public static final StringBuilder insertExtClr = new StringBuilder()
			.append("insert into  Mst_Ext_Clr (prod_Fmy_Cd,ext_Clr_Cd,GSIS_REGION_GRND,PROD_STAGE_CD,ext_Clr_Desc,crtd_By,crtd_Dt,updtd_By,updtd_Dt) values (:prdFmlyCd , :extClr,:gsisRegGrnd,:prodStageCd,:extClrDesc,:interfaceId,sysdate,:interfaceId,sysdate)");

	/** Constant updateExtClr. */
	public static final StringBuilder updateExtClr = new StringBuilder()
			.append("update MstExtClr m set m.extClrDesc= :extClrDesc,m.updtdBy = :interfaceId,m.updtdDt = sysdate  where m.id.prodFmyCd = :prdFmlyCd and m.id.extClrCd = :extClr and m.id.gsisRegionGrnd = :gsisRegGrnd and m.id.prodStageCd = :prodStageCd");

	/** Constant minOrdrTkeMnthQuery. */
	public static final StringBuilder minOrdrTkeMnthQuery = new StringBuilder()
			.append("select min(trim(m.col2 ||m.col3)) from CmnInterfaceData m  where m.cmnFileHdr.id.ifFileId= :interfaceId and m.col1 = :porCd  ")
			.append("and m.cmnFileHdr.id.seqNo=(Select min(cm.id.seqNo) from CmnFileHdr cm where cm.cmnInterfaceMst.ifFileId= :interfaceId ")
			.append("and cm.stts=:interfaceStatus and cm.recCount > 0 )");

	/** Constant intrfcDelMstProdTyp. */
	public static final StringBuilder intrfcDelMstProdTyp = new StringBuilder()
			.append("delete from MST_PROD_TYPE where POR_CD = :porCd  ")
			.append("and  ORDR_TAKE_BASE_MNTH >= :ordrTkeBsMnth ");

	/** Constant minOrdrTkeMnthQueryi10. */
	public static final StringBuilder minOrdrTkeMnthQueryi10 = new StringBuilder()
			.append("select min(m.col9) from CmnInterfaceData m  where m.cmnFileHdr.id.ifFileId= :interfaceId and m.col1 = :porCd  ")
			.append("and m.cmnFileHdr.id.seqNo=(Select min(cm.id.seqNo) from CmnFileHdr cm where cm.cmnInterfaceMst.ifFileId= :interfaceId ")
			.append("and cm.stts=:interfaceStatus and cm.recCount > 0 )");

	/** Constant FrznMstProdTyp. */
	public static final StringBuilder FrznMstProdTyp = new StringBuilder()
			.append("delete from MST_FRZN where POR_CD = :porCd  ").append(
					"and FRZN_ORDR_TAKE_BASE_MNTH >= :ordrTkeBsMnth");

	/** Constant getMinimumCarSeriesLimitQuery. */
	public static final StringBuilder getMinimumCarSeriesLimitQuery = new StringBuilder()
			.append("select trim(p.val1) FROM MstPrmtr p WHERE trim(p.id.key1) = :porCd AND  p.id.prmtrCd = 'FROZEN_CREATION_LIMIT'");

	public static final StringBuilder checkCountDuplicateCommonINterface = new StringBuilder()
			.append(" select count(*) from   cmn_interface_data a where rowid > (select min(rowid) from cmn_interface_data b ")
			.append(" where &1 b.if_file_id= :interfaceId and b.seq_no= (select min(seq_no) from Cmn_file_hdr where if_file_id= :interfaceId ")
			.append(" and stts= :interfaceStatus ) ) and a.if_file_id= :interfaceId and a.seq_no= (select min(seq_no) from Cmn_file_hdr where if_file_id= :interfaceId")
			.append(" and stts= :interfaceStatus )");

	public static final StringBuilder fetchOrdrTkBsOrdData = new StringBuilder()
			.append("SELECT * FROM MST_MNTH_ORDR_TAKE_BASE_PD ");

	public static final StringBuilder fetchLatestOrdrTkBsOrdData = new StringBuilder()
			.append("SELECT MAX(ORDR_TAKE_BASE_MNTH) FROM MST_MNTH_ORDR_TAKE_BASE_PD ");

	public static final StringBuilder fetchOrdrTkBsOrdDataCnd = new StringBuilder()
			.append(" WHERE POR_CD = :porCd ");

	public static final String draftOrdrStgCdCondition = "AND STAGE_CD IN ('D1','D2')";

	public static final String finalOrdrStgCdCondition = "AND STAGE_CD IN ('F1','F2')";

	public static final String stgCdNotClsdCondition = "AND STAGE_CD <> 'SC' ";

	public static final String stgStatusCdClsdCondition = "AND STAGE_STTS_CD = 'C' ";

	public static final StringBuilder fetchInVldPorCd = new StringBuilder()
			.append(" SELECT TABLE_NAME.ROW_NO FROM TABLE_NAME ")
			.append(" LEFT OUTER JOIN MST_POR ON TABLE_NAME.POR_CD = MST_POR.POR_CD ")
			.append(" WHERE TABLE_NAME.IF_FILE_ID = :FileID AND MST_POR.POR_CD IS NULL ")
			.append(" AND SEQ_NO=:seqNo ");

	public static final StringBuilder updateErrorCd = new StringBuilder()
			.append(" UPDATE TABLE_NAME SET ERR_CD = :errorCd ")
			.append(" , ERRORMESSAGE = :errorMsg ")
			.append(" , DATASKIPPED = :dataSkipped ");

	public static final StringBuilder updateDueDateErrorCd = new StringBuilder()
			.append(" UPDATE TABLE_NAME SET DUE_DATE_ERR_CD = :errorCd ")
			.append(" , ERRORMESSAGE = :errorMsg ")
			.append(" , DATASKIPPED = :dataSkipped ");

	public static final StringBuilder updateFrznType = new StringBuilder()
			.append(" UPDATE TABLE_NAME SET FRZN_TYPE_CD = :frznType ,OSEI_ID = :oseiId,OEI_BUYER_ID = :oeiByrId");

	public static final StringBuilder updateOseiId = new StringBuilder()
			.append(" UPDATE TABLE_NAME SET OSEI_ID = :oseiId,OEI_BUYER_ID = :oeiByrId");

	public static final StringBuilder updateCnd = new StringBuilder()
			.append(" WHERE POR_CD = :porCd ")
			.append(" AND IF_FILE_ID = :FileID ").append(" AND SEQ_NO=:seqNo ")
			.append(" AND ROW_NO IN :rowNos ");

	public static final StringBuilder updateCndbyRowNo = new StringBuilder()
			.append(" WHERE POR_CD = :porCd ")
			.append(" AND IF_FILE_ID = :FileID ").append(" AND SEQ_NO=:seqNo ")
			.append(" AND ROW_NO = :rowNo ");

	public static final StringBuilder updateInvldPorCdCnd = new StringBuilder()
			.append(" WHERE IF_FILE_ID = :FileID ")
			.append(" AND SEQ_NO=:seqNo ").append(" AND ROW_NO IN :rowNos ");

	public static final StringBuilder updateCndAll = new StringBuilder()
			.append(" WHERE POR_CD = :porCd ")
			.append(" AND IF_FILE_ID = :FileID ").append(" AND SEQ_NO=:seqNo ")
			.append(" AND POT_CD = :potCd ").append(" AND CAR_SRS = :carSrs ")
			.append(" AND APPLD_MDL_CD = :appldMdlCd ")
			.append(" AND PACK_CD = :pckCd ")
			.append(" AND ADD_SPEC_CD = :adtnlSpecCd ")
			.append(" AND SPEC_DESTN_CD = :specDestCd ")
			.append(" AND BUYER_CD = :byrCd ")
			.append(" AND ORDRTK_BASE_PRD = :ordrTkBsMnth ")
			.append(" AND PRODUCTION_PERIOD = :prdMnth ")
			.append(" AND ERR_CD IS NULL ");

	public static final StringBuilder updateErrorCdByDtls = new StringBuilder()

	.append(" UPDATE TABLE_NAME  ")
			.append(" SET TABLE_NAME.ERR_CD = :errorCd ")
			.append(" , ERRORMESSAGE = :errorMsg ")
			.append(" , DATASKIPPED = :dataSkipped ")
			.append(" WHERE TABLE_NAME.BUYER_CD ").append(" IN ")
			.append(" ( SELECT MST_BUYER.BUYER_CD ").append(" FROM MST_BUYER ")
			.append(" , MST_POR ").append(" WHERE ")
			.append(" MST_POR.POR_CD = :porCd ")
			.append(" AND MST_POR.PROD_REGION_CD = MST_BUYER.PROD_REGION_CD ")
			.append(" AND MST_BUYER.BUYER_GRP_CD = :byrGrpCd ) ")
			.append(" AND TABLE_NAME.POR_CD = :porCd ")
			.append(" AND IF_FILE_ID = :FileID ").append(" AND SEQ_NO=:seqNo ")
			.append(" AND TABLE_NAME.ORDRTK_BASE_PRD = :ordrTkBsMnth   ")
			.append("  AND CAR_SRS = :carSrs ")
			.append(" AND TABLE_NAME.ERR_CD IS NULL ");

	public static final StringBuilder updateErrorCdFrInVldPrdMnth = new StringBuilder()
			.append(" UPDATE TABLE_NAME  ")
			.append(" SET TABLE_NAME.ERR_CD = :errorCd ")
			.append(" , TABLE_NAME.ERRORMESSAGE = :errorMsg ")
			.append(" , TABLE_NAME.DATASKIPPED = :dataSkipped ")
			.append(" WHERE ").append(" TABLE_NAME.POR_CD = :porCd ")
			.append(" AND TABLE_NAME.BUYER_GRP_CD = :byrGrpCd  ")
			.append(" AND TABLE_NAME.IF_FILE_ID = :FileID ")
			.append(" AND SEQ_NO=:seqNo ")
			.append(" AND TABLE_NAME.ORDRTK_BASE_PRD = :ordrTkBsMnth   ")
			.append(" AND TABLE_NAME.PRODUCTION_PERIOD = :prdMnth ")
			.append(" AND TABLE_NAME.CAR_SRS = :carSrs ")
			.append(" AND TABLE_NAME.ERR_CD IS NULL ");

	public static final StringBuilder updateErrorCdDtlsFrInVldPrdMnth = new StringBuilder()
			.append(" UPDATE TABLE_NAME  ")
			.append(" SET TABLE_NAME.ERR_CD = :errorCd ")
			.append(" , TABLE_NAME.ERRORMESSAGE = :errorMsg ")
			.append(" , TABLE_NAME.ADOPTPERIOD = :adptPrd ")
			.append(" , TABLE_NAME.ABOLISHPERIOD = :ablshPrd ")
			.append(" , TABLE_NAME.ADOPTDATE = :adptDate ")
			.append(" , TABLE_NAME.ABOLISHDATE = :ablshDate ")
			.append(" , TABLE_NAME.ABOLISHMNTH = :ablshMnth ")
			.append(" , TABLE_NAME.DATASKIPPED = :dataSkipped ")
			.append(" WHERE ").append(" TABLE_NAME.POR_CD = :porCd ")
			.append(" AND TABLE_NAME.BUYER_GRP_CD = :byrGrpCd  ")
			.append(" AND TABLE_NAME.IF_FILE_ID = :FileID ")
			.append(" AND SEQ_NO=:seqNo ")
			.append(" AND TABLE_NAME.ORDRTK_BASE_PRD = :ordrTkBsMnth   ")
			.append(" AND TABLE_NAME.PRODUCTION_PERIOD = :prdMnth ")
			.append(" AND TABLE_NAME.CAR_SRS = :carSrs ")
			.append(" AND TABLE_NAME.ERR_CD IS NULL ");

	public static final StringBuilder updateHorizon = new StringBuilder()
			.append(" UPDATE TABLE_NAME  ")
			.append(" SET TABLE_NAME.HORIZON = :horizon ").append(" WHERE ")
			.append(" TABLE_NAME.POR_CD = :porCd ")
			.append(" AND TABLE_NAME.BUYER_GRP_CD = :byrGrpCd  ")
			.append(" AND TABLE_NAME.IF_FILE_ID = :FileID ")
			.append(" AND SEQ_NO=:seqNo ")
			.append(" AND TABLE_NAME.ORDRTK_BASE_PRD = :ordrTkBsMnth   ")
			.append(" AND TABLE_NAME.PRODUCTION_PERIOD = :prdMnth ")
			.append(" AND TABLE_NAME.CAR_SRS = :carSrs ");

	public static final StringBuilder fetchInVldCarSrs = new StringBuilder()
			.append(" SELECT TABLE_NAME.ROW_NO FROM TABLE_NAME  ")
			.append(" LEFT OUTER JOIN MST_POR_CAR_SRS ON TABLE_NAME.POR_CD = MST_POR_CAR_SRS.POR_CD AND ")
			.append(" MST_POR_CAR_SRS.CAR_SRS = TABLE_NAME.CAR_SRS ")
			.append(" WHERE TABLE_NAME.IF_FILE_ID = :FileID AND TABLE_NAME.POR_CD = :porCd ")
			.append(" AND SEQ_NO=:seqNo ")
			.append(" AND (MST_POR_CAR_SRS.CAR_SRS IS NULL AND MST_POR_CAR_SRS.POR_CD IS NULL) AND TABLE_NAME.ERR_CD IS NULL ");

	public static final StringBuilder fetchInVldBuyerCd = new StringBuilder()
			.append(" SELECT TABLE_NAME.ROW_NO  ").append(" FROM TABLE_NAME ")
			.append(" WHERE TABLE_NAME.IF_FILE_ID = :FileID ")
			.append(" AND SEQ_NO=:seqNo ")
			.append(" AND TABLE_NAME.POR_CD = :porCd  ")
			.append(" AND TABLE_NAME.ERR_CD IS NULL ")
			.append(" AND TABLE_NAME.BUYER_GRP_CD IS NULL ");

	public static final StringBuilder fetchInVldEndItem = new StringBuilder()
			.append(" SELECT DISTINCT " + "TABLE_NAME.ROW_NO"
					+ " FROM TABLE_NAME TABLE_NAME  ")
			.append(" LEFT JOIN " + "MST_POR " + "ON "
					+ "TABLE_NAME.POR_CD = MST_POR.POR_CD  ")
			.append(" INNER JOIN" + " MST_BUYER" + " ON "
					+ "(TABLE_NAME.BUYER_CD = MST_BUYER.BUYER_CD AND "
					+ "TABLE_NAME.POR_CD = MST_POR.POR_CD AND "
					+ "MST_POR.PROD_REGION_CD = MST_BUYER.PROD_REGION_CD )  ")
			.append(" LEFT JOIN " + "MST_OEI_SPEC" + " ON"
					+ " TABLE_NAME.POR_CD = MST_OEI_SPEC.POR_CD   ")
			.append(" AND TABLE_NAME.PACK_CD =  MST_OEI_SPEC.PCK_CD   ")
			.append(" AND TABLE_NAME.CAR_SRS = MST_OEI_SPEC.CAR_SRS  ")
			.append(" AND TABLE_NAME.APPLD_MDL_CD = MST_OEI_SPEC.APPLD_MDL_CD  ");

	public static final StringBuilder fetchInVldEndItemCnd = new StringBuilder()
			.append(" WHERE TABLE_NAME.IF_FILE_ID = :FileID "
					+ " AND TABLE_NAME.POR_CD = :porCd "
					+ " AND TABLE_NAME.ERR_CD IS NULL  ")
			.append(" AND SEQ_NO=:seqNo ")
			.append(" AND MST_OEI_SPEC.POR_CD IS NULL "
					+ " AND MST_OEI_SPEC.CAR_SRS IS NULL   ")
			.append(" AND MST_OEI_SPEC.APPLD_MDL_CD IS NULL "
					+ "AND MST_OEI_SPEC.PCK_CD IS NULL ");

	public static final StringBuilder fetchInVldSpecDest = new StringBuilder()
			.append(" AND TABLE_NAME.spec_destn_cd = MST_OEI_SPEC.spec_destn_cd   ");

	public static final StringBuilder fetchInVldSpecDestCnd = new StringBuilder()
			.append(" AND MST_OEI_SPEC.SPEC_DESTN_CD IS NULL  ");

	public static final StringBuilder fetchInVldAddtnlSpecCd = new StringBuilder()
			.append(" AND TABLE_NAME.ADD_SPEC_CD = MST_OEI_SPEC.ADTNL_SPEC_CD   ");

	public static final StringBuilder fetchInVldAddtnlSpecCdCnd = new StringBuilder()
			.append(" AND MST_OEI_SPEC.ADTNL_SPEC_CD IS NULL  ");

	public static final StringBuilder fetchInVldEndItemforByr = new StringBuilder()
			.append(" SELECT DISTINCT " + " TABLE_NAME.ROW_NO "
					+ " FROM TABLE_NAME ").append(
					"WHERE TABLE_NAME.ROW_NO NOT IN ( ");
	public static final StringBuilder fetchVldEndItemforByr = new StringBuilder()
			.append(" SELECT DISTINCT " + "TABLE_NAME.ROW_NO "
					+ "FROM TABLE_NAME , MST_OEI_BUYER,MST_OEI_SPEC ");

	public static final StringBuilder fetchVldEndItemforByrCnd = new StringBuilder()
			.append(" WHERE TABLE_NAME.POR_CD = MST_OEI_SPEC.POR_CD  ")
			.append(" AND TABLE_NAME.PACK_CD =  MST_OEI_SPEC.PCK_CD  ")
			.append(" AND TABLE_NAME.CAR_SRS = MST_OEI_SPEC.CAR_SRS ")
			.append(" AND TABLE_NAME.APPLD_MDL_CD = MST_OEI_SPEC.APPLD_MDL_CD ")

			.append(" AND TABLE_NAME.por_cd = MST_OEI_BUYER.por_cd ")
			.append(" AND MST_OEI_BUYER.buyer_cd = TABLE_NAME.buyer_cd ")
			.append(" AND MST_OEI_BUYER.oei_spec_id = MST_OEI_SPEC.oei_spec_id ")
			.append(" AND TABLE_NAME.IF_FILE_ID = :FileID ")
			.append(" AND SEQ_NO=:seqNo ")
			.append(" AND TABLE_NAME.POR_CD = :porCd ")
			.append(" AND TABLE_NAME.ERR_CD IS NULL ");

	public static final StringBuilder fetchInVldEndItemforByrCnd = new StringBuilder()
			.append(" ) AND TABLE_NAME.IF_FILE_ID = :FileID ")
			.append(" AND SEQ_NO=:seqNo ")
			.append(" AND TABLE_NAME.POR_CD = :porCd ")
			.append(" AND TABLE_NAME.ERR_CD IS NULL ");

	public static final StringBuilder fetchInVldSpecDestforByrCnd = new StringBuilder()
			.append(" AND TABLE_NAME.SPEC_DESTN_CD = MST_OEI_SPEC.SPEC_DESTN_CD ");

	public static final StringBuilder fetchInVldAddtnlSpecCdforByrCnd = new StringBuilder()
			.append(" AND TABLE_NAME.ADD_SPEC_CD = MST_OEI_SPEC.ADTNL_SPEC_CD ");

	public static final StringBuilder fetchDistinctByrGrpCdAndCarSrs = new StringBuilder()
			.append(" SELECT DISTINCT " + " TABLE_NAME.BUYER_GRP_CD, "
					+ " TABLE_NAME.CAR_SRS " + " ,TABLE_NAME.ORDRTK_BASE_PRD "
					+ " ,TABLE_NAME.PRODUCTION_PERIOD "
					+ " ,TABLE_NAME.OCF_BUYER_GRP_CD "
					+ " ,TABLE_NAME.OCF_REGION_CD "
					+ " ,TABLE_NAME.NSC_EIM_ORDER_HRZN_FLAG "
					+ " ,TABLE_NAME.HORIZON ")

			.append(" FROM TABLE_NAME ")
			.append(" WHERE TABLE_NAME.ERR_CD IS NULL ")
			.append(" AND TABLE_NAME.POR_CD = :porCd ")
			.append(" AND TABLE_NAME.if_file_id=:FileID ")
			.append(" AND SEQ_NO=:seqNo ")
			.append(" AND TABLE_NAME.ORDRTK_BASE_PRD IS NOT NULL ")
			.append(" AND TABLE_NAME.PRODUCTION_PERIOD IS NOT NULL ");

	public static final StringBuilder fetchOrdrTrnsmssnFlg = new StringBuilder()
			.append(" SELECT * " + " FROM " + " MST_MNTHLY_ORDR_TAKE_LCK ")
			.append(" WHERE POR_CD = :porCd  ")
			.append(" AND ORDR_TAKE_BASE_MNTH = :ordrTkBsMnth  ")
			.append(" AND BUYER_GRP_CD like :byrGrpCd  ")
			.append(" AND CAR_SRS like :carSrs  ")
			.append(" AND PROD_ORDR_STAGE_CD = :prdOrdrStgCd  ");

	public static final StringBuilder fetchCarSrsDtls = new StringBuilder()
			.append(" SELECT CAR_SRS,  ").append(" CAR_SRS_ORDR_HRZN, ")
			.append(" CAR_SRS_ADPT_DATE, ").append(" CAR_SRS_ABLSH_DATE ")
			.append(" FROM ").append(" MST_POR_CAR_SRS ")
			.append(" WHERE POR_CD = :porCd ")
			.append(" AND CAR_SRS = :carSrs ")
			.append(" AND CAR_SRS_ABLSH_DATE > :prdOrdr ");

	public static final StringBuilder fetchByrGrpHrzn = new StringBuilder()
			.append(" SELECT DISTINCT ")
			.append(" MAX( MST_BUYER.NSC_EIM_ODER_HRZN_FLAG )" + " FROM "
					+ " MST_POR ," + " MST_BUYER ")
			.append(" WHERE MST_POR.POR_CD = :porCd  ")
			.append(" AND MST_BUYER.BUYER_GRP_CD = :byrGrpCd  ")
			.append(" AND MST_BUYER.PROD_REGION_CD = MST_POR.PROD_REGION_CD  ");

	public static final StringBuilder fetchRowNo = new StringBuilder()
			.append(" select TABLE_NAME.row_no  ").append(" from  ")
			.append(" TABLE_NAME  ");

	public static final StringBuilder fetchInVldClrCdPart1Cnd = new StringBuilder()

	.append(" where TABLE_NAME.row_no not in ( ");

	public static final StringBuilder fetchInVldClrCdPart2Cnd = new StringBuilder()
			.append(" ) and TABLE_NAME.por_cd=:porCd ")
			.append(" and TABLE_NAME.if_file_id=:FileID ")
			.append(" AND SEQ_NO=:seqNo ")
			.append(" and TABLE_NAME.err_cd is NULL ");

	public static final StringBuilder fetchVldEIwithClrCdJoin = new StringBuilder()
			.append(" left join mst_oei_spec on ")
			.append(" TABLE_NAME.por_cd = mst_oei_spec.por_cd ")
			.append(" and TABLE_NAME.car_srs = mst_oei_spec.car_srs ")
			.append(" and TABLE_NAME.appld_mdl_cd = mst_oei_spec.appld_mdl_cd ")
			.append(" and TABLE_NAME.pack_cd = mst_oei_spec.pck_cd ")
			.append(" and TABLE_NAME.spec_destn_cd = mst_oei_spec.spec_destn_cd ")
			.append(" and TABLE_NAME.add_spec_cd = mst_oei_spec.adtnl_spec_cd ")

			.append(" inner join mst_oei_buyer on  ")
			.append(" TABLE_NAME.por_cd = mst_oei_buyer.por_cd ")
			.append(" and mst_oei_buyer.oei_spec_id = mst_oei_spec.oei_spec_id ")
			.append(" and TABLE_NAME.buyer_cd = mst_oei_buyer.buyer_cd ")

			.append(" inner join mst_osei on  ")
			.append(" mst_osei.por_cd = TABLE_NAME.por_cd ")
			.append(" and mst_osei.ext_clr_cd = TABLE_NAME.ext_clr_cd ")
			.append(" and mst_osei.int_clr_cd = TABLE_NAME.int_clr_cd ")
			.append(" and mst_osei.oei_buyer_id = mst_oei_buyer.oei_buyer_id ");

	public static final StringBuilder fetchOseiDtls = new StringBuilder()
			.append(" select mst_osei_dtl.osei_id  ")
			.append(" , mst_osei_dtl.osei_adpt_date ")
			.append(" , mst_osei_dtl.osei_ablsh_date  ")
			.append(" , mst_osei_dtl.osei_suspended_date  ")
			.append(" , TABLE_NAME.row_no ").append(" from  ")
			.append(" TABLE_NAME ");

	public static final StringBuilder fetchOrdrMsQtyForOseiId = new StringBuilder()
			.append(" select  TABLE_NAME.row_no ")
			.append(" , mst_osei.osei_id  ")
			.append(" , trn_mnthly_ordr.ordr_qty ")
			.append(" , trn_mnthly_ordr.ms_qty ").append(" from  ")
			.append(" TABLE_NAME ");

	public static final StringBuilder fetchFrznTypFrOseiId = new StringBuilder()
			.append(" select  TABLE_NAME.row_no ")
			.append(" , mst_osei.osei_id  ")
			.append(" , mst_osei_frzn.FRZN_TYPE_CD ")
			.append(" , mst_osei.OEI_BUYER_ID  ").append(" from  ")
			.append(" TABLE_NAME ");

	public static final StringBuilder fetchOseiIdForEI = new StringBuilder()
			.append(" select  TABLE_NAME.row_no ")
			.append(" , mst_osei.osei_id  ")
			.append(" , mst_osei.OEI_BUYER_ID  ").append(" from  ")
			.append(" TABLE_NAME ");

	public static final StringBuilder fetchOseiDtlsJoin = new StringBuilder()
			.append(" inner join mst_osei_dtl on  ")
			.append(" mst_osei_dtl.por_cd = mst_osei.por_cd ")
			.append(" and mst_osei_dtl.osei_id = mst_osei.osei_id ");

	public static final StringBuilder fetchFrznTypForOseiIdJoin = new StringBuilder()

			.append(" inner join mst_osei_frzn on  ")
			.append(" mst_osei_frzn.osei_id = mst_osei.osei_id ")
			.append(" and mst_osei_frzn.por_cd = mst_osei.por_cd ")
			/*
			 * .append( " and mst_osei_frzn.FRZN_PROD_MNTH =  '201512' " )
			 * .append(
			 * " and mst_osei_frzn.FRZN_ORDR_TAKE_BASE_MNTH = '201511' " );
			 */
			.append(" and mst_osei_frzn.FRZN_PROD_MNTH =  TABLE_NAME.PRODUCTION_PERIOD ")
			.append(" and mst_osei_frzn.FRZN_ORDR_TAKE_BASE_MNTH < TABLE_NAME.ORDRTK_BASE_PRD ");

	public static final StringBuilder fetchOrdrMsQtyForOseiIdJoin = new StringBuilder()

			.append(" left outer join trn_mnthly_ordr on ")
			.append(" trn_mnthly_ordr.osei_id = TABLE_NAME.osei_id ")
			.append(" and trn_mnthly_ordr.por_cd = TABLE_NAME.por_cd ")
			.append(" and trn_mnthly_ordr.prod_mnth =  TABLE_NAME.production_period ")
			.append(" and trn_mnthly_ordr.ordrtk_base_mnth = TABLE_NAME.ORDRTK_BASE_PRD ")
			.append(" and trn_mnthly_ordr.pot_cd = TABLE_NAME.pot_cd ")
			.append(" and trn_mnthly_ordr.PROD_ORDR_STAGE_CD = TABLE_NAME.PRODUCTION_ORDER_STAGE_CD ");

	public static final StringBuilder fetchVldEIwithClrCdCnd = new StringBuilder()

	.append(" where TABLE_NAME.por_cd=:porCd ")
			.append(" and TABLE_NAME.if_file_id=:FileID ")
			.append(" AND SEQ_NO=:seqNo ")
			.append(" and TABLE_NAME.err_cd is NULL ");

	public static final StringBuilder frznTypCdCnd = new StringBuilder()
			.append(" and TABLE_NAME.FRZN_TYPE_CD = :frznType ");

	public static final StringBuilder generalCnd = new StringBuilder()

	.append(" AND TABLE_NAME.ORDRTK_BASE_PRD = :ordrTkBsMnth ")
			.append(" AND TABLE_NAME.production_period = :prdMnth ")
			.append(" AND TABLE_NAME.car_srs =:carSrs ")
			.append(" AND TABLE_NAME.appld_mdl_cd = :appldMdlCd ")
			.append(" AND TABLE_NAME.pack_cd = :pckCd ")
			.append(" AND TABLE_NAME.buyer_grp_cd = :byrGrpCd ");

	public static final StringBuilder intClrCdCnd = new StringBuilder()
			.append(" AND TABLE_NAME.int_clr_cd  = :intClrCd ");
	public static final StringBuilder extClrCdCnd = new StringBuilder()
			.append(" AND TABLE_NAME.ext_clr_cd  = :extrClrCd ");
	public static final StringBuilder specDestCdCnd = new StringBuilder()
			.append(" AND TABLE_NAME.spec_destn_cd = :specDestCd ");

	public static final StringBuilder fetchSmmrzdOrdrQtyPart1 = new StringBuilder()
			.append(" select ").append(
					"  sum(TABLE_NAME.ordr_qty) as SUM_ORDR_QTY ");

	public static final StringBuilder fetchPotOseiId = new StringBuilder()
			.append(" select ").append(" TABLE_NAME.ordr_qty as ordr_qty ")
			.append(", TABLE_NAME.POT_CD as POT_CD")
			.append(", TABLE_NAME.OSEI_ID as OSEI_ID ");

	public static final StringBuilder fetchSmmrzdOrdrQtyPart2 = new StringBuilder()
			.append(" , TABLE_NAME.por_cd as POR_CD ")
			.append(" , TABLE_NAME.ORDRTK_BASE_PRD as ORDR_TK_BS_PRD ")
			.append(" , TABLE_NAME.production_period as PRD_PERIOD ")
			.append(" , TABLE_NAME.FRZN_TYPE_CD as FRZN_TYPE_CD ")
			.append(" , TABLE_NAME.car_srs as CAR_SRS ")
			.append(" , TABLE_NAME.appld_mdl_cd as APPLD_MDL_CD ")
			.append(" , TABLE_NAME.pack_cd as PCK_CD ")
			.append(" , TABLE_NAME.buyer_grp_cd as BUYER_GRP_CD ");

	public static final StringBuilder fetchFrznOrdrErrDtls = new StringBuilder()
			.append(" , TABLE_NAME.BUYER_CD as BUYER_CD ")
			.append(" , TABLE_NAME.ADD_SPEC_CD as ADD_SPEC_CD ")
			.append(" , TRN_MNTHLY_ORDR.MS_QTY as MS_QTY ");

	public static final StringBuilder fetchSmmrzdOrdrQtyPart3 = new StringBuilder()

	.append(" from  ").append(" TABLE_NAME ");

	public static final StringBuilder fetchSmmrzdOrdrQtyGrpBy = new StringBuilder()
			.append(" group by ").append(" TABLE_NAME.por_cd ")
			.append(" , TABLE_NAME.FRZN_TYPE_CD ")
			.append(" , TABLE_NAME.car_srs ")
			.append(" , TABLE_NAME.appld_mdl_cd ")
			.append(" , TABLE_NAME.pack_cd ")
			.append(" , TABLE_NAME.buyer_grp_cd ")
			.append(" , TABLE_NAME.ORDRTK_BASE_PRD ")
			.append(" , TABLE_NAME.production_period ");

	public static final StringBuilder fetchIntClrCd = new StringBuilder()
			.append(" , TABLE_NAME.int_clr_cd as INT_CLR_CD ");
	public static final StringBuilder fetchExtClrCd = new StringBuilder()
			.append(" , TABLE_NAME.ext_clr_cd as EXT_CLR_CD ");
	public static final StringBuilder fetchSpecDestCd = new StringBuilder()
			.append(" , TABLE_NAME.spec_destn_cd as SPEC_DESTN_CD ");

	public static final StringBuilder grpByIntClrCd = new StringBuilder()
			.append(" , TABLE_NAME.int_clr_cd  ");
	public static final StringBuilder grpByExtClrCd = new StringBuilder()
			.append(" , TABLE_NAME.ext_clr_cd  ");
	public static final StringBuilder grpBySpecDestCd = new StringBuilder()
			.append(" , TABLE_NAME.spec_destn_cd ");

	public static final StringBuilder fetchInVldPotCd = new StringBuilder()

	.append(" SELECT DISTINCT TABLE_NAME.ROW_NO  ").append(" FROM  ")
			.append(" TABLE_NAME ").append(" LEFT JOIN MST_PROD_ORDR_TYPE ON ")
			.append(" TABLE_NAME.POR_CD = MST_PROD_ORDR_TYPE.POR_CD ")
			.append(" AND TABLE_NAME.POT_CD = MST_PROD_ORDR_TYPE.POT_CD ")
			.append(" WHERE MST_PROD_ORDR_TYPE.POR_CD IS NULL  ")
			.append(" AND MST_PROD_ORDR_TYPE.POT_CD IS NULL ")
			.append(" AND TABLE_NAME.POR_CD = :porCd ")
			.append(" AND TABLE_NAME.IF_FILE_ID = :FileID ")
			.append(" AND SEQ_NO=:seqNo ")
			.append(" AND TABLE_NAME.ERR_CD IS NULL ");

	public static final StringBuilder fetchDplctRcrds = new StringBuilder()

			.append(" SELECT A.ROW_NO FROM  ")
			.append(" TABLE_NAME A ")
			.append(" WHERE ")
			.append(" ROWID > ")
			.append(" (SELECT MIN(ROWID) FROM TABLE_NAME B ")
			.append(" WHERE ")
			.append(" B.POR_CD = A.POR_CD ")
			.append(" AND B.CAR_SRS = A.CAR_SRS ")
			.append(" AND A.BUYER_CD = B.BUYER_CD ")
			.append(" AND A.APPLD_MDL_CD = B.APPLD_MDL_CD ")
			.append(" AND A.PACK_CD = B.PACK_CD ")
			.append(" AND A.SPEC_DESTN_CD = B.SPEC_DESTN_CD ")
			.append(" AND A.ADD_SPEC_CD = B.ADD_SPEC_CD ")
			.append(" AND A.EXT_CLR_CD = B.EXT_CLR_CD ")
			.append(" AND A.INT_CLR_CD = B.INT_CLR_CD ")
			.append(" AND A.POT_CD = B.POT_CD ")
			.append(" AND A.ORDRTK_BASE_PRD = B.ORDRTK_BASE_PRD ")
			.append(" AND A.PRODUCTION_ORDER_STAGE_CD = B.PRODUCTION_ORDER_STAGE_CD ")
			.append(" AND ((A.PRODUCTION_PERIOD = B.PRODUCTION_PERIOD) or (A.PRODUCTION_PERIOD IS NULL AND B.PRODUCTION_PERIOD IS NULL) ) ")
			.append(" AND B.IF_FILE_ID=:FileID AND B.SEQ_NO=:seqNo AND B.POR_CD = :porCd AND B.ERR_CD IS NULL ")
			.append(" ) AND A.IF_FILE_ID=:FileID AND A.SEQ_NO=:seqNo AND A.POR_CD = :porCd AND A.ERR_CD IS NULL ");

	public static final StringBuilder fetchDueDateFrmOrToNullRcrds = new StringBuilder()
			.append(" SELECT DISTINCT ROW_NO FROM TABLE_NAME  ")
			.append(" WHERE POR_CD = :porCd  ")
			.append(" AND IF_FILE_ID = :FileID ")
			.append(" AND SEQ_NO=:seqNo ")
			.append(" AND (ERR_CD IS NULL ")
			.append(" AND TABLE_NAME.DUE_DATE_ERR_CD IS NULL ) ")
			.append(" AND (DUE_DATE_FRM IS NULL OR DUE_DATE_TO IS NULL) ")
			.append(" AND (NOT (DUE_DATE_FRM IS NULL AND DUE_DATE_TO IS NULL)) ");

	public static final StringBuilder fetchDueDateFrmGrtrRcrds = new StringBuilder()
			.append(" SELECT DISTINCT ROW_NO FROM TABLE_NAME  ")
			.append(" WHERE POR_CD = :porCd  ")
			.append(" AND IF_FILE_ID = :FileID ")
			.append(" AND SEQ_NO=:seqNo ")
			.append(" AND ( ERR_CD IS NULL ")
			.append(" AND TABLE_NAME.DUE_DATE_ERR_CD IS NULL ) ")
			.append(" AND (DUE_DATE_FRM IS NOT NULL AND DUE_DATE_TO IS NOT NULL) ")
			.append(" AND DUE_DATE_FRM > DUE_DATE_TO ");

	public static final StringBuilder fetchInVldDueDateRcrds = new StringBuilder()
			.append(" SELECT DISTINCT ROW_NO FROM TABLE_NAME, MST_WK_NO_CLNDR  ")
			.append(" WHERE TABLE_NAME.POR_CD = :porCd  ")
			.append(" AND TABLE_NAME.IF_FILE_ID = :FileID ")
			.append(" AND SEQ_NO=:seqNo ")
			.append(" AND ( TABLE_NAME.ERR_CD IS NULL  ")
			.append(" AND TABLE_NAME.DUE_DATE_ERR_CD IS NULL ) ")
			.append(" AND (TABLE_NAME.DUE_DATE_FRM IS NOT NULL ")
			.append(" AND TABLE_NAME.DUE_DATE_TO IS NOT NULL) ")
			.append(" AND TABLE_NAME.DUE_DATE_FRM < TABLE_NAME.DUE_DATE_TO ")
			.append(" AND MST_WK_NO_CLNDR.POR_CD = TABLE_NAME.POR_CD ")
			.append(" AND MST_WK_NO_CLNDR.PROD_MNTH = TABLE_NAME.PRODUCTION_PERIOD ")
			.append(" AND (NOT (MST_WK_NO_CLNDR.WK_STRT_DATE <=  TABLE_NAME.DUE_DATE_FRM ")
			.append(" AND MST_WK_NO_CLNDR.WK_END_DATE >= TABLE_NAME.DUE_DATE_TO)) ");

	public static final StringBuilder fetchDistinctDueDateRcrds = new StringBuilder()

	.append(" SELECT DISTINCT  ").append(" POR_CD ").append(" , POT_CD ")
			.append(" , APPLD_MDL_CD ").append(" , PACK_CD ")
			.append(" , CAR_SRS ").append(" , ADD_SPEC_CD ")
			.append(" , SPEC_DESTN_CD ").append(" , BUYER_CD ")
			.append(" , ORDRTK_BASE_PRD ").append(" , PRODUCTION_PERIOD ")
			.append(" FROM TABLE_NAME ")
			.append(" WHERE TABLE_NAME.POR_CD = :porCd ")
			.append(" AND TABLE_NAME.IF_FILE_ID = :FileID ")
			.append(" AND SEQ_NO=:seqNo ")
			.append(" AND ( TABLE_NAME.ERR_CD IS NULL  ")
			.append(" AND TABLE_NAME.DUE_DATE_ERR_CD IS NULL ) ")
			.append(" AND (TABLE_NAME.DUE_DATE_FRM IS NOT NULL ")
			.append(" AND TABLE_NAME.DUE_DATE_TO IS NOT NULL) ");

	public static final StringBuilder fetchDiffDueDateRcrds = new StringBuilder()
			.append(" SELECT count(POR_CD) from  ").append(" TABLE_NAME ")
			.append(" where  ").append(" TABLE_NAME.POR_CD = :porCd ")
			.append("  AND TABLE_NAME.IF_FILE_ID = :FileID ")
			.append("  AND SEQ_NO=:seqNo ").append("  and POT_CD = :potCd ")

			.append("  and  APPLD_MDL_CD  = :appldMdlCd ")
			.append("  and CAR_SRS = :carSrs ")
			.append("  and  PACK_CD = :pckCd  ")
			.append("  and  ADD_SPEC_CD = :adtnlSpecCd ")
			.append("  and  SPEC_DESTN_CD =:specDestCd ")
			.append("  and BUYER_CD = :byrCd ")
			.append("  and  ORDRTK_BASE_PRD = :ordrTkBsMnth ")
			.append("  and  PRODUCTION_PERIOD = :prdMnth ")
			.append(" AND ( TABLE_NAME.ERR_CD IS NULL   ")
			.append("  AND TABLE_NAME.DUE_DATE_ERR_CD IS NULL ) ")
			.append("  AND (TABLE_NAME.DUE_DATE_FRM IS NOT NULL   ")
			.append("  AND TABLE_NAME.DUE_DATE_TO IS NOT NULL)  ");

	public static final StringBuilder fetchDistinctRowNo = new StringBuilder()
			.append(" SELECT DISTINCT ROW_NO ").append(" FROM ")
			.append(" TABLE_NAME ")
			.append(" WHERE TABLE_NAME.POR_CD = :porCd ")
			.append(" AND TABLE_NAME.IF_FILE_ID = :FileID ")
			.append(" AND SEQ_NO=:seqNo ")
			.append(" AND TABLE_NAME.ERR_CD IS NULL  ");

	public static final StringBuilder deleteProccessedFile = new StringBuilder()
			.append(" DELETE ").append(" FROM ").append(" TABLE_NAME ")
			.append(" WHERE TABLE_NAME.POR_CD = :porCd ")
			.append(" AND SEQ_NO=:seqNo ")
			.append(" AND TABLE_NAME.IF_FILE_ID = :FileID ");

	public static final StringBuilder fetchInVldPrdnPeriodCnd = new StringBuilder()
			.append(" AND TABLE_NAME.PRODUCTION_PERIOD_TYPE != 'M' ");

	public static final StringBuilder fetchInVldOrdrTkBsPrdCnd = new StringBuilder()
			.append(" AND  TABLE_NAME.ORDRTK_BASE_PRD_TYPE != 'M' ");

	public static final StringBuilder fetchInVldOrdrTkBsMnthCnd = new StringBuilder()
			.append(" AND ( ORDRTK_BASE_PRD NOT IN (:ordrTkBsMnthLst) ")
			.append(" OR ORDRTK_BASE_PRD IS NULL ) ");

	public static final StringBuilder fetchSmmrzdMsQtyPart1 = new StringBuilder()
			.append(" select ").append(
					" sum(TRN_MNTHLY_ORDR.ms_qty)  as SUM_MS_QTY  ");
	public static final StringBuilder selectPotOseiId = new StringBuilder()
			.append(" select ").append(" TRN_MNTHLY_ORDR.ORDR_QTY as ORDR_QTY")
			.append(", TRN_MNTHLY_ORDR.POT_CD as POT_CD")
			.append(", TRN_MNTHLY_ORDR.OSEI_ID as OSEI_ID ");

	public static final StringBuilder fetchSmmrzdMsQtyPart2 = new StringBuilder()
			.append(", TRN_MNTHLY_ORDR.por_cd  as POR_CD ")
			.append(", trn_mnthly_ordr.ordrtk_base_mnth as ORDR_TK_BS_MNTH ")
			.append(", trn_mnthly_ordr.prod_mnth  as PRD_MNTH ")
			.append(", mst_osei_frzn.FRZN_TYPE_CD  as FRZN_TYPE_CD ")
			.append(", mst_oei_spec.car_srs  as CAR_SRS ")
			.append(", mst_oei_spec.appld_mdl_cd as APPLD_MDL_CD ")
			.append(", mst_oei_spec.pck_cd  as PCK_CD  ")
			.append(", mst_buyer.buyer_grp_cd  as BUYER_GRP_CD ");

	public static final StringBuilder fetchExistingFrznOrdrErrDtls = new StringBuilder()
			.append(" , mst_buyer.BUYER_CD as BUYER_CD ")
			.append(" , mst_oei_spec.ADTNL_SPEC_CD as ADTNL_SPEC_CD ")
			.append(" , TRN_MNTHLY_ORDR.MS_QTY as MS_QTY ");

	public static final StringBuilder fetchSmmrzdMsQtyPart3 = new StringBuilder()

	.append("  from   TRN_MNTHLY_ORDR   ");

	public static final StringBuilder fetchOseiExtClrCd = new StringBuilder()
			.append(", mst_osei.ext_clr_cd as EXT_CLR_CD ");
	public static final StringBuilder fetchOseiIntClrCd = new StringBuilder()
			.append(", mst_osei.int_clr_cd as INT_CLR_CD ");
	public static final StringBuilder fetchOeiSpecSpecDestCd = new StringBuilder()
			.append(", mst_oei_spec.spec_destn_cd as SPEC_DESTN_CD");

	public static final StringBuilder grpByOseiExtClrCd = new StringBuilder()
			.append(", mst_osei.ext_clr_cd ");
	public static final StringBuilder grpByOseiIntClrCd = new StringBuilder()
			.append(", mst_osei.int_clr_cd ");
	public static final StringBuilder grpByOeiSpecSpecDestCd = new StringBuilder()
			.append(", mst_oei_spec.spec_destn_cd ");

	public static final StringBuilder fetchSmmrzdMsQtyJoin = new StringBuilder()

			.append(" inner join mst_oei_spec on  mst_oei_spec.por_cd = :porCd  ")
			.append(" and mst_oei_spec.car_srs  = :carSrs ")
			.append(" and mst_oei_spec.appld_mdl_cd = :appldMdlCd ")
			.append(" and mst_oei_spec.pck_cd  = :pckCd ")

			.append(" inner join mst_por on ")
			.append(" mst_por.por_cd = mst_oei_spec.por_cd ")

			.append(" inner join mst_buyer on    ")
			.append(" mst_buyer.buyer_cd  ")
			.append(" in (select buyer_cd from mst_buyer where buyer_grp_cd = :byrGrpCd) ")
			.append(" and mst_buyer.PROD_REGION_CD = mst_por.PROD_REGION_CD ")

			.append(" inner join mst_oei_buyer on    ")
			.append(" mst_oei_buyer.oei_spec_id = mst_oei_spec.oei_spec_id ")
			.append(" and mst_oei_buyer.buyer_cd = mst_buyer.buyer_cd ")
			.append(" and mst_oei_buyer.por_cd = mst_oei_spec.por_cd ")

			.append(" inner join mst_osei on   mst_osei.por_cd = mst_oei_buyer.por_cd ")

			.append(" and mst_osei.oei_buyer_id = mst_oei_buyer.oei_buyer_id ")

			.append(" inner join mst_osei_frzn on   ")
			.append(" mst_osei_frzn.osei_id = mst_osei.osei_id ")
			.append(" and mst_osei_frzn.por_cd = mst_osei.por_cd  ")
			.append(" and mst_osei_frzn.FRZN_PROD_MNTH =  :prdMnth  ")
			.append(" and mst_osei_frzn.FRZN_ORDR_TAKE_BASE_MNTH < :ordrTkBsMnth ")
			.append(" and mst_osei_frzn.FRZN_TYPE_CD = :frznType ");

	public static final StringBuilder fetchExistingFrznOrdrErrJoin = new StringBuilder()

			.append(" LEFT join TABLE_NAME  ")
			.append(" on  trn_mnthly_ordr.osei_id = TABLE_NAME.osei_id ")
			.append(" and trn_mnthly_ordr.por_cd = TABLE_NAME.por_cd  ")
			.append(" and trn_mnthly_ordr.ordrtk_base_mnth = TABLE_NAME.ORDRTK_BASE_PRD ")
			.append(" and trn_mnthly_ordr.PROD_ORDR_STAGE_CD = TABLE_NAME.PRODUCTION_ORDER_STAGE_CD ")
			.append(" and trn_mnthly_ordr.prod_mnth =  TABLE_NAME.production_period   ")
			.append(" and trn_mnthly_ordr.pot_cd =  TABLE_NAME.pot_cd ");

	public static final StringBuilder fetchSmmrzdMsQtyCnd = new StringBuilder()

			.append(" where trn_mnthly_ordr.por_cd = mst_osei.por_cd ")
			.append(" and trn_mnthly_ordr.prod_mnth =  :prdMnth ")
			.append(" and trn_mnthly_ordr.ordrtk_base_mnth = :ordrTkBsMnth ")
			.append(" and trn_mnthly_ordr.osei_id = mst_osei.osei_id  ")
			.append(" and trn_mnthly_ordr.PROD_ORDR_STAGE_CD = :prdOrdrStgCd  ")
			.append(" and trn_mnthly_ordr.MS_QTY is not null  ");

	public static final StringBuilder fetchExistingFrznOrdrErrCnd = new StringBuilder()
			.append(" and TABLE_NAME.por_cd  is null ")
			.append(" and TABLE_NAME.ORDRTK_BASE_PRD  is null ")
			.append(" and TABLE_NAME.PRODUCTION_ORDER_STAGE_CD is null ")
			.append(" and TABLE_NAME.production_period  is null ")
			.append(" and TABLE_NAME.pot_cd is null ")
			.append(" and TABLE_NAME.osei_id is null ");

	public static final StringBuilder grpByOseiExtClrCdCnd = new StringBuilder()
			.append(" and mst_osei.ext_clr_cd = :extrClrCd ");
	public static final StringBuilder grpByOseiIntClrCdCnd = new StringBuilder()
			.append(" and mst_osei.int_clr_cd = :intClrCd ");
	public static final StringBuilder grpByOeiSpecSpecDestCdCnd = new StringBuilder()
			.append(" and mst_oei_spec.spec_destn_cd = :specDestCd ");

	public static final StringBuilder fetchSmmrzdMsQtyGrpBy = new StringBuilder()
			.append(" group by  ").append(" TRN_MNTHLY_ORDR.por_cd ")
			.append(" , mst_osei_frzn.FRZN_TYPE_CD ")
			.append(" , mst_oei_spec.car_srs  ")
			.append(" , mst_oei_spec.appld_mdl_cd ")
			.append(" , mst_oei_spec.pck_cd   ")
			.append(" , mst_buyer.buyer_grp_cd  ")
			.append(" , trn_mnthly_ordr.ordrtk_base_mnth ")
			.append(" , trn_mnthly_ordr.prod_mnth  ");

	public static final StringBuilder fetchSmmrzdQrdrQtybyOseiId = new StringBuilder()

	.append(" SELECT ORDRTK_BASE_PRD ").append(", PRODUCTION_PERIOD ")
			.append(", OSEI_ID ").append(", SUM(ORDR_QTY) ")
			.append(" FROM TABLE_NAME ").append(" WHERE ERR_CD IS NULL ")
			.append(" AND OSEI_ID IS NOT NULL ")
			.append(" GROUP BY  ORDRTK_BASE_PRD ")
			.append(", PRODUCTION_PERIOD ").append(", OSEI_ID ");

	public static final StringBuilder fetchFeatCdForOseiId = new StringBuilder()
			.append(" SELECT * FROM TRN_BUYER_MNTHLY_OCF_USG ")
			.append(" WHERE  ").append(" POR_CD = :porCd ")
			.append(" AND ORDR_TAKE_BASE_MNTH = :ordrTkBsMnth ")
			.append(" AND OSEI_ID = :oseiId ")
			.append(" AND PROD_MNTH  = :prdMnth  ");

	public static final StringBuilder fetchDistinctByrCd = new StringBuilder()
			.append(" SELECT DISTINCT BUYER_CD ").append(" FROM  ")
			.append(" TABLE_NAME ").append(" WHERE POR_CD = :porCd ")
			.append(" AND SEQ_NO=:seqNo ").append(" AND IF_FILE_ID = :FileID ");

	public static final StringBuilder fetchDistinctCarSrs = new StringBuilder()
			.append(" SELECT DISTINCT TABLE_NAME.CAR_SRS ")
			.append(" ,TABLE_NAME.ORDRTK_BASE_PRD ")
			.append(" , TABLE_NAME.NSC_EIM_ORDER_HRZN_FLAG ")
			.append(" , mst_por_car_srs.car_srs_ordr_hrzn ")
			.append(" , TABLE_NAME.PRODUCTION_PERIOD ")
			.append(" , TABLE_NAME.BUYER_GRP_CD ")
			.append(" , mst_por_car_srs.CAR_SRS_ADPT_DATE ")
			.append(" , mst_por_car_srs.CAR_SRS_ABLSH_DATE ")
			.append(" FROM  ")
			.append(" TABLE_NAME")
			.append(" , mst_por_car_srs   ")
			.append(" WHERE TABLE_NAME.POR_CD = :porCd ")
			.append(" AND IF_FILE_ID = :FileID ")
			.append(" AND SEQ_NO=:seqNo ")
			.append(" AND TABLE_NAME.CAR_SRS = MST_POR_CAR_SRS.CAR_SRS ")
			.append(" AND TABLE_NAME.POR_CD = MST_POR_CAR_SRS.POR_CD ")
			.append(" AND MST_POR_CAR_SRS.CAR_SRS_ABLSH_DATE > TABLE_NAME.ORDRTK_BASE_PRD ")
			.append(" AND TABLE_NAME.ERR_CD IS NULL ");

	public static final StringBuilder fetchByrGrpCdForByrCdAndPorCd = new StringBuilder()
			.append(" SELECT MST_BUYER.BUYER_GRP_CD , NSC_EIM_ODER_HRZN_FLAG ")
			.append(" , OCF_REGION_CD ").append(" , OCF_BUYER_GRP_CD ")
			.append(" FROM MST_BUYER, MST_POR ")
			.append(" WHERE MST_POR.POR_CD = :porCd ")
			.append(" AND MST_POR.PROD_REGION_CD = MST_BUYER.PROD_REGION_CD ")
			.append(" AND MST_BUYER.BUYER_CD = :byrCd ");

	public static final StringBuilder updateByrGrpCd = new StringBuilder()

	.append(" UPDATE TABLE_NAME ").append(" SET BUYER_GRP_CD = :byrGrpCd  ")
			.append(" , NSC_EIM_ORDER_HRZN_FLAG = :nscEimOrdHrznFlg  ")
			.append(" , OCF_REGION_CD = :ocfRgnCd  ")
			.append(" , OCF_BUYER_GRP_CD = :ocfByrGrpCd  ")
			.append(" WHERE TABLE_NAME.POR_CD = :porCd ")
			.append(" AND TABLE_NAME.IF_FILE_ID=:FileID ")
			.append(" AND SEQ_NO=:seqNo ")
			.append(" AND TABLE_NAME.BUYER_CD = :byrCd  ");

	public static final StringBuilder updateOrdrQtyInmnthlyOrderTrn = new StringBuilder()
			.append(" UPDATE TRN_MNTHLY_ORDR  ")
			.append(" SET ORDR_QTY  = :ordrQty  ");
	
	public static final StringBuilder updateOrdrQtyInmnthlyOrderTrnB000020 = new StringBuilder()
	.append(" UPDATE TRN_MNTHLY_ORDR  ")
	.append(" SET ORDR_QTY  = :ordrQty , UPDTD_DT = sysdate , UPDTD_BY = :updtdBy ");
	
	public static final StringBuilder checkRecordExistsInmnthlyOrderTrn = new StringBuilder()
			.append(" SELECT COUNT(OSEI_ID) FROM TRN_MNTHLY_ORDR  ");

	public static final StringBuilder mnthlyOrderTrnPkCnd = new StringBuilder()
			.append(" WHERE POR_CD =:porCd   ")
			.append(" AND ORDRTK_BASE_MNTH = :ordrTkBsMnth ")
			.append(" AND PROD_MNTH = :prdMnth ")
			.append(" AND PROD_ORDR_STAGE_CD = :prdOrdrStgCd ")
			.append(" AND OSEI_ID  = :oseiId ");
	public static final StringBuilder mnthlyOrderTrnPotCnd = new StringBuilder()
			.append(" AND POT_CD   = :potCd ");
	public static final StringBuilder mnthlyOrderTrnPotCndAll = new StringBuilder()
			.append(" AND POT_CD   in ( select POT_CD from MST_PROD_ORDR_TYPE where POR_CD = :porCd)");

	public static final StringBuilder insertIntoManuelDueDatePrmtr = new StringBuilder()
			.append(" INSERT INTO TRN_MNL_DUE_DATE_PRMTR  ")
			.append(" (POR_CD,  ")
			.append(" ORDR_TAKE_BASE_MNTH, ")
			.append(" PROD_MNTH, ")
			.append(" OEI_BUYER_ID, ")
			.append(" POT_CD, ")
			.append(" DUE_DATE_FRM, ")
			.append(" DUE_DATE_TO, ")
			.append(" DUE_DATE_ORDR_QTY) ")
			.append(" SELECT  ")
			.append(" POR_CD, ")
			.append(" substr(ORDRTK_BASE_PRD, 0, 6) AS ORDR_TAKE_BASE_MNTH, ")
			.append(" substr(PRODUCTION_PERIOD, 0, 6) AS PROD_MNTH, ")
			.append(" OEI_BUYER_ID, ")
			.append(" POT_CD, ")
			.append(" OLD_DUE_DATE_FRM AS DUE_DATE_FRM, ")
			.append(" OLD_DUE_DATE_TO AS DUE_DATE_TO, ")
			.append(" ORDR_QTY AS DUE_DATE_ORDR_QTY ")
			.append(" FROM TABLE_NAME ")
			.append(" WHERE TABLE_NAME.POR_CD = :porCd ")
			.append(" AND TABLE_NAME.IF_FILE_ID = :FileID ")
			.append(" AND SEQ_NO=:seqNo ")
			.append(" AND (TABLE_NAME.ERR_CD IS NULL OR TABLE_NAME.DUE_DATE_ERR_CD IS NOT NULL )")

			.append(" AND ( TABLE_NAME.DUE_DATE_FRM IS NULL ")
			.append(" AND TABLE_NAME.DUE_DATE_TO IS NULL )");

	public static final StringBuilder deleteFrmManuelDueDatePrmtr = new StringBuilder()

			.append(" DELETE FROM  ")
			.append(" TRN_MNL_DUE_DATE_PRMTR ")
			.append(" WHERE ROWID IN  ")
			.append(" ( ")
			.append(" SELECT MNLDUEDATEPRMTR.ROWID ")
			.append(" FROM ")
			.append(" TRN_MNL_DUE_DATE_PRMTR MNLDUEDATEPRMTR ")
			.append(" INNER JOIN TABLE_NAME ON  ")
			.append(" MNLDUEDATEPRMTR.POR_CD = TABLE_NAME.POR_CD ")
			.append(" AND MNLDUEDATEPRMTR.ORDR_TAKE_BASE_MNTH = substr(TABLE_NAME.ORDRTK_BASE_PRD, 0, 6) ")
			.append(" AND MNLDUEDATEPRMTR.PROD_MNTH = substr(TABLE_NAME.PRODUCTION_PERIOD, 0, 6) ")
			.append(" AND MNLDUEDATEPRMTR.OEI_BUYER_ID = TABLE_NAME.OEI_BUYER_ID ")
			.append(" AND MNLDUEDATEPRMTR.POT_CD = TABLE_NAME.POT_CD ")
			.append(" WHERE TABLE_NAME.POR_CD = :porCd  ")
			.append(" AND TABLE_NAME.IF_FILE_ID =  :FileID  ")
			.append(" AND SEQ_NO=:seqNo ")
			.append(" AND ( TABLE_NAME.ERR_CD IS NULL ")
			.append("  OR TABLE_NAME.DUE_DATE_ERR_CD IS NOT NULL ) ")
			.append(" ) ");

	public static final StringBuilder fetchDistinctOseiId = new StringBuilder()
			.append(" SELECT DISTINCT " + " TABLE_NAME.OSEI_ID "
					+ " , TABLE_NAME.NSC_EIM_ORDER_HRZN_FLAG "
					+ " , TABLE_NAME.ORDRTK_BASE_PRD "
					+ " ,TABLE_NAME.HORIZON ")
			.append(" , TABLE_NAME.PRODUCTION_PERIOD ")
			.append(" , TABLE_NAME.BUYER_GRP_CD ")
			.append(" , TABLE_NAME.CAR_SRS ")
			.append(" , TABLE_NAME.OCF_REGION_CD ")
			.append(" , TABLE_NAME.OCF_BUYER_GRP_CD ")
			.append(" FROM TABLE_NAME ");

	public static final StringBuilder fetchDistinctOseiIdWithPotCd = new StringBuilder()
			.append(" SELECT DISTINCT ").append(" TABLE_NAME.OSEI_ID ")
			.append(" , TABLE_NAME.ORDRTK_BASE_PRD ")
			.append(" , TABLE_NAME.ORDR_QTY ").append(" , TABLE_NAME.POT_CD ")
			.append(" , TABLE_NAME.PRODUCTION_PERIOD ")
			.append(" , TABLE_NAME.HORIZON ").append(" FROM TABLE_NAME ");

	public static final StringBuilder fetchDistinctOseiIdCnd = new StringBuilder()
			.append(" WHERE TABLE_NAME.ERR_CD IS NULL ")
			.append(" AND TABLE_NAME.POR_CD = :porCd ")
			.append(" AND TABLE_NAME.if_file_id=:FileID ")
			.append(" AND SEQ_NO=:seqNo ")
			.append(" AND TABLE_NAME.FRZN_TYPE_CD IS NULL ")
			.append(" AND TABLE_NAME.OSEI_ID IS NOT NULL ");

	public static final StringBuilder fetchFtrCdFrOseiId = new StringBuilder()
			.append(" SELECT FEAT_CD  ").append(" FROM  ")
			.append(" TRN_BUYER_MNTHLY_OCF_USG ");

	public static final StringBuilder updateFeaturUsgFrOseiId = new StringBuilder()

	.append(" UPDATE TRN_BUYER_MNTHLY_OCF_USG ")
			.append(" SET BUYER_OCF_USG_QTY = :byrUsgQty ")
			.append(" , UPDTD_DT = sysdate ").append(" , UPDTD_BY = :updtBy ");

	public static final StringBuilder updateByrUsgFrOseiId = new StringBuilder()
			.append(" UPDATE TRN_BUYER_MNTHLY_OCF_USG ")
			.append(" SET BUYER_OCF_USG_QTY = :byrUsgQty ")
			.append(" , UPDTD_DT = sysdate ").append(" , UPDTD_BY = :updtBy ");

	public static final StringBuilder fetchByOseiIdCnd = new StringBuilder()
			.append(" WHERE POR_CD = :porCd ")
			.append(" AND ORDR_TAKE_BASE_MNTH = :ordrTkBsMnth ")
			.append(" AND PROD_MNTH = :prdMnth ")
			.append(" AND OSEI_ID = :oseiId ");

	public static final StringBuilder fetchSumOrdrQty = new StringBuilder()
			.append(" SELECT SUM(ORDR_QTY)  ").append(" FROM TRN_MNTHLY_ORDR ");

	public static final StringBuilder grpByOseiIdCnd = new StringBuilder()
			.append(" WHERE POR_CD = :porCd ")
			.append(" AND ORDRTK_BASE_MNTH = :ordrTkBsMnth ")
			.append(" AND PROD_MNTH = :prdMnth ")
			.append(" AND OSEI_ID = :oseiId ");

	public static final StringBuilder grpByToFetchSumOrdrQty = new StringBuilder()

	.append(" GROUP BY POR_CD").append(" , ORDRTK_BASE_MNTH ")
			.append(" , PROD_MNTH ").append(" , OSEI_ID ");

	public static final StringBuilder updateByrGrpLmt = new StringBuilder()

	.append(" UPDATE TRN_BUYER_GRP_MNTHLY_OCF_LMT ").append(
			" SET BUYER_GRP_OCF_USG_QTY = :byrGrpUsgQty ");

	public static final StringBuilder byrGrpLmtCnd = new StringBuilder()
			.append(" WHERE POR_CD = :porCd ")
			.append(" AND ORDR_TAKE_BASE_MNTH = :ordrTkBsMnth ")
			.append(" AND PROD_MNTH = :prdMnth ")
			.append(" AND CAR_SRS = :carSrs ")
			.append(" AND BUYER_GRP_CD = :byrGrpCd ");
	public static final StringBuilder byrGrpLmtByfeatCdCnd = new StringBuilder()
			.append(" AND FEAT_CD = :featCd ");

	public static final StringBuilder fetchByrGrpLmt = new StringBuilder()
			.append(" SELECT *  ")
			.append(" FROM TRN_BUYER_GRP_MNTHLY_OCF_LMT ");

	public static final StringBuilder fetchSumFeatUsage = new StringBuilder()
			.append(" SELECT  ").append("  SUM(BUYER_OCF_USG_QTY) ")
			.append(" , FEAT_CD ").append(" , OCF_FRME_CD ")
			.append(" , FEAT_TYPE_CD ").append(" , POR_CD ")
			.append(" , ORDR_TAKE_BASE_MNTH ").append(" , PROD_MNTH ")
			.append(" , CAR_SRS ").append(" , BUYER_GRP_CD ")
			.append(" FROM TRN_BUYER_MNTHLY_OCF_USG ");

	public static final StringBuilder fetchSumFeatUsageCnd = new StringBuilder()
			.append(" WHERE POR_CD = :porCd ")
			.append(" AND ORDR_TAKE_BASE_MNTH = :ordrTkBsMnth ")
			.append(" AND PROD_MNTH = :prdMnth ")
			.append(" AND CAR_SRS = :carSrs ")
			.append(" AND BUYER_GRP_CD = :byrGrpCd ");

	public static final StringBuilder fetchSumFeatUsageGrpBy = new StringBuilder()
			.append(" GROUP BY  ").append(" POR_CD ")
			.append(" , ORDR_TAKE_BASE_MNTH ").append(" , PROD_MNTH ")
			.append(" , CAR_SRS ").append(" , BUYER_GRP_CD ")
			.append(" , FEAT_CD ").append(" , FEAT_TYPE_CD ")
			.append(" , OCF_FRME_CD ");

	public static final StringBuilder updateRgnlUsgLmtTrn = new StringBuilder()

	.append(" UPDATE TRN_REGIONAL_MNTHLY_OCF_LMT ").append(
			" SET REGIONAL_OCF_USG_QTY = :rgnlMnthlyOcfUsgQty ");

	public static final StringBuilder updateRgnlUsgLmtTrnCnd = new StringBuilder()
			.append(" WHERE POR_CD = :porCd ")
			.append(" AND ORDR_TAKE_BASE_MNTH = :ordrTkBsMnth ")
			.append(" AND PROD_MNTH = :prdMnth ")
			.append(" AND CAR_SRS = :carSrs ")
			.append(" AND OCF_REGION_CD = :ocfRgnCd ")
			.append(" AND OCF_BUYER_GRP_CD = :ocfByrGrpCd ");
	public static final StringBuilder updateRgnlUsgLmtTrnByfeatCdCnd = new StringBuilder()
			.append(" AND FEAT_CD = :featCd ");

	public static final StringBuilder fetchSumFeatUsageByRgnLvl = new StringBuilder()
			.append(" SELECT ")
			.append(" SUM(TRN_BUYER_GRP_MNTHLY_OCF_LMT.BUYER_GRP_OCF_USG_QTY) ")
			.append(", TRN_BUYER_GRP_MNTHLY_OCF_LMT.FEAT_CD ")
			.append(", TRN_BUYER_GRP_MNTHLY_OCF_LMT.OCF_FRME_CD ")
			.append(", TRN_BUYER_GRP_MNTHLY_OCF_LMT.FEAT_TYPE_CD ")
			.append(", TRN_BUYER_GRP_MNTHLY_OCF_LMT.POR_CD ")
			.append(", TRN_BUYER_GRP_MNTHLY_OCF_LMT.ORDR_TAKE_BASE_MNTH ")
			.append(", TRN_BUYER_GRP_MNTHLY_OCF_LMT.PROD_MNTH ")
			.append(", TRN_BUYER_GRP_MNTHLY_OCF_LMT.CAR_SRS ")
			.append(", OCFREGIONCD ")
			.append(", OCFBYRGRPCD ")
			.append(" FROM  ")
			.append(" ( ")
			.append(" SELECT ")
			.append(" DISTINCT TRN_BUYER_GRP_MNTHLY_OCF_LMT.FEAT_CD AS FEATCD ")
			.append(", MST_BUYER.OCF_REGION_CD AS OCFREGIONCD ")
			.append(", MST_BUYER.OCF_BUYER_GRP_CD  AS OCFBYRGRPCD ")
			.append(" FROM TRN_BUYER_GRP_MNTHLY_OCF_LMT ")
			.append(", MST_BUYER ")
			.append(", MST_POR ")
			.append(" WHERE  ")
			.append(" TRN_BUYER_GRP_MNTHLY_OCF_LMT.POR_CD = :porCd ")
			.append(" AND TRN_BUYER_GRP_MNTHLY_OCF_LMT.CAR_SRS = :carSrs ")
			.append(" AND TRN_BUYER_GRP_MNTHLY_OCF_LMT.ORDR_TAKE_BASE_MNTH = :ordrTkBsMnth ")
			.append(" AND TRN_BUYER_GRP_MNTHLY_OCF_LMT.PROD_MNTH = :prdMnth ")
			.append(" AND MST_POR.POR_CD =TRN_BUYER_GRP_MNTHLY_OCF_LMT.POR_CD ")
			.append(" AND MST_BUYER.PROD_REGION_CD = MST_POR.PROD_REGION_CD ")
			.append(" AND MST_BUYER.BUYER_GRP_CD = TRN_BUYER_GRP_MNTHLY_OCF_LMT.BUYER_GRP_CD ")
			.append(" AND MST_BUYER.OCF_REGION_CD = :ocfRgnCd ")
			.append(" AND MST_BUYER.OCF_BUYER_GRP_CD = :ocfByrGrpCd ")
			.append(" ) ").append(" ,  TRN_BUYER_GRP_MNTHLY_OCF_LMT ");

	public static final StringBuilder fetchSumFeatUsageByRgnLvlCnd = new StringBuilder()

			.append(" WHERE TRN_BUYER_GRP_MNTHLY_OCF_LMT.POR_CD = :porCd ")
			.append(" AND TRN_BUYER_GRP_MNTHLY_OCF_LMT.ORDR_TAKE_BASE_MNTH = :ordrTkBsMnth ")
			.append(" AND TRN_BUYER_GRP_MNTHLY_OCF_LMT.PROD_MNTH = :prdMnth ")
			.append(" AND TRN_BUYER_GRP_MNTHLY_OCF_LMT.CAR_SRS = :carSrs ")
			.append(" AND TRN_BUYER_GRP_MNTHLY_OCF_LMT.FEAT_CD = FEATCD ")
			.append(" AND OCFREGIONCD = :ocfRgnCd ")
			.append(" AND OCFBYRGRPCD = :ocfByrGrpCd ");

	public static final StringBuilder fetchSumFeatUsageByRgnLvlGrpBy = new StringBuilder()
			.append(" GROUP BY  ")
			.append(" TRN_BUYER_GRP_MNTHLY_OCF_LMT.FEAT_CD ")
			.append(" , OCFREGIONCD ").append(" , OCFBYRGRPCD ")
			.append(" , TRN_BUYER_GRP_MNTHLY_OCF_LMT.FEAT_TYPE_CD ")
			.append(" , TRN_BUYER_GRP_MNTHLY_OCF_LMT.OCF_FRME_CD ")
			.append(" , TRN_BUYER_GRP_MNTHLY_OCF_LMT.POR_CD ")
			.append(" , TRN_BUYER_GRP_MNTHLY_OCF_LMT.ORDR_TAKE_BASE_MNTH ")
			.append(" , TRN_BUYER_GRP_MNTHLY_OCF_LMT.PROD_MNTH ")
			.append(" , TRN_BUYER_GRP_MNTHLY_OCF_LMT.CAR_SRS ");

	public static final StringBuilder fetchOcfBrchRcrds = new StringBuilder()
			.append(" SELECT TRN_BUYER_GRP_MNTHLY_OCF_LMT.POR_CD ")
			.append(" , TRN_BUYER_GRP_MNTHLY_OCF_LMT.ORDR_TAKE_BASE_MNTH ")
			.append(" , TRN_BUYER_GRP_MNTHLY_OCF_LMT.PROD_MNTH ")
			.append(" , TRN_BUYER_GRP_MNTHLY_OCF_LMT.CAR_SRS ")
			.append(" , TRN_BUYER_GRP_MNTHLY_OCF_LMT.BUYER_GRP_CD ")
			.append(" , TRN_BUYER_GRP_MNTHLY_OCF_LMT.OCF_FRME_CD ")
			.append(" , MST_FEAT.FEAT_CD ")
			.append(" , MST_FEAT.FEAT_SHRT_DESC ")
			.append(" , MST_FEAT.FEAT_LNG_DESC ")
			.append(" , TRN_BUYER_GRP_MNTHLY_OCF_LMT.BUYER_GRP_OCF_LMT_QTY ")
			.append(" , TRN_BUYER_GRP_MNTHLY_OCF_LMT.BUYER_GRP_OCF_USG_QTY ")
			.append(" , (TRN_BUYER_GRP_MNTHLY_OCF_LMT.BUYER_GRP_OCF_LMT_QTY - TRN_BUYER_GRP_MNTHLY_OCF_LMT.BUYER_GRP_OCF_USG_QTY) as DIFFERENCE ")
			.append(" FROM TRN_BUYER_GRP_MNTHLY_OCF_LMT, MST_FEAT ")
			.append(" WHERE TRN_BUYER_GRP_MNTHLY_OCF_LMT.POR_CD = :porCd ")
			.append(" AND TRN_BUYER_GRP_MNTHLY_OCF_LMT.ORDR_TAKE_BASE_MNTH = :ordrTkBsMnth ")
			.append(" AND TRN_BUYER_GRP_MNTHLY_OCF_LMT.PROD_MNTH = :prdMnth ")
			.append(" AND TRN_BUYER_GRP_MNTHLY_OCF_LMT.CAR_SRS = :carSrs ")
			.append(" AND TRN_BUYER_GRP_MNTHLY_OCF_LMT.BUYER_GRP_CD = :byrGrpCd ")
			.append(" AND MST_FEAT.POR_CD = TRN_BUYER_GRP_MNTHLY_OCF_LMT.POR_CD ")
			.append(" AND TRN_BUYER_GRP_MNTHLY_OCF_LMT.CAR_SRS = MST_FEAT.CAR_SRS ")
			.append(" AND TRN_BUYER_GRP_MNTHLY_OCF_LMT.FEAT_CD = MST_FEAT.FEAT_CD ")
			.append(" AND TRN_BUYER_GRP_MNTHLY_OCF_LMT.FEAT_TYPE_CD = MST_FEAT.FEAT_TYPE_CD ")
			.append(" AND MST_FEAT.OCF_REGION_CD = :ocfRgnCd ")
			.append(" AND MST_FEAT.OCF_BUYER_GRP_CD = :ocfByrGrpCd ");

	public static final StringBuilder fetchItmChkErrRcrds = new StringBuilder()
			.append(" SELECT ")
			.append(" POR_CD ")
			.append(" , ORDRTK_BASE_PRD ")
			.append(" , PRODUCTION_PERIOD ")
			.append(" , CAR_SRS ")
			.append(" , BUYER_GRP_CD ")
			.append(" , BUYER_CD ")
			.append(" , SPEC_DESTN_CD ")
			.append(" , APPLD_MDL_CD ")
			.append(" , PACK_CD ")
			.append(" , EXT_CLR_CD ")
			.append(" , INT_CLR_CD ")
			.append(" , ADD_SPEC_CD ")
			.append(" , POT_CD ")
			.append(" , ORDR_QTY ")
			.append(" , DUE_DATE_FRM ")
			.append(" , DUE_DATE_TO ")
			.append(" , ADOPTDATE ")
			.append(" , ABOLISHDATE ")
			.append(" , ERRORMESSAGE ")
			.append(" , DATASKIPPED ")
			.append(" FROM TABLE_NAME  ")
			.append(" WHERE (ERR_CD IS NOT NULL OR DUE_DATE_ERR_CD IS NOT NULL) ")
			.append(" AND IF_FILE_ID = :FileID ").append(" AND SEQ_NO=:seqNo ")
			.append(" AND POR_CD = :porCd ");

	public static final StringBuilder insertIntoDevMnthlyOrdrIf = new StringBuilder()
			.append(" INSERT INTO DEV_MNTHLY_ORDR_IF ").append(" ( ")
			.append(" row_no ").append(" , if_FILE_ID ").append(" , seq_no ")
			.append(" , ORDR_QTY ").append(" ,POR_CD ").append(" , CAR_SRS ")
			.append(" , BUYER_CD ").append(" , APPLD_MDL_CD ")
			.append(" , PACK_CD ").append(" , SPEC_DESTN_CD ")
			.append(" , ADD_SPEC_CD ").append(" , EXT_CLR_CD ")
			.append(" , INT_CLR_CD ").append(" , ORDRTK_BASE_PRD_TYPE ")
			.append(" , ORDRTK_BASE_PRD ").append(" , PRODUCTION_PERIOD_TYPE ")
			.append(" , PRODUCTION_PERIOD ")
			.append(" , PRODUCTION_ORDER_STAGE_CD ").append(" , POT_CD ")
			.append(" , PROD_PLNT_CD ").append(" , LINE_CLASS ").append(" ) ")

			.append(" SELECT DEV_TRN_MNTHLY_ORDR_IF_ROWNO.nextval AS ROW_NO ")
			.append(" ,IF_FILE_ID ").append(" , seq_no ")
			.append(" ,  ORDR_QTY ").append(" ,POR_CD ").append(" , CAR_SRS ")
			.append(" , BUYER_CD ").append(" , APPLD_MDL_CD ")
			.append(" , PACK_CD ").append(" , SPEC_DESTN_CD ")
			.append(" , ADD_SPEC_CD ").append(" , EXT_CLR_CD ")
			.append(" , INT_CLR_CD ").append(" , ORDRTK_BASE_PRD_TYPE ")
			.append(" , ORDRTK_BASE_PRD ").append(" , PRODUCTION_PERIOD_TYPE ")
			.append(" , PRODUCTION_PERIOD ")
			.append(" , PRODUCTION_ORDER_STAGE_CD ").append(" , POT_CD ")
			.append(" , PROD_PLNT_CD ").append(" , LINE_CLASS FROM ")
			.append(" ( ").append(" SELECT ").append(" IF_FILE_ID ")
			.append("  , seq_no ").append(" , sum(ORDR_QTY) as ORDR_QTY ")
			.append(" ,POR_CD ").append(" , CAR_SRS ").append(" , BUYER_CD ")
			.append(" , APPLD_MDL_CD ").append(" , PACK_CD ")
			.append(" , SPEC_DESTN_CD ").append(" , ADD_SPEC_CD ")
			.append(" , EXT_CLR_CD ").append(" , INT_CLR_CD ")
			.append(" , ORDRTK_BASE_PRD_TYPE ").append(" , ORDRTK_BASE_PRD ")
			.append(" , PRODUCTION_PERIOD_TYPE ")
			.append(" , PRODUCTION_PERIOD ")
			.append(" , PRODUCTION_ORDER_STAGE_CD ").append(" , POT_CD ")
			.append(" , PROD_PLNT_CD ").append(" , LINE_CLASS ")

			.append(" FROM TABLE_NAME WHERE ")
			.append(" TABLE_NAME.IF_FILE_ID = :FileID ")
			.append(" AND SEQ_NO=:seqNo ")
			.append(" and TABLE_NAME.por_cd = :porCd ")

			.append(" group by ").append(" IF_FILE_ID ").append(" , seq_no ")
			.append(" ,POR_CD ").append(" , CAR_SRS ").append(" , BUYER_CD ")
			.append(" , APPLD_MDL_CD ").append(" , PACK_CD ")
			.append(" , SPEC_DESTN_CD ").append(" , ADD_SPEC_CD ")
			.append(" , EXT_CLR_CD ").append(" , INT_CLR_CD ")
			.append(" , ORDRTK_BASE_PRD_TYPE ").append(" , ORDRTK_BASE_PRD ")
			.append(" , PRODUCTION_PERIOD_TYPE ")
			.append(" , PRODUCTION_PERIOD ")
			.append(" , PRODUCTION_ORDER_STAGE_CD ").append(" , POT_CD ")
			.append(" , PROD_PLNT_CD ").append(" , LINE_CLASS ").append(") ");

	public static final StringBuilder fetchOseiIdFrInVldPrdMnth = new StringBuilder()
			.append(" SELECT DISTINCT TABLE_NAME.OSEI_ID  ")
			.append(" FROM TABLE_NAME WHERE ")
			.append(" TABLE_NAME.POR_CD = :porCd ")
			.append(" AND TABLE_NAME.BUYER_GRP_CD = :byrGrpCd  ")
			.append(" AND TABLE_NAME.IF_FILE_ID = :FileID ")
			.append(" AND TABLE_NAME.ORDRTK_BASE_PRD = :ordrTkBsMnth   ")
			.append(" AND TABLE_NAME.PRODUCTION_PERIOD = :prdMnth ")
			.append(" AND TABLE_NAME.CAR_SRS = :carSrs ")
			.append(" AND TABLE_NAME.ERR_CD IS NULL ")
			.append(" AND SEQ_NO=:seqNo ")
			.append(" AND TABLE_NAME.OSEI_ID IS NOT NULL ");

	public static final StringBuilder fetchAdoptAbolishDatesByOseiId = new StringBuilder()
			.append(" SELECT * FROM MST_OSEI_DTL WHERE OSEI_ID = :oseiId ");

	public static final StringBuilder fetchWkStartDate = new StringBuilder()
			.append(" SELECT wk_strt_date  ").append(" FROM  ")
			.append(" MST_WK_NO_CLNDR ").append(" WHERE POR_CD = :porCd ")
			.append(" AND PROD_MNTH = :prdMnth ")
			.append(" AND PROD_WK_NO = :wkNo ");

	public static final StringBuilder fetchWkNoCalendarData = new StringBuilder()
			.append(" SELECT PROD_MNTH,prod_wk_no,wk_strt_date  ")
			.append(" FROM  ")
			.append(" MST_WK_NO_CLNDR ")
			.append(" WHERE POR_CD = :porCd ")
			.append(" and wk_strt_date <= :yyymmddDate  and wk_end_date >= :yyymmddDate ");

	public static final StringBuilder fetchValue = new StringBuilder()

	.append(" SELECT m FROM MstPrmtr m ").append(" WHERE ")
			.append(" id.prmtrCd = :prmtrCd ").append(" AND id.key1 = :key1 ");

	public static final StringBuilder fetchValue1 = new StringBuilder()

	.append(" SELECT VAL1 FROM MST_PRMTR ").append(" WHERE ")
			.append(" PRMTR_CD = :prmtrCd ").append(" AND KEY1 = :key1 ")
			.append(" AND KEY2 = :key2 ");

	public static final StringBuilder fetchValue2 = new StringBuilder()

	.append(" SELECT VAL2 FROM MST_PRMTR ").append(" WHERE ")
			.append(" PRMTR_CD = :prmtrCd ").append(" AND KEY1 = :key1 ")
			.append(" AND KEY2 = :key2 ");

	public static final StringBuilder fetchValue1Value2 = new StringBuilder()

	.append(" SELECT VAL1,VAL2 FROM MST_PRMTR ").append(" WHERE ")
			.append(" PRMTR_CD = :prmtrCd ").append(" AND KEY1 = :key1 ")
			.append(" AND KEY2 = :key2 ");

	public static final StringBuilder insertMnthlyOrdrErrIf = new StringBuilder()
			.append(" INSERT INTO TRN_MNTHLY_ORDR_ERR_IF   ( ")
			.append(" ROW_NO  ")
			.append(" , IF_FILE_ID  ")
			.append(" , SEQ_NO ")
			.append(" , POR ")
			.append(" , CAR_SRS ")
			.append(" , BUYER_CD ")
			.append(" , APPLD_MDL_CD ")
			.append(" , PACK_CD ")
			.append(" , SPEC_DESTN_CD  ")
			.append(" , ADD_SPEC_CD ")
			.append(" , EXT_CLR_CD ")
			.append(" , INT_CLR_CD ")
			.append(" ,ORDRTK_PRD_TYPE ")
			.append(" ,ORDRTK_PRD ")
			.append(" ,PROD_PRD_TYPE ")
			.append(" ,PROD_PRD ")
			.append(" ,POT_CD ")
			.append(" ,QTY ")
			.append(" ,PROD_ORDR_NO ")
			.append(" ,ERR_CD ")
			.append(" , ADPT_DT ")
			.append(" , ABLSH_DT ")
			.append(" , ERR_MSG ")
			.append(" , ABLSH_PRD ")
			.append(" , ABLSH_MNTH ")
			.append(" , ADPT_PRD ")

			.append(" ) ")

			.append(" SELECT ")
			.append(" trn_mnthly_ordr_ERR_if_ROWNO.nextval AS ROW_NO ")
			.append(" , IF_FILE_ID ")
			.append(" , SEQ_NO ")
			.append(" , POR_CD as POR ")
			.append(" , CAR_SRS ")
			.append(" , BUYER_CD ")
			.append(" , APPLD_MDL_CD ")
			.append(" , PACK_CD ")
			.append(" , SPEC_DESTN_CD ")
			.append(" , ADD_SPEC_CD ")
			.append(" , EXT_CLR_CD ")
			.append(" , INT_CLR_CD ")
			.append(" , ORDRTK_BASE_PRD_TYPE as ORDRTK_PRD_TYPE ")
			.append(" ,ORDRTK_BASE_PRD as ORDRTK_PRD ")
			.append(" ,PRODUCTION_PERIOD_TYPE as PROD_PRD_TYPE ")
			.append(" ,PRODUCTION_PERIOD as PROD_PRD ")
			.append(" ,POT_CD ")
			.append(" ,ORDR_QTY as  QTY ")
			.append(" ,PROD_ORDR_NO ")
			.append(" ,ERR_CD ")
			.append(" , ADOPTDATE as ADPT_DT ")
			.append(" , ABOLISHDATE as ABLSH_DT ")
			.append(" , ERRORMESSAGE as ERR_MSG ")
			.append(" , ABOLISHPERIOD as ABLSH_PRD ")
			.append(" , ABOLISHMNTH as ABLSH_MNTH ")
			.append(" , ADOPTPERIOD as ADPT_PRD ")
			.append(" FROM TABLE_NAME  WHERE TABLE_NAME.POR_CD = :porCd ")
			.append(" AND TABLE_NAME.IF_FILE_ID = :FileID  ")
			.append(" AND SEQ_NO=:seqNo ")
			.append(" AND (TABLE_NAME.ERR_CD IS NOT NULL OR TABLE_NAME.DUE_DATE_ERR_CD IS NOT NULL) ");

	public static final String trn_mnthly_ordr_ERR_if_ROWNO = "select trn_mnthly_ordr_ERR_if_ROWNO.nextval from dual";

	/** Constant selectMstExClr. */
	public static final StringBuilder selectMnthlyBtchStts = new StringBuilder()
			.append(" select m from MnthlyBatchProcessStt m WHERE m.id.porCd =:porCd AND m.id.ordrtkBaseMnth=:ordrTkeBsMnth ")
			.append(" m.id.batchId =:batchId AND m.id.seqId =:seqNo ");

	public static final StringBuilder fetchExNoandSpecDtls = new StringBuilder()

	.append(" select distinct ").append(" mst_buyer.ocf_region_cd ,")
			.append(" MST_BUYER_GRP.MC_REGION_CD ,")
			.append(" mst_buyer.buyer_grp_cd ,")
			.append(" mst_buyer.buyer_cd ,").append(" mst_oei_spec.car_srs,")
			.append(" mst_oei_spec.prod_fmy_cd ,")
			.append(" mst_oei_spec.appld_mdl_cd,")
			.append(" mst_oei_spec.pck_cd,")
			.append(" mst_oei_spec.spec_destn_cd,")
			.append(" mst_osei.ext_clr_cd,").append(" mst_osei.int_clr_cd,")
			.append(" mst_ex_no.ex_no , ").append(" mst_ex_no.pot_cd  ");

	public static final StringBuilder fetchMnthlyProdOrdrDtls = new StringBuilder()

	.append(" , trn_mnthly_prod_ordr.ordr_qty ");

	public static final StringBuilder fetchExNoandSpecDtlsJoin = new StringBuilder()
			.append(" from mst_oei_buyer ")

			.append(" inner join mst_osei on ")
			.append(" mst_oei_buyer.oei_buyer_id = mst_osei.oei_buyer_id ")
			.append(" and mst_oei_buyer.por_cd = mst_osei.por_cd ")

			.append(" inner join mst_oei_spec on ")
			.append(" mst_oei_spec.oei_spec_id = mst_oei_buyer.oei_spec_id ")
			.append(" and mst_oei_spec.por_cd = mst_oei_buyer.por_cd ")

			.append(" inner join mst_por on ")
			.append(" mst_por.por_cd = mst_oei_buyer.por_cd ")

			.append(" inner join mst_buyer on    ")
			.append(" mst_oei_buyer.buyer_cd = mst_buyer.buyer_cd ")
			.append(" and mst_buyer.PROD_REGION_CD = mst_por.PROD_REGION_CD ")

			.append(" inner join MST_BUYER_GRP on    ")
			.append(" MST_BUYER_GRP.BUYER_GRP_CD = mst_buyer.BUYER_GRP_CD ")

			.append(" inner join  mst_ex_no on  ")
			.append(" mst_ex_no.oei_buyer_id = mst_osei.oei_buyer_id ")
			.append(" and mst_ex_no.por_cd = mst_osei.por_cd ");

	public static final StringBuilder fetchMnthlyProdOrdrDtlsJoin = new StringBuilder()
			.append(" inner join trn_mnthly_prod_ordr on    ")
			.append(" mst_osei.por_cd = trn_mnthly_prod_ordr.por_cd ")
			.append(" and mst_osei.osei_id = trn_mnthly_prod_ordr.osei_id ");

	public static final StringBuilder fetchExNoandSpecDtlsCnd = new StringBuilder()

	.append(" where ").append(" mst_osei.oei_buyer_id = :oeiByrId ")
			.append(" and mst_ex_no.pot_cd = :potCd ")
			.append(" and mst_ex_no.por_cd = :porCd ")
			.append(" and mst_ex_no.prod_mnth = :prdMnth ")
			.append(" and mst_osei.por_cd = :porCd ");

	public static final StringBuilder fetchMnthlyProdOrdrDtlsCnd = new StringBuilder()
			.append(" and trn_mnthly_prod_ordr.ordrtk_base_mnth = :ordrTkBsMnth ")
			.append(" and trn_mnthly_prod_ordr.prod_mnth = :prdMnth ")
			.append(" and trn_mnthly_prod_ordr.por_cd = :porCd ")
			.append(" and trn_mnthly_prod_ordr.pot_cd = :potCd ");

	public static final StringBuilder updateCurrDateCmnFileHdr = new StringBuilder()
			.append("UPDATE CMN_FILE_HDR SET STTS = :STATUS , UPDTD_DT = :UPDTD_DT ")
			.append(" WHERE IF_FILE_ID = :IF_FILE_ID AND SEQ_NO =:SEQ_NO");

	/** Query to extract data from MST_FEAT table. */
	public static final StringBuilder ExtractFeatureDtlsQuery = new StringBuilder()
			.append("SELECT FEAT_CD, FEAT_ADPT_DATE, FEAT_ABLSH_DATE FROM MST_FEAT WHERE POR_CD=:POR_CODE AND CAR_SRS=:carSrs AND OCF_REGION_CD =:ocfRgnCd "
					+ "AND OCF_BUYER_GRP_CD=:byrGrpCd AND OCF_FRME_CD=:ocfFrameCD AND FEAT_SHRT_DESC=:shrtDesc AND FEAT_TYPE_CD not in (:featTypeCd)");
	/** Constant minOrdrTkeMnthQuery. */
	public static final StringBuilder OrdrTkeMnthQuery = new StringBuilder()
			.append("select min(trim(m.col2)) from CmnInterfaceData m  where m.cmnFileHdr.id.ifFileId= :interfaceId  ")
			.append("and m.cmnFileHdr.id.seqNo=(Select min(cm.id.seqNo) from CmnFileHdr cm where cm.cmnInterfaceMst.ifFileId= :interfaceId  ")
			.append("and cm.stts=:interfaceStatus  )");

	public static final StringBuilder getPotCdFrmMstPrm = new StringBuilder()
			.append(" SELECT trim(m.val1) FROM Mst_Prmtr m where  trim(m.key1) = :porCd and   trim(m.prmtr_cd) =  :potCd ");

	/** Query to extract data from MST_FEAT table. */
	public static final StringBuilder ExtractWeekNoDtls = new StringBuilder()
			.append("SELECT * from MST_WK_NO_CLNDR where POR_CD = :porCd and PROD_MNTH in (:prdMnth) ");

	public static final StringBuilder I000042prdMnthQuery = new StringBuilder()
			.append("select distinct(substr((m.col1),0,6)) from CmnInterfaceData m  where m.cmnFileHdr.id.ifFileId= :interfaceId  ")
			.append("and m.cmnFileHdr.id.seqNo=(Select min(cm.id.seqNo) from CmnFileHdr cm where cm.cmnInterfaceMst.ifFileId= :interfaceId  ")
			.append("and cm.stts=:interfaceStatus  )");

	public static final String TRN_MNTHLY_PROD_SCHDL_SEQ_ID = "select MNTHLY_PROD_SCHDL_IF_SEQ_ID.nextval from dual";

	public static final StringBuilder fetchInVldPorCdR000020 = new StringBuilder()
			.append(" SELECT TABLE_NAME.SEQ_ID FROM TABLE_NAME ")
			.append(" LEFT OUTER JOIN MST_POR ON TABLE_NAME.POR_CD = MST_POR.POR_CD ")
			.append(" WHERE  MST_POR.POR_CD IS NULL ");

	public static final StringBuilder andPrtTypFlgCdNull = new StringBuilder()
			.append(" AND trim(TABLE_NAME.PRTYPE_FLAG)  is null  ");

	public static final StringBuilder updateErrorCdR20 = new StringBuilder()
			.append(" UPDATE TABLE_NAME SET ERR_STTS_CD = :errorCd, UPDTD_BY = 'R000020', UPDTD_DT =  sysdate ")
			.append(" , ERRORMESSAGE = :errorMsg ");

	public static final StringBuilder updateInvldPorCdCndR20 = new StringBuilder()
			.append(" WHERE SEQ_ID  in :seqNo ");

	public static final StringBuilder fetchSeqNo = new StringBuilder()
			.append(" select TABLE_NAME.SEQ_ID  ").append(" from  ")
			.append(" TABLE_NAME  ");

	public static final StringBuilder whrOrdrTkBsMnthCndtn = new StringBuilder()
			.append(" AND  TABLE_NAME.ORDRTK_BASE_MNTH = :ordrTkBsMnth AND trim(TABLE_NAME.PRTYPE_FLAG)  is null  ");

	public static final StringBuilder whrOcfRgnCdCndtn = new StringBuilder()
			.append(" AND  TABLE_NAME.OCF_REGION_CD = :ocfRgnCd ");

	public static final StringBuilder I42InsrtQry = new StringBuilder()
			.append(" INSERT INTO TRN_MNTH_PROD_SHDL_IF ")
			.append(" (POR_CD,ORDRTK_BASE_MNTH,PROD_MNTH,SEQ_ID,POT_CD,OFFLN_PLAN_DATE,PROD_WK_NO,PROD_DAY_NO,ORDR_QTY,PROD_PLNT_CD,LINE_CLASS,EX_NO,PROD_MTHD_CD,FRZN_TYPE_CD,SLS_NOTE_NO,TYRE_MKR_CD,DEALER_LST,OWNR_MNL,WRNTY_BKLT,BDY_PRTCTN_CD,OCF_REGION_CD,PROD_ORDR_NO,VIN_NO,WK_NO_OF_YEAR,FXD_SYMBL,PRTYPE_FLAG,INTRNL_OR_TRD_FLAG,CRTD_BY,CRTD_DT,UPDTD_BY,UPDTD_DT,PROD_FMLY_CD,CAR_SRS,BUYER_CD,APPLD_MDL_CD,PACK_CD,SPEC_DESTN_CD,ADD_SPEC_CD,EXT_CLR_CD,INT_CLR_CD,ERR_STTS_CD,LOCAL_PROD_ORDR_NO,ERRORMESSAGE,OSEI_ID) ")
			.append(" values  (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) ");
}
