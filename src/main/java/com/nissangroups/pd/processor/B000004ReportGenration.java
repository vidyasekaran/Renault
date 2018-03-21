/*
 * System Name     :Post Dragon 
 * Sub system Name :Batch / Interface
 * Function ID     :PST-DRG-B000004ReportGenration
 * Module          :@Module
 * Process Outline :@Process_Outline
 *
 * <Revision History>
 * Date       Name(Company Name)             Description 
 * ---------- ------------------------------ ---------------------
 * @Date  	  @author(RNTBCI)               New Creation
 *
 */
package com.nissangroups.pd.processor;

import static com.nissangroups.pd.util.CommonUtil.convertStringToDate;
import static com.nissangroups.pd.util.PDConstants.*;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.nissangroups.pd.dao.B000005ReportDao;
import com.nissangroups.pd.dao.B00005Dao;
import com.nissangroups.pd.util.CommonUtil;

/**
 * The Class B000004ReportGenration.
 */
public class B000004ReportGenration {
    
    /** Constant LOG. */
	private static final Log LOG = LogFactory.getLog
                                                   (B000004ReportGenration.class);
    
    /** Variable b000005 report dao. */
    private B000005ReportDao    b000005ReportDao;
    
    /** Variable common util. */
    CommonUtil                  commonUtil = new CommonUtil();
    
    /**
     * Sets the b000005 report dao.
     *
     * @param b000005ReportDao
     *            the new b000005 report dao
     */
    public void setB000005ReportDao(B000005ReportDao b000005ReportDao) {
        LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR);
        this.b000005ReportDao = b000005ReportDao;
        LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);
    }
    
    /**
     * Process ID: P0007 Extract the OCF for Lesser Priority Add Objects to the
     * Report.
     *
     * @param featureLst
     *            the feature lst
     * @param eiSpecMst
     *            the ei spec mst
     * @throws ParseException
     *             the parse exception
     */
    public void generateReportError(Object[] featureLst, B00005Dao eiSpecMst)
            throws ParseException {
        LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR);
        String errorMsg = ERROR;
        Boolean addToReportFlg = false;
        B000005ReportDao currentReport = new B000005ReportDao();
        Boolean tmpErrorFlg = false;
        setReportValues(currentReport, eiSpecMst);
        
        currentReport.setErrorType(ERROR_REPORT);
        String stringBasePeriod = featureLst[FEATURE_FEAT_ADPT_DATE].toString();
        String stringBasePeriodNextMonth = featureLst[FEATURE_FEAT_ABLSH_DATE]
                .toString();
        appendError(stringBasePeriod, stringBasePeriodNextMonth, currentReport,
                errorMsg, featureLst);
        
        currentReport.setFromOtbm(stringBasePeriod);
        currentReport.setToOtbm(stringBasePeriodNextMonth);
        
        List<B000005ReportDao> existingObjects = b000005ReportDao
                .getReportList();
        if (!existingObjects.isEmpty()
                && existingObjects.contains(currentReport)) {
            for (B000005ReportDao existingReport : existingObjects) {
                
                if (existingReport.equals(currentReport)) {
                    tmpErrorFlg = compareInsertErrorDate(existingReport,
                            currentReport, errorMsg, featureLst, ERROR);
                    
                }
            }
            if (!tmpErrorFlg) {
                
                b000005ReportDao.getReportList().add(currentReport);
            }
        } else {
            b000005ReportDao.getReportList().add(currentReport);
        }
        
        LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);
    }
    
    private boolean compareInsertErrorDate(B000005ReportDao existingReport,
            B000005ReportDao currentReport, String msg, Object[] featureLst,
            String type) throws ParseException {
        Date currentFromDate = convertStringToDate(currentReport.getFromOtbm());
        Date currentToDate = convertStringToDate(currentReport.getToOtbm());
        Date existingFromDate = convertStringToDate(existingReport
                .getFromOtbm());
        Date existingToDate = convertStringToDate(existingReport.getToOtbm());
        if (currentFromDate.compareTo(existingToDate) <= 0) {
            if (currentFromDate.compareTo(existingFromDate) > 0 && currentFromDate.compareTo(existingToDate) <= 0
                    && currentToDate.compareTo(existingToDate) >= 0) {
                existingReport.setToOtbm(currentReport.getToOtbm());
                if (type.equals(ERROR)) {
                    appendError(existingReport.getFromOtbm(),
                            existingReport.getToOtbm(), existingReport, msg,
                            featureLst);
                } else {
                    appendWarning(existingReport.getFromOtbm(),
                            existingReport.getToOtbm(), existingReport, msg,
                            featureLst);
                }
                
            } else if (currentFromDate.compareTo(existingFromDate) <= 0
                    && currentToDate.compareTo(existingToDate) >= 0) {
                existingReport.setFromOtbm(currentReport.getFromOtbm());
                existingReport.setToOtbm(currentReport.getToOtbm());
                if (type.equals(ERROR)) {
                    appendError(existingReport.getFromOtbm(),
                            existingReport.getToOtbm(), existingReport, msg,
                            featureLst);
                } else {
                    appendWarning(existingReport.getFromOtbm(),
                            existingReport.getToOtbm(), existingReport, msg,
                            featureLst);
                }
            } else if (currentFromDate.compareTo(existingFromDate) <= 0) {
                existingReport.setFromOtbm(currentReport.getFromOtbm());
                if (type.equals(ERROR)) {
                    appendError(existingReport.getFromOtbm(),
                            existingReport.getToOtbm(), existingReport, msg,
                            featureLst);
                } else {
                    appendWarning(existingReport.getFromOtbm(),
                            existingReport.getToOtbm(), existingReport, msg,
                            featureLst);
                }
            } else {
                return false;
            }
        } else {
            return false;
        }
        return true;
    }
    
    /**
     * Append error.
     *
     * @param stringBasePeriod
     *            the string base period
     * @param stringBasePeriodNextMonth
     *            the string base period next month
     * @param report
     *            the report
     * @param errorMsgTmp
     *            the error msg tmp
     * @param featureLst
     *            the feature lst
     */
    private void appendError(String stringBasePeriod,
            String stringBasePeriodNextMonth, B000005ReportDao report,
            String errorMsgTmp, Object[] featureLst) {
        LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR);
        String errorMsg = errorMsgTmp + featureLst[FEATURE_FEAT_CD].toString()
                + ERROR1 + stringBasePeriod.substring(0, 4) + DELIMITE_HYPHEN
                + stringBasePeriod.substring(4, 6) + TO
                + stringBasePeriodNextMonth.substring(0, 4) + DELIMITE_HYPHEN
                + stringBasePeriodNextMonth.substring(4, 6);
        report.setCmmnts(errorMsg);
        LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);
        
    }
    
    /**
     * Append warning.
     *
     * @param stringBasePeriod
     *            the string base period
     * @param stringBasePeriodNextMonth
     *            the string base period next month
     * @param report
     *            the report
     * @param warningMsgTmp
     *            the warning msg tmp
     * @param featureLst
     *            the type
     * @param eiSpecMst
     *            the ei spec mst
     */
    private void appendWarning(String stringBasePeriod,
            String stringBasePeriodNextMonth, B000005ReportDao report,
            String warningMsgTmp, Object[] featureLst) {
        LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR);
        // String warningMsg = warningMsgTmp;
        String warningMsg = warningMsgTmp
                + featureLst[FEATURE_FEAT_CD].toString() + WARNING_REPORT_B4
                + featureLst[FEATURE_FEAT_CD].toString()
                + WARNING_REPORT_B4_temp + stringBasePeriod.substring(0, 4)
                + DELIMITE_HYPHEN + stringBasePeriod.substring(4, 6) + TO
                + stringBasePeriodNextMonth.substring(0, 4) + DELIMITE_HYPHEN
                + stringBasePeriodNextMonth.substring(4, 6);
        
        report.setCmmnts(warningMsg);
        
        LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);
        
    }
    
    /**
     * Generate report warning.
     *
     * @param eiSpecMst
     *            the ei spec mst
     * @param type
     *            the type
     * @param featureLst
     * @throws ParseException
     *             the parse exception
     */
    public void generateReportWarning(B00005Dao eiSpecMst, String type,
            Object[] featureLst) throws ParseException {
        LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR);
        Boolean tmpErrorFlg = false;
        String errorMsg = null;
        if (type.equals(MONTHLY)) {
            errorMsg = WARNING_MONTHLY;
        }
        if (type.equals(WEEKLY)) {
            errorMsg = WARNING_WEEKLY;
        }
        B000005ReportDao currentReport = new B000005ReportDao();
        currentReport.setErrorType(WARNING1);
        setReportValues(currentReport, eiSpecMst);
        
        String stringBasePeriod = featureLst[FEATURE_FEAT_ADPT_DATE].toString();
        String stringBasePeriodNextMonth = featureLst[FEATURE_FEAT_ABLSH_DATE]
                .toString();
        appendWarning(stringBasePeriod, stringBasePeriodNextMonth,
                currentReport, errorMsg, featureLst);
        
        currentReport.setFromOtbm(stringBasePeriod);
        currentReport.setToOtbm(stringBasePeriodNextMonth);
        
        List<B000005ReportDao> existingObjects = b000005ReportDao
                .getReportList();
        if (!existingObjects.isEmpty()
                && existingObjects.contains(currentReport)) {
            for (B000005ReportDao existingReport : existingObjects) {
                
                if (existingReport.equals(currentReport)) {
                    tmpErrorFlg = compareInsertErrorDate(existingReport,
                            currentReport, errorMsg, featureLst, WARNING);
                    
                }
                
            }
            if (!tmpErrorFlg) {
                
                b000005ReportDao.getReportList().add(currentReport);
            }
            
        }
        
        else {
            b000005ReportDao.getReportList().add(currentReport);
        }
        LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);
    }
    
    /**
     * Generate report warning date.
     *
     * @param eiSpecMst
     *            the ei spec mst
     * @param ftreAdpt
     *            the ftre adpt
     * @param ftreAblsh
     *            the ftre ablsh
     * @param type
     *            the type
     * @param featureResultList
     * @throws ParseException
     *             the parse exception
     */
    public void generateReportWarningDate(B00005Dao eiSpecMst, String ftreAdpt,
            String ftreAblsh, String type) throws ParseException {
        LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR);
        String errorMsg = type;
        
        B000005ReportDao report = new B000005ReportDao();
        report.setErrorType(WARNING2);
        setReportValues(report, eiSpecMst);
        String stringBasePeriod = eiSpecMst.getOseiAdoptDate();
        String stringBasePeriodNextMonth = eiSpecMst.getAbolishDate();
        appendWarningDate(ftreAdpt, ftreAblsh, report, errorMsg, eiSpecMst);
        
        report.setFromOtbm(stringBasePeriod);
        report.setToOtbm(stringBasePeriodNextMonth);
        
        List<B000005ReportDao> existingObjects = b000005ReportDao
                .getReportList();
        if (!existingObjects.isEmpty() && existingObjects.contains(report)) {
            for (B000005ReportDao obj : existingObjects) {
                
                if (obj.equals(report)
                        && !obj.getFromOtbm().equals(
                                eiSpecMst.getOseiAdoptDate())
                        && !obj.getToOtbm().equals(eiSpecMst.getAbolishDate())) {
                    
                    b000005ReportDao.getReportList().add(report);
                    
                }
            }
            
        } else {
            
            b000005ReportDao.getReportList().add(report);
        }
        LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);
    }
    
    /**
     * Sets the report values.
     *
     * @param report
     *            the report
     * @param eiSpecMst
     *            the ei spec mst
     */
    private void setReportValues(B000005ReportDao report, B00005Dao eiSpecMst) {
        LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR);
        report.setPor(eiSpecMst.getPorCd());
        report.setCarSeries(eiSpecMst.getCarSeries());
        report.setBuyerCd(eiSpecMst.getBuyerCd());
        report.setEiMdlCd(eiSpecMst.getAppliedModelCd() + eiSpecMst.getPackCd());
        report.setEiClrCd(eiSpecMst.getExteriorColorCd()
                + eiSpecMst.getInteriorColorCd());
        report.setAdptDate(eiSpecMst.getOseiAdoptDate());
        report.setAblshDate(eiSpecMst.getAbolishDate());
        report.setSpecDestinationCd(eiSpecMst.getSpecDestinationCd());
        report.setAddSpecCd(eiSpecMst.getAdtnlSpecCd());
        report.setOcfRegionCd(eiSpecMst.getOcfRegionCd());
        report.setOcfBuyerGrpCd(eiSpecMst.getOcfBuyerGroupCd());
        LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);
        
    }
    
    /**
     * Append warning date.
     *
     * @param ftreAdpt
     *            the ftre adpt
     * @param ftreAblsh
     *            the ftre ablsh
     * @param report
     *            the report
     * @param warningMsgTmp
     *            the warning msg tmp
     * @param eiSpecMst
     *            the ei spec mst
     */
    private void appendWarningDate(String ftreAdpt, String ftreAblsh,
            B000005ReportDao report, String warningMsgTmp, B00005Dao eiSpecMst) {
        LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR);
        String warningMsg = warningMsgTmp;
        warningMsg += eiSpecMst.getOseiAdoptDate().substring(0, 4)
                + DELIMITE_HYPHEN
                + eiSpecMst.getOseiAdoptDate().substring(4, 6) + TO
                + eiSpecMst.getAbolishDate().substring(0, 4) + DELIMITE_HYPHEN
                + eiSpecMst.getAbolishDate().substring(4, 6)
                + WARNING_ADOPT_ABOLISH + ftreAdpt.substring(0, 4)
                + DELIMITE_HYPHEN + ftreAdpt.substring(4, 6) + TO
                + ftreAblsh.substring(0, 4) + DELIMITE_HYPHEN
                + ftreAblsh.substring(4, 6);
        report.setCmmnts(warningMsg);
        LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);
        
    }
    
}
