/*
 * System Name     :Post Dragon 
 * Sub system Name :Batch / Interface
 * Function ID     :PST-DRG-B000002OEndItemOptionSpecCdMstDataWriter
 * Module          :@Create Spec Masters
 * Process Outline :@populate OEI option Spec Code details
 *
 * <Revision History>
 * Date       			Name(RNTBCI)                 Description 
 * ---------- ------------------------------ ---------------------
 * @09 July 2015  	  @author(z013576)               New Creation
 *
 */
package com.nissangroups.pd.writer;

import static com.nissangroups.pd.util.B000002QueryConstants.Query_41_deleteOeioptionSpecDetails;
import static com.nissangroups.pd.util.PDConstants.BATCH_2_ID;
import static com.nissangroups.pd.util.PDConstants.DOLLAR;
import static com.nissangroups.pd.util.PDConstants.INSIDE_METHOD;
import static com.nissangroups.pd.util.PDConstants.OUTSIDE_METHOD;
import static com.nissangroups.pd.util.PDConstants.PERSISTENCE_NAME;
import static com.nissangroups.pd.util.PDConstants.PRMTR_PORCD;
import static com.nissangroups.pd.util.PDConstants.PRMTR_UK_OEI_BUYEER_ID;
import static com.nissangroups.pd.util.PDConstants.WRITER_START_MSG;
import static com.nissangroups.pd.util.PDConstants.WRITER_STOP_MSG;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.batch.item.ItemWriter;

import com.nissangroups.pd.exception.PdApplicationException;
import com.nissangroups.pd.model.MstOeiBuyerOptnSpecCd;
import com.nissangroups.pd.model.MstOeiBuyerOptnSpecCdPK;

/**
 * Writer Class to write the processed data into EndItemOptionSpecCodeMSt Table
 * Process - P0006.
 * @version 1.0
 */
public class B000002OEndItemOptionSpecCdMstDataWriter implements
		ItemWriter<List<Object>> {
	
    /** Constant LOG. */
    private static final Log LOG = LogFactory.getLog(B000002OEndItemOptionSpecCdMstDataWriter.class);
	
	/** Object emgr. */
	@PersistenceContext(name = PERSISTENCE_NAME)
	private EntityManager eMgr;
	
	/** Variable job exec. */
	private JobExecution jobExec;
	
	/** Variable timestamp. */
	private Timestamp timeStamp;
	
	/** Variable currentdate. */
	private Date currentDate;
	
	/**
	 * Before Stpe method to set the stepExecution to jobExecution Object.
	 *
	 * @param stepExecution the step execution
	 */
	@BeforeStep
	public void beforeStep(StepExecution stepExecution) {
		LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR);
		jobExec = stepExecution.getJobExecution();
		LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);
	}
	
	/**
	 * Method to write the data in EndItemOptionSpecCodeMSt Table.
	 *
	 * @param splittedList the splitted list
	 * @throws PdApplicationException the pd application exception
	 */
	@Override
	public void write(List<? extends List<Object>> splittedList)
			throws PdApplicationException {
		LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR);
		LOG.info(WRITER_START_MSG);
		/* declaring Variables */
		currentDate = new Date();
        timeStamp = new Timestamp(currentDate.getTime());
		StringBuilder oeiByrTobeDeleted = new StringBuilder();
		String oeiBrltdByrPrdDtlsDeleteQry = Query_41_deleteOeioptionSpecDetails.toString();
		List<String> oeiByrList = new ArrayList<String>();
		String porCd = jobExec.getJobParameters().getString(PRMTR_PORCD);
		MstOeiBuyerOptnSpecCd optnSpec = new MstOeiBuyerOptnSpecCd();
		/* Iterating the splitted Lsit */
		/*for (List<Object> prcssdObjList : splittedList) {
				for (Object prcssdList : prcssdObjList) {
					Object[] prcssdListArray = (Object[]) prcssdList;
					oeiByrList.add((String) prcssdListArray[1]);
				}
			}*/
			/* to remove Dublicates */
			/*Set<String> hashSet = new HashSet<String>(oeiByrList);
			for(String oeiB : hashSet)
			{
				if(oeiByrTobeDeleted.length() > 0 )
				{
					oeiByrTobeDeleted.append(",");
				}
				oeiByrTobeDeleted.append(oeiB);
			}
			String rsOeiBlist = oeiByrTobeDeleted.toString();
			eMgr.createNativeQuery(oeiBrltdByrPrdDtlsDeleteQry)
			.setParameter(TOBEDELETED, rsOeiBlist)
			.setParameter(PRMTR_PORCD, porCd)
			.executeUpdate();*/
		for (List<Object> prcssdObjListDel : splittedList) {
			for (Object prcssdListDel : prcssdObjListDel) {
				Object[] prcssdListArrayDel = (Object[]) prcssdListDel;
				eMgr.createNativeQuery(oeiBrltdByrPrdDtlsDeleteQry)
				.setParameter(PRMTR_UK_OEI_BUYEER_ID, (String) prcssdListArrayDel[1])
				.setParameter(PRMTR_PORCD, (String) prcssdListArrayDel[2])
				.executeUpdate();
				}
			}
		
			/* itearting the dublicate removed Lsit */
			for (List<Object> prcssdObjList : splittedList) {
				for (Object prcssdList : prcssdObjList) {
					Object[] prcssdListArray = (Object[]) prcssdList;
					MstOeiBuyerOptnSpecCdPK optnSpecPk = new MstOeiBuyerOptnSpecCdPK();
					optnSpecPk.setOeiBuyerId((String) prcssdListArray[1]);
					optnSpecPk.setOptnSpecCode((String) prcssdListArray[3]);
					optnSpec.setId(optnSpecPk);
					optnSpec.setPorCd((String) prcssdListArray[2]);
					optnSpec.setCrtdBy(BATCH_2_ID);
					optnSpec.setCrtdDt(timeStamp);
					optnSpec.setUpdtdBy(BATCH_2_ID);
					optnSpec.setUpdtdDt(timeStamp);
					eMgr.merge(optnSpec);
					}
			}
			eMgr.close();
			LOG.info(WRITER_STOP_MSG);
			LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);
	}

}
