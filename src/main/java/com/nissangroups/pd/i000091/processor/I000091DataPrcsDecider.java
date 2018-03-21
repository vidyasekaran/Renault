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
import static com.nissangroups.pd.util.PDConstants.INSIDE_METHOD;
import static com.nissangroups.pd.util.PDConstants.PERSISTENCE_NAME;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.job.flow.FlowExecutionStatus;
import org.springframework.batch.core.job.flow.JobExecutionDecider;
import org.springframework.beans.factory.annotation.Autowired;

import com.nissangroups.pd.i000091.output.I000091Bean;
import com.nissangroups.pd.i000091.output.I000091OutputBean;
import com.nissangroups.pd.i000091.util.I000091CommonUtil;
import com.nissangroups.pd.i000091.util.I000091Constants;
import com.nissangroups.pd.util.IFConstants;
import com.nissangroups.pd.util.IfCommonUtil;
import com.nissangroups.pd.util.PDConstants;
import com.nissangroups.pd.util.PDMessageConsants;

/**
 * This Class I000091DataPrcsDecider to decide the execution of steps based on the condition.
 * The return value will be used as a status to determine the next step in the job. 
 * 
 * @author z016127
 *
 */
public class I000091DataPrcsDecider implements JobExecutionDecider{

	/* Constant LOG */
	private static final Log LOG = LogFactory
			.getLog(I000091DataPrcsDecider.class.getName());
	
	/** Common utility service bean injection */
	@Autowired(required = false)
	IfCommonUtil commonutility;

	/** Stores entity manager */
	@PersistenceContext(name = PERSISTENCE_NAME)
	private EntityManager entityManager;
	
	/** I000091 Common utility service bean injection */
	@Autowired(required = false)
	private I000091CommonUtil commonUtil;
	
	/**Variable Order take Period Type Code */
	private Map<String,String> extrctPrdTpecd = new HashMap<String,String>();
	
	/**Variable Constraint Period Type Code */
	private Map<String,String> constrntPrdTpecd = new HashMap<String,String>();
	
	/**Variable Parameter Mst Error Message */
	private String prmtrMstErrorMsg;
	
	/**Variable Buyer Group Error Msg */
	private String byrGrpErrMsg;
	
	/**Variable Error Message */
	private String errorMsg;
	
	/**Variable Por Code */
	private String porCd;
	
	/**Variable Buyer Group Code */
	private String buyrGrpCd;
	
	/**
	 * This method is used to decide which step to execute next based on the Input parameter
	 * If return value is dataPrcsFailed it goes to step Fail else if Completed it goes to step
	 * 
	 * @param jobExecution
	 *            JobExecution object
	 * @param stepExecution
	 *            StepExecution object
	 * @return FlowExecutionStatus object
	 */
	@Override
	public FlowExecutionStatus decide(JobExecution jobExecution,
			StepExecution stepExecution) {
		
		LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR);
		
		/**Variable of PorCode */
		porCd = jobExecution.getJobParameters()
				.getString(IFConstants.POR_CD);
		
		/**Variable of Buyer Group Code */
		buyrGrpCd = jobExecution.getJobParameters()
				.getString(IFConstants.BUYER_GRP_CD);
		
		/**Variable of Ocf Buyer Group Code */
		String ocfBuyrGrpCd = jobExecution.getJobParameters()
				.getString(IFConstants.OCF_BUYER_GRP_CD);
		
		/**Variable of Ocf Region Code */
		String ocfRgnCd = jobExecution.getJobParameters()
				.getString(IFConstants.OCF_REGION_CD);
		
		/**Variable of QueryString */
		String queryString = jobExecution.getExecutionContext().getString("dynaQuery");
		
		prmtrMstErrorMsg = PDMessageConsants.M00354.replace(PDConstants.ERROR_MESSAGE_1, PDConstants.EXTRACT_PORCD + " " +porCd)
				.replace(PDConstants.ERROR_MESSAGE_2, PDConstants.PARAMETER_MST);
		
		byrGrpErrMsg = PDMessageConsants.M00354.replace(PDConstants.ERROR_MESSAGE_1, PDConstants.Interface_91_ID + " " +ocfRgnCd + "" +ocfBuyrGrpCd)
				.replace(PDConstants.ERROR_MESSAGE_2, PDConstants.BUYER_MST_BUYER_GROUP_CD);
		
		errorMsg = PDMessageConsants.M00354.replace(PDConstants.ERROR_MESSAGE_1, PDConstants.EXTRACT_PORCD + " " +porCd+" "+ocfRgnCd + " " +ocfBuyrGrpCd+ " " +buyrGrpCd)
				.replace(PDConstants.ERROR_MESSAGE_2, PDConstants.BUYER_MST +" "+ PDConstants.OCF_REGION_MST +" "+ PDConstants.POR_MST);
		
		commonutility.setStatus(PDConstants.INTERFACE_UNPROCESSED_STATUS);
		
			/**Query of OcfAutoAllocationFlag */
			List<Object[]> selectResultSet = commonUtil.createCustomQuery(queryString);
				if(selectResultSet != null && !(selectResultSet.isEmpty())){
					
					/**ResultSet of OcfAutoAllocationFlag */
					List<I000091OutputBean> outputBeanList = commonUtil.extrctOcfAllctnFlg(selectResultSet);
					
					String status = getBuyGrpDtls(outputBeanList);
					return new FlowExecutionStatus(status);
				}else{
					commonutility.setRemarks(errorMsg);
					LOG.error(errorMsg);
					return new FlowExecutionStatus(I000091Constants.DATAPRCSFAILED);
				}
		}
	
	/**
	 * Extract Buyer Group details
	 * 
	 * @param outputBeanList
	 * @return string
	 */
	public String getBuyGrpDtls(List<I000091OutputBean> outputBeanList){
		
		String status="";
		
		for(I000091OutputBean item : outputBeanList){
			
			/**ResultSet of Buyer Group Details */
			List<Object[]> result = commonUtil.exctByrGrpCd(item.getOcfRgnCd(), item.getOcfByrGrpCd());
			
			if(result != null && !(result.isEmpty())){
				List<I000091Bean> outputBean = commonUtil.extrctByrGrp(result);
					status = getPrdTypeCd(outputBean);
				return status;
			}else{
				commonutility.setRemarks(byrGrpErrMsg);
				LOG.error(byrGrpErrMsg);
				return I000091Constants.DATAPRCSFAILED;
			}
		}
		return I000091Constants.COMPLETED;

	}
	
	/**
	 * Extract Order take and Constraint period type details 
	 * 
	 * @param outputBean
	 * @return string
	 */
	public String getPrdTypeCd(List<I000091Bean> outputBean){
		
		String status="";
		for(I000091Bean arr : outputBean){
			if(buyrGrpCd != null && !("").equals(buyrGrpCd) && !("*").equals(buyrGrpCd)){
				status = extrctPrdTypeDtls(buyrGrpCd);
			}else{
				status = extrctPrdTypeDtls(arr.getByrGrpCd());
			}
			
			return status;
		}
		return I000091Constants.COMPLETED;
	}
	
	/**
	 * Extract Order take and Constraint period type details and add the ResultSet to map
	 * 
	 * @param byrGrpCd	the buyer group code
	 * @return string
	 */
	public String extrctPrdTypeDtls(String byrGrpCd){
		
		/** ResultSet of Order take period type */
		List ordrTkeTyp = commonUtil.extrctOrdrTakeTyp(porCd, byrGrpCd);
		if(ordrTkeTyp != null && !(ordrTkeTyp.isEmpty()))
		{
			extrctPrdTpecd.put(byrGrpCd, ordrTkeTyp.get(0).toString());
		}else{
			commonutility.setRemarks(prmtrMstErrorMsg);
			LOG.error(prmtrMstErrorMsg);
			return I000091Constants.DATAPRCSFAILED;
		}
		
		/** ResultSet of Constraint period type */
		List constrPrdTyp = commonUtil.extrctCnstntPrdTyp(porCd, byrGrpCd);
		if(constrPrdTyp != null && !(constrPrdTyp.isEmpty()))
		{
			constrntPrdTpecd.put(byrGrpCd, constrPrdTyp.get(0).toString());
		}else{
			commonutility.setRemarks(prmtrMstErrorMsg);
			LOG.error(prmtrMstErrorMsg);
			return I000091Constants.DATAPRCSFAILED;
		}
		return I000091Constants.COMPLETED;
	}
		
	public EntityManager getEntityManager() {
		return entityManager;
	}

	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}
	
	/**
	 * Get Order take Period Type Code based on the key returns the value
	 * 
	 * @param key the key
	 * @return string
	 */
	public String getOrdrTakProdType(String key) {
		String message = null;
		if (extrctPrdTpecd != null) {
			message = extrctPrdTpecd.get(key);
		}
		return message;
	}
	
	/**
	 * Get Constraint Period Type Code based on the key returns the value
	 * 
	 * @param key the key
	 * @return string
	 */
	public String getConstntProdTyp(String key) {
		String message = null;
		if (constrntPrdTpecd != null) {
			message = constrntPrdTpecd.get(key);
		}
		return message;
	}
}
