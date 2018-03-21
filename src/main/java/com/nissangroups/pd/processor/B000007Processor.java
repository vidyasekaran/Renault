/*
 * System Name     :Post Dragon 
 * Sub system Name :Batch
 * Function ID     :PST-DRG-B000007
 * Module          :O Ordering
 * Process Outline :Create OSEI Frozen Master
 *
 * <Revision History>
 * Date       Name(Company Name)             Description 
 * ---------- ------------------------------ ---------------------
 * @Date      z011479(RNTBCI)               New Creation
 *
 */
package com.nissangroups.pd.processor;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

import com.nissangroups.pd.dao.B000007ReportDao;
import com.nissangroups.pd.mapper.B000007Dao;
import com.nissangroups.pd.model.MstOseiFrzn;
import com.nissangroups.pd.model.MstOseiFrznPK;
import com.nissangroups.pd.util.B000007QueryConstants;
import com.nissangroups.pd.util.CommonUtil;
import com.nissangroups.pd.util.PDConstants;
import com.nissangroups.pd.util.PDMessageConsants;

import static com.nissangroups.pd.util.PDConstants.*;

/**
 * This class is used to process to do the business logic for the batch B00007.
 *
 * @author z011479
 */
@Configuration
@PropertySource(BATCH_CONFIG_CLASSPATH)
@Component(B000007_PROCESSOR)
public class B000007Processor implements
		ItemProcessor<B000007Dao, List<MstOseiFrzn>> {
	
	/** Constant LOG. */
	private static final Log LOG = LogFactory.getLog
			(B000007Processor.class);
	
	/** Variable entity manager. */
	@PersistenceContext(name = PERSISTENCE_NAME)
	private EntityManager entityManager;
	
	/** Variable environment. */
    @Autowired(required = false)
    Environment environment;

	/** Variable error list. */
	@Autowired(required = false)
	private B000007ReportDao errorList;

	/** Variable order take base month. */
	String orderTakeBaseMonth = null;
	
	/** Variable minimum car series limit. */
	String minimumCarSeriesLimit = null;
	
	/** Variable job param por. */
	String jobParamPor = null;
	
	/** Variable job param update flag. */
	String jobParamUpdateFlag = null;
	
	/** Variable unique frozen key. */
	String uniqueFrozenKey = null;
	
	/** Variable frzn map. */
	Map<Object, Object> frznMap = new HashMap<>();

	/**
	 * This method will be executed before the STEP1 to access the batch Job
	 * parameter values.
	 *
	 * @param stepExecution the step execution
	 */
	@BeforeStep
	public void beforeStep(StepExecution stepExecution) {
		LOG.info(DOLLAR+INSIDE_METHOD+DOLLAR);
		LOG.info(DOLLAR+INSIDE_METHOD+DOLLAR);
        String errorPath = environment.getProperty(B000007_REPORT_PATH);
         DateFormat dateFormat = new SimpleDateFormat(DATE_TIME_FORMAT);
        String fileName = errorPath.trim()+REPORT_SUFFIX_B7+dateFormat.format(new Date())+FILE_EXT_TSV;

		JobExecution jobExecution = stepExecution.getJobExecution();
		jobParamPor = jobExecution.getJobParameters().getString(
				PDConstants.BATCH_POR_CODE);
		jobParamUpdateFlag = jobExecution.getJobParameters().getString(
				PRMTR_UPDATE_FLAG);
		ExecutionContext stepContext = jobExecution.getExecutionContext();
		orderTakeBaseMonth = (String) stepContext.get(ORDERTAKEBASEMONTH);
		minimumCarSeriesLimit = (String) stepContext
				.get(MINIMUM_CAR_SERIES_LIMIT);
        stepContext.put(PRMTR_FILE_NAME, fileName);

		LOG.info(MIN_CAR_SERIES_LIMIT_MSG
				+ minimumCarSeriesLimit);
		LOG.info(DOLLAR+OUTSIDE_METHOD+DOLLAR);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.batch.item.ItemProcessor#process(java.lang.Object)
	 */
	@Override
	@SuppressWarnings(UNCHECKED)
	public List<MstOseiFrzn> process(B000007Dao item) throws Exception {
		LOG.info(DOLLAR+INSIDE_METHOD+DOLLAR);
		String porCd = null;
		String productionFamilyCd = null;
		String carSeries = null;
		porCd = item.getPOR_CD();
		carSeries = item.getCAR_SERIES();
		productionFamilyCd = item.getPRODUCTION_FAMILY_CD();
		String ukOseiId = item.getUK_OSEI_ID().trim();
		List<MstOseiFrzn> totalsList = new ArrayList<MstOseiFrzn>();
		List<Object> minValue = getMinimumHorizon(porCd, carSeries,
				productionFamilyCd);
		String productionMonthHorizon = (String) minValue.get(0);
		Date maximumProductionMonth = getMaximumProductionMonth(
				minimumCarSeriesLimit, productionMonthHorizon);
		String maxproductionMonth = dateToString(maximumProductionMonth)
				.substring(0, 6);
		List<Object> resultList = getPatternMatchingResults(item,
				minimumCarSeriesLimit);
		getLowPriorityData(maxproductionMonth, resultList, ukOseiId);

		for (Map.Entry<Object, Object> entry : frznMap.entrySet()) {
			MstOseiFrzn frznMst = new MstOseiFrzn();
			MstOseiFrznPK frznMstPk = new MstOseiFrznPK();
			Object[] finalFrznList = (Object[]) entry.getValue();
			String frozenTypeCdResult = finalFrznList[9].toString();
			String orderTakeBaseMonthResult = (String) finalFrznList[4];
			String productionMonthResult = (String) finalFrznList[7];
			String porCdResult = (String) finalFrznList[0];
			String carSeriesResult = (String) finalFrznList[1];
			String ocfRegionCdResult = finalFrznList[8].toString();
			String frznPriorityCdResult = finalFrznList[10].toString();
			frznMst.setFrznTypeCd(frozenTypeCdResult);
			frznMstPk.setFrznOrdrTakeBaseMnth(orderTakeBaseMonthResult);
			frznMstPk.setOcfRegionCd(ocfRegionCdResult);
			frznMstPk.setFrznProdMnth(productionMonthResult);
			frznMstPk.setPorCd(porCdResult);
			frznMstPk.setFrznPrityCd(frznPriorityCdResult);
			frznMstPk.setCarSrs(carSeriesResult);
			frznMstPk.setOseiId(item.getUK_OSEI_ID().trim());
			frznMst.setUpdtdBy(BATCH_7_ID);
			frznMst.setCrtdBy(BATCH_7_ID);
			Date date = new Date();
			Timestamp createDate = new Timestamp(date.getTime());
			frznMst.setCrtdDt(createDate);
			frznMst.setUpdtdDt(createDate);
			frznMst.setId(frznMstPk);
			
			
			totalsList.add(frznMst);
			if (jobParamUpdateFlag.equals(PDConstants.CONSTANT_ONE)) {
				deleteOldFrozenData(item.getUK_OSEI_ID());
			}
		}
		LOG.info(DOLLAR+OUTSIDE_METHOD+DOLLAR);
		return totalsList;
	}

	/**
	 * This method is used to get the low priority end item details on given
	 * input details.
	 *
	 * @param maxproductionMonth the maxproduction month
	 * @param resultList the result list
	 * @param ukOseiId the uk osei id
	 * @return the low priority data
	 * @throws ParseException the parse exception
	 * @PROCESS ID P0006,P0006.1 & P0006.2
	 */
	private void getLowPriorityData(String maxproductionMonth,
			List<Object> resultList, String ukOseiId) throws ParseException {
		LOG.info(DOLLAR+INSIDE_METHOD+DOLLAR);
		for (Object result : resultList) {
			Object[] resultArray = (Object[]) result;
			Integer frozenProductionMonth = Integer
					.parseInt((String) resultArray[7]);

			uniqueFrozenKey = ukOseiId + SINGLE_QUOTE + frozenProductionMonth.toString();

			if (Integer.parseInt(maxproductionMonth.substring(0, 6)) >= frozenProductionMonth) {
				getValidProduction(resultArray, maxproductionMonth);
			} else {
				writeDataToErrorReport(resultArray, maxproductionMonth);
			}
		}
		LOG.info(DOLLAR+OUTSIDE_METHOD+DOLLAR);
	}

	/**
	 * This method is used to get the minimum car series horizon.
	 *
	 * @param porCode the por code
	 * @param carSeries the car series
	 * @param productionFamilyCd the production family cd
	 * @return the minimum horizon
	 * @PROCESS ID P0006,P0006.1 & P0006.2
	 */
	public List<Object> getMinimumHorizon(String porCode, String carSeries,
			String productionFamilyCd) {
		LOG.info(DOLLAR+INSIDE_METHOD+DOLLAR);
		Query query = entityManager
				.createNativeQuery(B000007QueryConstants.minimumHorizon
						.toString());
		query.setParameter(PDConstants.PRMTR_PORCD, porCode);
		query.setParameter(PDConstants.PRMTR_CARSRS, carSeries);
		query.setParameter(PDConstants.PRMTR_PROD_FMLY_CD, productionFamilyCd.trim());
		@SuppressWarnings(UNCHECKED)
		List<Object> minValue = query.getResultList();
		LOG.info(MIN_HORIZON_VALUE_MSG + minValue.get(0));
		LOG.info(DOLLAR+OUTSIDE_METHOD+DOLLAR);
		return minValue;

	}

	/**
	 * This Method used to get the maximum production using the
	 * MaximumProductionMonth.
	 *
	 * @param orderTakeBaseMonth the order take base month
	 * @param productionMonthHorizon the production month horizon
	 * @return the maximum production month
	 * @throws ParseException the parse exception
	 */
	public Date getMaximumProductionMonth(String orderTakeBaseMonth,
			String productionMonthHorizon) throws ParseException {
		LOG.info(DOLLAR+INSIDE_METHOD+DOLLAR);
		String str = orderTakeBaseMonth.trim();
		DateFormat formatter = new SimpleDateFormat(DATE_FORMAT);
		Calendar cal = Calendar.getInstance();
		Date date = formatter.parse(str);
		cal.setTime(date);
		int minimumCarSeries = Integer.parseInt(productionMonthHorizon);
		cal.add(Calendar.MONTH, minimumCarSeries);
		LOG.info(MAX_PRODUCTION_MONTH_MSG + cal.getTime());
		LOG.info(DOLLAR+OUTSIDE_METHOD+DOLLAR);
		return cal.getTime();
	}

	/**
	 * This Method used to convert Date to String.
	 *
	 * @param covertToString the covert to string
	 * @return String
	 * @throws ParseException the parse exception
	 */
	public String dateToString(Date covertToString) throws ParseException {
		LOG.info(DOLLAR+INSIDE_METHOD+DOLLAR);
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DATE_FORMAT);
		LOG.info(DOLLAR+OUTSIDE_METHOD+DOLLAR);
		return simpleDateFormat.format(covertToString);

	}

	/**
	 * This method is used to do the pattern matching and return the matched
	 * result PROCESS ID P0005 & P0007.
	 *
	 * @param item the item
	 * @param minimumCarSeriesLimit the minimum car series limit
	 * @return the pattern matching results
	 */
	public List<Object> getPatternMatchingResults(B000007Dao item,
			String minimumCarSeriesLimit) {
		LOG.info(DOLLAR+INSIDE_METHOD+DOLLAR);
		List<Object> resultList = null;
		Query query = entityManager
				.createNativeQuery(B000007QueryConstants.B00007PatternMatchingNew
						.toString());
		query.setParameter(PDConstants.PREFIX, item.getAPPLIED_MODEL_CD()
				.substring(0, 7).trim());
		query.setParameter(PDConstants.SUFFIX, (item.getAPPLIED_MODEL_CD()
				.trim() + item.getPACK_CD()).substring(10, 18).trim());
		query.setParameter(PDConstants.SPECDESTCD, item
				.getSPEC_DESTINATION_CD().trim());
		query.setParameter(PDConstants.EXTCOLOR, item.getEXTERIOR_COLOR_CD()
				.trim());
		query.setParameter(PDConstants.INTCOLOR, item.getINTERIOR_COLOR_CD()
				.trim());
		query.setParameter(PDConstants.ORDERTAKEBASEMONTH,
				minimumCarSeriesLimit.substring(0, 6).trim());
		query.setParameter(PDConstants.CARSERIES, item.getCAR_SERIES());
		query.setParameter(PDConstants.BATCH_POR_CODE, item.getPOR_CD().trim());
		query.setParameter(PDConstants.BATCH_PRODUCTION_FAMILY_CODE, item
				.getPRODUCTION_FAMILY_CD().trim());
		query.setParameter(PDConstants.PRMTR_FRZ_DEL_FLG,
				PDConstants.CONSTANT_N);
		resultList = query.getResultList();
		LOG.info(PATTERN_MATCHING_RESULT_MSG + resultList.size());
		if (resultList.size() == 0){
			LOG.info(PDMessageConsants.M00160.replace(PDConstants.ERROR_MESSAGE_1, PDConstants.BATCH_7_ID).replace(PDConstants.ERROR_MESSAGE_2, PDConstants.MESSAGE_DATA).replace(PDConstants.ERROR_MESSAGE_3, item.getPOR_CD().trim()).replace(PDConstants.ERROR_MESSAGE_4,PDConstants.MST_FRZN_TABLE) );
		}
		LOG.info(DOLLAR+OUTSIDE_METHOD+DOLLAR);
		return resultList;
	}

	/**
	 * This method is to write the Error data to Error report PROCESSS ID P0009.
	 *
	 * @param resultArray the result array
	 * @param maxProductionMonth the max production month
	 * @throws ParseException the parse exception
	 */
	public void writeDataToErrorReport(Object[] resultArray,
			String maxProductionMonth) throws ParseException {
		LOG.info(DOLLAR+INSIDE_METHOD+DOLLAR);
		String porCdError = (String) resultArray[0];
		String prefixYesError = (String) resultArray[2];
		String prefixNoError = (String) resultArray[3];
		String orderTakeBaseMonthError = (String) resultArray[4];
		String suffixYesError = (String) resultArray[5];
		String suffixNoError = (String) resultArray[6];
		String productionMonthError = (String) resultArray[7];
		String frozenTypeCdError = String.valueOf(resultArray[9]);
		String frznPrCdError = (String) resultArray[10];
		String frznCdDescriptionError = (String) resultArray[11];
		String ocfRegionCdError = String.valueOf(resultArray[8]);
		String ext1 = EMPTY_STRING;
		String int1 = EMPTY_STRING;
		String ext2 = EMPTY_STRING;
		String int2 = EMPTY_STRING;
		String ext3 = EMPTY_STRING;
		String int3 = EMPTY_STRING;
		String ext4 = EMPTY_STRING;
		String int4 = EMPTY_STRING;
		String ext5 = EMPTY_STRING;
		String int5 = EMPTY_STRING;
        String strResultArray12 = CommonUtil.convertObjectToString(resultArray[12]);
        LOG.info("inside writeDataToErrorReport");
        if (strResultArray12.length() >= 26){
        	LOG.info("writeDataToErrorReport >=26");
		    ext1 = resultArray[12].toString().substring(0, 3).trim();
		    int1 = resultArray[12].toString().substring(3, 5).trim();
		    ext2 = resultArray[12].toString().substring(5, 8).trim();
		    int2 = resultArray[12].toString().substring(8, 10).trim();
		    ext3 = resultArray[12].toString().substring(10, 13).trim();
		    int3 = resultArray[12].toString().substring(13, 15).trim();
		    ext4 = resultArray[12].toString().substring(15, 18).trim();
		    int4 = resultArray[12].toString().substring(18, 20).trim();
		    ext5 = resultArray[12].toString().substring(20, 23).trim();
		    int5 = resultArray[12].toString().substring(23, 25).trim();
        }
        String dest1 = EMPTY_STRING;
		String dest2 = EMPTY_STRING;
		String dest3 = EMPTY_STRING;
		String dest4 = EMPTY_STRING;
		String dest5 = EMPTY_STRING;
        String strResultArray13 = CommonUtil.convertObjectToString(resultArray[13]);
        if (strResultArray13.length() >= 21){
        	LOG.info("writeDataToErrorReport >=21");
		    dest1 = resultArray[13].toString().substring(0, 4).trim();
		    dest2 = resultArray[13].toString().substring(4, 8).trim();
		    dest3 = resultArray[13].toString().substring(8, 12).trim();
		    dest4 = resultArray[13].toString().substring(12, 16).trim();
		    dest5 = resultArray[13].toString().substring(16, 20).trim();
        } 
		int prodMonthErrorDiff = monthDiffernce(orderTakeBaseMonthError,
				productionMonthError);
		int actualProdMonthDiff = monthDiffernce(orderTakeBaseMonthError,
				maxProductionMonth);
		B000007ReportDao reportDao = new B000007ReportDao();
		reportDao.setPorCode(porCdError);
		reportDao.setPriority(frznPrCdError);
		reportDao.setFrznPrdMnth(productionMonthError);
		reportDao.setFrznOrdrTkBsMnth(orderTakeBaseMonthError);
		reportDao.setPrfxYes(prefixYesError);
		reportDao.setPrfxNo(prefixNoError);
		reportDao.setSfxYes(suffixYesError);
		reportDao.setSfxNo(suffixNoError);
		reportDao.setExt1(ext1);
		reportDao.setExt2(ext2);
		reportDao.setExt3(ext3);
		reportDao.setExt4(ext4);
		reportDao.setExt5(ext5);
		reportDao.setInt1(int1);
		reportDao.setInt2(int2);
		reportDao.setInt3(int3);
		reportDao.setInt4(int4);
		reportDao.setInt5(int5);
		reportDao.setDest1(dest1);
		reportDao.setDest2(dest2);
		reportDao.setDest3(dest3);
		reportDao.setDest4(dest4);
		reportDao.setDest5(dest5);
		reportDao.setOcfRgnCd(ocfRegionCdError);
		reportDao.setFrznType(frozenTypeCdError + SINGLE_HYPHEN + frznCdDescriptionError);
		reportDao.setFrznTmng(PDConstants.N + PDConstants.ADD_SYMBOL
				+ prodMonthErrorDiff);
		reportDao.setErrMsg("For POR CD : " + porCdError
				+ " Maximum Frozen Timing should be N + "
				+ (actualProdMonthDiff - 1));
		errorList.getReportList().add(reportDao);
		LOG.info(DOLLAR+OUTSIDE_METHOD+DOLLAR);
	}

	/**
	 * This method will give difference between two dates based on the inputted
	 * dates.
	 *
	 * @param orderTakeBaseMonth the order take base month
	 * @param productionMonth the production month
	 * @return int Date Differnce
	 * @throws ParseException the parse exception
	 */
	public int monthDiffernce(String orderTakeBaseMonth, String productionMonth)
			throws ParseException {
		LOG.info(DOLLAR+INSIDE_METHOD+DOLLAR);
		DateFormat formatter = new SimpleDateFormat(DATE_FORMAT_MONTH);
		int diffMonth;
		Date date = formatter.parse(orderTakeBaseMonth);
		Date date2 = formatter.parse(productionMonth);
		Calendar startCalendar = new GregorianCalendar();
		startCalendar.setTime(date);
		Calendar endCalendar = new GregorianCalendar();
		endCalendar.setTime(date2);
		int diffYear = endCalendar.get(Calendar.YEAR)
				- startCalendar.get(Calendar.YEAR);
		diffMonth = diffYear * 12 + endCalendar.get(Calendar.MONTH)
				- startCalendar.get(Calendar.MONTH);
		LOG.info(DOLLAR+OUTSIDE_METHOD+DOLLAR);
		return diffMonth;
	}

	/**
	 * This method is used to delete the Old Frozen Data PROCESS ID P0008.1
	 *
	 * @param uKOSEIID the u koseiid
	 */
	public void deleteOldFrozenData(String uKOSEIID) {
		LOG.info(DOLLAR+INSIDE_METHOD+DOLLAR);
		String queryString = B000007QueryConstants.deleteOldFrozenData
				.toString();
		Query query = null;
		if (PDConstants.CONSTANT_ONE.equals(jobParamUpdateFlag)) {
			String andUkoseiId = OSEI_ID_CONDITION;
			queryString = queryString + andUkoseiId;
			query = entityManager.createNativeQuery(queryString);
			query.setParameter(PDConstants.PRMTR_PORCD, jobParamPor);
			query.setParameter(PDConstants.PRMTR_UK_OSEI_ID, uKOSEIID);
		} else {
			query = entityManager.createNativeQuery(queryString);
			query.setParameter(PDConstants.PRMTR_PORCD, jobParamPor);
		}
		query.executeUpdate();
		LOG.info(DOLLAR+OUTSIDE_METHOD+DOLLAR);

	}

	/**
	 * This method is used to update the frozen based on the priority code.
	 * PROCESS ID P0010.1 & P0010.2
	 *
	 * @param resultArray the result array
	 * @param maxproductionMonth the maxproduction month
	 * @return the valid production
	 * @throws ParseException the parse exception
	 */
	public void getValidProduction(Object[] resultArray,
			String maxproductionMonth) throws ParseException {
		LOG.info(DOLLAR+INSIDE_METHOD+DOLLAR);
		Object[] oldFrznList = null;
		Integer nwPriorityCd = Integer.parseInt(((String) resultArray[10])
				.trim());writeDataToErrorReport(resultArray, maxproductionMonth);
		if (!frznMap.containsKey(uniqueFrozenKey)) {
			frznMap.put(uniqueFrozenKey, resultArray);
		} else {
			oldFrznList = (Object[]) frznMap.get(uniqueFrozenKey);
			Integer oldPriorityCd = Integer.parseInt(((String) oldFrznList[10])
					.trim());
			if (oldPriorityCd > nwPriorityCd) {
				// write the error report
				writeDataToErrorReport(oldFrznList, maxproductionMonth);
				frznMap.put(uniqueFrozenKey, resultArray);
			} else {
				// Write the error report
				writeDataToErrorReport(resultArray, maxproductionMonth);
			}
		}
		LOG.info(DOLLAR+OUTSIDE_METHOD+DOLLAR);
	}

	/**
	 * This method is used to update the reexcute status PROCESS ID P0010.1
	 * P0010.2
	 */
	@AfterWrite
	public void updateReexecuteStatus() {
		LOG.info(DOLLAR+INSIDE_METHOD+DOLLAR);
		String insertStatus = B000007QueryConstants.insertBatchUpdatedTime
				.toString();
		Query insertBatchUpdtTime = entityManager
				.createNativeQuery(insertStatus);
		insertBatchUpdtTime.setParameter(PDConstants.PRMTR_PORCD, jobParamPor);
		insertBatchUpdtTime.setParameter(PDConstants.PRMTRT_TABLE_NAME,
				PDConstants.TABLE_NAME_ORDERABLE_SALES_END_ITEM_DETAIL_MST);
		insertBatchUpdtTime.executeUpdate();
		LOG.info(DOLLAR+OUTSIDE_METHOD+DOLLAR);
    
	}

	/**
	 * After Step Spring framework method used to print the transaction in the
	 * log file.
	 *
	 * @param stepExecution the step execution
	 * @return the exit status
	 */
	@AfterStep
	public ExitStatus afterStep(StepExecution stepExecution) {
		LOG.info(DOLLAR+INSIDE_METHOD+DOLLAR);
		LOG.info(STAR+STEP_ID + stepExecution.getId()+STAR);
		LOG.info(READ_COUNT + stepExecution.getReadCount());
		LOG.info(READ_SKIPPED_COUNT + stepExecution.getReadSkipCount());
		LOG.info(WRITE_COUNT + stepExecution.getWriteCount());
		LOG.info(WRITE_SKIPPED_COUNT + stepExecution.getWriteSkipCount());
		if (stepExecution.getReadCount() == 0) {
			LOG.info(PDMessageConsants.M00160
					.replace(PDConstants.ERROR_MESSAGE_1,
							PDConstants.BATCH_7_ID)
					.replace(PDConstants.ERROR_MESSAGE_2,
							PDConstants.MESSAGE_DATA)
					.replace(PDConstants.ERROR_MESSAGE_3, jobParamPor)		
					.replace(PDConstants.ERROR_MESSAGE_4, PDConstants.TBL_NM_ORDERABLE_END_ITEM_SPEC_MST));
			stepExecution.setExitStatus(ExitStatus.FAILED);
			LOG.info(DOLLAR+OUTSIDE_METHOD+DOLLAR);
			return ExitStatus.FAILED;
		}
		if (stepExecution.getWriteCount() > 0){
			LOG.info(PDMessageConsants.M00163.replace(PDConstants.ERROR_MESSAGE_1, PDConstants.BATCH_7_ID)
					.replace(PDConstants.ERROR_MESSAGE_2, PDConstants.INSERTED)
					.replace(PDConstants.ERROR_MESSAGE_3, PDConstants.OSEI_FROZEN_MST));
		}
		else{
			LOG.info(PDMessageConsants.M00164.replace(PDConstants.ERROR_MESSAGE_1, PDConstants.BATCH_7_ID)
					.replace(PDConstants.ERROR_MESSAGE_2, PDConstants.INSERTED)
					.replace(PDConstants.ERROR_MESSAGE_3, PDConstants.OSEI_FROZEN_MST));
		}
		LOG.info(DOLLAR+OUTSIDE_METHOD+DOLLAR);
		return ExitStatus.COMPLETED;

	}

}