/*
 * System Name     :Post Dragon 
 * Sub system Name :Batch / Interface
 * Function ID     :PST-DRG-B000002OSEIBuyerPrdctnMstWriter
 * Module          :@Create Spec Masters
 * Process Outline :@populate OSEI Min Adopt & Max Abolish date
 *
 * <Revision History>
 * Date       			Name(RNTBCI)                 Description 
 * ---------- ------------------------------ ---------------------
 * @10 July 2015  	  @author(z013576)               New Creation
 *
 */
package com.nissangroups.pd.writer;

import static com.nissangroups.pd.util.CommonUtil.addMonthToDate;
import static com.nissangroups.pd.util.CommonUtil.convertStringToDate;
import static com.nissangroups.pd.util.CommonUtil.dateToString;
import static com.nissangroups.pd.util.PDConstants.DOLLAR;
import static com.nissangroups.pd.util.PDConstants.EMPTY_STRING;
import static com.nissangroups.pd.util.PDConstants.INSIDE_METHOD;
import static com.nissangroups.pd.util.PDConstants.OUTSIDE_METHOD;
import static com.nissangroups.pd.util.PDConstants.PERSISTENCE_NAME;
import static com.nissangroups.pd.util.PDConstants.PRMTR_MAX_ABLSH_DT;
import static com.nissangroups.pd.util.PDConstants.PRMTR_PORCD;
import static com.nissangroups.pd.util.PDConstants.PRMTR_UK_OEI_BUYEER_ID;
import static com.nissangroups.pd.util.PDConstants.WRITER_START_MSG;

import java.text.ParseException;
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
import org.springframework.transaction.annotation.Transactional;

import com.nissangroups.pd.model.MstOeiBuyerPrd;
import com.nissangroups.pd.model.MstOeiBuyerPrdPK;
import com.nissangroups.pd.util.B000002QueryConstants;

/**
 * Writer Class to write the processed data into OEIBuyerMst Table
 * Process - P0008.
 * @version 1.0
 */
public class B000002OSEIBuyerPrdctnMstWriter implements ItemWriter<List<Object[]>>{
	
    /** Constant LOG. */
    private static final Log LOG = LogFactory.getLog(B000002OSEIBuyerPrdctnMstWriter.class);
	
	/** Object emgr. */
	@PersistenceContext(name = PERSISTENCE_NAME)
	private EntityManager eMgr;
	
	/** object jobexec. */
	private JobExecution jobExec;
	
	
	/** Variable oeibuyprd. */
	MstOeiBuyerPrd oeiBuyPrd = new MstOeiBuyerPrd();
	
	/** Variable oeibuyprdpk. */
	MstOeiBuyerPrdPK oeiBuyPrdPk = new MstOeiBuyerPrdPK();
	
	/** Variable oeibyrlist. */
	/* Declaring Variables */
	List<String> oeiByrList = new ArrayList<String>();
	
	/** Variable oeibyrtobedeleted. */
	StringBuilder oeiByrTobeDeleted = new StringBuilder();
	
	/** Variable tempoeibuyr id. */
	String tempOeiBuyrId = EMPTY_STRING;
	
	/** Variable tempablshdate. */
	String tempAblshDate = EMPTY_STRING;
	
	/** Variable oeibrltdbyrprddtlsdeleteqry. */
	String oeiBrltdByrPrdDtlsDeleteQry = B000002QueryConstants.Query_37_deleteOeiBuyrDetails.toString();
	
	/** Variable byrprdupdtqry. */
	String byrPrdUpdtQry = B000002QueryConstants.Query_38_updateOeiBuyrDetails.toString();
	
	/** Variable date. */
	Date date;
	
	/** Variable tempincreasedablshdate. */
	Date tempIncreasedAblshDate;
	
	
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
	
	/* (non-Javadoc)
	 * @see org.springframework.batch.item.ItemWriter#write(java.util.List)
	 */
	@Override
	@Transactional
	public void write(List<? extends List<Object[]>> eiBuyerCodeAdptablshList) throws Exception {
		LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR);
		LOG.info(WRITER_START_MSG);
		String porCd = jobExec.getJobParameters().getString(PRMTR_PORCD);

					/* Iterating teh processed List */
					for (List<Object[]> prcssdObjList : eiBuyerCodeAdptablshList) {
						for (Object[] prcssdList : prcssdObjList) {
							oeiByrList.add((String) prcssdList[0]);
						}
					}
			// This method is commente
					/* Iterating to remove the dublicates */
//					Set<String> hashSet = new HashSet<String>(oeiByrList);
//					for(String oeiB : hashSet)
//					{
//						if(oeiByrTobeDeleted.length() > 0 )
//						{
//							oeiByrTobeDeleted.append(",");
//						}
//						oeiByrTobeDeleted.append(oeiB);
//					}
//					String rsOeiBlist = oeiByrTobeDeleted.toString();
			
//					eMgr.createNativeQuery(oeiBrltdByrPrdDtlsDeleteQry)
//						.setParameter(PRMTR_OEIB_TOBEDELETEDLIST, rsOeiBlist)
//						.setParameter(PRMTR_PORCD, porCd)
//						.executeUpdate();
//					int listSize = 	eiBuyerCodeAdptablshList.get(0).size();
					oeiByrPrdstCreation(eiBuyerCodeAdptablshList);
					LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);
				}
	
	/**
	 * Method for MST_OEI_BUYER_PRD population.
	 *
	 * @param listSize the list size
	 * @param eiBuyerCodeAdptablshList the ei buyer code adptablsh list
	 * @throws ParseException the parse exception
	 */
	private void oeiByrPrdstCreation(List<? extends List<Object[]>> eiBuyerCodeAdptablshList) 
			throws ParseException {
		for (List<Object[]> prcssdObjList : eiBuyerCodeAdptablshList) {
			for (Object[] prcssdList : prcssdObjList) {
				
		eMgr.createNativeQuery(oeiBrltdByrPrdDtlsDeleteQry)
		.setParameter(PRMTR_UK_OEI_BUYEER_ID,(String) prcssdList[0])
		.setParameter(PRMTR_PORCD, (String) prcssdList[4])
		.executeUpdate();
		}
			}
	int listSize = 	eiBuyerCodeAdptablshList.get(0).size();
		LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR);
		/* Declaring Variables */
		String listAdoptdate = EMPTY_STRING;
		String listablshDate = EMPTY_STRING;
		String currentOeiB = EMPTY_STRING;
		String listPorCd = EMPTY_STRING;
		/* checking for list Size */
		if(listSize == 1)
		{
			/* Iterating processed Lsit */
			for (List<Object[]> prcssdObjList : eiBuyerCodeAdptablshList) {
				for (Object[] prcssdList : prcssdObjList) {
					String minAdptDate = (String) prcssdList[1];
					String maxAbolishDate = (String) prcssdList[2];
					if(!(minAdptDate.equalsIgnoreCase(maxAbolishDate))){
					oeiBuyPrdPk.setOeiBuyerId((String) prcssdList[0]);
					oeiBuyPrdPk.setEimMinAdptDate((String) prcssdList[1]);
					oeiBuyPrdPk.setEimMaxAblshDate((String) prcssdList[2]);
					oeiBuyPrd.setId(oeiBuyPrdPk);
					oeiBuyPrd.setPorCd((String) prcssdList[4]);
					eMgr.merge(oeiBuyPrd);
					eMgr.flush();
					eMgr.clear();
					}	
				}
			}
		}
		else{
			for (List<Object[]> prcssdObjList : eiBuyerCodeAdptablshList) {
				for (Object[] prcssdList : prcssdObjList) {
					listAdoptdate = (String) prcssdList[1];
					listablshDate = (String) prcssdList[3];
					listPorCd = (String) prcssdList[4];
					if(listAdoptdate.equalsIgnoreCase(listablshDate))
					{
						currentOeiB = (String) prcssdList[0];
						String tempDate = oeiByrPrdTempCheck(listAdoptdate,listablshDate,listPorCd,currentOeiB,tempOeiBuyrId,tempAblshDate);
						tempAblshDate = tempDate;
						tempOeiBuyrId = currentOeiB;
					}
					else{
						currentOeiB = (String) prcssdList[0];
						String tempDate = oeiByrPrdTempCheck(listAdoptdate,listablshDate,listPorCd,currentOeiB,tempOeiBuyrId,tempAblshDate);
						tempAblshDate = tempDate;
						tempOeiBuyrId = currentOeiB;
					}
					
				}
			}
		}
		LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);
		
	}
	
	/**
	 * Method for checking already Present data .
	 *
	 * @param listAdoptdate the list adoptdate
	 * @param listablshDate the listablsh date
	 * @param listPorCd the list por cd
	 * @param currentOeiB the current oei b
	 * @param tempOeiBuyrId the temp oei buyr id
	 * @param tempAblshDate the temp ablsh date
	 * @return the string
	 * @throws ParseException the parse exception
	 */
	
	private String oeiByrPrdTempCheck(String listAdoptdate,String listablshDate,
									String listPorCd,String currentOeiB, String tempOeiBuyrId, String tempAblshDate)
											throws ParseException {
		LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR);
		/* Declaring Variables */
		String eMpty = EMPTY_STRING;
		String tmpOeiByr = tempOeiBuyrId;
		String tmpDate = tempAblshDate;
		if(tmpOeiByr.equalsIgnoreCase(eMpty))
		{	
			if(!(listAdoptdate.equalsIgnoreCase(listablshDate))){
			oeiBuyPrdPk.setOeiBuyerId(currentOeiB);
			oeiBuyPrdPk.setEimMinAdptDate(listAdoptdate);
			oeiBuyPrdPk.setEimMaxAblshDate(listablshDate);
			oeiBuyPrd.setId(oeiBuyPrdPk);
			oeiBuyPrd.setPorCd(listPorCd);
			eMgr.merge(oeiBuyPrd);
			eMgr.flush();
			eMgr.clear();
			tmpOeiByr = currentOeiB;
			date = convertStringToDate(listablshDate);
			tempIncreasedAblshDate = addMonthToDate(date, 1);
			tmpDate = dateToString(tempIncreasedAblshDate);
			}
			
		}
		else if((tmpOeiByr != eMpty) && tmpOeiByr.equalsIgnoreCase(currentOeiB) )
		{
			if(tmpDate.equalsIgnoreCase(listAdoptdate))
			{
				eMgr.createNativeQuery(byrPrdUpdtQry)
				.setParameter(PRMTR_MAX_ABLSH_DT, listablshDate)
				.setParameter(PRMTR_UK_OEI_BUYEER_ID, currentOeiB)
				.setParameter(PRMTR_PORCD, listPorCd)
				.executeUpdate();
				
				tmpOeiByr = currentOeiB;
				date = convertStringToDate(listablshDate);
				tempIncreasedAblshDate = addMonthToDate(date, 1);
				tmpDate = dateToString(tempIncreasedAblshDate);
				
			}
			else
			{
				if(!(listAdoptdate.equalsIgnoreCase(listablshDate))){
				oeiBuyPrdPk.setOeiBuyerId(currentOeiB);
				oeiBuyPrdPk.setEimMinAdptDate(listAdoptdate);
				oeiBuyPrdPk.setEimMaxAblshDate(listablshDate);
				oeiBuyPrd.setId(oeiBuyPrdPk);
				oeiBuyPrd.setPorCd(listPorCd);
				eMgr.merge(oeiBuyPrd);
				eMgr.flush();
				eMgr.clear();
				tmpOeiByr = currentOeiB;
				date = convertStringToDate(listablshDate);
				tempIncreasedAblshDate = addMonthToDate(date, 1);
				tmpDate = dateToString(tempIncreasedAblshDate);}
			}
		}
		else if((tmpOeiByr != eMpty) && !tmpOeiByr.equalsIgnoreCase(currentOeiB) && !(listAdoptdate.equalsIgnoreCase(listablshDate))){
			oeiBuyPrdPk.setOeiBuyerId(currentOeiB);
			oeiBuyPrdPk.setEimMinAdptDate(listAdoptdate);
			oeiBuyPrdPk.setEimMaxAblshDate(listablshDate);
			oeiBuyPrd.setId(oeiBuyPrdPk);
			oeiBuyPrd.setPorCd(listPorCd);
			eMgr.merge(oeiBuyPrd);
			eMgr.flush();
			eMgr.clear();
			tmpOeiByr = currentOeiB;
			date = convertStringToDate(listablshDate);
			tempIncreasedAblshDate = addMonthToDate(date, 1);
			tmpDate = dateToString(tempIncreasedAblshDate);
		}
		LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);

		return tmpDate;
	}
	
}


