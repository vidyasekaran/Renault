/*
 * System Name     :Post Dragon 
 * Sub system Name :Batch / Interface
 * Function ID     :PST-DRG-B000002OEndItemMstsDataWriter
 * Module          :@Create Spec Masters
 * Process Outline :@populate OEI detais
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
import com.nissangroups.pd.model.MstOeiSpec;
import com.nissangroups.pd.model.MstOeiSpecPK;
import static com.nissangroups.pd.util.PDConstants.DOLLAR;
import static com.nissangroups.pd.util.PDConstants.INSIDE_METHOD;
import static com.nissangroups.pd.util.PDConstants.OUTSIDE_METHOD;
import static com.nissangroups.pd.util.PDConstants.PERSISTENCE_NAME;
import static com.nissangroups.pd.util.PDConstants.WRITER_START_MSG;
import static com.nissangroups.pd.util.PDConstants.WRITER_STOP_MSG;;

/**
 * Writer Class to write the processed data into OrderableEndItemSpecMst Table
 * Process Id- P0002.
 * @version 1.0
 */
public class B000002OEndItemMstsDataWriter implements ItemWriter<List<Object>> {
	
    /** Constant LOG. */
    private static final Log LOG = LogFactory.getLog(B000002OEndItemMstsDataWriter.class);
    
	/** Object emgr. */
	@PersistenceContext(name = PERSISTENCE_NAME)
	private EntityManager eMgr;
	
	/**
	 * Method to write the data in OrderableEndItemSpecMst Table.
	 *
	 * @param eiSpecProcessedList the ei spec processed list
	 */
	@Override
	public void write(List<? extends List<Object>> eiSpecProcessedList){
		LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR);
		LOG.info(WRITER_START_MSG);
		MstOeiSpec mstOeiSpec = new MstOeiSpec();
		/* Iterating the processed List  */
		for (List<Object> prcssdObjList : eiSpecProcessedList) {
			for (Object prcssdList : prcssdObjList) {
				mstOeiSpec.setId(new MstOeiSpecPK());
				Object[] prcssdListArray = (Object[]) prcssdList;
				mstOeiSpec.getId().setPorCd((String) prcssdListArray[1]);
				mstOeiSpec.getId().setProdFmyCd((String) prcssdListArray[2]);
				mstOeiSpec.getId().setProdStageCd((String) prcssdListArray[3]);
				mstOeiSpec.getId().setAppldMdlCd((String) prcssdListArray[0]);
				mstOeiSpec.getId().setPckCd((String) prcssdListArray[5]);
				mstOeiSpec.getId().setSpecDestnCd((String) prcssdListArray[6]);
				mstOeiSpec.getId().setAdtnlSpecCd((String) prcssdListArray[7]);
				mstOeiSpec.setCarSrs(null);
				mstOeiSpec.setOeiSpecId((String) prcssdListArray[9]);
				mstOeiSpec.setCrtdBy("B000002");
				mstOeiSpec.setUpdtdBy("B000002");
				eMgr.merge(mstOeiSpec);
				}
			}
		LOG.info(WRITER_STOP_MSG);
		LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);
		}

}
