/*
 * System Name     :Post Dragon 
 * Sub system Name :Batch
 * Function ID     :PST-DRG-B000008
 * Module          :Monthly Ordering					
 * Process Outline :Create Monthly Order Base Data
 *
 * <Revision History>
 * Date       Name(Company Name)             Description 
 * ---------- ------------------------------ ---------------------
 * 21-10-2015  	  z015060(RNTBCI)               New Creation
 *
 */
package com.nissangroups.pd.b000008.process;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.nissangroups.pd.b000008.output.B000008P1Output;
import com.nissangroups.pd.b000008.output.B000008ParamOutput;
import com.nissangroups.pd.util.CommonUtil;
import com.nissangroups.pd.util.PDConstants;

/**
 * The Class PrdMnthCalProcess.
 *
 * @author z015060
 */
public class PrdMnthCalProcess {
	
	/** Constant LOG */
	private static final Log LOG = LogFactory.getLog(PrdMnthCalProcess.class
			.getName());
	
	B000008ParamOutput objB000008ParamOutput = new B000008ParamOutput();

	/** Constructor with B000008ParamOutput parameter */
	public PrdMnthCalProcess(B000008ParamOutput objB000008ParamOutput2) {
		this.objB000008ParamOutput=objB000008ParamOutput2;
	}

	
	/**
	 * calculate the Maximum production month
	 * Process P0003.3
	 * @param object
	 * @return  PORCD, productionFamilyCode, carSeries, MaximumProductionMonth, OrderTakeBaseMonth, StageCode,ProductionStageCode
	 * @throws ParseException
	 */
	public B000008P1Output executeProcess(B000008P1Output object) throws ParseException {
		LOG.info("PrdMnthCalProcess Process P0003.3");
		DateFormat formatter = new SimpleDateFormat(PDConstants.DATE_FORMAT_MONTH);
		LOG.info("PrdMnthCalProcess input is "+object.getHrznList().size());	
		List<Object[]> maxProdMonthCal = new ArrayList<Object[]>();
		for (Object[] maxPrdList : object.getHrznList()) {
			Date ordrtkBsMnth = formatter.parse(maxPrdList[4].toString());
			int horizn = Integer.parseInt(maxPrdList[3].toString());
			for(int i=0;i<horizn;i++){
			String maxMonthCal=formatter.format(CommonUtil.addMonthToDate(ordrtkBsMnth,i));
			maxProdMonthCal.add(new Object[] { (String) maxPrdList[0],
					(String) maxPrdList[1], (String) maxPrdList[2], maxMonthCal,
					(String) maxPrdList[4], (String) maxPrdList[5],
					objB000008ParamOutput.getPrdStgCd() });
			}
		}
		object.setMaxPrdList(maxProdMonthCal);
		LOG.info("P0003.3 PrdMnthCalProcess output is "+ object.getMaxPrdList().size() );
		return object;
	}



}
