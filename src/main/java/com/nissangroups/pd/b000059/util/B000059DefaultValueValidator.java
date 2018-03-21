/*
 * System Name     :Post Dragon 
 * Sub system Name :Batch
 * Function ID     :B000059/B000062
 * Module          :
 * Process Outline : This program validates and sets default value.
 *
 * <Revision History>
 * Date       Name(Company Name)             Description 
 * ---------- ------------------------------ ---------------------
 * @Date      z015847(RNTBCI)               New Creation
 *
 */
package com.nissangroups.pd.b000059.util;

import static com.nissangroups.pd.util.PDConstants.DOLLAR;
import static com.nissangroups.pd.util.PDConstants.ERROR_MESSAGE;
import static com.nissangroups.pd.util.PDConstants.INSIDE_METHOD;
import static com.nissangroups.pd.util.PDConstants.OUTSIDE_METHOD;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.nissangroups.pd.b000059.bean.B000059FileSpecVO;
import com.nissangroups.pd.b000059.bean.B000059FileVO;
import com.nissangroups.pd.model.MstIfLayout;
import com.nissangroups.pd.util.CommonUtil;
import com.nissangroups.pd.util.PDConstants;

public class B000059DefaultValueValidator implements B000059IDataValidator {
	
	
	/* Constant LOG */
	private static final Log LOG = LogFactory
			.getLog(B000059CommonUtilityService.class.getName());

	/**
	 * Filespecvo bean injection
	 */
	@Autowired(required = false)
	B000059FileSpecVO fileSpecVO;
	
	@Autowired(required = false)
	B000059ValidateUtility b000059ValidateUtility;

	/**
	 * This method used to set default value
	 * 
	 * @param item
	 *            fileVo
	 * @param interfaceFileID
	 *            inerface file id
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void validate(B000059FileVO item, String interfaceFileID) {

		LOG.info( DOLLAR + INSIDE_METHOD + DOLLAR);
		LOG.info("Performing  B000059DefaultValueValidator"
				+ item.toString());
		List<MstIfLayout> mstIfLayoutList = null;
		MstIfLayout msit = null;
		Iterator<MstIfLayout> iterator = null;
		String defValue = null;
		try {
			mstIfLayoutList = (ArrayList<MstIfLayout>) fileSpecVO
					.getInterfaceLayoutByOrder().get(interfaceFileID);
			for (iterator = mstIfLayoutList.iterator(); iterator.hasNext();) {
				msit = iterator.next();
				
				defValue = msit.getDefaultVal();
				
				if (null != defValue && msit.getFxdLength().equalsIgnoreCase(PDConstants.CONVERSION_LAYER_FIX_LENGTH_0)) {
					CommonUtil.setBeanValue(item, msit.getColumnOrdr().intValue(), defValue);
				}
				
				if (null != msit.getFxdLength() && msit.getFxdLength().equalsIgnoreCase(PDConstants.CONVERSION_LAYER_FIX_LENGTH_2)) {
					
					String v = CommonUtil.getBeanValue(item, msit.getColumnOrdr().intValue()).trim();
					if("".equalsIgnoreCase(v)){
						b000059ValidateUtility.formatLengthCheck(msit,interfaceFileID);
					}
				}

			}
		} catch (Exception e) {
			LOG.error(ERROR_MESSAGE, e);
		} finally {
			mstIfLayoutList = null;
			msit = null;
			iterator = null;
		}
		LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);
	}

}
