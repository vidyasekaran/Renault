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

import com.nissangroups.pd.i000091.output.I000091OutputBean;
import com.nissangroups.pd.i000091.util.I000091Constants;
import com.nissangroups.pd.i000091.util.I000091QueryConstants;
import com.nissangroups.pd.util.IFConstants;
import com.nissangroups.pd.util.IfCommonUtil;

/**
 * This class is used to generate query for extracting the Regional Weekly Ocf limit 
 * based on the Pattern.
 * 
 * @author z016127
 *
 */
public class ExctRegnlWklyOcfProcess {

	/** Common utility service bean injection */
	@Autowired(required = false)
	IfCommonUtil commonutility;
	
	/**Variable Query String */
	private StringBuilder finalQuery;
	
	/**
	 * Instantiates a new ExctByrGrpWklyOcfProcess.
	 */
	public ExctRegnlWklyOcfProcess() {
	}
	
	/**
	 * P0002.4.1 Query to extract Regional Weekly for Pattern 1
	 * @param item
	 * 				I000091OutputBean
	 * @param porCd
	 * 				string
	 * @param ordrTkeBseMnth
	 * 				string
	 * @return string
	 */
	public String getRegnlWklyOcfForPtrn1(I000091OutputBean item, String porCd, String ordrTkeBseMnth){
			
			finalQuery = new StringBuilder().append(I000091Constants.queryString + I000091QueryConstants.regnalWklyPtrn1
					.toString());
			StringBuilder rgnlWklyOcfQuery = getBaseQuery(item,porCd,ordrTkeBseMnth);
			rgnlWklyOcfQuery.append(I000091QueryConstants.regnalWklyPtrn1Condtn.toString());
			rgnlWklyOcfQuery.append(") a");	
			
		return rgnlWklyOcfQuery.toString();
	}
	
	/**
	 * P0002.4.2 Query to extract Regional Weekly for Pattern 2
	 * @param item
	 * 				I000091OutputBean
	 * @param porCd
	 * 				string
	 * @param ordrTkeBseMnth
	 * 				string
	 * @return string
	 */
	public String getRegnlWklyOcfForPtrn2(I000091OutputBean item, String porCd, String ordrTkeBseMnth){
		
			finalQuery = new StringBuilder().append(I000091Constants.queryString + I000091QueryConstants.regnalWklyPtrn2
					.toString());
			StringBuilder rgnlWklyOcfQuery = getBaseQuery(item,porCd,ordrTkeBseMnth);
			rgnlWklyOcfQuery.append(I000091QueryConstants.regnalWklyPtrn2Conditn.toString());
			rgnlWklyOcfQuery.append(") a");					
		return rgnlWklyOcfQuery.toString();
	}
	
	
	/**
	 * P0002.4.3 Query to extract Regional Weekly for Pattern 3
	 * @param item
	 * 				I000091OutputBean
	 * @param porCd
	 * 				string
	 * @param ordrTkeBseMnth
	 * 				string
	 * @return string
	 */
	public String getRegnlWklyOcfForPtrn3(I000091OutputBean item, String porCd, String ordrTkeBseMnth){
		
			finalQuery = new StringBuilder().append(I000091Constants.queryString + I000091QueryConstants.regnalWklyPtrn3
					.toString());
			StringBuilder rgnlWklyOcfQuery = getBaseQuery(item,porCd,ordrTkeBseMnth);
			rgnlWklyOcfQuery.append(") a");					
			
		return rgnlWklyOcfQuery.toString();
	}
	
	
	/**
	 * P0002.4.4 Query to extract Regional Weekly for Pattern 4
	 * @param item
	 * 				I000091OutputBean
	 * @param porCd
	 * 				string
	 * @param ordrTkeBseMnth
	 * 				string
	 * @return string
	 */
	public String getRegnlWklyOcfForPtrn4(I000091OutputBean item, String porCd, String ordrTkeBseMnth){
		
			finalQuery = new StringBuilder().append(I000091Constants.queryString + I000091QueryConstants.regnalWklyPtrn4
					.toString());
			StringBuilder rgnlWklyOcfQuery = getBaseQuery(item,porCd,ordrTkeBseMnth);
			rgnlWklyOcfQuery.append(I000091QueryConstants.regnalWklyPtrn4Cndtn.toString());
			rgnlWklyOcfQuery.append(") a");					
			
		return rgnlWklyOcfQuery.toString();
	}
	
	/**
	 * P0002.4 Base Query to extract Regional Weekly
	 * @param item
	 * 				I000091OutputBean
	 * @param porCd
	 * 				string
	 * @param ordrTkeBseMnth
	 * 				string
	 * @return string builder
	 */
	public StringBuilder getBaseQuery(I000091OutputBean item, String porCd,String ordrTkeBseMnth){
		
		String whereClause = "";
		
		whereClause = I000091QueryConstants.rgnlWklyCondition.toString();
		whereClause = ((I000091Constants.NULL).equals(porCd) || ("*").equals(porCd)) ? whereClause
				.replaceAll(IFConstants.param_RgnlWkly_Por, " ") : whereClause
				.replaceAll(IFConstants.porCd_Param, "'" + porCd.trim()
						+ I000091Constants.AND_QRYSTRNG);
		
		whereClause = ((I000091Constants.NULL).equals(item.getOcfRgnCd()) || ("*")
				.equals(item.getOcfRgnCd())) ? whereClause.replaceAll(
				IFConstants.param_RgnlWkly_OcfRgnCd, " ") : whereClause
				.replaceAll(IFConstants.ocfRegionCd_Param, "'"
						+ item.getOcfRgnCd().trim() + I000091Constants.AND_QRYSTRNG);
		
		whereClause = ((I000091Constants.NULL).equals(item.getOcfByrGrpCd()) || ("*")
				.equals(item.getOcfByrGrpCd())) ? whereClause.replaceAll(
				IFConstants.param_RgnlWkly_OcfByrGrp, " ") : whereClause
				.replaceAll(IFConstants.ocfBuyerGrpCd_Param, "'"
						+ item.getOcfByrGrpCd().trim() + I000091Constants.AND_QRYSTRNG);
		
		whereClause = ((I000091Constants.NULL).equals(ordrTkeBseMnth) || ("*")
				.equals(ordrTkeBseMnth)) ? whereClause.replaceAll(
				IFConstants.param_RgnlWklyPrdMnth, " ") : whereClause
				.replaceAll(IFConstants.ProdMnth_param, "'" + ordrTkeBseMnth
						+ I000091Constants.AND_QRYSTRNG);
		
		finalQuery.append("("+ whereClause + ")");
		int indx = finalQuery.toString().lastIndexOf("AND");
		finalQuery = finalQuery.replace(indx, indx + 3, " ");
		
		return finalQuery;
	}
}
