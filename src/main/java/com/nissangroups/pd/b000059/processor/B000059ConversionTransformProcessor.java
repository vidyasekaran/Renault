/*
 * System Name     :Post Dragon 
 * Sub system Name :Batch
 * Function ID     :B000059/B000062
 * Module          :
 * Process Outline :This program transforms B000059FileVO to CmnInterfaceData
 *
 * <Revision History>
 * Date       Name(Company Name)             Description 
 * ---------- ------------------------------ ---------------------
 * @Date      z015847(RNTBCI)               New Creation
 *
 */
package com.nissangroups.pd.b000059.processor;

import static com.nissangroups.pd.util.PDConstants.DOLLAR;
import static com.nissangroups.pd.util.PDConstants.INSIDE_METHOD;
import static com.nissangroups.pd.util.PDConstants.OUTSIDE_METHOD;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;

import com.nissangroups.pd.b000059.bean.B000059FileVO;
import com.nissangroups.pd.b000059.util.B000059CommonUtilityService;
import com.nissangroups.pd.exception.PdApplicationException;
import com.nissangroups.pd.model.CmnInterfaceData;
import com.nissangroups.pd.util.CommonUtil;

public class B000059ConversionTransformProcessor implements
		ItemProcessor<B000059FileVO, CmnInterfaceData> {

	/**
	 * Common utility service bean injection
	 */
	@Autowired(required = false)
	B000059CommonUtilityService commonutility;

	/* Constant LOG */
	private static final Log LOG = LogFactory
			.getLog(B000059ConversionTransformProcessor.class.getName());

	/**
	 * This method used to convert filevo object data into interface data
	 * 
	 * @param item
	 *            filevo object
	 * @return interface data
	 */
	@Override
	public CmnInterfaceData process(B000059FileVO item)
			throws PdApplicationException {

		LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR);
		LOG.info("InterfileID " + item.toString());

		CmnInterfaceData cid = new CmnInterfaceData();
		
		int totalOrder = commonutility.getFileSpecVO().getTotalOrderCols();
		
		for(int i= 0 ;i <= totalOrder; i++){
			CommonUtil.setBeanValue(cid, i+1, CommonUtil.getBeanValue(item, i+1));
		}
		LOG.info( DOLLAR + OUTSIDE_METHOD + DOLLAR);		
		return cid;
	}
}
