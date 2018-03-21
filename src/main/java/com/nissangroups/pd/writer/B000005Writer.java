/*
 * System Name     :Post Dragon 
 * Sub system Name :Batch 
 * Function ID     :PST-DRG-B000005
 * Module          :Cretae Orderable Sales  Enditem Feature MST
 * Process Outline :Spec Master 
 *
 * <Revision History>
 * Date       Name(Company Name)             Description 
 * ---------- ------------------------------ ---------------------
 * @Date  	  z010343(RNTBCI)               New Creation
 *
 */
package com.nissangroups.pd.writer;

import static com.nissangroups.pd.util.PDConstants.*;
import static com.nissangroups.pd.util.CommonUtil.*;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeSet;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

import com.nissangroups.pd.util.B00005QueryConstants;
import com.nissangroups.pd.util.CustomComparator;

/*
 *  Insert or Update the Feature's to Orderable sales End Item Feature Mst 
 */


@Component
public class B000005Writer implements ItemWriter<List<Object>> {
    private static final Log LOG = LogFactory.getLog(B000005Writer.class
            .getName());
    @PersistenceContext(unitName = PERSISTENCE_NAME)
    private EntityManager entityManager;

    private B00005QueryConstants queryConstants = new B00005QueryConstants();

    private JobExecution jobExecution;
    
    String decideBatch;
    String updateFlag ;
    String abolishDate;
   private StepExecution stepExecution; 
   Map<List<String>,Set<Date>> featureMapAdpt = new HashMap<List<String>,Set<Date>>();
   Map<List<String>,Set<Date>> featureMapAblsh = new HashMap<List<String>,Set<Date>>();

   int i;
    /*
     * Entry Point for B000005 Writer Class
     */
   
   @BeforeStep
    public void retrieveInterstepData(StepExecution stepExecution)
            throws ParseException {
	   LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR);

        this.stepExecution = stepExecution;
        
   }

    @Override
    public void write(List<? extends List<Object>> items) throws Exception {
    	LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR);
         jobExecution= this.stepExecution.getJobExecution(); 
         ExecutionContext executionContext = jobExecution.getExecutionContext();
        JobParameters jobParameters = jobExecution.getJobParameters();
        decideBatch = jobParameters.getString(PRMTR_BATCH);
         updateFlag = jobParameters.getString(PRMTR_UPDATE_FLAG);
        abolishDate = executionContext.getString(PRMTR_BASE_PERIOD_WEEKLY);
        deleteOrderableEndItem(items);
        for (Object obj : items) {
            selectExistingData(obj,decideBatch);
            
        }
        if (BATCH000004.equals(decideBatch)) {
            updateEiStatusCd(items);
        }
        LOG.info(featureMapAdpt);
        LOG.info(featureMapAblsh);
        updateFtreMst(featureMapAdpt,featureMapAblsh);
        LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);
       
    }
    private void updateFtreMst(Map<List<String>, Set<Date>> featureMapAdpt,
            Map<List<String>, Set<Date>> featureMapAblsh) throws ParseException {
       LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR);	
       for(Entry<List<String>, Set<Date>> mapEntry : featureMapAdpt.entrySet() )
       {
           updateFtreAdpt(mapEntry.getKey(),mapEntry.getValue());
           
       }
       
       for(Entry<List<String>, Set<Date>> mapEntry : featureMapAblsh.entrySet() )
       {
           updateFtreAblsh(mapEntry.getKey(),mapEntry.getValue());
           
       }
       LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR); 
        
    }

    private void updateFtreAblsh(List<String> key, Set<Date> value) throws ParseException {
    	LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR);
        String queryString =  queryConstants.updateFeatureAblsh();
        Query query = entityManager.createNativeQuery(queryString);
        query.setParameter(PRMTR_PORCD, key.get(0));
        query.setParameter(PRMTR_CARSRS, key.get(1));
        query.setParameter(PRMTR_FEAT_CD,key.get(2));
        query.setParameter(PRMTR_FEAT_TYPE_CD,
                key.get(3));
        query.setParameter(PRMTR_OCFRGNCD,
                key.get(4));
        query.setParameter(PRMTR_OCFBYRGRPCD,
                key.get(5));
        query.setParameter(PRMTR_FTRE_ABLSH_DATE, dateToString(value.iterator().next()));
        int succuess = query.executeUpdate();
        if(succuess != 0)
        {
            LOG.info(FEATURE_MST_ABOLISH_UPDATE_SUCCESS_MSG);
        }
        LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);
    }

    private void updateFtreAdpt(List<String> key, Set<Date> value) throws ParseException {
       LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR); 
       String queryString =  queryConstants.updateFeatureAdpt();
       Query query = entityManager.createNativeQuery(queryString);
       query.setParameter(PRMTR_PORCD, key.get(0));
       query.setParameter(PRMTR_CARSRS, key.get(1));
       query.setParameter(PRMTR_FEAT_CD,key.get(2));
       query.setParameter(PRMTR_FEAT_TYPE_CD,
               key.get(3));
       query.setParameter(PRMTR_OCFRGNCD,
               key.get(4));
       query.setParameter(PRMTR_OCFBYRGRPCD,
               key.get(5));
       query.setParameter(PRMTR_FTRE_ADPT_DATE, dateToString(value.iterator().next()));
       int succuess = query.executeUpdate();
       if(succuess != 0)
       {
           LOG.info(FEATURE_MST_ABOLISH_UPDATE_SUCCESS_MSG);
       }
       LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);
    }

    @SuppressWarnings(UNCHECKED)
    private void updateEiStatusCd(List<? extends List<Object>> items) {
    	LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR);
        for (Object obj : items) { 
        for(Object obj1 : (List<Object>)obj)
        {
            Object[] feature = (Object[]) obj1;
           String queryString = queryConstants.updateEndItemSts();
            Query query = entityManager.createNativeQuery(queryString);
            query.setParameter(PRMTR_PORCD,feature[FEATURE_POR_CD]);
            query.setParameter(PRMTR_UK_OEI_BUYEER_ID,feature[FEATURE_OEI_ID]);
            query.setParameter(PRMTR_ABLSHDATE,abolishDate);
            query.setParameter(BATCH_4_ID,BATCH_4_ID);

            int result = query.executeUpdate(); 
            if(result != 0){
                LOG.info( ORDERABLE_SALES_END_ITEM_DETAIL_MST_UPDATE_SUCCESS_MSG) ;
                }
                
               
        }
        }
        LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);
         
     }
    @SuppressWarnings(UNCHECKED)
    private void deleteOrderableEndItem(List<? extends List<Object>> items) {
    	LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR);
        String queryString = EMPTY_STRING;
        for (Object obj : items) { 
            for(Object obj1 : (List<Object>)obj)
            {
                Object[] feature = (Object[]) obj1;
        if (BATCH000004.equals(decideBatch)) {
            
            queryString = queryConstants.deleteOrderableEndItem(updateFlag);
            Query query = entityManager.createNativeQuery(queryString);
            query.setParameter(PRMTR_PORCD,feature[FEATURE_POR_CD]);
            if (updateFlag.equals(PRMTR_ONE)) {
                
                query.setParameter(PRMTR_UK_OEI_ID, feature[FEATURE_OEI_ID]);
            }
            int result = query.executeUpdate();
            if(result != 0){
                LOG.info(ORDERABLE_END_ITEM_FEATURE_MST_DELETE_SUCCESS_MSG);
                }
                
        } else {
            queryString = queryConstants
                    .deleteOrderableSalesEndItem(updateFlag);
            Query query = entityManager.createNativeQuery(queryString);
            query.setParameter(PRMTR_PORCD,feature[FEATURE_POR_CD]);
            if (updateFlag.equals(PRMTR_ONE)) {
                query.setParameter(PRMTR_UK_OSEI_ID, feature[FEATURE_OEI_ID]);
            }
            
            int result = query.executeUpdate();
            if(result != 0){
                LOG.info(ORDERABLE_SALES_END_ITEM_FEATURE_MST_DELETE_SUCCESS_MSG);
                }
                
               
        }
            }}
        LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);
        
    }

    
   

    /*
     * Process ID: P0005.3
     * Update if previous data's exists
     */
    public void updateOSEIFM(Object[] feature) {
    	LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR);
        String queryString = queryConstants.updateOSEIFM();
     
        Query query = entityManager.createNativeQuery(queryString);
        setParameter(query,feature);

        query.setParameter(PRMTR_FTRE_ABLSH_DATE, feature[FEATURE_FEAT_ABLSH_DATE]);
        int result = query.executeUpdate();
        if(result!=0){
            LOG.info(ORDERABLE_SALES_END_ITEM_FEATURE_MST_UPDATE_SUCCESS_MSG);
        }
        LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);

    }

    /*
     * Process ID: P0005.3
     * Insert if there is no previous data's 
     */

    public void insertOSEIFM(Object[] feature) {
    	LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR);
        String queryString = queryConstants.insertOSEIFM();
        Query query = entityManager.createNativeQuery(queryString);
        setParameter(query,feature);

        query.setParameter(PRMTR_FTRE_ABLSH_DATE, feature[FEATURE_FEAT_ABLSH_DATE]);
        
        int result = query.executeUpdate();
        if(result!=0){
            LOG.info(ORDERABLE_SALES_END_ITEM_FEATURE_MST_INSERT_SUCCESS_MSG);
        }
        LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);
    }

    /*
     * Process ID: P0005.3
     * Select the Previous Data 
     */
    @SuppressWarnings(UNCHECKED)
    public void selectExistingData(Object obj, String decideBatch) throws ParseException {
    	LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR);
        List<Object[]> ftreLst = (List<Object[]>) obj; 
        Collections.sort(ftreLst,new CustomComparator());
        
        for (Object o : ftreLst) {
            i++;
            
            Object[] feature = (Object[]) o;
            addToFtreMstList(feature);
           
            if(BATCH000004.equals(decideBatch))
            {
                String queryString = queryConstants.selectOEIFM();
                Query query = entityManager.createNativeQuery(queryString);
                setParameter(query,feature);

                List<Object> resultList = query.getResultList();
               if (!resultList.isEmpty()) {
                    updateOEIFM(feature);

                } else {
                    insertOEIFM(feature);

                }

            }
            else
            {
                String queryString = queryConstants.selectOSEIFM();
                Query query = entityManager.createNativeQuery(queryString);
                setParameter(query,feature);
                List<Object> resultList = query.getResultList();
                if (!resultList.isEmpty()) {
                updateOSEIFM(feature);

            } else {
                insertOSEIFM(feature);

            }
            }
        }
        LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);
    }

    private void addToFtreMstList(Object[] feature) throws ParseException {
    	LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR);
        List<String> ftre = new ArrayList<String>();
        if(feature[FEATURE_OCF_FRME_CD].toString().equals(OCF_FRAME_CODE_ZERO)){
        ftre.add((String) feature[FEATURE_POR_CD]);
        ftre.add((String) feature[FEATURE_CAR_SRS]);
        ftre.add((String) feature[FEATURE_FEAT_CD]);
        ftre.add((String) feature[FEATURE_FEAT_TYPE_CD]);
        ftre.add(feature[FEATURE_OCF_REGION_CD].toString());
        ftre.add((String) feature[FEATURE_OCF_BUYER_GRP_CD]);
        Date adpt = convertStringToDate((String)feature[FEATURE_FEAT_ADPT_DATE]);
        Date ablsh = convertStringToDate((String)feature[FEATURE_FEAT_ABLSH_DATE]);
        if(featureMapAdpt.containsKey(ftre) && featureMapAblsh.containsKey(ftre))
        {
            Set<Date> mapSetAdpt = featureMapAdpt.get(ftre);
            Set<Date> mapSetAblsh = featureMapAblsh.get(ftre);

            mapSetAdpt.add(adpt);
            mapSetAblsh.add(ablsh);
        }
        else
        {
            Set<Date> mapSetFtre = new TreeSet<Date>();
            mapSetFtre.add(adpt);
            Set<Date> mapSetAblsh = new TreeSet<Date>(Collections.reverseOrder());
            mapSetAblsh.add(ablsh);
            featureMapAdpt.put(ftre, mapSetFtre);
            featureMapAblsh.put(ftre, mapSetAblsh);

        }
        }
        LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);

    }

    private void insertOEIFM(Object[] feature) {
    	LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR);
        String updateQueryString = queryConstants.insertOEIFM(feature);
        Query query = entityManager.createNativeQuery(updateQueryString);
        
        int result = query.executeUpdate();
        if(result!=0){
            LOG.info(ORDERABLE_END_ITEM_FEATURE_MST_INSERT_SUCCESS_MSG);
        }
        LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);
       
    }

    private void updateOEIFM(Object[] feature) {
    	LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR);
       
        String queryString = queryConstants.updateOEIFM();
        
        Query query = entityManager.createNativeQuery(queryString);
        setParameter(query,feature);
        query.setParameter(PRMTR_FTRE_ABLSH_DATE, feature[FEATURE_FEAT_ABLSH_DATE]);

        int result = query.executeUpdate();
        if(result!=0){
            LOG.info(ORDERABLE_END_ITEM_FEATURE_MST_UPDTAE_SUCCESS_MSG);
        }
       
        LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);
        
    }

    private void setParameter(Query query, Object[] feature) {
    	LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR);
        query.setParameter(PRMTR_PORCD, feature[FEATURE_POR_CD]);
        query.setParameter(PRMTR_FEAT_CD, feature[FEATURE_FEAT_CD]);
        query.setParameter(PRMTR_FEAT_TYPE_CD, feature[FEATURE_FEAT_TYPE_CD]);
        query.setParameter(PRMTR_OCF_FRAME_CD, feature[FEATURE_OCF_FRME_CD]);
        query.setParameter(PRMTR_FTRE_ADPT_DATE, feature[FEATURE_FEAT_ADPT_DATE]);
        query.setParameter(PRMTR_UK_OEI_ID, ((String) feature[FEATURE_OEI_ID]).trim());
        LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);
        }
}
