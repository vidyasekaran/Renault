/*
 * System Name     :Post Dragon 
 * Sub system Name :Batch / Interface
 * Function ID     :PST-DRG-B000001/PST-DRG-R000001
 * Module          :SP SPEC
 * Process Outline :Validating the interface fields with Master and generating the error report
 *
 * <Revision History>
 * Date       Name(Company Name)             Description 
 * ---------- ------------------------------ ---------------------
 * 2015/07/25  	  @author(RNTBCI)               New Creation
 *
 */
package com.nissangroups.pd.b000067.processor;

import static com.nissangroups.pd.util.PDConstants.*;
import static com.nissangroups.pd.util.PDMessageConsants.M00003;
import static com.nissangroups.pd.util.PDMessageConsants.M00043;
import static com.nissangroups.pd.util.PDMessageConsants.M00076;
import static com.nissangroups.pd.util.PDMessageConsants.M00113;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.AfterStep;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;

import com.nissangroups.pd.b000067.repository.OfflineDateRepository;
import com.nissangroups.pd.common.CmnAfterStepDtls;
import com.nissangroups.pd.exception.PdApplicationNonFatalException;
import com.nissangroups.pd.model.TrnAoEtaPrmtr;
import com.nissangroups.pd.model.TrnAoEtaPrmtrPK;
import com.nissangroups.pd.model.TrnMnlDueDatePrmtr;
import com.nissangroups.pd.repository.OrdrTkBsPrdMstRepository;
import com.nissangroups.pd.util.CommonExcelItemWriter;
import com.nissangroups.pd.util.CommonUtil;
import com.nissangroups.pd.util.PDConstants;
import com.nissangroups.pd.util.PDMessageConsants;


/**
 * The Class B000067Processor.
 *
 * @author z001870
 */

public class OfflineDateProcessor implements
		ItemProcessor<TrnMnlDueDatePrmtr, List<TrnAoEtaPrmtr> > {

	/** Constant LOG. */
	private static final Log LOG = LogFactory.getLog
			(OfflineDateProcessor.class);

	/** Variable entity manager. */
	@PersistenceContext(name = PERSISTENCE_NAME )
	private EntityManager entityManager;

	/** Variable step execution id. */
	private String stepExecutionID;
	
	/** Variable environment. */
	@Autowired(required = false)
	Environment environment;
	
	
	private String porCd;
	private String targetMnth;
	
	private static List<Object[]> reportList = new ArrayList<Object[]>();
	
	@Autowired(required=false)
	private OrdrTkBsPrdMstRepository ordrTkBsPrdMstRepositoryObj;
	
	@Autowired(required=false)
	private OfflineDateRepository offlineDateRepository;
	
	
	
	
	/**
	 * In this method to get the Step Name and assign this value to some String value.
	 *
	 * @param stepExecution the step execution
	 * @throws PdApplicationNonFatalException 
	 */
	@BeforeStep
	public void beforeStep(StepExecution stepExecution) throws PdApplicationNonFatalException {
		stepExecutionID = stepExecution.getStepName();
		LOG.info(STEP_START);
		
		
		String ordrTkBsMnth = ordrTkBsPrdMstRepositoryObj.fetchLatestOrdrTkBsOrdDataForStageClose(porCd, "20");
		if(ordrTkBsMnth==null){
			LOG.info(STEP_ID + stepExecutionID);

			LOG.info(PDMessageConsants.M00159.replace(PDConstants.ERROR_MESSAGE_1,"B000067")
					.replaceAll(PDConstants.ERROR_MESSAGE_2, "Final Order Closed")
					.replaceAll(PDConstants.ERROR_MESSAGE_3, porCd)
					.replaceAll(PDConstants.ERROR_MESSAGE_4, "MONTHLY ORDER TAKE BASE PERIOD MST"));
			
			CommonUtil.stopBatch();
		}
		
		
		String query = buildQuery(ordrTkBsMnth);
		stepExecution.getJobExecution().getExecutionContext().put("dynamicQuery", query);
		

		LOG.info(STEP_ID + stepExecutionID);
	}

	/**
	 * After Step Complete to get the Record count of Read and Write
	 * Based on count values to capture the Log Informations.
	 *
	 * @param stepExecution the step execution
	 */
	@AfterStep
	public void afterStep(StepExecution stepExecution) {
		
		LOG.info(DOLLAR+INSIDE_METHOD+DOLLAR);
		
		LOG.info(STAR+STEP_ID + stepExecution.getId()+STAR);
		LOG.info(STAR+STEP_ID + stepExecution.getId()+STAR);
		LOG.info(READ_COUNT + stepExecution.getReadCount());
		LOG.info(READ_SKIPPED_COUNT + stepExecution.getReadSkipCount());
		LOG.info(WRITE_COUNT + stepExecution.getWriteCount());
		LOG.info(WRITE_SKIPPED_COUNT + stepExecution.getWriteSkipCount());
		
		int readCnt = stepExecution.getReadCount();
		int writeCnt = stepExecution.getWriteCount();

		if (readCnt == 0) {
			
			LOG.info(PDMessageConsants.M00160.replace(PDConstants.ERROR_MESSAGE_1,"B000067")
					.replaceAll(PDConstants.ERROR_MESSAGE_2, "Data Found")
					.replaceAll(PDConstants.ERROR_MESSAGE_3, porCd)
					.replaceAll(PDConstants.ERROR_MESSAGE_4, " MANUAL DUE DATE PARAMETER TRN"));
		}
		else if (stepExecution.getExitStatus().getExitCode().equals(FAILED)) {

			LOG.error(M00076.replace(PDConstants.ERROR_MESSAGE_1,"B000067"));
						
		}
		if (writeCnt > 0) {
			LOG.info(PDMessageConsants.M00189);
		}
		
		LOG.info(DOLLAR+OUTSIDE_METHOD+DOLLAR);
		
		

		createReport();
	}
	
	
	public void createReport(){
		String reportPath = environment.getProperty(PDConstants.B000067_REPORT_PATH);
		//String reportPath = "D:\\public\\B000067\\";
		
		 
	    DateFormat dateFormat = new SimpleDateFormat(DATE_TIME_FORMAT);
	    //reportPath = "D:\\Public\\B000018\\";
	    String dirPath = reportPath;
	    String fileName = "B000067_OFFLINEDATE_"+porCd+REPORT_SUFFIX+dateFormat.format(new Date())+FILE_EXT_XLS;
	    
	    File dir = new File(dirPath);
	    if(!dir.exists()) {
	        dir.mkdir();
	    }
	    
	    CommonExcelItemWriter excelItemWriter = new CommonExcelItemWriter();
	    excelItemWriter.setFilePath(dirPath+BACK_SLASH+fileName);
	    
	    excelItemWriter.setHeaders(new String[] {
	    		REPORT_HEADER_PRD_MNTH,REPORT_HEADER_OCF_REGION,REPORT_HEADER_MCREGION,
	    		REPORT_HEADER_BYR_GRP,REPORT_HEADER_BYR_CD,REPORT_HEADER_CAR_GRP,REPORT_HEADER_CAR_SRS,
	    		REPORT_HEADER_PRDN_FMLY_CD,REPORT_HEADER_END_ITEM,
	    		REPORT_HEADER_POT,REPORT_HEADER_SALES_NOTE,REPORT_HEADER_EX_NO,
	    		REPORT_HEADER_ORDER_TOTAL,REPORT_HEADER_VOLUME,REPORT_HEADER_FRM_DATE,
	    		REPORT_HEADER_TO_DATE,REPORT_HEADER_WARNING});
	    
	    try {
	    	Map<String,String> formatMap = new HashMap<String,String>();
	    	excelItemWriter.createReport(reportList,formatMap,"Error Report");
	    } catch (IOException e) {
	        LOG.error(EXCEPTION+e);
	        
	    }
	    
	    LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);

	}

	@Override
	public List<TrnAoEtaPrmtr> process(TrnMnlDueDatePrmtr item) throws Exception {
		List<TrnAoEtaPrmtr> trnAoEtaPrmtrObjList = new ArrayList<TrnAoEtaPrmtr>(); 
		
		List<Object[]> prdnOrdrExNoDtls =  offlineDateRepository.fetchmnthlyPrdnOrdrExNoDtls(item,true);
		if(prdnOrdrExNoDtls==null || prdnOrdrExNoDtls.isEmpty()){
			prdnOrdrExNoDtls =  offlineDateRepository.fetchmnthlyPrdnOrdrExNoDtls(item,false);
			 if(prdnOrdrExNoDtls==null || prdnOrdrExNoDtls.isEmpty()){
				 LOG.info(PDMessageConsants.M00076.replace(PDConstants.ERROR_MESSAGE_1,"B000067"));		 
			 } else {
				 trnAoEtaPrmtrObjList = populateTrnAoEtaPrmtr(item,prdnOrdrExNoDtls,false);
			 }
		
		} else {
			trnAoEtaPrmtrObjList = populateTrnAoEtaPrmtr(item,prdnOrdrExNoDtls,true);
		}
		
		if(trnAoEtaPrmtrObjList!= null && trnAoEtaPrmtrObjList.isEmpty()){
			trnAoEtaPrmtrObjList = null;
		}
		
		return trnAoEtaPrmtrObjList;
	}

	/**
	 * 
	 * @param ordrTkBsMnth
	 * @return
	 */
	public String buildQuery(String ordrTkBsMnth){
		
		List<String> prdMnths = CommonUtil.getProductionMonths(targetMnth, ordrTkBsMnth);
		String query ="select t from TrnMnlDueDatePrmtr t where t.id.porCd = '"+porCd+ "' and  t.id.ordrTakeBaseMnth = '"+ordrTkBsMnth+"'" ;
		
		StringBuilder prdMnthQuery =  new StringBuilder(" and t.id.prodMnth in ( ");
		int appendComma = 0;
		for(String prdMnth : prdMnths){
			if(appendComma!=0){
				prdMnthQuery.append(",");
			}
			appendComma++;
			prdMnthQuery.append("'");
			prdMnthQuery.append(prdMnth);
			prdMnthQuery.append("'");
		}
		prdMnthQuery.append(")");
		
		query = query + prdMnthQuery.toString();
		return query;
	}
	
	
	
	
	
	public List<TrnAoEtaPrmtr> populateTrnAoEtaPrmtr(TrnMnlDueDatePrmtr item , List<Object[]> prdnOrdrExNoDtls,boolean ordrQtyFlg){
		List<TrnAoEtaPrmtr> list =  new ArrayList<TrnAoEtaPrmtr>();
		
		for(Object[] prdnOrdrExNoDtl : prdnOrdrExNoDtls){
			Object[] errObj = new Object[17];
			int j = 0 ;
			TrnAoEtaPrmtr obj = new TrnAoEtaPrmtr();
			TrnAoEtaPrmtrPK objPk = new TrnAoEtaPrmtrPK();
			int i =0;
			objPk.setProdMnth(item.getId().getProdMnth());
			errObj[j++] = objPk.getProdMnth();
			obj.setOcfRegionCd(CommonUtil.convertObjectToString(prdnOrdrExNoDtl[i++]));
			errObj[j++] = obj.getOcfRegionCd();
			//mcregion cd
			errObj[j++] = CommonUtil.convertObjectToString(prdnOrdrExNoDtl[i++]);
			//buyer grp cd
			errObj[j++] = CommonUtil.convertObjectToString(prdnOrdrExNoDtl[i++]);
			//buyer cd
			errObj[j++] = CommonUtil.convertObjectToString(prdnOrdrExNoDtl[i++]);
			String carSrs = CommonUtil.convertObjectToString(prdnOrdrExNoDtl[i++]);
			String carGrp = carSrs.substring(0, 4);
			errObj[j++] = carGrp; 
			errObj[j++] = carSrs;
			
			 i = 5;
			 
			 
			obj.setProdFmyCd(CommonUtil.convertObjectToString(prdnOrdrExNoDtl[i++]));
			errObj[j++] = obj.getProdFmyCd();
			objPk.setAppldMdlCd(CommonUtil.convertObjectToString(prdnOrdrExNoDtl[i++]));
			
			objPk.setPckCd(CommonUtil.convertObjectToString(prdnOrdrExNoDtl[i++]));
			errObj[j++] = objPk.getAppldMdlCd()+objPk.getPckCd();
			objPk.setPorCd(porCd);
			
			objPk.setSpecDestnCd(CommonUtil.convertObjectToString(prdnOrdrExNoDtl[i++]));
			objPk.setExtClrCd(CommonUtil.convertObjectToString(prdnOrdrExNoDtl[i++]));
			objPk.setIntClrCd(CommonUtil.convertObjectToString(prdnOrdrExNoDtl[i++]));
			objPk.setExNo(CommonUtil.convertObjectToString(prdnOrdrExNoDtl[i++]));
			objPk.setProdPlntCd("JW");
			
			String potCd = CommonUtil.convertObjectToString(prdnOrdrExNoDtl[i++]);
			
			errObj[j++] =  potCd;
			//SalesNote NO
			errObj[j++] =  item.getId().getProdMnth().substring(5)+potCd;
			errObj[j++] = objPk.getExNo();
			
			
			String dueDateFrm = item.getDueDateFrm();
			if(dueDateFrm!=null){
				dueDateFrm = dueDateFrm.substring(4);
			}
			obj.setDateFrm1(dueDateFrm);
			String dueDateTO = item.getDueDateTo();
			if(dueDateTO!=null){
				dueDateTO = dueDateTO.substring(4);
			}
			obj.setDateTo1(dueDateTO);
			obj.setDestnClass1("2");
			obj.setId(objPk);
			
			String ordrQty =null;
			
			if(ordrQtyFlg){
				ordrQty = CommonUtil.convertObjectToString(prdnOrdrExNoDtl[i++]);
			}
		
			
			//Order Total
			errObj[j++] = ordrQty;
			//Volume Partial
			errObj[j++] = item.getDueDateOrdrQty().toString();
			
			errObj[j++] = dueDateFrm; 
			errObj[j++] = dueDateTO;
			
			if(ordrQty==null || ordrQty.equalsIgnoreCase("") || ordrQty.equalsIgnoreCase("0")){
				obj.setQty1(new BigDecimal(0));
				errObj[j++] = PDConstants.CM_OFFLINE_WARN1;
				//Warning : **Order Total is not existing, Order has not  been transmitted to Plant System
			} else {
				int prodOrdrQty = Integer.parseInt(ordrQty);
				if(item.getDueDateOrdrQty()==null){
					item.setDueDateOrdrQty(new BigDecimal(0));
				}
				if(prodOrdrQty<item.getDueDateOrdrQty().intValue()){
					obj.setQty1(new BigDecimal(prodOrdrQty));
					errObj[j++] = PDConstants.CM_OFFLINE_WARN2;
					//warning message Volume(Partial) is greater than Order Total, Order Total has been transmited to Plan Systemt
				} else {
					obj.setQty1(item.getDueDateOrdrQty());
					errObj[j++] = "";
					//Set Warning = Blank
				}
				
			}
			
			obj.setCrtdBy("B000067");
			obj.setUpdtdBy("B000067");
			if(obj.getQty1().intValue()>0){
				list.add(obj);
			}
			
			
			reportList.add(errObj);
		}
		
		
		return list;
	}
	
	
	
	/**
	 * Gets the entity manager.
	 *
	 * @return the entity manager
	 */
	public EntityManager getEntityManager() {
		return entityManager;
	}

	/**
	 * Sets the entity manager.
	 *
	 * @param entityManager the new entity manager
	 */
	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	public String getPorCd() {
		return porCd;
	}

	public void setPorCd(String porCd) {
		this.porCd = porCd;
	}

	public String getTargetMnth() {
		return targetMnth;
	}

	public void setTargetMnth(String targetMnth) {
		this.targetMnth = targetMnth;
	}
	
	
	
}
