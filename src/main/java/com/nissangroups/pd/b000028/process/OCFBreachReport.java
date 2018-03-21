/*
 * System Name     :Post Dragon 
 * Sub system Name :Batch
 * Function ID     :PST-DRG-B000028
 * Module          :OR Ordering					
 * Process Outline :Automatic_order_adjustment_to_OCF_Limit
 *
 * <Revision History>
 * Date       Name(Company Name)             Description 
 * ---------- ------------------------------ ---------------------
 * 24-11-2015  	  z015060(RNTBCI)               New Creation
 *
 */
package com.nissangroups.pd.b000028.process;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.nissangroups.pd.b000028.output.B000028Output;
import com.nissangroups.pd.b000028.output.R000005ReportDao;
import com.nissangroups.pd.repository.SpecRepository;

/**
 * The Class OCFBreachReport.
 *
 * @author z015060
 */

public class OCFBreachReport {

	/** Constant LOG */
	private static final Log LOG = LogFactory.getLog(OCFBreachReport.class
			.getName());

	@Autowired(required = false)
	private SpecRepository specRep;

	@Autowired(required = false)
	private R000005ReportDao objR000005Report;

	/** Constructor OCFBreachReport */
	public OCFBreachReport() {
	}

	public B000028Output executeProcess(B000028Output object) {
		object.setBuyerMnthlyOCFUsage(specRep.getMnthlyOCFTrn(object));

		object.setBuyerMnthlyTemp(specRep.getBuyerMnthlyTemp(object));
		
		object.setBreachOCFChk(specRep.getOCFBreach(object));

		LOG.info(object.getBreachOCFChk().size()+" OCF BREACH REPORT");
		for (Object[] report : object.getBreachOCFChk()) {
			R000005ReportDao reportDao = new R000005ReportDao();
			reportDao.setPorCd(object.getObjB000028ParamOutput().getPorCd());
			reportDao.setOrdrTkBsMnth(object.getObjB000028ParamOutput()
					.getOrdrTkBsMnth());
			reportDao.setPrdMnth(report[3].toString());
			reportDao.setCarSrs(report[2].toString());
			reportDao.setByrGrp(report[4].toString());
			reportDao.setOcfFeatCd(report[7].toString());
			reportDao.setOcdDescShrt(report[9].toString());
			reportDao.setOcfDescLng(report[10].toString());
			reportDao.setOcfRgn(report[11].toString());
			reportDao.setOcfLmt(report[6].toString());
			reportDao.setUsage(report[5].toString());
			reportDao.setDiff(report[8].toString());
			objR000005Report.getReportList().add(reportDao);
		}

		return object;
	}

}
