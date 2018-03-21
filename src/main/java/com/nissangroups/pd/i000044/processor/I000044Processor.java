/*
 * System Name            : Post Dragon 
 * Sub system Name 		  : Interface
 * Function ID            : PST-DRG-I000044
 * Module                 : OR Ordering		
 * Process Outline     	  : SEND MONTHLY PRODUCTION SCHEDULE TO NSC(STANDARD)															
 *
 * <Revision History>
 * Date       Name(Company Name)             Description 
 * ---------- ------------------------------ ---------------------
 * 11-01-2016  	  z016127(RNTBCI)               Initial Version
 *
 */
package com.nissangroups.pd.i000044.processor;

import static com.nissangroups.pd.util.PDConstants.DOLLAR;
import static com.nissangroups.pd.util.PDConstants.FAILED;
import static com.nissangroups.pd.util.PDConstants.INSIDE_METHOD;
import static com.nissangroups.pd.util.PDConstants.OUTSIDE_METHOD;
import static com.nissangroups.pd.util.PDConstants.PERSISTENCE_NAME;
import static com.nissangroups.pd.util.PDConstants.READ_COUNT;
import static com.nissangroups.pd.util.PDConstants.READ_SKIPPED_COUNT;
import static com.nissangroups.pd.util.PDConstants.STAR;
import static com.nissangroups.pd.util.PDConstants.STEP_ID;
import static com.nissangroups.pd.util.PDConstants.WRITE_COUNT;
import static com.nissangroups.pd.util.PDConstants.WRITE_SKIPPED_COUNT;
import static com.nissangroups.pd.util.PDMessageConsants.M00214;
import static com.nissangroups.pd.util.PDMessageConsants.M00226;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.AfterStep;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;

import com.nissangroups.pd.i000044.bean.I000044InputParam;
import com.nissangroups.pd.i000044.util.I000044Constants;
import com.nissangroups.pd.i000044.util.I000044QueryConstants;
import com.nissangroups.pd.model.CmnFileHdr;
import com.nissangroups.pd.model.CmnFileHdrPK;
import com.nissangroups.pd.model.CmnInterfaceData;
import com.nissangroups.pd.model.CmnInterfaceDataPK;
import com.nissangroups.pd.util.IFConstants;
import com.nissangroups.pd.util.IfCommonUtil;
import com.nissangroups.pd.util.PDConstants;
import com.nissangroups.pd.util.PDMessageConsants;

/**
 *  This class I000044Processor is to process the extracted Monthly & Weekly Schedule data 
 *  from Reader and insert the data into Common Interface data
 *
 * @author z016127
 */
public class I000044Processor implements
		ItemProcessor<String, List<CmnInterfaceData>> {

	/** Constant LOG. */
	private static final Log LOG = LogFactory.getLog
			(I000044Processor.class);

	/** Common utility service bean injection */
	@Autowired(required = false)
	IfCommonUtil commonutility;

	/** Variable Query String */
	private static StringBuilder finalQuery;
	
	/** Variable entity manager. */
	@PersistenceContext(unitName = PERSISTENCE_NAME)
	private EntityManager entityManagerFactory;
	
	/** Variable Input Param list */
	private List<I000044InputParam> paramList;
	
	/** Variable Weekly Schedule data list */
	private List<Object[]> weeklyDataList;
	
	/** Variable Monthly Schedule data list */
	private List<Object[]> monthlyDataList;
	
	/** Variable PorCd String */
	private String porCdStr ="";
	
	/** Variable Buyer Group Cd String */
	private String buyerGrpCdStr ="";
	
	/** Variable Summarize order quantity Flag */
	private String summarizeOrderQtyFlg;
	
	/** Variable Interface file Id */
	private String ifFileId;
	
	/** Variable Seq No */
	private long seqNo;
	
	/** Variable Weekly and Monthly data list */
	private List<CmnInterfaceData> totalList = new ArrayList<CmnInterfaceData>();
	
	/**
	 * This method will be called just before each step execution
	 * Get stepExecution and assign into instance variable
	 * 
	 * @param stepExecution 
	 * 					the step execution
	 */
	@BeforeStep 
	public void beforeStep(StepExecution stepExecution) {
		LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR);
		JobExecution jobExec = stepExecution.getJobExecution();
		
		/** Variable PorCD */
		porCdStr = jobExec.getExecutionContext().get(I000044Constants.PORCD_STR)+ "";
		
		/** Variable Buyer Group CD */
		buyerGrpCdStr = jobExec.getExecutionContext().get(I000044Constants.BUYERGRP_CODE_STR)+ "";	
		
		/** Summarize Order Qty Flg */
		summarizeOrderQtyFlg = jobExec.getJobParameters().getString(IFConstants.SUMMARIZE_ORDER_QTY_FLAG);
		
		paramList = (List<I000044InputParam>) jobExec.getExecutionContext().get(I000044Constants.PARAM_LIST);
		
		/** Variable Interface File Id*/
		ifFileId = jobExec.getJobParameters().getString(
				IFConstants.INTERFACE_FILE_ID);
		
		/** Variable SeqNo */
		seqNo = (long) jobExec.getExecutionContext().get(IFConstants.SEQ_NO);
		
		/** extract Weekly data */
		weeklyDataList = getWeeklyData();
		
		/** extract Monthly data */
		monthlyDataList = getMonthlyData();
		
		LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);
	}

	/** 
	 * This method is to process the extracted Weekly and Monthly schedule data 
	 * and insert the list into Common Interface data.
	 * P0003, P0004
	 * 
	 * @param ordrTakeBaseMnth 
	 * 						the ordrTakeBaseMnth
	 * @return the list of CmnInterfaceData 
	 * 						the class
	 * @throws Exception the exception
	 */
	@Override
	public List<CmnInterfaceData> process(String ordrTakeBaseMnth) throws Exception {

		LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR);

		/** Variable Common Interface data */
		totalList = new ArrayList<CmnInterfaceData>();
		
		/**P0003 Extract Weekly Schedule data */
		if(weeklyDataList != null && !(weeklyDataList.isEmpty())) {
			totalList = getCmnInterfaceDataList(weeklyDataList, totalList);
		}
		
		/** P0003 Extract Monthly Schedule data */
		if(monthlyDataList != null && !(monthlyDataList.isEmpty())){
			totalList = getCmnInterfaceDataList(monthlyDataList, totalList);
		}
		
		/** P0003 extracted Weekly and Monthly schedule data */
		if(totalList == null || totalList.isEmpty()){
			LOG.error(M00214.replace(PDConstants.ERROR_MESSAGE_1, ifFileId)
					 .replace(PDConstants.ERROR_MESSAGE_2, PDConstants.MONTHLY_PRODUCTION_SCHEDULE_TRN)
					 .replace(PDConstants.ERROR_MESSAGE_3, porCdStr)
					 .replace(PDConstants.ERROR_MESSAGE_4, buyerGrpCdStr));
		}
		LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);
		return totalList;
	}
	
	/**
	 * P0004 Insert the extracted weekly and monthly schedule data
	 * into Common Interface Data
	 * Fetch the rowcount, sequence number, Interface file id from
	 * the context 
	 * 
	 * @param dataList
	 * @param totalList
	 * 
	 * @return List of CmnInterfaceData
	 */
	private List<CmnInterfaceData> getCmnInterfaceDataList(List<Object[]> dataList, List<CmnInterfaceData> totalList){
		
		if(dataList != null && !(dataList.isEmpty())){
			
			for(Object[] item : dataList){
			/** Variable rowCount */
			long rowCount = commonutility.getRowCount() + 1;
			
			/** Variable Common Interface data */
			CmnInterfaceData cmnIfData = new CmnInterfaceData();		
			cmnIfData.setId(new CmnInterfaceDataPK());		
			cmnIfData.getId().setSeqNo(seqNo);		
			cmnIfData.getId().setRowNo(rowCount);	
			cmnIfData.getId().setIfFileId(ifFileId);
			CmnFileHdr fileHdr = new CmnFileHdr();		
			fileHdr.setId(new CmnFileHdrPK());
			fileHdr.getId().setIfFileId(ifFileId);
			fileHdr.getId().setSeqNo(seqNo);
			cmnIfData.setCmnFileHdr(fileHdr);
			
			cmnIfData.setCol1(String.valueOf(item[1]));// PorCd
			cmnIfData.setCol2(String.valueOf(item[8]));//Car Series
			cmnIfData.setCol3(String.valueOf(item[14]));//Buyer Cd
			cmnIfData.setCol4(String.valueOf(item[15]));//Applied Model cd
			cmnIfData.setCol5(String.valueOf(item[16]));//Pack cd
			cmnIfData.setCol6(String.valueOf(item[17]));//Spec destination cd
			cmnIfData.setCol7(String.valueOf(item[18]));//Add spec cd 
			cmnIfData.setCol8(String.valueOf(item[19]));//Exterior color cd
			
			cmnIfData.setCol9(String.valueOf(item[20]));//Interior color cd
			cmnIfData.setCol10(String.valueOf(item[23]));//Pot cd
			cmnIfData.setCol11(String.valueOf(item[4]));//Prod plant cd
			cmnIfData.setCol12(String.valueOf(item[5]));//Line class
			//Order Take Base Period Type
			cmnIfData.setCol13(getPeriodTypValue(String.valueOf(item[11]))); 
			cmnIfData.setCol14(String.valueOf(item[2]));//Order take base month
			//Production Period Type
			cmnIfData.setCol15(getPeriodTypValue(String.valueOf(item[11]))); 
			cmnIfData.setCol16(String.valueOf(item[3]));//Prod month	
			cmnIfData.setCol17(String.valueOf(item[25]));//Offln plan date
			cmnIfData.setCol18(String.valueOf(item[24]));//Order qty
			//NML Monthly Fixed Flag
			cmnIfData.setCol19(getNmlMnthlyFxdFlg(String.valueOf(item[11])));
			cmnIfData.setCol20(String.valueOf(item[11]));//Prod method cd
			cmnIfData.setCol21(String.valueOf(item[9]));//Frzn type cd
			cmnIfData.setCol22(String.valueOf(item[31]));//Local prod order no
			cmnIfData.setCol23(String.valueOf(item[30]));//Prod order no
			
			cmnIfData.setCol24(String.valueOf(item[7]));//Ex no 
			cmnIfData.setCol25(String.valueOf(item[21]));//Prod famly no
			cmnIfData.setCol26(String.valueOf(item[6]));//Sales note no 
			cmnIfData.setCol27(String.valueOf(item[10]));//Fixed symbol 
			cmnIfData.setCol28(String.valueOf(item[22]));//Week no of year 
			//Next Order Take Period Type
			if(item[2].equals(item[3])){
				cmnIfData.setCol29(nxtOrdrTakPrdTyp(item[2], item[3]));
			}else{
				cmnIfData.setCol29("M");
			}
			cmnIfData.setCol30(String.valueOf(item[2]));//Order take base month
			//Production Day No Month
			cmnIfData.setCol31(getProdDayNo(String.valueOf(item[12]), String.valueOf(item[13])));
			cmnIfData.setCol32(String.valueOf(item[26]));//Created user id
			cmnIfData.setCol33(String.valueOf(item[27]));//Created date time
			cmnIfData.setCol34(String.valueOf(item[28]));//Updated user id
			cmnIfData.setCol35(String.valueOf(item[29]));//Updated date time
			
			totalList.add(cmnIfData);
			commonutility.setRowCount(rowCount);
		}
			
		}
		return totalList;
	}
	
	/**
	 * Method to get the Next Order take Period Type
	 *  compares two object and return the String
	 * @param item
	 *  			the item
	 * @param value
	 * 				the value
	 * @return the string
	 */
	private String nxtOrdrTakPrdTyp(Object item, Object value) {
		
		String val= "";
		
		if(item.equals(value)){
			val = I000044Constants.W;
		}else{
			val  = I000044Constants.M;
		}
		return val;
	}

	/**
	 * Method to get the Order take period type and Production Period type
	 * compares the item with constants and return the string
	 * 
	 * @param item
	 * 				the item
	 * 
	 * @return the string
	 */
	public String getPeriodTypValue(String item){
		
		String value= "";
		
		if(item != null && (I000044Constants.B).equals(item)){
			value =I000044Constants.W; 
		}else if(item != null && (I000044Constants.C).equals(item)){
			value = I000044Constants.M; 
		}
		return value;
	}
	
	/**
	 * Method to get the NML Monthly Fixed Flag
	 * compares the item with constants and return the string
	 * 
	 * @param item
	 * 				the item
	 * 
	 * @return the string
	 */
	public String getNmlMnthlyFxdFlg(String item){
		
		String value= "";
		
		if(item != null && (I000044Constants.B).equals(item)){
			value = I000044Constants.ZERO; 
		}else if(item != null && (I000044Constants.C).equals(item)){
			value = I000044Constants.ONE; 
		}
		return value;
	}
	
	/**
	 * Method to get the Production day no
	 * Perform calculation and return the string
	 * 
	 * @param item
	 * 				the item
	 * @param val
	 * 				the value
	 * @return the string
	 */
	public String getProdDayNo(String item, String val){
		
		String nullString = "null";
		String value="";
		
		if(item != null && !(nullString).equalsIgnoreCase(item) && val != null && !(nullString).equalsIgnoreCase(val))
			value = String.valueOf(((Integer.parseInt(item) - 1) * 7 ) + Integer.parseInt(val));
		
		return value;
	}
	
	/**
	 * P0003 Extract the Weekly schedule data when production month
	 * is equal to P0002 Order take base month
	 * 
	 * @return the Weekly schedule data
	 */
	private List<Object[]> getWeeklyData(){
		
		LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR);
		List<Object[]> selectResultSet =null;
		String rownum = I000044Constants.query;
		
			if((I000044Constants.SUMMARIZE_ORDR_FLG).equals(summarizeOrderQtyFlg)){
				finalQuery = new StringBuilder().append(rownum+I000044QueryConstants.extractWeeklyScheduleData
							.toString());
			}else {
				finalQuery = new StringBuilder().append(rownum+I000044QueryConstants.extractWeeklyScheduleData2
						.toString());
			}
			getWklyDataList();
		
			int ind = finalQuery.toString().lastIndexOf("OR ");
			finalQuery = finalQuery.replace(ind, ind + 2, "");
	
			if((I000044Constants.SUMMARIZE_ORDR_FLG).equals(summarizeOrderQtyFlg)){
				finalQuery.append(I000044QueryConstants.summarizeOrderQtyFlgForWeeklyData.toString());
			}
			finalQuery.append(") a");
			
			Query query = entityManagerFactory.createNativeQuery(finalQuery.toString());
			selectResultSet = query.getResultList();
			LOG.info("**********Final Query for Weekly Data***********" +finalQuery.toString());
		
			LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);

		return selectResultSet;
	}
	
	/**
	 * Query to extract weekly schedule data
	 */
	private void getWklyDataList(){
		
		String nullString = "";
		
		for (I000044InputParam inputParams : paramList) {
			
			String whereClause = "";
			whereClause = I000044QueryConstants.baseQueryCondition.toString();
			whereClause = ((nullString).equals(inputParams.getPorCd())) ? whereClause
					.replaceAll(IFConstants.param_WklyPorCD, " ") : whereClause
					.replaceAll(IFConstants.porCd_Param, "'"
							+ inputParams.getPorCd().trim() + I000044Constants.quryStng);
			whereClause = ((nullString).equals(inputParams
					.getOrdrTakeBaseMnth())) ? whereClause.replaceAll(
					IFConstants.param_WklyOrdrTake, " ") : whereClause
					.replaceAll(IFConstants.ordrTkBsMnth_Param, "'"
							+ inputParams.getOrdrTakeBaseMnth().trim()
							+ I000044Constants.quryStng);
			whereClause = ((nullString).equals(inputParams
					.getOrdrTakeBaseMnth())) ? whereClause.replaceAll(
					IFConstants.param_trnWklyOrdrTake, " ") : whereClause
					.replaceAll(IFConstants.ordrTkBsMnth_Param, "'"
							+ inputParams.getOrdrTakeBaseMnth().trim()
							+ I000044Constants.quryStng);
			whereClause = getQueryBasedOnCndtn(inputParams, whereClause);

			finalQuery.append("("+ whereClause + ") OR ");
			int indx = finalQuery.toString().lastIndexOf("AND");
			finalQuery = finalQuery.replace(indx, indx + 3, " ");
		}
	}
	
	/**
	 * P0003 Extract the Monthly schedule data other than P0002 first production month
	 * 
	 * @return monthly schedule data
	 */
	private List<Object[]> getMonthlyData(){
		
		LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR);
		
		List<Object[]> selectResultSet =null;
		String rownum =I000044Constants.query;
		
			if((I000044Constants.SUMMARIZE_ORDR_FLG).equals(summarizeOrderQtyFlg)){
				finalQuery = new StringBuilder().append(rownum+I000044QueryConstants.extractMonthlyScheduleData
							.toString());
			}else {
				finalQuery = new StringBuilder().append(rownum+I000044QueryConstants.extractMonthlyScheduleData2
						.toString());
			}
			getMnthlyDataList();
	
			int ind = finalQuery.toString().lastIndexOf("OR ");
			finalQuery = finalQuery.replace(ind, ind + 2, "");
	
			if((I000044Constants.SUMMARIZE_ORDR_FLG).equals(summarizeOrderQtyFlg)){
				finalQuery.append(I000044QueryConstants.summarizeOrderQtyFlgForMonthlyData.toString());
			}
			finalQuery.append(") a");
			
			Query query = entityManagerFactory.createNativeQuery(finalQuery.toString());
			selectResultSet = query.getResultList();
			
			LOG.info("**********Final Query for Monthly Data***********" +finalQuery.toString());
		
		LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);

		return selectResultSet;
	}
	
	/**
	 * Query to extract the monthly schedule data
	 */
	private void  getMnthlyDataList(){
		
		String nullString ="";
		
		for (I000044InputParam inputParams : paramList) {
			
			String whereClause ="";
			whereClause = I000044QueryConstants.baseQueryCondition1.toString();
			whereClause = ((nullString).equals(inputParams.getPorCd())) ? whereClause
					.replaceAll(IFConstants.param_MnthPorCD, " ") : whereClause
					.replaceAll(IFConstants.porCd_Param, "'"
							+ inputParams.getPorCd().trim() + I000044Constants.quryStng);
			whereClause = ((nullString).equals(inputParams
					.getOrdrTakeBaseMnth())) ? whereClause.replaceAll(
					IFConstants.param_MnthOrdrTake, " ") : whereClause
					.replaceAll(IFConstants.ordrTkBsMnth_Param, "'"
							+ inputParams.getOrdrTakeBaseMnth().trim()
							+ I000044Constants.quryStng);
			whereClause = ((nullString).equals(inputParams
					.getOrdrTakeBaseMnth())) ? whereClause.replaceAll(
					IFConstants.param_trnMnthOrdrTake, " ") : whereClause
					.replaceAll(IFConstants.ordrTkBsMnth_Param, "'"
							+ inputParams.getOrdrTakeBaseMnth().trim()
							+ I000044Constants.quryStng);
			whereClause = getQueryBasedOnCndtn(inputParams, whereClause);

			finalQuery.append("(" + whereClause + ") OR ");
			int indx = finalQuery.toString().lastIndexOf("AND");
			finalQuery = finalQuery.replace(indx, indx + 3, " ");
		}
	}
	
	/**
	 * Method to generate the base query based on the input parameter
	 * 
	 * @param inputParams
	 * @param whrCluse
	 * 
	 * @return the query string
	 */
	public String getQueryBasedOnCndtn(I000044InputParam inputParams, String whrCluse){
		
		String nullString ="";
		String whereClause = whrCluse;
		
		whereClause = ((nullString).equals(inputParams.getBuyerGrpCd())) ? whereClause
				.replaceAll(IFConstants.param_buyer_buyerGrpCD, " ")
				: whereClause.replaceAll(IFConstants.buyer_buyerGrpCD_Param,
						"'" + inputParams.getBuyerGrpCd().trim() + I000044Constants.quryStng);
		whereClause = ((nullString).equals(inputParams.getRhqCd())) ? whereClause
				.replaceAll(IFConstants.param_rhqCd, " ") : whereClause
				.replaceAll(IFConstants.rhqCd_Param, "'"
						+ inputParams.getRhqCd().trim() + I000044Constants.quryStng);
		whereClause = ((nullString).equals(inputParams.getOcfRegionCd())) ? whereClause
				.replaceAll(IFConstants.param_ocfRegionCd, " ") : whereClause
				.replaceAll(IFConstants.ocfRegionCd_Param, "'"
						+ inputParams.getOcfRegionCd().trim() + I000044Constants.quryStng);
		whereClause = ((nullString).equals(inputParams.getOcfBuyerCd())) ? whereClause
				.replaceAll(IFConstants.param_ocfBuyerGrpCd, " ") : whereClause
				.replaceAll(IFConstants.ocfBuyerGrpCd_Param, "'"
						+ inputParams.getOcfBuyerCd().trim() + I000044Constants.quryStng);
		whereClause = ((nullString).equals(inputParams.getPorCd())) ? whereClause
				.replaceAll(IFConstants.param_porCd, " ") : whereClause
				.replaceAll(IFConstants.porCd_Param, "'"
						+ inputParams.getPorCd().trim() + I000044Constants.quryStng);
		
		return whereClause;
	}
	
	/**
	 * This method gets executed after each step Execution
	 * To get the count of Reader, Writer
	 * Based on the count values  write the Log.
	 *
	 * @param stepExecution the step execution
	 */
	@AfterStep
	public ExitStatus afterStep(StepExecution stepExecution) {
		LOG.info(DOLLAR+INSIDE_METHOD+DOLLAR);
		LOG.info(STAR+STEP_ID + stepExecution.getId()+STAR);
		LOG.info(READ_COUNT + totalList.size());
		LOG.info(READ_SKIPPED_COUNT + stepExecution.getReadSkipCount());
		LOG.info(WRITE_COUNT + commonutility.getRowCount());
		LOG.info(WRITE_SKIPPED_COUNT + stepExecution.getWriteSkipCount());
		
		String remarks = "";
		
		if(totalList == null || totalList.isEmpty()) {
			remarks = PDMessageConsants.M00214.replace(PDConstants.ERROR_MESSAGE_1, ifFileId)
					 .replace(PDConstants.ERROR_MESSAGE_2, PDConstants.MONTHLY_PRODUCTION_SCHEDULE_TRN)
					 .replace(PDConstants.ERROR_MESSAGE_3, porCdStr)
					 .replace(PDConstants.ERROR_MESSAGE_4, buyerGrpCdStr);
			
		}else if (commonutility.getRowCount() > 0) {
			remarks= PDMessageConsants.M00225.replace(PDConstants.ERROR_MESSAGE_1, ifFileId)
					 .replace(PDConstants.ERROR_MESSAGE_2, porCdStr)
					 .replace(PDConstants.ERROR_MESSAGE_3, buyerGrpCdStr);
			commonutility.setWriteCount((int)commonutility.getRowCount());
			
		}else if(commonutility.getRowCount() == 0){
			remarks=  PDMessageConsants.M00164.replace(PDConstants.ERROR_MESSAGE_1, ifFileId)
					  .replace(PDConstants.ERROR_MESSAGE_2, PDConstants.Insert)
					  .replace(PDConstants.ERROR_MESSAGE_3, PDConstants.Common_data_layer);
			
		}else if (stepExecution.getExitStatus().getExitCode().equals(FAILED)) {
			remarks = M00226.replace(PDConstants.ERROR_MESSAGE_1, ifFileId)
					 .replace(PDConstants.ERROR_MESSAGE_2, porCdStr)
					 .replace(PDConstants.ERROR_MESSAGE_3, buyerGrpCdStr);
		}
		//Else condition will not occur as the execution falls in anyone of the about condition
		commonutility.setStatus(PDConstants.INTERFACE_UNPROCESSED_STATUS);
		commonutility.setRemarks(remarks);
		LOG.info(remarks);
	
		LOG.info(DOLLAR+OUTSIDE_METHOD+DOLLAR);
		return ExitStatus.COMPLETED;
	}

}
