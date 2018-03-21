/*
 * System Name     :Post Dragon 
 * Sub system Name :Interface
 * Function ID     :PST-DRG-I000026
 * Module          :Ordering
 * Process Outline :Processor for I000026 Interface	
 *
 * <Revision History>
 * Date       Name(Company Name)             Description 
 * ---------- ------------------------------ ---------------------
 * 29-06-2015  	  z014029(RNTBCI)               New Creation
 *
 */
package com.nissangroups.pd.i000026.processor;

import static com.nissangroups.pd.util.PDConstants.BATCH_CONFIG_CLASSPATH;
import static com.nissangroups.pd.util.PDConstants.DELIMITE_HYPHEN;
import static com.nissangroups.pd.util.PDConstants.DOLLAR;
import static com.nissangroups.pd.util.PDConstants.FAILED;
import static com.nissangroups.pd.util.PDConstants.FILE_EXT_TSV;
import static com.nissangroups.pd.util.PDConstants.I000026_PROCESSOR;
import static com.nissangroups.pd.util.PDConstants.INSIDE_METHOD;
import static com.nissangroups.pd.util.PDConstants.OUTSIDE_METHOD;
import static com.nissangroups.pd.util.PDConstants.PERSISTENCE_NAME;
import static com.nissangroups.pd.util.PDConstants.PRMTR_FILE_NAME;
import static com.nissangroups.pd.util.PDConstants.R000036_REPORT_PATH;
import static com.nissangroups.pd.util.PDConstants.READ_COUNT;
import static com.nissangroups.pd.util.PDConstants.READ_SKIPPED_COUNT;
import static com.nissangroups.pd.util.PDConstants.REPORT_SUFFIX_R36;
import static com.nissangroups.pd.util.PDConstants.STAR;
import static com.nissangroups.pd.util.PDConstants.STEP_ID;
import static com.nissangroups.pd.util.PDConstants.WRITE_COUNT;
import static com.nissangroups.pd.util.PDConstants.WRITE_SKIPPED_COUNT;
import static com.nissangroups.pd.util.PDMessageConsants.M00003;
import static com.nissangroups.pd.util.PDMessageConsants.M00043;
import static com.nissangroups.pd.util.PDMessageConsants.M00076;
import static com.nissangroups.pd.util.PDMessageConsants.M00113;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.AfterStep;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import com.nissangroups.pd.exception.PdApplicationException;
import com.nissangroups.pd.i000026.util.I000026QueryConstants;
import com.nissangroups.pd.model.CmnInterfaceData;
import com.nissangroups.pd.model.TrnMnthlyOcf;
import com.nissangroups.pd.model.TrnMnthlyOcfIf;
import com.nissangroups.pd.r000036.dao.R000036ReportDao;
import com.nissangroups.pd.util.CommonUtil;
import com.nissangroups.pd.util.IfCommonUtil;
import com.nissangroups.pd.util.PDConstants;
import com.nissangroups.pd.util.PDMessageConsants;

/**
 * The Class MnthlyOcfIfTrnProcessor.
 * 
 * @author z014029
 * @param CmnInterfaceData
 * @param TrnMnthlyOcfIf
 */

@Configuration
@PropertySource(BATCH_CONFIG_CLASSPATH)
@Component(I000026_PROCESSOR)
public class MnthlyOcfIfTrnProcessor implements
		ItemProcessor<CmnInterfaceData, TrnMnthlyOcfIf> {

	/** Variable interface por cd. */
	private static String interfacePorCd = null;

	/** Variable interface id. */
	private static String interfaceId = null;

	/** Variable entity manager. */
	@PersistenceContext(name = PERSISTENCE_NAME)
	private EntityManager entityManager;

	/** Variable error list. */
	@Autowired(required = false)
	private R000036ReportDao R000036ReportDao;

	/** Variable environment. */
	@Autowired(required = false)
	Environment environment;

	/** Variable job paramPor. */
	String jobParamPor = null;

	/** Constant LOG. */
	private static final Log LOG = LogFactory
			.getLog(MnthlyOcfIfTrnProcessor.class.getName());

	@Autowired(required = false)
	IfCommonUtil commonutility;

	/** Variable date. */
	Date date = new Date();

	/** Variable create date. */
	Timestamp createDate = new Timestamp(date.getTime());

	String carGrp = null;
	String prodPlntcd = null;
	String ordrTkBsMnth = null;
	String crseries = null;
	String frmesrtcd =null;
	String byrgroup =null;
	String idnCD =null;
	String ocfRginCD =null;
	String ocfFrmeCd =null;
	
	Boolean crNullChk = false;
	Boolean insrtTrn = false;
	Boolean dtlNullChk = false;
	List<Object[]> validRec = null;
	boolean dltFlg = false;
	
	/*@Autowired(required = false)
	private CommonRepository commonRepository;*/
	
	
	/**
	 * Before step.
	 * 
	 * @param stepExecution
	 *            the step execution
	 */
	/*
	 * Before Step process Process No-P0002 Get the corresponding Variables from
	 * the PDConstants
	 */
	@BeforeStep
	public void beforeStep(StepExecution stepExecution) {
		LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR);

		String errorPath = environment.getProperty(R000036_REPORT_PATH);
		DateFormat dateFormat = new SimpleDateFormat(
				PDConstants.DATE_TIME_FORMAT);
		String fileName = errorPath.trim() + REPORT_SUFFIX_R36
				+ dateFormat.format(new Date()) + FILE_EXT_TSV;

		JobExecution jobExecution = stepExecution.getJobExecution();
		jobParamPor = jobExecution.getJobParameters().getString(
				PDConstants.BATCH_POR_CODE);
		interfacePorCd = jobParamPor;
		interfaceId = jobExecution.getJobParameters().getString(
				PDConstants.INTERFACE_FILE_ID);

		ExecutionContext stepContext = jobExecution.getExecutionContext();
		stepContext.put(PRMTR_FILE_NAME, fileName);
		LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);
	}

	/**
	 * Process.
	 * 
	 * @author z014029 This method processes the records read from
	 *         COMMON_INTERFACE_DATA P0002-Deletion of data from
	 *         MONTHLY_OCF_IF_TRN P0003-Insertion of data into
	 *         MONTHLY_OCF_IF_TRN
	 * @param commonInterfaceData
	 *            the common interface data
	 * @return TrnMnthlyOcf
	 * @throws Exception
	 *             the exception
	 */
	@Override
	public TrnMnthlyOcfIf process(CmnInterfaceData cmnInterfaceData)
			throws Exception {

		LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR);

		/** Build TrnMonthlyOcfIf object */

		int updateCount = dltInsrtMnthlyIf(cmnInterfaceData);
		List<Object[]> stgQueryRslt = extrctMnthlyOCFIf(cmnInterfaceData);
		if (stgQueryRslt == null || stgQueryRslt.size() == 0) {
			// move to process 6
			LOG.info(DOLLAR + "No Record!" + DOLLAR);
		} else {
			insrtDataMnthlyOCFTrn(cmnInterfaceData, stgQueryRslt);
		}
		LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);
		return null;
	}

	private int dltInsrtMnthlyIf(CmnInterfaceData cmnInterfaceData)
			throws PdApplicationException {
		LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR);
		Date date = new Date();

		/** Variable create date. */
		Timestamp createDate = new Timestamp(date.getTime());
		
		try {
			SimpleDateFormat dtFrmt = new SimpleDateFormat(
					PDConstants.DATE_FORMAT_MONTH);
			Query ocfIfQuery = entityManager
					.createNativeQuery(I000026QueryConstants.Query_Mnthly_OCF_IF_Insrt
							.toString());
			ocfIfQuery.setParameter(PDConstants.BATCH_POR_CODE, interfacePorCd);
			prodPlntcd = cmnInterfaceData.getCol1();

			ocfIfQuery.setParameter(PDConstants.PRMTR_PRODUCTION_PLANT_CD,
					prodPlntcd);
			ordrTkBsMnth = cmnInterfaceData.getCol2();
			ocfIfQuery.setParameter(PDConstants.PRMTR_ORDR_TK_BS_MNTH,
					ordrTkBsMnth);
			 crseries = cmnInterfaceData.getCol3();
			ocfIfQuery.setParameter(PDConstants.PRMTR_CAR_SRS,
					crseries);
			ocfFrmeCd = cmnInterfaceData.getCol4();
			ocfIfQuery.setParameter(PDConstants.PRMTR_OCF_FRAME_CD,
					ocfFrmeCd);
			idnCD = cmnInterfaceData.getCol5();
			ocfIfQuery.setParameter(PDConstants.PRMTR_SHRT_DESC,
					idnCD);
			byrgroup =cmnInterfaceData.getCol6();
			ocfIfQuery.setParameter(PDConstants.PRMTR_BYR_GRP_CD,
					byrgroup);
			carGrp =cmnInterfaceData.getCol7();
			ocfIfQuery.setParameter(PDConstants.PRMTR_CR_GRP,
					carGrp);
			ocfIfQuery.setParameter(PDConstants.PRMTR_OCF_MAX_QTY,
					(String)cmnInterfaceData.getCol8());
			ocfIfQuery.setParameter(PDConstants.PRMTR_OCF_STND_QTY,
					(String)cmnInterfaceData.getCol9());
			frmesrtcd = cmnInterfaceData.getCol10();
			ocfIfQuery.setParameter(PDConstants.PRMTR_OCF_FRM_SRT_CD,
					frmesrtcd);
			ocfIfQuery.setParameter(PDConstants.PRMTR_TERMINAL_ID,
					cmnInterfaceData.getCol11());
			ocfIfQuery.setParameter(
					PDConstants.PRMTR_MAINT_DT,
					new Timestamp(new SimpleDateFormat(
							PDConstants.DATE_FORMAT_MONTH).parse(
							cmnInterfaceData.getCol12()).getTime()));
			if (cmnInterfaceData.getCol13() != null) {
				ocfIfQuery
						.setParameter(
								PDConstants.PRMTR_MAINT_UPDTD_DT,
								(cmnInterfaceData.getCol13() == null) ? null
										: new Timestamp(dtFrmt.parse(
												cmnInterfaceData.getCol13())
												.getTime()));
			} else {
				ocfIfQuery.setParameter(PDConstants.PRMTR_MAINT_UPDTD_DT, "");
			}
			ocfRginCD =cmnInterfaceData.getCol14();
			ocfIfQuery.setParameter(PDConstants.PRMTR_OCFRGNCD,
					ocfRginCD);
			ocfIfQuery.setParameter(PDConstants.PRMTR_USR_ID,
					cmnInterfaceData.getCol15());
			ocfIfQuery.setParameter(PDConstants.PRMTR_NOTES,
					cmnInterfaceData.getCol16());

			LOG.info("I000026 Processor" + DOLLAR + OUTSIDE_METHOD + DOLLAR);
			return ocfIfQuery.executeUpdate();
		} catch (Exception e) {
			commonutility.setRemarks(M00043);
			LOG.error(M00043);
			LOG.error(e);
			throw new PdApplicationException(e.getMessage());
		}
	}

	private List<Object[]> extrctMnthlyOCFIf(CmnInterfaceData cmnInterfaceData)
			throws ParseException {
		LOG.info("I000026 Processor" + DOLLAR + INSIDE_METHOD + DOLLAR);
		List<Object[]> stgQueryRslt = null;

		Query stgQuery = entityManager
				.createNativeQuery(I000026QueryConstants.ExtractStageTable
						.toString());

		stgQuery.setParameter(PDConstants.BATCH_POR_CODE, interfacePorCd);
		stgQuery.setParameter(PDConstants.PRMTR_ORDR_TK_BS_MNTH,
				cmnInterfaceData.getCol2());
		stgQueryRslt = stgQuery.getResultList();
		LOG.info("I000026 Processor" + DOLLAR + OUTSIDE_METHOD + DOLLAR);
		return stgQueryRslt;
	}

	private List<Object[]> extrctCrGrp(CmnInterfaceData cmnInterfaceData,
			Object[] item) {
		LOG.info("I000026 Processor" + DOLLAR + INSIDE_METHOD + DOLLAR);
		List<Object[]> crGrpQryRslt = null;
		try {
			Query crGrpQry = entityManager
					.createNativeQuery(I000026QueryConstants.CarGroupQuery
							.toString());

			crGrpQry.setParameter(PDConstants.BATCH_POR_CODE, interfacePorCd);
			crGrpQry.setParameter(PDConstants.PRMTR_CAR_SRS, item[3]);
			crGrpQryRslt = crGrpQry.getResultList();

			if (crGrpQryRslt.size() > 0) {
				carGrp = (String) crGrpQryRslt.get(0)[0];
			} else {
				String errMeg = "M00003 :  I000026, POR CAR SERIES MST No Data Found.";
				throw new PdApplicationException(errMeg);
			}
			if (carGrp == null) {
				carGrp = cmnInterfaceData.getCol3().substring(0, 4);
			}
		} catch (Exception e) {
			LOG.error(e);
			CommonUtil.logMessage(PDMessageConsants.M00003,
					PDConstants.CONSTANT_V3, new String[] {
							PDConstants.INTERFACE_26_ID,
							PDConstants.INSERTED,
							PDConstants.POR_CAR_SERIES_MST });
			//CommonUtil.stopBatch();
			return  null;
		}
		LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);
		return crGrpQryRslt;
	}

	private List<String> calPrdMnth(Object[] item) throws ParseException {
		LOG.info("I000026 Processor" + DOLLAR + INSIDE_METHOD + DOLLAR);
		Calendar prdDate = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat(
				PDConstants.DATE_FORMAT_MONTH);
		prdDate.setTime(sdf.parse(((String) item[2])));
		List<String> prdMnths = new ArrayList<>();
		prdMnths.add(sdf.format(prdDate.getTime()));
		for (int month = 1; month < 24; month++) {
			prdDate.add(Calendar.MONTH, 1);
			prdMnths.add(sdf.format(prdDate.getTime()));
		}
		LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);
		return prdMnths;

	}

	private List<Object[]> extFeatDtls(Object[] item) {
		List<Object[]> dtlsQryRslt = null;
		LOG.info("I000026 Processor" + DOLLAR + INSIDE_METHOD + DOLLAR);
		try {
			Query dtlsQry = entityManager
					.createNativeQuery(I000026QueryConstants.FeatureDtlsQuery
							.toString());
			List<String> featTypCD = Arrays.asList("10", "20", "40", "50",
					"70", "80");
			dtlsQry.setParameter(PDConstants.BATCH_POR_CODE, interfacePorCd);
			dtlsQry.setParameter(PDConstants.PRMTR_CAR_SRS, item[3]);
			dtlsQry.setParameter(PDConstants.PRMTR_OCFRGNCD, item[14]);
			dtlsQry.setParameter(PDConstants.PRMTR_BYR_GRP_CD, item[6]);
			dtlsQry.setParameter(PDConstants.PRMTR_OCF_FRAME_CD, item[4]);
			dtlsQry.setParameter(PDConstants.PRMTR_FEAT_SHRT_DESC, item[5]);
			dtlsQry.setParameter(PDConstants.PRMTR_FEAT_TYPE_CD, featTypCD);
			dtlsQryRslt = dtlsQry.getResultList();
			
		} catch (Exception e) {
			 LOG.error(e);
			CommonUtil.logMessage(PDMessageConsants.M00003,
					PDConstants.CONSTANT_V3, new String[] {
							PDConstants.INTERFACE_26_ID, PDConstants.INSERTED,
							PDConstants.FEATURE_MST });
			CommonUtil.stopBatch();
		}
		LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);
		return dtlsQryRslt;
	}

	private void insrtDataMnthlyOCFTrn(CmnInterfaceData cmnInterfaceData,
			List<Object[]> stgQueryRslt) throws ParseException {
		LOG.info("I000026 Processor" + DOLLAR + INSIDE_METHOD + DOLLAR);
		validRec = new ArrayList<>();
		Object[] featDetails=null;
		
		boolean insertFlag = false;
		for (Object[] item : stgQueryRslt) {
			List<Object[]> crGrpQryRslt = extrctCrGrp(cmnInterfaceData, item);
			if(crGrpQryRslt != null){
			List<String> prodMnths = null;
			
			prodMnths = calPrdMnth(item);

			/**
			 * Process No - P0005.4 extracts the records from MST_FEAT
			 */
			List<Object[]> dtlsQryRslt = extFeatDtls(item);
			if (dtlsQryRslt == null || dtlsQryRslt.size() == 0) {
					String maxQty = (stgQueryRslt.get(0)[8])
							.toString();
					int i = 0, j = 0;
					String oneToSeven = null;
					for (; maxQty.length() >= (i + 7) && i < 168; i += 7, j++) {
						oneToSeven = maxQty.substring(i, i + 7);						
						writeDataToErrorReport(cmnInterfaceData, prodMnths.get(j),"" ,oneToSeven ,
								PDConstants.REPORT_R36_ERROR_MESSAGE1);
					}
					if(dtlsQryRslt !=null){
						dtlNullChk = true;	
				}
				String errMsg = "M00003 :  I000026, No Data Found.";				
				LOG.error(errMsg);
			}

			/* (i)P0005.4 -FEATURE CD is not null value*/
			String maxAdDt = null;
			String minAbDt = null;

			if (dtlsQryRslt != null) {

				for (Object[] featCD : dtlsQryRslt) {
					if (featCD[0] != null) {
						featDetails =featCD;
						insertFlag = true;
						String two = "2";
						
						String maxQty = (stgQueryRslt.get(0)[8])
								.toString();
						String stdQty = (stgQueryRslt.get(0)[9])
								.toString();
						int i = 0, j = 0;
						String oneToSeven = null;
						String productionMnth = ordrTkBsMnth;
						String productionMnthNw = ordrTkBsMnth;
						
						List<Object[]> test = new ArrayList<>();
						Map<String,String> maxQtyMp = new HashMap<>();
						Map<String,String> stdQtyMp = new HashMap<>();
						List<String> prodmtn = new ArrayList<>();
						for (; maxQty.length() >= (i + 7) && i < 168; i += 7, j++) {
							oneToSeven = maxQty.substring(i, i + 7);
							maxQtyMp.put(productionMnth, oneToSeven);
							prodmtn.add(productionMnth);
							productionMnth = CommonUtil.prdMnthCal(productionMnth,two);
						}
						i = 0;
						j = 0;
						oneToSeven = null;
						for (; stdQty.length() >= (i + 7) && i < 168; i += 7, j++) {
							oneToSeven = stdQty.substring(i, i + 7);
							stdQtyMp.put(productionMnthNw, oneToSeven);
							productionMnthNw = CommonUtil.prdMnthCal(productionMnthNw,two);
						}
						for(String prodmtnArry : prodmtn){
							Object[] testarr = new Object[3];
							testarr[0] = maxQtyMp.get(prodmtnArry);
							testarr[1] = prodmtnArry;
							testarr[2] = stdQtyMp.get(prodmtnArry);
							test.add(testarr);
							
						}
						Date featAdDt = CommonUtil
								.convertStringToDate((String) featCD[1]);
						Date carAdDt = CommonUtil
								.convertStringToDate((String) crGrpQryRslt
										.get(0)[1]);

						/** a) Get Maximum Adopt Date */
						if (featAdDt.compareTo(carAdDt) > 0) {
							maxAdDt = CommonUtil.convertDateToString(featAdDt,
									PDConstants.DATE_FORMAT_MONTH);
						} else {
							maxAdDt = CommonUtil.convertDateToString(carAdDt,
									PDConstants.DATE_FORMAT_MONTH);
						}

						Date featAbDt = CommonUtil
								.convertStringToDate((String) featCD[2]);
						Date carAbDt = CommonUtil
								.convertStringToDate((String) crGrpQryRslt
										.get(0)[2]);
						/** b) Get Minimum Abolish Date */
						if (featAbDt.compareTo(carAbDt) < 0) {
							minAbDt = CommonUtil.convertDateToString(featAbDt,
									PDConstants.DATE_FORMAT_MONTH);
						} else {
							minAbDt = CommonUtil.convertDateToString(carAbDt,
									PDConstants.DATE_FORMAT_MONTH);
						}
						int adptDt = CommonUtil.stringtoInt(maxAdDt);
						int abDt = CommonUtil.stringtoInt(minAbDt);

						for (Object[] prdMonthDtls : test) {
							int prdMnth = CommonUtil
									.stringtoInt((String) prdMonthDtls[1]);
							if (adptDt <= prdMnth && abDt >= prdMnth) {
								Object[] objArry = new Object[3];
								objArry[0] = prdMonthDtls[0];
								objArry[1] = prdMonthDtls[1];
								objArry[2] = prdMonthDtls[2];
								validRec.add(objArry);
								/** maxMnth insertion */
							} else {
								/** R000036Report Started */
								writeDataToErrorReport(cmnInterfaceData,
										(String) prdMonthDtls[1],
										(String) featCD[0], (String) prdMonthDtls[0],
										PDConstants.REPORT_R36_ERROR_MESSAGE2);
								LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);
							}
						}
					}
				}
			}else{
				dtlNullChk = true;
			}
		}else{
			crNullChk = true;
		}
	}

		/**
		 * Process No - P0005.5 insertion of data into master table
		 * MONTHLY_OCF_TRN
		 */
		if (insertFlag) {
			for (Object[] items1 : stgQueryRslt)
				calOCFAndInsrt(validRec, featDetails);			
		}
		LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);
	}	

	private void calOCFAndInsrt(List<Object[]> prodMnthDtls, Object[] featDtls) {
		LOG.info("I000026 Processor" + DOLLAR + INSIDE_METHOD + DOLLAR);
		for (Object[] prodDtlsArry : prodMnthDtls) {
			
			insertTrnExp(featDtls, (String) prodDtlsArry[1],
					(String) prodDtlsArry[0], (String) prodDtlsArry[2]);
			 insrtTrn = true;
		}
		LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);
	}

	/**
	 * Process No - P0005.5 insertion of data into master table MONTHLY_OCF_TRN
	 */
	private void insertTrnExp(
			Object[] item1, String prodMnths, String maxQty,String stdQty) {
		LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR);
		TrnMnthlyOcf item = new TrnMnthlyOcf();

			String queryString = I000026QueryConstants.Query_Mnthly_OCF_Select.toString();
			Query query = entityManager.createNativeQuery(queryString);
			query.setParameter(PDConstants.PRMTR_PORCD, interfacePorCd.substring(0, 2));
			query.setParameter(PDConstants.PRMTR_ORDR_TK_BS_MNTH, ordrTkBsMnth.substring(0, 6));
			query.setParameter(PDConstants.PRMTR_PRD_MNTH, prodMnths.substring(0, 6));
			query.setParameter(PDConstants.PRMTR_CAR_SRS,crseries.substring(0, 5));
			query.setParameter(PDConstants.PRMTR_PRODUCTION_PLANT_CD, prodPlntcd.substring(0, 2));
			query.setParameter(PDConstants.PRMTR_OCF_REGION, ocfRginCD.substring(0, 1));
			query.setParameter(PDConstants.PRMTR_OCFBYRGRPCD, byrgroup.substring(0, 3));
			query.setParameter(PDConstants.PRMTR_OCF_FRM_SRT_CD,frmesrtcd.substring(0, 4));
			query.setParameter(PDConstants.PRMTR_CR_GRP,carGrp.substring(0, 4));
			query.setParameter(PDConstants.PRMTR_OCF_IDNTCTN_CD,idnCD.substring(0, 15));
			List<Object> resultList = query.getResultList();
			if (!resultList.isEmpty()) {
				updateMnthlyOCFTrn(item, prodMnths, item1, maxQty, stdQty);
			} else {
				insertMnthlyOCFTrn(item, prodMnths, item1, maxQty, stdQty);
			}
		}

	/**
	 * This method used insert data if key combination not available in the Master table.
	 *
	 * @param item the item
	 */
	public void insertMnthlyOCFTrn(TrnMnthlyOcf item, String prodMnths, Object[] item1, String maxQty,String stdQty) {
		LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR);
		String queryString = I000026QueryConstants.Query_Mnthly_OCF_Insrt.toString();
		Query query = entityManager.createNativeQuery(queryString);
		
		query.setParameter(PDConstants.PRMTR_PORCD, interfacePorCd);
		query.setParameter(PDConstants.PRMTR_ORDR_TK_BS_MNTH, ordrTkBsMnth);
		query.setParameter(PDConstants.PRMTR_PRD_MNTH, prodMnths);
		query.setParameter(PDConstants.PRMTR_CAR_SRS,crseries);
		query.setParameter(PDConstants.PRMTR_PRODUCTION_PLANT_CD, prodPlntcd);
		query.setParameter(PDConstants.PRMTR_OCF_REGION, ocfRginCD);
		query.setParameter(PDConstants.PRMTR_OCFBYRGRPCD, byrgroup);
		query.setParameter(PDConstants.PRMTR_OCF_FRM_SRT_CD,frmesrtcd);
		query.setParameter(PDConstants.PRMTR_CR_GRP,carGrp);
		query.setParameter(PDConstants.PRMTR_OCF_IDNTCTN_CD,idnCD);
		query.setParameter(PDConstants.PRMTR_OCF_FRAME_CD,ocfFrmeCd);
		query.setParameter(PDConstants.PRMTR_FEAT_CD,CommonUtil.convertObjectToStringNull(item1[0]));
		query.setParameter(PDConstants.PRMTR_FEAT_TYPE_CD,CommonUtil.convertObjectToStringNull(item1[3]));		
		query.setParameter(PDConstants.PRMTR_OCF_MAX_QTY, new BigDecimal(maxQty));
		query.setParameter(PDConstants.PRMTR_OCF_STND_QTY,new BigDecimal(stdQty));
		query.setParameter(PDConstants.PRMTR_CRTD_BY, PDConstants.INTERFACE_26_ID);
		query.setParameter(PDConstants.PRMTR_UPDT_BY, PDConstants.INTERFACE_26_ID );
		query.setParameter(PDConstants.PRMTR_STTS_CD,PDConstants.CONSTANT_ZERO);
		query.executeUpdate();
		commonutility.setWriteCount(commonutility.getWriteCount()+1);
		LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);
	}

	
	private void updateMnthlyOCFTrn(TrnMnthlyOcf item, String prodMnths, Object[] item1, String maxQty,String stdQty) {
		LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR);
		
		String queryString = I000026QueryConstants.Query_Mnthly_OCF_Update.toString();
			
		queryString = queryString.replace(":"+PDConstants.PRMTR_PORCD, (interfacePorCd == null)?"''":"'"+interfacePorCd+"'")
				.replace(":"+PDConstants.PRMTR_ORDR_TK_BS_MNTH, (ordrTkBsMnth == null)?"''":"'"+ordrTkBsMnth+"'")
				.replace(":"+PDConstants.PRMTR_PRD_MNTH, (prodMnths == null)?"''":"'"+prodMnths+"'")
				.replace(":"+PDConstants.PRMTR_CAR_SRS, (crseries == null)?"''":"'"+crseries+"'")
				.replace(":"+PDConstants.PRMTR_PRODUCTION_PLANT_CD, (prodPlntcd == null)?"''":"'"+prodPlntcd+"'")
				.replace(":"+PDConstants.PRMTR_OCF_REGION, (ocfRginCD == null)?"''":"'"+ocfRginCD+"'")
				.replace(":"+PDConstants.PRMTR_OCFBYRGRPCD, (byrgroup == null)?"''":"'"+byrgroup+"'")
				.replace(":"+PDConstants.PRMTR_OCF_FRM_SRT_CD, (frmesrtcd == null)?"''":"'"+frmesrtcd+"'")
				.replace(":"+PDConstants.PRMTR_CR_GRP, (carGrp == null)?"''":"'"+carGrp+"'")
				.replace(":"+PDConstants.PRMTR_OCF_IDNTCTN_CD, (idnCD == null)?"''":"'"+idnCD+"'")
				.replace(":"+PDConstants.PRMTR_OCF_FRAME_CD, (ocfFrmeCd == null)?"''":"'"+ocfFrmeCd+"'")
				.replace(":"+PDConstants.PRMTR_FEAT_CD, CommonUtil.convertObjectToStringNull(item1[0]))
				.replace(":"+PDConstants.PRMTR_FEAT_TYPE_CD, CommonUtil.convertObjectToStringNull(item1[3]))
				.replace(":"+PDConstants.PRMTR_OCF_MAX_QTY, (new BigDecimal(maxQty) == null)?"''":"'"+new BigDecimal(maxQty)+"'")
				.replace(":"+PDConstants.PRMTR_OCF_STND_QTY, (new BigDecimal(stdQty) == null)?"''":"'"+new BigDecimal(stdQty)+"'")
				.replace(":"+PDConstants.PRMTR_UPDT_BY, (PDConstants.INTERFACE_26_ID == null)?"''":"'"+PDConstants.INTERFACE_26_ID+"'")
				.replace(":"+PDConstants.PRMTR_STTS_CD, (PDConstants.CONSTANT_ZERO == null)?"''":"'"+PDConstants.CONSTANT_ZERO+"'");				
		Query query = entityManager.createNativeQuery(queryString);
		query.executeUpdate();
		commonutility.setWriteCount(commonutility.getWriteCount()+1);
		LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);
	}	
	
	/**
	 * P0002 This method deletes the records with same order take base month.
	 * 
	 * @param orderTakeBaseMonthDeleteTemp
	 *            the order take base month delete temp
	 * @param jobParamPor2
	 */

		
	/*@BeforeProcess
	public void beforeProcess() {
		LOG.info(DOLLAR+INSIDE_METHOD+DOLLAR);

		if( !dltFlg){
			deleteOldData();
			dltFlg = true;
		}
		LOG.info(DOLLAR+OUTSIDE_METHOD+DOLLAR);
	}

	public void deleteOldData() {
		commonRepository.deleteMnthlyOcfIf();
	}*/

	private void writeDataToErrorReport(CmnInterfaceData cmnInterfaceData,
			String prodMnths, String featCD, String prdMonthDtls, String errMsg)
			throws ParseException {
		LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR);
		/** R000036Report Started */
		R000036ReportDao r000036ReportDao = new R000036ReportDao();

		r000036ReportDao.setPorCode(interfacePorCd);
		r000036ReportDao.setOrdrTakeBaseMnth(cmnInterfaceData.getCol2()
				.substring(0, 4)+ DELIMITE_HYPHEN+ cmnInterfaceData.getCol2().substring(4, 6));
		r000036ReportDao.setPrdMnth(prodMnths.substring(0, 4) + DELIMITE_HYPHEN	+ prodMnths.substring(4, 6));
		r000036ReportDao.setCrSrs(cmnInterfaceData.getCol3());
		r000036ReportDao.setOcfRgnCd(cmnInterfaceData.getCol14());
		r000036ReportDao.setOcfByrGrp(cmnInterfaceData.getCol6());
		r000036ReportDao.setOcfFrmCd(cmnInterfaceData.getCol4());
		r000036ReportDao.setFeatureCd(featCD);
		r000036ReportDao.setOcfShrtDesc(cmnInterfaceData.getCol5());
		r000036ReportDao.setOcfLmt((prdMonthDtls));
		r000036ReportDao.setErrMsg(errMsg);

		R000036ReportDao.getReportList().add(r000036ReportDao);
		LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);
	}

	/**
	 * Gets the entity manager.
	 * 
	 * @return the entity manager
	 */

	public EntityManager getEntityManager() {
		LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR);
		return entityManager;
	}

	/**
	 * Sets the entity manager.
	 * 
	 * @param entityManager
	 *            the new entity manager
	 */

	public void setEntityManager(EntityManager entityManager) {
		LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR);
		this.entityManager = entityManager;
	}
	
	

	@AfterStep
	public void afterStep(StepExecution stepExecution) {
		LOG.info(DOLLAR+INSIDE_METHOD+DOLLAR);
		LOG.info(STAR+STEP_ID + stepExecution.getId()+STAR);
		LOG.info(READ_COUNT + stepExecution.getReadCount());
		LOG.info(READ_SKIPPED_COUNT + stepExecution.getReadSkipCount());
		int writeCnt = commonutility.getWriteCount()/24;
		LOG.info(WRITE_COUNT + writeCnt);
		
		LOG.info(WRITE_SKIPPED_COUNT + stepExecution.getWriteSkipCount());

		if ((stepExecution.getReadCount() == 0 || (crNullChk == true)) ||  (dtlNullChk == true)) {
			commonutility.setStatus(PDConstants.INTERFACE_FAILURE_STATUS);
			commonutility.setRemarks(M00003);
			LOG.info(M00003);
		}
		else if (stepExecution.getExitStatus().getExitCode().equals(FAILED)) {
			commonutility.setStatus(PDConstants.INTERFACE_FAILURE_STATUS);
			if(commonutility.getRemarks() != null && commonutility.getRemarks().isEmpty() ){
				commonutility.setRemarks(M00076.replace(PDConstants.ERROR_MESSAGE_1,""));
				LOG.error(commonutility.getRemarks());
			}	
			LOG.error(M00076.replace(PDConstants.ERROR_MESSAGE_1,stepExecution.getFailureExceptions().toString()));
		}
		else if (stepExecution.getReadCount() == writeCnt|| (insrtTrn == true)) {
			commonutility.setWriteCount(stepExecution.getWriteCount());
			commonutility.setStatus(PDConstants.INTERFACE_PROCESSED_STATUS);
			commonutility.setRemarks(M00113);
			LOG.info(M00113);
		}
		else if (stepExecution.getReadCount() != writeCnt ) {
			commonutility.setStatus(PDConstants.INTERFACE_FAILURE_STATUS);
			commonutility.setRemarks(M00043);
			LOG.info(M00043);
		}
		LOG.info(DOLLAR+OUTSIDE_METHOD+DOLLAR);
	}	
}