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
package com.nissangroups.pd.i000091.util;

import static com.nissangroups.pd.util.PDConstants.DOLLAR;
import static com.nissangroups.pd.util.PDConstants.OUTSIDE_METHOD;
import static com.nissangroups.pd.util.PDConstants.PERSISTENCE_NAME;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.nissangroups.pd.i000091.output.I000091Bean;
import com.nissangroups.pd.i000091.output.I000091OutputBean;
import com.nissangroups.pd.util.CommonUtil;
import com.nissangroups.pd.util.IFConstants;
import com.nissangroups.pd.util.IfCommonUtil;
import com.nissangroups.pd.util.PDConstants;
import com.nissangroups.pd.util.PDMessageConsants;

/**
 * This classes utility methods are used by I000091DataPrcsDecider and I000091Processor to extract information 
 * such as OcfAutoAllocationFlag,OrderTakePeriodTypeCode etc.
 * 
 * @author z016127
 *
 */
public class I000091CommonUtil {

	/* Constant LOG */
	private static final Log LOG = LogFactory
			.getLog(I000091CommonUtil.class.getName());

	/** Common utility service bean injection */
	@Autowired(required = false)
	IfCommonUtil commonutility;
	
	/** Stores entity manager */
	@PersistenceContext(name = PERSISTENCE_NAME)
	private EntityManager entityManager;

	/**
	 * Creates the query to extract OcfAutoAllocationFlag.
	 * 
	 * @param queryString 
	 * 
	 * @return the result set
	 */
	public List<Object[]> createCustomQuery(String queryString) {

		Query query2 = entityManager
				.createNativeQuery(queryString);
		List<Object[]> resultSet = query2.getResultList();
		
		LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);

		return resultSet;
	}
	
	
	/**
	 * P0002.6.1 Extract OrderTakePeriodTypeCode based on the por code and buyer group code
	 * 
	 * @param porCd
	 * 				the string
	 * @param buyrGroupCd
	 * 				the string
	 * @return the result set
	 */
	@SuppressWarnings("unchecked")
	public List extrctOrdrTakeTyp(String porCd, String buyrGroupCd){
		
		List result = null;
		try{
			Query query1 = entityManager
					.createNativeQuery(I000091QueryConstants.getOrdrTkePrdTyp.toString());
			query1.setParameter(IFConstants.POR_CD, ("*").equals(porCd)? "" :porCd.trim());
			query1.setParameter(IFConstants.BUYER_GRP_CD, buyrGroupCd.trim());
			result =  query1.getResultList();
		}catch(Exception e){
			LOG.error("**** extrctOrdrTakeTyp ****" +e + IFConstants.POR_CD + porCd +IFConstants.BUYER_GRP_CD+ buyrGroupCd+e.getMessage());
		}
		
		return result;
	}
	
	/**
	 * P0002.6.2 Extract ConstraintPeriodTypeCode based on the por code and buyer group code
	 * 
	 * @param porCd
	 * 				the string
	 * @param buyrGroupCd
	 * 				the string
	 * @return the result set
	 */
	@SuppressWarnings("unchecked")
	public List extrctCnstntPrdTyp(String porCd, String buyrGroupCd){
		
		List resultSet = null ;
		
		try{
			Query query2 = entityManager
					.createNativeQuery(I000091QueryConstants.getCnstrntPrdTyp.toString());
			query2.setParameter(IFConstants.POR_CD, ("*").equals(porCd)? "" :porCd.trim());
			query2.setParameter(IFConstants.BUYER_GRP_CD, buyrGroupCd.trim());
			resultSet = query2.getResultList();
		}catch(Exception e){
			LOG.error("**** extrctCnstntPrdTyp ****" +e +IFConstants.POR_CD + porCd +IFConstants.BUYER_GRP_CD+ buyrGroupCd+e.getMessage());
		}
		return resultSet;
	}
	
	/**
	 * P0002.3 Extract Buyer Group Code based on the ocfRgnCd and ocfBuyrGrpCd
	 * 
	 * @param ocfRgnCd
	 * 					the string
	 * @param ocfBuyrGrpCd
	 * 					the string
	 * @return
	 */
	public List<Object[]> exctByrGrpCd(String ocfRgnCd, String ocfBuyrGrpCd){
		
		List<Object[]> resultSet = null;
		StringBuilder queryString = new StringBuilder().append(I000091Constants.queryString+I000091QueryConstants.extractBuyGrpCd
				.toString());
		
		String whereClause = "";
		whereClause = I000091QueryConstants.whereCondition.toString();
		whereClause = (("").equals(ocfRgnCd) || ("*").equals(ocfRgnCd)) ? whereClause
				.replaceAll(IFConstants.param_ocfRegionCd, " ") : whereClause
				.replaceAll(IFConstants.ocfRegionCd_Param,
						"'" + ocfRgnCd.trim() + I000091Constants.AND_QRYSTRNG);
		whereClause = (("").equals(ocfBuyrGrpCd) || ("*").equals(ocfBuyrGrpCd)) ? whereClause
				.replaceAll(IFConstants.param_ocfBuyerGrpCd, " ") : whereClause
				.replaceAll(IFConstants.ocfBuyerGrpCd_Param,
						"'" + ocfBuyrGrpCd.trim() + I000091Constants.AND_QRYSTRNG);
		
		queryString.append("("+ whereClause + ")");
		int indx = queryString.toString().lastIndexOf("AND");
		queryString = queryString.replace(indx, indx + 3, " ");
		queryString.append(") a");		
		
		try{
		Query query2 = entityManager
				.createNativeQuery(queryString.toString());
		
		 resultSet = query2.getResultList();
		}catch(Exception e){
			
			LOG.error("Exception :" +e);
		}
		return resultSet;
	}
	
	/**
	 * P0002.3 Extract Pattern Flag based on the buyer group code
	 * 
	 * @param buyrGroupCd
	 * 					the string
	 * @return the string
	 */
	@SuppressWarnings("unchecked")
	public String getPatternFlag(String buyrGroupCd){
		
		String prtnFlg = "";
		try{
			Query query1 = entityManager
					.createNativeQuery(I000091QueryConstants.getPatrnFlg.toString());
			query1.setParameter(IFConstants.BUYER_GRP_CD, buyrGroupCd.trim());
			List result =  query1.getResultList();
			if(result != null &&  !(result.isEmpty())){
				prtnFlg = (String)result.get(0);
			}else{
				commonutility.setRemarks(PDMessageConsants.M00354.replace(PDConstants.ERROR_MESSAGE_1, IFConstants.BUYER_GRP_CD + " " +buyrGroupCd.trim())
						.replace(PDConstants.ERROR_MESSAGE_2, PDConstants.PARAMETER_MST));
				LOG.error(PDMessageConsants.M00354.replace(PDConstants.ERROR_MESSAGE_1, IFConstants.BUYER_GRP_CD + " " +buyrGroupCd.trim())
						.replace(PDConstants.ERROR_MESSAGE_2, PDConstants.PARAMETER_MST));
			}
		}catch(Exception e){
			LOG.error("**** getPatternFlag ****" +e +IFConstants.BUYER_GRP_CD+ buyrGroupCd+e.getMessage());
		}
		
		return prtnFlg;
	}
	
	/**
	 * Extract Ocf Auto Allocation Flag and store in the List
	 * 
	 * @param selectResultSet
	 * 					the list of object
	 * 
	 * @return the list of I000091OutputBean
	 */
	public List<I000091OutputBean> extrctOcfAllctnFlg(List<Object[]> selectResultSet){
		/** Variable I000091OutputBean List*/
		List<I000091OutputBean> outputBeanList = new ArrayList<I000091OutputBean>();
		for(Object[] item : selectResultSet){
			/** Variable I000091OutputBean*/
			I000091OutputBean outputBean= new I000091OutputBean();
			outputBean.setOcfAutoAllctnFlg(CommonUtil.convertObjectToString(item[1]));// Ocf Auto allocation flag
			outputBean.setOcfRgnCd(CommonUtil.convertObjectToString(item[2]));//Ocf region code
			outputBean.setOcfByrGrpCd(CommonUtil.convertObjectToString(item[3])); //Ocf buyer group code
			outputBeanList.add(outputBean);
		}
		return outputBeanList;
	}
	
	/**
	 * Extract Buyer Group and store in the List
	 * 
	 * @param selectResultSet
	 * 				the list of object
	 * @return the list of I000091Bean
	 */
	public List<I000091Bean> extrctByrGrp(List<Object[]> selectResultSet){
		/** Variable I000091Bean List*/
		List<I000091Bean> outputBeanList = new ArrayList<I000091Bean>();
		for(Object[] item : selectResultSet){
			/** Variable I000091Bean */
			I000091Bean outputBean= new I000091Bean();
			outputBean.setByrGrpCd(CommonUtil.convertObjectToString(item[1]));//Buyer Group Code
			outputBean.setPtrnFlg(CommonUtil.convertObjectToString(item[2])); // Pattern flag
			outputBeanList.add(outputBean);
		}
		return outputBeanList;
	}
}
