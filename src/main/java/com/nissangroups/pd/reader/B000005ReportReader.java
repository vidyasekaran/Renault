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
package com.nissangroups.pd.reader;

import static com.nissangroups.pd.util.PDConstants.DOLLAR;
import static com.nissangroups.pd.util.PDConstants.INSIDE_METHOD;
import static com.nissangroups.pd.util.PDConstants.OUTSIDE_METHOD;

import org.springframework.batch.item.ItemReader;
import org.springframework.beans.factory.annotation.Autowired;

import com.nissangroups.pd.dao.B000005ReportDao;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


/**
 * The Class B000005ReportReader.
 *
 * @author z010343
 */
public class B000005ReportReader implements ItemReader<B000005ReportDao> {
	private static final Log LOG = LogFactory.getLog
			(B000005ReportReader.class);
    /** Variable b000005 report dao. */
    @Autowired
    private B000005ReportDao b000005ReportDao;
    
    /** Variable current index. */
    private int currentIndex = 0;
    
    /* (non-Javadoc)
     * @see org.springframework.batch.item.ItemReader#read()
     */
    @Override
    public B000005ReportDao read() {
    	LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR);
        if(currentIndex < b000005ReportDao.getReportList().size()){
        	LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);
            return b000005ReportDao.getReportList().get(currentIndex++);
        }
        LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);
        return null;
    }
    
    

    

}