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
package com.nissangroups.pd.processor;

import static com.nissangroups.pd.util.CommonUtil.addMonthToDate;
import static com.nissangroups.pd.util.CommonUtil.convertStringToDate;
import static com.nissangroups.pd.util.CommonUtil.dateToString;
import static com.nissangroups.pd.util.PDConstants.*;
import static com.nissangroups.pd.util.PDConstants.B4_ABOLISH_DATE;
import static com.nissangroups.pd.util.PDConstants.B4_BATCH_STOP;
import static com.nissangroups.pd.util.PDConstants.B4_EI_MES;
import static com.nissangroups.pd.util.PDConstants.B4_MIN_PARAMTER;
import static com.nissangroups.pd.util.PDConstants.B4_MONTHLY_MST;
import static com.nissangroups.pd.util.PDConstants.B4_OTBM;
import static com.nissangroups.pd.util.PDConstants.B4_PRD_STAGE_CD;
import static com.nissangroups.pd.util.PDConstants.B4_WEEKLY_MST;
import static com.nissangroups.pd.util.PDConstants.BATCH4_ID;
import static com.nissangroups.pd.util.PDConstants.BATCH5_ID;
import static com.nissangroups.pd.util.PDConstants.FTRE_PARAMTER;
import static com.nissangroups.pd.util.PDConstants.FTRE_PARAMTER_TABLE;
import static com.nissangroups.pd.util.PDConstants.OCF_PARAMTER;
import static com.nissangroups.pd.util.PDConstants.OCF_PARAMTER_TABLE;
import static com.nissangroups.pd.util.PDConstants.PARAMETER_MSG;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.AfterStep;
import org.springframework.batch.core.annotation.AfterWrite;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import com.nissangroups.pd.dao.B000005ReportDao;
import com.nissangroups.pd.dao.B00005Dao;
import com.nissangroups.pd.exception.PdApplicationFatalException;
import com.nissangroups.pd.exception.PdApplicationNonFatalException;
import com.nissangroups.pd.util.B00005QueryConstants;
import com.nissangroups.pd.util.CommonUtil;

/**
 * The Class B000005Processor.
 *
 * @author z010343
 * @param <T> the generic type
 */
@Configuration
@PropertySource(BATCH_CONFIG_CLASSPATH)
@Component
public class B000005Processor<T> implements ItemProcessor<T, List<Object>> {
    
    /** Constant LOG. */
    private static final Log         LOG                = LogFactory
                                                                .getLog(B000005Processor.class
                                                                        .getName());
    
    @Autowired(required = false)
    Environment environment;
    
    /** Variable decide batch. */
    private String                   decideBatch;
    
    /** Variable por. */
    private String                   por;
    
    /** Variable batch feature cd. */
    private String                   batchFeatureCd     = "";
    
    /** Variable b5 ocf count. */
    private int                      b5OcfCount         = 0;
    
    /** Variable b5 ocff lg. */
    private int                      b5OcffLG           = 0;
    
    /** Variable step execution. */
    private StepExecution            stepExecution;
    
    /** Variable b000005 report dao. */
    @Autowired(required = false)
    private B000005ReportDao         b000005ReportDao;
    
    /** Variable query constants. */
    B00005QueryConstants             queryConstants     = new B00005QueryConstants();
    
    /** Variable b5 report genration. */
    B000004ReportGenration           b5ReportGenration  = new B000004ReportGenration();
    
    /** Variable list base period. */
    List<ArrayList<String>>          listBasePeriod     = new ArrayList<ArrayList<String>>();
    
    /** Variable zerofrme cd error flg. */
    private Map<B00005Dao, Object[]> zerofrmeCdErrorFlg = new HashMap<B00005Dao, Object[]>();
    
    /** Variable twenty error flg. */
    private Map<B00005Dao, Object[]> twentyErrorFlg     = new HashMap<B00005Dao, Object[]>();
    
    /** Variable thirty error flg. */
    private Map<B00005Dao, Object[]> thirtyErrorFlg     = new HashMap<B00005Dao, Object[]>();
    
    /** Variable job execution. */
    private JobExecution             jobExecution;
    
    /** Variable ei spec mst. */
    B00005Dao                        eiSpecMst;
    
    /** Variable entity manager. */
    @PersistenceContext
    private EntityManager            entityManager;
    
    /** Variable abolish date. */
    String                           abolishDate;
    
    private int flgErr = 0 ;
    
    /* (non-Javadoc)
     * @see org.springframework.batch.item.ItemProcessor#process(java.lang.Object)
     */
    @Override
    public List<Object> process(T eiSpecMst1) throws Exception {
    	LOG.info(DOLLAR+INSIDE_METHOD+DOLLAR);
        b5ReportGenration.setB000005ReportDao(b000005ReportDao);
        jobExecution = this.stepExecution.getJobExecution();
        eiSpecMst = (B00005Dao) eiSpecMst1;
        
        List<Object> featureList = new ArrayList<Object>();
        List<Object[]> ocfResultList = new ArrayList<Object[]>();
        
        for (Object listBasePeriodTypeIterator : listBasePeriod) {
            List<Object[]> feaureTmpLst = new ArrayList<Object[]>();
            @SuppressWarnings("unchecked")
            List<String> listBasePeriodType = (ArrayList<String>) listBasePeriodTypeIterator;
            String basePeriod = (String) listBasePeriodType.get(0);
            String productionType = (String) listBasePeriodType.get(1);
            Date eiAdptDate = convertStringToDate(eiSpecMst.getOseiAdoptDate());
            Date eiAblshDate = convertStringToDate(eiSpecMst.getAbolishDate());
            Date basePerioddate = convertStringToDate(basePeriod);
            if (WEEKLY.equals(productionType)
                    && eiAblshDate.compareTo(basePerioddate) > 0
                    && eiAdptDate.compareTo(basePerioddate) <= 0) {
            	flgErr++;
                b5OcffLG = 1;
                ocfResultList = ocfExtraction(eiSpecMst, basePeriod,
                        batchFeatureCd);
                b5OcfCount += ocfResultList.size();
                extractFeature(ocfResultList, productionType, eiSpecMst,
                        basePerioddate, feaureTmpLst, featureList);
                if (BATCH000004.equals(decideBatch)) {
                    zeroFrameCdCheck(feaureTmpLst, eiSpecMst, featureList,
                            basePerioddate, WEEKLY, ocfResultList);
                }
                insertReport(eiSpecMst);
                
            }
            
            if (eiAblshDate.compareTo(basePerioddate) > 0
                    && MONTHLY.equals(productionType)) {
            	flgErr++;
                b5OcffLG = 1;
                ocfResultList = ocfExtraction(eiSpecMst, basePeriod,
                        batchFeatureCd);
                b5OcfCount += ocfResultList.size();
                extractFeature(ocfResultList, productionType, eiSpecMst,
                        basePerioddate, feaureTmpLst, featureList);
                if (BATCH000004.equals(decideBatch)) {
                    zeroFrameCdCheck(feaureTmpLst, eiSpecMst, featureList,
                            basePerioddate, MONTHLY, ocfResultList);
                }
                insertReport(eiSpecMst);
                
            }
            
            /* For Batch 4 to check and attach the 00 Frame Code for OCF */
            
        }
        LOG.info(DOLLAR+OUTSIDE_METHOD+DOLLAR);
        
        return featureList;
    }
    
    /**
     * Batch: 4 Process ID: P0006.
     *
     * @param eiSpecMst the ei spec mst
     * @throws ParseException the parse exception
     */
    
    private void insertReport(B00005Dao eiSpecMst) throws ParseException {
    	LOG.info(DOLLAR+INSIDE_METHOD+DOLLAR);
        if (zerofrmeCdErrorFlg.containsKey(eiSpecMst)) {
            b5ReportGenration.generateReportError(
                    zerofrmeCdErrorFlg.get(eiSpecMst), eiSpecMst);
            
        } else if (twentyErrorFlg.containsKey(eiSpecMst)) {
            b5ReportGenration.generateReportWarning(eiSpecMst, MONTHLY,twentyErrorFlg.get(eiSpecMst));
        } else if (thirtyErrorFlg.containsKey(eiSpecMst)) {
            b5ReportGenration.generateReportWarning(eiSpecMst, WEEKLY,thirtyErrorFlg.get(eiSpecMst));
        }
        LOG.info(DOLLAR+OUTSIDE_METHOD+DOLLAR);
        
    }
    
    /**
     * Batch :4 ,5 Process ID : P0003 Function to extract the OCF and Feature
     * from the OCF Classification Master and Feature Master.
     *
     * @param eiSpecMst the ei spec mst
     * @param basePeriod the base period
     * @param batchFeatureCd the batch feature cd
     * @return the list
     * @throws ParseException the parse exception
     */
    @SuppressWarnings("unchecked")
    private List<Object[]> ocfExtraction(B00005Dao eiSpecMst,
            String basePeriod, String batchFeatureCd) throws ParseException {
    	LOG.info(DOLLAR+INSIDE_METHOD+DOLLAR);
        Query query = entityManager.createNativeQuery(queryConstants
                .ocfExtraction(basePeriod, decideBatch));
        query.setParameter(PRMTR_APPLDMDLCD, eiSpecMst.getAppliedModelCd());
        query.setParameter(PRMTR_PACKCD, eiSpecMst.getPackCd());
        query.setParameter(PRMTR_SPECDSTNCD, eiSpecMst.getSpecDestinationCd()
                .trim());
        query.setParameter(PRMTR_ADPTDATE, eiSpecMst.getOseiAdoptDate());
        query.setParameter(PRMTR_ABLSHDATE, eiSpecMst.getAbolishDate());
        if (BATCH000005.equals(decideBatch)) {
            query.setParameter(PRMTR_EXTRCLRCD, eiSpecMst.getExteriorColorCd()
                    .trim());
            query.setParameter(PRMTR_INTRCLRCD, eiSpecMst.getInteriorColorCd()
                    .toString().trim());
        }
        query.setParameter(PRMTR_PORCD, eiSpecMst.getPorCd());
        query.setParameter(PRMTR_CARSRS, eiSpecMst.getCarSeries().trim());
        query.setParameter(PRMTR_OCFBYRGRPCD, eiSpecMst.getOcfBuyerGroupCd());
        query.setParameter(PRMTR_OCFRGNCD, eiSpecMst.getOcfRegionCd());
        query.setParameter(PRMTR_BASEPERIOD, basePeriod);
        LOG.info(DOLLAR+OUTSIDE_METHOD+DOLLAR);
        return compareOcf(query.getResultList(), batchFeatureCd, eiSpecMst);
        
    }
    
    /**
     * Batch : 4 ,5 Process ID :P0004.1 P0004.5 Function to extract the Feature
     * from Feature Master
     *
     * @param ocfResultList the ocf result list
     * @param productionType the production type
     * @param eiSpecMst the ei spec mst
     * @param basePerioddate the base perioddate
     * @param feaureTmpLst the feaure tmp lst
     * @param featureList the feature list
     * @throws ParseException the parse exception
     * @throws PdApplicationNonFatalException the pd application non fatal exception
     */
    @SuppressWarnings("unchecked")
    private void extractFeature(List<Object[]> ocfResultList,
            String productionType, B00005Dao eiSpecMst, Date basePerioddate,
            List<Object[]> feaureTmpLst, List<Object> featureList)
            throws ParseException, PdApplicationNonFatalException {
    	LOG.info(DOLLAR+INSIDE_METHOD+DOLLAR);
        List<Object[]> featureMstUpdate = new ArrayList<Object[]>();
        List<Object> mstFeatLst = new ArrayList<Object>();
        for (Object ocfResultListIterator : ocfResultList) {
            Object[] ocfSpecCd = (Object[]) ocfResultListIterator;
            String queryString = queryConstants.getFeatureLst();
            Query query = entityManager.createNativeQuery(queryString);
            query.setParameter(PRMTR_PORCD, ocfSpecCd[POR]);
            query.setParameter(PRMTR_CARSRS, ocfSpecCd[CAR_SRS].toString()
                    .replaceAll("\\s", ""));
            query.setParameter(PRMTR_FEAT_TYPE_CD, ocfSpecCd[FEAT_TYPE_CD]);
            query.setParameter(PRMTR_OCFRGNCD, ocfSpecCd[OCF_REGION_CD]);
            query.setParameter(PRMTR_SHORT_DSC,
                    ((String) ocfSpecCd[SHRT_DESC]).replaceAll("\\s", ""));
            query.setParameter(PRMTR_OCFBYRGRPCD, ocfSpecCd[OCF_BUYER_GRP_CD]);
            query.setParameter(PRMTR_OCF_FRAME_CD, ocfSpecCd[OCF_FRME_CD]);
            mstFeatLst = query.getResultList();
            String msgBatchId = null;
            if (this.decideBatch.equals(BATCH000004)) {
                msgBatchId = BATCH4_ID;
            } else {
                msgBatchId = BATCH5_ID;
            }
            if (mstFeatLst.isEmpty()) {
                // this.stepExecution.getJobExecution().setExitStatus(ExitStatus.STOPPED);
                LOG.info(msgBatchId
                        + FTRE_PARAMTER + por + FTRE_PARAMTER_TABLE);
                LOG.info("Batch stopped with FAILED status");
                CommonUtil.stopBatch();
            }
            if (productionType.equals(WEEKLY)) {
                ftreAdptAblshDateWeekly(mstFeatLst, eiSpecMst, basePerioddate,
                        feaureTmpLst, ocfSpecCd, featureList);
                
            } else {
                
                ftreAdptAblshDateMonthly(mstFeatLst, eiSpecMst, basePerioddate,
                        feaureTmpLst, featureMstUpdate, ocfSpecCd, featureList);
            }
            
        }
        LOG.info(DOLLAR+OUTSIDE_METHOD+DOLLAR);
        
    }
    
    /**
     * Batch : 4,5 Process ID: P0005.1 , P0005.2 Delete Orderable End Item If
     * Previous exists for POR
     *
     * @param mstFeatLst the mst feat lst
     * @param eiSpecMst the ei spec mst
     * @param featureList the feature list
     * @param basePeriodDate the base period date
     * @param productionType the production type
     * @param ocfResultList the ocf result list
     * @throws ParseException the parse exception
     */
    
    /**
     * Batch : 4 Process ID: P00004 Check whether the 00 Frame code Exists for
     * Previous Records and insert if new Record
     * 
     * @param mstFeatLst
     * @param eiSpecMst
     * @param newFeature1
     * @param productionType
     * @param ocfResultList
     * @param decideBatch
     * @param featureList
     * @param ocfResultList
     * @throws ParseException
     */
    private void zeroFrameCdCheck(List<Object[]> mstFeatLst,
            B00005Dao eiSpecMst, List<Object> featureList, Date basePeriodDate,
            String productionType, List<Object[]> ocfResultList)
            throws ParseException {
        /* To Update Report and adopt and abolish date */
    	LOG.info(DOLLAR+INSIDE_METHOD+DOLLAR);
        getOtherRegionFeature(mstFeatLst, eiSpecMst, basePeriodDate,
                productionType, ocfResultList);
        
        if (!mstFeatLst.isEmpty()) {
            for (Object obj : mstFeatLst) {
                obj = appendValue((Object[]) obj, eiSpecMst.getUkOeiBuyerId());
                featureList.add(obj);
                
            }
            
        }
        LOG.info(DOLLAR+OUTSIDE_METHOD+DOLLAR);
        
    }
    
    /**
     * Batch : 4 ,5 Process ID: P0003 Extract the OCF for Lesser Priority Filter
     * the OCF based on the Priority.
     *
     * @param ocfResultList the ocf result list
     * @param batchFeatureCd the batch feature cd
     * @return the list
     */
    private List<Object[]> checkPriority(List<Object[]> ocfResultList,
            String batchFeatureCd) {
    	LOG.info(DOLLAR+INSIDE_METHOD+DOLLAR); 
        Map<Object, HashMap<String, ArrayList<String>>> ocfFeature = new HashMap<Object, HashMap<String, ArrayList<String>>>();
        
        for (Object obj : ocfResultList) {
            
            List<String> priority = new ArrayList<String>();
            Object[] ocf = (Object[]) obj;
            String subkey = "";
            String key = getOcfString(ocf);
            if (ocfFeature.containsKey(key)) {
                Map<String, ArrayList<String>> featureType = ocfFeature
                        .get(key);
                if (batchFeatureCd.equals((String) ocf[FEAT_TYPE_CD])) {
                    subkey = (String) ocf[FEAT_TYPE_CD];
                } else {
                    subkey = (String) ocf[FEAT_TYPE_CD];
                }
                if (featureType.containsKey(subkey)) {
                    List<String> priority1 = featureType.get(subkey);
                    priority1.add((String) ocf[OCF_PRITY_CD]);
                    Collections.sort(priority1);
                } else {
                    priority.add((String) ocf[OCF_PRITY_CD]);
                    featureType.put(subkey, (ArrayList<String>) priority);
                    ocfFeature.put(getOcfString(ocf),
                            (HashMap<String, ArrayList<String>>) featureType);
                }
            } else {
                Map<String, ArrayList<String>> featureType = new HashMap<String, ArrayList<String>>();
                
                if (batchFeatureCd.equals((String) ocf[FEAT_TYPE_CD])) {
                    subkey = (String) ocf[FEAT_TYPE_CD];
                    
                } else {
                    subkey = (String) ocf[FEAT_TYPE_CD];
                }
                
                priority.add((String) ocf[OCF_PRITY_CD]);
                featureType.put(subkey, (ArrayList<String>) priority);
                ocfFeature.put(key,
                        (HashMap<String, ArrayList<String>>) featureType);
                
            }
        }
        LOG.info(DOLLAR+OUTSIDE_METHOD+DOLLAR);
        return selectOcf(ocfFeature, batchFeatureCd, ocfResultList);
        
    }
    
    /**
     * Batch : 4,5 Process ID : P00003 Split the Map (Key , Value) into the
     * Object Array and assign to a conslidated List.
     *
     * @param ocfFeature the ocf feature
     * @param batchFeatureCd the batch feature cd
     * @param ocfResultList the ocf result list
     * @return the list
     */
    private List<Object[]> selectOcf(
            Map<Object, HashMap<String, ArrayList<String>>> ocfFeature,
            String batchFeatureCd, List<Object[]> ocfResultList) {
    	LOG.info(DOLLAR+INSIDE_METHOD+DOLLAR);
        List<Object[]> ocfList = new ArrayList<Object[]>();
        for (Map.Entry<Object, HashMap<String, ArrayList<String>>> entry : ocfFeature
                .entrySet()) {
            String key = (String) entry.getKey();
            Map<String, ArrayList<String>> value = entry.getValue();
            List<String> ocfFinalList = new ArrayList<String>(Arrays.asList(key
                    .split(",")));
            selectPriorityOcf(ocfFinalList, ocfResultList, ocfList,
                    batchFeatureCd, value);
            
        }
        LOG.info(DOLLAR+OUTSIDE_METHOD+DOLLAR);
        return ocfList;
        
    }
    
    /**
     * * Batch : 4,5 Process ID : P00003 Split the Map (Key , Value) into the
     * Object Array and assign to a conslidated List For the Key select the
     * Minimum Priority OCF.
     *
     * @param ocfFinalList the ocf final list
     * @param ocfResultList the ocf result list
     * @param ocfList the ocf list
     * @param batchFeatureCd the batch feature cd
     * @param value the value
     */
    private void selectPriorityOcf(List<String> ocfFinalList,
            List<Object[]> ocfResultList, List<Object[]> ocfList,
            String batchFeatureCd, Map<String, ArrayList<String>> value) {
    	LOG.info(DOLLAR+INSIDE_METHOD+DOLLAR);
        if (value.containsKey(batchFeatureCd)) {
            
            minimumPrtyOcf(ocfFinalList, ocfResultList, ocfList,
                    batchFeatureCd, value);
        } else {
            Map<Integer, String> listFeatCd = new TreeMap<Integer, String>();
            for (String featCd : value.keySet()) {
                
                String key1 = value.get(featCd).get(0);
                key1 = key1.replaceAll(DELIMITE_SPACE, "");
                listFeatCd.put(Integer.parseInt(key1), featCd);
                minimumPrtyOcf(ocfFinalList, ocfResultList, ocfList, featCd,
                        value);
            }
        }
        LOG.info(DOLLAR+OUTSIDE_METHOD+DOLLAR);
        
    }
    
    /**
     * *
     * Batch : 4,5 Process ID : P00003 Split the Map (Key , Value) into the
     * Object Array and assign to a conslidated List Condition checking for OCF
     * Buyer Group Code and OCF Priority Code.
     *
     * @param ocfFinalList the ocf final list
     * @param ocfResultList the ocf result list
     * @param ocfList the ocf list
     * @param batchFeatureCd the batch feature cd
     * @param value the value
     */
    private void minimumPrtyOcf(List<String> ocfFinalList,
            List<Object[]> ocfResultList, List<Object[]> ocfList,
            String batchFeatureCd, Map<String, ArrayList<String>> value) {
    	LOG.info(DOLLAR+INSIDE_METHOD+DOLLAR);
        for (Object obj : ocfResultList) {
            
            Object[] ocfTemp = (Object[]) obj;
            
            if (checkCondition(ocfTemp, ocfFinalList)
                    && ((ocfTemp[OCF_BUYER_GRP_CD]).toString()
                            .equals(ocfFinalList.get(4).toString()))
                    && ((ocfTemp[FEAT_TYPE_CD]).toString()
                            .equals(batchFeatureCd.toString()))
                    
                    && ((Integer.parseInt(ocfTemp[OCF_PRITY_CD].toString()
                            .trim())) == (Integer.parseInt(value
                            .get(batchFeatureCd).get(0).toString().trim())))) {
                ocfList.add(ocfTemp);
            }
            
        }
        LOG.info(DOLLAR+OUTSIDE_METHOD+DOLLAR);
        
    }
    
    /**
     * Batch : 4,5 Process ID : P00003 Split the Map (Key , Value) into the
     * Object Array and assign to a conslidated List Condition checking for POR
     * , Car Series , OCF Frame Code , Ocf Region Code.
     *
     * @param ocfTemp the ocf temp
     * @param ocfFinalList the ocf final list
     * @return the boolean
     */
    private Boolean checkCondition(Object[] ocfTemp, List<String> ocfFinalList) {
    	LOG.info(DOLLAR+INSIDE_METHOD+DOLLAR); 
        return ((ocfTemp[POR]).toString()
                .equals(ocfFinalList.get(0).toString()))
                && ((ocfTemp[CAR_SRS]).toString().equals(ocfFinalList.get(1)
                        .toString()))
                && ((ocfTemp[OCF_FRME_CD]).toString().equals(ocfFinalList
                        .get(2).toString()))
                && ((ocfTemp[OCF_REGION_CD]).toString().equals(ocfFinalList
                        .get(3).toString()));
        
    }
    
    /**
     * Batch : 4 Process : P00003 Concat the OCF object to generate as a KEy for
     * the Map Object.
     *
     * @param ocf the ocf
     * @return the ocf string
     */
    private String getOcfString(Object[] ocf) {
    	LOG.info(DOLLAR+INSIDE_METHOD+DOLLAR);
        return ocf[POR] + "," + ocf[CAR_SRS] + "," + ocf[OCF_FRME_CD] + ","
                + ocf[OCF_REGION_CD] + "," + ocf[OCF_BUYER_GRP_CD] + ",";
        
    }
    
    /**
     * Batch : 4 ,5 Process ID : P0003 Option Spec Code Comparison for the
     * Extracted OCF's Filetr the OCF if Option Spec Code doesn't Exists.
     *
     * @param ocfResultList the ocf result list
     * @param eiSpecMst the ei spec mst
     * @return the list
     */
    public List<Object[]> checkOptionSpecCd(List<Object[]> ocfResultList,
            B00005Dao eiSpecMst) {
    	LOG.info(DOLLAR+INSIDE_METHOD+DOLLAR);
        List<Object[]> comparedOcf = new ArrayList<Object[]>();
        String eiOptionSpecCd = eiSpecMst.getOptionSpecCd();
        List<String> eiOptionSpecCdLst = new ArrayList<String>();
        if(eiOptionSpecCd!=null && !eiOptionSpecCd.equalsIgnoreCase("")){
        	eiOptionSpecCdLst = CommonUtil.splitString(eiOptionSpecCd,
                6);
        }
        for (Object ocfResultListIterator : ocfResultList) {
            Object[] ocfSpecCd = (Object[]) ocfResultListIterator;
            String optionSpecCd = null;
            if(ocfSpecCd[OPTN_SPEC_CD]!=null) {
            	optionSpecCd = (String) ocfSpecCd[OPTN_SPEC_CD];	
            } 
            
            int matchedflag = 0;
            if (optionSpecCd == null) {
                matchedflag = 1;
            } else {
                matchedflag = optionSpecCdComparison(optionSpecCd,
                        eiOptionSpecCdLst);
            }
            if (matchedflag == 1) {
                comparedOcf.add(ocfSpecCd);
            }
        }
        LOG.info(DOLLAR+OUTSIDE_METHOD+DOLLAR);
        return comparedOcf;
        
    }
    
    /**
     * Option spec cd comparison.
     *
     * @param optionSpecCd the option spec cd
     * @param eiOptionSpecCdLst the ei option spec cd lst
     * @return the int
     */
    private int optionSpecCdComparison(String optionSpecCd,
            List<String> eiOptionSpecCdLst) {
    	LOG.info(DOLLAR+INSIDE_METHOD+DOLLAR);
        int lengthIterator = 0;
        int matchedflag = 0;
        while (lengthIterator < (optionSpecCd.length() - 1) && matchedflag == 0) {
            String specCdSub = optionSpecCd.substring(lengthIterator,
                    lengthIterator + 5);
            if (eiOptionSpecCdLst.contains(specCdSub)) {
                matchedflag = 1;
            }
            
            lengthIterator += 5;
        }
        LOG.info(DOLLAR+OUTSIDE_METHOD+DOLLAR);
        return matchedflag;
    }
    
    /**
     * Batch :4 ,5 Process Id's: Process ID:
     * P0001.1,P0001.2,P0001.3,P0001.4,P0001.5 Calculating the Order take Base
     * Period before Processing the Step
     *
     * @param stepExecution the step execution
     * @throws ParseException the parse exception
     * @throws PdApplicationNonFatalException the pd application non fatal exception
     */
    @SuppressWarnings("unchecked")
    @BeforeStep
    public void retrieveInterstepData(StepExecution stepExecution)
            throws ParseException, PdApplicationNonFatalException {
    	LOG.info(DOLLAR+INSIDE_METHOD+DOLLAR);
    	String errorPath = environment.getProperty(B000004_REPORT_PATH);
    	  DateFormat dateFormat = new SimpleDateFormat(DATE_TIME_FORMAT);
        
        
        String fileName = errorPath.trim()+REPORT_SUFFIX_B4+dateFormat.format(new Date())+FILE_EXT_TSV;

        this.stepExecution = stepExecution;
        this.jobExecution = stepExecution.getJobExecution();
        ExecutionContext stepContext = this.jobExecution.getExecutionContext();
        stepContext.put(PRMTR_FILE_NAME, fileName);

        JobParameters jobInputs = jobExecution.getJobParameters();
        this.por = jobInputs.getString(PRMTR_POR);
        this.decideBatch = jobInputs.getString(PRMTR_BATCH);
        String msgBatchId = null;
        if (this.decideBatch.equals(BATCH000004)) {
            msgBatchId = BATCH4_ID;
        } else {
            msgBatchId = BATCH5_ID;
        }
        String otbperiodWeekly = "";
        String tmpOtbperiodWeekly = "";    
        String otbperriodMonthly = "";
    
        String ftreAbolishDate = "";
        int prmtrValue = 0;
        
        /* To Get the Order take Base Period Weekly */
        String weeklyQueryString = queryConstants.getBasePeriodWeekly();
        Query weeklyQuery = entityManager.createNativeQuery(weeklyQueryString);
        weeklyQuery.setParameter(PRMTR_PORCD, por);
        List<Object> weeklyresultList = weeklyQuery.getResultList();
        
        if (weeklyresultList !=null && weeklyresultList.get(0) == null) {
            LOG.info(msgBatchId + B4_OTBM + por
                    + B4_WEEKLY_MST);
            LOG.info("Batch stopped with FAILED status");
          CommonUtil.stopBatch();
        } else {
            tmpOtbperiodWeekly = (String) weeklyresultList.get(0);
        }
        
        /* To Get the Minimum parameter value from parameter Master */
        String queryString = queryConstants.getParameterValue();
        Query query = entityManager.createNativeQuery(queryString);
        query.setParameter(PRMTR_PORCD, por);
        List<Object> resultList = query.getResultList();
        
        if ((resultList!=null && resultList.isEmpty()) || resultList.get(0) == null) {
        	  LOG.info(msgBatchId
                    + B4_MIN_PARAMTER + por + PARAMETER_MSG);
        	  LOG.info("Batch stopped with FAILED status");
        	  CommonUtil.stopBatch();
        } else {
            prmtrValue = Integer.parseInt(resultList.get(0).toString());
            otbperiodWeekly = dateToString(addMonthToDate(
                    convertStringToDate(tmpOtbperiodWeekly), prmtrValue));
        }
        
        /* To Get the Order take Base Period Monthly */
        String monthlyQueryString = queryConstants.getBasePeriodMonthly();
        Query monthlyQuery = entityManager
                .createNativeQuery(monthlyQueryString);
        monthlyQuery.setParameter(PRMTR_PORCD, por);
        List<Object> monthlyResultList = monthlyQuery.getResultList();
        if ((monthlyResultList!=null && monthlyResultList.isEmpty()) || monthlyResultList.get(0) == null) {
            LOG.info(msgBatchId + B4_OTBM + por
                    + B4_MONTHLY_MST);
            LOG.info("Batch stopped with FAILED status");
            CommonUtil.stopBatch();
        } else {
            otbperriodMonthly = (String) monthlyResultList.get(0) + ZERO_ONE;
        }
        
        /* To Get the production Stage Code from Parameter Master */
        String prdStageCd = queryConstants.getProdStageCd();
        Query prodQuery = entityManager.createNativeQuery(prdStageCd);
        prodQuery.setParameter(PRMTR_PORCD, por);
        List<Object> prodStageResultList = prodQuery.getResultList();
        
        if ((prodStageResultList!=null && prodStageResultList.isEmpty()) || prodStageResultList.get(0) == null) {
            
            LOG.info(msgBatchId
                    + B4_PRD_STAGE_CD + por + PARAMETER_MSG);
            LOG.info("Batch stopped with FAILED status");
            CommonUtil.stopBatch();
        }
        
        /*
         * To get the Maximum Feature Abolish date from Parameter Master for
         * Batch 4
         */
        String ftreQueryString = queryConstants.getFtreAbolishDate();
        Query ftreQuery = entityManager.createNativeQuery(ftreQueryString);
        ftreQuery.setParameter(PRMTR_PORCD, por);
        List<Object> featureResultList = ftreQuery.getResultList();
        if (decideBatch.equals(BATCH000004) && featureResultList.isEmpty()) {
            LOG.info(msgBatchId
                    + B4_ABOLISH_DATE + por + PARAMETER_MSG);
            LOG.info("Batch stopped with FAILED status");
            CommonUtil.stopBatch();
        }
        
        for (Object obj : featureResultList) {
            ftreAbolishDate = (String) obj;
            
        }
        stepContext.put(PRMTR_PRODUCTION_STAGE_CODE, prodStageResultList);
        stepContext.put(PRMTR_BASE_PERIOD_WEEKLY, otbperiodWeekly);
        stepContext.put(PRMTR_FTRE_ABLSH_DATE, ftreAbolishDate);
        if (!otbperiodWeekly.isEmpty() || !otbperriodMonthly.isEmpty()) {
            
            listBasePeriod = getOrdertakeBasePeriodList(otbperiodWeekly,
                    otbperriodMonthly);
        }
        JobParameters jobParameters = this.jobExecution.getJobParameters();
        this.decideBatch = jobParameters.getString("batch");
        if (BATCH000004.equals(decideBatch)) {
            this.batchFeatureCd = TEN;
        } else {
            this.batchFeatureCd = FORTY;
        }
        abolishDate = (String) stepContext.get("orderTakeBasePeriodWeekly");
        LOG.info(DOLLAR+OUTSIDE_METHOD+DOLLAR);
        
    }
    
    /**
     * Append value.
     *
     * @param obj the obj
     * @param newObj the new obj
     * @return the object[]
     */
    private Object[] appendValue(Object[] obj, Object newObj) {
    	LOG.info(DOLLAR+INSIDE_METHOD+DOLLAR);
        List<Object> temp = new ArrayList<Object>(Arrays.asList(obj));
        temp.add(newObj);
        LOG.info(DOLLAR+OUTSIDE_METHOD+DOLLAR);
        return temp.toArray();
        
    }
    
    /**
     * Batch : 4,5 Process Id: P0001.5 Order take Base Period List
     *
     * @param orderTakeBasePeriodWeekly the order take base period weekly
     * @param orderTakeBasePeriodMonthly the order take base period monthly
     * @return the ordertake base period list
     * @throws ParseException the parse exception
     */
    
    public List<ArrayList<String>> getOrdertakeBasePeriodList(
            String orderTakeBasePeriodWeekly, String orderTakeBasePeriodMonthly)
            throws ParseException {
    	LOG.info(DOLLAR+INSIDE_METHOD+DOLLAR);
        SimpleDateFormat ordrTakeBaseMnthWeeklyformatter = new SimpleDateFormat(
                DATE_FORMAT);
        Date date = ordrTakeBaseMnthWeeklyformatter
                .parse(orderTakeBasePeriodWeekly);
        SimpleDateFormat orderTakeBasePeriodMonthlyformatter = new SimpleDateFormat(
                DATE_FORMAT);
        Date date1 = orderTakeBasePeriodMonthlyformatter
                .parse(orderTakeBasePeriodMonthly);
        SimpleDateFormat toStringformatter = new SimpleDateFormat(DATE_FORMAT);
        List<ArrayList<String>> orderTakeBasePeriod = new ArrayList<ArrayList<String>>();
        Date date2 = date;
        for (; toStringformatter.format(date2).compareTo(
                toStringformatter.format(date1)) < 1;) {
            int comparedValue = toStringformatter.format(date2).compareTo(
                    toStringformatter.format(date1));
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date1);
            Calendar calendar1 = Calendar.getInstance();
            calendar1.setTime(date2);
            String orderTakeBasePeriod1 = toStringformatter.format(date2);
            List<String> orderTakeBasePeriod2 = new ArrayList<String>();
            orderTakeBasePeriod2.add(orderTakeBasePeriod1);
            if (comparedValue < 0) {
                orderTakeBasePeriod2.add(WEEKLY);
            } else {
                orderTakeBasePeriod2.add(MONTHLY);
            }
            orderTakeBasePeriod.add((ArrayList<String>) orderTakeBasePeriod2);
            calendar1.add(Calendar.MONTH, 1);
            
            date2 = calendar1.getTime();
        }
        LOG.info(DOLLAR+OUTSIDE_METHOD+DOLLAR);
        return orderTakeBasePeriod;
        
    }
    
    /**
     * Fetch the Result set from Data base.
     *
     * @param queryString the query string
     * @return the result list
     */
    @SuppressWarnings("unchecked")
    public List<Object> getResultList(String queryString) {
    	LOG.info(DOLLAR+INSIDE_METHOD+DOLLAR);
        Query query = entityManager.createNativeQuery(queryString);
        LOG.info(DOLLAR+OUTSIDE_METHOD+DOLLAR);
        return query.getResultList();
        
    }
    
    /**
     * Update , Delete and Insert Operations in the Data base.
     *
     * @param feature the feature
     */
    public void insertFeature(Object[] feature) {
    	LOG.info(DOLLAR+INSIDE_METHOD+DOLLAR);
        String queryString = queryConstants.insertFeature();
        Query query = entityManager.createNativeQuery(queryString);
        query.setParameter(PRMTR_PORCD, feature[FEATURE_POR_CD]);
        query.setParameter(PRMTR_CARSRS, feature[FEATURE_CAR_SRS]);
        query.setParameter(PRMTR_FEAT_CD, feature[FEATURE_FEAT_CD]);
        query.setParameter(PRMTR_FEAT_TYPE_CD, feature[FEATURE_FEAT_TYPE_CD]);
        query.setParameter(PRMTR_OCF_FRAME_CD, feature[FEATURE_OCF_FRME_CD]);
        query.setParameter(PRMTR_FTRE_ADPT_DATE,
                feature[FEATURE_FEAT_ADPT_DATE]);
        query.setParameter(PRMTR_OCFRGNCD,
                feature[FEATURE_OCF_REGION_CD].toString());
        query.setParameter(PRMTR_OCFBYRGRPCD,
                feature[FEATURE_OCF_BUYER_GRP_CD].toString());
        query.setParameter(PRMTR_FTRE_ABLSH_DATE,
                feature[FEATURE_FEAT_ABLSH_DATE]);
        query.setParameter(PRMTR_FEAT_SHRT_DESC,
                feature[FEATURE_FEAT_SHRT_DESC]);
        query.setParameter(PRMTR_FEAT_LONG_DESC, feature[FEATURE_FEAT_LNG_DESC]);
        query.setParameter(PRMTR_FEAT_GRP_CD, feature[FEATURE_FEAT_GRP_CD]);
        
        LOG.info(query);
        
        int result = query.executeUpdate();
        if (result != 0) {
            LOG.info(FEATURE_MST_INSERT_SUCCESS_MSG);
        } 
        LOG.info(DOLLAR+OUTSIDE_METHOD+DOLLAR);
        
    }
    
    /**
     * Batch :4,5 Process ID:P0003 Pattern matching left out in Query Process.
     *
     * @param ocfResultList the ocf result list
     * @param batchFeatureCd the batch feature cd
     * @param eiSpecMst the ei spec mst
     * @return the list
     */
    public List<Object[]> compareOcf(List<Object[]> ocfResultList,
            String batchFeatureCd, B00005Dao eiSpecMst) {
    	LOG.info(DOLLAR+INSIDE_METHOD+DOLLAR);
        List<Object[]> optionComparedOcf = checkOptionSpecCd(ocfResultList,
                eiSpecMst);
        LOG.info(DOLLAR+OUTSIDE_METHOD+DOLLAR);
        return checkPriority(optionComparedOcf, batchFeatureCd);
        
    }
    
    /**
     * Batch :4 PRocess ID: P00004.2 Check and create 00 Frame Code for End Item
     *
     * @param featureResultList the feature result list
     * @param eiSpecMst the ei spec mst
     * @param basePeriodDate the base period date
     * @param productionType the production type
     * @param ocfResultList the ocf result list
     * @return the other region feature
     * @throws ParseException the parse exception
     */
    @SuppressWarnings("unchecked")
    private void getOtherRegionFeature(List<Object[]> featureResultList,
            B00005Dao eiSpecMst, Date basePeriodDate, String productionType,
            List<Object[]> ocfResultList) throws ParseException {
    	LOG.info(DOLLAR+INSIDE_METHOD+DOLLAR);
        List<Object[]> feature = new ArrayList<Object[]>();
        List<Object> featureToInsert = new ArrayList<Object>();
        Byte ocfMatchFlg = ocfFrameCdCheck(ocfResultList);
        List<Object[]> newFeature1 = new ArrayList<Object[]>();
        List<Object[]> tmpFtre = null;
        /*
         * Changes Done after review
         */
        
        Byte matchFlg = frameCdCheck(featureResultList);
        if (matchFlg == 0) {
            if (ocfMatchFlg == 0) {
                tmpFtre = extractNoOcfFeature(eiSpecMst, newFeature1,
                        featureResultList, basePeriodDate, productionType);
            }
            
            if (newFeature1.isEmpty()) {
                // to modify the Query to extract other region Features
                
                String queryString = queryConstants.getOtherRegionFeatureLst();
                Query query = entityManager.createNativeQuery(queryString);
                query.setParameter(PRMTR_PORCD, eiSpecMst.getPorCd());
                query.setParameter(PRMTR_CARSRS, eiSpecMst.getCarSeries()
                        .trim());
                feature = query.getResultList();
                
                matchFlg = insertOtherRegionFeature(feature, featureToInsert,
                        eiSpecMst, featureResultList);
                
            } else {
                           
                matchFlg = 1;
            }
        }
        /**
         * 00 Frame code is not available in Other Region and to create new
         * feature
         */
        
        if (matchFlg == 0) {
            String queryString = queryConstants.getFeatureWithoutCarSeries();
            Query query = entityManager.createNativeQuery(queryString);
            List<Object> resultList = query.getResultList();
            
            if (!resultList.isEmpty()) {
                Object[] newFeature = (Object[]) resultList.get(0);
                String tmpFeatureCd = (String) newFeature[FEATURE_FEAT_CD];
                int tmpFtreCd = Integer.parseInt(tmpFeatureCd);
                tmpFtreCd++;
                String newFtreCd = appendZero(Integer.toString(tmpFtreCd));
                createNewFeature(featureResultList, WEEKLY, eiSpecMst,
                        newFtreCd);
                createNewFeature(featureResultList, MONTHLY, eiSpecMst,
                        newFtreCd);
                
            } else {
                createNewFeature(featureResultList, WEEKLY, eiSpecMst, ONE);
                createNewFeature(featureResultList, MONTHLY, eiSpecMst, ONE);
                
            }
        }
        
            featureCdCheck(featureResultList, eiSpecMst);
            LOG.info(DOLLAR+OUTSIDE_METHOD+DOLLAR);
        
    }
    
    /**
     * Ocf frame cd check.
     *
     * @param ocfResultList the ocf result list
     * @return the byte
     */
    private Byte ocfFrameCdCheck(List<Object[]> ocfResultList) {
    	LOG.info(DOLLAR+INSIDE_METHOD+DOLLAR);
        Byte matchedFlg = 0;
        Iterator<Object[]> iterator = ocfResultList.iterator();
        while (iterator.hasNext() && matchedFlg == 0) {
            Object[] ocf = (Object[]) iterator.next();
            if (OCF_FRAME_CODE_ZERO.equals(ocf[3])) {
                matchedFlg = 1;
                
            }
        }
        LOG.info(DOLLAR+OUTSIDE_METHOD+DOLLAR);
        return matchedFlg;
        
    }
    
    /**
     * Append zero.
     *
     * @param tmpFeatureCd the tmp feature cd
     * @return the string
     */
    private String appendZero(String tmpFeatureCd) {
    	LOG.info(DOLLAR+INSIDE_METHOD+DOLLAR);
        String featureCd = tmpFeatureCd;
        while (featureCd.length() < 5) {
            featureCd = PRMTR_ZERO + featureCd;
        }
        LOG.info(DOLLAR+OUTSIDE_METHOD+DOLLAR);
        return featureCd;
    }
    
    /**
     * Batch : 4 Process ID: P0004.2 To check whether Other Region Features have
     * same short description as car series of End Item If exists, insert with
     * same feature Code Else Increment the Feature code by one and Insert
     *
     * @param feature the feature
     * @param featureToInsert the feature to insert
     * @param eiSpecMst the ei spec mst
     * @param featureResultList the feature result list
     * @return the byte
     * @throws ParseException the parse exception
     */
    
    private Byte insertOtherRegionFeature(List<Object[]> feature,
            List<Object> featureToInsert, B00005Dao eiSpecMst,
            List<Object[]> featureResultList) throws ParseException {
    	LOG.info(DOLLAR+INSIDE_METHOD+DOLLAR);
        Byte matchFlg = 0;
        Byte carSeriesMatchFlg = 0;
        
        String eiCarSrs = eiSpecMst.getCarSeries().trim();
        Iterator<Object[]> featureLst = feature.iterator();
        while (featureLst.hasNext() && carSeriesMatchFlg == 0) {
            Object[] featureAry = (Object[]) featureLst.next();
            
            String ftreCarSrs = featureAry[FEATURE_FEAT_SHRT_DESC].toString()
                    .trim();
            if (eiCarSrs.equals(ftreCarSrs)) {
                String featureCd = featureAry[FEATURE_FEAT_CD].toString();
                carSeriesMatchFlg = 1;
                createNewFeature(featureResultList, WEEKLY, eiSpecMst,
                        featureCd);
                createNewFeature(featureResultList, MONTHLY, eiSpecMst,
                        featureCd);
                zerofrmeCdErrorFlg.put(eiSpecMst, featureAry);
            }
            featureAry[FEATURE_FEAT_ADPT_DATE] = eiSpecMst.getOseiAdoptDate();
            featureAry[FEATURE_FEAT_ABLSH_DATE] = this.stepExecution
                    .getJobExecution().getExecutionContext()
                    .get(PRMTR_FTRE_ABLSH_DATE);
            
            featureToInsert.add(featureAry);
            matchFlg = 1;
            
        }
        if (carSeriesMatchFlg == 0) {
            matchFlg = 0;
        }
        LOG.info(DOLLAR+OUTSIDE_METHOD+DOLLAR);
        return matchFlg;
    }
    
    /**
     * Batch : 4 Process ID:P0004.1 There will be features without the OCF's
     * This method will extract the Non OCF Features as error case
     *
     * @param eiSpecMst the ei spec mst
     * @param newFeature1 the new feature1
     * @param featureResultList the feature result list
     * @param basePeriodDate the base period date
     * @param productionType the production type
     * @return the list
     * @throws ParseException the parse exception
     */
    @SuppressWarnings("unchecked")
    private List<Object[]> extractNoOcfFeature(B00005Dao eiSpecMst,
            List<Object[]> newFeature1, List<Object[]> featureResultList,
            Date basePeriodDate, String productionType) throws ParseException {
    	LOG.info(DOLLAR+INSIDE_METHOD+DOLLAR);
        String queryString = queryConstants.getNoOcfFeatureLst();
        Query query = entityManager.createNativeQuery(queryString);
        query.setParameter(PRMTR_PORCD, eiSpecMst.getPorCd());
        query.setParameter(PRMTR_CARSRS, eiSpecMst.getCarSeries().trim());
        query.setParameter(PRMTR_OCFRGNCD, eiSpecMst.getOcfRegionCd());
        query.setParameter(PRMTR_OCFBYRGRPCD, eiSpecMst.getOcfBuyerGroupCd());
        List<Object> resultList = checkTenFeatureCd(query.getResultList());
        
        List<Object[]> tmpLst = new ArrayList<Object[]>();
        for (Object obj : resultList) {
            
            Object[] featureLst = (Object[]) obj;
            newFeature1.add(featureLst);
            Date ftreAdptDate, ftreAblshDate;
            try {
                
               // ftreAdptDate = convertStringToDate(featureLst[FEATURE_FEAT_ADPT_DATE]
                     //   .toString());
                //ftreAblshDate = convertStringToDate(featureLst[FEATURE_FEAT_ABLSH_DATE]
                              //  .toString());
              ftreAdptDate = convertStringToDate(eiSpecMst.getOseiAdoptDate());
              String date = (String) this.stepExecution
                      .getJobExecution().getExecutionContext()
                      .get(PRMTR_FTRE_ABLSH_DATE);
           ftreAblshDate = convertStringToDate(date);
                if (productionType.equals(MONTHLY)) {
                    
                    compareAdptAblshDate(featureLst, tmpLst,
                            featureLst[FEATURE_OCF_FRME_CD].toString(),
                            eiSpecMst, ftreAdptDate, ftreAblshDate,
                            basePeriodDate);
                } else {
                    compareAdptDateWeekly(ftreAdptDate, eiSpecMst, featureLst,
                            basePeriodDate, tmpLst, null);
                }
                featureResultList.add(featureLst);
                
            } catch (ParseException e) {
                LOG.info(EXCEPTION, e);
                
            }
            b5ReportGenration.generateReportError(featureLst, eiSpecMst);
            
        }
        LOG.info(DOLLAR+OUTSIDE_METHOD+DOLLAR);
        return tmpLst;
        
    }
    
    /**
     * Check ten feature cd.
     *
     * @param resultList the result list
     * @return the list
     */
    private List<Object> checkTenFeatureCd(List<Object> resultList) {
    	LOG.info(DOLLAR+INSIDE_METHOD+DOLLAR);
        Iterator<Object> iterator = resultList.iterator();
        List<Object> filteredList = new ArrayList<Object>();
        Boolean tenMatchFlag = false;
        while (iterator.hasNext() && !tenMatchFlag) {
            Object[] feature = (Object[]) iterator.next();
            if (feature[FEATURE_FEAT_TYPE_CD].toString().equals(TEN)) {
                filteredList.add(feature);
                tenMatchFlag = true;
            }
        }
        if (filteredList.isEmpty()) {
        	LOG.info(DOLLAR+OUTSIDE_METHOD+DOLLAR);
            return resultList;
        } else {
        	LOG.info(DOLLAR+OUTSIDE_METHOD+DOLLAR);
            return filteredList;
        }
    }
    
    /**
     * Batch :4 Process ID: P00004.4 Create New Feature code for non existing 00
     * Frame Code
     *
     * @param featureResultList            List of feature's
     * @param productionType            (Weekly or Monthly)
     * @param eiSpecMst the ei spec mst
     * @param featureCd the feature cd
     * @throws ParseException the parse exception
     */
    private void createNewFeature(List<Object[]> featureResultList,
            String productionType, B00005Dao eiSpecMst, String featureCd)
            throws ParseException {
    	LOG.info(DOLLAR+INSIDE_METHOD+DOLLAR);
        String featureCdLocal = featureCd;
        
        Map<Integer, Object[]> featureMap = new TreeMap<Integer, Object[]>(
                Collections.reverseOrder());
        if (!featureResultList.isEmpty()) {
            for (Object obj : featureResultList) {
                Object[] feature = (Object[]) obj;
                if (feature[FEATURE_OCF_FRME_CD].toString().equals(
                        OCF_FRAME_CODE_ZERO)) {
                    String tempFeatureCd = (String) feature[FEATURE_FEAT_CD];
                    featureMap.put(Integer.parseInt(tempFeatureCd.replaceAll(
                            DELIMITE_SPACE, "")), feature);
                    featureCdLocal = (String) feature[FEATURE_FEAT_CD];
                }
            }
            Object[] newFeature = insertFeaturetoArray(featureCdLocal,
                    eiSpecMst, productionType);
            zerofrmeCdErrorFlg.put(eiSpecMst, newFeature);
            featureResultList.add(newFeature);
            Object[] ftreMstUpdateLSt = new Object[FEATURE_SIZE + 1];
            assignToNewAry(ftreMstUpdateLSt, newFeature);
            ftreMstUpdateLSt[FEATURE_FEAT_ADPT_DATE] = eiSpecMst
                    .getOseiAdoptDate();
            String ablshDate = (String) jobExecution.getExecutionContext().get(
                    PRMTR_FTRE_ABLSH_DATE);
            
            ftreMstUpdateLSt[FEATURE_FEAT_ABLSH_DATE] = ablshDate;
            
            insertFeature(ftreMstUpdateLSt);
        } else {
            Object[] newFeature = insertFeaturetoArray(featureCd, eiSpecMst,
                    productionType);
            zerofrmeCdErrorFlg.put(eiSpecMst, newFeature);
            featureResultList.add(newFeature);
            Object[] ftreMstUpdateLSt = new Object[FEATURE_SIZE + 1];
            assignToNewAry(ftreMstUpdateLSt, newFeature);
            ftreMstUpdateLSt[FEATURE_FEAT_ADPT_DATE] = eiSpecMst
                    .getOseiAdoptDate();
            String ablshDate = (String) jobExecution.getExecutionContext().get(
                    PRMTR_FTRE_ABLSH_DATE);
            ftreMstUpdateLSt[FEATURE_FEAT_ABLSH_DATE] = ablshDate;
            insertFeature(ftreMstUpdateLSt);
        }
        LOG.info(DOLLAR+OUTSIDE_METHOD+DOLLAR);
        
    }
    
    /**
     * Batch :4 Process ID: P00004.4 Inserting the new Feature values to an
     * array
     *
     * @param featureCd            newly created feature Code
     * @param eiSpecMst            --End Item
     * @param productionType            --> (Weekly or Monthly)
     * @return the object[]
     */
    private Object[] insertFeaturetoArray(String featureCd,
            B00005Dao eiSpecMst, String productionType) {
    	LOG.info(DOLLAR+INSIDE_METHOD+DOLLAR);
        Object[] newFeature = new Object[FEATURE_SIZE];
        newFeature[FEATURE_POR_CD] = eiSpecMst.getPorCd();
        newFeature[FEATURE_CAR_SRS] = eiSpecMst.getCarSeries();
        newFeature[FEATURE_FEAT_CD] = featureCd;
        newFeature[FEATURE_OCF_FRME_CD] = OCF_FRAME_CODE_ZERO;
        
        if (WEEKLY.equals(productionType)) {
            newFeature[FEATURE_FEAT_TYPE_CD] = THIRTY;
            
        }
        if (MONTHLY.equals(productionType)) {
            newFeature[FEATURE_FEAT_TYPE_CD] = TWENTY;
            
        }
        newFeature[FEATURE_FEAT_ADPT_DATE] = eiSpecMst.getOseiAdoptDate();
        newFeature[FEATURE_FEAT_ABLSH_DATE] = this.stepExecution
                .getJobExecution().getExecutionContext()
                .get(PRMTR_FTRE_ABLSH_DATE);
        
        newFeature[FEATURE_FEAT_GRP_CD] = "";
        newFeature[FEATURE_FEAT_SHRT_DESC] = eiSpecMst.getCarSeries();
        newFeature[FEATURE_FEAT_LNG_DESC] = eiSpecMst.getCarSeries();
        newFeature[FEATURE_CAR_GRP] = "";
        newFeature[FEATURE_OCF_REGION_CD] = eiSpecMst.getOcfRegionCd();
        newFeature[FEATURE_OCF_BUYER_GRP_CD] = eiSpecMst.getOcfBuyerGroupCd();
        LOG.info(DOLLAR+OUTSIDE_METHOD+DOLLAR);
        return newFeature;
    }
    
    /**
     * Batch :4 Process ID: P00003 To check whether the 00 Frame Code is
     * available in OCF.
     *
     * @param featureResultList the feature result list
     * @return Flag to indicate whether the 00 frame code is available
     */
    public Byte frameCdCheck(List<Object[]> featureResultList) {
    	LOG.info(DOLLAR+INSIDE_METHOD+DOLLAR);
        Byte matchedFlg = 0;
        Iterator<Object[]> iterator = featureResultList.iterator();
        while (iterator.hasNext() && matchedFlg == 0) {
            Object[] ocf = (Object[]) iterator.next();
            if (OCF_FRAME_CODE_ZERO.equals(ocf[3])) {
                matchedFlg = 1;
                
            }
        }
        LOG.info(DOLLAR+OUTSIDE_METHOD+DOLLAR);
        return matchedFlg;
        
    }
    
    /**
     * Batch : 4 Process id: P00004.4 Insert for 00 Frame Code if either 20 0r
     * 30 Feature Code not available
     *
     * @param featureResultList the feature result list
     * @param eiSpecMst the ei spec mst
     */
    public void featureCdCheck(List<Object[]> featureResultList,
            B00005Dao eiSpecMst) {
    	LOG.info(DOLLAR+INSIDE_METHOD+DOLLAR);
        Boolean matchFlg = false;
        Boolean matchedWeeklyFlg = false;
        Boolean matchedMonthlyFlg = false;
        
        Iterator<Object[]> iterator = featureResultList.iterator();
        List<String> existingFtreProperties = new ArrayList<String>();
        List<Object[]> existingFtrePropertiesTwenty = new ArrayList<Object[]>();
        List<Object[]> existingFtrePropertiesThirty = new ArrayList<Object[]>();
        
        while (iterator.hasNext() && !matchFlg) {
            Object[] feature = (Object[]) iterator.next();
            
            if (TEN.equals(feature[FEATURE_FEAT_TYPE_CD])
                    && feature[FEATURE_OCF_FRME_CD].toString().equals(
                            OCF_FRAME_CODE_ZERO)) {
                matchFlg = true;
            } else {
                if (THIRTY.equals(feature[FEATURE_FEAT_TYPE_CD])
                        && feature[FEATURE_OCF_FRME_CD].toString().equals(
                                OCF_FRAME_CODE_ZERO)) {
                    existingFtrePropertiesThirty.add(feature);
                    existingFtreProperties.add(feature[FEATURE_FEAT_CD]
                            .toString());
                    existingFtreProperties.add(feature[FEATURE_FEAT_ADPT_DATE]
                            .toString());
                    existingFtreProperties.add(feature[FEATURE_FEAT_ABLSH_DATE]
                            .toString());
                    matchedWeeklyFlg = true;
                    
                }
                if (TWENTY.equals(feature[FEATURE_FEAT_TYPE_CD])
                        && feature[FEATURE_OCF_FRME_CD].toString().equals(
                                OCF_FRAME_CODE_ZERO)) {
                    existingFtrePropertiesTwenty.add(feature);
                    existingFtreProperties.add(feature[FEATURE_FEAT_CD]
                            .toString());
                    existingFtreProperties.add(feature[FEATURE_FEAT_ADPT_DATE]
                            .toString());
                    existingFtreProperties.add(feature[FEATURE_FEAT_ABLSH_DATE]
                            .toString());
                    matchedMonthlyFlg = true;
                    
                }
            }
        }
        if (!matchFlg && existingFtrePropertiesTwenty.size() > 0
                || existingFtrePropertiesThirty.size() > 0) {
            compareList(existingFtrePropertiesTwenty,
                    existingFtrePropertiesThirty, featureResultList);
        }
       
        LOG.info(DOLLAR+OUTSIDE_METHOD+DOLLAR);
    }
    
    /**
     * Batch ID: 4 Process ID: P00004.4 If Multiple twenty and thirty are there
     * for same ocf , then to check corresponding weekly and monthly features
     * are avilable
     *
     * @param existingFtrePropertiesTwenty the existing ftre properties twenty
     * @param existingFtrePropertiesThirty the existing ftre properties thirty
     * @param featureResultList the feature result list
     */
    private void compareList(List<Object[]> existingFtrePropertiesTwenty,
            List<Object[]> existingFtrePropertiesThirty,
            List<Object[]> featureResultList) {
        /*
         * To check whether the corresponding weekly and Monthly features are
         * available
         */
    	LOG.info(DOLLAR+INSIDE_METHOD+DOLLAR);
        for (Object[] objTwenty : existingFtrePropertiesTwenty) {
            Boolean thityMatchFlg = false;
            List<Object[]> resultList = null;
            Iterator<Object[]> thirtyIterator = existingFtrePropertiesThirty
                    .iterator();
            while (thirtyIterator.hasNext() && !thityMatchFlg) {
                Object[] objThirty = thirtyIterator.next();
                thityMatchFlg = compareTwoObject(objTwenty, objThirty);
                /*
                 * if(thityMatchFlg) { featureResultList.add(objThirty); }
                 */
            }
            if (!thityMatchFlg) {
                resultList = checkNoOcfThirty(objTwenty, THIRTY);
                
                if (resultList != null && resultList.size() > 0) {
                    Object[] dbObject = resultList.get(INT_ZERO);
                    dbObject[FEATURE_FEAT_ADPT_DATE] = objTwenty [FEATURE_FEAT_ADPT_DATE]; 
                    dbObject[FEATURE_FEAT_ABLSH_DATE] = objTwenty [FEATURE_FEAT_ABLSH_DATE];
                    thirtyErrorFlg.put(eiSpecMst, dbObject);
                    featureResultList.add(dbObject);
                    
                } else {
                    List<String> existingFtreProperties = new ArrayList<String>();
                    existingFtreProperties.add(objTwenty[FEATURE_FEAT_CD]
                            .toString());
                    existingFtreProperties
                            .add(objTwenty[FEATURE_FEAT_ADPT_DATE].toString());
                    existingFtreProperties
                            .add(objTwenty[FEATURE_FEAT_ABLSH_DATE].toString());
                    
                    insertWeeklyMonthlyFeatures(false, true, featureResultList,
                            eiSpecMst, existingFtreProperties);
                    
                }
            }
        }
        
        for (Object[] objThirty : existingFtrePropertiesThirty) {
            Boolean twentyMatchFlg = false;
            List<Object[]> resultList = null;
            Iterator<Object[]> thirtyIterator = existingFtrePropertiesTwenty
                    .iterator();
            while (thirtyIterator.hasNext() && !twentyMatchFlg) {
                Object[] objTwenty = thirtyIterator.next();
                twentyMatchFlg = compareTwoObject(objTwenty, objThirty);
                /*
                 * if(twentyMatchFlg){ featureResultList.add(objTwenty); }
                 */
            }
            if (!twentyMatchFlg) {
                resultList = checkNoOcfThirty(objThirty, TWENTY);
                
                if (resultList != null && resultList.size() > 0) {
                    Object[] dbObject = resultList.get(INT_ZERO);

                    dbObject[FEATURE_FEAT_ADPT_DATE] = objThirty [FEATURE_FEAT_ADPT_DATE]; 
                    dbObject[FEATURE_FEAT_ABLSH_DATE] = objThirty [FEATURE_FEAT_ABLSH_DATE]; 
                    twentyErrorFlg.put(eiSpecMst, dbObject);

                    featureResultList.add(dbObject);
                    
                    
                } else {
                    List<String> existingFtreProperties = new ArrayList<String>();
                    existingFtreProperties.add(objThirty[FEATURE_FEAT_CD]
                            .toString());
                    existingFtreProperties
                            .add(objThirty[FEATURE_FEAT_ADPT_DATE].toString());
                    existingFtreProperties
                            .add(objThirty[FEATURE_FEAT_ABLSH_DATE].toString());
                    
                    insertWeeklyMonthlyFeatures(true, false, featureResultList,
                            eiSpecMst, existingFtreProperties);
                    
                }
            }
        }
        LOG.info(DOLLAR+OUTSIDE_METHOD+DOLLAR);
        
    }
    
    /**
     * To check whether twenty or thirty 00 frame code exists in database.
     *
     * @param objTwenty the obj twenty
     * @param decider the decider
     * @return the list
     */
    private List<Object[]> checkNoOcfThirty(Object[] objTwenty, String decider) {
    	LOG.info(DOLLAR+INSIDE_METHOD+DOLLAR);
        String queryString = queryConstants.getNoOcfWeeklyMonthlyFeatures();
        Query query = entityManager.createNativeQuery(queryString);
        query.setParameter(PRMTR_PORCD, objTwenty[FEATURE_POR_CD]);
        query.setParameter(PRMTR_CARSRS, objTwenty[FEATURE_CAR_SRS]);
        query.setParameter(PRMTR_FEAT_CD, objTwenty[FEATURE_FEAT_CD]);
        query.setParameter(PRMTR_FEAT_TYPE_CD, decider);
        query.setParameter(PRMTR_OCF_FRAME_CD, OCF_FRAME_CODE_ZERO);
        query.setParameter(PRMTR_OCFRGNCD, objTwenty[FEATURE_OCF_REGION_CD]);
        query.setParameter(PRMTR_OCFBYRGRPCD,
                objTwenty[FEATURE_OCF_BUYER_GRP_CD]);
        LOG.info(DOLLAR+OUTSIDE_METHOD+DOLLAR);
        return query.getResultList();
    }
    
    /**
     * Used to Compare weekly and monthly objects and return boolean value.
     *
     * @param objTwenty the obj twenty
     * @param objThirty the obj thirty
     * @return the boolean
     */
    private Boolean compareTwoObject(Object[] objTwenty, Object[] objThirty) {
    	LOG.info(DOLLAR+INSIDE_METHOD+DOLLAR);
        if (objTwenty[FEATURE_FEAT_CD].toString().equals(
                objThirty[FEATURE_FEAT_CD].toString())) {
            return true;
        }
        LOG.info(DOLLAR+OUTSIDE_METHOD+DOLLAR);
        return false;
        
    }
    
    /**
     * Batch :4 Process ID: P0004.4 To Insert the weekly and monthly Features
     * for the End Item If feature type Code 10 doesn't exists, Insert 20,30
     *
     * @param tmpmatchedWeeklyFlg the tmpmatched weekly flg
     * @param tmpmatchedMonthlyFlg the tmpmatched monthly flg
     * @param featureResultList the feature result list
     * @param eiSpecMst the ei spec mst
     * @param existingFtreProperties the existing ftre properties
     */
    private void insertWeeklyMonthlyFeatures(Boolean tmpmatchedWeeklyFlg,
            Boolean tmpmatchedMonthlyFlg, List<Object[]> featureResultList,
            B00005Dao eiSpecMst, List<String> existingFtreProperties) {
    	LOG.info(DOLLAR+INSIDE_METHOD+DOLLAR);
        String featureCd = null;
        String adpt = null;
        String ablsh = null;
        if (!existingFtreProperties.isEmpty()) {
            featureCd = existingFtreProperties.get(0);
            adpt = existingFtreProperties.get(1);
            ablsh = existingFtreProperties.get(2);
        }
        Boolean matchedWeeklyFlg = tmpmatchedWeeklyFlg, matchedMonthlyFlg = tmpmatchedMonthlyFlg;
        Object[] newFeature = null;
        
        if (!matchedWeeklyFlg) {
            
            newFeature = insertFeaturetoArray(featureCd, eiSpecMst, WEEKLY);
            newFeature[FEATURE_FEAT_ABLSH_DATE] = jobExecution
                    .getExecutionContext().get(PRMTR_FTRE_ABLSH_DATE);
            insertFeature(newFeature);
            thirtyErrorFlg.put(eiSpecMst, newFeature);
            
            newFeature[FEATURE_FEAT_ADPT_DATE] = adpt;
            newFeature[FEATURE_FEAT_ABLSH_DATE] = ablsh;
            featureResultList.add(newFeature);
            
        }
        
        if (!matchedMonthlyFlg) {
            newFeature = insertFeaturetoArray(featureCd, eiSpecMst, MONTHLY);
            newFeature[FEATURE_FEAT_ABLSH_DATE] = jobExecution
                    .getExecutionContext().get(PRMTR_FTRE_ABLSH_DATE);
            insertFeature(newFeature);
            newFeature[FEATURE_FEAT_ADPT_DATE] = adpt;
            newFeature[FEATURE_FEAT_ABLSH_DATE] = ablsh;
            featureResultList.add(newFeature);
            
            twentyErrorFlg.put(eiSpecMst, newFeature);
        }
        LOG.info(DOLLAR+OUTSIDE_METHOD+DOLLAR);
        
    }
    
    /**
     * Batch : 4,5 Process ID : P00004.5 ADopt and Abolish date Calculation for
     * the Feature Production type Monthly
     *
     * @param featureList the feature list
     * @param eiSpecMst the ei spec mst
     * @param basePerioddate the base perioddate
     * @param feaureTmpLst the feaure tmp lst
     * @param featureMstUpdate the feature mst update
     * @param ocfSpecCd the ocf spec cd
     * @param featureFinalList the feature final list
     * @throws ParseException the parse exception
     */
    public void ftreAdptAblshDateMonthly(List<Object> featureList,
            B00005Dao eiSpecMst, Date basePerioddate,
            List<Object[]> feaureTmpLst, List<Object[]> featureMstUpdate,
            Object[] ocfSpecCd, List<Object> featureFinalList)
            throws ParseException {
    	LOG.info(DOLLAR+INSIDE_METHOD+DOLLAR);
        Date adpt = null, ablsh = null;
        String frmeCd = ocfSpecCd[OCF_FRME_CD].toString().trim();
        
        for (Object featureLstIterator : featureList) {
            Object[] featureResultList = (Object[]) featureLstIterator;
            
            if (ocfSpecCd == null) {
                adpt = convertStringToDate(eiSpecMst.getOseiAdoptDate());
                ablsh = convertStringToDate(this.stepExecution
                        .getJobExecution().getExecutionContext()
                        .get(PRMTR_FTRE_ABLSH_DATE).toString());
                
            } else if (BATCH000004.equals(decideBatch)) {
                adpt = convertStringToDate((String) ocfSpecCd[OCF_ADPT_DATE]);
                ablsh = convertStringToDate((String) ocfSpecCd[OCF_ABLSH_DATE]);
                
            }
            if (BATCH000005.equals(decideBatch)) {
                adpt = convertStringToDate((String) ocfSpecCd[OCF_ADPT_DATE]);
                ablsh = convertStringToDate((String) ocfSpecCd[OCF_ABLSH_DATE]);
                featureResultList = appendValue(featureResultList,
                        eiSpecMst.getUkOseiId());
                
            }
            
            compareAdptAblshDate(featureResultList, featureMstUpdate, frmeCd,
                    eiSpecMst, adpt, ablsh, basePerioddate);
            addToFeatureLSt(featureResultList, featureFinalList, feaureTmpLst);
            
        }
        LOG.info(DOLLAR+OUTSIDE_METHOD+DOLLAR);
        
    }
    
    /**
     * Batch :4,5 Process ID: P0004.5 Adopt and Abolish date should be checked
     * whether there exists NULL in the Date
     *
     * @param featureResultList the feature result list
     * @param featureMstUpdate the feature mst update
     * @param frmeCd the frme cd
     * @param eiSpecMst the ei spec mst
     * @param adpt the adpt
     * @param ablsh the ablsh
     * @param basePerioddate the base perioddate
     */
    private void compareAdptAblshDate(Object[] featureResultList,
            List<Object[]> featureMstUpdate, String frmeCd,
            B00005Dao eiSpecMst, Date adpt, Date ablsh, Date basePerioddate) {
    	LOG.info(DOLLAR+INSIDE_METHOD+DOLLAR);
        if (adpt == null && ablsh == null) {
            appendFeatureAdptAblsh(featureResultList, eiSpecMst, basePerioddate);
            
        }
        
        if (adpt == null && ablsh != null) {
            appendFeatureAdpt(featureResultList, featureMstUpdate, frmeCd,
                    eiSpecMst, ablsh, basePerioddate);
            
        }
        
        if (adpt != null && ablsh == null) {
            appendFeatureAblsh(featureResultList, featureMstUpdate, frmeCd,
                    eiSpecMst, adpt, basePerioddate);
        }
        
        if (adpt != null && ablsh != null) {
            checkFeatureDate(featureResultList, featureMstUpdate, frmeCd,
                    eiSpecMst, adpt, ablsh, basePerioddate);
            
        }
        LOG.info(DOLLAR+OUTSIDE_METHOD+DOLLAR);
        
    }
    
    /**
     * Batch : 4,5 Consolidated Feature List that will will be inserted into
     * Orderable Feature MST Tables.
     *
     * @param featureResultList the feature result list
     * @param featureList the feature list
     * @param feaureTmpLst the feaure tmp lst
     */
    private void addToFeatureLSt(Object[] featureResultList,
            List<Object> featureList, List<Object[]> feaureTmpLst) {
    	LOG.info(DOLLAR+INSIDE_METHOD+DOLLAR);
        if (BATCH000005.equals(decideBatch)) {
            featureList.add(featureResultList);
        } else {
            feaureTmpLst.add(featureResultList);
        }
        LOG.info(DOLLAR+OUTSIDE_METHOD+DOLLAR);
        
    }
    
    /**
     * Batch :4,5 Process ID: P0004.5 Adopt and Abolish date both Not Null
     * Comparison
     *
     * @param featureResultList the feature result list
     * @param featureMstUpdate the feature mst update
     * @param frmeCd the frme cd
     * @param eiSpecMst the ei spec mst
     * @param adpt the adpt
     * @param ablsh the ablsh
     * @param basePerioddate the base perioddate
     */
    private void checkFeatureDate(Object[] featureResultList,
            List<Object[]> featureMstUpdate, String frmeCd,
            B00005Dao eiSpecMst, Date adpt, Date ablsh, Date basePerioddate) {
    	LOG.info(DOLLAR+INSIDE_METHOD+DOLLAR);
        Date eiAdptDate, eiAblshDate;
        
        try {
            Byte errFlg = 0;
            eiAdptDate = convertStringToDate(eiSpecMst.getOseiAdoptDate());
            
            eiAblshDate = convertStringToDate(eiSpecMst.getAbolishDate());
            
            if (OCF_FRAME_CODE_ZERO.equals(frmeCd)) {
                Object[] ftreMstUpdateLSt = new Object[FEATURE_SIZE + 1];
                assignToNewAry(ftreMstUpdateLSt, featureResultList);
                
                if (eiAdptDate.compareTo(adpt) < 0) {
                    ftreMstUpdateLSt[FEATURE_FEAT_ADPT_DATE] = CommonUtil
                            .dateToString(eiAdptDate);
                    errFlg = 1;
                    b5ReportGenration.generateReportWarningDate(eiSpecMst,
                            featureResultList[FEATURE_FEAT_ADPT_DATE]
                                    .toString(),
                            featureResultList[FEATURE_FEAT_ABLSH_DATE]
                                    .toString(), WARNING_ADOPT);
                    
                }
                
                if (eiAblshDate.compareTo(ablsh) > 0) {
                    ftreMstUpdateLSt[FEATURE_FEAT_ABLSH_DATE] = CommonUtil
                            .dateToString(eiAblshDate);
                    b5ReportGenration.generateReportWarningDate(eiSpecMst,
                            featureResultList[FEATURE_FEAT_ADPT_DATE]
                                    .toString(),
                            featureResultList[FEATURE_FEAT_ABLSH_DATE]
                                    .toString(), WARNING_ABOLISH);
                    
                    errFlg = 1;
                    
                }
                featureResultList[FEATURE_FEAT_ADPT_DATE] = CommonUtil
                        .dateToString(eiAdptDate);
                featureResultList[FEATURE_FEAT_ABLSH_DATE] = CommonUtil
                        .dateToString(eiAblshDate);
                if (errFlg == 1) {
                    
                    featureMstUpdate.add(ftreMstUpdateLSt);
                }
                
            }
            
            else {
                compareEiFeatureDate(featureResultList, eiAdptDate,
                        eiAblshDate, basePerioddate, adpt, ablsh);
                
            }
        } catch (ParseException e) {
            
            LOG.info(EXCEPTION, e);
        }
        LOG.info(DOLLAR+OUTSIDE_METHOD+DOLLAR);
    }
    
    /**
     * Assign to new ary.
     *
     * @param ftreMstUpdateLSt the ftre mst update l st
     * @param featureResultList the feature result list
     */
    private void assignToNewAry(Object[] ftreMstUpdateLSt,
            Object[] featureResultList) {
    	LOG.info(DOLLAR+INSIDE_METHOD+DOLLAR);
        int temp = 0;
        
        while (temp < featureResultList.length) {
            ftreMstUpdateLSt[temp] = featureResultList[temp];
            temp++;
        }
        LOG.info(DOLLAR+OUTSIDE_METHOD+DOLLAR);
        
    }
    
    /**
     * *
     * Batch :4,5 Process ID: P0004.5 Adopt and Abolish date both Not Null
     * Comparison for NON 00 Frame Codes
     *
     * @param featureResultList the feature result list
     * @param eiAdptDate the ei adpt date
     * @param eiAblshDate the ei ablsh date
     * @param basePerioddate the base perioddate
     * @param adpt the adpt
     * @param ablsh the ablsh
     */
    private void compareEiFeatureDate(Object[] featureResultList,
            Date eiAdptDate, Date eiAblshDate, Date basePerioddate, Date adpt,
            Date ablsh) {
    	LOG.info(DOLLAR+INSIDE_METHOD+DOLLAR);
        try {
            if (eiAdptDate.compareTo(basePerioddate) > 0) {
                
                if (eiAdptDate.compareTo(adpt) > 0) {
                    featureResultList[FEATURE_FEAT_ADPT_DATE] = CommonUtil
                            .dateToString(eiAdptDate);
                } else {
                    featureResultList[FEATURE_FEAT_ADPT_DATE] = CommonUtil
                            .dateToString(adpt);
                }
            } else if (basePerioddate.compareTo(adpt) > 0) {
                featureResultList[FEATURE_FEAT_ADPT_DATE] = CommonUtil
                        .dateToString(basePerioddate);
            } else {
                featureResultList[FEATURE_FEAT_ADPT_DATE] = CommonUtil
                        .dateToString(adpt);
            }
            
            if (eiAblshDate.compareTo(ablsh) > 0) {
                featureResultList[FEATURE_FEAT_ABLSH_DATE] = CommonUtil
                        .dateToString(ablsh);
            } else {
                featureResultList[FEATURE_FEAT_ABLSH_DATE] = CommonUtil
                        .dateToString(eiAblshDate);
            }
        } catch (Exception e) {
            LOG.info(EXCEPTION, e);
        }
        LOG.info(DOLLAR+OUTSIDE_METHOD+DOLLAR);
        
    }
    
    /**
     * *
     * Batch :4,5 Process ID: P0004.5 Adopt and Abolish date both Null
     * Comparison
     *
     * @param featureResultList the feature result list
     * @param eiSpecMst the ei spec mst
     * @param basePerioddate the base perioddate
     */
    private void appendFeatureAdptAblsh(Object[] featureResultList,
            B00005Dao eiSpecMst, Date basePerioddate) {
    	LOG.info(DOLLAR+INSIDE_METHOD+DOLLAR);
        Date eiAdptDate, eiAblshDate;
        
        try {
            eiAdptDate = convertStringToDate(eiSpecMst.getOseiAdoptDate());
            
            eiAblshDate = convertStringToDate(eiSpecMst.getAbolishDate());
            
            if (eiAdptDate.compareTo(basePerioddate) > 0) {
                featureResultList[FEATURE_FEAT_ADPT_DATE] = CommonUtil
                        .dateToString(eiAdptDate);
                featureResultList[FEATURE_FEAT_ABLSH_DATE] = CommonUtil
                        .dateToString(eiAblshDate);
            }
            
            else {
                
                featureResultList[FEATURE_FEAT_ADPT_DATE] = CommonUtil
                        .dateToString(basePerioddate);
                featureResultList[FEATURE_FEAT_ABLSH_DATE] = CommonUtil
                        .dateToString(eiAblshDate);
                
            }
            
        } catch (ParseException e) {
            
            LOG.info(EXCEPTION, e);
        }
        
        LOG.info(DOLLAR+OUTSIDE_METHOD+DOLLAR);
        
    }
    
    /**
     * *
     * Batch :4,5 Process ID: P0004.5 Adopt Date Not Null Comparison
     *
     * @param featureResultList the feature result list
     * @param featureMstUpdate the feature mst update
     * @param frmeCd the frme cd
     * @param eiSpecMst the ei spec mst
     * @param ablsh the ablsh
     * @param basePerioddate the base perioddate
     */
    private void appendFeatureAdpt(Object[] featureResultList,
            List<Object[]> featureMstUpdate, String frmeCd,
            B00005Dao eiSpecMst, Date ablsh, Date basePerioddate) {
    	LOG.info(DOLLAR+INSIDE_METHOD+DOLLAR);
        Date eiAdptDate, eiAblshDate;
        Byte errFlg = 0;
        try {
            eiAdptDate = convertStringToDate(eiSpecMst.getOseiAdoptDate());
            
            eiAblshDate = convertStringToDate(eiSpecMst.getAbolishDate());
            
            if (OCF_FRAME_CODE_ZERO.equals(frmeCd)) {
                featureResultList[FEATURE_FEAT_ADPT_DATE] = dateToString(eiAdptDate);
                featureResultList[FEATURE_FEAT_ABLSH_DATE] = dateToString(eiAblshDate);
                
                if (eiAblshDate.compareTo(ablsh) > 0) {
                    b5ReportGenration.generateReportWarningDate(eiSpecMst,
                            featureResultList[FEATURE_FEAT_ADPT_DATE]
                                    .toString(),
                            featureResultList[FEATURE_FEAT_ABLSH_DATE]
                                    .toString(), WARNING_ABOLISH);
                    
                }
                if (errFlg == 1) {
                    
                    featureMstUpdate.add(featureResultList);
                }
            }
            
            else {
                if (eiAblshDate.compareTo(ablsh) > 0) {
                    featureResultList[FEATURE_FEAT_ABLSH_DATE] = dateToString(ablsh);
                } else {
                    featureResultList[FEATURE_FEAT_ABLSH_DATE] = dateToString(eiAblshDate);
                }
                
            }
            
            if (eiAdptDate.compareTo(basePerioddate) > 0) {
                featureResultList[FEATURE_FEAT_ADPT_DATE] = dateToString(eiAdptDate);
                
            }
            
            else {
                featureResultList[FEATURE_FEAT_ADPT_DATE] = dateToString(basePerioddate);
                
            }
        }
        
        catch (ParseException e) {
            
            LOG.info(EXCEPTION, e);
        }
        LOG.info(DOLLAR+OUTSIDE_METHOD+DOLLAR);
    }
    
    /**
     * *
     * Batch :4,5 Process ID: P0004.5 Abolish date both Not Null Comparison
     *
     * @param featureResultList the feature result list
     * @param featureMstUpdate the feature mst update
     * @param frmeCd the frme cd
     * @param eiSpecMst the ei spec mst
     * @param adpt the adpt
     * @param basePerioddate the base perioddate
     */
    private void appendFeatureAblsh(Object[] featureResultList,
            List<Object[]> featureMstUpdate, String frmeCd,
            B00005Dao eiSpecMst, Date adpt, Date basePerioddate) {
    	LOG.info(DOLLAR+INSIDE_METHOD+DOLLAR);
        Date eiAdptDate, eiAblshDate;
        Byte errFlg = 0;
        try {
            eiAdptDate = convertStringToDate(eiSpecMst.getOseiAdoptDate());
            
            eiAblshDate = convertStringToDate(eiSpecMst.getAbolishDate());
            if (OCF_FRAME_CODE_ZERO.equals(frmeCd)) {
                featureResultList[FEATURE_FEAT_ADPT_DATE] = dateToString(eiAdptDate);
                featureResultList[FEATURE_FEAT_ABLSH_DATE] = dateToString(eiAblshDate);
                
                if (eiAdptDate.compareTo(adpt) < 0) {
                    b5ReportGenration.generateReportWarningDate(eiSpecMst,
                            featureResultList[FEATURE_FEAT_ADPT_DATE]
                                    .toString(),
                            featureResultList[FEATURE_FEAT_ABLSH_DATE]
                                    .toString(), WARNING_ADOPT);
                    
                }
                
                if (errFlg == 1) {
                    
                    featureMstUpdate.add(featureResultList);
                }
                
            } else {
                
                compareEiFtreAblshDate(featureResultList, eiAdptDate, adpt,
                        basePerioddate);
                
            }
            featureResultList[FEATURE_FEAT_ABLSH_DATE] = dateToString(eiAblshDate);
            
        } catch (ParseException e) {
            
            LOG.info(EXCEPTION, e);
        }
        LOG.info(DOLLAR+OUTSIDE_METHOD+DOLLAR);
        
    }
    
    /**
     * Batch :4,5 Process ID: P0004.5 Abolish date both Not Null Comparison For
     * NON 00 Frame Code
     *
     * @param featureResultList the feature result list
     * @param eiAdptDate the ei adpt date
     * @param adpt the adpt
     * @param basePerioddate the base perioddate
     */
    private void compareEiFtreAblshDate(Object[] featureResultList,
            Date eiAdptDate, Date adpt, Date basePerioddate) {
    	LOG.info(DOLLAR+INSIDE_METHOD+DOLLAR);
        try {
            if (eiAdptDate.compareTo(basePerioddate) > 0) {
                if (eiAdptDate.compareTo(adpt) > 0) {
                    featureResultList[FEATURE_FEAT_ADPT_DATE] = dateToString(eiAdptDate);
                } else {
                    featureResultList[FEATURE_FEAT_ADPT_DATE] = dateToString(adpt);
                }
            } else if (basePerioddate.compareTo(adpt) > 0) {
                featureResultList[11] = dateToString(basePerioddate);
            } else {
                featureResultList[FEATURE_FEAT_ADPT_DATE] = dateToString(adpt);
            }
        } catch (Exception e) {
            LOG.info(EXCEPTION, e);
        }
        LOG.info(DOLLAR+OUTSIDE_METHOD+DOLLAR);
        
    }
    
    
    /**
     * Batch :4 ,5 Process ID : P00004.5 ADopt and Abolish date Calculation for
     * the Feature Production type Weekly
     *
     * @param featureList the feature list
     * @param eiSpecMst the ei spec mst
     * @param basePerioddate the base perioddate
     * @param feaureTmpLst the feaure tmp lst
     * @param ocfSpecCd the ocf spec cd
     * @param featurefinalList the featurefinal list
     * @throws ParseException the parse exception
     */
    public void ftreAdptAblshDateWeekly(List<Object> featureList,
            B00005Dao eiSpecMst, Date basePerioddate,
            List<Object[]> feaureTmpLst, Object[] ocfSpecCd,
            List<Object> featurefinalList) throws ParseException {
    	LOG.info(DOLLAR+INSIDE_METHOD+DOLLAR);
        Date adpt = null;
        for (Object featureLstIterator : featureList) {
            Object[] featureResultList = (Object[]) featureLstIterator;
            if (ocfSpecCd == null) {
                adpt = convertStringToDate(featureResultList[FEATURE_FEAT_ADPT_DATE]
                                .toString());
                
            } else if (BATCH000004.equals(decideBatch)) {
                adpt = convertStringToDate((String) ocfSpecCd[OCF_ADPT_DATE]);
                
            }
            if (BATCH000005.equals(decideBatch)) {
                adpt = convertStringToDate((String) ocfSpecCd[OCF_ADPT_DATE]);
                featureResultList = appendValue(featureResultList,
                        eiSpecMst.getUkOseiId());
            }
            compareAdptDateWeekly(adpt, eiSpecMst, featureResultList,
                    basePerioddate, feaureTmpLst, featurefinalList);
        }
        LOG.info(DOLLAR+OUTSIDE_METHOD+DOLLAR);
    }
    
    /**
     * Compare adpt date weekly.
     *
     * @param adpt the adpt
     * @param eiSpecMst the ei spec mst
     * @param featureResultList the feature result list
     * @param basePerioddate the base perioddate
     * @param feaureTmpLst the feaure tmp lst
     * @param featurefinalList the featurefinal list
     * @throws ParseException the parse exception
     */
    private void compareAdptDateWeekly(Date adpt, B00005Dao eiSpecMst,
            Object[] featureResultList, Date basePerioddate,
            List<Object[]> feaureTmpLst, List<Object> featurefinalList)
            throws ParseException {
    	LOG.info(DOLLAR+INSIDE_METHOD+DOLLAR);
        Date eiAdptDate = convertStringToDate(eiSpecMst.getOseiAdoptDate());
        
        if (adpt.compareTo(eiAdptDate) <= 0) {
            
            featureResultList[FEATURE_FEAT_ADPT_DATE] = dateToString(basePerioddate);
            featureResultList[FEATURE_FEAT_ABLSH_DATE] = dateToString(addMonthToDate(basePerioddate, 1));
            
            if (BATCH000005.equals(decideBatch)) {
                featurefinalList.add(featureResultList);
            } else {
                feaureTmpLst.add(featureResultList);
            }
        }
        LOG.info(DOLLAR+OUTSIDE_METHOD+DOLLAR);
        
    }
    
    /**
     * This method is used to update the reexcute status PROCESS ID P0010.1
     * P0010.2
     */
    
    @AfterWrite
    public void updateReexecuteStatus() {
    	LOG.info(DOLLAR+INSIDE_METHOD+DOLLAR);
        insertBatchUpdatedTime();
        LOG.info(DOLLAR+OUTSIDE_METHOD+DOLLAR);
        
    }
    
    /**
     * This method is used to insert the batch details inside the Reexecute
     * Status table PROCESS ID P0010.2 P0010.2
     */
    
    @SuppressWarnings("static-access")
    public void insertBatchUpdatedTime() {
    	LOG.info(DOLLAR+INSIDE_METHOD+DOLLAR);
        String insertStatus = queryConstants.INSERT_BATCH_UPDATED_TIME
                .toString();
        Query insertBatchUpdtTime = entityManager
                .createNativeQuery(insertStatus);
        insertBatchUpdtTime.setParameter(PRMTR_PORCD, por);
        if (BATCH000004.equals(decideBatch)) {
            insertBatchUpdtTime.setParameter(BATCH_ID, BATCH_4_ID);
        } else {
            insertBatchUpdtTime.setParameter(BATCH_ID, BATCH_5_ID);
            
        }
        insertBatchUpdtTime.setParameter(MASTER_TABLE, MASTER_TABLE_NAME);
        
        insertBatchUpdtTime.executeUpdate();
        LOG.info(DOLLAR+OUTSIDE_METHOD+DOLLAR);
    }
    
    /**
     * After step.
     *
     * @throws PdApplicationNonFatalException the pd application non fatal exception
     */
    @AfterStep
    public void afterStep() throws PdApplicationNonFatalException {
    	LOG.info(DOLLAR+INSIDE_METHOD+DOLLAR);
    	String msgBatchId = null;
    	
    	if(flgErr==0) {
    		LOG.info(msgBatchId+B4_EI_MES+ por +B4_BATCH_STOP);
    		CommonUtil.stopBatch();
    	}
        if (this.decideBatch.equals(BATCH000004)) {
            msgBatchId = BATCH4_ID;
        } else {
            msgBatchId = BATCH5_ID;
        }
        if (b5OcffLG == 1 && b5OcfCount == 0 && decideBatch.equals(BATCH000005)) {
            LOG.info(msgBatchId + OCF_PARAMTER
                    + por + OCF_PARAMTER_TABLE);
            LOG.info("Batch stopped with FAILED status");
            CommonUtil.stopBatch();
        }
        LOG.info(DOLLAR+OUTSIDE_METHOD+DOLLAR);
    }
    
}
