/*
 * System Name     :Post Dragon 
 * Sub system Name :Batch 
 * Function ID     :PST-DRG-B000040
 * Module          :
 * Process Outline : 
 *
 * <Revision History>
 * Date       Name(Company Name)             Description 
 * ---------- ------------------------------ ---------------------
 * @Date  	  z015847(RNTBCI)               New Creation
 *
 */
package com.nissangroups.pd.b000040.util;

import static com.nissangroups.pd.util.PDConstants.PERSISTENCE_NAME;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;

import com.nissangroups.pd.b000040.bean.B000040PrdOrdrDetailReport;
import com.nissangroups.pd.b000040.bean.B000040ServiceErrorReport;
import com.nissangroups.pd.b000040.bean.B000040WklyOcfBreachReport;
import com.nissangroups.pd.b000040.bean.B000040WklyOrderSpecErrorReport;


/**
 * This class is used to generate below mentioned reports
 * 
 * a. Service Error Report
 * b. Weekly Order Spec Error Report
 * c. Create Production Order Detail Report
 * d. Create Weekly OCF Breach Report
 * 
 * 
 * @author z015847
 *
 */
public class B000040ErrorReportService 
{

	@Autowired(required = false)
	B000040UtilityService utilService;
	
	public void createServiceErrorReport(B000040ServiceErrorReport svcErrRpt)
	{
		utilService.getSvcErrorReportList().add(svcErrRpt);
	}
	
	public void createWeeklyOrderSpecErrorReport(B000040WklyOrderSpecErrorReport wklyOrdrSpecErrRpt)
	{
		
		utilService.getWklyOrdrSpecErrorReportList().add(wklyOrdrSpecErrRpt);
	}
	
	public void createProductionOrdrDetailReport(B000040PrdOrdrDetailReport prdOrdrDetailRpt)
	{
		utilService.getPrdOrdrDetailReportList().add(prdOrdrDetailRpt);
	}
	
	public void createWeeklyOCFBreechReport(B000040WklyOcfBreachReport wklyOcfBreachRpt)
	{
		utilService.getWklyOcfBreachReportList().add(wklyOcfBreachRpt);
	}
}
