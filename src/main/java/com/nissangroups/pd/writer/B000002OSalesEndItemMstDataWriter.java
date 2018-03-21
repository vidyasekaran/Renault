/*
 * System Name     :Post Dragon 
 * Sub system Name :Batch / Interface
 * Function ID     :PST-DRG-B000002OSalesEndItemMstDataWriter
 * Module          :@Create Spec Masters
 * Process Outline :@populate OSEI details
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
import com.nissangroups.pd.model.MstOsei;
import com.nissangroups.pd.model.MstOseiPK;

import static com.nissangroups.pd.util.PDConstants.DOLLAR;
import static com.nissangroups.pd.util.PDConstants.INSIDE_METHOD;
import static com.nissangroups.pd.util.PDConstants.OUTSIDE_METHOD;
import static com.nissangroups.pd.util.PDConstants.PERSISTENCE_NAME;
import static com.nissangroups.pd.util.PDConstants.WRITER_START_MSG;
import static com.nissangroups.pd.util.PDConstants.WRITER_STOP_MSG;

/**
 * Writer Class to write the processed data into OrderableSalesEndItemMst Table
 * Process - P0004.
 * @version 1.0
 */

public class B000002OSalesEndItemMstDataWriter implements
		ItemWriter<List<Object>> {
	
    /** Constant LOG. */
    private static final Log LOG = LogFactory.getLog(B000002OSalesEndItemMstDataWriter.class);
    
	/** Variable emgr. */
	@PersistenceContext(name = PERSISTENCE_NAME)
	private EntityManager eMgr;

	/**
	 * Method to write the Processed List in MST_OSEI table.
	 *
	 * @param eicolorCdProcessedList the eicolor cd processed list
	 * @throws PdApplicationException the pd application exception
	 */
	@Override
	public void write(List<? extends List<Object>> eicolorCdProcessedList)
			throws PdApplicationException {
		LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR);
		LOG.info(WRITER_START_MSG);
		MstOsei mstOsei = new MstOsei();
			/* Iterating Processed List */
			for (List<Object> prcssdObjList : eicolorCdProcessedList) {
				for (Object prcssdList : prcssdObjList) {
					Object[] prcssdListArray = (Object[]) prcssdList;
					MstOseiPK mstOseiPk = new MstOseiPK();
					mstOseiPk.setOeiBuyerId((String) prcssdListArray[0]);
					mstOseiPk.setExtClrCd((String) prcssdListArray[1]);
					mstOseiPk.setIntClrCd((String) prcssdListArray[2]);
					mstOsei.setId(mstOseiPk);
					mstOsei.setOseiId((String) prcssdListArray[4]);
					mstOsei.setPorCd((String) prcssdListArray[3]);
					mstOsei.setCrtdBy("B000002");
					mstOsei.setUpdtdBy("B000002");
					eMgr.merge(mstOsei);
				}
			}
			LOG.info(WRITER_STOP_MSG);
			eMgr.close();
			LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);
	}
}
