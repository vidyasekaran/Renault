/*
 * System Name     :Post Dragon 
 * Sub system Name :Batch / Interface
 * Function ID     :PST-DRG-CustomComparator
 * Module          :@Module
 * Process Outline :@Process_Outline
 *
 * <Revision History>
 * Date       Name(Company Name)             Description 
 * ---------- ------------------------------ ---------------------
 * @Date  	  @author(RNTBCI)               New Creation
 *
 */
package com.nissangroups.pd.util;

import static com.nissangroups.pd.util.PDConstants.FEATURE_FEAT_ADPT_DATE;

import java.text.ParseException;
import java.util.Comparator;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 *  CustomComparator class is used to sort objects using feature adopt date.
 */
public class CustomComparator implements Comparator<Object[]>{
    
    /** Constant LOG. */
    private static final Log LOG = LogFactory.getLog(CustomComparator.class);
 
    /** Variable util. */
    CommonUtil util = new CommonUtil();
    
    /* (non-Javadoc)
     * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
     */
    public int compare(Object[] feature, Object[] nextFeature) {
       int comparedValue = 0;
    try {
        comparedValue = (util.convertStringToDate((String)feature[FEATURE_FEAT_ADPT_DATE])).compareTo(util.convertStringToDate((String)nextFeature[FEATURE_FEAT_ADPT_DATE]));
    } catch (ParseException e) {
           LOG.error("Parsing exception" +e);
    }
    return comparedValue;
    }
}
