/*
 * System Name     :Post Dragon 
 * Sub system Name :Batch
 * Function ID     :B000003/R000002
 * Module          :S SPEC
 * Process Outline :Create POR CAR SERIES MASTER & OSEI Production Type Master
 *
 * <Revision History>
 * Date       Name(Company Name)             Description 
 * ---------- ------------------------------ ---------------------
 * @Date      z013865(RNTBCI)               New Creation
 *
 */
package com.nissangroups.pd.processor;

import static com.nissangroups.pd.util.PDConstants.B000003_REPORT_PATH;
import static com.nissangroups.pd.util.PDConstants.BATCH_CONFIG_CLASSPATH;
import static com.nissangroups.pd.util.PDConstants.DATE_TIME_FORMAT;
import static com.nissangroups.pd.util.PDConstants.DOLLAR;
import static com.nissangroups.pd.util.PDConstants.FILE_EXT_TSV;
import static com.nissangroups.pd.util.PDConstants.INSIDE_METHOD;
import static com.nissangroups.pd.util.PDConstants.OUTSIDE_METHOD;
import static com.nissangroups.pd.util.PDConstants.PERSISTENCE_NAME;
import static com.nissangroups.pd.util.PDConstants.PRMTR_FILE_NAME;
import static com.nissangroups.pd.util.PDConstants.REPORT_SUFFIX_B3;
import static com.nissangroups.pd.util.PDMessageConsants.M00003;
import static com.nissangroups.pd.util.PDMessageConsants.M00163;
import static com.nissangroups.pd.util.PDMessageConsants.M00164;
import static com.nissangroups.pd.util.PDMessageConsants.M00179;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.AfterStep;
import org.springframework.batch.core.annotation.AfterWrite;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import com.nissangroups.pd.dao.B000003ReportDao;
import com.nissangroups.pd.mapper.EndItemMapper;
import com.nissangroups.pd.mapper.OSEIDetailMapper;
import com.nissangroups.pd.mapper.OrderableEndItemSpecMstMapper;
import com.nissangroups.pd.mapper.OseiProductionTypeMstMapper;
import com.nissangroups.pd.util.B000003CommonUtil;
import com.nissangroups.pd.util.B000003QueryConstants;
import com.nissangroups.pd.util.B000007QueryConstants;
import com.nissangroups.pd.util.CommonUtil;
import com.nissangroups.pd.util.PDConstants;
import com.nissangroups.pd.util.PDMessageConsants;

/**
 * B000003 Pattern Matching Processor.
 *
 * @author z011479
 */
@Configuration
@PropertySource(BATCH_CONFIG_CLASSPATH)
@Component("b3Processor")
public class B000003Processor implements
		ItemProcessor<EndItemMapper, EndItemMapper> {

	/** Constant LOG. */
	private static final Log LOG = LogFactory.getLog
			(B000003Processor.class);

	/** Variable step execution. */
	private StepExecution stepExecution;

	/** Variable exit flag. */
	private int exitFlag = 0;

	@Autowired(required = false)
	Environment environment;

	/** Variable job param update flag. */
	String jobParamUpdateFlag = null;

	/** Variable entity manager. */
	@PersistenceContext(name = PERSISTENCE_NAME)
	private EntityManager entityManager;

	/** Variable list report dao. */
	@Autowired(required = false)
	private B000003ReportDao listReportDao;

	/** Variable check for multiple car series at color level. */
	String checkForMultipleCarSeriesAtColorLevel = "";

	/** Variable por cd error. */
	String porCdError = "";

	/** Variable production stage cd error. */
	String productionStageCdError = "";

	/** Variable production family code error. */
	String productionFamilyCodeError = "";

	/** Variable buyer cd error. */
	String buyerCdError = "";

	/** Variable end item model cd error. */
	String endItemModelCdError = "";

	/** Variable end item model clr cd error. */
	String endItemModelClrCdError = "";

	/** Variable spec dest error. */
	String specDestError = "";

	/** Variable add spec cd error. */
	String addSpecCdError = "";

	/** Variable adpt mnth error. */
	String adptMnthError = "";

	/** Variable ablsh mnth error. */
	String ablshMnthError = "";

	/** Variable order take base month error. */
	String orderTakeBaseMonthError = "";

	/** Variable car series error. */
	String carSeriesError = "";

	/** Variable prefix yes error. */
	String prefixYesError = "";

	/** Variable suffix yes error. */
	String suffixYesError = "";

	/** Variable error cd. */
	String errorCd = "";

	/** Variable error message. */
	String errorMessage = "";

	/** Variable mul car series error. */
	String mulCarSeriesError = "";

	/** Variable color. */
	String color = "";

	/** Variable por cd. */
	String porCd = "";

	/** Variable production month. */
	String productionMonth = "";

	/** Variable production week no. */
	String productionWeekNo = "";

	/** Variable production plant code. */
	String productionPlantCode = "";

	/** Variable production method cd. */
	String productionMethodCd = "";

	/** Variable ordr tk bs mnth. */
	String ordrTkBsMnth = "";

	/** Variable production family code. */
	String productionFamilyCode = "";

	/** Variable spec dest cd cndtn. */
	String specDestCdCndtn = "";

	/** Variable car srs. */
	String carSrs = "";

	/** Variable abolish date. */
	String abolishDate = "";

	/** Variable adpt dt. */
	Date adptDt;

	/** Variable ablsh dt. */
	Date ablshDt;

	/** Variable min yr mnth. */
	Date minYrMnth;

	/** Variable ext color. */
	String extColor = "";

	/** Variable int color. */
	String intColor = "";

	/** Variable adopt dt. */
	String adoptDt = "";

	/** Variable abolish dt. */
	String abolishDt = "";

	/** Variable osei id. */
	String oseiId = "";

	/** Variable end item. */
	String endItem = "";

	/** Variable converted string. */
	String convertedString = "";

	/** Variable end item car series. */
	String endItemCarSeries = "";

	/** Variable check horizon. */
	Date checkHorizon = null;

	/** Variable cal. */
	Calendar cal = null;

	/** Variable color cd. */
	String colorCd = "";

	/** Variable errorFlag. */
	boolean errorFlag = true;
	
	boolean addFlag = false;
	
	boolean sttsUpdtFlg = true;


	/** Variable count report20 size. */
	int countReport20Size = 0;

	/** Variable count report21 size. */
	int countReport21Size = 0;

	/** Variable count report22 size. */
	int countReport22Size = 0;

	/** Variable count report40 size. */
	int countReport40Size = 0;

	/** Variable job param por. */
	String jobParamPor = null;

	/** Variable matched yr month. */
	List<String> matchedYrMonth = new ArrayList<String>();

	DateFormat dateFormat = new SimpleDateFormat(PDConstants.DATE_FORMAT_MONTH);

	/** Variable por car srs insrtd set. */
	Set<String> porCarSrsInsrtdSet = new HashSet<String>();

	/** Variable sdf. */
	SimpleDateFormat sdf = new SimpleDateFormat(PDConstants.DATE_FORMAT_MONTH);

	/** Variable all production method code list. */
	List<HashMap<String, String>> allProductionMethodCodeList = new ArrayList<HashMap<String, String>>();

	/** Variable production method code list. */
	List<HashMap<String, String>> productionMethodCodeList = new ArrayList<HashMap<String, String>>();

	/** Variable week list. */
	List<Object[]> weekList = new ArrayList<Object[]>();

	/** Variable week no calendar list. */
	List<String> weekNoCalendarList = new ArrayList<String>();

	/** Variable check duplicate. */
	Set<B000003ReportDao> checkDuplicate = new HashSet<B000003ReportDao>();

	/** Variable common util. */
	B000003CommonUtil commonUtil = new B000003CommonUtil();

	boolean carSeriesUpdtfFlag = false;

	/**
	 * This method is used to check whether the extracted production method is
	 * empty and add to the error report.
	 *
	 * @param productionMethodCodeList
	 *            the production method code list
	 * @param eiSpecMst
	 *            the ei spec mst
	 */
	public void checkProductionMethodCdIsEmpty(
			List<HashMap<String, String>> productionMethodCodeList,
			EndItemMapper eiSpecMst) {
		LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR);
		if (productionMethodCodeList.isEmpty()) {

			/*
			 * 10 - ERRROR - CAR SERIES NOT ATTACHED TO SPEC Car Series not
			 * attached to Spec.
			 */

			porCdError = eiSpecMst.getId().getPOR_CD();
			productionStageCdError = eiSpecMst.getId().getPROD_STAGE_CD();
			productionFamilyCodeError = eiSpecMst.getId().getPROD_FMY_CD();
			buyerCdError = eiSpecMst.getId().getBUYER_CD();
			endItemModelCdError = eiSpecMst.getId().getAPPLD_MDL_CD()
					+ eiSpecMst.getId().getPCK_CD();
			endItemModelClrCdError = eiSpecMst.getId().getEXT_CLR_CD()
					+ eiSpecMst.getId().getINT_CLR_CD();
			specDestError = eiSpecMst.getId().getSPEC_DESTN_CD();
			addSpecCdError = eiSpecMst.getId().getADTNL_SPEC_CD();
			adptMnthError = eiSpecMst.getId().getOSEI_ADPT_DATE();
			ablshMnthError = eiSpecMst.getOSEI_ABLSH_DATE();
			errorCd = PDConstants.ERROR_CODE_10;
			errorMessage = PDConstants.ERROR_MESSAGE_CAR_SERIES_NOT_ATTACHED;
			B000003ReportDao reportDao = new B000003ReportDao();
			addDataToReportDao(reportDao);
		}
		LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);
	}

	/**
	 * This method is used to remove the updated record.
	 *
	 * @param removedUp
	 *            the removed up
	 * @param eiSpecMst
	 *            the ei spec mst
	 */
	public void removeUpdatedRecord(Map<String, Map<String, String>> removedUp,
			EndItemMapper eiSpecMst) {
		LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR);
		String defaultString = "";
		for (Entry<String, Map<String, String>> obj : removedUp.entrySet()) {
			String keyVal = obj.getKey();
			weekNoCalendarList.add(keyVal);
			Map<String, String> hm = obj.getValue();

			for (Entry<String, String> e : hm.entrySet()) {
				LOG.info(e.getKey());
				LOG.info(e.getValue());

				switch (e.getKey()) {
				case PDConstants.POR_CD:
					porCd = e.getValue();
					break;
				case PDConstants.PRODUCTION_PLANT_CD:
					productionPlantCode = e.getValue();
					break;

				case PDConstants.PRODUCTION_METHOD_CD:
					productionMethodCd = e.getValue();
					break;

				case PDConstants.ORDR_TK_BS_MNTH:
					ordrTkBsMnth = e.getValue();
					break;
				case PDConstants.PRODUCTION_FAMILY_CD:
					productionFamilyCode = e.getValue();
					break;

				case PDConstants.SPEC_DESTINATION_CD_CONDITION:
					specDestCdCndtn = e.getValue();
					break;
				case PDConstants.CR_SRS:
					carSrs = e.getValue();
					break;
				default:
					defaultString = e.getValue();
					break;
				}

			}

			insertCarSeries(eiSpecMst);
		}
		LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);
	}

	/**
	 * Insert New Car series in to the database.
	 *
	 * @param eiSpecMst
	 *            the ei spec mst
	 */
	public void insertCarSeries(EndItemMapper eiSpecMst) {
		LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR);
		commonUtil.setEntityManager(entityManager);
		for (String weekNoCalStr : weekNoCalendarList) {
			weekList = commonUtil.getweeknocalendar(eiSpecMst.getId()
					.getPOR_CD(), weekNoCalStr);

		}
		weekNoCalendarList.clear();
		LOG.info("week  no");
		if (!productionMethodCodeList.isEmpty()) {
			for (Object[] o : weekList) {
				porCd = o[0].toString();
				productionMonth = o[1].toString();
				productionWeekNo = o[2].toString();
				OseiProductionTypeMstMapper oseiProductionTypeMstMapper = new OseiProductionTypeMstMapper();
				oseiProductionTypeMstMapper.setPOR_CD(porCd);
				oseiProductionTypeMstMapper.setUK_OSEI_ID(eiSpecMst.getId()
						.getOSEI_ID());
				oseiProductionTypeMstMapper
						.setPRODUCTION_PLANT_CD(productionPlantCode);
				oseiProductionTypeMstMapper
						.setORDER_TAKE_BASE_MONTH(ordrTkBsMnth);
				oseiProductionTypeMstMapper
						.setPRODUCTION_MONTH(productionMonth);
				oseiProductionTypeMstMapper
						.setPRODUCTION_WEEK_NO(productionWeekNo);
				oseiProductionTypeMstMapper
						.setPRODUCTION_METHOD_CD(productionMethodCd);

				/* Inserting into the OSEI_MST_PROD_TYPE */
				commonUtil
						.insertOSeiProductionTypeMst(oseiProductionTypeMstMapper);
			}

			OrderableEndItemSpecMstMapper oeismMapper = new OrderableEndItemSpecMstMapper();
			oeismMapper.setPOR_CD(eiSpecMst.getId().getPOR_CD());
			oeismMapper.setPRODUCTION_FAMILY_CD(productionFamilyCode);
			oeismMapper
					.setAPPLIED_MODEL_CD(eiSpecMst.getId().getAPPLD_MDL_CD());
			oeismMapper.setPACK_CD(eiSpecMst.getId().getPCK_CD());
			oeismMapper.setSPEC_DESTINATION_CD(specDestCdCndtn);
			oeismMapper.setCAR_SERIES(carSrs);
			carSeriesUpdtfFlag = true;
			/* Updating the CAR_SERIES */
			commonUtil.updateCarseries(oeismMapper, eiSpecMst.getId()
					.getPOR_CD());

			/* Creating POR_CAR_SERIES_MST */
		String	keyValue = oeismMapper.getPOR_CD() + oeismMapper.getPRODUCTION_FAMILY_CD()
					+ oeismMapper.getCAR_SERIES();
			if (porCarSrsInsrtdSet.add(keyValue)) {
				Set<String> porCarSrsInsrtdSettemp = new HashSet<String>();
				porCarSrsInsrtdSettemp = commonUtil.porCarSeriesMst(oeismMapper,
						porCarSrsInsrtdSettemp, eiSpecMst.getId().getPOR_CD());
			}
		}
		LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);
	}

	/**
	 * Check whether production method code is empty and add data to the error
	 * report.
	 *
	 * @param eiSpecMst
	 *            the ei spec mst
	 * @throws ParseException
	 *             the parse exception
	 */
	public void checkProductionMethodCdListIsEmpty(EndItemMapper eiSpecMst)
			throws ParseException {
		LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR);
		if (!allProductionMethodCodeList.isEmpty()
				&& cal.getTime().equals(checkHorizon)) {
			DateFormat dateFormat = new SimpleDateFormat(
					PDConstants.DATE_FORMAT_MONTH);
			commonUtil.setEntityManager(entityManager);
			String minimumYr = dateFormat.format(cal.getTime());
			String ablshDate = dateFormat.format(checkHorizon);
			if ((adptDt.equals(minYrMnth) || adptDt.before(minYrMnth))
					&& ablshDt.after(minYrMnth)) {
				productionMethodCodeList = commonUtil.getProductionMethodCode(
						eiSpecMst, minimumYr, ablshDate);
				if (!productionMethodCodeList.isEmpty()) {
					allProductionMethodCodeList
							.addAll(productionMethodCodeList);
				} else {
					LOG.info(PDMessageConsants.M00160
							.replace(PDConstants.ERROR_MESSAGE_1,
									PDConstants.BATCH_3_ID)
							.replace(PDConstants.ERROR_MESSAGE_2,
									PDConstants.MESSAGE_DATA)
							.replace(PDConstants.ERROR_MESSAGE_3,
									PDConstants.MESSAGE_POR_CD)
							.replace(
									PDConstants.MESSAGE_POR_CD,
									stepExecution.getJobParameters().getString(
											PDConstants.PRMTR_POR))
							.replace(PDConstants.ERROR_MESSAGE_4,
									PDConstants.MESSAGE_PRODUCTION_TYPE_MST));
				}
			}
			if (productionMethodCodeList.isEmpty()) {

				/*
				 * 10 - ERRROR - CAR SERIES NOT ATTACHED TO SPEC Car Series not
				 * attached to Spec.
				 */

				porCdError = eiSpecMst.getId().getPOR_CD();
				productionStageCdError = eiSpecMst.getId().getPROD_STAGE_CD();
				productionFamilyCodeError = eiSpecMst.getId().getPROD_FMY_CD();
				buyerCdError = eiSpecMst.getId().getBUYER_CD();
				endItemModelCdError = eiSpecMst.getId().getAPPLD_MDL_CD()
						+ eiSpecMst.getId().getPCK_CD();
				endItemModelClrCdError = eiSpecMst.getId().getEXT_CLR_CD()
						+ eiSpecMst.getId().getINT_CLR_CD();
				specDestError = eiSpecMst.getId().getSPEC_DESTN_CD();
				addSpecCdError = eiSpecMst.getId().getADTNL_SPEC_CD();
				adptMnthError = eiSpecMst.getId().getOSEI_ADPT_DATE();
				ablshMnthError = eiSpecMst.getOSEI_ABLSH_DATE();
				errorCd = PDConstants.ERROR_CODE_10;
				errorMessage = PDConstants.ERROR_MESSAGE_CAR_SERIES_NOT_ATTACHED;
				B000003ReportDao reportDao = new B000003ReportDao();
				addDataToReportDao(reportDao);
			}
			allProductionMethodCodeList.addAll(productionMethodCodeList);

		}
		LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);
	}

	/**
	 * Calculate Horizon for the given car series.
	 *
	 * @param eiSpecMst
	 *            the ei spec mst
	 * @throws ParseException
	 *             the parse exception
	 */
	public void checkHorizon(EndItemMapper eiSpecMst) throws ParseException {
		LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR);
		commonUtil.setEntityManager(entityManager);
		while (cal.getTime().before(checkHorizon)) {
			DateFormat dateFormat = new SimpleDateFormat(
					PDConstants.DATE_FORMAT_MONTH);
			String minYr = dateFormat.format(cal.getTime());
			String abolishDt1 = dateFormat.format(checkHorizon);

			if ((adptDt.equals(minYrMnth) || adptDt.before(minYrMnth))
					&& ablshDt.after(minYrMnth)) {
				productionMethodCodeList = commonUtil.getProductionMethodCode(
						eiSpecMst, minYr, abolishDt1);
				allProductionMethodCodeList.addAll(productionMethodCodeList);
			}

			cal.add(Calendar.MONTH, 1);
			checkProductionMethodCdIsEmpty(productionMethodCodeList, eiSpecMst);
		}

		checkProductionMethodCdListIsEmpty(eiSpecMst);

		Map<String, Map<String, String>> removedUp = new TreeMap<String, Map<String, String>>();
		for (Map<String, String> obj : allProductionMethodCodeList) {

			removedUp
					.put(obj.get(PDConstants.PRMTR_ORDER_TAKE_BASE_MONTH), obj);

		}

		removeUpdatedRecord(removedUp, eiSpecMst);
		if (!productionMethodCodeList.isEmpty() && carSeriesUpdtfFlag) {
			OSEIDetailMapper oseiIdMppr = new OSEIDetailMapper();
			oseiIdMppr.setEND_ITM_STTS_CD(PDConstants.TWENTY); /*
																 * Car series
																 * attached
																 */
			oseiIdMppr.setPOR_CD(eiSpecMst.getId().getPOR_CD());
			oseiIdMppr.setUK_OSEI_ID(eiSpecMst.getId().getOSEI_ID());
			oseiIdMppr.setOSEI_ADPT_DATE(eiSpecMst.getId().getOSEI_ADPT_DATE());
			oseiIdMppr.setOSEI_ABLSH_DATE(eiSpecMst.getOSEI_ABLSH_DATE());
			/* Updation of EndItem Status Code */
			commonUtil.updateEndItemStatusCode(oseiIdMppr);
		}
		LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);

	}

	/**
	 * Attach new Car Series.
	 *
	 * @param eiSpecMst
	 *            the ei spec mst
	 * @throws ParseException
	 *             the parse exception
	 */
	public void attachCarSeries(EndItemMapper eiSpecMst) throws ParseException {
		LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR);
		LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR + "minYrMnth" + minYrMnth);
		LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR + "adptDt" + adptDt);
		LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR + "adptDt" + adptDt);

		if ((adptDt.equals(minYrMnth) || adptDt.before(minYrMnth))
				&& ablshDt.after(minYrMnth)) {
			LOG.info("Inside the first list");
			if (errorFlag || addFlag) {
				LOG.info("Inside the second list");
				commonUtil.setEntityManager(entityManager);

				/* Insert into the OSEI_MST_PROD_TYPE based on the Horizon value */
				/* configured in the PARAMETER_MST */

				LOG.info("Check Horizon" + checkHorizon);
				checkHorizon(eiSpecMst);
			}

		}

		else {
			porCdError = eiSpecMst.getId().getPOR_CD();
			productionStageCdError = eiSpecMst.getId().getPROD_STAGE_CD();
			productionFamilyCodeError = eiSpecMst.getId().getPROD_FMY_CD();
			buyerCdError = eiSpecMst.getId().getBUYER_CD();
			endItemModelCdError = eiSpecMst.getId().getAPPLD_MDL_CD()
					+ eiSpecMst.getId().getPCK_CD();
			endItemModelClrCdError = eiSpecMst.getId().getEXT_CLR_CD()
					+ eiSpecMst.getId().getINT_CLR_CD();
			specDestError = eiSpecMst.getId().getSPEC_DESTN_CD();
			addSpecCdError = eiSpecMst.getId().getADTNL_SPEC_CD();
			adptMnthError = eiSpecMst.getId().getOSEI_ADPT_DATE();
			ablshMnthError = eiSpecMst.getOSEI_ABLSH_DATE();
			errorCd = PDConstants.ERROR_CODE_10;
			errorMessage = PDConstants.ERROR_MESSAGE_CAR_SERIES_NOT_ATTACHED;
			B000003ReportDao reportDao = new B000003ReportDao();
			addDataToReportDao(reportDao);
		}
		LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.batch.item.ItemProcessor#process(java.lang.Object)
	 */
	@Override
	public EndItemMapper process(EndItemMapper eiSpecMst) throws Exception {
		LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR + "process");
		initializeGlobalVariables();
		commonUtil.setEntityManager(entityManager);

		String adoptDtStr = eiSpecMst.getId().getOSEI_ADPT_DATE()
				.substring(0, 6);
		String abolisDtStr = eiSpecMst.getOSEI_ABLSH_DATE().substring(0, 6);

		adptDt = sdf.parse(adoptDtStr);
		ablshDt = sdf.parse(abolisDtStr);

		List<Object[]> patternMatchingList = commonUtil
				.patternMatching(eiSpecMst);

		LOG.info("This is the pattern matching result size"
				+ patternMatchingList.size());
		if (patternMatchingList.size() == 0) {
			LOG.info(M00179
					.replace(PDConstants.ERROR_MESSAGE_1,
							PDConstants.PATTERN_MATCHING)
					.replace(PDConstants.ERROR_MESSAGE_2, jobParamPor)
					.replace(PDConstants.ERROR_MESSAGE_3,
							PDConstants.TBL_NAME_MST_PROD_TYPE));
		}

		for (Object[] ptrnMatchObj : patternMatchingList) {
			matchedYrMonth.add(ptrnMatchObj[1].toString());
		}
		Collections.sort(matchedYrMonth);

		String endItemCd = eiSpecMst.getId().getAPPLD_MDL_CD()
				+ eiSpecMst.getId().getPCK_CD();

		JobExecution jobExecution = stepExecution.getJobExecution();
		ExecutionContext jobContext = jobExecution.getExecutionContext();
		String minimumYearMonth = jobContext.get(
				PDConstants.MINIMUM_CAR_SERIES_PERIOD).toString(); // Start date
		abolishDate = eiSpecMst.getOSEI_ABLSH_DATE().substring(0, 6);
		minYrMnth = sdf.parse(minimumYearMonth);
		setHorizon(eiSpecMst);
		createErrorReport(patternMatchingList, eiSpecMst);

		if ((adptDt.equals(minYrMnth) || adptDt.before(minYrMnth))
				&& ablshDt.after(minYrMnth)) {
			getProductionFamilyCode(eiSpecMst, endItemCd.substring(0, 7),
					endItemCd.substring(10, 18));
		}
		attachCarSeries(eiSpecMst);
		if(sttsUpdtFlg){
		specReexecuteStatus();
		sttsUpdtFlg = false;
		}
		LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR + "process");
		return eiSpecMst;

	}

	/**
	 * Update the batch completion status in the SPEC REEXECUTE Status Table.
	 */
	@AfterWrite
	private void specReexecuteStatus() {
		LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR + "specReexecuteStatus");
		try {
			String insertStatus = B000003QueryConstants.insertBatchUpdatedTime
					.toString();
			Query insertBatchUpdtTime = entityManager
					.createNativeQuery(insertStatus);
			insertBatchUpdtTime.setParameter(PDConstants.PRMTR_PORCD,
					jobParamPor);
			insertBatchUpdtTime.setParameter(PDConstants.PRMTRT_TABLE_NAME,
					PDConstants.TABLE_NAME_ORDERABLE_SALES_END_ITEM_DETAIL_MST);
			insertBatchUpdtTime.executeUpdate();
			LOG.info(M00163
					.replace(PDConstants.ERROR_MESSAGE_1,
							PDConstants.BATCH_3_ID)
					.replace(PDConstants.ERROR_MESSAGE_2,
							PDConstants.MESSAGE_INSERTED)
					.replace(PDConstants.ERROR_MESSAGE_3,
							PDConstants.MESSAGE_SPEC_REEXECUTE_STATTUS));
		} catch (Exception e) {
			LOG.error(M00164
					.replace(PDConstants.ERROR_MESSAGE_1,
							PDConstants.BATCH_3_ID)
					.replace(PDConstants.ERROR_MESSAGE_2,
							PDConstants.MESSAGE_INSERTED)
					.replace(PDConstants.ERROR_MESSAGE_3,
							PDConstants.MESSAGE_SPEC_REEXECUTE_STATTUS)
					+ "specReexecuteStatus" + e);
		}
	}

	/**
	 * Insert the batch Updated Time in the batch reexecute status table.
	 *
	 * @param masterUptdTime
	 *            the master uptd time
	 * @param porCd
	 *            the por cd
	 */
	public void insertBatchUpdatedTime(Date masterUptdTime, String porCd) {
		LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR + "insertBatchUpdatedTime");
		java.util.Date date = new java.util.Date();
		String insertStatus = B000007QueryConstants.INSERT_B000003_BATCH_UPDATED_TIME
				.toString();
		Query insertBatchUpdtTime = entityManager
				.createNativeQuery(insertStatus);
		insertBatchUpdtTime.setParameter(PDConstants.PRMTR_PORCD, porCd);
		insertBatchUpdtTime.setParameter(PDConstants.PRMTR_CURRENT_TIME,
				new java.sql.Timestamp(date.getTime()));
		insertBatchUpdtTime.setParameter(PDConstants.PRMTR_MST_UPTD_TIME,
				new java.sql.Timestamp(masterUptdTime.getTime()));
		insertBatchUpdtTime.executeUpdate();
		LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR + "insertBatchUpdatedTime");
	}

	/**
	 * Create error report for Error Condition 10.
	 *
	 * @param patternMatchingList
	 *            the pattern matching list
	 * @param endItmMpr
	 *            the end itm mpr
	 */
	private void createErrorReport(List<Object[]> patternMatchingList,
			EndItemMapper endItmMpr) {
		LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR + "createErrorReport");
		int count10 = 0;
		if (count10 != 1 && patternMatchingList.isEmpty()) {
			/*
			 * 10 - ERRROR - CAR SERIES NOT ATTACHED TO SPEC Car Series not
			 * attached to Spec.
			 */

			porCdError = endItmMpr.getId().getPOR_CD();
			productionStageCdError = endItmMpr.getId().getPROD_STAGE_CD();
			productionFamilyCodeError = endItmMpr.getId().getPROD_FMY_CD();
			buyerCdError = endItmMpr.getId().getBUYER_CD();
			endItemModelCdError = endItmMpr.getId().getAPPLD_MDL_CD()
					+ endItmMpr.getId().getPCK_CD();
			endItemModelClrCdError = endItmMpr.getId().getEXT_CLR_CD()
					+ endItmMpr.getId().getINT_CLR_CD();
			specDestError = endItmMpr.getId().getSPEC_DESTN_CD();
			addSpecCdError = endItmMpr.getId().getADTNL_SPEC_CD();
			adptMnthError = endItmMpr.getId().getOSEI_ADPT_DATE();
			ablshMnthError = endItmMpr.getOSEI_ABLSH_DATE();
			errorCd = PDConstants.ERROR_CODE_10;
			errorMessage = PDConstants.ERROR_MESSAGE_CAR_SERIES_NOT_ATTACHED;
			B000003ReportDao reportDao = new B000003ReportDao();
			addDataToReportDao(reportDao);
			count10 = 1;

		} else {
			processMatchedRecordsForReport(patternMatchingList, endItmMpr);
		}
		LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR + "createErrorReport");
	}

	/**
	 * Before step method to get the input parameter updateflag from the step
	 * execution.
	 *
	 * @param stepExecution
	 *            the step execution
	 */
	@BeforeStep
	public void retrieveInterstepData(StepExecution stepExecution) {
		LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR + "retrieveInterstepData");
		this.stepExecution = stepExecution;
		String errorPath = environment.getProperty(B000003_REPORT_PATH);
		DateFormat dateFormat = new SimpleDateFormat(DATE_TIME_FORMAT);

		String fileName = errorPath.trim() + REPORT_SUFFIX_B3
				+ dateFormat.format(new Date()) + FILE_EXT_TSV;

		JobExecution jobExecution = stepExecution.getJobExecution();
		jobParamUpdateFlag = jobExecution.getJobParameters().getString(
				PDConstants.PRMTR_UPDATE_FLAG);
		jobParamPor = jobExecution.getJobParameters().getString(
				PDConstants.PRMTR_POR);
		ExecutionContext stepContext = jobExecution.getExecutionContext();
		stepContext.put(PRMTR_FILE_NAME, fileName);

		LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR + "retrieveInterstepData");

	}

	/**
	 * Get the count of the available production family code.
	 *
	 * @param por
	 *            the por
	 * @param specdest
	 *            the specdest
	 * @param prefixYes
	 *            the prefix yes
	 * @param suffixYes
	 *            the suffix yes
	 * @return the count of production family code
	 */
	public List<Object[]> getCountOfProductionFamilyCode(String por,
			String specdest, String prefixYes, String suffixYes) {
		LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR
				+ "getCountOfProductionFamilyCode");
		String getPrdFmlyCdCount = B000003QueryConstants.getProdFmlyCdCount
				.toString();
		Query countOfProdFamilyCd = entityManager
				.createNativeQuery(getPrdFmlyCdCount);
		countOfProdFamilyCd.setParameter(PDConstants.PRMTR_PORCD, por.trim());
		countOfProdFamilyCd.setParameter(PDConstants.SPECDESTCD,
				specdest.trim());
		countOfProdFamilyCd.setParameter(PDConstants.PREFIX_YES, prefixYes);
		countOfProdFamilyCd.setParameter(PDConstants.SUFFIX_YES, suffixYes);

		String startOrderTakeBaseMonth = dateFormat.format(minYrMnth);
		String endOrderTakeBaseMonth = dateFormat.format(checkHorizon);

		countOfProdFamilyCd
				.setParameter(PDConstants.START_ORDR_TAKE_BASE_MONTH,
						startOrderTakeBaseMonth);
		countOfProdFamilyCd.setParameter(PDConstants.END_ORDR_TAKE_BASE_MONTH,
				endOrderTakeBaseMonth);

		List<Object[]> countOfPrdFamilycode = countOfProdFamilyCd
				.getResultList();
		LOG.info("countOfPrdFamilycode" + countOfPrdFamilycode.size());
		LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR
				+ "getCountOfProductionFamilyCode");
		return countOfPrdFamilycode;
	}

	/**
	 * Concat car series.
	 *
	 * @param mulCarSeries
	 *            the mul car series
	 * @return the string
	 */
	private String concatCarSeries(List<String> mulCarSeries) {
		LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR + "concatCarSeries");
		List<String> carSeriesList = new ArrayList<String>();
		String carSeries = "";
		if (mulCarSeries.size() > 1) {
			for (String ob : mulCarSeries) {
				if (ob != null) {
					carSeriesList.add(ob.toString());
				}
			}
			for (int i = 0; i < carSeriesList.size(); i++) {
				carSeries = carSeries + carSeriesList.get(i) + ",";

			}
			if (carSeries != "") {
				carSeries = carSeries.substring(0, carSeries.length() - 1);
			}
		}
		LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR + "concatCarSeries");
		return carSeries;
	}

	/**
	 * Get the Production Family Code.
	 *
	 * @param endItmMppr
	 *            the end itm mppr
	 * @param prfxYes
	 *            the prfx yes
	 * @param sfxYes
	 *            the sfx yes
	 * @return the production family code
	 */
	public void getProductionFamilyCode(EndItemMapper endItmMppr,
			String prfxYes, String sfxYes) {
		LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR + "getProductionFamilyCode");
		List<Object[]> countofprdfamilycode = getCountOfProductionFamilyCode(
				endItmMppr.getId().getPOR_CD(), endItmMppr.getId()
						.getSPEC_DESTN_CD(), prfxYes, sfxYes);
		List<String> carserieslist = new ArrayList<String>();
		String crSrs = "";
		for (Object[] ob : countofprdfamilycode) {
			if (ob[1] != null && ob[1] != "") {
				carserieslist.add(ob[1].toString());
			}
		}
		for (int i = 0; i < carserieslist.size(); i++) {
			crSrs = crSrs + carserieslist.get(i) + ",";

		}
		if (crSrs != null && !"".equals(crSrs)) {
			crSrs = crSrs.substring(0, crSrs.length() - 1);
		}
		LOG.info("countofprdfamilycode**************" + countofprdfamilycode);
		for (Object[] ob : countofprdfamilycode) {

			if (Integer.parseInt(ob[0].toString()) > 1) {
				LOG.info("iNSIDE PFC**************" + countofprdfamilycode);
				/* *
				 * Different Production Family code with same car series : K131.
				 * 
				 * 50 - WARNING - DIFFERENT FAMILY CODE WITH SAME CAR SERIES
				 */
				porCdError = endItmMppr.getId().getPOR_CD();
				productionStageCdError = endItmMppr.getId().getPROD_STAGE_CD();
				productionFamilyCodeError = endItmMppr.getId().getPROD_FMY_CD();
				buyerCdError = endItmMppr.getId().getBUYER_CD();
				endItemModelCdError = prfxYes + sfxYes;
				endItemModelClrCdError = endItmMppr.getId().getEXT_CLR_CD()
						+ endItmMppr.getId().getINT_CLR_CD();
				specDestError = endItmMppr.getId().getSPEC_DESTN_CD();
				addSpecCdError = endItmMppr.getId().getADTNL_SPEC_CD();
				adptMnthError = endItmMppr.getId().getOSEI_ADPT_DATE();
				ablshMnthError = endItmMppr.getOSEI_ABLSH_DATE();
				errorCd = PDConstants.ERROR_CODE_50;
				errorMessage = "Different Production Family code with same car series :"
						+ crSrs;
				B000003ReportDao reportDao = new B000003ReportDao();
				addDataToReportDao(reportDao);

			}

		}
		LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR + "getProductionFamilyCode");
	}

	/**
	 * Add data to the error report.
	 *
	 * @param reportDao
	 *            the report dao
	 */
	public void addDataToReportDao(B000003ReportDao reportDao) {
		LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR + "addDataToReportDao");
		reportDao.setPor_cd(porCdError);
		reportDao.setProduction_stage_cd(productionStageCdError);
		reportDao.setProduction_family_cd(productionFamilyCodeError);
		reportDao.setBuyer_cd(buyerCdError);
		reportDao.setEnd_item_model_cd(endItemModelCdError);
		reportDao.setEnd_item_color_cd(endItemModelClrCdError);
		reportDao.setSpec_destination_cd(specDestError);
		reportDao.setAdditional_spec_cd(addSpecCdError);
		reportDao.setAdopt_month(adptMnthError);
		reportDao.setAbolish_month(ablshMnthError);
		reportDao.setError_code(errorCd);
		reportDao.setError_message(errorMessage);
		LOG.info("Error Message " + errorMessage);
		listReportDao.getReportList().add(reportDao);
		errorFlag = false;
		LOG.info(DOLLAR + "Error Flag Set As False" + DOLLAR);
		LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR + "addDataToReportDao");
	}

	/**
	 * Check for multiple car series available at color level.
	 *
	 * @param endItmMppr
	 *            the end itm mppr
	 */
	public void checkForMultipleCarSeriesAtColorSrs(EndItemMapper endItmMppr) {
		LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR
				+ "checkForMultipleCarSeriesAtColorSrs");

		checkForMultipleCarSeriesAtColorLevel = B000003QueryConstants.getMultipleCarSeriesAtColorLevel
				.toString();
		Query checkForMultipleCarSeriesAtColorLevelQry = entityManager
				.createNativeQuery(checkForMultipleCarSeriesAtColorLevel);
		checkForMultipleCarSeriesAtColorLevelQry.setParameter(
				PDConstants.PRMTR_OSEI_ADOPT_DT, endItmMppr.getId()
						.getOSEI_ADPT_DATE().trim());
		checkForMultipleCarSeriesAtColorLevelQry.setParameter(
				PDConstants.PRMTR_OSEI_ABOLISH_DT, endItmMppr
						.getOSEI_ABLSH_DATE().trim());
		checkForMultipleCarSeriesAtColorLevelQry.setParameter(
				PDConstants.PRMTR_OSEI_ABOLISH_DT, endItmMppr
						.getOSEI_ABLSH_DATE().trim());
		checkForMultipleCarSeriesAtColorLevelQry.setParameter(
				PDConstants.PRMTR_PORCD, porCdError.trim());
		checkForMultipleCarSeriesAtColorLevelQry.setParameter(
				PDConstants.PRMTR_OEI_SPEC_ID, endItmMppr.getId()
						.getOEI_SPEC_ID().trim());

		List<Object[]> mulCarSeries2 = checkForMultipleCarSeriesAtColorLevelQry
				.getResultList();
		LOG.info("mulCarSeries2 list size" + mulCarSeries2.size());
		String extColorCd = "";
		String intColorCd = "";
		String osei = "";
		for (Object[] obj : mulCarSeries2) {
			osei = obj[0].toString();
			extColor = obj[1].toString();
			intColor = obj[2].toString();
			color = extColor + intColor;
			adoptDt = obj[3].toString();
			abolishDt = obj[4].toString();

			String checkColorQry = B000003QueryConstants.getDistinctCarSeries
					+ B000003QueryConstants.andColorCondition.toString();

			if (suffixYesError != null
					&& !(suffixYesError.equalsIgnoreCase(""))) {
				checkColorQry = checkColorQry
						+ B000003QueryConstants.suffixNoCheck.toString();
			}
			if (prefixYesError != null
					&& !(prefixYesError.equalsIgnoreCase(""))) {
				checkColorQry = checkColorQry
						+ B000003QueryConstants.prefixNoCheck.toString();
			}

			Query checkDistCrColorQry = entityManager
					.createNativeQuery(checkColorQry);
			checkDistCrColorQry.setParameter(PDConstants.PRMTR_PORCD,
					porCdError);
			checkDistCrColorQry.setParameter(PDConstants.PREFIX_YES,
					prefixYesError + PDConstants.SYMBL_PERCENTAGE);
			checkDistCrColorQry.setParameter(PDConstants.SUFFIX_YES,
					suffixYesError + PDConstants.SYMBL_PERCENTAGE);
			checkDistCrColorQry.setParameter(PDConstants.PRMTR_CLR, color
					+ PDConstants.SYMBL_PERCENTAGE);

			checkDistCrColorQry.setParameter(PDConstants.PRMTRT_PRD_FMLY_CD,
					productionFamilyCodeError.trim());

			String startOrderTakeBaseMonth = dateFormat.format(minYrMnth);
			String endOrderTakeBaseMonth = dateFormat.format(checkHorizon);

			checkDistCrColorQry.setParameter(
					PDConstants.START_ORDR_TAKE_BASE_MONTH,
					startOrderTakeBaseMonth);
			checkDistCrColorQry
					.setParameter(PDConstants.END_ORDR_TAKE_BASE_MONTH,
							endOrderTakeBaseMonth);

			List<String> mulCarSeries3 = checkDistCrColorQry.getResultList();
			LOG.info("Multiple Car Series::::::::" + mulCarSeries3.size());
			String mulCarSrs = concatCarSeries(mulCarSeries3);
			// 21------------------------
			if (osei.equals(endItmMppr.getId().getOSEI_ID())
					&& countReport21Size != 1 && mulCarSrs != ""
					&& mulCarSeries3.size() > 1 && mulCarSrs.length() > 5) {

				productionStageCdError = endItmMppr.getId().getPROD_STAGE_CD();
				buyerCdError = endItmMppr.getId().getBUYER_CD();
				endItemModelCdError = prefixYesError + suffixYesError;
				endItemModelClrCdError = color;
				addSpecCdError = endItmMppr.getId().getADTNL_SPEC_CD();
				adptMnthError = endItmMppr.getId().getOSEI_ADPT_DATE();
				ablshMnthError = endItmMppr.getOSEI_ABLSH_DATE();
				errorCd = PDConstants.ERROR_CODE_21;
				errorMessage = PDConstants.MULTIPLE_CAR_SERIES
						+ mulCarSrs
						+ " was found  in End Item Level, car series can not be attached.";
				B000003ReportDao reportDao = new B000003ReportDao();
				addDataToReportDao(reportDao);
				countReport21Size = 1;
			}

			checkDistinctCarSrs(endItmMppr, osei);

		}
		LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR
				+ "checkForMultipleCarSeriesAtColorSrs");

	}

	/**
	 * Add data to the error report for error status 21.
	 *
	 * @param mulCarSeries4
	 *            the mul car series4
	 * @param endItmMppr
	 *            the end itm mppr
	 * @param osei
	 *            the osei
	 */
	public void addErrorReportForStts21(List<String> mulCarSeries4,
			EndItemMapper endItmMppr, String osei) {
		LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR + "addErrorReportForStts21");
		if (countReport21Size != 1
				&& osei.equals(endItmMppr.getId().getOSEI_ID())
				&& (endItmMppr.getCAR_SRS() == null || "".equals(endItmMppr
						.getCAR_SRS()))) {
			String concatMulCarSeries = concatCarSeries(mulCarSeries4);
			if (concatMulCarSeries.length() > 5) {
				productionStageCdError = endItmMppr.getId().getPROD_STAGE_CD();
				buyerCdError = endItmMppr.getId().getBUYER_CD();
				endItemModelCdError = prefixYesError + suffixYesError;
				endItemModelClrCdError = color;
				addSpecCdError = endItmMppr.getId().getADTNL_SPEC_CD();
				adptMnthError = endItmMppr.getId().getOSEI_ADPT_DATE();
				ablshMnthError = endItmMppr.getOSEI_ABLSH_DATE();
				errorCd = PDConstants.ERROR_CODE_21;
				errorMessage = PDConstants.MULTIPLE_CAR_SERIES
						+ concatMulCarSeries
						+ " was found  in End Item Level, car series can not be attached.";
				B000003ReportDao reportDao = new B000003ReportDao();
				addDataToReportDao(reportDao);
				countReport21Size = 1;
			}
		}
		LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR + "addErrorReportForStts21");

	}

	/**
	 * Check for the distinct car series and add data to the error report for
	 * error status 21.
	 *
	 * @param endItmMppr
	 *            the end itm mppr
	 * @param osei
	 *            the osei
	 */
	public void checkDistinctCarSrs(EndItemMapper endItmMppr, String osei) {
		LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR + "checkDistinctCarSrs");
		String extractDstntCrSrs = B000003QueryConstants.getDistinctCarSeries
				.toString();
		if (suffixYesError != null && !(suffixYesError.equalsIgnoreCase(""))) {
			extractDstntCrSrs = extractDstntCrSrs
					+ B000003QueryConstants.suffixNoCheck.toString();
		}
		if (prefixYesError != null && !(prefixYesError.equalsIgnoreCase(""))) {
			extractDstntCrSrs = extractDstntCrSrs
					+ B000003QueryConstants.prefixNoCheck.toString();
		}

		Query extractDstntCrSrsQry = entityManager
				.createNativeQuery(extractDstntCrSrs);
		extractDstntCrSrsQry.setParameter(PDConstants.PRMTR_PORCD, porCdError);
		extractDstntCrSrsQry.setParameter(PDConstants.PREFIX_YES,
				prefixYesError + PDConstants.SYMBL_PERCENTAGE);
		extractDstntCrSrsQry.setParameter(PDConstants.SUFFIX_YES,
				suffixYesError + PDConstants.SYMBL_PERCENTAGE);
		extractDstntCrSrsQry.setParameter(PDConstants.PRMTRT_PRD_FMLY_CD,
				productionFamilyCodeError.trim());

		String startOrderTakeBaseMonth = dateFormat.format(minYrMnth);
		String endOrderTakeBaseMonth = dateFormat.format(checkHorizon);

		extractDstntCrSrsQry
				.setParameter(PDConstants.START_ORDR_TAKE_BASE_MONTH,
						startOrderTakeBaseMonth);
		extractDstntCrSrsQry.setParameter(PDConstants.END_ORDR_TAKE_BASE_MONTH,
				endOrderTakeBaseMonth);

		List<String> mulCarSeries4 = extractDstntCrSrsQry.getResultList();
		LOG.info("mulCarSeries4" + mulCarSeries4.size());
		if (mulCarSeries4.size() > 1) {

			addErrorReportForStts21(mulCarSeries4, endItmMppr, osei);
			// 21------------------------
		}
		LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR + "checkDistinctCarSrs");

	}

	/**
	 * Check for multiple car series attached for error status 10.
	 *
	 * @param endItmMppr
	 *            the end itm mppr
	 */
	public void checkMultipleCarSeriesAttchdForSttsTen(EndItemMapper endItmMppr) {
		LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR
				+ "checkMultipleCarSeriesAttchdForSttsTen");
		String checkForMultipleCarSeriesQry = B000003QueryConstants.getMultipleCarSrsChck
				.toString();

		if (suffixYesError != null && !(suffixYesError.equalsIgnoreCase(""))) {
			checkForMultipleCarSeriesQry = checkForMultipleCarSeriesQry
					+ B000003QueryConstants.suffixNoCheck.toString();
		}
		if (prefixYesError != null && !(prefixYesError.equalsIgnoreCase(""))) {
			checkForMultipleCarSeriesQry = checkForMultipleCarSeriesQry
					+ B000003QueryConstants.prefixNoCheck.toString();
		}

		Query query = entityManager
				.createNativeQuery(checkForMultipleCarSeriesQry);
		query.setParameter(PDConstants.PRMTR_PORCD, porCdError);

		query.setParameter(PDConstants.PRMTRT_PRD_FMLY_CD,
				productionFamilyCodeError.trim());
		query.setParameter(PDConstants.PREFIX_YES, prefixYesError
				+ PDConstants.SYMBL_PERCENTAGE);
		query.setParameter(PDConstants.SUFFIX_YES, suffixYesError
				+ PDConstants.SYMBL_PERCENTAGE);

		String startOrderTakeBaseMonth = dateFormat.format(minYrMnth);
		String endOrderTakeBaseMonth = dateFormat.format(checkHorizon);

		query.setParameter(PDConstants.START_ORDR_TAKE_BASE_MONTH,
				startOrderTakeBaseMonth);
		query.setParameter(PDConstants.END_ORDR_TAKE_BASE_MONTH,
				endOrderTakeBaseMonth);

		List<String> mulCarSeries = query.getResultList();
		LOG.info("mulCarSeries" + mulCarSeries.size());

		if (countReport20Size != 1 && mulCarSeries.size() > 1) {
			String concatMultiCarSeries = concatCarSeries(mulCarSeries);
			/* 20----------------------------- */
			if (concatMultiCarSeries.length() > 5) {
				productionStageCdError = endItmMppr.getId().getPROD_STAGE_CD();
				buyerCdError = endItmMppr.getId().getBUYER_CD();
				endItemModelCdError = prefixYesError + suffixYesError;
				endItemModelClrCdError = PDConstants.CONSTANT_ALL;
				addSpecCdError = endItmMppr.getId().getADTNL_SPEC_CD();
				adptMnthError = PDConstants.CONSTANT_ALL;
				ablshMnthError = PDConstants.CONSTANT_ALL;
				errorCd = PDConstants.ERROR_CODE_20;
				mulCarSeriesError = concatMultiCarSeries;
				errorMessage = PDConstants.MULTIPLE_CAR_SERIES
						+ concatMultiCarSeries
						+ " was found in End Item Level, Check below combinations";
				B000003ReportDao reportDao = new B000003ReportDao();
				addDataToReportDao(reportDao);
				countReport20Size = 1;
			}

		}

		checkForMultipleCarSeriesAtColorSrs(endItmMppr);
		LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR
				+ "checkMultipleCarSeriesAttchdForSttsTen");

	}

	/**
	 * Check for the multiple car series attached at color level.
	 *
	 * @param endItmMppr
	 *            the end itm mppr
	 * @param mulCarSeries3
	 *            the mul car series3
	 */
	public void checkMultipleCarSrsAttached(EndItemMapper endItmMppr,
			List<String> mulCarSeries3) {
		LOG.info("mulCarSeries3" + mulCarSeries3.size());

		LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR
				+ "checkMultipleCarSrsAttached");
		if (!PDConstants.EMPTY_STRING.equalsIgnoreCase(endItemCarSeries)
				&& mulCarSeries3.size() > 1) {

			mulCarSeriesError = concatCarSeries(mulCarSeries3);
			/*
			 * 22 - ERRROR - MULTIPLE CAR SERIES FOUND, OLD CAR SERIES
			 * MAINTAINED. Multiple car series(K131, K132, K133) was found in
			 * End Item Level, Old car series : K131 is maintained.
			 */
			if (countReport20Size != 1 && mulCarSeries3.size() > 1) {
				String concatMultiCarSeries = concatCarSeries(mulCarSeries3);
				/* 20----------------------------- */
				if (concatMultiCarSeries.length() > 5) {
					productionStageCdError = endItmMppr.getId()
							.getPROD_STAGE_CD();
					buyerCdError = endItmMppr.getId().getBUYER_CD();
					endItemModelCdError = prefixYesError + suffixYesError;
					endItemModelClrCdError = PDConstants.CONSTANT_ALL;
					addSpecCdError = endItmMppr.getId().getADTNL_SPEC_CD();
					adptMnthError = PDConstants.CONSTANT_ALL;
					ablshMnthError = PDConstants.CONSTANT_ALL;
					errorCd = PDConstants.ERROR_CODE_20;
					mulCarSeriesError = concatMultiCarSeries;
					errorMessage = PDConstants.MULTIPLE_CAR_SERIES
							+ concatMultiCarSeries
							+ " was found in End Item Level, Check below combinations";
					B000003ReportDao reportDao = new B000003ReportDao();
					addDataToReportDao(reportDao);
					countReport20Size = 1;
				}

			}
			if (countReport22Size != 1
					&& oseiId.equals(endItmMppr.getId().getOSEI_ID())
					&& mulCarSeriesError.length() > 5
					&& mulCarSeriesError != "") {

				productionStageCdError = endItmMppr.getId().getPROD_STAGE_CD();
				productionFamilyCodeError = endItmMppr.getId().getPROD_FMY_CD();
				buyerCdError = endItmMppr.getId().getBUYER_CD();
				endItemModelCdError = endItmMppr.getId().getAPPLD_MDL_CD()
						+ endItmMppr.getId().getPCK_CD();
				endItemModelClrCdError = extColor + intColor;
				specDestError = endItmMppr.getId().getSPEC_DESTN_CD();
				addSpecCdError = endItmMppr.getId().getADTNL_SPEC_CD();
				adptMnthError = endItmMppr.getId().getOSEI_ADPT_DATE();

				ablshMnthError = endItmMppr.getOSEI_ABLSH_DATE();

				errorCd = PDConstants.ERROR_CODE_22;
				errorMessage = PDConstants.MULTIPLE_CAR_SERIES
						+ mulCarSeriesError
						+ " was found in End Item Level, Old car series :"
						+ endItemCarSeries + "is maintained.";

				B000003ReportDao reportDao = new B000003ReportDao();
				addDataToReportDao(reportDao);
				countReport22Size = 1;

			}

		} else if (mulCarSeries3.size() == 1 && mulCarSeries3.get(0) != null
				&& !endItemCarSeries.equals(mulCarSeries3.get(0))) {
			/*
			 * 30 - WARNING - CAR SERIES CHANGED Car series is changed. (Old Car
			 * Series: K131, New Car Series: K132)
			 */

			productionStageCdError = endItmMppr.getId().getPROD_STAGE_CD();
			buyerCdError = endItmMppr.getId().getBUYER_CD();
			endItemModelCdError = prefixYesError + "" + suffixYesError;
			endItemModelClrCdError = colorCd;
			addSpecCdError = endItmMppr.getId().getADTNL_SPEC_CD();
			adptMnthError = endItmMppr.getId().getOSEI_ADPT_DATE();
			ablshMnthError = endItmMppr.getOSEI_ABLSH_DATE();
			errorCd = PDConstants.ERROR_CODE_30;
			errorMessage = "Car series is changed. (Old Car Series:"
					+ endItemCarSeries + " New Car Series: "
					+ mulCarSeries3.get(0);

			B000003ReportDao reportDao = new B000003ReportDao();
			addDataToReportDao(reportDao);
			checkDuplicate.addAll(listReportDao.getReportList());
			String mulCarSeries = mulCarSeries3.get(0);

			checkDuplicateCarSeriesAttchd(endItmMppr, mulCarSeries, reportDao);

		}
		LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR
				+ "checkMultipleCarSrsAttached");

	}

	/**
	 * Check for the duplicate car series attached and remove data from the
	 * report list if data already exists.
	 *
	 * @param endItmMppr
	 *            the end itm mppr
	 * @param mulCarSeries
	 *            the mul car series
	 * @param reportDao
	 *            the report dao
	 */
	public void checkDuplicateCarSeriesAttchd(EndItemMapper endItmMppr,
			String mulCarSeries, B000003ReportDao reportDao) {
		LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR
				+ "checkDuplicateCarSeriesAttchd");
		if (checkDuplicate.size() == 1) {

			OrderableEndItemSpecMstMapper oeismMapper = new OrderableEndItemSpecMstMapper();
			oeismMapper.setPOR_CD(porCdError);
			oeismMapper.setPRODUCTION_FAMILY_CD(productionFamilyCodeError);
			oeismMapper.setAPPLIED_MODEL_CD(endItmMppr.getId()
					.getAPPLD_MDL_CD());
			oeismMapper.setPACK_CD(endItmMppr.getId().getPCK_CD());
			oeismMapper.setSPEC_DESTINATION_CD(specDestError);
			oeismMapper.setCAR_SERIES(mulCarSeries);
			// Updating the CAR_SERIES
			commonUtil.setEntityManager(entityManager);
			commonUtil.updateCarseries(oeismMapper, jobParamPor);
			
			addFlag = true;
			
		} else {
			listReportDao.getReportList().remove(reportDao);
		}
		LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR
				+ "checkDuplicateCarSeriesAttchd");
	}

	/**
	 * Check for the multiple car series attached at the color level.
	 *
	 * @param mulcarseries2
	 *            the mulcarseries2
	 * @param endItmMppr
	 *            the end itm mppr
	 */
	public void checkForMultiCarSeriesClrLvl(List<Object[]> mulcarseries2,
			EndItemMapper endItmMppr) {
		LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR
				+ "checkForMultiCarSeriesClrLvl");
		for (Object[] obj : mulcarseries2) {

			oseiId = obj[0].toString();
			extColor = obj[1].toString();
			intColor = obj[2].toString();
			colorCd = extColor + intColor;
			adoptDt = obj[3].toString();
			abolishDt = obj[4].toString();

			String checkColorQry = B000003QueryConstants.checkClrQry.toString();

			Query checkColorQr = entityManager.createNativeQuery(checkColorQry);
			checkColorQr.setParameter(PDConstants.PRMTR_PORCD, endItmMppr
					.getId().getPOR_CD());
			checkColorQr.setParameter(PDConstants.PRMTRT_PRD_FMLY_CD,
					productionFamilyCodeError.trim());
			checkColorQr.setParameter(PDConstants.PREFIX_YES,
					endItem.substring(0, 7));
			checkColorQr.setParameter(PDConstants.SUFFIX_YES,
					endItem.substring(10, 18));
			checkColorQr.setParameter(PDConstants.PRMTR_CLR, colorCd.trim());

			String startOrderTakeBaseMonth = dateFormat.format(minYrMnth);
			String endOrderTakeBaseMonth = dateFormat.format(checkHorizon);

			checkColorQr.setParameter(PDConstants.START_ORDR_TAKE_BASE_MONTH,
					startOrderTakeBaseMonth);
			checkColorQr.setParameter(PDConstants.END_ORDR_TAKE_BASE_MONTH,
					endOrderTakeBaseMonth);

			LOG.info("Query Parameter POR CD " + endItmMppr.getId().getPOR_CD());
			LOG.info("Query Parameter Production Family Code "
					+ productionFamilyCodeError.trim());
			LOG.info("Query Parameter PREFIX_YES " + endItem.substring(0, 7));
			LOG.info("Query Parameter SUFFIX_YES " + endItem.substring(10, 18));
			LOG.info("Query Parameter PRMTR_CLR " + endItem.substring(10, 18));
			List<String> mulCarSeries3 = checkColorQr.getResultList();
			LOG.info("mulCarSeries3" + mulCarSeries3.size());

			String getEmptyCarSrs = B000003QueryConstants.getEmptyCarSrs
					.toString();

			Query emptyCarQuery = entityManager
					.createNativeQuery(getEmptyCarSrs);
			emptyCarQuery.setParameter(PDConstants.PRMTR_PORCD, endItmMppr
					.getId().getPOR_CD());
			emptyCarQuery.setParameter(PDConstants.PRMTRT_PRD_FMLY_CD,
					productionFamilyCodeError.trim());
			emptyCarQuery.setParameter(PDConstants.PREFIX_YES,
					endItem.substring(0, 7) + PDConstants.SYMBL_PERCENTAGE);
			emptyCarQuery.setParameter(PDConstants.SUFFIX_YES,
					endItem.substring(10, 18) + PDConstants.SYMBL_PERCENTAGE);
			emptyCarQuery.setParameter(PDConstants.PRMTR_CLR, colorCd.trim()
					+ PDConstants.SYMBL_PERCENTAGE);
			List<Object[]> emptyCarQueryList = emptyCarQuery.getResultList();
			String carSeries;
			String orderTkeBseMnth;

			for (Object[] emp : emptyCarQueryList) {

				carSeries = (String) emp[0];
				orderTkeBseMnth = (String) emp[1];
				if (countReport40Size != 1) {
					addDataToErrorReportStts40(endItmMppr, carSeries,
							orderTkeBseMnth);
				}
			}

			checkMultipleCarSrsAttached(endItmMppr, mulCarSeries3);
		}
		LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR
				+ "checkForMultiCarSeriesClrLvl");
	}

	/**
	 * Add data to the error report for error status 40.
	 *
	 * @param endItmMppr
	 *            the end itm mppr
	 * @param carSeries
	 *            the car series
	 * @param orderTkeBseMnth
	 *            the order tke bse mnth
	 */
	public void addDataToErrorReportStts40(EndItemMapper endItmMppr,
			String carSeries, String orderTkeBseMnth) {
		LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR + "addDataToErrorReportStts40");
		if (!PDConstants.EMPTY_STRING.equalsIgnoreCase(endItemCarSeries)
				&& orderTkeBseMnth.equals(orderTakeBaseMonthError)
				&& (PDConstants.EMPTY_STRING.equals(carSeries) || carSeries == null)) {
			// Check for the car series null condition
			productionStageCdError = endItmMppr.getId().getPROD_STAGE_CD();
			buyerCdError = endItmMppr.getId().getBUYER_CD();
			endItemModelCdError = prefixYesError + suffixYesError;
			endItemModelClrCdError = extColor + intColor;
			addSpecCdError = endItmMppr.getId().getADTNL_SPEC_CD();
			adptMnthError = endItmMppr.getId().getOSEI_ADPT_DATE();
			ablshMnthError = endItmMppr.getOSEI_ABLSH_DATE();
			errorCd = PDConstants.ERROR_CODE_40;
			errorMessage = "Car series not found from Order Take Base Period "
					+ orderTkeBseMnth + ", Old car series :" + endItemCarSeries
					+ "is maintained.";
			B000003ReportDao reportDao = new B000003ReportDao();
			addDataToReportDao(reportDao);
			countReport40Size = 1;
		}
		LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR
				+ "addDataToErrorReportStts40");

	}

	/**
	 * Check for multiple car series attached at the color level.
	 *
	 * @param endItmMppr
	 *            the end itm mppr
	 */
	public void checkForMultipleCarSrsColorLvl(EndItemMapper endItmMppr) {
		LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR
				+ "checkForMultipleCarSrsColorLvl");
		endItem = endItmMppr.getId().getAPPLD_MDL_CD()
				+ endItmMppr.getId().getPCK_CD();

		endItemCarSeries = endItmMppr.getCAR_SRS();
		if (endItemCarSeries == null) {
			endItemCarSeries = "";
		}

		checkForMultipleCarSeriesAtColorLevel = B000003QueryConstants.checkForMultipleCarSeriesAtColorLevel
				.toString();

		Query checkForMultipleCarSeriesAtColorLevelQry = entityManager
				.createNativeQuery(checkForMultipleCarSeriesAtColorLevel);
		checkForMultipleCarSeriesAtColorLevelQry.setParameter(
				PDConstants.PRMTR_PORCD, porCdError.trim());
		checkForMultipleCarSeriesAtColorLevelQry.setParameter(
				PDConstants.PRMTR_OEI_SPEC_ID, endItmMppr.getId()
						.getOEI_SPEC_ID().trim());
		checkForMultipleCarSeriesAtColorLevelQry.setParameter(
				PDConstants.PRMTR_OSEI_ADOPT_DT, endItmMppr.getId()
						.getOSEI_ADPT_DATE().trim());
		checkForMultipleCarSeriesAtColorLevelQry.setParameter(
				PDConstants.PRMTR_OSEI_ABOLISH_DT, endItmMppr
						.getOSEI_ABLSH_DATE().trim());
		checkForMultipleCarSeriesAtColorLevelQry.setParameter(
				PDConstants.PRMTR_EIM_STTS_CD_1, PDConstants.TWENTY);
		checkForMultipleCarSeriesAtColorLevelQry.setParameter(
				PDConstants.PRMTR_EIM_STTS_CD_2, PDConstants.THIRTY);
		List<Object[]> mulcarseries2 = checkForMultipleCarSeriesAtColorLevelQry
				.getResultList();

		checkForMultiCarSeriesClrLvl(mulcarseries2, endItmMppr);
		LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR
				+ "checkForMultipleCarSrsColorLvl");

	}

	/**
	 * Check for multiple car series attached for status 20 and 30.
	 *
	 * @param endItmMppr
	 *            the end itm mppr
	 */
	public void checkMultipleCarSeriesAttchdSttsTwentyAndThirty(
			EndItemMapper endItmMppr) {
		LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR
				+ "checkMultipleCarSeriesAttchdSttsTwentyAndThirty");
		checkForMultipleCarSrsColorLvl(endItmMppr);
		LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR
				+ "checkMultipleCarSeriesAttchdSttsTwentyAndThirty");
	}

	/**
	 * Add data to error report.
	 *
	 * @param patternMatchingList
	 *            the pattern matching list
	 * @param endItmMppr
	 *            the end itm mppr
	 */
	public void processMatchedRecordsForReport(
			List<Object[]> patternMatchingList, EndItemMapper endItmMppr) {

		LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR);
		for (Object[] ptrMtchArryList : patternMatchingList) {
			CommonUtil commonUtil = new CommonUtil();
			porCdError = commonUtil.convertObjectToString(ptrMtchArryList[0]);

			orderTakeBaseMonthError = commonUtil
					.convertObjectToString(ptrMtchArryList[1]);

			productionFamilyCodeError = commonUtil
					.convertObjectToString(ptrMtchArryList[2]);

			carSeriesError = commonUtil
					.convertObjectToString(ptrMtchArryList[5]);

			prefixYesError = commonUtil
					.convertObjectToString(ptrMtchArryList[7]);

			suffixYesError = commonUtil
					.convertObjectToString(ptrMtchArryList[9]);

			specDestError = commonUtil
					.convertObjectToString(ptrMtchArryList[13]);
			color = commonUtil.convertObjectToString(ptrMtchArryList[12]);

			if (endItmMppr.getId().getEND_ITM_STTS_CD() != null
					&& PDConstants.TEN.equals(endItmMppr.getId()
							.getEND_ITM_STTS_CD().trim())) {
				checkMultipleCarSeriesAttchdForSttsTen(endItmMppr);

			}

			else if (endItmMppr.getId().getEND_ITM_STTS_CD() != null
					&& (PDConstants.TWENTY.equals(endItmMppr.getId()
							.getEND_ITM_STTS_CD().trim()) || PDConstants.THIRTY
							.equals(endItmMppr.getId().getEND_ITM_STTS_CD()
									.trim()))) {
				checkMultipleCarSeriesAttchdSttsTwentyAndThirty(endItmMppr);
			}
		}
		LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);

	}

	/**
	 * After step.
	 *
	 * @param stepExecution
	 *            the step execution
	 * @return the exit status
	 */
	@AfterStep
	public ExitStatus afterStep(StepExecution stepExecution) {

		if (stepExecution.getReadCount() == 0) {
			LOG.info(M00003);
		}
		LOG.info("exitFlag" + exitFlag);
		if (exitFlag == 1) {
			stepExecution.setExitStatus(ExitStatus.FAILED);
			return ExitStatus.FAILED;
		}
		return ExitStatus.COMPLETED;
	}

	public void setHorizon(EndItemMapper eiSpecMst) throws ParseException {

		String abolishDate = eiSpecMst.getOSEI_ABLSH_DATE().substring(0, 6);

		Date abolishMonth = sdf.parse(abolishDate);
		cal = Calendar.getInstance();

		cal.setTime(minYrMnth);

		String paramHorizon = commonUtil.getHorizonfromParameterMst(eiSpecMst);

		Calendar cal2 = Calendar.getInstance();

		cal2.setTime(minYrMnth);
		if (!PDConstants.CONSTANT_ZERO.equals(paramHorizon)) {
			cal2.add(Calendar.MONTH, Integer.valueOf(paramHorizon) - 1);
		}
		if (Integer.valueOf(paramHorizon) != 12) {
			checkHorizon = cal2.getTime();
		} else {
			checkHorizon = abolishMonth;
		}

	}

	/**
	 * This is method is used to reinitialize the global variables. Due to
	 * spring batch restrictions
	 */
	public void initializeGlobalVariables() {
		errorFlag = true;
		addFlag = false;
	}

}
