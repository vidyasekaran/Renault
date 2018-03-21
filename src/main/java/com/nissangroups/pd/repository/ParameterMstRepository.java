/*
 * System Name     :Post Dragon 
 * Sub system Name :Batch 
 * Function ID     :PST-DRG-B000018/PST-DRG-R000007,R000008,R000009
 * Module          :MONTHLY ORDERING
 * Process Outline : Repository/Business Layer Class to Co-Ordinate with DB.
 *   
 *
 * <Revision History>
 * Date       Name(Company Name)             Description 
 * ---------- ------------------------------ ---------------------
 * 06-OCT-2015  	 z001870(RNTBCI)               New Creation
 *
 */

package com.nissangroups.pd.repository;

import static com.nissangroups.pd.util.PDConstants.PERSISTENCE_NAME;
import static com.nissangroups.pd.util.PDConstants.PRMTR_KEY1;
import static com.nissangroups.pd.util.PDConstants.PRMTR_KEY2;
import static com.nissangroups.pd.util.PDConstants.PRMTR_PRMTR_CD;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.nissangroups.pd.b000052.util.B000052QueryConstants;
import com.nissangroups.pd.exception.PdApplicationException;
import com.nissangroups.pd.model.MstPrmtr;
import com.nissangroups.pd.util.CommonUtil;
import com.nissangroups.pd.util.QueryConstants;

public class ParameterMstRepository {

	/** Object emgr. */
	@PersistenceContext(name = PERSISTENCE_NAME)
	private EntityManager eMgr;


	/** Constant LOG. */
	private static final Log LOG = LogFactory.getLog
			(ParameterMstRepository.class);
	
	
	

	public List<MstPrmtr> fetchValue(String prmtrCd,String key1) throws PdApplicationException  {
		
		LOG.info("Parameter values are --> Parameter Cd is   : "+prmtrCd+ " and Key 1  is :" +key1);

		String result = "";
		StringBuilder dynamicQuery = new StringBuilder();
		dynamicQuery.append(QueryConstants.fetchValue);
		
		String queryStr = dynamicQuery.toString();
		Query query = eMgr.createQuery(queryStr.toString());
		
		query.setParameter(PRMTR_PRMTR_CD, prmtrCd);
		query.setParameter(PRMTR_KEY1, key1);
				
		List<MstPrmtr> resultList = (List<MstPrmtr>) query.getResultList();
		
		return resultList;
	}

	public String fetchValue1(String prmtrCd,String key1,String key2) throws PdApplicationException  {
		
		LOG.info("Parameter values are --> Parameter Cd is   : "+prmtrCd+ " and Key 1  is :" +key1+ " and Key 2  is :" +key2);

		String result = "";
		StringBuilder dynamicQuery = new StringBuilder();
		dynamicQuery.append(QueryConstants.fetchValue1);
		
		String queryStr = dynamicQuery.toString();
		Query query = eMgr.createNativeQuery(queryStr.toString());
		
		query.setParameter(PRMTR_PRMTR_CD, prmtrCd);
		query.setParameter(PRMTR_KEY1, key1);
		query.setParameter(PRMTR_KEY2, key2);
		
		
		List<String> resultList = query.getResultList();
		if(resultList != null && !(resultList.isEmpty())){
			 result = resultList.get(0);
		}
		
		
		return result;
		
	}
	
public String fetchValue2(String prmtrCd,String key1,String key2) throws PdApplicationException  {
		
		LOG.info("Parameter values are --> Parameter Cd is   : "+prmtrCd+ " and Key 1  is :" +key1+ " and Key 2  is :" +key2);

		String result = "";
		StringBuilder dynamicQuery = new StringBuilder();
		dynamicQuery.append(QueryConstants.fetchValue2);
		
		String queryStr = dynamicQuery.toString();
		Query query = eMgr.createNativeQuery(queryStr.toString());
		
		query.setParameter(PRMTR_PRMTR_CD, prmtrCd);
		query.setParameter(PRMTR_KEY1, key1);
		query.setParameter(PRMTR_KEY2, key2);
		
		
		List<String> resultList = query.getResultList();
		if(resultList != null && !(resultList.isEmpty())){
			 result = resultList.get(0);
		}
		
		
		return result;
		
	}


public String[] fetchValue1Value2(String prmtrCd,String key1,String key2) throws PdApplicationException  {
	
	LOG.info("Parameter values are --> Parameter Cd is   : "+prmtrCd+ " and Key 1  is :" +key1+ " and Key 2  is :" +key2);

	String result[] = new String[2];
	StringBuilder dynamicQuery = new StringBuilder();
	dynamicQuery.append(QueryConstants.fetchValue1Value2);
	
	String queryStr = dynamicQuery.toString();
	Query query = eMgr.createNativeQuery(queryStr.toString());
	
	query.setParameter(PRMTR_PRMTR_CD, prmtrCd);
	query.setParameter(PRMTR_KEY1, key1);
	query.setParameter(PRMTR_KEY2, key2);
	
	
	List<Object[]> resultList = query.getResultList();
	if(resultList != null && !(resultList.isEmpty())){
		 result[0] = CommonUtil.convertObjectToString((resultList.get(0))[0]);
		 result[1] = CommonUtil.convertObjectToString((resultList.get(0))[1]);
		 return result;
		 
	}
	
	
	return null;
	
}

public String fetchPlntlnSmry(String porCd){
	
	List<String> val2 = new ArrayList<String>();
	StringBuilder fetchPltSmryQuery = new StringBuilder()
	.append(B000052QueryConstants.fetchWklySmryVal1)
	.append("'")
	.append(porCd)
	.append("'")
	.append(B000052QueryConstants.KEY2)
	.append(B000052QueryConstants.PLNTLNSMRY);
	Query query = eMgr.createNativeQuery(fetchPltSmryQuery.toString());
	val2 =query.getResultList();
	
	String plntSmryVal = null;
	if(val2!=null && !(val2.isEmpty()) && val2.get(0)!=null){
		plntSmryVal = val2.get(0);
	
	}
	
	return plntSmryVal;
}

//Plant line and LIne class
public List<Object[]> fetchPlntln(String porCd){
	
	List<Object[]> val = new ArrayList<Object[]>();
	StringBuilder fetchPltlnQuery = new StringBuilder()
	.append(B000052QueryConstants.fetchWklySmryVal1n2)
	.append("'")
	.append(porCd)
	.append("'")
	.append(B000052QueryConstants.KEY2)
	.append(B000052QueryConstants.PLNTLNCLS);
	Query query = eMgr.createNativeQuery(fetchPltlnQuery.toString());
	val =query.getResultList();
	
	
	
	
	return val;
}

public String fetchCnstntDay(String porCd){

List<String> val2 = new ArrayList<String>();
StringBuilder fetchPltSmryQuery = new StringBuilder()
.append(B000052QueryConstants.fetchWklySmryVal1)
.append("'")
.append(porCd)
.append("'")
.append(B000052QueryConstants.KEY2)
.append(B000052QueryConstants.CNSTNTDAYNO);
Query query = eMgr.createNativeQuery(fetchPltSmryQuery.toString());
val2 =query.getResultList();

String plntSmryVal = null;
if(val2!=null && !(val2.isEmpty()) && val2.get(0)!=null){
	plntSmryVal = val2.get(0);

}

return plntSmryVal;
}

public String fetchFeatCdInfo(String porCd,String prcsType){

List<String> val2 = new ArrayList<String>();
StringBuilder fetchPltSmryQuery = new StringBuilder()
.append(B000052QueryConstants.fetchFeatCdVal1)
.append("'")
.append(porCd)
.append("'")
.append(B000052QueryConstants.KEY2)
.append("'")
.append(prcsType)
.append("'");
Query query = eMgr.createNativeQuery(fetchPltSmryQuery.toString());
val2 =query.getResultList();

String plntSmryVal = null;
if(val2!=null && !(val2.isEmpty()) && val2.get(0)!=null){
	plntSmryVal = val2.get(0);

}

return plntSmryVal;
}


	
	public EntityManager geteMgr() {
		return eMgr;
	}


	public void seteMgr(EntityManager eMgr) {
		this.eMgr = eMgr;
	}

	

}
