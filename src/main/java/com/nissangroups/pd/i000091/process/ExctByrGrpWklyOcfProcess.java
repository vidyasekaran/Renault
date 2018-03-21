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
package com.nissangroups.pd.i000091.process;

import org.springframework.beans.factory.annotation.Autowired;

import com.nissangroups.pd.i000091.output.I000091Bean;
import com.nissangroups.pd.i000091.util.I000091Constants;
import com.nissangroups.pd.i000091.util.I000091QueryConstants;
import com.nissangroups.pd.util.IFConstants;
import com.nissangroups.pd.util.IfCommonUtil;

/**
 * This class is used to generate query for extracting the Buyer Group Weekly Ocf limit 
 * based on the Pattern.
 * 
 * @author z016127
 *
 */
public class ExctByrGrpWklyOcfProcess {

	/** Common utility service bean injection */
	@Autowired(required = false)
	IfCommonUtil commonutility;
	
	/**Variable Query String */
	private StringBuilder finalQuery;
	
	/**
	 * Instantiates a new ExctByrGrpWklyOcfProcess.
	 */
	public ExctByrGrpWklyOcfProcess() {
	}
	
	/**
	 * P0002.5.1 Query to extract Buyer Group Weekly for Pattern 1
	 * @param porCd
	 * 				the string
	 * @param ordrTkeBseMnth
	 * 				the string
	 * @param bean
	 * 				I000091Bean
	 * @return the string
	 */
	public String getByrGrpWklyOcfForPtrn1(String porCd, String ordrTkeBseMnth, I000091Bean bean){
		
			finalQuery = new StringBuilder().append(I000091Constants.queryString +I000091QueryConstants.byrGrpWklyPtrn1
					.toString());
			StringBuilder rgnlWklyOcfQuery = getBaseQuery(porCd,ordrTkeBseMnth,bean);
			rgnlWklyOcfQuery.append(I000091QueryConstants.byrGrpWklyPtrn1Cndtn.toString());
			rgnlWklyOcfQuery.append(") a");					
			
		return rgnlWklyOcfQuery.toString();
	}
	
	/**
	 * P0002.5.2 Query to extract Buyer Group Weekly for Pattern 2
	 * @param porCd
	 * 				string
	 * @param ordrTkeBseMnth
	 * 				string
	 * @param bean
	 * 				I000091Bean
	 * @return the string
	 */
	public String getByrGrpWklyOcfForPtrn2(String porCd,String ordrTkeBseMnth, I000091Bean bean){
		
			finalQuery = new StringBuilder().append(I000091Constants.queryString +I000091QueryConstants.byrGrpWklyPtrn2
					.toString());
			StringBuilder rgnlWklyOcfQuery = getBaseQuery(porCd,ordrTkeBseMnth,bean);
			rgnlWklyOcfQuery.append(I000091QueryConstants.byrGrpWklyPtrn2Cndtn.toString());
			rgnlWklyOcfQuery.append(") a");					
			
		return rgnlWklyOcfQuery.toString();
	}
	
	
	/**
	 * P0002.5.3 Query to extract Buyer Group Weekly for Pattern 3
	 * @param porCd
	 * 				string
	 * @param ordrTkeBseMnth
	 * 				string
	 * @param bean
	 * 				I000091Bean
	 * @return the string
	 */
	public String getByrGrpWklyOcfForPtrn3(String porCd,String ordrTkeBseMnth, I000091Bean bean){
		
			finalQuery = new StringBuilder().append(I000091Constants.queryString +I000091QueryConstants.byrGrpWklyPtrn3
					.toString());
			StringBuilder rgnlWklyOcfQuery = getBaseQuery(porCd,ordrTkeBseMnth,bean);
			rgnlWklyOcfQuery.append(") a");					
			
		return rgnlWklyOcfQuery.toString();
	}
	
	
	/**
	 * P0002.5.4 Query to extract Buyer Group Weekly for Pattern 4
	 * @param porCd
	 * 				string
	 * @param ordrTkeBseMnth
	 * 				string
	 * @param bean
	 * 				I000091Bean
	 * @return the string
	 */
	public String getByrGrpWklyOcfForPtrn4(String porCd,String ordrTkeBseMnth, I000091Bean bean){
		
			finalQuery = new StringBuilder().append(I000091Constants.queryString +I000091QueryConstants.byrGrpWklyPtrn4
					.toString());
			StringBuilder rgnlWklyOcfQuery = getBaseQuery(porCd,ordrTkeBseMnth,bean);
			rgnlWklyOcfQuery.append(I000091QueryConstants.byrGrpWklyPtrn4Cndtn.toString());
			rgnlWklyOcfQuery.append(") a");					
			
		return rgnlWklyOcfQuery.toString();
	}
	
	/**
	 * P0002.5 Base Query to extract Buyer Group Weekly
	 * @param porCd
	 * 				string
	 * @param ordrTkeBseMnth
	 * 				string
	 * @param bean
	 * 				I000091Bean
	 * @return the string
	 */
	public StringBuilder getBaseQuery(String porCd,String ordrTkeBseMnth, I000091Bean bean){
		
		String whereClause = "";
		
		whereClause = I000091QueryConstants.byrGrpWklyCondition.toString();
		whereClause = ((I000091Constants.NULL).equals(porCd) || ("*").equals(porCd)) ? whereClause
				.replaceAll(IFConstants.param_ByrGrpWkly_PorCd, " ")
				: whereClause.replaceAll(IFConstants.porCd_Param,
						"'" + porCd.trim() + I000091Constants.AND_QRYSTRNG);
		
		whereClause = ((I000091Constants.NULL).equals(bean.getByrGrpCd()) || ("*")
				.equals(bean.getByrGrpCd())) ? whereClause.replaceAll(
				IFConstants.param_ByrGrpWkly_ByrGrpCd, " ") : whereClause
				.replaceAll(IFConstants.buyer_buyerGrpCD_Param, "'"
						+ bean.getByrGrpCd().trim() + I000091Constants.AND_QRYSTRNG);
		
		whereClause = ((I000091Constants.NULL).equals(ordrTkeBseMnth) || ("*")
				.equals(ordrTkeBseMnth)) ? whereClause.replaceAll(
				IFConstants.param_ByrGrpWkly_PrdMnth, " ") : whereClause
				.replaceAll(IFConstants.ProdMnth_param,
						"'" + ordrTkeBseMnth.trim() + I000091Constants.AND_QRYSTRNG);
		
		finalQuery.append("("+ whereClause + ")");
		int indx = finalQuery.toString().lastIndexOf("AND");
		finalQuery = finalQuery.replace(indx, indx + 3, " ");
		return finalQuery;
	}
}
