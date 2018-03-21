/*
 * System Name     : Post Dragon 
 * Sub system Name : I Interface
 * Function ID     : PST_DRG_I000091
 * Module          : OR Ordering
 * Process Outline : Send _Weekly_OCF_to_NSC(Standard_layout)
 *
 * <Revision History>
 * Date       Name(Company Name)             Description 
 * ---------- ------------------------------ ---------------------
 * @Date      z016127(RNTBCI)               New Creation
 *
 */
package com.nissangroups.pd.i000091.processor;

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
import static com.nissangroups.pd.util.PDMessageConsants.M00076;
import static com.nissangroups.pd.util.PDMessageConsants.M00113;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.AfterStep;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;

import com.nissangroups.pd.i000091.output.I000091Bean;
import com.nissangroups.pd.i000091.output.I000091OutputBean;
import com.nissangroups.pd.i000091.output.I000091PatternOutputBean;
import com.nissangroups.pd.i000091.process.ExctByrGrpWklyOcfProcess;
import com.nissangroups.pd.i000091.process.ExctRegnlWklyOcfProcess;
import com.nissangroups.pd.i000091.util.I000091CommonUtil;
import com.nissangroups.pd.i000091.util.I000091Constants;
import com.nissangroups.pd.model.CmnFileHdr;
import com.nissangroups.pd.model.CmnFileHdrPK;
import com.nissangroups.pd.model.CmnInterfaceData;
import com.nissangroups.pd.model.CmnInterfaceDataPK;
import com.nissangroups.pd.util.CommonUtil;
import com.nissangroups.pd.util.IFConstants;
import com.nissangroups.pd.util.IfCommonUtil;
import com.nissangroups.pd.util.PDConstants;
import com.nissangroups.pd.util.PDMessageConsants;

/**
 *  This class I000091Processor is to process the extracted Regional Weekly & Buyer Group Weekly data 
 *  and insert the data into Common Interface data
 *
 * @author z016127
 */
public class I000091Processor implements
		ItemProcessor<I000091OutputBean, List<CmnInterfaceData>> {

	/** Constant LOG. */
	private static final Log LOG = LogFactory.getLog
			(I000091Processor.class);

	/** Common utility service bean injection */
	@Autowired(required = false)
	IfCommonUtil commonutility;

	/** ExctRegnlWklyOcfProcess bean injection */
	@Autowired(required = false)
	private ExctRegnlWklyOcfProcess exctRegnlWklyOcfProcess;
	
	/** ExctByrGrpWklyOcfProcess bean injection */
	@Autowired(required = false)
	private ExctByrGrpWklyOcfProcess exctByrGrpWklyOcfProcess;
	
	/** I000091DataPrcsDecider bean injection */
	@Autowired(required = false)
	private I000091DataPrcsDecider dataPrcsDecider;
	
	/** I000091CommonUtil bean injection */
	@Autowired(required = false)
	private I000091CommonUtil commonUtil;

	/** Variable Common Interface List */
	private List<CmnInterfaceData> totalList = new ArrayList<CmnInterfaceData>();
	
	/** Variable  PatternOutputBean list */
	private List<I000091PatternOutputBean> finalList;
	
	/** Variable entity manager. */
	@PersistenceContext(name = PERSISTENCE_NAME)
	private EntityManager entityManager;
	
	/** Variable  Sequence number */
	private long seqNo;
	
	/** Variable  Por code */
	private String porCd;
	
	/** Variable  Order take base month */
	private String ordrTkeBseMnth;
	
	/** Variable Buyer Group Code */
	private String buyrGrpCd;
	
	/** Variable  Interface Field Id */
	private String ifFileId;
	
	/** Variable is data exist */
	private boolean isDataExist = false;
	
	/**Variable data length */ 
	private long dataLength = 0;
	
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
		
		ifFileId = jobExec.getJobParameters().getString(
				IFConstants.INTERFACE_FILE_ID);
		porCd = jobExec.getJobParameters()
				.getString(IFConstants.POR_CD);
		buyrGrpCd = jobExec.getJobParameters()
				.getString(IFConstants.BUYER_GRP_CD);
		seqNo = (long)jobExec.getExecutionContext().get(IFConstants.SEQ_NO);
		
		ordrTkeBseMnth = (String)jobExec.getExecutionContext().get("ordrTkeBseMnth");
		
		LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);
	}

	/** 
	 * This method is to process the extracted Regional Weekly and Buyer Group Weekly data 
	 * and insert the list into Common Interface data.
	 * P0002.4.1, P0002.4.2,P0002.4.3,P0002.4.4
	 * P0002.5.1, P0002.5.2, P0002.5.3,P0002.5.4
	 * 
	 * @param item 
	 * 				I000091OutputBean
	 * @return the list of CmnInterfaceData 
	 * 						the class
	 * @throws Exception the exception
	 */
	@Override
	public List<CmnInterfaceData> process(I000091OutputBean item) throws Exception {

		LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR);

		/**Variable PatternOutputBean list */
		finalList = new ArrayList<I000091PatternOutputBean>();
		
		/**Variable Common Interface data list */
		totalList = new ArrayList<CmnInterfaceData>();
		
		/**P0002.3 ResultSet of Buyer Group Code */
		List<Object[]> result = commonUtil.exctByrGrpCd(item.getOcfRgnCd(), item.getOcfByrGrpCd());
		
		if(result != null && !(result.isEmpty())){
			List<I000091Bean> byrGrpList = commonUtil.extrctByrGrp(result);
			
			if(result.size() == 1){
				getRgnlWklyList(item, byrGrpList.get(0));
			}else{
				getByrGrpList(item, byrGrpList);
			}
		}
		
		if(finalList != null && !(finalList.isEmpty())){
			for(I000091PatternOutputBean patternOutput : finalList){
				getCmnInterfaceData(patternOutput);
			}
		}
			
		LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);

		return totalList;
	}
	
	/**P0003 Insert the extracted weekly and monthly schedule data
	 * into Common Interface Data
	 * Fetch the rowcount, sequence number, Interface file id from
	 * the context
	 * 
	 * @param I000091PatternOutputBean
	 */
	public void getCmnInterfaceData(I000091PatternOutputBean patternOutput){
		
		LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR);
		
		long rowCount = commonutility.getRowCount() + 1;
		
			/**Variable Constraint Period Type */
			String value = dataPrcsDecider.getConstntProdTyp(getBuyerGrpCd(patternOutput));
			
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
			cmnIfData.setCol1(patternOutput.getPorCd());
			cmnIfData.setCol2(patternOutput.getCarSrs());
			cmnIfData.setCol3(patternOutput.getFeatCd());
			cmnIfData.setCol4(patternOutput.getFeatTypeCd());
			cmnIfData.setCol5(dataPrcsDecider.getOrdrTakProdType(getBuyerGrpCd(patternOutput)));
			cmnIfData.setCol6(ordrTkeBseMnth);
			cmnIfData.setCol7(value);
			cmnIfData.setCol8(getProdMnth(value, patternOutput));
			cmnIfData.setCol9(patternOutput.getBuyerGrpCd());
			cmnIfData.setCol10(!("").equalsIgnoreCase(patternOutput.getPlantCd())? patternOutput.getPlantCd() : "  ");
			cmnIfData.setCol11(!("").equalsIgnoreCase(patternOutput.getLineClass())? patternOutput.getLineClass() : "  ");
			cmnIfData.setCol12((patternOutput.getRgnlOcfUsgQty() == null || patternOutput.getRgnlOcfUsgQty() == "0" ) ? "0" :patternOutput.getRgnlOcfUsgQty());
			cmnIfData.setCol13(patternOutput.getRgnlOcfLmtQty());
			cmnIfData.setCol14(patternOutput.getCrtdBy());
			cmnIfData.setCol15(patternOutput.getCrtdDt());
			cmnIfData.setCol16(patternOutput.getUpdtdBy());
			cmnIfData.setCol17(patternOutput.getUpdtdDt());
			totalList.add(cmnIfData);
			
			commonutility.setRowCount(rowCount);
			LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);
	}
	
	/**
	 * Method to get the Production Month
	 * compares the value with constant and return the string
	 * @param value
	 * 				the string
	 * @param tmp
	 * 				the I000091PatternOutputBean
	 * @return string
	 */
	public String getProdMnth(String value, I000091PatternOutputBean patternOutput){
		
		String prodMnth ="";
		
		if(value != null && (I000091Constants.M).equalsIgnoreCase(value)){
			prodMnth = patternOutput.getProdMnth();	
		}else if(value != null && (I000091Constants.W).equalsIgnoreCase(value)){
			prodMnth = patternOutput.getProdMnth() + patternOutput.getProdWkNo() + patternOutput.getProdDayNo();	
		}
		return prodMnth;
	}
	
	/** 
	 * Method to get the Buyer Group Code
	 * Check the buyer group data and return the string 
	 * 
	 * @param tmp
	 * 			the I000091PatternOutputBean
	 * @return string
	 */
	public String getBuyerGrpCd(I000091PatternOutputBean patternOutput){
		
		return (buyrGrpCd == null || ("").equals(buyrGrpCd) || ("*").equals(buyrGrpCd)) ? patternOutput.getBuyerGrpCd() :  buyrGrpCd;
		
	}
	
	/**
	 * Extraction of Regional Weekly Ocf details based on the pattern
	 * P0002.4.1, P0002.4.2, P0002.4.3, P0002.4.4
	 *  
	 * @param item
	 * 				the I000091OutputBean
	 * @param bean
	 * 				the I000091Bean
	 */
	public void getRgnlWklyList(I000091OutputBean item, I000091Bean bean){
		
		String errMsg = PDMessageConsants.M00354.replace(PDConstants.ERROR_MESSAGE_1, PDConstants.EXTRACT_PORCD + " "+porCd+ " "+item.getOcfRgnCd() + " "+item.getOcfByrGrpCd())
				.replace(PDConstants.ERROR_MESSAGE_2, PDConstants.REGIONAL_WEEKLY_OCF_LIMIT_TRN);
		String alloctnFlg = "1";
		if(item.getOcfAutoAllctnFlg() != null && (alloctnFlg).equalsIgnoreCase(item.getOcfAutoAllctnFlg())){
			String queryString = "";	
			
			/** Extract Regional Weekly Ocf based on Pattern flag */
			switch(bean.getPtrnFlg()){
				case "1":
					queryString = exctRegnlWklyOcfProcess.getRegnlWklyOcfForPtrn1(item,porCd,ordrTkeBseMnth);
					break;
		
				case "2":
					queryString = exctRegnlWklyOcfProcess.getRegnlWklyOcfForPtrn2(item,porCd,ordrTkeBseMnth);
					break;
		
				case "3":
					queryString = exctRegnlWklyOcfProcess.getRegnlWklyOcfForPtrn3(item,porCd,ordrTkeBseMnth);
					break;
		
				case "4":
					queryString =  exctRegnlWklyOcfProcess.getRegnlWklyOcfForPtrn4(item,porCd,ordrTkeBseMnth);
					break;
				
				default:
					LOG.error(I000091Constants.errMsg);
					break;
			}
			try{
				Query query = entityManager
						.createNativeQuery(queryString);
				/**ResultSet of Regional Weekly Ocf*/
				List<Object[]> selectResultSet = query.getResultList();
				
				if(selectResultSet != null && !(selectResultSet.isEmpty())){
					isDataExist = true;
					dataLength = dataLength + selectResultSet.size();
					finalList.addAll(getWklyOcfList(selectResultSet,bean.getByrGrpCd()));
				}else{
					commonutility.setStatus(PDConstants.INTERFACE_UNPROCESSED_STATUS);
					commonutility.setRemarks(errMsg);
					LOG.error(errMsg);
				}
			}catch(Exception e){
				LOG.error("Exception " +e);
			}
			
		}
	}
	
	/**
	 * Extraction of Buyer Group Weekly Ocf data based on the Pattern
	 * P0002.5.1, P0002.5.2, P0002.5.3, P0002.5.4
	 * 
	 * @param item
	 * 				the I000091OutputBean
	 * @param bean
	 * 				the list of I000091Bean
	 */
	public void getByrGrpList(I000091OutputBean item, List<I000091Bean> byrGrpList){
		
		String alloctnFlg ="0";
		if(item.getOcfAutoAllctnFlg() != null && (alloctnFlg).equalsIgnoreCase(item.getOcfAutoAllctnFlg())){
			String queryString = "";
			
			for(I000091Bean bean: byrGrpList){
				/** Extract Buyer Group Weekly Ocf based on Pattern flag */
				switch(bean.getPtrnFlg()){
					case "1":
						queryString = exctByrGrpWklyOcfProcess.getByrGrpWklyOcfForPtrn1(porCd,ordrTkeBseMnth,bean);
						break;
			
					case "2":
						queryString = exctByrGrpWklyOcfProcess.getByrGrpWklyOcfForPtrn2(porCd,ordrTkeBseMnth,bean);
						break;
			
					case "3":
						queryString = exctByrGrpWklyOcfProcess.getByrGrpWklyOcfForPtrn3(porCd,ordrTkeBseMnth,bean);
						break;
			
					case "4":
						queryString = exctByrGrpWklyOcfProcess.getByrGrpWklyOcfForPtrn4(porCd,ordrTkeBseMnth,bean);
						break;
					
					default:
						LOG.error(I000091Constants.errMsg);
						break;	
				}
				getByrGrpWklyOcfList(queryString, bean);
			}
		}
	}
	
	/**
	 * Extract Buyer Group Weekly Ocf 
	 * @param queryString
	 * 					the string
	 * @param bean
	 * 				I000091Bean
	 */
	public void getByrGrpWklyOcfList(String queryString, I000091Bean bean){
		
			Query query = entityManager
					.createNativeQuery(queryString);
			/**ResultSet of Buyer Group Weekly Ocf*/
			List<Object[]> selectResultSet = query.getResultList();
			
			if(selectResultSet != null && !(selectResultSet.isEmpty())){
				isDataExist = true;
				dataLength = dataLength + selectResultSet.size();
				finalList.addAll(getWklyOcfList(selectResultSet,bean.getByrGrpCd()));
			}else{
				commonutility.setStatus(PDConstants.INTERFACE_UNPROCESSED_STATUS);
				commonutility.setRemarks(PDMessageConsants.M00354.replace(PDConstants.ERROR_MESSAGE_1, PDConstants.EXTRACT_PORCD + " "+porCd+ " "+bean.getByrGrpCd())
							.replace(PDConstants.ERROR_MESSAGE_2, PDConstants.BUYER_GROUP_WEEKLY_OCF_LIMIT_TRN));
				LOG.error(PDMessageConsants.M00354.replace(PDConstants.ERROR_MESSAGE_1, PDConstants.EXTRACT_PORCD + " "+porCd+ " "+bean.getByrGrpCd())
							.replace(PDConstants.ERROR_MESSAGE_2, PDConstants.BUYER_GROUP_WEEKLY_OCF_LIMIT_TRN));
			}
	}
	
	/**
	 * Method to get the I000091PatternOutputBean list
	 * Set the object value to  the bean
	 * 
	 * @param selectResultSet
	 * 					list of object
	 * @param buyerGrpCd
	 * 					the string
	 * @return list of I000091PatternOutputBean
	 */
	public List<I000091PatternOutputBean> getWklyOcfList(List<Object[]> selectResultSet, String buyerGrpCd){
		
		List<I000091PatternOutputBean> outputBeanList = new ArrayList<I000091PatternOutputBean>();
		 for (Object[] tmpArr : selectResultSet){
			
			 /**Variable I000091PatternOutputBean */
			 I000091PatternOutputBean outputBean = new I000091PatternOutputBean();
			 outputBean.setPorCd(CommonUtil.convertObjectToString(tmpArr[1]));//Por code
			 outputBean.setCarSrs(CommonUtil.convertObjectToString(tmpArr[2]));//Car series
			 outputBean.setFeatCd(CommonUtil.convertObjectToString(tmpArr[3]));//Feature code
			 outputBean.setFeatTypeCd(CommonUtil.convertObjectToString(tmpArr[4]));//Feature type code
			 outputBean.setProdMnth(CommonUtil.convertObjectToString(tmpArr[5]));//Production month
			 outputBean.setProdWkNo(CommonUtil.convertObjectToString(tmpArr[6]));//Production week no
			 outputBean.setProdDayNo(CommonUtil.convertObjectToString(tmpArr[7]));//Production day no
			 outputBean.setLineClass(CommonUtil.convertObjectToString(tmpArr[8]));//Line class
			 outputBean.setPlantCd(CommonUtil.convertObjectToString(tmpArr[9]));//Plant code
			 outputBean.setBuyerGrpCd(buyerGrpCd);
			 outputBean.setRgnlOcfLmtQty(CommonUtil.convertObjectToString(tmpArr[10]));//Regional ocf limit quantity
			 outputBean.setRgnlOcfUsgQty(CommonUtil.convertObjectToString(tmpArr[11]));//Regional ocf usage quantity
			 outputBean.setCrtdBy(CommonUtil.convertObjectToString(tmpArr[12]));//created by
			 outputBean.setCrtdDt(CommonUtil.convertObjectToString(tmpArr[13]));//created date
			 outputBean.setUpdtdBy(CommonUtil.convertObjectToString(tmpArr[14]));// updated by
			 outputBean.setUpdtdDt(CommonUtil.convertObjectToString(tmpArr[15]));// updated date
			 
			 outputBeanList.add(outputBean);
		 }
		return outputBeanList;
	}
	
	/**
	 * This method gets executed after each step Execution to get the count of Reader, Writer
	 * Based on the count values  and write the Log.
	 *
	 * @param stepExecution the step execution
	 */
	@AfterStep
	public void afterStep(StepExecution stepExecution) {
		LOG.info(DOLLAR+INSIDE_METHOD+DOLLAR);
		LOG.info(STAR+STEP_ID + stepExecution.getId()+STAR);
		LOG.info(READ_COUNT + dataLength);
		LOG.info(READ_SKIPPED_COUNT + stepExecution.getReadSkipCount());
		LOG.info(WRITE_COUNT + commonutility.getRowCount());
		LOG.info(WRITE_SKIPPED_COUNT + stepExecution.getWriteSkipCount());

		if (stepExecution.getExitStatus().getExitCode().equals(FAILED)) {

			commonutility.setStatus(PDConstants.INTERFACE_FAILURE_STATUS);
			commonutility.setRemarks(M00076.replace(PDConstants.ERROR_MESSAGE_1, stepExecution
					.getFailureExceptions().toString()));
			LOG.error(M00076.replace(PDConstants.ERROR_MESSAGE_1,stepExecution.getFailureExceptions().toString()));
						
		}
		else if (isDataExist && commonutility.getRowCount() == dataLength) {
			//write count in header
			commonutility.setWriteCount(stepExecution.getWriteCount());
			commonutility.setStatus(PDConstants.INTERFACE_UNPROCESSED_STATUS);
			commonutility.setRemarks(M00113);
			LOG.info(M00113);
		}
		//Else condition will not occur as the execution falls in anyone of the above conditions
		LOG.info(DOLLAR+OUTSIDE_METHOD+DOLLAR);
	}

}
