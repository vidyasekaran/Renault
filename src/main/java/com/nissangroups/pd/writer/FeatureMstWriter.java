/*
 * System Name     :Post Dragon 
 * Sub system Name :Interface
 * Function ID     :PST-DRG-I00008
 * Module          :Spec Master
 * Process Outline :Receive OCF Classification Master from Plant	
 *
 * <Revision History>
 * Date       Name(Company Name)             Description 
 * ---------- ------------------------------ ---------------------
 * 29-06-2015  	  z010356(RNTBCI)               New Creation
 *
 */
package com.nissangroups.pd.writer;

import static com.nissangroups.pd.util.I00008QueryConstants.SelectQuery;
import static com.nissangroups.pd.util.I00008QueryConstants.InsertMSTFeatQuery;

import static com.nissangroups.pd.util.PDConstants.DOLLAR;
import static com.nissangroups.pd.util.PDConstants.INSIDE_METHOD;
import static com.nissangroups.pd.util.PDConstants.INTERFACE8;
import static com.nissangroups.pd.util.PDConstants.OUTSIDE_METHOD;
import static com.nissangroups.pd.util.PDConstants.PERSISTENCE_NAME;
import static com.nissangroups.pd.util.PDConstants.PRMTR_CARSRS;
import static com.nissangroups.pd.util.PDConstants.PRMTR_FEAT_CD;
import static com.nissangroups.pd.util.PDConstants.PRMTR_FEAT_TYPE_CD;
import static com.nissangroups.pd.util.PDConstants.PRMTR_OCFBYRGRPCD;
import static com.nissangroups.pd.util.PDConstants.PRMTR_OCFRGNCD;
import static com.nissangroups.pd.util.PDConstants.PRMTR_PORCD;
import static com.nissangroups.pd.util.PDConstants.UNCHECKED;
import static com.nissangroups.pd.util.PDConstants.EMPTY_STRING;
import static com.nissangroups.pd.util.PDConstants.VALUES;
import static com.nissangroups.pd.util.PDConstants.COMMA_IN_QUERY;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.item.ItemWriter;

import com.nissangroups.pd.model.MstFeat;



/**
 * The Class FeatureMstWriter.
 *
 * @author z010356 Customized ItemWriter Class
 */
public class FeatureMstWriter implements ItemWriter<List<MstFeat>> {
    
    /** Variable entity manager. */
    @PersistenceContext(name = PERSISTENCE_NAME)
    private EntityManager entityManager;
    
    /** Variable select result. */
    List<Object[]> selectResult;
    
    /** Variable interface id. */
    String interfaceID = INTERFACE8;
    
    /** Constant LOG. */
    private static final Log LOG = LogFactory.getLog(FeatureMstWriter.class.getName());
    
    /**
     * Write.
     *
     * @author z010356
     * @param featureMstDataList the feature mst data list
     * @throws Exception the exception
     */

    @SuppressWarnings({UNCHECKED})
    @Override
    public void write(List<? extends List<MstFeat>> featureMstDataList)
            throws Exception {
    	LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR);
        try{
        for (Object prcssdObj : featureMstDataList) {
            selectResult = null;
            MstFeat prcssdMstFeat = (MstFeat) prcssdObj;
            Query querySelect = entityManager.createNativeQuery(SelectQuery.toString());
            querySelect.setParameter(PRMTR_PORCD, prcssdMstFeat.getId().getPorCd());
            querySelect.setParameter(PRMTR_CARSRS, prcssdMstFeat.getId().getCarSrs());
            querySelect.setParameter(PRMTR_FEAT_CD, prcssdMstFeat.getId().getFeatCd());
            querySelect.setParameter(PRMTR_FEAT_TYPE_CD, prcssdMstFeat.getId().getFeatTypeCd());
            querySelect.setParameter(PRMTR_OCFRGNCD, prcssdMstFeat.getId().getOcfRegionCd());
            querySelect.setParameter(PRMTR_OCFBYRGRPCD, prcssdMstFeat.getId().getOcfBuyerGrpCd());
            List<Object[]> selectResultSet = querySelect.getResultList();
             if (selectResultSet != null && !selectResultSet.isEmpty())
             {
                 executeUpdateQuery(selectResultSet,prcssdMstFeat);
                
             }
             else
             {
                 executeInsertQuery(prcssdMstFeat);
                
             }
        }
        }
        catch(Exception e){
            
            LOG.error(interfaceID,e);
            
        }
        LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);
    }
    
    /**
     * This method updates the MST_FEAT if the data already exists.
     *
     * @param selectResultSet the select result set
     * @param prcssdMstFeat the prcssd mst feat
     */
    private void executeUpdateQuery(List<Object[]> selectResultSet,MstFeat prcssdMstFeat){
        
    	     LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR);
             String adoptDateTemp = EMPTY_STRING;
             String abolishDateTemp = EMPTY_STRING;
             String longDescTemp = EMPTY_STRING;
             for (Object[] resultArray : selectResultSet)
             {
                 adoptDateTemp = minimumValue(prcssdMstFeat.getFeatAdptDate(),resultArray[0].toString());
                 abolishDateTemp = maximumValue(prcssdMstFeat.getFeatAblshDate(),resultArray[1].toString());
                 longDescTemp = maximumValue(prcssdMstFeat.getFeatLngDesc(),resultArray[2].toString());
             }               
             
             String updateQuery =
             "UPDATE MST_FEAT A SET A.FEAT_ADPT_DATE ='" + adoptDateTemp
             +"', A.FEAT_ABLSH_DATE ='" + abolishDateTemp
             +"', A.FEAT_LNG_DESC ='" + longDescTemp
             +"', A.UPDTD_BY ='" + prcssdMstFeat.getUpdtdBy()
             +"' WHERE POR_CD ='" + prcssdMstFeat.getId().getPorCd().toString()
             +"' AND CAR_SRS ='" + prcssdMstFeat.getId().getCarSrs().toString()
             +"' AND FEAT_CD ='" + prcssdMstFeat.getId().getFeatCd().toString()
             +"' AND FEAT_TYPE_CD ='" + prcssdMstFeat.getId().getFeatTypeCd().toString()
             +"' AND OCF_REGION_CD ='" + prcssdMstFeat.getId().getOcfRegionCd().toString()
             +"' AND OCF_BUYER_GRP_CD ='" + prcssdMstFeat.getId().getOcfBuyerGrpCd().toString() + "'";
             Query query = entityManager.createNativeQuery(updateQuery);
             query.executeUpdate();  
             LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);
    }
    
    /**
     * This method inserts the new record if the data doesn't exist already in MST_FEAT.
     *
     * @param prcssdMstFeat the prcssd mst feat
     */
    private void executeInsertQuery(MstFeat prcssdMstFeat){
    	LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR);
        String insertQuery =
        		    InsertMSTFeatQuery
                 + VALUES+ prcssdMstFeat.getId().getPorCd() + COMMA_IN_QUERY + prcssdMstFeat.getId().getCarSrs() + COMMA_IN_QUERY+ prcssdMstFeat.getId().getFeatCd() + COMMA_IN_QUERY 
                 + prcssdMstFeat.getOcfFrmeCd() + COMMA_IN_QUERY + prcssdMstFeat.getId().getFeatTypeCd() + "'," + prcssdMstFeat.getMstFeatGrp() + ",'" 
                 + prcssdMstFeat.getFeatShrtDesc() + COMMA_IN_QUERY + prcssdMstFeat.getFeatLngDesc() +COMMA_IN_QUERY + prcssdMstFeat.getCrtdBy() + COMMA_IN_QUERY 
                 + prcssdMstFeat.getId().getOcfRegionCd() + COMMA_IN_QUERY + prcssdMstFeat.getId().getOcfBuyerGrpCd() +COMMA_IN_QUERY + prcssdMstFeat.getFeatAdptDate() + COMMA_IN_QUERY 
                 + prcssdMstFeat.getFeatAblshDate() + "')";          
                 Query query = entityManager.createNativeQuery(insertQuery);
                 query.executeUpdate();
         LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);
        
        
    }
    
    /**
     * This method gets the minimum value between 2 strings.
     *
     * @param featAdptDate1 the feat adpt date1
     * @param featAdptDate2 the feat adpt date2
     * @return the string
     */
    private String minimumValue(String featAdptDate1, String featAdptDate2) {
    	LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR);
        if (featAdptDate1.compareTo(featAdptDate2)>0){
        	LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);
            return featAdptDate2;
        }
        else{
        	LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);
            return featAdptDate1;
        }
    }
    
    /**
     * This method gets the maximum value between 2 strings.
     *
     * @param value1 the value1
     * @param value2 the value2
     * @return the string
     */
    
    private String maximumValue(String value1, String value2) {
    	LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR);
        if (value1.compareTo(value2)>0){
        	LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);
            return value1;
        }
        else{       	
        	LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);       
            return value2;
        }
    }

}
