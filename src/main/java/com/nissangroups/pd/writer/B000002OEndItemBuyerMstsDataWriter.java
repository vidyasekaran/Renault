/*
 * System Name     :Post Dragon 
 * Sub system Name :Batch / Interface
 * Function ID     :PST-DRG-B000002OEndItemBuyerMstsDataWriter
 * Module          :@Create Spec Masters
 * Process Outline :@populate OEI Buyer details
 *
 * <Revision History>
 * Date       			Name(RNTBCI)                 Description 
 * ---------- ------------------------------ ---------------------
 * @08 July 2015  	  @author(z013576)               New Creation
 *
 */
package com.nissangroups.pd.writer;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.item.ItemWriter;

import com.nissangroups.pd.exception.PdApplicationException;
import com.nissangroups.pd.model.MstOeiBuyer;
import com.nissangroups.pd.model.MstOeiBuyerPK;

import static com.nissangroups.pd.util.PDConstants.DOLLAR;
import static com.nissangroups.pd.util.PDConstants.INSIDE_METHOD;
import static com.nissangroups.pd.util.PDConstants.OUTSIDE_METHOD;
import static com.nissangroups.pd.util.PDConstants.PERSISTENCE_NAME;
import static com.nissangroups.pd.util.PDConstants.WRITER_START_MSG;
import static com.nissangroups.pd.util.PDConstants.WRITER_STOP_MSG;

/**
 * Writer Class to write the processed data into OrderableEndItemSpecMst Table
 * Process Id - P0003.
 * @version 1.0
 */
public class B000002OEndItemBuyerMstsDataWriter implements
		ItemWriter<List<Object>> {
	
    /** Constant LOG. */
    private static final Log LOG = LogFactory.getLog(B000002OEndItemBuyerMstsDataWriter.class);
    
	/** Object emgr. */
	@PersistenceContext(name = PERSISTENCE_NAME)
	private EntityManager eMgr;

	/**
	 * Method to write the Buyer code details in OrderableEndItemBuyerMst table.
	 *
	 * @param eiSpecBuyerCdProcessedList the ei spec buyer cd processed list
	 * @throws PdApplicationException the pd application exception
	 */
	@Override
	public void write(List<? extends List<Object>> eiSpecBuyerCdProcessedList)
			throws PdApplicationException {
		LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR);
		LOG.info(WRITER_START_MSG);
		MstOeiBuyer mstOeiByr = new MstOeiBuyer();
			/* Iterating the fetched Lsit */
			for (List<Object> prcssdObjList : eiSpecBuyerCdProcessedList) {
				for (Object prcssdList : prcssdObjList) {
					Object[] prcssdListArray = (Object[]) prcssdList;
					MstOeiBuyerPK mstOeiByrPk = new MstOeiBuyerPK();
					mstOeiByrPk.setOeiSpecId((String) prcssdListArray[1]);
					mstOeiByrPk.setBuyerCd((String) prcssdListArray[0]);
					mstOeiByr.setId(mstOeiByrPk);
					mstOeiByr.setOeiBuyerId((String) prcssdListArray[3]);
					mstOeiByr.setPorCd((String) prcssdListArray[2]);
					mstOeiByr.setCrtdBy("B000002");
					mstOeiByr.setUpdtdBy("B000002");
					/* merging the values */
					eMgr.merge(mstOeiByr);
				}
			}
			
			eMgr.close();
			LOG.info(WRITER_STOP_MSG);
			LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);
	}
	
}
